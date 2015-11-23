---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#1096e72662b6afa390dc
apiNotebookVersion: 1.1.66
title: Zillow (part 2 -- mortgage)
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

The CalculateMonthlyPaymentsAdvanced API returns the estimated monthly payment, total payment, and amortization table for a given loan amount. It includes detailed monthly payment breakdown including taxes and insurance, as well as payment totals over the life of the loan.

```javascript
CalculateMonthlyPaymentsAdvancedResponse = client("/mortgage/CalculateMonthlyPaymentsAdvanced.htm").get({
  "zws-id": ZWSID,
  "price": 30000,
  "output": "json"
})
```

```javascript
assert.equal( CalculateMonthlyPaymentsAdvancedResponse.status, 200 )
assert.equal(extractCode(CalculateMonthlyPaymentsAdvancedResponse), 0)
```

The CalculateNoCostVsTraditional API calculates whether you will save money by taking the no cost or traditional mortgage over a given time period
&rate=6.504&nocostrate=&terminmonths=360&closingcosts=2000&rollfees=false&costofmoney=0&taxbracket=15&

```javascript
CalculateNoCostVsTraditionalResponse = client("/mortgage/CalculateNoCostVsTraditional.htm").get({
  "zws-id": ZWSID,
  "nocostrate": 4.503,
  "closingcosts": 2000,
  "rollfees": false,
  "rate": 6.504,
  "amount": 10000000,
  "taxbracket": 15,
  "comparisonyears": 50,
  "output": "json"
})
```

```javascript
assert.equal( CalculateNoCostVsTraditionalResponse.status, 200 )
assert.equal(extractCode(CalculateNoCostVsTraditionalResponse), 0)
```

The CalculateDiscountPoints API calculates the breakeven point and total savings you would receive by paying points on a loan

```javascript
CalculateDiscountPointsResponse = client("/mortgage/CalculateDiscountPoints.htm").get({
  "zws-id": ZWSID,
  "rate": 6.504,
  "rate2": 5.454,
  "points": 2.543,
  "points2": 3,
  "amount": 10000000,
  "output": "json"
})
```

```javascript
assert.equal( CalculateDiscountPointsResponse.status, 200 )
assert.equal(extractCode(CalculateDiscountPointsResponse), 0)
```

The CalculateFixedVsAdjustableRate API calculates whether you will save money by getting a fixed versus adjustable rate mortgage over a given time period

```javascript
CalculateFixedVsAdjustableRateResponse = client("/mortgage/CalculateFixedVsAdjustableRate.htm").get({
  "zws-id": ZWSID,
  "amount": 10000000,
  "monthsbeforeadjustment": 60,
  "monthsbetweenadjustments": 12,
  "adjustment": 0.03,
  "ratecap": 18,
  "output": "json"
})
```

```javascript
assert.equal( CalculateFixedVsAdjustableRateResponse.status, 200 )
assert.equal(extractCode(CalculateFixedVsAdjustableRateResponse), 0)
```

The CalculateMortgageTerms API compares the costs of different types of loans. Compare multiple loans that have different terms and rates over any time period

```javascript
CalculateMortgageTermsResponse = client("/mortgage/CalculateMortgageTerms.htm").get({
  "zws-id": ZWSID,
  "rate": 6.504,
  "rate2": 5.45,
  "terminmonths": 360,
  "terminmonths2": 480,
  "amount": 10000000,
  "output": "json"
})
```

```javascript
assert.equal( CalculateMortgageTermsResponse.status, 200 )
assert.equal(extractCode(CalculateMortgageTermsResponse), 0)
```

The CalculateHELOC API calculates how much could be borrowed for a home equity line of credit on a given home based on home value and outstanding balance

```javascript
CalculateHELOCResponse = client("/mortgage/CalculateHELOC.htm").get({
  "zws-id": ZWSID,
  "value": 10000000,
  "balance": 20000,
  "ltv": 6.5,
  "output": "json"
})
```

```javascript
assert.equal( CalculateHELOCResponse.status, 200 )
assert.equal(extractCode(CalculateHELOCResponse), 0)
```

The CalculateBiWeeklyPayment API calculates how much you could save by switching to bi-weekly payments as well as how long it would take to pay off the loan

```javascript
CalculateBiWeeklyPaymentResponse = client("/mortgage/CalculateBiWeeklyPayment.htm").get({
  "zws-id": ZWSID,
  "rate": 6.504,
  "amount": 10000000,
  "output": "json"
})
```

```javascript
assert.equal( CalculateBiWeeklyPaymentResponse.status, 200 )
assert.equal(extractCode(CalculateBiWeeklyPaymentResponse), 0)
```

The CalculateAdjustableMortgage API shows how changing interest rates will affect monthly payments and interest costs over the life of an adjustable rate mortgage

```javascript
CalculateAdjustableMortgageResponse = client("/mortgage/CalculateAdjustableMortgage.htm").get({
  "zws-id": ZWSID,
  "monthsbeforeadjustment": 60,
  "monthsbetweenadjustments": 12,
  "adjustment": 0.03,
  "ratecap": 18,
  "amount": 10000000,
  "output": "json"
})
```

```javascript
assert.equal( CalculateAdjustableMortgageResponse.status, 200 )
assert.equal(extractCode(CalculateAdjustableMortgageResponse), 0)
```

The CalculateRefinance API calculates whether or not it makes sense to refinance. It returns the amount of time it would take to break even as well as monthly and lifetime savings

**Attention** This method now throws a 506 exception which means "Invalid new loan term". The suggested way to resolve is to "Make sure the 'newterm' parameter is specified and is a valid integer" and it does not help. See http://www.zillow.com/howto/api/CalculateRefinance.htm

```javascript
// CalculateRefinanceResponse = client("/mortgage/CalculateRefinance.htm").get({
//   "zws-id": ZWSID,
//   "currentamount": 1000000,
//   "currentrate": 6.504,
//   "originationyear": 2009,
//   "currentterm": 80,
//   "newamount": 800000,
//   "newterm": 80,
//   "fees": 20000,
//   "output": "json"
// })
```

```javascript
// assert.equal( CalculateRefinanceResponse.status, 200 )
// assert.equal(extractCode(CalculateRefinanceResponse), 0)
```

The CalculateAffordability API calculates the amount a user can afford based on their income or desired monthly payment. It returns an affordability amount along with detailed breakdown of the mortgage payment, amortization schedule, and monthly budget

```javascript
CalculateAffordabilityResponse = client("/mortgage/CalculateAffordability.htm").get({
  "zws-id": ZWSID,
  "annualincome": 1000000,
  "monthlypayment": 2000,
  "down": 800000,
  "monthlydebts": 1500,
  "output": "json"
})
```

```javascript
assert.equal( CalculateAffordabilityResponse.status, 200 )
assert.equal(extractCode(CalculateAffordabilityResponse), 0)
```

The CalculateTaxSavings API calculates how much you would save in taxes over the life of a mortgage

```javascript
CalculateTaxSavingsResponse = client("/mortgage/CalculateTaxSavings.htm").get({
  "zws-id": ZWSID,
  "amount": 10000000,
  "rate": 6.504,
  "taxbracket": 3.50,
  "output": "json"
})
```

```javascript
assert.equal( CalculateTaxSavingsResponse.status, 200 )
assert.equal(extractCode(CalculateTaxSavingsResponse), 0)
```

Calculates the costs for interest only loans and compares them against the costs of a traditional mortgage

```javascript
CalculateInterstOnlyVsTraditionalResponse = client("/mortgage/CalculateInterstOnlyVsTraditional.htm").get({
  "zws-id": ZWSID,
  "interestterminmonths": 120,
  "amount": 10000000,
  "output": "json"
})
```

```javascript
assert.equal( CalculateInterstOnlyVsTraditionalResponse.status, 200 )
assert.equal(extractCode(CalculateInterstOnlyVsTraditionalResponse), 0)
```