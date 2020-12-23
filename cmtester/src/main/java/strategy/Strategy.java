package strategy;

import algorithm.mp.MP;
import algorithm.mp.MPFactory4PUT;
import algorithm.mp.MetamorphicPattern;
import scheduler.ThreadInfo;

import java.util.List;
import java.util.Set;

/**
 * @author GN
 * @description
 * @date 2020/10/16
 */
public interface Strategy {


    //生成衍生执行轨迹


    //衍生执行轨迹作为指导

    ThreadInfo choose(String MPName, Set<ThreadInfo> pausedThreadInfos);
}
