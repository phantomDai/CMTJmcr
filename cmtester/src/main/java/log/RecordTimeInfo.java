package log;

import constants.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.io.File.separator;
/**
 * 该类主要是记录测试用例在执行过程中消耗的时间，包含两个方法：
 * （1）recordInfo：记录非最后一条数据
 * （2）recordLastInfo：记录最后一条数据
 */
public class RecordTimeInfo {

    /**
     * 创建新的文件
     * @param file
     */
    private static void creatFile(File file){
        try{
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写信息
     * @param file 要写入的文件
     * @param timeInfo 时间信息
     * @param flag true表示是最后一个信息
     */
    private static void writeInfo(File file, String timeInfo, boolean flag){
        try{
            PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
            if (!flag) {
                printWriter.write(timeInfo + ";");
            }else {
                printWriter.write(timeInfo + "\n");
            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 记录非最后一条的数据
     * @param SUTName 待测程序的名字
     * @param timeinfo 时间信息
     * @param flag true表示是最后一个信息
     */
    public static void recordInfo(String SUTName, String timeinfo, boolean flag){
        String path = Constants.LOG_PATH + separator + SUTName;
        File file = new File(path);
        if (!file.exists()){
            creatFile(file);
        }
        writeInfo(file, timeinfo, flag);
    }

}
