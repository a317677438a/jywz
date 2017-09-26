package xft.workbench.backstage.base.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kayak.web.base.exception.KPromptException;
import com.opensymphony.oscache.util.StringUtil;

public class MessageXMLHandler {
	
	/**
	 * 
	* 描述 : 校验接收报文的正确性。                                                                                                                                                                                                      
	* @param url
	* @param json
	*/
	public static  void parserCheckXMLJson(String url,JSONObject json) throws Exception{
		 SAXReader reader = new SAXReader();
		 try {
			 Document document = reader.read(url);
			 Element root = document.getRootElement();
			 checkJSONObject(json, root);
		 }catch(Exception e){
			 
			 throw e;
		 }
	}
	
	private static void checkJSONObject(JSONObject json,Element element_root) throws Exception{
		 @SuppressWarnings("rawtypes")
		Iterator iterator=element_root.elementIterator();
		 while (iterator.hasNext()) {
			 Element element=(Element)iterator.next();
			 Attribute isNotNull = element.attribute("isNotNull");
			 Attribute name = element.attribute("name");
			 String name_value=element.attributeValue("name");
			 String desc_value=element.attributeValue("desc");
			
			 if(json.isNull(name_value)){
				 if(isNotNull==null || !"true".equals(isNotNull.getValue().toLowerCase())){
					 continue;
				 }else{
					 throw new KPromptException(desc_value+"("+name_value+")"+"上送值不存在！");
				 }
			}else{
				 Object object=json.get(name_value);
				 checkObject(object,element);
			}
		}
	}
	
	
	
	
	private static void checkObject(Object object,Element element) throws Exception{
		String element_name=element.getName();
		String name_value=element.attributeValue("name");
		String desc_value=element.attributeValue("desc");
		
		if("Object".equals(element_name)){
			String type_value = element.attributeValue("type");//数据类型
			Attribute isNotNull = element.attribute("isNotNull");//是否为空
			Attribute byteLength = element.attribute("byteLength");//字节长度
			Attribute scale = element.attribute("scale");//小数点精确度长度
			Attribute precision = element.attribute("precision");//值精确度长度
			Attribute maxValue = element.attribute("maxValue");//最大值
			Attribute minValue = element.attribute("minValue");//最小值
			Attribute df = element.attribute("df");//日期样式
			
			if(!"String,Integer,int,Date,BigDecimal".contains(type_value)){
				throw new KPromptException("接口中定义了非法数据类型");
			}
			
			if(object!=null && !StringUtil.isEmpty(object.toString())){
				try {
					if("Integer,int".contains(type_value)){
						if(!object.toString().matches("[-0-9]+")){
							throw new KPromptException(desc_value+"("+name_value+")"+"的值输入有误，请输入整数！");
						}
						object=Integer.valueOf(object.toString());
					}else if("BigDecimal".equals(type_value)){
						if(!object.toString().matches("[-E0-9.]+")){
							throw new KPromptException(desc_value+"("+name_value+")"+"的值输入有误，请输入可以带小数点的数！");
						}
						object=new BigDecimal(object.toString());
						if(((BigDecimal)object).scale()>8){
							object=new BigDecimal(object.toString()).setScale(8, BigDecimal.ROUND_HALF_UP);
						}
					}else if("Date".equals(type_value)){
						if(!object.toString().matches("[0-9]{8}+")){
							throw new KPromptException(desc_value+"("+name_value+")"+"的值输入有误，请输入正确的时间！");
						}
						object= object.toString();
					}else if("String".equals(type_value)){
						object= object.toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new KPromptException(desc_value+"("+name_value+")"+"的值与接口定义类型有冲突！");
				}
			}
			
			//判断是否为空
			if(isNotNull!=null && "true".equals(isNotNull.getValue().toLowerCase())){
				if(object==null || "".equals(object.toString()) ){
					throw new KPromptException(desc_value+"("+name_value+")"+"不能为空！");
				}
			}
			
			//判断长度
			if(byteLength!=null && object!=null && !StringUtil.isEmpty(object.toString())){
				int  byteLength_value = Integer.valueOf(byteLength.getValue());
				int object_byteLength= getStringLength(object.toString());
				if(object_byteLength>byteLength_value){
					//throw new KPromptException(desc_value+"("+name_value+")"+"值过长，不能超过"+byteLength_value+"个字节长度！");
					// 这里采用UTF-8编码来比较长度，UTF-8一个中文字符是用3个字节表示，英文字母是用一个字节表示
					// 前台有限制输入的字符个数，但前台输入的一般是中文，后台提示的限制个数却是字节，不仅前后端提示不一致，也会误导用户
					// 既然前台有说明输入的最大字符数，后台只需要提示输入超过限制就好了
					throw new KPromptException(desc_value+"字段输入字符超出最大限制，请重新输入");
				}
			}
			//判断精确度
			if("BigDecimal".equals(type_value)){
				if(scale!=null && object!=null && !StringUtil.isEmpty(object.toString())){
					BigDecimal object_value=(BigDecimal)object;
					int scale_value = Integer.valueOf(scale.getValue());
					int object_value_scale=object_value.scale();
					if(object_value_scale>scale_value){
						throw new KPromptException(desc_value+"("+name_value+")"+"小数点精确度过大！一共不能超过"+scale_value+"位");
					}
				}
				
				if(scale!=null && precision!=null && object!=null && !StringUtil.isEmpty(object.toString())){
					BigDecimal object_value=(BigDecimal)object;
					int precision_value = Integer.valueOf(precision.getValue());
					int scale_value = Integer.valueOf(scale.getValue());
					//object_value=object_value.setScale(scale_value);
					int object_value_precision=object_value.precision();
					if(object_value_precision>precision_value){
						throw new KPromptException(desc_value+"("+name_value+")"+"值精确度过大！一共不能超过"+precision_value+"位");
					}
				}
			}
			
			//判断最大值，最小值
			if("Integer,int,BigDecimal".contains(type_value)){
				if(maxValue!=null && object!=null && !StringUtil.isEmpty(object.toString())){
					BigDecimal object_value=new BigDecimal(object.toString());
					BigDecimal maxValue_value = new BigDecimal(maxValue.getValue());
					if(object_value.compareTo(maxValue_value)==1){
						throw new KPromptException(desc_value+"("+name_value+")"+"值过大！不能大于"+maxValue_value.toString());
					}
				}
				
				if(minValue!=null && object!=null && !StringUtil.isEmpty(object.toString())){
					BigDecimal object_value=new BigDecimal(object.toString());
					BigDecimal minValue_value = new BigDecimal(minValue.getValue());
					if(minValue_value.compareTo(object_value)==1){
						throw new KPromptException(desc_value+"("+name_value+")"+"值过小！不能小于"+minValue_value.toString());
					}
				}
			}
			
			//判断日期格式。
			if("Date".equals(type_value)){
				if(df!=null && object!=null && !StringUtil.isEmpty(object.toString())){
					DateFormat dateFormat =new SimpleDateFormat(df.getValue());
					Date object_date=null;
					try {
						object_date=dateFormat.parse(object.toString());
					} catch (Exception e) {
						throw new KPromptException(desc_value+"("+name_value+")"+"的值与接口定义类型有冲突！");
					}
					if(object_date ==null || !object.toString().equals(dateFormat.format(object_date))){
						throw new KPromptException(desc_value+"("+name_value+")"+"不符合日期格式："+df.getValue());
					}
				}
			}
			
		}else if("JSONObject".equals(element_name)){
			if(object!=null && !StringUtil.isEmpty(object.toString())){
				JSONObject jsonObject=null;
				try {
					jsonObject = new JSONObject(object.toString());
				} catch (Exception e) {
					
					throw new KPromptException(desc_value+"("+name_value+")"+"的值与接口定义类型有冲突！");
				}
				checkJSONObject(jsonObject,element);
			}
		}else if("JSONArray".equals(element_name)){
			if(object!=null && !StringUtil.isEmpty(object.toString())){
				JSONArray jsonArray=null;
				try {
					jsonArray = new JSONArray(object.toString());
				} catch (Exception e) {
					throw new KPromptException(desc_value+"("+name_value+")"+"的值与接口定义类型有冲突！");
				}
				@SuppressWarnings("unchecked")
				List<Element> lists=element.elements();
				if(lists ==null || lists.size()!=1){
					throw new KPromptException("接口定义文件有误！");
				}
				Element array_element=lists.get(0);
				for(int i=0;i<jsonArray.length();i++){
					checkObject(jsonArray.get(i), array_element);
				}
			}
		}else{
			throw new KPromptException("接口定义XML配制错误,不存在"+element_name+"对象！");
		}

	}
	
	/**
	 * 
	* 描述 :获得字符串的某种编码的字节长度，
	* @param str
	* @return
	* @throws UnsupportedEncodingException
	 */
	private static int getStringLength(String str) throws UnsupportedEncodingException{

		if(StringUtil.isEmpty(str))
			return 0;
		else
			return str.getBytes("utf-8").length;//gbk,utf-8

	}
}
