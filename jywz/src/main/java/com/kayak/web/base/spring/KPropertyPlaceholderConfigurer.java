package com.kayak.web.base.spring;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import xft.workbench.backstage.base.util.GlobalMessage;

import com.kayak.web.base.system.Global;

/**
 * 继承spring的PropertyPlaceholderConfigurer类为了使用在context.xml里配置的jdbc.
 * properties文件里的配置信息
 * 
 *
 */
public class KPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	private static Logger logger = Logger.getLogger(KPropertyPlaceholderConfigurer.class);
	private  Properties props;
	
	private Global global;

	public void setGlobal(Global global) {
		this.global = global;
	}

	public  String getPropValue(String key) {
		if (this.props == null) {
			return null;
		}
		return this.props.getProperty(key);
	}

	@Override
	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		this.props = props;
	}

	public void setLocationPro(Resource[] resources) throws Exception{
		FileInputStream is = null;
		//平台类型：-1开发、-2测试、
		String systemType= global.getGlobalProp().getProperty(GlobalMessage.SYSTEM_TYPE);
		if("-1".equals(systemType)){
			this.setLocations((org.springframework.core.io.Resource[]) new Resource[] {resources[0]});
			is = new FileInputStream(resources[0].getFile());
			GlobalMessage.param_props.load(is);
		}else if("-2".equals(systemType)){
			this.setLocations((org.springframework.core.io.Resource[]) new Resource[] {resources[1]});
			is = new FileInputStream(resources[1].getFile());
			GlobalMessage.param_props.load(is);
		}else if("1".equals(systemType)){
			this.setLocations((org.springframework.core.io.Resource[]) new Resource[] {resources[4]});
			is = new FileInputStream(resources[4].getFile());
			GlobalMessage.param_props.load(is);
		}else{
			logger.error("系统数据源参数加载失败，系统类型："+systemType+"(-1开发、-2测试、1生产)!");
			throw new Exception("系统数据源参数加载失败，系统类型："+systemType+"(-1开发、-2测试、1生产)!");
		}
		logger.info("系统数据源参数加载成功，系统类型："+systemType+"(-1开发、-2测试、1生产)!");
		
	}
}
