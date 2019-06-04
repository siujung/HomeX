<%@ page language="java" import="java.util.*, cookie.Manage"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta charset="UTF-8">
  <title>Message</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport"
        content="width=device-width, initial-scale=1">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="stylesheet" href="assets/css/amazeui.min.css"/>
  
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/amazeui.min.js"></script>
<!-- UM相关资源 -->
<link href="assets/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="assets/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="assets/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="assets/umeditor/lang/zh-cn/zh-cn.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href='https://fonts.googleapis.com/css?family=Gloria+Hallelujah'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'
	rel='stylesheet' type='text/css'>


<link
	href="https://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
	
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	
</head>
<div id="header"></div>


<body>
	<div class="page-wrapper bg-color-1 p-t-395 p-b-120">
		<div class="wrapper wrapper--w1070">
			<div class="card card-7">
				<div class="card-body">
	<div id="main">
	<div id="ChatBox" class="am-g am-g-fixed" >
		<div class="am-u-lg-12" style="height:400px;border:1px solid #999;overflow-y:scroll;">
	
		<ul id="chatContent" class="am-comments-list am-comments-list-flip">
			<li id="msgtmp" class="am-comment" style="display:none;">
			    <a href="">
			        <img class="am-comment-avatar" src="assets/images/other.jpg" alt=""/>
			    </a>
			    <div class="am-comment-main" >
			        <header class="am-comment-hd">
			            <div class="am-comment-meta">
			              <a ff="nickname" href="#link-to-user" class="am-comment-author">Somebody</a>
			              <time ff="msgdate" datetime="" title="">2019-6-12 15:30</time>
			            </div>
			        </header>
			     <div ff="content" class="am-comment-bd">Content</div>
			    </div>
			</li>
		</ul>
	  </div>
	  </div>
	  </div>
	  </div>
	<!-- Send -->
	<div id="EditBox" class="am-g am-g-fixed">
	
	<script type="text/plain" id="myEditor" style="width:100%;height:140px;"></script>
	<button id="send" type="button" class="msg_send_btn">Send</button>
	</div>
	</div>
			</div>
		</div>

<script src="js/jquery-2.1.1.js"></script>
<script src="js/jump.js"></script>
<script type="text/javascript">



$(function(){

    var um = UM.getEditor('myEditor',{
    	initialContent:"Text Here...",
    	autoHeightEnabled:false,
    	toolbar:[
            'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
            'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
            '| justifyleft justifycenter justifyright justifyjustify |',
            'link unlink | emotion image video  | map'
        ]
    });
    
    
    var nickname = "name";
	var socket = new WebSocket("ws://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}${pageContext.request.contextPath}/websocket");
    //接收服务器的消息
    socket.onmessage=function(ev){
    	var obj = eval(   '('+ev.data+')'   );
    	addMessage(obj);
    }
    
    $("#send").click(function(){
    	if (!um.hasContents()) { 
            um.focus();
            $('.edui-container').addClass('am-animation-shake');
            setTimeout("$('.edui-container').removeClass('am-animation-shake')", 1000);
        } else {
        	var txt = um.getContent();
        	var obj = JSON.stringify({
	    		nickname:nickname,
	    		content:txt
	    	});
            socket.send(obj);
            um.setContent('');
            um.focus();
        }
    
    });
    
    
    
    
    
});

//人名nickname，时间date，是否自己isSelf，内容content
function addMessage(msg){

	var box = $("#msgtmp").clone(); 	//复制一份模板，取名为box
	box.show();							//设置box状态为显示
	box.appendTo("#chatContent");		//把box追加到聊天面板中
	box.find('[ff="nickname"]').html(msg.nickname); //在box中设置昵称
	box.find('[ff="msgdate"]').html(msg.date); 		//在box中设置时间
	box.find('[ff="content"]').html(msg.content); 	//在box中设置内容
	box.addClass(msg.isSelf? 'am-comment-flip':'');	//右侧显示
	box.addClass(msg.isSelf? 'am-comment-warning':'am-comment-success');//颜色
	box.css((msg.isSelf? 'margin-left':'margin-right'),"20%");//外边距
	
	$("#ChatBox div:eq(0)").scrollTop(999999); 	//滚动条移动至最底部
	
}


</script>

</body>
</html>
