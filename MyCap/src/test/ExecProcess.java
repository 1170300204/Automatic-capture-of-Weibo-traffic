package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;

public interface ExecProcess {

    //包含实际进行的操作，根据不同的情况，构造不同的实现类
    public void process(WebDriver driver, WebDriverWait wait) throws Exception;

}
