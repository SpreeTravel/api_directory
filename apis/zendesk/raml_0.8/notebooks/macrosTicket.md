---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6929/preview
apiNotebookVersion: 1.1.66
title: Macros, ticket
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

Create macros

```javascript
macrosCreateResponse = client.macros.json.post({
  "macro":
  {
    "title": "Roger Wilco II",
    "actions": [
      {
        "field": "status",
        "value": "solved"
      }
    ]
  }
})
```

```javascript
assert.equal( macrosCreateResponse.status, 201 )
ID_MACRO = macrosCreateResponse.body.macro.id
```

Lists all shared and personal macros available to the current use

```javascript
macrosListResponse = client.macros.json.get()
```

```javascript
assert.equal( macrosListResponse.status, 200 )
```

Lists all shared and personal macros available to the current use

```javascript
singleMacrosResponse = client("/macros/{id}{mediaTypeExtension}", {
  "id": ID_MACRO,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleMacrosResponse.status, 200 )
```

Update macros

```javascript
macrosUpdateResponse = client("/macros/{id}{mediaTypeExtension}", {
  "id": ID_MACRO,
  "mediaTypeExtension": ".json"
}).put({"macro": {"title": "Roger Wilco III"}})
```

```javascript
assert.equal( macrosUpdateResponse.status, 200 )
```

Lists all active shared and personal macros available to the current use

```javascript
activeResponse = client.macros.active.json.get()
```

```javascript
assert.equal( activeResponse.status, 200 )
```

Personal macros available to the current use

```javascript
applyResponse = client.macros.id(ID_MACRO).apply.json.get()
```

```javascript
assert.equal( applyResponse.status, 200 )
```

Adding attachment for ticket.

```javascript
formData = new FormData()
formData.append("uploaded_data", new Blob([0,0,0,0,0,0,0,1]), "myApp3.txt")
uploadsAttachmentResponse = client.uploads.json.post(formData,{"query":{"filename":"myApp3.txt"}})
```

```javascript
ATTACHMENT_ID = uploadsAttachmentResponse.body.upload.attachment.id
ATTACHMENT_TOKEN = uploadsAttachmentResponse.body.upload.token
```

Creating Tickets

```javascript
ticketCreateResponse = client.tickets.json.post({
  "ticket":
  {
    "subject":"API Notebook Test Ticket 1",
    "comment": {
      "body": "Test ticket 1. Testing API notebook.",
      "uploads": [ ATTACHMENT_TOKEN ] 
    }
  }
})
```

```javascript
assert.equal(ticketCreateResponse.status, 201 )
ID_TICKET = ticketCreateResponse.body.ticket.id
```

Tickets are ordered chronologically by created date, from oldest to newest. Note: The first ticket listed may not be the absolute oldest ticket in your account due to ticket archiving. To get a list of all tickets in your account use the Incremental Ticket API
 Allowed For: [Admins]

```javascript
ticketsListResponse = client.tickets.json.get()
```

```javascript
assert.equal( ticketsListResponse.status, 200 )
```

Listing comment

```javascript
commentsListResponse = client("/tickets/{id}/comments{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( commentsListResponse.status, 200 )
ID_COMMENT = commentsListResponse.body.comments[0].id
```

Redaction allows you to permanently remove attachments from an existing comment on a ticket. Once removed from a comment, the attachment is replaced with a placeholder, "redacted.txt" file.
 Allowed For: [Admins]

```javascript
redactAttachmentUpdateResponse = client("/tickets/{id}/comments/{id_comment}/attachments/{id_attachment}/redact{mediaTypeExtension}", {
  "id": ID_TICKET,
  "id_comment": ID_COMMENT,
  "id_attachment": ATTACHMENT_ID,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( redactAttachmentUpdateResponse.status, 200 )
```

Redaction allows you to permanently remove words or strings from an existing comment on a ticket. Once removed from a comment, the word or string will be replaced by a ▇ symbol.

```javascript
redactCommentUpdateResponse = client("/tickets/{id}/comments/{id_comment}/redact{mediaTypeExtension}", {
  "id": ID_TICKET,
  "id_comment": ID_COMMENT,
  "mediaTypeExtension": ".json"
}).put({"text":"notebook"})
```

```javascript
assert.equal( redactCommentUpdateResponse.status, 200 )
```

Make comment private.

```javascript
makePrivateUpdateResponse = client("/tickets/{id}/comments/{id_comment}/make_private{mediaTypeExtension}", {
  "id": ID_TICKET,
  "id_comment": ID_COMMENT,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( makePrivateUpdateResponse.status, 200 )
```

Applies a macro to a specific ticket, or to all applicable tickets

```javascript
applyResponse = client("/tickets/{id}/macros/{id_macros}/apply{mediaTypeExtension}", {
  "id": ID_TICKET,
  "id_macros": ID_MACRO,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( applyResponse.status, 200 )
```

Delete ticket

```javascript
deleteTicketResponse = client.tickets.id(ID_TICKET).json.delete()
```

```javascript
assert.equal(deleteTicketResponse.status,200)
```

Create one more ticket

```javascript
ticket2CreateResponse = client.tickets.json.post({
  "ticket":
  {
    "subject":"API Notebook Test Ticket 1",
      "comment":  {
        "body": "Test ticket 1"
      }
   }
})
ID_TICKET = ticket2CreateResponse.body.ticket.id
```

Listing Audit

```javascript
auditsListResponse = client("/tickets/{id}/audits{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( auditsListResponse.status, 200 )
ID_AUDIT = auditsListResponse.body.audits[0].id
```

Change a comment from public to private

```javascript
makePrivateUpdateResponse = client("/tickets/{id}/audits/{id_audit}/make_private{mediaTypeExtension}", {
  "id": ID_TICKET,
  "id_audit": ID_AUDIT,
  "mediaTypeExtension": ".json"
}).put()
```

```javascript
assert.equal( makePrivateUpdateResponse.status, 200 )
```

Show Audit

```javascript
singleAuditResponse = client("/tickets/{id}/audits/{id_audit}{mediaTypeExtension}", {
  "id": ID_TICKET,
  "id_audit": ID_AUDIT,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( singleAuditResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsResponse = client("/tickets/{id}/tags{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( tagsResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
ticketTagCreateResponse = client("/tickets/{id}/tags{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).post({ "tags": ["important"] })
```

```javascript
assert.equal( ticketTagCreateResponse.status, 201 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsUpdateResponse = client("/tickets/{id}/tags{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).put({ "tags": ["customer"] })
```

```javascript
assert.equal( tagsUpdateResponse.status, 200 )
```

Lists the most popular recent tags in decreasing popularity

```javascript
tagsDeleteResponse = client("/tickets/{id}/tags{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( tagsDeleteResponse.status, 200 )
```

Create ticket for merging

```javascript
tempTicket = client.tickets.json.post({
  "ticket" : {
    "subject" : "My printer is on fire!" ,
    "comment" : {
      "body" : "The smoke is very colorful."
    }
  }
})
```

```javascript
ID_MERG_TICKET = tempTicket.body.ticket.id
```

Merge tickets.

```javascript
mergeCreateResponse = client("/tickets/{id}/merge{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).post({
  "ids" : [
    ID_TICKET ,
    ID_MERG_TICKET
  ] ,
  "source_comment" : "Closing in favour of #111" ,
  "target_comment" : "Combining tickets"
})
```

```javascript
assert.equal( mergeCreateResponse.status, 200 )
```

Retrieve related tickets.

```javascript
relatedResponse = client("/tickets/{id}/related{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get({
  "ticket" : {
    "subject" : "Hello" ,
    "comment" : {
      "body" : "Some question"
    } ,
    "custom_fields" : [
      {
        "id" : 34 ,
        "value" : "I need help!"
      }
    ]
  }
})
```

```javascript
assert.equal( relatedResponse.status, 200 )
```

List collaborators for ticket.

```javascript
collaboratorsResponse = client("/tickets/{id}/collaborators{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( collaboratorsResponse.status, 200 )
```

Listing ticket incidents.

```javascript
incidentsResponse = client("/tickets/{id}/incidents{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( incidentsResponse.status, 200 )
```

Getting Ticket Metric

```javascript
metricsResponse = client("/tickets/{id}/metrics{mediaTypeExtension}", {
  "id": ID_TICKET,
  "mediaTypeExtension": ".json"
}).get()
```

```javascript
assert.equal( metricsResponse.status, 200 )
```

Recent tickets. Tickets are ordered chronologically by created date, from oldest to newest. Note: The first ticket listed may not be the absolute oldest ticket in your account due to ticket archiving. To get a list of all tickets in your account use the Incremental Ticket AP

```javascript
recentResponse = client.tickets.recent.json.get()
```

```javascript
assert.equal( recentResponse.status, 200 )
```

Single ticket. Tickets are ordered chronologically by created date, from oldest to newest. Note: The first ticket listed may not be the absolute oldest ticket in your account due to ticket archiving. To get a list of all tickets in your account use the Incremental Ticket API
 Allowed For: [Agents]

```javascript
singleTicketResponse = client.tickets.id(ID_TICKET).json.get()
```

```javascript
assert.equal( singleTicketResponse.status, 200 )
```

Create temp ticket

```javascript
tempTicket = client.tickets.json.post({"ticket":{"subject":"My printer is on fire!", "comment": { "body": "The smoke is very colorful." }}})
```

```javascript
ID_TMP_TICKET = tempTicket.body.ticket.id
```

```javascript
singleTicketUpdateResponse = client.tickets.id(ID_TMP_TICKET).json.put({
  "ticket" : {
    "comment" : {
      "body" : "Thanks for choosing Acme Jet Motors." ,
      "public" : "true"
    } ,
    "status" : "solved"
  }
})
```

```javascript
assert.equal( singleTicketUpdateResponse.status, 200 )
```

Delete tickets.

```javascript
singleTicketDeleteResponse = client.tickets.id(ID_TICKET).json.delete()
```

```javascript
assert.equal(singleTicketDeleteResponse.status, 200 )
```

Create many tickets.

```javascript
showManyCreateResponse = client.tickets.show_many.json.post()
```

```javascript
assert.equal( showManyCreateResponse.status, 200 )
```

Update many tickets.

```javascript
updateManyUpdateResponse = client.tickets.update_many.json.put({
  "ticket" : {
    "status" : "solved"
  }
})
```

```javascript
assert.equal( updateManyUpdateResponse.status, 200 )
```

Mark many tickets as spam.

```javascript
markManyAsSpamUpdateResponse = client.tickets.mark_many_as_spam.json.put()
```

```javascript
assert.equal( markManyAsSpamUpdateResponse.status, 200 )
```

Delete many tickets.

```javascript
destroyManyDeleteResponse = client.tickets.destroy_many.json.delete({},{"query":{"ids":ID_TMP_TICKET + ", " + ID_MERG_TICKET}})
```

```javascript
assert.equal( destroyManyDeleteResponse.status, 200 )
```

Delete attachment.

```javascript
singeleAttachmentDeleteResponse = client.attachments.id(ATTACHMENT_ID).json.delete()
```

Delete macros

```javascript
macrosDeleteResponse = client("/macros/{id}{mediaTypeExtension}", {
  "id": ID_MACRO,
  "mediaTypeExtension": ".json"
}).delete()
```

```javascript
assert.equal( macrosDeleteResponse.status, 200 )
```