package com.mycompany.cmtester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.cmtester.ReadLastLine.readLastLine;

/**
 * @author GN
 * @description
 * @date 2020/10/23
 */
public class GetFO {
    public static String[] printFO4Cri(){

        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();



        try { //以缓冲区方式读取文件内容
            File file = new File("F:\\CMTester\\output\\Critical.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);
            String aline;
            while ((aline = bufferreader.readLine()) != null) {
                //按行读取文本，显示在TEXTAREA中
                list1.add(aline);
            }
            filereader.close();
            bufferreader.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        //执行命令行
        String cmdstr = "java -javaagent:E:/project/CMT/CMTJmcr/cmtester/lib/agent.jar test.critical.CriticalTest";

        Runtime runTime = Runtime.getRuntime();
        try {
            runTime.exec(cmdstr, null, new File("E:\\project\\CMT\\CMTJmcr\\cmtester\\target\\classes"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            File file1 = new File("F:\\CMTester\\output\\Critical.txt");
            FileReader filereader1 = new FileReader(file1);
            BufferedReader bufferreader1 = new BufferedReader(filereader1);
            String aline1;
            while ((aline1 = bufferreader1.readLine()) != null) {
                list2.add(aline1);
            }
            filereader1.close();
            bufferreader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String l : list1){
            System.out.println(l);
        }
        for (String ll:list2){
            System.out.println(ll);
        }

        int len = list2.size()-list1.size();
        String[] outputs = new String[len];

        for (int i=0;i<len;i++){
            System.out.println(list2.get(list1.size()+i));
            outputs[i]= list2.get(list1.size()+i);
        }
        return outputs;
    }

    public static String[] printFO4Acc(){

        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();



        try { //以缓冲区方式读取文件内容
            File file = new File("F:\\CMTester\\output\\Account.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);
            String aline;
            while ((aline = bufferreader.readLine()) != null) {
                //按行读取文本，显示在TEXTAREA中
                list1.add(aline);
            }
            filereader.close();
            bufferreader.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        //执行命令行
        String cmdstr = "java -javaagent:E:/project/CMT/CMTJmcr/cmtester/lib/agent.jar test.account.Account2ThreadDandWTest";

        Runtime runTime = Runtime.getRuntime();
        try {
            runTime.exec(cmdstr, null, new File("E:\\project\\CMT\\CMTJmcr\\cmtester\\target\\classes"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            File file1 = new File("F:\\CMTester\\output\\Account.txt");
            FileReader filereader1 = new FileReader(file1);
            BufferedReader bufferreader1 = new BufferedReader(filereader1);
            String aline1;
            while ((aline1 = bufferreader1.readLine()) != null) {
                list2.add(aline1);
            }
            filereader1.close();
            bufferreader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String l : list1){
            System.out.println(l);
        }
        for (String ll:list2){
            System.out.println(ll);
        }

        int len = list2.size()-list1.size();
        String[] outputs = new String[len];

        for (int i=0;i<len;i++){
            System.out.println(list2.get(list1.size()+i));
            outputs[i]= list2.get(list1.size()+i);
        }
        return outputs;
    }

    public static String[] printFO4Air(){

        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();



        try { //以缓冲区方式读取文件内容
            File file = new File("F:\\CMTester\\output\\AirlineTickets.txt");
            FileReader filereader = new FileReader(file);
            BufferedReader bufferreader = new BufferedReader(filereader);
            String aline;
            while ((aline = bufferreader.readLine()) != null) {
                //按行读取文本，显示在TEXTAREA中
                list1.add(aline);
            }
            filereader.close();
            bufferreader.close();

        } catch (IOException e) {
            System.out.println(e);
        }

        //执行命令行
        String cmdstr = "java -javaagent:E:/project/CMT/CMTJmcr/cmtester/lib/agent.jar test.airline.AirlineTicketsTest";

        Runtime runTime = Runtime.getRuntime();
        try {
            runTime.exec(cmdstr, null, new File("E:\\project\\CMT\\CMTJmcr\\cmtester\\target\\classes"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            File file1 = new File("F:\\CMT\\output\\AirlineTickets.txt");
            FileReader filereader1 = new FileReader(file1);
            BufferedReader bufferreader1 = new BufferedReader(filereader1);
            String aline1;
            while ((aline1 = bufferreader1.readLine()) != null) {
                list2.add(aline1);
            }
            filereader1.close();
            bufferreader1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        int len = list2.size()-list1.size();
        String[] outputs = new String[len];

        for (int i=0;i<len;i++){
            System.out.println(list2.get(list1.size()+i));
            outputs[i]= list2.get(list1.size()+i);
        }
        return outputs;
    }
}
