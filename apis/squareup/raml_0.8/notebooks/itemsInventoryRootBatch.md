---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#1273f28c8107d5bd3a1e
apiNotebookVersion: 1.1.66
title: Squareup API Notebook (items, inventory, root, batch)
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


This endpoint's response is an array that contains the response for each batched request

```javascript
batchCreateResponse = client.batch.post({
  "requests" : [
    {
      "method" : "POST" ,
      "relative_path" : "/v1/me/categories" ,
      "access_token" : "ACCESS_TOKEN" ,
      "body" : {
        "name" : "Beverages"
      } ,
      "request_id" : "MyRequestID"
    }
  ]
})
```

```javascript
assert.equal( batchCreateResponse.status, 200 )
```

Provides a merchant's account information, such as business name and email address

```javascript
merchantResponse = client.merchant_id(MERCHANT_ID).get()
```

```javascript
assert.equal( merchantResponse.status, 200 )
```

Creates an item temporary category 

```javascript
categoriesCreateResponse = client.merchant_id(MERCHANT_ID).categories.post({
  "name" : "Beverages"
})

```

The ID of the item's temporary category

```javascript
CATEGORY_ID = categoriesCreateResponse.body.id
```

Provides summary information for all of a merchant's items

```javascript
itemsResponse = client.merchant_id(MERCHANT_ID).items.get()
```

```javascript
assert.equal( itemsResponse.status, 200 )
```

Creates an item and at least one variation for it

```javascript
itemsCreateResponse = client.merchant_id(MERCHANT_ID).items.post({
  "name" : "Milkshake" ,
  "description" : "It's better than yours" ,
  "visibility" : "PRIVATE" ,
  "category_id" : CATEGORY_ID ,
  "variations" : [
    {
      "name" : "Small" ,
      "pricing_type" : "FIXED_PRICING" ,
      "price_money" : {
        "currency_code" : "USD" ,
        "amount" : 400
      } ,
      "sku" : "123"
    }
  ]
})
```

```javascript
assert.equal( itemsCreateResponse.status, 200 )
ITEM_ID = itemsCreateResponse.body.id
```

Provides the details for a single item, including associated modifier lists and fees

```javascript
itemDetailsResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).get()
```

```javascript
assert.equal( itemDetailsResponse.status, 200 )
```

Modifies the core details of an existing item

```javascript
itemUpdateResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).put({
  "name" : "Milkshake" ,
  "description" : "It's better than yours" ,
  "visibility" : "PRIVATE" ,
  "category_id" : CATEGORY_ID ,
  "variations" : [
    {
      "name" : "Small" ,
      "pricing_type" : "FIXED_PRICING" ,
      "price_money" : {
        "currency_code" : "USD" ,
        "amount" : 400
      } ,
      "sku" : "123"
    }
  ]
})
```

```javascript
assert.equal( itemUpdateResponse.status, 200 )
```

Uploads a JPEG or PNG image and sets it as the master image for an item

```javascript
formData = new FormData()
//formData.append("comment[text]", "asdasd" )
formData.append("image_data", new Blob([00000]), "myFile.png")
imageCreateResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).image.post(formData)
```

```javascript
assert.equal( imageCreateResponse.status, 200 )
```

Creates an item variation for an existing item

```javascript
variationsCreateResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).variations.post({
  "name" : "Small" ,
  "price_money" : {
    "currency_code" : "USD" ,
    "amount" : 400
  } ,
  "track_inventory" : true ,
  "inventory_alert_type" : "LOW_QUANTITY" ,
  "inventory_alert_threshold" : 10 ,
  "user_data" : "SEASONAL=TRUE"
})
```

```javascript
assert.equal( variationsCreateResponse.status, 200 )
VARIATION_ID = variationsCreateResponse.body.id
```

Modifies the details of an existing item variation

```javascript
variationUpdateResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).variations.variation_id(VARIATION_ID).put({
  "name" : "Small" ,
  "price_money" : {
    "currency_code" : "USD" ,
    "amount" : 400
  } ,
  "track_inventory" : true ,
  "inventory_alert_type" : "LOW_QUANTITY" ,
  "inventory_alert_threshold" : 10 ,
  "user_data" : "SEASONAL=TRUE"
})
```

```javascript
assert.equal( variationUpdateResponse.status, 200 )
```


Provides inventory information for all of a merchant's inventory-enabled item variations

```javascript
inventoryResponse = client.merchant_id(MERCHANT_ID).inventory.get()
```

```javascript
assert.equal( inventoryResponse.status, 200 )
```

Adjusts an item variation's current available inventory

```javascript
variationIdCreateResponse = client.merchant_id(MERCHANT_ID).inventory.variation_id(VARIATION_ID).post({
  "quantity_delta" : -1 ,
  "adjustment_type" : "SALE"
})
```

```javascript
assert.equal( variationIdCreateResponse.status, 200 )
```

Deletes an existing item variation from an item.

Every item must have at least one varation. This endpoint returns an error if you attempt to delete an item's only variation.

```javascript
variationDeleteResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).variations.variation_id(VARIATION_ID).delete()
```

```javascript
assert.equal( variationDeleteResponse.status, 200 )
```

Delete an item temporary category

```javascript
categoryDeleteResponse = client.merchant_id(MERCHANT_ID).categories.category_id(CATEGORY_ID).delete()
```

Creates a temporary fee (tax)

```javascript
feesCreateResponse = client.merchant_id(MERCHANT_ID).fees.post({
  "name" : "Sales tax" ,
  "rate" : "0.06"
})
```

The ID of the temporary fee to apply.

```javascript
FEE_ID = feesCreateResponse.body.id
```

Associates a fee with an item, meaning the fee is automatically applied to the item in Square Register

```javascript
itemFeeUpdateResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).fees.fee_id(FEE_ID).put()
```

```javascript
assert.equal( itemFeeUpdateResponse.status, 200 )
```

Removes a fee assocation from an item, meaning the fee is no longer automatically applied to the item in Square Register

```javascript
itemFeeDeleteResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).fees.fee_id(FEE_ID).delete()
```

```javascript
assert.equal( itemFeeDeleteResponse.status, 200 )
```

Delete a temporary fee 

```javascript
feeDeleteResponse = client.merchant_id(MERCHANT_ID).fees.fee_id(FEE_ID).delete()
```

Creates an item temporary modifier list and at least one modifier option for it

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

Temporary modifier list id

```javascript
MODIFIER_LIST_ID = modifierListsCreateResponse.body.id
```

Associates a modifier list with an item, meaning modifier options from the list can be applied to the item

```javascript
modifierListUpdateResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID).put()
```

```javascript
assert.equal( modifierListUpdateResponse.status, 200 )
```

Delete an existing item modifier list and all modifier options associated with it.

```javascript
modifierListDeleteResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID).delete() 
```

```javascript
assert.equal( modifierListDeleteResponse.status, 200 )
```

Delete an item temporary modifier list and at least one modifier option for it

```javascript
modifierListDeleteResponse = client.merchant_id(MERCHANT_ID)["modifier-lists"].modifier_list_id(MODIFIER_LIST_ID).delete()
```

Deletes an existing item and all item variations associated with it

```javascript
itemDeleteResponse = client.merchant_id(MERCHANT_ID).items.item_id(ITEM_ID).delete()
```

```javascript
assert.equal( itemDeleteResponse.status, 200 )
```