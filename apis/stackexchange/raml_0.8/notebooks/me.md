---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7956/versions/8119/portal/pages/6744/preview
apiNotebookVersion: 1.1.66
title: Me
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

Returns the user associated with the passed access_token.
 
This method returns a user.

```javascript
meResponse = client.me.get({
  "site": "stackoverflow",
  "order":"desc",
  "sort":"reputation",
  key: KEY
})
```

```javascript
assert.equal( meResponse.status, 200 )
```

Returns the answers owned by the user associated with the given access_token.
 
This method returns a list of answers.

```javascript
answersResponse = client.me.answers.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( answersResponse.status, 200 )
```

Returns the badges earned by the user associated with the given access_token.
 
This method returns a list of badges.

```javascript
badgesResponse = client.me.badges.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( badgesResponse.status, 200 )
```

Returns the comments owned by the user associated with the given access_token.
 
This method returns a list of comments.

```javascript
commentsResponse = client.me.comments.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Returns the comments owned by the user associated with the given access_token that are in reply to the user identified by {toId}.
 
This method returns a list of comments.

```javascript
commentsResponse = client.me.comments.toId(0).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Returns the questions favorites by the user associated with the given access_token.
 
This method returns a list of questions.

```javascript
favoritesResponse = client.me.favorites.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( favoritesResponse.status, 200 )
```

Returns the comments mentioning the user associated with the given access_token.
 
This method returns a list of comments.

```javascript
mentionedResponse = client.me.mentioned.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( mentionedResponse.status, 200 )
```

Returns a user's notifications, given an access_token.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of notifications.

```javascript
// Method not sup[ported
// notificationsResponse = client.me.notifications.get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( notificationsResponse.status, 200 )
```

Returns a user's unread notifications, given an access_token.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of notifications.

```javascript
// Method not sup[ported
// unreadResponse = client.me.notifications.unread.get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( unreadResponse.status, 200 )
```

Returns the privileges the user identified by access_token has.
 
This method returns a list of privileges.

```javascript
privilegesResponse = client.me.privileges.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( privilegesResponse.status, 200 )
```

Returns the questions owned by the user associated with the given access_token.
 
This method returns a list of questions.

```javascript
questionsResponse = client.me.questions.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( questionsResponse.status, 200 )
```

Returns the questions that have active bounties offered by the user associated with the given access_token.
 
This method returns a list of questions.

```javascript
featuredResponse = client.me.questions.featured.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( featuredResponse.status, 200 )
```

Returns the questions owned by the user associated with the given access_token that have no answers.
 
This method returns a list of questions.

```javascript
noAnswersResponse = client("me/questions/no-answers").get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( noAnswersResponse.status, 200 )
```

Returns the questions owned by the user associated with the given access_token that have no accepted answer.
 
This method returns a list of questions.

```javascript
unacceptedResponse = client.me.questions.unaccepted.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( unacceptedResponse.status, 200 )
```

Returns the questions owned by the user associated with the given access_token that are not considered answered.
 
This method returns a list of questions.

```javascript
unansweredResponse = client.me.questions.unanswered.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( unansweredResponse.status, 200 )
```

Returns the reputation changed for the user associated with the given access_token.
 
This method returns a list of reputation changes.

```javascript
reputationResponse = client.me.reputation.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
//assert.equal( reputationResponse.status, 200 )
```

Returns user's public reputation history.
 
This method returns a list of reputation_history.

```javascript
// Method not sup[ported
// reputationHistoryResponse = client("/me/reputation-history").get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( reputationHistoryResponse.status, 200 )
```

Returns user's full reputation history, including private events.
 
 This method requires an access_token, with a scope containing "private_info".
 
This method returns a list of reputation_history.

```javascript
// Method not sup[ported
// fullResponse = client("/me/reputation-history/full").get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( fullResponse.status, 200 )
```

Returns the suggested edits the user identified by access_token has submitted.
 
This method returns a list of suggested-edits.

```javascript
suggestedEditsResponse = client("/me/suggested-edits").get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( suggestedEditsResponse.status, 200 )
```

Returns the tags the user identified by the access_token passed is active in.
 
This method returns a list of tags.

```javascript
tagsResponse = client.me.tags.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Returns the top 30 answers the user associated with the given access_token has posted in response to questions with the given tags.
 
This method returns a list of answers.

```javascript
topAnswersResponse = client("/me/tags/{tag}/top-answers", {tag: "tagValue"}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topAnswersResponse.status, 200 )
```

Returns the top 30 questions the user associated with the given access_token has posted in response to questions with the given tags.
 
This method returns a list of questions.

```javascript
topQuestionsResponse = client("/me/tags/{tag}/top-questions", {tag: "tagValue"}).get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topQuestionsResponse.status, 200 )
```

Returns a subset of the actions the user identified by the passed access_token has taken on the site.
 
This method returns a list of user timeline objects.

```javascript
timelineResponse = client.me.timeline.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( timelineResponse.status, 200 )
```

Returns the user identified by access_token's top 30 tags by answer score.
 
This method returns a list of top tag objects.

```javascript
topAnswerTagsResponse = client("/me/top-answer-tags").get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topAnswerTagsResponse.status, 200 )
```

Returns the user identified by access_token's top 30 tags by question score.
 
This method returns a list of top tag objects.

```javascript
topQuestionTagsResponse = client("/me/top-question-tags").get({
  "site": "stackoverflow",
  key: KEY,
  access_token: ACCESS_TOKEN
})
```

```javascript
assert.equal( topQuestionTagsResponse.status, 200 )
```

Returns the write permissions a user has via the api, given an access token.
 
The Stack Exchange API gives users the ability to create, edit, and delete certain types. This method returns whether the passed user is capable of performing those actions at all, as well as how many times a day they can.
 
This method does not consider the user's current quota (ie. if they've already exhausted it for today) nor any additional restrictions on write access, such as editing deleted comments.
 
This method returns a list of write_permissions.

```javascript
// Method not sup[ported
// writePermissionsResponse = client("/me/write-permissions").get({
//   "site": "stackoverflow",
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( writePermissionsResponse.status, 200 )
```

Returns the user identified by access_token's inbox.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of inbox items.

```javascript
inboxResponse = client.me.inbox.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( inboxResponse.status, 200 )
```

Returns the unread items in the user identified by access_token's inbox.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of inbox items.

```javascript
unreadResponse = client.me.inbox.unread.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( unreadResponse.status, 200 )
```

Returns all of a user's associated accounts, given an access_token for them.
 
This method returns a list of network users.

```javascript
associatedResponse = client.me.associated.get({
  key: KEY
})
```

```javascript
assert.equal( associatedResponse.status, 200 )
```

Returns a record of merges that have occurred involving a user identified by an access_token.
 
This method allows you to take now invalid account ids and find what account they've become, or take currently valid account ids and find which ids were equivalent in the past.
 
This is most useful when confirming that an account_id is in fact "new" to an application.
 
Account merges can happen for a wide range of reasons, applications should not make assumptions that merges have particular causes.
 
Note that accounts are managed at a network level, users on a site may be merged due to an account level merge but there is no guarantee that a merge has an effect on any particular site.
 
This method returns a list of account_merge.

```javascript
// Method not sup[ported
// mergesResponse = client.me.merges.get({
//   key: KEY,
//   access_token: ACCESS_TOKEN
// })
```

```javascript
//assert.equal( mergesResponse.status, 200 )
```