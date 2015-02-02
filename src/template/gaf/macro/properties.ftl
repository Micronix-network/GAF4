<#assign yes_noHash = {'false':'${action.getText("yes.or.no.no", "No")}','true':'${action.getText("yes.or.no.yes", "Yes")}'}/>

<#macro insel label name1 name2 value1 value2 options id="prop_${cardId}">
  <p>
		<label for="${name1}" class="prop_label">${label}</label>				
		<input id="${id}_${name1}" class="right_input" type="text" name="props['${name1}']" size="30" style="width:100px" value="${value1}"/>
		<select id="${id}_${name2}" name="props['${name2}']">
		<#assign keys = options?keys>
		<#list keys as key>
			<option value="${key}" <#if value2=key>selected</#if>>${options[key]}</option>
		</#list>
		</select>																	
	</p>				
</#macro>  

<#macro textarea label name value id="prop_${cardId}" style="">
<p>
<label for="${name}" class="prop_label_area">${label}</label>	
		<textarea id="${id}_${name}" class="prop_text_area" name="props['${name}']" style="${style}">${value}</textarea>				
</p>
</#macro>

<#macro text label name value id="prop_${cardId}" style="">
<p>
	<label for="${name}" class="prop_label">${label}</label>				
	<input id="${id}_${name}" class="right_input" type="text" name="props['${name}']" size="30" value="${value}" style="${style}"/>																	
</p>
</#macro>

<#macro select label name value options id="prop_${cardId}" style="" empty=false force=false>
<p>
	<label for="${name}" class="prop_label">${label}</label>	
	<#if (options?size!=2 || force)>			
		<select id="${id}_${name}" class="right_input_select" name="props['${name}']" style="${style}">
		<#if empty>
			<option value></option>
		</#if>
		<#if options?is_collection>
			<#list options as key>
				<option value="${key}" <#if value=key>selected</#if>>${key}</option>
			</#list>
		<#elseif options?is_hash>
			<#assign keys = options?keys>
			<#list keys as key>
				<option value="${key}" <#if value=key>selected</#if>>${options[key]}</option>
			</#list>
		</#if>	
		</select>	
		<#else>
		<#if options?is_collection>
			<#list options as key>
				<input value="${key}" class="right_input_check" type="radio" name="props['${name}']" <#if value=key>checked</#if>>${key}
			</#list>
		<#elseif options?is_hash>
			<#assign keys = options?keys>
			<#list keys as key>
				<input value="${key}" class="right_input_check" type="radio" name="props['${name}']" <#if value=key>checked</#if>>${options[key]}
			</#list>
		</#if>	
	</#if>
																	
</p>
</#macro>

<#macro multi_select label name value options id="prop_${cardId}">
	<p>
	<label for="${name}" class="prop_label" style="text-align: left">${label}</label>
	<div style="border: 1px solid silver">	
	<input type="hidden" name="props['${name}']" value=""/>
	<select id="${id}_${name}" name="props['${name}']" class="multiselect" size="6" multiple="true">
	<#list options as key>
		<option value="${key}" <#if value?index_of(key)!=-1>selected</#if>>${key}</option>
	</#list>
	</select>
	<div style="clear: both;">
   	</div>
	</div>
   	</p>			
</#macro>

<#macro commons id>
<div id="${id}" style="overflow:hidden;">
      <#assign title = action.getCardParam('title')!""/>
	  <@text label="${action.getText('generic.properties.dialog.title', 'CARD Title')}" name="title" value="${title}"/> 
	  <@text label="${action.getText('generic.properties.dialog.wait', 'CARD to wait')}" name="waitFor" value="${action.getCardParam('waitFor')!''}"/>  
	  <#assign show_help = (action.getCardParam('show_help')!'')!'false'/>
	  <@select label="${action.getText('generic.properties.dialog.help', 'Help button')}" name="show_help" value="${show_help}" options=yes_noHash/>
	  <@select label="${action.getText('generic.properties.help.page', 'Help page')}" name="help_page" value="${action.getCardParam('help_page')!''}" options=helpPages empty=true/>
	  <#assign show_properties = (action.getCardParam('show_properties')!'')!'false'/>
	  <@select label="${action.getText('generic.properties.dialog.prop', 'Properties button')}" name="show_properties" value="${show_properties}" options=yes_noHash/>
      <#assign g_note = action.getCardParam('general_note')!""/>
      <@prop.textarea label="${action.getText('generic.properties.dialog.note', 'Note')}" name="general_note" value="${g_note}"/>
      <@prop.textarea label="${action.getText('events.properties.dialog.onactive', 'On Active')}" name="card_actived_event" value="${action.getCardParam('card_actived_event')!''}" style="height:100px"/>
</div>
</#macro>

<#macro codeMirror var areaId mode>
if (${var}==null){
	${var} = CodeMirror.fromTextArea(document.getElementById("${areaId}"), {
    mode: "${mode}",
    tabMode: "indent",
    matchBrackets: true,
    lineNumbers: true
  });
}
</#macro>