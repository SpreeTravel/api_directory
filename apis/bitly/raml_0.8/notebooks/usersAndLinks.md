---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7589/versions/7715/portal/pages/6302/preview
apiNotebookVersion: 1.1.66
title: Users and Links
---

```javascript

useDefaultApp = true; // Please, set this flag to false if you want to use your own application

```

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



In all the requests which require pagination parameter _LIMIT_ we will use the following value:



```javascript

LIMIT = 5

```

```javascript

// Read about the Bitly API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7589/versions/7715/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7589/versions/7715/definition');

```

```javascript

if (useDefaultApp) {

  API.authenticate(client);

} else {

  cId = prompt("Please, enter the clientId of your own application");

  cSecret = prompt("Please, enter the clientSecret of your own application");

  

  API.authenticate(client, 'oauth_2_0', {

    clientId: cId,

    clientSecret: cSecret

  });  

}

```



We need a long url and a short link for our tests. Let's take one of the most popular short links



```javascript

URL = "http://www.apihub.com/"

SHORT_LINK = client.shorten.get( { longUrl: URL } ).body.data.url

```



Returns metrics about a shares of a single link.



```javascript

sharesResponse = client.link.shares.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( sharesResponse.status, 200 )

assert.equal( sharesResponse.body.status_code, 200 ) 

```



Returns users who have encoded this link (optionally only those in the

requesting user's social graph).

Note: Some users may not be returned from this call depending on link

privacy settings.



```javascript

encodersResponse = client.link.encoders.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( encodersResponse.status, 200 )

assert.equal( encodersResponse.body.status_code, 200 ) 

```



Returns metadata about a single bitly link.



```javascript

infoResponse = client.link.info.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( infoResponse.status, 200 )

assert.equal( infoResponse.body.status_code, 200 ) 

```



Returns metrics about the countries referring click traffic to a single bitly link.



```javascript

countriesResponse = client.link.countries.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( countriesResponse.status, 200 )

assert.equal( countriesResponse.body.status_code, 200 ) 

```



Returns the number of users who have shortened (encoded) a single bitly link.



```javascript

encodersCountResponse = client.link.encoders_count.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( encodersCountResponse.status, 200 )

assert.equal( encodersCountResponse.body.status_code, 200 ) 

```



Returns the number of clicks on a single bitly link.

Note: historical data is stored hourly beyond the most recent 60 minutes.

If a unit_reference_ts is specified, unit cannot be minute.



```javascript

clicksResponse = client.link.clicks.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( clicksResponse.status, 200 )

assert.equal( clicksResponse.body.status_code, 200 ) 

```



Returns metrics about the pages referring click traffic to a single bitly

link, grouped by referring domain.



```javascript

referrersByDomainResponse = client.link.referrers_by_domain.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( referrersByDomainResponse.status, 200 )

assert.equal( referrersByDomainResponse.body.status_code, 200 ) 

```



Returns metrics about the pages referring click traffic to a single bitly link.



```javascript

referrersResponse = client.link.referrers.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( referrersResponse.status, 200 )

assert.equal( referrersResponse.body.status_code, 200 ) 

```



This is used to query for a bitly link based on a long URL.



```javascript

lookupResponse = client.link.lookup.get( { url: URL } )

```

```javascript

assert.equal( lookupResponse.status, 200 )

assert.equal( lookupResponse.body.status_code, 200 ) 

```



Returns metrics about the domains referring click traffic to a single bitly link.



```javascript

referringDomainsResponse = client.link.referring_domains.get( { link: SHORT_LINK } )

```

```javascript

assert.equal( referringDomainsResponse.status, 200 )

assert.equal( referringDomainsResponse.body.status_code, 200 ) 

```



Returns entries from a user's network history in reverse chronological order.

(A user's network history includes publicly saved links from Twitter and

Facebook connections.)



```javascript

networkHistoryResponse = client.user.network_history.get( { expand_client_id: false } )

```

```javascript

assert.equal( networkHistoryResponse.status, 200 )

assert.equal( networkHistoryResponse.body.status_code, 200 ) 

```



Returns the number of links shortened (encoded) in a given time period by

the authenticated user.



```javascript

shortenCountsResponse = client.user.shorten_counts.get()

```

```javascript

assert.equal( shortenCountsResponse.status, 200 )

assert.equal( shortenCountsResponse.body.status_code, 200 ) 

```



Return or update information about a user



```javascript

infoResponse = client.user.info.get()

```

```javascript

assert.equal( infoResponse.status, 200 )

assert.equal( infoResponse.body.status_code, 200 ) 

```



Returns the number of shares by the authenticated user in a given time period.



```javascript

shareCountsResponse = client.user.share_counts.get()

```

```javascript

assert.equal( shareCountsResponse.status, 200 )

assert.equal( shareCountsResponse.body.status_code, 200 ) 

```



Returns aggregate metrics about the countries referring click traffic to all of the authenticated user's bitly links.



```javascript

countriesResponse = client.user.countries.get()

```

```javascript

assert.equal( countriesResponse.status, 200 )

assert.equal( countriesResponse.body.status_code, 200 ) 

```



Returns entries from a user's link history in reverse chronological order.

Note: Entries will be sorted by the user_ts field found in the response data.



```javascript

linkHistoryResponse = client.user.link_history.get()

```

```javascript

assert.equal( linkHistoryResponse.status, 200 )

assert.equal( linkHistoryResponse.body.status_code, 200 ) 

```



Returns the authenticated user's most-clicked bitly links (ordered by number of clicks) in a given time period.

Note: This replaces the realtime_links endpoint.



```javascript

popularLinksResponse = client.user.popular_links.get()

```

```javascript

assert.equal( popularLinksResponse.status, 200 )

assert.equal( popularLinksResponse.body.status_code, 200 ) 

```



Returns the aggregate number of clicks on all of the authenticated user's bitly links.



```javascript

clicksResponse = client.user.clicks.get()

```

```javascript

assert.equal( clicksResponse.status, 200 )

assert.equal( clicksResponse.body.status_code, 200 ) 

```



Saves a link as a bitmark in a user's history, with optional pre-set metadata.

(Also returns a short URL for that link.)



```javascript

linkSaveResponse = client.user.link_save.get( { longUrl: URL } )

```

```javascript

assert.equal( linkSaveResponse.status, 200 )

assert( linkSaveResponse.body.status_code == 200 || linkSaveResponse.body.status_txt.indexOf("LINK_ALREADY_EXISTS") >= 0 ) 

```



Returns aggregate metrics about the pages referring click traffic to all

of the authenticated user's bitly links.



```javascript

referrersResponse = client.user.referrers.get()

```

```javascript

assert.equal( referrersResponse.status, 200 )

assert.equal( referrersResponse.body.status_code, 200 ) 

```



Returns aggregate metrics about the domains referring click traffic to all of the authenticated user's bitly links.

If the user is a master account, or is a subaccount with full_reports permission,

the user may choose to view the metrics of any account belonging to the master account.



```javascript

referringDomainsResponse = client.user.referring_domains.get()

```

```javascript

assert.equal( referringDomainsResponse.status, 200 )

assert.equal( referringDomainsResponse.body.status_code, 200 ) 

```



Now we need a short link created by the authenticated user. You may use any long url in the code block below.



```javascript

USER_URL = "http://www.apihub.com"

shortenResponse = client.shorten.get( { longUrl : USER_URL } )

```

```javascript

USER_SHORT_LINK = shortenResponse.body.data.url

```



Changes link metadata in a user's history.

Because link metadata is modified asynchronously, it may take a few moments

for changes made via this API method to update.



```javascript

linkEditResponse = client.user.link_edit.get({

  link: USER_SHORT_LINK,

  edit: "note",

  note: "new note value"

})

```

```javascript

assert.equal( linkEditResponse.status, 200 )

assert.equal( linkEditResponse.body.status_code, 200 ) 

```



This is used to query for a bitly link shortened by the authenticated user based on a long URL.



```javascript

linkLookupResponse = client.user.link_lookup.get( { url: USER_URL } )

```

```javascript

assert.equal( linkLookupResponse.status, 200 )

assert.equal( linkLookupResponse.body.status_code, 200 ) 

```



Returns all bundles this user has access to (public + private + collaborator)



```javascript

bundleHistoryResponse = client.user.bundle_history.get()

```

```javascript

assert.equal( bundleHistoryResponse.status, 200 )

assert.equal( bundleHistoryResponse.body.status_code, 200 ) 

```



Returns a list of tracking domains a user has configured



```javascript

trackingDomainListResponse = client.user.tracking_domain_list.get()

```

```javascript

assert.equal( trackingDomainListResponse.status, 200 )

assert.equal( trackingDomainListResponse.body.status_code, 200 ) 

```



In order to execute the two following methods i.e.



```

GET /user/tracking_domain_clicks

GET /user/tracking_domain_shorten_counts

```

you need at least one tracking domain to be registered at https://bitly.com/a/tracking_domain_settings .



You may house desired tracking domain by specifying array index in the code block below.



```javascript

DOMAIN = trackingDomainListResponse.body.data.tracking_domains[0]

```



Returns the number of clicks on bitly links pointing to the specified tracking domain that have occurred in a given time period.



```javascript

trackingDomainClicksResponse = client.user.tracking_domain_clicks.get( { domain: DOMAIN } )

```

```javascript

assert.equal( trackingDomainClicksResponse.status, 200 )

assert.equal( trackingDomainClicksResponse.body.status_code, 200 )

```



Returns the number of links, pointing to a specified tracking domain, shortened (encoded) in a given time period by all bitly users.





```javascript

trackingDomainShortenCountsResponse = client.user.tracking_domain_shorten_counts.get( { domain: DOMAIN } )

```

```javascript

assert.equal( trackingDomainShortenCountsResponse.status, 200 )

assert.equal( trackingDomainShortenCountsResponse.body.status_code, 200 )

```