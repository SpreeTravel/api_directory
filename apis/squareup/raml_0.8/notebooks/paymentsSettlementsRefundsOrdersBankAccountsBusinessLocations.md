---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#7bbd2752e1fdcb75c48d
apiNotebookVersion: 1.1.66
title: Squareup API Notebook (payments, settlements, refunds, orders, bank accounts, business, locations)
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
CLIENT_ID = prompt("Please, enter clientId of your Squareup application.")
CLIENT_SECRET = prompt("Please, enter clientSecret of your Squareup application.")
```

```javascript
// Read about the Squareup API at http://api-portal.anypoint.mulesoft.com/onpositive/api/squareup-api
API.createClient('client', 'http://api-portal.anypoint.mulesoft.com/onpositive/api/squareup-api/Squareup.raml');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Square-issued ID. If you haven't, specify 'me' for the Connect API to automatically determine your merchant ID based on your request's access token.

```javascript
MERCHANT_ID = "me"
```


Provides summary information for all payments taken by a merchant or any of the merchant's mobile staff during a date range

```javascript
paymentsResponse = client.merchant_id(MERCHANT_ID).payments.get()
```

```javascript
assert.equal( paymentsResponse.status, 200 )
```

The payment's Square-issued ID. You obtain this value from Payment objects returned by the List Payments endpoint.

```javascript
PAYMENT_ID = paymentsResponse.body.length ==0 ? null: paymentsResponse.body[0].id 
```

Provides comprehensive information for a single payment

```javascript
if (PAYMENT_ID){
  singlePaymentInfoResponse = client.merchant_id(MERCHANT_ID).payments.payment_id(PAYMENT_ID).get()
}
```

```javascript
if (PAYMENT_ID){
  assert.equal( singlePaymentInfoResponse.status, 200 )
}
```

Provides summary information for all deposits and withdrawals initiated by Square to a merchant's bank account during a date range

```javascript
settlementsResponse = client.merchant_id(MERCHANT_ID).settlements.get()
```

```javascript
assert.equal( settlementsResponse.status, 200 )
```

The settlement's Square-issued ID. You obtain this value from Settlement objects returned by the List Settlements endpoint.

```javascript
SETTLEMENT_ID = settlementsResponse.body.length==0?null:settlementsResponse.body[0].id
```

The bank account's Square-issued ID. You obtain this value from Settlement objects returned by the List Settlements endpoint.

```javascript
BANK_ACCOUNT_ID = settlementsResponse.body.length == 0?null:settlementsResponse.body[0].bank_account_id

```

Provides comprehensive information for a single settlement, including the entries that contribute to the settlement's total

```javascript
if(SETTLEMENT_ID){
  settlementInfoResponse = client.merchant_id(MERCHANT_ID).settlements.settlement_id(SETTLEMENT_ID).get()
}
```

```javascript
if(SETTLEMENT_ID){
  assert.equal( settlementInfoResponse.status, 200 )
}
```

Provides the details for all refunds initiated by a merchant or any of the merchant's mobile staff during a date range. Date ranges cannot exceed one year in length. 

```javascript
refundsResponse = client.merchant_id(MERCHANT_ID).refunds.get()
```

```javascript
assert.equal( refundsResponse.status, 200 )
```

Issues a refund for a previously processed payment. You must issue a refund within 60 days of the associated payment.

```javascript
if (PAYMENT_ID){
  refundsCreateResponse = client.merchant_id(MERCHANT_ID).refunds.post({
  "payment_id" : PAYMENT_ID ,
  "type" : "PARTIAL" ,
  "reason" : "Returned Goods" ,
  "refunded_money" : {
    "amount" : 500 ,
    "currency_code" : "USD"
  } ,
  "request_idempotence_key" : "1"
})
}
```

```javascript
if (PAYMENT_ID){assert.equal( 
  refundsCreateResponse.status, 200 )
 }
```

Delete endpoints

```javascript
if (refundsResponse.body.length != 0) {
  refundsDeleteResponse = client.merchant_id(MERCHANT_ID).refunds.delete()
}
```

```javascript
if (refundsResponse.body.length != 0) {
assert.equal( refundsDeleteResponse.status, 200 )
}
```

Provides summary information for a merchant's Square Market orders.

```javascript
ordersResponse = client.merchant_id(MERCHANT_ID).orders.get()
```

```javascript
assert.equal( ordersResponse.status, 200 )
```

The order's Square-issued ID. You obtain this value from Order objects returned by the List Orders endpoint.

```javascript
ORDER_ID = ordersResponse.body.length == 0?null: ordersResponse.body[0].id 
```

Provides comprehensive information for a single Square Market order, including the order's history

```javascript
if(ORDER_ID){
  orderResponse = client.merchant_id(MERCHANT_ID).orders.order_id(ORDER_ID).get()
}
```

```javascript
if(ORDER_ID){
  assert.equal( orderResponse.status, 200 )
}
```

Updates the details of a Square Market order

```javascript
if(ORDER_ID){
orderUpdateResponse = client.merchant_id(MERCHANT_ID).orders.order_id(ORDER_ID).put()
}
```

```javascript
if(ORDER_ID){
assert.equal( orderUpdateResponse.status, 200 )
}
```

Provides non-confidential details for all of a merchant's associated bank accounts

```javascript
bankAccountsResponse = client.merchant_id(MERCHANT_ID)["bank-accounts"].get()
```

```javascript
assert.equal( bankAccountsResponse.status, 200 )
```

Provides non-confidential details for a merchant's associated bank account

```javascript
if (BANK_ACCOUNT_ID){
  bankAccountResponse = client.merchant_id(MERCHANT_ID)["bank-accounts"].bank_account_id(BANK_ACCOUNT_ID).get()
}
```

```javascript
if (BANK_ACCOUNT_ID){
assert.equal( bankAccountResponse.status, 200 )
}
```

Return object containing the business account's profile information.

Returns an error if the single-location account does not have an associated business account.

```javascript
businessResponse = client.merchant_id(MERCHANT_ID).business.get()
```

```javascript
assert.equal( businessResponse.status, 200 )
```

Retrieve an array of Merchant objects containing profile information for the business's locations.

Returns an error if the single-location account does not have an associated business account.

```javascript
locationsResponse = client.merchant_id(MERCHANT_ID).locations.get()
```

```javascript
assert.equal( locationsResponse.status, 200 )
```