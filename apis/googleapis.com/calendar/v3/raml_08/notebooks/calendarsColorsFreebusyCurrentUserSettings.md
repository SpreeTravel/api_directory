---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8157/versions/8356/portal/pages/7071/preview
apiNotebookVersion: 1.1.66
title: Calendars, Colors, Freebusy, Current user settings
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
CLIENT_ID = prompt("Please, enter Client ID of your Google application.")
CLIENT_SECRET = prompt("Please, enter Client Secret of your Google application.")
```

```javascript
// Read about the GMail RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8157/versions/8356/contracts
//GMAIL client!
API.createClient('mailClient', '/apiplatform/repository/public/organizations/30/apis/7881/versions/8035/definition');
```

```javascript
API.authenticate(mailClient,"oauth_2_0",{
  clientId: CLIENT_ID,
  clientSecret: CLIENT_SECRET
})
```

```javascript
// Read about the Google Calendar RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8157/versions/8356/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8157/versions/8356/definition');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Creates a calendar

```javascript
calendarTitle = prompt("Input title for temporary calendar ")
```

```javascript
calendarsCreateResponse = client.calendars.post({
  "kind" : "calendar#calendar" ,
  "etag" : "etag" ,
  "summary" : calendarTitle ,
  "description" : calendarTitle ,
  "location" : "CN" ,
  "timeZone" : "UTC"
})
```

```javascript
assert.equal( calendarsCreateResponse.status, 200 )
ID_CALENDAR = calendarsCreateResponse.body.id
```

Returns metadata for a calendar

```javascript
calendarResponse = client.calendars.calendar_id(ID_CALENDAR).get()
```

```javascript
assert.equal( calendarResponse.status, 200 )
```

Updates metadata for a calendar

```javascript
calendarUpdateResponse = client.calendars.calendar_id(ID_CALENDAR).put({
  "summary" : "string" ,
  "description" : "string" ,
  "location" : "UK" ,
  "timeZone" : "UTC"
})
```

```javascript
assert.equal( calendarUpdateResponse.status, 200 )
```

Updates metadata for a calendar. This method supports patch semantics

```javascript
calendarPatchResponse = client.calendars.calendar_id(ID_CALENDAR).patch({
  "kind" : "calendar#calendar" ,
  "etag" : "etag" ,
  "summary" : "string" ,
  "description" : "string" ,
  "location" : "US" ,
  "timeZone" : "UTC"
})
```

```javascript
assert.equal( calendarPatchResponse.status, 200 )
```

Returns the rules in the access control list for the calendar

```javascript
aclResponse = client.calendars.calendar_id(ID_CALENDAR).acl.get()
```

```javascript
assert.equal( aclResponse.status, 200 )
```

Creates an access control rule

```javascript
email = prompt("Please input email address of a user or group, for providing owner access to calendar. \nNote. Do not use your own email.")
```

```javascript
aclCreateResponse = client.calendars.calendar_id(ID_CALENDAR).acl.post({
  "scope": {
    "type": "user",
    "value": email
  },
  "role": "writer"
})
```

```javascript
assert.equal( aclCreateResponse.status, 200 )
ID_ACL = aclCreateResponse.body.id
ETAG = aclCreateResponse.body.etag
KIND = aclCreateResponse.body.kind
```

Returns an access control rule

```javascript
ruleResponse = client.calendars.calendar_id(ID_CALENDAR).acl.rule_id(ID_ACL).get()
```

```javascript
assert.equal( ruleResponse.status, 200 )
```

Updates an access control rule

```javascript
ruleUpdateResponse = client.calendars.calendar_id(ID_CALENDAR).acl.rule_id(ID_ACL).put({
  "scope": {
    "type": "user",
    "value": email
  },
  "role": "reader"
})
```

```javascript
assert.equal( ruleUpdateResponse.status, 200 )
```

Updates an access control rule. This method supports patch semantics

```javascript
rulePatchResponse = client.calendars.calendar_id(ID_CALENDAR).acl.rule_id(ID_ACL).patch({
  "kind" : KIND ,
  "etag" : ETAG ,
  "id" : ID_ACL ,
  "scope": {
    "type": "user",
    "value": email
  }
})
```

```javascript
assert.equal( rulePatchResponse.status, 200 )
```

Watch for changes to ACL resources

```javascript
//Not supported
// watchCreateResponse = client.calendars.calendar_id(ID_CALENDAR).acl.watch.post({
//   "id" : "user:ramltest3@list.ru" ,
//   "type" : "api#ki89p" ,
//   "address" : "ramlTest@list.ru"
// })
```

```javascript
//assert.equal( watchCreateResponse.status, 200 )
```

Deletes an access control rule

```javascript
ruleDeleteResponse = client.calendars.calendar_id(ID_CALENDAR).acl.rule_id(ID_ACL).delete()
```

```javascript
assert.equal( ruleDeleteResponse.status, 204 )
```

Getting user primary calendar id 

```javascript
myMail = mailClient("/me/profile").get()
```

```javascript
ID = myMail.body.emailAddress
```

Clears a primary calendar. This operation deletes all data associated with the primary calendar of an account and cannot be undone

```javascript
clearCalendarResponse = client.calendars.calendar_id(ID).clear.post()
```

```javascript
assert.equal( clearCalendarResponse.status, 204 )
```

Deletes a secondary calendar

```javascript
calendarDeleteResponse = client.calendars.calendar_id(ID_CALENDAR).delete()
```

```javascript
assert.equal( calendarDeleteResponse.status, 204 )
```

Returns the color definitions for calendars and events

```javascript
colorsResponse = client.colors.get()
```

```javascript
assert.equal( colorsResponse.status, 200 )
```

Returns free/busy information for a set of calendars

```javascript
freeBusyCreateResponse = client.freeBusy.post({
  "timeMin" : "2014-10-10T04:45:03.331Z",
  "timeMax" : "2014-12-12T04:45:03.331Z" ,
  "timeZone" : "UTC" ,
  "groupExpansionMax" : 15 ,
  "calendarExpansionMax" : 15 ,
  "items" : [
    {
      "id" : "1"
    }
  ]
})
```

```javascript
assert.equal( freeBusyCreateResponse.status, 200 )
```

Returns all user settings for the authenticated user

```javascript
usersMeSettingsResponse = client.users.me.settings.get()
```

```javascript
assert.equal( usersMeSettingsResponse.status, 200 )
SETTING = usersMeSettingsResponse.body.items[0].id
```

Returns a single user setting

```javascript
settingResponse = client.users.me.settings.setting(SETTING).get()
```

```javascript
assert.equal( settingResponse.status, 200 )
```

Watch for changes to Settings resources

```javascript
//Not supported
// watchCreateResponse = client.users.me.settings.watch.post({
//   "id" : "string" ,
//   "token" : "string" ,
//   "type" : "string" ,
//   "address" : "string" ,
//   "params" : {
//     "ttl" : 2400
//   }
// })
```

```javascript
//assert.equal( watchCreateResponse.status, 200 )
```

Stop watching resources through this channel

```javascript
//Not supported
// channelsStopCreateResponse = client.channels.stop.post({
//   "id" : "ramlTest3@gmail.com" ,
//   "resourceId" : ""
// })
```

```javascript
//assert.equal( channelsStopCreateResponse.status, 200 )
```