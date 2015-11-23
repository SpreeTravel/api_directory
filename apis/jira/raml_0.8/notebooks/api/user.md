---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6624/edit
apiNotebookVersion: 1.1.66
title: User
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

PROTOCOL = null

{

  useHttps = window.confirm("Do you want to use 'https' protocol?")

  if(useHttps){

    PROTOCOL = "https"

  }

  else{

    PROTOCOL = "http"

    window.alert("The protocol has been set to 'http'")

  }

}

DOMAIN = prompt("Please, enter your Jira domain. For example, for jira URL 'https://jirahost.atlassian.net' enter 'jirahost.atlassian.net'")

```



Name of a test  user which is operated by the Notebook



```javascript

TEST_USER_NAME = "notebook-test-user"

```



Auxiliary project selecting method



```javascript

function selectProject(){

  

  projects = client.project.get().body

  var message = ''

  message += 'The Notebook needs a project to operate with.\nPlease, select a project from the following list:\n'

  for(var ind in projects){

    message += '\n' + ind + ": " + projects[ind].name + '\n'

  }  

  input = prompt(message)

  if(!input){

    return null

  }

  var parsedInd = Number.parseInt(input)

  if(Number.isInteger(parsedInd)&&parsedInd>=0){

    return projects[parsedInd].key

  }

  return null

}

```



Auxiliary method for retrieving role ID from _GET /role_ response



```javascript

function extractSomeRoleId(roles){

  for(var roleName in roles){

    var url = roles[roleName]

    if(url){

      var ind = url.lastIndexOf('/')

      if(ind>=0){

        var roleName = url.substring(ind+1)

        return roleName

      }

    }

  }

}

```

```javascript

// Read about the Jira API v2 at http://api-portal.anypoint.mulesoft.com/onpositive/api/jira-api-v2

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7868/versions/8022/definition', {baseUriParameters: {

  domain: DOMAIN,

  protocol: PROTOCOL

}});

```

```javascript

API.authenticate(client)

```

```javascript

projectKey = selectProject()

```



Let's delete a user which could have been created during previous notebook runs.



```javascript

client.user.delete({},{query:{username:TEST_USER_NAME}})

```



Create user. By default created user will not be notified with email. If password field is not set then password will be randomly generated



```javascript

userCreateResponse = client.user.post({

  "name" : TEST_USER_NAME, 

  "password" : "aoeuAOEUaoeu1!" ,

  "emailAddress" : "mail@somehost.com" ,

  "displayName" : "Notebok Test User"

})

```

```javascript

assert.equal( userCreateResponse.status, 201 )

```



Returns a user. This resource cannot be accessed anonymously



```javascript

userResponse = client.user.get({username:TEST_USER_NAME})

```

```javascript

assert.equal( userResponse.status, 200 )

```



Modify user. The "value" fields present will override the existing value. Fields skipped in request will not be changed



```javascript

userEditResponse = client.user.put({

  "displayName" : "Notebok Test User (upd)"

},{

  query:{

    "username" : TEST_USER_NAME

  }

})

```

```javascript

assert.equal( userEditResponse.status, 200 )

```

```javascript

rolesResponse = client.project.projectIdOrKey(projectKey).role.get()

roleId = extractSomeRoleId(rolesResponse.body)

```

```javascript

client.project.projectIdOrKey(projectKey).role.id(roleId).post({

  "user" : [

    TEST_USER_NAME

  ]

})

```



Returns a list of users that match the search string and can be assigned issues for all the given projects. This resource cannot be accessed anonymously



```javascript

// NOT SUPPORTED

// multiProjectSearchResponse = client.user.assignable.multiProjectSearch.get({

//   "username" : TEST_USER_NAME

// })

```

```javascript

//assert.equal( multiProjectSearchResponse.status, 200 )

```



Returns a list of users that match the search string. This resource cannot be accessed anonymously. Please note that this resource should be called with an issue key when a list of assignable users is retrieved for editing. For create only a project key should be supplied. The list of assignable users may be incorrect if it's called with the project key for editing



```javascript

searchResponse = client.user.assignable.search.get({

  "username" : TEST_USER_NAME,

  "project" : projectKey

})

```

```javascript

assert.equal( searchResponse.status, 200 )

```



Creates temporary avatar. Creating a temporary avatar is part of a 3-step process in uploading a new avatar for a user: upload, crop, confirm



```javascript

// NOT SUPPORTED

//temporaryResponse = client.user.avatar.temporary.post(picResponse.body,{headers:{"Content-Type":"image/png"}})

```

```javascript

//assert.equal( temporaryResponse.status, 200 )

```



Converts temporary avatar into a real avatar



```javascript

// NOT SUPPORTED

// avatarResponse = client.user.avatar.post({

//   "cropperWidth" : 120 ,

//   "cropperOffsetX" : 50 ,

//   "cropperOffsetY" : 50 ,

//   "needsCropping" : false

// },{

//   query:

//   {

//    username: TEST_USER_NAME

//   }

// })

```

```javascript

//assert.equal( avatarResponse.status, 200 )

```



Update avatar



```javascript

//NOT SUPPORTED

//avatarResponse = client.user.avatar.put({},{query:{username: TEST_USER_NAME}})

```

```javascript

//assert.equal(avatarResponse.status, 200)

```



Deletes avatar



```javascript

// NOT SUPPORTED

//avatarDeleteResponse = client.user.avatar.id("idValue").delete({})

```

```javascript

//assert.equal( avatarDeleteResponse.status, 200 )

```



Returns all avatars which are visible for the currently logged in user



```javascript

avatarsResponse = client.user.avatars.get({username:TEST_USER_NAME})

```

```javascript

assert.equal( avatarsResponse.status, 200 )

```



Returns the default columns for the given user. Admin permission will be required to get columns for a user other than the currently logged in user



```javascript

columnsResponse = client.user.columns.get({username:TEST_USER_NAME})

```

```javascript

assert.equal( columnsResponse.status, 200 )

```



Sets the default columns for the given user. Admin permission will be required to get columns for a user other than the currently logged in user



```javascript

columnsResponse = client.user.columns.put({})

```

```javascript

assert.equal( columnsResponse.status, 200 )

```



Reset the default columns for the given user to the system default. Admin permission will be required to get columns for a user other than the currently logged in user



```javascript

columnsResponse = client.user.columns.delete({}, {query:{username:TEST_USER_NAME}})

```

```javascript

assert.equal( columnsResponse.status, 204 )

```



Modify user password



```javascript

passwordResponse = client.user.password.put({

  "password" : "aoeuAOEUaoeu!1"

}, {query:{username:TEST_USER_NAME}})

```

```javascript

assert.equal( passwordResponse.status, 204 )

```



Returns a list of users matching query with highlighting. This resource cannot be accessed anonymously



```javascript

pickerResponse = client.user.picker.get({query:"notebook"})

```

```javascript

assert.equal( pickerResponse.status, 200 )

```



Returns a list of users that match the search string. This resource cannot be accessed anonymously



```javascript

searchResponse = client.user.search.get({username:TEST_USER_NAME})

```

```javascript

assert.equal( searchResponse.status, 200 )

```



Returns a list of active users that match the search string. This resource cannot be accessed anonymously. Given an issue key this resource will provide a list of users that match the search string and have the browse issue permission for the issue provided



```javascript

viewissuesearchResponse = client.user.viewissue.search.get({

  username:TEST_USER_NAME,

  projectKey: projectKey

})

```

```javascript

assert.equal( viewissuesearchResponse.status, 200 )

```



Removes user



```javascript

userResponse = client.user.delete({},{query:{username:TEST_USER_NAME}})

```

```javascript

assert.equal( userResponse.status, 204 )

```



Returns a list of active users that match the search string and have all specified permissions for the project or issue.

This resource can be accessed by users with ADMINISTER_PROJECT permission for the project or global ADMIN or SYSADMIN rights.



```javascript

permissionsearchResponse = client.user.permission.search.get({

  username:TEST_USER_NAME,

  permissions: "ADMINISTER",

  projectKey: projectKey

})

```

```javascript

assert.equal( permissionsearchResponse.status, 200 )

```