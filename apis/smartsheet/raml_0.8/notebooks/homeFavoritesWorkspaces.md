---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8136/versions/8333/portal/pages/7059/preview
apiNotebookVersion: 1.1.66
title: Home, favorites, workspaces
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
// Read about the Smartsheet API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8136/versions/8333/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8136/versions/8333/definition');
```

```javascript
TOKEN = prompt("Please input Access Token")
```

```javascript
function customAuthHack(o){
o.setRequestHeader("Authorization", "Bearer " + TOKEN);
}
```

```javascript
API.set(client, 'beforeSend', new function(){return customAuthHack})
```

Provides a nested list of all Home objects, including sheets, workspaces and folders, and optionally reports and/or templates, as shown on the Home tab. NOTE: if there are no folders, sheets, templates (optional), or workspaces present, empty arrays are returned.
 Allowed For: [READ_SHEETS]

```javascript
homeResponse = client.home.get()
```

```javascript
assert.equal( homeResponse.status, 200 )
```

Returns an array of top-level child folders.
 Allowed For: [READ_SHEETS]

```javascript
foldersResponse = client.home.folders.get()
```

```javascript
assert.equal( foldersResponse.status, 200 )
```

Delete existing notebook test folders

```javascript
NOTEBOOK_TEST_FOLDER = "notebookTestFolder"
```

```javascript
for (i = 0; i < foldersResponse.body.length; i++){
  if (foldersResponse.body[i].name == NOTEBOOK_TEST_FOLDER){
  	client.folder.folderId(foldersResponse.body[i].id).delete()
  }
}
```

Creates child folder.
 Allowed For: [ADMIN_WORKSPACES]

```javascript
foldersCreateResponse = client.home.folders.post({
  "name" : NOTEBOOK_TEST_FOLDER
})
```

```javascript
assert.equal( foldersCreateResponse.status, 200 )
ID_HOME_FOLDER = foldersCreateResponse.body.result.id
```

List All Favorites

```javascript
favoritesResponse = client.favorites.get()
```

```javascript
assert.equal( favoritesResponse.status, 200 )
```

Create Favorites

```javascript
favoritesCreateResponse = client.favorites.post([
  {
    "type" : "folder" ,
    "objectId" : ID_HOME_FOLDER
  }
])
```

```javascript
assert.equal( favoritesCreateResponse.status, 200 )
```

Delete Favorites

```javascript
objectDeleteResponse = client.favorites.type("folder").objectId(ID_HOME_FOLDER).delete()
```

```javascript
assert.equal( objectDeleteResponse.status, 200 )
```

Create temporary favorites

```javascript
tempFolderCreateResponse = client.home.folders.post({
  "name" : "notebookTestFolder"
})
ID_TEMP_FOLDER = tempFolderCreateResponse.body.result.id

favoritesCreateResponse = client.favorites.post([
  {
    "type" : "folder" ,
    "objectId" : ID_TEMP_FOLDER
  }
])

```

Delete Favorites in Bulk

```javascript
bulkFavoritesDeleteResponse = client.favorites.type("folder").delete({},{"query":{"objectIds":ID_TEMP_FOLDER}})
```

```javascript
assert.equal( bulkFavoritesDeleteResponse.status, 200 )
```

Lists all of the Workspaces to which the user has access.
 Allowed For: [READ_SHEETS]

```javascript
workspacesResponse = client.workspaces.get()
```

```javascript
assert.equal( workspacesResponse.status, 200 )
```

Delete test notebook workspaces if exists. 

```javascript
NOTEBOOK_TEST_WORKSPACE = "notebookTestWorkspace"
```

```javascript
for (i = 0; i < workspacesResponse.body.length; i++){
  if (workspacesResponse.body[i].name == NOTEBOOK_TEST_WORKSPACE | workspacesResponse.body[i].name == "UpdatedNotebookTestWorkspace"){
  	client.workspace.workspaceId(workspacesResponse.body[i].id).delete()
  }
}
```

Creates a new Workspace.
 Allowed For: [ADMIN_WORKSPACES]

```javascript
workspacesCreateResponse = client.workspaces.post({
  "name" : NOTEBOOK_TEST_WORKSPACE
})
```

```javascript
assert.equal( workspacesCreateResponse.status, 200 )
ID_WORKSPACE = workspacesCreateResponse.body.result.id
```

Lists contents of a Workspace. By default, this only lists the top level items in the workspace. To load all of the contents, including nested folders, include the "loadAll=true" query string parameter. NOTE: if no folders and/or sheets are present in the workspace, the corresponding attribute (e.g., "folders", "sheets") will not be present in the response object.
 Allowed For: [READ_SHEETS]

```javascript
singleWorkspaceResponse = client.workspace.workspaceId(ID_WORKSPACE).get()
```

```javascript
assert.equal( singleWorkspaceResponse.status, 200 )
```

Updates a Workspace. Method PUT /workspace/{id} Access Scope ADMIN_WORKSPACES Headers Content-Type: application/json Request Body Workspace object limited to the following attribute: name (string) Returns Result object containing the updated Workspace object.
 Allowed For: [ADMIN_WORKSPACES]

```javascript
workspaceUpdateResponse = client.workspace.workspaceId(ID_WORKSPACE).put({
  "name" : "UpdatedNotebookTestWorkspace"
})
```

```javascript
assert.equal( workspaceUpdateResponse.status, 200 )
```

Creates child folder.
 Allowed For: [ADMIN_WORKSPACES]

```javascript
workspaceFoldersCreateResponse = client.workspace.workspaceId(ID_WORKSPACE).folders.post({
  "name" : "notebookTestFolder"
})
```

```javascript
assert.equal( workspaceFoldersCreateResponse.status, 200 )
ID_WORKSPACE_FOLDER = workspaceFoldersCreateResponse.body.result.id
```

Returns an array of top-level child folders.
 Allowed For: [READ_SHEETS]

```javascript
workspaceFoldersResponse = client.workspace.workspaceId(ID_WORKSPACE).folders.get()
```

```javascript
assert.equal( workspaceFoldersResponse.status, 200 )
```

Delete notebook test sheet if exist

```javascript
sheetsResponse = client.sheets.get() 
```

```javascript
for (i = 0; i < sheetsResponse.body.length; i++){
  if (sheetsResponse.body[i].name == "notebookTestSheet"){
  	client.sheet.sheetId(sheetsResponse.body[i].id).delete()
  }
}
```

Creates a sheet from an existing sheet or template. Sheets can be created in your default "Sheets" collection, into a workspace or into any folder whether that folder is in a shared workspace or your personal "Sheets" collection. See "Method" below for the URL to use for each of these operations.
 Allowed For: [CREATE_SHEETS]

```javascript
sheetsCreateResponse = client.workspace.workspaceId(ID_WORKSPACE).sheets.post({
  "name" : "notebookTestSheet" ,
  "columns" : [
    {
      "title" : "Favorite" ,
      "type" : "CHECKBOX" ,
      "symbol" : "STAR"
    } , {
      "title" : "Primary Column" ,
      "primary" : true ,
      "type" : "TEXT_NUMBER"
    } , {
      "title" : "Status" ,
      "type" : "PICKLIST" ,
      "options" : [
        "Not Started" ,
        "Started" ,
        "Completed"
      ]
    }
  ]
})
```

```javascript
assert.equal( sheetsCreateResponse.status, 200 )
ID_SHEET = sheetsCreateResponse.body.result.id
```

Lists all the Users to whom a Sheet or Workspace is shared, and their access level.
 Allowed For: [READ_SHEETS]

```javascript
sharesResponse = client.workspace.workspaceId(ID_WORKSPACE).shares.get()
```

```javascript
assert.equal( sharesResponse.status, 200 )
```

Delete test notebook shares if exists. 

```javascript
NOTEBOOK_TEST_SHARE_EMAIL = "jane.doe@smartsheet.com"
```

```javascript
for (i = 0; i < sharesResponse.body.length; i++){
  if (sharesResponse.body[i].email == NOTEBOOK_TEST_SHARE_EMAIL){
  	 client.workspace.workspaceId(ID_WORKSPACE).share.userId(sharesResponse.body[i].id).delete()
  }
}
```

Shares a Sheet or Workspace to the user specified.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
sharesCreateResponse = client.workspace.workspaceId(ID_WORKSPACE).shares.post({
  "email" : NOTEBOOK_TEST_SHARE_EMAIL ,
  "accessLevel" : "EDITOR"
})
```

```javascript
assert.equal( sharesCreateResponse.status, 200 )
ID_USER_SHARE = sharesCreateResponse.body.result.id
```

Returns the access level for the specified user on the specified sheet or workspace .
 Allowed For: [READ_SHEETS]

```javascript
userShareResponse = client.workspace.workspaceId(ID_WORKSPACE).share.userId(ID_USER_SHARE).get()
```

```javascript
assert.equal( userShareResponse.status, 200 )
```

Updates the access level of a user of the specified Sheet or Workspace.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
userShareUpdateResponse = client.workspace.workspaceId(ID_WORKSPACE).share.userId(ID_USER_SHARE).put({
  "accessLevel" : "VIEWER"
})
```

```javascript
assert.equal( userShareUpdateResponse.status, 200 )
```

Deletes the Share.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
userShareDeleteResponse = client.workspace.workspaceId(ID_WORKSPACE).share.userId(ID_USER_SHARE).delete()
```

```javascript
assert.equal( userShareDeleteResponse.status, 200 )
```

Shares a Sheet or Workspace to the users specified.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
multishareCreateResponse = client.workspace.workspaceId(ID_WORKSPACE).multishare.post({
  "users" : [
    {
      "email" : "john.doe@smartsheet.com"
    } , {
      "email" : "jane.doe@smartsheet.com"
    }
  ] ,
  "subject" : "My new notebook Smartsheet" ,
  "message" : "I have shared a Smartsheet with you. Please review it for the latest updates" ,
  "accessLevel" : "ADMIN" ,
  "ccMe" : false
})
```

```javascript
assert.equal( multishareCreateResponse.status, 200 )
```

Lists all the users and groups to whom a Sheet or Workspace is shared, and their access level.
 Allowed For: [READ_SHEETS]

```javascript
shareswithgroupsResponse = client.workspace.workspaceId(ID_WORKSPACE).shareswithgroups.get()
```

```javascript
assert.equal( shareswithgroupsResponse.status, 200 )
```

Create test user

```javascript
newTestUserEmail = prompt("Please enter test user email")
```

```javascript
testUsersCreateResponse = client.users.post({
  "firstName" : "Notebook Test" ,
  "lastName" : "User" ,
  "email" : newTestUserEmail ,
  "admin" : true ,
  "licensedSheetCreator" : true
})
ID_TEST_USER = testUsersCreateResponse.body.result.id
```

Delete test notebook shares if exists.

```javascript
for (i = 0; i < shareswithgroupsResponse.body.length; i++){
  if (shareswithgroupsResponse.body[i].userId == ID_TEST_USER){
  	 client.workspace.workspaceId(ID_WORKSPACE).sharewithgroups.shareId(shareswithgroupsResponse.body[i].id).delete()
  }
}
```

Shares a Sheet or Workspace to the user specified.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareswithgroupsCreateResponse = client.workspace.workspaceId(ID_WORKSPACE).shareswithgroups.post({
  "type" : "USER" ,
  "userId" : ID_TEST_USER ,
  "accessLevel" : "EDITOR"
})
```

```javascript
assert.equal( shareswithgroupsCreateResponse.status, 200 )
ID_USER_SHAREWHITHGROUPS = shareswithgroupsCreateResponse.body.result.id
```

Returns the access level for the specified user or group on the specified sheet or workspace .
 Allowed For: [READ_SHEETS]

```javascript
singleShareResponse = client.workspace.workspaceId(ID_WORKSPACE).sharewithgroups.shareId(ID_USER_SHAREWHITHGROUPS).get()
```

```javascript
assert.equal( singleShareResponse.status, 200 )
```

Delete test group if exist

```javascript
TEST_GROUP_NAME = "Notebook test group"
```

```javascript
groupsResponse = client.groups.get()
```

```javascript
for (i = 0; i < groupsResponse.body.length; i++){
  if (groupsResponse.body[i].name == TEST_GROUP_NAME){
  	 client.group.groupId(groupsResponse.body[i].id).delete()
  }
}
```

Create test group

```javascript
meResponse = client.user.me.get()
myEmail = meResponse.body.email
```

```javascript
groupsCreateResponse = client.groups.post({
  "name" : TEST_GROUP_NAME ,
  "description" : "Group created via API" ,
  "members" : [
    {
      "email" : myEmail
    }
  ]
})
ID_GROUP = groupsCreateResponse.body.result.id
```

Shares a Sheet or Workspace to the users specified.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)

```javascript
multisharewithgroupsCreateResponse = client.workspace.workspaceId(ID_WORKSPACE).multisharewithgroups.post({
  "shares" : [
   {
      "type" : "GROUP" ,
      "groupId" : ID_GROUP
    }
  ] ,
  "subject" : "My new notebook Smartsheet" ,
  "message" : "I have shared a Smartsheet workspace with you. Please review it for the latest updates" ,
  "accessLevel" : "ADMIN" ,
  "ccMe" : false
})
```

```javascript
assert.equal( multisharewithgroupsCreateResponse.status, 200 )
```

Updates the access level of a user or group for the specified Sheet or Workspace.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareWhithGroupsUpdateResponse = client.workspace.workspaceId(ID_WORKSPACE).sharewithgroups.shareId(ID_USER_SHAREWHITHGROUPS).put({
  "accessLevel" : "VIEWER"
})
```

```javascript
assert.equal( shareWhithGroupsUpdateResponse.status, 200 )
```

Deletes the Share.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareWhithGroupsDeleteResponse = client.workspace.workspaceId(ID_WORKSPACE).sharewithgroups.shareId(ID_USER_SHAREWHITHGROUPS).delete()
```

```javascript
assert.equal( shareWhithGroupsDeleteResponse.status, 200 )
```

Deletes a Workspace and all its contents. Method DELETE /workspace/{id} Access Scope ADMIN_WORKSPACES Returns Result object indicating success..
 Allowed For: [ADMIN_WORKSPACES]

```javascript
workspaceDeleteResponse = client.workspace.workspaceId(ID_WORKSPACE).delete()
```

```javascript
assert.equal( workspaceDeleteResponse.status, 200 )
```

Delete temporary folders, group and user

```javascript
foldersResponse = client.home.folders.get()
for (i = 0; i < foldersResponse.body.length; i++){
  if (foldersResponse.body[i].name == NOTEBOOK_TEST_FOLDER){
  	client.folder.folderId(foldersResponse.body[i].id).delete()
  }
}
```

```javascript
client.user.userId(ID_TEST_USER).delete()
```

```javascript
client.group.groupId(ID_GROUP).delete()
```