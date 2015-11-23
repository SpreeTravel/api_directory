---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7718/versions/7852/portal/pages/6611/preview
apiNotebookVersion: 1.1.66
title: Applications, application hosts and instances
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

Auxiliary method for retrieving current time.

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
    message += '\n' + ind + ": " + objArray[ind].name + '\n'
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


This API endpoint returns a paginated list of the Applications associated with your New Relic account.
Applications can be filtered by their name, the list of application IDs or the application language as reported by the agents.

```javascript
appsResponse = client.applications.json.get({},{ headers: headers })
```

```javascript
assert.equal(appsResponse.status,200)
myAppId = appsResponse.body.applications[0].id
```

This API endpoint returns a single Application, identified its ID.

```javascript
appResponse = client.applications.id(myAppId).json.get({},{ headers: headers })
```

```javascript
assert.equal(appResponse.status,200)
```

This API endpoint allows you to update certain parameters of your application.
The input is expected to be in JSON or XML format in the body parameter of the PUT request. The exact schema is defined below. Any extra parameters passed in the body will be ignored.

We do not execute this method by default in order to prevent updating your working application. In the codeblock below we let you to select an application to edit.

```javascript
appIdToEdit = determineIdToUpdate( appResponse.body.application, appsResponse.body.applications, "application", "rename" )
```

```javascript
if(appIdToEdit){
  newAppName = "MyApp_" + getCurrentDate();
  updateAppResponse = client.applications.id(appIdToEdit).json.put({
    "application": {
      "name": newAppName,
      "settings": {
        "app_apdex_threshold": 1.0,
        "end_user_apdex_threshold": 1.0,
        "enable_real_user_monitoring": true
      }
    }
  }, { headers: headers })
}
```

```javascript
if(appIdToEdit){
  assert.equal(updateAppResponse.status,200)
}
```

Return a list of known metrics and their value names for the given resource

```javascript
metricsResponse = client.applications.application_id(myAppId).metrics.json.get({},{ headers: headers })
```

```javascript
assert.equal(metricsResponse.status,200)
```

This API endpoint returns a list of values for each of the requested metrics. The list of available metrics can be returned using the Metric Name API endpoint

```javascript
appMetricName = getSomeMetricName(metricsResponse.body)
metricsDataResponse = client.applications.application_id(myAppId).metrics.data.json.get({
  "names[]": appMetricName
},{ headers: headers })
```

```javascript
assert.equal(metricsDataResponse.status,200)
assert.equal(metricsDataResponse.body.metric_data.metrics[0].name,appMetricName)
```

This API endpoint returns a paginated list of hosts associated with the given application.
Application hosts can be filtered by hostname, or the list of application host IDs.

**Note:** you must have some server with the application running on it in order to test the method.

```javascript
hostsResponse = client.applications.application_id(myAppId).hosts.json.get({},{ headers: headers })
```

```javascript
assert.equal(hostsResponse.status,200)
hostId = hostsResponse.body.application_hosts[0].id
```

This API endpoint returns a single application host, identified by its ID.

```javascript
hostsResponse = client("/applications/{application_id}/hosts/{host_id}.json", { 
  application_id: myAppId,
  host_id: hostId
}).get({},{ headers: headers })
```

```javascript
assert.equal(hostsResponse.status,200)
```

Return a list of known metrics and their value names for the given resource

```javascript
hostMetricsResponse = client.applications.application_id(myAppId).hosts.host_id(hostId).metrics.json.get({},{ headers: headers })
```

```javascript
assert.equal(hostMetricsResponse.status,200)
```

This API endpoint returns a list of values for each of the requested metrics. The list of available metrics can be returned using the Metric Name API endpoint

```javascript
hostMetricName = getSomeMetricName(hostMetricsResponse.body)
hostMetricsDataResponse = client.applications.application_id(myAppId).hosts.host_id(hostId).metrics.data.json.get({
      "names[]": hostMetricName
},{ headers: headers })
```

```javascript
assert.equal(hostMetricsDataResponse.status,200)
assert.equal(hostMetricsDataResponse.body.metric_data.metrics[0].name,hostMetricName)
```

This API endpoint returns a paginated list of instances associated with the given application.
Application instances can be filtered by hostname, or the list of application instance IDs.

```javascript
instancesResponse = client.applications.application_id(myAppId).instances.json.get({},{ headers: headers })
```

```javascript
assert.equal(instancesResponse.status,200)
instanceId = instancesResponse.body.application_instances[0].id
```

This API endpoint returns a single application instance, identified by its ID.

```javascript
instanceResponse = client("/applications/{application_id}/instances/{instance_id}.json",
{
  application_id: myAppId,
  instance_id: instanceId
}).get({},{ headers: headers })
```

```javascript
assert.equal(instanceResponse.status,200)
```

Return a list of known metrics and their value names for the given resource

```javascript
instanceMetricsResponse = client.applications.application_id(myAppId).instances.instance_id(instanceId).metrics.json.get({},{ headers: headers })
```

```javascript
assert.equal(instanceMetricsResponse.status,200)
```

This API endpoint returns a list of values for each of the requested metrics. The list of available metrics can be returned using the Metric Name API endpoint

```javascript
instanceMetricName = getSomeMetricName(instanceMetricsResponse.body)
instanceMetricsDataResponse = client.applications.application_id(myAppId).instances.instance_id(instanceId).metrics.data.json.get({
    "names[]": instanceMetricName
  },{ headers: headers })
```

```javascript
assert.equal(instanceMetricsDataResponse.status,200)
assert.equal(instanceMetricsDataResponse.body.metric_data.metrics[0].name, instanceMetricName)
```

This API endpoint deletes an application and all of its reported data.
**WARNING**: Only applications that have stopped reporting can be deleted. This is an irreversible process which will delete all reported data for this application.

We do not execute this method by default in order to prevent deleting your working application.  In the codeblock below we let you to select an application to delete.

```javascript
appIdToDelete = determineIdToUpdate( appResponse.body.application, appsResponse.body.applications, "application", "delete" )
```

```javascript
if(appIdToDelete){
  applicationDeleteResponse = client.applications.id(appIdToDelete).json.delete({},{ headers: headers })
}
```

```javascript
if(appIdToDelete){
  assert.equal(applicationDeleteResponse.status,200)
}
```