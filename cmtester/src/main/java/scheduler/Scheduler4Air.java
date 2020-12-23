package scheduler;

import com.mycompany.cmtester.CMTester;
import strategy.Strategy;
import strategy.Strategy4Airline;
import strategy.Strategy4Critical;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GN
 * @description
 * @date 2020/10/19
 */
public class Scheduler4Air {

    private static Map<Thread, ThreadInfo> livingThreadInfos ;
    private static Set<ThreadInfo> pausedThreadInfos ;
    private static Set<String> pausedInfo;
    //可重入锁，为了手动控制加锁解锁
    private static ReentrantLock schedulerStateLock ;

    private static int count=0;

    static {
        //声明变量的过程
        initState();
        //守护进程
        Thread thread = new Thread(() -> {
            while (true) {
                //加锁
                schedulerStateLock.lock();
                try {


                    //如果活跃线程不为空，而且暂停线程不为空，从队列里选择一个线程释放choose()函数内部逻辑
                    if (!livingThreadInfos.isEmpty() && !pausedThreadInfos.isEmpty()) {
//                        if (count<9) {
//                            if (pausedThreadInfos.size() ==2) {
//                                ThreadInfo threadInfo = choose();
//                                pausedThreadInfos.remove(threadInfo);
//                                count++;
//                                threadInfo.getPausingSemaphore().release();
//                            }
//                        }
                        //MP1、MP4
//                        if (count<9){
                        //MP2
//                        if (count<10){
                        //MP7、MP10
//                        if (count<11){
                        //MP12
                        if (count<8){
                            if (pausedThreadInfos.size()==2){
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        }

//                        else {    // MP7、MP10
//                            ThreadInfo threadInfo = pausedThreadInfos.iterator().next();
//                            pausedThreadInfos.remove(threadInfo);
//                            count++;
//                            threadInfo.getPausingSemaphore().release();
//                        }
                        else {  //MP1\MP2\MP4\MP12
                            if (pausedThreadInfos.size()==1){
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        }

                    }
                } finally {
                    schedulerStateLock.unlock();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static void initState() {
        livingThreadInfos = new HashMap<>();
        pausedThreadInfos = new HashSet<>();
        pausedInfo = new HashSet<>();
        schedulerStateLock = new ReentrantLock();
    }

    public static void beforeFieldRead(boolean isRead, String methodName, String fieldName, String desc, int lineNumber) {
        beforeEvent(isRead, methodName, fieldName, desc);
    }

    public static void beforeFieldWrite(boolean isRead, String methodName, String fieldName, String desc,int lineNumber) {

        beforeEvent(isRead, methodName, fieldName, desc);
    }

    public static void beforeEvent(boolean isRead, String methodName, String name, String desc) {
//
        String action = isRead ? "READ" : "WRITE";
//        System.out.println("线程" + Thread.currentThread().getName() + " 将要" + action + "方法" + methodName +"中的变量" + name + ",描述符为 " + desc);
//
        ThreadInfo currentTI;
        if (!Thread.currentThread().getName().equals("main")
                && (name.equals("numberOfSeatsSold") || name.equals("stopSales"))
                && methodName.equals("run")) {

            try {
                schedulerStateLock.lock();
                Thread currentThread = Thread.currentThread();

                currentTI = new ThreadInfo(currentThread, action);
                livingThreadInfos.put(currentThread, currentTI);
                pausedThreadInfos.add(currentTI);
                pausedInfo.add(currentTI.toString());

            } finally {
                schedulerStateLock.unlock();

            }

            if (currentTI != null) {
                try {
//                System.out.println(1);
                    currentTI.getPausingSemaphore().acquire();
//                System.out.println(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static ThreadInfo choose() {
        System.out.println("=====================当前暂停的线程信息======================");
        pausedThreadInfos.forEach(System.out::println);

        ThreadInfo choice = null;
        Strategy strategy4Airline = new Strategy4Airline();

        choice = strategy4Airline.choose("MP12", pausedThreadInfos);
//
        System.out.println("返回的选择是" + choice);
        return choice;

    }
}
