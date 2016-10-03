## Mercado Pago Connect

This component let you login in a Mercado Pago account from an Android app.
You can start this component just like this code:

***
```java
    new MPConnect.StartActivityBuilder()
            .setActivity(context)
            .setAppId("app_id")
            .setMerchantBaseUrl("merchant_base_url")
            .setMerchantGetCredentialsUri("get_credentials_uri")
            .setUserIdentificationToken("user_identification_token")
            .startConnectActivity();
    }
```
***

Then you can wait the result with this code:

***
```java
  @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          String accessToken;

          if (requestCode == MPConnect.CONNECT_REQUEST_CODE) {
              if (resultCode == RESULT_OK) {
                  accessToken = data.getStringExtra("accessToken");
                  //Do something
              } else {
                  //Do something
              }
          }
      }
  }
```
***
