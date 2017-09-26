package xft.workbench.backstage.base.cache;

import java.util.List;

import org.bson.conversions.Bson;

public interface CacheService {

	/**
	 * 获得缓存ID
	 * @return
	 * @throws Exception
	 */
	public String  getId() throws Exception;
	
	/**
	 * 缓存对象信息
	 * @param cacheID
	 * @param obj	是一个实例类，不能为集合对象
	 * @throws Exception
	 */
	public <T> void  set(String cacheID,T obj) throws Exception;
	
	
	/**
	 * 缓存对象集合信息
	 * @param cacheID
	 * @param obj	是一个实例类，不能为集合对象
	 * @throws Exception
	 */
	public <T> void  set(String cacheID,List<T> objs) throws Exception;
	
	/**
	 * 得到缓存对象集体信息
	 * @param cacheID	缓存ID
	 * @param clz		反射生成的Class
	 * @return 没有得到返回null
	 * @throws Exception
	 */
	public <T> List<T>  find(String cacheID,Class<T> clz) throws Exception;
	
	/**
	 * 得到缓存对象集体信息
	 * @param cacheID	缓存ID
	 * @param clz		反射生成的Class
	 * @param filter	过滤对象
	 * @return 没有得到返回null
	 * @throws Exception
	 */
	public <T> List<T>  find(String cacheID,Class<T> clz,Bson filter) throws Exception;
	
	
	
	/**
	 * 得到一个缓存对象集体信息
	 * @param cacheID
	 * @param clz
	 * @return 没有得到返回null
	 * @throws Exception
	 */
	public <T> T  findOneAndDelete(String cacheID,Class<T> clz) throws Exception;
	
	/**
	 * 清除指定缓存
	 * @throws Exception
	 */
	public void cleanCache(String cacheID) throws Exception;
	
	/**
	 * 清除所有缓存
	 * @throws Exception
	 */
	public void cleanAllCache() throws Exception;
}
