package com.mycompany.cmtester;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author GN
 * @description
 * @date 2020/10/16
 */
public class Test {
    public static void main(String[] args){
        String cmdstr = "java -javaagent:E:/project/CMT/CMTJmcr/cmtester/lib/agent.jar test.critical.CriticalTest";

        Runtime runTime = Runtime.getRuntime();
        try{
            Process p = runTime.exec(cmdstr,null,new File("E:\\project\\CMT\\CMTJmcr\\cmtester\\target\\classes"));

            InputStreamReader in = new InputStreamReader(p.getInputStream(), Charset.forName("GBK"));
            BufferedReader inBr = new BufferedReader(in);
            System.out.println("读取执行内容！！！");
            System.out.println(inBr.readLine());
            String lineStr;
            while((lineStr = inBr.readLine()) != null) {

                System.out.println(inBr.readLine());
//                if (p.waitFor() != 0) {
//                    if (p.exitValue() == 1) {//p.exitValue()==0表示正常结束，1：非正常结束  
//                        System.err.println("命令执行失败!");
//                    }
//                }
            }
            inBr.close();
            in.close();

//                            }
//                        }.start();
//                        try{
//
//                            p.waitFor();
//                            p.destroy();
//                        }catch (InterruptedException e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                }.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
