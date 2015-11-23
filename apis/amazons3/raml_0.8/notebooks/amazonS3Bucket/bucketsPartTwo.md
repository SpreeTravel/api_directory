---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8120/versions/8316/portal/pages/7022/preview
apiNotebookVersion: 1.1.66
title: Buckets part 2
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
  result.headers["Host"] = HOST.toLowerCase()
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

This implementation of the PUT operation uses the logging subresource to set the logging parameters for a bucket and to specify permissions for who can view and modify the logging parameters. To set the logging status of a bucket, you must be the bucket owner.

The bucket owner is automatically granted FULL_CONTROL to all logs. You use the Grantee request element to grant access to other people. The Permissions request element specifies the kind of access the grantee has to the logs.

```javascript
loggingPayload = 
 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
+"<BucketLoggingStatus xmlns=\"http://doc.s3.amazonaws.com/2006-03-01\">"
+"</BucketLoggingStatus>"
```

```javascript
loggingUpdateResponse = client.put(loggingPayload, generateHeadersAndQuery("PUT","/",loggingPayload, {logging:""}))
```

```javascript
assert.equal( loggingUpdateResponse.status, 200 )
```

This implementation of the GET operation uses the logging subresource to return the logging status of a bucket and the permissions users have to view and modify that status. To use GET, you must be the bucket owner.

```javascript
loggingResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{logging:""}))
```

```javascript
assert.equal( loggingResponse.status, 200 )
```

This implementation of the PUT operation uses the notification subresource to enable notifications of specified events for a bucket. Currently, the s3:ReducedRedundancyLostObject event is the only event supported for notifications. The s3:ReducedRedundancyLostObject event is triggered when Amazon S3 detects that it has lost all replicas of an object and can no longer service requests for that object.

If the bucket owner and Amazon SNS topic owner are the same, the bucket owner has permission to publish notifications to the topic by default. Otherwise, the owner of the topic must create a policy to enable the bucket owner to publish to the topic. For more information about creating this policy, go to Example Cases for Amazon SNS Access Control.

By default, only the bucket owner can configure notifications on a bucket. However, bucket owners can use a bucket policy to grant permission to other users to set this configuration with s3:PutBucketNotification permission.

After you call the PUT operation to configure notifications on a bucket, Amazon S3 publishes a test notification to ensure that the topic exists and that the bucket owner has permission to publish to the specified topic. If the notification is successfully published to the SNS topic, the PUT operation updates the bucket configuration and returns the 200 OK response with a x-amz-sns-test-message-id header containing the message ID of the test notification sent to topic.

To turn off notifications on a bucket, you specify an empty NotificationConfiguration element in your request: <NotificationConfiguration />

For more information about setting and reading the notification configuration on a bucket, see Setting Up Notification of Bucket Events. For more information about bucket policies, see Using Bucket Policies.

```javascript
notificationPayload = "<NotificationConfiguration></NotificationConfiguration>"
```

```javascript
notificationUpdateResponse = client.put(notificationPayload, generateHeadersAndQuery("PUT","/",notificationPayload, {notification:""}))
```

```javascript
assert.equal( notificationUpdateResponse.status, 200 )
```

This implementation of the GET operation uses the notification subresource to return the notification configuration of a bucket. Currently, the s3:ReducedRedundancyLostObject event is the only event supported for notifications. The s3:ReducedRedundancyLostObject event is triggered when Amazon S3 detects that it has lost all replicas of a Reduced Redundancy Storage object and can no longer service requests for that object.
If notifications are not enabled on the bucket, the operation returns an empty NotificatonConfiguration element.
By default, you must be the bucket owner to read the notification configuration of a bucket. However, the bucket owner can use a bucket policy to grant permission to other users to read this configuration with the s3:GetBucketNotification permission.
For more information about setting and reading the notification configuration on a bucket, see Setting Up Notification of Bucket Events. For more information about bucket policies, see Using Bucket Policies.

```javascript
notificationResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{notification:""}))
```

```javascript
assert.equal( notificationResponse.status, 200 )
```

This implementation of the PUT operation uses the requestPayment subresource to set the request payment configuration of a bucket. By default, the bucket owner pays for downloads from the bucket. This configuration parameter enables the bucket owner (only) to specify that the person requesting the download will be charged for the download. For more information, see Requester Pays Buckets

```javascript
requestPaymentPayload =
 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
+"<RequestPaymentConfiguration xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">"
+  "<Payer>BucketOwner</Payer>"
+"</RequestPaymentConfiguration>"
```

```javascript
requestPaymentUpdateResponse = client.put(requestPaymentPayload, generateHeadersAndQuery("PUT","/",requestPaymentPayload, {requestPayment:""}))
```

```javascript
assert.equal( requestPaymentUpdateResponse.status, 200 )
```

This implementation of the GET operation uses the requestPayment subresource to return the request payment configuration of a bucket. To use this version of the operation, you must be the bucket owner. For more information, see Requester Pays Buckets.

```javascript
requestPaymentResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{requestPayment:""}))
```

```javascript
assert.equal( requestPaymentResponse.status, 200 )
```

Sets the configuration of the website that is specified in the website subresource. To configure a bucket as a website, you can add this subresource on the bucket with website configuration information such as the file name of the index document and any redirect rules. For more information, go to Hosting Websites on Amazon S3 in the Amazon Simple Storage Service Developer Guide.

This PUT operation requires the S3:PutBucketWebsite permission. By default, only the bucket owner can configure the website attached to a bucket; however, bucket owners can allow other users to set the website configuration by writing a bucket policy that grants them the S3:PutBucketWebsite permission.

```javascript
websitePayload =
 "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
+"<WebsiteConfiguration xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">"
+  "<IndexDocument>"
+    "<Suffix>index.html</Suffix>"
+  "</IndexDocument>"
+  "<ErrorDocument>"
+    "<Key>404.html</Key>"
+  "</ErrorDocument>"
+"</WebsiteConfiguration>"
```

```javascript
websiteUpdateResponse = client.put(websitePayload, generateHeadersAndQuery("PUT","/",websitePayload, {website:""}))
```

```javascript
assert.equal( websiteUpdateResponse.status, 200 )
```

This implementation of the GET operation returns the website configuration associated with a bucket.
To host website on Amazon S3, you can configure a bucket as website by adding a website configuration.

This GET operation requires the S3:GetBucketWebsite permission. By default, only the bucket owner can read
the bucket website configuration. However, bucket owners can allow other users to read the website configuration
by writing a bucket policy granting them the S3:GetBucketWebsite permission.

```javascript
websiteResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{website:""}))
```

```javascript
assert.equal( websiteResponse.status, 200 )
```

This operation removes the website configuration for a bucket. Amazon S3 returns a 200 OK response upon
successfully deleting a website configuration on the specified bucket. You will get a 200 OK response if
the website configuration you are trying to delete does not exist on the bucket. Amazon S3 returns a 404
response if the bucket specified in the request does not exist.

This DELETE operation requires the S3:DeleteBucketWebsite permission. By default, only the bucket owner
can delete the website configuration attached to a bucket. However, bucket owners can grant other users
permission to delete the website configuration by writing a bucket policy granting them the
S3:DeleteBucketWebsite permission.

```javascript
websiteDeleteResponse = client.delete(null, generateHeadersAndQuery("DELETE","/",null,{website:""}))
```

```javascript
assert.equal( websiteDeleteResponse.status, 204 )
```

This implementation of the PUT operation uses the policy subresource to add to or replace a policy on a bucket. If the
bucket already has a policy, the one in this request completely replaces it. To perform this operation,
you must be the bucket owner.

If you are not the bucket owner but have PutBucketPolicy permissions on the bucket, Amazon S3 returns a 405 Method Not Allowed.
In all other cases for a PUT bucket policy request that is not from the bucket owner, Amazon S3 returns 403 Access Denied.
There are restrictions about who can create bucket policies and which objects in a bucket they can apply to.

```javascript
AWS_ACCOUNT_ID = prompt("Please, enter your AWS Account ID. It can be found at\n\nhttps://console.aws.amazon.com/iam/home?#security_credential\n\n in the \"Account Identifiers\" section.")
```

```javascript
policyPayload = {
  "Version":"2008-10-17",
  "Id":"api-notebook-test-policy",
  "Statement" : [
    {
      "Effect" : "Allow",
      "Sid" : "1", 
      "Principal" : {
        "AWS" : [ "arn:aws:iam::" + AWS_ACCOUNT_ID.replace(/-/g,'') + ":root" ]
      },
      "Action" : [ "s3:ListBucket" ],
      "Resource" : "arn:aws:s3:::" + BUCKET_NAME.toLocaleLowerCase()
    }
  ] 
}
```

```javascript
policyUpdateResponse = client.put(policyPayload, generateHeadersAndQuery("PUT","/",JSON.stringify(policyPayload), {policy:""}))
```

```javascript
assert.equal( policyUpdateResponse.status, 204 )
```

This implementation of the GET operation uses the policy subresource to return the policy of a specified bucket. To use this operation,
you must have GetPolicy permissions on the specified bucket, and you must be the bucket owner.

If you don't have GetPolicy permissions, Amazon S3 returns a 403 Access Denied error. If you have the correct permissions, but you're
not the bucket owner, Amazon S3 returns a 405 Method Not Allowed error. If the bucket does not have a policy, Amazon S3 returns a 404
Policy Not found error. There are restrictions about who can create bucket policies and which objects in a bucket they can apply to.

```javascript
policyResponse = client.get(null, generateHeadersAndQuery("GET","/",null,{policy:""}))
```

```javascript
assert.equal( policyResponse.status, 200 )
```

This implementation of the DELETE operation uses the policy subresource to delete the policy on a specified bucket.
To use the operation, you must have DeletePolicy permissions on the specified bucket and be the bucket owner.

If you do not have DeletePolicy permissions, Amazon S3 returns a 403 Access Denied error. If you have the correct permissions,
but are not the bucket owner , Amazon S3 returns a 405 Method Not Allowed error. If the bucket doesn't have a policy, Amazon S3
returns a 204 No Content error. There are restrictions about who can create bucket policies and which objects in a bucket they can apply to.

```javascript
policyDeleteResponse = client.delete(null, generateHeadersAndQuery("DELETE","/",null,{policy:""}))
```

```javascript
assert.equal( policyDeleteResponse.status, 204 )
```

Garbage collection. Delete the test bucket.

```javascript
client.delete(null, generateHeadersAndQuery("DELETE","/"))
```