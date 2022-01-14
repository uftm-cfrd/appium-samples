using NUnit.Framework;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.Android;

namespace Appium_AndroidDemo
{
    public class Tests
    {
        public static AndroidDriver<AppiumWebElement> driver;

        [SetUp]
        public void Setup()
        {
            AppiumOptions appiumOptions = new AppiumOptions();

            // Set default capabilities            
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.NewCommandTimeout, 1800);
            //Capabilities for the device            
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.PlatformName, "Android");            
            appiumOptions.AddAdditionalCapability(MobileCapabilityType.Udid, "ce11160ba124842401");

            // Option 1: UFTM authentication using Access Key
            appiumOptions.AddAdditionalCapability("oauthClientId", "oauth2-zraGsvwMKM4UA1eQg09s@microfocus.com");
            appiumOptions.AddAdditionalCapability("oauthClientSecret", "jdxp5ufIFg7EIM03cI1c");
            appiumOptions.AddAdditionalCapability("tenantId", "999999999");
            // Option 2: UFTM authentication using user name and password
            //appiumOptions.AddAdditionalCapability("userName", "admin@default.com");
            //appiumOptions.AddAdditionalCapability("password", "p4ssw0rd");

            // Indicate the app we'll be working with 
            appiumOptions.AddAdditionalCapability(AndroidMobileCapabilityType.AppPackage, "com.Advantage.aShopping");
            appiumOptions.AddAdditionalCapability(AndroidMobileCapabilityType.AppActivity, "com.Advantage.aShopping.SplashActivity");

            // Instantiate the iOS driver
            System.Uri url = new System.Uri(string.Format("{0}/wd/hub", "https://demo.mobilecenter.io:443"));
            driver = new AndroidDriver<AppiumWebElement>(url, appiumOptions);

        }

        [Test]
        public void Test1()
        {
            try
            {
                driver.FindElementByXPath("//android.view.ViewGroup[@content-desc=\"Home Page\"]/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.support.v7.widget.RecyclerView/android.widget.RelativeLayout[1]/android.widget.TextView").Click();
                driver.FindElementByXPath("//android.widget.TextView[@text='HP CHROMEBOOK 14 G1(ENERGY STAR)']").Click();
                driver.FindElementById("com.Advantage.aShopping:id/buttonProductAddToCart").Click();
                driver.FindElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[3]/android.widget.EditText").SendKeys("non-existing user");
                driver.FindElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[4]/android.widget.EditText").SendKeys("pass");
                driver.FindElementById("com.Advantage.aShopping:id/buttonLogin").Click();
                driver.Navigate().Back();
                driver.Navigate().Back();
                driver.FindElementById("com.Advantage.aShopping:id/imageViewMenu").Click();
                driver.FindElementById("com.Advantage.aShopping:id/textViewMenuHome").Click();
                Assert.Pass();
            }
            finally
            {
                driver.Quit();
            }
        }
    }
}