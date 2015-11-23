---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8026/versions/8192/portal/pages/6859/preview
apiNotebookVersion: 1.1.66
title: Friends/Followers
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js');
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
var assert = chai.assert;
var screenName = "POlololoshechka";//just screen name for the tests
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8026/versions/8192/definition');
```

```javascript
API.authenticate(client);
```

Returns a collection of user_ids that the currently authenticated user does
not want to receive retweets from.
Use POST friendships/update to set the "no retweets" status for a given user
account on behalf of the current user.

```javascript
noRetweets = client.friendships.no_retweets.ids.json.get();
```

```javascript
assert.equal( noRetweets.status, 200 );
```

Returns the relationships of the authenticating user to the comma-separated
list of up to 100 screen_names or user_ids provided. Values for connections
can be: following, following_requested, followed_by, none.

```javascript
friendshipsLookup = client.friendships.lookup.json.get({
  "screen_name":screenName
});
```

```javascript
assert.equal( friendshipsLookup.status, 200 );
```

Returns a collection of numeric IDs for every user who has a pending request
to follow the authenticating user.

```javascript
friendshipsIncoming = client.friendships.incoming.json.get();
```

```javascript
assert.equal( friendshipsIncoming.status, 200 );
```

Returns a collection of numeric IDs for every protected user for whom the
authenticating user has a pending follow request.

```javascript
friendshipsOutgoing = client.friendships.outgoing.json.get();
```

```javascript
assert.equal( friendshipsOutgoing.status, 200 );
```

Allows the authenticating users to follow the user specified in the ID
parameter.
Returns the befriended user in the requested format when successful. Returns
a string describing the failure condition when unsuccessful. If you are
already friends with the user a HTTP 403 may be returned, though for performance
reasons you may get a 200 OK message even if the friendship already exists.
Actions taken in this method are asynchronous and changes will be eventually
consistent.

```javascript
friendshipsCreate = client.friendships.create.json.post({
  "screen_name":screenName
});
```

```javascript
assert.equal( friendshipsCreate.status, 200 );
```

Allows the authenticating user to unfollow the user specified in the ID
parameter.
Returns the unfollowed user in the requested format when successful. Returns
a string describing the failure condition when unsuccessful.
Actions taken in this method are asynchronous and changes will be eventually
consistent.

```javascript
friendshipsDestroy = client.friendships.destroy.json.post({
  "screen_name":screenName
});
```

```javascript
assert.equal( friendshipsDestroy.status, 200 );
```

Allows one to enable or disable retweets and device notifications from the
specified user.

```javascript
friendshipsUpdate = client.friendships.update.json.post({
  "screen_name":screenName
});
```

```javascript
assert.equal( friendshipsUpdate.status, 200 );
```

Returns detailed information about the relationship between two arbitrary
users.

```javascript
friendshipsShow = client.friendships.show.json.get({"source_screen_name": screenName, "target_screen_name":"realityfaker"});
```

```javascript
assert.equal( friendshipsShow.status, 200 );
```

Returns a cursored collection of user IDs for every user the specified user
is following (otherwise known as their "friends").
At this time, results are ordered with the most recent following first  however,
this ordering is subject to unannounced change and eventual consistency issues.
Results are given in groups of 5,000 user IDs and multiple "pages" of results
can be navigated through using the next_cursor value in subsequent requests.
See Using cursors to navigate collections for more information.
This method is especially powerful when used in conjunction with
'GET users/lookup', a method that allows you to convert user IDs into full
user objects in bulk.

```javascript
getUsersIds = client.friends.ids.json.get();
```

```javascript
assert.equal( getUsersIds.status, 200 );
```

Returns a cursored collection of user objects for every user the specified
user is following (otherwise known as their "friends").
At this time, results are ordered with the most recent following first
however, this ordering is subject to unannounced change and eventual consistency
issues. Results are given in groups of 20 users and multiple "pages" of results
can be navigated through using the next_cursor value in subsequent requests.
See Using cursors to navigate collections for more information.

```javascript
friendsList = client.friends.list.json.get();
```

```javascript
assert.equal( friendsList.status, 200 );
```

Returns a cursored collection of user IDs for every user following the
specified user.
At this time, results are ordered with the most recent following first
however, this ordering is subject to unannounced change and eventual
consistency issues. Results are given in groups of 5,000 user IDs and
multiple "pages" of results can be navigated through using the next_cursor
value in subsequent requests. See Using cursors to navigate collections
for more information.

```javascript
followersIds = client.followers.ids.json.get();
```

```javascript
assert.equal( followersIds.status, 200 );
```

Returns a cursored collection of user objects for users following the
specified user.
At this time, results are ordered with the most recent following first
however, this ordering is subject to unannounced change and eventual
consistency issues. Results are given in groups of 20 users and multiple
"pages" of results can be navigated through using the next_cursor value in
subsequent requests. See Using cursors to navigate collections for more
information.

```javascript
followersList = client.followers.list.json.get();
```

```javascript
assert.equal( followersList.status, 200 );
```