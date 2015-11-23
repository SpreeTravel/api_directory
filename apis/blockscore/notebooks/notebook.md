---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/organizations/52560d3f-c37a-409d-9887-79e0a9a9ecff/dashboard/apis/23625/versions/25119/portal/pages/38957/edit
apiNotebookVersion: 1.1.67
title: BlockScore
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript
API.createClient('client', '#REF_TAG_DEFENITION');
```

Input api key.

```javascript
apiKey = prompt("Please enter API Key")
authKey = btoa(apiKey + ":")
```

Function for setting header parameters.

```javascript
function addOutHeaders(o){
	o.setRequestHeader("Accept", "application/vnd.blockscore+json;version=4");
 	o.setRequestHeader("Authorization",  "Basic " + authKey);
}
```

```javascript
API.set(client, 'beforeSend', new function(){return addOutHeaders})
```

This endpoint creates a new person. The information will be run through our verification process and then returned with additional information that will help you determine the authenticity of the information given

```javascript
personCreateResponse = client.people.post({}, {query:{
  "name_first": "Test",
  "name_last": "NotebookPerson",
  "document_type": "ssn",
  "document_value": "0000",
  "birth_day": "23",
  "birth_month": "8",
  "birth_year": "1980",
  "address_street1": "1 Infinite Loop",
  "address_city": "Cupertino",
  "address_subdivision": "CA",
  "address_postal_code": "95014",
  "address_country_code": "US"
}})
```

```javascript
assert.equal( personCreateResponse.status, 201 )
ID_PERSON = personCreateResponse.body.id
```

This endpoint will allow you to see a historical record of all verifications that you have completed.

The list is displayed in reverse chronological order (newer people appear first).

```javascript
peopleResponse = client.people.get()
```

```javascript
assert.equal( peopleResponse.status, 200 )
```

You can pull up a single person at any time (typically this is used for auditing purposes)

```javascript
personResponse = client.people.PERSON_ID(ID_PERSON).get()
```

```javascript
assert.equal( personResponse.status, 200 )
```

This method will create a new question set to ask your users. You can call this endpoint multiple times with the same person ID and the questions asked as well as the order that everything is presented in will be randomized

```javascript
questionSetsCreateResponse = client.question_sets.post({},  {query:{
  "person_id": ID_PERSON
}})
```

```javascript
assert.equal( questionSetsCreateResponse.status, 201 )
ID_QUESTION_SET = questionSetsCreateResponse.body.id
ID_QUESTION = questionSetsCreateResponse.body.questions[1].id
ID_ANSWER = questionSetsCreateResponse.body.questions[1].answers[1].id
```

List all question sets

This endpoint will allow you to see a historical record of all question sets that you have created.

The list is displayed in reverse chronological order (newer question sets appear first).

```javascript
questionSetsResponse = client.question_sets.get()
```

```javascript
assert.equal( questionSetsResponse.status, 200 )
```

Retrieve an existing question set

This allows you to retrieve a question set you have created. If you have already scored the question set, we will also return the last score of your submitted answers.

```javascript
questionSetResponse = client.question_sets.QUESTION_SET_ID(ID_QUESTION_SET).get()
```

```javascript
assert.equal( questionSetResponse.status, 200 )
```

Once the user has finished answering the questions, you will need to send us their answers so that we can score it. The scoring system will score it based on how many questions you submit, so if you would only like to ask 3 questions, only submit the 3 questions which you would like scored

An array of objects containing question_id and answer_id pairs. 

```javascript
QUESTION_PARAMS = "answers[]["+ ID_QUESTION +"]=answers[][" + ID_ANSWER + "]=3"
```

```javascript
scoreCreateResponse = client.question_sets.QUESTION_SET_ID(ID_QUESTION_SET).score.post({}, {query:
  QUESTION_PARAMS
})
```

```javascript
assert.equal( scoreCreateResponse.status, 201 )
ID_SCORE = scoreCreateResponse.body.id
```

Create a new company

This endpoint creates a new company. The information will be run through our company verification process and then returned with additional information that will help you determine the authenticity of the information given.

Please be aware that the response time can sometimes be more than 6 seconds due to the speed of some government data sources.

```javascript
companiesCreateResponse = client.companies.post({}, {query:{
  "entity_name": "BlockScore",
  "tax_id": "123410000",
  "incorporation_country_code": "US",
  "incorporation_type": "corporation",
  "address_street1": "1 Infinite Loop",
  "address_city": "Cupertino",
  "address_subdivision": "CA",
  "address_postal_code": "95014",
  "address_country_code": "US"
 }})
```

```javascript
assert.equal( companiesCreateResponse.status, 201 )
ID_COMPANY = companiesCreateResponse.body.id
```

List all companies

This endpoint will allow you to see a historical record of all company verifications that you have completed.

The list is displayed in reverse chronological order (newer company verifications appear first).

```javascript
companiesResponse = client.companies.get()
```

```javascript
assert.equal( companiesResponse.status, 200 )
```

Retrieve an existing company

You can pull up a single company verification at any time (typically this is used for auditing purposes).

```javascript
companyResponse = client.companies.COMPANY_ID(ID_COMPANY).get()
```

```javascript
assert.equal( companyResponse.status, 200 )
```

Create a new candidate.
This endpoint creates a new candidate.

```javascript
candidatesCreateResponse = client.candidates.post()
```

```javascript
assert.equal( candidatesCreateResponse.status, 201 )
ID_CANDIDATE = candidatesCreateResponse.body.id
```

List all candidates

This endpoint will allow you to see a historical record of all candidates you have created.

The list is displayed in reverse chronological order (newer candidates appear first).

```javascript
candidatesResponse = client.candidates.get()
```

```javascript
assert.equal( candidatesResponse.status, 200 )
```

Retrieve an existing candidate

This endpoint allows you to retrieve the record of a candidate using their stored candidate_id.

```javascript
CANDIDATEIDResponse = client.candidates.CANDIDATE_ID(ID_CANDIDATE).get()
```

```javascript
assert.equal( CANDIDATEIDResponse.status, 200 )
```

Edit a candidate
If you'd like to amend the information associated with a candidate, you can modify it using this route. For instance, if your candidate changes addresses or you learn additional information about them. Only the information you send us will be updated - the rest will remain the same.

```javascript
CANDIDATEIDPatchResponse = client.candidates.CANDIDATE_ID(ID_CANDIDATE).patch()
```

```javascript
assert.equal( CANDIDATEIDPatchResponse.status, 200 )
```

View a candidate's revision history

We store a complete revision history of a candidate's edits. This allows you to maintain a full audit trail of when and how you update a client's profile over time.

The latest revision is presented at the top of the array, and the original is at the end of the array.

```javascript
historyResponse = client.candidates.CANDIDATE_ID(ID_CANDIDATE).history.get()
```

```javascript
assert.equal( historyResponse.status, 200 )
```

View a candidate's past hits

Whenever a search is executed for a candidate, we store the results of that search with timestamps of when those hits occurred. This endpoint allows you to view all historical watchlist hits for a given candidate.

```javascript
hitsResponse = client.candidates.CANDIDATE_ID(ID_CANDIDATE).hits.get()
```

```javascript
assert.equal( hitsResponse.status, 200 )
```

This endpoint creates a new verification. The information will be run through our verification process and then returned with additional information that will help you determine the authenticity of the information given.

```javascript
watchlistsCreateResponse = client.watchlists.post({}, {query:{
  "candidate_id": ID_CANDIDATE
                                                      }})
```

```javascript
assert.equal( watchlistsCreateResponse.status, 200 )
```

Delete a candidate
This endpoint allows you to remove a candidate from our system. If you have signed up for continuous verification, then the deleted candidate will no longer be scanned.

```javascript
deleteCandidateResponse = client.candidates.CANDIDATE_ID(ID_CANDIDATE).delete()
```

```javascript
assert.equal( deleteCandidateResponse.status, 200 )
```