using NUnit.Framework;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.iOS;

namespace Appium_iOSDemo
{
    public class Tests
    {
        public static IOSDriver<AppiumWebElement> driver;

        [SetUp]
        public void Setup()
        {
            AppiumOptions appiumOptions = new AppiumOptions();

            // Set default capabilities
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.AutomationName, "XCUITest");
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.NewCommandTimeout, 1800);
            //Capabilities for the device
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.DeviceName, "Apple iPhone XR");
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.PlatformName, "iOS");
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.PlatformVersion, "15.0.1");
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.Udid, "00008020-00056D080186002E");

            // Option 1: UFTM authentication using Access Key
            appiumOptions.AddAdditionalCapability("oauthClientId", "oauth2-zraGsvwMKM4UA1eQg09s@microfocus.com");
            appiumOptions.AddAdditionalCapability("oauthClientSecret", "jdxp5ufIFg7EIM03cI1c");
            appiumOptions.AddAdditionalCapability("tenantId", "999999999");
            // Option 2: UFTM authentication using user name and password
            //appiumOptions.AddAdditionalCapability("userName", "admin@default.com");
            //appiumOptions.AddAdditionalCapability("password", "p4ssw0rd");

            // Indicate the app we'll be working with 
            appiumOptions.AddAdditionalCapability(IOSMobileCapabilityType.BundleId, "com.mf.iShopping");

            // Instantiate the iOS driver
            System.Uri url = new System.Uri(string.Format("{0}/wd/hub", "https://demo.mobilecenter.io:443"));
            driver = new IOSDriver<AppiumWebElement>(url, appiumOptions);           
            
        }

        [Test]
        public void Test1()
        {
            try
            {
                driver.FindElementByAccessibilityId("LAPTOPS").Click();
                driver.FindElementByAccessibilityId("HP Chromebook 14 G1(ENERGY STAR)").Click();
                driver.FindElementByAccessibilityId("ADD TO CART").Click();
                driver.FindElementByAccessibilityId("Ok").Click();
                driver.FindElementByAccessibilityId("userNameLabel").SendKeys("non-existing user");
                driver.FindElementByAccessibilityId("passwordLabel").SendKeys("pass");
                driver.FindElementByAccessibilityId("LOGIN").Click();
                driver.Navigate().Back();
                driver.FindElementByAccessibilityId("HOME").Click();
                Assert.Pass();
            }            
            finally
            {
                driver.Quit();
            }            
        }
    }
}