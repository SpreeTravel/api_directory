Configuring PivotalTracker connector

- In order to run this connector you must register a PivotalTracker user.
- In the very end of your PivotalTracker user profile you can see API TOKEN. It must be set as value of the 'X-TrackerToken' header of your request.

Running PivotalTracker connector

- Make a call on the http://localhost:8081/pivotaltracker URL.




Configuring PivotalTracker Attachment Download connector

- In order to run this connector you must register a PivotalTracker user.
- In the very end of your PivotalTracker user profile you can see API TOKEN. It must be set as value of the 'X-TrackerToken' header of your request.
- You must set ID of some attachment as 'file_attachment_id' query parameter value. Such ID can be seen in attachment URL.


Running PivotalTracker Attachment Download connector

- Make a call on the http://localhost:8081/ptAttachmentDownload URL.