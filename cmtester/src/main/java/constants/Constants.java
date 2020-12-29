package constants;

import static java.io.File.separator;
/**
 * describe:
 * 该类主要是一些常量，供其它类调用
 * @author phantom
 * @date 2020/12/28
 */
public class Constants {

    // 存放原始测试用例执行轨迹中的读写事件
    public static String SOURCEEVENT_PATH = System.getProperty("user.dir") + separator  +
            separator + "sourceEvent" + separator + "sourceEvent.txt";

    // 存放原始测试用例的执行轨迹
    public static String SOURCETRACE_PATH = System.getProperty("user.dir") + separator +
            separator + "sourceTrace"+ separator + "sourceTrace.txt";

//    public static String LOG_PATH = System.getProperty("user.dir") + separator + "log";

    public static String LOG_PATH = "/Users/phantom/javaDir/CMTJmcr/log/";



    /**
     * 根据待测程序的名字获取共享变量的名字
     * @param SUTName 待测程序的名字
     * @return 待测程序中共享变量的名字
     */
    public static String getSheeredVariable(String SUTName){
        if (SUTName.equals("PingPong")){
            return "pingPongPlayer";
        }else {
            return null;
        }
    }

}
