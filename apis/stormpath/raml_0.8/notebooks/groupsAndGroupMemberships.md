---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7586/versions/7712/portal/pages/6299/preview
apiNotebookVersion: 1.1.66
title: Groups and group memberships
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



A StormPath resource does not have an ID field. Instead it provides a full HREF, which has ID as it's last segment. Here we define a helper method for obtaining IDs of resources.



```javascript

function extractId( obj ){

  

  var href = obj["href"];

  if(!href)

    return null;

  

  var ind = href.lastIndexOf('/')

  var id = href.substring(ind+1)

  return id;

} 

```

```javascript

// Read about the StormPathRAML at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7586/versions/7712/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7586/versions/7712/definition');

```



Here we define a helper method for retrieving ID of current tenant.



```javascript

function getCurrentTenentId(){

  var currentTenantResponse = client.tenants.current.get()

  var tenantId = extractId(currentTenantResponse.body)

  return tenantId

}

```



We need a StormPath application. In the code block below we define a helper method for getting an application with required name. If the application already exists, the method retrieves it, and in the opposite case it creates one. 



```javascript

function getApplication( appName, appDescription ){

  

  var application = null

  var tenantId = getCurrentTenentId()

  var applicationsResponse = client.tenants.tenantId(tenantId).applications.get()

  var applications = applicationsResponse.body.items

  for( var ind in applications ){

    

    var app = applications[ind]

    if(app.name == appName){

      application = app

    }

  }

  if(application==null){

    var resp = client.applications.post({

      "name": appName,

      "description": appDescription,

      "status": "enabled"

    })

    application = resp.body

  }

  return application

}

```



Get applicaiton.



```javascript

testApp = getApplication( "NotebookTestApp","Test Application")

appId = extractId(testApp)

```



Now we need a directory which so that we could set it as account storage for our application. In the code block below we define a helper method which creates a new directory with a given name. 



```javascript

function getNewDirectory( dirName )

{

  var tenantId = getCurrentTenentId()  

  var directoriesResponse = client.tenants.tenantId(tenantId).directories.get()

  var directories = directoriesResponse.body.items

  for( var ind in directories){

  

    var dir = directories[ind]

    var id = extractId(dir)

    

    if(dir.name == dirName){

      client.directories.directoryId(id).delete()

    }

  }

  var directoryCreateResponse = client.directories.post({

    "name" : dirName

  })

  var accStorage = directoryCreateResponse.body

  return accStorage

}

```



Get a directory



```javascript

accountStorage = getNewDirectory("NotebookTestAccountStorage")

dirId = extractId(accountStorage)

```



Account storage mapping is what makes a directory an account storage. Here is a helper method which links given application and directory by an account storage mapping.



```javascript

function setAccountStorage( application, accStorage ){

  client.accountStoreMappings.post({

    "application": {

     "href": application["href"]

    },

    "accountStore": {

     "href": accStorage["href"]

    },

    "isDefaultAccountStore": true,

    "isDefaultGroupStore": true

  })

}

```



Let's set the directory as an account storage for the application.



```javascript

setAccountStorage(testApp,accountStorage)

```



We need an account for our tests. Let's create one.



```javascript

auEmail = "au@somehost.com"

account = {

  "username" : "ActiveUser",

  "email" : auEmail,

  "givenName" : "John",

  "middleName" : "",

  "surname" : "Johnson",

  "password" : "Aupass_Aupass1!",

  "status" : "ENABLED"

}



accountCreateResponse = client.applications.applicationId(appId).accounts.post(account)

accId = extractId(accountCreateResponse.body)

```



To conclude preparation part of the present notebook, let's create some group data:



```javascript

group = {

  "name" : "Programmers",

  "description" : "Group of all the programmers",

  "status" : "ENABLED"

}

```



Create an application group.



```javascript

groupCreateResponse = client.applications.applicationId(appId).groups.post(group)

```

```javascript

assert.equal( groupCreateResponse.status, 201 )

groupId = extractId(groupCreateResponse.body)

```



You can list your applications groups by sending a GET request to your applications groups Collection Resource href URL. The response is a paginated list of application groups



```javascript

appGroupsResponse = client.applications.applicationId(appId).groups.get()

```

```javascript

assert.equal( appGroupsResponse.status, 200 )

```



You can list your directories groups by sending a GET request to your directories groups Collection Resource href URL. The response is a paginated list of directory groups



```javascript

dirGroupsResponse = client.directories.directoryId(dirId).groups.get()

```

```javascript

assert.equal(dirGroupsResponse.status,200 )

```



Retrieve a group



```javascript

groupResponse = client.groups.groupId(groupId).get()

```

```javascript

assert.equal( groupResponse.status, 200 )

```



Update a group



```javascript

newGroupDescription = "New description for group of all the programmers"

groupUPdateResponse = client.groups.groupId(groupId).post({

  "description" : newGroupDescription

})

```

```javascript

assert.equal( groupUPdateResponse.status, 200 )

assert.equal( groupUPdateResponse.body.description, newGroupDescription )

```



Retrieve group accounts.



```javascript

groupAccountsResponse = client.groups.groupId(groupId).accounts.get()

```

```javascript

assert.equal( groupAccountsResponse.status, 200 )

```



Retrieve a group custom data



```javascript

customDataResponse = client.groups.groupId(groupId).customData.get()

```

```javascript

assert.equal( customDataResponse.status, 200 )

```



Update a group custom data



```javascript

groupDataUpdateResponse = client.groups.groupId(groupId).customData.post({

  "Occupation": "developing",

  "Language": "JAVA"

})

```

```javascript

assert.equal( groupDataUpdateResponse.status, 200 )

```



Delete a customData field.



```javascript

deleteFieldResponse =client.groups.groupId(groupId).customData.fieldName("Occupation").delete()

```

```javascript

assert.equal( deleteFieldResponse.status, 204 )

```



Delete all of a groups custom data



```javascript

customDataDeleteResponse = client.groups.groupId(groupId).customData.delete()

```

```javascript

assert.equal( customDataDeleteResponse.status, 204 )

```



Retrieve a Collection Resource containing the group memberships to which a specific group is a member



```javascript

accountMembershipsResponse = client.groups.groupId(groupId).accountMemberships.get()

```

```javascript

assert.equal( accountMembershipsResponse.status, 200 )

```



Create a group membership. You need the account and the group href for it.



```javascript

accHref = client.accounts.accountId(accId).get().body.href

groupHref = client.groups.groupId(groupId).get().body.href

groupMembershipCreateResponse = client.groupMemberships.post({

  "account" : {

    "href" : accHref

  } ,

  "group" : {

    "href" : groupHref

  }

})

```

```javascript

assert.equal( groupMembershipCreateResponse.status, 201 )

GMID = extractId(groupMembershipCreateResponse.body)

```



Retrieve a group membership.



```javascript

groupMembershipResponse = client.groupMemberships.groupMembershipId(GMID).get()

```

```javascript

assert.equal( groupMembershipResponse.status, 200 )

```



Retrieve a paginated list of the group memberships where the account is involved



```javascript

groupMembershipsResponse = client.accounts.accountId(accId).groupMemberships.get()

```

```javascript

assert.equal( groupMembershipsResponse.status, 200 )

assert( groupMembershipsResponse.body.items.length > 0 )

```



Retrieve a Collection Resource containing links for all groups where a specific account is a member.



```javascript

accGroupsResponse = client.accounts.accountId(accId).groups.get()

```

```javascript

assert.equal( accGroupsResponse.status, 200 )

assert( accGroupsResponse.body.items.length > 0 )

```



Delete a group membership



```javascript

groupMembershipDeleteResponse = client.groupMemberships.groupMembershipId(GMID).delete()

```

```javascript

assert.equal( groupMembershipDeleteResponse.status, 204 )

```



Delete a group



```javascript

groupDeleteResponse = client.groups.groupId(groupId).delete()

```

```javascript

assert.equal( groupDeleteResponse.status, 204 )

```



Garbage collection



```javascript

accountDeleteResponse = client.accounts.accountId(accId).delete()

deleteApplicationResponse = client.applications.applicationId(appId).delete()

deleteDirectoryResponse = client.directories.directoryId(dirId).delete()

```