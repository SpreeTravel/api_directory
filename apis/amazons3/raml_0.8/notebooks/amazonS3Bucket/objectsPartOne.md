---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8120/versions/8316/portal/pages/7024/preview
apiNotebookVersion: 1.1.66
title: Objects part 1
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
OBJECT_KEY = "notebooktests/testfile.txt"
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
  if(!isEmpty(query)){
    for(var paramName in query){
      canonicalRequsest += paramName + "=" + query[paramName] + "&"      
    }
    canonicalRequsest = canonicalRequsest.substring(0,canonicalRequsest.length-1)
  }
  canonicalRequsest += "\n"
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

Auxiliary method for extracting values of XML elements

```javascript
function extractTagValue(xmlBody,tagName){
  var uploadIdOpenTag = "<" + tagName + ">"
  var uploadIdCloseTag = "</" + tagName + ">"  
  var ind1 = xmlBody.indexOf(uploadIdOpenTag)
  if(ind1<0){
    return null;
  }
  ind1 += uploadIdOpenTag.length
  var ind2 = xmlBody.indexOf(uploadIdCloseTag,ind1)
  if(ind2<0){
    return null
  }
  return xmlBody.substring(ind1,ind2)
}
```

Create a new bucket in the US Standard region

```javascript
client.put(null, generateHeadersAndQuery("PUT","/"))
```

This operation initiates a multipart upload and returns an upload ID. This upload ID is used to associate all the parts in the specific multipart upload. You specify this upload ID in each of your subsequent upload part requests (see Upload Part). You also include this upload ID in the final request to either complete or abort the multipart upload request.
Note: After you initiate multipart upload and upload one or more parts, you must either complete or abort multipart upload in order to stop getting charged for storage of the uploaded parts. Only after you either complete or abort multipart upload, Amazon S3 frees up the parts storage and stops charging you for the parts storage.

```javascript
uploadsCreateResponse = client("/"+OBJECT_KEY).post(null, generateHeadersAndQuery("POST","/"+OBJECT_KEY,null,{uploads:""}))
```

```javascript
assert.equal( uploadsCreateResponse.status, 200 )
uploadId = extractTagValue(uploadsCreateResponse.body,"UploadId")
```

This operation aborts a multipart upload. After a multipart upload is aborted, no additional parts can be uploaded using that upload ID. The storage consumed by any previously uploaded parts will be freed. However, if any part uploads are currently in progress, those part uploads might or might not succeed. As a result, it might be necessary to abort a given multipart upload multiple times in order to completely free all storage consumed by all parts. To verify that all parts have been removed, so you don't get charged for the part storage, you should call the List Parts operation and ensure the parts list is empty.

```javascript
cancelUploadResponse = client.objectName(OBJECT_KEY).delete(null,generateHeadersAndQuery("DELETE","/"+OBJECT_KEY,null,{uploadId: uploadId}))
```

```javascript
assert.equal( cancelUploadResponse.status, 204 )
```

```javascript
CR
```

Lets start another upload.

```javascript
uploadsCreateResponse = client("/"+OBJECT_KEY).post(null, generateHeadersAndQuery("POST","/"+OBJECT_KEY,null,{uploads:""}))
uploadId = extractTagValue(uploadsCreateResponse.body,"UploadId")
```

This operation uploads a part in a multipart upload.
Note: In this operation you provide part data in your request. However, you have an option to specify your existing Amazon S3 object as data source for the part your are uploading. To upload a part from an existing object you use the Upload Part (Copy) operation. For more information, see Upload Part - Copy.
You must initiate a multipart upload (see Initiate Multipart Upload) before you can upload any part. In response to your initiate request. Amazon S3 returns an upload ID, a unique identifier, that you must include in your upload part request.
Part numbers can be any number from 1 to 10,000, inclusive. A part number uniquely identifies a part and also defines its position within the object being created. If you upload a new part using the same part number that was used with a previous part, the previously uploaded part is overwritten. Each part must be at least 5 MB in size, except the last part. There is no size limit on the last part of your multipart upload.
To ensure that data is not corrupted when traversing the network, specify the Content-MD5 header in the upload part request. Amazon S3 checks the part data against the provided MD5 value. If they do not match, Amazon S3 returns an error.
Note: After you initiate multipart upload and upload one or more parts, you must either complete or abort multipart upload in order to stop getting charged for storage of the uploaded parts. Only after you either complete or abort multipart upload, Amazon S3 frees up the parts storage and stops charging you for the parts storage.


```javascript
uploadPartPayload = "API Notebook test"
```

```javascript
partUploadResponse = client.objectName(OBJECT_KEY).put(
  uploadPartPayload,
	generateHeadersAndQuery("PUT","/"+OBJECT_KEY,uploadPartPayload,{partNumber:1,uploadId : uploadId})
)
```

```javascript
assert.equal( partUploadResponse.status, 200 )
```

This operation completes a multipart upload by assembling previously uploaded parts.

```javascript
completeUploadPayload =
 "<CompleteMultipartUpload>" + "\n"
+  "<Part>" + "\n"
+    "<PartNumber>" + 1 + "</PartNumber>" + "\n"
+    "<ETag>"+ partUploadResponse.headers.etag + "</ETag>" + "\n"
+  "</Part>" + "\n"
+"</CompleteMultipartUpload>"
```

```javascript
uploadIdUploadIdCreateResponse = client("/"+OBJECT_KEY).post(completeUploadPayload
,generateHeadersAndQuery("POST","/"+OBJECT_KEY,completeUploadPayload,{uploadId : uploadId}))
```

```javascript
assert.equal( uploadIdUploadIdCreateResponse.status, 200 )
```

List objects of the bucket

```javascript
bucketResponse = client.get(null, generateHeadersAndQuery("GET","/"))
```

This implementation of the GET operation retrieves objects from Amazon S3. To use GET, you must have READ access to the object.
If you grant READ access to the anonymous user, you can return the object without using an authorization header.

An Amazon S3 bucket has no directory hierarchy such as you would find in a typical computer file system. You can, however,
create a logical hierarchy by using object key names that imply a folder structure. For example, instead of naming an
object sample.jpg , you can name it photos/2006/February/sample.jpg.

To get an object from such a logical hierarchy, specify the full key name for the object in the GET operation. For a virtual
hosted-style request example, if you have the object photos/2006/February/sample.jpg, specify the resource as
/photos/2006/February/sample.jpg. For a path-style request example, if you have the object photos/2006/February/sample.jpg in
the bucket named examplebucket, specify the resource as /examplebucket/photos/2006/February/sample.jpg.

To distribute large files to many people, you can save bandwidth costs using BitTorrent.

If the object you are retrieving is a GLACIER storage class object, the object is archived in Amazon Glacier. You must firs
restore a copy using the POST Object restore API before you can retrieve the object. Otherwise, this operation returns
InvalidObjectStateError error.

```javascript
retrieveObjectHeaders = generateHeadersAndQuery("GET","/"+OBJECT_KEY)
retrieveObjectHeaders.headers["If-Modified-Since"] = "Thu, 09 Oct 2013 20:46:48 GMT"
```

```javascript
//Not supported. Response has application/json Content-Type and a plain string (instead of json object or array) as content. Notebook is not capable of processing such responses.
//objectResponse = client.objectName(OBJECT_KEY).get(null, retrieveObjectHeaders)
```

```javascript
//assert.equal( objectResponse.status, 200 )
```

The HEAD operation retrieves metadata from an object without returning the object itself. This operation is useful if you are interested
only in an object's metadata. To use HEAD, you must have READ access to the object.

A HEAD request has the same options as a GET operation on an object. The response is identical to the GET response except that
there is no response body.

```javascript
objectHeadResponse = client.objectName(OBJECT_KEY).head(null, generateHeadersAndQuery("HEAD","/"+OBJECT_KEY))
```

```javascript
assert.equal( objectHeadResponse.status, 200 )
```

This implementation of the PUT operation adds an object to a bucket. You must have WRITE permissions on a bucket to add an object to it.

Amazon S3 never adds partial objects; if you receive a success response, Amazon S3 added the entire object to the bucket.

Amazon S3 is a distributed system. If it receives multiple write requests for the same object simultaneously, it overwrites all but the
last object written. Amazon S3 does not provide object locking; if you need this, make sure to build it into your application
layer or use versioning instead.

To ensure that data is not corrupted traversing the network, use the Content-MD5 header. When you use this header, Amazon S3 checks
the object against the provided MD5 value and, if they do not match, returns an error. Additionally, you can calculate the MD5 while
putting an object to Amazon S3 and compare the returned ETag to the calculated MD5 value.

**Note**
To configure your application to send the Request Headers prior to sending the request body, use the 100-continue HTTP status code.
For PUT operations, this helps you avoid sending the message body if the message is rejected based on the headers (e.g., because of
authentication failure or redirect).

```javascript
objectUpdateResponse = client.objectName(OBJECT_KEY).put(null, generateHeadersAndQuery("PUT","/"+OBJECT_KEY))
```

```javascript
assert.equal( objectUpdateResponse.status, 200 )
```

This implementation of the GET operation uses the torrent subresource to return torrent files from a bucket. BitTorrent can save you bandwidth when you're distributing large files. For more information about BitTorrent, see Amazon S3 Torrent.

Note
You can get torrent only for objects that are less than 5 GB in size and that are not encrypted using server-side encryption with customer-provided encryption key.
To use GET, you must have READ access to the object.

```javascript
torrentResponse = client("/"+OBJECT_KEY).get(null,generateHeadersAndQuery("GET","/"+OBJECT_KEY,null,{torrent:""}))
```

```javascript
assert.equal( torrentResponse.status, 200 )
```

This implementation of the GET operation uses the acl subresource to return the access control list (ACL) of an object.
To use this operation, you must have READ_ACP access to the object.

```javascript
aclResponse = client(OBJECT_KEY).get(null,generateHeadersAndQuery("GET","/"+OBJECT_KEY,null,{acl:""}))
```

```javascript
assert.equal( aclResponse.status, 200 )
```

This implementation of the PUT operation uses the acl subresource to set the access control list (ACL) permissions for an
object that already exists in a bucket. You must have WRITE_ACP permission to set the ACL of an object.

You can use one of the following two ways to set an object's permissions:
* Specify the ACL in the request body, or
* Specify permissions using request headers

Depending on your application needs, you may choose to set the ACL on an object using either the request body or the headers.
For example, if you have an existing application that updates an object ACL using the request body, then you can continue to use that approach.

```javascript
OWNER = extractTagValue(bucketResponse.body,"Owner")
```

```javascript
aclPayload = 
 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"
+"<AccessControlPolicy>" + "\n"
+  "<Owner>" + "\n"
+    OWNER + "\n"
+  "</Owner>" + "\n"
+  "<AccessControlList>" + "\n"
+  "</AccessControlList>" + "\n"
+"</AccessControlPolicy>" + "\n"
```

```javascript
aclUpdateResponse = client(OBJECT_KEY).put(aclPayload,generateHeadersAndQuery("PUT","/"+OBJECT_KEY,aclPayload,{acl:""}))
```

```javascript
assert.equal( aclUpdateResponse.status, 200 )
```

The DELETE operation removes the null version (if there is one) of an object and inserts a delete marker, which becomes the lates
version of the object. If there isn't a null version, Amazon S3 does not remove any objects.
To remove a specific version, you must be the bucket owner and you must use the versionId subresource. Using this subresource permanently
deletes the version. If the object deleted is a Delete Marker, Amazon S3 sets the response header, x-amz-delete-marker, to true.

If the object you want to delete is in a bucket where the bucket versioning configuration is MFA Delete enabled, you must include the
x-amz-mfa request header in the DELETE versionId request. Requests that include x-amz-mfa must use HTTPS.

```javascript
objectNameDeleteResponse = client.objectName(OBJECT_KEY).delete(null,generateHeadersAndQuery("DELETE","/"+OBJECT_KEY))
```

```javascript
assert.equal( objectNameDeleteResponse.status, 204 )
```

Garbage collection. Delete the test bucket.

```javascript
client.delete(null, generateHeadersAndQuery("DELETE","/"))
```