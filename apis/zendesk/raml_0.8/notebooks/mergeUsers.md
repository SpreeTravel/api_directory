---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6932/preview
apiNotebookVersion: 1.1.66
title: Merge users
---

This notebook is supposed to be launched with production Zendesk account, not with sandbox.

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
DOMAIN = prompt("Please, enter your Zendesk subdomain.")
CLIENT_ID = prompt("Please, enter ID of your Zendesk client.")
CLIENT_SECRET = prompt("Please, enter Secret of your Zendesk client.")
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8077/versions/8266/definition',{
  baseUriParameters: {
    domain: DOMAIN,
    version: 'v2'
}});
```

```javascript
API.set(client, 'baseUriParameters', {
  domain: DOMAIN,
  version: 'v2'
})
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Auxiliary method for deleting existing users

```javascript
function deleteExistingUser(username,email){
  searhResponse1 = client.users.search.json.get({"query":email})
  if (searhResponse1.body.users.length>0){
    client.users.id(searhResponse1.body.users[0].id).json.delete()
  }
  searhResponse2 = client.users.search.json.get({"query":username})
  if (searhResponse2.body.users.length>0){
    client.users.id(searhResponse2.body.users[0].id).json.delete()
  }
}
```

Auxiliary method for prompting data field

```javascript
function promptForDataField(message,defaultValue){
  var value = prompt(message.replace(defaultValuePlaceholder,defaultValue))
  if(value==null||value.trim().length==0){
    value = defaultValue
	}
  return value
}
```

Messages for prompting user data

```javascript
defaultValuePlaceholder = "#####"
messages = {
  "username" : "Please, enter USERNAME.\n\nPress 'cancel' to use default value: " + defaultValuePlaceholder,
  "email" : "Please, enter USER EMAIL.\n\nPress 'cancel' to use default value: " + defaultValuePlaceholder,
  "password" : "Please, enter USER PASSWORD.\n\nPress 'cancel' to use default value: " + defaultValuePlaceholder,
}
```

Auxiliary method for prompting user data

```javascript
function promptForData(data){
  var result = new Object()
  for(var key in data){
    var message = messages[key]
    var value = promptForDataField(message,data[key])
    result[key] = value
  }
  return result
}
```

```javascript
window.alert("We are about to create two end users which will be merged later on. You will be prompted to enter username, email and password for each of them.\n\nDuring execution Notebook will ask you to sign in under the first end user.")
```

Prompt for user 1 data

```javascript
user1Data = promptForData({
  "username" : "TestNotebookEndUser1",
  "email" : "test.notebook.end.user1@a.com",
  "password" : "zendesk123!"
})
```

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser(user1Data.username,user1Data.email)
```

Create end user 1

```javascript
user1CreateResponse = tempEndUser = client.users.json.post({
  "user": {
    "name": user1Data.username,
    "role": "end-user",
    "email": user1Data.email,
    "verified": true
  }
})
```

```javascript
endUser1Id = user1CreateResponse.body.user.id
```

Set end user 1 password.

```javascript
client("/users/{user_id}/password.json",{
  "user_id":endUser1Id}).post({
  "password":user1Data.password
})
```

Prompt for user 2 data

```javascript
user2Data = promptForData({
  "username" : "TestNotebookEndUser2",
  "email" : "test.notebook.end.user2@a.com",
  "password" : "zendesk123!"
})
```

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser(user2Data.username,user2Data.email)
```

Create end user 2

```javascript
user2CreateResponse = tempEndUser = client.users.json.post({
  "user": {
    "name": user2Data.username,
    "role": "end-user",
    "email": user2Data.email,
    "verified": true
  }
})
```

```javascript
endUser2Id = user2CreateResponse.body.user.id
```

```javascript
client("/users/{user_id}/password.json",{
  "user_id":endUser2Id}).post({
  "password":user2Data.password
})
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. You are expected to enter credentials of your END USER.\n\nemail: " + user1Data.email +"\npassword: " + user1Data.password )
```

Reset admin oauth token.

```javascript
currentTokenResponse = client.oauth.tokens.current.json.get()
client.oauth.tokens.id(currentTokenResponse.body.token.id).json.delete()
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

The current user will be merged into the existing user provided in the params. Users can only merge themselves into another user.
allowed for  [Verified end users]

```javascript
mergeMeUpdateResponse = client.users.me.merge.extension(".json").put({"user": {"password": user2Data.password, "email": user2Data.email}})
```

```javascript
assert.equal( mergeMeUpdateResponse.status, 200 )
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. You must enter your ADMIN credentials now.\n\nAs you are now signed in under END USER, you are not authorized to to reset authorization token via REST. Thus, you have to open " + DOMAIN + ".zendesk.com in your browser and sign out manually.")
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Garbage collection. Delete the created users.

```javascript
client.users.id(endUser1Id).json.delete()
client.users.id(endUser2Id).json.delete()
```