---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6870/preview
apiNotebookVersion: 1.1.66
title: Applications, Notifications, Incoming Numbers
---

This notebook requires your account to provide a Twilio incoming phone number and a Notification object. You may induce creation of Notification object by, for example, requesting password reset at https://www.twilio.com/reset-password (Note that requesting password reset does not itself changes or disables the password. You will receive an email which may be ignored if you do not really want to reset a password).

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
rootAccountSid = prompt("Please, enter your Account SID which can be found at https://www.twilio.com/user/account")
```

Names of applications operated by the notebook

```javascript
testAppName = "Notebook Test App"
newTestAppName = "Notebook Test App (upd)"
```

```javascript
// Read about the Twilio RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8031/versions/8196/definition');
```

Retrieve a list of the Account resources belonging to the account used to make the
API request. This list will include that Account as well.

```javascript
accountsResponse = client.Accounts.json.get()
```

```javascript
assert.equal(accountsResponse.status, 200 )
```

Creating an account by means of ```POST /Accounts.json``` is tested in the following notebook:
https://api-notebook.anypoint.mulesoft.com/notebooks#0dff4dae914517cfa509

Retreive a particular account

```javascript
accountResponse = client("/Accounts/{AccountSid}.json",{
  AccountSid : rootAccountSid
}).get()
```

```javascript
assert.equal(accountResponse.status, 200 )
```

Returns a representation of an account

```javascript
accountResponse = client("/Accounts/{AccountSid}.json", {
  "AccountSid": rootAccountSid
}).get()
```

```javascript
assert.equal(accountResponse.status, 200 )
testAccount = accountsResponse.body.accounts[0]
```

Allows you to modify the properties of an account

```javascript
accountUpdateResponse = client("/Accounts/{AccountSid}.json", {
  "AccountSid": rootAccountSid
}).post({
  FriendlyName: accountResponse.body.friendly_name
},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})
```

```javascript
assert.equal(accountUpdateResponse.status, 200 )
```

Returns a list of Application resource representations, each representing
an application within your account. The list includes paging information.

```javascript
applicationsResponse = client.Accounts.AccountSid(rootAccountSid).Applications.json.get()
```

```javascript
assert.equal(applicationsResponse.status, 200 )
applications = applicationsResponse.body.applications
```

Let's delete applications which could have been created during previous notebook runs.

```javascript
{
  applications = applicationsResponse.body.applications
  for(var ind in applications){
    var app = applications[ind]
    if( app.friendly_name == testAppName || app.friendly_name == newTestAppName ){      
      client.Accounts.AccountSid(rootAccountSid).Applications.ApplicationSid(app.sid).json.delete({})
    }
  }
}
```

Creates a new application within your account

```javascript
applicationCreateResponse = client.Accounts.AccountSid(rootAccountSid).Applications.json.post({
  "FriendlyName": testAppName
})
```

```javascript
assert.equal( applicationCreateResponse.status, 201 )
appSid = applicationCreateResponse.body.sid
```

Get application instance resource

```javascript
applicationResponse = client.Accounts.AccountSid(rootAccountSid).Applications.ApplicationSid(appSid).json.get()
```

```javascript
assert.equal( applicationResponse.status, 200 )
```

Tries to update the application's properties, and returns the updated
resource representation if successful. The returned response is identical
to that returned above when making a GET request.

```javascript
applicationUpdateResponse = client.Accounts.AccountSid(rootAccountSid).Applications.ApplicationSid(appSid).json.post({
  VoiceMethod:"GET"
})
```

```javascript
assert.equal( applicationUpdateResponse.status, 200 )
```

Delete the application.

```javascript
applicationDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Applications.ApplicationSid(appSid).json.delete({})
```

```javascript
assert.equal(applicationDeleteResponse.status, 204 )
```

Returns a list of notifications generated for an account. The list includes paging information.

We need a notification for further tests. If your notifications set is empty, you can force the Notification object creation by, for example, requesting password reset at https://www.twilio.com/reset-password. This action does not resets password by itself.

```javascript
notificationsResponse = client.Accounts.AccountSid(rootAccountSid).Notifications.json.get()
```

```javascript
assert.equal( notificationsResponse.status, 200 )
notificationSid = notificationsResponse.body.notifications.length == 0 ? null : notificationsResponse.body.notifications[0].sid
```

Get a notification entry

```javascript
if(notificationSid){
  notificationResponse = client.Accounts.AccountSid(rootAccountSid).Notifications.NotificationSid(notificationSid).json.get()
}
```

```javascript
if(notificationSid){
  assert.equal( notificationResponse.status, 200 )
}
```

Deletes the notification identified by {NotificationSid} from an account's log.

```javascript
doDeleteNotification = false
if(notificationSid){
  var text = notificationResponse.body.message_text
  if(text.length>500){ text = text.substring(0,500) }
  doDeleteNotification = window.confirm("We are about to test notification removal.\nDo you want to delete the following notification?\n\n"+notificationResponse.body.message_date+"\n\n"+text)
  if(doDeleteNotification){
    notificationDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Notifications.NotificationSid(notificationSid).json.delete({})
  }
}
```

```javascript
if(notificationSid && doDeleteNotification){
  assert.equal( notificationDeleteResponse.status, 204 )
}
```

Returns a list of IncomingPhoneNumber resource representations, each
representing a phone number given to your account. The list includes paging
information.

```javascript
incomingPhoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.json.get()
```

```javascript
assert.equal( incomingPhoneNumbersResponse.status, 200 )
numberSid = incomingPhoneNumbersResponse.body.incoming_phone_numbers.length==0?null:incomingPhoneNumbersResponse.body.incoming_phone_numbers[0].sid
```

Get info about incoming call's phone number

```javascript
if(numberSid){
  incomingPhoneNumberResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(numberSid).json.get()
}
```

```javascript
if(numberSid){
  assert.equal( incomingPhoneNumberResponse.status, 200 )
}
```

Tries to update the incoming phone number's properties, and returns the
updated resource representation if successful. The returned response is
identical to that returned above when making a GET request.

```javascript
if(numberSid){
  incomingPhoneNumberUpdateResponse1 = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(numberSid).json.post({
    FriendlyName : incomingPhoneNumberResponse.body.friendly_name
  })
}
```

```javascript
if(numberSid){
  assert.equal( incomingPhoneNumberUpdateResponse1.status, 200 )
}
```

Tries to update the incoming phone number's properties, and returns the
updated resource representation if successful. The returned response is
identical to that returned above when making a GET request.

```javascript
//Same as the POST method above
if(numberSid){
  incomingPhoneNumberUpdateResponse2 = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(numberSid).json.put({
    FriendlyName : incomingPhoneNumberResponse.body.friendly_name
  })
}
```

```javascript
if(numberSid){
  assert.equal( incomingPhoneNumberUpdateResponse2.status, 200 )
}
```

Deleting a phone number by means of ```DELETE /Accounts/{AccountSid}/IncomingPhoneNumbers/{IncomingPhoneNumberSid}.json``` is tested in the following notebook:
https://api-notebook.anypoint.mulesoft.com/notebooks#0dff4dae914517cfa509

Returns a list of local <IncomingPhoneNumber> elements, each representing a local (not toll-free) phone number given to your account, under an <IncomingPhoneNumbers> list element that includes paging information. Works exactly the same as the IncomingPhoneNumber resource, but filters out toll-free numbers

```javascript
localNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.Local.json.get()
```

```javascript
assert.equal( localNumbersResponse.status, 200 )
```

Returns a list of local <IncomingPhoneNumber> elements, each representing a toll-free phone number given to your account, under an <IncomingPhoneNumbers> list element that includes paging information. Works exactly the same as the IncomingPhoneNumber resource, but filters out all numbers that aren't toll-free

```javascript
tollFreeNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.TollFree.json.get()
```

```javascript
assert.equal( tollFreeNumbersResponse.status, 200 )
```

Returns a list of local <IncomingPhoneNumber> elements, each representing a mobile phone number given to your account, under an <IncomingPhoneNumbers> list element that includes paging information. Works exactly the same as the IncomingPhoneNumber resource, but filters out local and toll free numbers

```javascript
mobileNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.Mobile.json.get()
```

```javascript
assert.equal( mobileNumbersResponse.status, 200 )
```