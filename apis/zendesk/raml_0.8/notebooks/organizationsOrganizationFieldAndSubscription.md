---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6936/preview
apiNotebookVersion: 1.1.66
title: Organizations, organization field and subscription
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

Delete organization that could have been created during earlier notebook launches.

```javascript
searchOrganizationsResponse = client.organizations.search.json.get({"external_id":"ramlTest"})
if(searchOrganizationsResponse.body.count>0){
  var orgId = searchOrganizationsResponse.body.organizations[0].id
  client.organizations.id(orgId).json.delete()
}
```

Creating Organization

```javascript
organizationsCreateResponse = client.organizations.json.post({
  "organization": {
    "name": "My Organization",
    "external_id": "ramlTest"
  }
})
```

```javascript
assert.equal( organizationsCreateResponse.status, 201 )
ID_ORGANIZATION = organizationsCreateResponse.body.organization.id
```

Listing Organizations

```javascript
organizationsListResponse = client.organizations.json.get()
```

```javascript
assert.equal( organizationsListResponse.status, 200 )
```

Returns an array of organizations whose name starts with the value specified in the name parameter. The name must be at least 2 characters in length.
 Allowed For: [Agents]

```javascript
autocompleteResponse = client.organizations.autocomplete.json.get()
```

```javascript
assert.equal( autocompleteResponse.status, 200 )
```

Returns an array of organizations whose name starts with the value specified in the name parameter. The name must be at least 2 characters in length

```javascript
relatedResponse = client("/organizations/{id}/related{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( relatedResponse.status, 200 )
```

Listing Requests

```javascript
requestsResponse = client("/organizations/{id}/requests{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( requestsResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsCreateResponse = client("/organizations/{id}/tags{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).post({ "tags": ["important"] })
```

```javascript
assert.equal( tagsCreateResponse.status, 201 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsResponse = client("/organizations/{id}/tags{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsUpdateResponse = client("/organizations/{id}/tags{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).put({ "tags": ["customer"] })
```

```javascript
assert.equal( tagsUpdateResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsDeleteResponse = client("/organizations/{id}/tags{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( tagsDeleteResponse.status, 200 )
```

List User

```javascript
usersListResponse = client("/organizations/{id}/users{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( usersListResponse.status, 200 )
```

Returns an array of organizations whose name starts with the value specified in the name parameter. The name must be at least 2 characters in length.
 Allowed For: [Admins, Agents]

```javascript
singleOrganizationResponse = client("/organizations/{id}{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleOrganizationResponse.status, 200 )
```

Returns an array of organizations whose name starts with the value specified in the name parameter. The name must be at least 2 characters in length.
 Allowed For: [Admins]

```javascript
organizationUpdateResponse = client.organizations.id(ID_ORGANIZATION).json.put({
  "organization" : {
    "notes" : "Something interesting"
  }
})
```

```javascript
assert.equal( organizationUpdateResponse.status, 200 )
```

The name must be at least 2 characters in length.
 allows for[Agents, restrictions apply on certain actions]

```javascript
manyCreateResponse = client.organizations.create_many.json.post({
  "organizations" : [
    {
      "name" : "Org1" ,
      "external_id" : "Org1"
    } , {
      "name" : "Org2" ,
      "external_id" : "Org2"
    }
  ]
})
```

```javascript
assert.equal( manyCreateResponse.status, 200 )
```

Specify a partial or full name or email address as the value of the query attribute. Example query="Gerry". Specify an id number as the value of the external_id attribute. For more advanced searches use the Search API

```javascript
searchResponse = client.organizations.search.json.get({external_id:"ramlTest"})
```

```javascript
assert.equal( searchResponse.status, 200 )
```

List Organization Subscription

```javascript
subscriptionsResponse = client("/organizations/{id}/subscriptions{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( subscriptionsResponse.status, 200 )
```

Tickets are ordered chronologically by created date, from oldest to newest. Note: The first ticket listed may not be the absolute oldest ticket in your account due to ticket archiving. To get a list of all tickets in your account use the Incremental Ticket AP

```javascript
ticketsResponse = client("/organizations/{id}/tickets{mediaTypeExtension}", {
  "id": ID_ORGANIZATION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( ticketsResponse.status, 200 )
```

Delete organization field if exists

```javascript
organizationFieldsResponse = client.organization_fields.json.get()
for(i=0;i<organizationFieldsResponse.body.organization_fields.length;i++){
  if(organizationFieldsResponse.body.organization_fields[i].title == "Support description"){
  	orgFieldDeleteResponse = client.organization_fields.id(organizationFieldsResponse.body.organization_fields[i].id).json.delete()
  }
}
```

Create Organization Field

```javascript
organizationFieldsCreateResponse = client.organization_fields.json.post({
  "organization_field":{
    "type":"text", "title":"Support description", 
       "description": "This field describes the support plan this organization has", 
      "position":0, "active":true, "key":"support_description"
     }
})
```

```javascript
assert.equal( organizationFieldsCreateResponse.status, 201 )
ID_ORGANIZATION_FIELD = organizationFieldsCreateResponse.body.organization_field.id
```

Returns a list of all custom Organization Fields in your account. Fields are returned in the order that you specify in your Organization Fields configuration in Zendesk. Clients should cache this resource for the duration of their API usage and map the key for each Organization Field to the values returned under the organization_fields attribute on the Organization resource.
 Allowed For: [Agents]

```javascript
organizationFieldsResponse = client.organization_fields.json.get()
```

```javascript
assert.equal( organizationFieldsResponse.status, 200 )
```

Returns a list of all custom Organization Fields in your account. Fields are returned in the order that you specify in your Organization Fields configuration in Zendesk. Clients should cache this resource for the duration of their API usage and map the key for each Organization Field to the values returned under the organization_fields attribute on the Organization resource.
 Allowed For: [Agents]

```javascript
singleOrganizationFieldResponse = client.organization_fields.id(ID_ORGANIZATION_FIELD).json.get()
```

```javascript
assert.equal( singleOrganizationFieldResponse.status, 200 )
```

Types of custom fields that can be created are:text (default when no "type" is specified) textarea checkbox date integer decimal regexp tagger (custom dropdown)
 Allowed For: [Admins]

```javascript
orgFieldUpdateResponse = client.organization_fields.id(ID_ORGANIZATION_FIELD).json.put({
  "organization_field" : {
    "title" : "Support description"
  }
})
```

```javascript
assert.equal( orgFieldUpdateResponse.status, 200 )
```

Types of custom fields that can be created are:text (default when no "type" is specified) textarea checkbox date integer decimal regexp tagger (custom dropdown)
 Allowed For: [Admins]

```javascript
orgFieldDeleteResponse = client.organization_fields.id(ID_ORGANIZATION_FIELD).json.delete()
```

```javascript
assert.equal( orgFieldDeleteResponse.status, 200 )
```

Delete a temporary user which could have been created during earlier notebook launches.

```javascript
deleteExistingUser("Toger Wilco","rogen@example.org")
```

Create temporary user

```javascript
tempUser = client.users.json.post({
  "user": {
    "name": "Toger Wilco",
    "role": "agent",
    "email": "rogen@example.org"
  }
})
ID_USER = tempUser.body.user.id
```

Create Organization Subscription

```javascript
organizationSubscriptionsCreateResponse = client.organization_subscriptions.json.post({
  "organization_subscription": {
    "user_id": ID_USER, 
    "organization_id": ID_ORGANIZATION
  }
})
```

```javascript
assert.equal( organizationSubscriptionsCreateResponse.status, 201 )
ID_ORGANIZATION_SUBSCRIPTION = organizationSubscriptionsCreateResponse.body.organization_subscription.id
```

List Organization Subscriptions

```javascript
organizationSubscriptionsResponse = client.organization_subscriptions.json.get()
```

```javascript
assert.equal( organizationSubscriptionsResponse.status, 200 )
```

Show Organization Subscription

```javascript
singleSubscriptionResponse = client.organization_subscriptions.id(ID_ORGANIZATION_SUBSCRIPTION).json.get()
```

```javascript
assert.equal( singleSubscriptionResponse.status, 200 )
```

Delete Organization Subscription

```javascript
subscriptionDeleteResponse = client.organization_subscriptions.id(ID_ORGANIZATION_SUBSCRIPTION).json.delete()
```

```javascript
assert.equal( subscriptionDeleteResponse.status, 200 )
```

Delete organization

```javascript
organizationDeleteResponse = client.organizations.id(ID_ORGANIZATION).json.delete()
```

```javascript
assert.equal( organizationDeleteResponse.status, 200 )
```

Garbage collection. Delete temporary user

```javascript
client.users.id(ID_USER).json.delete()
```

Garbage collection. Delete temporary organizations

```javascript
tempOrg1 = client.organizations.search.json.get({external_id:"Org1"})
ID_TEMP_ORG1 = tempOrg1.body.organizations[0].id
client.organizations.id(ID_TEMP_ORG1).json.delete()
```

```javascript
tempOrg2 = client.organizations.search.json.get({external_id:"Org2"})
ID_TEMP_ORG2 = tempOrg2.body.organizations[0].id
client.organizations.id(ID_TEMP_ORG2).json.delete()
```