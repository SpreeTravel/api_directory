---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/organizations/52560d3f-c37a-409d-9887-79e0a9a9ecff/dashboard/apis/19694/versions/21039/portal/pages/34337/edit
apiNotebookVersion: 1.1.67
title: Status And Tracks
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

App credentials, Response data format.

```javascript
APPID = prompt("Please input APPID")
APPKEY = prompt("Please input APPKEY")
MEDIA_TYPE_JSON = "json"
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

Returns the Flight Statuses for the given Carrier and Flight Number that departed on the given date. Optionally, the arrival airport may be specified

```javascript
carrierFlightStatusesResponse = client.mediaType(MEDIA_TYPE_JSON).flight.status.carrier("AA").flight("1113").arr.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( carrierFlightStatusesResponse.status, 200 )
assert.notProperty(carrierFlightStatusesResponse.body, "error")
ID_FLIGHT = carrierFlightStatusesResponse.body.flightStatuses[0].flightId
```

Returns the Flight Status associated with provided Flight ID. The Flight ID is an arbitrary number that FlightStats uses to uniquely identify flight

```javascript
flightStatusResponse = client.mediaType(MEDIA_TYPE_JSON).flight.status.flightId(ID_FLIGHT).get()
```

```javascript
assert.equal( flightStatusResponse.status, 200 )
assert.notProperty(flightStatusResponse.body, "error")
```

Returns the Flight Statuses for the given Carrier and Flight Number that departed on the given date.

```javascript
flightNumberStatusResponse = client.mediaType(MEDIA_TYPE_JSON).flight.status.carrier("AA").flight("1113").dep.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( flightNumberStatusResponse.status, 200 )
assert.notProperty(flightNumberStatusResponse.body, "error")
```

Returns the positional track for a specific flight, specified by flight ID. Flight plan may be optionally included. To narrow down to only the freshest data, you may optionally limit the age (in minutes) and/or number of positions returned. You can obtain a candidate Flight ID using the other Flight Status API'

```javascript
trackFlightPositionResponse = client.mediaType(MEDIA_TYPE_JSON).flight.track.flightId(ID_FLIGHT).get()
```

```javascript
assert.equal( trackFlightPositionResponse.status, 200 )
assert.notProperty(trackFlightPositionResponse.body, "error")
```

Returns the positional tracks of flights, with a given carrier and flight number, arriving or having arrived on the given date. If 'hourOfDay' is specified, results will be limited to the given hour, unless 'numHours' is also specified. Arrival airport may optionally be specified. Flight plans may be optionally included. To narrow down to only the freshest data, you may optionally limit the age (in minutes) and/or number of positions per track

```javascript
positionalTracksFlightsArrivingResponse = client.mediaType(MEDIA_TYPE_JSON).flight.tracks.carrier("AA").flight("1113").arr.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( positionalTracksFlightsArrivingResponse.status, 200 )
assert.notProperty(positionalTracksFlightsArrivingResponse.body, "error")
```

Returns the positional tracks of flights, with a given carrier and flight number, departing or having departed on the given date. If 'hourOfDay' is specified, results will be limited to the given hour, unless 'numHours' is also specified. Departure airport may optionally be specified. Flight plans may be optionally included. To narrow down to only the freshest data, you may optionally limit the age (in minutes) or number of positions per track

```javascript
positionalTracksFlightsDepartingResponse = client.mediaType(MEDIA_TYPE_JSON).flight.tracks.carrier("AA").flight("1113").dep.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( positionalTracksFlightsDepartingResponse.status, 200 )
assert.notProperty(positionalTracksFlightsDepartingResponse.body, "error")
```

Returns the status of all flights departing (or having departed) from an airport within the specified hour, or within a window up to 6 hours wide if numHours is specified

```javascript
 statusAllFlightsDepartingResponse = client.mediaType(MEDIA_TYPE_JSON).airport.status.airport("ORD").dep.year(YEAR).month(MONTH).day(DAY).hourOfDay(DAY_HOUR).get()
```

```javascript
assert.equal( statusAllFlightsDepartingResponse.status, 200 )
assert.notProperty(statusAllFlightsDepartingResponse.body, "error")
```

Returns the status of all flights arriving (or having arrived) at an airport within the specified hour, or within a window up to 6 hours wide if numHours is specified

```javascript
statusAllFlightsArrivingResponse = client.mediaType(MEDIA_TYPE_JSON).airport.status.airport("ORD").arr.year(YEAR).month(MONTH).day(DAY).hourOfDay(DAY_HOUR).get()
```

```javascript
assert.equal( statusAllFlightsArrivingResponse.status, 200 )
assert.notProperty(statusAllFlightsArrivingResponse.body, "error")
```

Returns the positional tracks of active flights having the specified arrival airport. Flight plans may be optionally included. "Active" flights are those for which positional data are available, and which have not yet landed. To narrow down to only the freshest data, you may optionally limit the age (in minutes) and/or number of positions per track

```javascript
positionalAirportTracksResponse = client.mediaType(MEDIA_TYPE_JSON).airport.tracks.airport("ORD").arr.get()
```

```javascript
assert.equal( positionalAirportTracksResponse.status, 200 )
assert.notProperty(positionalAirportTracksResponse.body, "error")
```

Returns the positional tracks of active flights having the specified departure airport. Flight plans may be optionally included. "Active" flights are those for which positional data are available, and which have not yet landed. To narrow down to only the freshest data, you may optionally limit the age (in minutes) and/or number of positions per track.'

```javascript
positionalAirportDepTracksResponse = client.mediaType(MEDIA_TYPE_JSON).airport.tracks.airport("UUS").dep.get()
```

```javascript
assert.equal( positionalAirportDepTracksResponse.status, 200 )
assert.notProperty(positionalAirportDepTracksResponse.body, "error")
```

Returns the status of all flights for a given route departing on the specified dat

```javascript
allRouteStatusDepResponse = client.mediaType(MEDIA_TYPE_JSON).route.status.departureAirport("ORD").arrivalAirport("LAX").dep.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( allRouteStatusDepResponse.status, 200 )
assert.notProperty(allRouteStatusDepResponse.body, "error")
```

Returns the status of all flights for a given route arriving on the specified dat

```javascript
allRouteStatusArrResponse = client.mediaType(MEDIA_TYPE_JSON).route.status.departureAirport("ORD").arrivalAirport("LAX").arr.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( allRouteStatusArrResponse.status, 200 )
assert.notProperty(allRouteStatusArrResponse.body, "error")
```

Get all flights with current positions inside a specified bounding box. Bounding box coordinates are specified in degrees of latitude/longitude. Sides of box must not exceed 5 degrees in length

```javascript
boundingBoxResponse = client.mediaType(MEDIA_TYPE_JSON).flightsNear.topLat("51.46666717529297").leftLon("-1.1166666746139526").bottomLat("51.06666717529297").rightLon("-0.44999998807907104").get()
```

```javascript
assert.equal( boundingBoxResponse.status, 200 )
assert.notProperty(boundingBoxResponse.body, "error")
```

Get flights centered around a particular point, within a given radius. The area searched is a square (not circle); the radius specifies the distance from the center of the square to one of its corners. Center point is specified in degrees of latitude and longitude; radius is specified in miles

```javascript
flightsAroundPointResponse = client.mediaType(MEDIA_TYPE_JSON).flightsNear.lat("3.543056").lon("-76.381389").miles("8").get()
```

```javascript
assert.equal( flightsAroundPointResponse.status, 200 )
assert.notProperty(flightsAroundPointResponse.body, "error")
```

Methods ahead works with paid api plans only.

Returns the positional tracks of active flights for a fleet. Flight plans may be optionally included. "Active" flights are those for which positional data are available, and which have not yet landed. To narrow down to only the freshest data, you may optionally limit the age (in minutes) or number of positions per track.

```javascript
isPaid = confirm("Do you have Flight Stats paid plan?")
```

```javascript
if (isPaid){
  tracksCarrierResponse = client.mediaType(MEDIA_TYPE_JSON).fleet.tracks.carrier("AA").get()
}
```

```javascript
if(isPaid){
	assert.equal( tracksCarrierResponse.status, 200 )
	assert.notProperty(tracksCarrierResponse.body, "error")
}
```

Returns the status of all flights for a fleet departing or having departed on the given date (UTC). If 'hourOfDay' is specified, results will be limited to the given hour, unless 'numHours' is also specified

```javascript
if(isPaid){
	statusCarrierResponse = client.mediaType(MEDIA_TYPE_JSON).fleet.status.carrier("AA").dep.year(YEAR).month(MONTH).day(DAY).hourOfDay(DAY_HOUR).get()
}
```

```javascript
if(isPaid){
	assert.equal( statusCarrierResponse.status, 200 )
	assert.notProperty(statusCarrierResponse.body, "error")
}
```