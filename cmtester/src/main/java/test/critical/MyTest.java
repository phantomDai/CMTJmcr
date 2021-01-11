package test.critical;

import log.RecordTimeInfo;

import java.io.File;

public class MyTest {
    public static void main(String[] args) {
        RecordTimeInfo.recordInfo("Critical", "开始记录在MP5(turn)指导下衍生测试用例生成和执行的时间:",true);
        for (int j = 0; j < 30; j++) {
            long start = System.currentTimeMillis();
            String cmdstr = "java -javaagent:/Users/phantom/javaDir/CMTJmcr/cmtester/lib/agent.jar test.counter.Counter";
            Runtime runTime = Runtime.getRuntime();
            try {
                runTime.exec(cmdstr, null, new File("/Users/phantom/javaDir/CMTJmcr/cmtester/target/classes"));
            } catch (Exception e) {
                e.printStackTrace();}
            long end = System.currentTimeMillis();
            String timeInfo = "执行MP5(turn)的时间:" + String.valueOf(end - start);
            RecordTimeInfo.recordInfo("Critical", timeInfo, true);
        }
    }
}
