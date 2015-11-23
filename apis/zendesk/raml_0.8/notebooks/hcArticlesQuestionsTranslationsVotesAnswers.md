---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6945/preview
apiNotebookVersion: 1.1.66
title: Help center: articles, questions, translations
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

Create category
Allowed for:
Help Center managers

```javascript
categoriesCreateResponse = client.help_center.categories.json.post({
  "category" : {
    "position" : 2 ,
    "locale" : "en-us" ,
    "name" : "Super Hero Tricks2" ,
    "description" : "This category contains a collection of super hero tricks2"
  }
})
```

```javascript
ID_CATEGORY = categoriesCreateResponse.body.category.id
```

Create temporal section

```javascript
tempSectionCreateResponse = client("/help_center/categories/{id}/sections{mediaTypeExtension}", {
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
ID_SECTION = tempSectionCreateResponse.body.section.id
LOCALE = tempSectionCreateResponse.body.section.locale
```

Create article

```javascript
articlesCreateResponse = client("/help_center/sections/{id}/articles{mediaTypeExtension}", {
  "id": ID_SECTION,
  "mediaTypeExtension": ".json"
}).post({
  "article" : {
    "promoted" : false ,
    "position" : 2 ,
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

The search articles endpoint in the Articles API also takes labels into account. If you want to search for articles with specific labels, you can use the list articles endpoint and filter by label names

```javascript
articlesResponse = client.help_center.articles.json.get()
```

```javascript
assert.equal( articlesResponse.status, 200 )
```

Searches for articles or questions

```javascript
searchResponse = client.help_center.articles.search.json.get({"query":"print"})
```

```javascript
assert.equal( searchResponse.status, 200 )
```

Shows the properties of an article

```javascript
articlePropertiesResponse = client("/help_center/articles/{id}{mediaTypeExtension}", {
  "id": ID_ARTICLE,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( articlePropertiesResponse.status, 200 )
```

These endpoints update article-level metadata such as its promotion status or sorting position. The endpoints do not update translation properties such as the article's title or body. See Translations

```javascript
articleUpdateResponse = client("/help_center/articles/{id}{mediaTypeExtension}", {
  "id": ID_ARTICLE,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( articleUpdateResponse.status, 200 )
```


You can associate attachments in bulk to only one article at a time, with a maximum of 20 attachments per request

```javascript
bulkAttachmentsCreateResponse = client.help_center.articles.id(ID_ARTICLE).bulk_attachments.json.post({"attachment_ids": [10002]})
```

```javascript
assert.equal( bulkAttachmentsCreateResponse.status, 200 )
```

The endpoint updates article source_locale property

```javascript
sourceLocaleUpdateResponse = client.help_center.articles.id(ID_ARTICLE).source_locale.json.put({"article_locale": "en-us"})
```

```javascript
assert.equal( sourceLocaleUpdateResponse.status, 200 )
```

Creates an attachment for the specified article. You can specify whether the attachment is inline or not. The default is false

```javascript
formData = new FormData()
formData.append("file", new Blob([0,0,1,0,1,0,1,0,0,0,0,0,1,0,0,1]), "myApp.txt")
attachmentsCreateResponse = client.help_center.articles.id(ID_ARTICLE).attachments.json.post(formData)
```

```javascript
assert.equal( attachmentsCreateResponse.status, 201 )
```

Lists the comments created by a specific user, or all comments made by all users on a specific article

```javascript
commentsResponse = client.help_center.articles.id(ID_ARTICLE).comments.json.get()
```

```javascript
assert.equal( commentsResponse.status, 200 )
```

Adds a comment to the specified article. Because comments are associated with a specific article translation, or locale, you must specify a locale

```javascript
commentsCreateResponse = client.help_center.articles.id(ID_ARTICLE).comments.json.post({
  "comment" : {
    "body" : "Good info!" ,
    "locale" : "en-us"
  } ,
  "notify_subscribers" : "false"
})
```

```javascript
assert.equal( commentsCreateResponse.status, 201 )
ID_COMMENT = commentsCreateResponse.body.comment.id
```

Lists all the labels in a given article

```javascript
labelsResponse = client.help_center.articles.id(ID_ARTICLE).labels.json.get()
```

```javascript
assert.equal( labelsResponse.status, 200 )
```

Lists all the labels in a given article

```javascript
labelsCreateResponse = client.help_center.articles.id(ID_ARTICLE).labels.json.post({
  "label" : {
    "name" : "instructions"
  }
})
```

```javascript
assert.equal( labelsCreateResponse.status, 201 )
ID_LABEL = labelsCreateResponse.body.label.id
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
upCreateResponse = client.help_center.articles.id(ID_ARTICLE).up.json.post()
```

```javascript
assert.equal( upCreateResponse.status, 200 )
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
downCreateResponse = client.help_center.articles.id(ID_ARTICLE).down.json.post()
```

```javascript
assert.equal( downCreateResponse.status, 200 )
```

Lists all the article's attachments

```javascript
attachmentsResponse = client.help_center.articles.id(ID_ARTICLE).attachments.json.get()
```

```javascript
assert.equal( attachmentsResponse.status, 200 )
```

Lists all the article's inline attachments

```javascript
inlineResponse = client.help_center.articles.id(ID_ARTICLE).attachments.inline.json.get()
```

```javascript
assert.equal( inlineResponse.status, 200 )
```

Lists all the article's block attachments. Block attachments are those that are not inline

```javascript
blockResponse = client.help_center.articles.id(ID_ARTICLE).attachments.block.json.get()
```

```javascript
assert.equal( blockResponse.status, 200 )
```

Shows the properties of the specified comment

```javascript
articleCommentResponse = client.help_center.articles.id(ID_ARTICLE).comments.id(ID_COMMENT).json.get()
```

```javascript
assert.equal( articleCommentResponse.status, 200 )
```

Adds a comment to the specified article. Because comments are associated with a specific article translation, or locale, you must specify a locale

```javascript
articleCommentUpdateResponse = client.help_center.articles.id(ID_ARTICLE).comments.id(ID_COMMENT).json.put({
  "comment" : {
    "body" : "Didn't work"
  }
})
```

```javascript
assert.equal( articleCommentUpdateResponse.status, 200 )
```

Lists the subscriptions to a given article

```javascript
subscriptionsResponse = client.help_center.articles.id(ID_ARTICLE).subscriptions.json.get()
```

```javascript
assert.equal( subscriptionsResponse.status, 200 )
```

Creates a subscription to a given article

```javascript
subscriptionsCreateResponse = client.help_center.articles.id(ID_ARTICLE).subscriptions.json.post({
  "subscription" : {
    "source_locale" : "en-us"
  }
})
```

```javascript
assert.equal( subscriptionsCreateResponse.status, 201 )
ID_SUBSCRIPTION = subscriptionsCreateResponse.body.subscription.id
```

Lists the subscriptions to a given article

```javascript
articleSubscriptionResponse = client.help_center.articles.id(ID_ARTICLE).subscriptions.id(ID_SUBSCRIPTION).json.get()
```

```javascript
assert.equal( articleSubscriptionResponse.status, 200 )
```

Removes a subscription to a given article

```javascript
articleSubscriptionDeleteResponse = client.help_center.articles.id(ID_ARTICLE).subscriptions.id(ID_SUBSCRIPTION).json.delete()
```

```javascript
assert.equal( articleSubscriptionDeleteResponse.status, 204 )
```

Creates a translation for a given article, section, or category. Any locale that you specify must be enabled for the current Help Center. The locale must also be different from that of any existing translation associated with the source object

```javascript
translationsCreateResponse = client.help_center.articles.id(ID_ARTICLE).translations.json.post({
  "translation" : {
    "locale" : "ru" ,
    "title" : "Super Tricks" ,
    "body" : "This category contains a collection of Hero tricks"
  }
})
```

```javascript
assert.equal( translationsCreateResponse.status, 201 )
ID_ARTICLE_TRANSLATION = translationsCreateResponse.body.translation.id
```

Lists all translations for a given article, section, or category

```javascript
translationsResponse = client.help_center.articles.id(ID_ARTICLE).translations.json.get()
```

```javascript
assert.equal( translationsResponse.status, 200 )
```


Lists the locales that don't have a translation for a given article, section, or category

```javascript
missingResponse = client.help_center.articles.id(ID_ARTICLE).translations.missing.json.get()
```

```javascript
assert.equal( missingResponse.status, 200 )
```

Lists the locales that don't have a translation for a given article, section, or category

```javascript
localeResponse = client.help_center.articles.id(ID_ARTICLE).translations.locale(LOCALE).json.get()
```

```javascript
assert.equal( localeResponse.status, 200 )
```

When updating a translation, any locale that you specify must be enabled for the current Help Center. If you change the translation locale, it must be different from that of any existing translation associated with the same source object

```javascript
localeUpdateResponse = client.help_center.articles.id(ID_ARTICLE).translations.locale(LOCALE).json.put({
  "translation" : {
    "title" : "How to use HDR"
  }
})
```

```javascript
assert.equal( localeUpdateResponse.status, 200 )
```

Lists all votes cast by a given user, or all votes cast by all users for a given article

```javascript
votesResponse = client.help_center.articles.id(ID_ARTICLE).votes.json.get()
```

```javascript
assert.equal( votesResponse.status, 200 )
ID_VOTE = votesResponse.body.votes[0].id
```

You can use this endpoint for bulk imports. It lets you upload a file without associating it to an article until later. See Associate Attachments in Bulk to Article

```javascript
formData = new FormData()
//formData.append("comment[text]", "asdasd" )
formData.append("file", new Blob([0,0,0,0,0,0,0,1]), "myApp.png")
attachmentsCreateResponse = client.help_center.articles.attachments.json.post(formData)
```

```javascript
assert.equal( attachmentsCreateResponse.status, 201 )
ID_ATTACHMENT = attachmentsCreateResponse.body.article_attachment.id
```

Shows the properties of the specified attachment

```javascript
attachmentResponse = client.help_center.articles.attachments.id(ID_ATTACHMENT).json.get()
```

```javascript
assert.equal( attachmentResponse.status, 200 )
```

You can use this endpoint for bulk imports. It lets you upload a file without associating it to an article until later. See Associate Attachments in Bulk to Article

```javascript
attachmentDeleteResponse = client.help_center.articles.attachments.id(ID_ATTACHMENT).json.delete()
```

```javascript
assert.equal( attachmentDeleteResponse.status, 200 )
```

Lists all the labels in all the articles in Help Center

```javascript
labelsResponse = client.help_center.articles.labels.json.get()
```

```javascript
assert.equal( labelsResponse.status, 200 )
ID_LABEL = labelsResponse.body.labels[0].id
```

Shows the properties of the specified label

```javascript
lableResponse = client.help_center.articles.labels.id(ID_LABEL).json.get()
```

```javascript
assert.equal( lableResponse.status, 200 )
```

```javascript
questionCreateResponse = client("/help_center/questions.json").post({
  topic_id: 200184255,
  title: "API Notebook test question",
  details: "API Notebook test question",
})
```

```javascript
questionTitle = prompt("Please, enter title of your question, created in the help center.")
```

Select question to vote for

```javascript
question = null
questions = client("/help_center/questions.json").get().body.questions
for(var ind in questions){
  var q = questions[ind]
  if(q.title==questionTitle){
    question = q
    break
  }
}
questionId = question.id
```

Retrieve answer ID

```javascript
answers = client("/help_center/questions/{id}/answers.json",{id:questionId}).get().body.answers
```

```javascript
answerId = answers[0].id
```

Searches for articles or questions

```javascript
searchQuestionResponse = client.help_center.questions.search.json.get({"query": questionTitle })
```

```javascript
assert.equal( searchResponse.status, 200 )
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
upCreateResponse = client.help_center.questions.id(questionId).up.json.post()
```

```javascript
assert.equal( upCreateResponse.status, 200 )
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
downCreateResponse = client.help_center.questions.id(questionId).down.json.post()
```

```javascript
assert.equal( downCreateResponse.status, 200 )
```


Deletes a translation, provided it's not the only translation for the source object

```javascript
translationDeleteResponse = client.help_center.translations.id(ID_ARTICLE_TRANSLATION).json.delete()
```

```javascript
assert.equal( translationDeleteResponse.status, 200 )
```



Lists all votes cast by a given user, or all votes cast by all users for a given article

```javascript
voteResponse = client.help_center.votes.id(ID_VOTE).json.get()
```

```javascript
assert.equal( voteResponse.status, 200 )
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
voteDeleteResponse = client.help_center.votes.id(ID_VOTE).json.delete()
```

```javascript
assert.equal( voteDeleteResponse.status, 200 )
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
upCreateResponse = client.help_center.answers.id(answerId).up.json.post()
```

```javascript
assert.equal( upCreateResponse.status, 200 )
```

Creates an up or down vote for a given article, question, or answer. If a vote already exists for the source object, it's updated

```javascript
downCreateResponse = client.help_center.answers.id(answerId).down.json.post()
```

```javascript
assert.equal( downCreateResponse.status, 200 )
```

Delete article comment

```javascript
commentDeleteResponse = client.help_center.articles.id(ID_ARTICLE).comments.id(ID_COMMENT).json.delete()
```

```javascript
assert.equal( commentDeleteResponse.status, 200 )
```

Lists all the labels in a given article

```javascript
articleCommentDeleteResponse = client.help_center.articles.id(ID_ARTICLE).labels.id(ID_LABEL).json.delete()
```

```javascript
assert.equal( articleCommentDeleteResponse.status, 200 )
```

The endpoint updates article source_locale property

```javascript
articleDeleteResponse = client("/help_center/articles/{id}{mediaTypeExtension}", {
  "id": ID_ARTICLE,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( articleDeleteResponse.status, 204 )
```

Delete temporal variables

```javascript
client("/help_center/categories/{id}{mediaTypeExtension}", {
  "id": ID_CATEGORY,
  "mediaTypeExtension": ".json"
}).delete()
```