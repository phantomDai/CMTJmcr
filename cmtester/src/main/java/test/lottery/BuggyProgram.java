package test.lottery;


import java.io.FileWriter;
import java.io.IOException;

public class BuggyProgram {

  public static final int    LITTLE_CONCURRENCY=   2,
                             AVERAGE_CONCURRENCY=  2,
                             LOT_CONCURRENCY=      2,
                             MAX_DIGITS=           3,
                             INVALID=              -3;
  public static final String PROGRAM_NAME= "BuggyProgram";

  protected static final StringBuffer buffer=       new StringBuffer();
  protected              long         randomNumber= INVALID;
  protected              long[]       history=      null,
                                      generated=    null,
                                      presented=    null;
            static       int          numOfUsers=   AVERAGE_CONCURRENCY;
            static       String       pattern=      "None";

  public BuggyProgram(){
    int    i=       0;
    User[] user=    new User[numOfUsers];

    history=   new long[numOfUsers];
    presented= new long[numOfUsers];
    generated= new long[numOfUsers];

    for (i= 0; i < numOfUsers; ++i){
      history[i]=   INVALID;
      presented[i]= INVALID;
      generated[i]= INVALID;
    }

    for (i= 0; i < numOfUsers; ++i){
      user[i]= new User(i);
    }

    acivateUsers(user);

    for (i= 0; i < numOfUsers; ++i){
      buffer.append("\nUser (" + i + ") generated " + generated[i] + ", " +
                    "presented " + presented[i] + ", and recorded " +
                    history[i]);
    }

    for (i= 0; i < numOfUsers; ++i){
      if ((generated[i] != presented[i]) || (generated[i] != history[i]) ||
          (presented[i] != history[i])) {
        break;
      }
    }

    if (i != numOfUsers){
      pattern= "Weak-Reality";
    }
  }

  public static void main(String args[]){
    int        i=              0;
    String     outputFilename= null;
    FileWriter outputFile=     null;

    if (args.length > 2 || args.length < 1){
      System.out.println("Illegal arguments.");
      System.out.println("Arguments should be:");
      System.out.println("  1. The name of the output file, and");
      System.out.println("  2. Optional: Parameter of concurrency (little, " +
                         "average, lot).\n");
      System.exit(1);
    }

    outputFilename= args[0];

    if (args.length == 2){
      if (args[1].toLowerCase().equals("little")){
        numOfUsers= LITTLE_CONCURRENCY;
      } else {
        if (args[1].toLowerCase().equals("average")){
          numOfUsers = AVERAGE_CONCURRENCY;
        } else {
          if (args[1].toLowerCase().equals("lot")){
            numOfUsers= LOT_CONCURRENCY;
          } else {
            System.out.println("Unrecognized parameter of concurrency.\n" +
                               "Should be: little, average, or lot.\n");
            System.exit(1);
          }
        }
      }
    } else {
      numOfUsers= AVERAGE_CONCURRENCY;
    }

    buffer.append("<" + PROGRAM_NAME + ",");

    BuggyProgram buggyProgram= new BuggyProgram();

    buffer.append(", \n" + pattern + ">\n");

    try {
      outputFile= new FileWriter(outputFilename);

      outputFile.write(buffer.toString());
    } catch (IOException ex){
      System.out.println("File \"" + outputFilename + "\" is possibly "+
                         "corrupted.\n");
      System.exit(1);
    } catch (NullPointerException ex){
      System.out.println("File \"" + outputFilename + "\" is possibly "+
                         "corrupted cannot be accessed.\n");
      System.exit(1);
    } catch (Exception ex){
      System.out.println("File \"" + outputFilename + "\" is possibly "+
                         "corrupted cannot be accessed.\n");
      System.exit(1);
    } finally {
      if (outputFile != null){
        try {
          outputFile.close();
        } catch (IOException ex){
          System.out.println("Could not close the file \"" + outputFilename +
                             "\".\n");
        }
      }
    }
  }

  public void acivateUsers(User[] user){
    for (int i= 0; i < numOfUsers; ++i){
      user[i].start();
    }

    for (int i= 0; i < numOfUsers; ++i){
      try {
        user[i].join();
      } catch (InterruptedException ex){
        System.out.println("interrupted!!!");
      }
    }
  }

  public class User extends Thread{
    int userNumber;

    public User(int userNumber){
      this.userNumber= userNumber;
    }

    public void run(){
      int i= 0;

      while (i != numOfUsers){
        generate();

        for (i= 0; i < numOfUsers; ++i){
          if (history[i] == randomNumber) {
            break;
          }
        }
      }

      present();
      record();
    }

    protected synchronized void generate(){
      generated[userNumber]= randomNumber= (long) (Math.random() *
                                           Math.pow(10, MAX_DIGITS));
    }

    protected synchronized void present(){
      System.out.println("user " + userNumber + " assigned "
                         + (presented[userNumber]= randomNumber) + ".");
    }


    protected synchronized void record(){
      history[userNumber]= randomNumber;
    }
  }

}