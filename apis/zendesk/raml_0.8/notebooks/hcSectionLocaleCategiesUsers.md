---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6944/preview
apiNotebookVersion: 1.1.66
title: Help center: section, locale
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

Create category

```javascript
categoriesCreateResponse = client.help_center.categories.json.post({
  "category" : {
    "position" : 2 ,
    "locale" : "en-us" ,
    "name" : "Super Hero Tricks" ,
    "description" : "This category contains a collection of super hero tricks"
  }
})
```

```javascript
assert.equal( categoriesCreateResponse.status, 201 )
ID_CATEGORY = categoriesCreateResponse.body.category.id
LOCALE = categoriesCreateResponse.body.category.locale
```

List Categories

```javascript
categoriesResponse = client.help_center.categories.json.get()
```

```javascript
assert.equal( categoriesResponse.status, 200 )
```

The access policy will be serialized as shown in the default access policy serializer

```javascript
sectionsResponse = client.help_center.sections.json.get()
```

```javascript
assert.equal( sectionsResponse.status, 200 )
```

Creates a section in a given category. You must specify a section name and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple translations for the section. The specified locales must be enabled for the current Help Center

```javascript
sectionsCreateResponse = client("/help_center/categories/{id}/sections{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).post({
  "section" : {
    "position" : 2 ,
    "locale" : "en-us" ,
    "name" : "Avionics" ,
    "description" : "This section contains articles on flight instruments"
  }
})
```

```javascript
assert.equal( sectionsCreateResponse.status, 201 )
ID_SECTION = sectionsCreateResponse.body.section.id
```

Show Access Policy

```javascript
accessPolicyResponse = client.help_center.sections.section_id(ID_SECTION).access_policy.json.get()
```

```javascript
assert.equal( accessPolicyResponse.status, 200 )
```

Update Access Policy

```javascript
accessPolicyUpdateResponse = client.help_center.sections.section_id(ID_SECTION).access_policy.json.put({ 
    "access_policy": { 
      "viewable_by": "staff", 
      "manageable_by": "staff", 
      "required_tags": ["vip"] 
    } 
  })
```

```javascript
assert.equal( accessPolicyUpdateResponse.status, 200 )
```

Lists the subscriptions to a given section

```javascript
subscriptionsResponse = client.help_center.sections.section_id(ID_SECTION).subscriptions.json.get()
```

```javascript
assert.equal( subscriptionsResponse.status, 200 )
```

Creates a subscription to a given section

```javascript
subscriptionsCreateResponse = client.help_center.sections.section_id(ID_SECTION).subscriptions.json.post({
  "subscription": {
    "source_locale": "en-us",
    "include_comments": true
  }
})
```

```javascript
assert.equal( subscriptionsCreateResponse.status, 201 )
ID_SUBSCRIPTION = subscriptionsCreateResponse.body.subscription.id
```

Lists the subscriptions to a given section

```javascript
singleSubscriptionResponse = client.help_center.sections.section_id(ID_SECTION).subscriptions.id(ID_SUBSCRIPTION).json.get()
```

```javascript
assert.equal( singleSubscriptionResponse.status, 200 )
```

Removes a subscription to a given section

```javascript
singleSubscriptionDeleteResponse = client.help_center.sections.section_id(ID_SECTION).subscriptions.id(ID_SUBSCRIPTION).json.delete()
```

```javascript
assert.equal( singleSubscriptionDeleteResponse.status, 204 )
```

Lists all translations for a given article, section, or category

```javascript
translationsResponse = client.help_center.sections.section_id(ID_SECTION).translations.json.get()
```

```javascript
assert.equal( translationsResponse.status, 200 )
```

Creates a translation for a given article, section, or category. Any locale that you specify must be enabled for the current Help Center. The locale must also be different from that of any existing translation associated with the source object

```javascript
translationsCreateResponse = client.help_center.sections.section_id(ID_SECTION).translations.json.post({
  "translation" : {
    "locale" : "fr" ,
    "title" : "Super Hero Tricks7" ,
    "body" : "This category contains a collection of Super Hero tricks7"
  }
})
```

```javascript
assert.equal( translationsCreateResponse.status, 201 )
ID_TRANSLATION = translationsCreateResponse.body.translation.id
```

Lists the locales that don't have a translation for a given article, section, or category

```javascript
missingResponse = client.help_center.sections.section_id(ID_SECTION).translations.missing.json.get()
```

```javascript
assert.equal( missingResponse.status, 200 )
```

When updating a translation, any locale that you specify must be enabled for the current Help Center. If you change the translation locale, it must be different from that of any existing translation associated with the same source object

```javascript
localeUpdateResponse = client.help_center.sections.section_id(ID_SECTION).translations.locale(LOCALE).json.put({
  "translation" : {
    "title" : "How to use HDR"
  }
})
```

```javascript
assert.equal( localeUpdateResponse.status, 200 )
```

These endpoints let you list all articles, all articles in a locale, all articles in a given category or section, or all the articles provided by a specific agent. You can also list all articles that have been updated since a specified start time

```javascript
articlesResponse = client("/help_center/sections/{id}/articles{mediaTypeExtension}", {
  "id": ID_SECTION,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( articlesResponse.status, 200 )
```

Creates an article in the specified section. You must specify an article title and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple translations for the article. The specified locales must be enabled for the current Help Center

```javascript
articlesCreateResponse = client("/help_center/sections/{id}/articles{mediaTypeExtension}", {
  "id": ID_SECTION,
  "mediaTypeExtension": ".json"
}).post({
  "article" : {
    "promoted" : false ,
    "position" : 2 ,
    "draft" : true,
    "comments_disabled" : true ,
    "label_names" : [
      "photo" ,
      "tripod"
    ] ,
    "locale" : "en-us" ,
    "title" : "How to take pictures in low light" ,
    "body" : "Use a tripod"
  }
})
```

```javascript
assert.equal( articlesCreateResponse.status, 201 )
ID_ARTICLE = articlesCreateResponse.body.article.id
```

Update Article source locale

```javascript
sourceLocaleUpdateResponse = client("/help_center/sections/{id}/source_locale{mediaTypeExtension}", {
  "id": ID_SECTION,
  "mediaTypeExtension": ".json"
}).put({"section_locale": "fr"})
```

```javascript
assert.equal( sourceLocaleUpdateResponse.status, 200 )
```

The access policy will be serialized as shown in the default access policy serializer

```javascript
singleSectionResponse = client.help_center.sections.id(ID_SECTION).json.get()
```

```javascript
assert.equal( singleSectionResponse.status, 200 )
```

These endpoints only update section-level metadata such as the sorting position. They don't update section translations. See Translations

```javascript
 singleSectionUpdateResponse = client.help_center.sections.id(ID_SECTION).json.put({"section": {"position": 42}})
```

```javascript
assert.equal(  singleSectionUpdateResponse.status, 200 )
```

Creates an article in the specified section. You must specify an article title and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple translations for the article. The specified locales must be enabled for the current Help Center

```javascript
articlesCreateResponse = client("/help_center/{locale}/sections/{id}/articles{mediaTypeExtension}", {
  "locale":LOCALE,
  "id": ID_SECTION,
  "mediaTypeExtension": ".json"
}).post({
  "article" : {
    "promoted" : true ,
    "position" : 42 ,
    "comments_disabled" : false ,
    "label_names" : [
      "photo" ,
      "tripod"
    ] ,
    "locale" : "en-us" ,
    "title" : "How to take pictures in low light" ,
    "body" : "Use a tripod"
  }
})
```

```javascript
assert.equal( articlesCreateResponse.status, 201 )
ID_LOCALE_ARTICLE = articlesCreateResponse.body.article.id
```

These endpoints let you list all articles, all articles in a locale, all articles in a given category or section, or all the articles provided by a specific agent. You can also list all articles that have been updated since a specified start time

```javascript
articlesResponse = client.help_center.locale(LOCALE).articles.json.get()
```

```javascript
assert.equal( articlesResponse.status, 200 )
```

Retrieve locales list

```javascript
localesResponse = client.help_center.locales.json.get()
```

```javascript
assert.equal( localesResponse.status, 200 )
```

Shows the properties of an article

```javascript
singleArticleResponse = client.help_center.locale(LOCALE).articles.id(ID_LOCALE_ARTICLE).json.get()
```

```javascript
assert.equal( singleArticleResponse.status, 200 )
```

These endpoints update article-level metadata such as its promotion status or sorting position. The endpoints do not update translation properties such as the article's title or body. See Translations

```javascript
singleArticleUpdateResponse = client.help_center.locale(LOCALE).articles.id(ID_LOCALE_ARTICLE).json.put({
  "article" : {
    "position" : 42
  }
})
```

```javascript
assert.equal( singleArticleUpdateResponse.status, 200 )
```

The access policy will be serialized as shown in the default access policy serializer

```javascript
sectionResponse = client.help_center.locale(LOCALE).sections.id(ID_SECTION).json.get()
```

```javascript
assert.equal( sectionResponse.status, 200 )
```

These endpoints only update section-level metadata such as the sorting position. They don't update section translations. See Translations

```javascript
sectionUpdateResponse = client.help_center.locale(LOCALE).sections.id(ID_SECTION).json.put({"section": {"position": 42}})
```

```javascript
assert.equal( sectionUpdateResponse.status, 200 )
```

List Categories

```javascript
categoriesResponse = client.help_center.locale(LOCALE).categories.json.get()
```

```javascript
assert.equal( categoriesResponse.status, 200 )
```

You must specify a category name and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple translations for the category. The specified locales must be enabled for the current Help Center

```javascript
categoriesCreateResponse = client.help_center.locale(LOCALE).categories.json.post({
  "category" : {
    "position" : 2 ,
    "locale" : "en-us" ,
    "name" : "Super Hero Tricks" ,
    "description" : "This category contains a collection of super hero tricks"
  }
})
```

```javascript
assert.equal( categoriesCreateResponse.status, 201 )
ID_LOCALE_CATGORY = categoriesCreateResponse.body.category.id
```

Show Category

```javascript
localeCategoryResponse = client("/help_center/{locale}/categories/{id}{mediaTypeExtension}", {
  "id": ID_LOCALE_CATGORY,
  "locale": LOCALE,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( localeCategoryResponse.status, 200 )
```

These endpoints only update category-level metadata such as the sorting position. They don't update category translations

```javascript
localeCategoryUpdateResponse = client("/help_center/{locale}/categories/{id}{mediaTypeExtension}", {
  "id": ID_LOCALE_CATGORY,
  "locale": LOCALE,
  "mediaTypeExtension": ".json"
}).put({"category": {"position": 2}})
```

```javascript
assert.equal(localeCategoryUpdateResponse.status, 200 )
```

Creates a section in a given category. You must specify a section name and locale. The locale can be omitted if it's specified in the URL. Optionally, you can specify multiple translations for the section. The specified locales must be enabled for the current Help Center

```javascript
sectionsCreateResponse = client.help_center.locale(LOCALE).categories.id(ID_LOCALE_CATGORY).sections.json.post({
  "section" : {
    "position" : 2 ,
    "locale" : "en-us" ,
    "name" : "Avionics" ,
    "description" : "This section contains articles on flight instruments"
  }
})
```

```javascript
assert.equal( sectionsCreateResponse.status, 201 )
```

Lists all sections or all sections in a given locale or category

```javascript
sectionsResponse = client.help_center.locale(LOCALE).sections.json.get()
```

```javascript
assert.equal( sectionsResponse.status, 200 )
```

These endpoints let you list all articles, all articles in a locale, all articles in a given category or section, or all the articles provided by a specific agent. You can also list all articles that have been updated since a specified start time

```javascript
articlesResponse = client("/help_center/categories/{id}/articles{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( articlesResponse.status, 200 )
```

The endpoint updates category source_locale propert

```javascript
sourceLocaleUpdateResponse = client("/help_center/categories/{id}/source_locale{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).put({"category_locale": "en-us"})
```

```javascript
assert.equal( sourceLocaleUpdateResponse.status, 200 )
```

Lists all sections or all sections in a given locale or category

```javascript
sectionsResponse = client("/help_center/categories/{id}/sections{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( sectionsResponse.status, 200 )
```

Show Category

```javascript
sigleCategoryResponse = client("/help_center/categories/{id}{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( sigleCategoryResponse.status, 200 )
```

These endpoints only update category-level metadata such as the sorting position. They don't update category translations

```javascript
sigleCategoryUpdateResponse = client("/help_center/categories/{id}{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).put({
  "category" : {
    "position" : 2
  }
})
```

```javascript
assert.equal( sigleCategoryUpdateResponse.status, 200 )
```

Lists all translations for a given article, section, or category

```javascript
translationsResponse = client.help_center.categories.category_id(ID_LOCALE_CATGORY).translations.json.get()
```

```javascript
assert.equal( translationsResponse.status, 200 )
```

Creates a translation for a given article, section, or category. Any locale that you specify must be enabled for the current Help Center. The locale must also be different from that of any existing translation associated with the source object

```javascript
translationsCreateResponse = client.help_center.categories.category_id(ID_LOCALE_CATGORY).translations.json.post({
  "translation" : {
    "locale" : "fr" ,
    "title" : "Super Tricks" ,
    "body" : "This category contains a collection of Super tricks"
  }
})
```

```javascript
assert.equal( translationsCreateResponse.status, 201 )
```

Lists the locales that don't have a translation for a given article, section, or category

```javascript
missingResponse = client.help_center.categories.category_id(ID_LOCALE_CATGORY).translations.missing.json.get()
```

```javascript
assert.equal( missingResponse.status, 200 )
```

When updating a translation, any locale that you specify must be enabled for the current Help Center. If you change the translation locale, it must be different from that of any existing translation associated with the same source object

```javascript
localeUpdateResponse = client.help_center.categories.category_id(ID_LOCALE_CATGORY).translations.locale(LOCALE).json.put({
  "translation" : {
    "title" : "How to use HDR"
  }
})
```

```javascript
assert.equal( localeUpdateResponse.status, 200 )
```

Delete user which could have been created during earlier notebook launches

```javascript
deleteExistingUser("Roger Wilco8", "roge8@example.org")
```

Create temporal user

```javascript
tempUser = client.users.json.post({
  "user" : {
    "name" : "Roger Wilco8" ,
    "email" : "roge8@example.org"
  }
})
```

```javascript
ID_USER = tempUser.body.user.id
```

These endpoints let you list all articles, all articles in a locale, all articles in a given category or section, or all the articles provided by a specific agent. You can also list all articles that have been updated since a specified start time

```javascript
articlesResponse = client.help_center.users.id(ID_USER).articles.json.get()
```

```javascript
assert.equal( articlesResponse.status, 200 )
```

Lists the comments created by a specific user, or all comments made by all users on a specific article

```javascript
commentsResponse = client.help_center.users.id(ID_USER).comments.json.get()
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Lists the subscriptions of a given user. To list your own subscriptions, specify me as the user id

```javascript
subscriptionsResponse = client.help_center.users.id(ID_USER).subscriptions.json.get()
```

```javascript
assert.equal( subscriptionsResponse.status, 200 )
```

Lists all votes cast by a given user, or all votes cast by all users for a given article

```javascript
votesResponse = client.help_center.users.id(ID_USER).votes.json.get()
```

```javascript
assert.equal( votesResponse.status, 200 )
```

These endpoints let you list all articles, all articles in a locale, all articles in a given category or section, or all the articles provided by a specific agent. You can also list all articles that have been updated since a specified start time

```javascript
articlesResponse = client.help_center.incremental.articles.json.get({start_time:1404345231})
```

```javascript
assert.equal( articlesResponse.status, 200 )
```

WARNING: All articles in the section will also be deleted

```javascript
 singleSectionDeleteResponse = client.help_center.sections.id(ID_SECTION).json.delete()
```

```javascript
assert.equal(  singleSectionDeleteResponse.status, 200 )
```

WARNING: Every section and all articles in the category will also be deleted

```javascript
categoryDeleteResponse = client("/help_center/categories/{id}{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( categoryDeleteResponse.status, 200 )
```

Delete temporal variables

```javascript
client.users.id(ID_USER).json.delete()
```

```javascript
 client("/help_center/categories/{id}{mediaTypeExtension}", {
  "id": ID_LOCALE_CATGORY,
  "mediaTypeExtension": ".json"
}).delete()
```