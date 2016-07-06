<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <script src="/js/lib/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
        var Params = {
            app_id: '${AppID}',
            app_name: '${AppName}',
            app_slogan: '${Download_msg}',
            app_title: '${Download_title}',
            bundle_id: '${BundleID}',
            package_name: '${Pkg}',
            uri_scheme: '${Scheme}',
            forward_url: '${Url}',
            yyb_download_url: '${yyb_download_url}',
            logo_url: '${IconUrl}',
            host: '${Host}',
            click_id: '${Match_id}',
            ios_major: '${Ios_major}',
            chrome_major: '${Chrome_major}',
            installStatus: '${AppInsStatus}',
            button_text: '${Download_btn_text}',
            visit_id: '${visitId}',
            deeplink_id: '${deepLinkId}',
            browser_fingerprint_id: '${browserFingerprintId}',
            identity_id: '${identityId}',
            is_valid_identity: '${isValidIdentity}',
            live_test_flag: '${liveTestFlag}',

            isIOS: function () {
                return 'true' == '${isIOS}';
            },
            isAndroid: function () {
                return 'true' == '${isAndroid}';
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
            isChrome: function () {
                return (this.chrome_major > 0);
            },
            isUC: function () {
                return 'true' == '${isUC}';
            },
            isMIUI: function () {
                return 'true' == '${isMIUI}';
            },
            isUniversalLink: function () {
                return 'true' == '${isUniversalLink}';
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
                return 'true' == '${isCannotGoMarket}';
            },
            isForceUseScheme: function () {
                return 'true' == '${isForceUseScheme}';
            },
            isYYBAvailable: function () {
                return 'true' == '${isYYBAvailable}' && "" !== Params.yyb_download_url && 0 !== Params.yyb_download_url && !Params.isIOS();
            }
        };
        var DEBUG = ${DEBUG};
        var lang = navigator.language;
        var isEng = /^en/.test(lang);
        if (isEng) {
            document.write("<script src='/js/en/langconfig.js'><\/script>");
        } else {
            document.write("<script src='/js/cn/langconfig.js'><\/script>");
        }
    </script>
    <script src="/js/linkedme-redirect4.js"></script>
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

