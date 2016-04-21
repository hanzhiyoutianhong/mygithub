<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    //String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String basePath = "https://lkme.cc/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>应用方</title>
    <meta charset="utf-8" />
    <link href="<%=basePath %>demoh5/CSS/LinkedMe.css" rel="stylesheet" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div id="lb" class="banner">
    <div class="banner_left" onclick="javascript:location.href = '#';">
        <!--<div style="display:inline-block">
                <img class="close_img" src="<%=basePath %>demoh5/Images/Close.png" onclick="javascript:document.getElementById('lb').style.display='none';" />
            </div>-->
        <div style="display:inline-block;vertical-align:middle;">
            <img class="banner_img" src="<%=basePath %>demoh5/Images/LinkedME.png" />
        </div>
        <div class="banner_text">
            LinkedME 跨平台链接
        </div>
    </div>
    <div style="float:right;margin-top:12px;top:0;">
        <a href="" id="link">
            <img src="<%=basePath %>demoh5/Images/open.png" height="30" style="vertical-align:middle;margin-right:5px;" />
        </a>
        <script>
            var btn = document.getElementById('link');
            var search = location.search;
            var linkedme = /^\?linkedme=(.*)/.exec(search);
            linkedme && btn.setAttribute('href', linkedme[1]);
        </script>
    </div>
    <div style="clear:both"></div>
</div>
    
    
    <div class="div_center">
        <div class="app_row">
            <div class="app_icon_left">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/36kr.png" />
            </div>
            <div class="app_icon_right">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/juejin.png" />
            </div>
        </div>
        <div class="app_row">
            <div class="app_icon_left">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/daokoudai.png" />
            </div>
            <div class="app_icon_right">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/xiaofanzhuo.png" />
            </div>
        </div>
        <div class="app_row">
            <div class="app_icon_left">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/youya.png" />
            </div>
            <div class="app_icon_right">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/meitian.png" />
            </div>
        </div>
        <div class="app_row">
            <div class="app_icon_left">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/wanjubeijing.png" />
            </div>
            <div class="app_icon_right">
                <img class="app_img" src="<%=basePath %>demoh5/Images/APP/jiniu.png" />
            </div>
        </div>
        <div class="app_row">
            <div class="app_more">
                更多应用集成中...
            </div>
        </div>
    </div>
    <div class="footer">
        Copyright@2014-2015 LinkedME 北京微方程科技有限公司
    </div>
</body>
</html>
