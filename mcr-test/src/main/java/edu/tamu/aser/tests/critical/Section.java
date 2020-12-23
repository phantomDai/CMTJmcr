package edu.tamu.aser.tests.critical;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author GN
 * @description
 * @date 2020/10/1
 */
public class Section implements Runnable{
    Critical t;
    int threadNum;

    public Section(Critical t,int threadNum){
        this.t=t;
        this.threadNum=threadNum;
    }
    @Override
    public void run() {
        synchronized (this) {

            if (threadNum == 0) {

                t.turn = 0;
                PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                System.setOut(new PrintStream(bos)); //设置新的out
                System.out.println("In critical section,thread number:" + threadNum);
                System.setOut(oldPrintStream);
                FileWriter fileWriter = null;
                try {

                    fileWriter = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                    fileWriter.write("source output:"+"\n");
                    fileWriter.write(bos.toString());
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileWriter!=null){
                        try{
                            fileWriter.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }

                while (t.turn != 0) {
                    PrintStream oldPrintStream1 = System.out; //将原来的System.out交给printStream 对象保存
                    ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(bos1)); //设置新的out
                    System.err.println("0's turn do not allow 1");
                    System.setOut(oldPrintStream1);
                    FileWriter fileWriter1 = null;
                    try {

                        fileWriter1 = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                        fileWriter1.write(bos1.toString());
                        fileWriter1.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fileWriter1!=null){
                            try{
                                fileWriter1.close();
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                PrintStream oldPrintStream2 = System.out; //将原来的System.out交给printStream 对象保存
                ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                System.setOut(new PrintStream(bos2)); //设置新的out
                System.out.println("Out critical section,thread number:" + threadNum);
                System.setOut(oldPrintStream2);
                FileWriter fileWriter2 = null;
                try {

                    fileWriter2 = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                    fileWriter2.write(bos2.toString());
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
                t.turn = 1;

            } else {
                if (threadNum == 1) {
                    t.turn = 1;
                    PrintStream oldPrintStream = System.out; //将原来的System.out交给printStream 对象保存
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(bos)); //设置新的out
                    System.out.println("In critical section,thread number:" + threadNum);
                    System.setOut(oldPrintStream);
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                        fileWriter.write(bos.toString());
                        fileWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fileWriter!=null){
                            try{
                                fileWriter.close();
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    while (t.turn != 1) {
                        PrintStream oldPrintStream1 = System.out; //将原来的System.out交给printStream 对象保存
                        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                        System.setOut(new PrintStream(bos1)); //设置新的out
                        System.err.println("1's turn do not allow 0");
                        System.setOut(oldPrintStream1);
                        FileWriter fileWriter1 = null;
                        try {

                            fileWriter1 = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                            fileWriter1.write(bos1.toString());
                            fileWriter1.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fileWriter1!=null){
                                try{
                                    fileWriter1.close();
                                } catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    PrintStream oldPrintStream2 = System.out; //将原来的System.out交给printStream 对象保存
                    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(bos2)); //设置新的out
                    System.out.println("Out critical section,thread number:" + threadNum);
                    System.setOut(oldPrintStream2);
                    FileWriter fileWriter2 = null;
                    try {

                        fileWriter2 = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                        fileWriter2.write(bos2.toString());
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
                    t.turn = 0;

                } else {
                    PrintStream oldPrintStream2 = System.out; //将原来的System.out交给printStream 对象保存
                    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                    System.setOut(new PrintStream(bos2)); //设置新的out
                    System.err.println("This program only support two threads!  ");
                    System.setOut(oldPrintStream2);
                    FileWriter fileWriter2 = null;
                    try {

                        fileWriter2 = new FileWriter("G:\\PROJECT_IDEA\\CMT\\CMTJmcr\\output\\Critical.txt",true);
                        fileWriter2.write(bos2.toString());
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

    }
}
