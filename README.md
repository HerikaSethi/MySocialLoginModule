# Social Login Module [![Android CI](https://github.com/HerikaSethi/MySocialLoginModule/actions/workflows/android.yml/badge.svg)](https://github.com/HerikaSethi/MySocialLoginModule/actions/workflows/android.yml)

This is a modular project in kotlin which includes Social Login CTAs and functionality.
The user can import this as module into their projects and implement the social logins by calling some simple functions.

## Social Logins

- Google Login with Firebase
- Google Login without Firebase
- Facebook Login using sdk
- Github Login
- Fingerprint Login

## Documentation

Modules:

```
:myFacebookSignIn
:myGoogleSignIn
:myGithubSignIn
:myFingerprintAuthentication
```

Importing a module into project :
In build.gradle (app)

```implementation project(':name of module')```
##

#### Google Login With Firebase

    1. Object class: GoogleSignUp
    2. Function call: signInWithGoogle()
    3. Function call takes two arguments context, Request token

Set up your app in firebase, import the firebase dependencies to your project and in onClickListener of button, call the below function.

```http
  GoogleSignUp.signInWithGoogle(context, RequestToken,{success->},{exception->})
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `context` | `Context` | **Required**. context of activity or fragment |
| `requestToken`|`String`| **Required**. firebaseRequestToken
| `{success}`|` GoogleSignInAccount`| **Required**. success callback
| `{exception}`|`Throwable`| **Required**. error callback

Override onActivityResult function and call activityResult function from GoogleSignUp object class.
```http
  GoogleSignUp.activityResult(requestCode,resultCode,data)
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `requestCode` | `Int` | **Required**. requestCode |
| `resultCode`|`Int`| **Required**. resultCode
| `data`|`Intent?`| **Required**. data



#### Google Login Without Firebase

```http
  1. Object class: GoogleSignUpWithoutFirebase
  2. Function call: signUpWithGoogleWithoutFirebase()
  3. Function call takes one argument: context
```

onClickListener of button call function signUpWithGoogleWithoutFirebase()
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `context` | `Context` | **Required**. context of activity or fragment |
| `{success}`|` GoogleSignInAccount`| **Required**. success callback
| `{exception}`|`Throwable`| **Required**. error callback

```http
  GoogleSignUpWithoutFirebase.signUpWithGoogleWithoutFirebase(context, { success ->},{failure->})
```
Override onActivityResult function and call activityResult function from GoogleSignUp object class.

```http
  GoogleSignUpWithoutFirebase.activityResult(requestCode, resultCode, data)
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `requestCode` | `Int` | **Required**. requestCode |
| `resultCode`|`Int`| **Required**. resultCode
| `data`|`Intent?`| **Required**. data


#### Facebook Login

```http
  1. Object class: FacebookSignUp
  2. Function call: signUpWithFacebook()
  3. Function call takes one argument and two callbacks: context, {success},{failure}
```
Set up your app in facebook developers account. Declare the following in your AndroidManifest.xml in application tag

    <meta-data android:name="com.facebook.sdk.ApplicationId"
            android:value="@string/facebook_app_id"/>
        <activity android:name="com.facebook.FacebookActivity"
            android:configChanges=
                "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="@string/app_name" />


        <meta-data
            android:name="com.facebook.sdk.ApplicationId"
            android:value="@string/facebook_app_id" />
        <meta-data
            android:name="com.facebook.sdk.ClientToken"
            android:value="@string/facebook_client_token" />

        <activity
            android:name="com.facebook.CustomTabActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="@string/fb_login_protocol_scheme" />
            </intent-filter>
         </activity>

setOnClickListener of login button and call the below function.

```http
  FacebookSignUp.signUpWithFacebook(context,{profileData->},{exception->})
```

Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `context` | `Context` | **Required**. context of activity or fragment |
| `{profileData}`|`Profile`| **Required**. success callback
| `{exception}`|`Throwable`| **Required**. exception callback

Override onActivityResult function and call activityResult function from FaceboookSignUp class.
```http
  FacebookSignUp.activityResult(requestCode,resultCode,data)
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `requestCode` | `Int` | **Required**. requestCode |
| `resultCode`|`Int`| **Required**. resultCode
| `data`|`Intent?`| **Required**. data


#### Fingerprint Authentication
Applicable for AndroidVersion >= 28
```http
  1. Object class: FingerprintAuthentication
  2. Function call: authenticateWithFingerprint()
  3. Function call takes one argument and two callbacks: context, {success},{exception}
```

Set onClickListener of login button and call the below function
```http
  FingerprintAuthentication.authenticateWithFingerprint(context,{success->},{exception->})
```
Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `context` | `Context` | **Required**. context of activity or fragment |
| `{success}`|`BiometricPrompt.AuthenticationResult`| **Required**. success callback
| `{exception}`|`CharSequence`| **Required**. exception callback

#### Github Login
```http
  1. Object class: GithubSignUp
  2. Function call: signInWithGithub()
  3. Function call takes two arguments and two callbacks: context, email, {success},{exception}
```

Initialize your app in firebase with github login enabled.

Set onClickListener of login button and call the below function
```http
  GithubSignUp.signInWithGithub(this@GithubEmailActivity, email, {result ->}, {exception ->})
```

Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `context` | `Context` | **Required**. context of activity or fragment |
| `email` | `String` | **Required**. email associated with github account |
| `{result}`|`AuthResult?`| **Required**. success callback
| `{exception}`|`Throwable`| **Required**. exception callback
