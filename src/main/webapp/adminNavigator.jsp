<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/11
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
<html>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="navbar-toggler-icon"></span>
                </button> <a class="navbar-brand" href="#">Mean后台</a>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="navbar-nav">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">用户管理 <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">分类管理</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">文件管理</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">订单管理</a>
                        </li>
                    </ul>
                    <form class="form-inline">
                        <input class="form-control mr-sm-2" type="text" />
                        <button class="btn btn-primary my-2 my-sm-0" type="submit">
                            Search
                        </button>
                    </form>
                    <c:if test="${!empty currentUser}">
                        <ul class="navbar-nav ml-md-auto">
                            <li class="nav-item active">
                                <a class="nav-link" href="#">${currentUser.username}<span class="sr-only">(current)</span></a>
                            </li>
                            <li class="nav-item">
                                <a id="logout" class="nav-link" >注销 </a>
                            </li>
                        </ul>
                    </c:if>

                </div>
            </nav>
        </div>
    </div>
</div>
<script>
    $('#logout').click(function () {
        var url = "/manage/user/logout";
        $.post(
            url,
            function (result) {
                if(result.status == 0)
                    window.location.href="/adminLogin.jsp";
            }
        )
    });
</script>

</body>
</html>
