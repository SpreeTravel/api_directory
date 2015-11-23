---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6633/preview
apiNotebookVersion: 1.1.66
title: Workflowscheme
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



Auxilliary project selecting method



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

  if(Number.isInteger(parsedInd)&&parsedInd>=0&&parsedInd){

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



**ATTENTION** At this point of notebook execution you must get ensured that your Jira account does not already have a workflow scheme named "Notebook Workflow Scheme". Here you can find out how to navigate to the workflow schemes page inside your account: https://confluence.atlassian.com/display/JIRA/Configuring+workflow+schemes. This action is to be performed manually as there is no way to list workflow schemes via REST.



Create a new workflow scheme. The body contains a representation of the new scheme. Values not passed are assumed to be set to their defaults



```javascript

issueTypes = client.issuetype.get().body

```

```javascript

workflowschemeCreateResponse = client.workflowscheme.post({

  "name" : "Notebook Workflow Scheme" ,

  "description" : "This workflow scheme was created by the API Notebook" ,

  "defaultWorkflow" : "jira" ,

  "issueTypeMappings" : {

    "3": "jira"

  }

})

```

```javascript

assert.equal( workflowschemeCreateResponse.status, 201 )

workflowSchemeId = workflowschemeCreateResponse.body.id

```



Returns the requested workflow scheme to the caller



```javascript

workflowschemeResponse = client.workflowscheme.id(workflowSchemeId).get()

```

```javascript

assert.equal( workflowschemeResponse.status, 200 )

```



Update the passed workflow scheme. The body of the request is a representation of the workflow scheme. Values not passed are assumed to indicate no change for that field. The passed representation can have its updateDraftIfNeeded flag set to true to indicate that the draft should be created and/or updated when the actual scheme cannot be edited (e.g. when the scheme is being used by a project). Values not appearing the body will not be touched



```javascript

workflowschemeUpdateResponse = client.workflowscheme.id(workflowSchemeId).put({

  "description" : workflowschemeResponse.body.description + " (upd)" ,

})

```

```javascript

assert.equal( workflowschemeUpdateResponse.status, 200 )

```



Return the default workflow from the passed workflow scheme



```javascript

defaultWorkflowResponse = client.workflowscheme.id(workflowSchemeId).default.get()

```

```javascript

assert.equal( defaultWorkflowResponse.status, 200 )

```



Set the default workflow for the passed workflow scheme. The passed representation can have its updateDraftIfNeeded flag set to true to indicate that the draft should be created/updated when the actual scheme cannot be edited



```javascript

defaultWorkflowSetResponse = client.workflowscheme.id(workflowSchemeId).default.put({

  "workflow" : "jira" ,

  "updateDraftIfNeeded" : false

})

```

```javascript

assert.equal( defaultWorkflowSetResponse.status, 200 )

```



Returns the issue type mapping for the passed workflow scheme



```javascript

issuetypeResponse = client.workflowscheme.id(workflowSchemeId).issuetype.issueType(1).get()

```

```javascript

assert.equal( issuetypeResponse.status, 200 )

```



Set the issue type mapping for the passed scheme. The passed representation can have its updateDraftIfNeeded flag set to true to indicate that the draft should be created/updated when the actual scheme cannot be edited



```javascript

issuetypeUpdateResponse = client.workflowscheme.id(workflowSchemeId).issuetype.issueType(1).put({

  "issueType" : "3" ,

  "workflow" : "jira" ,

  "updateDraftIfNeeded" : false

})

```

```javascript

assert.equal( issuetypeUpdateResponse.status, 200 )

```



Remove the specified issue type mapping from the scheme



```javascript

issuetypeDeleteResponse = client.workflowscheme.id(workflowSchemeId).issuetype.issueType(3).delete()

```

```javascript

assert.equal( issuetypeDeleteResponse.status, 200 )

```



Returns the workflow mappings or requested mapping to the caller for the passed scheme



```javascript

workflowResponse = client.workflowscheme.id(workflowSchemeId).workflow.get()

```

```javascript

assert.equal( workflowResponse.status, 200 )

```



Update the scheme to include the passed mapping. The body is a representation of the workflow mapping. Values not passed are assumed to indicate no change for that field. The passed representation can have its updateDraftIfNeeded flag set to true to indicate that the draft should be created/updated when the actual scheme cannot be edited



```javascript

workflowUpdateResponse = client.workflowscheme.id(workflowSchemeId).workflow.put({

  "workflow" : "jira" ,

  "updateDraftIfNeeded" : true

},{query:{"workflowName":"jira"}})

```

```javascript

assert.equal( workflowUpdateResponse.status, 200 )

```



Delete the passed workflow from the workflow scheme



```javascript

workflowDeleteResponse = client.workflowscheme.id(workflowSchemeId).workflow.delete({},{query:{"workflowName":"jira"}})

```

```javascript

assert.equal( workflowDeleteResponse.status, 200 )

```



Remove the default workflow from the passed workflow scheme



```javascript

defaultWorkflowDeleteResponse = client.workflowscheme.id(workflowSchemeId).default.delete()

```

```javascript

assert.equal( defaultWorkflowDeleteResponse.status, 200 )

```



**ATTENTION** At this point you must activate the created schema. A workflow scheme is considered active if it is associated with at least one project. Thus, we recommend to create a new project and associate the schema with it. See https://confluence.atlassian.com/display/JIRA/Activating+workflow#Activatingworkflow-associatingworkflowschemetoproject. This action is to be performed manually as there is no way to associate a workflow scheme with a project via REST.



```javascript


window.alert("At this point you must activate the created schema. A workflow scheme is considered active if it is associated with at least one project. Thus, we recommend to create a new project and associate the schema with it. See https://confluence.atlassian.com/display/JIRA/Activating+workflow#Activatingworkflow-associatingworkflowschemetoproject. This action is to be performed manually as there is no way to associate a workflow scheme with a project via REST.")


```



Create a draft for the passed scheme. The draft will be a copy of the state of the parent



```javascript

createdraftResponse = client.workflowscheme.id(workflowSchemeId).createdraft.post()

```

```javascript

assert.equal( createdraftResponse.status, 201 )

```



Returns the requested draft workflow scheme to the caller



```javascript

draftResponse = client.workflowscheme.id(workflowSchemeId).draft.get()

```

```javascript

assert.equal( draftResponse.status, 200 )

```



Update a draft workflow scheme. The draft will created if necessary. The body is a representation of the workflow scheme. Values not passed are assumed to indicate no change for that field



```javascript

draftUpdateResponse = client.workflowscheme.id(workflowSchemeId).draft.put({

  "description" : "Updated Workflow Draft Scheme Name" ,

})

```

```javascript

assert.equal( draftUpdateResponse.status, 200 )

```



Return the default workflow from the passed draft workflow scheme to the caller



```javascript

defaultDraftResponse = client.workflowscheme.id(workflowSchemeId).draft.default.get()

```

```javascript

assert.equal( defaultDraftResponse.status, 200 )

```



Set the default workflow for the passed draft workflow scheme



```javascript

defaultDraftPutResponse = client.workflowscheme.id(workflowSchemeId).draft.default.put({

  "workflow" : "jira" ,

  "updateDraftIfNeeded" : false

})

```

```javascript

assert.equal( defaultDraftPutResponse.status, 200 )

```



Remove the default workflow from the passed draft workflow scheme



```javascript

defaultDraftRemoveResponse = client.workflowscheme.id(workflowSchemeId).draft.default.delete()

```

```javascript

assert.equal( defaultDraftRemoveResponse.status, 200 )

```



Returns the issue type mapping for the passed draft workflow scheme



```javascript

issuetypeResponse = client.workflowscheme.id(workflowSchemeId).draft.issuetype.issueType(1).get()

```

```javascript

assert.equal( issuetypeResponse.status, 200 )

```



Set the issue type mapping for the passed draft scheme. The passed representation can have its updateDraftIfNeeded flag set to true to indicate that the draft should be created/updated when the actual scheme cannot be edited



```javascript

issuetypeSetResponse = client.workflowscheme.id(workflowSchemeId).draft.issuetype.issueType(1).put({

  "issueType" : 3 ,

  "workflow" : "jira" ,

  "updateDraftIfNeeded" : false

})

```

```javascript

assert.equal( issuetypeSetResponse.status, 200 )

```



Remove the specified issue type mapping from the draft scheme



```javascript

issuetypeDeleteResponse = client.workflowscheme.id(workflowSchemeId).draft.issuetype.issueType(1).delete()

```

```javascript

assert.equal( issuetypeDeleteResponse.status, 200 )

```



Returns the draft workflow mappings or requested mapping to the caller



```javascript

draftWorkflowResponse = client.workflowscheme.id(workflowSchemeId).draft.workflow.get()

```

```javascript

assert.equal( draftWorkflowResponse.status, 200 )

```



Update the draft scheme to include the passed mapping. The body is a representation of the workflow mapping. Values not passed are assumed to indicate no change for that field



```javascript

draftWorkflowUpdateResponse = client.workflowscheme.id(workflowSchemeId).draft.workflow.put({

  "workflow" : "jira" ,

  "updateDraftIfNeeded" : true

},{query:{"workflowName":"jira"}})

```

```javascript

assert.equal( draftWorkflowUpdateResponse.status, 200 )

```



Delete the passed workflow from the draft workflow scheme



```javascript

draftWorkflowDeleteResponse = client.workflowscheme.id(workflowSchemeId).draft.workflow.delete({},{query:{"workflowName":"jira"}})

```

```javascript

assert.equal( draftWorkflowDeleteResponse.status, 200 )

```



Delete the passed draft workflow scheme



```javascript

draftDeleteResponse = client.workflowscheme.id(workflowSchemeId).draft.delete()

```

```javascript

assert.equal( draftDeleteResponse.status, 204 )

```



**ATTENTION** At this point you must deactivate the created schema as an active workflow schema can not be deleted. You may delete a project associated with the scheme or you may associate it with some other scheme.



```javascript


window.alert("At this point you must deactivate the created schema as an active workflow schema can not be deleted. You may delete a project associated with the scheme or you may associate it with some other scheme.")


```



Delete the passed workflow scheme



```javascript

workflowschemeDeleteResponse = client.workflowscheme.id(workflowSchemeId).delete()

```

```javascript

assert.equal( workflowschemeDeleteResponse.status, 204 )

```