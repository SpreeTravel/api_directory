---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7634/versions/7760/portal/pages/6332/preview
apiNotebookVersion: 1.1.66
title: Fuel Economy API Notebook
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



Data for our tests.



```javascript

YEAR = 2010,

MAKE = "Honda",

MODEL = "Fit"

```

```javascript

// Read about the Fuel Economy RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7634/versions/7760/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7634/versions/7760/definition');

```



Returns a list of model year



```javascript

yearResponse = client.vehicle.menu.year.get()

```

```javascript

assert.equal( yearResponse.status, 200 )

```



Return a list of makes for a particular year



```javascript

makeResponse = client.vehicle.menu.make.get( { year: YEAR } )

```

```javascript

assert.equal( makeResponse.status, 200 )

```



Return a list of models for a particular year and make



```javascript

modelResponse = client.vehicle.menu.model.get({

  year: YEAR,

  make: MAKE

})

```

```javascript

assert.equal( modelResponse.status, 200 )

```



Return a list of model options and the associated vehicle ID for a particular year, make and model



```javascript

optionsResponse = client.vehicle.menu.options.get({

  year: YEAR,

  make: MAKE,

  model: MODEL

})

```

```javascript

assert.equal( optionsResponse.status, 200 )

```



Let's grab some _vehicleId_ from the response above.



```javascript

vehicleId = 0

{

  var body = optionsResponse.body

  var ind1 = body.indexOf("")

  ind1 += "".length

  var ind2 = body.indexOf("",ind1)

  vehicleId = body.substring(ind1,ind2)

}

```



Return a specific vehicle record



```javascript

vrhicleResponse = client.vehicle.id(vehicleId).get()

```

```javascript

assert.equal( vrhicleResponse.status, 200 )

```



Return a list of emission records for specific vehicle record



```javascript

emissionsResponse = client.vehicle.emissions.id(vehicleId).get()

```

```javascript

assert.equal( emissionsResponse.status, 200 )

```

```javascript

/*

We do not test this method as it is the place where service behavior is incorrect.

For example, this response can not be fulfilled in Postman REST client.

Trying to open http://www.fueleconomy.gov/ws/rest/vehicle/export/all in browsers also causes errors.

The chrome browser starts showing huge amounts of data received and finally ends up with an error:

== Chrome msg ==

This page contains the following errors:

error on line 1 at column 60152898: Extra content at the end of the document

Below is a rendering of the page up to the first error.

====

Firefox does not show any data, but it also ends up with an error:

== Firefox msg ==

XML Parsing Error: junk after document element

Location: http://www.fueleconomy.gov/ws/rest/vehicle/export/all

Line Number 1, Column 60152901: 

====

*/

//exportallResponse = client.vehicle.export.all.get()

```

```javascript

//assert.equal( exportallResponse.status, 200 )

```



Return the current fuel price



```javascript

fuelpricesResponse = client.fuelprices.get()

```

```javascript

assert.equal( fuelpricesResponse.status, 200 )

```



Return a summary of shared user MPG data for a specific vehicle Id



```javascript

ympgVehicleResponse = client.ympg.shared.ympgVehicle.id(vehicleId).get()

```

```javascript

assert.equal( ympgVehicleResponse.status, 200 )

```



Return a list of user MPG records for a specific vehicle Id



```javascript

ympgDriverVehicleResponse = client.ympg.shared.ympgDriverVehicle.id(vehicleId).get()

```

```javascript

assert.equal( ympgDriverVehicleResponse.status, 200 )

```



Menu of shared make



```javascript

makeResponse = client.ympg.shared.menu.make.get()

```

```javascript

assert.equal( makeResponse.status, 200 )

```



Menu of shared models for the specified make



```javascript

modelResponse = client.ympg.shared.menu.model.get( { make: MAKE } )

```

```javascript

assert.equal( modelResponse.status, 200 )

```



List of vehicles with shared Your MPG data



```javascript

vehiclesResponse = client.ympg.shared.vehicles.get({

  make: MAKE,

  model: MODEL

})

```

```javascript

assert.equal( vehiclesResponse.status, 200 )

```



Recommendations for a given vehicle



```javascript

recommendationResponse = client.recommendation.byid.get({id1:vehicleId})

```

```javascript

assert.equal( recommendationResponse.status, 200 )

```



Find a SmartWay Vehicle



```javascript

smartwayResponse = client.smartway.vehicles.get({

  year: 2008,

  vclass: "Coupes",

  state: "CA"  

})

```

```javascript

assert.equal( smartwayResponse.status, 200 )

```



Find EISA141 vehicles



```javascript

fleetResponse = client.fleet.eisa141.get({

  vclass: "Midsize Cars",

  year1: 2010,

  year2: 2013,

  ffv: true

})

```

```javascript

assert.equal( fleetResponse.status, 200 )

```