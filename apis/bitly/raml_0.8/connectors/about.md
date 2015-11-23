Configuring connector

- In order to run this connector notebooks you will need a Bitly account.
- You must register your Bitly application at [(https://bitly.com/a/create_oauth_app)]((https://bitly.com/a/create_oauth_app)).
- Do not forget to set redirect URI for the application. In our case Mule application is launched locally and the redirect URI belongs to the localhost domain: http://localhost:9000/codeBitly
- Specify Client Id, Client Secret and redirect URI of your application in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.
- You must have the prueba.Bitly class on your classpath. If you want to use another class which provides same functionality, do not forget to specify correct class names and method calls in the XML.
- As Bitly expects access token to be passed as query parameter (which is not supported by MuleStudio), on your classpath you need a special Java class which stores access token so that it becomes available via expressions. The example of such class is prueba.AccessTokenManager2. If you want to use another class which provides same functionality, do not forget to specify correct class names and method calls in the XML.

Running connector

- Make a call on your Local Authorization URL which is http://localhost:9000/authorizeBitly. You will be prompted to authorize your application.
- Make a call on the http://localhost:8081/bitly URL.