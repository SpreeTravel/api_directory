Configuring PagerDuty connector

- In order to run this connector you will need a PagerDuty account. You must specify your pagerduty domain in the 'host' attribute of request-config.
- Specify username (which is registration email) and password in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.


Running PagerDuty connector

- Make a call on the http://localhost:8081/pagerduty URL.




Configuring PagerDuty Integration connector

- In order to run this connector you will need a PagerDuty account.
- Specify username (which is registration email) and password in the Authentication tab of the HTTP Request Configuration dialog. Alternatively, you may set corresponding attributes right in the XML file.
- You must register a service at https://{your pagerduty domain}/services.
- The PagerDuty Integration API has a single method which requires a nonempty JSON payload. In this example payload is attached by means of Java transformer which is prueba.PagerDutyIntegrationTransformer. This class has a 'serviceKey' constant which must be manually set key of your service. You must either have this class on your classpath or provide payload in some other way.

Running PagerDuty Integration connector

- Make a call on the http://localhost:8081/pagerdutyIntegration URL.