---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6628/edit
apiNotebookVersion: 1.1.66
title: Issue Part 3
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



Name and password of a test  user which is operated by the Notebook



```javascript

TEST_USER_NAME = "vote-user"

TEST_USER_PASSWORD = "aoeuAOEUaoeu1!"

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



Auxilliary method for retreiving role ID



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



We need a test user who will vote for an issue. Let's create him if he does not already exist.



```javascript

userCreateResponse = client.user.post({

  "name" : TEST_USER_NAME, 

  "password" : TEST_USER_PASSWORD ,

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

    "summary" : "Issue 4. This test issue was created by the API Notebook" ,

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



A user is not allowed to vote for an issue which has been reported by him. Thus, in order to vote for the created issue you should log in as another user. n the next code block you will be logged in as the test user which has been created in this notebook.



```javascript

API.authenticate(client,"basic",{username: TEST_USER_NAME,password:TEST_USER_PASSWORD})

```



Cast your vote in favour of an issue



```javascript

voteCreateResponse = client.issue.issueIdOrKey(issueId).votes.post()

```

```javascript

assert.equal( voteCreateResponse.status, 204 )

```



Remove your vote from an issue. (i.e. "unvote")



```javascript

votesDeleteResponse = client.issue.issueIdOrKey(issueId).votes.delete()

```

```javascript

assert.equal( votesDeleteResponse.status, 204 )

```



In order to finish the notebook execution you should log in under your own account.



```javascript

window.alert("In order to finish the notebook execution you should log in as project admin.")

```

```javascript

API.authenticate(client)

```



Delete an issue. If the issue has subtasks you must set the parameter deleteSubtasks=true to delete the issue. You cannot delete an issue without its subtasks also being deleted



```javascript

issueDeleteResponse = client.issue.issueIdOrKey(issueId).delete()

```

```javascript

assert.equal( issueDeleteResponse.status, 204 )

```



Garbage collection: removing the test user.



```javascript

client.user.delete({},{query:{ username:TEST_USER_NAME }})

```