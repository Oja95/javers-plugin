package org.zeroturnaround;

import org.zeroturnaround.bundled.javassist.CannotCompileException;
import org.zeroturnaround.bundled.javassist.ClassPool;
import org.zeroturnaround.bundled.javassist.CtClass;
import org.zeroturnaround.bundled.javassist.CtMethod;
import org.zeroturnaround.bundled.javassist.expr.ExprEditor;
import org.zeroturnaround.bundled.javassist.expr.MethodCall;
import org.zeroturnaround.javarebel.integration.support.JavassistClassBytecodeProcessor;

public class ReflectionUtilCBP extends JavassistClassBytecodeProcessor {
  @Override
  public void process(ClassPool cp, ClassLoader cl, CtClass ctClass) throws Exception {
    cp.importPackage("java.lang.reflect");

    ctClass.getDeclaredMethod("newInstance").instrument(new ExprEditor() {
      @Override
      public void edit(MethodCall m) throws CannotCompileException {
        if (m.getMethodName().equals("getDeclaredConstructors")) {
          m.replace("" +
              "if (clazz != org.javers.repository.inmemory.InMemoryRepository.class) {" +
              "  $_ = $proceed($$);" +
              "} else {" +
              "  $_ = new Constructor[] { clazz.getDeclaredConstructor((Class[]) null) };" +
              "}"
          );
        }
      }
    });
  }
}
