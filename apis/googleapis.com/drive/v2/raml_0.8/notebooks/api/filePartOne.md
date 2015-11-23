---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/12164/versions/12574/portal/pages/13394/preview
apiNotebookVersion: 1.1.66
title: File. part 1
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
//CLIENT_ID = prompt("Please, enter Client ID of your Google application.")
//CLIENT_SECRET = prompt("Please, enter Client Secret of your Google application.")
CLIENT_ID = "261592100079-5tplbsi62mj6vgdcs8dn8i84nhrrc8ou.apps.googleusercontent.com"
CLIENT_SECRET = "Jp9stAkyj9QqHU3FH-LdDXdy"
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/12164/versions/12574/definition');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Lists the user's files

```javascript
filesResponse = client.files.get()
```

```javascript
assert.equal( filesResponse.status, 200 )
```

Insert a new file

```javascript
filesCreateResponse = client.files.post({
  "title" : "NotebookTestFile" ,
  "description" : "testDescription" ,
  "labels" : {
    "starred": false,
    "hidden": false,
    "trashed": false,
    "restricted": false,
    "viewed": false
   } ,
  "writersCanShare" : true})
```

```javascript
assert.equal( filesCreateResponse.status, 200 )
ID_FILE = filesCreateResponse.body.id
USER_EMAIL = filesCreateResponse.body.owners[0].emailAddress
```

Gets a file's metadata by ID

```javascript
fileResponse = client.files.fileId(ID_FILE).get()
```

```javascript
assert.equal( fileResponse.status, 200 )
```

Updates file metadata and/or content

```javascript
fileUpdateResponse = client.files.fileId(ID_FILE).put({
  "description" : "NotebookTestFile description update"
})
```

```javascript
assert.equal( fileUpdateResponse.status, 200 )
```

Updates file metadata. This method supports patch semantics

```javascript
fileIdPatchResponse = client.files.fileId(ID_FILE).patch({
  "description" : "NotebookTestFile description" 
})
```

```javascript
assert.equal( fileIdPatchResponse.status, 200 )
```

Creates a copy of the specified file

```javascript
copyCreateResponse = client.files.fileId(ID_FILE).copy.post({
  "title" : "NotebookTestFile copy"  
})
```

```javascript
assert.equal( copyCreateResponse.status, 200 )
ID_FILE_COPY = copyCreateResponse.body.id
```

Set the file's updated time to the current server time

```javascript
touchCreateResponse = client.files.fileId(ID_FILE).touch.post()
```

```javascript
assert.equal( touchCreateResponse.status, 200 )
```

Moves a file to the trash

```javascript
trashCreateResponse = client.files.fileId(ID_FILE).trash.post()
```

```javascript
assert.equal( trashCreateResponse.status, 200 )
```

Restores a file from the trash

```javascript
untrashCreateResponse = client.files.fileId(ID_FILE).untrash.post()
```

```javascript
assert.equal( untrashCreateResponse.status, 200 )
```

Start watching for changes to a file

```javascript
watchCreateResponse = client.files.fileId(ID_FILE).watch.post({
  "id" : "string" ,
  "type" : "web_hook" ,
  "address" : "https://api-notebook.anypoint.mulesoft.com/authenticate/oauth.html"
})
```

```javascript
assert.equal( watchCreateResponse.status, 200 )
```

Lists a file's parents

```javascript
parentsResponse = client.files.fileId(ID_FILE).parents.get()
```

```javascript
assert.equal( parentsResponse.status, 200 )
ID_FILE_PARENT = parentsResponse.body.items[0].id
```

Adds a parent folder for a file

```javascript
parentsCreateResponse = client.files.fileId(ID_FILE).parents.post({
  "id" : ID_FILE_PARENT
})
```

```javascript
assert.equal( parentsCreateResponse.status, 200 )
```

Gets a specific parent reference

```javascript
parentResponse = client.files.fileId(ID_FILE).parents.parentId(ID_FILE_PARENT).get()
```

```javascript
assert.equal( parentResponse.status, 200 )
```

Removes a parent from a file

```javascript
parentIdDeleteResponse = client.files.fileId(ID_FILE).parents.parentId(ID_FILE_PARENT).delete()
```

```javascript
assert.equal( parentIdDeleteResponse.status, 204 )
```

Lists a file's permissions

```javascript
permissionsResponse = client.files.fileId(ID_FILE).permissions.get()
```

```javascript
assert.equal( permissionsResponse.status, 200 )
```

Inserts a permission for a file.

Warning: Concurrent permissions operations on the same file are not supported; only the last update is applied.

```javascript
ANOTHER_USER_EMAIL = prompt("Please enter real google email for sharing file with them")
```

```javascript
permissionsCreateResponse = client.files.fileId(ID_FILE).permissions.post({
  "role" : "writer" ,
  "type" : "user" ,
  "value": ANOTHER_USER_EMAIL
})
```

```javascript
assert.equal( permissionsCreateResponse.status, 200 )
ID_PERMISSION = permissionsCreateResponse.body.id
```

Gets a permission by ID

```javascript
permissionResponse = client.files.fileId(ID_FILE).permissions.permissionId(ID_PERMISSION).get()
```

```javascript
assert.equal( permissionResponse.status, 200 )
```

Updates a permission.

Warning: Concurrent permissions operations on the same file are not supported; only the last update is applied.

```javascript
permissionUpdateResponse = client.files.fileId(ID_FILE).permissions.permissionId(ID_PERMISSION).put({
  "role" : "reader" 
})
```

```javascript
assert.equal( permissionUpdateResponse.status, 200 )
```

Updates a permission. This method supports patch semantics.

Warning: Concurrent permissions operations on the same file are not supported; only the last update is applied.

```javascript
permissionPatchResponse = client.files.fileId(ID_FILE).permissions.permissionId(ID_PERMISSION).patch({
  "role" : "writer" 

})
```

```javascript
assert.equal( permissionPatchResponse.status, 200 )
```

Deletes a permission from a file. 

Warning: Concurrent permissions operations on the same file are not supported; only the last update is applied.

```javascript
permissionDeleteResponse = client.files.fileId(ID_FILE).permissions.permissionId(ID_PERMISSION).delete()
```

```javascript
assert.equal( permissionDeleteResponse.status, 204 )
```

Deleting temporary files

```javascript
client.files.fileId(ID_FILE).delete()
```

```javascript
client.files.fileId(ID_FILE_COPY).delete()
```