---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7593/versions/7719/portal/pages/6599/preview
apiNotebookVersion: 1.1.66
title: Schedules
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
testUserName = "Notebook Test User 2"
testUserEmail = "test_user_2@somehost.com" 
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


List existing on-call schedules

```javascript
schedulesList = client.schedules.get();
```

```javascript
assert.equal(schedulesList.status, 200);
```

Let's pick a test user if he already exists o create him in the opposite case.

```javascript
user = null
{
  usersResponse = client.users.get();
  users = usersResponse.body.users
  for(var i in users){
    var u = users[i]
    if(u.name == testUserName){
      user = u
      break
    }
  }
  if(!user){
    requester = users[0]
    userCreateResponse = client.users.post({
      "role" : "user" ,
      "name" : testUserName ,
      "email" : testUserEmail ,
      "requester_id" : requester.id
    });
    user = userCreateResponse.body.user
  }
}
```

We need two dates to create a new schedule.

```javascript
timeSince = generateDate(-1)
timeUntil = generateDate(1)
```

Create a new on-call schedule

```javascript
scheduleCreate = client.schedules.post({
  "since" : timeSince ,
  "overflow" : 1 ,
  "until" : timeUntil ,
  "schedule" : {
    "schedule_layers" : [ {
      "start" : timeSince ,
      "users" : [ {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 1
      }  ] ,
      "restriction_type" : null ,
      "rotation_virtual_start" : timeSince ,
      "priority" : 0 ,
      "rotation_turn_length_seconds" : 604800 ,
      "end" : null ,
      "restrictions" : [ ] ,
      "id" : null ,
      "name" : "Schedule Layer 1"
    } , {
      "start" : timeSince ,
      "users" : [ {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 1
      } ] ,
      "restriction_type" : "Daily" ,
      "rotation_virtual_start" : timeSince ,
      "rotation_turn_length_seconds" : 86400 ,
      "priority" : 1 ,
      "restrictions" : [ {
        "duration_seconds" : 43200 ,
        "start_time_of_day" : "00:00:00"
      } ] ,
      "end" : null ,
      "id" : null ,
      "name" : "Schedule Layer 2"
    } ] ,
    "time_zone" : "Eastern Time (US & Canada)" ,
    "id" : null,
    "name" : "Primary On-Call Schedule"
  }
})
```

```javascript
assert.equal(scheduleCreate.status, 201);
schedule = scheduleCreate.body.schedule
scheduleId = scheduleCreate.body.schedule.id
```

Show detailed information about a schedule, including entries for each layer and sub-schedule

```javascript
scheduleInfo = client.schedules.schedule_id(scheduleId).get();
```

```javascript
assert.equal(scheduleInfo.status,200)
```

List all the users on-call in a given schedule for a given time range

```javascript
scheduledUsers = client.schedules.schedule_id(scheduleId).users.get();
```

```javascript
assert.equal(scheduledUsers.status, 200);
```

List schedule entries that are active for a given time range for a specified on-call schedule

```javascript
scheduleEntries = client.schedules.schedule_id(schedule.id).entries.get({
  "since": timeSince,
  "until": timeUntil
});
```

```javascript
assert.equal(scheduleEntries.status, 200);
```

List overrides for a given time range

```javascript
overridesList = client.schedules.schedule_id(scheduleId).overrides.get({
  "since": timeSince,
  "until": timeUntil,
  "editable": "true"
});
```

```javascript
assert.equal(overridesList.status, 200);
```

Create an override for a specific user covering the specified time range

```javascript
overrideCreate = client.schedules.schedule_id(scheduleId).overrides.post({
  "override" : {
    "user_id" : user.id ,
    "start" : generateDate(2),
    "end" : generateDate(5)
  }
});
```

```javascript
assert.equal(overrideCreate.status, 201);
overrideId = overrideCreate.body.override.id;
```

Preview the configuration of an on-call schedule

```javascript
schedulePreview = client.schedules.preview.post({
  "schedule" : {
    "id" : null ,
    "name" : "Tertiary" ,
    "time_zone" : "Eastern Time (US & Canada)" ,
    "schedule_layers" : [ {
      "restriction_type" : "Daily" ,
      "priority" : 0 ,
      "start" : timeSince ,
      "rotation_turn_length_seconds" : 604800 ,
      "rotation_virtual_start" : timeSince ,
      "users" : [ {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 1
      } , {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 2
      } ] ,
      "end" : null ,
      "id" : null ,
      "restrictions" : [ {
        "duration_seconds" : 43200 ,
        "start_time_of_day" : "00:00:00"
      } ] ,
      "name" : "Schedule Layer 1"
    } , {
      "restriction_type" : null ,
      "priority" : 1 ,
      "start" : timeSince ,
      "rotation_turn_length_seconds" : 604800 ,
      "rotation_virtual_start" : timeSince ,
      "users" : [ {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 1
      } ] ,
      "end" : null ,
      "id" : null ,
      "restrictions" : [ ] ,
      "name" : "Schedule Layer 2"
    } ]
  }
});
```

```javascript
assert.equal(schedulePreview.status, 200);
```

Update an existing on-call schedule

```javascript
scheduleUpdate = client.schedules.schedule_id(scheduleId).put({
  "since" : timeSince ,
  "until" : timeUntil ,
  "overflow" : 1 ,
  "schedule" : {
    "time_zone" : "UTC" ,
    "name" : "new name" ,
    "schedule_layers" : [  {
      "start" : timeSince ,
      "users" : [ {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 1
      }  ] ,
      "restriction_type" : null ,
      "rotation_virtual_start" : timeSince ,
      "priority" : 0 ,
      "rotation_turn_length_seconds" : 604800 ,
      "end" : null ,
      "restrictions" : [ ] ,
      "id" :  scheduleCreate.body.schedule.schedule_layers[0].id ,
      "name" : "Schedule Layer 1"
    },{
      "start" : timeSince ,
      "users" : [ {
        "user" : {
          "id" : user.id
        } ,
        "member_order" : 1
      } ] ,
      "restriction_type" : "Daily" ,
      "rotation_virtual_start" : timeSince ,
      "rotation_turn_length_seconds" : 86400 ,
      "priority" : 1 ,
      "restrictions" : [ {
        "duration_seconds" : 43200 ,
        "start_time_of_day" : "00:00:00"
      } ] ,
      "end" : null ,
      "id" : scheduleCreate.body.schedule.schedule_layers[1].id ,
      "name" : "Schedule Layer 2"
    } ],
  }
});
```

```javascript
assert.equal(scheduleUpdate.status, 200);
```

Remove an override

```javascript
overrideRemove = client.schedules.schedule_id(scheduleId).overrides.id(overrideId).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(overrideRemove.status, 204);
```

Delete schedule

```javascript
scheduleDelete = client.schedules.schedule_id(scheduleId).delete({},{headers:{"Content-Type":"application/json"}});
```

```javascript
assert.equal(scheduleDelete.status, 204);
```

Garbage collection: remove the test user.

```javascript
client.users.user_id(user.id).delete({},{headers:{"Content-Type":"application/json"}});
```