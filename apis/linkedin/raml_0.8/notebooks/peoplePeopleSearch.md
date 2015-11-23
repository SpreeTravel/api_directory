---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8086/versions/8276/portal/pages/6953/preview
apiNotebookVersion: 1.1.66
title: People, people-search
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

CLIENT_ID = prompt("Please, enter clientId of your LinkedIn application."),

CLIENT_SECRET = prompt("Please, enter clientSecret of your LinkedIn application.")

```

```javascript

MY_GROUP_NAME = prompt("Please, enter name of your LinkedIn group.")

```

```javascript

// Read about the Linked In API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8086/versions/8276/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8086/versions/8276/definition');

```

```javascript

API.authenticate(client,"oauth_2_0",{

  clientId : CLIENT_ID,

  clientSecret : CLIENT_SECRET

})

```

```javascript

ACCESS_TOKEN = $5.accessToken

```



Retreive group under the authenticated user's ownership



```javascript

MY_GROUP_ID = null

start = 0

count = 5

total = start + count + 1

while( start < total ){

  groupMembershipsResponse = client.people["~"]["group-memberships"]("").get({

    start : start,

    count : count,

    oauth2_access_token : ACCESS_TOKEN,

    format: "json"

  })

  gmArray = groupMembershipsResponse.body.values

  for(var ind in gmArray){

    var name = gmArray[ind].group.name

    if(name == MY_GROUP_NAME){

      MY_GROUP_ID = gmArray[ind].group.id

      break

    }

  }

  if(MY_GROUP_ID){

    break

  }

  total = groupMembershipsResponse.body._total

  start += count

}

```



Returns profile of the current user



```javascript

currentUserResponse = client.people["~"]("").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( currentUserResponse.status, 200 )

```



Returns Group Memberships for a User



```javascript

groupMembershipsResponse = client.people["~"]["group-memberships"]("").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( groupMembershipsResponse.status, 200 )

```

```javascript

GROUP_ID = prompt("We are about to test applying for group membership and leaving a group. Please, enter ID of a group you wish to involve in these tests. In order to find out the ID open the group's page and look at the 'gid' query parameter value in the URL.")

```



Apply for group membership



```javascript

if(GROUP_ID){

  groupMembershipCreateResponse = client.people["~"]["group-memberships"]("").post({

    "group" : {

      "id" : GROUP_ID

    }

  },{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

}

```

```javascript

if(GROUP_ID){

  assert.equal( groupMembershipCreateResponse.status, 201 )

}

```



Leave a Group



```javascript

if(GROUP_ID){

  groupIdFieldSelectorsDeleteResponse = client("/people/~/group-memberships/{groupId}{fieldSelectors}", {

    "groupId": GROUP_ID

  }).delete({},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

}

```

```javascript

if(GROUP_ID){

  assert.equal( groupIdFieldSelectorsDeleteResponse.status, 204 )

}

```



Returns Group settings



```javascript

groupIdResponse = client("/people/~/group-memberships/{groupId}{fieldSelectors}", {

  "groupId": MY_GROUP_ID

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( groupIdResponse.status, 200 )

```



Change Group settings



```javascript

groupUpdateResponse = client("/people/~/group-memberships/{groupId}{fieldSelectors}", {

  "groupId": MY_GROUP_ID

}).put({

  "show-group-logo-in-profile" : "true"

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( groupUpdateResponse.status, 200 )

```



Returns a Group's Discussion Posts



```javascript

groupPostsResponse = client.people["~"]["group-memberships"].groupId(MY_GROUP_ID).posts("").get({

  oauth2_access_token : ACCESS_TOKEN,

  format: "json",

  "role": "creator"

})

```

```javascript

assert.equal( groupPostsResponse.status, 200 )

```



Returns Job Bookmarks



```javascript

jobBookmarksResponse = client.people["~"]["job-bookmarks"].get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( jobBookmarksResponse.status, 200 )

```



Bookmarking a Job



```javascript

jobBookmarkCreateResponse = client.people["~"]["job-bookmarks"].post({

  "job" : {

    "id" : 17400681

  }

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( jobBookmarkCreateResponse.status, 201 )

```



Deleting a Job Bookmark



```javascript

jobDeleteResponse = client.people["~"]["job-bookmarks"].jobId(17400681).delete({},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( jobDeleteResponse.status, 204 )

```



Get Suggested Groups for a User



```javascript

suggestedGroupsResponse = client.people["~"].suggestions.groups("").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( suggestedGroupsResponse.status, 200 )

```



Remove a Group Suggestion for a User



```javascript

groupSuggestionDeleteResponse = client.people["~"].suggestions.groups("").groupId(MY_GROUP_ID).delete({},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( groupSuggestionDeleteResponse.status, 204 )

```



Retrieving a List of a Member's Suggested Jobs



```javascript

jobSuggestionsResponse = client.people["~"].suggestions["job-suggestions"]("").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( jobSuggestionsResponse.status, 200 )

```



You can retrieve a collection of suggested companies for the current user



```javascript

toFollowCompaniesResponse = client.people["~"].suggestions["to-follow"].companies.get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( toFollowCompaniesResponse.status, 200 )

```



Returns a list of 1st degree connections for a user who has granted access to his/her account



```javascript

connectionsResponse = client.people["~"].connections("").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( connectionsResponse.status, 200 )

```



Retrieve the member's first-degree connection updates



```javascript

updatesResponse = client.people["~"].network.updates.get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( updatesResponse.status, 200 )

updateKey = updatesResponse.body.values[0].updateKey

```



Use a similar syntax to retrieve the complete list of people who liked an update



```javascript

likesResponse = client("/people/~/network/updates/key={NetworkUpdateKey}/likes",{NetworkUpdateKey:updateKey}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( likesResponse.status, 200 )

```



By default, a network update containing more than 10 comments will only return you the most recent five. To retrieve all comments for a given network update



```javascript

commentsResponse = client("/people/~/network/updates/key={NetworkUpdateKey}/update-comments", {

  "NetworkUpdateKey": updateKey

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( commentsResponse.status, 200 )

```



When you want to post a new comment to an existing update:

Ensure update/is-commentable is set to true

Take the update/update-key from an existing update and POST to the following URL structure:



```javascript

commentCreateResponse = client("/people/~/network/updates/key={NetworkUpdateKey}/update-comments", {

  "NetworkUpdateKey": updateKey

}).post({

   "comment" : "This comment was created by the 'LinkedIn' API Notebook"

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( commentCreateResponse.status, 201 )

```



A successful comment PUT will return a 201 Content Created status code



```javascript

isLikedUpdateResponse = client("/people/~/network/updates/key={NetworkUpdateKey}/is-liked", {

  "NetworkUpdateKey": updateKey

}).put("true",{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"},headers:{"Content-Type":"application/json"}})

```

```javascript

assert.equal( isLikedUpdateResponse.status, 201 )

```



Information about the member's network, such as how many connections they have one and two degrees away



```javascript

networkStatsResponse = client.people["~"].network["network-stats"].get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( networkStatsResponse.status, 200 )

```



The Post Network Update API allows you to send in an activity from your site to be posted to the first degree connections of the LinkedIn user. Network updates are the LinkedIn term for news feed activities and appear on the center column of the home page. Use it when a user on your site performs some activity on your site that they want to broadcast to their connections. For example, you might send a message "Peter Smith booked a trip to London, England on October 30.



```javascript

activityCreateResponse = client.people["~"]["person-activities"].post({

  body : "API Testing. Nothing interesting."

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( activityCreateResponse.status, 201 )

```



Post a share



```javascript

shareCreateResponse = client.people["~"].shares.post({

  "comment" : "Check out the LinkedIn Share API! " + new Date().getTime(),

  "content" : {

    "title" : "LinkedIn Developers Documentation On Using the Share API " + new Date().getMilliseconds,

    "description" : "Leverage the Share API to maximize engagement on user-generated content on LinkedIn",

    "submitted-url" : "https://developer.linkedin.com/documents/share-api",

    "submitted-image-url" : "https://m3.licdn.com/media/p/3/000/124/1a6/089a29a.png",

  } ,

  "visibility" : {

    "code" : "anyone"

  }

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( shareCreateResponse.status, 201 )

```



This call requires that you identify only the person being invited. The inviter will always come from the access token you use when you make the call. There are two methods of identifying the user being invited:

Member ID + auth token: If the invitee was found using a search API call, then the search result will have included the member ID and auth token to make the API call. Use both of those to identify the member to invite.

Email: For all other cases, you can use email address to identify the member to invite.



```javascript

mailboxCreateResponse = client.people["~"].mailbox.post({

  "recipients": {

    "values": [

      {

        "person": {

          "_path": "/people/~"

        }

      }

    ]

  },

  "subject": "Congratulations on your new position.",

  "body": "You are certainly the best person for the job!"

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( mailboxCreateResponse.status, 201 )

```



Retrieve a list of companies a member is following



```javascript

followingCompaniesResponse = client.people["~"].following.companies.get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( followingCompaniesResponse.status, 200 )

```



Start following a company



```javascript

followingCompaniesCreateResponse = client.people["~"].following.companies.post({

  id : 1441

},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( followingCompaniesCreateResponse.status, 201 )

```



Stop following a company



```javascript

followingCompaniesDeleteResponse = client("/people/~/following/companies/id={id}",{id : 1441}).delete({},{query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( followingCompaniesDeleteResponse.status, 204 )

```



Returns profile of user by URL



```javascript

urlPublicProfileUrlResponse = client("/people/url={publicProfileUrl}{fieldSelectors}", {

  "publicProfileUrl": "http://www.linkedin.com/pub/henry-montes/23/9a/184"

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( urlPublicProfileUrlResponse.status, 200 )

```



Retrieve a public profile url of the authenticated user



```javascript

currentUserResponse2 = client("/people/~:(id,public-profile-url)").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

publicProfileUrl = currentUserResponse2.body.publicProfileUrl

ownId = currentUserResponse2.body.id

```



Returns a list of 1st degree connections for a user who has granted access to his/her accoun



```javascript

userConnectionsResponse = client("/people/url={publicProfileUrl}/connections{fieldSelectors}", {

  "publicProfileUrl": publicProfileUrl

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( userConnectionsResponse.status, 200 )

```



Returns profile of user by ID



```javascript

idPeopleIdResponse = client("/people/id={peopleId}{fieldSelectors}", {

  "peopleId": ownId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( idPeopleIdResponse.status, 200 )

```



Returns a list of 1st degree connections for a user who has granted access to his/her account



```javascript

userConnections2Response = client("/people/id={peopleId}/connections{fieldSelectors}", {

  "peopleId": ownId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( userConnections2Response.status, 200 )

```



Returns information about people



```javascript

//Not supported

peopleSearchResponse = client("/people-search{fieldSelectors}").get({"first-name":"John",oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert( peopleSearchResponse.status == 200 || peopleSearchResponse.status == 403 )

```