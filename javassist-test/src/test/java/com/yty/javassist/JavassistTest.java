package com.yty.javassist;

import com.yty.bank.dao.AccountDao;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class JavassistTest {

    @Test
    public void testGenerateAccountDaoImpl() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.yty.bank.dao.impl.AccountDaoImpl");
        CtClass ctInterface = pool.makeInterface("com.yty.bank.dao.AccountDao");
        ctClass.addInterface(ctInterface);
        Method[] methods = AccountDao.class.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            CtMethod ctMethod = null;
            try {
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public ");
                methodCode.append(method.getReturnType().getName());
                methodCode.append(" ");
                methodCode.append(method.getName());
                methodCode.append("(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for(int i=0;i<parameterTypes.length;i++){
                    methodCode.append(parameterTypes[i].getName());
                    methodCode.append(" ");
                    methodCode.append("arg" + i);
                    if(i != parameterTypes.length - 1){
                        methodCode.append(",");
                    }


                }
                methodCode.append("){System.out.println(\"111\");");
                String simpleName = method.getReturnType().getSimpleName();
                if (simpleName.equals("void")){
                    
                } else if (simpleName.equals("int")) {
                    methodCode.append("return 1;");
                } else if (simpleName.equals("String")) {
                    methodCode.append("return \"1\";");
                }
                methodCode.append("}");
                System.out.println(methodCode);
                ctMethod = CtMethod.make(methodCode.toString(), ctClass);
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        Class<?> clazz = ctClass.toClass();
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        accountDao.insert("1");
        accountDao.delete();
        accountDao.update("1", 1000.0);
        accountDao.selectByActno("1");
    }

    @Test
    public void testGenerateImpl() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.yty.bank.dao.impl.AccountDaoImpl");
        CtClass ctInterface = pool.makeInterface("com.yty.bank.dao.AccountDao");
        ctClass.addInterface(ctInterface);
        CtMethod ctMethod = CtMethod.make("public void delete(){System.out.println(\"delete\");}", ctClass);
        ctClass.addMethod(ctMethod);
        Class<?> clazz = ctClass.toClass();
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        accountDao.delete();

    }
    @Test
    public void testGenerateFirstClass() throws Exception{
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("com.yty.bank.dao.impl.AccountDaoImpl");

        String methodCode = "public void insert(){System.out.println(\"insert\");}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);
        ctClass.addMethod(ctMethod);
        ctClass.toClass();
        Class<?> aClass = Class.forName("com.yty.bank.dao.impl.AccountDaoImpl");
        Object o = aClass.newInstance();
        Method insertMethod = aClass.getDeclaredMethod("insert");
        insertMethod.invoke(o);

    }
}
