# Spring-Boot OAuth2 Setup 

This is a working Spring Boot OAuth project copied from [here](https://howtodoinjava.com/spring5/security5/oauth2-auth-server/). All the Postman request templates can be found in the below button. 

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/dc698c270689a25bc286)

## Table of Contents
- [Project Setup](#project-setup)
- Registration
    - [Registering New User](#registering-new-user-via-userregister)
    - [Email Verification](#email-verification-verifyemaileid)
    - [Retrieving 2-Factor Authentication Code](#retrieving-2-factor-auth-code)
- Authentication with Password
    - [Authentication with Password](#authentication-with-password)
- Reset Password
    - [Reset Password](#reset-password)
## Workflow

### Project Setup
1. After cloning the project open it as a maven project in your favorite IDE.

2. Run `mvn package` to install all the dependencies.

3. Start the application through your IDE's play/start button.

### Registering new user via `/user/register`
1. Create a Postman request with method set to `POST` and url to `localhost:8080/user/register`.

2. Under the `Body` tab, select `raw` and then select `JSON (application/json)` from the dropdown menu to the right.

3. In the text box field under the `Body` tab, insert the request body in the following format with the following information (replace username, password, and email inputs with own credentials):
    ```
    {
        "username": "USERNAME",
        "password": "PASSWORD",
        "email":"VALID@EMAIL.COM"
    }
    ```
4. Press send button and you should receive back a JSON object with the same information that you inserted in previous step (plus a two additional fields).

    ![registration-response](img/registration-response.png)
    
    Note: The email verification link and the 2-factor authentication QR code link are also located in this response (also found in `emailVerification.txt` and `totpQRUrl.txt` files respectively) for email verification and login authentication.

5. Your new user was just registered but remains disabled until your email address is verified (follow the steps in [Email Verification](#email-verification) in order to verify your email).

### Email Verification `/verify/email/eid=?`
1. Using the `email-verification` link found in the registration response body (also found in `emailVerification.txt`), either create a new, empty, postman `GET` request directed at this url, or simply go to this url in your browser.

2. After sending the request (or visiting this link in a browser), you should receive a response that states `User Verified!`, indicating that you successfully verified your newly registered user's email.

3. Next, follow the steps below [(Retrieving 2-Factor Auth Code)](#retrieving-2-factor-auth-code) in order to retrieve your 2-factor authentication codes needed for login.

### Retrieving 2-Factor Auth Code 
1. If you do not already have one, download one of the many 2 factor authentication applications from your favorite app store (Recommended: `Google Authenticator` or `Authy` shown below)
    
    ![Authenticator App 1](img/authenticator-app1.jpg) ![Authenticator App 2](img/authenticator-app2.jpg)
    
2. Follow the `qr-url` link found in the registration response body (or found in `totpQRUrl.txt` file) in order to display your QR code.

3. Open your authenticator app and click the "+" button to add a new account - add this account by scanning the QR code displayed in your browser.

4. You should now see new 6 digit authentication codes being generated every 30 seconds in your app.  This code will be used in the following step to [retrieve your token](#authentication-with-password-oauthtoken).

### Authentication with Password `/oauth/token`
1. Create a Postman request with method set to `POST` and url to `localhost:8080/ouath/token`.

2. Under the `Params` tab add `code` with the 6 digit 2-factor authentication code that is generated in your authentication app from the step above - [Retrieving 2-Factor Auth Code](#retrieving-2-factor-auth-code) - as its value.  

    ![Postman Params](img/postman-params.PNG)
    Note: These codes expire every 30 seconds, so if it takes longer to set up the rest of the postman request and send it, you might have to re-enter a new 6 digit code here.

3. Under the `Headers` tab add the following key value pairs:
    - `Authorization` : `Basic Y2xpZW50YXBwOjEyMzQ1Ng==`
    - `Content-Type` : `application/x-www-form-urlencoded`

    ![Postman Headers](img/postman-headers.png)

4. Under the `Body` tab select `x-www-form-urlencoded` (this will populate a new header) and add the following key value pairs:
    - `grant_type`: `authorization_code`
    - `redirect_uri`: `localhost:8081/login`
    - `username`: `YOUR_USERNAME`
    - `password`: `YOUR_PASSWORD`

    ![Postman Body](img/postman-body.png)

5. Press send button and you should get an JSON object with JWT access-token.
    ![Return Object](img/return-object.png)

### Reset Password

Password reset flow consists of three steps:
1. Retrieving the password reset URL in an email (currently written into a file)
2. Using the URL to access the password reset page (not built yet)
3. Filling in the password reset form on the page and resetting the password

#### Get Reset Password Token `/user/reset_password/token`

1. For simplicity we are just passing the username to the API to get back a reset-password token in a form of an email with an URL. (will be changed to be more secure at a later date)
    ```
        {
            username: han
        }
    ```
2. Add `Content-Type: application/json` to the header section in Postman.
3. Send a `POST` request to `localhost:8080/user/reset_password/token` to receive a token back. If the user doesn't exist the response object will say so. A successful request will return the password-reset token used in the below functionality.
#### Password Reset URL

1. This REST endpoint will be redirecting the user from an URL sent to their email for password reset.
2. To mimic the output of the password reset email the content is written to `EmailResetPasswrd.txt`
3. To go to the password reset page (not built yet) just copy the link inside `EmailResetPassword.txt` and put it in Postman GET request or a browser. If the token included in this link is valid then a placeholder redirect text will be returned. If the token is invalid the response will say so.

### Password Reset

1. This REST endpoint will respond to a user submitting a password reset form from the browser.
2. For simplicity, there is no authentication for this endpoint. In Postman we only need `Content-Type: application/json` in Header section and the following JSON object in the Body section:
    ```
        {
        	"token": "21fc22b7-0f9a-4508-a806-601e619b852d",
        	"newPassword": "passwordhan2"
        }
            
   ```
3. Press `Send` and the response should either be a successful password reset or an invalid token exception.
