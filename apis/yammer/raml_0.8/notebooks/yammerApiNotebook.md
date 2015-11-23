---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8041/versions/8205/portal/pages/6893/preview
apiNotebookVersion: 1.1.66
title: Yammer API notebook
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

CLIENT_ID = prompt("Please, enter client Id of your Yammer application.")

CLIENT_SECRET = prompt("Please, enter client Secret of your Yammer application.")

```



Name of topic operated by the notebook



```javascript

TOPIC_NAME = "Running Yammer API Notebook"

```

```javascript

// Read about the Yammer RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8041/versions/8205/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8041/versions/8205/definition');

```

```javascript

API.authenticate(client,"oauth_2_0",{

  clientId : CLIENT_ID,

  clientSecret : CLIENT_SECRET

})

```

```javascript

authResponse=$5

```

```javascript

function customAuthHack(o){

  o.setRequestHeader("Authorization", "Bearer " + authResponse.accessToken.token);

}

```

```javascript

API.set(client, 'beforeSend', new function(){return customAuthHack})

```



We need a purse method in order to respect rate limit.



```javascript

function pause(seconds)

{

  var millis = seconds * 1000

  var date = new Date();

  var curDate = null;

  do { curDate = new Date(); }

  while(curDate-date < millis);

}

```



Returns a list of networks to which the current user has access. Supports included_suspended parameter.



```javascript

networksCurrentResponse = client.networks.current.json.get()

```

```javascript

assert.equal( networksCurrentResponse.status, 200 )

```



Select a network to operate with.



```javascript

if(networksCurrentResponse.body.length>1){

  var msg = "Please, select a Yammer network for the Notebook to operate with. The notebook will post a message into the network, upload a file and try entering and leaving a group.\n"

  var networks = networksCurrentResponse.body

  for(var i in networks){

    var net = networks[i]

    msg += "\n" + i + ": "+net.name    

  }

  var ind = prompt(msg)

  if(ind>=0&&ind<networks.length){

    networkId = networks[ind].id

    tokensResponse = client("oauth/tokens.json").get()

    for(var i in tokensResponse.body){

      var tokenObject = tokensResponse.body[i]

      if(tokenObject.network_id==networkId){

        API.set(client, 'beforeSend', new function(){

          return function customAuthHack(o){

  					o.setRequestHeader("Authorization", "Bearer " + tokenObject.token)

					}

        })

        break

      }

    }

  }

}

```



View data about the current user



```javascript

currentUserResponse = client.users.current.json.get()

```

```javascript

assert.equal( currentUserResponse.status, 200 )

currentUserId = currentUserResponse.body.id

currentUserEmail = currentUserResponse.body.contact.email_addresses[0].address

```



Create a new message. The response body will include the new message formatted the same way as message polling above. This allows your app to immediately display the newly posted message back to the user



```javascript

createMessagesResponse = client.messages.json.post({

  "body": "API Notebook test message" ,

  "topic1": TOPIC_NAME

})

```

```javascript

assert.equal( createMessagesResponse.status, 201 )

messageId = createMessagesResponse.body.messages[0].id

threadId = createMessagesResponse.body.messages[0].thread_id

```



All public messages in the user’s (whose access token is being used to make the API call henceforth referred to as current user) Yammer network. Corresponds to “All” conversations in the Yammer web interface.



```javascript

messagesResponse = client.messages.json.get()

```

```javascript

assert.equal( messagesResponse.status, 200 )

```



The user’s feed, based on the selection they have made between “Following” and “Top” conversations.



```javascript

myFeedResponse = client.messages.my_feed.json.get()

```

```javascript

assert.equal( myFeedResponse.status, 200 )

```



All messages received by the user.



```javascript

receivedResponse = client.messages.received.json.get()

```

```javascript

assert.equal( receivedResponse.status, 200 )

```



The algorithmic feed for the user that corresponds to “Top” conversations, which is what the vast majority of users will see in the Yammer web interface.



```javascript

algoResponse = client.messages.algo.json.get()

```

```javascript

assert.equal( algoResponse.status, 200 )

```



The “Following” feed which is conversations involving people, groups and topics that the user is following.



```javascript

followingResponse = client.messages.following.json.get()

```

```javascript

assert.equal( followingResponse.status, 200 )

```



All messages sent by the user. Alias for /api/v1/messages/from_user/logged-in_user_id.format.



```javascript

sentResponse = client.messages.sent.json.get()

```

```javascript

assert.equal( sentResponse.status, 200 )

```

```javascript

//wait for 10 seconds in order not to violate rate limits

pause(10)

```



Private messages received by the user.



```javascript

privateResponse = client.messages.private.json.get()

```

```javascript

assert.equal( privateResponse.status, 200 )

```



Marks the specified message as liked by the current user.



```javascript

likedCurrentResponse = client.messages.liked_by.current.json.post({

  "message_id": messageId

})

```

```javascript

assert.equal( likedCurrentResponse.status, 201 )

```



Removes the like mark from the specified message.



```javascript

unlikedCurrentResponse = client.messages.liked_by.current.json.delete({},{query:{

  "message_id": messageId

}})

```

```javascript

assert.equal( unlikedCurrentResponse.status, 200 )

```



The search resource will return a list of messages, users, topics and groups that match the user’s search query.



```javascript

searchResponse = client.search.json.get({search:TOPIC_NAME})

```

```javascript

assert.equal( searchResponse.status, 200 )

topicId = searchResponse.body.topics[0].id

```



The users that have used the topic specified by the numeric string ID.



```javascript

topicResponse = client.topics.id(topicId).json.get()

```

```javascript

assert.equal( topicResponse.status, 200 )

```



 The feed of messages for a hashtag specified by the numeric string ID.



```javascript

aboutTopicResponse=client.messages.about_topic.id(topicId).json.get()

```

```javascript

assert.equal( aboutTopicResponse.status, 200 )

```



Create a new user. Current user should be a verified admin in a paid Yammer network to perform this action.



```javascript

//Not supported

// userId = null

// createUsersResponse = client.users.json.post()

```

```javascript

// assert.equal( createUsersResponse.status, 200 )

// userId = createUsersResponse.status == 200 ? createUsersResponse.body.id : null

```



Suspend or delete a user. Current user should be a verified admin in a paid Yammer network to perform this action



```javascript

//not supported

// if(userId){

//   deleteUserResponse = client.users.id(userId).json.delete()

// }

```

```javascript

// if(userId){

//   assert.equal( deleteUserResponse.status, 200 )

// }

```



Update information about a user. The target user should be the current user or a verified admin in a paid Yammer network



```javascript

updateUserResponse = client.users.id(currentUserId).json.put({})

```

```javascript

assert.equal(updateUserResponse.status,200)

```



Retrieve users in the current user’s Yammer network. Supports page, sort_by, reverse, and letter parameters.



```javascript

usersResponse = client.users.json.get()

```

```javascript

assert.equal( usersResponse.status, 200 )

```



View data about a user.



```javascript

userResponse = client.users.id(currentUserId).json.get()

```

```javascript

assert.equal( userResponse.status, 200 )

```



Alias to /api/v1/users/current user’s id.format. Supports include_followed_users, include_followed_tags, and include_group_memberships parameters.



```javascript

byEmailResponse = client.users.by_email.json.get({

  "email": currentUserEmail

})

```

```javascript

assert.equal( byEmailResponse.status, 200 )

```

```javascript

//wait for 10 seconds in order not to violate rate limits

pause(10)

```



Create a new pending attachment.



```javascript

formData = new FormData()

formData.append("attachment", new Blob([0,0,0,0,0]), "NotebookTestFile.txt")

```

```javascript

pendingAttachmentsResponse = client.pending_attachments.post(formData)

```

```javascript

assert.equal(pendingAttachmentsResponse.status,201)

pendingAttachmentsBody = JSON.parse(pendingAttachmentsResponse.body)

attachmentId = pendingAttachmentsBody.id

```



Delete a pending attachment



```javascript

//Not supported

//pendinfAttachmentDeleteResponse = client.pending_attachments.id(attachmentId).delete()

```

```javascript

//assert.equal(pendinfAttachmentDeleteResponse.status,200)

```



Let's post a message with the attachment



```javascript

createMessageResponse2 = client.messages.json.post({

  "body": "API Notebook test message with attachment" ,

  "topic1": TOPIC_NAME,

  "pending_attachment1" : attachmentId

})

messageId2 = createMessageResponse2.body.messages[0].id

```



The “conversation view” for a thread specified by the numeric string ID.



```javascript

threadsResponse = client.threads.id(threadId).json.get()

```

```javascript

assert.equal( threadsResponse.status, 200 )

```

```javascript

//wait for 10 seconds in order not to violate rate limits

pause(10)

```



Retrieve a topic.



```javascript

topicResponse = client.topics.id(topicId).json.get()

```

```javascript

assert.equal( topicResponse.status, 200 )

```



Retrieve all groups



```javascript

allGroupsResponse = client("groups.json").get()

```

```javascript

assert.equal(allGroupsResponse.status,200)

groupId = allGroupsResponse.body[0].id

```

```javascript

{

  var msg = "Please, select a group to join:\n"

  for(var i in allGroupsResponse.body){

    var gr = allGroupsResponse.body[i]

    msg += "\n" + i + ": " + gr.full_name

  }

  var ind = prompt(msg)

  groupId = allGroupsResponse.body[ind].id

}

```



Retrieve users of a group.



```javascript

groupUsersResponse = client.users.in_group.id(groupId).json.get()

```

```javascript

assert.equal(groupUsersResponse.status,200)

```



Join the group specified by the numeric string ID



```javascript

joinGroupMembershipsResponse = client.group_memberships.json.post({

  "group_id": groupId

})

```

```javascript

assert.equal( joinGroupMembershipsResponse.status, 201 )

```



Leave a group



```javascript

deleteGroupMembershipsResponse = client.group_memberships.json.delete({

  "group_id": groupId

})

```

```javascript

assert.equal( deleteGroupMembershipsResponse.status, 200 )

```



Show org chart relationships. Defaults to user_current, supports user_id.



```javascript

relationshipsResponse = client.relationships.json.get()

```

```javascript

assert.equal( relationshipsResponse.status, 200 )

```



Add an org chart relationship. Specify id of the user for whom the relationship is being added as user_id, if the user is not the current user. Use [ subordinate | superior | colleague ] to create the relationship



```javascript

addRelationshipsResponse = client.relationships.json.post({})

```

```javascript

assert.equal( addRelationshipsResponse.status, 201 )

```

```javascript

//wait for 10 seconds in order not to violate rate limits

pause(10)

```



Remove the relationship specified by relationship_type from the user specified by the numeric string ID.



```javascript

deleteRelationshipResponse = client.relationships.id(currentUserId).json.delete({},{query:{type:"colleague"}})

```

```javascript

assert.equal( deleteRelationshipResponse.status, 200 )

```



Get the notifications feed for the current user



```javascript

streamsNotificationsResponse = client.streams.notifications.json.get()

```

```javascript

assert.equal( streamsNotificationsResponse.status, 200 )

```



Show suggested groups to join and users to follow.



```javascript

suggestionsResponse = client.suggestions.json.get()

```

```javascript

assert.equal( suggestionsResponse.status, 200 )

suggestionId = suggestionsResponse.body.length == 0 ? null : suggestionsResponse.body[0].id

```



Decline a suggestion.



```javascript

if(suggestionId){

  deleteSuggestionsResponse = client.suggestions.id(suggestionId).json.delete()

}

```

```javascript

if(suggestionId){

  assert.equal( deleteSuggestionsResponse.status, 200 )

}

```



Subscribe to a user or topic. Supports target_type and target_id parameters



```javascript

threadSubscriptionCreateResponse = client.subscriptions.post({

  "target_id": threadId,

  "target_type": "thread"

})

```

```javascript

assert.equal( threadSubscriptionCreateResponse.status, 201 )

```



Check to see if you are subscribed to thread with the given id. Returns 404 when you are not following a given thread.



```javascript

toThreadResponse = client.subscriptions.to_thread.id(threadId).json.get()

```

```javascript

assert.equal( toThreadResponse.status, 200 )

```



Let's subscribe on a topic.



```javascript

topicSubscriptionCreateResponse = client.subscriptions.post({

  "target_id": topicId,

  "target_type": "topic"

})

```

```javascript

assert.equal( topicSubscriptionCreateResponse.status, 201 )

```



Check to see if you are subscribed to topic with the given id. Returns 404 when you are not following a given topic.



```javascript

toTopicResponse = client.subscriptions.to_topic.id(topicId).json.get()

```

```javascript

assert.equal( toTopicResponse.status, 200 )

```



Let's subscribe on a user



```javascript

userSubscriptionCreateResponse = client.subscriptions.post({

  "target_id": currentUserId,

  "target_type": "user"

})

```

```javascript

assert.equal( userSubscriptionCreateResponse.status, 201 )

```



Check to see if you are subscribed to the user with the given id. Returns 404 when you are not following a given user. 



```javascript

toUserResponse = client.subscriptions.to_user.id(currentUserId).json.get()

```

```javascript

assert.equal( toUserResponse.status, 200 )

```

```javascript

//wait for 10 seconds in order not to violate rate limits

pause(10)

```



Unsubscribe to a thread.



```javascript

deleteThreadSubscriptionsResponse = client.subscriptions.delete({},{query:{

  "target_id": threadId ,

  "target_type": "thread"

}})

```

```javascript

assert.equal( deleteThreadSubscriptionsResponse.status, 200 )

```



Unsubscribe to a topic.



```javascript

deleteTopicSubscriptionsResponse = client.subscriptions.delete({},{query:{

  "target_id": topicId ,

  "target_type": "topic"

}})

```

```javascript

assert.equal( deleteTopicSubscriptionsResponse.status, 200 )

```



Unsubscribe to a user.



```javascript

deleteUserSubscriptionsResponse = client.subscriptions.delete({},{query:{

  "target_id": currentUserId,

  "target_type": "user"

}})

```

```javascript

assert.equal( deleteUserSubscriptionsResponse.status, 200 )

```



Return type a head suggestions for the prefix passed



```javascript

autocompleteRankedResponse = client.autocomplete.ranked.get({

  "prefix": "",

  "models": "topic:10"

})

```

```javascript

assert.equal( autocompleteRankedResponse.status, 200 )

```



Invites a user to the current user’s Yammer network.



```javascript

invitationsResponse = client.invitations.json.post({

  "email": currentUserEmail

})

```

```javascript

assert.equal( invitationsResponse.status, 200 )

```



No lets remove a message. The message must be owned by the current user.

If your app does not support the DELETE method you can do a POST with the parameter _method=DELETE.



```javascript

deleteMessageResponse = client.messages.id( messageId ).delete()

```

```javascript

assert.equal( deleteMessageResponse.status, 200 )

```



Garbage collection. Delete a message with pending attachment.



```javascript

deleteMessageResponse = client.messages.id( messageId2 ).delete()

```