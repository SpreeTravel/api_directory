---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6631/edit
apiNotebookVersion: 1.1.66
title: Screens, Projectvalidate, Reindex, Resolution
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



Returns a list of all tabs for the given screen



```javascript

tabsResponse = client.screens.screenId(1).tabs.get()

```

```javascript

assert.equal( tabsResponse.status, 200 )

```



Let's delete all  tabs which could have been created during earlier notebook runs



```javascript

for(var ind in tabsResponse.body){

  var tab = tabsResponse.body[ind]

  if(tab.name=="Notebook Tab"||tab.name=="Notebook Test Tab"||tab.name=="notebook tab 2"){

    client.screens.screenId(1).tabs.tabId(tab.id).delete()

  }

}

```



Creates tab for given screen



```javascript

tabCreateResponse = client.screens.screenId(1).tabs.post({"name":"Notebook Tab"})

```

```javascript

assert.equal( tabCreateResponse.status, 200 )

tabId = tabCreateResponse.body.id

```



Renames tab on given screen



```javascript

tabUpdateResponse = client.screens.screenId(1).tabs.tabId(tabId).put({name:"Notebook Test Tab"})

```

```javascript

assert.equal( tabUpdateResponse.status, 200 )

```



Gets all fields for a given tab



```javascript

fieldsResponse = client.screens.screenId(1).tabs.tabId(10000).fields.get()

```

```javascript

assert.equal( fieldsResponse.status, 200 )

```



Let's prepare a field which will be added to the tab.



```javascript

fieldId = null

{

  fields = client.field.get().body

  for(var ind in fields){

    var field = fields[ind]

    if(field.name=="Notebook custom field"){

      fieldId = field.id

      break

    }

  }

}

```

```javascript

if(!fieldId){

  fieldResponse = client.field.post({

    "name" : "Notebook custom field" ,

    "description" : "Custom field for picking groups" ,

    "type" : "com.atlassian.jira.plugin.system.customfieldtypes:grouppicker" ,

    "searcherKey" : "com.atlassian.jira.plugin.system.customfieldtypes:grouppickersearcher"

  })

  fieldId = fieldResponse.body.id

}

```



Let's remove the field from the screen if it is already present on it



```javascript

for(var i in tabsResponse.body){

  var _tabId = tabsResponse.body[i].id

  fields = client.screens.screenId(1).tabs.tabId(_tabId).fields.get().body

  for(var j in fields){

    var field = fields[j]

    if(field.name=="Notebook custom field"){

      client.screens.screenId(1).tabs.tabId(_tabId).fields.id(field.id).delete()

    }

  }  

}

```



Adds field to the given tab



```javascript

fieldCreateResponse = client.screens.screenId(1).tabs.tabId(tabId).fields.post({fieldId:fieldId})

```

```javascript

assert.equal( fieldCreateResponse.status, 200 )

```



We need one more tab so that we could move the field into it.



```javascript

tabCreateResponse2 = client.screens.screenId(1).tabs.post({"name":"Notebook Tab 2"})

tabId2 = tabCreateResponse2.body.id

```



Moves field on the given tab



```javascript

moveResponse = client.screens.screenId(1).tabs.tabId(tabId).fields.id(fieldId).move.post({},{query:{tabId:tabId2}})

```

```javascript

assert.equal( moveResponse.status, 204 )

```



Removes field from given tab



```javascript

fieldDeleteResponse = client.screens.screenId(1).tabs.tabId(tabId).fields.id(fieldId).delete()

```

```javascript

assert.equal( fieldDeleteResponse.status, 204 )

```



Moves tab position



```javascript

moveTabResponse = client.screens.screenId(1).tabs.tabId(tabId).move.pos(2).post()

```

```javascript

assert.equal( moveTabResponse.status, 204 )

```



Deletes tab to give screen



```javascript

tabDeleteResponse = client.screens.screenId(1).tabs.tabId(tabId).delete()

```

```javascript

assert.equal( tabDeleteResponse.status, 204 )

```

```javascript

tabDeleteResponse2 = client.screens.screenId(1).tabs.tabId(tabId2).delete()

```

```javascript

assert.equal( tabDeleteResponse2.status, 204 )

```



Gets available fields for screen. i.e ones that haven't already been added



```javascript

availableFieldsResponse = client.screens.screenId(1).availableFields.get()

```

```javascript

assert.equal( availableFieldsResponse.status, 200 )

```



Adds field or custom field to the default tab



```javascript

addToDefaultResponse = client.screens.addToDefault.fieldId(fieldId).post()

```

```javascript

assert.equal( addToDefaultResponse.status, 200 )

```



Validates a project key



```javascript

projectvalidatekeyResponse = client.projectvalidate.key.get()

```

```javascript

assert.equal( projectvalidatekeyResponse.status, 200 )

```



Returns information on the system reindexes. If a reindex is currently taking place then information about this reindex is returned



```javascript

reindexRetreiveResponse = client.reindex.get()

```

```javascript

assert.equal( reindexRetreiveResponse.status, 200 )

```



Kicks off a reindex. Need Admin permissions to perform this reindex



```javascript

reindexPerformResponse = client.reindex.post()

```

```javascript

assert.equal( reindexPerformResponse.status, 202 )

```



Returns a list of all resolutions



```javascript

resolutionsResponse = client.resolution.get()

```

```javascript

assert.equal( resolutionsResponse.status, 200 )

resolutionId = resolutionsResponse.body[0].id

```



Returns a resolution



```javascript

resolutionResponse = client.resolution.id(resolutionId).get()

```

```javascript

assert.equal( resolutionResponse.status, 200 )

```



Searches for issues using JQL



```javascript

searchResponse = client.search.get()

```

```javascript

assert.equal( searchResponse.status, 200 )

```



Performs a search using JQL



```javascript

searchPerformResponse = client.search.post({

  "startAt" : 0 ,

  "maxResults" : 15 ,

  "fields" : [

    "summary" ,

    "status" ,

    "assignee"

  ]

})

```

```javascript

assert.equal( searchPerformResponse.status, 200 )

```



**ATTENTION** In order to execute the next method successfully you should at least one security level defined as described at https://confluence.atlassian.com/display/JIRA/Configuring+Issue-level+Security

You will now be proposed to enter your security level ID. Note that the first security level ever created in your account is very likely to have ID=10000



```javascript

securityLevelId = prompt("Please enter your security level ID.\nNote that the first security level ever created in your accoun is very likely to have ID=10000.")

```



Returns a full representation of the security level that has the given id.



```javascript

securitylevelResponse = client.securitylevel.id(securityLevelId).get()

```

```javascript

assert.equal( securitylevelResponse.status, 200 )

```



Returns general information about the current JIRA server



```javascript

serverInfoResponse = client.serverInfo.get()

```

```javascript

assert.equal( serverInfoResponse.status, 200 )

```



Sets the base URL that is configured for this JIRA instance



```javascript

// NOT SUPPORTED

//baseUrlResponse = client.settings.baseUrl.put(PROTOCOL+"://"+DOMAIN)

```

```javascript

//assert.equal( baseUrlResponse.status, 200 )

```



Returns the default system columns for issue navigator. Admin permission will be required



```javascript

columnsResponse = client.settings.columns.get()

```

```javascript

assert.equal( columnsResponse.status, 200 )

```



Sets the default system columns for issue navigator. Admin permission will be required



```javascript

columnSetResponse = client.settings.columns.put({})

```

```javascript

assert.equal( columnSetResponse.status, 200 )

```



Returns a list of all statuses



```javascript

statusesResponse = client.status.get()

```

```javascript

assert.equal( statusesResponse.status, 200 )

statusId = statusesResponse.body[0].id

```



Returns a full representation of the Status having the given id or name



```javascript

statusResponse = client.status.idOrName(statusId).get()

```

```javascript

assert.equal( statusResponse.status, 200 )

```



Returns a list of all status categories



```javascript

statuscategoriesResponse = client.statuscategory.get()

```

```javascript

assert.equal( statuscategoriesResponse.status, 200 )

statusCategoryId = statuscategoriesResponse.body[0].id

```



Returns a full representation of the StatusCategory having the given id or key



```javascript

statusCategoryResponse = client.statuscategory.idOrKey(statusCategoryId).get()

```

```javascript

assert.equal( statusCategoryResponse.status, 200 )

```