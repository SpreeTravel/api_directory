---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7714/versions/7847/portal/pages/6432/preview
apiNotebookVersion: 1.1.66
title: Subscriptions
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

This helper method returns a _Product Rate Plan_ which can be used to create a subscription.

```javascript
function getSomeProductRatePlan(){
  
  var page = 1
  while(true){
    var productsResponse = client.catalog.products.get({pageSize:20,page:page++})
    if(!productsResponse.body.success) {
      break
    }
    var products = productsResponse.body.products
    if(products.length==0){
      break
    }
    for(var i in products ){
      var product = products[i]
      var productRatePlans = product.productRatePlans
      for(var j in productRatePlans){
        var productRatePlan = productRatePlans[j]
        var productRatePlanCharges = productRatePlan.productRatePlanCharges
        if(productRatePlanCharges && productRatePlanCharges.length!=0){
          
          var subscriptionResponse = client.subscriptions.post({
            "renewalTerm" : "3" ,
            "termType" : "TERMED" ,
            "autoRenew" : true ,
            "invoiceTargetDate" : productRatePlan.effectiveStartDate ,
            "accountKey" : accountId ,
            "contractEffectiveDate" : productRatePlan.effectiveStartDate ,
            "subscribeToRatePlans" : [
              {
                "productRatePlanId" : productRatePlan.id
              }
            ] ,
            "invoiceCollect" : false ,
            "initialTerm" : "12" ,
            "notes" : "Test POST subscription from API Notebook"
          })
          if(subscriptionResponse.body.success){
            return productRatePlan
          }
        }
      }
    }
  }  
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
  "currency" : "USD"
})
accountId = accountResponse.body.accountId
```

```javascript
productRatePlan = getSomeProductRatePlan()
```

```javascript
subscriptionResponse = client.subscriptions.post({
  "renewalTerm" : "3" ,
  "termType" : "TERMED" ,
  "autoRenew" : true ,
  "invoiceTargetDate" : productRatePlan.effectiveStartDate ,
  "accountKey" : accountId ,
  "contractEffectiveDate" : productRatePlan.effectiveStartDate ,
  "subscribeToRatePlans" : [
    {
      "productRatePlanId" : productRatePlan.id
    }
  ] ,
  "invoiceCollect" : false ,
  "initialTerm" : "12" ,
  "notes" : "Test POST subscription from API Notebook"
})
```

Let's pick one of the _Product Rate Plan Charges_ of the selected _Product Rate Plan_

```javascript
productRatePlanCharge = productRatePlan.productRatePlanCharges[0]
```

Creates a new subscription for an existing customer account

```javascript
subscriptionResponse = client.subscriptions.post({
  "renewalTerm" : "3" ,
  "termType" : "TERMED" ,
  "autoRenew" : true ,
  "invoiceTargetDate" : productRatePlan.effectiveStartDate ,
  "accountKey" : accountId ,
  "contractEffectiveDate" : productRatePlan.effectiveStartDate ,
  "subscribeToRatePlans" : [
    {
      "productRatePlanId" : productRatePlan.id
    }
  ] ,
  "invoiceCollect" : false ,
  "initialTerm" : "12" ,
  "notes" : "Test POST subscription from API Notebook"
})
```

```javascript
assert.equal(subscriptionResponse.status, 200)
assert(subscriptionResponse.body.success)
subscriptionId = subscriptionResponse.body.subscriptionId
```

Retrieves all subscriptions associated with the specified account.
Subscription data is returned in reverse chronological order based on updatedDate.

```javascript
accountSubscriptionsResponse = client.subscriptions.accounts.accountKey(accountId).get({})
```

```javascript
assert.equal(accountSubscriptionsResponse.status, 200)
assert(accountSubscriptionsResponse.body.success)
assert(accountSubscriptionsResponse.body.subscriptions.length>0)
```

Creates a new subscription in preview mode.
This call does not require a valid customer account. It can be used to show potential new customers a preview of a subscription with complete details and charges before creating an account, or to let existing customers preview a subscription with all charges before committing.

```javascript
subscriptionPreviewResponse = client.subscriptions.preview.post({
  "termType" : "TERMED" ,
  "invoiceTargetDate" : productRatePlan.effectiveStartDate ,
  "contractEffectiveDate" : productRatePlan.effectiveStartDate ,
  "accountKey" : accountId ,
  "subscribeToRatePlans" : [
    {
      "productRatePlanId" : productRatePlan.id
    }
  ] ,
 
  "initialTerm" : 12
})
```

```javascript
assert.equal(subscriptionPreviewResponse.status, 200)
assert(subscriptionPreviewResponse.body.success)
```

Retrieves detailed information about a specified subscription

```javascript
subscriptionResponse = client.subscriptions.subscriptionKey(subscriptionId).get({})
```

```javascript
assert.equal(subscriptionResponse.status, 200)
assert.equal(subscriptionResponse.body.id, subscriptionId)
```

Updates a subscription.
This call can make the following kinds of changes to a subscription:

Add a note

Change the renewal term or auto-renewal flag

Change the term length or change between evergreen and termed

Add a new product rate plan

Remove an existing subscription rate plan

Change the quantity of an existing subscription rate plan

```javascript
subscriptionUpdateResponse = client.subscriptions.subscriptionKey(subscriptionId).put({
  "renewalTerm" : "4" ,
  "termType" : "TERMED" ,
  "autoRenew" : false ,
  "currentTerm" : "10" ,
  "update" : [
    {
      "ratePlanId" : productRatePlan.id,
      "contractEffectiveDate" : productRatePlan.effectiveStartDate,
      "chargeUpdateDetails" : [
        {
          "ratePlanChargeId" : productRatePlanCharge.id,
          "quantity" : 12
        }
      ]
    }
  ] ,
  "notes" : "Test UPDATE subscription from RAML API Notebook" ,
})
```

```javascript
assert.equal(subscriptionUpdateResponse.status, 200)
assert(subscriptionUpdateResponse.body.success||subscriptionUpdateResponse.body.reasons.length>0)
```

Renews a termed subscription using existing renewal terms

```javascript
subscriptionRenewResponse = client.subscriptions.subscriptionKey(subscriptionId).renew.put({
  "invoiceCollect" : false ,
  "invoiceTargetDate" : "2013-04-28"
})
```

```javascript
assert.equal(subscriptionRenewResponse.status, 200)
assert(subscriptionRenewResponse.body.success||subscriptionRenewResponse.body.reasons.length>0)
```

Cancels an active subscription

```javascript
subscriptionCancellationResponse = client.subscriptions.subscriptionKey(subscriptionId).cancel.put({
  "cancellationPolicy" : "EndOfCurrentTerm" ,
  "invoiceCollect" : false ,
  "cancellationEffectiveDate" : productRatePlan.effectiveEndDate
})
```

```javascript
assert.equal(subscriptionCancellationResponse.status, 200)
assert(subscriptionCancellationResponse.body.success||subscriptionCancellationResponse.body.reasons.length>0)
```