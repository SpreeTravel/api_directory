---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6872/preview
apiNotebookVersion: 1.1.66
title: Outgoing Caller Id, Available Numbers and Calls
---

This notebook requires your account to provide a Twilio outgoing caller id and a Call object. Outgoing caller Ids can be created at https://www.twilio.com/user/account/phone-numbers/verified or in the following notebook: https://api-notebook.anypoint.mulesoft.com/notebooks#0dff4dae914517cfa509. If you are not sure that your Twilio account provides a Call object, just make a call on one of Twilio incoming call numbers registered in your account.

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

```javascript
// Read about the Twilio RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8031/versions/8196/definition');
```

Returns a list of OutgoingCallerId resource representations, each representing
a Caller ID number valid for an account. The list includes paging information.

```javascript
outgoingCallerIdsResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.json.get()
```

```javascript
assert.equal( outgoingCallerIdsResponse.status, 200 )
ocSid = outgoingCallerIdsResponse.body.outgoing_caller_ids.length==0?null:outgoingCallerIdsResponse.body.outgoing_caller_ids[0].sid
```

Get the set of an account's verified phone numbers

```javascript
if(ocSid){
  outgoingCallerIdResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.OutgoingCallerIdSid(ocSid).json.get()
}
```

```javascript
if(ocSid){
  assert.equal( outgoingCallerIdResponse.status, 200 )
}
```

Adding a new outgoing caller ID by means of ```POST /Accounts/{AccountSid}/OutgoingCallerIds.json``` is tested in the following notebook:
https://api-notebook.anypoint.mulesoft.com/notebooks#0dff4dae914517cfa509

Updates the caller id, and returns the updated resource if successful

```javascript
if(ocSid){
  outgoingCallerIdUpdateResponse1 = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.OutgoingCallerIdSid(ocSid).json.post({
    "FriendlyName": outgoingCallerIdResponse.body.friendly_name
  })
}
```

```javascript
if(ocSid){
  assert.equal( outgoingCallerIdUpdateResponse1.status, 200 )
}
```

Updates the caller id, and returns the updated resource if successful

```javascript
if(ocSid){
  outgoingCallerIdUpdateResponse2 = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.OutgoingCallerIdSid(ocSid).json.put({
    "FriendlyName": outgoingCallerIdResponse.body.friendly_name
  })
}
```

```javascript
if(ocSid){
  assert.equal( outgoingCallerIdUpdateResponse2.status, 200 )
}
```

Removing an outgoing caller ID by means of ```DELETE /Accounts/{AccountSid}/OutgoingCallerId/{OutgoingCallerIdSid}.json``` is tested in the following notebook:
https://api-notebook.anypoint.mulesoft.com/notebooks#0dff4dae914517cfa509

Returns a list of all AvailablePhoneNumber subresources for your account by ISO Country. For full information about our phone number support, see our Phone Number CSV (http://www.twilio.com/resources/rates/international-phone-number-rates.csv)

```javascript
availablePhoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).AvailablePhoneNumbers.json.get()
```

```javascript
assert.equal( availablePhoneNumbersResponse.status, 200 )
```

Returns a list of local AvailablePhoneNumber resource representations
that match the specified filters, each representing a phone number tha
is currently available for provisioning within your account.

```javascript
localResponse = client.Accounts.AccountSid(rootAccountSid).AvailablePhoneNumbers.IsoCountryCode("US").Local.json.get()
```

```javascript
assert.equal( localResponse.status, 200 )
```

Returns a list of mobile AvailablePhoneNumber resource representations that match the specified filters, each representing a phone number that is currently available for provisioning within your account

```javascript
mobileResponse = client.Accounts.AccountSid(rootAccountSid).AvailablePhoneNumbers.IsoCountryCode("GB").Mobile.json.get()
```

```javascript
assert.equal( mobileResponse.status, 200 )
```

Returns a list of toll-free AvailablePhoneNumber elements that match the
specified filters, each representing a phone number that is currently
available for provisioning within your account. To provision an available
phone number, POST the number to the IncomingPhoneNumbers resource.

```javascript
tollFreeResponse = client.Accounts.AccountSid(rootAccountSid).AvailablePhoneNumbers.IsoCountryCode("US").TollFree.json.get()
```

```javascript
assert.equal( tollFreeResponse.status, 200 )
```

Returns a list of phone calls made to and from the account identified by {AccountSid}

```javascript
callsResponse = client.Accounts.AccountSid(rootAccountSid).Calls.json.get()
```

```javascript
assert.equal( callsResponse.status, 200 )
callSid = callsResponse.body.calls.length==0?null:callsResponse.body.calls[0].sid
```

Returns the single Call resource identified by {CallSid}

```javascript
if(callSid){
  //callResponse = client.Accounts.AccountSid(rootAccountSid).Calls.CallSid(callSid).json.get()
  callResponse = client("/Accounts/{AccountSid}/Calls/{CallSid}.json", {
    AccountSid: rootAccountSid,
    CallSid: callSid
  }).get()
}
```

```javascript
if(callSid){
  assert.equal( callResponse.status, 200 )
}
```

Modify a phone call

```javascript
if(callSid){
  callUpdateResponse = client("/Accounts/{AccountSid}/Calls/{CallSid}.json", {
    AccountSid: rootAccountSid,
    CallSid: callSid
  }).post({})
}
```

```javascript
if(callSid){
  assert.equal( callUpdateResponse.status, 200 )
}
```

Returns a list of Recording resource representations, each representing a
recording generated during the course of a phone call.

```javascript
if(callSid){
  recordingsResponse = client.Accounts.AccountSid(rootAccountSid).Calls.CallSid(callSid).Recordings.json.get()
}
```

```javascript
if(callSid){
  assert.equal( recordingsResponse.status, 200 )
}
```

Returns a list of notifications generated for an account. The list includes
paging information.

```javascript
if(callSid){
  notificationsResponse = client.Accounts.AccountSid(rootAccountSid).Calls.CallSid(callSid).Notifications.json.get()
}
```

```javascript
if(callSid){
  assert.equal( notificationsResponse.status, 200 )
}
```