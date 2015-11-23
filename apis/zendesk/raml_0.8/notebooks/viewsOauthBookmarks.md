---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6940/preview
apiNotebookVersion: 1.1.66
title: Views, oauth, bookmarks
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

Create view

```javascript
viewsCreateResponse = client.views.json.post({
  "view":{
    "title":"Roger Wilco", 
    "all": [{ 
      "field": "status",
      "operator": "is", 
      "value": "open" 
      }, { 
      "field": "priority", 
      "operator": "less_than", 
      "value": "high" 
    }], 
    "any": [{ 
      "field": "current_tags", 
      "operator": "includes", 
      "value": "hello" 
    }]
  }
})
```

```javascript
assert.equal( viewsCreateResponse.status, 201 )
ID_VIEW = viewsCreateResponse.body.view.id
```

Lists shared and personal Views available to the current user

```javascript
viewsResponse = client.views.json.get()
```

```javascript
assert.equal( viewsResponse.status, 200 )
```

Lists active shared and personal Views available to the current user

```javascript
activeResponse = client.views.active.json.get()
```

```javascript
assert.equal( activeResponse.status, 200 )
```

A compacted list of shared and personal views available to the current user

```javascript
compactResponse = client.views.compact.json.get()
```

```javascript
assert.equal( compactResponse.status, 200 )
```

A compacted list of shared and personal views available to the current user

```javascript
singleViewResponse = client("/views/{id}{mediaTypeExtension}", {
  "id": ID_VIEW,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleViewResponse.status, 200 )
```

Update a compacted list of shared and personal views available to the current user
 Allowed For: [Agents]

```javascript
singleViewUpdateResponse = client("/views/{id}{mediaTypeExtension}", {
  "id": ID_VIEW,
  "mediaTypeExtension": ".json"
}).put({"view":{"title":"Loger Wilco II"}})
```

```javascript
assert.equal( singleViewUpdateResponse.status, 200 )
```

You execute a view in order to get the tickets that fulfil the conditions of the view

```javascript
executeResponse = client.views.id(ID_VIEW).execute.json.get()
```

```javascript
assert.equal( executeResponse.status, 200 )
```

You execute a view in order to get the tickets that fulfil the conditions of the view.
 Allowed For: [Agents]

```javascript
ticketsResponse = client.views.id(ID_VIEW).tickets.json.get()
```

```javascript
assert.equal( ticketsResponse.status, 200 )
```

Returns the ticket count for a single view

```javascript
countResponse = client.views.id(ID_VIEW).count.json.get()
```

```javascript
assert.equal( countResponse.status, 200 )
```

Returns the csv attachment of the current view if possible. Enqueues job to produce the csv if necessary

```javascript
exportResponse = client.views.id(ID_VIEW).export.json.get()
```

```javascript
assert.equal( exportResponse.status, 200 )
```

Calculates the size of the view in terms of number of tickets the view will return. Only returns values for personal and shared views accessible to the user performing the request

```javascript
countManyResponse = client.views.count_many.json.get()
```

```javascript
assert.equal( countManyResponse.status, 200 )
```

Views can be previewed by constructing the conditions in the proper format and nesting them under the 'view' key. The output can also be controlled by passing in any of the following parameters and nesting them under the 'output' key

```javascript
previewCreateResponse = client.views.preview.json.post({
  "view" : {
    "all" : [
      {
        "operator" : "is" ,
        "value" : "open" ,
        "field" : "status"
      }
    ] ,
    "output" : {
      "columns" : [
        "subject"
      ]
    }
  }
})
```

```javascript
assert.equal( previewCreateResponse.status, 200 )
```

Returns the ticket count for a single preview

```javascript
countResponse = client.views.preview.count.json.post({
  "view" : {
    "all" : [
      {
        "operator" : "is" ,
        "value" : "open" ,
        "field" : "status"
      }
    ]
  }
})
```

```javascript
assert.equal( countResponse.status, 200 )
```

Delete view

```javascript
singleViewDeleteResponse = client("/views/{id}{mediaTypeExtension}", {
  "id": ID_VIEW,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal(singleViewDeleteResponse.status, 200 )
```

Create Client

```javascript
clientsCreateResponse = client.oauth.clients.json.post({
  "client": 
  	{
      "name": "Test Client", "identifier": "api-notebook-test-client-" + new Date().getTime()
    }
})
```

```javascript
assert.equal( clientsCreateResponse.status, 201 )
ID_CLIENT = clientsCreateResponse.body.client.id

```

List Clients

```javascript
clientsResponse = client.oauth.clients.json.get()
```

```javascript
assert.equal( clientsResponse.status, 200 )
```

Show Client

```javascript
clientResponse = client.oauth.clients.id(ID_CLIENT).json.get()
```

```javascript
assert.equal( clientResponse.status, 200 )
```

Update Client

```javascript
clientUpdateResponse = client.oauth.clients.id(ID_CLIENT).json.put()
```

```javascript
assert.equal( clientUpdateResponse.status, 200 )
```

Delete Client

```javascript
clientDeleteResponse = client.oauth.clients.id(ID_CLIENT).json.delete()
```

```javascript
assert.equal( clientDeleteResponse.status, 200 )
```

List Tokens

```javascript
tokensResponse = client.oauth.tokens.json.get()
```

```javascript
assert.equal( tokensResponse.status, 200 )
ID_TOKEN = tokensResponse.body.tokens[0].id
```

Show single token

```javascript
tokenResponse = client.oauth.tokens.id(ID_TOKEN).json.get()
```

```javascript
assert.equal( tokenResponse.status, 200 )
```

Show token

```javascript
currentTokenResponse = client.oauth.tokens.current.json.get()
```

```javascript
assert.equal( currentTokenResponse.status, 200 )
```

Create temporary ticket

```javascript
tempTicket = client.tickets.json.post({
  "ticket":{
    	"subject":"Test bookmarks",
    	"comment": { 
      		"body": "Api notebook test" 
    }
  }
})
ID_TICKET = tempTicket.body.ticket.id
```

Create Bookmark

```javascript
bookmarksCreateResponse = client.bookmarks.json.post({
  "bookmark" : {
    "ticket_id" : ID_TICKET
  }
})
```

```javascript
assert.equal( bookmarksCreateResponse.status, 201 )
ID_BOOKMARK = bookmarksCreateResponse.body.bookmark.id
```

List Bookmarks

```javascript
bookmarksResponse = client.bookmarks.json.get()
```

```javascript
assert.equal( bookmarksResponse.status, 200 )
```

Delete bookmark

```javascript
bookmarkDeleteResponse = client.bookmarks.id(ID_BOOKMARK).json.delete()
```

```javascript
assert.equal( bookmarkDeleteResponse.status, 200 )
```

Delete temporary ticket

```javascript
client.tickets.id(ID_TICKET).json.delete()
```

Revoke Token

```javascript
tokenDeleteResponse = client.oauth.tokens.id(ID_TOKEN).json.delete()
```

```javascript
assert.equal( tokenDeleteResponse.status, 200 )
```