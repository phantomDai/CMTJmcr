package edu.tamu.aser.tests.account;


import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;


@RunWith(JUnit4MCRRunner.class)

public class Account2ThreadDandWTest extends Failable {


    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis());
        Account2ThreadDandWTest accountTest = new Account2ThreadDandWTest();
//        accountTest.test2RingTransferAndInvariantCheck();
//        accountTest.test3RingTransferAndInvariantCheck();
//        accountTest.test5RingTransferAndInvariantCheck();
//        accountTest.test2ThreadDepositAndCheckInvariant();
//        accountTest.test3ThreadDepositAndCheckInvariant();
//        accountTest.test5ThreadDepositAndCheckInvariant();
//        accountTest.test2ThreadWithdrawAndCheckInvariant();
//        accountTest.test3ThreadWithdrawAndCheckInvariant();
//        accountTest.test5ThreadWithdrawAndCheckInvariant();
        accountTest.test2ThreadDepositAndWithdrawAndCheckInvariant();
//        accountTest.test3ThreadDepositAndWithdrawAndCheckInvariant();
//        accountTest.test5ThreadDepositAndWithdrawAndCheckInvariant();
    }


    @Test
    public void test2ThreadDepositAndWithdrawAndCheckInvariant() throws Exception {
        performDepositsAndWithdrawalsAndCheckInvariant(2);
    }

    public void performRingTransfersAndCheckInvariant(int numOfThreads) throws Exception {
        ManageAccount.num = numOfThreads;
        ManageAccount[] bank = new ManageAccount[ManageAccount.num];
        String[] accountName = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
        for (int j = 0; j < ManageAccount.num; j++) {
            bank[j] = new ManageAccount(accountName[j], 100);
        }
        for (ManageAccount thread : bank) {
            thread.start();
        }
        for (ManageAccount thread : bank) {
            thread.join();
        }
        // flags which will indicate the kind of the bug
        boolean less = false, more = false;
        for (int i = 0; i < ManageAccount.num; i++) {
            Account account = ManageAccount.accounts[i];
            if (Account.amount < 300) {
                less = true;
            } else if (Account.amount > 300) {
                more = true;
            }
        }
        if (less && more) {
            fail("There is amount with more than 300 and there is amount with less than 300");
        }
        if (!less && more) {
            fail("There is amount with more than 300");
        }
        if (less && !more) {
            fail("There is amount with less than 300");
        }
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

    public void performDepositsAndCheckInvariant(int numThreads) throws Exception {

        Account account = new Account("The Account", 100);
        Thread[] depositThreads = new Thread[numThreads];

        for (int i = 0; i < depositThreads.length; i++) {
            depositThreads[i] = new Thread(new DepositThread(account, 100));
        }
        for (Thread depositThread : depositThreads) {
            depositThread.start();
        }
        for (Thread depositThread : depositThreads) {
            depositThread.join();
        }

        if (Account.amount != 100 * (numThreads + 1)) {
            fail("Multi-threaded deposits caused incorrect account state!");
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

    private void performTransferAndCheckInvariant(int numThreads) throws Exception {
        Account account = new Account("account", 0);
        Thread[] transferThread = new Thread[numThreads];
        Account[] accounts = new Account[numThreads];

        for (int i = 0; i < transferThread.length; i++) {
            accounts[i] = new Account("src", 110);
            transferThread[i] = new Thread(new TransferThread(accounts[i], account));
        }

        for (Thread t : transferThread) {
            t.start();
        }
        for (Thread t : transferThread) {
            t.join();
        }

        for (int i = 0; i < accounts.length; i++)
            if (Account.amount != 10)
                fail("Multi-threaded transfer caused incorrect account state!");

        if (Account.amount != numThreads * 100) {
        	System.out.println(Account.amount);
            fail("Multi-threaded transfer caused incorrect account state!");
        }
    }

    public void performWithdrawalsAndCheckInvariant(int numThreads) throws Exception {

        Account account = new Account("The Account", 100 * (numThreads + 1));
        Thread[] withdrawalThreads = new Thread[numThreads];

        for (int i = 0; i < withdrawalThreads.length; i++) {
            withdrawalThreads[i] = new Thread(new WithdrawThread(account, 100));
        }
        for (Thread withdrawalThread : withdrawalThreads) {
            withdrawalThread.start();
        }
        for (Thread withdrawalThread : withdrawalThreads) {
            withdrawalThread.join();
        }

        if (Account.amount != 100) {
            fail("Multi-threaded withdrawals caused incorrect account state!");
        }

    }

    public void performDepositsAndWithdrawalsAndCheckInvariant(int numThreads) throws Exception {

        Account account = new Account("The Account", 100);
//        numThreads /= 2;
        System.out.println(numThreads);
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
        System.out.println(Account.amount);
//
        System.setOut(oldPrintStream);

        FileWriter fileWriter2 = null;
        try {

            fileWriter2 = new FileWriter("F:\\CMT\\output\\Account.txt",true);
            fileWriter2.write("source output:");
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


    }

}
