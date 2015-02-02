<#assign user = stack.findValue('user')/>
<#assign ok_red = false/>
<#assign ok_green = false/>
<#assign ok_yellow = false/>

<#macro writemenu menu>
<#if menu.checkRoles(user.getRoles())>
	<#if (menu.subMenues?? && menu.subMenues?size>0)>
		<li class="icon icon-left-arrow">
				<a class="icon icon-folder-open" href="#">${menu.label}</a>
				<div class="mp-level">
				<h2 class="icon icon-folder-open">${menu.label}</h2>
				<ul>
				<#list menu.subMenues as smenu>
					<@writemenu smenu/>
				</#list>	
				</ul>
				</div>
		</li>		
	<#else>
		<#if menu.active>
		    <#assign link='#'/>
		    <#assign label=menu.label!'no_label'/>
		    <#if menu.page??>
		    	<#assign link=tag.calcAction('nav_'+menu.page, null, null)/>
		    	<#assign label=tag.getText('menu.nav_'+menu.page, label)/>
		    <#elseif menu.action??>
		    	<#assign link=tag.calcAction(menu.action, null, null)/>
		    	<#assign label=tag.getText('menu.'+menu.action, label)/>
		    <#elseif menu.url??>
		    	<#assign link=menu.url/>
		    	<#assign label=tag.getText('menu.'+label, label)/>
		    </#if>	
			<li>
			<#if menu.pin??>
				<#if menu.pin='red'><#assign ok_red = true/></#if>
				<#if menu.pin='green'><#assign ok_green = true/></#if>
				<#if menu.pin='yellow'><#assign ok_yellow = true/></#if>
					<span class="menu_notify_pin ${menu.pin}_pin" style="display:none"></span>
			</#if>
			<a class="icon <#if menu.icon??>icon-${menu.icon}<#else>icon-right-arrow</#if>" href="${link}">${label}</a>
			</li>
		</#if>
	</#if>
</#if>
</#macro>

<nav id="mp-menu" class="mp-menu">
	<div class="mp-level">
	<div id="user_card">
		<span id="user_card_user">Welcome: <b>${user.userName}</b></span>
	</div>
	<h2 class="icon icon-world">Main Menu</h2>
	<ul>
	<#list mainMenu.subMenues as menu>
		<@writemenu menu/>
	</#list>
	<li class="logout_menu">
		<a class='icon icon-switch' href='<@s.url value="/checkout"/>'>Log out</a>
	</li>
	</ul>
	</div>
	
	<#if ok_red>
	    <form id="red_pin_form_post"></form>
		<@gaf.div id="red_pin_action" action="redPinAction" formId="red_pin_form_post"  listen="red_pin_refresh" startOnLoad="true"/>
	</#if>
	
	<#if ok_green>
		<form id="green_pin_form_post"></form>
		<@gaf.div id="green_pin_action" action="greenPinAction" formId="green_pin_form_post"  listen="green_pin_refresh" startOnLoad="true"/>
	</#if>
	
	<#if ok_yellow>
		<form id="yellow_pin_form_post"></form>
		<@gaf.div id="yellow_pin_action" action="yellowPinAction" formId="yellow_pin_form_post"  listen="yellow_pin_refresh" startOnLoad="true"/>
	</#if>
	
</nav>		
				