---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8026/versions/8192/portal/pages/6863/preview
apiNotebookVersion: 1.1.66
title: Status
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

Returns a collection of the 100 most recent retweets of the tweet specified
by the id parameter.

```javascript
retweetsCollection = client.statuses.retweets_of_me.json.get()
```

```javascript
searchTweets=client.search.tweets.json.get({q:"Kiev"});
```

Returns a collection of the 100 most recent retweets of the tweet specified by the id parameter.

```javascript
retweetsCollection = client.statuses.retweets.id(searchTweets.body.statuses[0].id_str).json.get();
```

```javascript
assert.equal( retweetsCollection.status, 200 )
```

Updates the authenticating user's current status and attaches media for
upload. In other words, it creates a Tweet with a picture attached.
Unlike POST statuses/update, this method expects raw multipart data. Your
POST request's Content-Type should be set to multipart/form-data with the
media[] parameter
The Tweet text will be rewritten to include the media URL(s), which will
reduce the number of characters allowed in the Tweet text. If the URL(s)
cannot be appended without text truncation, the tweet will be rejected and
this method will return an HTTP 403 error.

```javascript
//Commented because of https://github.com/mulesoft/api-notebook/issues/366
fd=new FormData();
fd.append("status","A");
//fd.append("media[]",new Blob(),"mm.jpg");
update_with_mediaResponse = client.statuses.update_with_media.json.post(fd,{"Headers":{"Content-Type":"multipart/formdata"}});
```

```javascript
//assert.equal( update_with_mediaResponse.status, 200 );
```

Returns the 20 most recent mentions (tweets containing a users's @screen_name)
for the authenticating user.
The timeline returned is the equivalent of the one seen when you view your
mentions on twitter.com.
This method can only return up to 800 tweets.

```javascript
mentions_timelineResponse = client.statuses.mentions_timeline.json.get();
```

```javascript
assert.equal( mentions_timelineResponse.status, 200 );
```

Returns information allowing the creation of an embedded representation
of a Tweet on third party sites. See the oEmbed specification for information
about the response format.
While this endpoint allows a bit of customization for the final appearance
of the embedded Tweet, be aware that the appearance of the rendered Tweet may
change over time to be consistent with Twitter's Display Requirements. Do no
rely on any class or id parameters to stay constant in the returned markup.

```javascript
oembedResponse = client.statuses.oembed.json.get({
  "id": "99530515043983360",
  "url": "https%3A%2F%2Ftwitter.com%2F%23!%2Ftwitter%2Fstatus%2F99530515043983360"
});
```

```javascript
assert.equal( oembedResponse.status, 200 )
```

Returns a collection of up to 100 user IDs belonging to users who have
retweeted the tweet specified by the id parameter.

```javascript
retweetersidsResponse = client.statuses.retweeters.ids.json.get({
  "id": "99530515043983360"
})
```

```javascript
assert.equal( retweetersidsResponse.status, 200 )
```

Updates the authenticating user's current status, also known as tweeting.
To upload an image to accompany the tweet, use `POST statuses/update_with_media`.
For each update attempt, the update text is compared with the authenticating
user's recent tweets. Any attempt that would result in duplication will be
blocked, resulting in a 403 error. Therefore, a user cannot submit the same
status twice in a row.
While not rate limited by the API a user is limited in the number of tweets
they can create at a time. If the number of updates posted by the user reaches
the current allowed limit this method will return an HTTP 403 error.

```javascript
updateResponse = client.statuses.update.json.post({
  "status": "status"+new Date()
})
```

```javascript
assert.equal( updateResponse.status, 200 )
```

Returns a single Tweet, specified by the id parameter. The Tweet's author
will also be embedded within the tweet.

```javascript
tweetShow = client.statuses.show.id(updateResponse.body.id_str).json.get()
```

```javascript
assert.equal( tweetShow.status, 200 )
```

Destroys the status specified by the required ID parameter. The authenticating
user must be the author of the specified status. Returns the destroyed status
if successful.

```javascript
retweetDestroy = client.statuses.destroy.id(updateResponse.body.id_str).json.post();
```

```javascript
assert.equal(retweetDestroy.status,200)
```

This endpoint requires special permission to access.
Returns all public statuses. Few applications require this level of access.
Creative use of a combination of other resources and various access levels
can satisfy nearly every application use case.

```javascript
//we do not have access to it
//firehoseResponse = client.statuses.firehose.get();
```

```javascript
//assert.equal( firehoseResponse.status, 200 );
```

Retweets a tweet. Returns the original tweet with retweet details embedded.

```javascript
retweetPost = client.statuses.retweet.id("99530515043983360").json.post();
```

```javascript
if (retweetPost.status==403&&retweetPost.body.errors=="sharing is not permissible for this status (Share validations failed)"){
  //this user already retweeted this tweet or this user is original author ot the tweet
}
else assert.equal( retweetPost.status, 200 );
```

Returns the most recent tweets authored by the authenticating user tha
have been retweeted by others. This timeline is a subset of the user's GET
statuses/user_timeline.

```javascript
retweetsMeResponse = client.statuses.retweets_of_me.json.get();
```

```javascript
assert.equal( retweetsMeResponse.status, 200 );
```

Returns a collection of the most recent Tweets and retweets posted by the
authenticating user and the users they follow. The home timeline is central
to how most users interact with the Twitter service.
Up to 800 Tweets are obtainable on the home timeline. It is more volatile
for users that follow many users or follow users who tweet frequently.
See Working with Timelines for instructions on traversing timelines efficiently.

```javascript
home_timelineResponse = client.statuses.home_timeline.json.get();
```

```javascript
assert.equal( home_timelineResponse.status, 200 );
```

Returns a collection of the most recent Tweets posted by the user indicated
by the screen_name or user_id parameters.
User timelines belonging to protected users may only be requested when the
authenticated user either "owns" the timeline or is an approved follower of
the owner.
The timeline returned is the equivalent of the one seen when you view a user's
profile on twitter.com.
This method can only return up to 3,200 of a user's most recent Tweets. Native
retweets of other statuses by the user is included in this total, regardless
of whether include_rts is set to false when requesting this resource.

```javascript
user_timelineResponse = client.statuses.user_timeline.json.get();
```

```javascript
assert.equal( user_timelineResponse.status, 200 )
```