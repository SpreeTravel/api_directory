---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8136/versions/8333/portal/pages/7062/preview
apiNotebookVersion: 1.1.66
title: Sheet/attachments, sheet/publish, sheet/column
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

Delete notebook test sheet if exist

```javascript
sheetsResponse = client.sheets.get()
for (i = 0; i < sheetsResponse.body.length; i++){
  if (sheetsResponse.body[i].name == "notebookTestSheet"){
  	client.sheet.sheetId(sheetsResponse.body[i].id).delete()
  }
}
```

Creates a sheet from scratch. Sheets can be created in your default "Sheets" collection, into a workspace or into any folder whether that folder is in a shared workspace or your personal "Sheets" collection. See "Method" below for the URL to use for each of these operations.
 Allowed For: [CREATE_SHEETS]

```javascript
sheetsCreateResponse = client.sheets.post({
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
ID_SHEET = sheetsCreateResponse.body.result.id
ID_SHEET_COLUMN1 = sheetsCreateResponse.body.result.columns[0].id
ID_SHEET_COLUMN2 = sheetsCreateResponse.body.result.columns[1].id
ID_SHEET_COLUMN3 = sheetsCreateResponse.body.result.columns[2].id
```

Returns a list of all Attachments that are on the sheet, including sheet, row and discussion level Attachments.
 Allowed For: [READ_SHEETS]

```javascript
attachmentsResponse = client.sheet.sheetId(ID_SHEET).attachments.get()
```

```javascript
assert.equal( attachmentsResponse.status, 200 )
```

Attaches a URL to a sheet, row or comment. This url can be a normal URL (attachmentType "LINK"), a Google Drive URL (attachmentType "GOOGLE_DRIVE") a Box.com URL (attachmentType "BOX_COM"), a Dropbox URL (attachmentType "DROPBOX"), or an Evernote URL (attachmentType "EVERNOTE"). NOTE: attachmentSubType is valid only for GOOGLE_DRIVE attachments which are Google Docs. It can optionally be included to indicate the type of a file. The following attachmentSubTypes are valid for GOOGLE_DRIVE attachments "DOCUMENT", "SPREADSHEET", "PRESENTATION", "PDF", "DRAWING". NOTE: when the attachment type is BOX_COM, DROPBOX, or GOOGLE_DRIVE (without an attachmentSubType specified), the mimeType will be derived by the file extension specified on the "name".
 Allowed For: [WRITE_SHEETS]

```javascript
byteCharacters = "fileContent"
byteNumbers = new Array(byteCharacters.length);
for (var i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
}
byteArray = new Uint8Array(byteNumbers);
```

```javascript
formData = new FormData()
formData.append("data-binary", new Blob([byteArray]), "text.txt")
attachmentsCreateResponse = client.sheet.sheetId(ID_SHEET).attachments.post(formData,{headers:{
  "Content-Disposition" : "attachment; filename=\"text.txt\""
}})
```

```javascript
assert.equal( attachmentsCreateResponse.status, 200 )
ID_ATTACHMENT = attachmentsCreateResponse.body.result.id
```

Returns the status of the Publish settings of the sheet, including the URLs of any enabled publishings.
 Allowed For: [READ_SHEETS]

```javascript
publishResponse = client.sheet.sheetId(ID_SHEET).publish.get()
```

```javascript
assert.equal( publishResponse.status, 200 )
```

Sets the publish status of a sheet and returns the new status, including the URLs of any enabled publishings.
 Allowed For: [ADMIN_SHEETS]

```javascript
publishUpdateResponse = client.sheet.sheetId(ID_SHEET).publish.put({
  "readOnlyLiteEnabled" : true ,
  "readOnlyFullEnabled" : false ,
  "readWriteEnabled" : false ,
  "icalEnabled" : false
})
```

```javascript
assert.equal( publishUpdateResponse.status, 200 )
```

Allows you to move, rename, or change column properties. Note that you cannot change the type of a Primary column, or any special calendar/Gantt columns. Also, if the column type is changed, all cells in the column will be converted to the new column type. Type is optional when moving or renaming, but required when changing type or dropdown values. Result object containing Column object that was modified.
 Allowed For: [ADMIN_SHEETS]

```javascript
columnUpdateResponse = client.sheet.sheetId(ID_SHEET).column.columnId(ID_SHEET_COLUMN1).put({
  "title" : "First Column" ,
  "index" : 0 ,
  "sheetId" : ID_SHEET ,
  "type" : "PICKLIST" ,
  "options" : [
    "One" ,
    "Two"
  ]
})
```

```javascript
assert.equal( columnUpdateResponse.status, 200 )
```

Deletes a column. Result object.
 Allowed For: [ADMIN_SHEETS]

```javascript
columnIdDeleteResponse = client.sheet.sheetId(ID_SHEET).column.columnId(ID_SHEET_COLUMN3).delete()
```

```javascript
assert.equal( columnIdDeleteResponse.status, 200 )
```

Fetches an attachment based on the Attachment id.
 Allowed For: [READ_SHEETS]

```javascript
attachmentResponse = client.sheet.sheetId(ID_SHEET).attachment.attachmentId(ID_ATTACHMENT).get()
```

```javascript
assert.equal( attachmentResponse.status, 200 )
```

Get All Version

```javascript
versionsResponse = client.sheet.sheetId(ID_SHEET).attachment.attachmentId(ID_ATTACHMENT).versions.get()
```

```javascript
assert.equal( versionsResponse.status, 200 )
```

Upload New Version
{headers:{"Content-Disposition":"attachment","Content-Length":"302"}}

```javascript
byteCharacters = "fileContent2"
byteNumbers = new Array(byteCharacters.length);
for (var i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
}
byteArray2 = new Uint8Array(byteNumbers);
```

```javascript
formData2 = new FormData()
formData2.append("data-binary", new Blob([byteArray2]), "text.txt")
versionsCreateResponse = client.sheet.sheetId(ID_SHEET).attachment.attachmentId(ID_ATTACHMENT).versions.post(formData2,{headers:{
  "Content-Disposition" : "attachment; filename=\"text.txt\""
}})
```

```javascript
assert.equal( versionsCreateResponse.status, 200 )
ID_VERSION = versionsCreateResponse.body.result.id
```

Delete All Version

```javascript
versionsDeleteResponse = client.sheet.sheetId(ID_SHEET).attachment.attachmentId(ID_VERSION).versions.delete()
```

```javascript
assert.equal( versionsDeleteResponse.status, 200 )
```

Create test attachment

```javascript
formData = new FormData()
formData.append("data-binary", new Blob([byteArray]), "text.txt")
attachmentsCreateResponse2 = client.sheet.sheetId(ID_SHEET).attachments.post(formData,{headers:{
  "Content-Disposition" : "attachment; filename=\"text.txt\""
}})
ID_ATTACHMENT = attachmentsCreateResponse2.body.result.id
```

Deletes attachment. If the attachment has multiple versions this deletes only the specific version specified by the attachmentId (each version has a different attachment ID).
 Allowed For: [WRITE_SHEETS]

```javascript
attachmentDeleteResponse = client.sheet.sheetId(ID_SHEET).attachment.attachmentId(ID_ATTACHMENT).delete()
```

```javascript
assert.equal( attachmentDeleteResponse.status, 200 )
```

Creates a new Discussion on a Sheet or Row. Result object containing Discussion object for the newly created discussion.
 Allowed For: [WRITE_SHEETS]

```javascript
discussionsCreateResponse = client.sheet.sheetId(ID_SHEET).discussions.post({
  "title" : "This is a new discussion(Notebook test)" ,
  "comment" : {
    "text" : "This text is the body of the first comment"
  }
})
```

```javascript
assert.equal( discussionsCreateResponse.status, 200 )
ID_DISCUSSION = discussionsCreateResponse.body.result.id
```

Adds a Comment to a Discussion.
 Allowed For: [WRITE_SHEETS]

```javascript
commentsCreateResponse = client.sheet.sheetId(ID_SHEET).discussion.discussionId(ID_DISCUSSION).comments.post({
  "text" : "This is a new comment.(Notebook test)"
})
```

```javascript
assert.equal( commentsCreateResponse.status, 200 )
ID_COMMENT = commentsCreateResponse.body.result.id
```

Gets a comment by id. Method Access Scope READ_SHEETS Headers Content-Type: application/json Returns A Comment object.
 Allowed For: [READ_SHEETS]

```javascript
commentResponse = client.sheet.sheetId(ID_SHEET).comment.commentId(ID_COMMENT).get()
```

```javascript
assert.equal( commentResponse.status, 200 )
```

Attaches a URL to a sheet, row or comment. This url can be a normal URL (attachmentType "LINK"), a Google Drive URL (attachmentType "GOOGLE_DRIVE") a Box.com URL (attachmentType "BOX_COM"), a Dropbox URL (attachmentType "DROPBOX"), or an Evernote URL (attachmentType "EVERNOTE"). NOTE: attachmentSubType is valid only for GOOGLE_DRIVE attachments which are Google Docs. It can optionally be included to indicate the type of a file. The following attachmentSubTypes are valid for GOOGLE_DRIVE attachments "DOCUMENT", "SPREADSHEET", "PRESENTATION", "PDF", "DRAWING". NOTE: when the attachment type is BOX_COM, DROPBOX, or GOOGLE_DRIVE (without an attachmentSubType specified), the mimeType will be derived by the file extension specified on the "name".
 Allowed For: [WRITE_SHEETS].

```javascript
attachmentsCreateResponse = client.sheet.sheetId(ID_SHEET).comment.commentId(ID_COMMENT).attachments.post({
  "name" : "Search Engine" ,
  "attachmentType" : "LINK" ,
  "url" : "http://www.google.com"
})
```

```javascript
assert.equal( attachmentsCreateResponse.status, 200 )
```

Deletes a comment by id. Method DELETE /sheet/{sheetId}/comment/{commentId} Deprecated as of 11/8/2014: DELETE /comment/{commentId} Access Scope WRITE_SHEETS Headers Content-Type: application/json Returns A Result object indicate success of the operation.
 Allowed For: [WRITE_SHEETS]

```javascript
commentDeleteResponse = client.sheet.sheetId(ID_SHEET).comment.commentId(ID_COMMENT).delete()
```

```javascript
assert.equal( commentDeleteResponse.status, 200 )
```

Gets a Discussion by its ID.
 Allowed For: [READ_SHEETS]

```javascript
discussionResponse = client.sheet.sheetId(ID_SHEET).discussion.discussionId(ID_DISCUSSION).get()
```

```javascript
assert.equal( discussionResponse.status, 200 )
```

Returns a list of all Attachments that are in the discussion.
 Allowed For: [READ_SHEETS]

```javascript
attachmentsResponse = client.sheet.sheetId(ID_SHEET).discussion.discussionId(ID_DISCUSSION).attachments.get()
```

```javascript
assert.equal( attachmentsResponse.status, 200 )
```

Deletes a discussion by id. Method DELETE /sheet/{sheetId}/discussion/{discussionId} Deprecated as of 11/8/2014: DELETE /discussion/{discussionId} Access Scope WRITE_SHEETS Headers Content-Type: application/json Returns A Result object indicate success of the operation.
 Allowed For: [WRITE_SHEETS]

```javascript
discussionDeleteResponse = client.sheet.sheetId(ID_SHEET).discussion.discussionId(ID_DISCUSSION).delete()
```

```javascript
assert.equal( discussionDeleteResponse.status, 200 )
```

Lists all the Users to whom a Sheet or Workspace is shared, and their access level.
 Allowed For: [READ_SHEETS]

```javascript
sharesResponse = client.sheet.sheetId(ID_SHEET).shares.get()
```

```javascript
assert.equal( sharesResponse.status, 200 )
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
for (i = 0; i < sharesResponse.body.length; i++){
  if (sharesResponse.body[i].email == newTestUserEmail){
  	 client.sheet.sheetId(ID_SHEET).share.userId(ID_TEST_USER).delete()
  }
}
```

Shares a Sheet or Workspace to the user specified.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
sharesCreateResponse = client.sheet.sheetId(ID_SHEET).shares.post({
  "email" : newTestUserEmail ,
  "accessLevel" : "EDITOR"
})
```

```javascript
assert.equal( sharesCreateResponse.status, 200 )
```

Returns the access level for the specified user on the specified sheet or workspace .
 Allowed For: [READ_SHEETS]

```javascript
userShareResponse = client.sheet.sheetId(ID_SHEET).share.userId(ID_TEST_USER).get()
```

```javascript
assert.equal( userShareResponse.status, 200 )
```

Updates the access level of a user of the specified Sheet or Workspace.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
userShareUpdateResponse = client.sheet.sheetId(ID_SHEET).share.userId(ID_TEST_USER).put({
  "accessLevel" : "VIEWER"
})
```

```javascript
assert.equal( userShareUpdateResponse.status, 200 )
```

Deletes the Share.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
userShareDeleteResponse = client.sheet.sheetId(ID_SHEET).share.userId(ID_TEST_USER).delete()
```

```javascript
assert.equal( userShareDeleteResponse.status, 200 )
```

Shares a Sheet or Workspace to the users specified.
 Allowed For: [SHARE_SHEETS(when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
multishareCreateResponse = client.sheet.sheetId(ID_SHEET).multishare.post({
  "users" : [
    {
      "email" : "john.doe@smartsheet.com"
    } , {
      "email" : "jane.doe@smartsheet.com"
    }
  ] ,
  "subject" : "My new Smartsheet(Notebook test)" ,
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
shareswithgroupsResponse = client.sheet.sheetId(ID_SHEET).shareswithgroups.get()
```

```javascript
assert.equal( shareswithgroupsResponse.status, 200 )
```

Delete test notebook shares if exists.

```javascript
for (i = 0; i < shareswithgroupsResponse.body.length; i++){
  if (shareswithgroupsResponse.body[i].userId == ID_TEST_USER){
  	 client.sheet.sheetId(ID_SHEET).sharewithgroups.shareId(shareswithgroupsResponse.body[i].id).delete()
  }
}
```

Shares a Sheet or Workspace to the user specified.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareswithgroupsCreateResponse = client.sheet.sheetId(ID_SHEET).shareswithgroups.post({
  "type" : "USER" ,
  "userId" : ID_TEST_USER ,
  "accessLevel" : "EDITOR"
})
```

```javascript
assert.equal( shareswithgroupsCreateResponse.status, 200)
ID_GROUP_SHARE = shareswithgroupsCreateResponse.body.result.id
```

Returns the access level for the specified user or group on the specified sheet or workspace .
 Allowed For: [READ_SHEETS]

```javascript
shareResponse = client.sheet.sheetId(ID_SHEET).sharewithgroups.shareId(ID_GROUP_SHARE).get()
```

```javascript
assert.equal( shareResponse.status, 200 )
```

Delete test group if exist

```javascript
TEST_GROUP_NAME = "Notebook test group"
```

```javascript
groupsResponse = client.groups.get()
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
multisharewithgroupsCreateResponse = client.sheet.sheetId(ID_SHEET).multisharewithgroups.post({
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

Updates the access level of a user or group for the specified Sheet or Workspace.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareUpdateResponse = client.sheet.sheetId(ID_SHEET).sharewithgroups.shareId(ID_GROUP_SHARE).put({
  "accessLevel" : "VIEWER"
})
```

```javascript
assert.equal( shareUpdateResponse.status, 200 )
```

Deletes the Share.
 Allowed For: [SHARE_SHEETS (when working with Sheets) ADMIN_WORKSPACES (when working with Workspaces)]

```javascript
shareDeleteResponse = client.sheet.sheetId(ID_SHEET).sharewithgroups.shareId(ID_GROUP_SHARE).delete()
```

```javascript
assert.equal( shareDeleteResponse.status, 200 )
```

Deletes the sheet specified in the URL.
 Allowed For: [DELETE_SHEETS]

```javascript
sheetDeleteResponse = client.sheet.sheetId(ID_SHEET).delete()
```

```javascript
assert.equal( sheetDeleteResponse.status, 200 )
```

Delete test user, group

```javascript
client.user.userId(ID_TEST_USER).delete()
```

```javascript
client.group.groupId(ID_GROUP).delete()
```