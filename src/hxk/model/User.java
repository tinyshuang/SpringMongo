package hxk.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @author Administrator
 * @description
 *2016-3-12  下午10:03:30
 */
@Document
public class User {
    @Id
    private String id;
    private String name;
    private int age;
    @DateTimeFormat(iso=ISO.DATE)
    private Date date;
    
    public User() {}


    public User(String id, String name, int age, Date date) {
	this.id = id;
	this.name = name;
	this.age = age;
	this.date = date;
    }
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
