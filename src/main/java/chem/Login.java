package chem;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

    /**
     * 登录
     *
     * @param userName   用户名
     * @param password   密码
     * @param httpUrl    url
     * @param driverPath webdriver.exe路径
     * @return
     */
    public static WebDriver login(String userName, String password, String httpUrl, String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();

        driver.get(httpUrl);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // 登录
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.linkText("登录"));
            }
        }).click();

        //获取输入框的name,并输入用户名
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.xpath("//input[@name='loginAccount']"));
            }
        }).sendKeys(userName);

        //获取输入框的name，并输入密码
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.name("password"));
            }
        }).sendKeys(password);

        String checkCode = getCheckCode();

        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.name("checkCode"));
            }
        }).sendKeys(checkCode);

        // 提交登录
//        driver.findElement(By.id("_login")).click();
        driver.findElement(By.xpath("//a[@id='_login']")).click();
//        driver.findElement(By.cssSelector("a#_login")).click();
//        driver.findElement(By.cssSelector("#_login")).click();

        return driver;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    private static String getCheckCode() {
        return "111111";
    }

    /**
     * 卖家页面
     *
     * @param driver
     */
    public static void seller(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // 登录
        wait.until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(By.linkText("我是卖家"));
            }
        }).click();
    }

}
