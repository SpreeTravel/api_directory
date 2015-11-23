---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6883/preview
apiNotebookVersion: 1.1.66
title: Dangerous methods
---

**ATTENTION!** This notebook execute methods which interact with external environment or cause irreversible or hard-to-revert changes of your account.

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
rootAccountSid = prompt("Please, enter your Account SID which can be found at https://www.twilio.com/user/account")
```

```javascript
// Read about the Twilio RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8031/versions/8196/definition');
```

```javascript
//Auxiliary method which allows to select a particular phone number
function selectPhoneNumber(type,msg,specialNumber,numberComment){
  var number = null
  var pageSize = -1
  var pagesCount = 1
  for(var i = 0 ; i < pagesCount ; i++ ){

    var phoneNumbersResponse = null;
    if(pageSize<0){
      if(type=="in"){
        phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.json.get()
      }
      else if(type=="out"){
        phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.json.get()
      }
      else if(type=="avail"){
        phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).AvailablePhoneNumbers.IsoCountryCode("US").Local.json.get()
      }
      if(phoneNumbersResponse.body.page_size){
        pageSize = phoneNumbersResponse.body.page_size
      }
      if(phoneNumbersResponse.body.num_pages){
        pagesCount = phoneNumbersResponse.body.num_pages
      }
    }
    else{
      if(type=="in"){
        phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.json.get({"Page":i,"PageSize":pageSize})
      }
      else if(type=="out"){
        phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.json.get({"Page":i,"PageSize":pageSize})
      }
      else if(type=="avail"){
        phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).AvailablePhoneNumbers.IsoCountryCode("US").Local.json.get({"Page":i,"PageSize":pageSize})
      }
    }

    var numbers = null
    if(type =="in"){
      numbers = phoneNumbersResponse.body.incoming_phone_numbers
    }
    else if(type=="out"){
      numbers = phoneNumbersResponse.body.outgoing_caller_ids
    }
    else if(type=="avail"){
      numbers = phoneNumbersResponse.body.available_phone_numbers
    }
    var message = msg
    if(i<pagesCount-1){
      message += "\nTo view the next page enter a negative value."
    }
    message += "\n"
    for( var j in numbers){
      message += "\n" + j + ": " + numbers[j]["friendly_name"]
      if(specialNumber && numberComment){
        if(specialNumber.phone_number==numbers[j].phone_number){
          message += "   (" + numberComment + ")"
        }
      }
    }
    var indStr = prompt(message)
    if(!indStr)
      break;

    ind = Number.parseInt(indStr)
    if(!(typeof ind === "number")||Number.isNaN(ind)||ind<0){
       break
    }
    if(ind>=0){
      return numbers[ind]
    }  
  }
  return null
}
```

```javascript
phoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.json.get()
```

Create a new Account instance resource as a subaccount of the one used to make the request. See
Creating Subaccounts for more information.

In future this subaccount can only be closed. There is no way to delete this subaccount.

```javascript
doCreateAccount = window.confirm("Do you want to create a Twilio subaccount?\nIn future this subaccount can only be closed. There is no way to delete this subaccount.")
if(doCreateAccount){
  accountCreateResponse = client.Accounts.json.post({
    FriendlyName : "Test Subaccount " + new Date().getTime()
  })
}
```

```javascript
if(doCreateAccount){
  assert.equal( accountCreateResponse.status, 201 )
}
```

Release this phone number from your account. Twilio will no longer answer
calls to this number, and you will stop being billed the monthly phone
number fee. The phone number will eventually be recycled and potentially
given to another customer, so use with care. If you make a mistake, contact
Twilio. Twilio may be able to give you the number back.

```javascript
doRelease = window.confirm("Do you want to release one of your incoming numbers?\n\nTwilio will no longer answer calls to this number, and you will stop being billed the monthly phone number fee. The phone number will eventually be recycled and potentially given to another customer, so use with care. If you make a mistake, contact Twilio. Twilio may be able to give you the number back.")
```

```javascript
numberToPurchase = null
if(doRelease){
  var doPurchase = window.confirm("Do you want to purchase a new Twilio number in order to use it for number release test?\n\n If you refuse to purchase a number now, you will still be able to release one of your existing numbers.\n\nNote that this operation bills your account.")
  if(doPurchase){
    var continuePurchase = true
    while(continuePurchase){
      numberToPurchase = selectPhoneNumber("avail", "Please, select a number to purchase by entering it's index.")
      if(numberToPurchase){
        var confirmPurchase = window.confirm("Do you really want to purchase the " + numberToPurchase.friendly_name + " number?")
        if(confirmPurchase){
          purchaseResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.json.post({
            PhoneNumber: numberToPurchase.phone_number,
          })
          status = purchaseResponse.status
          if(purchaseResponse.status==201){
            continuePurchase = false
            window.alert("The " + numberToPurchase.friendly_name + " has been successfully purchased." )
          }
          else{
            var errorMessage = "Error has occured.\n\n"
            errorMessage += "Status: " + purchaseResponse.status + " (supposed to be 201)\n"
            if(purchaseResponse.body.code){
              errorMessage += "Code: " + purchaseResponse.body.code + "\n"
            }
            if(purchaseResponse.body.message){
              errorMessage += "Message: " + purchaseResponse.body.message + "\n"
            }
            if(purchaseResponse.body.more_info){
              errorMessage += "More info: " + purchaseResponse.body.more_info + "\n"
            }
            else{
              errorMessage += "More info: https://www.twilio.com/docs/errors\n"
            }
            errorMessage += "\nDo you want to try again once more?"
            continuePurchase = window.confirm(errorMessage)
          }
        }
        else{
          continuePurchase = false
        }
      }
      else{
        continuePurchase = false
      }
    }
  }
}
```

```javascript
if(doRelease){ 
  var number = selectPhoneNumber("in", "Please, select a number to release by entering its index.",numberToPurchase,"this number has just been purchased")
  if(number){
    confirmRelease = window.confirm("Do you really want to release " + number.friendly_name)
    if(confirmRelease){
      incomingPhoneNumberDeleteResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(number.sid).json.delete({})
    }
  }
}
```

```javascript
if(doRelease&&confirmRelease){
  assert.equal( incomingPhoneNumberDeleteResponse.status, 204 )
}
```

Adds a new CallerID to your account. After making this request, Twilio will return to you a validation code and **Twilio will dial** the phone number given to perform validation. The code returned must be entered via the phone before the CallerID will be added to your account. The following parameters are accepted:

```javascript
doAdd = window.confirm("Do you want to add a new CallerID to your account?\n\nAfter making this request, Twilio will return to you a validation code and Twilio will dial the phone number given to perform validation. The code returned must be entered via the phone before the CallerID will be added to your account. The following parameters are accepted:")
if(doAdd){  
  var number = prompt("Please, enter you new number formatted as \"+1112223344\".")
  var fName = prompt("Please, enter friendly name for your new number")
  if(number && fName){
    outgoingCallerIdCreateResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.json.post({
      "PhoneNumber": number,
      "FriendlyName": fName
    })
  }
}
```

```javascript
if(doAdd){
  assert.equal( outgoingCallerIdCreateResponse.status, 200 )
}
```

Deletes the caller ID from the account. Returns an HTTP 204 response if
successful, with no body.

```javascript
doDelete = window.confirm("Do you want to delete a Caller ID from the account?")
if(doDelete){
  var number = selectPhoneNumber("out", "Please, select the outgoing number to be removed by entering it's index.")
  if(number){
    if(window.confirm("Do you really want to remove " + number.friendly_name + " from the set of your outgoing caller Ids?")){
      outgoingCallerIdDeleteResponse = client.Accounts.AccountSid(rootAccountSid).OutgoingCallerIds.OutgoingCallerIdSid(number.sid).json.delete({})
    }
  }
  else{
    doDelete = false
  }
}
```

```javascript
if(doDelete){
  assert.equal( outgoingCallerIdDeleteResponse.status, 204 )
}
```