---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6941/preview
apiNotebookVersion: 1.1.66
title: Twitter
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
DOMAIN = prompt("Please, enter your Zendesk subdomain.")
CLIENT_ID = prompt("Please, enter ID of your Zendesk client.")
CLIENT_SECRET = prompt("Please, enter Secret of your Zendesk client.")
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8077/versions/8266/definition',{
  baseUriParameters: {
    domain: DOMAIN,
    version: 'v2'
}});
```

```javascript
API.set(client, 'baseUriParameters', {
  domain: DOMAIN,
  version: 'v2'
})
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Crete twitter client

```javascript
API.createClient('twitterClient', '/apiplatform/repository/public/organizations/30/apis/8026/versions/8192/definition');
```

```javascript
API.authenticate(twitterClient);
```

Listing Monitored Twitter Handle

```javascript
monitoredTwitterHandlesResponse = client.channels.twitter.monitored_twitter_handles.json.get()
```

```javascript
assert.equal( monitoredTwitterHandlesResponse.status, 200 )
ID_HANDLE = monitoredTwitterHandlesResponse.body.monitored_twitter_handles[0].id
```

```javascript
tweetId = null
{
  var tweetsResponse = twitterClient.statuses.user_timeline.json.get({count:20})
  tweets = tweetsResponse.body
  msg = "Please, select one of the tweets by entering it's index.\nKeep in mind that each tweet can be used only once.\n"
  for(var ind in tweets){
    var tweet = tweets[ind]
    msg += "\n" + ind + ": " + tweet.created_at + "\n" + tweet.text + "\n"
  }
  var ind = prompt(msg)
  tweetId = tweets[ind].id_str
}
```

This end-point allows you to turn a tweet into a ticket. You must provide the tweet id as well as the id of a monitored twitter handle configured for your account.
 Allowed For: [Agents]

```javascript
ticketsCreateResponse = client.channels.twitter.tickets.json.post({
  "ticket" : {
    "twitter_status_message_id" : tweetId ,
    "monitored_twitter_handle_id" : ID_HANDLE,
    "external_id" : tweetId
  }
})
```

```javascript
assert.equal( ticketsCreateResponse.status, 201 )
ID_TWITTER_TICKET = ticketsCreateResponse.body.ticket.id
```

Getting Monitored Twitter Handle

```javascript
handleResponse = client.channels.twitter.monitored_twitter_handles.id(ID_HANDLE).json.get()
```

```javascript
assert.equal( handleResponse.status, 200 )
```

This end-point allows you to turn a tweet into a ticket. You must provide the tweet id as well as the id of a monitored twitter handle configured for your account.
 Allowed For: [Agents]

```javascript
statusesResponse = client.channels.twitter.tickets.id(ID_TWITTER_TICKET).statuses.json.get()
```

```javascript
assert.equal( statusesResponse.status, 200 )
```

Garbage collection. Delete ticket.

```javascript
client.tickets.id(ID_TWITTER_TICKET).json.delete()
```