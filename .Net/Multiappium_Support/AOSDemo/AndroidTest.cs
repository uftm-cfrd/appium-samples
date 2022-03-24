using NUnit.Framework;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.Android;
using OpenQA.Selenium;
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Multiappium_Support
{
    public class AndroidTest
    {
        public static AndroidDriver driver;
        public static String UFTM_SERVER = "https://uftm-server:8443";
        //Update the below variable to indicate whether you want to use Appium v2 or the default Appium v1
        private Boolean useAppiumV2 = true;

        [SetUp]
        public void Setup()
        {
            AppiumOptions appiumOptions = new AppiumOptions();

            //UFTM embedded Appium capabilities                                                         
            appiumOptions.AddAdditionalAppiumOption("appium:udid", "RF8N807MPYH");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientId", "oauth2-9oISD2ZvwH0sxDR6RyB7@microfocus.com");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientSecret", "XUNPCs3cB1F04qLRFYOf");
            appiumOptions.AddAdditionalAppiumOption("uftm:tenantId", "999999999");                    
            appiumOptions.AddAdditionalAppiumOption("appium:appPackage", "com.Advantage.aShopping");
            appiumOptions.AddAdditionalAppiumOption("appium:appActivity", "com.Advantage.aShopping.SplashActivity");
            System.Uri url = new System.Uri(string.Format("{0}/wd/hub", UFTM_SERVER));

            //UFTM will default to Appium v1.x if appiumVersion capability is not indicated, so the "else" part is not required
            if (useAppiumV2)
                appiumOptions.AddAdditionalAppiumOption("uftm:appiumVersion", "v2.x");
            else
                appiumOptions.AddAdditionalAppiumOption("uftm:appiumVersion", "v1.x");

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
            printAppiumVersionFromLog();
        }

        /// <summary>
        /// Searches the Appium version from an Appium log string and prints it in the console
        /// </summary>
        private void printAppiumVersionFromLog()
        {
            MatchCollection matches = Regex.Matches(getAppiumlog(), "(Appium v[^\\s]+)");
            Console.WriteLine("Appium version extracted from the Appium log:");
            Console.WriteLine(String.IsNullOrEmpty(matches[0].Groups[1].Value) ? "Not found" : matches[0].Groups[1].Value);
        }

        /// <summary>
        /// Downloads Appium logs from UFTM
        /// </summary>
        /// <returns></returns>
        private String getAppiumlog()
        {
            Dictionary<string, string> encoding = new Dictionary<string, string>();
            encoding.Add("encoding", "UTF-8");
            String logFileContents = (String)driver.ExecuteScript("mc-wd: downloadLogs", encoding);
            return logFileContents;
        }

    }
}