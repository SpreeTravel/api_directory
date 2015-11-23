---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8136/versions/8333/portal/pages/7061/preview
apiNotebookVersion: 1.1.66
title: Sheet/version, sheet/emails, sheet/columns
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
// Read about the Smartsheet API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8136/versions/8333/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8136/versions/8333/definition');
```

```javascript
TOKEN = prompt("Please input Access Token")
```

```javascript
function customAuthHack(o){
o.setRequestHeader("Authorization", "Bearer " + TOKEN);
}
```

```javascript
API.set(client, 'beforeSend', new function(){return customAuthHack})
```

Lists all the sheets in alphabetical order, by name
 Allowed For: [READ_SHEETS]

```javascript
sheetsResponse = client.sheets.get()
```

```javascript
assert.equal( sheetsResponse.status, 200 )
```

Delete notebook test sheet if exist

```javascript
for (i = 0; i < sheetsResponse.body.length; i++){
  if (sheetsResponse.body[i].name == "notebookTestSheet"){
  	client.sheet.sheetId(sheetsResponse.body[i].id).delete()
  }
}
```

Creates a sheet from scratch. Sheets can be created in your default "Sheets" collection, into a workspace or into any folder whether that folder is in a shared workspace or your personal "Sheets" collection. See "Method" below for the URL to use for each of these operations.
 Allowed For: [CREATE_SHEETS]

```javascript
sheetsCreateResponse = client.sheets.post({
  "name" : "notebookTestSheet" ,
  "columns" : [
    {
      "title" : "Favorite" ,
      "type" : "CHECKBOX" ,
      "symbol" : "STAR"
    } , {
      "title" : "Primary Column" ,
      "primary" : true ,
      "type" : "TEXT_NUMBER"
    } , {
      "title" : "Status" ,
      "type" : "PICKLIST" ,
      "options" : [
        "Not Started" ,
        "Started" ,
        "Completed"
      ]
    }
  ]
})
```

```javascript
assert.equal( sheetsCreateResponse.status, 200 )
ID_SHEET = sheetsCreateResponse.body.result.id
ID_SHEET_COLUMN1 = sheetsCreateResponse.body.result.columns[0].id
ID_SHEET_COLUMN2 = sheetsCreateResponse.body.result.columns[1].id
ID_SHEET_COLUMN3 = sheetsCreateResponse.body.result.columns[2].id
```

Returns the sheet in the format specified. Gantt chart is not generated at this time.
 Allowed For: [READ_SHEETS]

```javascript
sheetResponse = client.sheet.sheetId(ID_SHEET).get()
```

```javascript
assert.equal( sheetResponse.status, 200 )
```

Updates (renames) the sheet specified in the URL. To modify sheet contents, see Insert Rows into Sheet, Modify Row, Update Row Cells, and Modify Column. Method PUT /sheet/{sheetId} Access Scope ADMIN_SHEETS Headers Content-type: application/json Request Body JSON-formatted Sheet object, limited to the following attribute: name(string) Name need not be unique. Returns Result object containing a Sheet object for the newly updated sheet.
 Allowed For: [ADMIN_SHEETS]

```javascript
sheetUpdateResponse = client.sheet.sheetId(ID_SHEET).put({
  "name" : "notebookTestSheet"
})
```

```javascript
assert.equal( sheetUpdateResponse.status, 200 )
```

Returns the sheet version without loading the entire sheet. The following actions increment sheet version: add/modify cell value add/modify row add/modify discussion/comment change formatting add/remove/update version attachment cell updated via cell link
 Allowed For: [READ_SHEETS]

```javascript
versionResponse = client.sheet.sheetId(ID_SHEET).version.get()
```

```javascript
assert.equal( versionResponse.status, 200 )
```

Sends the sheet as a PDF attachment via email to the designated recipients.
 Allowed For: [SHARE_SHEETS]

```javascript
emailsCreateResponse = client.sheet.sheetId(ID_SHEET).emails.post({
  "to" : [
    "john.doe@smartsheet.com" ,
    "jane.doe@smartsheet.com"
  ] ,
  "subject" : "Check these rows out!(Notebook Test)" ,
  "message" : "Here are the rows I mentioned in our meeting" ,
  "ccMe" : false ,
  "format" : "PDF" ,
  "formatDetails" : {
    "paperSize" : "A4"
  }
})
```

```javascript
assert.equal( emailsCreateResponse.status, 200 )
```

Returns the columns on the sheet.
 Allowed For: [READ_SHEETS]

```javascript
columnsResponse = client.sheet.sheetId(ID_SHEET).columns.get()
```

```javascript
assert.equal( columnsResponse.status, 200 )
```

Delete column if exist

```javascript
for (i = 0; i < columnsResponse.body.length; i++){
  if (columnsResponse.body[i].title == "Status2"){
  	client.sheet.sheetId(ID_SHEET).column.columnId(columnsResponse.body[i].id).delete()
  }
}
```

Inserts a column into the sheet. See Column Types for more information. Result object containing Column object for the newly created column.
 Allowed For: [ADMIN_SHEETS]
 

```javascript
columnsCreateResponse = client.sheet.sheetId(ID_SHEET).columns.post({
  "title" : "Status2" ,
  "type" : "PICKLIST" ,
  "options" : [
    "Not Started" ,
    "Started" ,
    "Completed"
  ] ,
  "index" : 1
})
```

```javascript
assert.equal( columnsCreateResponse.status, 200 )
ID_SHEET_COLUMN = columnsCreateResponse.body.result.id
```

Inserts rows into the sheet. Note that all rows are added at the same hierarchical level, as specified by the RowWrapper. Currently we do not support different levels of hierarchy within the inserted rows. See Contact List Columns for information on inserting cells for columns of type CONTACT_LIST. Result object containing an array of newly inserted Row objects. Result object also returns the new version of the sheet.
 Allowed For: [WRITE_SHEETS]

```javascript
rowsCreateResponse = client.sheet.sheetId(ID_SHEET).rows.post({
  "toTop" : true ,
  "rows" : [
    {
      "cells" : [
        {
          "columnId" : ID_SHEET_COLUMN1 ,
          "value" : true
        } , {
          "columnId" : ID_SHEET_COLUMN2 ,
          "value" : "New status" ,
          "strict" : false
        }
      ]
    } , {
      "cells" : [
        {
          "columnId" : ID_SHEET_COLUMN1 ,
          "value" : true
        } , {
          "columnId" : ID_SHEET_COLUMN2 ,
          "value" : "New status" ,
          "strict" : false
        }
      ]
    }
  ]
})
```

```javascript
assert.equal( rowsCreateResponse.status, 200 )
ROW_NUMBER = rowsCreateResponse.body.result[0].rowNumber
ID_ROW = rowsCreateResponse.body.result[0].id
ID_ROW2 = rowsCreateResponse.body.result[1].id
```

Get row by its number in the Sheet. Row object.
 Allowed For: [READ_SHEETS]

```javascript
rowNumberResponse = client.sheet.sheetId(ID_SHEET).row.rowNumber(ROW_NUMBER).get()
```

```javascript
assert.equal( rowNumberResponse.status, 200 )
```

Gets a row by its Id. Result object for the ID provided.
 Allowed For: [READ_SHEETS]

```javascript
rowResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).get()
```

```javascript
assert.equal( rowResponse.status, 200 )
```

Allows you to expand/collapse a row or modify its position (including indenting/outdenting). All child rows are moved with the row. Result object containing an array of Row objects that have been moved by the operation.
 Allowed For: [WRITE_SHEETS]

```javascript
rowUpdateResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).put({
  "parentId" : ID_ROW2 ,
  "expanded" : true
})
```

```javascript
assert.equal( rowUpdateResponse.status, 200 )
```

Sends a row via email. Result object.
 Allowed For: [SHARE_SHEETS]

```javascript
emailsCreateResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).emails.post({
  "to" : [
    "john.doe@smartsheet.com" ,
    "jane.doe@smartsheet.com"
  ] ,
  "subject" : "Check this row out!(Notebook test)" ,
  "message" : "Here are the rows I mentioned in our meeting" ,
  "ccMe" : false ,
  "includeAttachments" : false ,
  "includeDiscussions" : true
})
```

```javascript
assert.equal( emailsCreateResponse.status, 200 )
```

Gets the cell modification history. Note that this is a resource-intensive operation and incurs 10 additional requests against the rate limit. An array of Cell History Objects
 Allowed For: [READ_SHEETS]

```javascript
historyResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).column.columnId(ID_SHEET_COLUMN1).history.get()
```

```javascript
assert.equal( historyResponse.status, 200 )
```

Returns a list of all Attachments that are on the row, including row and discussion level Attachments.
 Allowed For: [READ_SHEETS]

```javascript
attachmentsResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).attachments.get()
```

```javascript
assert.equal( attachmentsResponse.status, 200 )
```

Attaches a URL to a sheet, row or comment. This url can be a normal URL (attachmentType "LINK"), a Google Drive URL (attachmentType "GOOGLE_DRIVE") a Box.com URL (attachmentType "BOX_COM"), a Dropbox URL (attachmentType "DROPBOX"), or an Evernote URL (attachmentType "EVERNOTE"). NOTE: attachmentSubType is valid only for GOOGLE_DRIVE attachments which are Google Docs. It can optionally be included to indicate the type of a file. The following attachmentSubTypes are valid for GOOGLE_DRIVE attachments "DOCUMENT", "SPREADSHEET", "PRESENTATION", "PDF", "DRAWING". NOTE: when the attachment type is BOX_COM, DROPBOX, or GOOGLE_DRIVE (without an attachmentSubType specified), the mimeType will be derived by the file extension specified on the "name".
 Allowed For: [WRITE_SHEETS]

```javascript
attachmentsCreateResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).attachments.post({
  "name" : "Search Engine" ,
  "description" : "A popular search engine(Notebook test)" ,
  "attachmentType" : "LINK" ,
  "url" : "http://www.google.com"
})
```

```javascript
assert.equal( attachmentsCreateResponse.status, 200 )
```

Creates a new Discussion on a Sheet or Row. Result object containing Discussion object for the newly created discussion.
 Allowed For: [WRITE_SHEETS]

```javascript
discussionsCreateResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).discussions.post({
  "title" : "This is a new discussion(Notebook test)" ,
  "comment" : {
    "text" : "This text is the body of the first comment"
  }
})
```

```javascript
assert.equal( discussionsCreateResponse.status, 200 )
```

Deletes the row by its Id. Note that this will delete ALL child rows of the row specified. Result object.
 Allowed For: [WRITE_SHEETS]

```javascript
rowDeleteResponse = client.sheet.sheetId(ID_SHEET).row.rowId(ID_ROW).delete()
```

```javascript
assert.equal( rowDeleteResponse.status, 200 )
```

Delete sheet

```javascript
client.sheet.sheetId(ID_SHEET).delete()
```