package algorithm.mp.mpFactory;

import algorithm.mp.MP;
import algorithm.mp.MetamorphicPattern;
import algorithm.obtainpattern.OBTP1;

import java.util.List;

/**
 * @author GN
 * @description MP6:依据pattern6生成衍生序列
 * W1(l1)W2(l1)W2(l2)W1(l2)
 * @date 2020/9/15
 */
public class MP6 extends MP implements MetamorphicPattern {
//    @Override
//    public List<String> getsourceSeq(String path) {
//        OBTP1 obtp = new OBTP1();
//        return obtp.getTrace(path);
//    }

    @Override
    public List<String> followUpSeq(List<String> sourceSeq) {
        return null;
    }

    @Override
    public List<String> followUpSeqWithoutSort(List<String> sourceSeq) {
        return null;
    }

    public static void main(String[] args){

        MetamorphicPattern mp6 = new MP6();
        List<String> list = mp6.followUpSeq(MP.getsourceSeq("F:\\gengning\\workplace\\CMT\\trace\\test.txt"));
        System.out.println(list);

    }
}
