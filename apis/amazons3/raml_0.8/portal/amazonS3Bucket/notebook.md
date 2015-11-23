The API notebooks are all executable! Hit "enter" in any code cell to execute it (and all cells before it that have not executed yet), or scroll to the bottom of the notebook and click "Play notebook". For more information, see [http://apinotebook.com](http://apinotebook.com).

#Considerations

- In order to run this notebooks you will need an Amazon S3 account. Each notebook prompts you to enter your Access Key and Secret Key. These credentials can be found at [https://console.aws.amazon.com/iam/home?#security_credential](https://console.aws.amazon.com/iam/home?#security_credential) in the section named "Access Keys (Access Key ID and Secret Access Key)".
- The "Buckets part 2" notebook prompts you to enter your AWS Account ID. It can be found at [https://console.aws.amazon.com/iam/home?#security_credential](https://console.aws.amazon.com/iam/home?#security_credential) in the "Account Identifiers" section.​
- The "Objects part 3, restoration" notebook requires a bucket with archived object inside. As there is no way to immediately archive an object, we require the user to prepare such bucket. You will be prompted to enter name of the bucked and key of the archived object. See [Object Archival (Transition Objects to the GLACIER Storage Class)](http://docs.aws.amazon.com/AmazonS3/latest/dev/object-archival.html) for details.
