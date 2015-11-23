---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/organizations/52560d3f-c37a-409d-9887-79e0a9a9ecff/dashboard/apis/25077/versions/26632/portal/pages/40283/edit
apiNotebookVersion: 1.1.69
title: Resellers
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert 
```

```javascript
CLIENT_ID = prompt("Please, enter Client ID of your 23andMe application.")
CLIENT_SECRET = prompt("Please, enter Client Secret of your 23andMe application.")
isReseller = confirm("Are you \"23andMe\" authorized reseller?")
```

```javascript
API.createClient('client', '#REF_TAG_DEFENITION_23andMe Reseller:');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Places an order for kits.

The minimum parameters are "first_name", "last_name", "address_line1", "city", "country", "email", "phone", "shipping_method", "product_id", "personalizations". All other parameters are optional but may become mandatory. For example, a state or postal code isn't necessary to ship to Ireland but are both necessary to ship to the US. 

The optional parameters are "state", "postal_code", "gift_message" and "address_line2". 

"shipping_method" is one of "Standard" or "Express". 

"personalizations" is a list of personalization strings. The amount of kits in this order is based on the number of personalizations. ["Barack", "Michelle"] will place an order for 2 kits.

```javascript
if(isReseller){
	placeorderCreateResponse = client.placeorder.post({
 	 "first_name" : "Barack" ,
 	 "last_name" : "Obama" ,
 	 "address_line1" : "1600 Pennsylvania Avenue" ,
 	 "city" : "Washington" ,
 	 "state" : "DC" ,
 	 "country" : "US" ,
 	 "email" : "test@example.com" ,
 	 "phone" : "12123644576" ,
 	 "shipping_method" : "Standard" ,
 	 "product_id" : "product_id" ,
	  "postal_code" : "20500" ,
	  "personalizations" : [
	    "Barack" ,
	    "Michelle"
  	]
	})
}
```

```javascript
if(isReseller){
  assert.equal( placeorderCreateResponse.status, 200 )
}
```

Verify's that we are allowed to ship to this address for you.

The minimum parameters are "address_line1", "city", "country", "shipping_method", "product_id". All other parameters are optional but may become mandatory. For example, a state or postal code isn't necessary to ship to Ireland but are both necessary to ship to the US. 

The optional parameters are "state", "postal_code" and "address_line2".

```javascript
if(isReseller){
  verifyaddressCreateResponse = client.verifyaddress.post({
	  "first_name" : "Barack" ,
  	"last_name" : "Obama" ,
	  "address_line1" : "1600 Pennsylvania Avenue" ,
 		"city" : "Washington" ,
 		"state" : "DC" ,
 		"country" : "US" ,
 		"product_id" : "product_id" ,
 		"postal_code" : "20500" ,
 		"shipping_method" : "Standard"
	})
}
```

```javascript
if(isReseller){
	assert.equal( verifyaddressCreateResponse.status, 200 )
}
```