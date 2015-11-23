---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7586/versions/7712/portal/pages/6297/preview
apiNotebookVersion: 1.1.66
title: Tenants, applications, directories
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



A StormPath resource does not have an ID field. Instead it provides a full HREF, which has ID as it's last segment. 

Here we define a helper method for obtaining IDs of resources.



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



Name of application which notebook operates with.



```javascript

applicationName = "NotebookTestApp"

```



Name of directory which notebook operates with.



```javascript

directoryName = "NotebookTestDir"

```

```javascript

// Read about the StormPathRAML at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7586/versions/7712/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7586/versions/7712/definition');

```



Because a REST caller can retrieve one and only one Tenant resource, it is often more convenient not to be concerned with the Tenant-specific URL is when performing a request, and instead use a simpler permanent alias.



You can request the current Tenant resource, and the API server will automatically issue a 302 redirect to the Tenant resource corresponding to the currently executing API caller. In other words, this endpoint redirects the API caller to their own Tenant�s URI.



```javascript

currentTenantResponse = client.tenants.current.get()

```

```javascript

assert.equal(currentTenantResponse.status, 200 );

tenantId = extractId(currentTenantResponse.body)

```



You can list your tenants applications by sending a GET request to your tenants applications Collection Resource href URL. The response is a paginated list of tenant applications



```javascript

applicationsResponse = client.tenants.tenantId(tenantId).applications.get()

```

```javascript

assert.equal(applicationsResponse.status, 200 );

```



Execute a GET request with the tenant URI to retrieve the Tenant resource. You may only retrieve your own Tenant: every API Key that executes REST requests is associated with a Tenant, and the request caller may only retrieve the Tenant corresponding to the API Key used



```javascript

tenantResponse = client.tenants.tenantId(tenantId).get()

```

```javascript

assert.equal( extractId(tenantResponse.body), tenantId );

```



Let's try deleting an application which could have been created during earlier notebook runs.



```javascript

applications = applicationsResponse.body.items

for( var ind in applications ){

  

  var app = applications[ind]  

  if(app.name == applicationName){

    var id = extractId(app)

    client.applications.applicationId(id).delete()

    break

  }

}

```



Create an application.



```javascript

applicationCreateResponse = client.applications.post({

  "name": applicationName,

  "description": "Test Application",

  "status": "enabled"

})

```

```javascript

assert.equal(applicationCreateResponse.status, 201 );

appId = extractId(applicationCreateResponse.body)

```



After you have created an application, you may retrieve its contents by sending a GET request to the applications URL returned in the Location header or href attribute



```javascript

applicationResponse = client.applications.applicationId(appId).get()

```

```javascript

assert.equal(applicationResponse.status, 200 );

```



Update an application. Unspecified attributes are not changed, but at least one attribute must be specified.



```javascript

newDescription = "New test description for test application"

client.applications.applicationId(appId).post({

  "description": newDescription

})

```

```javascript

applicationResponse = client.applications.applicationId(appId).get()

```

```javascript

assert.equal( applicationResponse.body.description, newDescription );

```



List your tenant's directories.



```javascript

directoriesResponse = client.tenants.tenantId(tenantId).directories.get()

```

```javascript

assert.equal(directoriesResponse.status, 200 );

```



Let's try deleting a directory which could have been created during earlier notebook runs.



```javascript

directories = directoriesResponse.body.items

for( var ind in directories ) {

  

  var dir = directories[ind]

  if(dir.name == directoryName){

    var id = extractId(dir)

    client.directories.directoryId(id).delete()

    break

  }

}

```



Create a cloud directory



```javascript

directoryCreateResponse = client.directories.post({

  "name" : directoryName

})

```

```javascript

assert.equal(directoryCreateResponse.status,201 )

directoryId = extractId(directoryCreateResponse.body)

```



Retrieve a cloud directory



```javascript

dirResponse = client.directories.directoryId(directoryId).get()

```

```javascript

assert.equal(dirResponse.status, 200 );

```



Update a cloud directory



```javascript

newDirDescription = "Updated description for test dir"

client.directories.directoryId(directoryId).post({

  "description" : newDirDescription

})

```

```javascript

dirResponse = client.directories.directoryId(directoryId).get()

assert.equal( dirResponse.body.description, newDirDescription );

```



Create account mapping



```javascript

appHref = client.applications.applicationId(appId).get().body.href

dirHref = client.directories.directoryId(directoryId).get().body.href

SMResponse = client.accountStoreMappings.post({

  "application": {

   "href": appHref

  },

  "accountStore": {

   "href": dirHref

  },

  "isDefaultAccountStore": true,

  "isDefaultGroupStore": true

})

```

```javascript

assert.equal( SMResponse.status, 201 );

ASMID = extractId(SMResponse.body)

```



List an applications assigned account stores.



```javascript

ASMsResponse = client.applications.applicationId(appId).accountStoreMappings.get()

```

```javascript

assert.equal( ASMsResponse.status, 200 );

assert( ASMsResponse.body.items.length>0 );

```



After you have created an account store mapping, you may retrieve its contents.



```javascript

ASMResponse = client.accountStoreMappings.accountStoreMappingId(ASMID).get()

```

```javascript

assert.equal( ASMResponse.status, 200 );

```



Update an account store mapping



```javascript

client.accountStoreMappings.accountStoreMappingId(ASMID).post({

  "isDefaultAccountStore": false

})

```

```javascript

ASMResponse = client.accountStoreMappings.accountStoreMappingId(ASMID).get()

assert(!ASMResponse.body.isDefaultAccountStore )

```



Delete a cloud directory



```javascript

deleteDirectoryResponse = client.directories.directoryId(directoryId).delete()

```

```javascript

assert.equal(deleteDirectoryResponse.status, 204 );

```



Let's see that the directory has indeed been deleted.



```javascript

getDeletedResponse = client.directories.directoryId(directoryId).get()

getDeletedResponse = assert.equal( getDeletedResponse.status, 404 );

```



Delete account store mapping



```javascript

deleteAccountStoreResponse = client.accountStoreMappings.accountStoreMappingId(ASMID).delete()

```

```javascript

assert.equal(deleteAccountStoreResponse.status, 204 );

```



Let's see that the account storage mapping has indeed been deleted.



```javascript

getDeletedResponse = client.accountStoreMappings.accountStoreMappingId(ASMID).get()

assert.equal( getDeletedResponse.status, 404 );

```



Delete an application



```javascript

deleteApplicationResponse = client.applications.applicationId(appId).delete()

```

```javascript

assert.equal( deleteApplicationResponse.status, 204 );

```



Let's see that the application has indeed been deleted.



```javascript

getDeletedResponse = client.applications.applicationId(appId).get()

assert.equal( getDeletedResponse.status, 404 );

```