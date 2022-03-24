using NUnit.Framework;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.Android;
using OpenQA.Selenium;
using System;

namespace Appium_dotnetClientV5
{
    public class AndroidTest
    {
        public static AndroidDriver driver;
        public static String UFTM_SERVER = "http://uftmserver:8080";

        [SetUp]
        public void Setup()
        {
            AppiumOptions appiumOptions = new AppiumOptions();

            //UFTM embedded Appium capabilities                                  
            appiumOptions.AddAdditionalAppiumOption("platformName", "Android");            
            appiumOptions.AddAdditionalAppiumOption("appium:udid", "RF8N807MPYH");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientId", "oauth2-wxAQbZly7DF9r6ukIvvz@microfocus.com");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientSecret", "wldgymaOGQRfCQxb2NbJ");
            appiumOptions.AddAdditionalAppiumOption("uftm:tenantId", "999999999");                    
            appiumOptions.AddAdditionalAppiumOption("appium:appPackage", "com.Advantage.aShopping");
            appiumOptions.AddAdditionalAppiumOption("appium:appActivity", "com.Advantage.aShopping.SplashActivity");
            System.Uri url = new System.Uri(string.Format("{0}/wd/hub", UFTM_SERVER));            

            driver = new AndroidDriver(url, appiumOptions);
            System.Console.WriteLine("UFTM session was successfully created [Android device]");

        }

        [TearDown]
        public void TearDown() {
            if (driver != null)
            {
                driver.Quit();
            }
        }

        [Test]
        public void Test()
        {
            driver.Manage().Timeouts().ImplicitWait = System.TimeSpan.FromSeconds(10);
            driver.FindElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"LAPTOPS\")")).Click();
            driver.FindElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"HP ENVY X360\")")).Click();
            driver.FindElement(MobileBy.Id("linearLayoutProductDetails")).Click();
            driver.FindElement(MobileBy.Id("imageViewProductDetailsClose")).Click();
            driver.FindElement(MobileBy.Id("linearLayoutProductQuantity")).Click();
            driver.FindElement(MobileBy.XPath(
                    "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]"))
                    .Click();
            driver.FindElement(MobileBy.Id("textViewApply")).Click();
            driver.FindElement(MobileBy.Id("buttonProductAddToCart")).Click();
            WebElement username = driver.FindElement(MobileBy.XPath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginUserName']/android.widget.EditText[1]"));
            username.Click();
            username.SendKeys("Username");
            WebElement password = driver.FindElement(MobileBy.XPath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginPassword']/android.widget.EditText[1]"));
            password.Click();
            password.SendKeys("password");
            driver.FindElement(MobileBy.Id("buttonLogin")).Click();
            string ErrorMessage = driver.FindElement(MobileBy.Id("textViewFailed")).Text;
            StringAssert.Contains("Incorrect user", ErrorMessage);
            driver.Navigate().Back();
            driver.Navigate().Back();
            driver.Navigate().Back();
            driver.FindElement(MobileBy.Id("imageViewMenu")).Click();
            driver.FindElement(MobileBy.Id("textViewMenuHome")).Click();
        }
        
    }
}