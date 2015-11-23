---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8026/versions/8192/portal/pages/6862/preview
apiNotebookVersion: 1.1.66
title: Saved Searches, Geo, Trends, Help, Apps
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js');
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
var assert = chai.assert;
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8026/versions/8192/definition');
```

```javascript
API.authenticate(client);
```

Create set of saved searches

```javascript
savedSearchesList = client.saved_searches.list.json.get();
```

```javascript
needSandviches=true;
needBananas=true;
for (v in savedSearchesList.body){
  if (v.query="sandwiches123"){needSandviches=false}
  if (v.query="bananas123"){needBananas=false}
}

```

```javascript
if (needSandviches){
savedSearchSand = client.saved_searches.create.json.post({
  "query": "sandwiches123"
});
savedSearchSandID = savedSearchSand.body.id_str;  
}
if (needBananas){
savedSearchBan = client.saved_searches.create.json.post({
  "query": "bananas123"
});
savedSearchBanID = savedSearchSand.body.id_str;
}

```

```javascript
if (needSandviches){
assert.equal(savedSearchBan.status, 200);
}
if (needBananas){
assert.equal(savedSearchSand.status, 200);
}
```

Returns the authenticated user's saved search queries.

```javascript
savedSearchesList = client.saved_searches.list.json.get();
```

```javascript
assert.equal( savedSearchesList.status, 200 );
```

Retrieve the information for the saved search represented by the given id.
The authenticating user must be the owner of saved search ID being requested.

```javascript
savedSearchBanID=savedSearchesList.body[0].id
```

```javascript

savedSearchBanShow = client.saved_searches.show.id(savedSearchesList.body[0].id).json.get();
```

```javascript
assert.equal( savedSearchBanShow.status, 200 );
```

Destroys a saved search for the authenticating user. The authenticating
user must be the owner of saved search id being destroyed.

```javascript
savedSearchBanDestroy = client.saved_searches.destroy.id(savedSearchBanID).json.post();
```

```javascript
assert.equal(savedSearchBanDestroy.status, 200);
```

Given a latitude and a longitude

```javascript
placesGet = client.geo.reverse_geocode.json.get({
  "lat":"37.7821120598956",
  "long":"-122.400612831116"
});
```

```javascript
placeGetId = placesGet.body.result.places[0].id;
```

```javascript
assert.equal(placesGet.status, 200);
assert.isNotNull(placeGetId);
```

Returns all the information about a known place

```javascript
placeGetInfo = client.geo.id.place_id(placeGetId).json.get();
```

```javascript
assert.equal(placeGetInfo.status, 200);
```

Search for places that can be attached to a statuses/update. Given a latitude
and a longitude pair, an IP address, or a name, this request will return a
list of all the valid places that can be used as the place_id when updating
a status.
Conceptually, a query can be made from the user's location, retrieve a lis
of places, have the user validate the location he or she is at, and then
send the ID of this location with a call to POST statuses/update.
This is the recommended method to use find places that can be attached to
statuses/update. Unlike GET geo/reverse_geocode which provides raw data
access, this endpoint can potentially re-order places with regards to the
user who is authenticated. This approach is also preferred for interactive
place matching with the user.

```javascript
placeSearch = client.geo.search.json.get({
	"lat":"37.7821120598956",
  "long":"-122.400612831116"
});
```

```javascript
assert.equal( placeSearch.status, 200 );
```

Locates places near the given coordinates which are similar in name.
Conceptually you would use this method to get a list of known places to choose from first. Then, if the desired place doesn't exist, make a request to POST geo/place to create a new one.
The token contained in the response is the token needed to be able to create a new place.

```javascript
placesSimilar = client.geo.similar_places.json.get({
  "name": "Twitter%20HQ",
  "lat":"37.7821120598956",
  "long":"-122.400612831116"
});
```

```javascript
assert.equal(placesSimilar.status, 200);
```

Creates a new place object at the given latitude and longitude. - No longer supported by twitter for third party applications

```javascript
/*placeCreate = client.geo.place.json.post({
  "name": "nameValue",
  "token": "tokenValue",
  "lat": "latValue",
  "long": "longValue"
});*/
```

```javascript
//assert.equal( place{mediaTypeExtension}Response.status, 200 )
```

Returns the top 10 trending topics for a specific WOEID, if trending information
is available for it.
The response is an array of "trend" objects that encode the name of the
trending topic, the query parameter that can be used to search for the topic
on Twitter Search, and the Twitter Search URL.
This information is cached for 5 minutes. Requesting more frequently than
that will not return any more data, and will count against your rate limit usage.

```javascript
placeTrends = client.trends.place.json.get({
  "id": "1"
});
```

```javascript
assert.equal(placeTrends.status, 200);
```

Returns the locations that Twitter has trending topic information for.
The response is an array of "locations" that encode the location's WOEID
and some other human-readable information such as a canonical name and
country the location belongs in.
A WOEID is a Yahoo! Where On Earth ID.

```javascript
trendsAvailable = client.trends.available.json.get();
```

```javascript
assert.equal(trendsAvailable.status, 200);
```

Returns the locations that Twitter has trending topic information for,
closest to a specified location.
The response is an array of "locations" that encode the location's WOEID
and some other human-readable information such as a canonical name and
country the location belongs in.
A WOEID is a Yahoo! Where On Earth ID.

```javascript
trendsClosest = client.trends.closest.json.get({
  "lat":"37.7821120598956",
  "long":"-122.400612831116"
});
```

```javascript
assert.equal(trendsClosest.status, 200);
```

Returns the current configuration used by Twitter including twitter.com
slugs which are not usernames, maximum photo resolutions, and t.co URL
lengths.
It is recommended applications request this endpoint when they are loaded,
but no more than once a day.

```javascript
helpConfiguration = client.help.configuration.json.get();
```

```javascript
assert.equal(helpConfiguration.status, 200);
```

Returns the list of languages supported by Twitter along with their ISO 639-1
code. The ISO 639-1 code is the two letter value to use if you include lang
with any of your requests.

```javascript
helpLanguages = client.help.languages.json.get();
```

```javascript
assert.equal(helpLanguages.status, 200);
```

Returns Twitter's Privacy Policy

```javascript
helpPrivacy = client.help.privacy.json.get();
```

```javascript
assert.equal(helpPrivacy.status, 200);
```

Returns the Twitter Terms of Service in the requested format. These are no
the same as the Developer Rules of the Road.

```javascript
helpTos = client.help.tos.json.get();
```

```javascript
assert.equal(helpTos.status, 200);
```

Returns the current rate limits for methods belonging to the specified
resource families.

```javascript
limitStatus = client.application.rate_limit_status.json.get();
```

```javascript
assert.equal(limitStatus.status, 200);
```