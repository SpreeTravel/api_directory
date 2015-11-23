---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7956/versions/8119/portal/pages/6745/preview
apiNotebookVersion: 1.1.66
title: Users
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
CLIENT_ID = prompt("Please, enter 'clientId' of your Stack application")
KEY = prompt("Please, enter 'key' of your Stack application")
```

```javascript
// Read about the StackExchange at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7956/versions/8119/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7956/versions/8119/definition');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId: CLIENT_ID,
  scope: ["read_inbox"]
})
```

```javascript
ACCESS_TOKEN = $4.accessToken
```

Returns all users on a site.
 
This method returns a list of users.
 
The sorts accepted by this method operate on the follow fields of the user object:
 - reputation - reputation
 - creation - creation_date
 - name - display_name
 - modified - last_modified_date
  reputation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
The inname parameter lets consumers filter the results down to just those users with a certain substring in their display name. For example, inname=kevin will return all users with both users named simply "Kevin" or those with Kevin as one of (or part of) their names; such as "Kevin Montrose".

```javascript
allUsersResponse = client.users.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( allUsersResponse.status, 200 )
```

Pick some user IDs

```javascript
userIDs = ""
userID = allUsersResponse.body.items[0].user_id
var userCount = 3
for(var i = 0 ; i < userCount ; i++){
  var user = allUsersResponse.body.items[i]
  userIDs += user.user_id
  if( i < userCount-1 ){
    userIDs += ";"
  }
}
```

Gets the users identified in ids in {ids}.
 
Typically this method will be called to fetch user profiles when you have obtained user ids from some other source, such as /questions.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the user object:
 - reputation - reputation
 - creation - creation_date
 - name - display_name
 - modified - last_modified_date
  reputation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of users.

```javascript
particularUsersResponse = client.users.ids(userIDs).get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( particularUsersResponse.status, 200 )
```

Returns the answers the users in {ids} have posted.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the answer object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of answers.

```javascript
answersResponse = client.users.ids(userIDs).answers.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( answersResponse.status, 200 )
```

Get the badges the users in {ids} have earned.
 
Badge sorts are a tad complicated. For the purposes of sorting (and min/max) tag_based is considered to be greater than named.
 
This means that you can get a list of all tag based badges a user has by passing min=tag_based, and conversely all the named badges by passing max=named, with sort=type.
 
For ranks, bronze is greater than silver which is greater than gold. Along with sort=rank, set max=gold for just gold badges, max=silver&min=silver for just silver, and min=bronze for just bronze.
 
rank is the default sort.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
This method returns a list of badges.

```javascript
badgesResponse = client.users.ids(userIDs).badges.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( badgesResponse.status, 200 )
```

Get the comments posted by users in {ids}.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the comment object:
 - creation - creation_date
 - votes - score
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of comments.

```javascript
commentsResponse = client.users.ids(userIDs).comments.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Get the comments that the users in {ids} have posted in reply to the single user identified in {toid}.
 
This method is useful for extracting conversations, especially over time or across multiple posts.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects. {toid} can contain only 1 id, found in the same manner as those in {ids}.
 
The sorts accepted by this method operate on the follow fields of the comment object:
 - creation - creation_date
 - votes - score
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of comments.

```javascript
intendedCommentsResponse = client.users.ids(userIDs).comments.toid(userID).get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( intendedCommentsResponse.status, 200 )
```

Get the questions that users in {ids} have favorited.
 
This method is effectively a view onto a user's favorites tab.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - added - when the user favorited the question
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
favoritesResponse = client.users.ids(userIDs).favorites.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( favoritesResponse.status, 200 )
```

Gets all the comments that the users in {ids} were mentioned in.
 
Note, to count as a mention the comment must be considered to be "in reply to" a user. Most importantly, this means that a comment can only be in reply to a single user.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the comment object:
 - creation - creation_date
 - votes - score
  It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of comments.

```javascript
mentionedResponse = client.users.ids(userIDs).mentioned.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( mentionedResponse.status, 200 )
```

Gets the questions asked by the users in {ids}.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
questionsResponse = client.users.ids(userIDs).questions.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( questionsResponse.status, 200 )
```

Gets the questions on which the users in {ids} have active bounties.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
featuredResponse = client.users.ids(userIDs).questions.featured.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( featuredResponse.status, 200 )
```

Gets the questions asked by the users in {ids} which have no answers.
 
Questions returns by this method actually have zero undeleted answers. It is completely disjoint /users/{ids}/questions/unanswered and /users/{ids}/questions/unaccepted, which only return questions with at least one answer, subject to other contraints.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
noAnswersResponse = client("/users/{ids}/questions/no-answers", {
  "ids": userIDs
}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( noAnswersResponse.status, 200 )
```

Gets the questions asked by the users in {ids} which have at least one answer, but no accepted answer.
 
Questions returned by this method have answers, but the owner has not opted to accept any of them.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
unacceptedResponse = client.users.ids(userIDs).questions.unaccepted.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( unacceptedResponse.status, 200 )
```

Gets the questions asked by the users in {ids} which the site consideres unanswered, while still having at least one answer posted.
 
These rules are subject to change, but currently any question without at least one upvoted or accepted answer is considered unanswered.
 
To get the set of questions that a user probably considers unanswered, the returned questions should be combined with those returned by /users/{id}/questions/no-answers. These methods are distinct so that truly unanswered (that is, zero posted answers) questions can be easily separated from early poorly or inadequately answered ones.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
unansweredResponse = client.users.ids(userIDs).questions.unanswered.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( unansweredResponse.status, 200 )
```

Gets a subset of the reputation changes for users in {ids}.
 
Reputation changes are intentionally scrubbed of some data to make it difficult to correlate votes on particular posts with user reputation changes. That being said, this method returns enough data for reasonable display of reputation trends.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
This method returns a list of reputation objects.

```javascript
reputationResponse = client.users.ids(userIDs).reputation.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( reputationResponse.status, 200 )
```

Returns users' public reputation history.
 
This method returns a list of reputation_history.

```javascript
// Method not sup[ported
// reputationHistoryResponse = client("/users/{ids}/reputation-history", {
//   "ids": userIDs
// }).get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( reputationHistoryResponse.status, 200 )
```

Returns the suggested edits a users in {ids} have submitted.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the suggested_edit object:
 - creation - creation_date
 - approval - approval_date Does not return unapproved suggested_edits
 - rejection - rejection_date Does not return unrejected suggested_edits
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of suggested-edits.

```javascript
suggestedEditsResponse = client("/users/{ids}/suggested-edits", {
  "ids": userIDs
}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( suggestedEditsResponse.status, 200 )
```

Returns the tags the users identified in {ids} have been active in.
 
This route corresponds roughly to user's stats tab, but does not include tag scores. A subset of tag scores are available (on a single user basis) in /users/{id}/top-answer-tags and /users/{id}/top-question-tags.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
The sorts accepted by this method operate on the follow fields of the tag object:
 - popular - count
 - activity - the creation_date of the last question asked with the tag
 - name - name
  popular is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of tags.

```javascript
tagsResponse = client.users.ids(userIDs).tags.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Returns a subset of the actions the users in {ids} have taken on the site.
 
This method returns users' posts, edits, and earned badges in the order they were accomplished. It is possible to filter to just a window of activity using the fromdate and todate parameters.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for user_id on user or shallow_user objects.
 
This method returns a list of user timeline objects.

```javascript
timelineResponse = client.users.ids(userIDs).timeline.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( timelineResponse.status, 200 )
```

Returns all of a user's associated accounts, given their account_ids in {ids}.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for account_id on user objects.
 
This method returns a list of network_users.

```javascript
associatedResponse = client.users.ids(userIDs).associated.get({"key": KEY})
```

```javascript
assert.equal( associatedResponse.status, 200 )
```

Returns a record of merges that have occurred involving the passed account ids.
 
This method allows you to take now invalid account ids and find what account they've become, or take currently valid account ids and find which ids were equivalent in the past.
 
This is most useful when confirming that an account_id is in fact "new" to an application.
 
Account merges can happen for a wide range of reasons, applications should not make assumptions that merges have particular causes.
 
Note that accounts are managed at a network level, users on a site may be merged due to an account level merge but there is no guarantee that a merge has an effect on any particular site.
 
This method returns a list of account_merge.

```javascript
//mergesResponse = client.users.ids(userIDs).merges.get()
```

```javascript
//assert.equal( mergesResponse.status, 200 )
```

Returns a user's notifications.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of notifications.

```javascript
// Method not sup[ported
// notificationsResponse = client.users.id(userID).notifications.get({
//   "site": "stackoverflow",
//   key: KEY
// })
```

```javascript
//assert.equal( notificationsResponse.status, 200 )
```

Returns a user's unread notifications.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of notifications.

```javascript
// Method not sup[ported
// unreadResponse = client.users.id(userID).notifications.unread.get({
//   "site": "stackoverflow",
//   key: KEY
// })
```

```javascript
//assert.equal( unreadResponse.status, 200 )
```

Returns the privileges a user has.
 
Applications are encouraged to calculate privileges themselves, without repeated queries to this method. A simple check against the results returned by /privileges and user.user_type would be sufficient.
 
{id} can contain only a single, to find it programatically look for user_id on user or shallow_user objects.
 
This method returns a list of privileges.

```javascript
privilegesResponse = client.users.id(userID).privileges.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( privilegesResponse.status, 200 )
```

Returns a user's full reputation history, including private events.
 
This method requires an access_token, with a scope containing "private_info".
 
This method returns a list of reputation_history.

```javascript
// Method not sup[ported
// fullResponse = client("/users/{id}/reputation-history/full", {
//   "id": userID
// }).get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( fullResponse.status, 200 )
```

Returns the top 30 answers a user has posted in response to questions with the given tags.
 
{id} can contain a single id, to find it programatically look for user_id on user or shallow_user objects. {tags} is limited to 5 tags, passing more will result in an error.
 
The sorts accepted by this method operate on the follow fields of the answer object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of answers.

```javascript
topAnswersResponse = client("/users/{id}/tags/{tags}/top-answers", {
  "tags": "tagsValue",
  "id": userID
}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topAnswersResponse.status, 200 )
```

Returns the top 30 questions a user has asked with the given tags.
 
{id} can contain a single id, to find it programatically look for user_id on user or shallow_user objects. {tags} is limited to 5 tags, passing more will result in an error.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
topQuestionsResponse = client("/users/{id}/tags/{tags}/top-questions", {
  "tags": "tagsValue",
  "id": userID
}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topQuestionsResponse.status, 200 )
```

Returns a single user's top tags by answer score.
 
This a subset of the data returned on a user's tags tab.
 
{id} can contain a single id, to find it programatically look for user_id on user or shallow_user objects.
 
This method returns a list of top_tag objects.

```javascript
topAnswerTagsResponse = client("/users/{id}/top-answer-tags", {
  "id": userID
}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topAnswerTagsResponse.status, 200 )
```

Returns a single user's top tags by question score.
 
This a subset of the data returned on a user's tags tab.
 
{id} can contain a single id, to find it programatically look for user_id on user or shallow_user objects.
 
This method returns a list of top_tag objects.

```javascript
topQuestionTagsResponse = client("/users/{id}/top-question-tags", {
  "id": userID
}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topQuestionTagsResponse.status, 200 )
```

Returns the write permissions a user has via the api.
 
The Stack Exchange API gives users the ability to create, edit, and delete certain types. This method returns whether the passed user is capable of performing those actions at all, as well as how many times a day they can.
 
This method does not consider the user's current quota (ie. if they've already exhausted it for today) nor any additional restrictions on write access, such as editing deleted comments.
 
This method returns a list of write_permissions.

```javascript
// Method not sup[ported
// writePermissionsResponse = client("/users/{id}/write-permissions", {
//   "id": userID
// }).get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( writePermissionsResponse.status, 200 )
```

Returns a user's inbox.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method is effectively an alias for /inbox. It is provided for consumers who make strong assumptions about operating within the context of a single site rather than the Stack Exchange network as a whole.
 
{id} can contain a single id, to find it programatically look for user_id on user or shallow_user objects.
 
This method returns a list of inbox items.

```javascript
currentUserID = client.me.get({site:"stackoverflow",key:KEY}).body.items[0].user_id
```

```javascript
inboxResponse = client.users.id(currentUserID).inbox.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( inboxResponse.status, 200 )
```

Returns the unread items in a user's inbox.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method is effectively an alias for /inbox/unread. It is provided for consumers who make strong assumptions about operating within the context of a single site rather than the Stack Exchange network as a whole.
 
{id} can contain a single id, to find it programatically look for user_id on user or shallow_user objects.
 
This method returns a list of inbox items.

```javascript
unreadResponse = client.users.id(currentUserID).inbox.unread.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( unreadResponse.status, 200 )
```

Gets those users on a site who can exercise moderation powers.
 
Note, employees of Stack Exchange Inc. will be returned if they have been granted moderation powers on a site even if they have never been appointed or elected explicitly. This method checks abilities, not the manner in which they were obtained.
 
The sorts accepted by this method operate on the follow fields of the user object:
 - reputation - reputation
 - creation - creation_date
 - name - display_name
 - modified - last_modified_date
  reputation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of users.

```javascript
moderatorsResponse = client.users.moderators.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( moderatorsResponse.status, 200 )
```

Returns those users on a site who both have moderator powers, and were actually elected.
 
This method excludes Stack Exchange Inc. employees, unless they were actually elected moderators on a site (which can only have happened prior to their employment).
 
The sorts accepted by this method operate on the follow fields of the user object:
 - reputation - reputation
 - creation - creation_date
 - name - display_name
 - modified - last_modified_date
  reputation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of users.

```javascript
electedResponse = client.users.moderators.elected.get({
  "site": "stackoverflow",
  "key": KEY
})
```

```javascript
assert.equal( electedResponse.status, 200 )
```