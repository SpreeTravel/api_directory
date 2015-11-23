---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8021/versions/8188/portal/pages/6851/preview
apiNotebookVersion: 1.1.66
title: Twitch.tv API notebook
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

CLIENT_ID = prompt("Please, enter clientId of your Twitch application."),

CLIENT_SECRET = prompt("Please, enter clientSecret of your Twitch application.")

```

```javascript

// Read about the Twitch RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8021/versions/8188/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8021/versions/8188/definition');

```

```javascript

API.authenticate(client,"oauth_2_0",{

  'clientId' : CLIENT_ID,

  'clientSecret' : CLIENT_SECRET

})

```

```javascript

ACCESS_TOKEN = $4.accessToken

```

```javascript

function modifyRequest(request){

  request.setRequestHeader("Authorization", "OAuth " + ACCESS_TOKEN );

}

```

```javascript

API.set(client, 'beforeSend', new function(){return modifyRequest})

```



Name of the authenticated user



```javascript

USER_NAME = client.get().body.token.user_name

```



User name which is operated by the notebook. This user will be added into the list of blocked users and then removed from it.



```javascript

BLOCKED_USER = "RamltestNotebook"

```



Channel name which is operated by the notebook.



```javascript

CHANNEL_NAME = "applejacked"

```



Team name which is operated by the notebook.



```javascript

TEAM = "testteam"

```



Basic information about the API and authentication status. If you are authenticated, the response includes the status of your token and links to other related resources.



```javascript

rootResponse = client.get()

```

```javascript

assert.equal(rootResponse.status,200)

```



Returns a channel object



```javascript

channelsChannelResponse = client.channels.channelId(USER_NAME).get()

```

```javascript

assert.equal( channelsChannelResponse.status, 200 )

```



Update channel's status or game



```javascript

channelsChannelUpdateResponse = client.channels.channelId(USER_NAME).put({

  "channel" : {

    "status" : "Playing cool new game!" ,

    "game" : "Diablo45"

  }

})

```

```javascript

assert.equal( channelsChannelUpdateResponse.status, 200 )

```



Returns a list of user objects who are editors of :channel



```javascript

editorsResponse = client.channels.channelId(USER_NAME).editors.get()

```

```javascript

assert.equal( editorsResponse.status, 200 )

```





Adds :target to :user's block list. :user is the authenticated user and :target is user to be blocked. Returns a blocks object



```javascript

targetUpdateResponse = client.users.userId(USER_NAME).blocks.targetId(BLOCKED_USER).put()

```

```javascript

assert.equal( targetUpdateResponse.status, 200 )

```



Adds :user to :target's followers. :user is the authenticated user's name and :target is the name of the channel to be followed



```javascript

targetUpdateResponse = client.users.userId(USER_NAME).follows.channels.targetId(CHANNEL_NAME).put()

```

```javascript

assert.equal( targetUpdateResponse.status, 200 )

```



Returns an list of videos ordered by time of creation, starting with the most recent from :channel



```javascript

videosResponse = client.channels.channelId(CHANNEL_NAME).videos.get()

```

```javascript

assert.equal( videosResponse.status, 200 )

ANOTHER_VIDEO = client.channels.channelId(CHANNEL_NAME).videos.get().body.videos[0]._id

```



Get channel's list of following user



```javascript

followsResponse = client.channels.channelId(CHANNEL_NAME).follows.get()

```

```javascript

assert.equal( followsResponse.status, 200 )

```



Resets channel's stream key



```javascript

streamKeyDeleteResponse = client.channels.channelId(USER_NAME).stream_key.delete({})

```

```javascript

assert.equal( streamKeyDeleteResponse.status, 200 )

```



Start a commercial on channel. You may only trigger a commercial longer than 30 seconds once every 8 minutes.

422 is returned if commercial break has been triggered less then 8 min ago.



```javascript

commercialCreateResponse = client.channels.channelId(USER_NAME).commercial.post({length:30})

```

```javascript

assert( commercialCreateResponse.status == 204 || commercialCreateResponse.status == 422)

```



Returns a list of subscription objects sorted by subscription relationship creation date which contain users subscribed to :channel

422 if :user does not have a subscription program



```javascript

subscriptionsResponse = client.channels.channelId(USER_NAME).subscriptions.get()

```

```javascript

assert( subscriptionsResponse.status == 200 || subscriptionsResponse.status == 422)

```



Returns a subscription object which includes the user if that user is subscribed. Requires authentication for :channel

404 if :user is not subscribed.

422 if :user does not have a subscription program



```javascript

userResponse = client.channels.channelId(USER_NAME).subscriptions.userId(USER_NAME).get()

```

```javascript

assert( userResponse.status == 200 ||  userIdResponse.status == 404 || userIdResponse.status == 422)

```



Returns a video object



```javascript

videoResponse = client.videos.videoId(ANOTHER_VIDEO).get()

```

```javascript

assert.equal( videoResponse.status, 200 )

```



Returns a list of videos created in a given time period sorted by number of views, most popular first



```javascript

topResponse = client.videos.top.get({

  "game" : "Diablo",

  "period" : "month"})

```

```javascript

assert.equal( topResponse.status, 200 )

```



Returns a link's object to all other chat endpoints



```javascript

channelResponse = client.chat.channelId(CHANNEL_NAME).get()

```

```javascript

assert.equal( channelResponse.status, 200 )

```



[DEPRECATED] - Emoticon sets are no longer tied to specific channels, so information returned from this endpoint is somewhat useless.

Returns a list of emoticon objects that can be used in the :channel's chat.



```javascript

emoticonsResponse = client.chat.channelId(CHANNEL_NAME).emoticons.get()

```

```javascript

assert.equal( emoticonsResponse.status, 200 )

```



Returns a list of all emoticon objects for Twitch



```javascript

emoticonsResponse = client.chat.emoticons.get()

```

```javascript

assert.equal( emoticonsResponse.status, 200 )

```



Returns a list of stream objects that are queried by a number of parameters sorted by number of viewers descending



```javascript

streamsResponse = client.streams.get()

```

```javascript

assert.equal( streamsResponse.status, 200 )

```



Returns a list of stream objects that the authenticated user is following



```javascript

followedResponse = client.streams.followed.get()

```

```javascript

assert.equal( followedResponse.status, 200 )

```



Returns a stream object if live



```javascript

channelResponse = client.streams.channelId(CHANNEL_NAME).get()

```

```javascript

assert.equal( channelResponse.status, 200 )

```



Returns a list of featured (promoted) stream objects



```javascript

featuredResponse = client.streams.featured.get()

```

```javascript

assert.equal( featuredResponse.status, 200 )

```



Returns a summary of current streams



```javascript

summaryResponse = client.streams.summary.get()

```

```javascript

assert.equal( summaryResponse.status, 200 )

```



Returns a list of active teams





```javascript

teamsResponse = client.teams.get()

```

```javascript

assert.equal( teamsResponse.status, 200 )

```



Returns a team object for :team



```javascript

teamsResponse = client.teams.teamsId(TEAM).get()

```

```javascript

assert.equal( teamsResponse.status, 200 )

```



Returns a user object.



```javascript

userResponse = client.user.get()

```

```javascript

assert.equal(userResponse.status,200)

```



Returns a user object





```javascript

usersUserResponse = client.users.userId(USER_NAME).get()

```

```javascript

assert.equal( usersUserResponse.status, 200 )

```



Returns a list of follows objects



```javascript

followsChannelsResponse = client.users.userId(USER_NAME).follows.channels.get()

```

```javascript

assert.equal( followsChannelsResponse.status, 200 )

```



Returns a channel object that user subscribes to. Requires authentication for :user

 404 if user doesn't subscripted



```javascript

subscriptionsChannelResponse = client.users.userId(USER_NAME).subscriptions.channelId(CHANNEL_NAME).get()

```

```javascript

assert( subscriptionsChannelResponse.status == 200 || subscriptionsChannelResponse.status == 404 )

```





Returns a list of games objects sorted by number of current viewers on Twitch, most popular first



```javascript

gamesTopResponse = client.games.top.get()

```

```javascript

assert.equal( gamesTopResponse.status, 200 )

```



Returns a list of ingest objects



```javascript

ingestsResponse = client.ingests.get()

```

```javascript

assert.equal( ingestsResponse.status, 200 )

```



Returns a list of stream objects matching the search query



```javascript

streamsResponse = client.search.streams.get({"q" : "starcraft"})

```

```javascript

assert.equal( streamsResponse.status, 200 )

```



Returns a list of game objects matching the search query



```javascript

gamesResponse = client.search.games.get({

  "query": "starcraft",

  "type": "suggest"

} )

```

```javascript

assert.equal( gamesResponse.status, 200 )

```





Returns a list of blocks objects on login's block list. List sorted by recency, newest first



```javascript

blocksResponse = client.users.userId(USER_NAME).blocks.get()

```

```javascript

assert.equal( blocksResponse.status, 200 )

```



Removes :target from :user's block list. :user is the authenticated user and :target is user to be unblocked



```javascript

targetDeleteResponse = client.users.userId(USER_NAME).blocks.targetId(BLOCKED_USER).delete({})

```

```javascript

assert.equal( targetDeleteResponse.status, 204 )

```



Check if the user follows the channel



```javascript

targetResponse = client.users.userId(USER_NAME).follows.channels.targetId(CHANNEL_NAME).get()

```

```javascript

assert.equal( targetResponse.status, 200 )

```



Removes :user from :target's followers. :user is the authenticated user's name and :target is the name of the channel to be unfollowed



```javascript

targetDeleteResponse = client.users.userId(USER_NAME).follows.channels.targetId(CHANNEL_NAME).delete({})

```

```javascript

assert.equal( targetDeleteResponse.status, 204 )

```