package test.rvExample;

import log.RecordTimeInfo;

import java.io.File;

public class RVExampleTest {
    public static void main(String[] args) {
        //执行命令行
        RecordTimeInfo.recordInfo("RVExample", "开始记录在MP3(x)指导下衍生测试用例生成和执行的时间:",true);
        for (int j = 0; j < 30; j++) {
            long start = System.currentTimeMillis();
            String cmdstr = "java -javaagent:/Users/phantom/javaDir/CMTJmcr/cmtester/lib/agent.jar test.rvExample.RVExample";
            Runtime runTime = Runtime.getRuntime();
            try {
                runTime.exec(cmdstr, null, new File("/Users/phantom/javaDir/CMTJmcr/cmtester/target/classes"));
            } catch (Exception e) {
                e.printStackTrace();}
            long end = System.currentTimeMillis();
            String timeInfo = "执行MP3(x)的时间:" + String.valueOf(end - start);
            RecordTimeInfo.recordInfo("RVExample", timeInfo, true);
        }
    }
}
