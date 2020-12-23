package scheduler;

import strategy.Strategy;
import strategy.Strategy4Account;
import strategy.Strategy4Airline;
import strategy.Strategy4Critical;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Scheduler4Acc {

    private static Map<Thread, ThreadInfo> livingThreadInfos ;
    private static Set<ThreadInfo> pausedThreadInfos ;
    private static Set<String> pausedInfo;
    private static Set<ThreadInfo> blockedThreadInfos;
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
                    //Critical程序保证暂停队列有两个线程，Account程序保证暂停队列有4个线程，Airline程序保证暂停队列有n个线程
//
                    if (!livingThreadInfos.isEmpty() && !pausedThreadInfos.isEmpty()) {
                        if (count < 3) {
//                            System.out.println("-----<3--------");
                            if (pausedThreadInfos.size() > 3) {
//                                System.out.println("------>3-----");
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
//                                pausedInfo.remove(threadInfo.toString());
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        } else if (count == 3) {
//                            System.out.println("-----3-------");
                            if (pausedThreadInfos.size() == 3) {
//                                System.out.println("-------3--------");
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
//                                pausedInfo.remove(threadInfo.toString());
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        } else if (count == 4 || count == 5) {
//                            System.out.println("-------4/5------");
                            if (pausedThreadInfos.size() == 2) {
//                                System.out.println("-----2-------");
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
//                                pausedInfo.remove(threadInfo.toString());
                                count++;
                                threadInfo.getPausingSemaphore().release();
                            }
                        } else if (count == 6 || count == 7) {
//                            System.out.println("----6-----");
                            if (pausedThreadInfos.size() == 1) {
//                                System.out.println("------1--------");
                                ThreadInfo threadInfo = choose();
                                pausedThreadInfos.remove(threadInfo);
//                                pausedInfo.remove(threadInfo.toString());
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
        blockedThreadInfos = new HashSet<>();
        schedulerStateLock = new ReentrantLock();
    }

    private static void resetState(){
        livingThreadInfos.clear();
        pausedThreadInfos.clear();
        blockedThreadInfos.clear();
    }

//    private static void informSchedulerOfCurrentThread() {
//        Thread currentThread = Thread.currentThread();
//        livingThreadInfos.put(currentThread,new ThreadInfo(currentThread));
//    }

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
        //如果是共享变量再将其挂起
        //Critical程序共享变量是turn,Account程序是amount,airline程序
        if (!Thread.currentThread().getName().equals("main")
               && name.equals("amount") && !methodName.equals("run")) {
//            System.out.println("没有进来吗");

            try {
                schedulerStateLock.lock();
                Thread currentThread = Thread.currentThread();

                currentTI = new ThreadInfo(currentThread, action);
                livingThreadInfos.put(currentThread, currentTI);
                pausedThreadInfos.add(currentTI);
                pausedInfo.add(currentTI.toString());


            } finally {
//                System.out.println("释放锁");
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
//
//        System.out.println("=====================当前暂停的线程信息======================");
//        pausedThreadInfos.forEach(System.out::println);

        ThreadInfo choice;
//        Strategy4Critical strategy4Critical = new Strategy4Critical();
        Strategy strategy4Account = new Strategy4Account();
//        Strategy4Airline strategy4Airline = new Strategy4Airline();
//        choice = strategy4Critical.choose("MP5", pausedThreadInfos);
        choice=strategy4Account.choose("MP1",pausedThreadInfos);
//

//        System.out.println("返回的选择是" + choice);
        return choice;
    }

}
