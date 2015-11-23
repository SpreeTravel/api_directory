---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7648/versions/7775/portal/pages/6345/preview
apiNotebookVersion: 1.1.66
title: Geonames part 1
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



Webservice for the GeoNames full text search in xml and json format. 

Returns a list of postal codes and places for the placename/postalcode query as xml document 

For the US the first returned zip code is determined using zip code area shapes, the following zip codes are based on the centroid. For all other supported countries all returned postal codes are based on centroids.



```javascript

postalCodeSearchResponse = client.postalCodeSearch("JSON").get({

  postalcode: 10003,

  username: USERNAME

})

```

```javascript

assert.equal( postalCodeSearchResponse.status, 200 )

```



Result : returns the most detailed information available for the lat/lng query as xml document 

It is a combination of several services.



```javascript

extendedFindNearbyResponse = client.extendedFindNearby.get({

  lat: 47.3,

  lng: 9,

  username: USERNAME

}, {headers:{"Content-Type":"application/json"}})

```

```javascript

assert.equal( extendedFindNearbyResponse.status, 200 )

```



Result : a single number giving the elevation in meters according to aster gdem, ocean areas have been masked as "no data" and have been assigned a value of -9999



```javascript

astergdemResponse = client.astergdem("JSON").get({

  lat: 50.01,

  lng: 10.2,

  username: USERNAME

})

```

```javascript

assert.equal( astergdemResponse.status, 200 )

```

```javascript

astergdemResponse2 = client.astergdem("JSON").post(null,{query:{

  lat: 50.01,

  lng: 10.2,

  username: USERNAME

}})

```

```javascript

assert.equal( astergdemResponse2.status, 200 )

```



Result: returns a list of GeoName records



```javascript

childrenResponse = client.children("JSON").get({

  geonameId: 2593110,

  hierarchy: "tourism",

  username: USERNAME

})

```

```javascript

assert.equal( childrenResponse.status, 200 )

```



Result : returns a list of cities and placenames in the bounding box, ordered by relevancy (capital/population). Placenames close together are filtered out and only the larger name is included in the resulting list



```javascript

citiesResponse = client.cities("JSON").get({

  north: 44.1,

  south: -9.9,

  east: -22.4,

  west: 55.2,

  username: USERNAME

})

```

```javascript

assert.equal( citiesResponse.status, 200 )

```



Result : returns a list of postalcodes and places for the lat/lng query as xml document. The result is sorted by distance. For Canada the FSA is returned (first 3 characters of full postal code)



```javascript

findNearbyPostalCodesResponse = client.findNearbyPostalCodes("JSON").get({

  postalcode: 8775,

  country: "CH",

  radius: 10,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyPostalCodesResponse.status, 200 )

```



Result : returns the closest populated place (feature class=P) for the lat/lng query as xml document. The unit of the distance element is 'km'.



```javascript

findNearbyPlaceNameResponse = client.findNearbyPlaceName("JSON").get({

  lat: 47.3,

  lng: 9,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyPlaceNameResponse.status, 200 )

```



Result : returns the closest toponym for the lat/lng query as xml document



```javascript

findNearbyResponse = client.findNearby("JSON").get({

  lat: 47.3,

  lng: 9,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyResponse.status, 200 )

```



Result : returns a list of GeoName records, ordered by hierarchy level. The top hierarchy (continent) is the first element in the list



```javascript

hierarchyResponse = client.hierarchy("JSON").get({

  geonameId: 2593110,

  username: USERNAME

})

```

```javascript

assert.equal( hierarchyResponse.status, 200 )

```



returns all features within the GeoName feature for the given geoNameId. It only returns contained features when a polygon boundary for the input feature is defined



```javascript

containsResponse = client.contains("JSON").get({

  geonameId: 2746385,

  username: USERNAME

})

```

```javascript

assert.equal( containsResponse.status, 200 )

```



Returns all siblings of a GeoNames toponym with feature class A



```javascript

siblingsResponse = client.siblings("JSON").get({

  geonameId: 2593110,

  username: USERNAME

})

```

```javascript

assert.equal( siblingsResponse.status, 200 )

```



This service comes in two flavors. You can either pass the lat/long or a postalcode/placename.

Result : returns a list of Wikipedia entries as xml document 



```javascript

findNearbyWikipediaResponse = client.findNearbyWikipedia("JSON").get({

  postalcode: 8775,

  country: "CH",

  radius: 10,

  username: USERNAME

})

```

```javascript

assert.equal( findNearbyWikipediaResponse.status, 200 )

```



Result : returns the Wikipedia entries found for the searchterm as xml documen



```javascript

wikipediaSearchResponse = client.wikipediaSearch("JSON").get({

  q: "london",

  username: USERNAME

})

```

```javascript

assert.equal( wikipediaSearchResponse.status, 200 )

```



Result : returns the Wikipedia entries within the bounding box as xml document



```javascript

wikipediaBoundingBoxResponse = client.wikipediaBoundingBox("JSON").get({

  north: 44.1,

  south: -9.9,

  east: -22.4,

  west: 55.2,

  username: USERNAME

})

```

```javascript

assert.equal( wikipediaBoundingBoxResponse.status, 200 )

```



Result : returns a weather station with the most recent weather observation



```javascript

findNearByWeatherResponse = client.findNearByWeather("JSON").get({

  lat: 43,

  lng: -2,

  username: USERNAME

})

```

```javascript

assert.equal( findNearByWeatherResponse.status, 200 )

```



Finds the nearest street and address for a given lat/lng pair.

Result : returns the nearest address for the given latitude/longitude, the street number is an 'educated guess' using an interpolation of street number at the end of a street segment.



```javascript

findNearestAddressResponse = client.findNearestAddress("JSON").get({

  lat: 37.451,

  lng: -122.18,

  username: USERNAME

})

```

```javascript

assert.equal( findNearestAddressResponse.status, 200 )

```



Result : returns the nearest intersection for the given latitude/longitude



```javascript

findNearestIntersectionResponse = client.findNearestIntersection("JSON").get({

  lat: 37.451,

  lng: -122.18,

  username: USERNAME

})

```

```javascript

assert.equal( findNearestIntersectionResponse.status, 200 )

```