package test;

import cap.MyCapture;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.MyLogger;
import tools.PCController;

import java.awt.event.KeyEvent;
import java.util.Set;

public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    public void initialize() {
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");// chromedriver服务地址
        this.driver = new ChromeDriver(); // 新建一个WebDriver 的对象，但是new 的是谷歌的驱动
        this.wait = new WebDriverWait(this.driver, 100);//最大的等待秒数
        this.driver.manage().window().maximize();//将窗口最大化
    }

    public void get(String url) {
        if (this.driver == null) {
            System.out.println("WebDriver Not Initialized");
            return;
        }
        this.driver.get(url);
    }

    public void start(ExecProcess eProcess) throws Exception {
        eProcess.process(this.driver, this.wait);
    }




    public static void main(String[] args) throws Exception {
//        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://www.baidu.com");
//        driver.manage().window().maximize();
//        Thread.sleep(2000);
//        driver.findElement(By.linkText("新闻")).click();
        String dataPath = System.getProperty("user.dir") + "\\src\\connect\\data";
        String logPath = dataPath + "\\logs\\" + "logData";
        MyLogger log = new MyLogger(logPath);
        MyCapture capture = new MyCapture(log);
        SeleniumTest test = new SeleniumTest();

        test.initialize();

        log.info("start cap");
        capture.startCap(null);

        log.info("get https://www.baidu.com");
        test.get("https://www.baidu.com");
        Thread.sleep(2000);
        log.info("done");
        test.start((driver, wait) -> {
            String baseHandle = driver.getWindowHandle();
            log.info("click 新闻");
            driver.findElement(By.linkText("新闻")).click();
            log.info("done");
            Set<String> handles = driver.getWindowHandles();
            for (String handle : handles) {
                if (!handle.equals(baseHandle)) {
                    driver.switchTo().window(handle);
                    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("科技")));
                    log.info("click 科技");
                    driver.findElement(By.linkText("科技")).click();
                    Thread.sleep(2000);
                    log.info("done");
                    log.info("close WindowHandle 科技");
                    driver.close();
                    Thread.sleep(500);
                    log.info("done");
                }
            }
            driver.switchTo().window(baseHandle);
            Thread.sleep(1000);
            //最后关闭窗口使用quit()方法,将同时关闭chromedriver.exe
            //如果使用close()方法，会导致chromedriver.exe在后台继续运行,导致内存浪费
            log.info("quit driver");
            driver.quit();
            Thread.sleep(500);
            log.info("done");
        });

        capture.stopCap();
        String[] keys = {"chrome","all"};
        capture.handlePcaps(keys,dataPath);

    }

}



