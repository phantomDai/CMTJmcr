package scheduler;

import java.util.concurrent.Semaphore;

public class ThreadInfo {

    public Thread thread;
    public String action;
    private Semaphore semaphore;

    public Semaphore getPausingSemaphore() {
        return semaphore;
    }

    public ThreadInfo(Thread thread,String action){
        this.thread = thread;
        this.action = action;
        semaphore =  new Semaphore(0);

    }

    public ThreadInfo(Thread thread){
        this(thread,null);
    }

    @Override
    public String toString() {
        return "线程："+ thread.getName()+ action;

    }
}
