---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/organizations/52560d3f-c37a-409d-9887-79e0a9a9ecff/dashboard/apis/24833/versions/26369/portal/pages/40052/edit
apiNotebookVersion: 1.1.69
title: API Notebook
---

```javascript
load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')
```

See http://chaijs.com/guide/styles/ for assertion styles

```javascript
assert = chai.assert 
```

```javascript
CLIENT_ID = prompt("Please, enter Client ID of your 23andMe application.")
CLIENT_SECRET = prompt("Please, enter Client Secret of your 23andMe application.")
```

```javascript
genotyped = confirm("Does you explore your DNA with the \"23andME\" service?")
```

```javascript
API.createClient('client', '#REF_TAG_DEFENITION');
```

```javascript
API.authenticate(client,"oauth_2_0",{
  clientId : CLIENT_ID,
  clientSecret : CLIENT_SECRET
})
```

Gets the user id, and a list of profiles (an account can have multiple genotyped people) with ids, whether or not they're genotyped.

The optional email parameter can be used to request the email for this account. Requires the "email" scope.

The optional services parameter can be used to request the endpoint to return what services are available to the profiles.

A service model object is made up of a unique service id, a service label, and a user-readable description of the service. All genotyped profiles should have "pgs_ancestry". Customers with access to health data will have "pgs_health". Demo profiles will have no services.

This endpoint is great for using an app anonymously because there is no personally identifying information.

```javascript
userResponse = client.demo.user.get({query:{"services":true}})
```

```javascript
assert.equal( userResponse.status, 200 )
ID_DEMO_PROFILE = userResponse.body.profiles[0].id
```

Retrive current user info.

```javascript
currentUser = client.user.get({query:{"services":true}})
```

```javascript
ID_PROFILE = currentUser.body.profiles[0].id
```

For the user and user's profile, gets first and last names. If your user wants to remain anonymous, you shouldn't request this scope. You can optionally filter by profile_id to get the names for just that profile

```javascript
namesProfileResponse = client.demo.names.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( namesProfileResponse.status, 200 )
```

Gets the user's profile picture for a few size

```javascript
profilePictureResponse = client.profile_picture.profile_id(ID_PROFILE).get()
```

```javascript
assert.equal( profilePictureResponse.status, 200 )
```

Uploads a picture, < 5MB, and crops it.  For the POST, set Content-Type: multipart/form-data and upload the image with parameter <image>

```javascript
base64Content="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4KPCFET0NUWVBFIHN2ZyBQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj4KPHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4IgoJIHZpZXdCb3g9IjAgMCAxNCAxMy45MzciIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDE0IDEzLjkzNyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI%2BCgk8cGF0aCBmaWxsPSIjRkZGRkZGIiBkPSJNNi45OTksMTMuOTM3QzMuMTQsMTMuOTM3LDAsMTAuODExLDAsNi45NjlDMCwzLjEyNiwzLjE0LDAsNi45OTksMEMxMC44NiwwLDE0LDMuMTI2LDE0LDYuOTY5CgkJQzE0LDEwLjgxMSwxMC44NiwxMy45MzcsNi45OTksMTMuOTM3eiBNNi45OTksMC40OTdjLTMuNTg1LDAtNi41MDIsMi45MDMtNi41MDIsNi40NzJjMCwzLjU2OCwyLjkxNyw2LjQ3MSw2LjUwMiw2LjQ3MQoJCWMzLjU4NiwwLDYuNTAzLTIuOTAzLDYuNTAzLTYuNDcxQzEzLjUwMywzLjQsMTAuNTg2LDAuNDk3LDYuOTk5LDAuNDk3eiIvPgoJPHBhdGggZmlsbD0iI0ZGRkZGRiIgZD0iTTUuMTU3LDEwLjA3MkM0LjExLDkuNTc3LDMuMzgsOC40NjksMy4zOCw3LjE3OWMwLTAuNjc5LDAuMjAzLTEuMzA5LDAuNTQ4LTEuODI0bDIuMjg2LDMuNDA2aDEuMzgxCgkJbDIuMjg2LTMuNDA2YzAuMzQ1LDAuNTE2LDAuNTQ4LDEuMTQ1LDAuNTQ4LDEuODI0YzAsMS4yMDItMC42MzQsMi4yNDgtMS41NjgsMi43ODRMOS4zLDExLjY0OQoJCWMxLjY3Ni0wLjg2NCwyLjgyMi0yLjYxMSwyLjgyMi00LjYyNmMwLTEuOTAzLTEuMDIzLTMuNTY3LTIuNTQ4LTQuNDc0TDYuOTIsNi42NDZMNC4yOTEsMi41MzRDMi43NTEsMy40MzgsMS43MTcsNS4xMSwxLjcxNyw3LjAyMwoJCWMwLDIuMDg4LDEuMjMsMy44ODgsMy4wMDUsNC43MTZMNS4xNTcsMTAuMDcyeiIvPgo8L3N2Zz4K"
```

Here we form payload for app creation POST request

```javascript
byteCharacters = base64Content
byteNumbers = new Array(byteCharacters.length);
for (var i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
}
byteArray = new Uint8Array(byteNumbers);
byteArray.length
formData = new FormData()
formData.append("multipart/form-data", new Blob([byteArray]),"44.jpg")
```

```javascript
profilePictureCreateResponse = client.profile_picture.profile_id(ID_PROFILE).post(formData, {
  headers:{
    "Content-Type": "multipart/form-data"
  }
})
```

```javascript
assert.equal( profilePictureCreateResponse.status, 200 )
```

Users can publish and share certain features publicly, for example on social networks, with a special link. Anyone with the link, if active, can view that person's results for the following feature_ids:

music: DNA Music 
neanderthal: Neanderthal Ancestry 
maternal: Maternal Haplogroup 
composition: Ancestry Composition 
paternal: Paternal Haplogroup 

```javascript
publishProfileFeatureResponse = client.publish.profile_id(ID_PROFILE).feature_id("maternal").get()
```

```javascript
assert.equal( publishProfileFeatureResponse.status, 200 )
```

Users can publish and share certain features publicly, for example on social networks, with a special link. Anyone with the link, if active, can view that person's results for the following feature_ids:

music: DNA Music 
neanderthal: Neanderthal Ancestry 
maternal: Maternal Haplogroup 
composition: Ancestry Composition 
paternal: Paternal Haplogroup 

```javascript
if(genotyped){
	publishProfileFeatureCreateResponse = client.publish.profile_id(ID_PROFILE).feature_id("neanderthal").post()
}
```

```javascript
if(genotyped){
	assert.equal( publishProfileFeatureCreateResponse.status, 200 )
	ID_LINK = publishProfileFeatureCreateResponse.body.link_id
}
```

Users can publish and share certain features publicly, for example on social networks, with a special link. Anyone with the link, if active, can view that person's results for the following feature_ids:

music: DNA Music 
neanderthal: Neanderthal Ancestry 
maternal: Maternal Haplogroup 
composition: Ancestry Composition 
paternal: Paternal Haplogroup 

```javascript
if(genotyped){
	linkResponse = client.publish.profile_id(ID_PROFILE).feature_id("neanderthal").link_id(ID_LINK).get()
}
```

```javascript
if(genotyped){
	assert.equal( linkResponse.status, 200 )
}
```

Users can publish and share certain features publicly, for example on social networks, with a special link. This method set status to published or unpublished

```javascript
if(genotyped){
	linkUpdateResponse = client.publish.profile_id(ID_PROFILE).feature_id("neanderthal").link_id(ID_LINK).put()
}
```

```javascript
if(genotyped){
	assert.equal( linkUpdateResponse.status, 200 )
}
```

Users can send introductions to other users who we've identified as genetic matches per the /relatives endpoint. The match_id must be a match of the profile_id (it must show up in /relatives endpoint.)

Parameters are message_text and visibility.

message_text may be customized, and defaults to:
Hi, Through our shared DNA, 23andMe has identified us as relatives. Our predicted relationship is <your_relationship>. Would you like to explore our relationship?

Values for visibility are the same as those of the PATCH method. If no parameter is supplied, visibility defaults to anonymous.

Relatives on 23andMe, for the user's profile. shared_segments is the total number of shared IBD segments; similarity is the actual proportion (1.00 being you, or an identical twin). maternal/paternal_side are True if this match is a relative from your mom or dad's side. range is defined if we couldn't pinpoint the relatedness of the match.

match_id is a unique identifier for matches for a given profile, but not across profiles. For example, if profile A has a relative C1, and profile B has relative C2, then any C1.match_id = C2.match_id is coincidental and does not mean C1 and C2 represent the same person. We cannot expose globally unique match_ids. match_id is null if the match is you.

Since you could have thousands of matches, limit defaults to 10, and offset to 0. You can override the limit (or not limit it at all with limit = 0, but be careful, the user may have thousands of relatives). count gives the total number of matches after filtering. Results are sorted by updated, descending. You can also get results that have been updated or added since a timestamp.

You can also filter matches by their intro_status and share_status. Note that you will have to URL-encode these parameters (i.e., Introduction%20Sent). The possible values are:

intro_status
Introduction Received
Introduction Accepted
Introduction Sent
Introduction Cancelled
Introduction Declined
share_status
Owned Profile
Sharing Genomes
Public Match

You can provide an optional match_id parameter to limit the results returned to that of an individual match. In this case, match_id overrides any other conflicting parameters. For instance, if you provide both a match_id and a since parameter, the match's information will be returned regardless of whether the match was updated or added since the timestamp specified by the since parameter. The count returned will be 1 when the match_id parameter is specified. Example usage is as follows: https://api.23andme.com/1/relatives/c44.../?match_id=48f...

```javascript
relativesProfileResponse = client.demo.relatives.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( relativesProfileResponse.status, 200 )
ID_MATCH = relativesProfileResponse.body.relatives[0].match_id
```

Users can see the status of introductions between themselves and other relatives as identified by /relatives. If an introduction exists between the two users, the endpoint returns a JSON representation of the introduction and can_send. If can_send is true, the user can send another introduction via a POST /introduction.

Possible values for status are those settable by the PATCH method as well as sent and received. An introduction is sent before being accepted, rejected, read, or cancelled. An introduction is sent and received at exactly the same time depending on whether the person querying is the sender or the recipient.

Statuses listed above are relative to you. For instance, if user B sends an introduction to you, cancels it, and then you query this endpoint, you will find that no introduction exists between you and user B. If user B then queries the endpoint, she will find an introduction between herself and you with the status cancelled.

You may send an introduction if there is no outstanding introduction between you and the user, if you've sent and cancelled an introduction, or if an invitation between you and the user has been rejected. A minimum time interval between introduction resends is also imposed. These cases are abstracted away for you -- just check if can_send is true.

If there is no outstanding introduction between you and the user represented by match_id, then you will receive an empty dict.

```javascript
if(genotyped){
	introductionProfileMatchCreateResponse = client.introduction.profile_id(ID_PROFILE).match_id(ID_MATCH).get()
}
```

```javascript
if(genotyped){
	assert.equal( introductionProfileMatchCreateResponse.status, 200 )
}
```

Users can see the status of introductions between themselves and other relatives as identified by /relatives. If an introduction exists between the two users, the endpoint returns a JSON representation of the introduction and can_send. If can_send is true, the user can send another introduction via a POST /introduction.

Possible values for status are those settable by the PATCH method as well as sent and received. An introduction is sent before being accepted, rejected, read, or cancelled. An introduction is sent and received at exactly the same time depending on whether the person querying is the sender or the recipient.

Statuses listed above are relative to you. For instance, if user B sends an introduction to you, cancels it, and then you query this endpoint, you will find that no introduction exists between you and user B. If user B then queries the endpoint, she will find an introduction between herself and you with the status cancelled.

You may send an introduction if there is no outstanding introduction between you and the user, if you've sent and cancelled an introduction, or if an invitation between you and the user has been rejected. A minimum time interval between introduction resends is also imposed. These cases are abstracted away for you -- just check if can_send is true.

If there is no outstanding introduction between you and the user represented by match_id, then you will receive an empty dict.

```javascript
if(genotyped){
	introductionProfileMatchPatchResponse = client.introduction.profile_id(ID_PROFILE).match_id(ID_MATCH).patch()
}
```

```javascript
if(genotyped){
	assert.equal( introductionProfileMatchPatchResponse.status, 200 )
}
```

For the user's profile, returns the base-pairs, like AA, for the given locations. The value can have Ds or Is for deletions and insertions (for example, DD or DI). It can be __ if the customer is not on a chip that calls that location, or hasn't yet unlocked their call since it corresponds to a sensitive report. It can be -- if the customer is on a chip that calls that location, but we could not determine it. To keep consistency with the /genomes endoint, which always returns two base pairs, hemizygous calls (such as on X-linked genes in males) will also return two base pairs. 

The scope of your token must include each location you request (i.e., getting the below data requires a scope of at least rs3094315 i3000001). This list of SNPs (31MB) shows which SNPs our customers are genotyped for. 

The unfiltered parameter can be used for completely sex-unfiltered data. 

The format parameter can be used to alter the JSON output format of this endpoint. 

Since this is a GET endpoint, you may hit the browser-imposed URL limit with a lot of SNPs. We recommend splitting your requests into 100-SNP chunks.

```javascript
genotypesProfileResponse = client.demo.genotypes.profile_id(ID_DEMO_PROFILE).get({"locations":"rs3094315 i3000001"})
```

```javascript
assert.equal( genotypesProfileResponse.status, 200 )
```

For the user's profile, returns the requested phenotype.
For the /phenotypes read and write endpoints, your phenotype_id can be any of the following:

family_tree_url: family tree url
date_of_birth: date of birth (YYYY-MM-DD)
weight_g: weight in grams
height_mm: height in millimeters
sex: sex

```javascript
phenotypesProfilePhenotypeResponse = client.phenotypes.profile_id(ID_PROFILE).phenotype_id("sex").get()
```

```javascript
assert.equal( phenotypesProfilePhenotypeResponse.status, 200 )
```

Returns the entire profile's genome as a packed string of base pairs "AACTGA...". This ~2MB request returns over a million SNP locations, so you must specify profile_id. See the /genotypes endpoint for possible values. Each SNP is represented with two base pairs, and to know which SNP corresponds to which index, see this key. 

The unfiltered parameter can be used for completely sex-unfiltered data. 

When our genotyping chip is upgraded, the packed string and corresponding key will grow, but the changes will be backwards-compatible additions.

```javascript
genomesProfileResponse = client.demo.genomes.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( genomesProfileResponse.status, 200 )
```

For the user's profile, gets maternal and paternal haplogroups, as well as terminal SNPs. Maternal terminal SNPs include the rsid and rCRS reference position, while the paternal ones include the rsid and ISOGG SNP.

Note: if the profile belongs to a female, the paternal (y) haplogroup and terminal SNP information will be null.

```javascript
haplogroupsProfileResponse = client.demo.haplogroups.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( haplogroupsProfileResponse.status, 200 )
```

Ancestral breakdown for the user's profile. Each population has an absolute proportion of the genome. A population with sub_populations has an unassigned proportion — the ancestry we couldn't classify in it. 

threshold is optional, default 0.51 and range (0.5, 1.0) exclusive. 0.51 means a speculative estimate, 0.75 standard, and 0.90 conservative. A higher threshold would increase the unassigned proportions, a lower would speculate. 

If the user's ancestry hasn't been computed yet, you'll see "ancestry": null.

```javascript
ancestryProfileResponse = client.demo.ancestry.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( ancestryProfileResponse.status, 200 )
```

All Family Members in an account's family tree as entered in the family tree tool. Every profile has a family tree member node created automatically, so even users who have never used the family tree tool will have some data here.

The response is formed to return the account id, and pagination information limit offset and count. Since the account can have thousands of family tree members, limit defaults to 10, and offset to 0. You can override the limit (or not limit it at all with limit = 0, but be careful, the user may have thousands of family tree members). count gives the total number of matches after filtering. The limit and offset values in the response will represent the pagination values used by this request.

Family Members:
A family member in the members array will have a member id field id. Any family member that is associated with a profile will also have non empty profile_id. The sex field can be set to "m" or "f" to represent male and female respectively. The adopted and deceased fields will both contain true or false. The first_name, middle_name, last_name, birth_surname, name_suffix and nickname fields will only be sent if the account has given you the 'names' scope. The partners and parents fields are arrays of member ids. Finally, the events field contains an array of events for the family member as shown in the example below.

```javascript
familyMembersResponse = client.demo.family_members.get()
```

```javascript
assert.equal( familyMembersResponse.status, 200 )
```

Estimated genome-wide proportion of Neanderthal ancestry for the user's profile. Most users have between 0.01 and 0.04 Neanderthal ancestry -- read a full explanation of the science. proportion is -1 for un-genotyped (or as-of-yet uncomputed) profiles

```javascript
neanderthalProfileIdResponse = client.demo.neanderthal.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( neanderthalProfileIdResponse.status, 200 )
```

Updates a relative match. In our DNA Relatives feature, we calculate a predicted relationship based on your overlapping DNA segments. But if you know for sure, you can update the match with a known user_relationship_code (see below). You can also add notes about the match

```javascript
if(genotyped){
	matchIdPatchResponse = client.relatives.profile_id(ID_PROFILE).match_id(ID_MATCH).patch()
}
```

```javascript
if(genotyped){
	assert.equal( matchIdPatchResponse.status, 200 )
}
```

Our analysis for each profile's lifetime risk of these diseases (starred reports). population_risk is the average risk for the population for which the analysis applies, and risk is the profile's risk

```javascript
risksProfileResponse = client.demo.risks.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( risksProfileResponse.status, 200 )
```

Our analysis of whether or not each profile is a carrier for these diseases (starred reports). The person has 0, 1, or 2 mutations, or null if they're not analyzed at any of the markers. Normally, with one mutation, the person is considered a "carrier" and can pass the mutation to children; with two, the person is likely to be affected by the disease.

```javascript
carriersProfileResponse = client.demo.carriers.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( carriersProfileResponse.status, 200 )
```

Our analysis of how each profile responds to these drugs (starred reports). status is reduced, typical, or increased for a person, not_applicable if the drug is not applicable to them (e.g., the oral contraceptives report is for women only), or null if they're not analyzed at any of the markers

```javascript
drugResponsesProfileResponse = client.demo.drug_responses.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( drugResponsesProfileResponse.status, 200 )
```

Our analysis for each profile for these traits (starred reports). trait is a value in possible_traits, or null if the profile's not analyzed at those markers.

```javascript
traitsProfileResponse = client.demo.traits.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( traitsProfileResponse.status, 200 )
```

Our analysis for each profile for genetic risk factors. mutations is positive if the profile contains a mutation which corresponds to the condition denoted by description

```javascript
grfsProfileResponse = client.demo.grfs.profile_id(ID_DEMO_PROFILE).get()
```

```javascript
assert.equal( grfsProfileResponse.status, 200 )
```