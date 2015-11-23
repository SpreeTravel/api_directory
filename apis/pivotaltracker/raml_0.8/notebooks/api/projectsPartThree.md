---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8017/versions/8183/portal/pages/6837/preview
apiNotebookVersion: 1.1.66
title: Projects part 3
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

API_TOKEN = prompt("Please, enter your PivotalTracker API TOKEN")

```

```javascript

// Read about the Pivotal Tracker at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8017/versions/8183/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8017/versions/8183/definition');

```

```javascript

function modifyRequest(request){

  request.setRequestHeader("X-TrackerToken", API_TOKEN);

}

```

```javascript

API.set(client, 'beforeSend', new function(){return modifyRequest})

```



Retrieve ID of the authenticated user



```javascript

currentUser_id = client.me.get().body.id

```



Retrieve a test project or create one if it does not already exist.



```javascript

projectName = "TestNotebookProject"

project_id = null

projectsResponse = client.projects.get()

for( var i in projectsResponse.body){

  if( projectsResponse.body[i].name == projectName ){

    project_id = projectsResponse.body[i].id

  }

}



if( !project_id ){

  createProjectResponse = client.projects.post({ "name" : projectName })

  project_id = createProjectResponse.body.id

}

```



Delete a label which could have been created during earlier notebook runs.



```javascript

labelName = "my new test notebook label"

label_id = null

labelsResponse = client.projects.project_id( project_id ).labels.get()

for( var i in labelsResponse.body){

  if( labelsResponse.body[i].name == labelName ){

    label_id = labelsResponse.body[i].id

  }

}

if( label_id ){

  deleteLabelResponse =  client.projects.project_id( project_id ).labels.label_id( label_id ).delete()

}

```



Creates a label on the project



```javascript

createLabelsResponse = client.projects.project_id( project_id ).labels.post({

  "name" : labelName

})

```

```javascript

assert.equal( createLabelsResponse.status, 200 )

label_id = createLabelsResponse.body.id

```



Provides all of the project's labels



```javascript

labelsResponse = client.projects.project_id( project_id ).labels.get()

```

```javascript

assert.equal( labelsResponse.status, 200 )

```



Provides a project's label



```javascript

labelResponse = client.projects.project_id( project_id ).labels.label_id( label_id ).get()

```

```javascript

assert.equal( labelResponse.status, 200 )

```



Updates a project's label



```javascript

updateLabelResponse = client.projects.project_id( project_id ).labels.label_id( label_id ).put({

  "name" : "update my new label"

})

```

```javascript

assert.equal( updateLabelResponse.status, 200 )

```



Delete a project's label



```javascript

deleteLabelResponse = client.projects.project_id( project_id ).labels.label_id( label_id ).delete()

```

```javascript

assert.equal( deleteLabelResponse.status, 204 )

```



Create a new story



```javascript

createStoryResponse = client.projects.project_id( project_id ).stories.post({

  "comments":

    [

      {

        "text":"Just ray shielding? What about proton weapons?"

      }

    ],

  "name":"Exhaust ports are ray shielded"

})

```

```javascript

assert.equal( createStoryResponse.status, 200 )

```

```javascript

story_id = createStoryResponse.body.id

```



Provides selected stories



```javascript

storiesResponse = client.projects.project_id( project_id ).stories.get()

```

```javascript

assert.equal( storiesResponse.status, 200 )

```



Returns the specified story



```javascript

storyResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).get()

```

```javascript

assert.equal( storyResponse.status, 200 )

```



Updates a story



```javascript

updateStoryResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).put({

  "labels":[

    {

      "name":"newnew"

    },

    {

      "name":"labellabel"

    } 

  ]

})

```

```javascript

assert.equal( updateStoryResponse.status, 200 )

```



Returns the task that was created



```javascript

createTasksResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).tasks.post({

  "description":"port 270"

})

```

```javascript

assert.equal( createTasksResponse.status, 200 )

```

```javascript

task_id = createTasksResponse.body.id

```



Returns the tasks on a specified story



```javascript

tasksResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).tasks.get()

```

```javascript

assert.equal( tasksResponse.status, 200 )

```



Returns the specified task on a specified story.



```javascript

taskResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).tasks.task_id( task_id ).get()

```

```javascript

assert.equal( taskResponse.status, 200 )

```



Updates the specified task.



```javascript

updateTaskResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).tasks.task_id( task_id ).put({

  "complete" : true ,

})

```

```javascript

assert.equal( updateTaskResponse.status, 200 )

```



Deletes the specified task.



```javascript

deleteTaskResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).tasks.task_id( task_id ).delete()

```

```javascript

assert.equal( deleteTaskResponse.status, 204 )

```



Returns the labels on a specified story.



```javascript

storylabelsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).labels.get()

```

```javascript

assert.equal( storylabelsResponse.status, 200 )

```



Returns the label that was created



```javascript

createStorylabelResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).labels.post({

  "name" : "my new label"

})

```

```javascript

assert.equal( createStorylabelResponse.status, 200 )

story_label_id = createStorylabelResponse.body.id

```



Removes the label from the story



```javascript

deleteLabelsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).labels.label_id( story_label_id ).delete()

```

```javascript

assert.equal( deleteLabelsResponse.status, 200 )

```



Add a user as an owner of the story



```javascript

addStoryOwnersResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).owners.post({

  "id" : currentUser_id

})

```

```javascript

assert.equal( addStoryOwnersResponse.status, 200 )

```



Returns the owners of a specified story



```javascript

storyOwnersResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).owners.get()

```

```javascript

assert.equal( storyOwnersResponse.status, 200 )

```



Removes ownership of a story from a person



```javascript

deleteStoryOwnerResponse = client.projects.project_id( project_id).stories.story_id( story_id ).owners.person_id( currentUser_id ).delete()

```

```javascript

assert.equal( deleteStoryOwnerResponse.status, 204 )

```



Returns the specified story's comments



```javascript

commentsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.get()

```

```javascript

assert.equal( commentsResponse.status, 200 )

```



Add a new comment



```javascript

addStoryCommentsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.post({

  "text" : "This is my test notebook comment"

})

```

```javascript

assert.equal( commentsResponse.status, 200 )

comment_id = addStoryCommentsResponse.body.id

```



Gets a comment



```javascript

storyCommentResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.comment_id( comment_id ).get()

```

```javascript

assert.equal( storyCommentResponse.status, 200 )

```



Updates a comment



```javascript

updateStoryCommentsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.comment_id( comment_id ).put({

  "text" : "This is my update test notebook comment"

})

```

```javascript

assert.equal( updateStoryCommentsResponse.status, 200 )

```



Deletes a comment



```javascript

deleteStoryCommentResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.comment_id( comment_id ).delete()

```

```javascript

assert.equal( deleteStoryCommentResponse.status, 204 )

```



Return list of the selected story's activity.



```javascript

activityResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).activity.get()

```

```javascript

assert.equal( activityResponse.status, 200 )

```



Deletes a story



```javascript

deleteStoryResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).delete()

```

```javascript

assert.equal( deleteStoryResponse.status, 204 )

```

```javascript

epicName = "Epic RAML Test Notebook"

epic_id = 0

epicsResponse = client.projects.project_id( project_id ).epics.get()

for( var i in epicsResponse.body){

  if( epicsResponse.body[i].name == epicName ){

    epic_id = epicsResponse.body[i].id

  }

}

if( 0!= epic_id ){

  client.projects.project_id( project_id ).epics.epic_id( epic_id ).delete()

}

```



Create a new epic



```javascript

createEpicsResponse = client.projects.project_id( project_id ).epics.post({

  "name":"Epic RAML Test Notebook"

})

```

```javascript

assert.equal( createEpicsResponse.status, 200 )

epic_id = createEpicsResponse.body.id

```



Returns a list of all epics in the project.



```javascript

epicsResponse = client.projects.project_id( project_id ).epics.get()

```

```javascript

assert.equal( epicsResponse.status, 200 )

```



Returns the specified epic



```javascript

epicResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).get()

```

```javascript

assert.equal( epicResponse.status, 200 )

```



Updates an epic



```javascript

updateEpicResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).put({

  "description" : "new test notebook desc"

})

```

```javascript

assert.equal( updateEpicResponse.status, 200 )

```



Return list of the selected epic's activity



```javascript

activityEpicResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).activity.get()

```

```javascript

assert.equal( activityEpicResponse.status, 200 )

```





Add a new comment



```javascript

addEpicCommentsResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).comments.post({

  "text" : "This is my test epics comment"

})

```

```javascript

assert.equal( addEpicCommentsResponse.status, 200 )

```

```javascript

epic_comment_id = addEpicCommentsResponse.body.id

```



Returns the specified epic's comments



```javascript

commentsEpicsResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).comments.get()

```

```javascript

assert.equal( commentsEpicsResponse.status, 200 )

```



Gets a comment



```javascript

epicCommentResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).comments.comment_id( epic_comment_id ).get()

```

```javascript

assert.equal( epicCommentResponse.status, 200 )

```



Updates a comment



```javascript

updateEpicCommentsResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).comments.comment_id( epic_comment_id ).put({

  "text" : "This is my comment"

})

```

```javascript

assert.equal( updateEpicCommentsResponse.status, 200 )

```



Deletes a comment



```javascript

deleteEpicCommentsResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).comments.comment_id( epic_comment_id ).delete()

```

```javascript

assert.equal( deleteEpicCommentsResponse.status, 204 )

```



Deletes an epics.



```javascript

deleteEpicResponse = client.projects.project_id( project_id ).epics.epic_id( epic_id ).delete()

```

```javascript

assert.equal( deleteEpicResponse.status, 204 )

```



Garbage collection. Delete the created test project.



```javascript

client.projects.project_id( project_id ).delete()

```