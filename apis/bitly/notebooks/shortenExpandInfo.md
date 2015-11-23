---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7589/versions/7715/portal/pages/6304/preview
apiNotebookVersion: 1.1.66
title: Shorten, Expand, Info
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

Given a long URL, returns a bitly short URL

```javascript

shortenResponse = client.shorten.get( { longUrl: "http://www.apihub.com" } )

```

```javascript

assert.equal( shortenResponse.status, 200 )

assert.equal( shortenResponse.body.status_code, 200 )
 
```

We need a short link for the next test. Let's take one of the most popular.

```javascript

SHORT_LINK = shortenResponse.body.data.url

```

Given a bitly URL or hash (or multiple), returns the target (long) URL.
Note: either shortUrl or hash must be given as a parameter.

```javascript

expandResponse = client.expand.get({

  shortUrl: SHORT_LINK
  
})

```

```javascript

assert.equal( expandResponse.status, 200 )

assert.equal( expandResponse.body.status_code, 200 )
 
```

This is used to return the page title for a given bitly link.

```javascript

infoResponse = client.info.get({

  shortUrl: SHORT_LINK,
  
  expand_user: true
  
})

```

```javascript

assert.equal( infoResponse.status, 200 )

assert.equal( infoResponse.body.status_code, 200 )
 
```

Query whether a given domain is a valid bitly pro domain. Keep in mind that
bitly custom short domains are restricted to less than 15 characters in
length.

```javascript

bitlyProDomainResponse = client.bitly_pro_domain.get( { domain: "nytimes.com" } )

```

```javascript

assert.equal( bitlyProDomainResponse.status, 200 )

assert.equal( bitlyProDomainResponse.body.status_code, 200 )

```