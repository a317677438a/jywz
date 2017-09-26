package xft.workbench.backstage.base.mongodb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.eclipse.jdt.core.dom.ThisExpression;

import xft.workbench.backstage.base.util.GlobalMessage;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

@SuppressWarnings("deprecation")
public enum MongoDBUtil {
	
	instance;
    /**
     * 代表此类的一个单实例
     */
	private MongoClient mongoClient;
	private static String dbName; 
    private static String credentialUser;
    private static String credentialDbName;
    private static String credentialPassword;
    private static String serverAddressHost;
    private static String serverAddressPort;
    private static Integer connectionsPerHost;
    private static Integer connectTimeout;
    private static Integer maxWaitTime;
    private static Integer socketTimeout;
    private static Integer threadsAllowedToBlockForConnectionMultiplier;
	
	static{
		
		 dbName=GlobalMessage.param_props.getProperty("mongodb.use.dbName", "test"); 
	     credentialUser=GlobalMessage.param_props.getProperty("mongodb.credential.user", "admin"); 
	     credentialDbName=GlobalMessage.param_props.getProperty("mongodb.credential.dbName", "admin"); 
	     credentialPassword=GlobalMessage.param_props.getProperty("mongodb.credential.password", "test"); 
	     serverAddressHost=GlobalMessage.param_props.getProperty("mongodb.serverAddress.host", ""); 
	     serverAddressPort=GlobalMessage.param_props.getProperty("mongodb.serverAddress.port", ""); 
	     connectionsPerHost=Integer.valueOf(GlobalMessage.param_props.getProperty("mongodb.options.connectionsPerHost", "300")); 
	     connectTimeout=Integer.valueOf(GlobalMessage.param_props.getProperty("mongodb.options.connectTimeout", "15000")); 
	     maxWaitTime=Integer.valueOf(GlobalMessage.param_props.getProperty("mongodb.options.maxWaitTime", "5000")); 
	     socketTimeout=Integer.valueOf(GlobalMessage.param_props.getProperty("mongodb.options.socketTimeout", "0")); 
	     threadsAllowedToBlockForConnectionMultiplier=Integer.valueOf(GlobalMessage.param_props.getProperty("mongodb.options.threadsAllowedToBlockForConnectionMultiplier", "5000")); 
		
		 
		
		//用户认证
		MongoCredential credentials = MongoCredential.createCredential(
				credentialUser, credentialDbName, credentialPassword.toCharArray());
        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        credentialsList.add(credentials);
        
        //复制集。  
        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        String[] hosts=serverAddressHost.split(",");
		String[] ports=serverAddressPort.split(",");
		for(int i=0;i<hosts.length;i++){
			ServerAddress serverAddress = new ServerAddress(hosts[i],Integer.valueOf(ports[i]));
			seeds.add(serverAddress);
		}
        
        
       //连接池设置 
       Builder options = new MongoClientOptions.Builder();
        // options.autoConnectRetry(true);// 自动重连true
        // options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
        options.connectionsPerHost(connectionsPerHost);// 连接池设置为300个连接,默认为100
        options.connectTimeout(connectTimeout);// 连接超时，推荐>3000毫秒
        options.maxWaitTime(maxWaitTime); //
        options.socketTimeout(socketTimeout);// 套接字超时时间，0无限制
        options.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
        options.writeConcern(WriteConcern.SAFE);//
        options.readPreference(ReadPreference.secondary());//从副节点读取
       
        //得到mongodb连接池
        instance.mongoClient= new MongoClient(seeds, credentialsList , options.build());
	}
	
	
	public static MongoClient getMongoClient() {
		return instance.mongoClient;
	}
	
   /**
    * 将 map 转换成 文件
    * @param map
    * @return
    */
	public static Document MapToDocument(Map<String,Object> map){
		Document doc = new Document();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			doc.put(key, value);
		}
		return doc;
	}
    
    // ------------------------------------共用方法---------------------------------------------------
    /**
     * 获取DB实例 - 指定DB
     * 
     * @param dbName
     * @return
     */
    public MongoDatabase getDB() {
        if (dbName != null && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        }
        return null;
    }

    /**
     * 获取collection对象 - 指定Collection
     * 
     * @param collName
     * @returno
     */
    public MongoCollection<Document> getCollection(String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
        return collection;
    }

    /**
     * 查询DB下的所有表名
     */
    public List<String> getAllCollections() {
        MongoIterable<String> colls = getDB().listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }

    /**
     * 获取所有数据库名称列表
     * 
     * @return
     */
    public MongoIterable<String> getAllDBNames() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }

    /**
     * 删除一个数据库
     */
    public void dropDB() {
        getDB().drop();
    }

    /**
     * 查找对象 - 根据主键_id
     * 
     * @param collection
     * @param id
     * @return
     */
    public Document findById(MongoCollection<Document> coll, String id) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Document myDoc = coll.find(Filters.eq("_id", _idobj)).first();
        return myDoc;
    }

    /** 统计数 */
    public int getCount(MongoCollection<Document> coll) {
        int count = (int) coll.count();
        return count;
    }

    
    
    /** 条件查询 */
    public MongoCursor<Document> find(String collName, Bson filter) {
    	
        return getCollection(collName).find(filter).iterator();
    }
    
    /** 条件查询 */
    public MongoCursor<Document> find(String collName, Bson filter , Bson sort) {
    	
        return getCollection(collName).find(filter).sort(sort).iterator();
    }
    
    
    /** 条件查询 */
    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }
    
    
    /** 条件查询 */
    public Document findOneAndDelete(String collName, Bson filter ) {
    	return getCollection(collName).findOneAndDelete(filter);
    }
    
    
    /** 分页查询 */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    /**
     * 通过ID删除
     * 
     * @param coll
     * @param id
     * @return
     */
    public int deleteById(MongoCollection<Document> coll, String id) {
        int count = 0;
        ObjectId _id = null;
        try {
            _id = new ObjectId(id);
        } catch (Exception e) {
            return 0;
        }
        Bson filter = Filters.eq("_id", _id);
        DeleteResult deleteResult = coll.deleteOne(filter);
        count = (int) deleteResult.getDeletedCount();
        return count;
    }

    /**
     * 更新一个文件
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
        } catch (Exception e) {
            return null;
        }
        Bson filter = Filters.eq("_id", _idobj);
        // coll.replaceOne(filter, newdoc); // 完全替代
        coll.updateOne(filter, new Document("$set", newdoc));
        return newdoc;
    }

    /**
     * 建立一个集合
     * @param collName
     */
    public void createCollection( String collName) {
        getDB().createCollection(collName);
    }
    /**
     * 删除一个集合
     * @param collName
     */
    public void dropCollection( String collName) {
        getDB().getCollection(collName).drop();
    }

    /**
     * 删除一个集合内的文档信息。
     * @param collName
     */
    public void deleteCollectionDoc( String collName , Bson removeFilter) {
        getDB().getCollection(collName).deleteMany(removeFilter);
    }
    
    /**
     * 指定集合保存一个文件
     * @param doc
     * @param collName
     */
    public void save(String collName,Document doc){
    	MongoCollection<Document> coll = getCollection(collName);
    	for(Entry<String, Object> entry:doc.entrySet()){
    		if(entry.getValue() instanceof BigDecimal){
    			doc.put(entry.getKey(), ((BigDecimal)entry.getValue()).doubleValue());
    		}
    	}
    	coll.insertOne(doc);
    }
    
    /**
     * 关闭Mongodb
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    
    /**
     * 测试入口
     * 
     * @param args
     */
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 使用的java连接jar包版本 mongo-java-driver-3.2.2.jar 
    	 * mongodb数据库 3.2
    	 * 
    	 */
    	
    	//集合名
    	String collName="users";
    	MongoCursor<Document> cursor= MongoDBUtil.instance.find(collName, Filters.eq("name", "小明"));
        while (cursor.hasNext()) {
        	Document document=cursor.next();
        	System.out.println(document.toJson());
		}
        
        //MongoDBUtil.instance.close();
    }
}