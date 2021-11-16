package cn.gy.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;

public class ReflectUtil {
    public static <T> T parseProperty(ProceedingJoinPoint pjp,String name,Class<T> tClass){
        T instId = null;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameters = parameterNameDiscoverer.getParameterNames(signature.getMethod());

        //过滤出路径参数在args中的索引
        int index = 0;
        for (String para : parameters) {
            if (para.equals(name)) break;
            index++;
        }
        if (index < parameters.length) {
            Object obj = pjp.getArgs()[index];
            //通过instId判断此实例是否被当前用户拥有
            if (obj.getClass() == tClass) {
                instId = (T)obj;
            }
        }
        return instId;
    }
}
