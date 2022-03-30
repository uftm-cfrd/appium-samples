import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;

public class Simulation {
    private static AndroidDriver driver;
    private static String SERVER = "https://uftm-server:8443";

    public static void main(String[] args) throws MalformedURLException {
        UiAutomator2Options caps = new UiAutomator2Options();
        caps.setCapability("platformName", "Android");
        caps.setCapability("udid", "8BSX1EDTD");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.Advantage.aShopping");
        caps.setCapability("appActivity", "com.Advantage.aShopping.SplashActivity");
        caps.setCapability("uftm:oauthClientId", "oauth2-tGR1nN6ZxLQNMrKH4Hq9@microfocus.com");
        caps.setCapability("uftm:oauthClientSecret", "A3BNk1Tc4gbf7o2jDU5m");
        caps.setCapability("uftm:tenantId", "999999999");

        //Simulations are supported only on packaged apps. The below capability instructs UFTM to install the packaged version of the app. Default value is false
        caps.setCapability("uftm:installPackagedApp", true);

        driver = new AndroidDriver(new URL(SERVER + "/wd/hub"), caps);
        System.out.println("UFTM session was successfully created [Android device]");

        try{
            //Implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.findElement(AppiumBy.id("imageViewMenu")).click();
            driver.findElement(AppiumBy.id("textViewMenuUser")).click();
            WebElement username = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginUserName']/android.widget.EditText[1]"));
            username.click();
            username.sendKeys("Mercury");
            WebElement password = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginPassword']/android.widget.EditText[1]"));
            password.click();
            password.sendKeys("Mercury");
            driver.findElement(AppiumBy.id("buttonLogin")).click();
            Thread.sleep(3000);
            String message = driver.findElement(AppiumBy.id("android:id/message")).getText();
            if(message.contains("Would you like to use fingerprint authentication for logging in ?")){
                driver.findElement(AppiumBy.id("android:id/button1")).click();
                //Perform biometric simulation
                System.out.println("Biometric simulation result:" + biometricSimulation("Success",""));
            }
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"LAPTOPS\")")).click();
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"HP CHROMEBOOK 14 G1(ENERGY STAR)\")")).click();
            int requiredSwipes = driver.findElements(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/linearLayoutImagesLocation']/android.widget.FrameLayout")).size();
            WebElement imageList = driver.findElement(AppiumBy.id("viewPagerImagesOfProducts"));
            for(int i = 1;i <= requiredSwipes;i++){
                swipeElement(((RemoteWebElement) imageList).getId(),"up", 1);
            }
            //load the photo before opening the camera
            System.out.println("Photo simulation result:" + cameraSimulation("image","cat.png","camera"));
            imageList.click();
            //The steps below are performed on the device's camera and correspond to a device with Android 12.
            //These steps may change depending on the device model/brand since the camera app might be different.
            driver.findElement(AppiumBy.accessibilityId("Take photo")).click();
            driver.findElement(AppiumBy.accessibilityId("Done")).click();

            //Barcode simulation sample usage:
            //System.out.println(cameraSimulation("image","QRCode.jpg","barcode"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (driver != null) driver.quit();
        }
    }

    /**
     * Biometric authentication simulation enable/update command.
     * @param authResult The simulate result: Failure, Success, or Cancel
     * @param authResultDetails The simulate reason when Failure/Cancel
     *     Failure: NotRecognized, Lockout, FingerIncomplete(Android only), SensorDirty(Android only), NoFingerprintRegistered(iOS only)
     *     Cancel: System, User
     * @return The result of ExecuteScript
     */
    private static String biometricSimulation(String authResult, String authResultDetails) throws InterruptedException {
        HashMap<String, Object> sensorSimulationMap = new HashMap<String, Object>();
        HashMap<String, String> simulationData = new HashMap<String, String>();
        simulationData.put("authResult", authResult);
        simulationData.put("authType", "Fingerprint");
        simulationData.put("authResultDetails", authResultDetails);

        sensorSimulationMap.put("simulationData", simulationData);
        sensorSimulationMap.put("action", "authentication");        

        //Execute the script and convert the result to a JSON string
        String simulationResult = new Gson().toJson(driver.executeScript("mc:sensorSimulation", sensorSimulationMap));
        //Return just the message value from simulationResult
        return new Gson().fromJson(simulationResult, JsonObject.class).get("message").getAsString();
    }

    /**
     *
     * @param contentType Image
     * @param fileName Any file name
     * @param action Action: barcode, camera
     * @return
     */
    private static String cameraSimulation(String contentType, String fileName, String action) throws IOException, URISyntaxException, InterruptedException {
        Path path = Paths.get(Objects.requireNonNull(Resources.getResource("photo/" + fileName)).toURI());
        byte[] bytes = Files.readAllBytes(path);
        String encodedString = Base64.getEncoder().encodeToString(bytes);

        HashMap<String, String> sensorSimulationMap =  new HashMap<>();
        sensorSimulationMap.put("uploadMedia", encodedString);
        sensorSimulationMap.put("contentType", contentType);
        sensorSimulationMap.put("fileName", fileName);
        sensorSimulationMap.put("action", action);

        //Execute the script and convert the result to a JSON string
        String simulationResult = new Gson().toJson(driver.executeScript("mc:sensorSimulation", sensorSimulationMap));
        //Return just the message value from simulationResult
        return new Gson().fromJson(simulationResult, JsonObject.class).get("message").getAsString();
    }

    /**
     *
     * @param elementId The id of the element to be swiped
     * @param direction Swipe direction. Mandatory value. Acceptable values are: up, down, left and right (case insensitive)
     * @param percent The size of the swipe as a percentage of the swipe area size. Valid values must be float numbers in range 0..1, where 1.0 is 100%
     */
    private static void swipeElement(String elementId, String direction, double percent){
        HashMap<String, Object> swipeMap =  new HashMap<>();
        swipeMap.put("elementId", elementId);
        swipeMap.put("direction", direction);
        swipeMap.put("percent", percent);

        driver.executeScript("mobile: swipeGesture", swipeMap);
    }
}
