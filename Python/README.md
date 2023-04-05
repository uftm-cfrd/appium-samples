
# Python Appium sample
This is a basic Appium sample written in Python for both, Android and iOS, platforms

## Requirements
1. Download and install Python from [here](https://www.python.org/downloads/)
2. Install the sample project dependencies
    ```sh
    pip install -r requirements.txt
    ```

## Run the samples
The project contains two sample tests, one for Android and one for iOS:
* sampleTestAOSapp_Android.py
* sampleTestAOSapp_iOS.py
### Before running:
1. Update the Appium capabilities to match the desired device and your UFT Digital Lab credentials. For more details, check the [Digital Lab Appium Capabilities](https://admhelp.microfocus.com/uftmobile/en/latest/Content/Appium/Appium_Capabilities.htm)
2. Update the protocol, hostname and port to point to the UFT Digital Lab server that you plan to use:
```python
self.driver = webdriver.Remote('<protocol>://<hostname>:<port>/wd/hub', options=options)
```
For example:
```python
self.driver = webdriver.Remote('https://uftdl-server:8443/wd/hub', options=options)
```

### Run
```sh
python sampleTestAOSapp_Android.py
```

```sh
python sampleTestAOSapp_iOS.py
```