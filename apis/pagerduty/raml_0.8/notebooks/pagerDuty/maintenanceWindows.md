---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7593/versions/7719/portal/pages/6603/preview
apiNotebookVersion: 1.1.66
title: Maintenance windows
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

We need a helper method to generate dates.

```javascript
function generateDate(days){

  var today = new Date();
  var time = today.getTime() + days * 1000*60*60*24;
  var date = new Date(time);
  
  var dd = date.getDate();
  var mm = date.getMonth()+1; //January is 0!
  var hh = date.getHours();
  var min = date.getMinutes();
  var sec = date.getSeconds();
  
  var yyyy = date.getFullYear();
  var result = yyyy + '-' + mm + '-' + dd + 'T' + hh + ':' + min + ':' + sec + 'Z'
  return result
}
```

In order to run this notebook we will create a service named as follows:

```javascript
windowsTestServiceName = "Maintenance Windows Testing Service";
```

Let's delete the service if it has been created during earlier notebook runs.

```javascript
services = client.services.get().body.services
for(var ind in services){
  var serv = services[ind]
  if(serv.name==windowsTestServiceName){
    client.services.service_id(serv.id).delete({},{headers:{"Content-Type":"application/json"}});
  }
}
```

In order to create a service we need some escalation policy, and it, in turn, requires some user.
Let's pick the test user if he already exists or create him in the opposite case.

```javascript
testUserName = "Notebook Test User 6";
testUserEmail = "test_user_6@somehost.com",
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
testEscalationPolicyName = "Notebook Maintenance Windows Test Escalation Policy"
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
ePolicyId = escalationPolicyCreate.body.escalation_policy.id
```

Let's now create a service

```javascript
service = client.services.post({"service":{
    "name" : windowsTestServiceName ,
    "escalation_policy_id" : ePolicyId,
    "type" : "generic_events_api"
}}).body.service;
```

Now the environment preparation is over and we can switch to tests.

List existing maintenance windows, optionally filtered by service,
or whether they are from the past, present or future.

```javascript
maintenance_windows = client.maintenance_windows.get();
```

```javascript
assert.equal(maintenance_windows.status, 200);
```

Create a new maintenance window for the specified services.
No new incidents will be created for a service that is currently in maintenance.

```javascript
maintenance_windowCreate = client.maintenance_windows.post({
  "maintenance_window" : {
    "start_time" : generateDate(0) ,
    "end_time" : generateDate(730) ,
    "description" : "Upgrading to new software" ,
    "service_ids" : [ service.id ]
  } ,
  "requester_id" : userId
} );
```

```javascript
assert.equal(maintenance_windowCreate.status, 201);
```

```javascript
maintenance_windowId = maintenance_windowCreate.body.maintenance_window.id;
```

Get details about an existing maintenance window

```javascript
maintenance_windowDetails = client.maintenance_windows.id(maintenance_windowId).get();
```

```javascript
assert.equal(maintenance_windowDetails.status, 200);
```

Update an existing maintenance window

```javascript
maintenance_windowUpdate = client.maintenance_windows.id(maintenance_windowId).put({
  "description" : "Description goes here" ,
  "service_ids" : [ service.id ]
} );
```

```javascript
assert.equal(maintenance_windowUpdate.status, 200);
```

Cancel or delete an existing maintenance window

```javascript
maintenance_windowDelete = client.maintenance_windows.id(maintenance_windowId).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(maintenance_windowDelete.status, 204);
```

Garbage collection: remove a test service, escalation policy and user

```javascript
client.services.service_id(service.id).delete({},{headers:{"Content-Type":"application/json"}})
```

```javascript
client.escalation_policies.escalation_policy_id(ePolicyId).delete({},{headers:{"Content-Type":"application/json"}})
```

```javascript
client.users.user_id(userId).delete({},{headers:{"Content-Type":"application/json"}})
```