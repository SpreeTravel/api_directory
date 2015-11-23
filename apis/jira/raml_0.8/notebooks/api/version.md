---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7868/versions/8022/portal/pages/6625/preview
apiNotebookVersion: 1.1.66
title: Version
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



Name of project version which is operated by the notebook



```javascript

VERSION_NAME = "VersionForNotebook"

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

  if(Number.isInteger(parsedInd)&&parsedInd>=0&&parsedInd){

    return projects[parsedInd]

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

project = selectProject()

```



Let's remove a version which could have been created during previous notebook runs



```javascript

projectVersions = client.project.projectIdOrKey(project.id).versions.get().body

for(var ind in projectVersions){

  var version = projectVersions[ind]

  if(version.name==VERSION_NAME){

    client.version.id(version.id).delete()

    break

  }

}

```



Create a version via POST



```javascript

versionCreateResponse = client.version.post({

  "description" : "This project jersion was created by the API Notebook" ,

  "name" : VERSION_NAME ,

  "archived" : false ,

  "released" : true ,

  "releaseDate" : "2010-07-06",

  "project" : project.key,

  "projectId" : project.id

})

```

```javascript

assert.equal( versionCreateResponse.status, 201 )

versionId = versionCreateResponse.body.id

```



Returns a project version



```javascript

versionResponse = client.version.id( versionId ).get()

```

```javascript

assert.equal( versionResponse.status, 200 )

```



Modify a version via PUT. Any fields present in the PUT will override existing values. As a convenience, if a field is not present, it is silently ignored



```javascript

versionUpdateResponse = client.version.id(versionId).put({

  "description" : versionResponse.body.description + " (upd)",

})

```

```javascript

assert.equal( versionUpdateResponse.status, 200 )

```



Modify a version's sequence within a project. The move version bean has 2 alternative field value pairs:

position

An absolute position, which may have a value of 'First', 'Last', 'Earlier' or 'Later'

after

A version to place this version after. The value should be the self link of another version



```javascript

moveVersioResponse = client.version.id(versionId).move.post({

  "position" : "Last"

})

```

```javascript

assert.equal( moveVersioResponse.status, 200 )

```



Returns a bean containing the number of fixed in and affected issues for the given version



```javascript

relatedIssueCountsResponse = client.version.id(versionId).relatedIssueCounts.get()

```

```javascript

assert.equal( relatedIssueCountsResponse.status, 200 )

```



Returns the number of unresolved issues for the given version



```javascript

unresolvedIssueCountResponse = client.version.id(versionId).unresolvedIssueCount.get()

```

```javascript

assert.equal( unresolvedIssueCountResponse.status, 200 )

```



Returns the remote version links associated with the given version ID



```javascript

remotelinkResponse = client.version.id(versionId).remotelink.get()

```

```javascript

assert.equal( remotelinkResponse.status, 200 )

```



Create a remote version link via POST. The link's global ID will be taken from the JSON payload if provided; otherwise, it will be generated



```javascript

remoteLinkCreateResponse = client.version.id(versionId).remotelink.post({

  "globalId" : "SomeGlobalId" ,

  "myCustomLinkProperty" : true ,

  "colors" : [

    "red" ,

    "green" ,

    "blue"

  ] ,

  "notes" : [

    "Remote version links may take any well-formed JSON shape that is desired," ,

    "provided that they fit within the maximum buffer size allowed," ,

    "which is currently 32,768 characters."

  ]

})

```

```javascript

assert.equal( remoteLinkCreateResponse.status, 201 )

```



A REST sub-resource representing a remote version link



```javascript

remoteLinkResponse = client.version.id(versionId).remotelink.globalId("SomeGlobalId").get()

```

```javascript

assert.equal( remoteLinkResponse.status, 200 )

```



Create a remote version link via POST. The link's global ID will be taken from the JSON payload if provided; otherwise, it will be generated



```javascript

remoteLinkCreate2Response = client.version.id(versionId).remotelink.globalId("someGlobalId2").post({

  "myCustomLinkProperty" : true ,

  "colors" : [

    "red" ,

    "green" ,

    "blue"

  ] ,

  "notes" : [

    "Remote version links may take any well-formed JSON shape that is desired," ,

    "provided that they fit within the maximum buffer size allowed," ,

    "which is currently 32,768 characters."

  ]

})

```

```javascript

assert.equal( remoteLinkCreate2Response.status, 201 )

```



Delete a specific remote version link with the given version ID and global ID



```javascript

remoteLinkDeleteResponse = client.version.id(versionId).remotelink.globalId("someGlobalId2").delete()

```

```javascript

assert.equal( remoteLinkDeleteResponse.status, 204 )

```



Delete all remote version links for a given version ID



```javascript

remoteLinksDeleteResponse = client.version.id(versionId).remotelink.delete()

```

```javascript

assert.equal( remoteLinksDeleteResponse.status, 204 )

```



Returns the remote version links for a given global ID



```javascript

remotelinksResponse = client.version.remotelink.get({globalId:"SomeGlobalId"})

```

```javascript

assert.equal( remotelinksResponse.status, 200 )

```



Delete a project version



```javascript

versionDeleteResponse = client.version.id(versionId).delete()

```

```javascript

assert.equal( versionDeleteResponse.status, 204 )

```