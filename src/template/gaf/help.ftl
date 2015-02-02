<#if helpPage?? && helpPage!="">
	<#assign helpFile="/WEB-INF/view/help_pages/${helpPage}.ftl"/>
	<#attempt>
		<#include helpFile/>
	<#recover>		
	  	<h1>Error: fragment loading</h1>
	  	<div class="fragment_error">${.error}</div>
	</div>
	</#attempt>
<#else>
	<h1>No help page</h1>
</#if>	
