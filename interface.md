# Interface
## 系统管理员
### 登录
/admin/login?username=admin&password=admin

    {
       "id": "402881e54b407082014b4078a3d70087"
    }

### 注销
/admin/logout

    {
    }


### 问题分类

添加  
/admin/category/add?name=时事  

    {
       "id": "402881e54b407082014b4078a3d70002"
    }

删除一个或多个  
/admin/category/delete?ids=402881e54b407082014b4078a3d70002,402881e54b407082014b407dbca20003  

    {
    }

获得某一id的问题分类
/admin/category/get?id=402881e54b407082014b4078a3d70002  

    {
       "category":
       {
           "id": "402881e54b407082014b4078a3d70002",
           "isDeleted": false,
           "name": "时事"
       }
    }

获得所有
/admin/category/get_all

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

## 用户
### 注册 登录 注销  
注册  
/user/register?username=user&password=user&passwordConfirm=user&email=user@163.com&role=USER  

role为用户角色：USER——普通用户、ENTERPRISE——企业用户

    {
       "id": "402881e54b445ee6014b44637cff0001"
    }
登录  
/user/login?username=user&password=user

    {
       "id": "402881e54b445ee6014b44637cff0001"
    }
注销  
/user/logout?id=402881e54b445ee6014b44637cff0001

    {
    }



