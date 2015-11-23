---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6875/preview
apiNotebookVersion: 1.1.66
title: Recordings
---

If your account has no recordings, this notebook will asks you to provide an **incoming phone number** and a URI which points to a TwiML document similar to
```
<?xml version="1.0" encoding="UTF-8"?>
<Response>    
    <Say voice="woman">Hello. We are going to record your speech for purposes of testing API Notebook. Thank you.</Say>
    <Record timeout="15"/>
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


Returns a list of Recording resource representations, each representing a
recording generated during the course of a phone call.

```javascript
recordingsResponse = client.Accounts.AccountSid(rootAccountSid).Recordings.json.get()
```

```javascript
assert.equal( recordingsResponse.status, 200 )
recordingSid = recordingsResponse.body.recordings.length==0?null:recordingsResponse.body.recordings[0].sid
```

```javascript
if(!recordingSid){
  doRecord = window.confirm("If you wish to call those methods which retrieve or delete a particular recording, you must have at least one recording available inside your account.\n\nDo you want to create a recording now?")
}
else{
  doRecord = window.confirm("Do you want to create a new recording?")
}
if(doRecord){  
  number = selectPhoneNumber("Please, select the incoming phone number by entering its index.\nThis number will be configured for recording your speech.")
  if(number){
  twimlUri = prompt("Please, enter your URI which points to a TwiML document similar to\n\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n</Response>\n   <Say voice=\"woman\">\n      Hello. We are going to record your speech for purposes of testing API Notebook. Thank you.\n   </Say>\n   <Record timeout=\"15\"/>\n</Response>")
  }
  client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(number.sid).json.post({
    VoiceUrl : twimlUri,
    VoiceMethod: "GET",
    VoiceApplicationSid : ""
  })
  window.alert("Now " + number.friendly_name + " is configured to record speech. Before continuing Notebook execution, please, make a call and wait a little while Twilio is processing your recording.")
    
  recordingsResponse = client.Accounts.AccountSid(rootAccountSid).Recordings.json.get()
  recordingSid = recordingsResponse.body.recordings.length==0?null:recordingsResponse.body.recordings[0].sid
  if(!recordingSid){
    window.alert("No recordings are available. Something has gone wrong.")
  }
}
```

Returns a set of Transcription resource representations that includes paging
information.

```javascript
if(recordingSid){
  transcriptionsResponse = client("Accounts/{AccountSid}/Recordings/{RecordingSid}/Transcriptions.json",{
    AccountSid : rootAccountSid,
    RecordingSid : recordingSid
  }).get()
}
```

```javascript
if(recordingSid){
  assert.equal( transcriptionsResponse.status, 200 )
}
```

Returns one of several representations:
Without an extension, or with a ".wav", a binary WAV audio file is returned
with mime-type "audio/x-wav".
Appending ".mp3" to the URI returns a binary MP3 audio file with mime-type
type "audio/mpeg".
Appending ".xml" to the URI returns a XML representation.

```javascript
if(recordingSid){
  recordingResponse = client.Accounts.AccountSid(rootAccountSid).Recordings.RecordingSid(recordingSid).xml.get()
}
```

```javascript
if(recordingSid){
  assert.equal( recordingResponse.status, 200 )
}
```

Deletes a recording  from your account

```javascript
if(recordingSid){
  doDeleteRecording = window.confirm("Do you want to delete the recording permanently?")
  if(doDeleteRecording){
    recordingDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Recordings.RecordingSid(recordingSid).wav.delete({})
  }
}
```

```javascript
if(recordingSid&&doDeleteRecording){
  assert.equal( recordingDeleteResponse.status, 204 )
}
```