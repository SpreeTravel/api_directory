---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6877/preview
apiNotebookVersion: 1.1.66
title: Conferences
---

This notebook asks you to provide an **incoming phone number** and a URI which points to a TwiML document similar to
```
<?xml version="1.0" encoding="UTF-8"?>
<Response>
   <Say voice="woman">Hello. You are going to be redirected into the {CONFERENCE_NAME} conference.</Say>
   <Dial>
      <Conference startConferenceOnEnter="true" endConferenceOnExit="true" record="record-from-start">
        {CONFERENCE_NAME}
      </Conference>      
    </Dial>
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

Returns a list of conferences within an account. The list includes paging
information.

```javascript
conferencesResponse = client.Accounts.AccountSid(rootAccountSid).Conferences.json.get()
```

```javascript
assert.equal(conferencesResponse.status,200)
```

```javascript
conferenceSid = null;
doCreate = window.confirm("If you wish to call those methods which deal with a conference and its participants, you must initialize a conference.\n\nDo you want to initialize a conference now?")
  
if(doCreate){
  number = selectPhoneNumber("Please, select the incoming phone number by entering its index.\nThis number will be configured to start a conference when you call it.")
  if(number){
    twimlUri = prompt("Please, enter your URI which points to a TwiML document similar to\n\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n</Response>\n   <Say voice=\"woman\">\n      Hello. You are going to be redirected into the {CONFERENCE_NAME} conference.\n   </Say>\n   <Dial>\n      <Conference startConferenceOnEnter=\"true\" endConferenceOnExit=\"true\" record=\"record-from-start\">\n         {CONFERENCE_NAME}\n      </Conference>\n   </Dial>\n</Response>")
    conferenceName = prompt("Please, enter name of your conference.")

    client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(number.sid).json.post({
      VoiceUrl : twimlUri,
      VoiceMethod: "GET",
      VoiceApplicationSid : ""
    })
    window.alert("Now " + number.friendly_name + " is configured to start a conference. Before continuing Notebook execution, please, make a call and wait until the music starts playing. Notebook will end the call by itself. Do not do it yourself.")
    conferencesResponse = client.Accounts.AccountSid(rootAccountSid).Conferences.json.get()
    conferences = conferencesResponse.body.conferences
    for(var ind in conferences){
      var conf = conferences[ind]
      if(conf.friendly_name == conferenceName){
        conferenceSid = conf.sid
        break
      }
    }
    if(!conferenceSid){
      window.alert("Your conference is not found. Something has gone wrong.")
    }      
  }
}  
```

```javascript
assert.equal( conferencesResponse.status, 200 )
conferenceSid = conferencesResponse.body.conferences.length==0?null:conferencesResponse.body.conferences[0].sid
```

Returns a representation of the conference identified by {ConferenceSid}.

```javascript
if(conferenceSid){
  conferenceResponse = client("/Accounts/{AccountSid}/Conferences/{ConferenceSid}.json", {
    AccountSid : rootAccountSid,
    ConferenceSid : conferenceSid
  }).get()
}
```

```javascript
if(conferenceSid){
  assert.equal( conferenceResponse.status, 200 )
}
```

Returns the list of participants in the conference.

```javascript
if(conferenceSid){
  participantsResponse = client.Accounts.AccountSid(rootAccountSid).Conferences.ConferenceSid(conferenceSid).Participants.json.get()
}
```

```javascript
if(conferenceSid){
  assert.equal( participantsResponse.status, 200 )
  participantSid = participantsResponse.body.participants[0].call_sid
}
```

Returns a representation of this participant

```javascript
if(participantSid){
  participantResponse = client.Accounts.AccountSid(rootAccountSid).Conferences.ConferenceSid(conferenceSid).Participants.CallSid(participantSid).json.get()
}
```

```javascript
assert.equal( participantResponse.status, 200 )
```

Updates the status of a participant

```javascript
participantUpdateResponse = client.Accounts.AccountSid(rootAccountSid).Conferences.ConferenceSid(conferenceSid).Participants.CallSid(participantSid).json.post({})
```

```javascript
if(participantSid){
  assert.equal( participantUpdateResponse.status, 200 )
}
```

Kick this participant from the conference

```javascript
if(participantSid){
  participantDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Conferences.ConferenceSid(conferenceSid).Participants.CallSid(participantSid).json.delete()
}
```

```javascript
if(participantSid){
  assert.equal( participantDeleteResponse.status, 204 )
}
```