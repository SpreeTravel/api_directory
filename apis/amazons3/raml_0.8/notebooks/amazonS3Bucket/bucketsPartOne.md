---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8120/versions/8316/portal/pages/7021/preview
apiNotebookVersion: 1.1.66
title: Buckets part 1
---

Loading four libraries for cryptography operations and one for value assertions.

```javascript
load('http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js')
```

```javascript
load('http://crypto-js.googlecode.com/svn/tags/3.1.2/build/components/enc-base64-min.js')
```

```javascript
load('http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/sha256.js')
```

```javascript
load('http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/hmac-sha256.js')
```

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
ACCESS_KEY = prompt("Please, enter your Access Key")
SECRET_KEY = prompt("Please, enter your Secret Key")
```

```javascript
BUCKET_NAME = "NotebookTestBucket" + new Date().getTime()
REGION = "us-east-1"
REGION_SERVICE = "s3"
HOST = (BUCKET_NAME + ".s3.amazonaws.com").toLowerCase()
```

```javascript
// Read about the Amazon RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8120/versions/8316/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8120/versions/8316/definition',
                 {
  baseUriParameters:{
    bucketName: BUCKET_NAME,
    region: REGION_SERVICE
  }
});
```

Auxiliary method for obtaining current date

```javascript
function getCurrentDate(){
  //Sun, 11 Oct 2009 21:49:13 GMT
  var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  var date = new Date()
  var day = days[date.getUTCDay()]
  var dayOfMonth = date.getUTCDate()
    if(dayOfMonth<10){dayOfMonth = "" + 0 + dayOfMonth}
  var month = date.getUTCMonth();
  var monthWord = months[month]
  month++
    if(month<10){month = "" + 0 + month}
  var year = date.getUTCFullYear()
  var h = date.getUTCHours()
    if(h<10){h = "" + 0 + h}
  var m = date.getUTCMinutes()
    if(m<10){m = "" + 0 + m}
  var s = date.getUTCSeconds()
    if(s<10){s = "" + 0 + s}

  var short = "" + year + month + dayOfMonth + "T" + h + m + s + "Z"
  var long = "" + day + ", " + dayOfMonth + " " + monthWord + " " + year + " " + h + ":" + m + ":" + s + " GMT"
  var result = new Object()
  result.longDate = long
  result.shortDate = short
  result.day = "" + year + month + dayOfMonth
  return result
}
```

Auxiliary method for checking if the object has any fields

```javascript
function isEmpty(obj){
  if(obj == undefined || obj == null){
    return true
  }
  for(var key in obj){
    return false
  }
  return true
}
```

Auxiliary method for generating value of Authorization header

```javascript
function generateAuthorizationHeaderValue(date,method,uri,payload,query,contentType){
  var dateHash = CryptoJS.HmacSHA256(date.day,"AWS4" + SECRET_KEY)
  var dateRegionHash = CryptoJS.HmacSHA256(REGION,dateHash)
  var dateRegionServiceHash = CryptoJS.HmacSHA256(REGION_SERVICE,dateRegionHash)
  var signingKey = CryptoJS.HmacSHA256("aws4_request",dateRegionServiceHash)

  var payloadHash = CryptoJS.SHA256(payload).toString(CryptoJS.enc.Hex)
  var signedHeaders = "content-md5;content-type;host;x-amz-content-sha256;x-amz-date"
  var contentMD5 = CryptoJS.MD5(payload).toString(CryptoJS.enc.Base64)
  var canonicalRequsest = method + "\n"
    + uri +"\n"
  if(isEmpty(query)){
    canonicalRequsest += "\n"
  }
  else{
    for(var paramName in query){
      canonicalRequsest += paramName + "=" + query[paramName] + "\n"
    }
  }
  canonicalRequsest += "content-md5:" + contentMD5 + "\n"
    + "content-type:" + contentType + "\n"    
    + "host:" + HOST + "\n"
    + "x-amz-content-sha256:" + payloadHash + "\n"
    + "x-amz-date:" + date.shortDate + "\n\n"    
    + signedHeaders + "\n"
    + payloadHash
  CR = canonicalRequsest
  var canonicalRequestHash = CryptoJS.SHA256(canonicalRequsest).toString(CryptoJS.enc.Hex)
  
  var stringToSign = "AWS4-HMAC-SHA256\n"
    + date.shortDate + "\n"
    + date.day + "/" + REGION + "/" + REGION_SERVICE + "/aws4_request\n"
    + canonicalRequestHash

  var signedString = CryptoJS.HmacSHA256(stringToSign,signingKey).toString(CryptoJS.enc.Hex)
  
  var authString = "AWS4-HMAC-SHA256 Credential=" + ACCESS_KEY + "/" + date.day + "/" +  REGION + "/" + REGION_SERVICE + "/aws4_request,"
    +"SignedHeaders=" + signedHeaders + ",Signature=" + signedString
   
   return authString
}
```

Auxiliary method which forms a set of valid headers an query parameters

```javascript
function generateHeadersAndQuery(method,uri,payload,query,contentType){

  if(contentType==undefined||contentType==null){
    contentType = "application/json"
  }
  
  var date = getCurrentDate()
  var authString = generateAuthorizationHeaderValue(date,method,uri,payload,query,contentType)
  var payloadHash = CryptoJS.SHA256(payload).toString(CryptoJS.enc.Hex)
  var contentMD5 = CryptoJS.MD5(payload).toString(CryptoJS.enc.Base64)
  var result = new Object()
  result.headers = new Object()
  result.headers["Authorization"] = authString
  result.headers["Date"] = date.longDate
  result.headers["Host"] = HOST
  result.headers["Content-MD5"] = contentMD5
  result.headers["Content-Type"] = contentType
  result.headers["x-amz-content-sha256"] = payloadHash
  result.headers["x-amz-date"] = date.shortDate
  if(!isEmpty(query)){
    result["query"] = query
  }
  return result
}
```

Create a new bucket in the US Standard region

```javascript
client.put(null, generateHeadersAndQuery("PUT","/"))
```


Sets the cors configuration for your bucket. If the configuration exists, Amazon S3 replaces it.

To use this operation, you must be allowed to perform the s3:PutBucketCORS action. By default, the bucket owner has this permission and can grant it to others.

You set this configuration on a bucket so that the bucket can service cross-origin requests. For example, you might want to enable a request whose origin is http://www.example.com to access your Amazon S3 bucket at my.example.bucket.com by using the browser's XMLHttpRequest capability.

To enable cross-origin resource sharing (CORS) on a bucket, you add the cors subresource to the bucket. The cors subresource is an XML document in which you configure rules that identify origins and the HTTP methods that can be executed on your bucket. The document is limited to 64 KB in size.

```javascript
corsPayload =
 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
+"<CORSConfiguration>"
+  "<CORSRule>"
+    "<ID>Rule_1</ID>"
+    "<AllowedOrigin>http://www.example.com</AllowedOrigin>"
+    "<AllowedMethod>GET</AllowedMethod>"
+    "<MaxAgeSeconds>3000</MaxAgeSeconds>"
+    "<ExposeHeader>x-amz-server-side-encryption</ExposeHeader>"
+  "</CORSRule>"
+"</CORSConfiguration>"
```

```javascript
corsUpdateResponse = client.put(corsPayload, generateHeadersAndQuery("PUT","/",corsPayload, {cors:""}))
```

```javascript
assert.equal( corsUpdateResponse.status, 200 )
```

Returns the cors configuration information set for the bucket.

To use this operation, you must have permission to perform the s3:GetBucketCORS action.
By default, the bucket owner has this permission and can grant it to others.

```javascript
corsResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{cors:""}))
```

```javascript
assert.equal( corsResponse.status, 200 )
```

Deletes the cors configuration information set for the bucket.

To use this operation, you must have permission to perform the s3:PutCORSConfiguration action.
The bucket owner has this permission by default and can grant this permission to others.

```javascript
corsDeleteResponse = client.delete(null, generateHeadersAndQuery("DELETE","/",null,{cors:""}))
```

```javascript
assert.equal( corsDeleteResponse.status, 204 )
```

This implementation of the PUT operation uses the tagging subresource to add a set of tags to an existing bucket.

Use tags to organize your AWS bill to reflect your own cost structure. To do this, sign up to get your AWS account bill with tag key values included. Then, to see the cost of combined resources, organize your billing information according to resources with the same tag key values. For example, you can tag several resources with a specific application name, and then organize your billing information to see the total cost of that application across several services. For more information, see Cost Allocation and Tagging in About AWS Billing and Cost Management.

To use this operation, you must have permission to perform the s3:PutBucketTagging action. By default, the bucket owner has this permission and can grant this permission to others.

```javascript
taggingPayload =
 "<Tagging>"
+  "<TagSet>"
+    "<Tag>"
+      "<Key>Project</Key>"
+      "<Value>Project One</Value>"
+    "</Tag>"
+    "<Tag>"
+      "<Key>User</Key>"
+      "<Value>jsmith</Value>"
+    "</Tag>"
+  "</TagSet>"
+"</Tagging>"
```

```javascript
taggingUpdateResponse = client.put(taggingPayload, generateHeadersAndQuery("PUT","/",taggingPayload,{tagging:""}))
```

```javascript
assert.equal( taggingUpdateResponse.status, 204 )
```

This implementation of the GET operation uses the tagging subresource to return the tag set associated with the bucket.

To use this operation, you must have permission to perform the s3:GetBucketTagging action. By default, the bucket owner
has this permission and can grant this permission to others.

```javascript
taggingResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{tagging:""}))
```

```javascript
assert.equal( taggingResponse.status, 200 )
```

This implementation of the DELETE operation uses the tagging subresource to remove a tag set from the specified bucket.

To use this operation, you must have permission to perform the s3:PutBucketTagging action. By default, the bucket
owner has this permission and can grant this permission to others.
Syntax

```javascript
taggingDeleteResponse = client.delete(null, generateHeadersAndQuery("DELETE","/",null,{tagging:""}))
```

```javascript
assert.equal( taggingDeleteResponse.status, 204 )
```

This implementation of the GET operation uses the versioning subresource to return the versioning state of a bucket.
To retrieve the versioning state of a bucket, you must be the bucket owner.

 This implementation also returns the MFA Delete status of the versioning state, i.e., if the MFA Delete status
 is enabled, the bucket owner must use an authentication device to change the versioning state of the bucket.

 There are three versioning states:
 * If you enabled versioning on a bucket, the response is:
```
     <VersioningConfiguration xmlns="http://s3.amazonaws.com/doc/2006-03-01/">
       <Status>Enabled</Status>
     </VersioningConfiguration>
```
 * If you suspended versioning on a bucket, the response is:
```
     <VersioningConfiguration xmlns="http://s3.amazonaws.com/doc/2006-03-01/">
       <Status>Suspended</Status>
     </VersioningConfiguration>
```
 * If you never enabled (or suspended) versioning on a bucket, the response is:
```
     <VersioningConfiguration xmlns="http://s3.amazonaws.com/doc/2006-03-01/"/>
```

```javascript
versioningResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{versioning:""}))
```

```javascript
assert.equal( versioningResponse.status, 200 )
```

This implementation of the PUT operation uses the versioning subresource to set the versioning state of an existing bucket.
To set the versioning state, you must be the bucket owner.

You can set the versioning state with one of the following values:
* **Enabled** Enables versioning for the objects in the bucket
  All objects added to the bucket receive a unique version ID.
* **Suspended** Disables versioning for the objects in the bucket
  All objects added to the bucket receive the version ID null.

If the versioning state has never been set on a bucket, it has no versioning state; a GET versioning request does no
return a versioning state value.

If the bucket owner enables MFA Delete in the bucket versioning configuration, the bucket owner must include the x-amz-mfa
request header and the Status and the MfaDelete request elements in a request to set the versioning state of the bucket.

```javascript
versioningPayload = 
 "<VersioningConfiguration>"
+  "<Status>Enabled</Status>"
+"</VersioningConfiguration>"
```

```javascript
versioningUpdateResponse = client.put(versioningPayload, generateHeadersAndQuery("PUT","/",versioningPayload, {versioning:""}))
```

```javascript
assert.equal( versioningUpdateResponse.status, 200 )
```

This operation lists in-progress multipart uploads. An in-progress multipart upload is a multipart upload that has been initiated, using the Initiate Multipart Upload request, but has not yet been completed or aborted.

This operation returns at most 1,000 multipart uploads in the response. 1,000 multipart uploads is the maximum number of uploads a response can include, which is also the default value. You can further limit the number of uploads in a response by specifying the max-uploads parameter in the response. If additional multipart uploads satisfy the list criteria, the response will contain an IsTruncated element with the value true. To list the additional multipart uploads, use the key-marker and upload-id-marker request parameters.

In the response, the uploads are sorted by key. If your application has initiated more than one multipart upload using the same object key, then uploads in the response are first sorted by key. Additionally, uploads are sorted in ascending order within each key by the upload initiation time.

```javascript
uploadsResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{uploads:""}))
```

```javascript
assert.equal( uploadsResponse.status, 200 )
```


Creates a new lifecycle configuration for the bucket or replaces an existing lifecycle configuration.

To use this operation, you must be allowed to perform the s3:PutLifecycleConfiguration action. By default,
the bucket owner has this permission and can grant this permission to others.

**Note**
If your bucket is version-enabled or versioning is suspended, you cannot add a lifecycle configuration.

If you want to block users or accounts from removing or deleting objects from your bucket, you must deny them
permissions for the following actions:
* s3:DeleteObjec
* s3:DeleteObjectVersion and
* s3:PutLifecycleConfiguration

If you want to block users or accounts from managing lifecycle configurations, you must deny permission
for the s3:PutLifecycleConfiguration action.

```javascript
lifeCyclePayload =
 "<LifecycleConfiguration>"
+  "<Rule>"
+    "<ID>archive-objects-glacier-immediately-upon-creation</ID>"
+    "<Prefix>glacierobjects/</Prefix>"
+    "<Status>Enabled</Status>"
+    "<Transition>"
+      "<Days>0</Days>"
+      "<StorageClass>GLACIER</StorageClass>"
+    "</Transition>"
+  "</Rule>"
+"</LifecycleConfiguration>"
```

```javascript
lifecycleUpdateResponse = client.put(lifeCyclePayload, generateHeadersAndQuery("PUT","/",lifeCyclePayload, {lifecycle:""}))
```

```javascript
assert.equal( lifecycleUpdateResponse.status, 200 )
```

Returns the lifecycle configuration information set on the bucket.

To use this operation, you must have permission to perform the s3:GetLifecycleConfiguration action. The bucket owner
has this permission, by default. The bucket owner can grant this permission to others.

```javascript
lifecycleResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{lifecycle:""}))
```

```javascript
assert.equal( lifecycleResponse.status, 200 )
```

You can use the versions subresource to list metadata about all of the versions of objects in a bucket.
You can also use request parameters as selection criteria to return metadata about a subset of all the object versions.

To use this operation, you must have READ access to the bucket.

```javascript
versionsResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{versions:""}))
```

```javascript
assert.equal( versionsResponse.status, 200 )
```

This implementation of the GET operation uses the location subresource to return a bucket's Region. You set the bucket's Region using the LocationContraint request parameter in a PUT Bucket request. For more information, see PUT Bucket. To use this implementation of the operation, you must be the bucket owner.

```javascript
locationResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{location:""}))
```

```javascript
assert.equal( locationResponse.status, 200 )
```


Deletes the lifecycle configuration from the specified bucket. Amazon S3 removes all the lifecycle configuration rules in the
lifecycle subresource associated with the bucket. Your objects never expire, and Amazon S3 no longer automatically deletes any
objects on the basis of rules contained in the deleted lifecycle configuration.

To use this operation, you must have permission to perform the s3:PutLifecycleConfiguration action.
By default, the bucket owner has this permission and the bucket owner can grant this permission to others.

There is usually some time lag before lifecycle configuration deletion is fully propagated to all the Amazon S3 systems.

```javascript
lifecycleDeleteResponse = client.delete(null, generateHeadersAndQuery("DELETE","/",null,{lifecycle:""}))
```

```javascript
assert.equal( lifecycleDeleteResponse.status, 204 )
```

Garbage collection. Delete the test bucket.

```javascript
client.delete(null, generateHeadersAndQuery("DELETE","/"))
```