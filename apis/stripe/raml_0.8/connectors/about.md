Configuring connector

- In order to run this connector you will need a stripe account and a Stripe application. To obtain these you should go to [https://manage.stripe.com/account/applications/settings](https://manage.stripe.com/account/applications/settings). You will be proposed to login or create an account and then moved to the applications management page.
- Do not forget to set redirect URI for the application. In our case Mule application is launched locally and the redirect URI belongs to the localhost domain: http://localhost:9000/codeStripe.
- Specify Client Id, Client Secret and redirect URI of your application in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.
- You must have the prueba.TokenReader class on your classpath in order for the OAuth access token to be read correctly. If you want to use another class which provides same functionality, do not forget to specify correct class names and method calls in the XML.
- Note that each time you authenticate your application, Stripe asks to fill required fields in your account, if they are not already filled. But in the developer mode you are allowed to skip this procedure by clicking the Skip this account form link in the top right corner of the page. In order to enter the developer mode you should use your development client_id and test Secret Key.


Running connector

- Make a call on your Local Authorization URL which is http://localhost:9000/authorizeStripe. You will be prompted to authorize your application.
- Make a call on the http://localhost:8081/stripe URL.