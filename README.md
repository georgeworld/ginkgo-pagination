# Ginkgo Pagination Library
## 银杏树分页标签库
![image](https://raw.githubusercontent.com/georgeworld/georgeworld.github.com/master/ginkgo/pagination/img/GinkgoPagination-logo.png)<br>  

&nbsp;&nbsp;&nbsp;&nbsp;这是一个分页标签库，[老乔](http://www.georgeinfo.com)创建于2013年。使用JSP标签的方式，实现了方便快捷地分页功能，内置提供了简单样式分页栏、bootstrap样式分页栏两种内置分页栏样式。<br>
&nbsp;&nbsp;&nbsp;&nbsp;同时，Ginkgo Pagination 也提供了返回JSON格式的分页栏数据，让使用者自己实现分页栏样式。<br>
&nbsp;&nbsp;&nbsp;&nbsp;现在直接使用jsp作为前端视图层模板的项目不多了（现在流行用react/vue做前端，后端提供json），我这个也是多年前做的一个小组件，现在开源出来，给仍然使用jsp作为模板的有缘人，或许能提供一些帮助。

# 预览
## &nbsp;&nbsp;&nbsp;&nbsp;默认分页栏样式<br>
![image](https://raw.githubusercontent.com/georgeworld/georgeworld.github.com/master/ginkgo/pagination/img/pagination-bar-default.png)<br>

## &nbsp;&nbsp;&nbsp;&nbsp;Bootstrap风格的分页栏样式<br>
![image](https://raw.githubusercontent.com/georgeworld/georgeworld.github.com/master/ginkgo/pagination/img/pagination-bar-json-bootstrap.png)<br>

# 使用说明
&nbsp;&nbsp;&nbsp;&nbsp;需要配合[Ginkgo-JDBC框架](https://github.com/georgeworld/ginkgo-jdbc)，或者Ginkgo-SpringJDBC(稍后开源)框架来使用。
## 服务端数据对象部分
>DefaultPagingContext<BlogArticle> pagingContext = new DefaultPagingContext<BlogArticle>(sql, param);<br>
>pagingContext.setPageSize(2L);<br>

>//paging service 对象，可以手动创建，也可以使用RapidMVC内置的简单DI方式注入，或者使用任何一个你自己的依赖注入框架（比如Spring)来注入。<br>
>PagingService pagingService = JdbcPagingService.createInstanceManually();<br>

>//执行下面的doPaging(...);方法时，JDBC分页功能模块，已经把分页上下文对象，存入的HttpServletRequest 的Attribute属性中，key是：**pagingContext**<br>
>List<BlogArticle> dataList = pagingService.doPaging(this.getRequest(), pagingContext, BlogArticle.class);<br>

>//dataList是你在视图层便利的数据集列表对象<br>
>request.setAttribute("dataList", dataList);<br>

## jsp页面部分
第一步：引入jsp标签库
><%@ taglib prefix="i" uri="http://georgeinfo.com/paginationframework/tags" %>

第二步：在要显示分页栏的地方，加入分页标签
><i:jpb renderer="bootstrap" data="${pagingContext}"></i:jpb>

第三步：在页面初始化js代码中，初始化分页栏样式<br>
$(function (){

     
         //## 分页栏调用JS 开始 ##########################
         paginationBar_func(); //paginationBar是分页栏标签的varName
         //## 分页栏调用JS 结束 ##########################
 
         //## 分页栏及页面其他气泡弹出框渲染 开始 #########
         $("[data-toggle='popover']").popover();
         //## 分页栏及页面其他气泡弹出框渲染 结束 #########
    
});

**你完全可以自己实现**<br>
>com.georgeinfo.pagination.context.GenericPagingContext <br>

接口，自己来实现Ginkgo Pagination组件的服务端对象提供机制，不必依赖[Ginkgo-JDBC框架](https://github.com/georgeworld/ginkgo-jdbc)，或者Ginkgo-SpringJDBC(稍后开源)框架。
    
# 参与及讨论
  &nbsp;&nbsp;&nbsp;&nbsp;欢迎加入《互联网软件之家》QQ群：[693203950](//shang.qq.com/wpa/qunwpa?idkey=61c4589ea5618ae46d063f94cbd9394de290dd39ef46fca059a4309b8c1d7874)<br>  
  ![image](https://raw.githubusercontent.com/georgeworld/georgeworld.github.com/master/gstudio/res/img/qq_group.png) <br> 
  &nbsp;&nbsp;&nbsp;&nbsp;有问题，可以到[这里](https://github.com/georgeworld/ginkgo-pagination/issues)来反馈。
