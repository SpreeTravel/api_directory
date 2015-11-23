---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8026/versions/8192/portal/pages/6858/preview
apiNotebookVersion: 1.1.66
title: Users
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js');
```

```javascript
var assert = chai.assert;
var screenName = "noradio"; //just screen name for the tests
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8026/versions/8192/definition');
```

```javascript
API.authenticate(client);
```

Returns a cursored collection of user objects for users following the
specified user.
At this time, results are ordered with the most recent following first
however, this ordering is subject to unannounced change and eventual
consistency issues. Results are given in groups of 20 users and multiple
"pages" of results can be navigated through using the next_cursor value in
subsequent requests. 

```javascript
followers=client.followers.list.json.get();
```

```javascript
assert.equal(followers.status,200)
assert.isTrue(followers.body.users.length>=0)
```

Returns settings (including current trend, geo and sleep time information)
for the authenticating user.


```javascript
settings=client.account.settings.json.get();
```

```javascript
assert.equal(settings.status,200)
assert.isNotNull(settings.body.screen_name)
```

Returns an HTTP 200 OK response code and a representation of the requesting
user if authentication was successful; returns a 401 status code and an error
message if not. Use this method to test if supplied user credentials are valid.

```javascript
verifyCredentialsResponse=client.account.verify_credentials.json.get()
```

```javascript
assert.equal(verifyCredentialsResponse.status,200)
```

Updates the authenticating user's settings.
While all parameters for this method are optional, at least one or more
should be provided when executing this request.

```javascript
updateSettingsResponse=client.account.settings.json.post(settings)
```

```javascript
assert.equal(updateSettingsResponse.status,200)
```

Sets values that users are able to set under the "Account" tab of their
settings page. Only the parameters specified will be updated.
While no specific parameter is required, at least one of these parameters
should be provided when executing this method.

```javascript
updateProfileResponse = client.account.update_profile.json.post({
  "contributors_enabled": "true"
})
```

```javascript
assert.equal(updateProfileResponse.status,200)
```

Sets one or more hex values that control the color scheme of the authenticating
user's profile page on twitter.com. Each parameter's value must be a valid
hexidecimal value, and may be either three or six characters (ex: #fff or #ffffff).

```javascript
updateProfileColorsResponse= client.account.update_profile_colors.json.post({"profile_text_color": "000000"})
```

```javascript
assert.equal(updateProfileColorsResponse.status,200)
```

Returns fully-hydrated user objects for. up to 100 users per request, as
specified by comma-separated values passed to the user_id and/or
screen_name parameters.
This method is especially useful when used in conjunction with collections
of user IDs returned from GET friends/ids and GET followers/ids.
GET users/show is used to retrieve a single user object.

```javascript
usersLookup = client.users.lookup.json.get({
  "screen_name": "PifpaLol"
});
```

```javascript
assert.equal(usersLookup.status, 200);
```

Returns a variety of information about the user specified by the required
user_id or screen_name parameter. The author's most recent Tweet will be
returned inline when possible.
GET users/lookup is used to retrieve a bulk collection of user objects.
You must be following a protected user to be able to see their most recent
Tweet. If you don't follow a protected user, the users Tweet will be removed.
A Tweet will not always be returned in the current_status field.

```javascript
userShow = client.users.show.json.get({
  "user_id":"123456",
  "screen_name": "noradio"
});
```

```javascript
assert.equal( userShow.status, 200 )
```

Provides a simple, relevance-based search interface to public user accounts
on Twitter. Try querying by topical interest, full name, company name,
location, or other criteria. Exact match searches are not supported.
Only the first 1,000 matching results are available.

```javascript
usersSearch = client.users.search.json.get({
  "q": "pifpa"
});
```

```javascript
assert.equal( usersSearch.status, 200 );
```

Returns a collection of users that the specified user can "contribute" to.

```javascript
usersContributees = client.users.contributees.json.get({
  "screen_name":"realityfaker"
});
```

This api is not enabled for usual users, twitter developers forum tells that it does not exist any more, but according to the model it is just not publically available.

```javascript
if (usersContributees.status!=403){
  assert.equal( usersContributees.status, 200 );
}
```

Returns a collection of users who can contribute to the specified account.

```javascript
usersContributors = client.users.contributors.json.get({
"screen_name":"re"
});
```

This api is not enabled for usual users, twitter developers forum tells that it does not exist any more, but according to the model it is just not publically available.

```javascript
if (usersContributors.status!=403){
  assert.equal( usersContributors.status, 200 )
}
```

Returns a map of the available size variations of the specified user's profile
banner. If the user has not uploaded a profile banner, a HTTP 404 will be
served instead. This method can be used instead of string manipulation on the
profile_banner_url returned in user objects as described in User Profile Images
and Banners.
The profile banner data available at each size variant's URL is in PNG format.}

```javascript
usersProfileBanner = client.users.profile_banner.json.get({
  "screen_name":"noradio"
});
```

```javascript
assert.equal( usersProfileBanner.status, 200 );
```

Access to Twitter's suggested user list. This returns the list of suggested
user categories. The category can be used in GET users/suggestions/{slug}.json
to get the users in that category.

```javascript
usersSuggestions = client.users.suggestions.json.get({});
```

```javascript
assert.equal( usersSuggestions.status, 200 );
slugValue = usersSuggestions.body[0].slug
```

Access the users in a given category of the Twitter suggested user list.
It is recommended that applications cache this data for no more than one hour.

```javascript
usersSuggestionsSlug = client("/users/suggestions/{slug}{mediaTypeExtension}", {
  "slug": slugValue,
  "mediaTypeExtension": ".json"
}).get();
```

```javascript
assert.equal( usersSuggestionsSlug.status, 200 );
```

Access the users in a given category of the Twitter suggested user lis
and return their most recent status if they are not a protected user.

```javascript
suggestionsMembers = client.users.suggestions.slug(slugValue).members.json.get();
```

```javascript
assert.equal( suggestionsMembers.status, 200 );
```

Report the specified user as a spam account to Twitter. Additionally performs
the equivalent of POST blocks/create on behalf of the authenticated user.
One of parameters must be provided.

```javascript
reportSpam = client.users.report_spam.json.post({
  "screen_name":"noradio"
});
```

```javascript
assert.equal( reportSpam.status, 200 );
```