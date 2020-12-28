package algorithm.mp.mp4pingpong;

import algorithm.mp.MP;
import algorithm.mp.MetamorphicPattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * describe:
 * 在GuggedProgram中的85行代码前面添加逻辑让另一个线程写
 * @author phantom
 * @date 2020/12/25
 */
public class MP14Pingpong extends MP implements MetamorphicPattern {





    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq) {
        List<String> followSeq = new ArrayList<>();

        String t1;
        String t2;

        //临时序列
        List<String> tempList = new ArrayList<>();
        List<String> temp = new ArrayList<>();

        //存储已经添加到followSeq中的事件
        List<String> added = new ArrayList<>();
        List<String> source = new ArrayList<>();

        //存储被使用的两个线程
        HashSet<String> thSet = new HashSet<>();

        //存储轨迹中的线程
        HashSet<String> threadSet = new HashSet<>();



        return null;
    }
}
