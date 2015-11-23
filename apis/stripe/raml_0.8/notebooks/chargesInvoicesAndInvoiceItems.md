---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7661/versions/7789/portal/pages/6377/preview
apiNotebookVersion: 1.1.66
title: Charges, invoices and invoice items
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
var assert = chai.assert
```

Id of a plan which notebook operates with.

```javascript
planId = "TEST_NOTEBOOK_PLAN"
```

Email of a customer which notebook operates with.

```javascript
customerEmail = "customer@somehost.com"
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

Let's delete a customer which could be created during previous notebook runs.

```javascript
customersResponse = client.customers.get()
for(var ind in customersResponse.body.data){
  var customer = customersResponse.body.data[ind]
  if(customer.email==customerEmail){
    client.customers.CUSTOMER_ID(customer.id).delete()
  }
}
```

Let's delete a plan which could be created during previous notebook runs.

```javascript
plansResponse = client.plans.get()
for(var ind in plansResponse.body.data){
  var plan = plansResponse.body.data[ind]
  if(plan.id == planId){
    client.plans.PLAN_ID(planId).delete()
  }
}

```

Now let's create a customer and a card for him.

```javascript
customerCreateResponse = client.customers.post({
  email: customerEmail,
})
customerId = customerCreateResponse.body.id

tokenCreateResponse = client.tokens.post({
  "card[exp_month]": 8,
  "card[exp_year]": getNextYear(),
  "card[number]": 4242424242424242
})

cardCreateResponse = client.customers.CUSTOMER_ID(customerId).cards.post( {
  card: tokenCreateResponse.body.id
})
```

Let's create a plan.

```javascript
planCreateResponse = client.plans.post({
  id: planId,
  amount: 1,
  currency: "usd",
  interval: "week",
  name: "Test"
})
```

Now we can subscribes the customer to the plan.

```javascript
subscriptionCreateResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.post({
  plan: planId
})
```

Returns a list of charges you've previously created. The charges are returned in sorted order, with the most recent charges appearing first.

```javascript
chargesResponse = client.charges.get()
```

```javascript
assert.equal( chargesResponse.status, 200, "Error" )
```

Creating a new charge (charging a credit card).
To charge a credit card, you create a new charge object. If your API key is in test mode, the supplied card won'
actually be charged, though everything else will occur as if in live mode. (Stripe assumes that the charge would have
completed successfully).

```javascript
chargeCreateResponse = client.charges.post({
  amount: 50,
  currency: "usd",
  customer: customerId,
  capture: false  
})
```

```javascript
assert.equal( chargeCreateResponse.status, 200, "Error" )
chargeId = chargeCreateResponse.body.id
```

Retrieves the details of a charge that has previously been created. Supply the unique charge ID that was returned
from your previous request, and Stripe will return the corresponding charge information. The same information is
returned when creating or refunding the charge.

```javascript
chargeResponse = client.charges.CHARGE_ID(chargeId).get()
```

```javascript
assert.equal( chargeResponse.status, 200, "Error" )
```

Updates the specified charge by setting the values of the parameters passed. Any parameters not provided will be left unchanged

```javascript
chargeUpdateResponse = client.charges.CHARGE_ID(chargeId).post({
  description: "Test charge."
})
```

```javascript
assert.equal( chargeUpdateResponse.status, 200, "Error" )
```

Capture the payment of an existing, uncaptured, charge. This is the second half of the two-step payment flow, where first you created
a charge with the capture option set to false.

Uncaptured payments expire exactly seven days after they are created. If they are not captured by that point in time, they will be
marked as refunded and will no longer be capturable.

```javascript
captureResponse = client.charges.CHARGE_ID(chargeId).capture.post({amount:50})
```

```javascript
assert.equal( captureResponse.status, 200, "Error" )
```

Refunds a charge that has previously been created but not yet refunded. Funds will be refunded to the credit or
debit card that was originally charged. The fees you were originally charged are also refunded.

You can optionally refund only part of a charge. You can do so as many times as you wish until the entire charge has been refunded.

Once entirely refunded, a charge can't be refunded again. This method will return an error when called on an already-refunded charge,
or when trying to refund more money than is left on a charge.

```javascript
refundResponse = client.charges.CHARGE_ID(chargeId).refund.post({amount:50})
```

```javascript
assert.equal( refundResponse.status, 200, "Error" )
```

Contacting your customer is always the best first step, but if that doesn'
work, you can submit (text-only) evidence in order to help us resolve the
dispute in your favor. You can do this in your dashboard, but if you prefer,
you can use the API to submit evidence programmatically.

```javascript
/*
We do not test dispute related methods as it is not possible to create a 'dispute' object in test mode.
According to https://stripe.com/help/disputes ,
"a dispute occurs when one of your customers questions your charge with their bank or credit card company".
*/
//disputeResponse = client.charges.CHARGE_ID(chargeId).dispute.post()
```

```javascript
//assert.equal( disputeResponse.status, 200, "Error" )
```

Closing the dispute for a charge indicates that you do not have any evidence
to submit and are essentially 'dismissing' the dispute, acknowledging it as
lost.
The status of the dispute will change from under_review to lost. Closing
a dispute is irreversible.

```javascript
/*
We do not test dispute related methods as it is not possible to create a 'dispute' object in test mode.
According to https://stripe.com/help/disputes ,
"a dispute occurs when one of your customers questions your charge with their bank or credit card company".
*/
//closeResponse = client.charges.CHARGE_ID(chargeId).dispute.close.post()
```

```javascript
//assert.equal( closeResponse.status, 200, "Error" )
```

Returns a list of your invoice items. Invoice Items are returned sorted by
creation date, with the most recently created invoice items appearing first.

```javascript
invoiceitemsResponse = client.invoiceitems.get()
```

```javascript
assert.equal( invoiceitemsResponse.status, 200, "Error" )
```

Adds an arbitrary charge or credit to the customer's upcoming invoice.

```javascript
invoiceItemCreateResponse = client.invoiceitems.post({
  customer: customerId,
  amount: 100,
  currency: "usd"
})
```

```javascript
assert.equal( invoiceItemCreateResponse.status, 200, "Error" )
itemId = invoiceItemCreateResponse.body.id
```

Retrieves the invoice item with the given ID

```javascript
invoiceItemResponse = client.invoiceitems.ID(itemId).get()
```

```javascript
assert.equal( invoiceItemResponse.status, 200, "Error" )
```

Updates the amount or description of an invoice item on an upcoming invoice. Updating an invoice item is only possible before the invoice it's attached to is closed

```javascript
invoiceItemUpdateResponse = client.invoiceitems.ID(itemId).post({
  description: "Test invoice item"
})
```

```javascript
assert.equal( invoiceItemUpdateResponse.status, 200, "Error" )
```

You can list all invoices, or list the invoices for a specific customer. The invoices are returned sorted by creation date, with the most recently created invoices appearing first

```javascript
invoicesResponse = client.invoices.get()
```

```javascript
assert.equal( invoicesResponse.status, 200, "Error" )
```

If you need to invoice your customer outside the regular billing cycle, you
can create an invoice that pulls in all pending invoice items, including
prorations. The customer's billing cycle and regular subscription won't be
affected.
Once you create the invoice, it'll be picked up and paid automatically,
though you can choose to pay it right away.

```javascript
invoiceCreateResponse = client.invoices.post({
  customer: customerId
})
```

```javascript
assert.equal( invoiceCreateResponse.status, 200, "Error" )
invoiceId = invoiceCreateResponse.body.id
```

Retrieves the invoice with the given ID.
Returns an invoice object if a valid invoice ID was provided. Returns an
error otherwise.
The invoice object contains a lines hash that contains information about the
subscriptions and invoice items that have been applied to the invoice, as
well as any prorations that Stripe has automatically calculated. Each line
on the invoice has an amount attribute that represents the amount actually
contributed to the invoice's total. For invoice items and prorations, the
amount attribute is the same as for the invoice item or proration respectively.
For subscriptions, the amount may be different from the plan's regular price
depending on whether the invoice covers a trial period or the invoice period
differs from the plan's usual interval.
The invoice object has both a subtotal and a total. The subtotal represents
the total before any discounts, while the total is the final amount to be
charged to the customer after all coupons have been applied.
The invoice also has a next_payment_attempt attribute that tells you the nex
time (as a UTC timestamp) payment for the invoice will be automatically
attempted. For invoices that have been closed or that have reached the maximum
number of retries (specified in your retry settings) , the next_payment_attemp
will be null.

```javascript
invoiceResponse = client.invoices.INVOICE_ID(invoiceId).get()
```

```javascript
assert.equal( invoiceResponse.status, 200, "Error" )
```

Until an invoice is paid, it is marked as open (closed=false). If you'd like to
stop Stripe from automatically attempting payment on an invoice or would simply
like to close the invoice out as no longer owed by the customer, you can update
the closed parameter.

```javascript
invoiceCloseResponse = client.invoices.INVOICE_ID(invoiceId).post({
  closed: true
})
```

```javascript
assert.equal( invoiceCloseResponse.status, 200, "Error" )
```

Let's create one more invoice. In order to do so, we need an invoice item.

```javascript
invoiceItemCreateResponse = client.invoiceitems.post({
  customer: customerId,
  amount: 100,
  currency: "usd"
})
itemId = invoiceItemCreateResponse.body.id

invoiceCreateResponse2 = client.invoices.post({
  customer: customerId
})

invoiceId2 = invoiceCreateResponse2.body.id
```

Stripe automatically creates and then attempts to pay invoices for customers
on subscriptions. We'll also retry unpaid invoices according to your retry
settings. However, if you'd like to attempt to collect payment on an invoice
out of the normal retry schedule or for some other reason, you can do so.

```javascript
payResponse = client.invoices.INVOICE_ID(invoiceId2).pay.post()
```

```javascript
assert.equal( payResponse.status, 200, "Error" )
```

When retrieving an invoice, you'll get a lines property containing the total
count of line items and the first handful of those items. There is also a URL
where you can retrieve the full (paginated) list of line items.

```javascript
linesResponse = client.invoices.INVOICE_ID(invoiceId).lines.get()
```

```javascript
assert.equal( linesResponse.status, 200, "Error" )
```

Let's create an invoice item which will be used to test delete method.

```javascript
invoiceItemCreateResponse = client.invoiceitems.post({
  customer: customerId,
  amount: 100,
  currency: "usd"
})
itemId = invoiceItemCreateResponse.body.id
```

Removes an invoice item from the upcoming invoice. Removing an invoice item
is only possible before the invoice it's attached to is closed.

```javascript
invoiceItemDeleteResponse = client.invoiceitems.ID(itemId).delete()
```

```javascript
assert.equal( invoiceItemDeleteResponse.status, 200, "Error" )
```

At any time, you can view the upcoming invoice for a customer. This will
show you all the charges that are pending, including subscription renewal
charges, invoice item charges, etc. It will also show you any discoun
that is applicable to the customer.

```javascript
upcomingResponse = client.invoices.upcoming.get({
  customer: customerId
})
```

```javascript
assert.equal( upcomingResponse.status, 200, "Error" )
```