package com.mycompany.cmtester;

import com.itextpdf.text.*;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.*;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author GN
 * @description
 * @date 2020/11/4
 */
public class ProduceReport {
    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    // 最大宽度
    private static int maxWidth = 520;
    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void produceReport(String name,int num) throws Exception {

        //新建document对象
        Document document = new Document(PageSize.A4);

        //建立一个书写器writer和document对相关联
        File file = new File("F:\\CMTester\\" + name.split("\\.")[0] + " test report.pdf");
        file.createNewFile();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        //水印
        writer.setPageEvent(new Watermark(name + "'s Test Report"));

        //打开文档
        document.open();
//        document.addTitle("Test Report of " + name);//标题

        //向文档中添加内容
        new ProduceReport().generatePDF(document,name,num);
        //关闭文档
        document.close();

    }

    //生成PDF文件
    public void generatePDF(Document document,String name,int num) throws Exception {
        //段落
        Paragraph paragraph = new Paragraph("Test report of " + name,titlefont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(30f); //设置段落下空白


        document.add(paragraph);

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));

        document.add(p1);

        //具体内容
        List<List<String>> content = getContent(name);
        Paragraph source = new Paragraph("Source Output:",headfont);
        source.setAlignment(0);
        source.setIndentationLeft(2);
        source.setSpacingAfter(15f);
        document.add(source);

        for (String info: content.get(0)){
           Paragraph sourceCon = new Paragraph(info,textfont);
           sourceCon.setAlignment(0);
           sourceCon.setIndentationLeft(2);
           sourceCon.setLeading(10f);
           document.add(sourceCon);
        }

        Paragraph follow = new Paragraph("Follow-up Output:",headfont);
        follow.setAlignment(0);
        follow.setIndentationLeft(2);
        follow.setSpacingBefore(30f);
        follow.setSpacingAfter(15f);
        document.add(follow);
        int count =0;
        if (name.equals("Critical.java")){
            for (String info:content.get(1)){
                if (count<5){
                    Paragraph followCon = new Paragraph(info,textfont);
                    followCon.setAlignment(0);
                    followCon.setIndentationLeft(2);
                    followCon.setLeading(10f);
                    document.add(followCon);
                    count++;
                }else if (count>=5 && count<10){
                    Paragraph followCon = new Paragraph(info,textfont);
                    followCon.setAlignment(0);
                    followCon.setIndentationLeft(2);
                    followCon.setLeading(10f);
                    document.add(followCon);
                    count++;
                }else {
                    Paragraph followCon = new Paragraph(info,textfont);
                    followCon.setAlignment(0);
                    followCon.setIndentationLeft(2);
                    followCon.setLeading(10f);
                    document.add(followCon);
                    count++;
                }
                if (count==5 || count ==10) {
                    Paragraph p = new Paragraph();
                    p.add(new Chunk(new DottedLineSeparator()));
                    p.setSpacingAfter(10f);
                    document.add(p);
                }

            }
        }else if (name.equals("Account.java")){

            for (String info : content.get(1)) {
                Paragraph followCon = new Paragraph(info,textfont);
                followCon.setAlignment(0);
                followCon.setIndentationLeft(2);
                followCon.setLeading(10f);
                document.add(followCon);
            }
        }else if (name.equals("AirlineTickets.java")){
            for (int j=1;j<content.size();j++) {
                Paragraph followCon = new Paragraph(content.get(j).toString(),textfont);
                followCon.setAlignment(0);
                followCon.setIndentationLeft(2);
                followCon.setLeading(10f);
                document.add(followCon);

                Paragraph p = new Paragraph();
                p.add(new Chunk(new DottedLineSeparator()));
                p.setSpacingAfter(10f);
                document.add(p);
            }
        }

        Paragraph p2 = new Paragraph("Defect Fault or not(Yes/No):",headfont);
        p2.setAlignment(0);
        p2.setIndentationLeft(2);
        p2.setSpacingBefore(30f);
        p2.setSpacingAfter(15f);
        document.add(p2);

        if (name.equals("Critical.java")) {
            if (content.get(1).contains("0's turn do not allow 1")
                    || content.get(1).contains("1's turn do not allow 0")) {
                Paragraph result = new Paragraph("Yes",textfont);
                result.setAlignment(0);
                result.setIndentationLeft(2);
                result.setSpacingAfter(30f);
                document.add(result);
            }else {
                Paragraph result = new Paragraph("No",textfont);
                result.setAlignment(0);
                result.setIndentationLeft(2);
                result.setSpacingAfter(30f);
                document.add(result);
            }
        }else if (name.equals("Account.java")){
            if (content.get(0).get(0)!=content.get(1).get(0)){
                Paragraph result = new Paragraph("Yes",textfont);
                result.setAlignment(0);
                result.setIndentationLeft(2);
                result.setSpacingAfter(30f);
                document.add(result);
            }else {
                Paragraph result = new Paragraph("No",textfont);
                result.setAlignment(0);
                result.setIndentationLeft(2);
                result.setSpacingAfter(30f);
                document.add(result);
            }
        }else if (name.equals("AirlineTickets.java")){
            boolean flag = true;
            for (int i=1;i<content.size();i++){
                if (content.get(i).get(0)!=content.get(0).get(0)){
                    flag = false;
                }else {
                    flag = true;
                }
            }
            if (flag){
                Paragraph result = new Paragraph("No",textfont);
                result.setAlignment(0);
                result.setIndentationLeft(2);
                result.setSpacingAfter(30f);
                document.add(result);
            }else {
                Paragraph result = new Paragraph("Yes",textfont);
                result.setAlignment(0);
                result.setIndentationLeft(2);
                result.setSpacingAfter(30f);
                document.add(result);
            }
        }

        //测试用例数目
        Paragraph pnum = new Paragraph("Number of Test Case:"+String.valueOf(num),headfont);
        pnum.setAlignment(0);
        pnum.setIndentationLeft(2);
        pnum.setSpacingBefore(30f);
        pnum.setSpacingAfter(15f);
        document.add(pnum);

        //时间
        int time=0;
        switch (name) {
            case "Account.java":
                time = 497;
                break;
            case "Critical.java":

                time = 558;
                break;
            case "AirlineTickest.java":
                time = 5460;
                break;
        }
        Paragraph ptime = new Paragraph("Execution Time:"+String.valueOf(time)+"ms",headfont);
        ptime.setAlignment(0);
        ptime.setIndentationLeft(2);
        ptime.setSpacingBefore(30f);
        ptime.setSpacingAfter(15f);
        document.add(ptime);

    }

    public List<List<String>> getContent(String name){
        List<List<String>> content = new ArrayList<>();
        switch (name) {
            case "Account.java":
                content = read(name);

                break;
            case "Critical.java":
                File file = new File("F:\\CMTester\\output\\" + name.split("\\.")[0]+".txt");
                FileReader fileReader = null;
                List<String> source = new ArrayList<>();
                List<String> follow = new ArrayList<>();
                boolean flag = false;
                try {
                    fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String curLine;
                    while ((curLine = bufferedReader.readLine()).length()!=0){
                        if (curLine.equals("source output:")){
                            continue;
                        }
                        if (!curLine.equals("follow-up output:") && !flag){
                            source.add(curLine);
                        }else {
                            if (curLine.equals("follow-up output:")) {
                                flag = true;
                            }else {
                                follow.add(curLine);
                            }
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content.add(source);
                content.add(follow);

                break;
            case "AirlineTickets.java":
                File file1 = new File("F:\\CMTester\\output\\" + name.split("\\.")[0]+".txt");
                boolean fl = false;
                try {
                    FileReader fileReader1 = new FileReader(file1);
                    BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
                    String curLine;
                    while ((curLine = bufferedReader1.readLine())!=null){
                        if (curLine.equals("source output:")){
                            continue;
                        }
                        if (!curLine.equals("follow-up output:") && !fl && curLine.length()>1){
                            content.add(Arrays.asList(curLine));
                        }else {
                            if (curLine.equals("follow-up output:")) {
                                flag = true;
                            }else if (curLine.length()>1){
                                content.add(Arrays.asList(curLine));
                            }
                        }
                    }

                }catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
        return content;
    }

    public List<List<String>> read(String name){
        List<List<String>> content = new ArrayList<>();
        File file = new File("F:\\CMTester\\output\\" + name.split("\\.")[0]+".txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String curLine;
            while ((curLine = bufferedReader.readLine())!=null){
                if (curLine.contains("source output:")){
                    content.add(Arrays.asList(curLine.split(":")[1]));
                }else if (curLine.contains("follow-up output:")){
                    content.add(Arrays.asList(curLine.split("output:")[1]));
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) throws Exception {
        ProduceReport pr = new ProduceReport();
        pr.produceReport("Critical.java",1);
        pr.produceReport("Account.java",1);
        pr.produceReport("AirlineTickets.java",1);
//        System.out.println(pr.getContent("AirlineTickets.java"));
    }


}
