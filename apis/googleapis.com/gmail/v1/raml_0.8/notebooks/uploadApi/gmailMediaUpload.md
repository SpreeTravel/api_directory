---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7974/versions/8138/portal/pages/6794/preview
apiNotebookVersion: 1.1.66
title: GMail media upload
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
// Read about the GMail Upload API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7974/versions/8138/contracts
API.createClient('clientUpload', '/apiplatform/repository/public/organizations/30/apis/7974/versions/8138/definition');
```

```javascript
API.authenticate(clientUpload,"oauth_2_0",{
  clientId: CLIENT_ID,
  clientSecret: CLIENT_SECRET
})
```

Media upload request. Directly inserts a message into only this user's mailbox. Does not send a message

```javascript
messageCreateResponse = clientUpload.userId(USER_ID).messages.post("", {
  query: {
    "uploadType": "media"
  },
  headers:{
  	"Content-Type":"message/rfc822"
}
})
```

```javascript
assert.equal( messageCreateResponse.status, 200 )
```

Media upload request. Sends the specified message to the recipients in the 'To', 'Cc', and 'Bcc' headers

```javascript
messageSendResponse = clientUpload.userId(USER_ID).messages.send.post("", {
  query: {
    "uploadType": "media"
  },
  headers:{
  	"Content-Type":"message/rfc822"
}
})
```

```javascript
assert.equal( messageSendResponse.status, 200 )
```

Let's form a message body

```javascript
messageBody="From: example@example.com\r\n"
messageBody+="To: example2@example.com\r\n"
messageBody+="Subject: As basic as it gets\r\n"
messageBody+="\r\n"
messageBody+="This is the plain text body of the message.  Note the blank line"
```

Media upload request. Directly imports a message into only this user's mailbox, similar to receiving via SMTP. Does not send a message

```javascript
messageImportResponse = clientUpload.userId(USER_ID).messages.import.post( messageBody, {
  query: {
    "uploadType": "media"
  },
  headers:{
  	"Content-Type":"message/rfc822"
}
})
```

```javascript
assert.equal( messageImportResponse.status, 200 )
```

Media upload request. Creates a new draft with the DRAFT label

```javascript
draftCreateResponse = clientUpload.userId(USER_ID).drafts.post("", {
  query: {
    "uploadType": "media"
  },
  headers:{
  	"Content-Type":"message/rfc822"
}
})
```

```javascript
assert.equal( draftCreateResponse.status, 200 )
draftId = draftCreateResponse.body.id
```

Media upload request. Replaces a draft's content

```javascript
draftUpdateResponse = clientUpload.userId(USER_ID).drafts.id(draftId).put("", {
  query: {
    "uploadType": "media"
  },
  headers:{
  	"Content-Type":"message/rfc822"
}
})
```

```javascript
assert.equal( draftUpdateResponse.status, 200 )
```

Media upload request. Sends the specified, existing draft to the recipients in the 'To', 'Cc', and 'Bcc' headers.

```javascript
// NOT SUPPORTED
// draftSendResponse = clientUpload.userId(USER_ID).drafts.send.post( "", {
//   query: {
//     "uploadType": "media"
//   }
// })
```

```javascript
//assert.equal( draftSendResponse.status, 200 )
```