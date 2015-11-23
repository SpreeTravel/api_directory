The API notebooks are all executable! Hit "enter" in any code cell to execute it (and all cells before it that have not executed yet), or scroll to the bottom of the notebook and click "Play notebook". For more information, see [http://apinotebook.com](http://apinotebook.com).

#Considerations

- In order to run these notebooks you will need a Jira account which can be hosted by Atlassian or on your own. In the later case your jira installation URL must have empty path.
- Inside your Jira account you must create a special test project type of "JIRA Classic". Select this project when the notebook asks you to provide a project to operate with.
- The test project must have an administrator. Apply his credentials when you are prompted to authorize the client application.
- Some methods of the notebook, related with security levels, require you to have at least one security level defined as described at [https://confluence.atlassian.com/display/JIRA/Configuring+Issue-level+Security](https://confluence.atlassian.com/display/JIRA/Configuring+Issue-level+Security)
- Methods which retreive a particular dashboard require your account to have at least one dashboard. A dashboard can be easily created via "Dashboards" menu.
- Note that the "Workflowscheme" notebook is not fully automated. At some points of execution it requires a test workflow scheme to be manually activated and deactivated. A workflow scheme is considered active if it is associated with at least one project. Learn how to associate workflolw schemes with projects here: [https://confluence.atlassian.com/display/JIRA/Activating+workflow#Activatingworkflow-associatingworkflowschemetoproject](https://confluence.atlassian.com/display/JIRA/Activating+workflow#Activatingworkflow-associatingworkflowschemetoproject).
