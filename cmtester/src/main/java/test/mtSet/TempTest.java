package test.mtSet;

import log.RecordTimeInfo;

import java.io.File;

public class TempTest {

    public static void main(String[] args) {
        //执行命令行
        RecordTimeInfo.recordInfo("MTSet", "开始记录在MP1指导下衍生测试用例生成和执行的时间:",true);
        for (int j = 0; j < 1; j++) {
            long start = System.currentTimeMillis();
            String cmdstr = "java -javaagent:/Users/phantom/javaDir/CMTJmcr/cmtester/lib/agent.jar test.mtSet.MTSetTest";
            Runtime runTime = Runtime.getRuntime();
            try {
                runTime.exec(cmdstr, null, new File("/Users/phantom/javaDir/CMTJmcr/cmtester/target/classes"));
            } catch (Exception e) {
            e.printStackTrace();}
            long end = System.currentTimeMillis();
            String timeInfo = "执行MP1的时间:" + String.valueOf(end - start);
            RecordTimeInfo.recordInfo("MTSet", timeInfo, true);
        }
    }
}
