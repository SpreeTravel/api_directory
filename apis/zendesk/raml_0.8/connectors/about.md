Configuring connector

- In order to run this connector you will need a Zendesk account. At some point of the execution, you will be prompted to authorize the OAuth client to your account.
- It is also recommended to create a sandbox at https://{Your Zendesk domain}/agent/admin/sandbox.
- If you choose using sandbox, you must register an OAuth client at https://{Your Zendesk sandbox domain}/agent/admin/api. If you want to use production account, the application must be registered at https://{Your Zendesk domain}/agent/admin/api. 
- Do not forget to set redirect URI for the application. In our case Mule application is launched locally and the redirect URI belongs to the localhost domain: http://localhost:9000/codeZendesk
- Specify Client Id, Client Secret and redirect URI of your application in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.
- You must specify your Zendesk domain or Zendesk sandbox domain in the 'host' attribute of request-config depending on whether you want to use production account or sandbox.
- You must also substitute the selected domain into authorization and token URLs which can be found in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.


Running connector

- Make a call on your Local Authorization URL which is http://localhost:9000/authorizeZendesk. You will be prompted to authorize your application.
- Make a call on the http://localhost:8081/zendesk URL.