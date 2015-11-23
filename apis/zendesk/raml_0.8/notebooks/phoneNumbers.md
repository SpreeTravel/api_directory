---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8077/versions/8266/portal/pages/6943/preview
apiNotebookVersion: 1.1.66
title: Phone numbers
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
DOMAIN = prompt("Please, enter your Zendesk subdomain.")
CLIENT_ID = prompt("Please, enter ID of your Zendesk client.")
CLIENT_SECRET = prompt("Please, enter Secret of your Zendesk client.")
```

```javascript
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8077/versions/8266/definition',{
  baseUriParameters: {
    domain: DOMAIN,
    version: 'v2'
}});
```

```javascript
API.set(client, 'baseUriParameters', {
  domain: DOMAIN,
  version: 'v2'
})
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

```javascript
function selectPhoneNumber(msg){
  var number = null
  var pageSize = -1
  var pagesCount = 1
  var countryCode = prompt("Please enter literal country code in upper case for searching phone number")
  countryCode = countryCode.toUpperCase()
  var searchResponse = client.channels.voice.phone_numbers.search.json.get({"country":countryCode})
  for(var i = 0 ; i < pagesCount ; i++ ){
    var numbers = searchResponse.body.phone_numbers
    var message = msg
    if(i<pagesCount-1){
      message += "\nTo view the next page enter a negative value."
    }
    message += "\n"
    for( var j in numbers){
      message += "\n" + j + ": " + numbers[j].number
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

Searching for Available Phone Number

```javascript
PHONE_NUMBER = selectPhoneNumber("Please, select the phone number by entering its index.\nThis number will be created for your Zendesk account.")
```

```javascript
searchResponse = client.channels.voice.phone_numbers.search.json.get({"country":"US"})
```

```javascript
assert.equal( searchResponse.status, 200 )
PHONE_TOKEN = searchResponse.body.phone_numbers[0].token
```

Creating Phone Number

```javascript
phoneNumbersCreateResponse = client.channels.voice.phone_numbers.json.post({
  "phone_number" : {
    "token" : PHONE_NUMBER.token
  }
})
```

```javascript
assert.equal( phoneNumbersCreateResponse.status, 200 )
ID_PHONE_NUMBER = phoneNumbersCreateResponse.body.phone_number.id
```

Getting Phone Number

```javascript
phoneNumberResponse = client.channels.voice.phone_numbers.id(ID_PHONE_NUMBER).json.get()
```

```javascript
assert.equal( phoneNumberResponse.status, 200 )
```

Updating Phone Number

```javascript
phoneNumberUpdateResponse = client.channels.voice.phone_numbers.id(ID_PHONE_NUMBER).json.put({
  "phone_number" : {
    "nickname" : "Awesome Support Line"
  }
})
```

```javascript
assert.equal( phoneNumberUpdateResponse.status, 200 )
```

Listing Phone Numbers

```javascript
phoneNumbersListResponse = client.channels.voice.phone_numbers.json.get()
```

```javascript
assert.equal( phoneNumbersListResponse.status, 200 )
```

Deleting Phone Number

```javascript
phoneNumbersDeleteResponse = client.channels.voice.phone_numbers.id(ID_PHONE_NUMBER).json.delete()
```

```javascript
assert.equal( phoneNumbersDeleteResponse.status, 200 )
```