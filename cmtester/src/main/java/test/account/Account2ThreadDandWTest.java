package test.account;


import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Account2ThreadDandWTest extends Failable {


    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis());
        Account2ThreadDandWTest accountTest = new Account2ThreadDandWTest();
//
        accountTest.test2ThreadDepositAndWithdrawAndCheckInvariant();
//
    }

//

    public void test2ThreadDepositAndWithdrawAndCheckInvariant() throws Exception {
        performDepositsAndWithdrawalsAndCheckInvariant(2);
    }


    private class DepositThread implements Runnable {

        private final Account account;
        private final double amount;

        public DepositThread(Account account, double amount) {
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            account.depsite(amount);
        }

    }


    private class WithdrawThread implements Runnable {

        private final Account account;
        private final double amount;

        public WithdrawThread(Account account, double amount) {
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            account.withdraw(amount);
        }

    }

    private class TransferThread implements Runnable {
        private final Account src;
        private final Account dst;

        public TransferThread(Account src, Account dst) {
            this.src = src;
            this.dst = dst;
        }

        @Override
        public void run() {
            src.transfer(dst, 100);
        }
    }


    public void performDepositsAndWithdrawalsAndCheckInvariant(int numThreads) throws Exception {

        Account account = new Account("The Account", 100);
//        numThreads /= 2;
        Thread[] withdrawalThreads = new Thread[numThreads];
        Thread[] depositThreads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            depositThreads[i] = new Thread(new DepositThread(account, 100));
            withdrawalThreads[i] = new Thread(new WithdrawThread(account, 100));

        }
        for (int i = 0; i < numThreads; i++) {
            depositThreads[i].start();
            withdrawalThreads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            depositThreads[i].join();
            withdrawalThreads[i].join();
        }
        PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos)); //设置新的out
//
        System.out.println(account.amount);
//
        System.setOut(oldPrintStream);
        FileWriter fileWriter2 = null;
        try {

            fileWriter2 = new FileWriter("F:\\CMTester\\output\\Account.txt",true);
            fileWriter2.write("follow-up output:");
            fileWriter2.write(bos.toString());
            fileWriter2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter2!=null){
                try{
                    fileWriter2.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        System.out.println(System.currentTimeMillis());

    }

}
