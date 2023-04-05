import unittest
from appium import webdriver
from appium.options.android import UiAutomator2Options
from appium.webdriver.common.appiumby import AppiumBy

class AppiumTest(unittest.TestCase):
    def setUp(self):
        options = UiAutomator2Options().load_capabilities({                              
            "platformName": "android",
            "platformVersion": "13",
            'deviceName': 'Galaxy S21',
            "automationName": "UiAutomator2",                        
            "appPackage": "com.Advantage.aShopping",
            "appActivity": "com.Advantage.aShopping.SplashActivity",            
            # UFT Digital Lab capabilities:
            "uftm:oauthClientId": "oauth2-By6oMfOazCwqH6RE44xK@microfocus.com",
            "uftm:oauthClientSecret": "GDbTsTDf36qpPd03TlP2",
            "uftm:tenantId": "999999999",
            # disable SSL certificate verification if desired
            'verify': False 
        })       
        self.driver = webdriver.Remote('https://uftdl-server/wd/hub', options=options)

    def test_app(self):
        # Implicit wait
        self.driver.implicitly_wait(10)                
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "LAPTOPS Category").click()        
        self.driver.find_element(AppiumBy.XPATH, "//android.widget.RelativeLayout[@content-desc=\"Laptops\"]/android.widget.LinearLayout/android.widget.GridView/android.widget.RelativeLayout[4]/android.widget.TextView[1]").click()
        # Click Product Details
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/linearLayoutProductDetails").click()
        # Click Close button
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/imageViewProductDetailsClose").click()
        # Click Product Quantity
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/linearLayoutProductQuantity").click()
        # Click Add button
        self.driver.find_element(AppiumBy.XPATH, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]").click()
        # Click Apply button
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/textViewApply").click()
        # Click Add to Cart button
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/buttonProductAddToCart").click()
        # Enter Username
        self.driver.find_element(AppiumBy.XPATH, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[3]/android.widget.EditText").send_keys("Username")
        # Enter Password
        self.driver.find_element(AppiumBy.XPATH, "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.RelativeLayout[4]/android.widget.EditText").send_keys("password")
        # Click Login button
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/buttonLogin").click()
        # Verify error message
        error_message = self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/textViewFailed").text
        assert "Incorrect user" in error_message
        # Click back
        self.driver.back()
        # Click back
        self.driver.back()
        # Click menu
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/imageViewMenu").click()
        # Click Home
        self.driver.find_element(AppiumBy.ID, "com.Advantage.aShopping:id/textViewMenuHome").click()

    def tearDown(self):
        if self.driver is not None:
            self.driver.quit()

if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(AppiumTest)
    unittest.TextTestRunner(verbosity=2).run(suite)