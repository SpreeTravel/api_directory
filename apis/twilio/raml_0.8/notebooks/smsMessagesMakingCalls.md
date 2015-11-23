---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6874/preview
apiNotebookVersion: 1.1.66
title: SMS, Messages, making Calls
---

If you want to test making calls with Twilio, you must prepare some URI which points to a TwiML document. For example, like this:
```
<?xml version="1.0" encoding="UTF-8"?>
<Response>    
   <Say voice="woman">
      Hello. This is a Twilio API Notebook test. Thank you for participation.
   </Say>
</Response>
```

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
function selectPhoneNumber(msg){
  var number = null
  var pageSize = -1
  var pagesCount = 1
  for(var i = 0 ; i < pagesCount ; i++ ){

    var incomingPhoneNumbersResponse = null;
    if(pageSize<0){
      incomingPhoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.json.get()
      pageSize = incomingPhoneNumbersResponse.body.page_size
      pagesCount = incomingPhoneNumbersResponse.body.num_pages
    }
    else{
      incomingPhoneNumbersResponse = client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.json.get({"Page":i,"PageSize":pageSize})
    }

    var numbers = incomingPhoneNumbersResponse.body.incoming_phone_numbers  
    var message = msg
    if(i<pagesCount-1){
      message += "\nTo view the next page enter a negative value."
    }
    message += "\n"
    for( var j in numbers){
      message += "\n" + j + ": " + numbers[j]["friendly_name"]
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

Returns a list of ShortCode resource representations, each representing a
short code within your account. The list includes paging information.

```javascript
shortCodesResponse = client.Accounts.AccountSid(rootAccountSid).SMS.ShortCodes.json.get()
```

```javascript
assert.equal( shortCodesResponse.status, 200 )
shortCodeSid = shortCodesResponse.body.short_codes.length == 0 ? null : shortCodesResponse.body.short_codes[0].sid
```

Get a single message

```javascript
if(shortCodeSid){
  shortCodeResponse = client.Accounts.AccountSid(rootAccountSid).SMS.ShortCodes.ShortCodeSid(shortCodeSid).json.get()
}
```

```javascript
if(shortCodeSid){
  assert.equal( shortCodeResponse.status, 200 )
}
```

Tries to update the shortcode's properties, and returns the updated resource representation if successful.

```javascript
if(shortCodeSid){
  shortCodeUpdateResponse = client.Accounts.AccountSid(rootAccountSid).SMS.ShortCodes.ShortCodeSid().json.post({})
}
```

```javascript
if(shortCodeSid){
  assert.equal( shortCodeUpdateResponse.status, 200 )
}
```

Returns a list of messages associated with your account. The list includes paging information

```javascript
messagesResponse = client.Accounts.AccountSid(rootAccountSid).Messages.json.get()
```

```javascript
assert.equal( messagesResponse.status, 200 )
msgSid = messagesResponse.body.messages.length==0?null:messagesResponse.body.messages[0].sid
```

To send a new outgoing message, make an HTTP POST to your Messages list resource URI

```javascript
doSend = window.confirm("Do you want to test sending messages?")
if(doSend){
  number = selectPhoneNumber("Please, select the \"from\" number by entering its index.")
  receiver = prompt("Please, enter the \"To\" number. Press \"cancel\" to use the same number as \"from\".")
  if(!receiver){
    receiver = number.phone_number
  }
  messageCreateResponse = client.Accounts.AccountSid(rootAccountSid).Messages.json.post({
    "From": number.phone_number,
    "To": receiver,
    "Body" : "API Notebook test message"
  })
}
```

```javascript
if(doSend){
  assert.equal( messageCreateResponse.status, 201 )
  msgSid = messageCreateResponse.body.sid
}
```

Returns a list of media associated with your message

```javascript
//Need US shortcodes or Canadian long numbers to invoke this method
//mediaResponse = client.Accounts.AccountSid(rootAccountSid).Messages.MessageSid(msgSid).Media.json.get()
// mediaResponse = client("/Accounts/{AccountSid}/Messages/{MessageSid}/Media.json",{
//   AccountSid : rootAccountSid,
//   MessageSid : msgSid
// }).get()
```

```javascript
// assert.equal( mediaResponse.status, 200 )
// mediaSid = null
```

Without an extension, the media is returned using the mime-type provided when the media was generated

```javascript
//Need US shortcodes or Canadian long numbers to invoke this method
//mediaResponse = client.Accounts.AccountSid(rootAccountSid).Messages.MessageSid(msgSid).Media.MediaSid(mediaSid).json.get()
```

```javascript
//assert.equal( mediaResponse.status, 200 )
```

Returns a single message specified by the provided {MessageSid}.

```javascript
if(msgSid){
  messageResponse = client.Accounts.AccountSid(rootAccountSid).Messages.MessageSid(msgSid).json.get()
}
```

```javascript
if(msgSid){
  assert.equal( messageResponse.status, 200 )
}
```

To make a call, make an HTTP POST request. Initiate a new phone call

```javascript
doCall = window.confirm("Do you want to test making voice calls?")
if(doCall){
  number = selectPhoneNumber("Please, select the \"from\" number by entering its index.")
  receiver = prompt("Please, enter the \"To\" number. Press \"cancel\" to use the same number as \"from\".")
  if(!receiver){
    receiver = number.phone_number
  }
  
  twimlUri = prompt("Please, enter your URI which points to a TwiML document similar to\n\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n</Response>\n   <Say voice=\"woman\">\n      Hello. This is a Twilio API Notebook test. Thank you for participation.\n   </Say>\n</Response>")
  
  callMakeResponse = client.Accounts.AccountSid(rootAccountSid).Calls.json.post({
    "From": number.phone_number,
    "To": receiver,
    "Url": twimlUri,
    "Record": true
  })
}
```

```javascript
if(doCall){
  var status = callMakeResponse.status  
  if(status==400 && callMakeResponse.body.code==21215){
    window.alert(callMakeResponse.body.message)
  }
  else{
    assert.equal( callMakeResponse.status,201 )
  }
}
```