---
site: https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7589/versions/7715/portal/pages/6303/edit
apiNotebookVersion: 1.1.66
title: Bundles
---

```javascript

useDefaultApp = true; // Please, set this flag to false if you want to use your own application

```

```javascript

load('https://github.com/chaijs/chai/releases/download/1.9.0/chai.js')

```



See http://chaijs.com/guide/styles/ for assertion styles



```javascript

assert = chai.assert

```



In order to test collaboration related API functionality we need some bitly user data. Dummy data is enough to execute 

```

GET /bundle/collaborator_add

GET /bundle/pending_collaborator_remove

```

methods.



```javascript

DUMMY_COLLABORATOR_EMAIL = "mail@somehost.com"

DUMMY_COLLABORATOR_USERNAME = "notebookuser"

```



Executing the 



```

GET /bundle/collaborator_remove

```



method would require real data, which you should enter in the code block below.



```javascript

REAL_COLLABORATOR_USERNAME = prompt("Please, enter username of a verified Bitly user which would accept your invitation to a bundle.")

REAL_COLLABORATOR_EMAIL = prompt("Please, enter Bitly registration email of the same user.")

```

```javascript

// Read about the Bitly API at https://anypoint.mulesoft.com/apiplatform/popular/admin/#/dashboard/apis/7589/versions/7715/contracts

API.createClient('client', '/apiplatform/repository/public/organizations/30/apis/7589/versions/7715/definition');

```

```javascript

if (useDefaultApp) {

  API.authenticate(client);

} else {

  cId = prompt("Please, enter the clientId of your own application");

  cSecret = prompt("Please, enter the clientSecret of your own application");

  

  API.authenticate(client, 'oauth_2_0', {

    clientId: cId,

    clientSecret: cSecret

  });  

}

```



We need a short link created by the authenticated user for our tests. You may use any long url in the code block below.



```javascript

shortenResponse = client.shorten.get( { longUrl : "http://www.apihub.com" } )

```

```javascript

USER_SHORT_LINK = shortenResponse.body.data.url

```



Returns a list of public bundles created by a user.



```javascript

bundlesByUserResponse = client.bundle.bundles_by_user.get({

  user: "lmason"

})

```

```javascript

assert.equal( bundlesByUserResponse.status, 200 )

assert.equal( bundlesByUserResponse.body.status_code, 200 ) 

```



Returns all bundles the authenticated user has access to (public + private + collaborator)



```javascript

bundleHistoryResponse = client.user.bundle_history.get()

```

```javascript

assert.equal( bundleHistoryResponse.status, 200 )

assert.equal( bundleHistoryResponse.body.status_code, 200 ) 

```



Create a new bundle for the authenticated user.



```javascript

bundleCreateResponse = client.bundle.create.get({

  title: "Notebook Test Bundle",

  description: "This bundle was created by API Notebook."

})

```

```javascript

assert.equal( bundleCreateResponse.status, 200 )

assert.equal( bundleCreateResponse.body.status_code, 200 ) 

```



We will use the newly created bundle in the following tests. In the very end it will be archived and since then will become unaccessible via REST.

In one of the tests a clone will be created for this bundle. It will also be archived eventually.



```javascript

BUNDLE_LINK = bundleCreateResponse.body.data.bundle.bundle_link

```





The `GET /bundle/collaborator_remove` method works only for actual collaborators i.e. those users who were not only sent an invitation, but have also accepted it. Thus, we need to add a real user as a collaborator.



```javascript

realCollaboratorAddResponse = client.bundle.collaborator_add.get({

  bundle_link: BUNDLE_LINK,

  collaborator: REAL_COLLABORATOR_EMAIL

})

```

```javascript

window.alert("At this point the invited user must check his email and accept your invitation to a bundle.")

```



Adds a link to a bitly bundle. Links are automatically added to the top (position 0) of a bundle.



```javascript

linkAddResponse = client.bundle.link_add.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK

}) 

```

```javascript

assert.equal( linkAddResponse.status, 200 )

assert.equal( linkAddResponse.body.status_code, 200 )

```



Edit a bundle for the authenticated user



```javascript

editResponse = client.bundle.edit.get( { bundle_link: BUNDLE_LINK } )

```

```javascript

assert.equal( editResponse.status, 200 )

assert.equal( editResponse.body.status_code, 200 ) 

```



Change the position of a link in a bitly bundle



```javascript

linkReorderResponse = client.bundle.link_reorder.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK,

  display_order: -1

})

```

```javascript

assert.equal( linkReorderResponse.status, 200 )

assert.equal( linkReorderResponse.body.status_code, 200 ) 

```



Get the number of views for a bundle



```javascript

viewCountResponse = client.bundle.view_count.get( { bundle_link: BUNDLE_LINK } )

```

```javascript

assert.equal( viewCountResponse.status, 200 )

assert.equal( viewCountResponse.body.status_code, 200 ) 

```



Add a comment to bundle item



```javascript

linkCommentAddResponse = client.bundle.link_comment_add.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK,

  comment: "comment value"

}) 

```

```javascript

assert.equal( linkCommentAddResponse.status, 200 )

assert.equal( linkCommentAddResponse.body.status_code, 200 )

```



Let's determine ID of the newly created comment. 



```javascript

COMMENT_ID = 0

links = linkCommentAddResponse.body.data.bundle.links

for(var i in links){

  var link = links[i]

  if(!(link.link == USER_SHORT_LINK))

    continue;

  

  for(var j in link.comments){

    

    var comment = link.comments[j]

    if(!(link.link == USER_SHORT_LINK))

      continue;

    

    COMMENT_ID = comment.id

  }

}

```



Edit a comment.



```javascript

linkCommentEditResponse = client.bundle.link_comment_edit.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK,

  comment_id: COMMENT_ID,

  comment: "new comment value"

})

```

```javascript

assert.equal( linkCommentEditResponse.status, 200 )

assert.equal( linkCommentEditResponse.body.status_code, 200 ) 

```



Re-order the links in a bundle.



```javascript

reorderResponse = client.bundle.reorder.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK

})

```

```javascript

assert.equal( reorderResponse.status, 200 )

assert.equal( reorderResponse.body.status_code, 200 ) 

```



Clone a bundle for the authenticated user



```javascript

cloneResponse = client.bundle.clone.get( { bundle_link: BUNDLE_LINK } )

```

```javascript

assert.equal( cloneResponse.status, 200 )

assert.equal( cloneResponse.body.status_code, 200 ) 

```



Returns information about a bundle



```javascript

contentsResponse = client.bundle.contents.get( { bundle_link: BUNDLE_LINK } )

```

```javascript

assert.equal( contentsResponse.status, 200 )

assert.equal( contentsResponse.body.status_code, 200 ) 

```



Remove a comment from a bundle item. Only the original commenter and the bundles owner may perform this action.



```javascript

linkCommentRemoveResponse = client.bundle.link_comment_remove.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK,

  comment_id: COMMENT_ID

})

```

```javascript

assert.equal( linkCommentRemoveResponse.status, 200 )

assert.equal( linkCommentRemoveResponse.body.status_code, 200 ) 

```



Edit the title for a link



```javascript

link_editResponse = client.bundle.link_edit.get({

  bundle_link: BUNDLE_LINK,

  link: USER_SHORT_LINK,

  edit: "title",

  title: "new link title"

})

```

```javascript

assert.equal( link_editResponse.status, 200 )

assert.equal( link_editResponse.body.status_code, 200 ) 

```



Add a collaborator to a bundle.

Executing this method does not immediately make the bitly user a collaborator. Instead the user obtains status of _pending_collaborator_ i.e. one that has been sent an email with invitation and is supposed to accept it.



```javascript

collaboratorAddResponse = client.bundle.collaborator_add.get({

  bundle_link: BUNDLE_LINK,

  collaborator: DUMMY_COLLABORATOR_EMAIL

})

```

```javascript

assert.equal( collaboratorAddResponse.status, 200 )

assert.equal( collaboratorAddResponse.body.status_code, 200 ) 

```



Removes a pending/invited collaborator from a bundle



```javascript

pendingCollaborator_removeResponse = client.bundle.pending_collaborator_remove.get({

  bundle_link: BUNDLE_LINK,

  collaborator: DUMMY_COLLABORATOR_EMAIL

})

```

```javascript

assert.equal( pendingCollaborator_removeResponse.status, 200 )

assert.equal( pendingCollaborator_removeResponse.body.status_code, 200 ) 

```



Remove a collaborator from a bundle.



Before executing the code block below you should get ensured that the real user has accepted the invitation.



```javascript

collaboratorRemoveResponse = client.bundle.collaborator_remove.get({

  bundle_link: BUNDLE_LINK,

  collaborator: REAL_COLLABORATOR_USERNAME

})

```

```javascript

assert.equal( collaboratorRemoveResponse.status, 200 )

assert.equal( collaboratorRemoveResponse.body.status_code, 200 )

```



Archive a bundle for the authenticated user. Only a bundle's owner is

allowed to archive a bundle.



**ATTENTION** the bundle gets unaccessible after being moved to archive.



```javascript

archiveResponse = client.bundle.archive.get( { bundle_link: BUNDLE_LINK } )

```

```javascript

assert.equal( archiveResponse.status, 200 )

assert.equal( archiveResponse.body.status_code, 200 ) 

```



Let's also archive the previously created bundle clone.



```javascript

client.bundle.archive.get( { bundle_link: cloneResponse.body.data.bundle.bundle_link } )

```