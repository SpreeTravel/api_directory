---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7660/versions/7788/portal/pages/6354/preview
apiNotebookVersion: 1.1.66
title: Zillow Notebook
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

ZWSID = prompt("Please, enter your ZWS-ID")

```



As some of the zillow API methods can only return XML responses, we define a helper method for extracting response codes. This method works with both XML and JSON, so the assertion blocks are uniform.



```javascript
function extractCode( obj ){  
  
  if(!("body" in obj)){
    
    return -1
    
  }
  
  var body = obj.body  
  
  if( typeof body == "string" ){
    
    var ind1 = body.indexOf("<message>")
    
    if(ind1<0){
      
      return -2;
      
    }
    
    var ind2 = body.indexOf("<code>",ind1)
    
    if(ind2<0){
      
      return -3;
      
    }
    
    ind2 += "<code>".length
    
    var ind3 = body.indexOf("</code>",ind2)
    
    if(ind3<0){
      
      return -4;
      
    }
    
    var code = Number.parseInt(body.substring(ind2,ind3))
    
    return code
  }
  
  if(!( typeof body == "object" )){
    
    return -12
    
  }
  
  if(!("message" in body)){
    
    return -13
    
  }
  
  var message = body.message
  
  if(!( typeof message == "object")){
    
    return -14
    
  }  
  
  if(!("code" in message)){
    
    return -15
    
  }
  
  var code = message.code
  
  return code
}
```

```javascript

// Read about the Zillow RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7660/versions/7788/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7660/versions/7788/definition');

```



Returns the full property address and geographic location (latitude/longitude) and a set of identifiers that uniquely represent the region (ZIP code, city, county & state) in which the property exists



```javascript

GetZestimateResponse = client("GetZestimate.htm").get({

  "zws-id": ZWSID,

  "zpid": 48749425

})

```

```javascript

assert.equal(GetZestimateResponse.status, 200)

assert.equal(extractCode(GetZestimateResponse), 0)

```



The GetSearchResults API finds a property for a specified address. The content returned contains the address for the property or properties as well as the Zillow Property ID (ZPID) and current Zestimate. It also includes the date the Zestimate was computed, a valuation range and the Zestimate ranking for the property within its ZIP code



```javascript

GetSearchResultsResponse = client("GetSearchResults.htm").get({

  "zws-id": ZWSID,

  "address": "2114 Bigelow Ave",

  "citystatezip": "Seattle WA"

})

```

```javascript

assert.equal( GetSearchResultsResponse.status, 200 )

assert.equal(extractCode(GetSearchResultsResponse), 0)

```



The GetChart API generates a URL for an image file that displays historical Zestimates for a specific property. The API accepts as input the Zillow Property ID as well as a chart type: either percentage or dollar value change. Optionally, the API accepts width and height parameters that constrain the size of the image. The historical data can be for the past 1 year, 5 years or 10 years.



```javascript

GetChartResponse = client("GetChart.htm").get({

  "zws-id": ZWSID,

  "zpid": 48749425,

  "unit-type": "percent"

})

```

```javascript

assert.equal( GetChartResponse.status, 200 )

assert.equal(extractCode(GetChartResponse), 0)

```



The GetComps API returns a list of comparable recent sales for a specified property. The result set returned contains the address, Zillow property identifier, and Zestimate for the comparable properties and the principal property for which the comparables are being retrieved.



```javascript

GetCompsResponse = client("GetComps.htm").get({

  "zws-id": ZWSID,

  "zpid": 48749425,

  "count": 5

})

```

```javascript

assert.equal( GetCompsResponse.status, 200 )

assert.equal(extractCode(GetCompsResponse), 0)

```



The GetDeepComps API returns a list of comparable recent sales for a specified property. The result set returned contains the address, Zillow property identifier, and Zestimate for the comparable properties and the principal property for which the comparables are being retrieved. This API call also returns rich property data for the comparables.



```javascript

GetDeepCompsResponse = client("GetDeepComps.htm").get({

  "zws-id": ZWSID,

  "zpid": 48749425,

  "count": 5

})

```

```javascript

assert.equal( GetDeepCompsResponse.status, 200 )

assert.equal(extractCode(GetDeepCompsResponse), 0)

```



The GetDeepSearchResults API finds a property for a specified address. The result set returned contains the full address(s), zpid and Zestimate data that is provided by the GetSearchResults API. Moreover, this API call also gives rich property data like lot size, year built, bath/beds, last sale details etc.



```javascript

GetDeepSearchResultsResponse = client("GetDeepSearchResults.htm").get({

  "zws-id": ZWSID,

  "address": "2114 Bigelow Ave",

  "citystatezip": "Seattle WA"

})

```

```javascript

assert.equal( GetDeepSearchResultsResponse.status, 200 )

assert.equal(extractCode(GetDeepSearchResultsResponse), 0)

```



For a specified property, the GetUpdatedPropertyDetails API returns all of the home facts that have been edited by the home's owner or agent. The result set contains the following attributes:

Property address

Zillow property identifier

Posting details such as the agent name, MLS number, price, and posting type (For Sale or Make Me Move)

Up to five photos of the property

Updated home facts such as beds, baths, square footage, home description, and neighborhood and school names



```javascript

GetUpdatedPropertyDetailsResponse = client("GetUpdatedPropertyDetails.htm").get({

  "zws-id": ZWSID,

  "zpid": 48749425

})

```

```javascript

assert.equal( GetUpdatedPropertyDetailsResponse.status, 200 )

assert.equal(extractCode(GetUpdatedPropertyDetailsResponse), 0)

```



For a specified region, the GetDemographics API returns a set of market, affordability, real estate, and demographic data, including:

Local Market Data (e.g., median list price, number of homes for sale, percent listing price reduction)

Affordability data (e.g., Zillow Home Value Index, 1-Yr change, property tax, etc.)

Homes and Real Estate data (e.g., percent owners & renters, median home size, average year built, etc.)

People data from US Census (e.g., median household income, median age, average commute time, average household size, etc.)

Who Lives Here (if available for the region)

What's Unique About the People (if available for the region)



```javascript

GetDemographicsResponse = client("GetDemographics.htm").get({

  "zws-id": ZWSID,

  "state": "WA",

  "city": "Seattle",

  "neighborhood": "Ballard"

})

```

```javascript

assert.equal( GetDemographicsResponse.status, 200 )

assert.equal(extractCode(GetDemographicsResponse), 0)

```



For a specified region, the GetRegionChildren API returns a list of subregions with the following information:

Subregion Type

Region IDs

Region Names

URL to Corresponding Zillow Page (only for cities and neighborhoods)

Latitudes and Longitudes



```javascript

GetRegionChildrenResponse = client("GetRegionChildren.htm").get({

  "zws-id": ZWSID,

  "state": "WA",

  "city": "Seattle",

  "neighborhood": "Ballard"

})

```

```javascript

assert.equal( GetRegionChildrenResponse.status, 200 )

assert.equal(extractCode(GetRegionChildrenResponse), 0)

```



The GetRegionChart API generates a URL for an image file that displays the historical Zestimates for a specific geographic region. The API accepts as input the name of the region as well as a chart type: either percentage or dollar value change. Optionally, the API accepts width and height parameters that constrain the size of the image. The historical data can be for the past 1 year, 5 years or 10 years.



```javascript

GetRegionChartResponse = client("GetRegionChart.htm").get({

  "zws-id": ZWSID,

  "unit-type": "percent"

})

```

```javascript

assert.equal( GetRegionChartResponse.status, 200 )

assert.equal(extractCode(GetRegionChartResponse), 0)

```



The GetRateSummary API returns the current rates per loan type from Zillow Mortgage Marketplace. Current supported loan types are 30-year fixed, 15-year fixed, and 5/1 ARM. Rates are computed from real quotes borrowers receive from lenders just seconds before the rate data is returned. The GetRateSummary API returns rates for a specific state if the optional state parameter is used.



```javascript

GetRateSummaryResponse = client("GetRateSummary.htm").get({

  "zws-id": ZWSID,

  "output": "json"

})

```

```javascript

assert.equal( GetRateSummaryResponse.status, 200 )

assert.equal(extractCode(GetRateSummaryResponse), 0)

```



For a specific loan amount, the GetMonthlyPayments API returns the estimated monthly payment that includes principal and interest based on todays mortgage rate. The API returns the estimated monthly payment per loan type (30-year fixed, 15-year fixed, and 5/1 ARM). If a ZIP code is entered, the estimated taxes and insurance are returned in the result set



```javascript

GetMonthlyPaymentsResponse = client("GetMonthlyPayments.htm").get({

  "zws-id": ZWSID,

  "price": 300000,

  "output": "json"

})

```

```javascript

assert.equal( GetMonthlyPaymentsResponse.status, 200 )

assert.equal(extractCode(GetMonthlyPaymentsResponse), 0)

```

The CalculateAffordability API calculates the amount a user can afford based on their income or desired monthly payment. It returns an affordability amount along with detailed breakdown of the mortgage payment, amortization schedule, and monthly budget

```javascript

CalculateAffordabilityAdvancedResponse = client("/mortgage/CalculateAffordability.htm").get({
  
  "zws-id": ZWSID,
  
  "annualincome": 1000000,
  
  "monthlypayment": 2000,
  
  "down": 800000,
  
  "monthlydebts": 1500,
  
  "output": "json"
  
})

```

```javascript

assert.equal( CalculateAffordabilityAdvancedResponse.status, 200 )

assert.equal(extractCode(CalculateAffordabilityAdvancedResponse), 0)

```