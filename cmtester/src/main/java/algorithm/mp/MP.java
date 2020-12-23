package algorithm.mp;


import algorithm.obtainpattern.OBTP1;

import java.util.Arrays;
import java.util.List;

/**
 * @author GN
 * @description
 * @date 2020/9/15
 */
public abstract class MP implements MetamorphicPattern{

    private String MPID;
    private String MPName;

    public void setMPID(String MPID) {
        this.MPID = MPID;
    }

    public String getMPID() {
        return MPID;
    }

    public void setMPName(String MPName) {
        this.MPName = MPName;
    }

    public String getMPName() {
        return MPName;
    }

    //将生成的序列按按顺序重新赋予事件序号
    public static List<String> sortSeq(List<String> followSeq){
        //存储事件序号
        int[] array = new int[followSeq.size()];
        for (int i=0;i<followSeq.size();i++){

            array[i] = Integer.valueOf(followSeq.get(i).split(" ")[0]);
        }
        Arrays.sort(array);
        for (int j=0;j<followSeq.size();j++){

            StringBuilder stringBuilder = new StringBuilder(followSeq.get(j));

            if (followSeq.get(j).split(" ")[0].length()==1){
                stringBuilder.replace(0,1,String.valueOf(array[j]));
            }else {

                stringBuilder.replace(0, 2, String.valueOf(array[j]));
            }

            followSeq.set(j,stringBuilder.toString());
        }

        return followSeq;
    }

    public static List<String> getsourceSeq(String path) {
        OBTP1 obtp = new OBTP1();
        return obtp.getTrace(path);
    }

    @Override
    public List<String> followUpSeq(List<String> followSeq) {
        return sortSeq(followSeq);
    }

    @Override
    public abstract List<String> followUpSeqWithoutSort(List<String> sourceSeq);

//    public List<String> getTrace(String path){
//        //获取上一步得到的Trace信息
//        File file = null;
//        List<String> list = new ArrayList<String>();
//        try { //以缓冲区方式读取文件内容
//
//            file = new File(path);
//            FileReader filereader = new FileReader(file);
//            BufferedReader bufferreader = new BufferedReader(filereader);
//
//            String aline;
//            while ((aline = bufferreader.readLine()) != null) {
//
//                if (aline.length()>1) {
//                    list.add(aline);
//                }
//            }
//            filereader.close();
//            bufferreader.close();
//
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        return list;
//    }
//
//    abstract List<String> followUpSeq(List<String> sourceSeq);
//


//    public MP(String id,String name){
//        setMPID(id);
//        setMPName(name);
//    }
}
