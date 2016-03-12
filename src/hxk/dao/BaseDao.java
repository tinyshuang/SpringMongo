package hxk.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

/**
 * @author Administrator
 * @description
 *2015-1-13  上午11:29:00
 */
public class BaseDao<T> {
    	
    	private  MongoTemplate mongoTemplate;
    	private Class<T> clazz;
    	public static  final int PAGE_COUNT = 10;
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDao() {
           Type genType = getClass().getGenericSuperclass();
           Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
           clazz = (Class) params[0];
	}

    	//if non exist = insert else = upsert
    	public  void save(T object) {
    	    mongoTemplate.save(object);
    	}
    	
    	
    	public void saveAll(List<T> list){
    	    mongoTemplate.insertAll(list);
    	}
    	
	public List<T> getAll() {
	   return (List<T>) mongoTemplate.findAll(clazz);
	}


	public T get(String id) {
	    return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),clazz);
	}
	
	public T get(int id) {
	    return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),clazz);
	}
	
	/**
	 * @description	查询表里某name列为value的行 
	 * @param name  列名
	 * @param value 列值
	 * @return
	 *2015-1-14  上午10:11:03
	 *返回类型:List<T>
	 */
	public List<T> find(String name,String value){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(name).regex(value));
	    return (List<T>) mongoTemplate.find(query, clazz);
	}
	
	public List<T> exist(String name,boolean flag){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(name).exists(flag));
	    return (List<T>) mongoTemplate.find(query, clazz);
	}
	
	/**
	 * @description 查询表里某name列为value的第一行 
	 * 	如果有多个行复合条件..返回按照ID排列的第一个..	 
	 * @param name  列名
	 * @param value 列值
	 * @return
	 *2015-1-14  上午10:07:49
	 *返回类型:T
	 */
	public T findone(String name,String value){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(name).regex(value));
	    return mongoTemplate.findOne(query, clazz);
	}
	
	public WriteResult update(String id, String name,Object value) {
	   return mongoTemplate.updateFirst(new Query(Criteria.where("_id").is(id)),Update.update(name, value), clazz);
	}

	public void removeById(String id) {
	   mongoTemplate.remove(new Query(Criteria.where("_id").is(id)),clazz);
	}
	
	public void remove(T object){
	    mongoTemplate.remove(object);
	}
	
	public void removeByField(String name,Object value){
	    mongoTemplate.remove(new Query(Criteria.where(name).is(value)), clazz);
	}
	

	public int count(String table){
	    return (int) mongoTemplate.count(new Query(), table);
	}
	
	
	public List<T> between(String name,int min,int max){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(name).gte(min).lte(max));
	    return mongoTemplate.find(query, clazz);
	}
	
	public List<T> in(String name,List<?> list){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(name).in(list));
	    return mongoTemplate.find(query, clazz);
	}
	
	public List<T> like(String name,String value){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(name).regex(value));
	    return mongoTemplate.find(query, clazz);
	}
	
	public List<T> sort(String name,boolean isAsce){
	    Order order = isAsce ? Order.ASCENDING : Order.DESCENDING;
	    Query query = new Query();
	    query.sort().on(name, order);
	    return mongoTemplate.find(query, clazz);
	}
	
	public List<T> sort(String name,boolean isAsce,int size){
	    Order order = isAsce ? Order.ASCENDING : Order.DESCENDING;
	    Query query = new Query();
	    //query.skip(skip) 分页时表示从第几行开始的记录
	    query.limit(size).sort().on(name, order);
	    return mongoTemplate.find(query, clazz);
	}
	
	public List<T> pageAll(int skip,int size){
	    Query query = new Query();
	    query.skip(skip).limit(size);
	    return mongoTemplate.find(query, clazz);
	}
	
	public List<T> pageAll(int skip){
	    return pageAll(skip,PAGE_COUNT);
	}
	
	//单条件分页查询
	public List<T> pageCheck(String attrName,String word,int skip){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(attrName).regex(word));
	    query.skip(skip).limit(PAGE_COUNT);
	    return mongoTemplate.find(query, clazz);
	}
	
	//单条件分页查询时数据的总大小
	public int pageCheckCount(String attrName,String word){
	    Query query = new Query();
	    query.addCriteria(Criteria.where(attrName).is(word));
	    return mongoTemplate.find(query, clazz).size();
	}
	
	//多条件的MongoTemplate的or查询
	public List<T> check(String word,int skip,String firAttr,String sedAttr){
	   Query query = new Query();
	   query.skip(skip).limit(PAGE_COUNT);
	   Criteria cr = new Criteria();
	   query.addCriteria(cr.orOperator(
	       Criteria.where(firAttr).regex(word)
	       ,Criteria.where(sedAttr).regex(word)
	   ));
           
	   return getMongoTemplate().find(query, clazz);
	}	
	
	//多条件的MongoTemplate的or查询时查询记录总数
	public int checkCount(String word,String firAttr,String sedAttr){
	  Query query = new Query();
	  Criteria cr = new Criteria();
	  query.addCriteria(cr.orOperator(
	  	Criteria.where(firAttr).regex(word),Criteria.where(sedAttr).regex(word)
	  ));
	  return getMongoTemplate().find(query, clazz).size();
	}
	
	protected MongoTemplate getMongoTemplate(){
	    return mongoTemplate;
	}
	
	
	@Resource
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
	    this.mongoTemplate = mongoTemplate;
	}
	//多条件查询
       /*public List<Message> check(String word){
       	Criteria criteria = new Criteria();
       	criteria.andOperator(Criteria.where("title").is(word),Criteria.where("content").is(word));
       	return getMongoTemplate().find(new Query(criteria), Message.class);
       }*/	
	
}
