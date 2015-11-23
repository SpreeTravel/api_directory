---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7956/versions/8119/portal/pages/6746/preview
apiNotebookVersion: 1.1.66
title: Events, info, posts, privileges, questions
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
  clientId: CLIENT_ID
})
```

```javascript
ACCESS_TOKEN = $4.accessToken
```

Returns a stream of events that have occurred on the site.
 
The API considers the following "events":
 - posting a question
 - posting an answer
 - posting a comment
 - editing a post
 - creating a user
  
 
Events are only accessible for 15 minutes after they occurred, and by default only events in the last 5 minutes are returned. You can specify the age of the oldest event returned by setting the since parameter.
 
It is advised that developers batch events by ids and make as few subsequent requests to other methods as possible.
 
This method returns a list of events.

```javascript
eventsResponse = client.events.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( eventsResponse.status, 200 )
```

Returns a collection of statistics about the site.
 
Data to facilitate per-site customization, discover related sites, and aggregate statistics is all returned by this method.
 
This data is cached very aggressively, by design. Query sparingly, ideally no more than once an hour.
 
This method returns an info object.

```javascript
infoResponse = client.info.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( infoResponse.status, 200 )
```

Fetches all posts (questions and answers) on the site.
 
In many ways this method is the union of /questions and /answers, returning both sets of data albeit only the common fields.
 
Most applications should use the question or answer specific methods, but /posts is available for those rare cases where any activity is of intereset. Examples of such queries would be: "all posts on Jan. 1st 2011" or "top 10 posts by score of all time".
 
The sorts accepted by this method operate on the follow fields of the post object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of posts.

```javascript
allPostsResponse = client.posts.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( allPostsResponse.status, 200 )
```

Pick some post IDs

```javascript
postIDs = ""
postID = allPostsResponse.body.items[0].post_id
var count = 3
for(var i = 0 ; i < count ; i++){
  var post = allPostsResponse.body.items[i]
  postIDs += post.post_id
  if( i < count-1 ){
    postIDs += ";"
  }
}
```

Fetches a set of posts by ids.
 
This method is meant for grabbing an object when unsure whether an id identifies a question or an answer. This is most common with user entered data.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for post_id, answer_id, or question_id on post, answer, and question objects respectively.
 
The sorts accepted by this method operate on the follow fields of the post object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of posts.

```javascript
postsResponse = client.posts.ids(postIDs).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( postsResponse.status, 200 )
```

Gets the comments on the posts identified in ids, regardless of the type of the posts.
 
This method is meant for cases when you are unsure of the type of the post id provided. Generally, this would be due to obtaining the post id directly from a user.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for post_id, answer_id, or question_id on post, answer, and question objects respectively.
 
The sorts accepted by this method operate on the follow fields of the comment object:
 - creation - creation_date
 - votes - score
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of comments.

```javascript
postCommentsResponse = client.posts.ids(postIDs).comments.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( postCommentsResponse.status, 200 )
```

Returns edit revisions for the posts identified in ids.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for post_id, answer_id, or question_id on post, answer, and question objects respectively.
 
This method returns a list of revisions.

```javascript
postRevisionsResponse = client.posts.ids(postIDs).revisions.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( postRevisionsResponse.status, 200 )
```

Returns suggsted edits on the posts identified in ids.
 
 - creation - creation_date
 - approval - approval_date
 - rejection - rejection_date
  creation is the default sort.
 
 {ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for post_id, answer_id, or question_id on post, answer, and question objects respectively.
 
This method returns a list of suggested-edits.

```javascript
suggestedEditsResponse = client("/posts/{ids}/suggested-edits", {
  "ids": postIDs
}).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( suggestedEditsResponse.status, 200 )
```

Create a new comment.
 
Use an access_token with write_access to create a new comment on a post.
 
This method returns the created comment.

```javascript
// Not supported
// addResponse = client.posts.id(51812).comments.add.post({
//   "site": "meta",
//   key: KEY,
//   preview: true,
//   body: "API Notebook test comment"
// },{headers:{"Content-Type":"application/x-www-form-urlencoded"}})
```

```javascript
//assert.equal( addResponse.status, 201 )
```

Returns the earnable privileges on a site.
 
Privileges define abilities a user can earn (via reputation) on any Stack Exchange site.
 
While fairly stable, over time they do change. New ones are introduced with new features, and the reputation requirements change as a site matures.
 
This method returns a list of privileges.

```javascript
privilegesResponse = client.privileges.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( privilegesResponse.status, 200 )
```

Gets all the questions on the site.
 
This method allows you make fairly flexible queries across the entire corpus of questions on a site. For example, getting all questions asked in the the week of Jan 1st 2011 with scores of 10 or more is a single query with parameters sort=votes&min=10&fromdate=1293840000&todate=1294444800.
 
To constrain questions returned to those with a set of tags, use the tagged parameter with a semi-colon delimited list of tags. This is an and contraint, passing tagged=c;java will return only those questions with both tags. As such, passing more than 5 tags will always return zero results.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - hot - by the formula ordering the hot tab Does not accept min or max
 - week - by the formula ordering the week tab Does not accept min or max
 - month - by the formula ordering the month tab Does not accept min or max
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
allQuestionsResponse = client.questions.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( allQuestionsResponse.status, 200 )
```

Pick some question IDs

```javascript
questionIDs = ""
questionID = allQuestionsResponse.body.items[0].question_id
var count = 3
for(var i = 0 ; i < count ; i++){
  var question = allQuestionsResponse.body.items[i]
  questionIDs += question.question_id
  if( i < count-1 ){
    questionIDs += ";"
  }
}
```

Returns the questions identified in {ids}.
 
This is most useful for fetching fresh data when maintaining a cache of question ids, or polling for changes.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for question_id on question objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
questionsResponse = client.questions.ids(questionIDs).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( questionsResponse.status, 200 )
```

Gets the answers to a set of questions identified in id.
 
This method is most useful if you have a set of interesting questions, and you wish to obtain all of their answers at once or if you are polling for new or updates answers (in conjunction with sort=activity).
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for question_id on question objects.
 
The sorts accepted by this method operate on the follow fields of the answer object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of answers.

```javascript
answersResponse = client.questions.ids(questionIDs).answers.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( answersResponse.status, 200 )
```

Gets the comments on a question.
 
If you know that you have an question id and need the comments, use this method. If you know you have a answer id, use /answers/{ids}/comments. If you are unsure, use /posts/{ids}/comments.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for question_id on question objects.
 
The sorts accepted by this method operate on the follow fields of the comment object:
 - creation - creation_date
 - votes - score
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of comments.

```javascript
questionCommentsResponse = client.questions.ids(questionIDs).comments.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( questionCommentsResponse.status, 200 )
```

Gets questions which link to those questions identified in {ids}.
 
This method only considers questions that are linked within a site, and will never return questions from another Stack Exchange site.
 
A question is considered "linked" when it explicitly includes a hyperlink to another question, there are no other heuristics.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for question_id on question objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - rank - a priority sort by site applies, subject to change at any time Does not accept min or max
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
linkedResponse = client.questions.ids(questionIDs).linked.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( linkedResponse.status, 200 )
```

Returns questions that the site considers related to those identified in {ids}.
 
The algorithm for determining if questions are related is not documented, and subject to change at any time. Futhermore, these values are very heavily cached, and may not update immediately after a question has been editted. It is also not guaranteed that a question will be considered related to any number (even non-zero) of questions, and a consumer should be able to handle a variable number of returned questions.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for question_id on question objects.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - rank - a priority sort by site applies, subject to change at any time Does not accept min or max
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
relatedResponse = client.questions.ids(questionIDs).related.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( relatedResponse.status, 200 )
```

Returns a subset of the events that have happened to the questions identified in id.
 
This provides data similar to that found on a question's timeline page.
 
Voting data is scrubbed to deter inferencing of voter identity.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for question_id on question objects.
 
This method returns a list of question timeline events.

```javascript
timelineResponse = client.questions.ids(questionIDs).timeline.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( timelineResponse.status, 200 )
```

Returns all the questions with active bounties in the system.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
featuredResponse = client.questions.featured.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( featuredResponse.status, 200 )
```

Returns questions the site considers to be unanswered.
 
Note that just because a question has an answer, that does not mean it is considered answered. While the rules are subject to change, at this time a question must have at least one upvoted answer to be considered answered.
 
To constrain questions returned to those with a set of tags, use the tagged parameter with a semi-colon delimited list of tags. This is an and contraint, passing tagged=c;java will return only those questions with both tags. As such, passing more than 5 tags will always return zero results.
 
Compare with /questions/no-answers.
 
This method corresponds roughly with the unanswered tab.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
unansweredResponse = client.questions.unanswered.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( unansweredResponse.status, 200 )
```

Returns questions which have received no answers.
 
Compare with /questions/unanswered which mearly returns questions that the sites consider insufficiently well answered.
 
This method corresponds roughly with the this site tab.
 
To constrain questions returned to those with a set of tags, use the tagged parameter with a semi-colon delimited list of tags. This is an and contraint, passing tagged=c;java will return only those questions with both tags. As such, passing more than 5 tags will always return zero results.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
noAnswersResponse = client("/questions/no-answers", {
}).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( noAnswersResponse.status, 200 )
```

Pick some 'revision_guid' fields in order to form {ids} for GET /revisions/{ids}

```javascript
revisionIDs = ""
var count = Math.min(postRevisionsResponse.body.items.length, 3)
for(var i = 0 ; i < count ; i++){
  var revision = postRevisionsResponse.body.items[i]
  revisionIDs += revision.revision_guid
  if( i < count-1 ){
    revisionIDs += ";"
  }
}
```

Returns edit revisions identified by ids in {ids}.
 
{ids} can contain up to 20 semicolon delimited ids, to find ids programatically look for revision_guid on revision objects. Note that unlike most other id types in the API, revision_guid is a string.
 
This method returns a list of revisions.

```javascript
revisionsResponse = client.revisions.ids(revisionIDs).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( revisionsResponse.status, 200 )
```

Searches a site for any questions which fit the given criteria.
 
This method is intentionally quite limited. For more general searching, you should use a proper internet search engine restricted to the domain of the site in question.
 
At least one of tagged or intitle must be set on this method. nottagged is only used if tagged is also set, for performance reasons.
 
tagged and nottagged are semi-colon delimited list of tags. At least 1 tag in tagged will be on each returned question if it is passed, making it the OR equivalent of the AND version of tagged on /questions.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - relevance - matches the relevance tab on the site itself Does not accept min or max
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
searchResponse = client.search.get({
  "site": "stackoverflow",
  "intitle": "java",
  key: KEY
})
```

```javascript
assert.equal( searchResponse.status, 200 )
```

Searches a site for any questions which fit the given criteria.
 
Search criteria are expressed using the following parameters:
  - q - a free form text parameter, will match all question properties based on an undocumented algorithm.
 - accepted - true to return only questions with accepted answers, false to return only those without. Omit to elide constraint.
 - answers - the minimum number of answers returned questions must have.
 - body - text which must appear in returned questions' bodies.
 - closed - true to return only closed questions, false to return only open ones. Omit to elide constraint.
 - migrated - true to return only questions migrated away from a site, false to return only those not. Omit to elide constraint.
 - notice - true to return only questions with post notices, false to return only those without. Omit to elide constraint.
 - nottagged - a semicolon delimited list of tags, none of which will be present on returned questions.
 - tagged - a semicolon delimited list of tags, of which at least one will be present on all returned questions.
 - title - text which must appear in returned questions' titles.
 - user - the id of the user who must own the questions returned.
 - url - a url which must be contained in a post, may include a wildcard.
 - views - the minimum number of views returned questions must have.
 - wiki - true to return only community wiki questions, false to return only non-community wiki ones. Omit to elide constraint.
  
At least one additional parameter must be set if nottagged is set, for performance reasons.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - relevance - matches the relevance tab on the site itself Does not accept min or max
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
// Method not sup[ported
// advancedResponse = client.search.advanced.get({
//   "site": "stackoverflow",
//   "q": "Regular expressions in java"
// })
```

```javascript
//assert.equal( advancedResponse.status, 200 )
```