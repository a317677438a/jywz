package xft.workbench.backstage.base.util;

import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;



public class WordTemplateUtil {
	private static Configuration configuration = null;
	
	private void init() throws Exception{
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// ftl文件存放路径,/为classpath
		configuration.setClassForTemplateLoading(this.getClass(), "/wordtemplate");
	}
	
	public Template getTemplate(String ftlfile) throws Exception{
		if(configuration==null){
			this.init();
		}
//		Template t = configuration.getTemplate("packinfo.ftl");
		Template t = configuration.getTemplate(ftlfile);
		
		t.setEncoding("utf-8");
		return t;
	}
	
	public void sendTemplateFile(Template t,Map<String,Object> dataMap,Writer out)throws Exception{
		t.process(dataMap, out);
		out.flush();
		out.close();
	}
	
}
