# Interface
## 系统管理员
### 登录 注销
**登录**  
```js
/admin/login?username=admin&password=admin
```
**注销** 
```js
/admin/logout
```
### 问题分类
**添加**  
```js
/admin/category/add?name=时事  
```
```js
    {
       "id": "402881e54b407082014b4078a3d70002"
    }
```
**删除一个或多个**  
```js
/admin/category/delete?ids=402881e54b407082014b4078a3d70002,402881e54b407082014b407dbca20003  
```
**获得某一id的问题分类**  
```js
/admin/category/get?id=402881e54b407082014b4078a3d70002  
```
```js
    {
       "category":
       {
           "id": "402881e54b407082014b4078a3d70002",
           "isDeleted": false,
           "name": "时事"
       }
    }
```
**获得所有**  
```js
/admin/category/get_all
```
```js
    {
       "categorys":
       [
           {
               "id": "402881e54b407082014b4078a3d70002",
               "isDeleted": false,
               "name": "时事"
           },
           {
               "id": "402881e54b407082014b407dbca20003",
               "isDeleted": false,
               "name": "汽车"
           }
       ]
    }
```
## 用户
### 注册 登录 注销  
**注册**  
```js
/user/register?username=user&password=user&passwordConfirm=user&email=user@163.com&role=USER  
```
role为用户角色：USER——普通用户、ORG——企业用户

**登录**  
```js
/user/login?username=user&password=user
```
**注销**  
```js
/user/logout
```
### 用户关系处理（增删好友、增删关注）

添加普通用户为好友流程：  
1. A查找B  
2. A申请添加B为好友  
3. B接收到好友申请消息并处理  
4. 若B同意A的请求，则A接收到添加成功的消息

关注组织流程：  
1. A查找G  
2. A关注G  
3. G收到A关注的消息

**查找用户** 
```js
/user/get?username=user1
```
```js
    {
       "user":
       {
           "avatar": "/default_avatar.jpg",
           "isVerified": false,
           "nickName": "user1",
           "role": "USER",
           "username": "user1"
       }
    }
```
**申请添加好友（针对普通用户）**  
```js
/user/apply_friend?username=user1
```
**删除好友**  
```js
/user/delete_friend?username=user1
```
**添加关注（针对组织）**  
```js
/user/pay_attention?username=org
```
**删除关注（针对组织）**  
```js
/user/delete_attention?username=org
```
### 消息处理

消息类型：
<table>
    <tbody>
		<tr>
            <td>值</td>
            <td>类型</td>
            <td>说明</td>
        </tr>
        <tr>
            <td>SYSTEM</td>
            <td>系统消息</td>
            <td>由管理员发送给用户的消息，比如用户题目违规已被系统删除</td>
        </tr>
        <tr>
            <td>FRIEND_APPLICATION</td>
            <td>好友申请消息</td>
            <td>当A请求添加B为好友时，B会收到此类消息</td>
        </tr>
 		<tr>
            <td>FRIEND_APPLICATION_REPLAY</td>
            <td>好友申请回复消息</td>
            <td>B同意A的好友请求后，A会收到此类消息</td>
        </tr>
 		<tr>
            <td>ORG_ATTENTION</td>
            <td>组织关注消息</td>
            <td>A关注组织G后，G会收到此类消息</td>
        </tr>
 		<tr>
            <td>SHARE_QUESTION</td>
            <td></td>
            <td></td>
        </tr>
 		<tr>
            <td>SHARE_QUESTION_REPLY</td>
            <td></td>
            <td></td>
        </tr>
 		<tr>
            <td>ORG_APPLICATION</td>
            <td></td>
            <td></td>
        </tr>
 		<tr>
            <td>QUESTION_REPORT</td>
            <td></td>
            <td></td>
        </tr>
    </tbody>
</table>

**获取登录用户的所有消息**  
```js
/user/message/get_all
```
```js
    {
       "messages":
       [
           {
               "date": "2015-02-03 15:38:43",
               "id": "8af529b94b4e1d3f014b4e6085950004",
               "isProcessed": false,
               "receiver": "user1",
               "sender": "user",
               "type": "FRIEND_APPLICATION"
           }
       ]
    }
```
**设置消息为已读**  
*id:消息ID*
```js
/user/message/set_processed?id=8af529b94b4e1d3f014b4e861b0a0005  
```

**删除消息**  
*ids：待删除的消息ID，多个ID之间用逗号分隔*
```js
/user/message/delete?ids=8af529b94b4e1d3f014b4e861b0a0005  
```
**处理好友申请消息**  
*id：消息ID*  
*isAgreed：是否同意加对方为好友*
```js
/user/message/process\_friend\_application?id=8af529b94b4e1d3f014b4e6085950004&isAgreed=true  
```



