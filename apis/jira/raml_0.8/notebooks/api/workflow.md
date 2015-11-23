---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6632/edit
apiNotebookVersion: 1.1.66
title: Workflow
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



Create a test issue



```javascript

prioritiesResponse = client.priority.get()

priorityId = prioritiesResponse.body[0].id

issueTypesResponse = client.issuetype.get()

issueTypeId = issueTypesResponse.body[0].id

issueCreateResponse = client.issue.post({

  "update" : {    

  } ,

  "fields" : {

    "project" : {

      "id" : projectId

    } ,

    "summary" : "Issue 5. This test issue was created by the API Notebook" ,

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

issueId = issueCreateResponse.body.id

```

```javascript

fieldResponse = client.field.post({

    "name" : "description" ,

    "type" : "com.atlassian.jira.plugin.system.customfieldtypes:grouppicker" ,

    "searcherKey" : "com.atlassian.jira.plugin.system.customfieldtypes:grouppickersearcher"

})

```

```javascript

fieldCreateResponse = client.screens.screenId(1).tabs.tabId(10000).fields.post({fieldId:fieldResponse.body.id})

```



Select a transition



```javascript

transitions = client.issue.issueIdOrKey(issueId).transitions.get().body.transitions

transitionId = 21;//transitions[0].id

```



Returns all workflows



```javascript

workflowsResponse = client.workflow.get()

```

```javascript

assert.equal( workflowsResponse.status, 200 )

workflow = workflowsResponse.body[0]

workflowId = workflow.name

```



Return the property or properties associated with a transition



```javascript

// NOT SUPPORTED

//workflowPropertiesResponse = client.workflow.id(transitionId).properties.get({workflowName:"jira",workflowMode:"live",includeReservedKeys:true})

```

```javascript

//assert.equal( workflowPropertiesResponse.status, 200 )

```



Add a new property to a transition. Trying to add a property that already exists will fail



```javascript

// NOT SUPPORTED

// propertyCreateResponse = client.workflow.id(transitionId).properties.post({

//   "value" : "createissue"

// },{query:{workflowName:"jira",workflowMode:"live",key:"notebook-property"}})

```

```javascript

//assert.equal( propertyCreateResponse.status, 200 )

```



Update/add new property to a transition. Trying to update a property that does not exist will result in a new property being added



```javascript

// NOT SUPPORTED

// propertyUpdateResponse = client.workflow.id(transitionId).properties.put({

//   "value" : "createissue"

// },{query:{key:"notebook-property"}})

```

```javascript

//assert.equal( propertyUpdateResponse.status, 200 )

```



Delete a property from the passed transition on the passed workflow. It is not an error to delete a property that does not exist



```javascript

// NOT SUPPORTED

//propertyDeleteResponse = client.workflow.id(transitionId).properties.delete({},{query:{key:"notebook-property"}})

```

```javascript

//assert.equal( propertyDeleteResponse.status, 200 )

```



Garbage collection. Delete the issue.



```javascript

client.issue.issueIdOrKey(issueId).delete()

```