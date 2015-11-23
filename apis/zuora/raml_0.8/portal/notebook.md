The API notebooks are all executable! Hit "enter" in any code cell to execute it (and all cells before it that have not executed yet), or scroll to the bottom of the notebook and click "Play notebook". For more information, see [http://apinotebook.com](http://apinotebook.com).

#Considerations

- By default, the notebook uses the Zuora Sandbox environment. You can follow the link in order to get your own credentials for that environment (those credentials will be prompted by the Notebooks at some point of their execution). If you want to run notebooks against Zuora production environment you just need to switch useSandbox flag to false.​
- The "Subscriptions" notebook requires your account to provide at least one [Product Rate Plan](http://knowledgecenter.zuora.com/D_SOAP_API/E_SOAP_API_Objects/ProductRatePlan) with valid EffectiveStartDate and EffectiveEndDate.
