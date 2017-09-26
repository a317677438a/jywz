package xft.workbench.backstage.base.system;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

import com.kayak.web.base.system.IReloadConfig;
import com.kayak.web.base.system.SysBeans;
import com.kayak.web.base.util.FileUtil;
import com.opensymphony.oscache.util.StringUtil;

public class MesInterface implements ServletContextAware, IReloadConfig
{
	private static Logger log = Logger.getLogger(MesInterface.class);

	private ServletContext servletContext;

	private List<String> interfaceFiles = new ArrayList<String>();

	

	private Properties interfaceProp = new Properties();
	
	private static String fileParent = null;

	/**
	 * 获取加载好的配置信息
	 * 
	 * @return
	 */
	public Properties getInterfaceProp()
	{
		if (!isInited())
			init();

		return interfaceProp;
	}

	/**
	 * 保存最后加载的配置文件的最后更新时间<br />
	 * 用于在init时判断文件更新时间有变化才重新加载
	 */
	private Map<String, Long> lastModified = null;

	private class Filter implements FilenameFilter
	{
		public boolean accept(File dir, String name)
		{
			return name.startsWith("interface") && name.endsWith(".properties");
		}
	}

	private Filter filter = new Filter();

	/**
	 * 加载信息接口配置文件
	 */
	public synchronized void init()
	{
		List<File> files = new ArrayList<File>();
		// 收集所有指定的文件和文件夹下的interface-*.properties文件
		for (String gfile : getInterfaceFiles())
		{
			File file = FileUtil.getFile(gfile, this.servletContext);
			if (file == null)
			{
				log.error("找不到文件：" + gfile);
				continue;
			}
			FileUtil.getFiles(files, file, this.servletContext, filter);
		}

		for (File f : files)
		{
			//接口文件 的文件夹的路径。
			if(fileParent==null)
				fileParent=f.getParent();
			
			Long last = getLastModified().get(f.getAbsolutePath());
			if (last != null && last == f.lastModified())
			{// 文件更新时间没有变化，无需重新加载
				continue;
			}

			InputStream in = null;
			try
			{
				in = new BufferedInputStream(new FileInputStream(f));
				interfaceProp.load(in);
				getLastModified().put(f.getAbsolutePath(), f.lastModified());
				log.info("加载信息接口配置文件成功：" + f.getAbsolutePath());
			} catch (IOException e)
			{
				log.error("加载信息接口配置文件失败：" + f.getAbsolutePath(), e);
			} finally
			{
				if (in != null)
				{
					try
					{
						in.close();
					} catch (IOException e)
					{
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		files.clear();
		files = null;
	}

	public void destroy()
	{
		lastModified.clear();
		lastModified = null;
		interfaceProp.clear();
		interfaceProp = null;
	}

	public String getInterfaceConfig(String prop, String def)
	{
		Properties p = this.getInterfaceProp();
		String value = p.getProperty(prop);
		if (value == null || "".equals(value.trim()))
		{
			if (def == null || "".equals(def.trim()))
			{
				return "";
			}
			return def.trim();
		}
		return value.trim();
	}

	/**
	 * 读取信息接口配置信息
	 * 
	 * @param propKey
	 *            要读取的配置变量名
	 * @param defaultValue
	 *            默认值，当找不到propKey或其值为空时，返回defaultValue
	 * @return
	 */
	public static String getInterfaceConf(String propKey, String defaultValue)
	{
		MesInterface mesInterface = SysBeans.getBean("mesInterface");//TODO
		return mesInterface.getInterfaceConfig(propKey, defaultValue);
	}
	
	
	/**
	 * 读取信息接口配置信息
	 * 
	 * @param propKey
	 *            要读取的配置变量名
	 * @return
	 */
	public static String getInterfaceConf(String propKey)
	{
		return getInterfaceConf(propKey, null);
	}

	/**
	 * 读取信息接口配置信息中某个接口的文件路径。
	 * 
	 * @param propKey
	 *            要读取的配置变量名
	 * @return
	 */
	public static String  getMesInterfacePath(String propKey) {
		String propValue=getInterfaceConf(propKey, null);
		if(fileParent==null ||  StringUtil.isEmpty(fileParent) 
				|| propValue==null ||  StringUtil.isEmpty(propValue))
			return "";
		return fileParent+File.separator+getInterfaceConf(propKey, null);
	}


	/**
	 * 提供给SPRING注入的方法
	 */
	public void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}

	/**
	 * @return the lastModified
	 */
	public long getLastModified(String filepathname)
	{
		return lastModified.get(filepathname);
	}

	public void reload()
	{
		init();
	}

	public boolean isInited()
	{
		return !getLastModified().isEmpty();
	}

	public List<String> getInterfaceFiles()
	{
		return this.interfaceFiles;
	}

	/**
	 * @param interfaceFiles
	 *            the interfaceFiles to set
	 */
	public void setInterfaceFiles(List<String> interfaceFiles)
	{
		if (interfaceFiles == null || interfaceFiles.isEmpty())
			return;
		getInterfaceFiles().addAll(interfaceFiles);
	}

	public static void main(String[] args)
	{
	}

	/**
	 * @return the lastModified
	 */
	public Map<String, Long> getLastModified()
	{
		if (this.lastModified == null)
		{
			this.lastModified = new HashMap<String, Long>();
		}
		return lastModified;
	}

}
