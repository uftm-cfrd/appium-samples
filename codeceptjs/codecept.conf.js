// If your are using a self-signed certificate
process.env['STRICT_SSL'] = 'false';

exports.config = {
  tests: './*_test.js',
  output: './output',
  helpers: {
    Appium: {
      protocol: 'https',  // this is optional, add only when https is enabled for the connection.
      host: 'server.host',
      port: 8443,
      platform: 'Android',      
      capabilities: {
        insecure: true,
        platformName: 'Android',
        deviceName: 'SM-N950F',
        // for UFT Mobile prior to 3.2, use userName and password
        //userName: 'admin@default.com',
        //password: 'password',
        // for UFT Mobile 3.2 and newer, use oauthClientId, oauthClientSecret, and tenantId
        oauthClientId: 'oauth2-8BtEwdjyWMyrf2QETRkP@microfocus.com',
        oauthClientSecret: 'ocDQ46L4FwHcEOBbW1Ar',
		tenantId: '999999999',
        browserName: 'chrome',
        proxy: { // See https://codecept.io/helpers/WebDriver/#connect-through-proxy
          "proxyType": "manual",
          "httpProxy": "http://proxy.server:8080",
          "sslProxy": "https://proxy.server:8080"
        }
      }
    }
  },
  include: {
    I: './steps_file.js'
  },
  bootstrap: null,
  mocha: {},
  name: 'uftm-codeceptjs-appium'
};
