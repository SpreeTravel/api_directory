---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6938/preview
apiNotebookVersion: 1.1.66
title: Suspended ticket, mark as spam, activities
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

```javascript
setPasswordResponse = client("/users/{user_id}/password.json",{
  "user_id":ID_USER}).post({
  "password":userData.password
})
```

We need two tickets assigned to the new user

```javascript
ticketCreateResponse = client.tickets.json.post({
  "ticket":	{
    "assignee_id" : ID_USER,
    "subject":"API Notebook test ticket. Testing ticket suspending and activities.", 
    "description":"API Notebook test ticket. Testing ticket suspending and activities."
  }
})
ticket1Id = ticketCreateResponse.body.ticket.id
```

```javascript
ticket2CreateResponse = client.tickets.json.post({
  "ticket":	{
    "assignee_id" : ID_USER,
    "subject":"API Notebook test ticket 1. Testing ticket suspending and activities.", 
    "description":"API Notebook test ticket 2. Testing ticket suspending and activities."
  }
})
ticket2Id = ticket2CreateResponse.body.ticket.id
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

List Activities

```javascript
activitiesResponse = client.activities.json.get()
```

```javascript
assert.equal(activitiesResponse.status,200)
activityId = activitiesResponse.body.activities[0].id
```

Show Activity

```javascript
activityResponse = client.activities.id(activityId).json.get()
```

```javascript
assert.equal(activityResponse.status,200)
```

Create ticket

```javascript
ticket3CreateResponse = client.tickets.json.post({
  "ticket":	{
    "subject":"API Notebook test ticket 3. This ticket will be marked as spam.", 
    "description":"API Notebook test ticket. This ticket will be marked as spam." 
  }
})
ticket3Id = ticket3CreateResponse.body.ticket.id
```

Reset agent token

```javascript
currentTokenResponse = client.oauth.tokens.current.json.get()
client.oauth.tokens.id(currentTokenResponse.body.token.id).json.delete()
```

```javascript
window.alert("Now you will be prompted to authorize the client once again. Please, enter your ADMIN credentials.")
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Accepts a comma-separated list of up to 100 ticket ids.
 Allowed For: [Agents]

```javascript
markAsSpamUpdateResponse = client("/tickets/{id}/mark_as_spam{mediaTypeExtension}", {
  "id": ticket3Id,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( markAsSpamUpdateResponse.status, 200 )
```

Listing Suspended Tickets

```javascript
suspendedTicketsResponse = client.suspended_tickets.json.get()
```

```javascript
assert.equal( suspendedTicketsResponse.status, 200 )

```

Select two suspended tickets assigned to the test agent with fake email

```javascript
ID_SUSPEND_TICKET = null
ID_RECOVER_TICKET = null
suspendedTickets = suspendedTicketsResponse.body.suspended_tickets
for(var ind in suspendedTickets){
  var st = suspendedTickets[ind]
  if(st.content.indexOf(userData.email)>0){
    if(ID_SUSPEND_TICKET==null){
      ID_SUSPEND_TICKET = st.id
    }
    else if(ID_RECOVER_TICKET==null){
      ID_RECOVER_TICKET = st.id
      break
    }
  }
}
```

```javascript
suspendedTicketsResponse.body.suspended_tickets[0].content
```

Getting Suspended Ticket

```javascript
suspendTicketResponse = client("/suspended_tickets/{id}{mediaTypeExtension}", {
  "id": ID_SUSPEND_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( suspendTicketResponse.status, 200 )
```

Note: During recovery, the API sets the requester to the authenticated agent who called the API, not the original requester. This prevents the ticket from being re-suspended after recovery. To preserve the original requester, GET the suspended ticket before recovery and grab the author value.
 Allowed For: [Unrestricted Agents]

```javascript
recoverUpdateResponse = client.suspended_tickets.id(ID_RECOVER_TICKET).recover.json.put()
```

```javascript
assert.equal( recoverUpdateResponse.status, 200 )
```

Delete suspended ticket

```javascript
ticketDeleteResponse = client("/suspended_tickets/{id}{mediaTypeExtension}", {
  "id":ID_SUSPEND_TICKET,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( ticketDeleteResponse.status, 200 )
```

Suspended tickets that fail to be recovered will be included in the response.
Allowed For: [Unrestricted Agents]

```javascript
recoverManyUpdateResponse = client.suspended_tickets.recover_many.json.put()
```

```javascript
assert.equal( recoverManyUpdateResponse.status, 200 )
```

Note: Suspended tickets that fail to be recovered will be included in the response.
 Allowed For: [Unrestricted Agents]

```javascript
destroyManyDeleteResponse = client.suspended_tickets.destroy_many.json.delete()
```

```javascript
assert.equal( destroyManyDeleteResponse.status, 200 )
```

Garbage collection. Delete the test agent and created tickets.

```javascript
client.users.id(ID_USER).json.delete()
client.tickets.id(ticket1Id).json.delete()
client.tickets.id(ticket2Id).json.delete()
```