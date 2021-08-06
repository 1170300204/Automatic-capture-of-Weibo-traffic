package cap;

import tools.CmdRunner;

public class testSH {
    public static void main(String[] args) {
        System.out.println("hello world!");
        System.out.println(CmdRunner.runCmdWait("D:\\Workspace\\bat workspace","cmd /c start test.bat"));
//        CmdRunner.runCmd("D:\\Workspace\\bat workspace","cmd /c start test.bat");
        System.out.println("process finish");
    }

}
