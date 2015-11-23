---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7714/versions/7847/portal/pages/6431/preview
apiNotebookVersion: 1.1.66
title: Accounts, catalog and signature
---

```javascript
useSandbox = true
```

```javascript
partition = useSandbox ? "apisandbox-api" : "api"
```

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

We need a helper method to obtain next year

```javascript
function getNextYear(){
  var today = new Date();
  var yyyy = today.getFullYear();
  return yyyy+1;
}
```

```javascript
// Read about the Zuora at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7714/versions/7847/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7714/versions/7847/definition',
{
  baseUriParameters: {
    partitionName: partition
  }
});
```

```javascript
API.authenticate(client)
```

Creates a customer account.
This call creates a customer account with a credit-card payment method, a bill-to contact, and an optional sold-to contact. In the same operation, it can optionally create a subscription, invoice for that subscription, and collect payment through the default payment method. The transaction is atomic; if any part fails for any reason, the entire transaction is rolled back.

```javascript
nextYear = getNextYear()
accountResponse = client.accounts.post({
  "creditCard" : {
    "cardType" : "Visa" ,
    "expirationMonth" : 2 ,
    "securityCode" : "111" ,
    "cardNumber" : "4856200223544175" ,
    "expirationYear" : nextYear
  } ,
  "invoiceTargetDate" : "2013-12-31" ,
  "name" : "Mulesoft" ,
  "billCycleDay" : "15" ,
  "soldToContact" : {
    "country" : "USA" ,
    "lastName" : "Doe" ,
    "workEmail" : "john.doe@zoura.com" ,
    "address1" : "address1" ,
    "address2" : "address2" ,
    "firstName" : "John" ,
    "state" : "California" ,
    "city" : "San Francisco" ,
    "mobilePhone" : "14156789012"
  } ,
  "billToContact" : {
    "zipCode" : 11111 ,
    "country" : "USA" ,
    "lastName" : "Doe" ,
    "workEmail" : "jane.doe@zoura.com" ,
    "address1" : "address1" ,
    "address2" : "address2" ,
    "state" : "California" ,
    "firstName" : "Jane" ,
    "city" : "San Francisco" ,
    "mobilePhone" : "14156789012"
  } ,
  "invoiceCollect" : true ,
  "notes" : "Soho Networks" ,

  "paymentTerm" : "Net 30" ,
  "currency" : "USD"
})
```

```javascript
assert.equal(accountResponse.status,200)
assert(accountResponse.body.success)
accountId = accountResponse.body.accountId
```

Retrieves basic information about a specified customer account.

This is a quick retrieval that doesn't include the account's subscriptions, invoices, payments, or usage details. For more detailed information about an account, use the "Get account summary" call.

```javascript
accountResponse = client.accounts.accountKey(accountId).get()
```

```javascript
assert.equal(accountResponse.status, 200)
assert(accountResponse.body.success)
```

Updates the specified customer account

```javascript
newZipCode = "22222"
accountUpdateResponse = client.accounts.accountKey(accountId).put({
  "billToContact" : {
    "zipCode" : newZipCode ,
    "homePhone" : "9255559259"
  }
})
```

```javascript
assert.equal(accountUpdateResponse.status, 200)
assert(accountUpdateResponse.body.success)
```

Let's get ensured that the account has indeed been updated

```javascript
accountResponse = client.accounts.accountKey(accountId).get({})
assert.equal(accountResponse.body.billToContact.zipCode, newZipCode)
```

Retrieves detailed information about the specified customer account.

The response includes everything retrieved with the "Get basic account information" call, plus a summary of the account's subscriptions, invoices, payments, and usage for the last six months.

```javascript
accountSummaryResponse = client.accounts.accountKey(accountId).summary.get({})
```

```javascript
assert.equal(accountSummaryResponse.status, 200)
assert.equal(accountSummaryResponse.body.billToContact.zipCode, newZipCode)
```

Retrieves the entire product catalog, including all products and their corresponding rate plans and charges.

Products are returned in reverse chronological order on UpdatedDate. Information for each product includes rate plans and, within those, rate plan charges.

The REST API does not support the creation or updating of products, product rate plans and charges; these tasks can only be performed in the web-based UI or via the SOAP API.

```javascript
productsResponse = client.catalog.products.get({pageSize:5})
```

```javascript
assert.equal(productsResponse.status, 200)
assert(productsResponse.body.success)
```

Establishes a connection to the Zuora REST API service based on a valid user credentials.
This call authenticates the user and returns an API session cookie that's used to authorize subsequent calls to the REST API. A call to connections is a required first step before using the Zuora REST API to access data.
The credentials must belong to a user account that has permission to access the API service.

```javascript
connectionsResponse = client.connections.post({})
```

```javascript
assert.equal(connectionsResponse.status, 200)
assert(connectionsResponse.body.success)
```

Returns unique signature and token values, facilitating a CORS enabled API call

```javascript
signatureResponse = client("hmac-signatures").post({
  "uri" : "https://api.zuora.com/rest/v1/payment-methods/credit-cards" ,
  "method" : "POST" ,
  "accountKey" : accountId
}, {headers:{"Content-Type":"application/json"}})
```

```javascript
assert.equal(signatureResponse.status, 200)
assert(signatureResponse.body.success)
```