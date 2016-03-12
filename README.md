# SpringMongo

这是一个spring与mongodb结合的简单demo..使用了spring-data-mongo的template类..

里面涉及了mongodb的认证..


mongodb相关 : 
创建用户 : 
db.createUser({user:"root",pwd:"123456",roles:[{"role":"userAdminAnyDatabase","db":"admin"},{"role":"readWrite","db":"test"}]})

