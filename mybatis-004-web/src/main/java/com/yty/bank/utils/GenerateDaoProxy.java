package com.yty.bank.utils;

import org.apache.ibatis.javassist.CannotCompileException;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * generate DAO implementation dynamically
 * @author yty
 * @version 1.0
 * @since 1.0
 */
public class GenerateDaoProxy {
    private GenerateDaoProxy(){}
    public static Object generate(Class daoInterface, SqlSession sqlSession){
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(daoInterface.getName()+"Proxy");
        CtClass ctInterface = pool.makeClass(daoInterface.getName());
        ctClass.addInterface(ctInterface);
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            try {
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public ");
                methodCode.append(method.getReturnType().getName());
                methodCode.append(" ");
                methodCode.append(method.getName());
                methodCode.append("(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    methodCode.append(parameterTypes[i].getName());
                    methodCode.append(" ");
                    methodCode.append("arg" + i);
                    if (i != parameterTypes.length - 1){
                        methodCode.append(",");
                    }
                }
                methodCode.append(")");
                methodCode.append("{");
                System.out.println(methodCode);
                methodCode.append("org.apache.ibatis.session.SqlSession sqlSession = com.yty.bank.utils.SqlSessionUtil.openSession();");

                String sqlid = daoInterface.getName() + "." + method.getName();
                SqlCommandType sqlCommandType = sqlSession.getConfiguration().getMappedStatement(sqlid).getSqlCommandType();
                if (sqlCommandType == SqlCommandType.INSERT){
                }
                else if (sqlCommandType == SqlCommandType.UPDATE){
                    // int count = sqlSession.update("account.updateByActno", act);
                    methodCode.append("return sqlSession.update(\"" + sqlid + "\", arg0);");
                }
                else if (sqlCommandType == SqlCommandType.DELETE){
                    // int count = sqlSession.update("account.updateByActno", act);
                    methodCode.append("return sqlSession.update(\"" + sqlid + "\", act);");
                }
                else if (sqlCommandType == SqlCommandType.SELECT){
                    // Account account = (Account) sqlSession.selectOne("account.selectByActno", actno);
                    methodCode.append("return ("+method.getReturnType().getName()+")sqlSession.selectOne(\""+sqlid+"\", arg0);");
                }
                methodCode.append("}");
                CtMethod ctMethod = CtMethod.make(methodCode.toString(), ctClass);
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        Object obj = null;
        try {
            Class<?> clazz = ctClass.toClass();
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
