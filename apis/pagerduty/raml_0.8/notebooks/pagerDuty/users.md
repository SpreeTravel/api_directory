---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7593/versions/7719/portal/pages/6598/preview
apiNotebookVersion: 1.1.66
title: Users
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

The present notebook operates with the following name and email:

```javascript
testUserName = "Notebook Test User 1"
testUserEmail = "test_user_1@somehost.com"
```

List existing users

```javascript
users = client.users.get();
```

```javascript
assert.equal(users.status, 200);
```

Let's delete a user which could have been created during previous notebook runs.

```javascript
for(var i in users.body.users){
  var user = users.body.users[i]
  if( user.name == testUserName ) {
    client.users.user_id(user.id).delete({},{headers:{"Content-Type":"application/json"}});
  }
}
```

Create a new user

```javascript
userCreated = client.users.post({
  "role" : "user" ,
  "name" : testUserName ,
  "email" : testUserEmail
});
```

```javascript
assert.equal(userCreated.status, 201);
userId = userCreated.body.user.id;
```

Get information about an existing user

```javascript
userInfo = client.users.user_id(userId).get()
```

```javascript
assert.isNotNull(userInfo.body.user);
assert.equal(userInfo.status, 200);
```

Update an existing user

```javascript
userUpdated = client.users.user_id(userId).put({
  "user" : {
    "role" : "admin",
  }
});
```

```javascript
assert.equal(userUpdated.status, 200);
```

List existing contact methods for the specified user

```javascript
contactMethods = client.users.user_id(userId).contact_methods.get()
```

```javascript
assert.equal(contactMethods.status, 200);
```

Create a new contact method for the specified user

```javascript
contactMethodCreate = client.users.user_id(userId).contact_methods.post({
  "contact_method" : {
    "type" : "phone" ,
    "address" : "5551112222" ,
    "label" : "Island Lair" ,
    "country_code" : "1"
  }
});
```

```javascript
assert.equal(contactMethodCreate.status, 201);
contactMethod = contactMethodCreate.body.contact_method
```

Get details for a contact method

```javascript
contactMethodDetails = client.users.user_id(userId).contact_methods.id(contactMethod.id).get()
```

```javascript
assert.isNotNull(contactMethodDetails.body.contact_method);
assert.equal(contactMethodDetails.status, 200);
```

Update an existing contact method

```javascript
contactMethodUpdated = client.users.user_id(userId).contact_methods.id(contactMethod.id).put({
  "contact_method" : {
    "type" : "phone" ,
    "address" : "5558888888" ,
    "label" : "Island Lair" ,
    "country_code" : "1"
  }
});
```

```javascript
assert.equal(contactMethodUpdated.status, 200);
```

Create a new notification rule for the specified user

```javascript
notificationCreated = client.users.user_id(userId).notification_rules.post({
  "notification_rule" : {
    "contact_method_id" : contactMethod.id ,
    "start_delay_in_minutes" : 4
  }
});
```

```javascript
assert.equal(notificationCreated.status, 201);
```

```javascript
ruleId = notificationCreated.body.notification_rule.id;
```

List existing notification rules for the specified user

```javascript
notificationRulesList = client.users.user_id(userId).notification_rules.get();
```

```javascript
assert.equal(notificationRulesList.status, 200);
```

Get details for a notification rule

```javascript
notificationRuleDetails = client.users.user_id(userId).notification_rules.id(ruleId).get();
```

```javascript
assert.equal(notificationRuleDetails.status, 200);
```

Update an existing notification rule

```javascript
notificationRuleUpdated = client.users.user_id(userId).notification_rules.id(ruleId).put({
  "notification_rule" : {
    "contact_method_id" : contactMethod.id ,
    "start_delay_in_minutes" : 5
  }
});
```

```javascript
assert.equal(notificationRuleUpdated.status, 200);
```

List all incident log entries that describe interactions with a specific user

```javascript
incidentLog = client.users.user_id(userId).log_entries.get()
```

```javascript
assert.equal(incidentLog.status, 200);
```

Remove a notification rule

```javascript
notificationRuleRemoved = client.users.user_id(userId).notification_rules.id(ruleId).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(notificationRuleRemoved.status, 204);
```

Remove a contact method and any corresponding notification rules

```javascript
methodDeleted = client.users.user_id(userId).contact_methods.id(contactMethod.id).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(methodDeleted.status, 204);
```

Delete a user

```javascript
userDelete = client.users.user_id(userId).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(userDelete.status, 204);
```