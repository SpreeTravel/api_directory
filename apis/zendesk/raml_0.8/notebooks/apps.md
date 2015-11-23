---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6928/preview
apiNotebookVersion: 1.1.66
title: Apps
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

Delete application which could have been created during earlier notebook launches

```javascript
ownedApps = client.apps.owned.json.get().body.apps
for(var ind in ownedApps){
  var app = ownedApps[ind]
  if(app.name=="NotebookApp"){
    client.apps.id(app.id).json.delete()
  }
}
```

Base64 encoded ZIP archive with truncated sample application.
https://github.com/zendesk/demo_apps/tree/master/requirements_sample_app

```javascript
base64Content="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"
```

Name of archive with sample application.

```javascript
appArchiveName = "requirements_sample_app.zip"
```

Here we form payload for app creation POST request

```javascript
byteCharacters = atob(base64Content)
byteNumbers = new Array(byteCharacters.length);
for (var i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
}
byteArray = new Uint8Array(byteNumbers);
byteArray.length
formData = new FormData()
formData.append("uploaded_data", new Blob([byteArray]), appArchiveName)
```

Upload a new zip file package for an app. Use the returned upload id to create or update an app

```javascript
uploadsAppFileResponse = client.apps.uploads.json.post(formData,{"query":{"filename":appArchiveName}})
```

```javascript
assert.equal( uploadsAppFileResponse.status, 201 )
UPLOAD_APP_ID = uploadsAppFileResponse.body.id
```

Enqueues a build of a new app from a new upload, as specified by the upload_id parameter

```javascript
appBuildResponse = client.apps.json.post({"name":"NotebookApp", "short_description":"Uploaded by API Notebook", "upload_id":UPLOAD_APP_ID})
```

```javascript
assert.equal(appBuildResponse.status,202)
```

```javascript
window.alert("You have to wait while Zendesk is building the application.")
```

Queries the application build job status using a job id given from the job creation step

```javascript
jobStatusResponse = client.apps.job_statuses.id(appBuildResponse.body.job_id).json.get()
```

```javascript
assert.equal(jobStatusResponse.status,200)
```

Let's check if the build has completed. If it is not, you must wait for it to complete.

```javascript
count = 0
while(jobStatusResponse.body.status!="completed"){
  jobStatusResponse = client.apps.job_statuses.id(appBuildResponse.body.job_id).json.get()
  if(jobStatusResponse.body.status=="failed"){
    break
  }
  count++
  if(count==50){
    var waitMore = window.confirm("Wait more?")
    if(waitMore){
      count = 0;
    }
    else {
      break
    }
  }
}
UPLOAD_APP_ID = jobStatusResponse.body.app_id
```

Retrieves information about an App owned by the current account, where {id} is the id of the App

```javascript
appResponse = client.apps.id(UPLOAD_APP_ID).json.get()
```

```javascript
assert.equal( appResponse.status, 200)
```

Installs an App on the account. app_id is required, as is a settings hash containing keys for all required parameters for the app. Any values in settings that don't correspond to a parameter that the app declares will be silently ignored

```javascript
appInstallResponse = client.apps.installations.json.post({
  "app_id" : UPLOAD_APP_ID,
  "settings[name]" : "NotebookApp"
},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})
```

```javascript
assert.equal( appInstallResponse.status, 201 )
installationId = appInstallResponse.body.id
```

Lists Apps which are owned by the current account

```javascript
ownedResponse = client.apps.owned.json.get()
```

```javascript
assert.equal( ownedResponse.status, 200 )
```

Enqueues a build of an existing app from a new upload, as specified by the upload_id parameter

```javascript
singleAppUpdateResponse = client.apps.id(UPLOAD_APP_ID).json.put({"name":"NotebookApp", "short_description":"Uploaded by API Notebook"})
```

```javascript
assert.equal( singleAppUpdateResponse.status, 200 )
```

The notify endpoint allows you to send messages to currently-open instances of an app. For example, you could send a message to all logged-in agents telling them to take the day off

```javascript
notifyCreateResponse = client.apps.notify.json.post({"event": "app.activated", "app_id": UPLOAD_APP_ID,  "body": ""})
```

```javascript
assert.equal( notifyCreateResponse.status, 200 )
```

Lists all App installations on the account

```javascript
installationsResponse = client.apps.installations.json.get()
```

```javascript
assert.equal( installationsResponse.status, 200 )
```

Lists all Apps Requirements for an installation

```javascript
requirementsResponse = client.apps.installations.id(installationId).requirements.json.get()
```

```javascript
assert.equal( requirementsResponse.status, 200 )
```

Returns the list of locations available for use in Zendesk Apps
 Allowed For: [Admins]

```javascript
locationsResponse = client.apps.locations.json.get()
```

```javascript
assert.equal( locationsResponse.status, 200 )
ID_LOCATION = locationsResponse.body.locations[0].id
```

Returns the Location record
 Allowed For: [Admins]

```javascript
locationResponse = client.apps.locations.id(ID_LOCATION).json.get()
```

```javascript
assert.equal(locationResponse.status, 200 )
```

Returns the list of Location Installation records
 Allowed For: [Admins]

```javascript
locationInstallationResponse = client.apps.location_installations.json.get()
```

```javascript
assert.equal( locationInstallationResponse.status, 200 )
```

Returns the Location Installation record
 Allowed For: [Admins]

```javascript
installationResponse = client.apps.location_installations.id(UPLOAD_APP_ID).json.get()
```

```javascript
assert.equal( installationResponse.status, 200 )
```

Creates or updates the relevant Location Installation record with the installation order specified.
 Allowed For: [Admins]

```javascript
reorderInstalationCreateResponse = client.apps.location_installations.reorder.json.post({
  "installations": [installationId],
  "location_name": "nav_bar"
})
```

```javascript
assert.equal(reorderInstalationCreateResponse.status,201)
```

Retrieve information about an App installation, including the settings for that App installation

```javascript
installationResponse = client("/apps/installations/{id}{mediaTypeExtension}", {
  "id": installationId,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( installationResponse.status, 200 )
```

Updates an installation. app_id will be ignored; otherwise, this behaves much like creating installations

```javascript
installationUpdateResponse = client("/apps/installations/{id}{mediaTypeExtension}", {
  "id": installationId,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( installationUpdateResponse.status, 200 )
```

Removed an installed App. Use the installation id from the installation list response to make this request

```javascript
installationDeleteResponse = client("/apps/installations/{id}{mediaTypeExtension}", {
  "id": installationId,
  "mediaTypeExt": "mediaTypeExtValue",
  "mediaTypeExtension": ".json"
}).delete()
```

Deletes an App and removes it from Manage App

```javascript
singleAppDeleteResponse = client.apps.id(UPLOAD_APP_ID).json.delete()
```

```javascript
assert.equal( singleAppDeleteResponse.status, 200 )
```