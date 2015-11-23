---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8120/versions/8316/portal/pages/7025/preview
apiNotebookVersion: 1.1.66
title: Objects part 2
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
OBJECT_KEY = "notebooktests/test-content.txt"
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

```javascript
function createCredential(date){return  "" +ACCESS_KEY +"/" + date.day + "/" + REGION + "/" + REGION_SERVICE + "/aws4_request" }
```

Auxiliary method for generating a policy

```javascript
function getPostPolicy(signDate){  
  var date = getCurrentDate(24*60*60*1000)
  var result = {
    "expiration": date.toPolicy,
    "conditions": [
      {"bucket": BUCKET_NAME.toLowerCase() },
      ["starts-with", "$key", ""],
      {"acl": "public-read"},      
      ["starts-with", "$Content-Type", ""],
      {"x-amz-credential": createCredential(signDate) },
      {"x-amz-algorithm": "AWS4-HMAC-SHA256"},
      {"x-amz-date": signDate.shortDate }
    ]
  }
  return result
}
```

Auxiliary method for obtaining current date

```javascript
function getCurrentDate(shift){
  //Sun, 11 Oct 2009 21:49:13 GMT
  var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  var date = new Date()
  if(shift!=undefined&&shift!=null&&Number.isInteger(shift)){
    date = new Date(date.getTime()+shift)
  }
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
  var toPolicy = "" + year + "-"+ month + "-" + dayOfMonth + "T" + h  + ":" + m + ":"+ s + ".000Z"//2013-08-01T12:00:00.000Z
  var long = "" + day + ", " + dayOfMonth + " " + monthWord + " " + year + " " + h + ":" + m + ":" + s + " GMT"
  var result = new Object()
  result.longDate = long
  result.shortDate = short
  result.day = "" + year + month + dayOfMonth
  result.toPolicy = toPolicy
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

Auxiliary method for generating a signing key

```javascript
function createSigningKey(date){
  var dateHash = CryptoJS.HmacSHA256(date.day,"AWS4" + SECRET_KEY)
  var dateRegionHash = CryptoJS.HmacSHA256(REGION,dateHash)
  var dateRegionServiceHash = CryptoJS.HmacSHA256(REGION_SERVICE,dateRegionHash)
  var signingKey = CryptoJS.HmacSHA256("aws4_request",dateRegionServiceHash)
  return signingKey
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

Auxiliary method which forms a valid multipart request

```javascript
function generateMultipartRequest(){
  
  var date = getCurrentDate()
  var policy = getPostPolicy(date)
  var policyString = JSON.stringify(policy)
  var policyBase64 = CryptoJS.enc.Utf8.parse(policyString).toString(CryptoJS.enc.Base64)
  
  var signingKey = createSigningKey(date)
  var signature = CryptoJS.HmacSHA256(policyBase64,signingKey).toString(CryptoJS.enc.Hex)
  
  var fd = new FormData()  
  fd.append("policy",policyBase64)
  fd.append("x-amz-algorithm","AWS4-HMAC-SHA256")
  fd.append("x-amz-credential",createCredential(date))
  fd.append("x-amz-date",date.shortDate)
  fd.append("x-amz-signature",signature)
  fd.append("acl","public-read")
  fd.append("Content-Type","text/plain")
  fd.append("key",OBJECT_KEY)  
  //fd.append("success_action_redirect","http://acl6.s3.amazonaws.com/successful_upload.html")
  return fd
}
```

Create a new bucket in the US Standard region

```javascript
client.put(null, generateHeadersAndQuery("PUT","/"))
```

The POST operation adds an object to a specified bucket using HTML forms. POST is an alternate form of PUT that enables browser-based
uploads as a way of putting objects in buckets. Parameters that are passed to PUT via HTTP Headers are instead passed as form fields to
POST in the multipart/form-data encoded message body. You must have WRITE access on a bucket to add an object to it. Amazon S3 never
stores partial objects: if you receive a successful response, you can be confident the entire object was stored.

Amazon S3 is a distributed system. If Amazon S3 receives multiple write requests for the same object simultaneously, all but the last
object written will be overwritten.

To ensure that data is not corrupted traversing the network, use the Content-MD5 form field. When you use the Content-MD5 form field,
Amazon S3 checks the object against the provided MD5 value. If they do not match, Amazon S3 returns an error. Additionally, you can
calculate the MD5 while posting an object to Amazon S3 and compare the returned ETag to the calculated MD5 value. The ETag only reflects
changes to the contents of an object, not its metadata.

```javascript
objectCreatePayload = generateMultipartRequest()
objectCreatePayload.append("file", new Blob([0,0,0,0,0,0,0,0,0]), OBJECT_KEY)
```

```javascript
objectCreateResponse = client.post(objectCreatePayload)
```

```javascript
assert.equal(objectCreateResponse.status,204)
```

Garbage collection. Delete the created object and the test bucket.

```javascript
client.objectName(OBJECT_KEY).delete(null,generateHeadersAndQuery("DELETE","/"+OBJECT_KEY))
```

```javascript
client.delete(null, generateHeadersAndQuery("DELETE","/"))
```