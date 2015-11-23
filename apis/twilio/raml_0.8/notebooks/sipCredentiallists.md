---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6886/preview
apiNotebookVersion: 1.1.66
title: SIP CredentialLists
---

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```

```javascript

rootAccountSid = prompt("Please, enter your Account SID which can be found at https://www.twilio.com/user/account")

```



Name of domain operated by the notebook



```javascript

domainName = "api-notebook-test-domain-" + new Date().getTime() + ".sip.twilio.com"

```

```javascript

// Read about the Twilio RAML API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/8031/versions/8196/definition');

```



Returns a paged list of the domains for an account



```javascript

domainsResponse = client("/Accounts/{AccountSid}/SIP/Domains.json",{

  "AccountSid" : rootAccountSid

}).get()

```

```javascript

assert.equal( domainsResponse.status, 200 )

```



Remove a domain that could have been created during earlier notebook runs.



```javascript

for(var ind in domainsResponse.body.domains){

  var domain = domainsResponse.body.domains[ind]

  if(domainName == domain.domain_name){

    client.Accounts.AccountSid(rootAccountSid).SIP.Domains.SipDomainSid(domain.sid).json.delete({})

  }

}

```



Creates a new Domain and returns its instance resource. You must pick a unique domain name that ends in ".sip.twilio.com".

After creating a Domain, you must map it to an authentication method before the domain is ready to receive traffic.



```javascript

domainCreateResponse = client("/Accounts/{AccountSid}/SIP/Domains.json",{

  "AccountSid" : rootAccountSid

}).post({

  "DomainName": domainName

},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( domainCreateResponse.status, 201 )

domainSid = domainCreateResponse.body.sid

```



Gets a list of Credential Lists for an account



```javascript

credentialListsResponse = client.Accounts.AccountSid(rootAccountSid).SIP.CredentialLists.json.get()

```

```javascript

assert.equal( credentialListsResponse.status, 200 )

```



Create a new Credential List



```javascript

credentialListCreateResponse = client.Accounts.AccountSid(rootAccountSid).SIP.CredentialLists.json.post({

  "FriendlyName": "API NOtebook Test Credential List"

})

```

```javascript

assert.equal( credentialListCreateResponse.status, 201 )

CrLSid = credentialListCreateResponse.body.sid

```



Get the user lists mapped to this domain



```javascript

dCredentialListMappingsResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/CredentialListMappings.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid

}).get()

```

```javascript

assert.equal( dCredentialListMappingsResponse.status, 200 )

```



Map a CredentialList to the domain



```javascript

dCredentialListMappingsCreateResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/CredentialListMappings.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid

}).post({

  "CredentialListSid": CrLSid

},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( dCredentialListMappingsCreateResponse.status, 201 )

```



Retrieve a particular credential list mapping



```javascript

dCredentialListMappingResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/CredentialListMappings/{ClSid}.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid,

  ClSid : CrLSid

}).get()

```

```javascript

assert.equal( dCredentialListMappingResponse.status, 200 )

```



Return a specific instance by Sid



```javascript

domainResponse = client.Accounts.AccountSid(rootAccountSid).SIP.Domains.SipDomainSid(domainSid).json.get()

```

```javascript

assert.equal( domainResponse.status, 200 )

```



Update the attributes of a domain



```javascript

domainUpdateResponse = client.Accounts.AccountSid(rootAccountSid).SIP.Domains.SipDomainSid(domainSid).json.post({

  "FriendlyName": "API NOtebook test domain (upd)"

})

```

```javascript

assert.equal( domainUpdateResponse.status, 200 )

```



Get the list of Credentials in a CredentialList. The passwords for the Credentials are intentionally not returned so as to protect them



```javascript

credentialsResponse = client("/Accounts/{AccountSid}/SIP/CredentialLists/{CLSid}/Credentials.json",{

  AccountSid : rootAccountSid,

  CLSid : CrLSid

}).get()

```

```javascript

assert.equal( credentialsResponse.status, 200 )

```



Add a Credential to the CredentialList.



When creating a Credential, you will POST both a username and password, but only receive the username back in the response. The password is intentionally not returned so as to protect it.



```javascript

credentialsCreateResponse = client("/Accounts/{AccountSid}/SIP/CredentialLists/{CLSid}/Credentials.json",{

  AccountSid : rootAccountSid,

  CLSid : CrLSid

}).post({

  "Password": "StrongPassword123",

  "Username": "NotebookUser"

},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( credentialsCreateResponse.status, 201 )

credentialSid = credentialsCreateResponse.body.sid

```



Get a specific Credential in a list. Though a password is stored for each username in your list, the password is not returned to protect your password. If you cannot remember your password, you will need to POST to this resource to update it



```javascript

credentialResponse = client("/Accounts/{AccountSid}/SIP/CredentialLists/{CLSid}/Credentials/{CredentialSid}.json",{

  AccountSid : rootAccountSid,

  CLSid : CrLSid,

  CredentialSid : credentialSid

}).get()

```

```javascript

assert.equal( credentialResponse.status, 200 )

```



Change the password of a Credential record.



If the change is successful, Twilio will respond with the Credential record but will not include the password in the response.



```javascript

credentialUpdateResponse = client("/Accounts/{AccountSid}/SIP/CredentialLists/{CLSid}/Credentials/{CredentialSid}.json",{

  AccountSid : rootAccountSid,

  CLSid : CrLSid,

  CredentialSid : credentialSid

}).post({

  "Password": "StrongPassword567",

  "Username": "NotebookUser"

},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( credentialUpdateResponse.status, 200 )

```



Remove a Credential from a CredentialList



```javascript

credentialDeleteResponse = client("/Accounts/{AccountSid}/SIP/CredentialLists/{CLSid}/Credentials/{CredentialSid}.json",{

  AccountSid : rootAccountSid,

  CLSid : CrLSid,

  CredentialSid : credentialSid

}).delete()

```

```javascript

assert.equal( credentialDeleteResponse.status, 204 )

```



Get a credential list instance resource



```javascript

credentialListResponse = client.Accounts.AccountSid(rootAccountSid).SIP.CredentialLists.CLSid(CrLSid).json.get()

```

```javascript

assert.equal( credentialListResponse.status, 200 )

```



Change the FriendlyName of the list



```javascript

credentialListUpdateResponse = client.Accounts.AccountSid(rootAccountSid).SIP.CredentialLists.CLSid(CrLSid).json.post({

  "FriendlyName": credentialListResponse.body.friendly_name

})

```

```javascript

assert.equal( credentialListUpdateResponse.status, 200 )

```



Remove a CredentialListMapping from a domain



```javascript

credentialListMappingDeleteResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/CredentialListMappings/{CLSid}.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid,

  CLSid : CrLSid

}).delete({})

```

```javascript

assert.equal( credentialListMappingDeleteResponse.status, 204 )

```



Delete a CredentialList from your account. It can only be deleted if no domains are mapped to it. If you attempt to delete one that is mapped to a domain, you will receive an error



```javascript

credentialListDeleteResponse = client.Accounts.AccountSid(rootAccountSid).SIP.CredentialLists.CLSid(CrLSid).json.delete({})

```

```javascript

assert.equal( credentialListDeleteResponse.status, 204 )

```



Delete a domain. If you have created subdomains of a domain, you will not be able to delete the domain until you first delete all subdomains of it



```javascript

domainDeleteResponse = client.Accounts.AccountSid(rootAccountSid).SIP.Domains.SipDomainSid(domainSid).json.delete({})

```

```javascript

assert.equal( domainDeleteResponse.status, 204 )

```