---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/portal/pages/6292/preview
apiNotebookVersion: 1.1.66
title: Users
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



Credentials of the test user.



```javascript

testUserName = "Test User"

testUserPassword = "testuserpassword"

```

```javascript

// Read about the Parse API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7571/versions/7694/definition');

```



Get all users, optionally filter



```javascript

usersResponse = client.users.get({},{headers:keyHeaders}, false)

```

```javascript

assert.equal(usersResponse.status,200)

```



Delete the test user if he already exists



```javascript

{

  var loginResponse = client.login.get({

    username: testUserName,

    password: testUserPassword

  },{headers:keyHeaders});

  if( loginResponse.status == 200 ){

    

    var userId = loginResponse.body.objectId

    var sessionToken = loginResponse.body.sessionToken;

    client.users.objectId(userId).delete({},{

      headers: {

        "X-Parse-Application-Id": appId,

        "X-Parse-REST-API-Key": apiKey,

        "X-Parse-Session-Token": sessionToken

      }

    })

  }

}

```



Signs up a new user.



```javascript

userCreateResponse = client.users.post({

  username: testUserName,

  password: testUserPassword

}, { headers: keyHeaders })

```

```javascript

assert.equal(userCreateResponse.status,201)

userId = userCreateResponse.body.objectId

```



Retrieve a user by objectId. Response body is a JSON object containing all the user-provided fields except password.



It also contains the createdAt, updatedAt, and objectId fields



```javascript

userResponse = client.users.objectId(userId).get({}, { headers : keyHeaders})

```

```javascript

assert.equal(userResponse.status,200)

assert.equal(userResponse.body.username,testUserName)

```



Updates a user. Any keys you don't specify will remain unchanged, so you can update just a subset of the object's data.





```javascript

updateUserResponse = client.users.objectId(userId).put({

  "phone" : "415-369-6201"

}, { headers : masterHeaders})

```

```javascript

assert.equal(updateUserResponse.status,200)

```



Let's check that the field has indeed obtained the new value.



```javascript

userResponse = client.users.objectId(userId).get({}, { headers : keyHeaders})

assert.equal(userResponse.body.phone,"415-369-6201")

```



Logs in a user to his or her account. Response provides a 'sessionToken' which is used to invoke methods with authorized access



```javascript

loginResponse = client.login.get({

  "username": testUserName,

  "password": testUserPassword

}, { headers:keyHeaders})

```

```javascript

assert.equal(loginResponse.status,200)

sessionToken = loginResponse.body.sessionToken

```



Retrieve the user associated with the session token placed into the 'X-Parse-Session-Token' header.

If the session token is not valid, an error object is returned.



```javascript

currentUserResponse = client.users.me.get({}, {

  headers:{

    "X-Parse-Application-Id": appId,

    "X-Parse-REST-API-Key": apiKey,

    "X-Parse-Session-Token": sessionToken

  }

})

```

```javascript

assert.equal(currentUserResponse.status,200)

assert.equal(currentUserResponse.body.username,testUserName)

```



Update user



```javascript

updateUserResponse = client.users.objectId(userId).put({

  "email" : "coolguy@iloveapps.com"

}, { headers : masterHeaders})

```

```javascript

assert.equal(updateUserResponse.status, 200)

```



Initiates password reset for a users who has email associated with his or her account



```javascript

passwordResetResponse = client.requestPasswordReset.post({

  "email" : "coolguy@iloveapps.com"

}

, {

  headers:keyHeaders

})

```

```javascript

assert.equal(passwordResetResponse.status,200)

```



Delete user by objectId



```javascript

deleteUserResponse = client.users.objectId(userId).delete({}, {

  headers:{

    "X-Parse-Application-Id": appId,

    "X-Parse-REST-API-Key": apiKey,

    "X-Parse-Session-Token": sessionToken

  }

}, false)

```

```javascript

assert.equal(deleteUserResponse.status,200)

```

```javascript

userResponse = client.users.objectId(userId).get({}, { headers : keyHeaders})

assert.equal(userResponse.status,404)

```