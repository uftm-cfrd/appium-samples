using NUnit.Framework;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.iOS;
using OpenQA.Selenium;
using System;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Multiappium_Support
{
    public class iOSTest
    {
        public static IOSDriver driver;
        public static String UFTM_SERVER = "https://uftm-server:8443";
        //Update the below variable to indicate whether you want to use Appium v2 or the default Appium v1
        private Boolean useAppiumV2 = true;

        [SetUp]
        public void Setup()
        {
            AppiumOptions appiumOptions = new AppiumOptions();

            //UFTM embedded Appium capabilities                                              
            appiumOptions.AddAdditionalAppiumOption("appium:udid", "fd95352a82e684a73378eb10a0e584fbb098a56c");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientId", "oauth2-9oISD2ZvwH0sxDR6RyB7@microfocus.com");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientSecret", "XUNPCs3cB1F04qLRFYOf");
            appiumOptions.AddAdditionalAppiumOption("uftm:tenantId", "999999999");
            appiumOptions.AddAdditionalAppiumOption("appium:bundleId", "com.mf.iShopping");

            //UFTM will default to Appium v1.x if appiumVersion capability is not indicated, so the "else" part is not required
            if (useAppiumV2)
                appiumOptions.AddAdditionalAppiumOption("uftm:appiumVersion", "v2.x");
            else
                appiumOptions.AddAdditionalAppiumOption("uftm:appiumVersion", "v1.x");

            System.Uri url = new System.Uri(string.Format("{0}/wd/hub", UFTM_SERVER));
            driver = new IOSDriver(url, appiumOptions);
            System.Console.WriteLine("UFTM session was successfully created [iOS device]");
        }

        [TearDown]
        public void TearDown()
        {
            if (driver != null)
            {
                driver.Quit();
            }
        }

        [Test]
        public void Test()
        {
            
            WebDriverWait wait = new WebDriverWait(driver, TimeSpan.FromSeconds(10));
            driver.FindElement(MobileBy.XPath("//XCUIElementTypeStaticText[@name=\"LAPTOPS\"]")).Click();
            driver.FindElement(MobileBy.AccessibilityId("HP ENVY x360 - 15t Laptop")).Click();            
            wait.Until(ExpectedConditions.ElementExists(MobileBy.AccessibilityId("productDetailsButton"))).Click();
            driver.FindElement(MobileBy.AccessibilityId("Close2")).Click();
            driver.FindElement(MobileBy.AccessibilityId("quantityButtonId")).Click();
            driver.FindElement(MobileBy.AccessibilityId("Plus")).Click();
            driver.FindElement(MobileBy.AccessibilityId("APPLY")).Click();
            driver.FindElement(MobileBy.AccessibilityId("ADD TO CART")).Click();
            driver.FindElement(MobileBy.AccessibilityId("Ok")).Click();
            driver.FindElement(MobileBy.AccessibilityId("userNameLabel")).SendKeys("Username");
            driver.FindElement(MobileBy.AccessibilityId("passwordLabel")).SendKeys("password");
            driver.FindElement(MobileBy.AccessibilityId("LOGIN")).Click();
            WebElement loginErrorText = (WebElement) wait.Until(ExpectedConditions.ElementExists(MobileBy.AccessibilityId("Incorrect user name or password.")));
            Assert.IsTrue(loginErrorText.Displayed);
            wait.Until(ExpectedConditions.ElementExists(MobileBy.AccessibilityId("Menu"))).Click();
            driver.FindElement(MobileBy.AccessibilityId("HOME")).Click();
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