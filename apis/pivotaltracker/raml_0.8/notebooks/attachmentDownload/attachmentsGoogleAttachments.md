---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8017/versions/8183/portal/pages/6838/preview
apiNotebookVersion: 1.1.66
title: Attachments, google attachments
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

// Read about the PivotalTracker Attachment Download API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8019/versions/8185/contracts

API.createClient('attachmentClient', '/apiplatform/repository/public/organizations/30/apis/8019/versions/8185/definition');

```

```javascript

function modifyRequest(request){

  request.setRequestHeader("X-TrackerToken", API_TOKEN);

}

```

```javascript

API.set(client, 'beforeSend', new function(){return modifyRequest})

API.set(attachmentClient, 'beforeSend', new function(){return modifyRequest})

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



if(!project_id){

  createProjectResponse = client.projects.post({ "name" : projectName })

  project_id = createProjectResponse.body.id

}

```



Create a story for the test project.



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

story_id = createStoryResponse.body.id

```



Upload a file to the Tracker server. Tracker will create a matching file_attachment object which can be attached to a future comment on a story



```javascript

formData = new FormData()

formData.append("comment[text]", "asdasd" )

formData.append("file", new Blob([0,0,0,0,0,0]), "myFile.txt")

uploadsResponse = client.projects.project_id( project_id ).uploads.post(formData)

```

```javascript

assert.equal( uploadsResponse.status, 200 )

```



Let's parse the response and put it into array



```javascript

attachmentObject = JSON.parse(uploadsResponse.body)

file_attachments = new Array()

file_attachments.push(attachmentObject)

attachmentId = attachmentObject.id

```



Now we can create a story comment wich contains the uploaded attachment.



```javascript

addStoryCommentsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.post({

  "text" : "This is my test notebook comment with attachment",

  "file_attachments" : file_attachments

})

```

```javascript

commentId = addStoryCommentsResponse.body.id

```



Retrieve content of the attached file.



```javascript

file_attachmentsDownloadResponse = attachmentClient.file_attachments.file_attachment_id(attachmentId).download.get()

```

```javascript

assert.equal( file_attachmentsDownloadResponse.status, 200 )

```



Deletes an attachment



```javascript

deleteFile_attachmentsResponse = client.projects.project_id( project_id ).stories.story_id( story_id ).comments.comment_id( commentId ).file_attachments.file_attachment_id( attachmentId ).delete()

```

```javascript

assert.equal( deleteFile_attachmentsResponse.status, 204 )

```



Fetch items describing the user's available Google resources



```javascript

google_attachmentsResponse = client.google_attachments.get()

```

```javascript

assert.equal( google_attachmentsResponse.status, 200 )

googleAttachment = google_attachmentsResponse.body.length > 0 ? google_attachmentsResponse.body[0] : null

```



Now let's create a story comment wich contains with the google doc attached.



```javascript

if(googleAttachment){

  addStoryCommentsResponse2 = client.projects.project_id(project_id).stories.story_id(story_id).comments.post({

    "text" : "This is my test notebook comment with a google attachment",

    "google_attachments" : [ googleAttachment ]

  })

  commentId2 = addStoryCommentsResponse2.body.id

}

```



Now let's pick a google attachment ID



```javascript

if(googleAttachment){

  commentResponse = client.projects.project_id(project_id).stories.story_id(story_id).comments.comment_id(commentId2).get({

    fields:"id,google_attachment_ids"

  })

  googleAttachmentId = commentResponse.body.google_attachment_ids[0]

}

```



Deletes a google attachment



```javascript

if(googleAttachment){

  deleteGoogle_attachmentsResponse = client.projects.project_id(project_id).stories.story_id(story_id).comments.comment_id(commentId2 ).google_attachments.google_attachment_id(googleAttachmentId).delete()

}

```

```javascript

if(googleAttachment){

  assert.equal( deleteGoogle_attachmentsResponse.status, 204 )

}

```



Garbage collection. Delete the created test project.



```javascript

deleteProjectResponse = client.projects.project_id( project_id ).delete()

```