<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/11
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
<html>
<head>
    <title>AdminLogin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<br>
<br>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="text-center">
                <h1>


                    Login


                </h1>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <%--<form role="form">--%>
                <div class="form-group">

                    <label for="username">
                        输入用户名
                    </label>
                    <input type="text" class="form-control" id="username" name="username"/>
                </div>
                <div class="form-group">

                    <label for="password">
                        密码
                    </label>
                    <input type="password" class="form-control" id="password" name="password"/>
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox"/> 十天内自动登录
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" id="sub" name="sub" class="btn btn-primary">
                        登录
                    </button>
                </div>
            <%--</form>--%>

        </div>
        <div class="col-md-4">
        </div>
    </div>
</div>
<script>
    $('#sub').click(function () {
        var url = "/manage/user/login";
        $.post(
            url,
            {"username":document.getElementById("username").value,"password":document.getElementById("password").value},
            function (result) {
                if(result.status == 0)
                    window.location.href="/listCategory.jsp";
            }
        )
    });
</script>
</body>
</html>
