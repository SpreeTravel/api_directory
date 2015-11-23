---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8136/versions/8333/portal/pages/7060/preview
apiNotebookVersion: 1.1.66
title: Folder, templates, report, users, groups, search
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
o.setRequestHeader("Authorization", "Bearer "  + TOKEN);
}
```

```javascript
API.set(client, 'beforeSend', new function(){return customAuthHack})
```

```javascript
function selectReport(msg){
  var number = null
  var pageSize = -1
  var pagesCount = 1
  var searchResponse = client.home.get({"include":"reports"})
  for(var i = 0 ; i < pagesCount ; i++ ){
    var numbers = searchResponse.body.reports
    var message = msg
    if(i<pagesCount-1){
      message += "\nTo view the next page enter a negative value."
    }
    message += "\n"
    for( var j in numbers){
      message += "\n" + j + ": " + numbers[j].name
    }
    var indStr = prompt(message)
    if(!indStr)
      break;

    ind = Number.parseInt(indStr)
    if(!(typeof ind === "number")||Number.isNaN(ind)||ind<0){
       break
    }
    if(ind>=0){
      return numbers[ind]
    }  
  }
  return null
}
```

Delete existing notebook test folders

```javascript
NOTEBOOK_TEST_FOLDER = "notebookTestFolder"
```

```javascript
foldersResponse = client.home.folders.get()
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
ID_FOLDER = foldersCreateResponse.body.result.id
```

List contents of a Folder. NOTE: if no folders and/or sheets are present in the folder, the corresponding attribute (e.g., "folders", "sheets") will not be present in the response object.
 Allowed For: [READ_SHEETS]

```javascript
singleFolderResponse = client.folder.folderId(ID_FOLDER).get()
```

```javascript
assert.equal( singleFolderResponse.status, 200 )
```

Updates folder.
 Allowed For: [ADMIN_WORKSPACES]

```javascript
singleFolderUpdateResponse = client.folder.folderId(ID_FOLDER).put({
  "name" : NOTEBOOK_TEST_FOLDER
})
```

```javascript
assert.equal( singleFolderUpdateResponse.status, 200 )
```

Returns an array of top-level child folders.
 Allowed For: [READ_SHEETS]

```javascript
foldersResponse = client.folder.folderId(ID_FOLDER).folders.get()
```

```javascript
assert.equal( foldersResponse.status, 200 )
```

Delete notebook test folders if exist

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
foldersCreateResponse = client.folder.folderId(ID_FOLDER).folders.post({
  "name" : NOTEBOOK_TEST_FOLDER
})
```

```javascript
assert.equal( foldersCreateResponse.status, 200 )
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

Creates a sheet from scratch. Sheets can be created in your default "Sheets" collection, into a workspace or into any folder whether that folder is in a shared workspace or your personal "Sheets" collection. See "Method" below for the URL to use for each of these operations.
 Allowed For: [CREATE_SHEETS]

```javascript
sheetsCreateResponse = client.folder.folderId(ID_FOLDER).sheets.post({
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

Lists all of the templates to which the user has access. Note: only user-created templates will be returned. If you like a public template created by Smartsheet and want to make it available via the API, please save it as your own template.
 Allowed For: [READ_SHEETS]

```javascript
templatesResponse = client.templates.get()
```

```javascript
assert.equal( templatesResponse.status, 200 )
```

Retrieve a list of available Reports

```javascript
REPORT = selectReport ("Please, select Report by entering its index.\nThis report will be used for notebook tests.")
ID_REPORT = REPORT.id
```

Get Report

```javascript
singlepReportResponse = client.report.reportId(ID_REPORT).get()
```

```javascript
assert.equal(singlepReportResponse.status, 200 )
```

Send Report

```javascript
emailsCreateResponse = client.report.reportId(ID_REPORT).emails.post({
  "to" : [
    "john.doe@smartsheet.com" ,
    "jane.doe@smartsheet.com"
  ] ,
  "subject" : "Check these rows out!(Notebook Test)" ,
  "message" : "Here are the rows I mentioned in our meeting" ,
  "ccMe" : false ,
  "format" : "PDF" ,
  "formatDetails" : {
    "paperSize" : "A4"
  }
})
```

```javascript
assert.equal( emailsCreateResponse.status, 200 )
```

Lists all the users and groups to whom a Sheet or Workspace is shared, and their access level.
 Allowed For: [READ_SHEETS]

```javascript
shareswithgroupsResponse = client.report.reportId(ID_REPORT).shareswithgroups.get()
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
  	 client.report.reportId(ID_REPORT).sharewithgroups.shareId(shareswithgroupsResponse.body[i].id).delete()
  }
}
```

Shares a Sheet or Workspace to the user specified.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareswithgroupsCreateResponse = client.report.reportId(ID_REPORT).shareswithgroups.post({
  "type" : "USER" ,
  "userId" : ID_TEST_USER ,
  "accessLevel" : "EDITOR"
})
```

```javascript
assert.equal( shareswithgroupsCreateResponse.status, 200 )
ID_SHARE_WITH_GROUP = shareswithgroupsCreateResponse.body.result.id
```

Returns the access level for the specified user or group on the specified sheet or workspace .
 Allowed For: [READ_SHEETS]

```javascript
singleShareResponse = client.report.reportId(ID_REPORT).sharewithgroups.shareId(ID_SHARE_WITH_GROUP).get()
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
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
multisharewithgroupsCreateResponse = client.report.reportId(ID_REPORT).multisharewithgroups.post({
  "shares" : [
   {
      "type" : "GROUP" ,
      "groupId" : ID_GROUP
    }
  ] ,
  "subject" : "My new notebook Smartsheet" ,
  "message" : "I have shared a Smartsheet Report with you. Please review it for the latest updates" ,
  "accessLevel" : "ADMIN" ,
  "ccMe" : false
})
```

```javascript
assert.equal( multisharewithgroupsCreateResponse.status, 200 )
```

Deletes the Share.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareDeleteResponse = client.report.reportId(ID_REPORT).sharewithgroups.shareId(ID_SHARE_WITH_GROUP).delete()
```

```javascript
assert.equal( shareDeleteResponse.status, 200 )
```

Lists all users in the organization. You can optionally specify a list of email addresses of users to narrow down the results using the "email" query string parameter.
Allowed For: [ADMIN_USERS]

```javascript
usersResponse = client.users.get()
```

```javascript
assert.equal( usersResponse.status, 200 )
```

Adds a user to the organization. If successful, will trigger the same invitation flow as it would in the web application.
 Allowed For: [ADMIN_USERS]

```javascript
newUserEmail = prompt("Please input email for new user")
```

```javascript
usersCreateResponse = client.users.post({
  "firstName" : "Notebook Test" ,
  "lastName" : "User" ,
  "email" : newUserEmail ,
  "admin" : true ,
  "licensedSheetCreator" : true
})
```

```javascript
assert.equal( usersCreateResponse.status, 200 )
ID_USER = usersCreateResponse.body.result.id
```

Returns the list of all sheets owned by the members of the account (organization).
 Allowed For: [ADMIN_USERS]

```javascript
sheetsResponse = client.users.sheets.get()
```

```javascript
assert.equal( sheetsResponse.status, 200 )
```

Gets the current user. Method GET /user/me Access Scope all scopes Headers Content-Type: application/json Returns A User Profile object for the current user.
 Allowed For: [all scopes]

```javascript
meResponse = client.user.me.get()
```

```javascript
assert.equal( meResponse.status, 200 )
```

Gets a user by id. Method GET /user/{userId} Access Scope ADMIN_USERS Headers Content-Type: application/json Returns A User Profile object for the user specified
 Allowed For: [ADMIN_USERS]

```javascript
userResponse = client.user.userId(ID_USER).get()
```

```javascript
assert.equal( userResponse.status, 200 )
```

Updates a user in the organization.
 Allowed For: [ADMIN_USERS]

```javascript
userUpdateResponse = client.user.userId(ID_USER).put({
  "admin" : false ,
  "licensedSheetCreator" : true
})
```

```javascript
assert.equal( userUpdateResponse.status, 200 )
```

Removes a user from an organization. User is transitioned to a free collaborator with read-only access to owned sheets (unless those are optionally transferred to another user).
 Allowed For: [ADMIN_USERS]

```javascript
userDeleteResponse = client.user.userId(ID_USER).delete()
```

```javascript
assert.equal( userDeleteResponse.status, 200 )
```

Returns the list of groups in an organization. To fetch group members, use the GET /group/{id} method.
 Allowed For: [ADMIN_USERS]

```javascript
groupsResponse = client.groups.get()
```

```javascript
assert.equal( groupsResponse.status, 200 )
```

Delete test group if exist

```javascript
GROUP_NAME = "Notebook group"
```

```javascript
for (i = 0; i < groupsResponse.body.length; i++){
  if (groupsResponse.body[i].name == GROUP_NAME){
  	 client.group.groupId(groupsResponse.body[i].id).delete()
  }
}
```

Creates a new group. NOTE: this method is only available to group administrators. Method POST /groups Access Scope ADMIN_USERS Headers Content-Type: application/json Request Body JSON-formatted Group object, limited to the following attributes: name; must be unique within the organization description (optional) members (optional array of Group Member objects limited to the following attributes) email Returns Result object, containing a Group object for the newly-created group.
 Allowed For: [ADMIN_USERS]

```javascript
meResponse = client.user.me.get()
myEmail = meResponse.body.email
```

```javascript
groupsCreateResponse = client.groups.post({
  "name" : GROUP_NAME ,
  "description" : "Group created via API" ,
  "members" : [
    {
      "email" : myEmail
    }
  ]
})
```

```javascript
assert.equal( groupsCreateResponse.status, 200 )
ID_NOTEBOOK_GROUP = groupsCreateResponse.body.result.id
```

Returns group information, including its members. Method GET /group/{groupId} Access Scope ADMIN_USERS Returns Group object, including a list of Group Member objects.
 Allowed For: [ADMIN_USERS]

```javascript
groupResponse = client.group.groupId(ID_NOTEBOOK_GROUP).get()
```

```javascript
assert.equal( groupResponse.status, 200 )
```

Updates a group. NOTE: this method is only available to group administrators and system administrators. Method PUT /group/{groupId} Access Scope ADMIN_USERS Headers Content-type: application/json Request Body JSON-formatted Sheet object, limited to the following attributes: name; must be unique within the organization(optional) description (optional) Returns Result object containing Group object for the updated group.
 Allowed For: [ADMIN_USERS]

```javascript
groupUpdateResponse = client.group.groupId(ID_NOTEBOOK_GROUP).put({
  "name" : GROUP_NAME ,
  "description" : "Some new description"
})
```

```javascript
assert.equal( groupUpdateResponse.status, 200 )
```

Adds members to a group. NOTES: This method is only available to group administrators and system administrators. This operation is asynchronous, meaning the users may not yet have sharing access to sheets for a period of time after this operation returns. For small groups with limited sharing, the operation should complete quickly (within a few seconds). For large groups with many shares, this operation could possibly take more than a minute to complete. Method POST /group/{groupId}/members Access Scope ADMIN_USERS Headers Content-Type: application/json Request Body Array of JSON-formatted Group Member objects, limited to the following attribute: email Returns Result object, containing an array of the group members that will be added to the group (duplicate members will be filtered out)
 Allowed For: [ADMIN_USERS]

```javascript
membersCreateResponse = client.group.groupId(ID_NOTEBOOK_GROUP).members.post([
  {
    "email" : newTestUserEmail
  }
])
```

```javascript
assert.equal( membersCreateResponse.status, 200 )
```

Removes member from a group. NOTES: This method is only available to group administrators and system administrators. This operation is asynchronous, meaning group members may retain their sharing access for a brief period of time after the call returns. For small groups with limited sharing, the operation should complete quickly (within a few seconds). For large groups with many shares, this operation could possibly take more than a minute to complete. Method DELETE /group/{groupId}/member/{userId} Access Scope ADMIN_USERS Returns Result object.
 Allowed For: [ADMIN_USERS]

```javascript
userDeleteResponse = client.group.groupId(ID_NOTEBOOK_GROUP).member.userId(ID_TEST_USER).delete()
```

```javascript
assert.equal( userDeleteResponse.status, 200 )
```

Deletes a group. NOTES: This method is only available to group administrators and system administrators. This operation is asynchronous, meaning group members may retain their sharing access for a brief period of time after the call returns. For small groups with limited sharing, the operation should complete quickly (within a few seconds). For large groups with many shares, this operation could possibly take more than a minute to complete. Method DELETE /group/{groupId} Access Scope ADMIN_USERS Returns Result object.
 Allowed For: [ADMIN_USERS]

```javascript
groupDeleteResponse = client.group.groupId(ID_NOTEBOOK_GROUP).delete()
```

```javascript
assert.equal( groupDeleteResponse.status, 200 )
```

Performs a search across all Sheets to which user has access. NOTE: Searching on partial words is supported using wildcards. NOTE: The number of results returned is limited to one hundred. However
 Allowed For: [READ_SHEETS]

```javascript
searchResponse = client.search.get({"query":"mySheet"})
```

```javascript
assert.equal( searchResponse.status, 200 )
```

Search Sheet

```javascript
sheetResponse = client.search.sheet.sheetId(ID_SHEET).get({"query":"stuff"})
```

```javascript
assert.equal( sheetResponse.status, 200 )
```

Deletes folder and its contents.
 Allowed For: [ADMIN_WORKSPACES]

```javascript
folderDeleteResponse = client.folder.folderId(ID_FOLDER).delete()
```

```javascript
assert.equal( folderDeleteResponse.status, 200 )
```

Get Server Info

```javascript
serverinfoResponse = client.serverinfo.get()
```

```javascript
assert.equal( serverinfoResponse.status, 200 )
```

Delete test user, group

The method throws 403 if the same email have been entered for both test users.

```javascript
client.user.userId(ID_TEST_USER).delete()
```

```javascript
client.group.groupId(ID_GROUP).delete()
```