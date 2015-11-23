---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#85d02a93160e0044239c
apiNotebookVersion: 1.1.66
title: Squareup API Notebook  (modifier-lists, categories, discounts, fees, pages)
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

Lists all of a merchant's modifier lists

```javascript
modifierListsResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].get()
```

```javascript
assert.equal( modifierListsResponse.status, 200 )
```

Creates an item modifier list and at least one modifier option for it

```javascript
modifierListsCreateResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].post({
  "name" : "Toppings" ,
  "selection_type" : "MULTIPLE" ,
  "modifier_options" : [
    {
      "name" : "Whipped Cream" ,
      "price_money" : {
        "currency_code" : "USD" ,
        "amount" : 50
      }
    }
  ]
})
```

```javascript
assert.equal( modifierListsCreateResponse.status, 200 )
MODIFIER_LIST_ID = modifierListsCreateResponse.body.id
```

Modifies the details of an existing item modifier list.

```javascript
modifierListIdUpdateResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID).put()
```

```javascript
assert.equal( modifierListIdUpdateResponse.status, 200 )
```

Creates an item modifier option and adds it to a modifier list

```javascript
modifierOptionsCreateResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID)["modifier-options"].post ({
  "name" : "Sprinkles" ,
  "price_money" : {
    "currency_code" : "USD" ,
    "amount" : 50
  }
})
```

```javascript
assert.equal( modifierOptionsCreateResponse.status, 200 )
MODIFIER_OPTION_ID = modifierOptionsCreateResponse.body.id
```

Modifies the details of an existing item modifier option

```javascript
modifierOptionUpdateResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID)["modifier-options"].modifier_option_id(MODIFIER_OPTION_ID).put ({
  "name" : "Sprinkles" ,
  "price_money" : {
    "currency_code" : "USD" ,
    "amount" : 50
  }
})
```

```javascript
assert.equal( modifierOptionUpdateResponse.status, 200 )
```

Deletes an existing item modifier option from a modifier list

```javascript
modifierOptionDeleteResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID)["modifier-options"].modifier_option_id(MODIFIER_OPTION_ID).delete()
```

```javascript
assert.equal( modifierOptionDeleteResponse.status, 200 )
```

Deletes an existing item modifier list and all modifier options associated with it.

```javascript
modifierListDeleteResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID).delete()
```

```javascript
assert.equal( modifierListDeleteResponse.status, 200 )
```

Lists all of a merchant's item categories.

```javascript
categoriesResponse = client.merchant_id(MERCHANT_ID).categories.get()
```

```javascript
assert.equal( categoriesResponse.status, 200 )
```

Creates an item category

```javascript
categoriesCreateResponse = client.merchant_id(MERCHANT_ID).categories.post({
  "name" : "Beverages"
})
```

```javascript
assert.equal( categoriesCreateResponse.status, 200 )
CATEGORY_ID = categoriesCreateResponse.body.id
```

Update an item category

```javascript
categoryUpdateResponse = client.merchant_id(MERCHANT_ID).categories.category_id(CATEGORY_ID).put({
  "name" : "Beverages"
})
```

```javascript
assert.equal( categoryUpdateResponse.status, 200 )
```

Deletes an existing item category.

```javascript
categoryDeleteResponse = client.merchant_id(MERCHANT_ID).categories.category_id(CATEGORY_ID).delete()
```

```javascript
assert.equal( categoryDeleteResponse.status, 200 )
```

Lists all of a merchant's discounts

```javascript
discountsResponse = client.merchant_id(MERCHANT_ID).discounts.get()
```

```javascript
assert.equal( discountsResponse.status, 200 )
```

Creates a discount

```javascript
discountsCreateResponse = client.merchant_id(MERCHANT_ID).discounts.post({
  "pin_required" : false ,
  "discount_type" : "FIXED" ,
  "name" : "Early Bird" ,
  "rate" : "0.1"
})
```

```javascript
assert.equal( discountsCreateResponse.status, 200 )
DISCOUNT_ID = discountsCreateResponse.body.id
```

Modifies the details of an existing discount

```javascript
discountUpdateResponse = client.merchant_id(MERCHANT_ID).discounts.discount_id(DISCOUNT_ID).put({
  "pin_required" : false ,
  "discount_type" : "FIXED" ,
  "name" : "Early Bird" ,
  "rate" : "0.1"
})
```

```javascript
assert.equal( discountUpdateResponse.status, 200 )
```

Lists all of a merchant's fees (taxes)

```javascript
feesResponse = client.merchant_id(MERCHANT_ID).fees.get()
```

```javascript
assert.equal( feesResponse.status, 200 )
```

Creates a fee (tax)

```javascript
feesCreateResponse = client.merchant_id(MERCHANT_ID).fees.post({
  "name" : "Sales tax" ,
  "rate" : "0.06"
})
```

```javascript
assert.equal( feesCreateResponse.status, 200 )
FEE_ID = feesCreateResponse.body.id
```

Modifies the details of an existing fee (tax)

```javascript
feeUpdateResponse = client.merchant_id(MERCHANT_ID).fees.fee_id(FEE_ID).put({
  "name" : "Sales tax" ,
  "rate" : "0.06"
})
```

```javascript
assert.equal( feeUpdateResponse.status, 200 )
```

Deletes an existing fee (tax).

```javascript
feeIdDeleteResponse = client.merchant_id(MERCHANT_ID).fees.fee_id(FEE_ID).delete()
```

```javascript
assert.equal( feeIdDeleteResponse.status, 200 )
```

Lists all of a merchant's Favorites pages in Square Register.

```javascript
pagesResponse = client.merchant_id(MERCHANT_ID).pages.get()
```

```javascript
assert.equal( pagesResponse.status, 200 )
```

Creates a Favorites page in Square Register

```javascript
pagesCreateResponse = client.merchant_id(MERCHANT_ID).pages.post({
  "name" : "Lunch Items" ,
  "page_index" : 0
})
```

```javascript
assert.equal( pagesCreateResponse.status, 200 )
PAGE_ID = pagesCreateResponse.body.id
```

Modifies the details of a Favorites page in Square Register

```javascript
pageUpdateResponse = client.merchant_id(MERCHANT_ID).pages.page_id(PAGE_ID).put({
  "name" : "Lunch Items" ,
  "page_index" : 0
})
```

```javascript
assert.equal( pageUpdateResponse.status, 200 )
```

Modifies a cell of a Favorites page in Square Register

```javascript
cellsUpdateResponse = client.merchant_id(MERCHANT_ID).pages.page_id(PAGE_ID).cells.put({
  "row" : 0 ,
  "column" : 0 ,
  "object_type" : "DISCOUNT" ,
  "object_id" : DISCOUNT_ID
})
```

```javascript
assert.equal( cellsUpdateResponse.status, 200 )
```

Deletes a cell from a Favorites page in Square Register

```javascript
cellsDeleteResponse = client.merchant_id(MERCHANT_ID).pages.page_id(PAGE_ID).cells.delete({},{
  query:{ "row" : 0 ,
  				"column" : 0 }})
```

```javascript
assert.equal( cellsDeleteResponse.status, 200 )
```

Deletes an existing Favorites page and all of its cells.

```javascript
pageDeleteResponse = client.merchant_id(MERCHANT_ID).pages.page_id(PAGE_ID).delete()
```

```javascript
assert.equal( pageDeleteResponse.status, 200 )
```

Deletes an existing discount.

```javascript
discountDeleteResponse = client.merchant_id(MERCHANT_ID).discounts.discount_id(DISCOUNT_ID).delete()
```

```javascript
assert.equal( discountDeleteResponse.status, 200 )
```