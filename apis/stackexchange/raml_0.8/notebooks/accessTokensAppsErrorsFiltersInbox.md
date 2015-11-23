---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7956/versions/8119/portal/pages/6747/preview
apiNotebookVersion: 1.1.66
title: Access-tokens, apps, errors, filters, inbox
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

Passing valid access_tokens to this method causes the application that created them to be de-authorized by the user associated with each access_token. This will remove the application from their apps tab, and cause all other existing access_tokens to be destroyed.
 
This method is meant for uninstalling applications, recovering from access_token leaks, or simply as a stronger form of /access-tokens/{accessTokens}/invalidate.
 
Nothing prevents a user from re-authenticate to an application that has de-authenticated itself, the user will be prompted to approve the application again however.
 
{accessTokens} can contain up to 20 access tokens. These are obtained by authenticating a user using OAuth 2.0.
 
This method returns a list of access_tokens.

```javascript
deAuthenticateResponse = client("/apps/{accessTokens}/de-authenticate", {
  "accessTokens": ACCESS_TOKEN
}).get({
  key: KEY
})
```

```javascript
assert.equal( deAuthenticateResponse.status, 200 )
```

Returns the various error codes that can be produced by the API.
 
This method is provided for discovery, documentation, and testing purposes, it is not expected many applications will consume it during normal operation.
 
For testing purposes, look into the /errors/{id} method which simulates errors given a code.
 
This method returns a list of errors.

```javascript
errorsResponse = client.errors.get({
  key: KEY
})
```

```javascript
assert.equal( errorsResponse.status, 200 )
```

This method allows you to generate an error.
 
This method is only intended for use when testing an application or library. Unlike other methods in the API, its contract is not frozen, and new error codes may be added at any time.
 
This method results in an error, which will be expressed with a 400 HTTP status code and setting the error* properties on the wrapper object.

```javascript
errorResponse = client.errors.id(403).get({
  key: KEY
})
```

```javascript
assert.equal( errorResponse.status, 400 )
assert.equal( errorResponse.body.error_id, 403 )
```

Creates a new filter given a list of includes, excludes, a base filter, and whether or not this filter should be "unsafe".
 
Filter "safety" is defined as follows. Any string returned as a result of an API call with a safe filter will be inline-able into HTML without script-injection concerns. That is to say, no additional sanitizing (encoding, HTML tag stripping, etc.) will be necessary on returned strings. Applications that wish to handle sanitizing themselves should create an unsafe filter. All filters are safe by default, under the assumption that double-encoding bugs are more desirable than script injections.
 
If no base filter is specified, the default filter is assumed. When building a filter from scratch, the none built-in filter is useful.
 
When the size of the parameters being sent to this method grows to large, problems can occur. This method will accept POST requests to mitigate this.
 
It is not expected that many applications will call this method at runtime, filters should be pre-calculated and "baked in" in the common cases. Furthermore, there are a number of built-in filters which cover common use cases.
 
This method returns a single filter.

```javascript
createResponse = client.filters.create.get({
  key: KEY
})
```

```javascript
assert.equal( createResponse.status, 200 )
```

Returns the fields included by the given filters, and the "safeness" of those filters.
 
It is not expected that this method will be consumed by many applications at runtime, it is provided to aid in debugging.
 
{filters} can contain up to 20 semicolon delimited filters. Filters are obtained via calls to /filters/create, or by using a built-in filter.
 
This method returns a list of filters.

```javascript
filterResponse = client.filters.filters("default").get({
  key: KEY
})
```

```javascript
assert.equal( filterResponse.status, 200 )
```

Returns a user's inbox.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of inbox items.

```javascript
inboxResponse = client.inbox.get({key: KEY})
```

```javascript
assert.equal( inboxResponse.status, 200 )
```

Returns the unread items in a user's inbox.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of inbox items.

```javascript
unreadResponse = client.inbox.unread.get({key:KEY})
```

```javascript
assert.equal( unreadResponse.status, 200 )
```

Returns a user's notifications.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of notifications.

```javascript
// Method not sup[ported
//notificationsResponse = client.notifications.get({key:KEY})
```

```javascript
//assert.equal( notificationsResponse.status, 200 )
```

Returns a user's unread notifications.
 
This method requires an access_token, with a scope containing "read_inbox".
 
This method returns a list of notifications.

```javascript
// Method not sup[ported
//unreadResponse = client.notifications.unread.get({key:KEY})
```

```javascript
//assert.equal( unreadResponse.status, 200 )
```

Returns all sites in the network.
 
This method allows for discovery of new sites, and changes to existing ones. Be aware that unlike normal API methods, this method should be fetched very infrequently, it is very unusual for these values to change more than once on any given day. It is suggested that you cache its return for at least one day, unless your app encounters evidence that it has changed (such as from the /info method).
 
The pagesize parameter for this method is unbounded, in acknowledgement that for many applications repeatedly fetching from /sites would complicate start-up tasks needlessly.
 
This method returns a list of sites.

```javascript
sitesResponse = client.sites.get({
  key: KEY
})
```

```javascript
assert.equal( sitesResponse.status, 200 )
```

Reads the properties for a set of access tokens.
 
{accessTokens} can contain up to 20 access tokens. These are obtained by authenticating a user using OAuth 2.0.
 
This method returns a list of access_tokens.

```javascript
accessTokensResponse = client("/access-tokens/{accessTokens}", {
  "accessTokens": ACCESS_TOKEN
}).get({
  key: KEY
})
```

```javascript
assert.equal( accessTokensResponse.status, 200 )
```

Immediately expires the access tokens passed. This method is meant to allow an application to discard any active access tokens it no longer needs.
 
{accessTokens} can contain up to 20 access tokens. These are obtained by authenticating a user using OAuth 2.0.
 
This method returns a list of access_tokens.

```javascript
invalidateResponse = client("/access-tokens/{accessTokens}/invalidate", {
  "accessTokens": ACCESS_TOKEN
}).get({
  key: KEY
})
```

```javascript
assert.equal( invalidateResponse.status, 200 )
```

Returns questions which are similar to a hypothetical one based on a title and tag combination.
 
This method is roughly equivalent to a site's related questions suggestion on the ask page.
 
This method is useful for correlating data outside of a Stack Exchange site with similar content within one.
 
Note that title must always be passed as a parameter. tagged and nottagged are optional, semi-colon delimited lists of tags.
 
If tagged is passed it is treated as a preference, there is no guarantee that questions returned will have any of those tags. nottagged is treated as a requirement, no questions will be returned with those tags.
 
The sorts accepted by this method operate on the follow fields of the question object:
 - activity - last_activity_date
 - creation - creation_date
 - votes - score
 - relevance - order by "how similar" the questions are, most likely candidate first with a descending order Does not accept min or max
  activity is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of questions.

```javascript
similarResponse = client.similar.get({
  "site": "stackoverflow",
  title: "Regular expressions in java",
  key: KEY
})
```

```javascript
assert.equal( similarResponse.status, 200 )
```

Returns all the suggested edits in the systems.
 
This method returns a list of suggested-edits.
 
The sorts accepted by this method operate on the follow fields of the suggested_edit object:
 - creation - creation_date
 - approval - approval_date Does not return unapproved suggested_edits
 - rejection - rejection_date Does not return unrejected suggested_edits
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.

```javascript
allSuggestedEditsResponse = client("/suggested-edits", {
}).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( allSuggestedEditsResponse.status, 200 )
```

Pick some suggested edit IDs

```javascript
suggestedEditIDs = ""
var count = 3
for(var i = 0 ; i < count ; i++){
  var se = allSuggestedEditsResponse.body.items[i]
  suggestedEditIDs += se.post_id
  if( i < count-1 ){
    suggestedEditIDs += ";"
  }
}
```

Returns suggested edits identified in ids.
 
{ids} can contain up to 100 semicolon delimited ids, to find ids programatically look for suggested_edit_id on suggested_edit objects.
 
The sorts accepted by this method operate on the follow fields of the suggested_edit object:
 - creation - creation_date
 - approval - approval_date Does not return unapproved suggested_edits
 - rejection - rejection_date Does not return unrejected suggested_edits
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of suggested-edits.

```javascript
suggestedEditsResponse = client("/suggested-edits/{ids}", {
  "ids": suggestedEditIDs
}).get({
  "site": "stackoverflow",
  key: KEY

})
```

```javascript
assert.equal( suggestedEditsResponse.status, 200 )
```

Returns the tags found on a site.
 
The inname parameter lets a consumer filter down to tags that contain a certain substring. For example, inname=own would return both "download" and "owner" amongst others.
 
This method returns a list of tags.
 
The sorts accepted by this method operate on the follow fields of the tag object:
 - popular - count
 - activity - the creation_date of the last question asked with the tag
 - name - name
  popular is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.

```javascript
tagsResponse = client.tags.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Returns tag objects representing the tags in {tags} found on the site.
 
This method diverges from the standard naming patterns to avoid to conflicting with existing methods, due to the free form nature of tag names.
 
This method returns a list of tags.
 
The sorts accepted by this method operate on the follow fields of the tag object:
 - popular - count
 - activity - the creation_date of the last question asked with the tag
 - name - name
  popular is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.

```javascript
infoResponse = client.tags.tags("java;c++").info.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( infoResponse.status, 200 )
```

Returns the frequently asked questions for the given set of tags in {tags}.
 
For a question to be returned, it must have all the tags in {tags} and be considered "frequently asked". The exact algorithm for determining whether a question is considered a FAQ is subject to change at any time.
 
{tags} can contain up to 5 individual tags per request.
 
This method returns a list of questions.

```javascript
faqResponse = client.tags.tags("java;c++").faq.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( faqResponse.status, 200 )
```

Returns the tags that are most related to those in {tags}.
 
Including multiple tags in {tags} is equivalent to asking for "tags related to tag #1 and tag #2" not "tags related to tag #1 or tag #2".
 
count on tag objects returned is the number of question with that tag that also share all those in {tags}.
 
{tags} can contain up to 4 individual tags per request.
 
This method returns a list of tags.

```javascript
relatedResponse = client.tags.tags("java;c++").related.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( relatedResponse.status, 200 )
```

Gets all the synonyms that point to the tags identified in {tags}. If you're looking to discover all the tag synonyms on a site, use the /tags/synonyms methods instead of call this method on all tags.
 
{tags} can contain up to 20 individual tags per request.
 
The sorts accepted by this method operate on the follow fields of the tag_synonym object:
 - creation - creation_date
 - applied - applied_count
 - activity - last_applied_date
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of tag synonyms.

```javascript
synonymsResponse = client.tags.tags("java;c++").synonyms.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( synonymsResponse.status, 200 )
```

Returns the wikis that go with the given set of tags in {tags}.
 
Be aware that not all tags have wikis.
 
{tags} can contain up to 20 individual tags per request.
 
This method returns a list of tag wikis.

```javascript
wikisResponse = client.tags.tags("java;c++").wikis.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( wikisResponse.status, 200 )
```

Returns the tags found on a site that only moderators can use.
 
The inname parameter lets a consumer filter down to tags that contain a certain substring. For example, inname=own would return both "download" and "owner" amongst others.
 
This method returns a list of tags.
 
The sorts accepted by this method operate on the follow fields of the tag object:
 - popular - count
 - activity - the creation_date of the last question asked with the tag
 - name - name
  popular is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.

```javascript
moderatorOnlyResponse = client("/tags/moderator-only", {
}).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( moderatorOnlyResponse.status, 200 )
```

Returns the tags found on a site that fulfill required tag constraints on questions.
 
The inname parameter lets a consumer filter down to tags that contain a certain substring. For example, inname=own would return both "download" and "owner" amongst others.
 
This method returns a list of tags.
 
The sorts accepted by this method operate on the follow fields of the tag object:
 - popular - count
 - activity - the creation_date of the last question asked with the tag
 - name - name
  popular is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.

```javascript
requiredResponse = client.tags.required.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( requiredResponse.status, 200 )
```

Returns all tag synonyms found a site.
 
When searching for synonyms of specific tags, it is better to use /tags/{tags}/synonyms over this method.
 
The sorts accepted by this method operate on the follow fields of the tag_synonym object:
 - creation - creation_date
 - applied - applied_count
 - activity - last_applied_date
  creation is the default sort.
 
 It is possible to create moderately complex queries using sort, min, max, fromdate, and todate.
 
This method returns a list of tag_synonyms.

```javascript
synonymsResponse = client.tags.synonyms.get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( synonymsResponse.status, 200 )
```

Returns the top 30 answerers active in a single tag, of either all-time or the last 30 days.
 
This is a view onto the data presented on the tag info page on the sites.
 
This method returns a list of tag score objects.

```javascript
topAnswersForTagResponse = client("/tags/{tag}/top-answerers/{period}", {
  "tag": "java",
  "period": "all_time"
}).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( topAnswersForTagResponse.status, 200 )
```

Returns the top 30 askers active in a single tag, of either all-time or the last 30 days.
 
This is a view onto the data presented on the tag info page on the sites.
 
This method returns a list of tag score objects.

```javascript
topAnswersForPeriodResponse = client("/tags/{tag}/top-askers/{period}", {
  "tag": "java",
  "period": "all_time"
}).get({
  "site": "stackoverflow",
  key: KEY
})
```

```javascript
assert.equal( topAnswersForPeriodResponse.status, 200 )
```