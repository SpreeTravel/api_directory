---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6623/edit
apiNotebookVersion: 1.1.66
title: Group, Project, Myself
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



Name of a test  user which is operated by the Notebook.



```javascript

TEST_USER_NAME = "notebook-test-user"

```



Name of a test  group which is operated by the Notebook.



```javascript

TEST_GROUP_NAME = "jira-notebook-writers"

```



Auxilliary method for retreiving role ID from _GET /role response



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



Auxilliary project selecting method



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

    return projects[parsedInd].id

  }

  return null

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

projectId = selectProject()

```



Here we produce a test user for our needs.



```javascript

client.user.delete({},{query:{username:TEST_USER_NAME}})

client.user.post({

  "name" : TEST_USER_NAME ,

  "password" : "aoeuAOEUaoeu1!" ,

  "emailAddress" : "mail@somehost.com" ,

  "displayName" : "Notebok Test User"

})

```



Returns currently logged user. This resource cannot be accessed anonymously



```javascript

myselfResponse = client.myself.get()

```

```javascript

assert.equal( myselfResponse.status, 200 )

```



Modify currently logged user. The "value" fields present will override the existing value. Fields skipped in request will not be changed. Only email and display name can be change that way



```javascript

changeDNamed = window.confirm("Do you whish your Display Name to be changed?")

```

```javascript

myselfUpdateResponse = null

if(changeDNamed){

  newDName = prompt("Please, enter the new Display Name")

  myselfUpdateResponse = client.myself.put({

    "displayName" : newDName

  })

}

```

```javascript

if(changeDNamed){

  assert.equal( myselfUpdateResponse.status, 200 )

}

```



Modify caller password



```javascript

changePassword = window.confirm("Do you want to change your password?\n\nNote that you'll be asked to reauthenticate the Jira client if the password is changed.")

```

```javascript

passwordResponse = null

if(changePassword){

  var newPassword = prompt("Please, enter the new password")

  passwordResponse = client.myself.password.put({password:newPassword})

}

```

```javascript

if(changePassword){

  assert.equal( passwordResponse.status, 204 )

}

```

```javascript

if(changePassword){

  API.authenticate(client)

}

```



Returns groups with substrings matching a given query. This is mainly for use with the group picker, so the returned groups contain html to be used as picker suggestions. The groups are also wrapped in a single response object that also contains a header for use in the picker, specifically Showing X of Y matching groups. The number of groups returned is limited by the system property "jira.ajax.autocomplete.limit" The groups will be unique and sorted.



```javascript

groupspickerResponse = client.groups.picker.get()

```

```javascript

assert.equal( groupspickerResponse.status, 200 )

```



Returns a list of users and groups matching query with highlighting. This resource cannot be accessed anonymously.



```javascript

groupuserpickerResponse = client.groupuserpicker.get({query:TEST_USER_NAME})

```

```javascript

assert.equal( groupuserpickerResponse.status, 200 )

```



Let's delete a group which could have been created during previous notebook runs.



```javascript

client.group.delete({},{query:{groupname:TEST_GROUP_NAME}})

```



Creates a group by given group parameter Returns REST representation for the requested group



```javascript

groupCreateResponse = client.group.post({name:TEST_GROUP_NAME})

```

```javascript

assert.equal( groupCreateResponse.status, 201 )

```



Returns REST representation for the requested group. Allows to get list of active users belonging to the specified group and its subgroups if "users" expand option is provided. You can page through users list by using indexes in expand param. For example to get users from index 10 to index 15 use "users[10:15]" expand value. This will return 6 users (if there are at least 16 users in this group). Indexes are 0-based and inclusive



```javascript

groupResponse = client.group.get({groupname:TEST_GROUP_NAME})

```

```javascript

assert.equal( groupResponse.status, 200 )

```



Adds given user to a group. Returns the current state of the group



```javascript

userResponse = client.group.user.post({

  name: TEST_USER_NAME

},{query:{

  groupname:TEST_GROUP_NAME,

}})

```

```javascript

assert.equal( userResponse.status, 201 )

```



Removes given user from a group. Returns no content



```javascript

userResponse = client.group.user.delete(null,{query:{

  groupname:TEST_GROUP_NAME,

  username: TEST_USER_NAME

}})

```

```javascript

assert.equal( userResponse.status, 200 )

```



Returns all projects which are visible for the currently logged in user. If no user is logged in, it returns the list of projects that are visible when using anonymous access



```javascript

projectsResponse = client.project.get()

```

```javascript

assert.equal( projectsResponse.status, 200 )

```



Contains a full representation of a project in JSON format.

All project keys associated with the project will only be returned if expand=projectKeys.



```javascript

projectResponse = client.project.projectIdOrKey( projectId ).get()

```

```javascript

assert.equal( projectResponse.status, 200 )

```



Returns all avatars which are visible for the currently logged in user. The avatars are grouped into system and custom



```javascript

avatarsResponse = client.project.projectIdOrKey( projectId ).avatars.get()

```

```javascript

assert.equal( avatarsResponse.status, 200 )

avatarId = avatarsResponse.body.system[0].id

```

```javascript

// NOT SUPPORTED

//avatarResponse = client.project.projectIdOrKey( projectId ).avatar.put({})

```

```javascript

//assert.equal( avatarResponse.status, 204 )

```



Converts temporary avatar into a real avatar



```javascript

// NOT SUPPORTED

// avatarResponse = client.project.projectIdOrKey( projectId ).avatar.post({

//   "cropperWidth" : 120 ,

//   "cropperOffsetX" : 50 ,

//   "cropperOffsetY" : 50 ,

//   "needsCropping" : false

// })

```

```javascript

//assert.equal( avatarResponse.status, 200 )

```



Deletes avatar



```javascript

// NOT SUPPORTED

//avaterDelete = client.project.projectIdOrKey( projectId ).avatar.id("idValue").delete()

```

```javascript

//assert.equal( avaterDelete.status, 200 )

```



Contains a full representation of a the specified project's components



```javascript

componentsResponse = client.project.projectIdOrKey( projectId ).components.get()

```

```javascript

assert.equal( componentsResponse.status, 200 )

```



Get all issue types with valid status values for a project



```javascript

statusesResponse = client.project.projectIdOrKey( projectId ).statuses.get()

```

```javascript

assert.equal( statusesResponse.status, 200 )

```



Contains a full representation of a the specified project's versions



```javascript

versionsResponse = client.project.projectIdOrKey( projectId ).versions.get()

```

```javascript

assert.equal( versionsResponse.status, 200 )

```

```javascript

propertiesResponse = client.project.projectIdOrKey( projectId ).properties.get()

```

```javascript

assert.equal( propertiesResponse.status, 200 )

```



Sets the value of the specified project's property



```javascript

propertySetResponse = client.project.projectIdOrKey( projectId ).properties.propertyKey("TestProperty").put({value:"TestValue"})

```

```javascript

assert.equal( propertySetResponse.status, 201 )

```



Returns the value of the property with a given key from the project identified by the key or by the id. The user who retrieves the property is required to have permissions to read the project



```javascript

propertyResponse = client.project.projectIdOrKey( projectId ).properties.propertyKey("TestProperty").get()

```

```javascript

assert.equal( propertyResponse.status, 200 )

```



Removes the property from the project identified by the key or by the id. Ths user removing the property is required to have permissions to administer the project



```javascript

propertyDeleteResponse = client.project.projectIdOrKey( projectId ).properties.propertyKey("TestProperty").delete()

```

```javascript

assert.equal( propertyDeleteResponse.status, 204 )

```



Contains a list of roles in this project with links to full details



```javascript

rolesResponse = client.project.projectIdOrKey( projectId ).role.get()

```

```javascript

assert.equal( rolesResponse.status, 200 )

roleId = extractSomeRoleId(rolesResponse.body)

```



Details on a given project role



```javascript

roleResponse = client.project.projectIdOrKey( projectId ).role.id( roleId ).get()

```

```javascript

assert.equal( roleResponse.status, 200 )

```



Add an actor to a project role



```javascript

roleAddActorResponse = client.project.projectIdOrKey( projectId ).role.id(roleId).post({

  "group" : [

    TEST_GROUP_NAME

  ]

})

```

```javascript

assert.equal( roleAddActorResponse.status, 200 )

```



Let's store all the role actors into a map.



```javascript

atlassianActors = new Object()

{

  var actors = roleResponse.body.actors

  for(var ind in actors){

    var actor = actors[ind]

    var aType = actor["type"]

    var arr = atlassianActors[aType]

    if(!arr){

      arr = []

      atlassianActors[aType] = arr

    }

    arr[arr.length] = actor.name

  }

}

```



Let's add our test group and test user into the map



```javascript

{

  var userActors = atlassianActors["atlassian-user-role-actor"]

  if(!userActors){

    userActors = []

  }

  

  var groupActors = atlassianActors["atlassian-group-role-actor"]

  if(!groupActors){

    groupActors = []

  }

  

  userActors[userActors.length] = TEST_USER_NAME

  groupActors[groupActors.length] = TEST_GROUP_NAME

}

```



Updates a project role to contain the sent actors



```javascript

//Currently does not work with body like { user: [u1, i2,... ] }. See https://jira.atlassian.com/browse/JRA-32213

roleUpdateResponse = client.project.projectIdOrKey( projectId ).role.id(roleId).put({

  id: roleId,

  categorisedActors: atlassianActors

})

```

```javascript

assert.equal( roleUpdateResponse.status, 200 )

```



Remove actors from a project role



```javascript

roleRemoveActorResponse = client.project.projectIdOrKey( projectId ).role.id(roleId).delete({},{query:{ group: TEST_GROUP_NAME }})

```

```javascript

assert.equal( roleRemoveActorResponse.status, 204 )

```



Let's also remove the user



```javascript

roleRemoveActorResponse2 = client.project.projectIdOrKey( projectId ).role.id(roleId).delete({},{query:{ user: TEST_USER_NAME }})

```

```javascript

assert.equal( roleRemoveActorResponse2.status, 204 )

```



Deletes a group by given group parameter. Returns no content



```javascript

groupResponse = client.group.delete({},{query:{

  groupname:TEST_GROUP_NAME

}})

```

```javascript

assert.equal( groupResponse.status, 200 )

```



Garbage collection: removing the test user.



```javascript

client.user.delete({},{query:{

  username:TEST_USER_NAME

}})

```