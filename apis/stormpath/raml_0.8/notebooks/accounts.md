---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7586/versions/7712/portal/pages/6298/preview
apiNotebookVersion: 1.1.66
title: Accounts
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

var assert = chai.assert

```



A StormPath resource does not have an ID field. Instead it provides a full HREF, which has ID as it's last segment. Here we define a helper method for obtaining IDs of resources.



```javascript

function extractId( obj ){

  

  var href = obj["href"];

  if(!href)

    return null;

  

  var ind = href.lastIndexOf('/')

  var id = href.substring(ind+1)

  return id;

}

```

```javascript

// Read about the StormPathRAML at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7586/versions/7712/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7586/versions/7712/definition');

```



Here we define a helper method for retrieving ID of current tenant.



```javascript

function getCurrentTenentId(){

  var currentTenantResponse = client.tenants.current.get()

  var tenantId = extractId(currentTenantResponse.body)

  return tenantId

}

```



Name and account storage name for a test application.



```javascript

applicationName = "NotebookTestApp"

accountStorageName = "NotebookTestAccountStorage"

```



In the code block below we define a helper method for getting an application with required name. If the application already exists, the method retrieves it, and in the opposite case it creates one.



```javascript

function getApplication( appName, appDescription ){



  var application = null

  var tenantId = getCurrentTenentId()

  var applicationsResponse = client.tenants.tenantId(tenantId).applications.get()

  var applications = applicationsResponse.body.items

  for( var ind in applications ){

    

    var app = applications[ind]

    if(app.name == appName){

      application = app

    }

  }

  if(application==null){

    var resp = client.applications.post({

      "name": appName,

      "description": appDescription,

      "status": "enabled"

    })

    application = resp.body

  }

  return application

}

```



Get an application.



```javascript

testApp = getApplication(applicationName,"Notebook test application")

appId = extractId(testApp)

```



In the code below we define a helper method for getting a directory with required name. If the directory does not exist, the method creates it. In the opposite case the method cleans the directory and returns it.



```javascript

function getEmptyDirectory( dirName )

{

  var tenantId = getCurrentTenentId()

  var directoriesResponse = client.tenants.tenantId(tenantId).directories.get()

  var directories = directoriesResponse.body.items

  var accStorage = null

  for( var ind in directories){

  

    var dir = directories[ind]

    var id = extractId(dir)

    

    if(dir.name == dirName){



      accStorage = dir    

      var accountsResponse = client.directories.directoryId(id).accounts.get()

      if(accountsResponse.body != null && accountsResponse.body.items != null){

        var accounts = accountsResponse.body.items

        for(var j in accounts){

          var account = accounts[j]

          if(account!=null){

            var accId = extractId(account)

            client.accounts.accountId(accId).delete()

          }

        }

      }

    }

  }

  if( accStorage == null ){

    var directoryCreateResponse = client.directories.post({

      "name" : dirName

    })

    accStorage = directoryCreateResponse.body

  }

  return accStorage

}

```



Get a directory which is to be used as account storage for our application.



```javascript

accountStorage = getEmptyDirectory(accountStorageName)

dirId = extractId(accountStorage)

```



Account storage mapping is what makes a directory an account storage. Here is a helper method which links given application and directory by an account storage mapping.



```javascript

function setAccountStorage( application, accStorage ){

  client.accountStoreMappings.post({

    "application": {

     "href": application["href"]

    },

    "accountStore": {

     "href": accStorage["href"]

    },

    "isDefaultAccountStore": true,

    "isDefaultGroupStore": true

  })

}

```



Let the directory become an account storage.



```javascript

setAccountStorage(testApp,accountStorage)

```



To conclude preparation part of the present notebook, let's create some account data:



```javascript

auEmail = "mail@somehost.com"

account = {

  "username" : "ActiveUser",

  "email" : auEmail,

  "givenName" : "John",

  "middleName" : "",

  "surname" : "Johnson",

  "password" : "Aupass_Aupass1!",

  "status" : "ENABLED"

}



//Base64 encoded username and password pair that is separated with a colon

//(in our case � "ActiveUser:Aupass_Aupass1!")

//This value is used to perform a login operation

accountSSLValue = "QWN0aXZlVXNlcjpBdXBhc3NfQXVwYXNzMSE="

```



Now we are ready to perform account related tests.



Register a new account for a certain application



```javascript

accountCreateResponse = client.applications.applicationId(appId).accounts.post(account)

```

```javascript

assert.equal( accountCreateResponse.status, 201)

accId = extractId(accountCreateResponse.body)

```



Execute an account login attempt. An HTTP POST authenticates an associated application account. Only HTTP POST is supported.



|Parameter|Required|Description|          

|---|---|

|type|true|The type of the login attempt. The only currently supported type is basic. Additional types will likely be supported in the future.|

|value|true|The Base64 encoded username:plaintextPassword pair. For example, for username jsmith or email jsmith@email.com and plaintext password mySecretPassword this value attribute would be set to the following computed result: base64_encode("jsmith:mySecretPassword"); The base64_encode method call is only an example. You will need to use the Base64 encoding method is available to you in your chosen programming language and/or software frameworks.|

|accountStore|false|An optional link to the application's accountStore (directory or group) that you are certain contains the account attempting to login. Specifying this attribute can speed up logins if you know exactly which of the application's assigned account stores contains the account: Stormpath will not have to iterate over the assigned account stores to find the account to authenticate it. This can speed up logins significantly if you have many account stores (> 15) assigned to the application.|



```javascript

loginAttemptsResponse = client.applications.applicationId(appId).loginAttempts.post({

  type: "basic",

  value: accountSSLValue

},{

  headers: {

    "Content-Type":"application/json"

  }

})

```

```javascript

assert.equal( loginAttemptsResponse.status, 200)

```



You can list your applications accounts by sending a GET request to your applications accounts Collection Resource href URL. The response is a paginated list of application accounts



```javascript

appAccountsResponse = client.applications.applicationId(appId).accounts.get()

```

```javascript

assert.equal( appAccountsResponse.status, 200)

```



A successful HTTP POST sends a password reset email to the first discovered account associated with the corresponding application. The email recipient can then click a password reset URL in the email to reset their password in a web form



```javascript

resetTokenResponse = client.applications.applicationId(appId).passwordResetTokens.post({

  "email" : auEmail

})

```

```javascript

assert.equal(resetTokenResponse .status, 200)

```

```javascript

passwordResetToken = extractId(resetTokenResponse.body)

```



Retrieving a token resource successfully using an HTTP GET indicates that the token is valid. Thus, to validate a token, you call to the passwordResetTokens end point and specify the token captured from the query string as the specific resource to request



```javascript

tokenResponse = client.applications.applicationId(appId).passwordResetTokens.token(passwordResetToken).get()

```

```javascript

assert.equal( tokenResponse.status, 200)

```



Delete an account



```javascript

accountDeleteResponse = client.accounts.accountId(accId).delete()

```

```javascript

assert.equal(accountDeleteResponse.status,204)

```



Create an account inside a certain account storage



```javascript

accountCreateResponse = client.directories.directoryId(dirId).accounts.post(account)

```

```javascript

assert.equal( accountCreateResponse.status, 201)

accId = extractId(accountCreateResponse.body)

```



You can list your directories accounts by sending a GET request to your directories accounts Collection Resource href URL. The response is a paginated list of directory accounts.



```javascript

dirAccountsResponse = client.directories.directoryId(dirId).accounts.get()

```

```javascript

assert.equal( dirAccountsResponse.status, 200)

```



Retrieve an account



```javascript

accountResponse = client.accounts.accountId(accId).get()

```

```javascript

assert.equal( accountResponse.status, 200)

```



Update an account



```javascript

accountUpdateResponse = client.accounts.accountId(accId).post({

  "favoriteColor": "red",

  "hobby": "Kendo"

})

```

```javascript

assert.equal( accountUpdateResponse.status, 200)

```



Retrieve an account custom data



```javascript

customDataResponse = client.accounts.accountId(accId).customData.get()

```

```javascript

assert.equal( customDataResponse.status, 200)

```



Update account custom data



```javascript

customDataUpdateResponse = client.accounts.accountId(accId).customData.post({

  "Occupation": "developing",

  "Language": "JAVA"

})

```

```javascript

assert.equal( customDataUpdateResponse.status, 200)

```



Delete a customData field.



```javascript

customDataDeleteFieldResponse = client.accounts.accountId(accId).customData.fieldName("Occupation").delete()

```

```javascript

assert.equal( customDataDeleteFieldResponse.status, 204)

```



Delete all of an accounts custom data



```javascript

customDataDeleteResponse = client.accounts.accountId(accId).customData.delete()

```

```javascript

assert.equal( customDataDeleteResponse.status, 204)

```



Delete an account.



```javascript

client.accounts.accountId(accId).delete()

```



**Attention.** We are about to test email verification process (user makes a registration request -> send email �> user clicks a link �> their account is verified and they can login).



At this point of notebook execution you should enable email verification enabled for the **TestAccountStorage** directory. Please, visit http://docs.stormpath.com/console/product-guide/#account-registration-and-verification in order to learn how to switch email verification for a certain directory.



```javascript

window.alert("**Attention.** We are about to test email verification features.\n\nAt this point of notebook execution you should enable email verification enabled for the **TestAccountStorage** directory.\n\nPlease, visit http://docs.stormpath.com/console/product-guide/#account-registration-and-verification in order to learn how to switch email verification for a certain directory.")

```



Create an account once again.



```javascript

userCreate2Response = client.applications.applicationId(appId).accounts.post(account)

```



When account storage has verification enabled, user creating method returns an email verification token.



```javascript

verificationToken = extractId(userCreate2Response.body.emailVerificationToken)

```



Let's use the token to verify the email.



```javascript

verificationTokenResponse = client.accounts.emailVerificationTokens.verificationToken(verificationToken).post()

```

```javascript

assert.equal( verificationTokenResponse.status, 200)

```



Finally, let's perform some garbage collection.



```javascript

client.applications.applicationId(appId).delete()

client.directories.directoryId(dirId).delete()

```