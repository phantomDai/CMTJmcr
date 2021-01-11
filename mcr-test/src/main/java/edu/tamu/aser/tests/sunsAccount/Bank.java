package edu.tamu.aser.tests.sunsAccount;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.linkedList.BugTester;
import log.RecordTimeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/*
 * The Bank class is a demo for a multi-threaded system which manages accounts
 * while keeping track of their internal balance. The chance for error is very low
 * although possible for the lack of synchronization.
 */
@RunWith(JUnit4MCRRunner.class)
public class Bank{

	// Total balance as recorded in bank.
	static int Bank_Total = 0;
	
	// all accounts
	static Account[] accounts; 
	
	// random numbers generator
	static Random Bank_random = new Random();
	
	// The number of accounts is randomly chosen from [10,110]
	static int NUM_ACCOUNTS = Math.abs((Bank_random.nextInt(1) + 2));
	
	/*
	 * Method main creates all the accounts from which the Bank accepts requests
	 * for actions. The total sum of the accounts is recorded on each
	 * action execution.
	 */
	public static void main(String args[]){
		
		accounts = new Account[NUM_ACCOUNTS];

		// create all accounts
		for(int i = 0; i< NUM_ACCOUNTS; i++){
			accounts[i] = new Account(i);
		}
	
		System.out.println("Bank system started");

		// start all accounts
		for(int i = 0; i< NUM_ACCOUNTS; i++){
			accounts[i].start();
		}

		// wait for all threads (accounts) to die.
		for(int i = 0; i< NUM_ACCOUNTS; i++){
			if(accounts[i].isAlive()){
//				i = 0;
				// if some are alive, sleep for a while
				try{
					Thread.sleep(2);
				}catch(Exception exception){
				}
			}
		}	

		System.out.println("");
		System.out.println("End of the week.");

		int Total_Balance = 0;
		// sum up all balances.
		for(int i = 0; i< NUM_ACCOUNTS; i++){
			Total_Balance += accounts[i].Balance;
		}	

		// Give report.
		System.out.println("Bank records = "+Bank_Total+", accounts balance = "+Total_Balance+".");
		if(Bank_Total == Total_Balance)
			System.out.println("Records match.");
		else
			System.out.println("ERROR: records don't match !!!");
	}

	/*
	 * The Service method performs the actual action on the account, 
	 * and it also updates the Bank's records. (Bank_Total)
	 */
	public static void Service(int id,int sum){
		accounts[id].Balance += sum;
		Bank_Total += sum;
	}

	@Test
	public void test(){
//		RecordTimeInfo.recordInfo("SunsAccount", "记录原始测试用例生成和执行的时间:",true);
		for (int i = 0; i < 1; i++) {
			long start = System.currentTimeMillis();
			Bank.main(null);
			long end = System.currentTimeMillis();
			String timeInfo = "执行原始测试用例的时间为:" + (end - start);
			if (i != 29){
				RecordTimeInfo.recordInfo("SunsAccount", timeInfo, true);
			}else {
				RecordTimeInfo.recordInfo("SunsAccount", timeInfo, true);
			}
		}

	}
	
}

