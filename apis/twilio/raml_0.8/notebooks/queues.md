---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6881/preview
apiNotebookVersion: 1.1.66
title: Queues
---

This notebook asks you to provide an **incoming phone number** and a URI which points to a TwiML document similar to
```
<?xml version="1.0" encoding="UTF-8"?>
<Response>
   <Enqueue waitUrl="http://example.com/QueueMessage.xml" method="GET">{QUEUE_NAME}</Enqueue>
</Response>
```
where ```http://example.com/QueueMessage.xml``` is like
```
<?xml version="1.0" encoding="UTF-8"?>
<Response>    
   <Say voice="woman">Hello. You have been put into the {QUEUE_NAME} queue. You may continue executing the notebook.</Say>
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

```javascript
doCreate = window.confirm("If you wish to call those methods which deal with queue members members, you must have one of your incoming phone numbers configured to put all callers into a queue.\n\nDo you want to configure a phone number now?")
  
if(doCreate){
  number = selectPhoneNumber("Please, select the incoming phone number by entering its index.\nThis number will be configured to put callers into a queue.")
  if(number){
    twimlUri = prompt("Please, enter your URI which points to a TwiML document similar to\n\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n</Response>\n   </Say>\n   <Enqueue waitUrl=\"http://example.com/QueueMessage.xml\" method=\"GET\">{QUEUE_NAME}</Enqueue>\n   <Record timeout=\"15\" transcribe=\"true\"/>\n</Response>\n\nwhere http://example.com/QueueMessage.xml is like\n\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Response>\n   <Say voice=\"woman\">Hello. You have been put into the {QUEUE_NAME} queue. You may continue executing the notebook.</Say>\n</Response>")
    queueName = prompt("Please, enter name of your queue")

    client.Accounts.AccountSid(rootAccountSid).IncomingPhoneNumbers.IncomingPhoneNumberSid(number.sid).json.post({
      VoiceUrl : twimlUri,
      VoiceMethod: "GET",
      VoiceApplicationSid : ""
    })
    var queuesResponse = client.Accounts.AccountSid(rootAccountSid).Queues.json.get()
    var queues = queuesResponse.body.queues
    for(var ind in queues){
      var queue = queues[ind]
      if(queueName==queue.friendly_name){
        client("/Accounts/{AccountSid}/Queues/{QueueSid}.json",{
          AccountSid: rootAccountSid,
          QueueSid: queue.sid
        }).delete({})
      }
    }
    window.alert("Now " + number.friendly_name + " is configured to put all callers into a queue.\n\nBut DO NOT CALL RIGHT NOW!")
  }
}
```

Returns a list of queues within an account. The list includes paging
information.

```javascript
queuesResponse = client.Accounts.AccountSid(rootAccountSid).Queues.json.get()
```

```javascript
assert.equal( queuesResponse.status, 200 )
```

Create a new Queue resource.

```javascript
queueCreateResponse = client.Accounts.AccountSid(rootAccountSid).Queues.json.post({
  "FriendlyName" : queueName,
  "MaxSize": 10
})
```

```javascript
assert.equal( queueCreateResponse.status, 201 )
queueSid = queueCreateResponse.body.sid
```

Get resource's individual Queue instance.

```javascript
//queueResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).json.get()
queueResponse = client("/Accounts/{AccountSid}/Queues/{QueueSid}.json",{
  AccountSid: rootAccountSid,
  QueueSid: queueSid
}).get()
```

```javascript
assert.equal( queueResponse.status, 200 )
```

This POST request allows you to change the FriendlyName or MaxSize.

```javascript
queueUpdateResponse = client("/Accounts/{AccountSid}/Queues/{QueueSid}.json",{
  AccountSid: rootAccountSid,
  QueueSid: queueSid
}).post({MaxSize: 11},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})
```

```javascript
assert.equal( queueUpdateResponse.status, 200 )
```

```javascript
window.alert("Right now the queue does not contain any members. Please, call the number before you continue to execute the notebook.")
```

Returns the list of members in the queue identified by {QueueSid}

```javascript
membersResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).Members.json.get()
```

```javascript
assert.equal( membersResponse.status, 200 )
memberSid=membersResponse.body.queue_members.length==0?null:membersResponse.body.queue_members[0].call_sid
```

Get a specific member

```javascript
memberResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).Members.CallSid(memberSid).json.get()
```

```javascript
assert.equal( memberResponse.status, 200 )
```

Posting a URL and Method to a Queue instance will dequeue a member from a
queue and have the member's call begin executing the TwiML document at that URL
When redirecting a member of a queue addressed by CallSid, only the first request
will succeed and return a 200 response code. A second request will fail and
return an appropriate 400 response code.

```javascript
memberUpdateResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).Members.CallSid(memberSid).json.post({
  "Url": "http://demo.twilio.com/docs/voice.xml"
})
```

```javascript
assert.equal( memberUpdateResponse.status, 200 )
```

```javascript
window.alert("You have just been redirected from the queue, thus, it does not contain any members.\n\nAs we need one more queue member, please, finish the current call now and call the same number once again before you continue to execute the notebook.")
```

Get a front member

```javascript
frontMemberResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).Members.Front.json.get()
```

```javascript
assert.equal( frontMemberResponse.status, 200 )
```

Posting a URL and Method to a Queue instance will dequeue a member from a
queue and have the member's call begin executing the TwiML document at that URL
When dequeuing the 'Front' of the queue, the next call in the queue will be redirected.

```javascript
frontMemberCreateResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).Members.Front.json.post({
  "Url": "http://demo.twilio.com/docs/voice.xml"
})
```

```javascript
assert.equal( frontMemberCreateResponse.status, 200 )
```

The DELETE method allows you to remove a Queue. Only empty queues are deletable.

```javascript
//queueDeleteResponse = client.Accounts.AccountSid(rootAccountSid).Queues.QueueSid(queueSid).json.delete({})
queueDeleteResponse = client("/Accounts/{AccountSid}/Queues/{QueueSid}.json",{
  AccountSid: rootAccountSid,
  QueueSid: queueSid
}).delete({})
```

```javascript
assert.equal( queueDeleteResponse.status, 204 )
```

```javascript
window.alert("Do not forget to finish your call.")
```