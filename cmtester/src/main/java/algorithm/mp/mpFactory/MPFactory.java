package algorithm.mp.mpFactory;

import algorithm.mp.MP;

/**
 * @author GN
 * @description
 * @date 2020/9/16
 */
public class MPFactory {
    public MP productMP(String MPName){
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

        }else if (MPName.equals("MP6")){

            return new MP6();

        }else if (MPName.equals("MP7")){

            return new MP7();

        }else if (MPName.equals("MP8")){

            return new MP8();

        }else if (MPName.equals("MP9")){

            return new MP9();

        }else if (MPName.equals("MP10")){

            return new MP10();
        }else if (MPName.equals("MP11")){

            return new MP11();

        }else if (MPName.equals("MP12")){

            return new MP12();

        }else if (MPName.equals("MP13")){

            return new MP13();

        }else {

            return new MP14();
        }
    }
}
