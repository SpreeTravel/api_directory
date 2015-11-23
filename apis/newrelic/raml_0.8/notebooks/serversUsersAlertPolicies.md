---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7718/versions/7852/portal/pages/6613/preview
apiNotebookVersion: 1.1.66
title: Servers, users, alert policies
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
API_KEY = prompt("Please, enter your New Relic API key")
```

```javascript
headers = {
  "Content-Type":"application/json",
  "X-Api-Key": API_KEY
}
```

Auxiliary method for retreiving current time.

```javascript
function getCurrentDate(){
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var hh = today.getHours();
  var min = today.getMinutes();
  var sec = today.getSeconds();
  
  var yyyy = today.getFullYear();
  if(dd<10){dd='0'+dd}
  if(mm<10){mm='0'+mm}
  if(hh<10){hh='0'+hh}
  if(min<10){min='0'+min}
  if(sec<10){sec='0'+sec}
  today = mm+'/'+dd+'/'+yyyy+'_'+hh+":"+min+":"+sec;
  return today;
}
```

Auxiliary method which selects a metric name.

```javascript
function getSomeMetricName(body){
  if(body.metrics==null){
    return null;
  }
  var ind = Math.floor(body.metrics.length/2)
  var metric = body.metrics[ind]
  return metric.name
}
```

Auxiliary method to request an ID of object to edit.

```javascript
function determineIdToUpdate(defaultObj, objArray, objType, operationType ){
  
  var message = ''
  message += 'If you whish to ' + operationType + ' the \"' +defaultObj.name + '\" ' + objType + ', enter \"Y\" or \"Yes\".\n\n'  
  message += 'If you whish to ' + operationType + ' another ' + objType + ', enter it\'s index according to the following list:\n'
  for(var ind in objArray){
    message += '\n' + ind + ": " + objArray[ind].name
  }  
  input = prompt(message)
  if(!input){
    return null
  }
  
  var inputLc = input.toLowerCase()
  if(inputLc=='y'||inputLc=='yes'){
    return defaultObj.id
  }
  var parsedInd = Number.parseInt(input)
  if(Number.isInteger(parsedInd)&&parsedInd>=0&&parsedInd<objArray.length){
    return objArray[parsedInd].id
  }
  return null
}
```

```javascript
// Read about the NewRelic at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7718/versions/7852/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7718/versions/7852/definition');
```

This API endpoint returns a paginated list of the Servers associated with your New Relic account.
Servers can be filtered by their name/hostname, or the list of server IDs.

**Note:** you must have server monitoring configured correctly in order to test the method.

```javascript
serversResponse = client.servers.json.get({},{ headers: headers })
```

```javascript
assert.equal(serversResponse.status, 200)
serverId = serversResponse.body.servers[0].id
```

This API endpoint returns a single Server, identified by ID.

```javascript
serverResponse = client("/servers/{server_id}.json", { server_id: serverId }).get({},{ headers: headers })
```

```javascript
assert.equal(serverResponse.status, 200)
```

This API endpoint allows you to rename your server.
The input is expected to be in JSON or XML format in the body parameter of the PUT request. The exact schema is defined below. Any extra parameters passed in the body will be ignored.

We do not execute this method by default in order to prevent renaming your working server. In the codeblock below we let you to select a server to edit.

```javascript
serverIdToEdit = determineIdToUpdate( serverResponse.body.server, serversResponse.body.servers, "server", "rename" )
```

```javascript
if(serverIdToEdit){
  newServerName = "MyServer_" + getCurrentDate();
  updateServerResponse = client("/servers/{server_id}.json", { server_id: serverIdToEdit }).put({
    "server": {
      "display_name": newServerName
    }
  }, { headers: headers })
}
```

```javascript
if(serverIdToEdit){
  assert.equal(updateServerResponse.status, 200)
}
```

Return a list of known metrics and their value names for the given resource

```javascript
serverMetricsResponse = client.servers.server_id(serverId).metrics.json.get({},{ headers: headers })
```

```javascript
assert.equal(serverMetricsResponse.status,200)
```

This API endpoint returns a list of values for each of the requested metrics. The list of available metrics can be returned using the Metric Name API endpoint

```javascript
serverMetricName = getSomeMetricName(serverMetricsResponse.body)
serverMetricsQueryResponse = client.servers.server_id(serverId).metrics.data.json.get({
  "names[]": serverMetricName
},{ headers: headers })
```

```javascript
assert.equal(serverMetricsQueryResponse.status,200)
assert.equal(serverMetricsQueryResponse.body.metric_data.metrics[0].name,serverMetricName)
```

Show a list of all users

```javascript
usersResponse = client.users.json.get({},{ headers: headers })
```

```javascript
assert.equal(usersResponse.status,200)
userId = usersResponse.body.users[0].id
```

This API endpoint returns a single user, identified by ID.

```javascript
userResponse = client.users.id(userId).json.get({},{ headers: headers })
```

```javascript
assert.equal(userResponse.status,200)
```

This API endpoint returns a paginated list of the alert policies associated with your New Relic account.
Alert policies can be filtered by their name, list of IDs, type (application, key_transaction, or server) or whether or not policies are archived (defaults to filtering archived policies).

```javascript
alertPoliciesResponse = client.alert_policies.json.get({},{ headers: headers })
```

```javascript
assert.equal(alertPoliciesResponse.status,200)
```

In the code block below you may choose an alert policy for subsequent tests. Note that all updates are by default disabled in the notebook.

```javascript
alertPolicyId = alertPoliciesResponse.body.alert_policies[0].id
```

This API endpoint returns a single alert policy, identified by ID.

```javascript
alertPolicyResponse = client.alert_policies.id(alertPolicyId).json.get({},{ headers: headers })
```

```javascript
assert.equal(alertPolicyResponse.status,200)
```

This API endpoint allows you to update your alert policies.
The input is expected to be in JSON or XML format in the body parameters of the PUT request. The exact schema is defined below. Any extra parameters passed in the body will be ignored.
**NOTE**: When updating alertable and notification channel links, the list sent replaces the existing list. Invalid values will be ignored but an empty array will result in alertables/channels being reset.

We do not execute this method by default in order to prevent updating your working alert policy. In the codeblock below we let you to select an alert policy to edit.

```javascript
alertPolicyIdToEdit = determineIdToUpdate( alertPolicyResponse.body.alert_policy, alertPoliciesResponse.body.alert_policies, "alert policy", "edit" )
```

```javascript
if(alertPolicyIdToEdit){
  newAlertPolicyName = "MyPolicy_" + getCurrentDate();
  alertpolicyUpdateResponse = client.alert_policies.id(alertPolicyIdToEdit).json.put({
    "alert_policy": {
      "name": newAlertPolicyName,
      "enabled": true,
      "links": {
        "notification_channels": [
          1
        ],
        "applications": [
          1
        ],
        "key_transactions": [
          1
        ],
        "servers": [
          1
        ]
      }
    }
  },{ headers: headers })
}
```

```javascript
if(alertPolicyIdToEdit){
  assert.equal(alertpolicyUpdateResponse.status,200)
}
```

This API endpoint returns a paginated list of the notification channels associated with your New Relic account.
Notification channels can be filtered by their type or a list of IDs.

```javascript
notificationChannelsResponse = client.notification_channels.json.get({},{ headers: headers })
```

```javascript
assert.equal(notificationChannelsResponse.status,200)
notificationChannelId = notificationChannelsResponse.body.notification_channels[0].id
```

This API endpoint returns a single notification channel, identified by ID.

```javascript
notificationChannelResponse = client.notification_channels.id(notificationChannelId).json.get({},{ headers: headers })
```

```javascript
assert.equal(notificationChannelResponse.status,200)
```

This API endpoint returns a paginated list of the key transactions associated with your New Relic account.

```javascript
keyTransactionsResponse = client.key_transactions.json.get({},{ headers: headers })
```

```javascript
assert.equal(keyTransactionsResponse.status,200)
```

This endpoint returns a single key transaction, identified by ID
**NOTE:** you must have at least one key transaction in order to test the method

```javascript
keyTransactionId = keyTransactionsResponse.body.key_transactions[0].id
keyTransactionResponse = client.key_transactions.id(keyTransactionId).json.get({},{ headers: headers })
```

```javascript
assert.equal(keyTransactionResponse.status,200)
```

This API endpoint deletes a server and all of its reported data.
**WARNING**: Only servers that have stopped reporting can be deleted. This is an irreversible process which will delete all reported data for this server. A server can be deleted in 10 minutes after it has been stopped.

We do not execute this method by default in order to prevent deleting your working server. In the codeblock below we let you to select a server to delete.

```javascript
serverIdToDelete = determineIdToUpdate( serverResponse.body.server, serversResponse.body.servers, "server", "delete" )
```

```javascript
if(serverIdToDelete){
  serverDeleteResponse = client("/servers/{server_id}{format}", { server_id: serverIdToDelete, format: ".json" }).delete({},{ headers: headers })
}
```

```javascript
if(serverIdToDelete){
  assert.equal(serverDeleteResponse.status, 200)
}
```