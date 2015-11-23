---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/portal/pages/6294/preview
apiNotebookVersion: 1.1.66
title: Files, events, push notifications, installations
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



Prepare authentication data.



```javascript

appId = prompt("Please, enter Id of your Parse application");

apiKey = prompt("Please, enter REST Api Key of your Parse application");

masterKey = prompt("Please, enter Master Key of your Parse application");



keyHeaders = {

  "X-Parse-Application-Id": appId,

  "X-Parse-REST-API-Key": apiKey

}



masterHeaders = {

  "X-Parse-Application-Id": appId,

  "X-Parse-Master-Key": masterKey

}

```

```javascript

// Read about the Parse API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7571/versions/7694/definition');

```





To upload a file to Parse, send a POST request to the files URL, postfixed with the name of the file.

The request must contain the Content-Type header associated with the file.

Keep in mind that files are limited to 10 megabytes



```javascript

uploadFileResponse = client.files.fileName("MyFile.txt").post("Test file content", { headers: keyHeaders })

```

```javascript

assert.equal(uploadFileResponse.status,201)

```



Starts to collect data on when and how often the application is opened



```javascript

appOpenedResponse = client.events.AppOpened.post({

  "at" : {

    "__type" : "Date" ,

    "iso" : "2013-12-21T01:37:25Z"

  }

}, { headers : keyHeaders})

```

```javascript

assert.equal(appOpenedResponse.status,200)

```



Start event track



```javascript

startEventTrackResponse = client.events.eventName("MyEvent").post({

  "at" : {

    "__type" : "Date" ,

    "iso" : "2013-12-21T01:37:25Z"

  } ,

  "dimensions" : {

    "priceRange" : "1000-1500" ,

    "source" : "craigslist" ,

    "dayType" : "weekday"

  }

}, { headers: keyHeaders})

```

```javascript

assert.equal(startEventTrackResponse.status,200)

```



We shall need an auxiliary function to set **pushTime** for a push notification.



```javascript

function generateDate(secIncr){



  var date = new Date( new Date().getTime() + secIncr*1000 );

  

  var dd = date.getDate();

  var mm = date.getMonth()+1; //January is 0!

  var hh = date.getHours();

  var min = date.getMinutes();

  var sec = date.getSeconds();

  

  var yyyy = date.getFullYear();

  if(dd<10){dd='0'+dd}

  if(mm<10){mm='0'+mm}

  if(hh<10){hh='0'+hh}

  if(min<10){min='0'+min}

  if(sec<10){sec='0'+sec}

  //"2013-12-21T23:47:14Z"

  var result = yyyy+'-'+mm+'-'+dd+'T'+hh+':'+min+':'+sec+'Z';

  return result;

}

```

```javascript

pushDate = generateDate(60*60*24)

```



Send a push notifies



```javascript

pushResponse = client.push.post({

  "where" : {

    "deviceType" : "android"

  } ,

  "push_time" : pushDate ,

  "expiration_interval" : 45634 ,

  "data" : {

    "alert" : "Season tickets on sale until December 27, 2013"

  }

}, { headers: keyHeaders })

```

```javascript

assert.equal(pushResponse.status,200)

```



We are going to test installation related part of the Parse API.

Installation object created by the present notebook can identified by the special **deviceToken** value:



```javascript

notebookDeviceToken =  "0000111122223333444455556666777788889999aaaabbbbccccddddeeeeffff"

```



Get all installations, optionally filtered



```javascript

installationsResponse = client.installations.get({}, { headers : masterHeaders });

```

```javascript

assert.equal(installationsResponse.status,200)

```



Let's delete an installation which could have been created by the notebook earlier.



```javascript

installationsResponse.body.results.forEach(function(inst){

  var insId = inst.objectId

  var deviceToken = inst.deviceToken

  if(deviceToken == notebookDeviceToken){

    resp = client.installations.objectId(insId).delete({}, {headers: masterHeaders})

  }

})

```



Create a new installation



```javascript

createInstallationResponse = client.installations.post({

  "deviceType" : "ios" ,

  "deviceToken" : notebookDeviceToken,

  "channels" : [ "" ]

}, {

  headers : keyHeaders

})

```

```javascript

assert.equal(createInstallationResponse.status,201)

instId = createInstallationResponse.body.objectId

```



Retrieve installation by objectID



```javascript

installationResponse = client.installations.objectId(instId).get({}, {headers:keyHeaders})

```

```javascript

assert.equal(installationResponse.status,200)

```



Update installation



```javascript

updateInstallationResponse = client.installations.objectId(instId).put({

  "deviceType" : "ios" ,

  "deviceToken" : notebookDeviceToken ,

  "channels" : [ "" , "foo" ]

},{ headers: keyHeaders})

```

```javascript

assert.equal(updateInstallationResponse.status,200)

```



Delete installation by objectId



```javascript

deleteInstallationResponse = client.installations.objectId(instId).delete({}, {headers: masterHeaders})

```

```javascript

assert.equal(deleteInstallationResponse.status,200)

```



Let's check that the installation has indeed been deleted.



```javascript

installationResponse = client.installations.objectId(instId).get({}, {headers:keyHeaders})

assert.equal(installationResponse.status,404)

```