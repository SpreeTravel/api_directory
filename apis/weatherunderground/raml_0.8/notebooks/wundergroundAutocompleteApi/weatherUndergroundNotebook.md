---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7733/versions/7867/portal/pages/6501/preview
apiNotebookVersion: 1.1.66
title: Weather Underground notebook
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
API_KEY = prompt("Please, enter your Weather Underground API key.")
```

Create Wether Underground REST client.

```javascript
// Read about the Weather Underground API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7733/versions/7867/contracts
API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7733/versions/7867/definition',
{
  baseUriParameters:{
    api_key: API_KEY
  }
});
```

Define some test data.

```javascript
settings = "lang:FR"
settingsQuery = "autoip"
query = "KJFK"
```

Radar and Satellite can be requested on one combined image.
Response formats include GIF and PNG.

```javascript
radarSatteliteFeaturesResponse = client.radarSatteliteFeatures("radar.satellite").image.gif.get({
  "delay": 50,
  "interval": 30
})
```

```javascript
assert.equal( radarSatteliteFeaturesResponse.status, 200 )
```

Radar and Satellite can be requested on one combined image.

```javascript
queryRadarSatteliteFeaturesResponse = client("/radar/satellite/q/{query}{mediaTypeExtension}", {
  "query": "KS",
  "mediaTypeExtension": "gif"
}).get()
```

```javascript
assert.equal( queryRadarSatteliteFeaturesResponse.status, 200 )
```

Returns a static or animated radar image for a given location.

```javascript
imageRadarFeatureResponse = client.radarFeature("radar").image.gif.get()
```

```javascript
assert.equal( imageRadarFeatureResponse.status, 200 )
```

Returns a static or animated radar image for a given location.
A valid location query as described in the API URL Format.

```javascript
queryRadarFeatureResponse = client.radarFeature("radar").q.query( query ).gif.get()
```

```javascript
assert.equal( queryRadarFeatureResponse.status, 200 )
```

Returns a static or animated satellite image for a given location.
Response formats include GIF and PNG.

```javascript
imageSatelliteResponse = client.satteliteFeature("sattelite").image.gif.get()
```

```javascript
assert.equal( imageSatelliteResponse.status, 200 )
```

Returns a static or animated satellite image for a given location.
A valid location query as described in the API URL Format.

```javascript
querySatelliteResponse = client.satteliteFeature("sattelite").q.query( query ).gif.get()
```

```javascript
assert.equal( querySatelliteResponse.status, 200 )
```

Returns the short name description, expiration time and a long text description of a severe alert, if one has been issued for the searched upon location.

```javascript
settingsAlertsResponse = client.alerts.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsAlertsResponse.status, 200 )
```

Returns the short name description, expiration time and a long text description of a severe alert, if one has been issued for the searched upon location.

```javascript
settingsAlertsResponse = client.alerts.q.query( query ).json.get()
```

```javascript
assert.equal( settingsAlertsResponse.status, 200 )
```

Returns a summary of the observed weather for the specified date.

```javascript
settingsHistoryResponse = client.history.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsHistoryResponse.status, 200 )
```

Returns a summary of the observed weather for the specified date.


```javascript
queryHistoryResponse = client.history.q.query( query ).json.get()
```

```javascript
assert.equal( queryHistoryResponse.status, 200 )
```

Returns a weather summary based on historical information between the specified dates (30 days max).

```javascript
settingsPlannerResponse = client.planner.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsPlannerResponse.status, 200 )
```

Returns a weather summary based on historical information between the specified dates (30 days max).

```javascript
queryPlannerResponse = client.planner.q.query( query ).json.get()
```

```javascript
assert.equal( queryPlannerResponse.status, 200 )
```

Raw Tidal information for graphs.

```javascript
settingsRawtideResponse = client.rawtide.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsRawtideResponse.status, 200 )
```

Raw Tidal information for graphs.

```javascript
queryRawtideResponse = client.rawtide.q.query( query ).json.get()
```

```javascript
assert.equal( queryRawtideResponse.status, 200 )
```

Returns a URL link to .gif visual and infrared satellite images.

```javascript
settingsSatelliteResponse = client.satellite.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsSatelliteResponse.status, 200 )
```

Returns a URL link to .gif visual and infrared satellite images.

```javascript
querySatelliteResponse = client.satellite.q.query( query ).json.get()
```

```javascript
assert.equal( querySatelliteResponse.status, 200 )
```

Tidal information.

```javascript
settingsTideResponse = client.tide.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsTideResponse.status, 200 )
```

Tidal information.


```javascript
queryTideResponse = client.tide.q.query( query ).json.get()
```

```javascript
assert.equal( queryTideResponse.status, 200 )
```

Returns a summary of the observed weather history for yesterday.

```javascript
settingsYesterdayResponse = client.yesterday.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsYesterdayResponse.status, 200 )
```

Returns a summary of the observed weather history for yesterday.

```javascript
queryYesterdayResponse = client.yesterday.q.query( query ).json.get()
```

```javascript
assert.equal( queryYesterdayResponse.status, 200 )
```

Records within the United States come from the National Weather Service, with approximately 120 reporting stations giving records. Records for the rest of the United States, and locations outside of the United States, come from the data we have stored. These are compiled records and only go as far back as we have data for.
The average high and low temperature going back as far as Weather Underground has data for OR from National Weather Service going back 30 years.


```javascript
settingsAlmanacResponse = client.almanac.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsAlmanacResponse.status, 200 )
```

Records within the United States come from the National Weather Service, with approximately 120 reporting stations giving records. Records for the rest of the United States, and locations outside of the United States, come from the data we have stored. These are compiled records and only go as far back as we have data for.
The average high and low temperature going back as far as Weather Underground has data for OR from National Weather Service going back 30 years.

```javascript
queryAlmanacResponse = client.almanac.q.query( query ).json.get()
```

```javascript
assert.equal( queryAlmanacResponse.status, 200 )
```

Returns the moon phase, sunrise and sunset times.

```javascript
settingsAstronomyResponse = client.astronomy.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsAstronomyResponse.status, 200 )
```

Returns the moon phase, sunrise and sunset times.

```javascript
queryAstronomyResponse = client.astronomy.q.query( query ).json.get()
```

```javascript
assert.equal( queryAstronomyResponse.status, 200 )
```

Returns the current temperature, weather condition, humidity, wind, 'feels like' temperature, barometric pressure, and visibility.

```javascript
settingsConditionsResponse = client.conditions.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsConditionsResponse.status, 200 )
```

Returns the current temperature, weather condition, humidity, wind, 'feels like' temperature, barometric pressure, and visibility.

```javascript
queryConditionsResponse = client.conditions.q.query( query ).json.get()
```

```javascript
assert.equal( queryConditionsResponse.status, 200 )
```

Returns information about current hurricanes and tropical storms.
This feature can be used with other weather API features. However, location query options do not apply to the results for currenthurricane.

```javascript
settingsCurrenthurricaneResponse = client.currenthurricane.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsCurrenthurricaneResponse.status, 200 )
```

Returns information about current hurricanes and tropical storms.
This feature can be used with other weather API features. However, location query options do not apply to the results for currenthurricane.

```javascript
queryCurrenthurricaneResponse = client.currenthurricane.q.query( query ).json.get()
```

```javascript
assert.equal( queryCurrenthurricaneResponse.status, 200 )
```

Returns a summary of the weather for the next 3 days. This includes high and low temperatures, a string text forecast and the conditions.

```javascript
settingsForecastResponse = client.forecast.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsForecastResponse.status, 200 )
```

Returns a summary of the weather for the next 3 days. This includes high and low temperatures, a string text forecast and the conditions.

```javascript
queryForecastResponse = client.forecast.q.query( query ).json.get()
```

```javascript
assert.equal( queryForecastResponse.status, 200 )
```

Returns a summary of the weather for the next 10 days. This includes high and low temperatures, a string text forecast and the conditions.

```javascript
settingsForecast10dayResponse = client.forecast10day.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsForecast10dayResponse.status, 200 )
```

Returns a summary of the weather for the next 10 days. This includes high and low temperatures, a string text forecast and the conditions.

```javascript
queryForecast10dayResponse = client.forecast10day.q.query( query ).json.get()
```

```javascript
assert.equal( queryForecast10dayResponse.status, 200 )
```

Returns the city name, zip code / postal code, latitude-longitude coordinates and nearby personal weather stations.

```javascript
settingsGeolookupResponse = client.geolookup.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsGeolookupResponse.status, 200 )
```

Returns the city name, zip code / postal code, latitude-longitude coordinates and nearby personal weather stations.

```javascript
queryGeolookupResponse = client.geolookup.q.query( query ).json.get()
```

```javascript
assert.equal( queryGeolookupResponse.status, 200 )
```

Returns an hourly forecast for the next 36 hours immediately following the API request.

```javascript
settingsHourlyResponse = client.hourly.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsHourlyResponse.status, 200 )
```

Returns an hourly forecast for the next 36 hours immediately following the API request.

```javascript
queryHourlyResponse = client.hourly.q.query( query ).json.get()
```

```javascript
assert.equal( queryHourlyResponse.status, 200 )
```

Returns a summary of the weather for the next 10 days. This includes high and low temperatures, a string text forecast and the conditions.

```javascript
settingsHourly10dayResponse = client.hourly10day.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsHourly10dayResponse.status, 200 )
```

Returns a summary of the weather for the next 10 days. This includes high and low temperatures, a string text forecast and the conditions.

```javascript
queryHourly10dayResponse = client.hourly10day.q.query( query ).json.get()
```

```javascript
assert.equal( queryHourly10dayResponse.status, 200 )
```

Returns locations of nearby Personal Weather Stations and URLs for images from their web cams.

```javascript
settingsWebcamsResponse = client.webcams.settings( settings ).q.query( settingsQuery ).json.get()
```

```javascript
assert.equal( settingsWebcamsResponse.status, 200 )
```

Returns locations of nearby Personal Weather Stations and URLs for images from their web cams.

```javascript
queryWebcamsResponse = client.webcams.q.query( query ).json.get()
```

```javascript
assert.equal( queryWebcamsResponse.status, 200 )
```

Create Wether Underground autocomplete API REST client.

```javascript
// Read about the Weather Underground Autocomplete API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7779/versions/7914/contracts
API.createClient('clientAutocomplete', '/apiplatform/repository/public/organizations/30/apis/7779/versions/7914/definition');
```

Returns a list of locations or hurricanes which match against a partial query. For example, searching for San F will return San Francisco, California, San Fernando del Valle de Catamarca, Argentina, and San Felipe de Puerto Plata, Dominican Republic, among others.

The request options include the ability to query for cities, hurricanes, or both. The results can be used to form Weather API queries or to link directly to wunderground resources.

This feature does not require an API key.

#Examples
http://autocomplete.wunderground.com/aq?query=San%20F
http://autocomplete.wunderground.com/aq?query=San%20F&c=US
http://autocomplete.wunderground.com/aq?query=Kat&h=1&cities=0
http://autocomplete.wunderground.com/aq?query=Katr&h=1
(See description of the response.)

```javascript
aqResponse = clientAutocomplete.aq.get({
  "query": "Kat",
  "h": 1
})
```

```javascript
assert.equal( aqResponse.status, 200 )
```