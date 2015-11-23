---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6946/preview
apiNotebookVersion: 1.1.66
title: Incremental
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
DOMAIN = prompt("Please, enter your Zendesk subdomain.")
CLIENT_ID = prompt("Please, enter ID of your Zendesk client.")
CLIENT_SECRET = prompt("Please, enter Secret of your Zendesk client.")
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8077/versions/8266/definition',{
  baseUriParameters: {
    domain: DOMAIN,
    version: 'v2'
}});
```

```javascript
API.set(client, 'baseUriParameters', {
  domain: DOMAIN,
  version: 'v2'
})
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

```javascript
window.alert("Requests with start_time less than 5 minutes old will be rejected. You are only allowed to make 1 API call to this API end point every minute and we will return up to 1000 items per request.")
```

Get information about tickets updated since a given point in time
Requests with start_time less than 5 minutes old will be rejected. You are only allowed to make 1 API call to this API end point every minute and we will return up to 1000 items per request.

```javascript
ticketsResponse = client.incremental.tickets.json.get({"start_time":1332034771})
```

```javascript
assert.equal( ticketsResponse.status, 200 )
```

```javascript
window.alert("Please, wait for one minute before you continue executing the Notebook.")
```

Get information about ticket events updated since a given point in time
Requests with start_time less than 5 minutes old will be rejected. You are only allowed to make 1 API call to this API end point every minute and we will return up to 1000 items per request.

```javascript
ticketEventsResponse = client.incremental.ticket_events.json.get({"start_time":1332034771})
```

```javascript
assert.equal( ticketEventsResponse.status, 200 )
```

```javascript
window.alert("Please, wait for one minute before you continue executing the Notebook.")
```

Get information about organizations updated since a given point in time
Requests with start_time less than 5 minutes old will be rejected. You are only allowed to make 1 API call to this API end point every minute and we will return up to 1000 items per request.

```javascript
organizationsResponse = client.incremental.organizations.json.get({"start_time":1332034771})
```

```javascript
assert.equal( organizationsResponse.status, 200 )
```

```javascript
window.alert("Please, wait for one minute before you continue executing the Notebook.")
```

Get information about users updated since a given point in time
Requests with start_time less than 5 minutes old will be rejected. You are only allowed to make 1 API call to this API end point every minute and we will return up to 1000 items per request.

```javascript
usersResponse = client.incremental.users.json.get({"start_time":1332034771})
```

```javascript
assert.equal( usersResponse.status, 200 )
```