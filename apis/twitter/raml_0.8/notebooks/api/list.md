---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8026/versions/8192/portal/pages/6861/preview
apiNotebookVersion: 1.1.66
title: List
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js');
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
var assert = chai.assert;
var screenName = "POlololoshechka";//screen name for a tests
var followerScreenName = "Test3Anton";//screen name for a tests
var directMessageId = null;//just temp variable
var tweetsArray = null;//just temp variable
var tweetId = null;//just temp variable
var list_id = null;//just temp variable
var list_slug = null;//just temp variable
var member_id = null;//just temp variable
var elements = null;//just temp variable
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8026/versions/8192/definition');
```

```javascript
API.authenticate(client);
```

Create new list

```javascript
createNewList = client.lists.create.json.post({
  "name": "test_list"
});
```

```javascript
assert.equal(createNewList.status, 200);
```

Returns all lists the authenticating or specified user subscribes to, including
their own. The user is specified using the user_id or screen_name parameters.
If no user is given, the authenticating user is used.
This method used to be GET lists in version 1.0 of the API and has been renamed
for consistency with other call.
A maximum of 100 results will be returned by this call. Subscribed lists are
returned first, followed by owned lists. This means that if a user subscribes
to 90 lists and owns 20 lists, this method returns 90 subscriptions and 10 owned
lists. The reverse method returns owned lists first, so with reverse=true, 20
owned lists and 80 subscriptions would be returned. If your goal is to obtain
every list a user owns or subscribes to, use GET lists/ownerships and/or
GET lists/subscriptions instead.

```javascript
list = client.lists.list.json.get();
```

```javascript
list_id = list.body[0].id_str;
list_slug = list.body[0].slug;
```

```javascript
assert.equal( list.status, 200 );
```

Returns a timeline of tweets authored by members of the specified list.
Retweets are included by default. Use the include_rts=false parameter to
omit retweets.
Embedded Timelines is a great way to embed list timelines on your website.

```javascript
statusesList = client.lists.statuses.json.get({
  "list_id": list_id
});
```

```javascript
assert.equal(statusesList.status, 200);
```

Returns the lists the specified user has been added to. If user_id or
screen_name are not provided the memberships for the authenticating user
are returned.

```javascript
membershipsList = client.lists.memberships.json.get();
```

```javascript
assert.equal( membershipsList.status, 200);
```

Returns the subscribers of the specified list. Private list subscribers will
only be shown if the authenticated user owns the specified list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
subscribersList = client.lists.subscribers.json.get({
  "list_id": list_id
});
```

```javascript
assert.equal(subscribersList.status, 200);
```

Subscribes the authenticated user to the specified list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript

subcriberCreate = client.lists.subscribers.create.json.post({
  "slug":"ww1-less-well-known","owner_screen_name":"FamilyLetters"
});
```

```javascript
assert.equal( subcriberCreate.status, 200 );
```

Check if the specified user is a subscriber of the specified list. Returns
the user if they are subscriber.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
sbscribersShow = client.lists.subscribers.show.json.get({
  "slug":"ww1-less-well-known","owner_screen_name":"FamilyLetters",
  "screen_name":createNewList.body.user.screen_name
  
});
```

```javascript
assert.equal( sbscribersShow.status, 200 );
```

Unsubscribes the authenticated user from the specified list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
subscribersDestroy = client.lists.subscribers.destroy.json.post({
  "slug":"ww1-less-well-known","owner_screen_name":"FamilyLetters"
});
```

```javascript
assert.equal( subscribersDestroy.status, 200 )
```

Create member of a list

```javascript
memberCreate = client.lists.members.create.json.post({
  "user_id": "2463668354",
  "screen_name": "PifpaLol",
  "list_id": list_id
});
```

```javascript
member_id = memberCreate.body.user.id_str;
```

```javascript
assert.equal(memberCreate.status, 200);
assert.isNotNull(member_id);
```

Returns the members of the specified list. Private list members will only be
shown if the authenticated user owns the specified list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
membersList = client.lists.members.json.get({
  "list_id": list_id,
  "owner_screen_name": "PifpaLol"
});
```

```javascript
for(var i in membersList.body.users){
  elements = membersList.body.users[i];
  if (elements.id_str==member_id)break;
}
```

```javascript
assert.equal( membersList.status, 200);
```

Removes the specified member from the list. The authenticated user must be
the list's owner to remove members from the list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
memberDestroy = client.lists.members.destroy.json.post({
  "list_id":list_id,
  "screen_name": "PifpaLol",
  "owner_screen_name": "PifpaLol"
});
```

```javascript
membersList = client.lists.members.json.get({
  "list_id": list_id,
  "owner_screen_name": "PifpaLol"
});
elements = membersList.body.users.length;
```

```javascript
assert.equal(memberDestroy.status, 200);
assert.equal(elements, 0);
```

Create new

```javascript
membersCreateAll = client.lists.members.create_all.json.post({
  "screen_name":"POlololoshechka," + "PifpaLol",
    "list_id":list_id
});
```

Removes multiple members from a list, by specifying a comma-separated lis
of member ids or screen names. The authenticated user must own the list to
be able to remove members from it. Note that lists can't have more than 500
members, and you are limited to removing up to 100 members to a list at a
time with this method.
Please note that there can be issues with lists that rapidly remove and add
memberships. Take care when using these methods such that you are not too
rapidly switching between removals and adds on the same list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
membersDestroy_all = client.lists.members.destroy_all.json.post({
  "list_id": list_id,
  "screen_name": "POlololoshechka,"+"PifpaLol"
});
```

```javascript
membersList = client.lists.members.json.get({
  "list_id": list_id,
  "owner_screen_name": "PifpaLol"
});
elements = membersList.body.users.length;
```

```javascript
assert.equal(membersDestroy_all.status, 200 );
assert.equal(elements, 0);
```

Create a test member

```javascript
memberCreate = client.lists.members.create.json.post({
  "user_id": "2463668354",
  "screen_name": "PifpaLol",
  "list_id": list_id
});
```

Check if the specified user is a member of the specified list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
listsShow = client.lists.show.json.get({
  "list_id":list_id,
  "owner_screen_name": "PifpaLol"
});
```

```javascript
member_id = listsShow.body.user.id_str;
```

```javascript
assert.equal(listsShow.status, 200);
assert.isNotNull(member_id);
```

Check if the specified user is a member of the specified list.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
membersShow = client.lists.members.show.json.get({
  "list_id": list_id,
  "slug": list_slug,
  "screen_name": "PifpaLol",
  "user_id": "2463668354"
});
```

```javascript
member_id = membersShow.body.id_str
```

```javascript
assert.equal(membersShow.status, 200);
assert.isNotNull(member_id);
```

Add a member to a list. The authenticated user must own the list to be able
to add members to it. Note that lists can't have more than 500 members.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
memberAdd = client.lists.members.create.json.post({
  "list_id": list_id,
  "screen_name": "POlololoshechka"
});
```

```javascript
membersShow = client.lists.members.show.json.get({
  "list_id": list_id,
  "slug": list_slug,
  "screen_name": "POlololoshechka"
});

```

```javascript
assert.equal(memberAdd.status, 200);
assert.isNotNull(membersShow.body.id_str);
```

Updates the specified list. The authenticated user must own the list to be
able to update it.

```javascript
listUpdate = client.lists.update.json.post({
  "list_id":list_id,
  "owner_screen_name": "PifpaLol"
})
```

```javascript
assert.equal(listUpdate.status, 200);
```

Deletes the specified list. The authenticated user must own the list to be
able to destroy it.
Either a list_id or a slug is required. If providing a list_slug, an
owner_screen_name or owner_id is also required.

```javascript
listDestroy = client.lists.destroy.json.post({
  "list_id": list_id
});
```

```javascript
assert.equal(listDestroy.status, 200)
```

Obtain a collection of the lists the specified user is subscribed to, 20
lists per page by default. Does not include the user's own lists.
A user_id or screen_name must be provided.

```javascript
listSubscribtions = client.lists.subscriptions.json.get();
```

```javascript
assert.equal(listSubscribtions.status, 200);
```

Returns the lists owned by the specified Twitter user. Private lists will
only be shown if the authenticated user is also the owner of the lists.
A user_id or screen_name must be provided.

```javascript
listOwnership = client.lists.ownerships.json.get();
```

```javascript
assert.equal(listOwnership.status, 200)
```