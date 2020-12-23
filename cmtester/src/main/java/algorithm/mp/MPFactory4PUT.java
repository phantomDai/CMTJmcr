package algorithm.mp;

import algorithm.mp.mp4Acc.*;
import algorithm.mp.mp4Air.*;
import algorithm.mp.mp4Cri.*;

/**
 * @author GN
 * @description
 * @date 2020/10/6
 */
public class MPFactory4PUT {
    public MP produceMP(String MPName,String program){
        if (program.equals("Account.java")){
            if (MPName.equals("MP1")){

                return new MP14Acc();

            }else if (MPName.equals("MP2")){

                return new MP24Acc();

            }else if (MPName.equals("MP3")){

                return new MP34Acc();

            }else if (MPName.equals("MP4")){

                return new MP44Acc();

            }else if (MPName.equals("MP5")){

                return new MP54Acc();

            }else if (MPName.equals("MP6")){

                return new MP64Acc();

            }else if (MPName.equals("MP7")){

                return new MP74Acc();

            }else if (MPName.equals("MP8")){

                return new MP84Acc();

            }else if (MPName.equals("MP9")){

                return new MP94Acc();

            }else if (MPName.equals("MP10")){

                return new MP104Acc();
            }else if (MPName.equals("MP11")){

                return new MP114Acc();

            }else if (MPName.equals("MP12")){

                return new MP124Acc();

            }else if (MPName.equals("MP13")){

                return new MP134Acc();

            }else {

                return new MP144Acc();
            }
        }
        else if (program.equals("AirlineTickets.java")){
            if (MPName.equals("MP1")){

                return new MP14Air();

            }else if (MPName.equals("MP2")){

                return new MP24Air();

            }else if (MPName.equals("MP3")){

                return new MP34Air();

            }else if (MPName.equals("MP4")){

                return new MP44Air();

            }else if (MPName.equals("MP5")){

                return new MP54Air();

            }else if (MPName.equals("MP6")){

                return new MP64Air();

            }else if (MPName.equals("MP7")){

                return new MP74Air();

            }else if (MPName.equals("MP8")){

                return new MP84Air();

            }else if (MPName.equals("MP9")){

                return new MP94Air();

            }else if (MPName.equals("MP10")){

                return new MP104Air();
            }else if (MPName.equals("MP11")){

                return new MP114Air();

            }else if (MPName.equals("MP12")){

                return new MP124Air();

            }else if (MPName.equals("MP13")){

                return new MP134Air();

            }else {

                return new MP144Air();
            }
        }
        else if (program.equals("Critical.java")){
            if (MPName.equals("MP1")){

                return new MP14Cri();

            }else if (MPName.equals("MP2")){

                return new MP24Cri();

            }else if (MPName.equals("MP3")){

                return new MP34Cri();

            }else if (MPName.equals("MP4")){

                return new MP44Cri();

            }else if (MPName.equals("MP5")){

                return new MP54Cri();

            }else if (MPName.equals("MP6")){

                return new MP64Cri();

            }else if (MPName.equals("MP7")){

                return new MP74Cri();

            }else if (MPName.equals("MP8")){

                return new MP84Cri();

            }else if (MPName.equals("MP9")){

                return new MP94Cri();

            }else if (MPName.equals("MP10")){

                return new MP104Cri();
            }else if (MPName.equals("MP11")){

                return new MP114Cri();

            }else if (MPName.equals("MP12")){

                return new MP124Cri();

            }else if (MPName.equals("MP13")){

                return new MP134Cri();

            }else {

                return new MP144Cri();
            }
        }
        else {
            return null;
        }

    }
}
