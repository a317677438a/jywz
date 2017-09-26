package xft.workbench.backstage.base.cache.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import xft.workbench.backstage.base.cache.CacheService;
import xft.workbench.backstage.base.mongodb.MongoDBUtil;
import xft.workbench.backstage.base.util.ObjectMapUtil;

import com.kayak.web.base.exception.KPromptException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.msfl.minshengzulin.communicate.util.StringUtil;

@Service(value = "cacheService")
public class CacheServiceImpl implements CacheService {

	//需要建立过期索引db.CACHERESULT.createIndex({"coll_crt_date":1},{expireAfterSeconds:60*60*24});单位是秒
	public  static final String SYSTEM_CACHERESULT_COLLECTION="CACHERESULT";//系统缓存的集合名
	
	@Override
	public String getId() throws Exception {
		String cacheID=UUID.randomUUID().toString().replace("-", "");
		 return cacheID;
	}

	@Override
	public <T> void set(String cacheID, T obj) throws Exception {
		if(cacheID == null || obj == null || StringUtil.isEmpty(cacheID)){
			throw new KPromptException("缓存数据异常！cacheID："+cacheID +",Object:"+ obj==null?"null":obj.toString());
		}
		Document doc = null;
		if(obj instanceof String){
			doc = new Document("cacheString",obj.toString());
		}else{
			doc = new Document(ObjectMapUtil.getFieldVlaue2(obj));
		}
				
				
		doc.put("coll_crt_date", new Date());//记录创建时间
		doc.put("cacheID", cacheID);//缓存标示
		
		MongoDBUtil.instance.save(SYSTEM_CACHERESULT_COLLECTION, doc);
	}

	@Override
	public <T> void set(String cacheID, List<T> objs) throws Exception {
		if(cacheID == null || objs == null || StringUtil.isEmpty(cacheID)){
			throw new KPromptException("缓存数据异常！cacheID："+cacheID +",Object:"+ objs==null?"null":objs.toString());
		}
		for (Object obj:objs) {
			this.set(cacheID, obj);
		}
	}

	@Override
	public <T> List<T> find(String cacheID, Class<T> clz)
			throws Exception {
		if(cacheID == null || clz == null || StringUtil.isEmpty(cacheID)){
			throw new KPromptException("取缓存数据异常！cacheID："+cacheID +",Class:"+ clz==null?"null":clz.toString());
		}
		//将结果记录缓存集合中。
		BasicDBObject filters = new BasicDBObject();
		filters.put("cacheID", cacheID);
		MongoCursor<Document> cursor =MongoDBUtil.instance.find(SYSTEM_CACHERESULT_COLLECTION, filters);
		
		return this.cursorToList(cursor, clz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> cursorToList(MongoCursor<Document> cursor,Class<T> clz)throws Exception{
		List<T> objs= new ArrayList<T>();
		while(cursor.hasNext()) {
			Document document = cursor.next();
			objs.add(this.documentToObj(document, clz));
		}
		
		if(objs.size() == 0){
			return null;
		}
		
		return objs;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T documentToObj(Document document,Class<T> clz) throws Exception{
		T obj = null;
		if ("String".equals(clz.getSimpleName())){
			obj = (T)document.get("cacheString");
		}else{
			obj = clz.newInstance();
			ObjectMapUtil.DocumentToObject(document, obj);
		}
		return obj;
	}
	
	@Override
	public <T> List<T> find(String cacheID, Class<T> clz ,Bson filter)
			throws Exception {
		if(cacheID == null || clz == null || StringUtil.isEmpty(cacheID) ||
				filter == null){
			throw new KPromptException("取缓存数据异常！cacheID："+cacheID +",Class:"+ clz==null?"null":clz.toString()
					+",filter:" + filter==null?"null":filter.toString());
		}
		
		//将结果记录缓存集合中。
		BasicDBObject filters = (BasicDBObject)filter;
		filters.put("cacheID", cacheID);
		MongoCursor<Document> cursor =MongoDBUtil.instance.find(SYSTEM_CACHERESULT_COLLECTION, filters);
		
		return this.cursorToList(cursor, clz);
	}
	
	@Override
	public <T> T findOneAndDelete(String cacheID, Class<T> clz)
			throws Exception {
		if(cacheID == null || clz == null || StringUtil.isEmpty(cacheID)){
			throw new KPromptException("取缓存数据异常！cacheID："+cacheID +",Class:"+ clz==null?"null":clz.toString());
		}
		//将结果记录缓存集合中。
		BasicDBObject filters = new BasicDBObject();
		filters.put("cacheID", cacheID);
		Document document =MongoDBUtil.instance.findOneAndDelete(SYSTEM_CACHERESULT_COLLECTION, filters);
		if(document==null){
			return null;
		}
		return this.documentToObj(document, clz);
	}
	
	
	@Override
	public void cleanCache(String cacheID) throws Exception {
		BasicDBObject filters = new BasicDBObject();
		filters.put("cacheID", cacheID);
		MongoDBUtil.instance.deleteCollectionDoc(SYSTEM_CACHERESULT_COLLECTION, filters);
	}

	@Override
	public void cleanAllCache() throws Exception {
		MongoDBUtil.instance.deleteCollectionDoc(SYSTEM_CACHERESULT_COLLECTION, new BasicDBObject());
	}

	

}
