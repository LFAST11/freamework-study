package com.example.proxy.dynamicproxy.diyproxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * lingfan
 * 2019-06-14 17:37
 */
public class DiyProxy {
    private final static String ln = "\r\n";

    public static Object newProxyInstance(DiyClassLoader loader,
                                          Class<?>[] interfaces,
                                          DiyInvationHandle h)
            throws Exception {

        //动态生成源码
        String code = generateCode(interfaces);

        //写入到文件
        String path = DiyProxy.class.getResource("").getPath();

        File file = new File(path, "$Proxy0.java");
        FileWriter fw = new FileWriter(file);
        fw.write(code);
        fw.flush();
        fw.close();

        //将java文件编译成class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> fileObjects = manager.getJavaFileObjects(file);

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, fileObjects);
        task.call();
        manager.close();

        //将class文件加载到内存
        Class<?> proxyClass = loader.findClass("$Proxy0");
        Constructor<?> constructor = proxyClass.getConstructor(DiyInvationHandle.class);
        file.delete();
        return constructor.newInstance(h);
    }

    private static String generateCode(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();

        sb.append("package com.example.proxy.dynamicproxy.diyproxy;" + ln);
        sb.append("import com.example.proxy.dynamicproxy.jdkproxy.Person;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);

        sb.append("public final class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);

        sb.append("DiyInvationHandle h;" + ln);

        sb.append("public $Proxy0(DiyInvationHandle h){");
        sb.append("this.h = h ;" + ln);
        sb.append("}" + ln);

        for (Method method : interfaces[0].getMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();

            StringBuffer names = new StringBuffer();
            StringBuffer values = new StringBuffer();
            StringBuffer classes = new StringBuffer();

            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameter = parameterTypes[i];
                String type = parameter.getName();
                String paramName = toLowerFirstCase(parameter.getSimpleName()) + i;


                names.append(type + " " + paramName + ",");
                values.append(paramName + ",");
                classes.append(parameter.getName() + ".class " + ",");

                if (i == parameterTypes.length - 1) {
                    names.deleteCharAt(names.length() - 1);
                    values.deleteCharAt(values.length() - 1);
                    classes.deleteCharAt(classes.length() - 1);
                }
            }


            sb.append("public " + method.getReturnType().getName() + " " + method.getName() + " (" + names.toString() + " ){" + ln);
            sb.append("try{" + ln);
            sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + method.getName() + "\",new Class[]{" + classes.toString() + "});" + ln);
            sb.append((hasReturnValue(method.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + values + "})", method.getReturnType()) + ";" + ln);
            sb.append("}catch(Exception e){" + ln);
            sb.append("}catch(Throwable e){" + ln);
            sb.append("throw new UndeclaredThrowableException(e);" + ln);
            sb.append("}");
            sb.append(getReturnEmpty(method.getReturnType()));
            sb.append("}" + ln);
        }
        sb.append("}" + ln);

        return sb.toString();
    }

    private static String getCaseCode(String code, Class<?> returnType) {
        if (mappings.containsKey(returnType)) {
            return "((" + mappings.get(returnType.getName() + ")" + code + ")." + returnType.getSimpleName() + "Values()");
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> returnType) {
        return returnType != void.class;
    }

    private static String getReturnEmpty(Class<?> returnType) {
        if (mappings.containsKey(returnType)) {
            return "return 0;";
        } else if (returnType == void.class) {
            return "";
        } else {
            return "return null;";
        }
    }

    private static Map<Class, Class> mappings = new HashMap();

    private static String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();

        chars[0] += 32;

        return new String(chars);

    }

}
