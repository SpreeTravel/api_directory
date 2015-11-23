---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/12164/versions/12574/portal/pages/13395/preview
apiNotebookVersion: 1.1.66
title: File. part 2
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

Create file

```javascript
createFileResponse = client.files.post({
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
ID_FILE = createFileResponse.body.id
```


Lists a file's comments

```javascript
commentsResponse = client.files.fileId(ID_FILE).comments.get()
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Creates a new comment on the given file

```javascript
commentsCreateResponse = client.files.fileId(ID_FILE).comments.post({
  "content" : "NotebookTestFile comment" 
})
```

```javascript
assert.equal( commentsCreateResponse.status, 200 )
ID_FILE_COMMENT = commentsCreateResponse.body.commentId
```

Gets a comment by ID

```javascript
commentResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).get()
```

```javascript
assert.equal( commentResponse.status, 200 )
```

```javascript
commentUpdateResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).put({
  "content" : "NotebookTestFile comment updated" 
})
```

```javascript
assert.equal( commentUpdateResponse.status, 200 )
```

Updates an existing comment. This method supports patch semantics

```javascript
commentPatchResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).patch({
 "content" : "NotebookTestFile comment patched" 
})
```

```javascript
assert.equal( commentPatchResponse.status, 200 )
```

Lists all of the replies to a comment

```javascript
repliesResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).replies.get()
```

```javascript
assert.equal( repliesResponse.status, 200 )
```

Creates a new reply to the given comment

```javascript
repliesCreateResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).replies.post({
  "content" : "Test reply" 
})
```

```javascript
assert.equal( repliesCreateResponse.status, 200 )
ID_FILE_COMMENT_REPLY = repliesCreateResponse.body.replyId
```

Gets a reply

```javascript
replyResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).replies.replyId(ID_FILE_COMMENT_REPLY).get()
```

```javascript
assert.equal( replyResponse.status, 200 )
```

Updates an existing reply

```javascript
replyUpdateResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).replies.replyId(ID_FILE_COMMENT_REPLY).put({
  "content":"Test reply update"
})
```

```javascript
assert.equal( replyUpdateResponse.status, 200 )
```

Updates an existing reply. This method supports patch semantics

```javascript
replyPatchResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).replies.replyId(ID_FILE_COMMENT_REPLY).patch({
  "content" : "Test reply patched" 
})
```

```javascript
assert.equal( replyPatchResponse.status, 200 )
```

Deletes a reply

```javascript
replyDeleteResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).replies.replyId(ID_FILE_COMMENT_REPLY).delete()
```

```javascript
assert.equal( replyDeleteResponse.status, 204 )
```

Deletes a comment

```javascript
commentIdDeleteResponse = client.files.fileId(ID_FILE).comments.commentId(ID_FILE_COMMENT).delete()
```

```javascript
assert.equal( commentIdDeleteResponse.status, 204 )
```

Exports the contents of the Realtime API data model associated with this file as JSON

```javascript
realtimeResponse = client.files.fileId(ID_FILE).realtime.get()
```

```javascript
assert.equal( realtimeResponse.status, 200 )
```

Lists a file's properties

```javascript
propertiesResponse = client.files.fileId(ID_FILE).properties.get()
```

```javascript
assert.equal( propertiesResponse.status, 200 )
```

Adds a property to a file

```javascript
propertiesCreateResponse = client.files.fileId(ID_FILE).properties.post({
  "key"  : "test",
  "value" : "yes"
})
```

```javascript
assert.equal( propertiesCreateResponse.status, 200 )
PROPERTY_KEY = propertiesCreateResponse.body.key
```

Gets a property by its key

```javascript
propertyKeyResponse = client.files.fileId(ID_FILE).properties.propertyKey(PROPERTY_KEY).get()
```

```javascript
assert.equal( propertyKeyResponse.status, 200 )
```

Updates a property

```javascript
propertyKeyUpdateResponse = client.files.fileId(ID_FILE).properties.propertyKey(PROPERTY_KEY).put({
  "key" : PROPERTY_KEY,
  "value" : "true"
})
```

```javascript
assert.equal( propertyKeyUpdateResponse.status, 200 )
```

Updates a property. This method supports patch semantics

```javascript
propertyKeyPatchResponse = client.files.fileId(ID_FILE).properties.propertyKey(PROPERTY_KEY).patch({
  "key" : PROPERTY_KEY,
  "value" : "NotebookTest"
})
```

```javascript
assert.equal( propertyKeyPatchResponse.status, 200 )
```

Deletes a property

```javascript
propertyKeyDeleteResponse = client.files.fileId(ID_FILE).properties.propertyKey(PROPERTY_KEY).delete()
```

```javascript
assert.equal( propertyKeyDeleteResponse.status, 204 )
```

Permanently deletes a file by ID. Skips the trash

```javascript
fileDeleteResponse = client.files.fileId(ID_FILE).delete()
```

```javascript
assert.equal( fileDeleteResponse.status, 204 )
```

Permanently deletes all of the user's trashed files

```javascript
permanetlyDelete = confirm("Do want to delete all your trashed files, permanently?")
```

```javascript
if (permanetlyDelete){
	trashDeleteResponse = client.files.trash.delete()
}
```

```javascript
if (permanetlyDelete){
  assert.equal( trashDeleteResponse.status, 204 )
}  
```

Create test folder

```javascript
createFolderResponse = client.files.post({
  "title": "NotebookTestFolder",
  "mimeType": "application/vnd.google-apps.folder"
})
```

```javascript
ID_FOLDER = createFolderResponse.body.id
```

Create test file

```javascript
createTestFileResponse = client.files.post({
  "title" : "NotebookFolderTestFile" ,
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
ID_TEST_FILE = createTestFileResponse.body.id
```

Lists a folder's children. To list all children of the root folder, use the alias root for the folderId value

```javascript
folderIdChildrenResponse = client.files.folderId(ID_FOLDER).children.get()
```

```javascript
assert.equal( folderIdChildrenResponse.status, 200 )
```

Inserts a file into a folder

```javascript
folderChildrenCreateResponse = client.files.folderId(ID_FOLDER).children.post({
  "id" : ID_TEST_FILE
})
```

```javascript
assert.equal( folderChildrenCreateResponse.status, 200 )
```

Gets a specific child reference

```javascript
childIdResponse = client.files.folderId(ID_FOLDER).children.childId(ID_TEST_FILE).get()
```

```javascript
assert.equal( childIdResponse.status, 200 )
```

Removes a child from a folder

```javascript
childIdDeleteResponse = client.files.folderId(ID_FOLDER).children.childId(ID_TEST_FILE).delete()
```

```javascript
assert.equal( childIdDeleteResponse.status, 204 )
```

Delete test files

```javascript
client.files.fileId(ID_TEST_FILE).delete()
```

```javascript
client.files.fileId(ID_FOLDER).delete()
```