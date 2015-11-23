---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6876/preview
apiNotebookVersion: 1.1.66
title: Transcriptions
---

If your account has no transcriptions, this notebook will asks you to provide an **incoming phone number** and a URI which points to a TwiML document similar to
```
<?xml version="1.0" encoding="UTF-8"?>
<Response>    
    <Say voice="woman">
      Hello. We are going to record your speech and transcribe it for purposes of testing API Notebook.
    </Say>
    <Record timeout="15" transcribe="true"/>
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


Returns a set of Transcription resource representations that includes paging
information.


```javascript
transcriptionsResponse = client.Accounts.AccountSid(rootAccountSid).Transcriptions.json.get()
```

```javascript
assert.equal( transcriptionsResponse.status, 200 )
transcriptionSid = transcriptionsResponse.body.transcriptions.length==0?null:transcriptionsResponse.body.transcriptions[0].sid
```

```javascript
doCreate = false
if(!transcriptionSid){
  doCreate = window.confirm("If you wish to call those methods which retrieve or delete a particular transcription, you must have at least one transcription available inside your account.\n\nDo you want to create a transcription now?")
}
else{
  doCreate = window.confirm("Do you want to create a new transcription?")
}
if(doCreate){
  number = selectPhoneNumber("Please, select the incoming phone number by entering its index.\nThis number will be configured for recording and transcribing your speech.")
  if(number){
    twimlUri = prompt("Please, enter your URI which points to a TwiML similar to\n\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n</Response>\n   <Say voice=\"woman\">\n      Hello. We are going to record your speech and transcribe it.\n   </Say>\n   <Record timeout=\"15\" transcribe=\"true\"/>\n</Response>")

    client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(number.sid).json.post({
      VoiceUrl : twimlUri,
      VoiceMethod: "GET",
      VoiceApplicationSid : ""
    })
    window.alert("Now " + number.friendly_name + " is configured to record and transcribe speech. Before continuing Notebook execution, please, make a call and wait a little while Twilio is processing your recording.")

    transcriptionsResponse = client.Accounts.AccountSid(rootAccountSid).Transcriptions.json.get()
    transcriptionSid = transcriptionsResponse.body.transcriptions.length==0?null:transcriptionsResponse.body.transcriptions[0].sid
  }
  if(!transcriptionSid){
    window.alert("No transcriptions are available. Something has gone wrong.")
  }
}
```

Returns a single Transcription resource representation identified by the
given {TranscriptionSid}. By default Twilio will respond with the XML metadata for the Transcription. If you append ".txt" to the end of the Transcription resource's URI Twilio will just return you the transcription tex.


```javascript
if(transcriptionSid){
  transcriptionResponse = client.Accounts.AccountSid(rootAccountSid).Transcriptions.TranscriptionSid(transcriptionSid).json.get()
}
```

```javascript
if(transcriptionSid){
  assert.equal( transcriptionResponse.status, 200 )
}
```

If the transcription is successful, we can take a look at it's text

```javascript
if(transcriptionSid){
  status = transcriptionResponse.body.status
  if(status&&status=="completed"){
    window.alert("Text of your transcription:\n\n " + transcriptionResponse.body.transcription_text)
  }
}
```

Deletes a transcription from your account

```javascript
doDelete = false
if(transcriptionSid){
  doDelete = window.confirm("Do you want to delete the transcription?")
  if(doDelete){
    transcriptionDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Transcriptions.TranscriptionSid(transcriptionSid).json.delete({})
  }
}
```

```javascript
if(doDelete){
  assert.equal( transcriptionDeleteResponse.status, 204 )
}
```