//Show Loading
function g_showLoading(){
	return layer.msg('Processing...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;
}
//salt
var g_passsword_salt="ILOVEIRENE";

//Max image size
var IMAGE_MAX_SIZE = 4096 * 1000;
var NOT_GIVEN = -8;

// Get Parameter from url
function g_getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
}
//设定时间格式化函数，使用new Date().format("yyyyMMddhhmmss");  
Date.prototype.format = function (format) {  
    var args = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds()
    };  
    if (/(y+)/.test(format))  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var i in args) {  
        var n = args[i];  
        if (new RegExp("(" + i + ")").test(format))  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));  
    }  
    return format;  
};

function logout() {
    $.ajax({
        url: "/user/logout",
        method: "POST",
        data: {},
        success: function (data) {
            if(data.code == 0){
                window.location.href = "/user/tologin";
            }else{
                //TODO Protection method, user is not login but doing some service
                window.location.href = "/user/tologin";
            }
        },
        error: function (data) {
            $.post("/error/toError",
                {
                    msg: "Wow, something bad happened."
                }
            )
        }
    });
}

function trashMsg() {
    var msgId =  $("#trashFinalBtn").attr("data-id");

    $.ajax({
        url: "/message/trash/" + msgId,
        method: "POST",
        data:{},
        success: function (data) {
            if(data.code == 0){
                window.location.href = "/message/list"
            }else{
                window.location.href = "/error/toError"
            }
        },
        error: function (data) {
            window.location.href = "/error/toError"
        }
    })
}

function blockRender() {
    $("#meta").load("/static/meta.htm");
    $("#cssinclude").load("/static/cssinclude.htm");
    $("#footer").load("/static/footer.htm");
    $("#logout").load("/static/logout.htm");
    $("#jsinclude").load("/static/jsinclude.htm");
}