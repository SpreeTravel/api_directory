Configuring connector

- In order to run this connector will need a StackExchange account. Do not use your GMail or any other account to log in to StackExchange.
- You will need to register a stackoverflow account using your StackExchange account.
- You must also register your own Stack application at [https://stackapps.com/apps/oauth/register](https://stackapps.com/apps/oauth/register).
- Do not forget to set redirect URI for the application. In our case Mule application is launched locally and the redirect URI belongs to the localhost domain: http://localhost:9000/codeStackexchange
- Specify Client Id, Client Secret and redirect URI of your application in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.


Running connector

- Make a call on your Local Authorization URL which is http://localhost:9000/authorizeStackexchange. You will be prompted to authorize your application.
- Make a call on the http://localhost:8081/stackexchange URL.