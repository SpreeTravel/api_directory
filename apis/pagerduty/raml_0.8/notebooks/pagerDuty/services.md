---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7593/versions/7719/portal/pages/6600/preview
apiNotebookVersion: 1.1.66
title: Services
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

In order to cover the whole variety of service related methods, we will create two services. Here are names of these services.

```javascript
emailTestServiceName = "Notebook Generic Email Test Service"
eventsApiTestServiceName = "Notebook Generic Events Api Test Service"
svcNames = [ emailTestServiceName, eventsApiTestServiceName ]
```

List existing services

```javascript
servicesList = client.services.get()
```

Let's delete services that could have been created during previous notebook runs.

```javascript
assert.equal(servicesList.status, 200)
{
  var servicesArray = servicesList.body.services
  for(var ind in servicesArray){
    var svc = servicesArray[ind]
    if(svcNames.indexOf(svc.name)>=0){
      client.services.service_id(svc.id).delete({},{headers:{"Content-Type":"application/json"}});
    }
  }
}
```

We need an escalation policy to create services.
And in order to create an escalation policy we need a user.
Let's pick the test user if he already exists or create him in the opposite case.

```javascript
testUserName = "Notebook Test User 3";
testUserEmail = "test_user_3@somehost.com",
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
testEscalationPolicyName = "Notebook Test Escalation Policy 2"
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

```javascript
epId = escalationPolicyCreate.body.escalation_policy.id
```

Create a new service

```javascript
servicesCreated = client.services.post({"service":{
    "name" : emailTestServiceName ,
    "escalation_policy_id" : epId,
    "type" : "generic_email",
    "service_key": "key_1"
}});
```

```javascript
assert.equal(servicesCreated.status, 201);
```

Get details about an existing service

```javascript
serviceId = servicesCreated.body.service.id;
```

```javascript
serviceDetails = client.services.service_id(serviceId).get();
```

```javascript
assert.equal(serviceDetails.status, 200);
```

Update an existing service

```javascript
serviceUpdated = client.services.service_id(serviceId).put({
  "service" : {
    "description" : "Edited service description."
  }
});
```

```javascript
assert.equal(serviceUpdated.status, 200);
```

Disable a service. Once a service is disabled, it will not be
able to create incidents until it is enabled again.

```javascript
usersList = client.users.get();
user = usersList.body.users[0];
```

```javascript
serviceDisable = client.services.service_id(serviceId).disable.put({
  "requester_id" : user.id
});
```

```javascript
assert.equal(serviceDisable.status, 200);
```

Enable a previously disabled service

```javascript
serviceEnabled = client.services.service_id(serviceId).enable.put();
```

```javascript
assert.equal(serviceEnabled.status, 200);
```

In order to test the _POST /services/{service_id}/regenerate_key_ method we need a _generic_events_api_ service.

```javascript
regenerationService = client.services.post({"service":{
    "name" : eventsApiTestServiceName ,
    "escalation_policy_id" : epId,
    "type" : "generic_events_api"
}}).body.service;
```

Regenerate a new service key for an existing service

```javascript
reg_key = client.services.service_id(regenerationService.id).regenerate_key.post();
```

```javascript
assert.equal(reg_key.status, 201);
```

Delete an existing service

```javascript
serviceDelete = client.services.service_id(regenerationService.id).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(serviceDelete.status, 204);
```


Create a new Email Filter for the specified service

```javascript
emailFilters = client.services.service_id(serviceId).email_filters.post({
  "email_filter" : {
    "body_mode" : "no-match" ,
    "body_regex" : "sev 3"
  }
});
```

```javascript
assert.equal(emailFilters.status, 201);
```

Update an existing Email Filter

```javascript
emaiFilterId = emailFilters.body.email_filter.id;
```

```javascript
updatedEmailFilter = client.services.service_id(serviceId).email_filters.id(emaiFilterId).put({
  "email_filter" : {
    "from_email_mode" : "match" ,
    "from_email_regex" : "[rR]yan"
  }
});
```

```javascript
assert.equal(updatedEmailFilter.status, 200);
```

Delete an existing Email Filter

```javascript
emailFilterDelete = client.services.service_id(serviceId).email_filters.id(emaiFilterId).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(emailFilterDelete.status, 204);
```

Garbage collection: remove escalation policy, test user and all the services which could have been created during the notebook run.

```javascript
{
  servicesList = client.services.get()
  var servicesArray = servicesList.body.services
  for(var ind in servicesArray){
    var svc = servicesArray[ind]
    if(svcNames.indexOf(svc.name)>=0){
      client.services.service_id(svc.id).delete({},{headers:{"Content-Type":"application/json"}});
    }
  }
}

client.escalation_policies.escalation_policy_id(epId).delete({},{headers:{"Content-Type":"application/json"}});
client.users.user_id(userId).delete({},{headers:{"Content-Type":"application/json"}});
```