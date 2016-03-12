package hxk.action;

import java.util.Date;

import javax.annotation.Resource;

import hxk.dao.UserDao;
import hxk.model.User;
import hxk.util.UUIDGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 * @description
 *2016-3-12  下午10:06:18
 */
@Controller
public class UserAction {
    @Resource
    private UserDao userDao;
    
    
    @RequestMapping("add")
    public @ResponseBody String add(){
	User user = new User(new UUIDGenerator().generate().toString(), "tinys", 25, new Date());
	userDao.save(user);
	return "ok";
    }
    
    @RequestMapping("get")
    public @ResponseBody int get(){
	User user = (User) userDao.findone("name", "tinys");
	return user.getAge();
    }
    
    @RequestMapping("count")
    public @ResponseBody int count(){
	return userDao.count("user");
    }
}
