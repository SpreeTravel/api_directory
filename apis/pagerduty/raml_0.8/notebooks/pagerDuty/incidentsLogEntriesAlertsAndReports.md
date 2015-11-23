---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7593/versions/7719/portal/pages/6601/preview
apiNotebookVersion: 1.1.66
title: Incidents, log entries, alerts and reports
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js');
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
serviceNamePrefix = prompt("Please, enter your site address first segment. For example, for site address 'http://www.acme-co.pagerduty.com' enter 'acme-co'.");
```

```javascript
API.createClient(
  'client',
  '/apiplatform/repository/public/organizations/30/apis/7593/versions/7719/definition',
  {
    baseUriParameters: {
      serviceName: serviceNamePrefix
    }
  });
```

Here we initialize a PagerDuty Integration API client. We will use it for triggering incidents.

```javascript
API.createClient('integrationClient', '/apiplatform/repository/public/organizations/30/apis/8417/versions/8630/definition');
```

In order to run this notebook we will create a service named as follows:

```javascript
incidentTestServiceName = "Notenook Incident Testing Service";
```

Let's delete the service if it has been created during earlier notebook runs.

```javascript
services = client.services.get().body.services
for(var ind in services){
  var serv = services[ind]
  if(serv.name==incidentTestServiceName){
    client.services.service_id(serv.id).delete({},{headers:{"Content-Type":"application/json"}});
  }
}
```

In order to create a service we need some escalation policy, and it, in turn, requires some user.
Let's pick the test user if he already exists or create him in the opposite case.

```javascript
testUserName = "Notebook Test User 5";
testUserEmail = "test_user_5@somehost.com",
userId = null
{
  var user = null
  var usersResponse = client.users.get();
  var users = usersResponse.body.users
  for(var i in users){
    var u = users[i]
    if(u.name == testUserName){
      user = u
    }
  }
  if(!user){
    var userCreateResponse = client.users.post({
      "role" : "user" ,
      "name" : testUserName ,
      "email" : testUserEmail,
    });
    user = userCreateResponse.body.user
  }
  userId = user.id
}
```

Our escalation policy will be named as

```javascript
testEscalationPolicyName = "Notebook Incident Test Escalation Policy"
```

Let's delete the policy if it has been created during earlier notebook runs.

```javascript
escalationPolicies = client.escalation_policies.get().body.escalation_policies
for(var ind in escalationPolicies){
  var ep = escalationPolicies[ind]
  if(ep.name==testEscalationPolicyName){
    client.escalation_policies.escalation_policy_id(ep.id).delete({},{headers:{"Content-Type":"application/json"}});
  }
}
```

Now we can create an escalation policy.

```javascript
escalationPolicyCreate = client.escalation_policies.post({
  "name" : testEscalationPolicyName ,
  "escalation_rules" : [ {
    "escalation_delay_in_minutes" : 22 ,
    "rule_object" : {
      "type" : "user" ,
      "id" : userId
    }
  } ] ,
  "num_loops" : 2
});
```

Let's now create a _generic_events_api_ service

```javascript
incidentsTestService = client.services.post({"service":{
    "name" : incidentTestServiceName ,
    "escalation_policy_id" : escalationPolicyCreate.body.escalation_policy.id,
    "type" : "generic_events_api"
}}).body.service;
```

This helper retreives a triggered incident on the service we have just created.

```javascript
function getTriggeredIncident(){
  
  var incident = null
  //find the just triggered incident
  while(incident==null){
    
    var incidentsList = client.incidents.get();
    var incidents = incidentsList.body.incidents
  
    for( var ind = 0 ; ind < 10 && !incident ; ind++ ){
      for(var i in incidents){
        var inc  = incidentsList.body.incidents[i];
        if(inc.status == "triggered" && inc.service.id == incidentsTestService.id){
          incident = inc;
          break;
        }    
      }
    }
    if(incident == null){
      //let's trigger one more incident -- just in case
      triggerIncident( "Notebook test incident N")
      var decision = prompt("PagerDuty can not provide you with triggered incidents right now. Perhaps, the recently triggered incident have not been processed yet. We recommend you to try once more.\n\n Try onece more?")
      decision = decision.toLowerCase()
      if(decision != "y" && decision != "yes"){
        break;
      }
    }
  }
  return incident
}
```

This helper method triggers an incident for our service.

```javascript
function triggerIncident( description ){
  integrationClient("create_event.json").post({
    "event_type": "trigger",
    "service_key": incidentsTestService.service_key,
    "description": description
  })
}
```

Let's trigger a couple of incidents right now.

```javascript
triggerIncident( "Notebook test incident 1")
triggerIncident( "Notebook test incident 2")
```

```javascript
alert("It may take some time for PagerDuty to process incident triggering. At this point we recommend to wait about 30 seconds.")
```

**Attention** It may take some time for PagerDuty to process incident triggering. At this point we recommend to wait about 30 seconds.

Now the environment preparation is over and we can switch to tests.


List existing incidents

```javascript
incidentsList = client.incidents.get();
```

```javascript
assert.equal(incidentsList.status, 200);
```

Count the number of incidents matching certain criteria

```javascript
incidentsCount = client.incidents.count.get();
```

```javascript
assert.equal(incidentsCount.status, 200);
```

Let's pick a _triggered_ incident.

```javascript
incident = getTriggeredIncident()
```

Show detailed information about an incident. Accepts either an incident id, or an incident number

```javascript
incidentDetails = client.incidents.incident_id(incident.id).get();
```

```javascript
assert.equal(incidentDetails.status, 200);
```

Acknowledge an incident

```javascript
incidentAcknowledge = client.incidents.incident_id(incident.id).acknowledge.put({requester_id: userId});
```

```javascript
assert.equal(incidentAcknowledge.status, 200);
```

Reassign an incident.

```javascript
incidentReassign = client.incidents.incident_id(incident.id).reassign.put({assigned_to_user:userId});
```

```javascript
assert.equal(incidentReassign.status, 200);
```

List existing notes for the specified incident

```javascript
notesList = client.incidents.incident_id(incident.id).notes.get();
```

```javascript
assert.equal(notesList.status, 200);
```

Create a new note for the specified incident

```javascript
noteCreate = client.incidents.incident_id(incident.id).notes.post({
  "requester_id" : userId ,
  "note" : {
    "content" : "New Note"
  }
});
```

```javascript
assert.equal(noteCreate.status, 201);
```

List all incident log entries for a specific incident.

```javascript
incidentLog = client.incidents.incident_id(incident.id).log_entries.get();
```

```javascript
assert.equal(incidentLog.status, 200);
```

List all incident log entries across the entire account

```javascript
incidentEntries = client.log_entries.get();
```

```javascript
assert.equal(incidentEntries.status, 200);
```

Let's pick a log entry

```javascript
entry = incidentEntries.body.log_entries[0];
```

Get details for a specific incident log entry. This method provides additional information you can use to get at raw event data.

```javascript
incidentEntriesDetails = client.log_entries.id(entry.id).get();
```

```javascript
assert.equal(incidentEntriesDetails.status, 200);
```

Acknowledge, resolve, escalate or reassign one or more incidents

```javascript
incidentChange = client.incidents.put({
  "requester_id" : userId,
  "incidents" : [{
    "id" : incident.id,
    "status" : "resolved"}]
})
```

```javascript
assert.equal(incidentChange.status, 200);
```

Now we need another _triggered_ incident.

```javascript
incident = getTriggeredIncident()
```

Resolve an incident

```javascript
incidentResolve = client.incidents.incident_id(incident.id).resolve.put({requester_id:userId});
```

```javascript
assert.equal(incidentResolve.status, 200);
```

Get high level statistics about the number of alerts (SMSes, phone calls and emails)
sent for the desired time period, summed daily, weekly or monthly.

```javascript
timeSince = incident.created_on;
timeUntil = incident.last_status_change_on;
```

```javascript
incidentTime = client.reports.incidents_per_time.get({
  "since": timeSince,
  "until": timeUntil
});
```

```javascript
assert.equal(incidentTime.status, 200);
```

Get high level statistics about the number of incidents created for the desired time period, summed daily, weekly or monthly

```javascript
reportsAlerts = client.reports.alerts_per_time.get({
  "since": timeSince,
  "until": timeUntil
});
```

```javascript
assert.equal(reportsAlerts.status, 200);
```

List existing alerts for a given time range, optionally filtered by type (SMS, Email, Phone, or Push).

```javascript
alerts = client.alerts.get({
  "since": timeSince,
  "until": timeUntil
});
```

```javascript
assert.equal(alerts.status, 200);
```

Garbage collection: remove a test service, escalation policy and user

```javascript
client.services.service_id(incidentsTestService.id).delete({},{headers:{"Content-Type":"application/json"}})
```

```javascript
client.escalation_policies.escalation_policy_id(escalationPolicyCreate.body.escalation_policy.id).delete({},{headers:{"Content-Type":"application/json"}})
```

In order to remove a user, we should resolve all the incidents assigned to him.

```javascript
allIncidents = client.incidents.get().body.incidents
for(var ind in allIncidents){
  var inc = allIncidents[ind]
  if(inc.assigned_to_user && inc.assigned_to_user.id==userId){
    client.incidents.incident_id(inc.id).resolve.put({requester_id:userId})
  }
}
```

Now we are free to delete the user

```javascript
client.users.user_id(userId).delete({},{headers:{"Content-Type":"application/json"}})
```