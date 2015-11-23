---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8120/versions/8316/portal/pages/7023/preview
apiNotebookVersion: 1.1.66
title: Buckets part 3
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
GLOBAL_HOST = "s3.amazonaws.com"
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

```javascript
// Read about the Amazon S3 API (Services) at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8124/versions/8320/contracts
API.createClient('servicesClient', '/apiplatform/repository/public/organizations/30/apis/8124/versions/8320/definition');
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
function generateAuthorizationHeaderValue(date,method,uri,payload,query,contentType,useGlobalHost){

  var host = useGlobalHost ? GLOBAL_HOST : HOST
  
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
    + "host:" + host + "\n"
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
  
  var authString = "AWS4-HMAC-SHA256 Credential=" + ACCESS_KEY + "/" + date.day + "/" + REGION + "/" + REGION_SERVICE + "/aws4_request,"
    +"SignedHeaders=" + signedHeaders + ",Signature=" + signedString
   
   return authString
}
```

Auxiliary method which forms a set of valid headers an query parameters

```javascript
function generateHeadersAndQuery(method,uri,payload,query,contentType,useGlobalHost){

  if(contentType==undefined||contentType==null){
    contentType = "application/json"
  }
  
  var date = getCurrentDate()
  var authString = generateAuthorizationHeaderValue(date,method,uri,payload,query,contentType,useGlobalHost)
  var payloadHash = CryptoJS.SHA256(payload).toString(CryptoJS.enc.Hex)
  var contentMD5 = CryptoJS.MD5(payload).toString(CryptoJS.enc.Base64)
  var result = new Object()
  result.headers = new Object()
  result.headers["Authorization"] = authString
  result.headers["Date"] = date.longDate
  result.headers["Host"] = useGlobalHost ? GLOBAL_HOST : HOST.toLowerCase()
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

This implementation of the PUT operation creates a new bucket. To create a bucket, you must register with Amazon S3 and have
a valid AWS Access Key ID to authenticate requests. Anonymous requests are never allowed to create buckets.
By creating the bucket, you become the bucket owner.

Not every string is an acceptable bucket name. For information on bucket naming restrictions, see Working with Amazon S3 Buckets.

By default, the bucket is created in the US Standard region. You can optionally specify a region in the request body.
You might choose a Region to optimize latency, minimize costs, or address regulatory requirements. For example, if you reside
in Europe, you will probably find it advantageous to create buckets in the EU (Ireland) Region.

**Note**

If you create a bucket in a region other than US Standard, your application must be able to handle 307 redirect.

When creating a bucket using this operation, you can optionally specify the accounts or groups that should be granted specific
permissions on the bucket. There are two ways to grant the appropriate permissions using the request headers.

* Specify a canned ACL using the x-amz-acl request header.
* Specify access permissions explicitly using the x-amz-grant-read, x-amz-grant-write, x-amz-grant-read-acp, x-amz-grant-write-acp,
  x-amz-grant-full-control headers. These headers map to the set of permissions Amazon S3 supports in an ACL.

**Note**
You can use either a canned ACL or specify access permissions explicitly. You cannot do both.

```javascript
bucketPayload = ""
//As we use a standard US region, we leave the request payload empty. Otherwise, we would use XML similar to:
//   "<CreateBucketConfiguration>"
//  +  "<LocationConstraint>EU</LocationConstraint>"
//  +"</CreateBucketConfiguration>"
```

```javascript
bucketCreateResponse = client.put(bucketPayload, generateHeadersAndQuery("PUT","/",bucketPayload))
```

```javascript
assert.equal( bucketCreateResponse.status, 200 )
```

This implementation of the GET operation returns a list of all buckets owned by the authenticated sender of the request.
To authenticate a request, you must use a valid AWS Access Key ID that is registered with Amazon S3.
Anonymous requests cannot list buckets, and you cannot list buckets that you did not create.

```javascript
bucketsListResponse = servicesClient.get(null, generateHeadersAndQuery("GET","/",null,null,null,true))
```

```javascript
assert.equal( bucketsListResponse.status, 200 )
```

This implementation of the GET operation returns some or all (up to 1000) of the objects in a bucket.
You can use the request parameters as selection criteria to return a subset of the objects in a bucket.

To use this implementation of the operation, you must have READ access to the bucket.

```javascript
bucketResponse = client.get(null, generateHeadersAndQuery("GET","/"))
```

```javascript
assert.equal( bucketResponse.status, 200 )
```

HEAD Bucket
This operation is useful to determine if a bucket exists and you have permission to access it. The operation returns a
200 OK if the bucket exists and you have permission to access it. Otherwise, the operation might return responses such
as 404 Not Found and 403 Forbidden.

```javascript
bucketHeadResponse = client.head(null, generateHeadersAndQuery("HEAD","/"))
```

```javascript
assert.equal( bucketHeadResponse.status, 200 )
```

This implementation of the PUT operation uses the acl subresource to set the permissions on an existing
bucket using access control lists (ACL). For more information, go to Using ACLs. To set the ACL of a bucket,
you must have WRITE_ACP permission.

You can use one of the following two ways to set a bucket's permissions:
* Specify the ACL in the request body
* Specify permissions using request headers

**Note**
You cannot specify access permission using both the body and the request headers.

Depending on your application needs, you may choose to set the ACL on a bucket using either the request body or
the headers. For example, if you have an existing application that updates a bucket ACL using the request body,
then you can continue to use that approach.

```javascript
OWNER = extractTagValue(bucketsListResponse.body,"Owner")
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
+"</AccessControlPolicy>"
```

```javascript
aclUpdateResponse = client.put(aclPayload, generateHeadersAndQuery("PUT","/",aclPayload, {acl:""}))
```

```javascript
assert.equal( aclUpdateResponse.status, 200 )
```

This implementation of the GET operation uses the acl subresource to return the access control list (ACL)
of a bucket. To use GET to return the ACL of the bucket, you must have READ_ACP access to the bucket.
If READ_ACP permission is granted to the anonymous user, you can return the ACL of the bucket without
using an authorization header.

```javascript
aclResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{acl:""}))
```

```javascript
assert.equal( aclResponse.status, 200 )
```

This implementation of the DELETE operation deletes the bucket named in the URI. All objects (including all object versions and Delete Markers)
in the bucket must be deleted before the bucket itself can be deleted.

```javascript
bucketDeleteResponse = client.delete(null, generateHeadersAndQuery("DELETE","/"))
```

```javascript
assert.equal( bucketDeleteResponse.status, 204 )
```