---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6933/preview
apiNotebookVersion: 1.1.66
title: Account, uploads, attachments, locales
---

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

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

Auxiliary method for deleting existing users

```javascript
function deleteExistingUser(username,email){
  searhResponse1 = client.users.search.json.get({"query":email})
  if (searhResponse1.body.users.length>0){
    client.users.id(searhResponse1.body.users[0].id).json.delete()
  }
  searhResponse2 = client.users.search.json.get({"query":username})
  if (searhResponse2.body.users.length>0){
    client.users.id(searhResponse2.body.users[0].id).json.delete()
  }
}
```

This shows the settings that are available for the account

```javascript
accountSettingsResponse = client.account.settings.json.get()
```

```javascript
assert.equal( accountSettingsResponse.status, 200 )
```

Only two settings can be updated:"lotus": { "prefer_lotus": boolean }, where boolean is true or false "ticket_form": { "ticket_forms_instructions": "your_instructions" }, where "your_instructions" is the string displayed when end-users select a for

```javascript
settingsUpdateResponse = client.account.settings.json.put({
  "settings" : {
    "active_features" : {
      "customer_satisfaction" : true
    } ,
    "lotus" : {
      "prefer_lotus" : true
    }
  }
})
```

```javascript
assert.equal( settingsUpdateResponse.status, 200 )
```

Create attachment.

```javascript
formData = new FormData()
formData.append("uploaded_data", new Blob([00000001]), "myApp.zip")
uploadsFileCreateResponse = client.uploads.json.post(formData)
```

```javascript
assert.equal( uploadsFileCreateResponse.status, 201 )
TOKEN_UPLOADED_FILE = uploadsFileCreateResponse.body.upload.token
```

Delete attachment.

```javascript
uploadedFileDeleteResponse = client.uploads.token(TOKEN_UPLOADED_FILE).json.delete()
```

```javascript
assert.equal( uploadedFileDeleteResponse.status, 200 )
```

Adding multiple attachments to the same upload is handled by splitting requests and passing the API token received from the first request to each subsequent request.
 Allowed For: [Agents]

```javascript
formData = new FormData()
formData.append("uploaded_data", new Blob([00000001]), "myApp2.zip")
uploadsAttachmentResponse = client.uploads.json.post(formData)
```

```javascript
ID_ATTACHMENT = uploadsAttachmentResponse.body.upload.attachment.id
```

Retrieve single attachment

```javascript
singeleAttachmentResponse = client.attachments.id(ID_ATTACHMENT).json.get()
```

```javascript
assert.equal( singeleAttachmentResponse.status, 200 )
```

Delete attachment. Currently, only attachments on forum posts are allowed to be deleted.
 Allowed For: [Agents]

```javascript
singeleAttachmentDeleteResponse = client.attachments.id(ID_ATTACHMENT).json.delete()
```

```javascript
assert.equal( singeleAttachmentDeleteResponse.status, 200 )
```

This lists the translation locales that are available for the account

```javascript
localesResponse = client.locales.json.get()
```

```javascript
assert.equal( localesResponse.status, 200 )
```

This lists the translation public locales that are available to all accounts

```javascript
publicLocalesResponse = client.locales.public.json.get()
```

```javascript
assert.equal( publicLocalesResponse.status, 200 )
ID_LOCALE = publicLocalesResponse.body.locales[0].id
```

This lists the translation locales that have been localized for agents

```javascript
agentLocalesResponse = client.locales.agent.json.get()
```

```javascript
assert.equal( agentLocalesResponse.status, 200 )
```

This lists the translation locales that have been localized for agents.
 Allowed For: [Anyone]

```javascript
singleLocaleResponse = client.locales.id(ID_LOCALE).json.get()
```

```javascript
assert.equal( singleLocaleResponse.status, 200 )
```

This works exactly like show, but instead of taking an id as argument, it renders the locale of the user performing the request

```javascript
currentLocaleResponse = client.locales.current.json.get()
```

```javascript
assert.equal( currentLocaleResponse.status, 200 )
```

This works exactly like show, but instead of taking an id as argument, it renders the locale of the user performing the request.
 Allowed For: [Anyone]

```javascript
detectBestLocaleResponse = client.locales.detect_best_locale.json.get()
```

```javascript
assert.equal( detectBestLocaleResponse.status, 200 )
```

Delete existing user field

```javascript
userFieldsListResponse = client.user_fields.json.get()
  for ( i = 0; i < userFieldsListResponse.body.user_fields.length; i++){
    if (userFieldsListResponse.body.user_fields[i].title == "Support description"){
    	client.user_fields.id(userFieldsListResponse.body.user_fields[i].id).json.delete()
    }
  }
```

Create User Field

```javascript
userFieldsCreateResponse = client.user_fields.json.post({
  "user_field": {
    "type":"text", 
    "title":"Support description", 
    "description": "This field describes the support plan this user has",
    "position":0, 
    "active":true, 
    "key":"support_descriptions"
  }
})
```

```javascript
assert.equal( userFieldsCreateResponse.status, 201 )
ID_USER_FIELD = userFieldsCreateResponse.body.user_field.id
```

Returns a list of all custom User Fields in your account. Fields are returned in the order that you specify in your User Fields configuration in Zendesk. Clients should cache this resource for the duration of their API usage and map the key for each User Field to the values returned under the user_fields attribute on the User resource.
 Allowed For: [Agents]

```javascript
userFieldsResponse = client.user_fields.json.get()
```

```javascript
assert.equal( userFieldsResponse.status, 200 )
```

Types of custom fields that can be created are:text (default when no "type" is specified) textarea checkbox date integer decimal regexp tagger (custom dropdown)
 Allowed For: [Admins]

```javascript
reorderUserFieldResponse = client.user_fields.reorder.json.put({ "user_field_ids": [3, 4] })
```

```javascript
assert.equal( reorderUserFieldResponse.status, 200 )
```

Returns a list of all custom User Fields in your account. Fields are returned in the order that you specify in your User Fields configuration in Zendesk. Clients should cache this resource for the duration of their API usage and map the key for each User Field to the values returned under the user_fields attribute on the User resource.
 Allowed For: [Agents]

```javascript
singleUserFieldResponse = client.user_fields.id(ID_USER_FIELD).json.get()
```

```javascript
assert.equal( singleUserFieldResponse.status, 200 )
```

Types of custom fields that can be created are:text (default when no "type" is specified) textarea checkbox date integer decimal regexp tagger (custom dropdown)
 Allowed For: [Admins]

```javascript
userFieldUpdateResponse = client.user_fields.id(ID_USER_FIELD).json.put({
  "user_field" : {
    "title" : "Support description"
  }
})
```

```javascript
assert.equal( userFieldUpdateResponse.status, 200 )
```

Types of custom fields that can be created are:text (default when no "type" is specified) textarea checkbox date integer decimal regexp tagger (custom dropdown)
 Allowed For: [Admins]

```javascript
userFieldDeleteResponse = client.user_fields.id(ID_USER_FIELD).json.delete()
```

```javascript
assert.equal( userFieldDeleteResponse.status, 200 )
```

Create Group

```javascript
groupCreateResponse = client.groups.json.post({
  "group": 
    {
      "name": "My new group"
    }
})
```

```javascript
assert.equal( groupCreateResponse.status, 201 )
ID_GROUP = groupCreateResponse.body.group.id
```

List Group

```javascript
groupsListeResponse = client.groups.json.get()
```

```javascript
assert.equal( groupsListeResponse.status, 200 )
```

Show assignable groups

```javascript
assignableGroupResponse = client.groups.assignable.json.get()
```

```javascript
assert.equal( assignableGroupResponse.status, 200 )
```

Show Group

```javascript
singleGroupResponse = client.groups.id(ID_GROUP).json.get()
```

```javascript
assert.equal( singleGroupResponse.status, 200 )
```

Update Group

```javascript
groupUpdateResponse = client.groups.id(ID_GROUP).json.put({
  "group" : {
    "name" : "My interesting Group"
  }
})
```

```javascript
assert.equal( groupUpdateResponse.status, 200 )
```

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser("Roger Philco", "rogeAgent@example.org")
```



Create temp agent user

```javascript
tempAgent = client.users.json.post({
  "user": {
    "name": "Roger Philco",
    "role": "agent",
    "email": "rogeAgent@example.org",
    "verified": true
  }
})
ID_USER_AGENT = tempAgent.body.user.id
```

Create Membership

```javascript
groupMembershipsCreateResponse = client.users.user_id(ID_USER_AGENT).group_memberships.json.post({
  "group_membership": 
    {
      "user_id": ID_USER_AGENT, 
      "group_id": ID_GROUP
    }  
})
```

```javascript
assert.equal( groupMembershipsCreateResponse.status, 201 )
ID_MEMBERSHIP = groupMembershipsCreateResponse.body.group_membership.id
```

List Memberships

```javascript
membershipsListResponse = client.groups.group_id(ID_GROUP).memberships.json.get()
```

```javascript
assert.equal( membershipsListResponse.status, 200 )
```

List Assignable Memberships

```javascript
assignableListResponse = client.groups.group_id(ID_GROUP).memberships.assignable.json.get()
```

```javascript
assert.equal( assignableListResponse.status, 200 )
```

List Users of the group

```javascript
groupUsersResponse = client.groups.group_id(ID_GROUP).users.json.get()
```

```javascript
assert.equal( groupUsersResponse.status, 200 )
```

List Memberships

```javascript
groupMembershipsListResponse = client.group_memberships.json.get()
```

```javascript
assert.equal( groupMembershipsListResponse.status, 200 )
```


List Assignable Memberships

```javascript
assignableMembershipListResponse = client.group_memberships.assignable.json.get()
```

```javascript
assert.equal( assignableMembershipListResponse.status, 200 )
```

Show Membership

```javascript
membershipDeleteResponse = client.group_memberships.id(ID_MEMBERSHIP).json.get()
```

```javascript
assert.equal( membershipDeleteResponse.status, 200 )
```

Delete membership

```javascript
membershipDeleteResponse = client.group_memberships.id(ID_MEMBERSHIP).json.delete()
```

```javascript
assert.equal( membershipDeleteResponse.status, 200 )
```

Delete Group

```javascript
groupDeleteResponse = client.groups.id(ID_GROUP).json.delete()
```

```javascript
assert.equal( groupDeleteResponse.status, 200 )
```

Garbage collection. Delete temporary user.

```javascript
client.users.id(ID_USER_AGENT).json.delete()
```