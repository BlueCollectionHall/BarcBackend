# API接口文档
## 通信接口
baseUrl: `/api`
### 学园信息
#### 全部获取
`GET:` `/schools_all`
#### 获取唯一
`GET:` `/school_only`
#### 上传修改
`POST:` `/up_school`<br>
请求头：
```json
{
  "Content-type": "application/json",
  "Authorization": "Token令牌"
}
```
请求体：`学园实体中全部参数`<br>
请求参：param：`{"up_type": "参数"}` `upload` `update`
### 部团信息
#### 全部获取
`GET:` `/clubs_all`
#### 通过学园id获取
`GET:` `/clubs_by_school`
#### 获取唯一
`GET:` `/club_only`
#### 上传修改
`POST:` `/up_club`<br>
请求头：
```json
{
  "Content-type": "application/json",
  "Authorization": "Token令牌"
}
```
请求体：`部团实体中全部参数`<br>
请求参：param：`{"up_type": "参数"}` `upload` `update`
### 学生信息
#### 全部获取
`GET:` `/students_all`
#### 通过学园id获取
`GET:` `/students_by_school`
#### 通过部团id获取
`GET:` `/students_by_club`
#### 获取唯一
`GET:` `/student_only`
#### 上传修改
`POST:` `/up_student`<br>
请求头：
```json
{
  "Content-type": "application/json",
  "Authorization": "Token令牌"
}
```
请求体：`学生实体中全部参数`<br>
请求参：param：`{"up_type": "参数"}` `upload` `update`
