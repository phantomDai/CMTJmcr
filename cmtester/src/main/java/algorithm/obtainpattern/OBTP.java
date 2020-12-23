package algorithm.obtainpattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GN
 * @description
 * @date 2020/10/27
 */
public abstract class OBTP implements ObtainPattern {
    @Override
    public List<String> getTrace(String path) {
        //获取上一步得到的Trace信息
        File file = null;
        List<String> list = new ArrayList<String>();
        try { //以缓冲区方式读取文件内容

            file = new File(path);
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);

            String aline;
            while ((aline = bufferreader.readLine()) != null) {

                if (aline.length()>1) {
                    list.add(aline);
                }
            }
            filereader.close();
            bufferreader.close();

        } catch (IOException e) {
            System.out.println(e);
        }
        return list;
    }
}
