using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Enums;
using OpenQA.Selenium.Appium.Android;
using OpenQA.Selenium;
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Threading;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System.IO;
using System.Drawing.Imaging;
using System.Drawing;
using Appium_Simulations.Resources;
using OpenQA.Selenium.Remote;

namespace Appium_Simulations
{
    public class Android
    {
        public static AndroidDriver driver;
        public static String UFTM_SERVER = "http://uftm-server:8080";
        //Update the below variable to indicate whether you want to use Appium v2 or the default Appium v1
        private static Boolean useAppiumV2 = true;

        static void Main(string[] args)
        {            
            AppiumOptions appiumOptions = new AppiumOptions();

            //UFTM embedded Appium capabilities                                                                                 
            appiumOptions.AddAdditionalAppiumOption("appium:udid", "8BSX1EDTD");            
            appiumOptions.AddAdditionalAppiumOption("appium:appPackage", "com.Advantage.aShopping");
            appiumOptions.AddAdditionalAppiumOption("appium:appActivity", "com.Advantage.aShopping.SplashActivity");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientId", "oauth2-UNmB6dXe4XNzJpjY4GSg@microfocus.com");
            appiumOptions.AddAdditionalAppiumOption("uftm:oauthClientSecret", "3Rd1wgB0g71KBT6S2tv3");
            appiumOptions.AddAdditionalAppiumOption("uftm:tenantId", "999999999");

            //Simulations are supported only on packaged apps. The below capability instructs UFTM to install the packaged version of the app. Default value is false
            appiumOptions.AddAdditionalAppiumOption("uftm:installPackagedApp", true);
            System.Uri url = new System.Uri(string.Format("{0}/wd/hub", UFTM_SERVER));            

            try
            {
                driver = new AndroidDriver(url, appiumOptions);
                System.Console.WriteLine("UFTM session was successfully created [Android device]");

                //Implicit wait
                driver.Manage().Timeouts().ImplicitWait = TimeSpan.FromSeconds(10);
                driver.FindElement(MobileBy.Id("imageViewMenu")).Click();
                driver.FindElement(MobileBy.Id("textViewMenuUser")).Click();
                WebElement username = driver.FindElement(MobileBy.XPath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginUserName']/android.widget.EditText[1]"));
                username.Click();
                username.SendKeys("Mercury");
                WebElement password = driver.FindElement(MobileBy.XPath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginPassword']/android.widget.EditText[1]"));
                password.Click();
                password.SendKeys("Mercury");
                driver.FindElement(MobileBy.Id("buttonLogin")).Click();
                Thread.Sleep(3000);
                String message = driver.FindElement(MobileBy.Id("android:id/message")).Text;
                if (message.Contains("Would you like to use fingerprint authentication for logging in ?"))
                {
                    driver.FindElement(MobileBy.Id("android:id/button1")).Click();
                    //Perform biometric simulation
                    Console.WriteLine("Biometric simulation result:" + BiometricSimulation("Success", ""));
                }
                driver.FindElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"LAPTOPS\")")).Click();
                driver.FindElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"HP CHROMEBOOK 14 G1(ENERGY STAR)\")")).Click();
                int requiredSwipes = driver.FindElements(MobileBy.XPath("//*[@resource-id='com.Advantage.aShopping:id/linearLayoutImagesLocation']/android.widget.FrameLayout")).Count;
                WebElement imageList = driver.FindElement(MobileBy.Id("viewPagerImagesOfProducts"));
                for (int i = 1; i <= requiredSwipes; i++)
                {                                        
                    swipeElement(imageList.Location, imageList.Size, "up", 1);
                }
                //load the photo before opening the camera                
                Console.WriteLine("Photo simulation result:" + CameraSimulation(SimulationResources.cat, ImageFormat.Png, contentType: "image", fileName: "cat.png", action: "camera"));
                imageList.Click();
                //The steps below are performed on the device's camera and correspond to a device with Android 12.
                //These steps may change depending on the device model/brand since the camera app might be different.
                driver.FindElement(MobileBy.AccessibilityId("Take photo")).Click();
                driver.FindElement(MobileBy.AccessibilityId("Done")).Click();

                //Barcode simulation sample usage:
                //Console.WriteLine(CameraSimulation(SimulationResources.QRCode, ImageFormat.Png, contentType: "image", fileName: "QRCode.png", action: "barcode"));                
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            finally
            {
                if (driver != null)
                    driver.Quit();
            }
        }

        /// <summary>
        /// Biometric authentication simulation enable/update command.
        /// </summary>
        /// <param name="authResult">The simulate result, including Failure, Success, Cancel</param>
        /// <param name="authResultDetails">
        /// The simulate reason when Failure/Cancel
        /// Failure: NotRecognized, Lockout, FingerIncomplete(Android only), SensorDirty(Android only), NoFingerprintRegistered(iOS only)
        /// Cancel: System, User
        /// </param>
        /// <returns>The result of ExecuteScript</returns>
        public static string BiometricSimulation(string authResult, string authResultDetails = "")
        {
            Dictionary<string, object> sensorSimulationMap = new Dictionary<string, object>();
            Dictionary<string, string> simulationData = new Dictionary<string, string>
            {
                { "authResult", authResult },
                { "authType", "Fingerprint" },
                { "authResultDetails", authResultDetails }
            };
            sensorSimulationMap.Add("simulationData", simulationData);
            sensorSimulationMap.Add("action", "authentication");
            sensorSimulationMap.Add("simulateCount", "0");            

            string simulationResult = JsonConvert.SerializeObject(driver.ExecuteScript("mc:sensorSimulation", sensorSimulationMap));
            return JToken.Parse(simulationResult)["message"].ToString();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="picture"></param>
        /// <param name="format"></param>
        /// <param name="contentType">image</param>
        /// <param name="fileName"></param>
        /// <param name="action">Action: barcode, camera</param>
        /// <returns></returns>
        public static string CameraSimulation(Bitmap picture, ImageFormat format, string contentType, string fileName, string action)
        {
            MemoryStream ms = new MemoryStream();
            picture.Save(ms, format);
            Byte[] bytes = ms.ToArray();
            string encodeString = Convert.ToBase64String(bytes);

            Dictionary<string, string> sensorSimulationMap = new Dictionary<string, string>
            {
                { "uploadMedia", encodeString },
                { "contentType", contentType },
                { "fileName", fileName },
                { "action", action }
            };

            string simulationResult = JsonConvert.SerializeObject(driver.ExecuteScript("mc:sensorSimulation", sensorSimulationMap));
            return JToken.Parse(simulationResult)["message"].ToString();
        }


        /// <summary>
        /// Performs swipe on element
        /// </summary>
        /// <param name="elementId">The id of the element to be swiped</param>
        /// <param name="direction">Swipe direction. Mandatory value. Acceptable values are: up, down, left and right (case insensitive)</param>
        /// <param name="percent">The size of the swipe as a percentage of the swipe area size. Valid values must be float numbers in range 0..1, where 1.0 is 100%</param>

        private static void swipeElement(Point location, Size size, String direction, double percent)
        {            
            Dictionary<string, object> swipeMap = new Dictionary<string, object>
            {
                { "left", 0},
                { "top", 0},
                { "width", size.Width },
                { "height", size.Height + location.Y - 30},
                { "direction", direction },
                { "percent", percent }
            };
            
            driver.ExecuteScript("mobile: swipeGesture", swipeMap);
        }

    }
}