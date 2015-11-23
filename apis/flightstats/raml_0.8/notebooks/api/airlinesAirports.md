---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/organizations/52560d3f-c37a-409d-9887-79e0a9a9ecff/dashboard/apis/19695/versions/21040/portal/pages/34343/edit
apiNotebookVersion: 1.1.67
title: Airlines, airports
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

App credentials, Using api version, Response data format.

```javascript
APPID = prompt("Please input APPID")
APPKEY = prompt("Please input APPKEY")
VERSION = "v1"
MEDIA_TYPE_JSON = "json"
MEDIA_TYPE_JSON_EXT = ".json"
```

Date and time for requests.

```javascript
var currentTime = new Date()
var YEAR = currentTime.getFullYear()
var MONTH = currentTime.getMonth() + 1
var DAY = currentTime.getDate()
var DAY_HOUR = "8"
```

Function for setting header parameters.

```javascript
function addOutHeaders(o){
o.setRequestHeader("appId", APPID);
o.setRequestHeader("appKey",  APPKEY);
}
```

```javascript
API.set(client, 'beforeSend', new function(){return addOutHeaders})
```

Airlines resource info

```javascript
airlinesResourcesResponse = client.airlines.rest.resources.get()
```

```javascript
assert.equal( airlinesResourcesResponse.status, 200 )
assert.notProperty(airlinesResourcesResponse.body, "error")
```

Returns a listing of currently active airline

```javascript
activeResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).active.get()
```

```javascript
assert.equal( activeResponse.status, 200 )
assert.notProperty(activeResponse.body, "error")
```

Returns a listing of active airlines on the given date

```javascript
dayResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).active.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( dayResponse.status, 200 )
assert.notProperty(dayResponse.body, "error")
```

Returns a listing of all airlines, including those that are not currently active

```javascript
allResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).all.get()
```

```javascript
assert.equal( allResponse.status, 200 )
assert.notProperty(allResponse.body, "error")
AIRLINE_FS_CODE = allResponse.body.airlines[0].fs 
AIRLINE_IATA_CODE = allResponse.body.airlines[0].iata 
AIRLINE_ICAO_CODE = allResponse.body.airlines[0].icao
```

Returns the airline with the given FlightStats code, a globally unique code that is consistent across time

```javascript
fsCodeResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).fs.code(AIRLINE_FS_CODE).get()
```

```javascript
assert.equal( fsCodeResponse.status, 200 )
assert.notProperty(fsCodeResponse.body, "error")
```

Returns a listing of airlines that have had the given IATA code

```javascript
iataCodeResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).iata.iataCode(AIRLINE_IATA_CODE).get()
```

```javascript
assert.equal( iataCodeResponse.status, 200 )
assert.notProperty(iataCodeResponse.body, "error")
```

Returns the airline that had the IATA code on the given date

```javascript
dayResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).iata.iataCode(AIRLINE_IATA_CODE).year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( dayResponse.status, 200 )
assert.notProperty(dayResponse.body, "error")
```

Returns a listing of airlines that have had the given ICAO code

```javascript
icaoCodeResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).icao.icaoCode(AIRLINE_ICAO_CODE).get()
```

```javascript
assert.equal( icaoCodeResponse.status, 200 )
assert.notProperty(icaoCodeResponse.body, "error")
```

Returns the airline that had the ICAO code on the given date

```javascript
dayIcaoResponse = client.airlines.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).icao.icaoCode(AIRLINE_ICAO_CODE).year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( dayIcaoResponse.status, 200 )
assert.notProperty(dayIcaoResponse.body, "error")
```

Airports resources info

```javascript
airportsResourcesResponse = client.airports.rest.resources.get()
```

```javascript
assert.equal( airportsResourcesResponse.status, 200 )
assert.notProperty(airportsResourcesResponse.body, "error")
```

Airports resources minimal info

```javascript
airportsRestResponse = client.airports.rest.resources.extension(MEDIA_TYPE_JSON_EXT).get()
```

```javascript
assert.equal( airportsRestResponse.status, 200 )
assert.notProperty(airportsRestResponse.body, "error")
```

Returns a listing of currently active airport

```javascript
activeAirportsResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).active.get()
```

```javascript
assert.equal( activeAirportsResponse.status, 200 )
assert.notProperty(activeAirportsResponse.body, "error")
```

Returns a listing of active airports on the given date

```javascript
actinveAirportsByDayResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).active.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( actinveAirportsByDayResponse.status, 200 )
assert.notProperty(actinveAirportsByDayResponse.body, "error")
```

Returns a listing of all airports, including those that are not currently activ

```javascript
allAirportsResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).all.get()
```

Returns a listing of airports that have had the given city cod

```javascript
assert.equal( allAirportsResponse.status, 200 )
assert.notProperty(allAirportsResponse.body, "error")
AIRPORT_CITY_CODE = allAirportsResponse.body.airports[0].cityCode
AIRPORT_COUNTRY_CODE = allAirportsResponse.body.airports[0].countryCode
AIRPORT_FS_CODE = allAirportsResponse.body.airports[0].fs
AIRPORT_IATA_CODE = allAirportsResponse.body.airports[0].iata
AIRPORT_ICAO_CODE = allAirportsResponse.body.airports[0].icao
AIRPORT_LONGITUDE = allAirportsResponse.body.airports[0].longitude
AIRPORT_LATITUDE = allAirportsResponse.body.airports[0].latitude
```

```javascript
cityCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).cityCode.cityCode(AIRPORT_CITY_CODE).get()
```

```javascript
assert.equal( cityCodeResponse.status, 200 )
assert.notProperty(cityCodeResponse.body, "error")
```

Returns a listing of airports that have had the given country code

```javascript
countryCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).countryCode.countryCode(AIRPORT_COUNTRY_CODE).get()
```

```javascript
assert.equal( countryCodeResponse.status, 200 )
assert.notProperty(countryCodeResponse.body, "error")
```

Returns the airport with the given FlightStats code, a globally unique code across tim

```javascript
fsCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).fs.code(AIRPORT_FS_CODE).get()
```

```javascript
assert.equal( fsCodeResponse.status, 200 )
assert.notProperty(fsCodeResponse.body, "error")
```

Returns a listing of airports that have had the given IATA cod

```javascript
iataCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).iata.iataCode(AIRPORT_IATA_CODE).get()
```

```javascript
assert.equal( iataCodeResponse.status, 200 )
assert.notProperty(iataCodeResponse.body, "error")
```

Returns the airport that had the IATA code on the given date

```javascript
iataCodeDayResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).iata.iataCode(AIRPORT_IATA_CODE).year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( iataCodeDayResponse.status, 200 )
assert.notProperty(iataCodeDayResponse.body, "error")
```

Returns a listing of airports that have had the given ICAO cod

```javascript
icaoAirportCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).icao.icaoCode(AIRPORT_ICAO_CODE).get()
```

```javascript
assert.equal( icaoAirportCodeResponse.status, 200 )
assert.notProperty(icaoAirportCodeResponse.body, "error")
```

```javascript
dayResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).icao.icaoCode(AIRPORT_ICAO_CODE).year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( dayResponse.status, 200 )
assert.notProperty(dayResponse.body, "error")
```

Returns a listing of airports located within a specified radius of the given position

```javascript
radiusMilesResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).withinRadius.longitude(AIRPORT_LONGITUDE).latitude(AIRPORT_LATITUDE).radiusMiles("50").get()
```

```javascript
assert.equal( radiusMilesResponse.status, 200 )
assert.notProperty(radiusMilesResponse.body, "error")
```

Returns the airport that currently has the given code (or null if none)

```javascript
todayCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).code("PDX").today.get()
```

```javascript
assert.equal( todayCodeResponse.status, 200 )
assert.notProperty(todayCodeResponse.body, "error")
```

Returns the airport that had the given code on the given date

```javascript
yearMonthDayCodeResponse = client.airports.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).code("PDX").year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( yearMonthDayCodeResponse.status, 200 )
assert.notProperty(yearMonthDayCodeResponse.body, "error")
```