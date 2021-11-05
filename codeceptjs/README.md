## Using UFT Mobile with CodeceptJS and Android Chrome browser

### How to use
1. Clone/download this code repo.
1. Unzip it if needed, then go into this directory.
1. Execute ```npm install```
1. Run tests
   * Run with npx  
      For all, run ```npx codeceptjs run --steps```  
      For single test, run ```npx codeceptjs run Web_test.js```
   * Run without npx  
      For all, run ```node node_modules\codeceptjs\bin\codecept.js run --steps```  
      For single test, run ```node node_modules\codeceptjs\bin\codecept.js run Web_test.js```

### Proxy Setting
1. Proxy setting via environmental variables.  
 To enable proxy for your testing, please setup environment variable ```http_proxy=http://proxy.server:8080```,
 or ```https_proxy=https://proxy.server:8080``` for https connection. For details, see 
 [Controlling proxy behaviour using environment variables](https://github.com/request/request#controlling-proxy-behaviour-using-environment-variables).
1. Proxy setting via CodeceptJS/Webdriver API  
  Refer to [Connect through proxy](https://codecept.io/helpers/WebDriver/#connect-through-proxy)

### HTTPS connection
To connect a HTTPS enabled UFT Mobile Server, please add ```protocol: 'https'``` in Appium setting to enable it.

### Trust self-signed certificate for SSL/HTTPS
Add following code snippet before helper configuration.
```javascript
process.env['STRICT_SSL'] = 'false';
```
### Example code

```javascript 1.8
//If your are using a self-signed certificate
process.env['STRICT_SSL'] = 'false';

exports.config = {
  tests: './*_test.js',
  output: './output',
  helpers: {
    Appium: {
        protocol: 'https',  // this is optional, add only when https is enabled for the connection.
        host: '10.5.33.54',
        port: 8443,
        platform: 'Android',
        // url: "https://github.com/", // not working for Appium.
        capabilities: {
          insecure: true,
          platformName: 'Android',
          deviceName: 'Nexus 6',
          // For UFT Mobile prior to 3.2, use userName and password
          // userName: 'admin@default.com',
          // password: 'password',

          // For UFT Mobile 3.2 and newer, use oauthClientId and oauthClientSecret
          // oauthClientId: 'oauth2-DXyUfdFOegMZiduw4JCe@microfocus.com',
          // oauthClientSecret: 'eMua6RJ08hEoDlOB5lUE',
		  // tenantId: '999999999',
          browserName: 'Chrome',
          proxy: { // See https://codecept.io/helpers/WebDriver/#connect-through-proxy
            "proxyType": "manual",
            "httpProxy": "http://127.0.0.1:8889",
            "sslProxy": "http://127.0.0.1:8889"
          }
        }
      }
  },
  include: {
    I: './steps_file.js'
  },
  bootstrap: null,
  mocha: {},
  name: 'uftm-codeceptjs-appium-web'
};
```
### Example code for web test case
```javascript
Feature('Simple Feature');

Scenario('test something', ({ I }) => {
  I.amOnPage('https://admhelp.microfocus.com/uftmobile/en/latest/Content/Resources/_TopNav/_TopNav_Home.htm');
  I.see('UFT Mobile Help');
});
```
