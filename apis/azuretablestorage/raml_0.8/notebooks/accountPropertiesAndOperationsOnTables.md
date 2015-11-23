---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#65f53695cb2c06554b57
apiNotebookVersion: 1.1.66
title: Account Properties and Operations on Tables
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
TABLE_NAME = "MyTestTable" + new Date().getTime()
```

The Create Table operation creates a new table in the storage account

```javascript
tableCreateResponse = client.Tables.post({
  "TableName" : TABLE_NAME
},generateHeaders("/Tables"))
```

```javascript
assert.equal( tableCreateResponse.status, 201 )
```

The Get Table Service Properties operation (comp="properties") gets the properties of a storage account's Table service, including properties for Storage Analytics and CORS (Cross-Origin Resource Sharing) rules.

The Get Table Service Stats operation (comp="stats") retrieves statistics related to replication for the Table service. It is only available on the secondary location endpoint when read-access geo-redundant replication is enabled for the storage account.

```javascript
accountPropertiesResponse = client.get(null,generateHeaders("/?comp=properties",{restype:"service",comp:"properties"}))
```

```javascript
assert.equal( accountPropertiesResponse.status, 200 )
```

The Set Table Service Properties operation sets properties for a storage account's Table service endpoint, including properties for Storage Analytics and CORS (Cross-Origin Resource Sharing) rules. See Cross-Origin Resource Sharing (CORS) Support for the Windows Azure Storage Services for more information on CORS rules.

```javascript
accountPropertiesUpdateResponse = client.put(
 "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
+  "<StorageServiceProperties>"
+    "<Logging>"
+     "<Version>1.0</Version>"
+     "<Delete>true</Delete>"
+      "<Read>false</Read>"
+      "<Write>true</Write>"
+      "<RetentionPolicy>"
+      "<Enabled>true</Enabled>"
+      "<Days>7</Days>"
+    "</RetentionPolicy>"
+  "</Logging>"
+"</StorageServiceProperties>",generateHeaders("/?comp=properties",{restype:"service",comp:"properties"}))
```

```javascript
assert.equal( accountPropertiesUpdateResponse.status, 202 )
```

The Get Table ACL operation returns details about any stored access policies specified on the table that may be used with Shared Access Signatures. For more information, see Establishing a Stored Access Policy (REST API)

```javascript
tableAclRetreiveHeaders = generateHeaders("/" + TABLE_NAME + "?comp=acl",{comp:"acl"})
tableAclRetreiveHeaders.headers["Accept"] = "application/xml"
```

```javascript
tableAclResponse = client.myTable(TABLE_NAME).get(null,tableAclRetreiveHeaders)
```

```javascript
assert.equal( tableAclResponse.status, 200 )
```

The Get Table ACL operation returns details about any stored access policies specified on the table that may be used with Shared Access Signatures. For more information, see Establishing a Stored Access Policy (REST API)

```javascript
// Not supported
//tableAclHeadResponse = client.myTable(TABLE_NAME).head(null,tableAclRetreiveHeaders)
```

```javascript
//assert.equal( myTableHeadResponse.status, 200 )
```

The Set Table ACL operation sets the stored access policies for the table that may be used with Shared Access Signatures. For more information, see Creating a Shared Access Signature

```javascript
tableAclUpdateHeaders = generateHeaders("/" + TABLE_NAME +"?comp=acl",{comp:"acl"})
tableAclUpdateHeaders.headers["Content-Type"] = "application/xml"
tableAclUpdateHeaders.headers["Accept"] = "application/xml"
```

```javascript
tableAclUpdateResponse = client.myTable(TABLE_NAME).put(
  "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
+"<SignedIdentifiers>"
+  "<SignedIdentifier> "
+    "<Id>MTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTI=</Id>"
+    "<AccessPolicy>"
+      "<Start>2013-11-26T08:49:37.0000000Z</Start>"
+      "<Expiry>2014-11-26T08:49:37.0000000Z</Expiry>"
+      "<Permission>raud</Permission>"
+    "</AccessPolicy>"
+  "</SignedIdentifier>"
  +"</SignedIdentifiers>",tableAclUpdateHeaders)
```

```javascript
assert.equal( tableAclUpdateResponse.status, 204 )
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