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
**获得所有好友**  
Request:  
```js
/user/get_all_friend
```  
Response:  
```js
{
    "users": [
        {
            "avatar": "resources\\avatar\\402881e64b97e52f014b97e6468d0002.gif",
            "id": "402881e64b97e52f014b97e6468d0002",
            "nickname": "路在脚下2",
            "username": "user2@163.com"
        },
        {
            "avatar": "resources\\avatar\\402881e64b97e52f014b97e60b800001.gif",
            "id": "402881e64b97e52f014b97e60b800001",
            "nickname": "路在脚下3",
            "username": "user3@163.com"
        },
        {
            "avatar": "resources\\avatar\\402881e64b97e52f014b97e5c3340000.gif",
            "id": "402881e64b97e52f014b97e5c3340000",
            "nickname": "路在脚下4",
            "username": "user4@163.com"
        }
    ]
}
```  
**获得所有关注的组织**  
Request:  
```js
/user/get_all_following
```  
Response:  
```js
{
    "users": [
        {
            "avatar": "resources\\avatar\\402881e64b97f78b014b97fc305a0000.gif",
            "id": "402881e64b97f78b014b97fc305a0000",
            "isVerified": false,
            "nickname": "组织1",
            "username": "org1@163.com"
        },
        {
            "avatar": "resources\\avatar\\402881e64b97f78b014b97fc6f220001.gif",
            "id": "402881e64b97f78b014b97fc6f220001",
            "isVerified": false,
            "nickname": "组织2",
            "username": "org2@163.com"
        }
    ]
}
```  
**获得所有粉丝**  
Request:  
```js
/user/get_all_follower
```  
Response:  
```js
{
    "users": [
        {
            "avatar": "resources\\avatar\\402881e64b97e52f014b97e6834a0003.gif",
            "id": "402881e64b97e52f014b97e6834a0003",
            "nickname": "路在脚下1",
            "username": "user1@163.com"
        }
    ]
}
``` 
### 朋友圈
**添加**  
Request:  
```js
/user/circle/add?name=IT
```  
Response:  
```js
{
    "id": "402881e64b8d16d4014b8d1a35d10003"
}
```  
**修改**  
Request:  
```js
/user/circle/update?id=402881e64b8d16d4014b8d1a35d10003&name=同学
```  
**删除**  
Request:  
```js
/user/circle/delete?id=402881e64b8d16d4014b8d1a35d10003
```  
**获取某用户所有**  
Request:  
```js
/user/circle/get_all
```  
Response:  
```js
{
    "circles": [
        {
            "id": "402881e64b8d16d4014b8d1a35d10003",
            "name": "IT"
        },
        {
            "id": "402881e64b8d16d4014b8d178b680002",
            "name": "我的好友"
        }
    ]
}
```  
**添加好友或粉丝到某一朋友圈**  
Request:  
```js
/user/add_to_circle?userId=402881e64b97e52f014b97e60b800001&circleId=402881e64b981221014b981262a70000
```  
Param:  
* userId：好友或粉丝的ID  
* circleId：朋友圈ID    
**将好友或粉丝从某一朋友圈删除**  
Request:  
```js
/user/delete_from_circle?circleId=402881e64b981221014b981262a70000&userId=402881e64b97e52f014b97e6468d0002
```  
**获得某一朋友圈的好友**  
Request:  
```js
/user/get_by_circle?circleId=402881e64b981221014b981262a70000
```  
Response:  
```js
{
    "users": [
        {
            "avatar": "resources\\avatar\\402881e64b97e52f014b97e6468d0002.gif",
            "id": "402881e64b97e52f014b97e6468d0002",
            "nickname": "路在脚下2",
            "username": "user2@163.com"
        },
        {
            "avatar": "resources\\avatar\\402881e64b97e52f014b97e60b800001.gif",
            "id": "402881e64b97e52f014b97e60b800001",
            "nickname": "路在脚下3",
            "username": "user3@163.com"
        }
    ]
}
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
Request:  
```js
/user/message/process_friend_application?id=8af529b94b4e1d3f014b4e6085950004&isAgreed=true  
```
Params:  
* id:消息ID
* isAgreed:是否同意添加好友请求




