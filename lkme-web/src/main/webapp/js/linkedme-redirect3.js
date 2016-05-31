/**
 * Created by LinkedME01 on 16/4/1.
 * modify at 4.15
 */
function start() {
    if ("" !== Params.button_text &&
        (downloadAPK = Params.button_text, gotoAppStore = Params.button_text),
            isIosNotAvailable()) return void gotoDivPlatformNotAvail("ios");

    if (isAndroidNotAvailable()) return void gotoDivPlatformNotAvail("android");

    if (Params.isWechat())
        DEBUG && alert("isWeChat"),
            shouldGotoYYB() ? (DEBUG && alert(Params.yyb_url), gotoUrl(Params.yyb_url)) : Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstweixintipios)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstweixintipandroid));
    else if (Params.isQQ()) DEBUG && alert("isQQ"),
        shouldGotoYYB() ? (DEBUG && alert(Params.yyb_url), gotoUrl(Params.yyb_url)) : Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstqqtipios)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstqqtipandroid));
    else if (Params.isWeibo()) DEBUG && alert("isWeibo"),
        Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstweibotipios)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstweibotipandroid));
    else if (Params.isIOS()) {
        DEBUG && alert("isIOS");
        var a = Params.uri_scheme + "://";
        if (Params.click_id && Params.click_id.length > 0 && (a += "?click_id=" + Params.click_id), DEBUG && alert(a), Params.ios_major < 9) DEBUG && alert("IOS Major below 9:" + Params.ios_major),
            iframeDeeplinkLaunch(a, 2e3,
                function () {
                    gotoUrl(Params.forward_url)
                });
        else if (DEBUG && alert("IOS Major upper 9:" + Params.ios_major), Params.isChrome()) DEBUG && alert("isChrome"),
            chiosDeeplinkLaunch(a,
                function () {
                    gotoUrl(Params.forward_url)
                });
        else if (Params.isUniversalLink()) if (DEBUG && alert("isUniversalLink = true"), env.cookieEnabled()) switch (DEBUG && alert("cookie Enabled; installStatus:" + Params.installStatus), parseInt(Params.installStatus, 10)) {
            case CONST_APP_INS_STATUS.Installed:
                return void gotoIOSLandingPage();
            case CONST_APP_INS_STATUS.NotInstall:
                return void gotoUrl(Params.forward_url);
            case CONST_APP_INS_STATUS.Unclear:
                return void gotoIOSLandingPage();
            default:
                return void gotoIOSLandingPage()
        } else DEBUG && alert("cookie Not Enabled"),
            gotoIOSLandingPage();
        else DEBUG && alert("is safari"),
                deeplinkLaunch(a, 2500,
                    function () {
                        gotoUrl(Params.forward_url)
                    })
    } else if (Params.isAndroid())
        if (DEBUG && alert("isAndroid"), a = Params.uri_scheme + "://" + Params.host, Params.click_id && Params.click_id.length > 0 && (a += "?click_id=" + Params.click_id),
            DEBUG && alert(a), Params.isCannotDeeplink()) iframeDeeplinkLaunch(a, 10e3,
            function () {
                gotoCannotDeeplink()
            });
        else if (Params.isQQBrowser()) DEBUG && alert("QQ browser"),
            shouldGotoYYB() ? (DEBUG && alert(Params.yyb_url), gotoUrl(Params.yyb_url)) : gotoCannotDeeplink();
        else if (Params.isUC()) DEBUG && alert("UC browser"),
            gotoUC(a);
        else if (Params.isChrome() && Params.chrome_major >= 25 && !Params.isForceUseScheme()) {
            DEBUG && alert("chrome_major:" + Params.chrome_major);
            var b = Params.host;
            Params.click_id && Params.click_id.length > 0 && (b += "?click_id=" + Params.click_id);
            var c = Params.package_name,
                d = "intent://" + b + "#Intent;scheme=" + Params.uri_scheme + ";package=" + c + ";S.browser_fallback_url=" + Params.forward_url + ";end";
            alert("d=" + d);
            deeplinkLaunch(d, 2e3,
                function () {
                    gotoAndroidNewInstall()
                })
        } else DEBUG && alert("default browser"),
            iframeDeeplinkLaunch(a, 2e3,
                function () {
                    gotoAndroidNewInstall()
                })
}
var winWidth = $(window).width(),
    winHeight = $(window).height(),
    VISIT_ID = "visit_id",
    deeplinkLocation = "",
    dstLocation = "",
    dsAction = {
        trackingUrl: "/i/v2/dsactions/",
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
        reportDSJSEvent: function (a, b) {
            var c = {
                    action: a,
                    kvs: {
                        click_id: Params.click_id,
                        destination: b,
                        visit_id: Params.visitId
                    }
                },
                d = JSON.stringify(c);
            $.post(this.trackingUrl + Params.app_id, d,
                function (a) {
                }).error(function () {
            })
        },
        reportDSJSUserClickEvent: function (a, b, c) {
            var d = {
                    action: a,
                    kvs: {
                        click_id: Params.click_id,
                        user_btn: b,
                        user_choice: c,
                        visit_id: Params.visitId
                    }
                },
                e = JSON.stringify(d);
            $.post(this.trackingUrl + Params.app_id, e,
                function (a) {
                }).error(function () {
            })
        }
    },
    CONST_APP_INS_STATUS = {
        NotInstall: 0,
        Installed: 1,
        Unclear: 2
    },

    weixinTipTemplate = '<div class="image-tip" width="100%" height="100%" style="position:relative;">    <div style="background-color:#ffffff;width:100%;height:100%;position:absolute; top:0;">        {img_tip}    </div>    <div style="text-align:center; width:100%; position:absolute; top:67%">        </div></div>',
    imgInfo = "<img src=" + basePathLang + 'open_{mobile-os}_browser.png align="center" style="height: 100%;"/>',
    div_goto_landingpage = '<div style="background-image:url({Bg_Url});background-size: 100% 100%;width:100%;height:100%;">    <div style = "position:absolute; top:20%; width:100%; ">        <div style="text-align:center; width:100%; ">            <img id="appIcon" src={logo_url} style="width:22%;"/>        </div>        <div style="text-align:center; width:100%; margin-top:10px;">            <span id="appName" style="font-size: 1.5em; color: #959595; padding: 15px 10px;">                {app_name}            </span>        </div>    </div>    <div style="text-align:center; width:100%; position:absolute; top:56%;">        <span id="downloadTitle" style="font-size: 1em; color: #959595; padding: 15px 10px;">            {Download_title}        </span>    </div>    <div style="text-align:center; width:100%; position:absolute; top:59%;">    </div>    <div style="text-align:center; width:100%; position:absolute; top:70%;">        <{Element_type} id="btnGotoLandingPage" style="background-color:#FFFFFF; border: {Border_width}px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">{Btn_landingpage_text}</{Element_type}>    </div></div>',
    div_goto_cannot_deeplink_with_market_btn = '<div style="background-image:url(' + basePathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidMarket" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + gotoStore + "</button>    </div></div>",
    div_goto_cannot_deeplink_with_download_btn = '<div style="background-image:url(' + basePathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidDownload" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + downloadAPK + "</button>    </div></div>",
    div_allow_me_deeplink = '<div style="background-image:url(' + basePathLang + 'open_app.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:35%;">        <p id="textCountDown" style="font-size: 1em; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;"></p>    </div></div>',

    div_platform_not_available = '<div style="background-image:url(' + basePathLang + 'no_{Platform}.png);background-size: 100% 100%;width:100%;height:100%;"></div>',

    env = {
        windowLocation: function (a) {
            window.location = a
        },
        windowOpen: function (a) {
            window.open(a)
        },
        windowClose: function () {
            window.close()
        },
        windowChangeHistory: function () {
            window.history.replaceState("Object", "Title", "0")
        },
        windowAddEventListener: function (a, b) {
            window.addEventListener(a, b)
        },
        windowUrlAddTag: function () {
            if (window.location.search.indexOf(VISIT_ID) < 0) {
                var a = Math.floor(1e6 * Math.random()),
                    b = VISIT_ID + "=" + a;
                if (window.location.search == undefined || window.location.search != "") {
                    window.location.search += "&" + b;
                } else {
                    window.location.search = "?" + b;
                }
                DEBUG && alert("Url Add Tag:" + b)
            }
        },
        cookieEnabled: function () {
            var a = !1;
            try {
                localStorage.test = 2
            } catch (b) {
                DEBUG && alert("private mode"),
                    a = !0
            }
            return navigator.cookieEnabled && !a
        }
    },
    clearTimeoutOnPageUnload = function (a) {
        env.windowAddEventListener("pagehide",
            function () {
                DEBUG && alert("window event pagehide"),
                    clearTimeout(a),
                    env.windowChangeHistory()
            }),
            env.windowAddEventListener("blur",
                function () {
                    DEBUG && alert("window event blur"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            env.windowAddEventListener("unload",
                function () {
                    DEBUG && alert("window event unload"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            document.addEventListener("webkitvisibilitychange",
                function () {
                    DEBUG && alert("window event webkitvisibilitychange"),
                    document.webkitHidden && (clearTimeout(a), env.windowChangeHistory())
                }),
            env.windowAddEventListener("beforeunload",
                function () {
                    DEBUG && alert("window event beforeunload")
                }),
            env.windowAddEventListener("focus",
                function () {
                    DEBUG && alert("window event focus")
                }),
            env.windowAddEventListener("focusout",
                function () {
                    DEBUG && alert("window event focusout"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                })
    },
    gotoTip = function (a, b) {
        weixin_tip = weixinTipTemplate.replace(/{img_tip}/g, imgInfo).replace(/{logo_url}/g, Params.logo_url).replace(/{mobile-os}/g, a),
            $("body").append(weixin_tip),
            $(".image-tip").show(),
            env.windowUrlAddTag(),
            dstLocation = b,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoCannotDeeplink = function () {
        DEBUG && alert("cannot deeplink"),
            Params.isDownloadDirectly() ? ($("body").append(div_goto_cannot_deeplink_with_download_btn), $("#btnGotoAndroidDownload").click(function () {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.forward_url)
            })) : ($("body").append(div_goto_cannot_deeplink_with_market_btn), $("#btnGotoAndroidMarket").click(function () {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })),
            dstLocation = dsAction.destination.dstcannotdeeplink,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoAndroidNewInstall = function () {
        Params.isDownloadDirectly() ? gotoAndroidDownloadLandingPage() : Params.isCannotGoMarket() ? gotoAndroidCannotGoMarketLandingPage() : Params.isCannotGetWinEvent() || Params.isUC() ? gotoAndroidMarketLandingPage() : gotoAndroidMarket()
    },
    gotoIOSLandingPage = function () {
        dstLocation = dsAction.destination.dstios9UniversalLinkLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, basePathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function () {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoIosAppStore", "yes"),
                    gotoUrl(Params.forward_url)
            })
    },
    gotoAndroidMarketLandingPage = function () {
        dstLocation = dsAction.destination.dstandroidMarketLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, basePathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function () {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })
    },
    gotoAndroidCannotGoMarketLandingPage = function () {
        dstLocation = dsAction.destination.dstandroidCannotGoMarketLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, basePathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, openStore).replace(/{Border_width}/g, "0").replace(/{Element_type}/g, "p"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoAndroidDownloadLandingPage = function () {
        dstLocation = dsAction.destination.dstandroidDirectDownloadLandPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, basePathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, downloadAPK).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function () {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.forward_url)
            })
    },
    gotoUC = function (a) {
        dstLocation = dsAction.destination.dstucbrowser;
        var b = $("body").html();
        $("body").append(div_allow_me_deeplink);
        var c = 6,
            d = function () {
                c--,
                    $("#textCountDown").html(c),
                    0 === c ? ($("#textCountDown").html(""), iframeDeeplinkLaunch(a, 10e3,
                        function () {
                            $("body").html(b),
                                gotoAndroidNewInstall()
                        })) : setTimeout(d, 1e3)
            };
        d()
    },
    gotoAndroidMarket = function () {
        env.windowChangeHistory(),
            dstLocation = "market://details?id=" + Params.package_name,
        DEBUG && alert(dstLocation),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            env.windowLocation(dstLocation)
    },
    gotoUrl = function (a) {
        env.windowChangeHistory(),
            dstLocation = a,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, a),
            env.windowLocation(a)
    },
    gotoDivPlatformNotAvail = function (a) {
        div_platform_NA = div_platform_not_available.replace(/{Platform}/g, a),
            $("body").append(div_platform_NA),
            dstLocation = dsAction.destination.dstplatformNA.replace(/{Platform}/g, a),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    deeplinkLaunch = function (a, b, c) {
        deeplinkLocation = a,
        DEBUG && alert(deeplinkLocation),
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a),
            // a = "intent://linkedme?click_id=sWpK2qR01#Intent;scheme=linkedmedemo;package=com.microquation.linkedme.android;S.browser_fallback_url=https://www.baidu.com;end";
            env.windowLocation(a);
        var d = setTimeout(function () {
                c()
            },
            b);
        clearTimeoutOnPageUnload(d)
    },
    chiosDeeplinkLaunch = function (a, b) {
        deeplinkLocation = a;
        var c = null;
        try {
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a),
                c = env.windowOpen(a),
            DEBUG && alert("pass"),
                env.windowChangeHistory()
        } catch (d) {
            DEBUG && alert("exception")
        }
        c ? env.windowClose() : b()
    },
    iframeDeeplinkLaunch = function (a, b, c) {
        var d = document.createElement("iframe");
        d.style.width = "1px",
            d.style.height = "1px",
            d.border = "none",
            d.style.display = "none",
            d.src = a,
            document.body.appendChild(d),
            deeplinkLocation = a,
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a);
        var e = setTimeout(function () {
                c()
            },
            b);
        clearTimeoutOnPageUnload(e)
    },
    isIosNotAvailable = function () {
        <!--void 0 和undefined-->
        return Params.isIOS() && (void 0 === Params.bundle_id || "" === Params.bundle_id)
    },
    isAndroidNotAvailable = function () {
        return Params.isAndroid() && (void 0 === Params.package_name || "" === Params.package_name)
    },
    shouldGotoYYB = function () {
        return void 0 !== Params.yyb_url && "" !== Params.yyb_url && !Params.isIOS()
    };
