package edu.tamu.aser.tests.airline;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import junit.framework.Assert;
//import omcr.airline.airline;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;


@RunWith(JUnit4MCRRunner.class)
public class AirlineTicketsTest {

    public static void main(String args[]) throws Exception {
        AirlineTicketsTest airlineTest = new AirlineTicketsTest();

        airlineTest.test2ThreadsNotTooMany();

    }
    
    // @Test
    public void test2ThreadsFullInvarient() throws Exception {
        makeBookings(2);
        testSoldInvarient();
    }

     @Test
    public void test2ThreadsNotTooMany() throws Exception {
        makeBookings(2);
        testSoldInvarient();
    }

    // @Test
    public void test2ThreadsNotTooFew() throws Exception {
        makeBookings(2);
        testSoldInvarient();
    }
    
//     @Test
    public void test3ThreadsFullInvarient() throws Exception {
        makeBookings(3);
        testSoldInvarient();
    }

    // @Test
    public void test3ThreadsNotTooMany() throws Exception {
        makeBookings(3);
        testSoldInvarient();
    }

//    @Test
    public void test3ThreadsNotTooFew() throws Exception {
        makeBookings(3);
        testSoldInvarient();
    }

//     @Test
    public void test5ThreadsFullInvarient() throws Exception {
        makeBookings(5);
        testSoldInvarient();
    }

//     @Test
    public void test5ThreadsNotTooMany() throws Exception {
        makeBookings(5);
        testSoldInvarient();
    }

    //@Test
    public void test5ThreadsNotTooFew() throws Exception {
        makeBookings(5);
        testSoldInvarient();
    }

    private AirlineTickets airline;

    public void makeBookings(int numTickets) throws Exception {
        airline = new AirlineTickets(numTickets);
        airline.makeBookings();
    }


    public void testSoldInvarient() {
        if (airline.numberOfSeatsSold > airline.maximumCapacity) {
            PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos)); //设置新的out
            System.out.println("Too many were sold! Number of tickets sold: " + airline.numberOfSeatsSold + " lager than max: " + airline.maximumCapacity);
            System.setOut(oldPrintStream);

            FileWriter fileWriter2 = null;
            try {

                fileWriter2 = new FileWriter("F:\\CMT\\output\\AirlineTickets.txt",true);
                fileWriter2.write("source output:"+"\n\n");
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
        }else if (airline.numberOfSeatsSold < airline.maximumCapacity) {
            PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos)); //设置新的out
            System.out.println("Too few were sold! Number of tickets sold: " + airline.numberOfSeatsSold + " less than max: " + airline.maximumCapacity);
            System.setOut(oldPrintStream);

            FileWriter fileWriter2 = null;
            try {

                fileWriter2 = new FileWriter("F:\\CMT\\output\\AirlineTickets.txt",true);
                fileWriter2.write("source output:"+"\n\n");
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
        else {
            PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            System.setOut(new PrintStream(bos)); //设置新的out
            System.out.println("Sold out "+ airline.numberOfSeatsSold+"!");
            System.setOut(oldPrintStream);

            FileWriter fileWriter2 = null;
            try {

                fileWriter2 = new FileWriter("F:\\CMT\\output\\AirlineTickets.txt",true);
                fileWriter2.write("source output:"+"\n\n");
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

}
