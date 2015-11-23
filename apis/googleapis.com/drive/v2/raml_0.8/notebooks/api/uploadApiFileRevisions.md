---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/12468/versions/12920/portal/pages/13791/preview
apiNotebookVersion: 1.1.66
title: Upload API, File revisions
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
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/12468/versions/12920/definition');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

```javascript
API.createClient('driveClient', '/apiplatform/repository/public/organizations/30/apis/12164/versions/12574/definition');
```

```javascript
API.authenticate(driveClient,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Base64 encoded ZIP archive

```javascript
base64Content="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"
```

Name of archive

```javascript
fileName = "sample.zip"
```

Here we form payload for file creation POST request

```javascript
byteCharacters = atob(base64Content)
byteNumbers = new Array(byteCharacters.length);
for (var i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
}
byteArray = new Uint8Array(byteNumbers);
byteArray.length
formData = new FormData()
formData.append("uploaded_data", new Blob([byteArray]), fileName)
```

Insert a new file

```javascript
filesCreateResponse = client.files.post(
  formData, {"query":{
  "uploadType": "media"
  }
})
```

```javascript
assert.equal( filesCreateResponse.status, 200 )
ID_FILE = filesCreateResponse.body.id
```

Updates file metadata and/or content

```javascript
fileUpdateResponse = client.files.fileId(ID_FILE).put(
  formData, {"query":{
  "uploadType": "media"
  }
})
```

```javascript
assert.equal( fileUpdateResponse.status, 200 )
```

```javascript
driveClient.files.fileId(ID_FILE).realtime.get()
```

Overwrites the Realtime API data model associated with this file with the provided JSON data model.

This method supports an /upload URI and accepts uploaded media with the following characteristics:

Maximum file size: 10MB
Accepted Media MIME types: */*

If successful, this method returns an empty response body.

```javascript
realtimeUpdateResponse = client.files.fileId(ID_FILE).realtime.put({},{"query":{
  "uploadType": "media"
  }
})
```

```javascript
assert.equal( realtimeUpdateResponse.status, 200 )
```



Lists a file's revisions

```javascript
revisionsResponse = driveClient.files.fileId(ID_FILE).revisions.get()
```

```javascript
assert.equal( revisionsResponse.status, 200 )
ID_FILE_REVISION = revisionsResponse.body.items[0].id
```

Gets a specific revision

```javascript
revisionResponse = driveClient.files.fileId(ID_FILE).revisions.revisionId(ID_FILE_REVISION).get()
```

```javascript
assert.equal( revisionResponse.status, 200 )
```

Updates a revision

```javascript
revisionUpdateResponse = driveClient.files.fileId(ID_FILE).revisions.revisionId(ID_FILE_REVISION).put({
  "pinned" : false ,
  "publishAuto" : true ,
  "publishedOutsideDomain" : false
})
```

```javascript
assert.equal( revisionUpdateResponse.status, 200 )
```

Updates a revision. This method supports patch semantics

```javascript
revisionPatchResponse = driveClient.files.fileId(ID_FILE).revisions.revisionId(ID_FILE_REVISION).patch({
  "pinned" : true
})
```

```javascript
assert.equal( revisionPatchResponse.status, 200 )
```

Removes a revision

```javascript
revisionDeleteResponse = driveClient.files.fileId(ID_FILE).revisions.revisionId(ID_FILE_REVISION).delete()
```

```javascript
assert.equal( revisionDeleteResponse.status, 204 )
```

Delete test file

```javascript
driveClient.files.fileId(ID_FILE).delete()
```