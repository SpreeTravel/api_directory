The API notebooks are all executable! Hit "enter" in any code cell to execute it (and all cells before it that have not executed yet), or scroll to the bottom of the notebook and click "Play notebook". For more information, see [http://apinotebook.com](http://apinotebook.com).

#Considerations

- In order to run these notebooks you will need a Bitly account. At some point of the excecution, you will be prompted to authorize the client application to access to your account.
- Optional: For some APIs such as Bitly, "Notebooks" has a default application ready to be the consumer. In these cases, the notebooks will use that application. You can change that behavior by shitch the useDefaultBoxApp flag to false and use your own Bitly application. If that's the case, at some point of the run, you will be prompted to enter the clientId and clientSecret of your own application. [Click here](https://bitly.com/a/create_oauth_app) if you need/want to register an application on Bitly.
- The notebook "Users and Links" requires you to register a tracking domain at [https://bitly.com/a/tracking_domain_settings](https://bitly.com/a/tracking_domain_settings).
- The notebook "Bundles" requires email and username of some real Bitly user, who is ready to accept invitation to collaborate on your test bundle. See the notebook itself for details.
