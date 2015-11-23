---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6931/preview
apiNotebookVersion: 1.1.66
title: Users, job statuses
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

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser("Roger Wilco","rogers@example.org")
```

Create User

```javascript
userCreateResponse = client.users.json.post({
  "user": {
    "name": "Roger Wilco",
    "role": "agent",
    "email": "rogers@example.org",
    "verified":true
  }
})
```

```javascript
assert.equal( userCreateResponse.status, 201 )
ID_USER = userCreateResponse.body.user.id
```

List Users

```javascript
usersListResponse = client.users.json.get()
```

```javascript
assert.equal( usersListResponse.status, 200 )
```

List Forum Subscription

```javascript
forumSubscriptionsResponse = client.users.user_id(ID_USER).forum_subscriptions.json.get()
```

```javascript
assert.equal( forumSubscriptionsResponse.status, 200 )
```

List Groups

```javascript
groupsListResponse = client.users.user_id(ID_USER).groups.json.get()
```

```javascript
assert.equal( groupsListResponse.status, 200 )
```

List Memberships

```javascript
groupMembershipsListResponse = client.users.user_id(ID_USER).group_memberships.json.get()
```

```javascript
assert.equal( groupMembershipsListResponse.status, 200 )
```

Creation temporary group for creation group membership

```javascript
tempGroup = client.groups.json.post({
  "group" : {
    "name" : "My Group"
  }
})
```

```javascript
ID_GROUP = tempGroup.body.group.id
```

Creating a membership means assigning an agent to a given group
 Allowed For: [Admins]

```javascript
groupMembershipsCreateResponse = client.users.user_id(ID_USER).group_memberships.json.post({
  "group_membership": {
  "user_id": ID_USER,
  "group_id": ID_GROUP
  }
})
```

```javascript
assert.equal( groupMembershipsCreateResponse.status, 201 )
ID_MEMBERSHIP = groupMembershipsCreateResponse.body.group_membership.id
```

Show Membership

```javascript
showMembershipResponse = client("/users/{user_id}/group_memberships/{id}{mediaTypeExtension}", {
  "id": ID_MEMBERSHIP,
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( showMembershipResponse.status, 200 )
```

Set membership as default
 Allowed For: [Agents]

```javascript
makeDefaultMembershipResponse = client.users.user_id(ID_USER).group_memberships.id(ID_MEMBERSHIP).make_default.json.put()
```

```javascript
assert.equal( makeDefaultMembershipResponse.status, 200 )
```

Immediately removes a user from a group and schedules a job to unassign all working tickets that are assigned to the given user and group combination
 Allowed For: [Admins]

```javascript
membershipDeleteResponse = client("/users/{user_id}/group_memberships/{id}{mediaTypeExtension}", {
  "id": ID_MEMBERSHIP,
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( membershipDeleteResponse.status, 200 )
```

Listing Organizations

```javascript
userOrganizationsListResponse = client.users.user_id(ID_USER).organizations.json.get()
```

```javascript
assert.equal( userOrganizationsListResponse.status, 200 )
```

List Organization Subscriptions

```javascript
userOrganizationSubscriptionsResponse = client.users.user_id(ID_USER).organization_subscriptions.json.get()
```

```javascript
assert.equal( userOrganizationSubscriptionsResponse.status, 200 )
```

Tickets are ordered chronologically by created date, from oldest to newest. Note: The first ticket listed may not be the absolute oldest ticket in your account due to ticket archiving. To get a list of all tickets in your account use the Incremental Ticket API

```javascript
userRequestedTicketsResponse = client.users.user_id(ID_USER).tickets.requested.json.get()
```

```javascript
assert.equal( userRequestedTicketsResponse.status, 200 )
```

Tickets are ordered chronologically by created date, from oldest to newest. Note: The first ticket listed may not be the absolute oldest ticket in your account due to ticket archiving. To get a list of all tickets in your account use the Incremental Ticket API

```javascript
userTicketsCcdResponse = client.users.user_id(ID_USER).tickets.ccd.json.get()
```

```javascript
assert.equal( userTicketsCcdResponse.status, 200 )
```

Create temporary forum and topic

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

```javascript
tempTopic = client.topics.json.post({
  "topic": {
    "forum_id": ID_FORUM, 
    "title": "My Topic", 
    "body": "This is a test topic."
  }
})
```

```javascript
ID_TOPIC = tempTopic.body.topic.id
```

Create comment

```javascript
tempTopicComment = client("/topics/{id}/comments{mediaTypeExtension}", {
  "id": ID_TOPIC,
  "mediaTypeExtension": ".json"
}).post({
  "topic_comment": {
    "body": "A man walks into a bar", 
    "user_id": ID_USER
  }
})
```

```javascript
ID_TOPIC_COMMENT = tempTopicComment.body.topic_comment.id
```

Get user comments on topic

```javascript
userTopicCommentResponse = client("/users/{user_id}/topic_comments/{id}{mediaTypeExtension}", {
  "id": ID_TOPIC_COMMENT,
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( userTopicCommentResponse.status, 200 )
```

List Topic Subscriptions

```javascript
topicSubscriptionsResponse = client.users.user_id(ID_USER).topic_subscriptions.json.get()
```

```javascript
assert.equal( topicSubscriptionsResponse.status, 200 )
```

Delete temporary users which could have been created during earlier notebook launches

```javascript
deleteExistingUser("Huan Wilco","huang@example.org")
deleteExistingUser("Huan Wilcoe","h@example.org")
```

Create user for merging

```javascript
tempUser = client.users.json.post({
  "user": {
    "name": "Huan Wilco",
    "role": "end-user",
    "email": "huang@example.org"
  }
})
ID_MERGE_USER = tempUser.body.user.id
```

Create second user for merge

```javascript
tempUser2 = client.users.json.post({
  "user": {
    "name": "Huan Wilcoe",
    "role": "end-user",
    "email": "h@example.org"
  }
})
ID_MERGE_USER2 = tempUser2.body.user.id
```

Merge users. The current user will be merged into the existing user provided in the params. Users can only merge themselves into another user.
allowed for  [Verified end users]

```javascript
mergeUserUpdateResponse = client("/users/{user_id}/merge{mediaTypeExtension}", {
  "user_id": ID_MERGE_USER,
  "mediaTypeExtension": ".json"
}).put({"user": {"id": ID_MERGE_USER2}})
```

```javascript
assert.equal( mergeUserUpdateResponse.status, 200 )
```


Create user identity

```javascript
userIdentityCreateResponse = client.users.user_id(ID_USER).identities.json.post({
  "identity" : {
    "type" : "email" ,
    "value" : "foo@br.com"
  }
})
```

```javascript
assert.equal( userIdentityCreateResponse.status, 201 )
ID_USER_IDENTITY = userIdentityCreateResponse.body.identity.id
```

Returns all user identities for a given user id
 Allowed For: [Agents]

```javascript
userIdentitiesResponse = client.users.user_id(ID_USER).identities.json.get()
```

```javascript
assert.equal( userIdentitiesResponse.status, 200 )
```

Shows the identity with the given id
 Allowed For: [Agents]

```javascript
singleIdentityResponse = client("/users/{user_id}/identities/{id}{mediaTypeExtension}", {
  "id": ID_USER_IDENTITY,
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleIdentityResponse.status, 200 )
```

This API method only allows you to set an identity as verified. You cannot otherwise change value of an identity but must create a new identity and delete the one you're replacing.
 Allowed For: [Agents]

```javascript
userIdentityUpdateResponse = client("/users/{user_id}/identities/{id}{mediaTypeExtension}", {
  "id": ID_USER_IDENTITY,
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).put({"identity": {"verified": true}})
```

```javascript
assert.equal( userIdentityUpdateResponse.status, 200 )
```

This API method only allows you to set an identity to primary. If you wish to change an identity, you create a new one with the correct value and delete the old one. This is a collection level operation and the correct behavior for an API client is to subsequently reload the entire collection.
 Allowed For: [Agents, End Users]

```javascript
makeIdentityPrimaryUpdateResponse = client.users.user_id(ID_USER).identities.id(ID_USER_IDENTITY).make_primary.put()
```

```javascript
assert.equal( makeIdentityPrimaryUpdateResponse.status, 200 )
```

This API method only allows you to set an identity as verified.
 Allowed For: [Agents, Agents]

```javascript
verifyUpdateResponse = client.users.user_id(ID_USER).identities.id(ID_USER_IDENTITY).verify.put()
```

```javascript
assert.equal( verifyUpdateResponse.status, 200 )
```

Delete identity for a given user
 Allowed For: [Agents]

```javascript
identityDeleteResponse = client("/users/{user_id}/identities/{id}{mediaTypeExtension}", {
  "id": ID_USER_IDENTITY,
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( identityDeleteResponse.status, 200 )
```

List oAuth Clients

```javascript
oauthClientsResponse = client.users.me.oauth.clients.json.get()
```

```javascript
assert.equal( oauthClientsResponse.status, 200 )
```

Listing Requests

```javascript
userRequestsResponse = client("/users/{user_id}/requests{mediaTypeExtension}", {
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( userRequestsResponse.status, 200 )
```

List Topics

```javascript
userTopicsListResponse = client("/users/{user_id}/topics{mediaTypeExtension}", {
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( userTopicsListResponse.status, 200 )
```

User related information

```javascript
topicCommentsResponse = client("/users/{user_id}/topic_comments{mediaTypeExtension}", {
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( topicCommentsResponse.status, 200 )
```

Check for Vote

```javascript
topicVotesResponse = client("/users/{user_id}/topic_votes{mediaTypeExtension}", {
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( topicVotesResponse.status, 200 )
```

Accepts a comma-separated list of user ids

```javascript
relatedResponse = client("/users/{user_id}/related{mediaTypeExtension}", {
  "user_id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( relatedResponse.status, 200 )
```

Specify a partial or full name or email address as the value of the query attribute. Example: query="Gerry". Specify an id number as the value of the external_id attribute. For more advanced searches of users, use the Search API

```javascript
meResponse = client.users.me.json.get()
```

```javascript
assert.equal( meResponse.status, 200 )
```

Show User

```javascript
showUserResponse = client("/users/{id}{mediaTypeExtension}", {
  "id": ID_USER,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( showUserResponse.status, 200 )
```

Update user

```javascript
userUpdateResponse = client("/users/{id}{mediaTypeExtension}", {
  "id": ID_USER,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( userUpdateResponse.status, 200 )
```

Show few users
The name must be at least 2 characters in length.
 allows for[Agents, restrictions apply on certain actions]

```javascript
showManyCreateResponse = client.users.show_many.json.post()
```

```javascript
assert.equal( showManyCreateResponse.status, 200 )
```

Create few users.
The name must be at least 2 characters in length.
 allows for[Agents, restrictions apply on certain actions]

```javascript
manyUsersCreateResponse = client.users.create_many.json.post({
  "users" : [
    {
      "name" : "Roger Wilco" ,
      "email" : "roge@example.org" ,
      "role" : "agent"
    } , {
      "name" : "Woger Rilco" ,
      "email" : "woge@example.org" ,
      "role" : "admin"
    }
  ]
})
```

```javascript
assert.equal( manyUsersCreateResponse.status, 200 )
ID_JOB = manyUsersCreateResponse.body.job_status.id
```

This shows the status of a background job

```javascript
jobStatusResponse = client.job_statuses.id(ID_JOB).json.get()
```

```javascript
assert.equal( jobStatusResponse.status, 200 )
```

Specify a partial or full name or email address as the value of the query attribute. Example query="Gerry". Specify an id number as the value of the external_id attribute. For more advanced searches use the Search API

```javascript
searchUserResponse = client.users.search.json.get()
```

```javascript
assert.equal( searchUserResponse.status, 200 )
```

Autocomplete users
 Allowed For: [Agents]

```javascript
userAutocompleteResponse = client.users.autocomplete.json.get({"name": "att"})
```

```javascript
assert.equal( userAutocompleteResponse.status, 200 )
```

Delete membership

```javascript
client.group_memberships.id(ID_MEMBERSHIP).json.delete()
```

Delete user

```javascript
userDeleteResponse = client.users.id(ID_USER).json.delete()
```

```javascript
assert.equal( userDeleteResponse.status, 200 )
```

Delete temporal group, forum, topic, topic comment, user for merging

```javascript
client("/topics/{topic_id}/comments/{comment_id}{mediaTypeExtension}", {
  "topic_id": ID_TOPIC,
  "comment_id": ID_TOPIC_COMMENT,
  "mediaTypeExtension": ".json"
}).delete()
```

Garbage collection. Delete temporary users.

```javascript
client.topics.id(ID_TOPIC).json.delete()
client.users.id(ID_MERGE_USER).json.delete()
client.users.id(ID_MERGE_USER2).json.delete()
```