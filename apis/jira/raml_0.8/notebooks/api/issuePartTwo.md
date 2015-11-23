---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6627/edit
apiNotebookVersion: 1.1.66
title: Issue Part 2
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

PROTOCOL = null

{

  useHttps = window.confirm("Do you want to use 'https' protocol?")

  if(useHttps){

    PROTOCOL = "https"

  }

  else{

    PROTOCOL = "http"

    window.alert("The protocol has been set to 'http'")

  }

}

DOMAIN = prompt("Please, enter your Jira domain. For example, for jira URL 'https://jirahost.atlassian.net' enter 'jirahost.atlassian.net'")

```



Name of a test  user which is operated by the Notebook



```javascript

TEST_USER_NAME = "notebook-test-issue-user"

```



Auxiliary project selecting method



```javascript

function selectProject(){

  

  projects = client.project.get().body

  var message = ''

  message += 'The Notebook needs a project to operate with.\nPlease, select a project from the following list:\n'

  for(var ind in projects){

    message += '\n' + ind + ": " + projects[ind].name + '\n'

  }  

  input = prompt(message)

  if(!input){

    return null

  }

  var parsedInd = Number.parseInt(input)

  if(Number.isInteger(parsedInd)&&parsedInd>=0){

    return projects[parsedInd].id

  }

  return null

}

```



Auxiliary method for retrieving role ID



```javascript

function extractSomeRoleId(projectId,requiredRoleName){

  roles = client.project.projectIdOrKey(projectId).role.get().body

  for(var roleName in roles){

    if(roleName==requiredRoleName){

      var url = roles[roleName]    

      if(url){

        var ind = url.lastIndexOf('/')

        if(ind>=0){

          var roleName = url.substring(ind+1)

          return roleName

        }

      }

    }

  }

}

```

```javascript

// Read about the Jira API v2 at http://api-portal.anypoint.mulesoft.com/onpositive/api/jira-api-v2

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7868/versions/8022/definition', {baseUriParameters: {

  domain: DOMAIN,

  protocol: PROTOCOL

}});

```

```javascript

API.authenticate(client)

```



Select a project to create issue for



```javascript

projectId = selectProject()

```



We need a test user which will watch an issue. Let's create him if he does not already exist.



```javascript

userCreateResponse = client.user.post({

  "name" : TEST_USER_NAME, 

  "password" : "aoeuAOEUaoeu1!" ,

  "emailAddress" : "mail@somehost.com" ,

  "displayName" : "Notebok Test User"

})

```



Set the user's role to "Users"



```javascript

roleId = extractSomeRoleId(projectId,"Users")

client.project.projectIdOrKey(projectId).role.id(roleId).post({

  "user" : [ TEST_USER_NAME ]

})

```



Returns a list of all issue priorities



```javascript

prioritiesResponse = client.priority.get()

```

```javascript

assert.equal( prioritiesResponse.status, 200 )

priorityId = prioritiesResponse.body[0].id

```



Returns an issue priority



```javascript

priorityResponse = client.priority.id(priorityId).get()

```

```javascript

assert.equal( priorityResponse.status, 200 )

```



Returns a list of all issue types visible to the use



```javascript

issueTypesResponse = client.issuetype.get()

```

```javascript

assert.equal( issueTypesResponse.status, 200 )

issueTypeId = issueTypesResponse.body[0].id

```



Returns a full representation of the issue type that has the given id



```javascript

issueTypeResponse = client.issuetype.id(issueTypeId).get()

```

```javascript

assert.equal( issueTypeResponse.status, 200 )

```



Creates an issue or a sub-task from a JSON representation



```javascript

issueCreateResponse = client.issue.post({

  "update" : {    

  } ,

  "fields" : {

    "project" : {

      "id" : projectId

    } ,

    "summary" : "Issue 3. This test issue was created by the API Notebook" ,

    "issuetype" : {

      "id" : issueTypeId

    } ,

    "priority" : {

      "id" : priorityId

    } ,

    "labels" : [

      "bugfix" ,

      "blitz_test"

    ] ,

    "timetracking" : {

      "originalEstimate" : "10" ,

      "remainingEstimate" : "5"

    },

    "environment" : "environment" ,

    "description" : "description"

 }

})

```

```javascript

assert.equal( issueCreateResponse.status, 201 )

issueId = issueCreateResponse.body.id

```



A REST sub-resource representing the remote issue links on the issue



```javascript

remoteLinksResponse = client.issue.issueIdOrKey(issueId).remotelink.get()

```

```javascript

assert.equal( remoteLinksResponse.status, 200 )

```



Creates or updates a remote issue link from a JSON representation. If a globalId is provided and a remote issue link exists with that globalId, the remote issue link is updated. Otherwise, the remote issue link is created



```javascript

remoteLinkCreateResponse = client.issue.issueIdOrKey(issueId).remotelink.post({

  "globalId" : "system=http://www.mycompany.com/support&id=1" ,

  "application" : {

    "type" : "com.acme.tracker" ,

    "name" : "My Acme Tracker"

  } ,

  "relationship" : "causes" ,

  "object" : {

    "url" : "http://www.mycompany.com/support?id=1" ,

    "title" : "TSTSUP-111" ,

    "summary" : "Crazy customer support issue" ,

    "icon" : {

      "url16x16" : "http://www.mycompany.com/support/ticket.png" ,

      "title" : "Support Ticket"

    } ,

    "status" : {

      "resolved" : true ,

      "icon" : {

        "url16x16" : "http://www.mycompany.com/support/resolved.png" ,

        "title" : "Case Closed" ,

        "link" : "http://www.mycompany.com/support?id=1&details=closed"

      }

    }

  }

})

```

```javascript

assert.equal( remoteLinkCreateResponse.status, 201 )

remoteLinkId = remoteLinkCreateResponse.body.id

```



Get the remote issue link with the given id on the issue



```javascript

remoteLinkResponse = client.issue.issueIdOrKey(issueId).remotelink.linkId(remoteLinkId).get()

```

```javascript

assert.equal( remoteLinkResponse.status, 200 )

remoteLinkGlobalId = remoteLinkResponse.body.globalId

```



Updates a remote issue link from a JSON representation. Any fields not provided are set to null



```javascript

remoteLinkUpdateResponse = client.issue.issueIdOrKey(issueId).remotelink.linkId(remoteLinkId).put({

  "globalId" : "system=http://www.mycompany.com/support&id=1" ,

  "application" : {

    "type" : "com.acme.tracker" ,

    "name" : "My Acme Tracker"

  } ,

  "relationship" : "causes" ,

  "object" : {

    "url" : "http://www.mycompany.com/support?id=1" ,

    "title" : "TSTSUP-111" ,

    "summary" : "Crazy customer support issue" ,

    "icon" : {

      "url16x16" : "http://www.mycompany.com/support/ticket.png" ,

      "title" : "Support Ticket"

    } ,

    "status" : {

      "resolved" : true ,

      "icon" : {

        "url16x16" : "http://www.mycompany.com/support/resolved.png" ,

        "title" : "Case Closed" ,

        "link" : "http://www.mycompany.com/support?id=1&details=closed"

      }

    }

  }

})

```

```javascript

assert( remoteLinkUpdateResponse.status == 200 || remoteLinkUpdateResponse.status == 204 )

```



Delete the remote issue link with the given global id on the issue



```javascript

remoteLinksDeleteResponse = client.issue.issueIdOrKey(issueId).remotelink.delete({},{query:{globalId:remoteLinkGlobalId}})

```

```javascript

assert.equal( remoteLinksDeleteResponse.status, 204 )

```



We need another remote link in order to test another way to delete it.



```javascript

remoteLinkCreateResponse2 = client.issue.issueIdOrKey(issueId).remotelink.post({

  "globalId" : "system=http://www.mycompany.com/support&id=2" ,

  "application" : {

    "type" : "com.acme.tracker" ,

    "name" : "My Acme Tracker"

  } ,

  "relationship" : "causes" ,

  "object" : {

    "url" : "http://www.mycompany.com/support?id=1" ,

    "title" : "TSTSUP-111" ,

    "summary" : "Crazy customer support issue" ,

    "icon" : {

      "url16x16" : "http://www.mycompany.com/support/ticket.png" ,

      "title" : "Support Ticket"

    } ,

    "status" : {

      "resolved" : true ,

      "icon" : {

        "url16x16" : "http://www.mycompany.com/support/resolved.png" ,

        "title" : "Case Closed" ,

        "link" : "http://www.mycompany.com/support?id=1&details=closed"

      }

    }

  }

})

```



Delete the remote issue link with the given id on the issue



```javascript

assert.equal( remoteLinkCreateResponse2.status, 201 )

remoteLinkId = remoteLinkCreateResponse2.body.id

```

```javascript

remoteLinkDeleteResponse = client.issue.issueIdOrKey(issueId).remotelink.linkId(remoteLinkId).delete()

```

```javascript

assert.equal( remoteLinkDeleteResponse.status, 204 )

```



A REST sub-resource representing the voters on the issue



```javascript

votesResponse = client.issue.issueIdOrKey(issueId).votes.get()

```

```javascript

assert.equal( votesResponse.status, 200 )

```



Returns the list of watchers for the issue with the given key



```javascript

watchersResponse = client.issue.issueIdOrKey(issueId).watchers.get()

```

```javascript

assert.equal( watchersResponse.status, 200 )

```



Adds a user to an issue's watcher list



```javascript

//See https://answers.atlassian.com/questions/61770/please-help-got-error-400-when-trying-to-add-watcher-using-rest-api

watcherAddResponse = client.issue.issueIdOrKey(issueId).watchers.post('"'+TEST_USER_NAME+'"',{headers:{"Content-Type":"application/json"}})

```

```javascript

assert.equal( watcherAddResponse.status, 204 )

```



Removes a user from an issue's watcher list



```javascript

watcherDeleteResponse = client.issue.issueIdOrKey(issueId).watchers.delete({},{query:{username:TEST_USER_NAME}})

```

```javascript

assert.equal( watcherDeleteResponse.status, 204 )

```



Returns all work logs for an issue



```javascript

worklogResponse = client.issue.issueIdOrKey(issueId).worklog.get()

```

```javascript

assert.equal( worklogResponse.status, 200 )

```



Adds a new worklog entry to an issue



```javascript

workLogCreateResponse = client.issue.issueIdOrKey(issueId).worklog.post({

  "author" : {

    "name" : TEST_USER_NAME ,

    "active" : true

  } ,

  "updateAuthor" : {

    "name" : TEST_USER_NAME ,

    "active" : true

  } ,

  "comment" : "I did some work here." ,

  "started" : "2014-05-27T08:32:06.865+0000",

  "timeSpentSeconds" : 12000 ,

  "id" : "100028"

})

```

```javascript

assert.equal( workLogCreateResponse.status, 201 )

worklogId = workLogCreateResponse.body.id

```



Returns a specific worklog



```javascript

workLogResponse = client.issue.issueIdOrKey(issueId).worklog.id(worklogId).get()

```

```javascript

assert.equal( workLogResponse.status, 200 )

```



Updates an existing worklog entry using its JSON representation



```javascript

workLogUpdateResponse = client.issue.issueIdOrKey( issueId ).worklog.id(worklogId).put({

  "author" : {

    "name" : TEST_USER_NAME ,

    "active" : true

  } ,

  "updateAuthor" : {

    "name" : TEST_USER_NAME ,

    "active" : true

  } ,

  "comment" : "I did even more work here." ,

  "started" : "2014-05-27T08:32:06.865+0000",

  "timeSpentSeconds" : 12000 ,

  "id" : worklogId

})

```

```javascript

assert.equal( workLogUpdateResponse.status, 200 )

```



Deletes an existing worklog entry 



```javascript

workLogDeleteResponse = client.issue.issueIdOrKey( issueId ).worklog.id(worklogId).delete()

```

```javascript

assert.equal( workLogDeleteResponse.status, 204 )

```



Add one or more attachments to an issue



```javascript

//NOT SUPPORTED

//attachmentsResponse = client.issue.issueIdOrKey(issueId).attachments.post({})

```

```javascript

//assert.equal( attachmentsResponse.status, 201 )

```



Delete an issue. If the issue has subtasks you must set the parameter deleteSubtasks=true to delete the issue. You cannot delete an issue without its subtasks also being deleted



```javascript

issueDeleteResponse = client.issue.issueIdOrKey(issueId).delete()

```

```javascript

assert.equal( issueDeleteResponse.status, 204 )

```



Returns the meta data for creating issues. This includes the available projects, issue types and fields, including field types and whether or not those fields are required. Projects will not be returned if the user does not have permission to create issues in that project



```javascript

createmetaResponse = client.issue.createmeta.get()

```

```javascript

assert.equal( createmetaResponse.status, 200 )

```



Garbage collection: removing the test user.



```javascript

client.user.delete({},{query:{ username:TEST_USER_NAME }})

```