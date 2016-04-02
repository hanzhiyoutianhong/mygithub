/**
 * Created by LinkedME01 on 16/4/1.
 */
function start() {
    if ("" === Params.Download_msg && (Params.Download_msg = ResDefaultDownloadMsg),
        "" !== Params.Download_btn_text &&
        (ResDownloadAPK = Params.Download_btn_text, ResGotoAppStoreDownload = Params.Download_btn_text),
            isIosNotAvailable()) return void gotoDivPlatformNotAvail("ios");

    if (isAndroidNotAvailable()) return void gotoDivPlatformNotAvail("android");

    if (Params.isWechat())
        DEBUG && alert("isWeChat"),
            shouldGotoYYB() ? (DEBUG && alert(Params.YYB_url), gotoUrl(Params.YYB_url)) : Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstweixintipios)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstweixintipandroid));
    else if (Params.isQQ()) DEBUG && alert("isQQ"),
        shouldGotoYYB() ? (DEBUG && alert(Params.YYB_url), gotoUrl(Params.YYB_url)) : Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstqqtipios)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstqqtipandroid));
    else if (Params.isWeibo()) DEBUG && alert("isWeibo"),
        Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstweibotipios)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstweibotipandroid));
    else if (Params.isIOS()) {
        DEBUG && alert("isIOS");
        var a = Params.Scheme + "://";
        if (Params.Match_id && Params.Match_id.length > 0 && (a += "?click_id=" + Params.Match_id), DEBUG && alert(a), Params.Ios_major < 9) DEBUG && alert("IOS Major below 9:" + Params.Ios_major),
            iframeDeeplinkLaunch(a, 2e3,
                function() {
                    gotoUrl(Params.Url)
                });
        else if (DEBUG && alert("IOS Major upper 9:" + Params.Ios_major), Params.isChrome()) DEBUG && alert("isChrome"),
            chiosDeeplinkLaunch(a,
                function() {
                    gotoUrl(Params.Url)
                });
        else if (Params.isUniversallink()) if (DEBUG && alert("isUniversallink = true"), env.cookieEnabled()) switch (DEBUG && alert("cookie Enabled; AppInsStatus:" + Params.AppInsStatus), parseInt(Params.AppInsStatus, 10)) {
            case CONST_APP_INS_STATUS.Installed:
                return void gotoIOSLandingPage();
            case CONST_APP_INS_STATUS.NotInstall:
                return void gotoUrl(Params.Url);
            case CONST_APP_INS_STATUS.Unclear:
                return void gotoIOSLandingPage();
            default:
                return void gotoIOSLandingPage()
        } else DEBUG && alert("cookie Not Enabled"),
            gotoIOSLandingPage();
        else DEBUG && alert("is safari"),
                deeplinkLaunch(a, 500,
                    function() {
                        gotoUrl(Params.Url)
                    })
    } else if (Params.isAndroid())
        if (DEBUG && alert("isAndroid"), a = Params.Scheme + "://" + Params.Host, Params.Match_id && Params.Match_id.length > 0 && (a += "?click_id=" + Params.Match_id),
            DEBUG && alert(a), Params.isCannotDeeplink()) iframeDeeplinkLaunch(a, 10e3,
        function() {
            gotoCannotDeeplink()
        });
    else if (Params.isQQBrowser()) DEBUG && alert("QQ browser"),
        shouldGotoYYB() ? (DEBUG && alert(Params.YYB_url), gotoUrl(Params.YYB_url)) : gotoCannotDeeplink();
    else if (Params.isUC()) DEBUG && alert("UC browser"),
        gotoUC(a);
    else if (Params.isChrome() && Params.Chrome_major >= 25 && !Params.isForceUseScheme()) {
        DEBUG && alert("Chrome_major:" + Params.Chrome_major);
        var b = Params.Host;
        alert("123");
        Params.Match_id && Params.Match_id.length > 0 && (b += "?click_id=" + Params.Match_id);
        var c = Params.Pkg,
            d = "intent://" + b + "#Intent;scheme=" + Params.Scheme + ";package=" + c + ";S.browser_fallback_url=" + Params.Url + ";end";
            alert("d=" + d);
        deeplinkLaunch(d, 2e3,
            function() {
                alert(234);
                gotoAndroidNewInstall()
            })
    } else DEBUG && alert("default browser"),
        iframeDeeplinkLaunch(a, 2e3,
            function() {
                gotoAndroidNewInstall()
            })
}
var winWidth = $(window).width(),
    winHeight = $(window).height(),
    CONST_DS_TAG = "ds_tag",
    deeplinkLocation = "",
    dstLocation = "",
    dsAction = {
        trackingUrl: "/v1/dsactions/",
        actionJSDeepLink: "js/deeplink",
        actionJSDst: "js/dst",
        actionJSUserClick: "js/userclick",
        destination: {
            dstweixintipandroid: "dst-weixin-tip-android",
            dstweixintipios: "dst-weixin-tip-ios",
            dstqqtipandroid: "dst-qq-tip-android",
            dstqqtipios: "dst-qq-tip-ios",
            dstweibotipandroid: "dst-weibo-tip-android",
            dstweibotipios: "dst-weibo-tip-ios",
            dstcannotdeeplink: "dst-cannot-deeplink",
            dstucbrowser: "dst-uc-browser",
            dstios9UniversalLinkLandPage: "dst-ios9-universallink-landpage",
            dstandroidDirectDownloadLandPage: "dst-android-direct-download-landpage",
            dstandroidMarketLandPage: "dst-android-market-landpage",
            dstandroidCannotGoMarketLandPage: "dst-android-cannot-gomarket-landpage",
            dstplatformNA: "dst-{Platform}-not-available"
        },
        <!--a = js/dst, b = dst-ios-not-available-->
        reportDSJSEvent: function(a, b) {
            var c = {
                    action: a,
                    kvs: {
                        click_id: Params.Match_id,
                        destination: b,
                        ds_tag: Params.DsTag
                    }
                },
                d = JSON.stringify(c);
            $.post(this.trackingUrl + Params.AppID, d,
                function(a) {}).error(function() {})
        },
        reportDSJSUserClickEvent: function(a, b, c) {
            var d = {
                    action: a,
                    kvs: {
                        click_id: Params.Match_id,
                        user_btn: b,
                        user_choice: c,
                        ds_tag: Params.DsTag
                    }
                },
                e = JSON.stringify(d);
            $.post(this.trackingUrl + Params.AppID, e,
                function(a) {}).error(function() {})
        }
    },
    CONST_APP_INS_STATUS = {
        NotInstall: 0,
        Installed: 1,
        Unclear: 2
    },

    weixinTipTemplate = '<div class="image-tip" width="100%" height="100%" style="position:relative;">    <div style="background-color:#ffffff;width:100%;height:100%;position:absolute; top:0;">        {img_tip}    </div>    <div style="text-align:center; width:100%; position:absolute; top:67%">         <img id="appIcon" src={Icon_Url} style="height:10%;"/>    </div></div>',
    imgInfo = "<img src=" + ResPathLang + 'openbrowser_{mobile-os}.png align="center" style="height: 100%;"/>',
    div_goto_landingpage = '<div style="background-image:url({Bg_Url});background-size: 100% 100%;width:100%;height:100%;">    <div style = "position:absolute; top:20%; width:100%; ">        <div style="text-align:center; width:100%; ">            <img id="appIcon" src={Icon_Url} style="width:22%;"/>        </div>        <div style="text-align:center; width:100%; margin-top:10px;">            <span id="appName" style="font-size: 1.5em; color: #959595; padding: 15px 10px;">                {App_Name}            </span>        </div>    </div>    <div style="text-align:center; width:100%; position:absolute; top:56%;">        <span id="downloadTitle" style="font-size: 1em; color: #959595; padding: 15px 10px;">            {Download_title}        </span>    </div>    <div style="text-align:center; width:100%; position:absolute; top:59%;">        <span id="downloadMsg" style="font-size: 1em; border-bottom: 1px solid #959595; color: #959595; padding: 6px 10px;">            {Download_msg}        </span>    </div>    <div style="text-align:center; width:100%; position:absolute; top:70%;">        <{Element_type} id="btnGotoLandingPage" style="background-color:#FFFFFF; border: {Border_width}px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">{Btn_landingpage_text}</{Element_type}>    </div></div>',
    div_goto_cannot_deeplink_with_market_btn = '<div style="background-image:url(' + ResPathLang + 'oops.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidMarket" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + ResGotoAppStore + "</button>    </div></div>",
    div_goto_cannot_deeplink_with_download_btn = '<div style="background-image:url(' + ResPathLang + 'oops.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidDownload" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + ResDownloadAPK + "</button>    </div></div>",
    div_allow_me_deeplink = '<div style="background-image:url(' + ResPathLang + 'allowme.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:35%;">        <p id="textCountDown" style="font-size: 1em; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;"></p>    </div></div>',

    div_platform_not_available = '<div style="background-image:url(' + ResPathLang + '{Platform}_not_avail.png);background-size: 100% 100%;width:100%;height:100%;"></div>',

    env = {
        windowLocation: function(a) {
            window.location = a
        },
        windowOpen: function(a) {
            window.open(a)
        },
        windowClose: function() {
            window.close()
        },
        windowChangeHistory: function() {
            window.history.replaceState("Object", "Title", "0")
        },
        windowAddEventListener: function(a, b) {
            window.addEventListener(a, b)
        },
        windowUrlAddTag: function() {
            if (window.location.search.indexOf(CONST_DS_TAG) < 0) {
                var a = Math.floor(1e6 * Math.random()),
                    b = "?" + CONST_DS_TAG + "=" + a;
                window.location.search = b,
                DEBUG && alert("Url Add Tag:" + b)
            }
        },
        cookieEnabled: function() {
            var a = !1;
            try {
                localStorage.test = 2
            } catch(b) {
                DEBUG && alert("private mode"),
                    a = !0
            }
            return navigator.cookieEnabled && !a
        }
    },
    clearTimeoutOnPageUnload = function(a) {
        env.windowAddEventListener("pagehide",
            function() {
                DEBUG && alert("window event pagehide"),
                    clearTimeout(a),
                    env.windowChangeHistory()
            }),
            env.windowAddEventListener("blur",
                function() {
                    DEBUG && alert("window event blur"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            env.windowAddEventListener("unload",
                function() {
                    DEBUG && alert("window event unload"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            document.addEventListener("webkitvisibilitychange",
                function() {
                    DEBUG && alert("window event webkitvisibilitychange"),
                    document.webkitHidden && (clearTimeout(a), env.windowChangeHistory())
                }),
            env.windowAddEventListener("beforeunload",
                function() {
                    DEBUG && alert("window event beforeunload")
                }),
            env.windowAddEventListener("focus",
                function() {
                    DEBUG && alert("window event focus")
                }),
            env.windowAddEventListener("focusout",
                function() {
                    DEBUG && alert("window event focusout"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                })
    },
    gotoTip = function(a, b) {
        weixin_tip = weixinTipTemplate.replace(/{img_tip}/g, imgInfo).replace(/{Icon_Url}/g, Params.IconUrl).replace(/{mobile-os}/g, a),
            $("body").append(weixin_tip),
            $(".image-tip").show(),
            env.windowUrlAddTag(),
            dstLocation = b,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoCannotDeeplink = function() {
        DEBUG && alert("cannot deeplink"),
            Params.isDownloadDirectly() ? ($("body").append(div_goto_cannot_deeplink_with_download_btn), $("#btnGotoAndroidDownload").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.Url)
            })) : ($("body").append(div_goto_cannot_deeplink_with_market_btn), $("#btnGotoAndroidMarket").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })),
            dstLocation = dsAction.destination.dstcannotdeeplink,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoAndroidNewInstall = function() {
        Params.isDownloadDirectly() ? gotoAndroidDownloadLandingPage() : Params.isCannotGoMarket() ? gotoAndroidCannotGoMarketLandingPage() : Params.isCannotGetWinEvent() || Params.isUC() ? gotoAndroidMarketLandingPage() : gotoAndroidMarket()
    },
    gotoIOSLandingPage = function() {
        dstLocation = dsAction.destination.dstios9UniversalLinkLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, ResPathLang + "bg_ul.png").replace(/{App_Name}/g, Params.AppName).replace(/{Icon_Url}/g, Params.IconUrl).replace(/{Download_title}/g, Params.Download_title).replace(/{Download_msg}/g, Params.Download_msg).replace(/{Btn_landingpage_text}/g, ResGotoAppStoreDownload).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoIosAppStore", "yes"),
                    gotoUrl(Params.Url)
            })
    },
    gotoAndroidMarketLandingPage = function() {
        dstLocation = dsAction.destination.dstandroidMarketLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, ResPath + "bg1.png").replace(/{App_Name}/g, Params.AppName).replace(/{Icon_Url}/g, Params.IconUrl).replace(/{Download_title}/g, Params.Download_title).replace(/{Download_msg}/g, Params.Download_msg).replace(/{Btn_landingpage_text}/g, ResGotoAppStoreDownload).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })
    },
    gotoAndroidCannotGoMarketLandingPage = function() {
        dstLocation = dsAction.destination.dstandroidCannotGoMarketLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, ResPath + "bg1.png").replace(/{App_Name}/g, Params.AppName).replace(/{Icon_Url}/g, Params.IconUrl).replace(/{Download_title}/g, Params.Download_title).replace(/{Download_msg}/g, Params.Download_msg).replace(/{Btn_landingpage_text}/g, ResPleaseOpenAppStore).replace(/{Border_width}/g, "0").replace(/{Element_type}/g, "p"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoAndroidDownloadLandingPage = function() {
        dstLocation = dsAction.destination.dstandroidDirectDownloadLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, ResPath + "bg1.png").replace(/{App_Name}/g, Params.AppName).replace(/{Icon_Url}/g, Params.IconUrl).replace(/{Download_title}/g, Params.Download_title).replace(/{Download_msg}/g, Params.Download_msg).replace(/{Btn_landingpage_text}/g, ResDownloadAPK).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.Url)
            })
    },
    gotoUC = function(a) {
        dstLocation = dsAction.destination.dstucbrowser;
        var b = $("body").html();
        $("body").append(div_allow_me_deeplink);
        var c = 6,
            d = function() {
                c--,
                    $("#textCountDown").html(c),
                    0 === c ? ($("#textCountDown").html(""), iframeDeeplinkLaunch(a, 10e3,
                        function() {
                            $("body").html(b),
                                gotoAndroidNewInstall()
                        })) : setTimeout(d, 1e3)
            };
        d()
    },
    gotoAndroidMarket = function() {
        env.windowChangeHistory(),
            dstLocation = "market://details?id=" + Params.Pkg,
        DEBUG && alert(dstLocation),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            env.windowLocation(dstLocation)
    },
    gotoUrl = function(a) {
        env.windowChangeHistory(),
            dstLocation = a,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, a),
            env.windowLocation(a)
    },
    gotoDivPlatformNotAvail = function(a) {
            div_platform_NA = div_platform_not_available.replace(/{Platform}/g, a),
            $("body").append(div_platform_NA),
            dstLocation = dsAction.destination.dstplatformNA.replace(/{Platform}/g, a),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    deeplinkLaunch = function(a, b, c) {
        deeplinkLocation = a,
        DEBUG && alert(deeplinkLocation),
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a),
            env.windowLocation(a);
        var d = setTimeout(function() {
                c()
            },
            b);
        clearTimeoutOnPageUnload(d)
    },
    chiosDeeplinkLaunch = function(a, b) {
        deeplinkLocation = a;
        var c = null;
        try {
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a),
                c = env.windowOpen(a),
            DEBUG && alert("pass"),
                env.windowChangeHistory()
        } catch(d) {
            DEBUG && alert("exception")
        }
        c ? env.windowClose() : b()
    },
    iframeDeeplinkLaunch = function(a, b, c) {
        var d = document.createElement("iframe");
        d.style.width = "1px",
            d.style.height = "1px",
            d.border = "none",
            d.style.display = "none",
            d.src = a,
            document.body.appendChild(d),
            deeplinkLocation = a,
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a);
        var e = setTimeout(function() {
                alert("chenhao"),
                c()
            },
            b);
        clearTimeoutOnPageUnload(e)
    },
    isIosNotAvailable = function() {
        <!--void 0 和undefined-->
        return Params.isIOS() && (void 0 === Params.BundleID || "" === Params.BundleID)
    },
    isAndroidNotAvailable = function() {
        return Params.isAndroid() && (void 0 === Params.Pkg || "" === Params.Pkg)
    },
    shouldGotoYYB = function() {
        return void 0 !== Params.YYB_url && "" !== Params.YYB_url && !Params.isIOS()
    };