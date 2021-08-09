package test;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import tools.MyLogger;

import java.io.*;
import java.util.*;

public class Myprocess1 implements ExecProcess {
    private static String musername = "username";
    private static String mpassword = "password";
    private WebDriver driver;
    private MyLogger log;

    public Myprocess1(MyLogger log) {
        this.log = log;
    }

    @Override
    public void process(WebDriver driver, WebDriverWait wait) throws Exception {
        this.driver = driver;
        System.out.println("in process");

        Thread.sleep(15000);
        login();
        System.out.println("doSomething");
        try {
            doFunc();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("quit driver with exception");
            driver.quit();
            Thread.sleep(500);
            System.out.println("done");
        }

    }

    /**
     * 登录
     */
    private void login() throws Exception {
        driver.manage().deleteAllCookies();

        //
        String path = "src/cookies/cookies.txt";
        try {
            Map<String, String> cookies = loadCookies(path);
            System.out.printf("加载cookie成功");
            for(Map.Entry<String, String> cookie : cookies.entrySet()) {
                driver.manage().addCookie(new Cookie(cookie.getKey(),cookie.getValue()));
            }
            Thread.sleep(1000);
            driver.navigate().refresh();
//            Thread.sleep(5000);

            int loopNum = 0;
            while (loopNum < 3){
                boolean loginStatus = getLoginStatus();
                if (loginStatus) {
                    break;
                } else {
                    cookies = loadCookies(path);
                    System.out.printf("加载cookie成功");
                    for(Map.Entry<String, String> cookie : cookies.entrySet()) {
                        driver.manage().addCookie(new Cookie(cookie.getKey(),cookie.getValue()));
                    }
                    loopNum++;
                    Thread.sleep(3000);
                }
            }
        } catch (Exception e) {
            System.out.println("加载cookies失败");
        }

        boolean loginStatus = getLoginStatus();
        if (!loginStatus) {
            try {
                System.out.println("SAOMA");
//                driver.findElement(By.id("loginname")).sendKeys(musername); //loginname
//                driver.findElement(By.name("password")).sendKeys(mpassword);
//                driver.findElement(By.className("W_btn_a")).click();
//
//                driver.findElement(By.id("message_sms_login")).click();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter anything:");
                System.out.printf(scanner.nextLine());
//                driver.findElement(By.id("message_confirm")).click();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            loginStatus = getLoginStatus();
            if (loginStatus) {
                try {
                    saveCookies(path);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                System.out.printf("登录失败");
                return;
            }
        }
        System.out.printf("登录成功");

//        driver.findElement(By.id("loginname")).sendKeys(username); //loginname
//        driver.findElement(By.name("password")).sendKeys(password);
//        driver.findElement(By.className("W_btn_a")).click();
//
//        driver.findElement(By.id("message_sms_login")).click();
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Please enter login code : ");
//        String code = scanner.nextLine();
//        System.out.println(code);
//        driver.findElement(By.xpath("//*[@id=\"codeSingle\"]")).sendKeys(code);
//
//        driver.findElement(By.id("message_confirm")).click();
    }

    /**
     * 操作
     */
    private void doFunc() throws Exception {

        System.out.println("点击个人主页");
        driver.findElement(By.xpath("//*[@id=\"skin_cover_s\"]/div/a")).click();
        Thread.sleep(6000);
        //返回浏览页
        WebElement weibotubiao = driver.findElement(By.xpath("//*[@id=\"plc_top\"]/div/div/div[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", weibotubiao);
        Thread.sleep(3000);
        System.out.println("点赞");
        WebElement dianzan = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[1]/div[2]/div/ul/li[4]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dianzan);
        Thread.sleep(3000);

        System.out.println("评论");
        WebElement pinglun = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[8]/div[2]/div/ul/li[3]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pinglun);
        Thread.sleep(500);
        driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[8]/div[3]/div/div/div[2]/div[2]/div[1]/textarea")).sendKeys("。。。。。。" + new Random().nextInt());

        Thread.sleep(1000);
        WebElement pinglunqueding = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[8]/div[3]/div/div/div[2]/div[2]/div[2]/div[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pinglunqueding);
        Thread.sleep(3000);

        System.out.println("转发");
        WebElement zhuanfa = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[1]/div[2]/div/ul/li[2]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", zhuanfa);
        Thread.sleep(800);
        WebElement zhuanfaqueren = driver.findElement(By.xpath("//*[@class=\"W_layer \"]/div[2]/div[3]/div/div[2]/div/div[2]/div/div[1]/div/div[2]/div[1]/a"));
                                                                //*[@id="layer_16281463324501"]/div[2]/div[3]/div/div[2]/div/div[2]/div/div/div/div[2]/div[1]/a
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", zhuanfaqueren);
        Thread.sleep(3000);

//        JavascriptExecutor js = (JavascriptExecutor) driver;
////        js.executeScript("arguments[0].click();", zhuanfa);
////        driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[3]/div[3]/div/ul/li[2]/a")).click();
//
//        System.out.println(driver.findElements(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[1]/div[2]/div/ul/li[2]/a")).size());
//        WebElement webElement = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[1]/div[2]/div/ul/li[2]/a"));//找到这个元素
////        ((JavascriptExecutor) driver).executeScript("window.scrollTo("+webElement.getLocation().getX()+","+(webElement.getLocation().getY()+50)+")");//滚动到当前元素
//        webElement.sendKeys("\\n");
//        webElement.click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//*[@id=\"layer_16281330969561\"]/div[2]/div[3]/div/div[2]/div/div[2]/div/div[1]/div/div[2]/div[1]/a")).click();
//        Thread.sleep(5000);

        System.out.println("收藏");
        WebElement shoucang = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_homefeed\"]/div/div[3]/div[2]/div[2]/div/ul/li[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", shoucang);
        Thread.sleep(500);
        WebElement tianjiashoucang = driver.findElement(By.xpath("//*[@class=\"W_layer \"]/div[2]/div[1]/div[4]/a[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tianjiashoucang);
        Thread.sleep(3000);

        System.out.println("发布文字微博");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_content_publishertop\"]/div/div[2]/textarea")).sendKeys("微博测试" + new Random().nextInt());
        Thread.sleep(800);
        WebElement fabu = driver.findElement(By.xpath("//*[@id=\"v6_pl_content_publishertop\"]/div/div[3]/div[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", fabu);
        Thread.sleep(5000);

        System.out.println("关注他人");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_rightmod_attfeed\"]/div[2]/div[1]/div/div[2]/div[2]/div/div/ul/li/div[2]/div[2]/a")).click();
        Thread.sleep(3000);

        System.out.println("我的收藏");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_leftnav_group\"]/div[2]/div[1]/div[2]/h3/a")).click();
        Thread.sleep(3000);

        System.out.println("我的赞");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_leftnav_group\"]/div[2]/div[1]/div[3]/h3/a")).click();
        Thread.sleep(3000);

        //返回浏览页
        weibotubiao = driver.findElement(By.xpath("//*[@id=\"plc_top\"]/div/div/div[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", weibotubiao);
        Thread.sleep(5000);

        System.out.println("我的关注");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_rightmod_myinfo\"]/div[1]/div/div[2]/ul/li[1]/a")).click();
        Thread.sleep(6000);

        //返回浏览页
        weibotubiao = driver.findElement(By.xpath("//*[@id=\"plc_top\"]/div/div/div[1]/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", weibotubiao);
        Thread.sleep(5000);

        System.out.println("我的粉丝");
        driver.findElement(By.xpath("//*[@id=\"v6_pl_rightmod_myinfo\"]/div[1]/div/div[2]/ul/li[2]/a")).click();
        Thread.sleep(5000);

        System.out.println("DONE");

    }

    /**
     * 加载cookie
     **/
    private Map<String, String> loadCookies(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("***** not found cookies file");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        Map<String, String> cookies = new HashMap<>();
        String content = null;

        while ((content = reader.readLine()) != null) {
            String[] cookie = content.split(";");

            for (String str : cookie) {
                String[] tmp = str.split(":");
                cookies.put(tmp[0], tmp[1]);
            }
        }
        reader.close();
        return cookies;
    }

    /**
     * 保存cookie
     **/
    private void saveCookies(String path) throws Exception {
        Set<Cookie> cookies = driver.manage().getCookies();
        StringBuilder data = new StringBuilder();

        for (Cookie cookie : cookies) {
            data.append(cookie.getName()).append(":").append(cookie.getValue()).append(";");
        }

        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
        writer.write(data.toString());
        writer.flush();
        writer.close();
        System.out.printf("cookie保存成功");
    }

    /**
     * 获得登录状态
     **/
    private boolean getLoginStatus() {
        try {
//            driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/div/div[1]/div/div/div[2]/div/div[1]"));
            driver.findElement(By.xpath("//*[@id=\"skin_cover_s\"]/div/a/img"));
            return true;
        } catch (Exception e) {
            System.out.printf("登录未成功");
        }

        return false;
    }

}
