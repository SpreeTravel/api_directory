---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7881/versions/8035/portal/pages/6648/preview
apiNotebookVersion: 1.1.66
title: Messages and Drafts
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

Rfc822 encoded message content which will be used to create messages

```javascript
RAW_MESSAGE = "TUlNRS1WZXJzaW9uOiAxLjANClJlY2VpdmVkOiBieSAxMC42NC45LjIzMiB3aXRoIEhUVFA7IEZyaSwgMjcgSnVuIDIwMTQgMTE6NTM6MzIgLTA3MDAgKFBEVCkNCkRhdGU6IFNhdCwgMjggSnVuIDIwMTQgMDE6NTM6MzIgKzA3MDANCkRlbGl2ZXJlZC1Ubzoga29uc3RhbnRpbi5zdmlyaWRvdkBvbnBvc2l0aXZlLmNvbQ0KTWVzc2FnZS1JRDogPENBTmlzN3d6eTZPVExVYjRGLVBPVjhhckJKVnh5OU9EX3JFOXVQS21FSFF1QjFmRDVDQUBtYWlsLmdtYWlsLmNvbT4NClN1YmplY3Q6IEdNYWlsIE5vdGVib29rc1Rlc3QgTWVzc2FnZQ0KRnJvbTogS29uc3RhbnRpbiBTdmlyaWRvdiA8a29uc3RhbnRpbi5zdmlyaWRvdkBvbnBvc2l0aXZlLmNvbT4NClRvOiBLb25zdGFudGluIFN2aXJpZG92IDxrb25zdGFudGluLnN2aXJpZG92QG9ucG9zaXRpdmUuY29tPg0KQ29udGVudC1UeXBlOiBtdWx0aXBhcnQvbWl4ZWQ7IGJvdW5kYXJ5PTAwMWExMWMzNTlmMDZlMzU5ZjA0ZmNkNWQzNDcNCg0KLS0wMDFhMTFjMzU5ZjA2ZTM1OWYwNGZjZDVkMzQ3DQpDb250ZW50LVR5cGU6IG11bHRpcGFydC9hbHRlcm5hdGl2ZTsgYm91bmRhcnk9MDAxYTExYzM1OWYwNmUzNThiMDRmY2Q1ZDM0NQ0KDQotLTAwMWExMWMzNTlmMDZlMzU4YjA0ZmNkNWQzNDUNCkNvbnRlbnQtVHlwZTogdGV4dC9wbGFpbjsgY2hhcnNldD1VVEYtOA0KDQpUaGlzIG1lc3NhZ2UgaXMgaW50ZW5kZWQgZm9yIHRlc3RzIHdpdGggR01haWwgQVBJIE5vdGVib29rcy4NCg0KLS0wMDFhMTFjMzU5ZjA2ZTM1OGIwNGZjZDVkMzQ1DQpDb250ZW50LVR5cGU6IHRleHQvaHRtbDsgY2hhcnNldD1VVEYtOA0KDQo8ZGl2IGRpcj0ibHRyIj48c3BhbiBzdHlsZT0iZm9udC1mYW1pbHk6YXJpYWwsc2Fucy1zZXJpZjtmb250LXNpemU6MTNweCI-VGhpcyBtZXNzYWdlIGlzIGludGVuZGVkIGZvciB0ZXN0cyB3aXRoIEdNYWlsIEFQSSBOb3RlYm9va3MuPC9zcGFuPjxicj48L2Rpdj4NCg0KLS0wMDFhMTFjMzU5ZjA2ZTM1OGIwNGZjZDVkMzQ1LS0NCg0KLS0wMDFhMTFjMzU5ZjA2ZTM1OWYwNGZjZDVkMzQ3DQpDb250ZW50LVR5cGU6IHRleHQvcGxhaW47IGNoYXJzZXQ9VVMtQVNDSUk7IG5hbWU9ImF0dGFjaG1lbnQudHh0Ig0KQ29udGVudC1EaXNwb3NpdGlvbjogYXR0YWNobWVudDsgZmlsZW5hbWU9ImF0dGFjaG1lbnQudHh0Ig0KQ29udGVudC1UcmFuc2Zlci1FbmNvZGluZzogYmFzZTY0DQpYLUF0dGFjaG1lbnQtSWQ6IGZfaHd4dXlieW0wDQoNClZHaHBjeUJ0WlhOellXZGxJR2x6SUdsdWRHVnVaR1ZrSUdadmNpQjBaWE4wY3lCM2FYUm9JRWROWVdsc0lFRlFTU0JPYjNSbFltOXYNCmEzTXUNCi0tMDAxYTExYzM1OWYwNmUzNTlmMDRmY2Q1ZDM0Ny0tDQo="
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

Lists the messages in the user's mailbox

```javascript
messagesResponse = client.userId(USER_ID).messages.get()
```

```javascript
assert.equal( messagesResponse.status, 200 )
```

Metadata-only request. Sends the specified message to the recipients in the 'To', 'Cc', and 'Bcc' headers

```javascript
messageSendResponse = client.userId(USER_ID).messages.send.post({
  "raw": RAW_MESSAGE
}, {
  query: {
    "uploadType": "multipart"
  }
})
```

```javascript
assert.equal( messageSendResponse.status, 200 )
messageId = messageSendResponse.body.id
```

Metadata-only request. Directly inserts a message into only this user's mailbox. Does not send a message

```javascript
messageInsertResponse = client.userId(USER_ID).messages.post({
  "raw": RAW_MESSAGE
}, {
  query: {
    "uploadType": "resumable"
  }
})
```

```javascript
assert.equal( messageInsertResponse.status, 200 )
```

Gets the specified message

```javascript
messageResponse = client.userId(USER_ID).messages.messageId(messageId).get()
```

```javascript
assert.equal( messageResponse.status, 200 )
```

Metadata-only request. Directly imports a message into only this user's mailbox, similar to receiving via SMTP. Does not send a message

```javascript
messageImportResponse = client.userId(USER_ID).messages.import.post({
  raw: RAW_MESSAGE
} , {
  query: {
    "uploadType": "multipart"
  }
})
```

```javascript
assert.equal( messageImportResponse.status, 200 )
```

Modifies the labels on the specified message

```javascript
modifyResponse = client.userId(USER_ID).messages.messageId(messageId).modify.post({
  "addLabelIds": [ "TRASH" ],
  "removeLabelIds": [ ]
})
```

```javascript
assert.equal( modifyResponse.status, 200 )
```

Removes the specified message from the trash

```javascript
untrashResponse = client.userId(USER_ID).messages.messageId(messageId).untrash.post()
```

```javascript
assert.equal( untrashResponse.status, 200 )
```

Moves the specified message to the trash

```javascript
trashResponse = client.userId(USER_ID).messages.messageId(messageId).trash.post()
```

```javascript
assert.equal( trashResponse.status, 200 )
```

Extract attachment ID

```javascript
attachmentId = null
messageParts = messageResponse.body.payload.parts
for(var i in messageParts){
  var part = messageParts[i]
  var body = part.body
  if(body && body.attachmentId){
    attachmentId = body.attachmentId
    break
  }  
}
```

Gets the specified message attachment

```javascript
attachmentResponse = client.userId(USER_ID).messages.messageId(messageId).attachments.id(attachmentId).get()
```

```javascript
assert.equal( attachmentResponse.status, 200 )
```

Lists the drafts in the user's mailbox

```javascript
draftsResponse = client.userId(USER_ID).drafts.get()
```

```javascript
assert.equal( draftsResponse.status, 200 )
```

Metadata-only request. Creates a new draft with the DRAFT label

```javascript
draftCreateResponse = client.userId(USER_ID).drafts.post({
  message:{
    raw: RAW_MESSAGE
  }  
}, {
  query: {
    "uploadType": "multipart"
  }
})
```

```javascript
assert.equal( draftCreateResponse.status, 200 )
draftId = draftCreateResponse.body.id
```

Gets the specified draft

```javascript
draftResponse = client.userId(USER_ID).drafts.id(draftId).get()
```

```javascript
assert.equal( draftResponse.status, 200 )
```

Metadata-only request. Replaces a draft's content

```javascript
draftUpdateResponse = client.userId(USER_ID).drafts.id(draftId).put({
  message:{
    raw: RAW_MESSAGE
  }  
} , {
  query: {
    "uploadType": "multipart"
  }
})
```

```javascript
assert.equal( draftUpdateResponse.status, 200 )
```

Metadata-only request. Sends the specified, existing draft to the recipients in the 'To', 'Cc', and 'Bcc' headers

```javascript
draftSendResponse = client.userId(USER_ID).drafts.send.post({
  id: draftId
} , {
  query: {
    "uploadType": "multipart"
  }
})
```

```javascript
assert.equal( draftSendResponse.status, 200 )
```

We need another draft in order to test the DELETE method

```javascript
draftCreateResponse2 = client.userId(USER_ID).drafts.post({
  message:{
    raw: RAW_MESSAGE
  }  
}, {
  query: {
    "uploadType": "multipart"
  }
})
draftId2 = draftCreateResponse2.body.id
```

Immediately and permanently deletes the specified draft. Does not simply trash it

```javascript
draftDeleteResponse = client.userId(USER_ID).drafts.id(draftId2).delete()
```

```javascript
assert.equal( draftDeleteResponse.status, 204 )
```

Immediately and permanently deletes the specified message. This operation cannot be undone. Prefer 'messages.trash' instead

```javascript
messageDeleteResponse = client.userId(USER_ID).messages.messageId(messageId).delete()
```

```javascript
assert.equal( messageDeleteResponse.status, 204 )
```