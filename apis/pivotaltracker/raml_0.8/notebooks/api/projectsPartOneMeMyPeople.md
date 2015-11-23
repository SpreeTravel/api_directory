---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8017/versions/8183/portal/pages/6835/preview
apiNotebookVersion: 1.1.66
title: Projects part 1, me, my, people
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

```javascript

workspaceName = "NotebookWorkspaceName"

projectName = "NotebookProjectName"

```



Provides information about the authenticated user.



```javascript

meResponse = client.me.get()

```

```javascript

assert.equal( meResponse.status, 200 )

```

```javascript

currentUser_id = meResponse.body.id

```



Return list of the notification



```javascript

notificationsResponse = client.my.notifications.get()

```

```javascript

assert.equal( notificationsResponse.status, 200 )

notification_id = notificationsResponse.body.length > 0 ? notificationsResponse.body[0].id : null

```



Marks the person's notifications as read up to the given notification id



```javascript

mark_readResponse = client.my.notifications.mark_read.put({

  "before": 0

})

```

```javascript

assert.equal( mark_readResponse.status, 204 )

```



Mark the notification as read



```javascript

if(notification_id){

  markNotificationResponse = client.my.notifications.notification_id( notification_id ).put({"read_at":0})

}

```

```javascript

if(notification_id){

  assert.equal( markNotificationResponse.status, 200 )

}

```



Return list of the selected activity for the authenticated person.



```javascript

activityResponse = client.my.activity.get()

```

```javascript

assert.equal( activityResponse.status, 200 )

```



Delete a test project that could have been created during earlier notebook runs.



```javascript

project_id = null

projectsResponse = client.projects.get()

for( var i in projectsResponse.body){

  if( projectsResponse.body[i].name == projectName ){

    project_id = projectsResponse.body[i].id

  }

}

if( project_id ){

  deleteProjectResponse = client.projects.project_id( project_id ).delete()

}

```



Create a new empty project.



```javascript

createProjectResponse = client.projects.post({

  "name" : projectName 

})

```

```javascript

assert.equal( createProjectResponse.status, 200 )

```



Return list of workspaces for the authenticated person.



```javascript

workspacesResponse = client.my.workspaces.get()

```

```javascript

assert.equal( workspacesResponse.status, 200 )

```

```javascript

project_id = createProjectResponse.body.id

```



Create a new workspace for the authenticated person.



```javascript

createWorkspacesResponse = client.my.workspaces.post({

  "name" : workspaceName 

})

```

```javascript

assert.equal( createWorkspacesResponse.status, 200 )

```

```javascript

workspace_id = createWorkspacesResponse.body.id

```



Return a workspace for the authenticated person.



```javascript

workspaceResponse = client.my.workspaces.workspace_id( workspace_id ).get()

```

```javascript

assert.equal( workspaceResponse.status, 200 )

```



Return the specified workspace



```javascript

specifiedWorkspaceResponse = client.my.workspaces.workspace_id( workspace_id ).put({

  "project_ids" : [

    project_id

  ]

})

```

```javascript

assert.equal( specifiedWorkspaceResponse.status, 200 )

```



Permanently delete the workspace.



```javascript

deleteWorkspaceResponse = client.my.workspaces.workspace_id( workspace_id ).delete()

```

```javascript

assert.equal( deleteWorkspaceResponse.status, 204 )

```



Returns list of notifications for the specified person whose updated_at values are less than the timestamp



```javascript

peopleNotificationsSinceResponse = client.people.person_id( currentUser_id ).notifications.since.timestamp( "2014-08-10T07:00:00Z" ).get()  

```

```javascript

assert.equal( peopleNotificationsSinceResponse.status, 200 )

```



Get all of a user's active projects



```javascript

projectsResponse = client.projects.get()

```

```javascript

assert.equal( projectsResponse.status, 200 )

```



Fetch the content of the specified project



```javascript

projectResponse = client.projects.project_id( project_id ).get()

```

```javascript

assert.equal( projectResponse.status, 200 )

```



Update the specified project



```javascript

updateProjectResponse = client.projects.project_id( project_id ).put({

  "atom_enabled" : true

})

```

```javascript

assert.equal( updateProjectResponse.status, 200 )

```



Create a new integration for a project.



```javascript

createIntegrationsResponse = client.projects.project_id( project_id ).integrations.post({

  "active":false,

  "base_url":"http://some.th/ing",

  "name":"something",

  "type":"other"

})

```

```javascript

assert.equal( createIntegrationsResponse.status, 200 )

```



Returns a project integrations.



```javascript

integrationsResponse = client.projects.project_id( project_id ).integrations.get()

```

```javascript

assert.equal( integrationsResponse.status, 200 )

```

```javascript

integration_id = integrationsResponse.body[0].id

```



Returns a project integration.



```javascript

integrationResponse = client.projects.project_id( project_id ).integrations.integration_id( integration_id ).get()

```

```javascript

assert.equal( integrationResponse.status, 200 )

```



Updates a project integration



```javascript

updateIntegrationResponse = client.projects.project_id( project_id ).integrations.integration_id( integration_id ).put({

  "base_url" : "http://some.th/ing" ,

  "name" : "something"

})

```

```javascript

assert.equal( updateIntegrationResponse.status, 200 )

```



Provides external_story records for all of the potential stories available through the selected integration (already configured on the selected project). The content of the objects returned is suitable for use as parameters to create stories 'imported from' the selected integration



```javascript

storiesResponse = client.projects.project_id( project_id ).integrations.integration_id( integration_id ).stories.get()

```

```javascript

assert.equal( storiesResponse.status, 200 )

```



Deletes a project integration.



```javascript

deleteIntegrationResponse = client.projects.project_id( project_id ).integrations.integration_id( integration_id ).delete()

```

```javascript

assert.equal( deleteIntegrationResponse.status, 204 )

```



Return a set of iterations from the project.



```javascript

iterationsResponse = client.projects.project_id( project_id ).iterations.get()

```

```javascript

assert.equal( iterationsResponse.status, 200 )

```

```javascript

iteration_number = iterationsResponse.body[0].number

```



Updates an iteration's overrides



```javascript

iteration_overridesResponse = client.projects.project_id( project_id ).iteration_overrides.iteration_number( iteration_number ).put({

  "length" : 5 ,

  "team_strength" : 0.7

})

```

```javascript

assert.equal( iteration_overridesResponse.status, 200 )

```



List all of the memberships in a project



```javascript

membershipsResponse = client.projects.project_id( project_id ).memberships.get()

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



Add a user to the project.



```javascript

addMembershipResponse = client.projects.project_id( project_id ).memberships.post({

  "role" : "member" ,

  "email" : "test.notebook.user@somehost.com" ,

  "initials" : "TNU" ,

  "name" : "Test Notebook User"

})

```

```javascript

assert.equal( addMembershipResponse.status, 200 )

membership_id = addMembershipResponse.body.id

```



Returns the specified project membership



```javascript

membershipResponse = client.projects.project_id( project_id ).memberships.membership_id( membership_id ).get()

```

```javascript

assert.equal( membershipResponse.status, 200 )

```



Updates the specified project membership.



```javascript

updateMembershipResponse = client.projects.project_id( project_id ).memberships.membership_id( membership_id ).put({

  "role" : "viewer" ,

  "project_color" : "ffffff"

})

```

```javascript

assert.equal( updateMembershipResponse.status, 200 )

```



Deletes the specified project membership.



```javascript

deleteMembershipResponse = client.projects.project_id( project_id ).memberships.membership_id( membership_id ).delete()

```

```javascript

assert.equal( deleteMembershipResponse.status, 204 )

```



Garbage collection. Delete the created test project.



```javascript

deleteProjectResponse = client.projects.project_id( project_id ).delete()

```