---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6871/preview
apiNotebookVersion: 1.1.66
title: Connect Apps, Authorized Connect Apps
---

Running this notebook requires some AuthorizedConnectApp object. You may register it at https://www.twilio.com/user/account/connect/apps/add. Then you must try opening the https://www.twilio.com/authorize/{YOUR_CONNECT_APP_CID} URL in your browser, and you'll be prompted to authorize the connect app.

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

Returns a list of Connect App resource representations, each representing a
Connect App you've authorized to access your account. The list includes
paging information.

```javascript
authorizedConnectAppsResponse = client.Accounts.AccountSid(rootAccountSid).AuthorizedConnectApps.json.get()
```

```javascript
assert.equal( authorizedConnectAppsResponse.status, 200 )
appSid = authorizedConnectAppsResponse.body.authorized_connect_apps.length==0?null:authorizedConnectAppsResponse.body.authorized_connect_apps[0].connect_app_sid
```

Get the properties of the authorized application

```javascript
if(appSid){
  authorizedConnectAppResponse = client.Accounts.AccountSid(rootAccountSid).AuthorizedConnectApps.ConnectAppSid(appSid).json.get()
}
```

```javascript
if(appSid){
  assert.equal( authorizedConnectAppResponse.status, 200 )
}
```

Returns a list of UsageTrigger resource representations. The list includes
paging information.
By default, all UsageTriggers are returned. You can filter the list by
specifying one or more query parameters. Note that the query parameters are
case-sensitive

```javascript
triggersResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Triggers.json.get()
```

```javascript
assert.equal( triggersResponse.status, 200 )
```

Creates a new UsageTrigger. Each account can create up to 1,000 UsageTriggers.
Currently, UsageTriggers that are no longer active are not deleted automatically.
Use DELETE to delete triggers you no longer need.

```javascript
triggerCreateResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Triggers.json.post({
  "UsageCategory": "sms",
  "TriggerValue": "1000",
  "CallbackUrl": "http://www.example.com/"
})
```

```javascript
assert.equal( triggerCreateResponse.status, 201 )
triggerSid = triggerCreateResponse.body.sid
```

Returns a representation of the UsageTrigger

```javascript
triggerResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Triggers.UsageTriggerSid(triggerSid).json.get()
```

```javascript
assert.equal( triggerResponse.status, 200 )
```

Tries to update the UsageTrigger's properties, and returns the updated
resource representation if successful.

```javascript
triggerUpdateResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Triggers.UsageTriggerSid(triggerSid).json.post({
  "FriendlyName": "API Notebook test trigger"
})
```

```javascript
assert.equal( triggerUpdateResponse.status, 200 )
```

Delete this UsageTrigger

```javascript
triggerDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Triggers.UsageTriggerSid(triggerSid).json.delete({})
```

```javascript
assert.equal( triggerDeleteResponse.status, 204 )
```

Returns UsageRecords for all usage categories. The list includes paging
information.
By default, the UsageRecords resource will return one UsageRecord for
each Category, representing all usage accrued all-time for the account.
You can filter the usage Category or change the date-range over which usage
is counted using optional GET query parameters.

```javascript
recordsResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Records.json.get()
```

```javascript
assert.equal( recordsResponse.status, 200 )
```

Returns UsageRecords for all usage categories for a specified period.

```javascript
recordsResponse = client.Accounts.AccountSid(rootAccountSid).Usage.Records.Subresource("Daily").json.get()
```

```javascript
assert.equal( recordsResponse.status, 200 )
```

Returns a list of Connect App resource representations, each representing
a Connect App in your account. The list includes paging information.

```javascript
connectAppsResponse = client.Accounts.AccountSid(rootAccountSid).ConnectApps.json.get()
```

```javascript
assert.equal( connectAppsResponse.status, 200 )
cAppSid = connectAppsResponse.body.connect_apps.length==0?null:connectAppsResponse.body.connect_apps[0].sid
```

Get the properties of a Connect App

```javascript
if(cAppSid){
  connectAppsResponse = client.Accounts.AccountSid(rootAccountSid).ConnectApps.ConnectAppSid(cAppSid).json.get()
}
```

```javascript
if(cAppSid){
  assert.equal( connectAppsResponse.status, 200 )
}
```

Tries to update the Connect App's properties, and returns the updated
resource representation if successful. The returned response is identical
to that returned above when making a GET request.

```javascript
if(cAppSid){
  connectAppUpdateResponse = client.Accounts.AccountSid(rootAccountSid).ConnectApps.ConnectAppSid(cAppSid).json.post({
    "Description": connectAppsResponse.body.description
  })
}
```

```javascript
if(cAppSid){
  assert.equal( connectAppUpdateResponse.status, 200 )
}
```