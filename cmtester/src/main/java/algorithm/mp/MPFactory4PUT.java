package algorithm.mp;

import algorithm.mp.*;

/**
 * @author GN
 * @description
 * @date 2020/10/6
 */
public class MPFactory4PUT {
    public MP produceMP(String MPName,String program){
        if (MPName.equals("MP1")){
            return new MP1();
        }else if (MPName.equals("MP2")){
            return new MP2();
        }else if (MPName.equals("MP3")){
            return new MP3();
        }else if (MPName.equals("MP4")){
            return new MP4();
        }else if (MPName.equals("MP5")){
            return new MP5();
        }else if (MPName.equals("MP7")){
            return new MP7();
        }else if (MPName.equals("MP10")){
            return new MP10();
        }else if (MPName.equals("MP12")){
            return new MP12();
        }else if (MPName.equals("MP6")){
            return new MP12();
        }else if (MPName.equals("MP8")){
            return new MP12();
        }else if (MPName.equals("MP9")){
            return new MP12();
        }else {
            return new MP12();
        }


    }
}
