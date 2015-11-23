---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6873/preview
apiNotebookVersion: 1.1.66
title: Add incoming numbers
---

**ATTENTION!** This notebook is supposed to be used with test credentials: https://www.twilio.com/user/account/developer-tools/test-credentials

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

Default phone number used by the notebook

```javascript
DEFAULT_NUMBER = "+15105555555"
```

```javascript
testAccountSid = prompt("Please, enter your test Account SID which can be found at https://www.twilio.com/user/account/developer-tools/test-credentials")
```

```javascript
// Read about the Twilio RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8031/versions/8196/definition');
```

```javascript
function promptForNumber(type){
  var str = ""
  if(type != undefined && type != null && type.trim().length != 0){
    str = type + " "
  }
  var number = prompt("Please, enter the " + str + "phone number you wish to purchase, It must be formatted as +12223334455.\n\nIf the number is not found by Twilio or is not available, the method will return a 400 status response with 21422 error code.\n\nPress 'Cancel' or enter empty value if you want to try purchasing " + DEFAULT_NUMBER + ". This number is not available and will not be purchased.")
  
  if(!number||number.trim().length==0){
    number = DEFAULT_NUMBER
  }
  return number
}
```

Purchases a new phone number for your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number.

To find an available phone number to POST, use the subresources of the AvailablePhoneNumbers list resource.

```javascript
phoneNumber = promptForNumber()
```

```javascript
incomingPhoneNumbersCreateResponse = client.Accounts.AccountSid(testAccountSid).IncomingPhoneNumbers.json.post({
  PhoneNumber: phoneNumber,
})
```

```javascript
assert( incomingPhoneNumbersCreateResponse.status == 200 || incomingPhoneNumbersCreateResponse.status == 201 || incomingPhoneNumbersCreateResponse.body.code == 21422 )
```

Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number.

```javascript
localPhoneNumber = promptForNumber("local")
```

```javascript
localPhoneNumberCreateResponse = client.Accounts.AccountSid(testAccountSid).IncomingPhoneNumbers.Local.json.post({
  PhoneNumber: localPhoneNumber
})
```

```javascript
assert( localPhoneNumberCreateResponse.status == 200 || localPhoneNumberCreateResponse.status == 201 || localPhoneNumberCreateResponse.body.code == 21422 )
```

Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number.

```javascript
tollFreePhoneNumber = promptForNumber("toll free")
```

```javascript
tollFreeNumberCreateResponse = client.Accounts.AccountSid(testAccountSid).IncomingPhoneNumbers.TollFree.json.post({
  PhoneNumber: tollFreePhoneNumber
})
```

```javascript
assert( tollFreeNumberCreateResponse.status == 200 || tollFreeNumberCreateResponse.status == 201 || tollFreeNumberCreateResponse.body.code == 21422 )
```

Adds a new phone number to your account. If a phone number is found for your request, Twilio will add it to your account and bill you for the first month's cost of the phone number

```javascript
mobilePhoneNumber = promptForNumber("mobile")
```

```javascript
mobileNumberCreateResponse = client.Accounts.AccountSid(testAccountSid).IncomingPhoneNumbers.Mobile.json.post({
  PhoneNumber: mobilePhoneNumber
})
```

```javascript
assert( mobileNumberCreateResponse.status == 200 || mobileNumberCreateResponse.status == 201 || mobileNumberCreateResponse.body.code == 21422 )
```