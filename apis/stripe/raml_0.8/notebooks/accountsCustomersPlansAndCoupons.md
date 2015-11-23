---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7661/versions/7789/portal/pages/6376/preview
apiNotebookVersion: 1.1.66
title: Accounts, customers, plans and coupons
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

Id of a coupon which notebook operates with.

```javascript
couponId = "TEST_NOTEBOOK_COUPON"
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
clientId = prompt("Please, enter your Client id.")
secretKey = prompt("Please, enter your secret key.")
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

Retrieves the details of the account, based on the API key that was used to
make the request.

```javascript
accountResponse = client.account.get()
```

```javascript
assert.equal( accountResponse.status, 200 )
```

Returns a list of your plans

```javascript
plansResponse = client.plans.get()
```

```javascript
assert.equal( plansResponse.status, 200 )
```

Delete a plan which could have been created during previous notebook runs.

```javascript
for(var ind in plansResponse.body.data){
  var plan = plansResponse.body.data[ind]
  if(plan.id == planId){
    client.plans.PLAN_ID(planId).delete()
  }
}
```

You can create plans easily via the plan management page of the Stripe dashboard.
Plan creation is also accessible via the API if you need to create plans on
the fly.

```javascript
planCreateResponse = client.plans.post({
  id: planId,
  amount: 1,
  currency: "usd",
  interval: "week",
  name: "Test"
})
```

```javascript
assert.equal( planCreateResponse.status, 200 )
```

Retrieves the plan with the given ID

```javascript
planResponse = client.plans.PLAN_ID(planId).get()
```

```javascript
assert.equal( planResponse.status, 200 )
```

Updates the name of a plan. Other plan details (price, interval, etc.) are,
by design, not editable.

```javascript
planUpdateResponse = client.plans.PLAN_ID(planId).post({name:"___Test___"})
```

```javascript
assert.equal( planUpdateResponse.status, 200 )
```

Returns a list of your coupons.

```javascript
couponsResponse = client.coupons.get()
```

```javascript
assert.equal( couponsResponse.status, 200 )
```

Delete a coupon which could have been created during previous notebook runs.

```javascript
for(var ind in couponsResponse.body.data){
  var coupon = couponsResponse.body.data[ind]
  if(coupon.id==couponId){
    client.coupons.COUPON_ID(couponId).delete()
  }
}
```

You can create coupons easily via the coupon management page of the Stripe
dashboard. Coupon creation is also accessible via the API if you need to
create coupons on the fly.
A coupon has either a percent_off or an amount_off and currency. If you se
an amount_off, that amount will be subtracted from any invoice's subtotal.
For example, an invoice with a subtotal $10 will have a final total of -$10
if a coupon with an amount_off of 2000 is applied to it.}

```javascript
couponCreateResponse = client.coupons.post({
  id: couponId,
  duration: "forever",
  percent_off: 25,
  currency: "usd"
} )
```

```javascript
assert.equal( couponCreateResponse.status, 200 )
```

Retrieves the coupon with the given ID

```javascript
couponResponse = client.coupons.COUPON_ID(couponId).get()
```

```javascript
assert.equal( couponResponse.status, 200 )
```

Returns a list of your customers. The customers are returned sorted by creation date, with the most recently created customers appearing first.

```javascript
customersResponse = client.customers.get()
```

```javascript
assert.equal( customersResponse.status, 200 )
```

Delete a customer which could have been created during previous notebook runs

```javascript
for(var ind in customersResponse.body.data){
  var customer = customersResponse.body.data[ind]
  if(customer.email==customerEmail){
    client.customers.CUSTOMER_ID(customer.id).delete()
  }
}
```

Creates a new customer object.

```javascript
customerCreateResponse = client.customers.post( {
  email: customerEmail,
  coupon: couponId
} )
```

```javascript
assert.equal( customersResponse.status, 200 )
customerId = customerCreateResponse.body.id
```

Retrieves the details of an existing customer.

```javascript
customerResponse = client.customers.CUSTOMER_ID(customerId).get()
```

```javascript
assert.equal( customerResponse.status, 200 )
```

Updates the specified customer by setting the values of the parameters passed. Any parameters not provided will
be left unchanged. For example, if you pass the card parameter, that becomes the customer's active card to be
used for all charges in future. When you update a customer to a new valid card, the last unpaid invoice (if one exists)
will be retried automatically.

```javascript
customerUpdateResponse = client.customers.CUSTOMER_ID(customerId).post({
  description: "This is a special customer for API Notebook tests."
} )
```

```javascript
assert.equal( customerUpdateResponse.status, 200 )
```

You can see a list of the customer's cards. Note that the 10 most recent cards are always available by default on the customer object.
If you need more than 10, you can use the listing API to page through the additional cards.

```javascript
cardsResponse = client.customers.CUSTOMER_ID(customerId).cards.get()
```

```javascript
assert.equal( cardsResponse.status, 200 )
```

We need a token in order to create a new card.

```javascript
tokenCreateResponse = client.tokens.post({
  "card[exp_month]": 8,
  "card[exp_year]": getNextYear(),
  "card[number]": 4242424242424242
})
```

Creating a new card.
When you create a new credit card, you must specify a customer.

Creating a new credit card will not change the customer's default credit card automatically;
you should update the customer with a new default_card for that.

```javascript
cardCreateResponse = client.customers.CUSTOMER_ID(customerId).cards.post( {
  card: tokenCreateResponse.body.id
})
```

```javascript
assert.equal( cardCreateResponse.status, 200 )
cardId = cardCreateResponse.body.id
```

Retrieving a customer's card.
By default, you can see the 10 most recent cards stored on a customer directly on the customer object,
but you can also retrieve details about a specific card stored on the customer.

```javascript
cardResponse = client.customers.CUSTOMER_ID(customerId).cards.CARD_ID(cardId).get()
```

```javascript
assert.equal( cardResponse.status, 200 )
```

Updating a card.
If you need to update only some card details, like the billing address or expiration date, you can do so withou
having to re-enter the full card details.

When you update a card, Stripe will automatically validate the card.

```javascript
cardUpdateResponse = client.customers.CUSTOMER_ID(customerId).cards.CARD_ID(cardId).post({name:"Jack Jackson"})
```

```javascript
assert.equal( cardUpdateResponse.status, 200 )
```

You can delete cards from a customer. If you delete a card that is currently a customer's default, the most recently
added card will be used as the new default. If you delete the customer's last remaining card, the default_card
attribute on the customer will become null.

Note that you may want to prevent customers on paid subscriptions from deleting all cards on file so that there
is at least one default card for the next invoice payment attempt.

```javascript
cardDeleteResponse = client.customers.CUSTOMER_ID(customerId).cards.CARD_ID(cardId).delete()
```

```javascript
assert.equal( cardDeleteResponse.status, 200 )
```

You can see a list of the customer's active subscriptions. Note that the 10 most recent active subscriptions are always available by default on the customer object. If you need more than those 10, you can use the 'limit' and 'starting_after' parameters to page through additional subscriptions

```javascript
subscriptionsResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.get()
```

```javascript
assert.equal( subscriptionsResponse.status, 200 )
```

Subscribes a customer to a plan, meaning the customer will be billed monthly
starting from signup. If the customer already has an active subscription,
we'll update it to the new plan and optionally prorate the price we charge
next month to make up for any price changes.

```javascript
subscriptionCreateResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.post({
  plan: planId,
  coupon: couponId 
})
```

```javascript
assert.equal( subscriptionCreateResponse.status, 200 )
subscriptionId = subscriptionCreateResponse.body.id
```

Retrieving a customer's subscription
By default, you can see the 10 most recent active subscriptions stored on a customer directly on the customer object, but you can also retrieve details about a specific active subscription for a customer.


```javascript
subscriptionResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.SUBSCRIPTION_ID(subscriptionId).get()
```

```javascript
assert.equal( subscriptionResponse.status, 200 )
```

Updates an existing subscription on a customer to match the specified parameters. When changing plans or quantities, we will optionally prorate the price we charge next month to make up for any price changes

```javascript
subscriptionUpdateResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.SUBSCRIPTION_ID(subscriptionId).post({
  quantity: 2
})
```

```javascript
assert.equal( subscriptionUpdateResponse.status, 200 )
```

Removes the currently applied discount on a subscription.

```javascript
subscriptionDiscountResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.SUBSCRIPTION_ID(subscriptionId).discount.delete()
```

```javascript
assert.equal( subscriptionDiscountResponse.status, 200 )
```

Cancels the subscription if it exists. If you set the at_period_end parameter
to true, the subscription will remain active until the end of the period, a
which point it will be cancelled and not renewed. By default, the subscription
is terminated immediately. In either case, the customer will not be charged
again for the subscription. Note, however, that any pending invoice items
that you've created will still be charged for at the end of the period unless
manually deleted.If you've set the subscription to cancel at period end, any
pending prorations will also be left in place and collected at the end of the
period, but if the subscription is set to cancel immediately, pending prorations
will be removed.
By default, all unpaid invoices for the customer will be closed upon subscription
cancellation. We do this in order to prevent unexpected payment retries once
the customer has canceled a subscription. However, you can reopen the invoices
manually after subscription cancellation to have us proceed with automatic
retries, or you could even re-attempt payment yourself on all unpaid invoices
before allowing the customer to cancel the subscription at all.

```javascript
subscriptionDeleteResponse = client.customers.CUSTOMER_ID(customerId).subscriptions.SUBSCRIPTION_ID(subscriptionId).delete({})
```

```javascript
assert.equal( subscriptionDeleteResponse.status, 200 )
```

Removes the currently applied discount on a customer

```javascript
customerDiscountResponse = client.customers.CUSTOMER_ID(customerId).discount.delete()
```

```javascript
assert.equal( customerDiscountResponse.status, 200 )
```

Permanently deletes a customer. It cannot be undone.

```javascript
customerDeleteResponse = client.customers.CUSTOMER_ID(customerId).delete()
```

```javascript
assert.equal( customerDeleteResponse.status, 200 )
```

You can delete plans via the plan management page of the Stripe dashboard.
However, deleting a plan does not affect any current subscribers to the plan;
it merely means that new subscribers can't be added to that plan. You can
also delete plans via the API.

```javascript
planDeleteResponse = client.plans.PLAN_ID(planId).delete()
```

```javascript
assert.equal( planDeleteResponse.status, 200 )
```

You can delete coupons via the coupon management page of the Stripe dashboard.
However, deleting a coupon does not affect any customers who have already
applied the coupon; it means that new customers can't redeem the coupon. You
can also delete coupons via the API.

```javascript
couponDeleteResponse = client.coupons.COUPON_ID(couponId).delete()
```

```javascript
assert.equal( couponDeleteResponse.status, 200 )
```