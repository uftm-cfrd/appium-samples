### How to use?
1. Clone/download this code repo.
1. Unzip it if needed, then go into the extracted directory
1. Navigate to the desired sample and open the pom.xml file as a project from IntelliJ or Eclipse
1. The project dependencies will be downloaded automatically
1. Once the dependencies are downloaded, update the general parameters (like UFTM server, authentication credentials, device UDID) and run the JUnit test.

### Samples:
- **Multiappium_Support**: starting with UFTM 2021 R2, UFTM introduces support for multiple Appium versions. With this sample test, you can observe how to switch between Appium version 1.x and 2.x by using the new _**appiumVersion**_ capability. It includes sample tests for both, Android and iOS, and it uses the Advantage Shopping demo app that comes bundled with UFTM.
- **Appium_Simulations**: starting with UFTM 2021 R2, UFTM introduces support for biometric, photo, QR/barcode simulations on Appium. This sample shows how to use these simulations using Java code on Advantage Shopping demo app for Android.
- **JavaClient_v7**: This is a basic Appium sample written in Java for both, Android and iOS, platforms. It uses the Appium Java client version 7.x
- **JavaClient_v8**: This is a basic Appium sample written in Java for both, Android and iOS, platforms. It uses the Appium Java client version 8.x
