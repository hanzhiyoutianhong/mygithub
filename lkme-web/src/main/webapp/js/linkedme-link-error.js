/**
 * Created by vontroy on 2016/6/12.
 */
function begin() {
    if( ErrorParams.invalidLink() ) {
        goto_invalid_link_page();
    }
}
var invalid_link_template = '<div style="background-image:url(' + baseImgPathLang + 'invalid_link.jpg);background-size: 100% 100%;width:100%;height:100%;"></div>',
    goto_invalid_link_page = function () {
        gotoErrorPage();
    },
    gotoErrorPage = function () {
        $("body").append(invalid_link_template);
    }