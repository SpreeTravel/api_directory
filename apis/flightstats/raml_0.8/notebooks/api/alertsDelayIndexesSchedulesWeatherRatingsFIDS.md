---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/organizations/52560d3f-c37a-409d-9887-79e0a9a9ecff/dashboard/apis/19695/versions/21040/portal/pages/34344/edit
apiNotebookVersion: 1.1.67
title: Alerts, Delay Index, FIDS, Ratings
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

Alerts resource info.

```javascript
resourceResponse = client.alerts.rest.resources.get()
```

```javascript
assert.equal( resourceResponse.status, 200 )
assert.notProperty(resourceResponse.body, "error")
```

Alerts minimal info

```javascript
alertsRestMediaTypeResponse = client.alerts.rest.resources.extension(MEDIA_TYPE_JSON_EXT).get()
```

```javascript
assert.equal( alertsRestMediaTypeResponse.status, 200 )
assert.notProperty(alertsRestMediaTypeResponse.body, "error")
```

E-mail for receiving alerts.

```javascript
EMAIL = "your@email.com"
userEmail = prompt("Please input your email for receiving alerts. \nOr click \"Cancel\" button for using fake email.")
if ((userEmail != null) && (userEmail != "")){
	EMAIL = userEmail
}
```

Create a flight rule to be monitored for a specific flight between two airports departing on the given day. Returns the fully constructed flight rule that was created

```javascript
flightMonitoringResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).create.carrier("AA").flightNumber("1113").from.departureAirport("LAX").to.arrivalAirport("ORD").departing.year(YEAR).month(MONTH).day(DAY).get({"type":MEDIA_TYPE_JSON, "deliverTo":EMAIL})
```

```javascript
assert.equal( flightMonitoringResponse.status, 200 )
assert.notProperty(flightMonitoringResponse.body, "error")
```

Create a flight rule to be monitored for a specific flight between two airports arriving on the given day. Returns the fully constructed flight rule that was created

```javascript
arrivingMonitoringResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).create.carrier("AA").flightNumber("1113").from.departureAirport("LAX").to.arrivalAirport("ORD").arriving.year(YEAR).month(MONTH).day(DAY).get({"type":MEDIA_TYPE_JSON, "deliverTo":EMAIL})
```

```javascript
assert.equal( arrivingMonitoringResponse.status, 200 )
assert.notProperty(arrivingMonitoringResponse.body, "error")
```

Create a flight rule to be monitored for a specific flight departing from an airport on the given day. Returns the fully constructed flight rule that was created

```javascript
departingMonitoringResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).create.carrier("AA").flightNumber("1113").from.departureAirport("LAX").departing.year(YEAR).month(MONTH).day(DAY).get({"type":MEDIA_TYPE_JSON, "deliverTo":EMAIL})
```

```javascript
assert.equal( departingMonitoringResponse.status, 200 )
assert.notProperty(departingMonitoringResponse.body, "error")
```

Create a flight rule to be monitored for a specific flight arriving at an airport on the given day. Returns the fully constructed flight rule that was created

```javascript
arrivingAirportMonitoringResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).create.carrier("AA").flightNumber("1113").to.arrivalAirport("ORD").arriving.year(YEAR).month(MONTH).day(DAY).get({"type":MEDIA_TYPE_JSON, "deliverTo":EMAIL})
```

```javascript
assert.equal( arrivingAirportMonitoringResponse.status, 200 )
assert.notProperty(arrivingAirportMonitoringResponse.body, "error")
ID_RULE = arrivingAirportMonitoringResponse.body.rule.id
```

Returns the flight rule that was previously created given a rule ID

```javascript
ruleResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).get.ruleId(ID_RULE).get()
```

```javascript
assert.equal( ruleResponse.status, 200 )
assert.notProperty(ruleResponse.body, "error")
```

Deletes a flight rule that was previously created given a rule ID. Returns the flight rule that was deleted. Note that once deleted any subsequent calls with the same ID will return a rule not found exception

```javascript
deleteRuleResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).delete.ruleId(ID_RULE).get()
```

```javascript
assert.equal( deleteRuleResponse.status, 200 )
assert.notProperty(deleteRuleResponse.body, "error")
```

Returns at most the last thousand Alert Rule IDs. See the alternative form of this to specify the max Rule ID, which allows for iteration over all Rule IDs

```javascript
alertListResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).list.get({"appId":APPID,"appKey":APPKEY})
```

```javascript
assert.equal( alertListResponse.status, 200 )
assert.notProperty(alertListResponse.body, "error")
```

Returns at most the last thousand Alert Rule IDs that are less than the given max Rule ID. See the alternative form of this list the last 1000 Rule IDs

```javascript
alertLessThanResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).list.lessThan(ID_RULE).get({"appId":APPID,"appKey":APPKEY})
```

```javascript
assert.equal( alertLessThanResponse.status, 200 )
assert.notProperty(alertLessThanResponse.body, "error")
```

We made this service to help you create and test your alert webservices, where FlightStats will send alert messages. When you invoke this service, FlightStats will send a simulated alert - a fake event for a fake flight - to your EMAIL or URL

```javascript
arrivalAirportResponse = client.alerts.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).testdelivery.carrier("AA").flightNumber("1113").from.departureAirport("LAX").to.arrivalAirport("ORD").get({"appId":APPID,"appKey":APPKEY, "type":MEDIA_TYPE_JSON, "deliverTo":"smtp://" + EMAIL})
```

```javascript
assert.equal( arrivalAirportResponse.status, 200 )
assert.notProperty(arrivalAirportResponse.body, "error")
```

Delayindex API resources info

```javascript
delayindexVersionResponse = client.delayindex.rest.resources.version(VERSION).get()
```

```javascript
assert.equal( delayindexVersionResponse.status, 200 )
assert.notProperty(delayindexVersionResponse.body, "error")
```

Delayindex API resource info

```javascript
delayindexResourcesResponse = client.delayindex.rest.resources.extension(MEDIA_TYPE_JSON_EXT).get()
```

```javascript
assert.equal( delayindexResourcesResponse.status, 200 )
assert.notProperty(delayindexResourcesResponse.body, "error")
```

Returns the current DelayIndex(es) for the given Airports

```javascript
airportsResponse = client.delayindex.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).airports.airports("LAX").get()
```

```javascript
assert.equal( airportsResponse.status, 200 )
assert.notProperty(airportsResponse.body, "error")
```

Returns DelayIndexes for airports in the given Country

```javascript
countryResponse = client.delayindex.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).country.country("US").get()
```

```javascript
assert.equal( countryResponse.status, 200 )
assert.notProperty(countryResponse.body, "error")
```

Returns DelayIndexes for airports in the given Region

```javascript
regionResponse = client.delayindex.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).region.region("ASIA").get()
```

```javascript
assert.equal( regionResponse.status, 200 )
assert.notProperty(regionResponse.body, "error")
```

Returns DelayIndexes for airports in the given State

```javascript
stateResponse = client.delayindex.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).state.state("IL").get()
```

```javascript
assert.equal( stateResponse.status, 200 )
assert.notProperty(stateResponse.body, "error")
```

Retrieve FIDS display data for flights departing from a given airport, including fields selected by a comma-separated list

```javascript
fidsAirportDeparturesResponse = client.fids.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).airport("LAX").departures.get()
```

```javascript
assert.equal( fidsAirportDeparturesResponse.status, 200 )
assert.notProperty(fidsAirportDeparturesResponse.body, "error")
```

Retrieve FIDS display data for flights arriving at a given airport, including fields selected by a comma-separated list

```javascript
fidsAirportArrivalsResponse = client.fids.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).airport("LAX").arrivals.get()
```

```javascript
assert.equal( fidsAirportArrivalsResponse.status, 200 )
assert.notProperty(fidsAirportArrivalsResponse.body, "error")
```

Returns the calculated ratings information for a given airline and flight number

```javascript
ratingsCalculationAirlineResponse = client.ratings.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).flight.carrier("AA").flightNumber("1113").get()
```

```javascript
assert.equal( ratingsCalculationAirlineResponse.status, 200 )
assert.notProperty(ratingsCalculationAirlineResponse.body, "error")
```

Returns the calculated ratings information for all flights having the given departure and arrival airport

```javascript
ratingsAirportDepartureArrivalResponse = client.ratings.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).route.departureAirport("LAX").arrivalAirport("ORD").get()
```

```javascript
assert.equal( ratingsAirportDepartureArrivalResponse.status, 200 )
assert.notProperty(ratingsAirportDepartureArrivalResponse.body, "error")
```

Weather resources info

```javascript
weatherVersionResponse = client.weather.rest.resources.version(VERSION).get()
```

```javascript
assert.equal( weatherVersionResponse.status, 200 )
assert.notProperty(weatherVersionResponse.body, "error")
```

Weather resource info

```javascript
weatherRestResourcesMediaTypeExtResponse = client.weather.rest.resources.extension(MEDIA_TYPE_JSON_EXT).get()
```

```javascript
assert.equal( weatherRestResourcesMediaTypeExtResponse.status, 200 )
assert.notProperty(weatherRestResourcesMediaTypeExtResponse.body, "error")
```

Retrieve all weather products (METAR, TAF, and Zone Forecast) for the airport

```javascript
allAirportWeatherProductsResponse = client.weather.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).all.airport("ORD").get()
```

```javascript
assert.equal( allAirportWeatherProductsResponse.status, 200 )
assert.notProperty(allAirportWeatherProductsResponse.body, "error")
```

Retrieve the most current available METAR weather report for the aerodrome around a given airport. METAR reports describe current conditions and are updated about once an hour

```javascript
airportMETARWeatherResponse = client.weather.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).metar.airport("ORD").get()
```

```javascript
assert.equal( airportMETARWeatherResponse.status, 200 )
assert.notProperty(airportMETARWeatherResponse.body, "error")
```

Retrieve the most current available Terminal Aerodrome Forecast (TAF) for the airport. TAFs forecast weather conditions for the area within a 5 mile radius from the center of the airport runway complex

```javascript
airportTAFResponse = client.weather.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).taf.airport("ORD").get()
```

```javascript
assert.equal( airportTAFResponse.status, 200 )
assert.notProperty(airportTAFResponse.body, "error")
```

Retrieve the most current available zone forecast for the airport. Zone forecasts can cover several days, and apply to a more extensive area around the airport than TAFs

```javascript
airportAvailorecastResponse = client.weather.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).zf.airport("ORD").get()
```

```javascript
assert.equal( airportAvailorecastResponse.status, 200 )
assert.notProperty(airportAvailorecastResponse.body, "error")
```

Schedules resources info

```javascript
schedulesResourcesResponse = client.schedules.rest.resources.get()
```

```javascript
assert.equal( schedulesResourcesResponse.status, 200 )
assert.notProperty(schedulesResourcesResponse.body, "error")
```

Schedules resource info

```javascript
schedulesResourceResponse = client("/schedules/rest/resources" + MEDIA_TYPE_JSON_EXT).get()
```

```javascript
assert.equal( schedulesResourceResponse.status, 200 )
assert.notProperty(schedulesResourceResponse.body, "error")
```

Scheduled Flight(s) by carrier and flight number, departing on the given date

```javascript
scheduledFlightResponse = client.schedules.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).flight.carrier("AA").flightnumber("1113").departing.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( scheduledFlightResponse.status, 200 )
assert.notProperty(scheduledFlightResponse.body, "error")
```

Scheduled Flight(s) by carrier and flight number, arriving on the given date

```javascript
scheduledFlightArrivingResponse = client.schedules.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).flight.carrier("AA").flightnumber("1113").arriving.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( scheduledFlightArrivingResponse.status, 200 )
assert.notProperty(scheduledFlightArrivingResponse.body, "error")
```

Scheduled Flight(s) by route, arriving on the given date

```javascript
scheduledFlightRouteResponse = client.schedules.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).from.departureAirportCode("LAX").to.arrivalAirportCode("ORD").arriving.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( scheduledFlightRouteResponse.status, 200 )
assert.notProperty(scheduledFlightRouteResponse.body, "error")
```

Scheduled Flight(s) by route, departing on the given date

```javascript
scheduledFlightDepartingResponse = client.schedules.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).from.departureAirportCode("LAX").to.arrivalAirportCode("ORD").departing.year(YEAR).month(MONTH).day(DAY).get()
```

```javascript
assert.equal( scheduledFlightDepartingResponse.status, 200 )
assert.notProperty(scheduledFlightDepartingResponse.body, "error")
```

Scheduled Flight(s) departing from the given airport at the given date and hour

```javascript
scheduledFlightAirportDepartingResponse = client.schedules.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).from.departureAirportCode("LAX").departing.year(YEAR).month(MONTH).day(DAY).hourOfDay(DAY_HOUR).get()
```

```javascript
assert.equal( scheduledFlightAirportDepartingResponse.status, 200 )
assert.notProperty(scheduledFlightAirportDepartingResponse.body, "error")
```

Scheduled Flight(s) arriving at the given airport at the given date and hour

```javascript
scheduledFlightHourOfDayResponse = client.schedules.rest.version(VERSION).mediaType(MEDIA_TYPE_JSON).to.arrivalAirportCode("ORD").arriving.year(YEAR).month(MONTH).day(DAY).hourOfDay(DAY_HOUR).get()
```

```javascript
assert.equal( scheduledFlightHourOfDayResponse.status, 200 )
assert.notProperty(scheduledFlightHourOfDayResponse.body, "error")
```