---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6626/preview
apiNotebookVersion: 1.1.66
title: IssueLink, Issue Part 1
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

Let's delete a user which could have been created during previous notebook runs.

```javascript
client.user.delete({},{query:{username:TEST_USER_NAME}})
```



We need a test user which will be assigned an issue. Let's create him if he does not already exist.



```javascript

userCreateResponse = client.user.post({

  "name" : TEST_USER_NAME, 

  "password" : "aoeuAOEUaoeu1!" ,

  "emailAddress" : "mail@somehost.com" ,

  "displayName" : "Notebok Test User"

})

```



Set the user's role to "Developers"



```javascript

roleId = extractSomeRoleId(projectId,"Developers")

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

    "summary" : "Issue 1. This test issue was created by the API Notebook" ,

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



Creates issues or sub-tasks from a JSON representation.

Creates many issues in one bulk operation.



```javascript

bulkResponse = client.issue.bulk.post({

  "issueUpdates" : [

    {

       "update" : {    

        } ,

        "fields" : {

          "project" : {

            "id" : projectId

          } ,

          "summary" : "Issue 2. This test issue was created by the API Notebook" ,

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

          } ,

          "environment" : "environment" ,

          "description" : "description"

       }

    }

  ]

})

```

```javascript

assert.equal( bulkResponse.status, 201 )

issueId2 = bulkResponse.body.issues[0].id

```



Returns a full representation of the issue for the given issue key



```javascript

issueResponse = client.issue.issueIdOrKey(issueId).get()

```

```javascript

assert.equal( issueResponse.status, 200 )

```



Edits an issue from a JSON representation



```javascript

issueUpdateResponse = client.issue.issueIdOrKey( issueId ).put({

  "update" : {

    "description": [ { set: "Updated description" } ]

  }  

})

```

```javascript

assert.equal( issueUpdateResponse.status, 204 )

```



Assigns an issue to a user. You can use this resource to assign issues when the user submitting the request has the assign permission but not the edit issue permission. If the name is "-1" automatic assignee is used. A null name will remove the assignee



```javascript

assigneeResponse = client.issue.issueIdOrKey( issueId ).assignee.put({ "name" : TEST_USER_NAME })

```

```javascript

assert.equal( assigneeResponse.status, 204 )

```



Returns all comments for an issue



```javascript

commentsResponse = client.issue.issueIdOrKey( issueId ).comment.get()

```

```javascript

assert.equal( commentsResponse.status, 200 )

```



Adds a new comment to an issue



```javascript

commentAddResponse = client.issue.issueIdOrKey( issueId ).comment.post({

  "body" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper." ,

  "visibility" : {

    "type" : "role" ,

    "value" : "Administrators"

  }

})

```

```javascript

assert.equal( commentAddResponse.status, 201 )

commentId = commentAddResponse.body.id

```



Returns all comments for an issue



```javascript

commentResponse = client.issue.issueIdOrKey(issueId).comment.id(commentId).get()

```

```javascript

assert.equal( commentResponse.status, 200 )

```



Updates an existing comment using its JSON representation



```javascript

commentUpdateResponse = client.issue.issueIdOrKey(issueId).comment.id(commentId).put({

  "body" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper." ,

  "visibility" : {

    "type" : "role" ,

    "value" : "Administrators"

  }

})

```

```javascript

assert.equal( commentUpdateResponse.status, 200 )

```



Deletes an existing comment 



```javascript

commentDeleteResponse = client.issue.issueIdOrKey(issueId).comment.id(commentId).delete()

```

```javascript

assert.equal( commentDeleteResponse.status, 204 )

```



Returns the meta data for editing an issue



```javascript

editmetaResponse = client.issue.issueIdOrKey(issueId).editmeta.get()

```

```javascript

assert.equal( editmetaResponse.status, 200 )

```



Sends a notification (email) to the list or recipients defined in the request



```javascript

notifyResponse = client.issue.issueIdOrKey(issueId).notify.post({

  "subject" : "Duis eu justo eget augue iaculis fermentum." ,

  "textBody" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper." ,

  "htmlBody" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper." ,

  "to" : {

    "reporter" : false ,

    "assignee" : false ,

    "watchers" : true ,

    "voters" : true ,

    "users" : [

      {

        "name" : TEST_USER_NAME ,

        "active" : true

      }

    ]

  } 

})

```

```javascript

assert.equal( notifyResponse.status, 204 )

```



Returns the keys of all properties for the issue identified by the key or by the id



```javascript

propertiesResponse = client.issue.issueIdOrKey( issueId ).properties.get()

```

```javascript

assert.equal( propertiesResponse.status, 200 )

propertyKey = "TestProperty"

```



Sets the value of the specified issue's property



```javascript

propertySetResponse = client.issue.issueIdOrKey(issueId).properties.propertyKey(propertyKey).put({value:"TestValue"})

```

```javascript

assert( propertySetResponse.status == 200 || propertySetResponse.status == 201 )

```



Returns the value of the property with a given key from the issue identified by the key or by the id. The user who retrieves the property is required to have permissions to read the issue



```javascript

propertyResponse = client.issue.issueIdOrKey( issueId ).properties.propertyKey( propertyKey ).get()

```

```javascript

assert.equal( propertyResponse.status, 200 )

```



Removes the property from the issue identified by the key or by the id. The user removing the property is required to have permissions to edit the issue



```javascript

propertyDeleteResponse = client.issue.issueIdOrKey(issueId).properties.propertyKey(propertyKey).delete()

```

```javascript

assert.equal( propertyDeleteResponse.status, 204 )

```



Creates an issue link between two issues. The user requires the link issue permission for the issue which will be linked to another issue. The specified link type in the request is used to create the link and will create a link from the first issue to the second issue using the outward description. It also create a link from the second issue to the first issue using the inward description of the issue link type. It will add the supplied comment to the first issue. The comment can have a restriction who can view it. If group is specified, only users of this group can view this comment, if roleLevel is specified only users who have the specified role can view this comment. The user who creates the issue link needs to belong to the specified group or have the specified role



```javascript

issueLinkCreateResponse = client.issueLink.post({

  "type" : {

    "name" : "Blocks"

  } ,

  "inwardIssue" : {

    "id" : issueId

  } ,

  "outwardIssue" : {

    "id" : issueId2

  }

})

```

```javascript

assert.equal( issueLinkCreateResponse.status, 201 )

```



Let's extract the issue link ID from the outward Issue object



```javascript

outwardIssue = client.issue.issueIdOrKey(issueId2).get()

issueLink = outwardIssue.body.fields.issuelinks[0]

linkId = issueLink.id

```



Returns an issue link with the specified id



```javascript

issueLinkResponse = client.issueLink.linkId(linkId).get()

```

```javascript

assert.equal( issueLinkResponse.status, 200 )

```



Deletes an issue link with the specified id. To be able to delete an issue link you must be able to view both issues and must have the link issue permission for at least one of the issues



```javascript

issueLinkDeleteResponse = client.issueLink.linkId(linkId).delete()

```

```javascript

assert.equal( issueLinkDeleteResponse.status, 204 )

```



Get a list of the transitions possible for this issue by the current user, along with fields that are required and their types



```javascript

transitionsResponse = client.issue.issueIdOrKey( issueId ).transitions.get({expand:"transitions.fields"})

```

```javascript

assert.equal( transitionsResponse.status, 200 )

```



Perform a transition on an issue. When performing the transition you can update or set other issue fields



```javascript

transitionCreateResponse = client.issue.issueIdOrKey( issueId ).transitions.post({
    "update": {
        "comment": [
            {
                "add": {
                    "body": "Bug has been fixed."
                }
            }
        ]
    },
    "fields": {
        "assignee": {
            "name": TEST_USER_NAME
        },
        "resolution": {
            "name": "Fixed"
        }
    },
    "transition": {
        "id": "5"
    },
    "historyMetadata": {
        "type": "myplugin:type",
        "description": "text description",
        "descriptionKey": "plugin.changereason.i18.key",
        "activityDescription": "text description",
        "activityDescriptionKey": "plugin.activity.i18.key",
        "actor": {
            "id": "tony",
            "displayName": "Tony",
            "type": "mysystem-user",
            "avatarUrl": "http://mysystem/avatar/tony.jpg",
            "url": "http://mysystem/users/tony"
        },
        "generator": {
            "id": "mysystem-1",
            "type": "mysystem-application"
        },
        "cause": {
            "id": "myevent",
            "type": "mysystem-event"
        },
        "extraData": {
            "keyvalue": "extra data",
            "goes": "here"
        }
    }
})

```

```javascript

assert.equal( transitionCreateResponse.status, 204 )

```



Delete an issue. If the issue has subtasks you must set the parameter deleteSubtasks=true to delete the issue. You cannot delete an issue without its subtasks also being deleted



```javascript

issueDeleteResponse = client.issue.issueIdOrKey(issueId).delete()

```

```javascript

assert.equal( issueDeleteResponse.status, 204 )

```



Garbage collection. Delete the test user and the second test issue.



```javascript

client.user.delete({},{query:{ username:TEST_USER_NAME }})

```

```javascript

issueDeleteResponse = client.issue.issueIdOrKey(issueId2).delete()

```