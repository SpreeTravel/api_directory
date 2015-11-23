---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6629/preview
apiNotebookVersion: 1.1.66
title: Application-properties, Attachment, Auditing
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

TEST_USER_NAME = "notebook-test-user"

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



We need an issue for our tests. First, let's select a project to create issue for



```javascript

projectId = selectProject()

```



Now let's create an issue



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

issueId = issueCreateResponse.body.id

```



We also need a test user. Let's create him if he does not already exist.



```javascript

userCreateResponse = client.user.post({

  "name" : TEST_USER_NAME, 

  "password" : "aoeuAOEUaoeu1!" ,

  "emailAddress" : "mail@somehost.com" ,

  "displayName" : "Notebok Test User"

})

```



Returns an application property



```javascript

applicationPropertiesResponse = client("/application-properties").get()

```

```javascript

assert.equal( applicationPropertiesResponse.status, 200 )

appPropertyId = applicationPropertiesResponse.body[0].id

appPropertyValue = applicationPropertiesResponse.body[0].value

```



Modify an application property via PUT. The "value" field present in the PUT will override thee existing value



```javascript

appPropertyEditResponse = client("/application-properties/{id}", { id: appPropertyId}).put({

  "id" : appPropertyId ,

  "value" : false

},{headers:{"Content-Type":"application/json"}})

```

```javascript

assert.equal( appPropertyEditResponse.status, 200 )

```



Returns the meta information for an attachments, specifically if they are enabled and the maximum upload size allowed



```javascript

attachmentMetaResponse = client.attachment.meta.get()

```

```javascript

assert.equal( attachmentMetaResponse.status, 200 )

```



Returns the meta-data for an attachment, including the URI of the actual attached file



```javascript

// NOT SUPPORTED

//attachmentId = null

//attachmentResponse = client.attachment.id(attachmentId).get()

```

```javascript

//assert.equal( attachmentResponse.status, 200 )

```



Remove an attachment from an issue



```javascript

// NOT SUPPORTED

//attachmentDeleteResponse = client.attachment.id(attachmentId).delete()

```

```javascript

//assert.equal( attachmentDeleteResponse.status, 200 )

```



Returns current Auditing settings



```javascript

// NOT SUPPORTED

//auditingSettingsResponse = client.auditing.settings.get()

```

```javascript

//assert.equal( auditingsettingsResponse.status, 200 )

```



Changed current Auditing settings Returns REST representation for the requested group



```javascript

// NOT SUPPORTED

//auditingSettingsResponse = client.auditing.settings.put({"enabled": true})

```

```javascript

//assert.equal( auditingsettingsResponse.status, 200 )

```



Creates temporary avatar



```javascript

// NOT SUPPORTED

//temporaryResponse = client.avatar.type("PROJECT").temporary.post({})

```

```javascript

//assert.equal( temporaryResponse.status, 200 )

```



Updates the cropping instructions of the temporary avatar



```javascript

// NOT SUPPORTED

// temporaryCropResponse = client.avatar.type("USER").temporaryCrop.post({

//   "cropperWidth" : 120 ,

//   "cropperOffsetX" : 50 ,

//   "cropperOffsetY" : 50 ,

//   "needsCropping" : false

// })

```

```javascript

//assert.equal( temporaryCropResponse.status, 200 )

```



Returns all system avatars of the given type



```javascript

// NOT SUPPORTED

//systemResponse = client.avatar.type("PROJECT").system.get()

```

```javascript

//assert.equal( systemResponse.status, 200 )

```



Let's create an issue comment



```javascript

commentCreateResponse = client.issue.issueIdOrKey(issueId).comment.post({

  "body" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper." ,

  "visibility" : {

    "type" : "role" ,

    "value" : "Administrators"

  }

})

```

```javascript

commentId = commentCreateResponse.body.id

```



Returns the keys of all properties for the comment identified by the key or by the id



```javascript

commentPropertiesResponse = client.comment.commentId(commentId).properties.get()

```

```javascript

assert.equal( commentPropertiesResponse.status, 200 )

```



You can use this resource to store a custom data against the comment identified by the key or by the id. The user who stores the data is required to have permissions to administer the comment



```javascript

propertySetResponse = client.comment.commentId(commentId).properties.propertyKey("test-comment-prop").put({value:"test-value"})

```

```javascript

assert.equal( propertySetResponse.status, 201 )

```



Returns the value of the property with a given key from the comment identified by the key or by the id. The user who retrieves the property is required to have permissions to read the comment



```javascript

propertyResponse = client.comment.commentId(commentId).properties.propertyKey("test-comment-prop").get()

```

```javascript

assert.equal( propertyResponse.status, 200 )

```



Removes the property from the comment identified by the key or by the id. Ths user removing the property is required to have permissions to administer the comment



```javascript

propertyDeleteResponse = client.comment.commentId(commentId).properties.propertyKey("test-comment-prop").delete()

```

```javascript

assert.equal( propertyDeleteResponse.status, 204 )

```



We need a the test project's key



```javascript

projectKey = client.project.projectIdOrKey(projectId).get().body.key

```



Let's delete a component which could have been created during earlier notebook runs.



```javascript

{

  componentsResponse = client.project.projectIdOrKey(projectKey).components.get()

  for(var ind in componentsResponse.body){

    var comp = componentsResponse.body[ind]

    if(comp.name=="Notebook Test Component"){

      client.component.id(comp.id).delete()

    }

  }

}

```



Create a component via POST



```javascript

componentCreateResponse = client.component.post({

  "name" : "Notebook Test Component" ,

  "description" : "This component has been created by the API Notebook" ,

  "leadUserName" : TEST_USER_NAME ,

  "assigneeType" : "PROJECT_LEAD" ,

  "isAssigneeTypeValid" : false ,

  "project" : projectKey

})

```

```javascript

assert.equal( componentCreateResponse.status, 201 )

componentId = componentCreateResponse.body.id

```



Returns a project component



```javascript

componentResponse = client.component.id(componentId).get()

```

```javascript

assert.equal( componentResponse.status, 200 )

```



Modify a component via PUT. Any fields present in the PUT will override existing values. As a convenience, if a field is not present, it is silently ignored. If leadUserName is an empty string ("") the component lead will be removed



```javascript

componentUpdateResponse = client.component.id(componentId).put({

  "description": "This component has been created by the API Notebook (upd)",

})

```

```javascript

assert.equal( componentUpdateResponse.status, 200 )

```



Returns counts of issues related to this component



```javascript

relatedIssueCountsResponse = client.component.id(componentId).relatedIssueCounts.get()

```

```javascript

assert.equal( relatedIssueCountsResponse.status, 200 )

```



Delete a project component



```javascript

componentDeleteResponse = client.component.id(componentId).delete()

```

```javascript

assert.equal( componentDeleteResponse.status, 204 )

```



Returns a list of all fields, both System and Custom



```javascript

fieldsResponse = client.field.get()

```

```javascript

assert.equal( fieldsResponse.status, 200 )

```



Creates a custom field using a definition (object encapsulating custom field data



```javascript

fieldResponse = client.field.post({

  "name" : "Notebook Custom Field 3" ,

  "description" : "Custom field for picking groups" ,

  "type" : "com.atlassian.jira.plugin.system.customfieldtypes:grouppicker" ,

  "searcherKey" : "com.atlassian.jira.plugin.system.customfieldtypes:grouppickersearcher"

})

```

```javascript

assert.equal( fieldResponse.status, 201 )

fieldId = fieldResponse.body.id

```



Returns a full representation of the Custom Field Option that has the given id



```javascript

// NOT SUPPORTED

//customFieldOptionResponse = client.customFieldOption.id("Select lists").get()

```

```javascript

//assert.equal( customFieldOptionResponse.status, 200 )

```



Returns a list of all dashboards, optionally filtering them



```javascript

dashboardsResponse = client.dashboard.get()

```

```javascript

assert.equal( dashboardsResponse.status, 200 )

dashboardId = dashboardsResponse.body.dashboards[0].id

```



Returns a single dashboard



```javascript

dashboardResponse = client.dashboard.id(dashboardId).get()

```

```javascript

assert.equal( dashboardResponse.status, 200 )

```



Garbage collection. Delete the issue and the user.



```javascript

client.issue.issueIdOrKey(issueId).delete()

```

```javascript

client.user.delete({},{query:{ username:TEST_USER_NAME }})

```