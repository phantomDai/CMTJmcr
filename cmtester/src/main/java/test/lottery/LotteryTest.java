package test.lottery;

import log.RecordTimeInfo;

import java.io.File;

public class LotteryTest {
    public static void main(String[] args) {
        RecordTimeInfo.recordInfo("Lottery", "开始记录在MP4(randomNumber)指导下衍生测试用例生成和执行的时间:",true);
        for (int j = 0; j < 30; j++) {
            long start = System.currentTimeMillis();
            String cmdstr = "java -javaagent:/Users/phantom/javaDir/CMTJmcr/cmtester/lib/agent.jar test.lottery.BuggyProgram \"/Users/phantom/javaDir/CMTJmcr/output/output\" \"little\"";
            Runtime runTime = Runtime.getRuntime();
            try {
                runTime.exec(cmdstr, null, new File("/Users/phantom/javaDir/CMTJmcr/cmtester/target/classes"));
            } catch (Exception e) {
                e.printStackTrace();}
            long end = System.currentTimeMillis();
            String timeInfo = "执行MP4(randomNumber)的时间:" + String.valueOf(end - start);
            RecordTimeInfo.recordInfo("Lottery", timeInfo, true);
        }
    }
}
