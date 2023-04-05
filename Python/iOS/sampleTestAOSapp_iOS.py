import unittest
from appium import webdriver
from appium.options.ios import XCUITestOptions
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import timedelta

class AppiumTest(unittest.TestCase):    
    def setUp(self):
        options = XCUITestOptions().load_capabilities({                              
            "platformName": "iOS",
            "platformVersion": "15.1",
            'deviceName': 'iPhone 13 Mini',                                   
            "bundleId": "com.mf.iShopping",            
            # UFT Digital Lab capabilities:
            "uftm:oauthClientId": "oauth2-By6oMfOazCwqH6RE44xK@microfocus.com",
            "uftm:oauthClientSecret": "GDbTsTDf36qpPd03TlP2",
            "uftm:tenantId": "999999999",
            # disable SSL certificate verification if desired
            'verify': False 
        })       
        self.driver = webdriver.Remote('https://uftdl-server/wd/hub', options=options)

    def test_app(self):
        wait = WebDriverWait(self.driver, timeout=timedelta(seconds=10).total_seconds())
        self.driver.find_element(AppiumBy.XPATH, "//XCUIElementTypeStaticText[@name=\"LAPTOPS\"]").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "HP ENVY x360 - 15t Laptop").click()
        wait.until(EC.presence_of_element_located((AppiumBy.ACCESSIBILITY_ID, "productDetailsButton"))).click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "Close2").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "quantityButtonId").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "Plus").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "APPLY").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "ADD TO CART").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "Ok").click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "userNameLabel").send_keys("Username")
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "passwordLabel").send_keys("password")
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "LOGIN").click()
        login_error_text = wait.until(EC.presence_of_element_located(
            (AppiumBy.ACCESSIBILITY_ID, "Incorrect user name or password.")
        ))
        assert login_error_text.is_displayed() == True
        wait.until(EC.presence_of_element_located((AppiumBy.ACCESSIBILITY_ID, "Menu"))).click()
        self.driver.find_element(AppiumBy.ACCESSIBILITY_ID, "HOME").click()

    def tearDown(self):
        if self.driver is not None:
            self.driver.quit()

if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(AppiumTest)
    unittest.TextTestRunner(verbosity=2).run(suite)