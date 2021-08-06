package test;

import cap.MyCapture;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.MyLogger;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WeiboCtrl {

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
        WeiboCtrl test = new WeiboCtrl();
        String dataPath = System.getProperty("user.dir") + "\\src\\connect\\data";
        String logPath = dataPath + "\\logs\\" + "logData";
        MyLogger log = new MyLogger(logPath);
        MyCapture capture = new MyCapture(log);

        test.initialize();
        log.info("start cap");
        capture.startCap(null);
        Thread.sleep(2000);//静息2s，防止TLS秘钥保存不成功，影响后续的解密

        test.get("https://www.weibo.com");
        test.start(new Myprocess1(log));

        capture.stopCap();
        String[] keys = {"chrome", "all"};
        capture.handlePcaps(keys,dataPath);
    }


}
