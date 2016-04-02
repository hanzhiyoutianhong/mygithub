<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!--CDN,阿里云托管-->
    <script src="<%=basePath %>/jsserver/lib/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
        var Params = {
            AppName: '${AppName}',
            Pkg: '${Pkg}',
            BundleID: '${BundleID}',
            AppID: '${AppID}',
            IconUrl: '${IconUrl}',
            Url: '${Url}',
            <!--click_id replace deeplink_id_base62-->
            Match_id: '${Match_id}',
            <!--开腔自己设置下载链接,设置应用宝下载地址-->
            Download_msg: '${Download_msg}',
            Download_btn_text: '${Download_btn_text}',
            Download_title: '${Download_title}',
            Chrome_major: '${Chrome_major}',
            Ios_major: '${Ios_major}',
            Redirect_url: '${Redirect_url}',
            YYB_url: '${YYB_url}',
            Scheme: '${Scheme}',
            Host: '${Host}',
            AppInsStatus: '${AppInsStatus}',
            TimeStamp: '${TimeStamp}',
            <!--—tracking-->
            DsTag: '${DsTag}',
            isAndroid: function () {
                return 'true' == '${isAndroid}';
            },
            isIOS: function () {
                return 'true' == '${isIOS}';
            },
            isWechat: function () {
                return 'true' == '${isWechat}';
            },
            isWeibo: function () {
                return 'true' == '${isWeibo}';
            },
            isQQ: function () {
                return 'true' == '${isQQ}';
            },
            isQQBrowser: function () {
                return 'true' == '${isQQBrowser}';
            },
            isFirefox: function () {
                return 'true' == '${isFirefox}';
            },
            isChrome: function () {
                return (this.Chrome_major > 0);
            },
            isUniversallink: function () {
                return 'true' == '${isUniversallink}';
            },
            isDownloadDirectly: function () {
                return 'true' == '${isDownloadDirectly}';
            },
            isCannotDeeplink: function () {
                return 'true' == '${isCannotDeeplink}';
            },
            isCannotGetWinEvent: function () {
                return 'true' == '${isCannotGetWinEvent}';
            },
            isCannotGoMarket: function () {
                return 'true' == '${TimeStisCannotGoMarketamp}';
            },
            isForceUseScheme: function () {
                return 'true' == '${isForceUseScheme}';
            },
            isUC: function () {
                return 'true' == '${isUC}';
            }
        };
        var DEBUG = '${DEBUG}';
        <!--测试数据,live and test-->
        var MOCK_DATA = false;
        var lang = navigator.language;
        var isEng = /^en/.test(lang);
        if (isEng) {
            document.write("<script src='./js/en/langconfig.js'><\/script>");
        } else {
            document.write("<script src='./js/cn/langconfig.js'><\/script>");
        }
    </script>
    <script src="<%=basePath %>/jsserver/linkedme-redirect.js?v=1459492344"></script>
    <title></title>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }
        a {
            text-decoration: none;
        }
        img {
            max-width: 100%;
            height: auto;
        }
        .image-tip {
            text-align: center;
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.4);
            filter: alpha(opacity=40);
            height: 100%;
            width: 100%;
            z-index: 100;
        }
    </style>
</head>
<body style="width:100%; height:100%;">
<script type="text/javascript">
    window.onload = function () {
        start();
    };
</script>
</body>

</html>