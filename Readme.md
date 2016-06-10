## SMS Handler for Ideabiz

##Pre request
* Maven install bellow projects
	Ideabiz-APICallHandler (https://github.com/ideabizlk/IdeaBiz-Request-Handler-JAVA)
	ideabiz-common-java-class (https://github.com/ideabizlk/Classes-JAVA)
* Setup request handler as your app or use sample app setup. More help, please refer Request handler Readme

## Usage
Include `SMS Handler` to project

###Create SMS handler objct 
```
smsHandler = new SMSHandler("https://ideabiz.lk/apicall/smsmessaging/v2/outbound/94777123456/requests", LibraryManager.getApiCall(), 1, "MyMask", "tel:87711");
```

`LibraryManager.getApiCall()` is `APICall` Object from `Ideabiz-APICallHandler`

###Read Incomming smsHandler
```
 SMSMessage smsMessage = smsHandler.readSMS(body);
```

####SMS Message content
```
    String message;
    String messageId;
    String destinationAddress;
    String sourceAddress;
    String Operator;
```

###Send SMS

```
smsHandler.sendSMS("tel:+94777123456", "Hi");
```

###Send SMS with custom sender name or address

```
smsHandler.sendSMS("tel:87722", "MyMask2", "tel:+94777123456", "Hi");

smsHandler.sendSMS("tel:87722", null, "tel:+94777123456", "Hi");
```


