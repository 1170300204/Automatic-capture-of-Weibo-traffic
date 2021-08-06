package cap;

import tools.CmdRunner;
import tools.FileHelper;
import tools.MyLogger;
import tools.ZipUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Set;

public class MyCapture {

    private final String projectPath = System.getProperty("user.dir");
    private final String tempDataPath = projectPath + "\\src\\connect\\data";
    private String dataPath = projectPath + "\\src\\capdata";//默认的数据保存位置
    private final String capCorePath = projectPath + "\\src\\connect\\";
//    private final String logPath = projectPath + "\\src\\logs\\";
    private final String capCmd = ".\\CAP.exe U H U 1 data 0";
    private final String stopCapCmd = " taskkill /F /IM CAP.exe";
    private MyLogger log;

    public MyCapture(MyLogger log) {
        this.log = log;
    }

    //启动捕获,并将pcap包保存在指定的位置
    public void startCap(String dir){
        if (dir!=null) {
            dataPath = dir;
        }

        System.out.println("[START]");

        new Thread(() -> {
            System.out.println("start cap thread : " + CmdRunner.refFormatNowDate());
            CmdRunner.runCmd(capCorePath,"cmd /c" + capCmd);
            System.out.println("Thread run over : " + CmdRunner.refFormatNowDate());
        }).start();

    }

    //停止捕包,关闭捕包程序
    public void stopCap(){
        CmdRunner.runCmdWait(capCorePath, "cmd /c" + stopCapCmd);
        System.out.println("process done : " + CmdRunner.refFormatNowDate());
    }

    //将捕获到的pcap包进行整理,保存所需的pcap包,删除多余的的pcap包
    public void handlePcaps(String[] keys, String dir){

        Set<String> pcaps = FileHelper.getFileName(dir);
        if (pcaps==null||pcaps.isEmpty()) {
            //System.out.println("No pcap is captured!");
            log.warning("No pcap is captured!");
            return;
        }
        for (String pcap : pcaps) {
            boolean flag = false;
            for (String key:keys) {
                if (pcap.contains(key)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
//                System.out.println("delete " + pcap);
                FileHelper.deleteFile(tempDataPath + "\\" + pcap);
            }
        }
        try {
            log.removeFileLocked();
            String zipName = CmdRunner.refFormatNowDate()+".zip";
            FileOutputStream fos = new FileOutputStream(new File(dataPath + "\\" + zipName));
            ZipUtils.toZip(tempDataPath, fos, true);
            FileHelper.deleteDirectory_(tempDataPath, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

//    public void doJob(long millis, String[] keys) {
//        startCap(null);
//        try {
//            Thread.sleep(millis);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        stopCap();
//        handlePcaps(keys,tempDataPath);
//    }

//    public static void main(String[] args) {
//        String[] keys = {"chrome","non","all"};
//        MyCapture capture = new MyCapture();
//        capture.doJob(5000,keys);
//
//    }

}

//一次执行结果，进程启动和捕包程序启动相差小于0.1s(0.065s)，但仍需考虑捕包程序启动到正式开始抓包的时间差
//[START]
//        start Thread sleep : 2021-03-09-15-22-11.526
//        start cap thread : 2021-03-09-15-22-11.526
//        start cap exe : 2021-03-09-15-22-11.591
//        kill cap exe : 2021-03-09-15-22-14.582
//        [FINISH CAP THREAD]
//        Thread run over
//        program done : 2021-03-09-15-22-14.719
//
//        Process finished with exit code 0
//        第一个数据包的时间为 : 2021-03-09-15-22-12.319
//        最后一个数据包的时间为 : 2021-03-09-15-22-14.586 -1-->584 -2-->580

//一次执行结果，进程启动和捕包程序启动相差小于0.1s(0.064s)，但仍需考虑捕包程序启动到正式开始抓包的时间差
//[START]
//        start Thread sleep : 2021-03-09-15-40-44.497
//        start cap thread : 2021-03-09-15-40-44.497
//        start cap exe : 2021-03-09-15-40-44.561
//        kill cap exe : 2021-03-09-15-40-47.553
//        [FINISH CAP THREAD]
//        Thread run over
//        program done : 2021-03-09-15-40-47.689
//
//        Process finished with exit code 0
//        第一个数据包的时间为 : 2021-03-09-15-40-45.261
//        最后一个数据包的时间为 : 2021-03-09-15-40-47.661  -1-->655 -2-->649 -23-->570 -24-->548
