---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7661/versions/7789/portal/pages/6378/preview
apiNotebookVersion: 1.1.66
title: Transfers, recepients, balance, events, tokens
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
var assert = chai.assert
```

Name of a recepient which notebook operates with. 

```javascript
recepientName = "TEST_NOTEBOOK RECEPIENT"
```

Id of a plan which notebook operates with.

```javascript
planId = "TEST_NOTEBOOK_PLAN"
```

Id of a coupon which notebook operates with.

```javascript
couponId = "TEST_NOTEBOOK_COUPON"
```

Email of a customer which notebook operates with.

```javascript
customerEmail = "customer@somehost.com"
```

Email of a recepient which notebook operates with.

```javascript
recipientEmail = "recepient@somehost.com"
```

A helper method to obtain next year

```javascript
function getNextYear(){
  var today = new Date()
  var yyyy = today.getFullYear()
  return yyyy + 1 
}
```

```javascript
clientId = prompt("Please, enter your Client Id.")
secretKey = prompt("Please, enter your API Secret Key.")
```

```javascript
// Read about the Stripe RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7661/versions/7789/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7661/versions/7789/definition');
```

```javascript
API.authenticate(client, "oauth_2_0",{
  clientId: clientId,
  clientSecret: secretKey,
  scopes: [ "read_write" ]
})
```

Returns a list of your recipients. The recipients are returned sorted by
creation date, with the most recently created recipient appearing first.

```javascript
recipientsResponse = client.recipients.get({},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( recipientsResponse.status, 200, "Error" )
```

Delete a recepient which could have been created during previous notebook runs.

```javascript
for(var ind in recipientsResponse.body.data){
  var recipient = recipientsResponse.body.data[ind]
  if(recipient.email==recipientEmail){
    client.recipients.recipient_ID(recipient.id).delete({},{headers:{"Authorization":"Bearer " + secretKey}})
  }
}
```

Creates a new recipient object and verifies both the recipient identity and
bank account information.

```javascript
recipientsResponse = client.recipients.post({
  name: recepientName,
  email: recipientEmail,
  type: "individual",
  "bank_account[country]": "US",
  "bank_account[routing_number]": "051000017",
  "bank_account[account_number]": "000123456789"
},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( recipientsResponse.status, 200, "Error" )
recipientId = recipientsResponse.body.id
```

Retrieves the details of an existing recipient. You need only supply the
unique recipient identifier that was returned upon recipient creation.

```javascript
recipientResponse = client.recipients.recipient_ID(recipientId).get({},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( recipientResponse.status, 200, "Error" )
```

Updates the specified recipient by setting the values of the parameters
passed. Any parameters not provided will be left unchanged.
If you update the name or tax ID, the identity verification will automatically
be rerun. If you update the bank account, the bank account validation will
automatically be rerun.

```javascript
recipientUpdateResponse = client.recipients.recipient_ID(recipientId).post({
  description: "This is a test API Notebook recepient"
},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( recipientUpdateResponse.status, 200, "Error" )
```

Returns a list of existing transfers sent to third-party bank accounts or
that Stripe has sent you. The transfers are returned in sorted order, with
the most recent transfers appearing first.

```javascript
transfersResponse = client.transfers.get()
```

```javascript
assert.equal( transfersResponse.status, 200, "Error" )
```

To send funds from your Stripe account to a third-party bank account, you
create a new transfer object. Your Stripe balance must be able to cover
the transfer amount, or you'll receive an "Insufficient Funds" error.
If your API key is in test mode, money won't actually be sent, though
everything else will occur as if in live mode.

```javascript
transferCreateResponse = client.transfers.post({
  amount: 100,
  currency: "usd",
  recipient: recipientId
},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( transferCreateResponse.status, 200, "Error" )
transferId = transferCreateResponse.body.id
```

Retrieves the details of an existing transfer. Supply the unique transfer ID
from either a transfer creation request or the transfer list, and Stripe will
return the corresponding transfer information.

```javascript
transferResponse = client.transfers.transfer_ID(transferId).get({},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( transferResponse.status, 200, "Error" )
```

Updates the specified transfer by setting the values of the parameters passed. Any parameters not provided will be left unchanged

```javascript
transferUpdateResponse = client.transfers.transfer_ID(transferId).post({
  description: "Test API Notebook transfer"
},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( transferUpdateResponse.status, 200, "Error" )
```

Cancels a transfer that has previously been created. Funds will be refunded
to your available balance, and the fees you were originally charged on the
transfer will be refunded. You may not cancel transfers that have already
been paid out, or automatic Stripe transfers.

```javascript
/*
It is possible to cancel only those transfers which have 'status'=='pending'.
The newly created transfer, returned by POST /transfers, really satisfies this condition, but in test mode transfer status is changed too quickly.
The same transfer, returned by GET /transfers/{TRANSFER_ID} a second later has it's 'status'=='paid'.
Thus, the cancel method returns an error:
"Transfer cannot be canceled, because it has already been submitted. You can currently only cancel pending transfers."
*/
//transferCancelResponse = client.transfers.transfer_ID(transferId).cancel.post()
```

```javascript
//assert.equal( transferCancelResponse.status, 200, "Error" )
```

List events, going back (at least) up to 30 days

```javascript
eventsResponse = client.events.get()
```

```javascript
assert.equal( eventsResponse.status, 200, "Error" )
eventId = eventsResponse.body.data[0].id
```

Retrieves the details of an event. Supply the unique identifier of the
event, which you might have received in a webhook.

```javascript
eventResponse = client.events.EVENT_ID(eventId).get()
```

```javascript
assert.equal( eventResponse.status, 200, "Error" )
```

Retrieves the current account balance, based on the API key that was used to
make the request.

```javascript
balanceResponse = client.balance.get()
```

```javascript
assert.equal( balanceResponse.status, 200, "Error" )
```

Returns a list of transactions that have contributed to the Stripe accoun
balance (includes charges, refunds, transfers, and so on). The transactions
are returned in sorted order, with the most recent transactions appearing
first.

```javascript
balanceHistoryResponse = client.balance.history.get()
```

```javascript
assert.equal( balanceHistoryResponse.status, 200, "Error" )
transactionId = balanceHistoryResponse.body.data[0].id
```

Retrieves the balance transaction with the given ID

```javascript
transactionResponse = client.balance.history.TRANSACTION_ID(transactionId).get()
```

```javascript
assert.equal( transactionResponse.status, 200, "Error" )
```

```javascript
cardObject = {
  "card[exp_month]": 8,
  "card[exp_year]": getNextYear(),
  "card[number]": 4242424242424242
}
```

Creates a single use token that wraps the details of a credit card. This
token can be used in place of a credit card dictionary with any API method.
These tokens can only be used once: by creating a new charge object, or
attaching them to a customer.

```javascript
tokenCreateResponse = client.tokens.post(cardObject)
```

```javascript
assert.equal( tokenCreateResponse.status, 200, "Error" )
tokenId = tokenCreateResponse.body.id
```

Retrieves the token with the given ID

```javascript
tokenResponse = client.tokens.ID(tokenId).get()
```

```javascript
assert.equal( tokenResponse.status, 200, "Error" )
```

At this point we need a customer with an active card. Let's first delete a recepient which could have been created during previous notebook runs.

```javascript
customersResponse = client.customers.get()
for(var ind in customersResponse.body.data){
  var customer = customersResponse.body.data[ind]
  if(customer.email==customerEmail){
    client.customers.CUSTOMER_ID(customer.id).delete()
  }
}
```

Now let's create a customer and a card for him.

```javascript
customerCreateResponse = client.customers.post({
  email: customerEmail,
})
customerId = customerCreateResponse.body.id

cardCreateResponse = client.customers.CUSTOMER_ID(customerId).cards.post( {
  card: tokenResponse.body.id
})
```

Returns a list of application fees you've previously collected. The application fees are returned in sorted order, with the most recent fees appearing first

```javascript
application_feesResponse = client.application_fees.get()
```

```javascript
assert.equal( application_feesResponse.status, 200, "Error" )
```

Create charge

```javascript
chargeCreateResponse = client.charges.post({
  amount: 50,
  currency: "usd",
  customer: customerId,
  application_fee: 29  
})
```

```javascript
application_feesResponse = client.application_fees.get({},{headers:{"Authorization":"Bearer " + secretKey}})
feeId = application_feesResponse.body.data[0].id
```

Retrieves the details of an application fee that your account has collected. The same information is returned when refunding the application fee

```javascript
feeResponse = client.application_fees.FEE_ID(feeId).get({},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( feeResponse.status, 200, "Error" )
```

Refunds an application fee that has previously been collected but not yet refunded. Funds will be refunded to the Stripe account that the fee was originally collected from.
You can optionally refund only part of an application fee. You can do so as many times as you wish until the entire fee has been refunded.
Once entirely refunded, an application fee can't be refunded again. This method will return an error when called on an already-refunded application fee, or when trying to refund more money than is left on an application fee.

```javascript
refundResponse = client.application_fees.FEE_ID(feeId).refund.post({},{headers:{"Authorization":"Bearer " + secretKey}})
```

```javascript
assert.equal( refundResponse.status, 200, "Error" )
```

Permanently deletes a recipient. It cannot be undone.

```javascript
recepientDeleteResponse = client.recipients.recipient_ID(recipientId).delete({},{
  headers: {
    "Authorization": "Bearer " + secretKey,
    "Content-Type": "application/json"
  }
})
```

```javascript
assert.equal( recepientDeleteResponse.status, 200, "Error" )
```