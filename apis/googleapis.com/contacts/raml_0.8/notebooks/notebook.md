---
site: https://api-notebook.anypoint.mulesoft.com/notebooks#242f1b06a1a84ddccbd1
apiNotebookVersion: 1.1.66
title: Google Contacts (cloned)
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert
```

```javascript
// Read about the Google Contacts RAML API at http://api-portal.anypoint.mulesoft.com/onpositive/api/google-contacts-raml-api-0
API.createClient('client', 'http://api-portal.anypoint.mulesoft.com/onpositive/api/google-contacts-raml-api-0/google_contacts.raml');
```

```javascript
clientId = "446158694252-9cajk3j5qp90qpsih40i7ekm8sm1v183.apps.googleusercontent.com",
clientSecret = "FnNsrFaN00oLbrjDVs1PfNK4"
```

Auxiliary method to determine object ID.

```javascript
function extractId( xmlString){
  var ind0 = xmlString.indexOf("<id>")
  if(ind0<0){
    return null
  }
  ind0 += "<id>".length
  var ind1 = xmlString.indexOf("</id>",ind0)
  if(ind1<0){
    return null
  }
  var link = xmlString.substring(ind0,ind1)
  var ind = link.lastIndexOf("/")
  if(ind<0){
    return null
  }
  ind++
  var id = link.substring(ind).trim()
  return id
}
```

Auxiliary method to determine object URL.

```javascript
function extractUrl(xmlString){
  var ind0 = xmlString.indexOf("<id>")
  if(ind0<0){
    return null
  }
  ind0 += "<id>".length
  var ind1 = xmlString.indexOf("</id>",ind0)
  if(ind1<0){
    return null
  }
  var link = xmlString.substring(ind0,ind1)
  return link
}
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId: clientId,
  clientSecret: clientSecret
})
```

To retrieve all of a user's contacts, send an authorized GET request.

```javascript
contactsResponse = client.contacts.userEmail("default").full.get()
```

```javascript
assert.equal( contactsResponse.status, 200 )
```

```javascript
client.contacts.userEmail("default").full.contactId("liz@gmail.com").get()
```

To create a new contact, send an authorized POST request to the user's contacts feed URL with contact data in the body.

```javascript
contactCreateResponse = client.contacts.userEmail("default").full.post(
"<?xml version='1.0' encoding='UTF-8'?>" + 
"<atom:entry xmlns:atom='http://www.w3.org/2005/Atom'" + 
"    xmlns:gd='http://schemas.google.com/g/2005'>" + 
"  <atom:category scheme='http://schemas.google.com/g/2005#kind'" + 
"    term='http://schemas.google.com/contact/2008#contact'/>" + 
"  <gd:name>" + 
"     <gd:givenName>Elizabeth</gd:givenName>" + 
"     <gd:familyName>Bennet</gd:familyName>" + 
"     <gd:fullName>Elizabeth Bennet</gd:fullName>" + 
"  </gd:name>" + 
"  <atom:content type='text'>Notes</atom:content>" + 
"  <gd:email rel='http://schemas.google.com/g/2005#work'" + 
"    primary='true'" + 
"    address='liz"+Math.random()+"@gmail.com' displayName='E. Bennet'/>" + 
"  <gd:email rel='http://schemas.google.com/g/2005#home'" + 
"    address='liz@example.org'/>" + 
"  <gd:phoneNumber rel='http://schemas.google.com/g/2005#work'" + 
"    primary='true'>" + 
"    (206)555-1212" + 
"  </gd:phoneNumber>" + 
"  <gd:phoneNumber rel='http://schemas.google.com/g/2005#home'>" + 
"    (206)555-1213" + 
"  </gd:phoneNumber>" + 
"  <gd:im address='liz@gmail.com'" + 
"    protocol='http://schemas.google.com/g/2005#GOOGLE_TALK'" + 
"    primary='true'" + 
"    rel='http://schemas.google.com/g/2005#home'/>" + 
"  <gd:structuredPostalAddress" + 
"      rel='http://schemas.google.com/g/2005#work'" + 
"      primary='true'>" + 
"    <gd:city>Mountain View</gd:city>" + 
"    <gd:street>1600 Amphitheatre Pkwy</gd:street>" + 
"    <gd:region>CA</gd:region>" + 
"    <gd:postcode>94043</gd:postcode>" + 
"    <gd:country>United States</gd:country>" + 
"    <gd:formattedAddress>" + 
"      1600 Amphitheatre Pkwy Mountain View" + 
"    </gd:formattedAddress>" + 
"  </gd:structuredPostalAddress>" + 
  "</atom:entry>",{headers:{"Content-Type":"application/atom+xml; charset=UTF-8;"}} )
```

```javascript

```

```javascript
assert.equal( contactCreateResponse.status, 201 )
contactUrl = extractUrl(contactCreateResponse.body)
contactId = extractId(contactCreateResponse.body)
```

To retrieve a single contact, send an authorized GET request to the contact's selfLink URL.

```javascript
contactResponse = client.contacts.userEmail("default").full.contactId( contactId ).get()
```

```javascript
assert.equal( contactResponse.status, 200 )
```

To update a contact, first retrieve the contact entry, modify the data and send an authorized PUT reques
to the contact's edit URL with the modified contact entry in the body.
**Note**: To ensure forward compatibility, be sure that when you PUT an updated entry you preserve all the XML that was present
when you retrieved the entry from the server. Otherwise the ignored elements will be deleted

```javascript
contactUpdateResponse = client.contacts.userEmail("default").full.contactId( contactId ).put(
"<?xml version='1.0' encoding='UTF-8'?>" +
"<atom:entry xmlns:atom='http://www.w3.org/2005/Atom'" +
"    xmlns:gd='http://schemas.google.com/g/2005'>" +
"  <id>" + contactUrl + "</id>" +
"  <updated>2008-12-10T04:45:03.331Z</updated>" +
"  <app:edited xmlns:app='http://www.w3.org/2007/app'>2008-12-10T04:45:03.331Z</app:edited>" +
"  <category scheme='http://schemas.google.com/g/2005#kind'" +
"    term='http://schemas.google.com/contact/2008#contact'/>" +
"  <title>Elizabeth Bennet</title>" +
"  <gd:name>" +
"     <gd:givenName>Elizabeth</gd:givenName>" +
"     <gd:familyName>Bennet</gd:familyName>" +
"     <gd:fullName>Elizabeth Bennet</gd:fullName>" +
"  </gd:name>" +
"  <link rel='http://schemas.google.com/contacts/2008/rel#photo' type='image/*'" +
"    href='" + contactUrl + "'/>" +
"  <link rel='self' type='application/atom+xml'" +
"    href='" + contactUrl + "'/>" +
"  <link rel='edit' type='application/atom+xml'" +
"    href='" + contactUrl + "'/>" +
"  <atom:content type='text'>Notes</atom:content>" +
"  <gd:email rel='http://schemas.google.com/g/2005#work'" +
"    primary='true'" +
"    address='liz@gmail.com' displayName='E. Bennet'/>" +
"  <gd:email rel='http://schemas.google.com/g/2005#home'" +
"    address='liz@example.org'/>" +
"  <gd:phoneNumber rel='http://schemas.google.com/g/2005#work'" +
"    primary='true'>" +
"    (206)555-1212" +
"  </gd:phoneNumber>" +
"  <gd:phoneNumber rel='http://schemas.google.com/g/2005#home'>" +
"    (206)555-1213" +
"  </gd:phoneNumber>" +
"  <gd:im address='liz@gmail.com'" +
"    protocol='http://schemas.google.com/g/2005#GOOGLE_TALK'" +
"    primary='true'" +
"    rel='http://schemas.google.com/g/2005#home'/>" +
"  <gd:structuredPostalAddress" +
"      rel='http://schemas.google.com/g/2005#work'" +
"      primary='true'>" +
"    <gd:city>Mountain View</gd:city>" +
"    <gd:street>1600 Amphitheatre Pkwy</gd:street>" +
"    <gd:region>CA</gd:region>" +
"    <gd:postcode>94043</gd:postcode>" +
"    <gd:country>United States</gd:country>" +
"    <gd:formattedAddress>" +
"      1600 Amphitheatre Pkwy Mountain View" +
"    </gd:formattedAddress>" +
"  </gd:structuredPostalAddress>" +
"</atom:entry>",
  {headers:{"If-Match":"*","Content-Type":"application/atom+xml"}} )
```

```javascript
assert.equal( contactUpdateResponse.status, 200 )
```

To send a batch request for operations on contacts, send an authorized POST request to the contacts batch feed URL with the
batch feed data in the body.

```javascript
batchContactResponse = client.contacts.userEmail("default").full.batch.post(
"<?xml version='1.0' encoding='UTF-8'?>" +
"<feed xmlns='http://www.w3.org/2005/Atom'" + 
"      xmlns:gContact='http://schemas.google.com/contact/2008'" + 
"      xmlns:gd='http://schemas.google.com/g/2005'" + 
"      xmlns:batch='http://schemas.google.com/gdata/batch'>" + 
"  <entry>" + 
"    <batch:id>retrieve</batch:id>" + 
"    <batch:operation type='query'/>" + 
"    <id>" + contactUrl + "</id>" + 
"  </entry>" + 
"  <entry>" + 
"    <batch:id>create</batch:id>" + 
"    <batch:operation type='insert'/>" + 
"    <category scheme='http://schemas.google.com/g/2005#kind' term='http://schemas.google.com/g/2008#contact'/>" + 
"    <gd:name>" + 
"      <gd:fullName>Elizabeth Bennet</gd:fullName>" + 
"      <gd:givenName>Elizabeth</gd:givenName>" + 
"      <gd:familyName>Bennet</gd:familyName>" + 
"    </gd:name>" + 
"    <gd:email rel='http://schemas.google.com/g/2005#home' address='liz@gmail.com' primary='true'/>" + 
"  </entry>" +
"</feed>")
```

```javascript
assert.equal( batchContactResponse.status, 200 )
```

To retrieve a contact's photo, send an authorized GET request to the contact's photo link URL.


```javascript
photosMediaResponse = client.photos.media.userEmail("default").contactId( contactId ).get()
```

```javascript
assert.equal( photosMediaResponse.status, 404 )//new contact do not have any photo
```

To add or update a photo for a contact, send an authorized PUT request to the contact's photo
URL with the photo data bytes in the body.

```javascript
//Temporty commented because of https://github.com/mulesoft/api-notebook/issues/349
//photosMediaUpdateResponse = client.photos.media.userEmail("default").contactId( contactId ).put(z,{headers:{"If-Match":"*"}})
```

```javascript
//assert.equal( photosMediaUpdateResponse.status, 200 )
```

To retrieve the user's contact groups, send an authorized GET request to the contact groups feed URL.

```javascript
groupsResponse = client.groups.userEmail("default").full.get()
```

```javascript
assert.equal( groupsResponse.status, 200 )
```

To create a new contact group, send an authorized POST request to the contact groups feed URL with the contact group entry data in the body.

```javascript
groupCreateResponse = client.groups.userEmail("default").full.post(
"<atom:entry xmlns:gd='http://schemas.google.com/g/2005' xmlns:atom='http://www.w3.org/2005/Atom'>" +
"  <atom:category scheme='http://schemas.google.com/g/2005#kind'" +
"    term='http://schemas.google.com/contact/2008#group'/>" +
"  <atom:title type='text'>Salsa group</atom:title>" +
"  <gd:extendedProperty name='more info about the group'>" +
"    <info>Nice people.</info>" +
"  </gd:extendedProperty>" +
"</atom:entry>" ,{headers:{"Content-Type":"application/atom+xml"}})
```

```javascript
assert.equal( groupCreateResponse.status, 201 )
groupUrl = extractUrl(groupCreateResponse.body)
groupId = extractId(groupCreateResponse.body)

```

We need to wait a bit here

```javascript
ms=20000;
ms += new Date().getTime();
while (new Date() < ms){alert("Please wait for 20 seconds to allow google indexes become consistent")}
```

To retrieve a single group, send an authorized HTTP GET request to the contact group's edit URL.


```javascript
groupResponse = client.groups.userEmail("default").full.groupId( groupId ).get()
```

```javascript
assert.equal( groupResponse.status, 200 )
```

To update a contact group, first retrieve the group entry, modify the data and send an authorized PUT request to the contac
group's edit URL with the updated contact group's entry in the body.

```javascript
groupUpdateResponse = client.groups.userEmail("default").full.groupId( groupId ).put(
"<?xml version='1.0' encoding='UTF-8'?>" +
"<atom:entry xmlns:atom='http://www.w3.org/2005/Atom'" +
"    xmlns:gd='http://schemas.google.com/g/2005' gd:etag='ETAG'>" +
"  <id>" + groupUrl + "</id>" +
"  <updated>2008-12-10T04:44:37.324Z</updated>" +
"  <category scheme='http://schemas.google.com/g/2005#kind' term='http://schemas.google.com/contact/2008#group'/>" +
"  <atom:title type='text'>Salsa group</atom:title>" +
"  <gd:extendedProperty name='more info about the group'>" +
"    <info>Nice people.</info>" +
"  </gd:extendedProperty>" +
"  <link rel='self' type='application/atom+xml' href='https://www.google.com/m8/feeds/groups/userEmail/full/groupID'/>" +
"  <link rel='edit' type='application/atom+xml' href='https://www.google.com/m8/feeds/groups/userEmail/full/groupID'/>" +
"</atom:entry>",
{headers:{"If-Match":"*","Content-Type":"application/atom+xml"}} )
```

```javascript
assert.equal( groupUpdateResponse.status, 200 )
```

To send a batch request for operations on contact groups, send an authorized POST request to the contact groups batch feed URL
with the batch feed data in the body

```javascript
batchGroupResponse = client.groups.userEmail("default").full.batch.post(
"<?xml version='1.0' encoding='UTF-8'?>" +
"<feed xmlns='http://www.w3.org/2005/Atom'" +
"      xmlns:gContact='http://schemas.google.com/contact/2008'" +
"      xmlns:gd='http://schemas.google.com/g/2005'" +
"      xmlns:batch='http://schemas.google.com/gdata/batch'" +
"      xmlns:atom='http://www.w3.org/2005/Atom'>" +
"  <entry>" +
"    <batch:id>retrieve</batch:id>" +
"    <batch:operation type='query'/>" +
"    <id>" + groupUrl + "</id>" +
"  </entry>" +
"  <entry>" +
"    <batch:id>create</batch:id>" +
"    <batch:operation type='insert'/>" +
"    <atom:category scheme='http://schemas.google.com/g/2005#kind'" +
"      term='http://schemas.google.com/contact/2008#group'/>" +
"    <atom:title type='text'>Another Salsa group</atom:title>" +
"    <gd:extendedProperty name='more info about the group'>" +
"      <info>Nice people.</info>" +
"    </gd:extendedProperty>" +
"  </entry>" +
"</feed>")
```

```javascript
assert.equal( batchGroupResponse.status, 200 )
```

To delete a contact's photo, send an authorized DELETE request to the contact's photo URL.

```javascript
//Temporary commented because of https://github.com/mulesoft/api-notebook/issues/349
//photosMediaDeleteResponse = client.photos.media.userEmail("default").contactId( contactId ).delete({},{headers:{"If-Match":"*"}})
```

```javascript
//assert.equal( photosMediaDeleteResponse.status, 200 )
```

To delete a contact group, send an authorized DELETE request to the contact group's edit URL.

```javascript
groupDeleteResponse = client.groups.userEmail("default").full.groupId( groupId ).delete(null, {headers:{"If-Match":"*"}})
```

```javascript
assert.equal( groupDeleteResponse.status, 200 )
```

To delete a contact, send an authorized DELETE request to the contact's edit URL.

```javascript
contactDeleteResponse = client.contacts.userEmail("default").full.contactId( contactId ).delete(null,{headers:{"If-Match":"*"}})
```

```javascript
assert.equal( contactDeleteResponse.status, 200 )
```