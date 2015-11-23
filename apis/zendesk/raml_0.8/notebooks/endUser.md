---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6934/preview
apiNotebookVersion: 1.1.66
title: End user
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
  "identity" : "We are about to add identity to the end user.\nPlease, enter email.\nPress 'cancel' to use default value: " + defaultValuePlaceholder
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
window.alert("We are about to create end user. You will be prompted to enter username, email and password.\n\nLater on the notebook will ask you to sign in under the created end user.")
```

Prompt for user data

```javascript
userData = promptForData({
  "username" : "TestNotebookEndUser",
  "email" : "test.notebook.end.user@a.com",
  "password" : "zendesk123!"
})
```

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser(userData.username,userData.email)
```

Create end user

```javascript
userCreateResponse = tempEndUser = client.users.json.post({
  "user": {
    "name": userData.username,
    "role": "end-user",
    "email": userData.email,
    "verified": true
  }
})
```

```javascript
endUserId = userCreateResponse.body.user.id
```

Set end user password.
An admin can set a user's password only if the setting is enabled under Settings > Security > Global. The setting is off by default.

```javascript
setPasswordResponse = client("/users/{user_id}/password.json",{
  "user_id":endUserId}).post({
  "password": userData.password
})
```

```javascript
assert.equal(setPasswordResponse.status,200)
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. You are expected to enter credentials of your END USER.\n\nemail: " + userData.email +"\npassword: " + userData.password )
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

Create request

```javascript
requestsCreateResponse = client.requests.json.post({
  "request": {
    "subject": "API Notebook test request",
    "comment": {
      "body": "API Notebook test request. End users test."
    }
  }
})
requestId = requestsCreateResponse.body.request.id
```

```javascript
IDENTITY_EMAIL = promptForDataField(messages["identity"],"test.identity@a.com")
```

Add new identities for a given user id. An agent can add an identity to any user profile. An end-user can only add an identity to their own user profile.
Allowed For: [Agents, Verified end-users]

```javascript
endUserIdentitiesCreateResponse = client.end_users.user_id(endUserId).identities.json.post({
  "identity": {
    "type": "email",
    "value": IDENTITY_EMAIL
  }
})
assert.equal( endUserIdentitiesCreateResponse.status, 201 )
ID_IDENTITY = endUserIdentitiesCreateResponse.body.identity.id
```

Show end-user

```javascript
endUserResponse = client.end_users.id(endUserId).json.get()
```

```javascript
assert.equal( endUserResponse.status, 200 )
```

Update end-user

```javascript
endUserUpdateResponse = client.end_users.id(endUserId).json.put()
```

```javascript
assert.equal( endUserUpdateResponse.status, 200 )
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

Resolve end users request

```javascript
client.tickets.id(requestId).json.put({
  "ticket":{
    "status":"solved"
  }
})
```

Retrieve identities of the user

```javascript
identitiesResponse = client("/users/{user_id}/identities{mediaTypeExtension}", {
  "user_id": endUserId,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal(identitiesResponse.status,200)
```

Delete identity

```javascript
deleteIdentityResponse = client("/users/{user_id}/identities/{id}{mediaTypeExtension}", {
  "user_id": endUserId,
  "id": ID_IDENTITY,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal(deleteIdentityResponse.status,200)
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. You are expected to enter credentials of your END USER.\n\nemail: " + userData.email +"\npassword: " + userData.password )
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

Create a Satisfaction Rating

```javascript
satisfactionCreateResponse = client("/tickets/{id}/satisfaction_rating{mediaTypeExtension}", {
  "id": requestId,
  "mediaTypeExtension": ".json"
}).post({
  "satisfaction_rating": {
    "score": "good", 
    "comment": "API Notebook test satisfaction rating."
  }
})
```

```javascript
assert.equal(satisfactionCreateResponse.status,200)
staisfactionId = satisfactionCreateResponse.body.satisfaction_rating.id
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

Lists all received satisfaction rating requests ever issued for your account. To only list the satisfaction ratings submitted by your customers, use the "received" end point below instead

```javascript
satisfactionRatingsResponse = client.satisfaction_ratings.json.get()
```

```javascript
assert.equal( satisfactionRatingsResponse.status, 200 )
```

Show satisfaction rating

```javascript
singleRatingResponse = client.satisfaction_ratings.id(staisfactionId).json.get()
```

```javascript
assert.equal( singleRatingResponse.status, 200 )
```

Garbage collection. Delete created end-user and request.

```javascript
client.tickets.id(requestId).json.delete()
client.users.id(endUserId).json.delete()
