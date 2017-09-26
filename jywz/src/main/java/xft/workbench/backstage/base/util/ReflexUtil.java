package xft.workbench.backstage.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflexUtil {
	public static String getObjectFieldValue(Object obj) throws Exception{
		Class<?> aclass=obj.getClass();
		Field[] fields=aclass.getDeclaredFields();
		StringBuffer mes=new StringBuffer();
		for(Field field:fields){
			String fieldName=field.getName();
			Method method = aclass.getDeclaredMethod("get"+fieldName.substring(0,1).toUpperCase()+
						fieldName.substring(1));
			Object fobj = method.invoke(obj);
			if(fobj!=null){
				mes.append(fobj.toString()).append("-");
			}
		}
		return mes.toString();
	}
}
