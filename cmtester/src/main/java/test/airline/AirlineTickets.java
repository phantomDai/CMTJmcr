package test.airline;

public class AirlineTickets implements Runnable {

    public int numberOfSeatsSold = 0;
    public final int maximumCapacity;
    private boolean stopSales = false;
    private final int numOfTickets;
    
    public AirlineTickets(int numOfTickets) {
        /* Issuing 10% more tickets for sale */
        int numOfExtra = numOfTickets / 10;
        numOfExtra = numOfExtra == 0 ? 1 : numOfExtra;
        maximumCapacity = numOfTickets - numOfExtra;
        this.numOfTickets = numOfTickets;
    }

    public void makeBookings() throws Exception {
        //创建numOfTicets个线程
        final Thread[] threadArr = new Thread[numOfTickets];

        //逐个启动线程
        for (int i = 0; i < numOfTickets; i++) {

            threadArr[i] = new Thread(this);

//            if (stopSales) {
////                threadArr[i] = new Thread(this);
//                numberOfSeatsSold--;
//                break;
//
//            }
            /**
             * THE BUG : StopSales is updated by the selling posts ( public
             * void run() ), and by the time it is updated more tickets then
             * are allowed to be are sold by other threads that are still
             * running
             */
            threadArr[i].start(); // "make the sale !!!"
        }

        for (Thread thread : threadArr) {
            if (thread != null) {
                thread.join();
            }
        }

    }

    /**
     * the selling post: making the sale & checking if limit was reached ( and
     * updating "StopSales" ),
     */
    public/* TO RE-INTRODUCE BUG remove the synchronized */void run() {
//        synchronized (this)
//        {
        if (numberOfSeatsSold<=maximumCapacity) {
            numberOfSeatsSold++;                  //making the sale
            if (numberOfSeatsSold >= maximumCapacity) // checking
            {
                if (!stopSales) {
                    stopSales = true; // updating
                }else {
                    numberOfSeatsSold--;
                }
            }
        }
//        }
    }

}
