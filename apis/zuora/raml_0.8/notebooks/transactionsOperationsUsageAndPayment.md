---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7714/versions/7847/portal/pages/6433/preview
apiNotebookVersion: 1.1.66
title: Transactions, operations, usage and payment
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

We need helper methods to obtain next year and current date

```javascript
function getNextYear(){
  var today = new Date();
  var yyyy = today.getFullYear();
  return yyyy+1;
}

function getCurrentDate(){
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  
  var yyyy = today.getFullYear();
  if(dd<10){dd='0'+dd}
  if(mm<10){mm='0'+mm}
  today = yyyy+'-'+mm+"-"+dd;
  return today;
}

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
  "currency" : "USD"})
accountId = accountResponse.body.accountId
```

Retrieves invoices for a specified account.
Invoices are returned in reverse chronological order by "invoiceDate".

```javascript
invoicesResponse = client.transactions.invoices.accounts.accountKey(accountId).get({})
```

```javascript
assert.equal(invoicesResponse.status, 200)
assert(invoicesResponse.body.success||invoicesResponse.body.reasons.length>0)
```

Retrieves payments for a specified account.

```javascript
paymentsResponse = client.transactions.payments.accounts.accountKey(accountId).get({})
```

```javascript
assert.equal(paymentsResponse.status, 200)
assert(paymentsResponse.body.success||paymentsResponse.body.reasons.length>0)
```

Generates invoices and collects payments for a specified account.
This method can generate invoices and collect payments on the invoices generated, or else simply collect payment on a specified existing invoice. The customer's default payment method is used, and the full amount due is collected. The operation depends on the parameters you specify:

To generate one or more new invoices for that customer and collect payment on the generated invoice(s), leave the invoiceId field empty.

To collect payment on an existing invoice, specify the invoice ID.

The operation is atomic; if any part is unsuccessful, the entire operation is rolled back.

```javascript
invoiceAndCollectResponse = client("operations/invoice-collect").post({
  "accountKey" : accountId ,
  "invoiceDate" : getCurrentDate() ,
  "invoiceTargetDate" : getCurrentDate()
})
```

```javascript
assert.equal(invoiceAndCollectResponse.status, 200)
assert(invoiceAndCollectResponse.body.success||invoiceAndCollectResponse.body.reasons.length>0)
```

Imports usage data for one or more accounts in CSV or XLS format.
There are no path or query parameters. The data is uploaded using the HTTP multipart/form-data POST method and applied to the user's tenant. See the example below for more information.
The content of the upload file must follow the field format used by the UI import tool, as it is documented below and in the KnowledgeCenter.  It must be a comma-separated (CSV) or Excel (XLS) file with a corresponding .csv or .xls extension.  The file size must not exceed 4MB.  Zipped files with .zip extension are acceptable, as long as they are within the size limit and contain only a single acceptable CSV or XLS file.
At the completion of the upload, before actually processing the file contents, the API returns a response containing the byte count of the received file and a URL for checking the status of the import process.  Of the five possible results displayed at that URL (Pending, Processing, Completed, Canceled, and Failed) only a Completed status indicates that the import was successful.  The operation is atomic; if any record fails, the file is rejected.  In that case, the entire import is rolled back and all stored data is returned to its original state.
To manage the information after a successful upload, use the web-based UI.

Upload file format

The upload file uses the following headings:

|Heading| Description |
|---|---|
|ACCOUNT_ID|  Account ID|
|UOM|  Units of measure |
|QTY|  Quantity of units used|
|STARTDATE|  Start date of usage|
|ENDDATE|  End date of usage |
|SUBSCRIPTION_ID|  Subscription ID|
|CHARGE_ID|  Charge ID|
|DESCRIPTION|  Description|

```javascript
usagePostResponse = client.usage.post({
  "filename" : "/home/user/NetBeansProjects/zuora.rest.ruby/samples/sample_usage.csv"
})
```

```javascript
assert.equal(usagePostResponse.status, 200)
assert(usagePostResponse.body.success||usagePostResponse.body.reasons.length>0)
```

Retrieves usage details for an account.
Usage data is returned in reverse chronological order.

```javascript
accountUsageResponse = client.usage.accounts.accountKey(accountId).get({})
```

```javascript
assert.equal(accountUsageResponse.status, 200)
assert(accountUsageResponse.body.success)
```

Creates a new credit card payment method for the specified customer account

```javascript
paymentMethodResponse = client("payment-methods/credit-cards").post({
  "defaultPaymentMethod" : false ,
  "cardHolderInfo" : {
    "addressLine1" : "77 Fallon Glen" ,
    "addressLine2" : "" ,
    "zipCode" : "94020" ,
    "state" : "California" ,
    "phone" : "4155551234" ,
    "country" : "USA" ,
    "cardHolderName" : "Bill Thiebault" ,
    "city" : "Fremont" ,
    "email" : "bill@testaddress.com"
  } ,
  "expirationMonth" : "10" ,
  "accountKey" : accountId ,
  "creditCardType" : "Discover" ,
  "expirationYear" : getNextYear() ,
  "creditCardNumber" : "4856200223544175" ,
  "securityCode" : "123"
},{headers:{"Content-Type":"application/json"}})
```

```javascript
assert.equal(paymentMethodResponse.status, 200)
assert(paymentMethodResponse.body.success)
paymentMethodId = paymentMethodResponse.body.paymentMethodId
```

Retrieves all credit card information for the specified customer account.

The response includes details on all the credit or debit cards for the specified customer account. Card numbers are masked, e.g., "************1234". Cards are returned in reverse chronological order of last update.

```javascript
paymentMethodsResponse = client("payment-methods/credit-cards/accounts/{accountKey}",{accountKey:accountId}).get({})
```

```javascript
assert.equal(paymentMethodsResponse.status, 200)
assert(paymentMethodsResponse.body.success)
assert(paymentMethodsResponse.body.creditCards.length>0)
```

Updates an existing credit card payment method for the specified customer account

```javascript
paymentMethodUpdateResponse = client("payment-methods/credit-cards/{paymentMethodId}",{paymentMethodId:paymentMethodId}).put({
  "expirationMonth" : 8 ,
  "expirationYear" : 2015 ,
  "cardHolderName" : "Leo" ,
  "securityCode" : "111"
})
```

```javascript
assert.equal(paymentMethodUpdateResponse.status, 200)
assert(paymentMethodUpdateResponse.body.success||paymentMethodUpdateResponse.body.reasons.length>0)
```

Deletes a credit card payment method from the specified customer account.
If the specified payment method is the account's default payment method, the request will fail.  In that case, you must first designate a different payment method for that customer to be the default.

```javascript
paymentMethodDeleteResponse = client("payment-methods/{paymentMethodId}",{paymentMethodId:paymentMethodId}).delete()
```

```javascript
assert.equal(paymentMethodDeleteResponse.status, 200)
assert(paymentMethodDeleteResponse.body.success)
```