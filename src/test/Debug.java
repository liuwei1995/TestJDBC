package test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Debug<T> {
	
	public void name() {
		Type[] parameterizedTypes = getParameterizedTypes(this);
		System.out.println(parameterizedTypes);
	}
	
	  public static Type[] getParameterizedTypes(Object object) {
		    Type superclassType = object.getClass().getGenericSuperclass();
		    if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
		        return null;
		    }
		    return ((ParameterizedType)superclassType).getActualTypeArguments();
		}
	  
	public static int line(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null || trace.length == 0)
            return -1; //
        return trace[0].getLineNumber();
    }
	
    public static String fun(Exception e) {
    	
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null)
            return ""; //
        return trace[0].getMethodName();
    }
    public static String name(Exception e) {
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null)
            return ""; //
        return trace[0].getClassName();
	}
}
