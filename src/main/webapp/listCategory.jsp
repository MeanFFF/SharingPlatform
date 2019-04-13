<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/11
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="adminNavigator.jsp" %>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
<html>
<head>
    <title>ListCategory</title>
</head>
<body>
<br>
<br>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-1">
        </div>
        <div class="col-md-10">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        Category_id
                    </th>
                    <th>
                        Category_Name
                    </th>
                    <th>
                        删除
                    </th>
                    <th>
                        修改
                    </th>
                </tr>
                </thead>
                <tbody id="messageDiv">

                </tbody>
            </table>

            <a id="modal-775553" href="#modal-container-775553" role="button" class="btn btn-primary"
               data-toggle="modal">添加分类</a>

            <div class="modal fade" id="modal-container-775553" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="myModalLabel">
                                添加分类
                            </h5>
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">×</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">

                                <label for="categoryName">
                                    输入分类名称
                                </label>
                                <input type="text" class="form-control" id="categoryName" name="categoryName"/>
                            </div>
                            <div class="form-group">

                                <label for="parentId">
                                    分类上级id
                                </label>
                                <input type="password" class="form-control" id="parentId" name="parentId"/>
                            </div>
                        </div>
                        <div class="modal-footer">

                            <button id="sub" name="sub" type="button" class="btn btn-primary">
                                确定
                            </button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                关闭
                            </button>
                        </div>
                    </div>

                </div>

            </div>

        </div>
        <div class="col-md-1">
        </div>
    </div>
</div>

<script>
    $(function () {
        var url = "/manage/category/get_category";
        $.post(
            url,
            {"categoryId": 0},
            function (result) {
                var status = result.status;
                if (status == 0){
                    var data = result.data;
                    var html = "";
                    for (var i = 0; i < data.length; i++) {
                        html += '<tr>';
                        html += '<td>' + data[i].id + '</td>';
                        html += '<td>' + data[i].name + '</td>';
                        html += '<td><a href="#">' + data[i].id + '</a></td>';
                        html += '<td><a href="#">' + data[i].id + '</a></td>';
                        html += '</tr>';
                    }
                }

                $('#messageDiv').append(html);

            }
        );
    });
    $('#sub').click(function () {
        var url = "/manage/category/add_category";
        $.post(
            url,
            {
                "categoryName": document.getElementById("categoryName").value,
                "parentId": document.getElementById("parentId").value
            },
            function (result) {
                if (result.status == 0)
                    window.location.href = "/listCategory.jsp";
            }
        )
    });
</script>
</body>
</html>
