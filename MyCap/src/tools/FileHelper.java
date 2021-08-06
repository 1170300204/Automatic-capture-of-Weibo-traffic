package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileHelper {

    public static Set<String> getFileName(String path) {
        File f = new File(path);//获取路径  F:\测试目录
        if (!f.exists()) {
//            System.out.println(path + " not exists");//不存在就输出
            return null;
        }
        Set<String> result = new HashSet<>();
        File fa[] = f.listFiles();//用数组接收  F:\笔记总结\C#, F:\笔记总结\if语句.txt
        for (int i = 0; i < fa.length; i++) {//循环遍历
            File fs = fa[i];//获取数组中的第i个
            if (!fs.isDirectory()) {
                result.add(fs.getName());
//                System.out.println(fs.getName());//否则直接输出
            }
        }
        return  result;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     * @author only-dream
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName, true);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     * @author only-dream
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
//                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @param tag
     *            true删除目录和目录下的文件，false保留目录仅删除目录下的文件
     * @return 目录删除成功返回true，否则返回false
     * @author only-dream
     */
    public static boolean deleteDirectory(String dir, boolean tag) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);

        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileHelper.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileHelper.deleteDirectory(files[i].getAbsolutePath(), true);
                if (!flag)
                    break;
            }
        }

        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }

        if (!tag) {
            return true;
        }

        // 删除当前目录
        if (dirFile.delete()) {
//            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件，但保留目录的子目录结构（仅删除文件，但不删除子目录）
     *
     * @param dir
     *            要删除的目录的文件路径
     * @param tag
     *            true删除目录和目录下的文件，false保留目录仅删除目录下的文件
     * @return 目录删除成功返回true，否则返回false
     * @author only-dream
     */
    public static boolean deleteDirectory_(String dir, boolean tag) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);

        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileHelper.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileHelper.deleteDirectory_(files[i].getAbsolutePath(), false);
                if (!flag)
                    break;
            }
        }

        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }

        if (!tag) {
            return true;
        }

        // 删除当前目录
        if (dirFile.delete()) {
//            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 文件名重命名
     *
     * @param path
     *            文件的上一层目录路径
     * @param oldFileName
     *            要修改的文件旧路径
     * @param newFileName
     *            新的文件路径
     * @return 成功返回true,失败返回false
     * @author only-dream
     */
    public static boolean renameFile(String path, String oldFileName, String newFileName) {
        if (!oldFileName.equals(newFileName)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldFileName);
            File newfile = new File(path + "/" + newFileName);
            if (newfile.exists()) {// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newFileName + "已经存在！");
                return false;
            } else {
                oldfile.renameTo(newfile);
//                System.out.println("文件修改成功");
                return true;
            }
        }
        System.out.println("文件名称一致");
        return false;
    }

    /**
     * 移动文件
     *
     * @param filename
     *            文件名
     * @param oldpath
     *            旧文件地址
     * @param newpath
     *            新文件地址
     * @param cover
     *            存在同名文件true:覆盖；false:不覆盖
     *
     */
    public static void changeDirectory(String filename, String oldpath, String newpath, boolean cover) {
        if (!oldpath.equals(newpath)) {
            File oldfile = new File(oldpath + "/" + filename);
            File newfile = new File(newpath + "/" + filename);
            if (newfile.exists()) {// 若在待转移目录下，已经存在待转移文件
                if (cover) {// 覆盖
                    oldfile.renameTo(newfile);
                } else {
                    System.out.println("在新目录下已经存在：" + filename);
                }
            } else {
                oldfile.renameTo(newfile);
            }
        }
    }
    /**
     * 复制文件
     * @param src    文件的源路径
     * @param dest    文件的目标路径
     * @throws IOException
    　　　　支持中文处理，并且可以复制多种类型，比如txt，xml，jpg，doc等多种格式
     */
    public static void copyFile(String src, String dest) throws IOException {
        FileInputStream in = new FileInputStream(src);
        File file = new File(dest);
        if (!file.exists()){//如果没有创建
            file.createNewFile();
        }else{}
        FileOutputStream out = new FileOutputStream(file);
        int c;
        byte buffer[] = new byte[1024];
        while ((c = in.read(buffer)) != -1) {
            for (int i = 0; i < c; i++){
                out.write(buffer[i]);
            }
        }
        in.close();
        out.close();
    }

    public static void main(String[] args) {
        FileHelper.deleteDirectory("D:\\Workspace\\IDEA_workspace\\MyCap\\src\\connect\\data\\test", true);
    }

}
