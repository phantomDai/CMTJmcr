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
            }

           //----- 输出 ------//

            return classfileBuffer;

        });
    }
}
