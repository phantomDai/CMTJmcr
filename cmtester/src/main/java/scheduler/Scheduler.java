package scheduler;

import com.mycompany.cmtester.CMTester;
import strategy.Strategy;
import strategy.Strategy4Critical;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler {

    private static Map<Thread, ThreadInfo> livingThreadInfos ;
    private static Set<ThreadInfo> pausedThreadInfos ;
    private static Set<String> pausedInfo;
    //可重入锁，为了手动控制加锁解锁
    private static ReentrantLock schedulerStateLock ;

    private static int count=0;

    // 这个类初次被调用这个守护进程就开启，并顺序执行
    static {
        //声明变量的过程
        initState();
        //守护进程
        Thread thread = new Thread(() -> {
            while (true) {
                //加锁
                schedulerStateLock.lock();
                try {

                    // 需要针对每一个程序定制代码
                    // 变化的逻辑是：
                    //如果活跃线程不为空，而且暂停线程不为空，从队列里选择一个线程释放choose()函数内部逻辑
                    if (!livingThreadInfos.isEmpty() && !pausedThreadInfos.isEmpty()) {
                        //MP3、MP4、MP5
                        if (count<5) {
                            if (pausedThreadInfos.size() > 1) {
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        }else if (count>=5){
                            if (pausedThreadInfos.size()==1){
                                ThreadInfo threadInfo = pausedThreadInfos.iterator().next();
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
        thread.setDaemon(true); //设置守护进程
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

    /**
     * 线程到一个点暂停
     * @param isRead
     * @param methodName
     * @param name
     * @param desc
     */
    public static void beforeEvent(boolean isRead, String methodName, String name, String desc) {
//
        String action = isRead ? "READ" : "WRITE";
//        System.out.println("线程" + Thread.currentThread().getName() + " 将要" + action + "方法" + methodName +"中的变量" + name + ",描述符为 " + desc);
//
        ThreadInfo currentTI;
        if (!Thread.currentThread().getName().equals("main") && name.equals("turn")) {

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
//        System.out.println("=====================当前暂停的线程信息======================");
//        pausedThreadInfos.forEach(System.out::println);

        ThreadInfo choice = null;
        Strategy strategy4Critical = new Strategy4Critical();
        String mp = CMTester.myMP;
//        System.out.println(mp);
        choice = strategy4Critical.choose("MP3", pausedThreadInfos);
//
//        System.out.println("返回的选择是" + choice);
            return choice;

    }

}
