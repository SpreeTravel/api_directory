Configuring connector

- In order to run this connector you will need a Bufferapp account.
- You must also register a Bufferapp application at [https://bufferapp.com/developers/apps/create](https://bufferapp.com/developers/apps/create). 
- Do not forget to set redirect URI for the application. In our case Mule application is launched locally and the redirect URI belongs to the localhost domain: http://localhost:9000/codeBufferapp.
- Specify Client Id, Client Secret and redirect URI of your application in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.
- You must have the prueba.TokenReader class on your classpath in order for the OAuth access token to be read correctly. If you want to use another class which provides same functionality, do not forget to specify correct class names and method calls in the XML.


Running connector

- Make a call on your Local Authorization URL which is http://localhost:9000/authorizeBufferapp. You will be prompted to authorize your application.
- Make a call on the http://localhost:8081/bufferapp URL.