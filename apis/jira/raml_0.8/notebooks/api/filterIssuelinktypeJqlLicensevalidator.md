---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6630/preview
apiNotebookVersion: 1.1.66
title: Filter, IssueLinkType, Jql, LicenseValidator
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

Returns the favourite filters of the logged-in user

```javascript
favouriteResponse = client.filter.favourite.get()
```

```javascript
assert.equal( favouriteResponse.status, 200 )
```

Let's delete a filter which could have been created during previous notebook runs.

Note that we can list only favourite filters because of https://jira.atlassian.com/browse/JRA-36045. Thus we should create only filters with _favourite_ property set to _true_ in this notebook if we want to be able to access them via REST in future.

```javascript
{
  for(var ind in favouriteResponse.body){
    var filter = favouriteResponse.body[ind]
    if(filter.name == "Notebook Test Filter"){
      client.filter.id(filter.id).delete()
    }
  }
}
```

Creates a new filter, and returns newly created filter. Currently sets permissions just using the users default sharing permission

```javascript
filterCreateResponse = client.filter.post({
  "name" : "Notebook Test Filter" ,
  "description" : "This filter was created by the API Notebook" ,
  "jql" : "type = Bug and resolution is empty" ,
  "favourite" : true
})
```

```javascript
assert.equal( filterCreateResponse.status, 200 )
filterId = filterCreateResponse.body.id
```

Returns a filter given an id

```javascript
filterResponse = client.filter.id(filterId).get()
```

```javascript
assert.equal( filterResponse.status, 200 )
```

Updates an existing filter, and returns its new value

```javascript
filterUpdateResponse = client.filter.id(filterId).put({
  "description" : filterResponse.body.description + " (upd)" ,
})
```

```javascript
assert.equal( filterUpdateResponse.status, 200 )
```

Sets the default columns for the given filter

```javascript
columnsSetResponse = client.filter.id(filterId).columns.put({})
```

```javascript
assert.equal( columnsSetResponse.status, 200 )
```

Returns the default columns for the given filter. Currently logged in user will be used as the user making such request

```javascript
columnsResponse = client.filter.id(filterId).columns.get()
```

```javascript
assert.equal( columnsResponse.status, 200 )
```

Resets the columns for the given filter such that the filter no longer has its own column config

```javascript
columnsRemoveResponse = client.filter.id(filterId).columns.delete()
```

```javascript
assert.equal( columnsRemoveResponse.status, 204 )
```

Delete a filter

```javascript
filterDeleteResponse = client.filter.id(filterId).delete()
```

```javascript
assert.equal( filterDeleteResponse.status, 204 )
```

Returns the default share scope of the logged-in user

```javascript
defaultShareScopeResponse = client.filter.defaultShareScope.get()
```

```javascript
assert.equal( defaultShareScopeResponse.status, 200 )
```

Sets the default share scope of the logged-in user. Available values are GLOBAL and PRIVATE

```javascript
defaultShareScopeEditResponse = client.filter.defaultShareScope.put({
  "scope" : "GLOBAL"
})
```

```javascript
assert.equal( defaultShareScopeEditResponse.status, 200 )
```

Returns a list of available issue link types, if issue linking is enabled. Each issue link type has an id, a name and a label for the outward and inward link relationship

```javascript
issueLinkTypesResponse = client.issueLinkType.get()
```

```javascript
assert.equal( issueLinkTypesResponse.status, 200 )
```

Let's delete an issue link type which could have been created during earlier notebook runs.

```javascript
{
  for(var ind in issueLinkTypesResponse.body.issueLinkTypes){
    var issueLinkType = issueLinkTypesResponse.body.issueLinkTypes[ind]
    if(issueLinkType.name=="Notebook Test Issue Type"){
      client.issueLinkType.issueLinkTypeId(issueLinkType.id).delete()
    }
  }
}
```

Create a new issue link type

```javascript
issueLinkTypeCreateResponse = client.issueLinkType.post({
  "name" : "Notebook Test Issue Type" ,
  "inward" : "�aused by" ,
  "outward" : "�auses"
})
```

```javascript
assert.equal( issueLinkTypeCreateResponse.status, 201 )
issueLinkTypeId = issueLinkTypeCreateResponse.body.id
```

Returns for a given issue link type id all information about this issue link type

```javascript
issueLinkTypeResponse = client.issueLinkType.issueLinkTypeId(issueLinkTypeId).get()
```

```javascript
assert.equal( issueLinkTypeResponse.status, 200 )
```

Update the specified issue link type

```javascript
issueLinkTypeUpdateResponse = client.issueLinkType.issueLinkTypeId(issueLinkTypeId).put({
  "inward" : "�aused in some way by" ,
  "outward" : "�auses in some way"
})
```

```javascript
assert.equal( issueLinkTypeUpdateResponse.status, 200 )
```

Delete the specified issue link type

```javascript
issueLinkTypeDeleteResponse = client.issueLinkType.issueLinkTypeId( issueLinkTypeId).delete()
```

```javascript
assert.equal( issueLinkTypeDeleteResponse.status, 204 )
```

Returns the auto complete data required for JQL searches

```javascript
jqlautocompletedataResponse = client.jql.autocompletedata.get()
```

```javascript
assert.equal( jqlautocompletedataResponse.status, 200 )
```

A REST endpoint to provide simple validation services for a JIRA license. Typically used by the setup phase of the JIRA application. This will return an object with a list of errors as key, value pairs

```javascript
licenseValidatorResponse = client.licenseValidator.post()
```

```javascript
assert.equal( licenseValidatorResponse.status, 200 )
```

Returns all permissions in the system and whether the currently logged in user has them

```javascript
mypermissionsResponse = client.mypermissions.get()
```

```javascript
assert.equal( mypermissionsResponse.status, 200 )
```

Sets preference of the currently logged in user. Preference key must be provided as input parameters (key)

```javascript
mypreferencesPutResponse = client.mypreferences.put({value: "test-value"},{query:{key:"notebook-pref-key"}})
```

```javascript
assert.equal( mypreferencesPutResponse.status, 204 )
```

Returns preference of the currently logged in user. Preference key must be provided as input parameter (key)

```javascript
mypreferencesResponse = client.mypreferences.get({key:"notebook-pref-key"})
```

```javascript
assert.equal( mypreferencesResponse.status, 200 )
```

Removes preference of the currently logged in user. Preference key must be provided as input parameters (key)

```javascript
mypreferencesDeleteResponse = client.mypreferences.delete({},{query:{key:"notebook-pref-key"}})
```

```javascript
assert.equal( mypreferencesDeleteResponse.status, 204 )
```

Returns user-friendly statements governing the system's password policy

```javascript
passwordpolicyResponse = client.password.policy.get()
```

```javascript
assert.equal( passwordpolicyResponse.status, 200 )
```

Create user

```javascript
createUserResponse = client.password.policy.createUser.post({
  "username" : "notebook-test-user" ,
  "password" : "abracadabra" ,
  "emailAddress" : "mail2@somehost.com" ,
  "displayName" : "Notebok Test User 2"
})
```

```javascript
assert.equal( createUserResponse.status, 200 )
```

```javascript
// NOT SUPPORTED
// updateUserResponse = client.password.policy.updateUser.post({
  
// },{query:{"username" : "notebook-test-user"}})
```

```javascript
//assert.equal( updateUserResponse.status, 200 )
```

Returns a list of all issue priorities.

```javascript
priorityResponse = client.priority.get()
```

```javascript
assert.equal( priorityResponse.status, 200 )
```

Returns an issue priority.

```javascript
priorityResponse = client.priority.id(1).get()
```

```javascript
assert.equal( priorityResponse.status, 200 )
```