---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6935/preview
apiNotebookVersion: 1.1.66
title: Set user password, change own password
---

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
  "password" : "Please, enter USER PASSWORD.\n\nPress 'cancel' to use default value: " + defaultValuePlaceholder
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
window.alert("We are about to create new agent. You will be prompted to enter username, email and password.\n\nLater on the notebook will ask you to sign in under the created agent.")
```

Prompt for user data

```javascript
userData = promptForData({
  "username" : "TestNotebookAgent",
  "email" : "test.notebook.agent@a.com",
  "password" : "zendesk123!"
})
```

Delete test user which could have been created during earlier notebook launches

```javascript
deleteExistingUser(userData.username,userData.email)
```

Create agent

```javascript
userCreateResponse = tempEndUser = client.users.json.post({
  "user": {
    "name": userData.username,
    "role": "agent",
    "email": userData.email,
    "verified": true
  }
})
ID_USER = userCreateResponse.body.user.id
```

Set user password

An admin can set a user's password only if the setting is enabled under Settings > Security > Global. The setting is off by default.

```javascript
setPasswordResponse = client("/users/{user_id}/password.json",{
  "user_id":ID_USER}).post({
  "password":userData.password
})
```

```javascript
assert.equal(setPasswordResponse.status,200)
```

Reset admin auth token

```javascript
currentTokenResponse = client.oauth.tokens.current.json.get()
client.oauth.tokens.id(currentTokenResponse.body.token.id).json.delete()
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. You must enter credentials of the newly created test agent:\n\n\email: " + userData.email +"\npassword: " + userData.password)
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Change own password.

You can only change your own password. Nobody can change the password of another user because it requires knowing the user's existing password. However, an admin can set a new password for another user without knowing the existing password. See "Set a User's Password" above.

```javascript
changePasswordResponse = client("/users/{user_id}/password.json",{
  "user_id":ID_USER}).put({
  "previous_password":userData.password,
  "password":userData.password + "_new"
})
```

```javascript
assert.equal(changePasswordResponse.status,200)
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. Please, enter your ADMIN credentials.")
```

Reset agent token

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


Garbage collection. Delete the test agent and created tickets.

```javascript
client.users.id(ID_USER).json.delete()
```