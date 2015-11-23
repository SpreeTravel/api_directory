---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8017/versions/8183/portal/pages/6836/preview
apiNotebookVersion: 1.1.66
title: Projects part 2, stories, accounts, source commits
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

API_TOKEN = prompt("Please, enter your PivotalTracker API TOKEN")

```

```javascript

// Read about the Pivotal Tracker at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8017/versions/8183/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8017/versions/8183/definition');

```

```javascript

function modifyRequest(request){

  request.setRequestHeader("X-TrackerToken", API_TOKEN);

}

```

```javascript

API.set(client, 'beforeSend', new function(){return modifyRequest})

```



Retrieve a test project or create one if it does not already exist.



```javascript

projectName = "TestNotebookProject"

project_id = null

projectsResponse = client.projects.get()

for( var i in projectsResponse.body){

  if( projectsResponse.body[i].name == projectName ){

    project_id = projectsResponse.body[i].id

  }

}



if(!project_id){

  createProjectResponse = client.projects.post({ "name" : projectName })

  project_id = createProjectResponse.body.id

}

```



Create a story and an epic for the test project.



```javascript

createStoryResponse = client.projects.project_id( project_id ).stories.post({

  "comments":

    [

      {

        "text":"Just ray shielding? What about proton weapons?"

      }

    ],

  "name":"Exhaust ports are ray shielded"

})

story_id = createStoryResponse.body.id



createEpicsResponse = client.projects.project_id( project_id ).epics.post({

  "name":"Epic RAML Test Notebook"

})

epic_id = createEpicsResponse.body.id

```



Search for stories and epics



```javascript

searchResponse = client.projects.project_id( project_id ).search.get({

  "query": "text:Just ray shielding?"

})

```

```javascript

assert.equal( searchResponse.status, 200 )

```



Saves a new search



```javascript

saveMySearchesResponse = client.projects.project_id( project_id ).my.searches.post({

  "name" : "my saved search" ,

  "query" : "text:Just ray shielding?"

})

```

```javascript

assert.equal( saveMySearchesResponse.status, 200 )

```

```javascript

search_id = saveMySearchesResponse.body.id

```



Provides a list of the searches which have been saved by the current person.



```javascript

mysearchesResponse = client.projects.project_id( project_id ).my.searches.get()

```

```javascript

assert.equal( mysearchesResponse.status, 200 )

```



Deletes a saved search



```javascript

deleteMySearchesResponse = client.projects.project_id( project_id ).my.searches.search_id( search_id ).delete()

```

```javascript

assert.equal( deleteMySearchesResponse.status, 204 )

```



Return list of the selected project activity



```javascript

activityResponse = client.projects.project_id( project_id ).activity.get()

```

```javascript

assert.equal( activityResponse.status, 200 )

```



POSTing to an individual project resource's URL is used to send story-selection parameters. The server responds with a CSV export file containing data from the selected stories



```javascript

projectExportResponse = client.projects.project_id( project_id ).export.post({ story_ids : [ story_id ] })

```

```javascript

assert.equal( projectExportResponse.status, 200 )

```



Return the project's webhooks



```javascript

webhooksResponse = client.projects.project_id( project_id ).webhooks.get()

```

```javascript

assert.equal( webhooksResponse.status, 200 )

```



Creates a webhook on the project



```javascript

createWebhooksResponse = client.projects.project_id( project_id ).webhooks.post({

  "webhook_url" : "http://localhost:3000/test_postbin" ,

  "webhook_version" : "v5"

})

```

```javascript

assert.equal( createWebhooksResponse.status, 200 )

webhook_id = createWebhooksResponse.body.id

```



Return the specified project webhook



```javascript

webhookResponse = client.projects.project_id( project_id ).webhooks.webhook_id( webhook_id ).get()

```

```javascript

assert.equal( webhookResponse.status, 200 )

```



Update the specified project webhook



```javascript

updateWebhookResponse = client.projects.project_id( project_id ).webhooks.webhook_id( webhook_id ).put({

  "webhook_url" : "http://localhost:3000/test_postbin" ,

  "webhook_version" : "v5"

})

```

```javascript

assert.equal( updateWebhookResponse.status, 200 )

```



Delete the specified project webhook



```javascript

deleteWebhookResponse = client.projects.project_id( project_id ).webhooks.webhook_id( webhook_id ).delete()

```

```javascript

assert.equal( deleteWebhookResponse.status, 204 )

```



Get all of the accounts a user is an owner or admin of



```javascript

accountsResponse = client.accounts.get()

```

```javascript

assert.equal( accountsResponse.status, 200 )

account_id = accountsResponse.body[0].id

```



Fetch the content of the specified account



```javascript

accountResponse = client.accounts.account_id( account_id ).get()

```

```javascript

assert.equal( accountResponse.status, 200 )

```



List all of the memberships in an account



```javascript

membershipsResponse = client.accounts.account_id( account_id ).memberships.get()

```

```javascript

assert.equal( membershipsResponse.status, 200 )

```



Delete a member that could have been created during earlier notebook runs.



```javascript

for(var ind in membershipsResponse.body){

  var member = membershipsResponse.body[ind]

  if(member.person.name=="Test Notebook User"){

    client.accounts.account_id( account_id ).memberships.person_id(member.id).delete()

  }

}

```



Create a new membership in an account



```javascript

membershipCreateResponse = client.accounts.account_id( account_id ).memberships.post({

  "email" : "test.notebook.user@somehost.com" ,

  "initials" : "TNU" ,

  "name" : "Test Notebook User"

})

```

```javascript

assert.equal( membershipCreateResponse.status, 200 )

person_id = membershipCreateResponse.body.person.id

```



Returns the specified account membership



```javascript

accountMembershipsResponse = client.accounts.account_id( account_id ).memberships.person_id( person_id ).get()

```

```javascript

assert.equal( accountMembershipsResponse.status, 200 )

```



Updates the specified account membership



```javascript

updateMembershipsResponse = client.accounts.account_id( account_id ).memberships.person_id( person_id ).put({

  "project_creator" : true

})

```

```javascript

assert.equal( updateMembershipsResponse.status, 200 )

```



Delete the specified account membership



```javascript

deleteMembershipsResponse = client.accounts.account_id( account_id ).memberships.person_id( person_id ).delete()

```

```javascript

assert.equal( deleteMembershipsResponse.status, 204 )

```



Returns the specified story



```javascript

storyResponse = client.stories.story_id( story_id ).get()

```

```javascript

assert.equal( storyResponse.status, 200 )

```



Updates the specified story



```javascript

updateStoryResponse = client.stories.story_id( story_id ).put({

  "labels":[

    {

      "name":"newnew"

    },

    {

      "name":"labellabel"

    } 

  ]

})

```

```javascript

assert.equal( updateStoryResponse.status, 200 )

```



POSTing is used to send story-selection parameters. The server responds with a CSV export file containing data from the selected stories



```javascript

storiesExportResponse = client.stories.export.post({ids:[story_id]})

```

```javascript

assert.equal( storiesExportResponse.status, 200 )

```



Returns the specified epic



```javascript

epicResponse = client.epics.epic_id( epic_id ).get()

```

```javascript

assert.equal( epicResponse.status, 200 )

```



Creates a comment associated with a commit in a Source Control system



```javascript

source_commitsResponse = client.source_commits.post({

  "source_commit" : {

    "commit_id" : "abc123" ,

    "message" : "[#" + story_id + "] some commit" ,

    "url" : "http://example.com/abc123" ,

    "author" : "Darth Vader"

  }

})

```

```javascript

assert.equal( source_commitsResponse.status, 200 )

```



The aggregator allows the client to make a single request that returns the same information as several separate GETs to other endpoints.



```javascript

//Unimplemented

//aggregatorResponse = client.aggregator.post({})

```

```javascript

//assert.equal( aggregatorResponse.status, 200 )

```



Deletes the specified story



```javascript

deleteStoryResponse = client.stories.story_id( story_id ).delete()

```

```javascript

assert.equal( deleteStoryResponse.status, 204 )

```



Permanently delete the specified project



```javascript

deleteProjectResponse = client.projects.project_id( project_id ).delete()

```

```javascript

assert.equal( deleteProjectResponse.status, 204 )

```