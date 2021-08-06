package tools;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class PCController {

    private final Robot robot = new Robot();

    public PCController() throws AWTException {
    }

    /**
     * 将鼠标移动到坐标(x,y)处,并延迟millis毫秒
     * @param x 目标位置距离左边框x px
     * @param y 目标位置距离上边框y px
     * @param delayMillis 移动到指定位置后休眠的时间,单位毫秒(ms)
     */
    public void moveTo(int x, int y, int delayMillis) {
        //使用robot.mouseMove时,有可能会因为一些不可知因素无法一次完成
        // 因此一般需要搭配循环,始得鼠标的最终位置符合预期
        int i = 5;
        while (i-- > 0) {
            this.robot.mouseMove(x,y);
        }
        if (delayMillis != 0)
            this.robot.delay(delayMillis);
    }

    /**
     * 模拟单击键盘的某个按键
     * @param keycode 按键的值，可通过KeyEvent获得,如KeyEvent.VK_ENTER
     * @param delayMillis 单击按键后休眠的时间,单位毫秒(ms)
     */
    public void pressSingleKey(int keycode, int delayMillis) {
        this.robot.keyPress(keycode);
        this.robot.keyRelease(keycode);

        if (delayMillis != 0)
            this.robot.delay(delayMillis);
    }

    /**
     * 组合按键,正序按下，反序松开
     * @param keycode 包存按键值的数组,其中的按键值按照需要的顺序排序
     * @param intervalMillis 组合按键时中间间隔的时间,单位毫秒(ms),通常可设置为50ms
     * @param delayMillis 组合按键完成之后休眠的时间,单位毫秒(ms)
     */
    public void pressMultiKey(int[] keycode, int intervalMillis, int delayMillis) {

        //正序按下
        for (int value : keycode) {
            this.robot.keyPress(value);
            this.robot.delay(intervalMillis);
        }
        //反序松开
        for (int i = keycode.length - 1; i >= 0; i--) {
            this.robot.keyRelease(keycode[i]);
            this.robot.delay(intervalMillis);
        }

        if (delayMillis != 0)
            this.robot.delay(delayMillis);

    }

    /**
     * 鼠标单击指定位置
     * @param x 目标位置距离左边框x px
     * @param y 目标位置距离上边框y px
     * @param LR 鼠标完成单击的按键(左键或右键)
     * @param durationMillis 点击持续时间,单位毫秒(ms)
     * @param delayMillis 单击完成后休眠的时间,单位毫秒(ms)
     */
    public void click(int x, int y, int LR, int durationMillis, int delayMillis) {

        this.moveTo(x, y, 0);
        if (LR != InputEvent.BUTTON1_DOWN_MASK && LR != InputEvent.BUTTON3_DOWN_MASK) {
            System.out.println("Click error : Invalid LR type! " + LR);
            return;
        }
        this.robot.mousePress(LR);
        this.robot.delay(durationMillis);
        this.robot.mouseRelease(LR);

        if (delayMillis != 0)
            this.robot.delay(delayMillis);
    }

    /**
     * 鼠标点击拖动
     * @param x1 起始位置距离左边框x1 px
     * @param y1 起始位置距离上边框y1 px
     * @param x2 目标位置距离左边框x2 px
     * @param y2 目标位置距离上边框y2 px
     * @param intervalMillis 单击拖动时中间停顿的时间,单位毫秒(ms),通常可设置为50ms
     * @param delayMillis 拖动完成后休眠的时间,单位毫秒(ms)
     */
    public void drag(int x1, int y1, int x2, int y2, int intervalMillis, int delayMillis) {

        this.moveTo(x1, y1, 0);
        this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        this.robot.delay(intervalMillis);
        this.moveTo(x2, y2, 0);
        this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        if (delayMillis != 0)
            this.robot.delay(delayMillis);
    }

    /**
     * 滚动鼠标滚轮
     * @param wheelAmt 滚动滚轮的刻度数,小于0表示向上滚动，大于0表示向下滚动
     * @param delayMillis 滚动完成后休眠的时间,单位毫秒(ms)
     */
    public void wheel(int wheelAmt, int delayMillis) {

        this.robot.mouseWheel(wheelAmt);

        if (delayMillis != 0)
            this.robot.delay(delayMillis);
    }

    public static void main(String[] args) throws AWTException {
        PCController controller = new PCController();
//        controller.moveTo(600,200,1000);
//        controller.click(650,200, KeyEvent.BUTTON1_DOWN_MASK,100,1000);
//        controller.pressSingleKey(KeyEvent.VK_0,1000);
//        int[] paste = {KeyEvent.VK_CONTROL,KeyEvent.VK_V};
//        controller.pressMultiKey(paste,100,1000);
//        controller.drag(600,300,600,450,500,1000);
//        controller.wheel(5,0);

    }



}
