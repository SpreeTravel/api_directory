---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7648/versions/7775/portal/pages/6346/preview
apiNotebookVersion: 1.1.66
title: Geonames part 2
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

USERNAME = prompt("Please, enter your GeoNames username.")

```

```javascript

// Read about the GeoNames RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7648/versions/7775/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7648/versions/7775/definition');

```



Finds the nearest street for a given lat/lng pair.

Result : returns the nearest street segments for the given latitude/longitude

Restriction : this webservice is only available for the US.



```javascript

findNearbyStreetsResponse = client.findNearbyStreets("JSON").get({

  lat: 37.451,

  lng: -122.18,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyStreetsResponse.status, 200 )

```



Finds the nearest street and the next crossing street for a given lat/lng pair.

Result : returns the nearest intersection for the given latitude/longitude



```javascript

findNearestIntersectionOSMResponse = client.findNearestIntersectionOSM("JSON").get({

  lat: 37.451,

  lng: -122.18,

  username: USERNAME

})

```

```javascript

assert.equal( findNearestIntersectionOSMResponse.status, 200 )

```



Finds the nearest streets for a given lat/lng pair.

Result: returns the nearest street segments for the given latitude/longitude



```javascript

findNearbyStreetsOSMResponse = client.findNearbyStreetsOSM("JSON").get({

  lat: 37.451,

  lng: -122.18,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyStreetsOSMResponse.status, 200 )

```



Finds the nearest points of interests for a given lat/lng pair.



```javascript

findNearbyPOIsOSMResponse = client.findNearbyPOIsOSM("JSON").get({

  lat: 37.451,

  lng: -122.18,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyPOIsOSMResponse.status, 200 )

```



The iso country code and the administrative subdivision of any given point.

Result : returns the country and the administrative subdivison (state, province,...) for the given latitude/longitude



```javascript

countrySubdivisionResponse = client.countrySubdivision("JSON").get({

  lat: 47.3,

  lng: 10.2,

  username: USERNAME

})

```

```javascript

assert.equal( countrySubdivisionResponse.status, 200 )

```



The name of the ocean or sea.

Result : returns the ocean or sea for the given latitude/longitude



```javascript

oceanResponse = client.ocean("JSON").get({

  lat: 40.78343,

  lng: -43.96625,

  username: USERNAME

})

```

```javascript

assert.equal( oceanResponse.status, 200 )

```



The neighbourhood for US cities. Data provided by Zillow under cc-by-sa license.

Result : returns the neighbourhood for the given latitude/longitude



```javascript

neighbourhoodResponse = client.neighbourhood("JSON").get({

  lat: 40.78343,

  lng: -73.96625,

  username: USERNAME

})

```

```javascript

assert.equal( neighbourhoodResponse.status, 200 )

```



Result : the timezone at the lat/lng with gmt offset (1. January) and dst offset (1. July)



```javascript

timezoneResponse = client.timezone("JSON").get({

  lat: 47.01,

  lng: 10.2,

  username: USERNAME

})

```

```javascript

assert.equal( timezoneResponse.status, 200 )

```



GTOPO30 is a global digital elevation model (DEM) with a horizontal grid spacing of 30 arc seconds (approximately 1 kilometer). GTOPO30 was derived from several raster and vector sources of topographic information



```javascript

gtopo30Response = client.gtopo30("JSON").get({

  lat: 47.01,

  lng: 10.2,

  username: USERNAME

})

```

```javascript

assert.equal( gtopo30Response.status, 200 )

```



Result : Country information : Capital, Population, Area in square km, Bounding Box of mainland (excluding offshore islands



```javascript

countryInfoResponse = client.countryInfo("JSON").get({

  country: "DE",

  username: USERNAME

})

```

```javascript

assert.equal( countryInfoResponse.status, 200 )

```



returns a RSS feed with latitude and longitude for each entry where the geonames search engine has found a relevant location. Already existant GeoRSS elements in the feed remain unchanged. There is an upper limit of 20 entries for performance reasons.



```javascript

rssToGeoResponse = client.rssToGeoRSS.get({

  feedUrl: "http://feeds.reuters.com/reuters/worldNews",

  username: USERNAME

})

```

```javascript

assert.equal( rssToGeoResponse.status, 200 )

```



returns the names found for the searchterm as xml or json document, the search is using an AND operator



```javascript

searchResponse = client.search("JSON").get({

  q: "panama",

  username: USERNAME

})

```

```javascript

assert.equal( searchResponse.status, 200 )

```



Result : returns a list of places for the given postalcode in JSON format, sorted by postalcode,placenames



```javascript

postalCodeLookupJSONResponse = client.postalCodeLookup("JSON").get({

  postalcode: 6600,

  country: "AT",

  username: USERNAME

})

```

```javascript

assert.equal( postalCodeLookupJSONResponse.status, 200 )

```



The iso country code of any given point.

Result : returns the iso country code for the given latitude/longitude

With the parameter type=xml this service returns an xml document with iso country code and country name. The optional parameter lang can be used to specify the language the country name should be in. JSON output is produced with type=JSON



```javascript

countryCodeResponse = client.countryCode("JSON").get({

  lat: 47.01,

  lng: 10.2,

  username: USERNAME

})

```

```javascript

assert.equal( countryCodeResponse.status, 200 )

```



Result : returns a list of earthquakes, ordered by magnitude



```javascript

earthquakesResponse = client.earthquakes("JSON").get({

  north: 44.1,

  south: -9.9,

  east: -22.4,

  west: 55.2,

  username: USERNAME

})

```

```javascript

assert.equal( earthquakesResponse.status, 200 )

```



Result : returns the attribute of the geoNames feature with the given geonameId as xml document



```javascript

getResponse = client.get("JSON").get({

  geonameId: 2746385,

  username: USERNAME

})

```

```javascript

assert.equal( getResponse.status, 200 )

```



Returns all neighbours for a country or administrative division. (coverage: all countries on country level, and lower levels as specified here: supported levels



```javascript

neighboursResponse = client.neighbours("JSON").get({

  geonameId: 2593110,

  username: USERNAME

})

```

```javascript

assert.equal( neighboursResponse.status, 200 )

```



Result : countries for which postal code geocoding is available



```javascript

postalCodeCountryInfoResponse = client.postalCodeCountryInfo("JSON").get({

  username: USERNAME

})

```

```javascript

assert.equal( postalCodeCountryInfoResponse.status, 200 )

```



sample area: ca 90m x 90m Result : a single number giving the elevation in meters according to srtm3, ocean areas have been masked as "no data" and have been assigned a value of -32768



```javascript

srtm3Response = client.srtm3("JSON").get({

  lat: 50.01,

  lng: 10.2,

  username: USERNAME

})

```

```javascript

assert.equal( srtm3Response.status, 200 )

```

```javascript

srtm3Response2 = client.srtm3("JSON").post(null,{query:{

  lat: 50.01,

  lng: 10.2,

  username: USERNAME

}})

```

```javascript

assert.equal( srtm3Response2.status, 200 )

```



sample area: ca 90m x 90m Result : a single number giving the elevation in meters according to srtm3, ocean areas have been masked as "no data" and have been assigned a value of -32768



```javascript

srtm3Response2 = client.srtm3("JSON").post({},{query:{

  lat: 50.01,

  lng: 10.2,

  username: USERNAME

}})

```

```javascript

assert.equal( srtm3Response2.status, 200 )

```



Result : returns a list of weather stations with the most recent weather observation



```javascript

weatherResponse = client.weather("JSON").get({

  north: 44.1,

  south: -9.9,

  east: -22.4,

  west: 55.2,

  username: USERNAME

})

```

```javascript

assert.equal( weatherResponse.status, 200 )

```



Result : returns the weather station and the most recent weather observation for the ICAO cod



```javascript

weatherIcaoResponse = client.weatherIcao("JSON").get({

  ICAO : "LSZH",

  username: USERNAME

})

```

```javascript

assert.equal( weatherIcaoResponse.status, 200 )

```