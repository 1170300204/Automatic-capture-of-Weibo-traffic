package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Justtest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void initialize() {
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");// chromedriver服务地址
        this.driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        this.wait = new WebDriverWait(this.driver, 100);//最大的等待秒数
        this.driver.manage().window().maximize();//将窗口最大化
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void get(String url) {
        if (this.driver == null) {
            System.out.println("WebDriver Not Initialized");
            return;
        }
        this.driver.get(url);
    }

    public static void main(String[] args) throws Exception{
        Justtest justtest = new Justtest();
        justtest.initialize();
        Thread.sleep(2000);
        justtest.get("https://www.baidu.com");
    }

}
