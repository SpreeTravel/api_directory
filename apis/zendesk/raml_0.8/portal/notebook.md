The API notebooks are all executable! Hit "enter" in any code cell to execute it (and all cells before it that have not executed yet), or scroll to the bottom of the notebook and click "Play notebook". For more information, see [http://apinotebook.com](http://apinotebook.com).

#Considerations

- In order to run these notebooks you will need a Zendesk account. At some point of the execution, you will be prompted to authorize the OAuth client to your account.
- You must register an OAuth client at https://{your_domain}.zendesk.com/agent/admin/api. Redirect URI of the application must be set to https://api-notebook.anypoint.mulesoft.com/authenticate/oauth.html. At some point you will be prompted for clientId and clientSecret of your client.
- You must create a sandbox at https://{your_domain}.zendesk.com/agent/admin/sandbox. All but the "End user" and "Merge users" notebooks must be launched in sandbox.
- In order to be capable of setting user passwords you must enable the 'Allow admins to set passwords' setting at https://{your_domain}.zendesk.com/agent/admin/security
- "End users" and "Merge users" notebooks must be launched with production account, not with sandbox.
- The "Twitter" notebook consumes tweets to produce tickets. You must link you Zendesk account with your Twitter account at https://{your_domain}.zendesk.com/agent/admin/twitter/twitter_auth and the notebook will ask for access to this account. Remember that each tweet can be used only once to produce a ticket.
- In order to run Help Center notebooks, you must create help center at https://{your_domain}.zendesk.com/hc.
- In order to run the "Help center: articles, questions, translations" notebook, inside your help center you must create a question and an answer on it. The exact link for creating questions is https://{your_domain}.zendesk.com/hc/communities/public/questions/new. The notebook prompts you to enter title of your question.
- In order to run the "Incremental" notebook you must apply for beta test permission at [https://zendesk.wufoo.com/forms/incremental-apis-beta](https://zendesk.wufoo.com/forms/incremental-apis-beta).
