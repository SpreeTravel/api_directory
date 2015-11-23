---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/portal/pages/6293/preview
apiNotebookVersion: 1.1.66
title: Roles
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



Credentials of test users.



```javascript

testUserNames = [ "dude", "programmer1", "programmer2" ];



testUserPasswords = {

  dude: "carpet",

  programmer1: "progpass1",

  programmer2: "progpass2"

}

```



We'll use a special map to store IDs of test users.



```javascript

userIDs = {}

```

```javascript

// Read about the Parse API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7571/versions/7694/definition');

```



Delete all test users if they already exist



```javascript

{

  var usersResponse = client.users.get({},{headers:keyHeaders}, false)

  var allUsers = usersResponse.body.results

  for( var ind in allUsers ) {

    

    var user = allUsers[ind]

    var username = user.username;

    var userId = user.objectId;

    if(testUserNames.indexOf(username)>=0){      

    

      var password = testUserPasswords[username];

      if(password){

        

        var loginResponse = client.login.get({

          username: username,

          password: password

        },{headers:keyHeaders});

        

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

  }

}

```



Signs up all test users



```javascript

for( var ind in testUserNames ) {

  

  var username = testUserNames[ind]

  userCreateResponse = client.users.post({

    "username" : username ,

    "password" : testUserPasswords[username]

  }, { headers: keyHeaders })

  assert.equal(userCreateResponse.status,201)

  userIDs[username] = userCreateResponse.body.objectId

}

```



Get all roles, optionally filtered



```javascript

rolesResponse = client.roles.get({}, {

  headers:keyHeaders

})

```

```javascript

assert.equal(rolesResponse.status,200)

```



Let's delete all the test roles that could have been created during previous notebook runs.



```javascript

{

  allRoles = rolesResponse.body.results

  for( var ind in allRoles ){

    var role = allRoles[ind]

    var roleId = role.objectId

    resp = client.roles.objectId(roleId).delete({},{headers:masterHeaders});

    assert.equal(resp.status,200)

  }

}

```



Create a new role



```javascript

roleResponse = client.roles.post({

  "name" : "Programmer" ,

  "ACL" : {

    "*" : {

      "read" : true,

      "write" : true

    }

  }

}, {

  headers: keyHeaders

})

```

```javascript

assert.equal(roleResponse.status,201)

programmerRoleId = roleResponse.body.objectId

```



You can create a role with child roles or users by adding existing objects to the roles and users relations:



```javascript

moderatorRoleResponse = client.roles.post({

  "name" : "Moderator" ,

  "ACL" : {

    "*" : {

      "read" : true

    }

  } ,

  "roles" : {

    "__op" : "AddRelation" ,

    "objects" : [ {

      "__type" : "Pointer" ,

      "className" : "_Role" ,

      "objectId" : programmerRoleId

    } ]

  } ,

  "users" : {

    "__op" : "AddRelation" ,

    "objects" : [ {

      "__type" : "Pointer" ,

      "className" : "_User" ,

      "objectId" : userIDs["dude"]

    } ]

  }

}

, {

  headers: keyHeaders

})

```

```javascript

assert.equal(moderatorRoleResponse.status,201)

moderatorRoleId = moderatorRoleResponse.body.objectId

```



Retrieve role by objectId



```javascript

roleResponse = client.roles.objectId(moderatorRoleId).get({}, { headers: keyHeaders })

```

```javascript

assert.equal(roleResponse.status,200)

assert.equal(roleResponse.body.name, "Moderator")

```



Update roles



```javascript

updateRoleResponse = client.roles.objectId(programmerRoleId).put({

  "users" : {

    "__op" : "AddRelation" ,

    "objects" : [ {

      "__type" : "Pointer" ,

      "className" : "_User" ,

      "objectId" : userIDs["programmer1"]

    } , {

      "__type" : "Pointer" ,

      "className" : "_User" ,

      "objectId" : userIDs["programmer1"]

    } ]

  }

}, {headers: keyHeaders})

```

```javascript

assert.equal(updateRoleResponse.status,200)

```



Query roles. List roles for the "programmer1" user



```javascript

queryRolesResponse = client.roles.get({

  "where":{

    "users": {

      "__type": "Pointer",

      "className":"_User",

      "objectId": userIDs["programmer1"]

    }

  }

}, {headers: keyHeaders})

```

```javascript

assert.equal(queryRolesResponse.status,200)

assert(queryRolesResponse.body.results.length>0)

```



Delete role. Allows both authorized and master access



```javascript

deleteRoleResponse = client.roles.objectId(moderatorRoleId).delete({}, {headers: masterHeaders})

```

```javascript

assert.equal(deleteRoleResponse.status,200)

```



Let's check that the role has indeed been deleted.



```javascript

roleResponse = client.roles.objectId(moderatorRoleId).get({}, { headers: keyHeaders })

assert.equal(roleResponse.status,404)

```