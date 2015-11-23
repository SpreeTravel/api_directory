---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8157/versions/8356/portal/pages/7070/preview
apiNotebookVersion: 1.1.66
title: CalendarList, Event
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
// Read about the Google Calendar RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8157/versions/8356/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8157/versions/8356/definition');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Create temporary calendar

```javascript
calendarTitle = prompt("Input title for temporary calendar ")
```

```javascript
calendarsCreateResponse = client.calendars.post({
  "kind" : "calendar#calendar" ,
  "etag" : "etag" ,
  "summary" : calendarTitle ,
  "description" : calendarTitle ,
  "location" : "US" ,
  "timeZone" : "UTC"
})
ID_CALENDAR = calendarsCreateResponse.body.id
```

Returns entries on the user's calendar list

```javascript
myCalendarListResponse = client.users.me.calendarList.get()
```

```javascript
assert.equal( myCalendarListResponse.status, 200 )
```

Adds an entry to the user's calendar list

```javascript
myCalendarListCreateResponse = client.users.me.calendarList.post({
  "id" : ID_CALENDAR ,
  "defaultReminders" : [
    {
      "method" : "popup" ,
      "minutes" : 30,
    }
  ] ,
  "notificationSettings" : {
    "notifications" : [
      {
        "type" : "eventResponse" ,
        "method" : "email"
      }
    ]
  }
})
```

```javascript
assert.equal( myCalendarListCreateResponse.status, 200 )
ID_CALENDAR_LIST = myCalendarListCreateResponse.body.id
```

Returns an entry on the user's calendar list

```javascript
calendarResponse = client.users.me.calendarList.calendar_id(ID_CALENDAR).get()
```

```javascript
assert.equal( calendarResponse.status, 200 )
```

Updates an entry on the user's calendar list

```javascript
calendarUpdateResponse = client.users.me.calendarList.calendar_id(ID_CALENDAR).put({
  "id" : ID_CALENDAR ,
  "colorId" : 17 ,
  "backgroundColor" : "#9a9cff" ,
  "foregroundColor" : "#000000" ,
  "hidden" : true ,
  "selected" : true ,
  "defaultReminders" : [
    {
      "method" : "popup" ,
      "minutes" : 30,
    }
  ] ,
  "notificationSettings" : {
    "notifications" : [
      {
        "type" : "eventCreation" ,
        "method" : "email"
      }
    ]
  }
})
```

```javascript
assert.equal( calendarUpdateResponse.status, 200 )
```

Updates an entry on the user's calendar list. This method supports patch semantics


```javascript
calendarPatchResponse = client.users.me.calendarList.calendar_id(ID_CALENDAR).patch({
  "id" : ID_CALENDAR ,
  "colorId" : 17 ,
  "backgroundColor" : "#9a9cff" ,
  "foregroundColor" : "#000000" ,
  "hidden" : true ,
  "selected" : true ,
  "defaultReminders" : [
    {
      "method" : "popup" ,
      "minutes" : 30,
    }
  ] ,
  "notificationSettings" : {
    "notifications" : [
      {
        "type" : "eventCreation" ,
        "method" : "email"
      }
    ]
  }
})
```

```javascript
assert.equal( calendarPatchResponse.status, 200 )
```

Creates an event

```javascript
eventsCreateResponse = client.calendars.calendar_id(ID_CALENDAR).events.post({

  "start" : {
 
    "dateTime" : "2014-11-21T10:28:20Z" ,
    "timeZone" : "UTC"
  } ,
  "end" : {

    "dateTime" : "2014-11-21T11:29:20Z" ,
    "timeZone" : "UTC"
  } ,
  "attendees" : [
    {
    
      "email" : "ramlTest3@gmail.com" 
    }
  ] ,
  "reminders" : {
    "useDefault": false,
    "overrides" : [
      {
        "method" : "popup" ,
        "minutes" : "0"
      }
    ]
  }
})
```

```javascript
assert.equal( eventsCreateResponse.status, 200)
ID_EVENT = eventsCreateResponse.body.id
iCalUID = eventsCreateResponse.body.iCalUID
```

Returns events on the specified calendar

```javascript
eventsResponse = client.calendars.calendar_id(ID_CALENDAR).events.get()
```

```javascript
assert.equal( eventsResponse.status, 200 )
```

Imports an event. This operation is used to add a private copy of an existing event to a calendar

```javascript
importEventResponse = client.calendars.calendar_id(ID_CALENDAR).events.import.post({

  "start" : {
 
    "dateTime" : "2014-11-21T10:28:20Z" ,
    "timeZone" : "UTC"
  } ,
  "end" : {

    "dateTime" : "2014-11-21T11:29:20Z" ,
    "timeZone" : "UTC"
  } ,
  "iCalUID":iCalUID, 
  "attendees" : [
    {
    
      "email" : "ramlTest3@gmail.com" 
    }
  ] ,
  "reminders" : {
    "useDefault": false,
    "overrides" : [
      {
        "method" : "popup" ,
        "minutes" : "10"
      }
    ]
  }
})
```

```javascript
assert.equal( importEventResponse.status, 200 )
```

Returns an event

```javascript
eventIdResponse = client.calendars.calendar_id(ID_CALENDAR).events.event_id(ID_EVENT).get()
```

```javascript
assert.equal( eventIdResponse.status, 200 )
```

Updates an event

```javascript
eventUpdateResponse = client.calendars.calendar_id(ID_CALENDAR).events.event_id(ID_EVENT).put({

  "start" : {
 
    "dateTime" : "2014-11-21T10:28:20Z" ,
    "timeZone" : "UTC"
  } ,
  "end" : {

    "dateTime" : "2014-11-21T11:29:20Z" ,
    "timeZone" : "UTC"
  } ,
  "attendees" : [
    {
    
      "email" : "ramlTest3@gmail.com" 
    }
  ] ,
  "reminders" : {
    "useDefault": false,
    "overrides" : [
      {
        "method" : "popup" ,
        "minutes" : "20"
      }
    ]
  }
})
```

```javascript
assert.equal( eventUpdateResponse.status, 200 )
```

Updates an event. This method supports patch semantics

```javascript
eventPatchResponse = client.calendars.calendar_id(ID_CALENDAR).events.event_id(ID_EVENT).patch({

  "start" : {
 
    "dateTime" : "2014-11-21T10:28:20Z" ,
    "timeZone" : "UTC"
  } ,
  "end" : {

    "dateTime" : "2014-11-21T11:29:20Z" ,
    "timeZone" : "UTC"
  } ,
  "attendees" : [
    {
    
      "email" : "ramlTest3@gmail.com" 
    }
  ] ,
  "reminders" : {
    "useDefault": false,
    "overrides" : [
      {
        "method" : "popup" ,
        "minutes" : "30"
      }
    ]
  }
})
```

```javascript
assert.equal( eventPatchResponse.status, 200 )
```

Returns instances of the specified recurring event

```javascript
instancesResponse = client.calendars.calendar_id(ID_CALENDAR).events.event_id(ID_EVENT).instances.get()
```

```javascript
assert.equal( instancesResponse.status, 200 )
```

Create temporary calendar

```javascript
secondCalendarTitle = prompt("Input title for second temporary calendar ")
```

```javascript
secondCalendarsCreateResponse = client.calendars.post({
  "kind" : "calendar#calendar" ,
  "etag" : "etag2" ,
  "summary" : secondCalendarTitle,
  "description" : secondCalendarTitle,
  "location" : "BU" ,
  "timeZone" : "UTC"
})
ID_SECOND_CALENDAR = secondCalendarsCreateResponse.body.id
```

Moves an event to another calendar, i.e. changes an event's organizer.
Required query parameters: destination

```javascript
moveCreateResponse = client.calendars.calendar_id(ID_CALENDAR).events.event_id(ID_EVENT).move.post({},{"query":{
  "destination": ID_SECOND_CALENDAR
	}
})
```

```javascript
assert.equal( moveCreateResponse.status, 200 )
```

Deletes an event

```javascript
eventDeleteResponse = client.calendars.calendar_id(ID_SECOND_CALENDAR).events.event_id(ID_EVENT).delete()
```

```javascript
assert.equal( eventDeleteResponse.status, 204 )
```

Creates an event based on a simple text string.
Required query parameters: text

```javascript
quickAddCreateResponse = client.calendars.calendar_id(ID_CALENDAR).events.quickAdd.post({}, {"query":{
  "text": "My event"
	}
})
```

```javascript
assert.equal( quickAddCreateResponse.status, 200 )
```

Watch for changes to Events resources

```javascript
//Not supported
// watchCreateResponse = client.calendars.calendar_id(ID_CALENDAR).events.watch.post({
//   "id": "string",
//   "token": "string",
//   "type": "string",
//   "address": "string",
//   "params": {
//     "ttl": "string"
//   }
// })
```

```javascript
//assert.equal( watchCreateResponse.status, 200 )
```

Deletes an entry on the user's calendar list

```javascript
calendarListDeleteResponse = client.users.me.calendarList.calendar_id(ID_CALENDAR_LIST).delete()
```

```javascript
assert.equal( calendarListDeleteResponse.status, 204 )
```

Watch for changes to CalendarList resources

```javascript
//Not supported
// watchCreateResponse = client.users.me.calendarList.watch.post({
//   "id" : "string" ,
//   "token" : "string" ,
//   "type" : "string" ,
// //   "address" : "string" ,
//   "params" : {
//     "ttl" : "string"
//   }
// })
```

```javascript
//assert.equal( watchCreateResponse.status, 200 )
```

Delete temporary calendar

```javascript
client.calendars.calendar_id(ID_CALENDAR).delete()
```

```javascript
client.calendars.calendar_id(ID_SECOND_CALENDAR).delete()
```