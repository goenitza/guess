# Interface
## 系统管理员
### 问题分类
* 添加  
/admin/category/add?name=时事  
{"id":"402881e54b21600c014b2160a3160000"}
* 删除一个或多个  
/admin/category/delete?ids=402881e54b21600c014b2160a3160000,402881e44b0196a4014b0197826e0001  
{}
* 修改
/admin/category/update?id=402881e54b21600c014b2160a3160000&name=汽车  
{}
* 获得某一id的问题分类
/admin/category/get?id=402881e54b21600c014b2160a3160000  
{"category":{"id":"402881e54b21600c014b2160a3160000","isDeleted":false,"name":"汽车"}}
* 获得所有
/admin/category/get_all
{"categorys":[{"id":"402881e44b0196a4014b0197826e0001","isDeleted":false,"name":"汽车"},{"id":"402881e44b0196a4014b0197b24d0002","isDeleted":false,"name":"房产"},{"id":"402881e54b21600c014b2160a3160000","isDeleted":false,"name":"时事"}]}

###题型
