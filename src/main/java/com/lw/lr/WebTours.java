package com.lw.lr;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class WebTours {
    public static void main(String[] args) throws IOException, InterruptedException {
        String httpUrl = "http://127.0.0.1:1080/WebTours/index.htm";
        String userName = "jojo";
        String password = "bean";
        String driverName = "webdriver.chrome.driver";// chorme
        String driverPath = "D:/SoftWare/python3.7.4/chromedriver.exe";// chorme

        // 登录
        WebDriver driver = login(userName, password, httpUrl, driverName, driverPath);

        // 预订航班信息
        bookFlights(driver);

        Thread.sleep(2000);

        // 取消航班
        cancelFlights(driver);

        Thread.sleep(2000);

        // 退出登录
        menu(driver, "SignOff Button");

        // 关闭页面
        driver.quit();

    }

    /**
     * 取消航班
     *
     * @param driver
     */
    public static void cancelFlights(WebDriver driver) {
        menu(driver, "Itinerary Button");
//        driver.findElement(By.xpath("//img[@alt='Itinerary Button']")).click();

        driver.switchTo().defaultContent();
        driver.switchTo().frame("body");
        driver.switchTo().frame("info");

        // 取消所有
        driver.findElement(By.xpath("//input[@name='removeAllFlights']")).click();
    }

    /**
     * 预订航班
     *
     * @param driver
     */
    public static void bookFlights(WebDriver driver) {
        // 切换菜单
        menu(driver, "Search Flights Button");
        // 切换查看页面
        info(driver);

        driver.findElement(By.xpath("//td[contains(text(),'Departure City :')]/following-sibling::td/select")).click();
        driver.findElement(By.xpath("//select[@name='depart']/option[contains(text(),'Los Angeles')]")).click();
        driver.findElement(By.xpath("//input[@name='findFlights']")).click();

        // 选择航空公司及时间
        driver.findElements(By.xpath("//input[@name='outboundFlight']")).get(2).click();

        // 下一步
        driver.findElement(By.xpath("//input[@name='reserveFlights']")).click();

        // 输入 Credit Card
        driver.findElement(By.xpath("//input[@name='creditCard']")).sendKeys((int) ((Math.random() * 9 + 1) * 1000000000) + "");

        // 购买
        driver.findElement(By.xpath("//input[@name='buyFlights']")).click();

    }

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @param httpUrl
     * @param driverName
     * @param driverPath
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static WebDriver login(String userName, String password, String httpUrl, String driverName, String driverPath) throws IOException, InterruptedException {
        System.setProperty(driverName, driverPath);
        WebDriver driver = new ChromeDriver();

        driver.get(httpUrl);

        driver.manage().window().maximize();

        // 层层切换frame
        driver.switchTo().frame(1);
        driver.switchTo().frame(0);

        //获取输入框的name,并输入用户名
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.xpath("//input[@name='username']"));
            }
        }).sendKeys(userName);

        //获取输入框的name，并输入密码
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.xpath("//input[@name='password']"));
            }
        }).sendKeys(password);

        // 提交登录
        driver.findElement(By.name("login")).click();

        return driver;
    }

    /**
     * 切换菜单：查询航班
     *
     * @param driver
     * @param altName
     */
    public static void menu(WebDriver driver, String altName) {
        // 使用driver.switchTo.default_content方法，从左侧frame中返回到frameset页面
        // 如果不调用此行代码，则无法从左侧frame页面中直接进入其他frame页面
        driver.switchTo().defaultContent();
        // 层层切换frame
        driver.switchTo().frame("body");
        driver.switchTo().frame("navbar");
        driver.findElement(By.xpath("//img[@alt='" + altName + "']")).click();
    }

    /**
     * 切换查看页面：查看信息
     *
     * @param driver
     */
    public static void info(WebDriver driver) {
        // 层层切换frame
        driver.switchTo().frame("body");
        driver.switchTo().frame("info");
    }

}
