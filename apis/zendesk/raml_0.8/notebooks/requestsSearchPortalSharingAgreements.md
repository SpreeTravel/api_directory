---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6937/preview
apiNotebookVersion: 1.1.66
title: Requests, search, portal, sharing agreements
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

Creating Request

```javascript
requestsCreateResponse = client.requests.json.post({
  "request": {
    "subject": "API Notebook test request",
    "comment": {
      "body": "API Notebook test request."
     }
  }
})
```

```javascript
assert.equal( requestsCreateResponse.status, 201 )
ID_REQUEST = requestsCreateResponse.body.request.id
```

Listing request

```javascript
requestsResponse = client.requests.json.get()
```

```javascript
assert.equal( requestsResponse.status, 200 )
```

List open requests

```javascript
openResponse = client.requests.open.json.get()
```

```javascript
assert.equal( openResponse.status, 200 )
```

Listing solved requests

```javascript
solvedResponse = client.requests.solved.json.get()
```

```javascript
assert.equal( solvedResponse.status, 200 )
```

Listing ccd requests

```javascript
ccdResponse = client.requests.ccd.json.get()
```

```javascript
assert.equal( ccdResponse.status, 200 )
```

Searching requests

```javascript
searchResponse = client.requests.search.json.get()
```

```javascript
assert.equal( searchResponse.status, 200 )
```

Getting Request

```javascript
singleRequestResponse = client("/requests/{id}{mediaTypeExtension}", {
  "id": ID_REQUEST,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleRequestResponse.status, 200 )
```

Updating Request

```javascript
singleRequestUpdateResponse = client("/requests/{id}{mediaTypeExtension}", {
  "id": ID_REQUEST,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal(singleRequestUpdateResponse.status, 200 )
```

Listing Comments

```javascript
commentsResponse = client.requests.id(ID_REQUEST).comments.json.get()
```

```javascript
assert.equal( commentsResponse.status, 200 )
ID_COMMENT = commentsResponse.body.comments[0].id
```

Getting Comment

```javascript
idResponse = client.requests.id(ID_REQUEST).comments.id(ID_COMMENT).json.get()
```

```javascript
assert.equal( idResponse.status, 200 )
```


Search

```javascript
searchResponse = client.search.json.get({query:""})
```

```javascript
assert.equal( searchResponse.status, 200 )
```

Portal search

```javascript
portalSearchResponse = client.portal.search.json.get({query:""})
```

```javascript
assert.equal( portalSearchResponse.status, 200 )
```

Create Recipient Addresses

```javascript
recipientAddressesCreateResponse = client.recipient_addresses.json.post({
  "recipient_address": {
    "name": "Sales", 
    "email": "name@domain.com", 
    "default": false 
  }
})
```

```javascript
assert.equal( recipientAddressesCreateResponse.status, 201 )
ID_RECIPIENT_ADDRESS = recipientAddressesCreateResponse.body.recipient_address.id
```

List Sharing Agreements

```javascript
sharingAgreementsResponse = client.sharing_agreements.json.get()
```

```javascript
assert.equal( sharingAgreementsResponse.status, 200 )
```

Listing Recipient Addresses

```javascript
recipientAddressesResponse = client.recipient_addresses.json.get()
```

```javascript
assert.equal( recipientAddressesResponse.status, 200 )
```

Show Recipient Address

```javascript
addressResponse = client("/recipient_addresses/{id}{mediaTypeExtension}", {
  "id": ID_RECIPIENT_ADDRESS,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( addressResponse.status, 200 )
```

Update Recipient Address

```javascript
addressUpdateResponse = client("/recipient_addresses/{id}{mediaTypeExtension}", {
  "id": ID_RECIPIENT_ADDRESS,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( addressUpdateResponse.status, 200 )
```


Verify Recipient Address

```javascript
verifyUpdateResponse = client.recipient_addresses.id(ID_RECIPIENT_ADDRESS).verify.json.put({
  "type": "forwarding"
})
```

```javascript
assert.equal( verifyUpdateResponse.status, 200 )
```

Delete Recipient Address

```javascript
addressDeleteResponse = client("/recipient_addresses/{id}{mediaTypeExtension}", {
  "id": ID_RECIPIENT_ADDRESS,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( addressDeleteResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsResponse = client.tags.json.get()
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Create temporary forum

```javascript
tempForum = client.forums.json.post({
  "forum" : {
    "name" : "My topics Forum" ,
    "forum_type" : "articles" ,
    "access" : "logged-in users"
  }
})
ID_FORUM = tempForum.body.forum.id
```

Create Topic

```javascript
topicsCreateResponse = client.topics.json.post({
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

List Topics

```javascript
topicsListResponse = client.topics.json.get()
```

```javascript
assert.equal( topicsListResponse.status, 200 )
```

Create topic tag

```javascript
tagsCreateResponse = clienttagsResponse = client("/topics/{id}/tags{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).post({ "tags": ["importantTopic"] })
```

```javascript
assert.equal( tagsCreateResponse.status, 201 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsResponse = client("/topics/{id}/tags{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsUpdateResponse = client("/topics/{id}/tags{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).put({ "tags": ["topicCustomer"] })
```

```javascript
assert.equal( tagsUpdateResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsDeleteResponse = client("/topics/{id}/tags{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( tagsDeleteResponse.status, 200 )
```

Create Topic Comment

```javascript
commentsCreateResponse = client("/topics/{id}/comments{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).post({"topic_comment": {"body": "A man walks into a bar"}})
```

```javascript
assert.equal(commentsCreateResponse.status, 201)
ID_TOPIC_COMMENT = commentsCreateResponse.body.topic_comment.id
```

List Topic Comments

```javascript
commentsResponse = client("/topics/{id}/comments{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Create Vote

```javascript
voteCreateResponse = client("/topics/{id}/vote{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).post()
```

```javascript
assert.equal( voteCreateResponse.status, 201 )
ID_TOPIC_VOTE = voteCreateResponse.body.topic_vote.id
```

Check for Votes

```javascript
votesResponse = client("/topics/{id}/votes{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( votesResponse.status, 200 )
```

Check for Votes

```javascript
voteResponse = client("/topics/{id}/vote{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( voteResponse.status, 200 )
```

Delete Vote

```javascript
voteDeleteResponse = client("/topics/{id}/vote{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( voteDeleteResponse.status, 200 )
```

Show Topic

```javascript
singleTopicResponse = client.topics.id(ID_TOPIC).json.get()
```

```javascript
assert.equal( singleTopicResponse.status, 200 )
```

Update topic

```javascript
singleTopicUpdateResponse = client.topics.id(ID_TOPIC).json.put({
  "topic" : {
    "forum_id" : ID_FORUM ,
    "title" : "My Topic" ,
    "body" : "This is a test topic." ,
    "uploads" : [
      "vz7ll9ud8oofowy"
    ]
  }
})
```

```javascript
assert.equal( singleTopicUpdateResponse.status, 200 )
```

Accepts a comma separated list of topic ids to return

```javascript
showManyCreateResponse = client.topics.show_many.json.post()
```

```javascript
assert.equal( showManyCreateResponse.status, 200 )
```

Show Topic Comment

```javascript
singleCommentResponse = client("/topics/{id}/comments/{id_comment}{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "id_comment": ID_TOPIC_COMMENT,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleCommentResponse.status, 200 )
```

Creates a comment without sending out notifications. Allows setting created_at and updated_at

```javascript
singleCommentUpdateResponse = client("/topics/{id}/comments/{id_comment}{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "id_comment": ID_TOPIC_COMMENT,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal(singleCommentUpdateResponse.status, 200 )
```

List Topic Subscriptions

```javascript
subscriptionsResponse = client("/topics/{id}/subscriptions{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( subscriptionsResponse.status, 200 )
```

Creates a comment without sending out notifications. Allows setting created_at and updated_at.
 Allowed For: [Admins on non-enterprise accounts, Admins and agents with full forum access on enterprise accounts, The user who created the original comment]

```javascript
commentDeleteResponse = client("/topics/{id}/comments/{id_comment}{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "id_comment": ID_TOPIC_COMMENT,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( commentDeleteResponse.status, 200 )
```

Delete topic

```javascript
singleTopicDeleteResponse = client.topics.id(ID_TOPIC).json.delete()
```

```javascript
assert.equal( singleTopicDeleteResponse.status, 200 )
```

Garbage collection. Delete temporary forum

```javascript
client("/forums/{id}{mediaTypeExtension}", {
  "id": ID_FORUM,
  "mediaTypeExtension": ".json"
}).delete()
```