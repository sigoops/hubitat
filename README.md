# hubitat
Hubitat Drivers, Scripts, Stuff

## Drivers
### Http Json Post
Can be used as a virtual device that can send HTTP POST calls with bearer token

#### Usage
1. Add the driver to Hubitat
2. Create a virtual device using this driver
3. Configure base URL and bearer token
4. Use in RM action: `Send HTTP Request` ... `Send/Speak a Message`
5. Enter JSON message to send (escape quotes):
```
    {
        "endpoint": "/api/greet",
        "payload": "{ \"hello\": \"world\" }"
    }
```
6. Select the device you created to send this message to

#### Configuration:
- baseUrl: e.g. https://www.example.com
- token: Bearer token
    - Used in the header by the driver as such: `Authorization: Bearer MY_TOKEN`


### Send Text Notification Event
Can be used to send an event to Hubitat with provided message. Useful for drivers that pick-up these events, e.g. [hubitat-mqtt-link](https://github.com/mydevbox/hubitat-mqtt-link)

#### Usage
1. Add the driver to Hubitat
2. Create a virtual device using this driver
3. Use in RM action: `Send HTTP Request` ... `Send/Speak a Message`
4. Enter message to send.
5. Select the device you created to send this message to
6. If used with `hubitat-mqtt-link`, the text will be published to MQTT at the `notification` endpoint, e.g.: `hubitat/home-000d/my-device-1-738/notification`
