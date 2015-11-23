---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7881/versions/8035/portal/pages/6649/preview
apiNotebookVersion: 1.1.66
title: Threads, History and Labels
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
USER_ID = prompt("Please, enter your GMail address")
```

```javascript
CLIENT_ID = prompt("Please, enter client ID of your GMail application")
CLIENT_SECRET = prompt("Please, enter client Secret of your GMail application")
```

```javascript
// Read about the GMail RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7881/versions/8035/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7881/versions/8035/definition');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId: CLIENT_ID,
  clientSecret: CLIENT_SECRET
})
```

Metadata-only request. Sends the specified message to the recipients in the 'To', 'Cc', and 'Bcc' headers

```javascript
messageSendResponse = client.userId(USER_ID).messages.send.post({
  "raw": ""
}, {
  query: {
    "uploadType": "multipart"
  }
})
```

```javascript

```

```javascript
message=client.userId(USER_ID).messages.messageId(messageSendResponse.body.id).get()
```

```javascript
assert.equal( messageSendResponse.status, 200 )
messageId = messageSendResponse.body.id
threadId = messageSendResponse.body.threadId
```

Lists the history of all changes to the given mailbox. History results are returned in chronological order (increasing 'historyId')

```javascript
historyResponse = client.userId(USER_ID).history.get({startHistoryId: message.body.historyId })
```

```javascript
assert.equal( historyResponse.status, 200 )
```

Lists all labels in the user's mailbox

```javascript
labelsResponse = client.userId(USER_ID).labels.get()
```

```javascript
assert.equal( labelsResponse.status, 200 )
```

Creates a new label

```javascript
labelCreateResponse = client.userId(USER_ID).labels.post({
  labelListVisibility: "labelShow",
  messageListVisibility: "show",
  name: "API_Notebook"  
})
```

```javascript
assert.equal( labelCreateResponse.status, 200 )
labelId = labelCreateResponse.body.id
```

Gets the specified label

```javascript
labelResponse = client.userId(USER_ID).labels.id(labelId).get()
```

```javascript
assert.equal( labelResponse.status, 200 )
```

Updates the specified label

```javascript
labelUpdateResponse = client.userId(USER_ID).labels.id(labelId).put({ name: "API_Notebook_upd"})
```

```javascript
assert.equal( labelUpdateResponse.status, 200 )
```

Updates the specified label. This method supports patch semantics

```javascript
labelPatchResponse = client.userId(USER_ID).labels.id(labelId).patch({ name: "API_Notebook_upd2"})
```

```javascript
assert.equal( labelPatchResponse.status, 200 )
```

Immediately and permanently deletes the specified label and removes it from any messages and threads that it is applied to

```javascript
labelDeleteResponse = client.userId(USER_ID).labels.id(labelId).delete()
```

```javascript
assert.equal( labelDeleteResponse.status, 204 )
```

Lists the threads in the user's mailbox

```javascript
threadsResponse = client.userId(USER_ID).threads.get()
```

```javascript
assert.equal( threadsResponse.status, 200 )
```

Gets the specified thread

```javascript
threadResponse = client.userId(USER_ID).threads.id(threadId).get()
```

```javascript
assert.equal( threadResponse.status, 200 )
```

Modifies the labels applied to the thread. This applies to all messages in the thread

```javascript
threadUpdateResponse = client.userId(USER_ID).threads.id(threadId).modify.post({
  "addLabelIds": [
    "SPAM"
  ],
  "removeLabelIds": [
    
  ]
})
```

```javascript
assert.equal( threadUpdateResponse.status, 200 )
```

Moves the specified thread to the trash

```javascript
trashResponse = client.userId(USER_ID).threads.id(threadId).trash.post()
```

```javascript
assert.equal( trashResponse.status, 200 )
```

Removes the specified thread from the trash

```javascript
untrashResponse = client.userId(USER_ID).threads.id(threadId).untrash.post()
```

```javascript
assert.equal( untrashResponse.status, 200 )
```

Immediately and permanently deletes the specified thread. This operation cannot be undone. Prefer 'threads.trash' instead

```javascript
threadDeleteResponse = client.userId(USER_ID).threads.id(threadId).delete()
```

```javascript
assert.equal( threadDeleteResponse.status, 204 )
```