package xft.workbench.backstage.base.util;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.bson.Document;
import org.json.JSONObject;

import com.google.gson.internal.LinkedTreeMap;
import com.kayak.web.base.sql.SqlResult;
import com.kayak.web.base.sql.SqlRow;


public class ObjectMapUtil {
    /**
     * 返回由对象的属性为key,值为map的value的Map集合
     *
     * @param obj Object
     * @return mapValue Map<String,String>
     * @throws Exception
     */
    public static Map<String, String> getFieldVlaue(Object obj) throws Exception {
        Map<String, String> mapValue = new HashMap<String, String>();
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
            Method methodGet = cls.getDeclaredMethod(strGet);
            Object object = methodGet.invoke(obj);
            String value = object != null ? object.toString() : "";
            mapValue.put(name, value);
        }
        return mapValue;
    }

    public static Map<String, Object> getFieldVlaue2(Object obj) throws Exception {
        Map<String, Object> mapValue = new HashMap<String, Object>();
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
            Method methodGet = null;
            try {
                methodGet = cls.getDeclaredMethod(strGet);
            } catch (Exception e) {
                continue;
            }
            Object object = methodGet.invoke(obj);
            Object value = object != null ? object : "";
            mapValue.put(name, value);
        }
        return mapValue;
    }

    /**
     * @param obj
     * @return
     * @throws Exception
     * @author hupp
     * 描述 : 深度转化对象属性 <br>
     * <p>
     */
    public static Map<String, Object> getDeepFieldVlaue(Object obj) throws Exception {
        Map<String, Object> mapValue = new HashMap<String, Object>();
        if (null != obj) {
            Class<?> cls = obj.getClass();
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (Field field : declaredFields) {
                String type = field.getGenericType().toString();
                String name = field.getName();
                Object result = field.get(obj);
                if (type.contains("xft.workbench.backstage")) {
                    Map<String, Object> inObj = getDeepFieldVlaue(result);
                    mapValue.put(name, inObj);
                } else {
                    if (result == null) {
                        result = "";
                    }
                    if (result.equals(true)) {
                        result = 1;
                    }
                    if (result.equals(false)) {
                        result = 0;
                    }
                    mapValue.put(name, result);
                }
            }
        }
        return mapValue;
    }


    /**
     * 返回由Map的key对属性，value对应值组成的对应
     *
     * @param map Map<String,String>
     * @param cls Class
     * @return obj Object
     * @throws Exception
     */
    public static Object setFieldValue(Map<String, String> map, Class<?> cls) throws Exception {
        Field[] fields = cls.getDeclaredFields();
        Object obj = cls.newInstance();
        for (Field field : fields) {
            Class<?> clsType = field.getType();
            String name = field.getName();
            String strSet = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
            Method methodSet = cls.getDeclaredMethod(strSet, clsType);
            if (map.containsKey(name)) {
                Object value = map.get(name);
                if (value != null && !"".equals(value.toString())) {
                    Object objValue = typeConversion(clsType, value);
                    methodSet.invoke(obj, objValue);
                }

            }
        }
        return obj;
    }

    /**
     * 将Map里面的部分值通过反射设置到已有对象里去
     *
     * @param obj  Object
     * @param data Map<String,String>
     * @return obj Object
     * @throws Exception
     */
    public static Object setObjectFileValue(Object obj, Map<String, Object> data) throws Exception {
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Class<?> clsType = field.getType();
            String name = field.getName();
            String strSet = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());

            Method methodSet = null;
            try {
                methodSet = cls.getDeclaredMethod(strSet, clsType);
            } catch (Exception e) {
                continue;
            }

            if (data.containsKey(name)) {
                Object value = data.get(name);
                if (value != null && !"".equals(value.toString())) {
                    Object objValue = typeConversion(clsType, value);
                    methodSet.invoke(obj, objValue);
                }
            }
        }
        return obj;
    }


    /**
     * 将Map里面的部分值通过反射设置到已有对象里去
     *
     * @param obj  Object
     * @param data Map<String,String>
     * @return obj Object
     * @throws Exception
     */
    public static Object setObjectFileValue(Object obj, Document data) throws Exception {
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Class<?> clsType = field.getType();
            String name = field.getName();
            String strSet = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());

            Method methodSet = null;
            try {
                methodSet = cls.getDeclaredMethod(strSet, clsType);
            } catch (Exception e) {
                continue;
            }

            if (data.containsKey(name)) {
                Object value = data.get(name);
                if (value != null && !"".equals(value.toString())) {
                    Object objValue = typeConversion(clsType, value);
                    methodSet.invoke(obj, objValue);
                }
            }
        }
        return obj;
    }


    /**
     * 把对象的值用Map对应装起来
     *
     * @param map Map<String,String>
     * @param obj Object
     * @return 与对象属性对应的Map Map<String,String>
     */
    public static Map<String, String> compareMap(Map<String, String> map, Object obj) {
        Map<String, String> mapValue = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (map.containsKey(name)) {
                mapValue.put(name, map.get(name));
            }
        }
        return mapValue;
    }

    /**
     * 把临时对象的值复制到持久化对象上
     *
     * @param oldObject Object 持久化对象
     * @param newObject Object 临时对象
     * @return 持久化对象
     * @throws Exception
     */
    public static Object mergedObject(Object oldObject, Object newObject) throws Exception {
        Class<?> cls = newObject.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Class<?> clsType = field.getType();
            String name = field.getName();
            String method = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
            String strGet = "get" + method;
            Method methodGet = cls.getDeclaredMethod(strGet);
            Object object = methodGet.invoke(newObject);
            if (object != null) {
                String strSet = "set" + method;
                Method methodSet = cls.getDeclaredMethod(strSet, clsType);
                Object objValue = typeConversion(clsType, object.toString());
                methodSet.invoke(oldObject, objValue);
            }
        }
        return oldObject;
    }

    /**
     * 将sql语句返回结果信息，封装成特定对象。
     *
     * @param sr
     * @param obj
     * @return
     */
    public static Object sqlResultToObject(SqlResult sr, Object obj) throws Exception {
        Class<?> aclass = obj.getClass();
        Field[] fields = aclass.getDeclaredFields();
        sr.resetCursor();
        if (sr.next()) {
            for (Field field : fields) {
                Class<?> clsType = field.getType();
                String fieldName = field.getName();
                Method method = aclass.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() +
                        fieldName.substring(1, fieldName.length()), clsType);
                if (sr.getObject(fieldName) != null) {
                    Object value = sr.getObject(fieldName);
                    if (value != null && !"".equals(value.toString())) {
                        Object objValue = typeConversion(clsType, value);
                        method.invoke(obj, objValue);
                    }
                }
            }
            return obj;
        } else {
            return null;
        }
    }

    /**
     * 将sql语句返回结果信息，封装成特定对象。
     */
    public static void sqlResultToObject(SqlRow row, Object obj) throws Exception {
        mapToObject(row, obj);
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> targetClass) {
        T obj;
        try {
            obj = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        mapToObject(map, obj);
        return obj;
    }


    public static <T> void mapToObject(Map<String, Object> map, T obj) {
        Class<?> targetClass = obj.getClass();
        Field[] fields = targetClass.getDeclaredFields();

        for (Field field : fields) {
            Class<?> clsType = field.getType();
            String fieldName = field.getName();
            if (Modifier.isStatic(field.getModifiers()) ||
                    Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            Method setter;
            try {
                setter = targetClass.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1, fieldName.length()), clsType);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                continue;
            }
            if (map.get(fieldName) != null) {
                Object value = map.get(fieldName);
                if (value != null && !"".equals(value.toString())) {
                    Object objValue = typeConversion(clsType, value);
                    try {
                        setter.invoke(obj, objValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("无法设置" + fieldName + " 为" + value);
                    }
                }
            }
        }
    }


    public static <T> void objectToMap(T obj, Map<String, Object> map) {
        Class<?> targetClass = obj.getClass();
        Field[] fields = targetClass.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (Modifier.isStatic(field.getModifiers()) ||
                    Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            Object objValue = null;
            try {
                objValue = PropertyUtils.getProperty(obj, fieldName);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("无法获取" + fieldName + " 的值");
            }
            map.put(fieldName, objValue);
        }
    }


    /**
     * 将sql语句返回结果集信息，封装成特定对象集合。
     */
    public static <T> List<T> sqlResultToObject(SqlResult sr, Class<T> aclass) throws Exception {

        List<T> objects = new ArrayList<>();
        Field[] fields = aclass.getDeclaredFields();
        sr.resetCursor();
        while (sr.next()) {
            T obj = (T) aclass.newInstance();
            for (Field field : fields) {
                Class<?> clsType = field.getType();
                String fieldName = field.getName();
                Method method;
                try {
                    if (Modifier.isStatic(field.getModifiers()) ||
                            Modifier.isFinal(field.getModifiers())) {
                        continue;
                    }
                    method = aclass.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() +
                            fieldName.substring(1, fieldName.length()), clsType);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    continue;
                }
                if (sr.getObject(fieldName) != null) {
                    Object value = sr.getObject(fieldName);
                    if (value != null && !"".equals(value.toString())) {
                        Object objValue;
                        try {
                            objValue = typeConversion(clsType, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.printf("不能转化 %s 为类型 %s\n", value, clsType);
                            continue;
                        }
                        method.invoke(obj, objValue);
                    }
                }
            }
            objects.add(obj);
        }
        return objects;
    }


    @SuppressWarnings("unchecked")
    public static Object typeConversion(Class<?> cls, Object str) {
        Object obj = null;
        String nameType = cls.getSimpleName();
        if ("Integer".equals(nameType)) {
            obj = Integer.valueOf(str.toString());
        } else if ("String".equals(nameType.toString())) {
            if (str instanceof LinkedTreeMap) {
                LinkedTreeMap<String, Object> temp = (LinkedTreeMap<String, Object>) str;
                obj = new JSONObject(temp).toString();
            } else {
                obj = str.toString();
            }
        } else if ("Float".equals(nameType.toString())) {
            obj = Float.valueOf(str.toString());
        } else if ("Double".equals(nameType)) {
            obj = Double.valueOf(str.toString());
        } else if ("Boolean".equals(nameType)) {
            obj = Boolean.valueOf(str.toString());
        } else if ("Long".equals(nameType)) {
            obj = Long.valueOf(str.toString());
        } else if ("Short".equals(nameType)) {
            obj = Short.valueOf(str.toString());
        } else if ("Character".equals(nameType)) {
            obj = str.toString().charAt(1);
        } else if ("BigDecimal".equals(nameType)) {
            obj = new BigDecimal(str.toString());
        }
        return obj;
    }

    
    /**
     * 将 map 转换成 文件
     * @param map
     * @return
     */
 	public static void DocumentToObject(Document doc,Object obj) throws Exception{
 		Class<?> cls = obj.getClass();
         Field[] fields = cls.getDeclaredFields();
         for (Field field : fields) {
         	Class<?> clsType = field.getType();
             String name = field.getName();
             String strGet = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
             Method methodSet=null;
             try{
             	methodSet = cls.getDeclaredMethod(strGet,clsType);
             }catch(Exception e ){
             	continue;
             }
             if(doc.containsKey(name)){
 				Object value = doc.get(name);
             	if(value !=null && !"".equals(value.toString())){
             		Object objValue = typeConversion(clsType, value);
             		methodSet.invoke(obj, objValue);
             	}
 			}
         }
     }

}
