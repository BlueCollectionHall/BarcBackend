# User接口文档
## 通信接口
baseUrl: `/user`
### 注册相关接口
#### 发送邮箱验证码
`GET:` `/get_signup_code`<br>
请求参：param： `email`<br>
响应：
```json
{
  "code": 0,
  "msg": "邮件发送成功",
  "data": "<unique_id>"
}
```
#### 注册请求
`POST:` `/sign_up`<br>
请求参：param： `email`、`code`、`unique_id`<br>
请求体：

```json
{
  "username": "",
  "password": "",
  "email": "",
  "telephone": ""
}
```
### 登录相关接口
#### 登录
`GET:` `/sign_in`<br>
请求参：param：`account`、`password`、`type`<br>
`type`：两种`"email"`和`"username"`，登录方式<br>
`account`：当`type`是`email`时account应该是邮箱地址、`username`时应该是用户名
