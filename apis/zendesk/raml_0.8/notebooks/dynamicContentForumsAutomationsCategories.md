---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#79a774cb7a036590c6d1
apiNotebookVersion: 1.1.66
title: Dynamic content, forums, automations, categories
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

Returns a list of all dynamic content items for your account if accessed as an admin or agents who have permission to manage dynamic content.
 Allowed For: [Admins, Agents]

```javascript
dynamicContentItemsResponse = client.dynamic_content.items.json.get()
```

```javascript
assert.equal( dynamicContentItemsResponse.status, 200 )
```

Delete dynamic items which could have been created during earlier notebook launches

```javascript
dynamicItems = dynamicContentItemsResponse.body.items
for(var ind in dynamicItems){
  var di = dynamicItems[ind]
  if(di.name == "Snowboard Problem"){
    dynamicContentItemsListResponse = client("/dynamic_content/items/{id}{mediaTypeExtension}", {
      "id" : di.id,
      "mediaTypeExtension" : ".json"
    }).delete()
  }
}
```

Create new dynamic content.
When creating a new dynamic content item, you'll be creating the first variant for that item simultaneously.
 Allowed For: [Admins, Agents]

```javascript
dynamicContentItemsCreateResponse = client.dynamic_content.items.json.post({
	"item": {
		"name": "Snowboard Problem",
		"default_locale_id": 1, 
		"content": "Snowboard Problem variant"
	}
})
```

```javascript
assert.equal( dynamicContentItemsCreateResponse.status, 201 )
ID_DYNAMIC_ITEM = dynamicContentItemsCreateResponse.body.item.id
```

Retrive selected content
 Allowed For: [Admins, Agents]

```javascript
dynamicContentItemsListResponse = client("/dynamic_content/items/{id}{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( dynamicContentItemsListResponse.status, 200 )
```

Update content
 Allowed For: [Admins, Agents]

```javascript
dynamicContentItemUpdateResponse = client("/dynamic_content/items/{id}{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "mediaTypeExtension": ".json"
}).put({
  "item" : {
    "name" : "New name"
  }
})
```

```javascript
assert.equal( dynamicContentItemUpdateResponse.status, 200 )
```

Returns a list of all the variants for a dynamic content item for your account if accessed as an admin or agents who have permission to manage dynamic content.
 Allowed For: [Admins, Agents]

```javascript
variantsListResponse = client("/dynamic_content/items/{id}/variants{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( variantsListResponse.status, 200 )
```

Create variant
 Allowed For: [Admins, Agents, Admins, Agents]

```javascript
variantCreateResponse = client("/dynamic_content/items/{id}/variants{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "mediaTypeExtension": ".json"
}).post({
  "variant" : {
    "locale_id" : 127 ,
    "active" : true ,
    "default" : false ,
    "content" : "C'est mon contenu dynamique en français"
  }
})
```

```javascript
assert.equal( variantCreateResponse.status, 201 )
ID_VARIANT = variantCreateResponse.body.variant.id
```

Show variant
 Allowed For: [Admins, Agents]

```javascript
singleVariantResponse = client("/dynamic_content/items/{id}/variants/{id_variant}{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "id_variant": ID_VARIANT,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleVariantResponse.status, 200 )
```

Update variant
 Allowed For: [Admins, Agents]

```javascript
singleVariantUpdateResponse = client("/dynamic_content/items/{id}/variants/{id_variant}{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "id_variant": ID_VARIANT,
  "mediaTypeExtension": ".json"
}).put({
  "variant" : {
    "active" : false ,
    "default" : false ,
    "content" : "C'est mon contenu dynamique en français"
  }
})
```

```javascript
assert.equal (singleVariantUpdateResponse.status, 200)
```

Update Many Variants
 Allowed For: [Admins, Agents]

```javascript
updateManyUpdateResponse = client("/dynamic_content/items/{id}/variants/update_many{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "mediaTypeExtension": ".json"
}).put({
  "variants" : [
    {
      "id" : 23 ,
      "active" : false ,
      "default" : false ,
      "content" : "C'est mon contenu dynamique en français"
    } , {
      "id" : 24 ,
      "active" : false ,
      "default" : false ,
      "content" : "Este es mi contenido dinámico en español"
    }
  ]
})
```

```javascript
assert.equal( updateManyUpdateResponse.status, 200 )
```

Delete selected content
 Allowed For: [Admins, Agents]

```javascript
dynamicContentItemDeleteResponse = client("/dynamic_content/items/{id}{mediaTypeExtension}", {
  "id": ID_DYNAMIC_ITEM,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( dynamicContentItemDeleteResponse.status, 200 )
```

Delete temporal user if it was created by previous notebook tests.

```javascript
searhTempUser =  client.users.search.json.get({"query":"poge@example.org"})
```

```javascript
if (searhTempUser.body.users.length>0)
  client.users.id(searhTempUser.body.users[0].id).json.delete()
```

Create new category

```javascript
categoryCreateResponse = client.categories.json.post({"category": {"name": "My Category3"}})
```

```javascript
assert.equal( categoryCreateResponse.status, 201 )
ID_MY_CATEGORY = categoryCreateResponse.body.category.id
```

Update category

```javascript
categoryUpdateResponse = client("/categories/{id}{mediaTypeExtension}", {
  "id": ID_MY_CATEGORY,
  "mediaTypeExtension": ".json"
}).put({"category": {"name": "The Category About Nothin"}})
```

```javascript
assert.equal( categoryUpdateResponse.status, 200 )
```

Create new forum

```javascript
newForumCreateResponse = client.forums.json.post({
  "forum": {
    "name": "My Forum",
    "forum_type": "articles",
    "access": "logged-in users",
	  "category_id": ID_MY_CATEGORY
  }
})
```

```javascript
assert.equal( newForumCreateResponse.status, 201 )
ID_FORUM = newForumCreateResponse.body.forum.id
```

List Forum

```javascript
forumsListResponse = client.forums.json.get()
```

```javascript
assert.equal( forumsListResponse.status, 200 )
```


Show Forum

```javascript
singleForumResponse = client.forums.id(ID_FORUM).json.get()
```

```javascript
assert.equal( singleForumResponse.status, 200 )
```

Update Forum

```javascript
forumUpdateResponse = client.forums.id(ID_FORUM).json.put()
```

```javascript
assert.equal( forumUpdateResponse.status, 200 )
```

List Forum Subscription

```javascript
forumSubscriptionsResponse = client.forums.forum_id(ID_FORUM).subscriptions.json.get()
```

```javascript
assert.equal( forumSubscriptionsResponse.status, 200 )
```

List Topic

```javascript
forumTopicsResponse = client.forums.forum_id(ID_FORUM).topics.json.get()
```

```javascript
assert.equal( forumTopicsResponse.status, 200 )
```

Returns an array of registered and recent tag names that start with the specified name. The name must be at least 2 characters in length.
 Allowed For: [Agents]

```javascript
autocompleteTagsResponse = client.autocomplete.tags.json.get()
```

```javascript
assert.equal( autocompleteTagsResponse.status, 200 )
```

Lists all automations for the current account

```javascript
automationsListResponse = client.automations.json.get()
```

```javascript
assert.equal( automationsListResponse.status, 200 )
```

Create automation

```javascript
automationCreateResponse = client.automations.json.post({
"automation":
  {
  	"title":"Roger Wilco",
   	"all": [
       				{ "field": "status", "operator": "is", "value": "open" },
              { "field": "priority", "operator": "less_than", "value": "high" }
    				],
   "actions": [{ "field": "priority", "value": "high" }]
   }
})
```

```javascript
assert.equal( automationCreateResponse.status, 201 )
ID_AUTOMATION = automationCreateResponse.body.automation.id
```

Single automation

```javascript
singleAutomationResponse = client.automations.id(ID_AUTOMATION).json.get()
```

```javascript
assert.equal( singleAutomationResponse.status, 200 )
```

Update automation
 Allowed For: [Agents]

```javascript
updateAutomationResponse = client.automations.id(ID_AUTOMATION).json.put()
```

```javascript
assert.equal( updateAutomationResponse.status, 200 )
```

Lists all active automation

```javascript
activeAutomationsResponse = client.automations.active.json.get()
```

```javascript
assert.equal( activeAutomationsResponse.status, 200 )
```

Delete automation

```javascript
automationDeleteResponse = client.automations.id(ID_AUTOMATION).json.delete()
```

```javascript
assert.equal( automationDeleteResponse.status, 200 )
```

List Categories

```javascript
categoriesListResponse = client.categories.json.get()
```

```javascript
assert.equal( categoriesListResponse.status, 200 )
```

Show Category

```javascript
singleCategoryResponse = client("/categories/{id}{mediaTypeExtension}", {
  "id": ID_MY_CATEGORY,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleCategoryResponse.status, 200 )
```

Category forums list

```javascript
categoryForumsResponse = client.categories.id(ID_MY_CATEGORY).forums.json.get()
```

```javascript
assert.equal( categoryForumsResponse.status, 200 )
```

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser("Roger Wilco","roge1@example.org")
```

Create user

```javascript
tempUser = client.users.json.post(
{
	"user": {
		"name": "Roger Wilco",
		 "email": "roge1@example.org"
	}
})
```

```javascript
ID_USER = tempUser.body.user.id
```

Create Forum Subscription

```javascript
forumSubscriptionsCreateResponse = client.forum_subscriptions.json.post({
  "forum_subscription": 
    {
      "user_id": ID_USER, 
      "forum_id": ID_FORUM
    }
})
```

```javascript
assert.equal( forumSubscriptionsCreateResponse.status, 201 )
ID_SUBSCRIPTION = forumSubscriptionsCreateResponse.body.forum_subscription.id
```

List Forum Subscription

```javascript
forumSubscriptionsListResponse = client.forum_subscriptions.json.get()
```

```javascript
assert.equal( forumSubscriptionsListResponse.status, 200 )
```

Show Forum Subscription

```javascript
singleForumSubscriptionResponse = client.forum_subscriptions.id(ID_SUBSCRIPTION).json.get()
```

```javascript
assert.equal( singleForumSubscriptionResponse.status, 200 )
```

Delete Forum Subscription

```javascript
forumSubscriptionDeleteResponse = client.forum_subscriptions.id(ID_SUBSCRIPTION).json.delete()
```

```javascript
assert.equal( forumSubscriptionDeleteResponse.status, 200 )
```

Delete Forum

```javascript
forumDeleteResponse = client.forums.id(ID_FORUM).json.delete()
```

```javascript
assert.equal( forumDeleteResponse.status, 200 )
```

Delete Category

```javascript
categoryDeleteResponse = client("/categories/{id}{mediaTypeExtension}", {
  "id": ID_MY_CATEGORY,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( categoryDeleteResponse.status, 200 )
```

Delete temporal user and category

```javascript
client("users/{id}{mediaTypeExtension}", {
  "id": ID_USER,
  "mediaTypeExtension": ".json"
}).delete()
```