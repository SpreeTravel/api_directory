---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6942/preview
apiNotebookVersion: 1.1.66
title: Voice, ticket metric
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


Creating Greeting

```javascript
greetingsCreateResponse = client.channels.voice.greetings.json.post({
  "greeting" : {
    "name" : "Hello" ,
    "category_id" : 1
  }
})
```

```javascript
assert.equal( greetingsCreateResponse.status, 201 )
ID_MY_GREETING_CATEGORY = greetingsCreateResponse.body.greeting.id
```

Listing Greeting Categories

```javascript
greetingCategoriesResponse = client.channels.voice.greeting_categories.json.get()
```

```javascript
assert.equal( greetingCategoriesResponse.status, 200 )
```

Getting Greeting Category
Allowed For:
Agents

```javascript
greetingCategoryResponse = client.channels.voice.greeting_categories.id("1").json.get()
```

```javascript
assert.equal( greetingCategoryResponse.status, 200 )
```

Listing Greetings

```javascript
greetingsResponse = client.channels.voice.greetings.json.get()
```

```javascript
assert.equal( greetingsResponse.status, 200 )
```

Getting Greeting

```javascript
myGreetingResponse = client.channels.voice.greetings.id(ID_MY_GREETING_CATEGORY).json.get()
```

```javascript
assert.equal( myGreetingResponse.status, 200 )
```

Updating Greeting

```javascript
myGreetingUpdateResponse = client.channels.voice.greetings.id(ID_MY_GREETING_CATEGORY).json.put({
  "greeting" : {
    "name" : "Premium Support"
  }
})
```

```javascript
assert.equal( myGreetingUpdateResponse.status, 200 )
```

Deleting Greeting

```javascript
 myGreetingDeleteResponse = client.channels.voice.greetings.id(ID_MY_GREETING_CATEGORY).json.delete()
```

```javascript
assert.equal(  myGreetingDeleteResponse.status, 200 )
```

Getting Statistics for Current Queue Activity

```javascript
currentQueueActivityResponse = client.channels.voice.stats.current_queue_activity.json.get()
```

```javascript
assert.equal( currentQueueActivityResponse.status, 200 )
```

Getting Statistics for Historical Queue Activity

```javascript
historicalQueueActivityResponse = client.channels.voice.stats.historical_queue_activity.json.get()
```

```javascript
assert.equal( historicalQueueActivityResponse.status, 200 )
```

Getting Statistics for Agents Activity

```javascript
agentsActivityResponse = client.channels.voice.stats.agents_activity.json.get()
```

```javascript
assert.equal( agentsActivityResponse.status, 200 )
ID_AGENT = agentsActivityResponse.body.agents_activity[0].agent_id
```

Getting Availability

```javascript
availabilityResponse = client.channels.voice.availabilities.id(ID_AGENT).json.get()
```

```javascript
assert.equal( availabilityResponse.status, 200 )
```

Updating Availability

```javascript
availabilityUpdateResponse = client.channels.voice.availabilities.id(ID_AGENT).json.put({
  "availability" : {
    "via" : "client" ,
    "available" : true
  }
})
```

```javascript
assert.equal( availabilityUpdateResponse.status, 200 )
```

Delete temporal user if exists

```javascript
deleteExistingUser("Roger Wilce","rof@example.org")
```

This end-point allows you to instruct an agent's browser to open a user's profile.
 Allowed For: [Agents]

Create temporal user

```javascript
tempUserResponse = client.users.json.post({
  "user" : {
    "name" : "Roger Wilce" ,
    "email" : "rof@example.org"
  }
})
```

```javascript
ID_USER = tempUserResponse.body.user.id
```

```javascript
displayCreateResponse = client.channels.voice.agents.agent_id(ID_AGENT).users.user_id(ID_USER).display.json.post()
```

```javascript
assert.equal( displayCreateResponse.status, 200 )
```



Create temporal ticket

```javascript
tempTicketResponse = client.tickets.json.post({
  "ticket" : {
    "subject" : "My printer is on fire!" ,
    "comment" : {
      "body" : "The smoke is very colorful."
    }
  }
})
ID_TICKET = tempTicketResponse.body.ticket.id
```

```javascript

```

This end-point allows you to instruct an agent's browser to open a ticket.
Allowed For: [Agents]

```javascript
displayCreateResponse = client.channels.voice.agents.agent_id(ID_AGENT).tickets.ticket_id(ID_TICKET).display.json.post()
```

```javascript
assert.equal( displayCreateResponse.status, 200 )
```

This end-point allows you to instruct an agent's browser to open a ticket.
 Allowed For: [Agents]

```javascript
ticketsCreateResponse = client.channels.voice.tickets.json.post( {
  "ticket": {
    "via_id": 45,
    "description": "Incoming phone call from: +16617480240",
    "voice_comment": {
      "from": "+16617480240",
      "to": "+16617480123",
      "recording_url": "http://yourdomain.com/recordings/1.mp3",
      "started_at": "2013-07-11 15:31:44 +0000",
      "call_duration": 40,
      "answered_by_id": 28,
      "transcription_text": "The transcription of the call",
      "location": "Dublin, Ireland"
    }
  }
})
```

```javascript
assert.equal( ticketsCreateResponse.status, 201 )
```

Listing Ticket Metrics

```javascript
ticketMetricsResponse = client.ticket_metrics.json.get()
```

```javascript
assert.equal( ticketMetricsResponse.status, 200 )
ID_TICKET_METRIC = ticketMetricsResponse.body.ticket_metrics[0].id
```

Getting Ticket Metrics

```javascript
singleTicketMetricsResponse = client.ticket_metrics.id(ID_TICKET_METRIC).json.get()
```

```javascript
assert.equal( singleTicketMetricsResponse.status, 200 )
```

Delete temporal user and ticket

```javascript
client.users.id(ID_USER).json.delete()
```

```javascript
client.tickets.id(ID_TICKET).json.delete()
```