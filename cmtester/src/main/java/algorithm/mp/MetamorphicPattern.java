package algorithm.mp;


import java.util.List;

/**
 * @author GN
 * @description 蜕变模式的接口
 * @date 2020/9/15
 */
public interface MetamorphicPattern {
    /**
     * 获取原始序列
     * @param path  程序的路径
     * @return  原始轨迹
     */

    public static String READ = "READ";
    public static String WRITE = "WRITE";
    public static String BLANK = " ";

//    List<String> getsourceSeq(String path);

    List<String> followUpSeq(List<String> followSeq);
    List<String> followUpSeqWithoutSort(List<String> sourceSeq);
}
