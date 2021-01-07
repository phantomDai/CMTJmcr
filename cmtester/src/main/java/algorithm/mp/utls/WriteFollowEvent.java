package algorithm.mp.utls;

import constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WriteFollowEvent {

    public static void writeFollowEvent(List<String> followEvent){
        try{
            PrintWriter printWriter = new PrintWriter(new FileWriter(Constants.FOLLOWEVENT_PATH, true));
            StringBuffer stringBuffer = new StringBuffer(10);
            for (String info : followEvent){
                stringBuffer.append(info + "\n");
            }
            printWriter.write(stringBuffer.toString());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
