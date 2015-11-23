Configuring connector

- In order to run this connector you must register a SmartSheet Developer account at [http://www.smartsheet.com/developers/register](http://www.smartsheet.com/developers/register).
- MuleStudio does not support OAuth for SmartSheet, so you must generate access token as described at [Direct API Access](http://www.smartsheet.com/developers/api-documentation#id.eg9s4hawz6wd). This access token must be put into 'Authorization' header of your request. 

Running connector

- Make a call on the http://localhost:8081/smartsheet URL. 