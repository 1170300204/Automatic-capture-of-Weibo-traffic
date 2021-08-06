package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CmdRunner {
    public static String runCmdWait(String cmdpath, String cmd) {
        String result="";
        File dir = new File(cmdpath);
        try {
            Process ps = Runtime.getRuntime().exec(cmd, null, dir);
            System.out.println("kill cap exe : " + CmdRunner.refFormatNowDate());
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("GBK")));
            String line = null;
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                result+=line+"\n";
            }

            br.close();
//            System.out.println("[close br ...]");
            ps.waitFor();
//            System.out.println("[wait over ...]\n[FINISH]");

            return result;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("child thread donn");
        return null;
    }

    public static void runCmd(String cmdpath, String cmd) {
        File dir = new File(cmdpath);
        try {
            Process ps = Runtime.getRuntime().exec(cmd, null, dir);

            System.out.println("start cap exe : " + CmdRunner.refFormatNowDate());
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream(), Charset.forName("GBK")));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                System.out.flush();
            }

            br.close();
//            System.out.println("[FINISH CAP THREAD]");
            return ;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println("child thread donn");
    }

    public static String refFormatNowDate(){

        Date nowTime = new Date(System.currentTimeMillis());

        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");

        String retStrFormatNowDate = sdFormatter.format(nowTime);

        return retStrFormatNowDate;

    }

    public static void main(String[] args) {
        System.out.println(CmdRunner.refFormatNowDate());
    }

}
