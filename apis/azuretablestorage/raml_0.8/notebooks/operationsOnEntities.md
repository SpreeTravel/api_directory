---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#a091212da1baa5e408cf
apiNotebookVersion: 1.1.66
title: Operations on Entities
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

Load a cryptographic library

```javascript
load('https://caligatio.github.io/jsSHA/sha.js')
```

```javascript
ACCESS_KEY = prompt("Please, enter Access Key of your Microsoft Azure account.")
ACCOUNT_NAME = prompt("Please, enter name of your Microsoft Azure storage.")
```

```javascript
// Read about the Azure Table Storage REST API at http://api-portal.anypoint.mulesoft.com/onpositive/api/azure-table-storage-rest-api
API.createClient(
  'client',
  '/apiplatform/repository/public/organizations/30/apis/8112/versions/8308/definition', {
    baseUriParameters: {
      accountName: ACCOUNT_NAME
    }
  }
);
```

Auxiliary method for retreiving corrent date.

```javascript
function getCurrentDate(){
  //Sun, 11 Oct 2009 21:49:13 GMT
  var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  var date = new Date()
  var day = days[date.getUTCDay()]
  var dayOfMonth = date.getUTCDate()
  var month = months[date.getUTCMonth()]
  var year = date.getUTCFullYear()
  var h = date.getUTCHours()
  var m = date.getUTCMinutes()
  var s = date.getUTCSeconds()

  var str = day + "," + " " 
  if(dayOfMonth<10)
    str += 0
  str += dayOfMonth + " " + month + " " + year + " "
  if(h<10)
    str += 0
  str += h + ":"
  if(m<10)
    str += 0
  str +=  m + ":"
  if(s<10)
    str += 0
  str += s + " GMT"
  result = new Object()  
  return str
}
```

Auxiliary method for checking whether the object is empty or not.

```javascript
function isEmpty(obj){
  if(obj==undefined||obj==null){
    return true
  }
  for(var key in obj){
    return false
  }
  return true
}
```

Auxiliary method which forms the request headers set.

```javascript
function generateHeaders(uri,query,type){
  var date = getCurrentDate()
  var hashObj = new jsSHA( date +"\n/" + ACCOUNT_NAME + uri, "TEXT");
  var hash = hashObj.getHMAC( ACCESS_KEY, "B64", "SHA-256", "B64", 1 )
  var result = new Object()
  var acceptType = "json;odata=fullmetadata"
  var contentType = "json"
  if (type == "xml"){
    acceptType = "xml"
    contentType = "xml"
  }
  result.headers = new Object()
  result.headers["Authorization"] = "SharedKeyLite " + ACCOUNT_NAME + ":" + hash
  result.headers["Date"] = date
  result.headers["x-ms-date"] = date
  result.headers["Accept"] = "application/" + acceptType
  result.headers["Content-Type"] = "application/" + contentType
  result.headers["x-ms-version"] = "2014-02-14"
  if(!isEmpty(query)){
    result["query"] = query
  } 
  return result
}
```

Name of table operated by the notebook.

```javascript
TABLE_NAME = "NotebookTable" + new Date().getTime()
```

The Query Tables operation returns a list of tables under the specified account

```javascript
tablesResponse = client.Tables.get(null,generateHeaders("/Tables"))
```

```javascript
assert.equal( tablesResponse.status, 200 )
```

The Create Table operation creates a new table in the storage account

```javascript
tableCreateResponse = client.Tables.post({
  "TableName": TABLE_NAME
},generateHeaders("/Tables"))
```

```javascript
assert.equal( tableCreateResponse.status, 201 )
```

The Insert Entity operation inserts a new entity into a table

```javascript
entityCreateResponse =  client.myTable(TABLE_NAME).post({
  "Address" : "Mountain View" ,
  "Age" : 23 ,
  "AmountDue" : 200.23 ,
  "CustomerCode@odata.type" : "Edm.Guid" ,
  "CustomerCode" : "c9da6455-213d-42c9-9a79-3e9149a57833" ,
  "CustomerSince@odata.type" : "Edm.DateTime" ,
  "CustomerSince" : "2008-07-10T00:00:00" ,
  "IsActive" : true ,
  "NumberOfOrders@odata.type" : "Edm.Int64" ,
  "NumberOfOrders" : "255" ,
  "PartitionKey" : "p1" ,
  "RowKey" : "r1"
},generateHeaders("/" + TABLE_NAME ))
```

```javascript
assert.equal(entityCreateResponse.status, 201 )
partitionKey =entityCreateResponse.body.PartitionKey
rowKey = entityCreateResponse.body.RowKey
```

The Query Entities operation queries entities in a table and includes the"filter" and "select" options.

```javascript
myTableKeyURI = escape("/" + TABLE_NAME + "(PartitionKey='" + partitionKey +"',RowKey='" + rowKey + "')")
```

```javascript
myTableKeyResponse = client(myTableKeyURI).get(null, generateHeaders(myTableKeyURI))
```

```javascript
assert.equal( myTableKeyResponse.status, 200 )
```

The Insert Or Replace Entity operation replaces an existing entity or inserts a new entity if it does not exist in the table. Because this operation can insert or update an entity, it is also known as an upsert operation.

```javascript
entityUpdateResponse = client(myTableKeyURI).put({ 
  "Address" : "Santa Clara" ,
  "Age" : 23 ,
  "AmountDue" : 200.23 ,
  "CustomerCode@odata.type" : "Edm.Guid" ,
  "CustomerCode" : "c9da6455-213d-42c9-9a79-3e9149a57833" ,
  "CustomerSince@odata.type" : "Edm.DateTime" ,
  "CustomerSince" : "2008-07-10T00:00:00" ,
  "IsActive" : false ,
  "NumberOfOrders@odata.type" : "Edm.Int64" ,
  "NumberOfOrders" : "255" ,
  "PartitionKey" : partitionKey ,
  "RowKey" : rowKey
}, generateHeaders(myTableKeyURI))
```

```javascript
assert.equal( entityUpdateResponse.status, 204 )
```

The Query Entities operation queries entities in a table and includes the"filter" and "select" options.

```javascript
tableResponse = client.myTable(TABLE_NAME).get(null,generateHeaders("/" + TABLE_NAME ))
```

```javascript
assert.equal( tableResponse.status, 200 )
```

The Delete Entity operation deletes an existing entity in a table.

Let's prepare headers for entity removal.

```javascript
deleteEntityHeaders = generateHeaders(myTableKeyURI)
deleteEntityHeaders.headers["If-Match"] = "*"
```

```javascript
entityDeleteResponse = client(myTableKeyURI).delete(null,deleteEntityHeaders)
```

```javascript
assert.equal( entityDeleteResponse.status, 204 )
```

The Table service supports batch transactions on entities that are in the same table and belong to the same partition group.
Multiple Insert Entity (REST API), Update Entity (REST API), Merge Entity (REST API), Delete Entity (REST API),
Insert Or Replace Entity (REST API), and Insert Or Merge Entity (REST API) operations are supported within a single transaction.
You can perform entity group transactions either via REST or by using the .NET Client Library for WCF Data Services.

Let's prepare payload for batch.

```javascript
batchContent = {
  "Address" : "Mountain View" ,
  "Age" : 23 ,
  "AmountDue" : 200.23 ,
  "CustomerCode@odata.type" : "Edm.Guid" ,
  "CustomerCode" : "c9da6455-213d-42c9-9a79-3e9149a57833" ,
  "CustomerSince@odata.type" : "Edm.DateTime" ,
  "CustomerSince" : "2008-07-10T00:00:00" ,
  "IsActive" : true ,
  "NumberOfOrders@odata.type" : "Edm.Int64" ,
  "NumberOfOrders" : "255" ,
  "PartitionKey" : "p3" ,
  "RowKey" : "r3"
}
```

```javascript
batchPayload = 
"--batch_a1e9d677-b28b-435e-a89e-87e6a768a431" + "\n"
+"Content-Type: multipart/mixed; boundary=changeset_8a28b620-b4bb-458c-a177-0959fb14c977" + "\n"
+"" + "\n"
+"--changeset_8a28b620-b4bb-458c-a177-0959fb14c977" + "\n"
+"Content-Type: application/http" + "\n"
+"Content-Transfer-Encoding: binary" + "\n"
+"" + "\n"
+"POST https://myaccount.table.core.windows.net/" + TABLE_NAME +" HTTP/1.1" + "\n"
+"Content-Type: application/json" + "\n"
+"Accept: application/json;odata=minimalmetadata" + "\n"
+"Prefer: return-no-content" + "\n"
+"DataServiceVersion: 3.0;" + "\n"
+"" + "\n"
+ JSON.stringify(batchContent) + "\n"
+"--changeset_8a28b620-b4bb-458c-a177-0959fb14c977--" + "\n"
+"--batch_a1e9d677-b28b-435e-a89e-87e6a768a431--"
```

Let's prepare headers for batch.

```javascript
batchHeaders = generateHeaders("/$batch")
batchHeaders.headers["Content-Type"] = "multipart/mixed; boundary=batch_a1e9d677-b28b-435e-a89e-87e6a768a431"
```

```javascript
batchResponse = client.$batch.post(batchPayload,batchHeaders)
```

```javascript
assert.equal(batchResponse.status, 202 )
```

The Delete Table operation deletes the specified table and any data it contains

```javascript
tableURI = escape("/Tables('" + TABLE_NAME + "')")
```

```javascript
tableDeleteResponse = client(tableURI).delete(null,generateHeaders(tableURI))
```

```javascript
assert.equal(tableDeleteResponse.status, 204 )
```