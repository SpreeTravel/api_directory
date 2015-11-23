---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6939/preview
apiNotebookVersion: 1.1.66
title: Zendesk targets, problems, exports, import
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

Create Target

```javascript
targetsCreateResponse = client.targets.json.post({
  "target": {
    "type": "email_target",
    "title": "Test Email Target",
    "email": "hello@example.com",
    "subject": "Test Target"
  }
})
```

```javascript
assert.equal(targetsCreateResponse.status, 201)
ID_TARGET = targetsCreateResponse.body.target.id
```

List Targets

```javascript
targetsResponse = client.targets.json.get()
```

```javascript
assert.equal( targetsResponse.status, 200 )
```

Show Target

```javascript
singleTargetResponse = client.targets.id(ID_TARGET).json.get()
```

```javascript
assert.equal( singleTargetResponse.status, 200 )
```

Update Target

```javascript
singleTargetUpdateResponse = client.targets.id(ID_TARGET).json.put({"target": {"email": "roger@example.com"}})
```

```javascript
assert.equal(singleTargetUpdateResponse.status, 200 )
```

Delete Target

```javascript
singleTargetDeleteResponse = client.targets.id(ID_TARGET).json.delete()
```

```javascript
assert.equal( singleTargetDeleteResponse.status, 200 )
```

Create ticket problem.

```javascript
autocompleteCreateResponse = client.problems.autocomplete.json.post({"text": "att"})
```

```javascript
assert.equal( autocompleteCreateResponse.status, 200 )
```

Retrieve problems

```javascript
problemsResponse = client.problems.json.get()
```

```javascript
assert.equal( problemsResponse.status, 200 )
```

Incremental Ticket Export

```javascript
ticketsResponse = client.exports.tickets.json.get({"start_time":1332034771})
```

```javascript
assert.equal( ticketsResponse.status, 200 )
```

This endpoint is meant to be used only for testing the incremental export format. It's more relaxed in terms of rate limiting, but will only return up to 50 records. Other than this, it's identical to the above API

```javascript
sampleResponse = client.exports.tickets.sample.json.get({"start_time":1332034771})
```

```javascript
assert.equal( sampleResponse.status, 200 )
```

Create Ticket Field

```javascript
ticketFieldsCreateResponse = client.ticket_fields.json.post({
  "ticket_field": {
    "type": "text", 
    "title": "Age"
  }
})
```

```javascript
assert.equal( ticketFieldsCreateResponse.status, 201 )
ID_TICKET_FIELD = ticketFieldsCreateResponse.body.ticket_field.id
```

Returns a list of all ticket fields in your account. Fields are returned in the order that you specify in your Ticket Fields configuration in Zendesk. Clients should cache this resource for the duration of their API usage and map the id for each ticket field to the values returned under the fields attributes on the Ticket resource.
 Allowed For: [Agents]

```javascript
ticketFieldsResponse = client.ticket_fields.json.get()
```

```javascript
assert.equal( ticketFieldsResponse.status, 200 )
```

Returns a list of all ticket fields in your account. Fields are returned in the order that you specify in your Ticket Fields configuration in Zendesk. Clients should cache this resource for the duration of their API usage and map the id for each ticket field to the values returned under the fields attributes on the Ticket resource.
 Allowed For: [Agents]

```javascript
singleFieldResponse = client.ticket_fields.id(ID_TICKET_FIELD).json.get()
```

```javascript
assert.equal(singleFieldResponse.status, 200 )
```

Update ticket field

```javascript
singleFieldUpdateResponse = client.ticket_fields.id(ID_TICKET_FIELD).json.put()
```

```javascript
assert.equal( singleFieldUpdateResponse.status, 200 )
```

Delete ticket field

```javascript
singleFieldDeleteResponse = client.ticket_fields.id(ID_TICKET_FIELD).json.delete()
```

```javascript
assert.equal( singleFieldDeleteResponse.status, 200 )
```

Ticket Import

```javascript
ticketsCreateResponse = client.imports.tickets.json.post({
  "ticket" : {
    "subject" : "Help" ,
    "comments" : [
      {
        "author_id" : 19 ,
        "value" : "This is a comment"
      }
    ]
  }
})
```

```javascript
assert.equal( ticketsCreateResponse.status, 201 )
```

Delete ticket import

```javascript
client.tickets.id(ticketsCreateResponse.body.ticket.id).json.delete()
```

Create temporary forum

```javascript
tempForum = client.forums.json.post({
  "forum" : {
    "name" : "My Forum" ,
    "forum_type" : "articles" ,
    "access" : "logged-in users"
  }
})
```

```javascript
ID_FORUM = tempForum.body.forum.id
```

Creates a topic without sending out notifications. Allows setting created_at and updated_at.
 Allowed For: [Admins]

```javascript
topicsCreateResponse = client.import.topics.json.post({
  "topic" : {
    "forum_id" : ID_FORUM ,
    "title" : "My Topic" ,
    "body" : "This is a test topic."
  }
})
```

```javascript
assert.equal( topicsCreateResponse.status, 201 )
ID_TOPIC = topicsCreateResponse.body.topic.id
```

Creates a comment without sending out notifications. Allows setting created_at and updated_at.
 Allowed For: [Admins]

```javascript
commentsCreateResponse = client.import.topics.id(ID_TOPIC).comments.json.post({
  "topic_comment" : {
    "body" : "A man walks into a bar"
  }
})
```

```javascript
assert.equal( commentsCreateResponse.status, 201 )
ID_TOPIC_COMMENT = commentsCreateResponse.body.topic_comment.id
```

Delete user that could have been created during earlier notebook runs.

```javascript
deleteExistingUser("Roger Wilco","poge@example.org")
```

Create temporal user

```javascript
tempUser = client.users.json.post({
  "user" : {
    "name" : "Roger Wilco" ,
    "email" : "poge@example.org"
  }
})
ID_USER = tempUser.body.user.id
```

Create topic subscription

```javascript
topicSubscriptionsCreateResponse = client.topic_subscriptions.json.post({
  "topic_subscription" : {
    "user_id" : ID_USER ,
    "topic_id" : ID_TOPIC
  }
})
```

```javascript
assert.equal( topicSubscriptionsCreateResponse.status, 201 )
ID_SUBSCRIPTION = topicSubscriptionsCreateResponse.body.topic_subscription.id
```

List topic subscription

```javascript
topicSubscriptionsResponse = client.topic_subscriptions.json.get()
```

```javascript
assert.equal( topicSubscriptionsResponse.status, 200 )
```


Show topic subscription

```javascript
subscriptionResponse = client.topic_subscriptions.id(ID_SUBSCRIPTION).json.get()
```

```javascript
assert.equal( subscriptionResponse.status, 200 )
```

Delete topic subscription

```javascript
subscriptionDeleteResponse = client.topic_subscriptions.id(ID_SUBSCRIPTION).json.delete()
```

```javascript
assert.equal( subscriptionDeleteResponse.status, 200 )
```

Lists all triggers for the current account

```javascript
triggersResponse = client.triggers.json.get()
```

```javascript
assert.equal( triggersResponse.status, 200 )
```

Delete a trigger which could have been created during earlier notebook launches

```javascript
for(var ind in triggersResponse.body.triggers){
  var trigger = triggersResponse.body.triggers[ind]
  var title = trigger.title
  if( title == "roger wilco 1"){
    client.triggers.id(trigger.id).json.delete()
  }
}
```

Create trigger

```javascript
triggerCreateResponse = client.triggers.json.post({
  "trigger":{
    "title":"roger wilco 1",
    "conditions": {
      "all": [
        { 
           "field": "priority", 
           "operator": "less_than", 
           "value": "high" 
         }
       ],
    },
    "actions" : [
      { 
        "field": "group_id", 
        "value": "200" 
      }
    ]
  }
})
```

```javascript
assert.equal( triggerCreateResponse.status, 201 )
ID_TRIGGER = triggerCreateResponse.body.trigger.id
```

Lists all triggers for the current account

```javascript
singleTriggerResponse = client.triggers.id(ID_TRIGGER).json.get()
```

```javascript
assert.equal( singleTriggerResponse.status, 200 )
```

Update trigger
 allowed for: [agents]

```javascript
triggerUpdateResponse = client.triggers.id(ID_TRIGGER).json.put()
```

```javascript
assert.equal( triggerUpdateResponse.status, 200 )
```

Lists all active triggers
Allowed for: [agents]

```javascript
activeTriggersResponse = client.triggers.active.json.get()
```

```javascript
assert.equal( activeTriggersResponse.status, 200 )
```

Let's store list of active triggers IDs. This method expects a complete set of active trigger IDs ordered in some way. A complete list of active triggers can be obtained via `/triggers/active.json` endpoint.

```javascript
triggerIDs=[]
activeTriggers = activeTriggersResponse.body.triggers
for(var ind in activeTriggers){
  var t = activeTriggers[ind]
  triggerIDs.push(t.id)
}
```

Reorder triggers

```javascript
reorderResponse = client.triggers.reorder.json.put({
  "trigger_ids" : triggerIDs
})
```

```javascript
assert.equal( reorderResponse.status, 200 )
```

Delete trigger

```javascript
triggerDeleteResponse = client.triggers.id(ID_TRIGGER).json.delete()
```

```javascript
assert.equal( triggerDeleteResponse.status, 200 )
```

Garbage collection. Delete topic, forums and user.

```javascript
client.topics.id(ID_TOPIC).json.delete()
client("/forums/{id}{mediaTypeExtension}", {
  "id": ID_FORUM,
  "mediaTypeExtension": ".json"
}).delete()
client.users.id(ID_USER).json.delete()
```