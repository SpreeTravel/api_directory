---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7593/versions/7719/portal/pages/6602/preview
apiNotebookVersion: 1.1.66
title: Escalation policies
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

The present notebook operates with escalation policy named as follows:

```javascript
escalationPolicyName = "Notebook Test Escalation Policy";
```

Here is name and email of a test user used in this notebook.

```javascript
testUserName = "Notebook Test User 4"
testUserEmail = "test_user_4@somehost.com"
```

Let's pick the test user if he already exists or create him in the opposite case.

```javascript
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
      "email" : testUserEmail
    });
    user = userCreateResponse.body.user
  }
  userId = user.id
}
```

List existing escalation policies

```javascript
escalationPolicies = client.escalation_policies.get();
```

```javascript
assert.equal(escalationPolicies.status, 200);
```

Let's remove a test escalation policy which could have been created during earlier notebook runs.

```javascript
epArray = escalationPolicies.body.escalation_policies
for(var ind in epArray){
  var ep = epArray[ind]
  if( ep.name == escalationPolicyName ){
    client.escalation_policies.escalation_policy_id(ep.id).delete({},{headers:{"Content-Type":"application/json"}});
  }
}
```

Create a new escalation policy

```javascript
escalationPolicyCreate = client.escalation_policies.post({
  "name" : escalationPolicyName ,
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
assert.equal(escalationPolicyCreate.status, 201);
ePolicyId = escalationPolicyCreate.body.escalation_policy.id;
```


Get details about an escalation policy

```javascript
escalationPolicyDetails = client.escalation_policies.escalation_policy_id(ePolicyId).get();
```

```javascript
assert.equal(escalationPolicyDetails.status, 200);
```

Update an escalation policy

```javascript
escalationUpdate = client.escalation_policies.escalation_policy_id(ePolicyId).put({
  "escalation_policy" : {
    "description": "Edited escalation policy description.",    
  }
});
```

```javascript
assert.equal(escalationUpdate.status, 200);
```

List escalation rules for an escalation policy

```javascript
escalationRulesList = client.escalation_policies.escalation_policy_id(ePolicyId).escalation_rules.get();
```

```javascript
assert.equal(escalationRulesList.status, 200);
```

Creates a new escalation rule for an escalation policy and appends it to the end of the existing escalation rules

```javascript
escalationRuleCreate = client.escalation_policies.escalation_policy_id(ePolicyId).escalation_rules.post({
  "escalation_rule" : {
    "escalation_delay_in_minutes" : 2 ,
    "rule_object" : {
      "type" : "user" ,
      "id" : userId
    }
  }
} );
```

```javascript
assert.equal(escalationRuleCreate.status, 201);
eRuleId = escalationRuleCreate.body.escalation_rule.id
```

Updates the entire collection of escalation rules

```javascript
escalationRulesUpdate = client.escalation_policies.escalation_policy_id(ePolicyId).escalation_rules.put({
  "escalation_rules" : [ {
    "escalation_delay_in_minutes" : 12 ,
    "rule_object" : {
      "type" : "user" ,
      "id" : userId
    }
  } , {
    "id" : eRuleId ,
    "escalation_delay_in_minutes" : 240
  } ]
});
```

```javascript
assert.equal(escalationRulesUpdate.status, 200);
```

Get details about an escalation rule

```javascript
escalationRuleDetails = client.escalation_policies.escalation_policy_id(ePolicyId).escalation_rules.escalation_rule_id(eRuleId).get();
```

```javascript
assert.equal(escalationRuleDetails.status, 200);
```

Update an escalation rule

```javascript
escalationRuleUpdate = client.escalation_policies.escalation_policy_id(ePolicyId).escalation_rules.escalation_rule_id(eRuleId).put({
  "id": eRuleId,
  "escalation_delay_in_minutes" : 44,
});
```

```javascript
assert.equal(escalationRuleUpdate.status, 200);
```

Delete an escalation rule

```javascript
escalationRuleDelete = client.escalation_policies.escalation_policy_id(ePolicyId).escalation_rules.escalation_rule_id(eRuleId).delete(
  {},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(escalationRuleDelete.status, 200);
```

Delete an escalation policy

```javascript
escalationPolicyDelete = client.escalation_policies.escalation_policy_id(ePolicyId).delete(
  {},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(escalationPolicyDelete.status, 204);
```

Gerbage collection: remove a test user.

```javascript
client.users.user_id(userId).delete({},{headers:{"Content-Type":"application/json"}})
```