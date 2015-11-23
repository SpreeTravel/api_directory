---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8026/versions/8192/portal/pages/6860/preview
apiNotebookVersion: 1.1.66
title: Search, DMs, Blocks, Favorites
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js');
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
var assert = chai.assert;
var screenName = "POlololoshechka";//just screen name for the test
```

```javascript
var newDirectMessage=null;//just temporary variable
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8026/versions/8192/definition');
```

```javascript
API.authenticate(client);
```

Returns a collection of relevant Tweets matching a specified query.

```javascript
tweetsArray = client.search.tweets.json.get({
  "q":"@twitter"
});

```

```javascript
assert.equal( tweetsArray.status, 200 );
```

```javascript
tweetId = tweetsArray.body.statuses[0].id_str;
```

Send new direct message

```javascript
followerScreenName=prompt("please enter screen name of your follower. Please note that if you will click 'ok' test direct message will be sent to him.");
```

```javascript
if (followerScreenName!=null){
   newDirectMessage = client.direct_messages.new.json.post({
   "text": "test value",
   "screen_name":followerScreenName
});
}
```

```javascript
if(followerScreenName!=null){
  assert.equal(newDirectMessage.status, 200);
}
```

Get direct message id

```javascript
if (newDirectMessage!=null){
  directMessageId = newDirectMessage.body.id_str;
}
```

Returns the 20 most recent direct messages sent to the authenticating user.
Includes detailed information about the sender and recipient user. You can
request up to 200 direct messages per call, up to a maximum of 800 incoming DMs.
Important: This method requires an access token with RWD (read, write & direc
message) permissions. Consult The Application Permission Model for more
information. (https://dev.twitter.com/docs/application-permission-model)}

```javascript
directMessages = client.direct_messages.json.get();
```

```javascript
assert.equal(directMessages.status, 200);
```

Returns the 20 most recent direct messages sent by the authenticating user.
Includes detailed information about the sender and recipient user. You can
request up to 200 direct messages per call, up to a maximum of 800 outgoing DMs.
Important: This method requires an access token with RWD (read, write &
direct message) permissions. Consult The Application Permission Model for
more information.

```javascript
sent = client.direct_messages.sent.json.get();
```

```javascript
assert.equal(sent.status, 200);
```

Returns a single direct message, specified by an id parameter. Like the
/1.1/direct_messages.format request, this method will include the user
objects of the sender and recipient.
Important: This method requires an access token with RWD (read, write &
direct message) permissions. Consult The Application Permission Model for
more information.

```javascript
if(followerScreenName!=null){
showDirectMessages = client.direct_messages.show.json.get({
  "id": directMessageId
});
}
```

```javascript
if(followerScreenName!=null){
assert.equal( showDirectMessages.status, 200);
}
```

Destroys the direct message specified in the required ID parameter. The
authenticating user must be the recipient of the specified direct message.
Important: This method requires an access token with RWD (read, write &
direct message) permissions. Consult The Application Permission Model for
more information.

```javascript
if(followerScreenName!=null){
destroyDirectMessage = client.direct_messages.destroy.json.post({
  "id": directMessageId
});
}
```

```javascript
if(followerScreenName!=null){
assert.equal(destroyDirectMessage.status, 200 );
}
```

Returns a collection of user objects that the authenticating user is blocking.
Important On October 15, 2012 this method will become cursored by default,
altering the default response format. See Using cursors to navigate collections
for more details on how cursoring works.

```javascript
listBlocked = client.blocks.list.json.get();
```

```javascript
assert.equal(listBlocked.status, 200);
```

Returns an array of numeric user ids the authenticating user is blocking.

```javascript
idsBlocked = client.blocks.ids.json.get();
```

```javascript
assert.equal(idsBlocked.status, 200);
```

Blocks the specified user from following the authenticating user. In addition
the blocked user will not show in the authenticating users mentions or timeline
(unless retweeted by another user). If a follow or friend relationship exists
it is destroyed.
Either screen_name or user_id must be provided.

```javascript
blockUser = client.blocks.create.json.post({
  "screen_name": screenName
});
```

```javascript
assert.equal(blockUser.status, 200);
```

Un-blocks the user specified in the ID parameter for the authenticating user.
Returns the un-blocked user in the requested format when successful. If
relationships existed before the block was instated, they will not be restored.
One of screen_name or id must be provided.

```javascript
unblockUser = client.blocks.destroy.json.post({
  "screen_name":screenName
});
```

```javascript
assert.equal(unblockUser.status, 200);
```

Add to favorites

```javascript
addFavorites = client.favorites.create.json.post({
  "id": tweetId
});
```

```javascript
assert.equal(addFavorites.status, 200);
```

Returns the 20 most recent Tweets favorited by the authenticating or specified
user.
If you do not provide either a user_id or screen_name to this method, i
will assume you are requesting on behalf of the authenticating user. Specify
one or the other for best results.

```javascript
listFavorites = client.favorites.list.json.get();
```

```javascript
assert.equal(listFavorites.status, 200);
```

Un-favorites the status specified in the ID parameter as the authenticating
user. Returns the un-favorited status in the requested format when successful.
This process invoked by this method is asynchronous. The immediately returned
status may not indicate the resultant favorited status of the tweet. A 200 OK
response from this method will indicate whether the intended action was
successful or not.

```javascript
destroyFavorite = client.favorites.destroy.json.post({
  "id": tweetId
});
```

```javascript
assert.equal(destroyFavorite.status, 200);
```