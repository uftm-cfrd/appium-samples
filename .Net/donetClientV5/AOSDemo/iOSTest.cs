using NUnit.Framework;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.iOS;
using OpenQA.Selenium;
using System;
using OpenQA.Selenium.Support.UI;
using SeleniumExtras.WaitHelpers;

namespace Appium_dotnetClientV5
{
    public class iOSTest
    {
        public static IOSDriver driver;
        public static String UFTM_SERVER = "http://uftmserver:8080";

        [SetUp]
        public void Setup()
        {
            AppiumOptions appiumOptions = new AppiumOptions();

            //UFTM embedded Appium capabilities                                  
            appiumOptions.AddAdditionalAppiumOption("platformName", "iOS");
            appiumOptions.AddAdditionalAppiumOption("appium:udid", "3ecdf8889a8b18cb3658410d1540f3e6b22a2cb1");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientId", "oauth2-wxAQbZly7DF9r6ukIvvz@microfocus.com");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientSecret", "wldgymaOGQRfCQxb2NbJ");
            appiumOptions.AddAdditionalAppiumOption("uftm:tenantId", "999999999");
            appiumOptions.AddAdditionalAppiumOption("appium:bundleId", "com.mf.iShopping");            
            
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
        }

    }
}