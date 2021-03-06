# 模拟在线票务管理系统

本项目可作为Spring Boot初学者入门非常实用的练手项目。

本项目使用了Spring Boot + Mybatis实现了模拟在线票务管理系统，集成Spring Security实现用户的认证和授权。

希望可以有一起在学习Spring Boot的朋友可以一起来完善该项目。

提供API：

*用户注册(/user/register)、登录(/user/login)、查看用户资料(/user/myProfile)、上传头像(/user/uploadAvatar)

*根据始发站、目的地和出发日查找可购买的车次信息(/ticket/search)

*购票(/order/new)、退票(/order/roll)、查看“我的订单”(/order/myOrder)。（数据库事务）

Version 0.9主要实现了以上的主要功能。

Version 1.0集成了Spring Security，使用JWT实现用户的认证和授权。

认证原理：在Spring Security配置中添加JWTLoginFilter和JWTAuthenticationFilter。向Spring Security默认登录URL(/login)地址发送POST请求，提交包含用户名密码的表单，程序进入`JWTLoginFilter`的`attemptAuthentication`方法，接收并解析用户凭证。
然后进入`CustomerAuthenticationProvider`的`authenticate`方法将接受到的用户信息与数据库中信息做比较，
如果一致，生成令牌，令牌中保存了用户名、密码、用户角色信息。之后，进入`JWTLoginFilter`的`successfulAuthentication`方法，在该方法中根据生成的令牌生成JWT，JWT中包含了用户信息并设置了过期时间。最后，将JWT作为response的header返回至前端。

在之后的请求中只要将JWT设置为request header的“Authorization”字段传给后端，后端进入JWTAuthenticationFilter解析该header获取用户信息。

*用户信息中包含了登录时间，

