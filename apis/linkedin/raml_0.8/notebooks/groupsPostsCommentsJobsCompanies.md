---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8086/versions/8276/portal/pages/6954/preview
apiNotebookVersion: 1.1.66
title: Groups, posts, comments, jobs, companies
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

MY_COMPANY_NAME = prompt("Please, enter name of your LinkedIn company.")

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



Retrieve group under the authenticated user's ownership



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



Retrieve company under the authenticated user's ownership



```javascript

MY_COMPANY_ID = null

start = 0

count = 5

total = start + count + 1

while( start < total && MY_COMPANY_NAME ){

  followingCompaniesResponse = client.people["~"].following.companies.get({

    start : start,

    count : count,

    oauth2_access_token : ACCESS_TOKEN,

    format: "json"

  })

  cArray = followingCompaniesResponse.body.values

  for(var ind in cArray){

    var name = cArray[ind].name

    if(name == MY_COMPANY_NAME){

      MY_COMPANY_ID = cArray[ind].id

      break

    }

  }

  if(MY_COMPANY_ID){

    break

  }

  total = followingCompaniesResponse.body._total

  start += count

}

```



Returns Group's Profile Details



```javascript

groupResponse = client("/groups/{groupId}{fieldSelectors}", {

  "groupId": MY_GROUP_ID

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( groupResponse.status, 200 )

```



Sharing content with a post is fairly straight forward. Simply make a POST call to the Groups AP



```javascript

groupPostsCreateResponse = client.groups.groupId(MY_GROUP_ID).posts.post({

  "title" : "New Group Discussion 1",

  "summary" : "What does everyone think about platform development?",

}, {query:{oauth2_access_token : ACCESS_TOKEN}})

```

```javascript

assert.equal( groupPostsCreateResponse.status, 201 )

```



Returns a Group's Discussion Posts



```javascript

groupPostsResponse = client.groups.groupId(MY_GROUP_ID).posts.get({

  oauth2_access_token : ACCESS_TOKEN,

  format: "json"

})

```

```javascript

assert.equal( groupPostsResponse.status, 200 )

postId = groupPostsResponse.body.values[0].id

```



Returns Discussion Post



```javascript

postResponse = client("/posts/{postId}{fieldSelectors}", {

  "postId": postId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( postResponse.status, 200 )

```



Add a Comment to a Post



```javascript

commentCreateResponse = client.posts.postId(postId).comments.post({

  "text" : "This comment was created by the 'LinkedIn' API Notebook"

}, {query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( commentCreateResponse.status, 201 )

```



Retrieve comments on particular post



```javascript

commentsResponse = client.posts.postId(postId).comments.get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( commentsResponse.status, 200 )

commentId = commentsResponse.body.values[0].id

```



Returns Comments



```javascript

commentResponse = client("/comments/{commentId}{fieldSelectors}", {

  "commentId": commentId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( commentResponse.status, 200 )

```



Deletes a Comment



```javascript

commentDeleteResponse = client("/comments/{commentId}{fieldSelectors}", {

  "commentId": commentId

}).delete({}, {query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( commentDeleteResponse.status, 204 )

```



Deletes a Post



```javascript

postDeleteResponse = client("/posts/{postId}{fieldSelectors}", {

  "postId": postId

}).delete({}, {query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

assert.equal( postDeleteResponse.status, 204 )

```



Use the Company Search API to find companies using keywords, industry, location, or some other criteria. It returns a collection of matching companies. Each entry can contain much of the information available on the company page.

The API can also return facets. Facets provide you with data about the collection of companies, such as which companies are located in a certain area, size of the companies, and the industry a company is in. You can then use this data to make a new API call that further refines your original request. This is similar to clicking the buttons on the left-hand side of the LinkedIn Search results page.



```javascript

companySearchResponse = client["company-search"]("").get({"name":"notebook test company",oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( companySearchResponse.status, 200 )

```



Return companies by domain



```javascript

companiesResponse = client.companies("").get({"email-domain":"redbull.com",oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( companiesResponse.status, 200 )

companyId = companiesResponse.body.values[0].id

```



Returns a single company if found



```javascript

companyResponse = client("/companies/{companyId}{fieldSelectors}", {

  "companyId": companyId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( companyResponse.status, 200 )

```



To retrieve the company's update



```javascript

updatesResponse = client.companies.companyId(companyId).updates.get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( updatesResponse.status, 200 )

updateKey = updatesResponse.body.values[0].updateKey

```



Retrieve company's update comments by update key



```javascript

updateCommentsResponse = client("/companies/{companyId}/updates/key={CompanyUpdateKey}/update-comments", {

  "CompanyUpdateKey": updateKey,

  "companyId": companyId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( updateCommentsResponse.status, 200 )

```



Retrieve company's update likes by update key



```javascript

likesResponse = client("/companies/{companyId}/updates/key={CompanyUpdateKey}/likes", {

  "CompanyUpdateKey": updateKey,

  "companyId": companyId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( likesResponse.status, 200 )

```



Full company share with content



```javascript

if(MY_COMPANY_ID){

  sharesCreateResponse = client.companies.companyId(MY_COMPANY_ID).shares.post({

    "visibility" : {

      "code" : "anyone"

    } ,

    "comment" : "Testing a full company share with the API Notebook! " + new Date().getMilliseconds() ,

    "content" : {

      "submitted-url" : "http://www.example.com/content.html" ,

      "title" : "Test Share with Content" ,

      "description" : "content description" ,

      "submitted‐image-url" : "http://www.example.com/image.jpg"

    }

  }, {query:{oauth2_access_token : ACCESS_TOKEN}})

}

```

```javascript

if(MY_COMPANY_ID){

  assert.equal( sharesCreateResponse.status, 201 )

}

```



Check company share status



```javascript

isCompanyShareEnabledResponse = client("/companies/{companyId}/is-company-share-enabled", {

  "companyId": companyId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( isCompanyShareEnabledResponse.status, 200 )

```



Check status of relation to view company share



```javascript

relationToViewerIsCompanyShareEnabledResponse = client("/companies/{companyId}/relation-to-viewer/is-company-share-enabled", {

  "companyId": companyId

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( relationToViewerIsCompanyShareEnabledResponse.status, 200 )

```



Retrieve count followers



```javascript

numFollowersResponse = client.companies.companyId(companyId)["num-followers"].get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( numFollowersResponse.status, 200 )

```



The Company Statistics API provides the ability to retrieve statistics for a particular company page. These stats can give you insights on both company share and follower metrics. Currently, the numbers provided are not real-time. The analytics breakdown for a company page is provided on a daily basis



```javascript

if(MY_COMPANY_ID){

  companyStatisticsResponse = client("/companies/{companyId}/company-statistics", {

    "companyId": MY_COMPANY_ID

  }).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

}

```

```javascript

if(MY_COMPANY_ID){

  assert( companyStatisticsResponse.status == 200 || companyStatisticsResponse.status == 403 )

}

```



Use the Company Products API to:

Return a list of products and services supported by a company

Return recommendations for a particular product



```javascript

//removed on 07.14.2014

//https://developer.linkedin.com/blog/company-products-services-api-no-longer-supported

productsResponse = client.companies.companyId(companyId).products("").get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( productsResponse.status, 410 )

```



Returns a single company if found



```javascript

universalNameCompanyNameResponse = client("/companies/universal-name={companyName}", {

  "companyName": "apple"

}).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert.equal( universalNameCompanyNameResponse.status, 200 )

```



Returns Jobs found by some criteria



```javascript

jobSearchResponse = client["job-search"]("").get({"company-name":"mulesoft",oauth2_access_token : ACCESS_TOKEN, format: "json"})

```

```javascript

assert( jobSearchResponse.status == 200 || jobSearchResponse.status == 403 )

if(jobSearchResponse.status==200){

  jobId = jobSearchResponse.body.jobs.values[0].id

}

else{

  jobId = null

}

```



Returns Job info



```javascript

if(jobId){

  jobByIdResponse = client("/jobs/{jobId}{fieldSelectors}", {

    "jobId" : jobId

  }).get({oauth2_access_token : ACCESS_TOKEN, format: "json"})

}

```

```javascript

if(jobId){

  assert.equal( jobByIdResponse.status, 200 )

}

```



Post a job



```javascript

//Method not supported

//jobsCreateResponse = client.jobs.post({}, {query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

//assert.equal( jobsCreateResponse.status, 200 )

```



Edit or renew a job.



```javascript

//Method not supported

// jobUpdateResponse = client("/jobs/partner-job-id={partnerJobId}", {

//   "partnerJobId": "partnerJobIdValue"

// }).put({}, {query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

//assert.equal( jobUpdateResponse.status, 200 )

```



Close calls should not include XML; only the URL is required.

An HTTP DELETE must be used to close a job.



```javascript

//Method not supported

//jobDeleteResponse = client("/jobs/partner-job-id={partnerJobId}", {

//  "partnerJobId": "partnerJobIdValue"

//}).delete({}, {query:{oauth2_access_token : ACCESS_TOKEN, format: "json"}})

```

```javascript

//assert.equal( jobDeleteResponse.status, 204 )

```