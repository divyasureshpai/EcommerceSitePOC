# Ecommerce automation test suite

This test suit tests

1. Nature's way web page 'Add to cart feature: WEB
2. Verify the Authentication functionality of the app.baze: API
3. Password reset functionality of app.baze: API

## Set Up for executing API password reset functionality

1. Follow the below google documentation to create a new Google cloud project.
     https://developers.google.com/workspace/guides/create-project
2. Enable the Gmail API for the newly created project.
3. To create credentials(OAuth client ID for Desktop app) follow the below instructions.
      https://developers.google.com/workspace/guides/create-credentials
5. Download and keep the credentials in the path: src/main/resources/googleCredentials/credentials.json (rename the downloaded file )
4. Once credentials are set up do the following under the OAuth consent tab to perform App registration:
		a) add the test user mail id 
		b) add scope as Gmail ReadOnly.
		c) add the App name as: POCBazeLabs
5. Update the user name mail id & new password for the API at: src/test/resources/com/config/config.properties


## Usage

1. Execute the TestRunner class(src/test/java/com/testRunner/TestRunner.java)

## Note

1. The test results can be found in the test-output folder
2. If this is your first time running the suit, it will open a new window prompting you to authorize access to your data. If you are not already signed in to your Google account, you are prompted to sign in. Click Accept. The app is authorized to access your data.
