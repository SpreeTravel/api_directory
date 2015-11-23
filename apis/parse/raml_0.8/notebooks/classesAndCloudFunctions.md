---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/portal/pages/6290/preview
apiNotebookVersion: 1.1.66
title: Classes and cloud functions
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



Prepare authentication data.



```javascript

appId = prompt("Please, enter Id of your Parse application");

apiKey = prompt("Please, enter REST Api Key of your Parse application");



keyHeaders = {

  "X-Parse-Application-Id": appId,

  "X-Parse-REST-API-Key": apiKey

}

```



All objects operated by the present notebook belong to the same class called "rectangle". Let's store this class name into a variable.



```javascript

rectangleClassName = "rectangle";

```

```javascript

// Read about the Parse API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7571/versions/7694/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7571/versions/7694/definition');

```



Retrieves objects. The response body is a JSON object containing all the user-provided fields,

plus the createdAt, updatedAt, and objectId fields.



```javascript

rectanglesResponse = client.classes.className(rectangleClassName).get({}, {

  headers:keyHeaders

}, false)

```

```javascript

assert.equal(rectanglesResponse.status,200)

```



First, let's delete all the previously created rectangles.



```javascript

rectanglesResponse.body.results.forEach(function(rectangle){

  var id = rectangle.objectId

  resp = client.classes.className(rectangleClassName).objectId(id).delete({}, { headers: keyHeaders })

  assert.equal(resp.status,200)

})

```



Creates an object. When the creation is successful, the HTTP response is a 201 Created



and the Location header contains the object URL for the new object.



```javascript

createRectangleResponse = client.classes.className(rectangleClassName).post({

  "a" : 15 ,

  "b" : 30 ,

  "color" : "green"

}, { headers:keyHeaders })

rectangleId = createRectangleResponse.body.objectId

```

```javascript

assert.equal(createRectangleResponse.status,201)

```



Retrieve instance by objectId



```javascript

rectangleResponse = client.classes.className(rectangleClassName).objectId(rectangleId).get({}, { headers : keyHeaders })

```

```javascript

assert.equal(rectangleResponse.status,200)

assert.equal(rectangleResponse.body.a,15)

assert.equal(rectangleResponse.body.b,30)

assert.equal(rectangleResponse.body.color,"green")

```



Updates an object. Any keys you don't specify will remain unchanged, so you can update just a subset of the object's data.



```javascript

batchResponse = client.classes.className(rectangleClassName).objectId(rectangleId).put({

  a: 111

}, { headers: keyHeaders}, {

}, false)

```

```javascript

rectangleResponse = client.classes.className(rectangleClassName).objectId(rectangleId).get({}, { headers : keyHeaders })

assert.equal(rectangleResponse.status,200)

assert.equal(rectangleResponse.body.a,111)

```



Delete instance by objectId



```javascript

deleteRectangleResponse = client.classes.className(rectangleClassName).objectId(rectangleId).delete({}, { headers: keyHeaders })

```

```javascript

assert.equal(deleteRectangleResponse.status,200)

```



Retrieve rectangle



```javascript

rectangleResponse = client.classes.className(rectangleClassName).objectId(rectangleId).get({}, { headers: keyHeaders })

assert.equal(rectangleResponse.status,404)

```



Batch method create 



```javascript

batchResponse = client.batch.post({

  "requests" : [ {

    "method" : "POST" ,

    "path" : "/1/classes/" + rectangleClassName ,

    "body" : {

      "a" : 100 ,

      "b" : 200

    }

  } , {

    "method" : "POST" ,

    "path" : "/1/classes/" + rectangleClassName ,

    "body" : {

      "a" : 110 ,

      "b" : 220

    }

  } ]

}, { headers:keyHeaders });

```

```javascript

assert.equal(batchResponse.status,200)

batchResponse.body.forEach(function(resp){

  assert(resp.success!=null)

})

```



We are about to test cloud function call.

Here you may learn how to deploy cloud code:

https://parse.com/docs/cloud_code_guide

We suggest deployng the following function:

```

Parse.Cloud.define("calculateAreasSum", function(request, response) {



   var rectanglesArray = request.params.rectanglesArray

   if(!(rectanglesArray==null)){

     areaSum = 0;

 

    for(var ind in rectanglesArray){

      var rectangle = rectanglesArray[ind]

      areaSum += rectangle.a*rectangle.b

    }

    response.success(areaSum);

  }

  else{

    response.error("Rectangle array is null");

  }

});

```



Prepare input data for the function.



```javascript

allRectangles = client.classes.className(rectangleClassName).get({},{headers:keyHeaders})

rectanglesArray = allRectangles.body.results

```



Call a function



```javascript

functionResponse = client.functions.functionName("calculateAreasSum").post({

  "rectanglesArray" : rectanglesArray

},{

  headers: keyHeaders

})

```

```javascript

assert.equal(functionResponse.status,200)

```



Now let's perform the same calculations right here ...



```javascript

areaSum = 0;

{  

  for(var ind in rectanglesArray){

    var rectangle = rectanglesArray[ind]

    areaSum += rectangle.a*rectangle.b

  }

}

```



... and compare the results.



```javascript

assert.equal(functionResponse.body.result,areaSum)

```