package instrument;

/**
 * @author GN
 * @description
 * @date 2020/10/19
 */
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class MyClassTransformer4Acc extends ClassVisitor {
    public MyClassTransformer4Acc( ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return cv.visitField(access, name, desc, signature, value);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//        System.out.println("访问方法"+ name);
        MethodVisitor mv =  super.visitMethod(access, name, desc, signature, exceptions);
        mv = new MyMethodTransformer4Acc( mv, access, name, desc);
        return mv;
    }

}
