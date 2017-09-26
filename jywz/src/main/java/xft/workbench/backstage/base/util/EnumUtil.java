package xft.workbench.backstage.base.util;

import java.lang.reflect.Field;

import org.json.JSONObject;

import xft.workbench.backstage.base.annotation.EnumDesc;
import xft.workbench.backstage.base.annotation.EnumValue;
import xft.workbench.backstage.base.enumeration.asset.LoanStatusEnum;

public class EnumUtil {
    public static JSONObject enumToJSON(String enumClassName) {
        JSONObject jsonObj = new JSONObject();
        try {
            Class<?> enumClass = Class.forName(enumClassName);
            Field[] fields = enumClass.getFields();
            for (Field item : fields) {
                EnumValue valueA = item.getAnnotation(EnumValue.class);
                EnumDesc descA = item.getAnnotation(EnumDesc.class);
                if (valueA == null || descA == null) {
                    continue;
                }
                String value = valueA.value();
                String desc = descA.value();
                jsonObj.put(value, desc);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    /**
     * 通过描述（EnumDesc）得到枚举的值（EnumValue）
     *
     * @param enumClass
     * @param enumDesc
     * @return
     */
    public static String getEnumValueByDesc(Class<?> enumClass, String enumDesc) {
        String enumValue = null;
        try {
            Field[] fields = enumClass.getFields();
            for (Field item : fields) {
                EnumValue valueA = item.getAnnotation(EnumValue.class);
                EnumDesc descA = item.getAnnotation(EnumDesc.class);
                if (valueA == null || descA == null) {
                    continue;
                }
                String value = valueA.value();
                String desc = descA.value();
                if (desc.equals(enumDesc)) {
                    enumValue = value;
                    break;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumValue;
    }

    /**
     * 通过描述（EnumDesc）得到枚举的值（EnumValue）
     */
    public static String getEnumValueByDesc(String enumClassName, String enumDesc) {
        String enumValue = null;
        try {
            Class<?> enumClass = Class.forName(enumClassName);
            Field[] fields = enumClass.getFields();
            for (Field item : fields) {
                EnumValue valueA = item.getAnnotation(EnumValue.class);
                EnumDesc descA = item.getAnnotation(EnumDesc.class);
                if (valueA == null || descA == null) {
                    continue;
                }
                String value = valueA.value();
                String desc = descA.value();
                if (desc.equals(enumDesc)) {
                    enumValue = value;
                    break;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumValue;
    }


    /**
     * 通过值（EnumValue）得到描述（EnumDesc）
     */
    public static String getDescValueByEnumValue(Class<?> enumClass, Integer enumValue) {
        return getDescValueByEnumValue(enumClass, enumValue.toString());
    }

    /**
     * 通过值（EnumValue）得到描述（EnumDesc）
     */
    public static String getDescValueByEnumValue(Class<?> enumClass, String enumValue) {
        String enumDesc = "";
        try {
            Field[] fields = enumClass.getFields();
            for (Field item : fields) {
                EnumValue valueA = item.getAnnotation(EnumValue.class);
                EnumDesc descA = item.getAnnotation(EnumDesc.class);
                if (valueA == null || descA == null) {
                    continue;
                }
                String value = valueA.value();
                String desc = descA.value();
                if (value.equals(enumValue)) {
                    enumDesc = desc;
                    break;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return enumDesc;
    }


    public static String getDescValueByEnumValue(String enumClassName, Integer enumValue) {
        String enumDesc = "";
        try {
            Class<?> enumClass = Class.forName(enumClassName);
            return getDescValueByEnumValue(enumClass, enumValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumDesc;
    }


//	public static void main(String[] args){
//		EnumUtil.enumToJSON("xft.workbench.backstage.base.enumeration.OrgTypeEnum");
//		String value = EnumUtil.getEnumValueByDesc(OrgTypeEnum.class, "发起机构");
//		String desc = EnumUtil.getDescValueByEnumValue(OrgTypeEnum.class, 1);
//		
//		System.out.println(value);
//		System.out.println(desc);
//	}
}
