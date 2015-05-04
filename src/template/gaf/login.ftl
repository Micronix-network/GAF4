<!DOCTYPE html>
<#assign ctx= request.contextPath/>
<#assign message= message!'login.form.insertData'/>
<#assign gui_theme=themeName!'default'/>

<meta name="contentHeight" content="450"/>

<html>
  <head>
    <title>${action.appName}</title>
    <script type="text/javascript" src="/${gui_theme}/js/jquery.js"></script>
    <link rel="stylesheet" type="text/css" href="/${gui_theme}/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/style.css">
    <link rel="icon" href="images/gwa_favicon.png" type="image/png" />
    <script type="text/javascript">
    	<#if user??>
            window.location='${ctx}/loginSuccess.action';
	</#if> 
            $(function(){
		<#if message!=''>
                    for(i=0;i<5;i++)
			$("#loginResult .resultMsg").animate({opacity: 'toggle'}, 500 );	
			$("#loginResult .resultMsg").fadeIn(500);	
                </#if>
	});
	</script>
  </head>
  <body>
<div class="page-header">  
<h1>${action.appName}</h1>	
</div>  
<div class="application_logo"><img src="images/logo.png" width="128"></div>
  
<div id="home_login">
<fieldset class="loginForm">
    <div class="display">
        <div class="display_light">
        </div>
        <p id="loginResult"><span class="resultMsg">${action.getText(message, '')}</span></p>
    </div>
    <form id="form_login" action="checkIn" method="POST">
        <dl>
                <dt><label style="font-size: 20px" for="username">${action.getText('login.form.user', 'Utente')}</label></dt>
                <dd><input class="inputTextbox" name="username" type="text" /></dd>
                <dt><label style="font-size: 20px" for="password">${action.getText('login.form.password', 'Password')}</label></dt>
                <dd><input class="inputTextbox" name="password" type="password" /></dd>
                <p style="margin-top: 10px"><input type="submit" class="btn btn-primary" value="${action.getText('login.form.button', 'Acccedi')}" style="float:right;"/></p>
        </dl>
	</form>
</fieldset>
	
</div>
<div style="clear: both;"></div>
  </body>
</html>
