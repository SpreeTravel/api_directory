---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/8031/versions/8196/portal/pages/6885/preview
apiNotebookVersion: 1.1.66
title: SIP IpAccessControlLists
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



Return a paged list of all IpAccessControlLists under this account



```javascript

ipAccessControlListsResponse = client.Accounts.AccountSid(rootAccountSid).SIP.IpAccessControlLists.json.get()

```

```javascript

assert.equal( ipAccessControlListsResponse.status, 200 )

```



Create a new IpAccessControlList resource.



When created, the list will contain no IP addresses. You will need to add IP addresses to the list for it to be active. To add IP addresses, you will need to POST to the IpAddresses List subresource.



```javascript

ipAccessControlListCreateResponse = client.Accounts.AccountSid(rootAccountSid).SIP.IpAccessControlLists.json.post({

  "FriendlyName": "API Notebook test ip access controll list"

})

```

```javascript

assert.equal( ipAccessControlListCreateResponse.status, 201 )

ipAccessControlListSid = ipAccessControlListCreateResponse.body.sid

```



Return a specific IpAccessControlList resource



```javascript

//ipAccessControllListResponse = client.Accounts.AccountSid(rootAccountSid).SIP.IpAccessControlLists.IpAccessControlListSid(ipAccessControllListSid).json.get()

ipAccessControllListResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}.json",{

  AccountSid: rootAccountSid,

  IpAccessControlListSid: ipAccessControlListSid

}).get()

```

```javascript

assert.equal( ipAccessControllListResponse.status, 200 )

```



Rename an IpAccessControlList.



```javascript

ipAccessControllListUpdateResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}.json",{

  AccountSid: rootAccountSid,

  IpAccessControlListSid: ipAccessControlListSid

}).post({

  "FriendlyName": "API Notebook test ip access control list (upd)"

}, {headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( ipAccessControllListUpdateResponse.status, 200 )

```



List the IP Addresses contained in this list



```javascript

//ipAddressesResponse = client.Accounts.AccountSid(rootAccountSid).SIP.IpAccessControlLists.IpAccessControlListSid(ipAccessControllListSid).IpAddressSid.json.get()

ipAddressesResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}/IpAddresses.json",{

  AccountSid : rootAccountSid,

  IpAccessControlListSid : ipAccessControlListSid

}).get()

```

```javascript

assert.equal( ipAddressesResponse.status, 200 )

//ipAddressSid = ipAddressesResponse.body.ip_addresses[0].sid

```



Add an IP Address to the list with a description.



```javascript

ipAddresseCreateResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}/IpAddresses.json",{

  AccountSid: rootAccountSid,

  IpAccessControlListSid: ipAccessControlListSid

}).post({

  "FriendlyName": "API Notebook Test Ip Address",

  "IpAddress" : "55.102.123.124"

},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( ipAddresseCreateResponse.status, 201 )

ipAddressSid = ipAddresseCreateResponse.body.sid

```



Return a single IP Address resource.



```javascript

ipAddressResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}/IpAddresses/{IpAddressSid}.json",{

  AccountSid : rootAccountSid,

  IpAccessControlListSid : ipAccessControlListSid,

  IpAddressSid : ipAddressSid

}).get()

```

```javascript

assert.equal( ipAddressResponse.status, 200 )

```



Change the description or IP address of a given IpAddress instance resource



```javascript

ipAddressUpdateResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}/IpAddresses/{IpAddressSid}.json",{

  AccountSid : rootAccountSid,

  IpAccessControlListSid : ipAccessControlListSid,

  IpAddressSid : ipAddressSid

}).post({

  "FriendlyName": "API Notebook Test Ip Address (upd)",

  "IpAddress": "IpAddressValue"

})

```

```javascript

assert.equal( ipAddressUpdateResponse.status, 200 )

```



Deletes an IP address entry from the list.



```javascript

ipAddressDeleteResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}/IpAddresses/{IpAddressSid}.json",{

  AccountSid : rootAccountSid,

  IpAccessControlListSid : ipAccessControlListSid,

  IpAddressSid : ipAddressSid

}).delete()

```

```javascript

assert.equal( ipAddressDeleteResponse.status, 204 )

```



Return the IpAccessControlListMappings that are associated to this domain



```javascript

//ipAccessControlListMappingsResponse = client.Accounts.AccountSid(rootAccountSid).SIP.Domains.SipDomainSid(domainSid).IpAccessControlListMappings.json.get()

ipAccessControlListMappingsResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/IpAccessControlListMappings.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid

}).get()

```

```javascript

assert.equal( ipAccessControlListMappingsResponse.status, 200 )

```



Map an IpAccessControlList to this domain



```javascript

ipAccessControlListMappingCreateResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/IpAccessControlListMappings.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid

}).post({

  "IpAccessControlListSid": ipAccessControlListSid

},{headers:{"Content-Type":"application/x-www-form-urlencoded"}})

```

```javascript

assert.equal( ipAccessControlListMappingCreateResponse.status, 201 )

```



Return a specific IpAccessControlListMapping instance by Sid



```javascript

ipAccessControlListMappingResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/IpAccessControlListMappings/{ALSid}.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid,

  ALSid : ipAccessControlListSid

}).get()

```

```javascript

assert.equal( ipAccessControlListMappingResponse.status, 200 )

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



Remove a mapping from this domain



```javascript

ipAccessControlListMappingDeleteResponse = client("/Accounts/{AccountSid}/SIP/Domains/{SipDomainSid}/IpAccessControlListMappings/{ALSid}.json",{

  AccountSid : rootAccountSid,

  SipDomainSid : domainSid,

  ALSid : ipAccessControlListSid

}).delete({})

```

```javascript

assert.equal( ipAccessControlListMappingDeleteResponse.status, 204 )

```



Delete an IpAccessControlList from your account. It can only be deleted if no domains are mapped to it. If you attempt to delete one that is mapped to a domain, you will receive an error



```javascript

//ipAccessControllListDeleteResponse = client.Accounts.AccountSid(rootAccountSid).SIP.IpAccessControlLists.IpAccessControlListSid(ipAccessControllListSid).json.delete()

ipAccessControllListDeleteResponse = client("/Accounts/{AccountSid}/SIP/IpAccessControlLists/{IpAccessControlListSid}.json",{

  AccountSid: rootAccountSid,

  IpAccessControlListSid: ipAccessControlListSid

}).delete({})

```

```javascript

assert.equal( ipAccessControllListDeleteResponse.status, 204 )

```



Delete a domain. If you have created subdomains of a domain, you will not be able to delete the domain until you first delete all subdomains of it



```javascript

domainDeleteResponse = client.Accounts.AccountSid(rootAccountSid).SIP.Domains.SipDomainSid(domainSid).json.delete({})

```

```javascript

assert.equal( domainDeleteResponse.status, 204 )

```