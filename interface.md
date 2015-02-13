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
Request：
```js
/user/register?username=user@163.com&password=user&passwordConfirm=user&nickname=user&role=individual    
```
Parameter：  
* role为用户角色：individual——普通用户、org——企业用户  
  
Response:
```js
{
   "id": "8af529b94b81d035014b81d2271f0000"
}
```
**登录**  
Request:  
```js
/user/login?username=user@163.com&password=user&role=individual
```
Response:  
```js
{
   "id": "8af529b94b81d035014b81d2271f0000"
}
```
**注销**  
Request:  
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
Request:  
```js
/user/get?username=user2@163.com&role=individual
```
Response:  
```js
{
   "user":
   {
       "avatar": "resources\avatar\8af529b94b81d035014b81eab24c0003.gif",
       "id": "8af529b94b81d035014b81eab24c0003",
       "nickname": "user2",
       "username": "user2@163.com"
   }
}
```
**申请添加好友（针对普通用户）**  
Request:  
```js
/user/apply_friend?id=8af529b94b81d035014b81eab24c0003
```
**删除好友**  
Request:  
```js
/user/delete_friend?id=8af529b94b81d035014b81eab24c0003
```
**添加关注（针对组织）**  
Request:  
```js
/user/pay_attention?id=8af529b94b81d035014b81f3c1c80007
```
**删除关注（针对组织）**  
Request:  
```js
/user/delete_attention?id=8af529b94b81d035014b81f3c1c80007
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
            <td>QUESTION_SHARE</td>
            <td></td>
            <td></td>
        </tr>
 		<tr>
            <td>QUESTION_SHARE_REPLY</td>
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
Request:  
```js
/user/message/get_all
```
Response:  
```js
    {
       "messages":
       [
           {
               "date": "2015-02-13 15:58:00",
               "id": "8af529b94b81d035014b81f1c34f0006",
               "isProcessed": false,
               "receiverId": "8af529b94b81d035014b81eab24c0003",
               "senderAvatar": "resources\avatar\8af529b94b81d035014b81d2271f0000.gif",
               "senderId": "8af529b94b81d035014b81d2271f0000",
               "senderNickname": "user",
               "type": "FRIEND_APPLICATION"
           }
       ]
    }
```
**设置消息为已读**  
Request:  
```js
/user/message/set_processed?id=8af529b94b4e1d3f014b4e861b0a0005  
```
Params:  
* id:消息ID
**删除消息**  
Request:  
```js
/user/message/delete?ids=8af529b94b4e1d3f014b4e861b0a0005  
```
Params:  
* ids：待删除的消息ID，多个ID之间用逗号分隔  

**处理好友申请消息**  
加对方为好友*  
Request:  
```js
/user/message/process_friend_application?id=8af529b94b4e1d3f014b4e6085950004&isAgreed=true&circleId=circlename  
```
Params:  
* id:消息ID
* isAgreed:是否同意添加好友请求
* circleId:朋友圈ID，同意好友请求后会将此好友添加到这个朋友圈中，若无此参数，则添加到缺省的朋友圈中。



