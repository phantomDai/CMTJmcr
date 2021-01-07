package scheduler;

import strategy.Strategy;
import strategy.Strategy4Lamport;
import strategy.Strategy4LinkedList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler4LinkedList {
    private static Map<Thread, ThreadInfo> livingThreadInfos ;
    private static Set<ThreadInfo> pausedThreadInfos ;
    private static Set<String> pausedInfo;
    //可重入锁，为了手动控制加锁解锁
    private static ReentrantLock schedulerStateLock ;

    /**
     * 根据具体的测试情况设置count和MPName的值
     */
    private static int count = 0;
    // 这个类初次被调用这个守护进程就开启，并顺序执行
    static {
        //初始化存储线程信息的变量
        initial();
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
                        if (count <= 15) {
                            if (pausedThreadInfos.size() >= 2) {
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        }else if (count > 15){
                            if (pausedThreadInfos.size() != 0){
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

    public static void initial() {
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
        String action = isRead ? "READ" : "WRITE";
        ThreadInfo currentTI;
        if (!Thread.currentThread().getName().equals("main") && name.equals("_next")) {
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
                    //该线程获取0个信号量，即暂停该线程
                    currentTI.getPausingSemaphore().acquire();
//                System.out.println(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 按照指定序列选择线程执行
     * @return
     */
    public static ThreadInfo choose() {
        ThreadInfo choice = null;
        Strategy strategy = new Strategy4LinkedList();
        choice = strategy.choose("MP2", pausedThreadInfos);
        return choice;
    }
}
