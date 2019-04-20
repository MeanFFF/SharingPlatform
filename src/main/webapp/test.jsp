<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/4/18
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/manage/file/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/><br>
    <label>文件名</label>
    <input type="text" name="file_name"/><br>
    <label>分类id</label>
    <input type="number" name="category_id"/><br>
    <label>详细描述</label>
    <input type="text" name="detail"/><br>
    <label>价格</label>
    <input type="number" name="price"><br>
    <input type="submit" value="upload">
</form>
</body>
</html>
