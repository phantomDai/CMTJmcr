package edu.tamu.aser.tests.DCL;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.lottery.BuggyProgram;
import log.RecordTimeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.util.*;

@RunWith(JUnit4MCRRunner.class)
public class TicketsOrderSim {
     static Seat[] seats;
     static TravelAgent[] agents;
     static int seats_num;
     static int agents_num;
     static final int flight_num=74721;
     static final int airline_company_code=8888;
     static boolean bug_accured = false;
     static int bug_count=0;

     static void check_ticket_details(int index){
	 boolean bug_flag = false;
	 if (seats[index].ticket.flight!=flight_num) {
	     System.out.println("Flight number is not initialized on seat  " + index);
	     bug_accured = true;
	     bug_flag = true;
	 }
	 if (seats[index].ticket.airline!=airline_company_code) {
	     System.out.println("Airline company code  is not initialized on seat  " + index);
	     bug_accured = true;
	     bug_flag = true;
	 }
	 if (seats[index].ticket.status==null) {
	     System.out.println("Status is not initialized on seat  " + index);
	     bug_accured = true;
	     bug_flag = true;
	 }
	 else if (seats[index].ticket.status.compareTo("Sold")!=0) {
	     System.out.println("Status is not initialized on seat  " + index);
	     bug_accured = true;
	     bug_flag = true;
	 }
	 if (bug_flag == true) {
	     bug_count++;
	 }

     }


     static class Ticket {
	 String status;
	 int flight;
	 int airline;
         int agent_name;


        Ticket(int name){
              

	     flight = flight_num;
	     airline = airline_company_code;
	     status = new String("Sold");
	     agent_name =name;
	 }
     }

     static class Seat {
	   Ticket ticket;
     }


     static class TravelAgent extends Thread {

	 int name;

	 TravelAgent( int agent_name){
	     name = agent_name;
	 }

	 public void run(){
	     for( int i = 0; i < seats_num; i++) {
		 if (seats[i].ticket==null) {
		     synchronized(seats[i]){
			 if (seats[i].ticket==null) {
			     seats[i].ticket=new Ticket(this.name);
			 }
		     }
		 }
		 else {
		    synchronized(seats[i]){
		     check_ticket_details(i);
		    }

		 }
	     }
	 }
     }
	     

     static void get_input(String [] args){
	 if (args[1].compareTo("user")==0) {
	     if(args.length == 2 )  {
	       System.out.println("When You enter user, You must enter number of agents and tickets ");
	       System.exit(0);
	     }
	     agents_num = Integer.parseInt(args[2]);
	     seats_num = Integer.parseInt(args[3]);
	 }
	 else if (args[1].compareTo("little")==0) {
	     agents_num = 2;
	     seats_num = 3;
	 }
	 else if (args[1].compareTo("average")==0) {
	     agents_num = 20;
	     seats_num = 30;
	 }
	 else if (args[1].compareTo("lot")==0) {
	     agents_num = 1000;
	     seats_num = 1000;
	 }
	 else 
	      System.out.println("You have entered a wrong concurrency parameter.\nThe parameters are little, average, lot.");


     }
     


     public static void main(String[] args){
     
         if(( args.length != 2 ) && ( args.length != 4 )) {
           
           System.out.println("You have not entered enough arguments.");
	   System.exit(0);
         }
         File output = new File(args[0]);
	 try {
     	      FileWriter out = new FileWriter(output);
	      get_input(args);

              agents = new TravelAgent[agents_num];
              seats = new Seat[seats_num];

              // fill seats array
              for( int i = 0; i < seats_num; i++)
                seats[i] = new Seat();

              // fill thread (agents) array
              for( int i = 0; i < agents_num; i++)
                agents[i] = new TravelAgent(i);

              for( int i = 0; i < agents_num; ++i)
                agents[i].start();

              // wait for threads (agents) to finish
              for( int i = 0; i < agents_num; ++i){
                try{
                  agents[i].join();
                }
                catch(InterruptedException e){
                }
              }

     	      if (bug_accured == false) {
     	          out.write("<TicketsOrderSim, All Tickets were sold properly, No Bug Happened>\n");
     	      }
	      else {
		  System.out.print("Bug Happened  "+ bug_count+"  Times");
     	          out.write("<TicketsOrderSim, "+bug_count+" Tickets were used without being initialized, Double Checked Locking - Partial initialization>\n");
	      }
	      out.close();
	 }
	 catch (IOException e) {
             System.out.print("\nIOException Happened.");
             System.exit(0);
         }

         System.out.println("Finished");

    }


    @Test
    public void test(){
     	RecordTimeInfo.recordInfo("DCL", "记录原始测试用例生成和执行的时间:",true);
		for (int i = 0; i < 1; i++) {
			long start = System.currentTimeMillis();
			BuggyProgram.main(new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output","little"});
			long end = System.currentTimeMillis();
			String timeInfo = "执行原始测试用例的时间为:" + (end - start);
			if (i != 29){
				RecordTimeInfo.recordInfo("Lottery", timeInfo, true);
			}else {
				RecordTimeInfo.recordInfo("Lottery", timeInfo, true);
			}
		}
     	TicketsOrderSim.main(new String[]{"/Users/phantom/javaDir/CMTJmcr/output/output","little"});
	}

}


