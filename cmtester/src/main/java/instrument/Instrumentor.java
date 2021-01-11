package instrument;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Instrumentor {

    public static String EVENT_RECIEVER = "scheduler/Scheduler";
    public static String EVENT_RECIEVER2 = "scheduler/Scheduler4Acc";
    public static String EVENT_RECIEVER3 = "scheduler/Scheduler4Air";
    public static String EVENT_RECIEVER4 = "scheduler/Scheduler4PingPong";
    public static String EVENT_RECIEVER5 = "scheduler/Scheduler4MTSet";
    public static String EVENT_RECIEVER6 = "scheduler/Scheduler4Dekker";
    public static String EVENT_RECIEVER7 = "scheduler/Scheduler4Lamport";
    public static String EVENT_RECIEVER8 = "scheduler/Scheduler4Peterson";
    public static String EVENT_RECIEVER9 = "scheduler/Scheduler4RVExample";
    public static String EVENT_RECIEVER10 = "scheduler/Scheduler4LinkedList";
    public static String EVENT_RECIEVER11 = "scheduler/Scheduler4Lottery";
    public static String EVENT_RECIEVER12 = "scheduler/Scheduler4SunsAccount";
    public static String EVENT_RECIEVER13 = "scheduler/Scheduler4Counter";
    //每测一个程序都需要在这里创建一个新的常量，然后再下面的逻辑中进行配置（根据不同的程序名字调用不同的ClassTransformer）

    /**
     * 拦截字节码
     * @param agentArgs
     * @param inst
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer((loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            if (className.contains("test/critical")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new MyClassTransformer(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (className.contains("test/account")){
                ClassReader classReader4Acc = new ClassReader(classfileBuffer);
                ClassWriter classWriter4Acc = new ClassWriter(classReader4Acc, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new MyClassTransformer4Acc(classWriter4Acc);
                classReader4Acc.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter4Acc.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/airline")){
                ClassReader classReader4Air = new ClassReader(classfileBuffer);
                ClassWriter classWriter4Air = new ClassWriter(classReader4Air, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new MyClassTransformer4Air(classWriter4Air);
                classReader4Air.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter4Air.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/pingpong")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4PingPong(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/mtSet")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4MTSet(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/dekker")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4Dekker(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/lamport")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4Lamport(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/peterson")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4Peterson(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/rvExample")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4RVExample(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/linkedList")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4LinkedList(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/lottery")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4Lottery(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/sunsAccount")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4SunsAccount(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (className.contains("test/counter")){
                ClassReader classReader = new ClassReader(classfileBuffer);
                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
                ClassVisitor myClassTransformer = new ClassTransformer4Counter(classWriter);
                classReader.accept(myClassTransformer,ClassReader.EXPAND_FRAMES);
                classfileBuffer = classWriter.toByteArray();
                File dir = new File("out");
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File classFile = new File(dir, className.replace("/", ".") + ".class");

                try {
                    classFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fos = new FileOutputStream(classFile);
                    fos.write(classfileBuffer);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

           //----- 输出 ------//

            return classfileBuffer;

        });
    }
}
