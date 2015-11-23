---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7717/versions/7851/portal/pages/6610/preview
apiNotebookVersion: 1.1.66
title: Bufferapp API Notebook
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



Auxiliary method for retrieving current time



```javascript

function getTime(){

  var today = new Date();

  var dd = today.getDate();

  var mm = today.getMonth()+1; //January is 0!

  var yyyy = today.getFullYear();

  

  var hh = today.getHours();

  var min = today.getMinutes();

  var sec = today.getSeconds();  

  

  if(dd<10){dd='0'+dd}

  if(mm<10){mm='0'+mm}

  if(hh<10){hh='0'+hh}

  if(min<10){min='0'+min}

  if(sec<10){sec='0'+sec}

  today = mm + '/' + dd + '/' + yyyy + ' ' + hh + ":" + min+':' + sec;

  return today;

}

```

```javascript

clientId = prompt("Please, enter clientId of your BufferApp application");

clientSecret = prompt("Please, enter clientSecret of your BufferApp application");

```

```javascript

// Read about the Bufferapp API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7717/versions/7851/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7717/versions/7851/definition');

```

```javascript

API.authenticate(client,"oauth_2_0",{

  clientId: clientId,

  clientSecret: clientSecret

})

```



Returns a single user



```javascript

currentUserResponse = client.user.json.get()

```

```javascript

assert.equal( currentUserResponse.status, 200 )

```



Returns an array of social media profiles connected to a users account



```javascript

profilesResponse = client.profiles.json.get()

```

```javascript

assert.equal( profilesResponse.status, 200 )

profileId = profilesResponse.body[0].id

```



Return single profile



```javascript

profileResponse = client("/profiles/{id}{mediaTypeExtension}", {

  "id": profileId,

  "mediaTypeExtension": ".json"

}).get()

```

```javascript

assert.equal( profileResponse.status, 200 )

```



Returns details of the posting schedules associated with a social media profile



```javascript

schedulesResponse = client.profiles.id( profileId ).schedules.json.get()

```

```javascript

assert.equal( schedulesResponse.status, 200 )

```



Set the posting schedules for the specified social media profile.



```javascript

schedulesUpdateResponse = client.profiles.id( profileId ).schedules.update.json.post({

  "schedules[0][days][]": "mon"    

})

```

```javascript

assert.equal( schedulesUpdateResponse.status, 200 )

```



Returns an array of updates that are currently in the buffer for an individual social media profile.



```javascript

pendingResponse = client.profiles.id( profileId ).updates.pending.json.get()

```

```javascript

assert.equal( pendingResponse.status, 200 )

```



Returns an array of updates that have been sent from the buffer for an individual social media profile.



```javascript

sentResponse = client.profiles.id( profileId ).updates.sent.json.get()

```

```javascript

assert.equal( sentResponse.status, 200 )

```



Edit the order at which statuses for the specified social media profile will be sent out of the buffer.



```javascript

reorderResponse = client.profiles.id( profileId ).updates.reorder.json.post({

  "order[]": "4eb854340acb04e870000010"

})

```

```javascript

assert.equal( reorderResponse.status, 200 )

```



Randomize the order at which statuses for the specified social media profile will be sent out of the buffer.



```javascript

shuffleResponse = client.profiles.id( profileId ).updates.shuffle.json.post()

```

```javascript

assert.equal( shuffleResponse.status, 200 )

```



Create one or more new status updates.



```javascript

createUpdateResponse = client.updates.create.json.post({

  "text": "Notebook Status Update " + getTime(),

  "profile_ids[]": [ profileId ]

})

```

```javascript

assert.equal( createUpdateResponse.status, 200 )

updateId = createUpdateResponse.body.updates[0].id

```



Returns a single social media update



```javascript

updatesResponse = client("/updates/{id}{mediaTypeExtension}", {

  "id": updateId,

  "mediaTypeExtension": ".json"

}).get()

```

```javascript

assert.equal( updatesResponse.status, 200 )

```



Returns the detailed information on individual interactions with the social media update such as favorites, retweets and likes.



```javascript

interactionsResponse = client.updates.id( updateId ).interactions.json.get({

  "event": "like"

})

```

```javascript

assert.equal( interactionsResponse.status, 200 )

```



Edit an existing, individual status update.



```javascript

postUpdateResponse = client.updates.id( updateId ).update.json.post({

  "text": "Notebook Status Update (upd) " + getTime()

})

```

```javascript

assert.equal( postUpdateResponse.status, 200 )

```



Move an existing status update to the top of the queue and recalculate times for all updates in the queue. Returns the update with its new posting time.



```javascript

move_to_topResponse = client.updates.id( updateId ).move_to_top.json.post()

```

```javascript

assert.equal( move_to_topResponse.status, 200 )

```



Immediately shares a single pending update and recalculates times for updates remaining in the queue



```javascript

shareUpdateResponse = client.updates.id( updateId ).share.json.post()

```

```javascript

assert.equal( shareUpdateResponse.status, 200 )

```



An update becomes unavailable for edit after being shared. Thus, we need another update in order to test update destroying.



```javascript

updateId2 = client.updates.create.json.post({

  "text": "Notebook Status Update " + getTime(),

  "profile_ids[]": [ profileId ]

}).body.updates[0].id

```



Permanently delete an existing status update.



```javascript

destroyResponse = client.updates.id( updateId2 ).destroy.json.post()

```

```javascript

assert.equal( destroyResponse.status, 200 )

```



Returns an object with a the numbers of shares a link has had using Buffer



```javascript

linksSharesResponse = client.links.shares.json.get({

  "url": "http://www.google.com"

})

```

```javascript

assert.equal( linksSharesResponse.status, 200 )

```



Returns an object with the current configuration that Buffer is using, including supported services, their icons and the varying limits of character and schedules



```javascript

infoConfigurationResponse = client.info.configuration.json.get()

```

```javascript

assert.equal( infoConfigurationResponse.status, 200 )

```