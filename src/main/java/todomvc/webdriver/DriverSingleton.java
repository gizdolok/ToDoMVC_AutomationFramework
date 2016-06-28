package todomvc.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.List;


/**
 * Created by kolod on 26/06/2016.
 */
public class DriverSingleton implements Config {

    private static WebDriver driver;

    public DriverSingleton() {
        driver = getDriver();
    }

    public static WebDriver getDriver()  {

            if(driver == null) {
                if(Config.browser != null && Config.browser.equalsIgnoreCase("chrome")){

                    URL file = System.getProperty("os.name").contains("Windows") ?
                            DriverSingleton.class.getClassLoader().getResource("chromedriver.exe") :
                            DriverSingleton.class.getClassLoader().getResource("chromedriver");

                    System.setProperty("webdriver.chrome.driver",  file.getPath());

                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                } else {
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                }
            }
        return driver;
    }

    public static void setDriver(RemoteWebDriver driver) {
        DriverSingleton.driver = driver;
    }

    public static void closeAndResetDriver(){
        driver.close();
        driver.quit();
        setDriver(null);
    }

    public static String getBaseAddress(){
                return todomvcReactURL;
    }

    public static void visit(String url) {
        driver.get(url);
    }

    public static Boolean finishLocatorPresent(By locator) {
        try{
            return waitForIsDisplayed(locator, 40);
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public static Boolean finishLocatorPresent(By locator, int time) { return waitForIsDisplayed(locator, time); }

    public static Boolean waitForIsDisplayed(By locator, Integer... timeout){
        try{
            waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                    timeout.length > 0 ? timeout[0] : null);
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    private static void waitFor(ExpectedCondition<WebElement> condition, Integer timeout) {
        timeout = timeout != null ? timeout : 20;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(condition);
    }

    public static WebElement find(By locator) {
        try{
            return driver.findElement(locator);
        } catch (Exception e){
            return null;
        }
    }

    public static List<WebElement> findMany(By locator)  {
        return driver.findElements(locator);
    }

    public static void click(By locator) {
            driver.findElement(locator).click();
    }

    public static void type(String inputText, By locator) throws Exception {
        try {
            find(locator).sendKeys(inputText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pressKey(Keys key, By locator) throws Exception {
            find(locator).sendKeys(key);
    }

    public static Boolean isDisplayed(By locator){
        try{
            return find(locator).isDisplayed();
        } catch (java.lang.Exception exception){
            return false;
        }
    }

    public static void WaitInSeconds(int s){
        try {
            Thread.sleep(s*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
