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


<#macro double_select label name1 value1 options1 name2 value2 options2 id="prop_${cardId}" style1="" empty1=false force1=false style2="" empty2=false force2=false>
<p>
    <label for="${name1}" class="prop_label">${label}</label>	
    <#if (options1?size!=2 || force1)>			
            <select id="${id}_${name1}" class="right_input_select" name="props['${name1}']" style="${style1}">
            <#if empty1>
                    <option value></option>
            </#if>
            <#if options1?is_collection>
                    <#list options1 as key>
                            <option value="${key}" <#if value1=key>selected</#if>>${key}</option>
                    </#list>
            <#elseif options1?is_hash>
                    <#assign keys = options1?keys>
                    <#list keys as key>
                            <option value="${key}" <#if value1=key>selected</#if>>${options1[key]}</option>
                    </#list>
            </#if>	
            </select>	
            <#else>
            <#if options1?is_collection>
                    <#list options1 as key>
                            <input value="${key}" class="right_input_check" type="radio" name="props['${name1}']" <#if value1=key>checked</#if>>${key}
                    </#list>
            <#elseif options1?is_hash>
                    <#assign keys = options1?keys>
                    <#list keys as key>
                            <input value="${key}" class="right_input_check" type="radio" name="props['${name1}']" <#if value1=key>checked</#if>>${options1[key]}
                    </#list>
            </#if>	
    </#if>
    <#if (options2?size!=2 || force2)>			
            <select id="${id}_${name2}" class="right_input_select" name="props['${name2}']" style="${style2}">
            <#if empty2>
                    <option value></option>
            </#if>
            <#if options2?is_collection>
                    <#list options2 as key>
                            <option value="${key}" <#if value2=key>selected</#if>>${key}</option>
                    </#list>
            <#elseif options2?is_hash>
                    <#assign keys = options2?keys>
                    <#list keys as key>
                            <option value="${key}" <#if value1=key>selected</#if>>${options2[key]}</option>
                    </#list>
            </#if>	
            </select>	
            <#else>
            <#if options2?is_collection>
                    <#list options2 as key>
                            <input value="${key}" class="right_input_check" type="radio" name="props['${name2}']" <#if value2=key>checked</#if>>${key}
                    </#list>
            <#elseif options2?is_hash>
                    <#assign keys = options2?keys>
                    <#list keys as key>
                            <input value="${key}" class="right_input_check" type="radio" name="props['${name2}']" <#if value2=key>checked</#if>>${options1[key]}
                    </#list>
            </#if>	
    </#if>
</p>
</#macro>



<#macro multi_select label name value options id="prop_${cardId}">
    <div style="clear: both;margin: .5em 0;overflow: hidden;">
        <label for="${name}" class="prop_label_area" style="text-align: center">${label}</label>
        <div style="border: 1px solid silver">	
            <input type="hidden" name="props['${name}']" value=""/>
            <select id="${id}_${name}" name="props['${name}']" class="multiselect" size="6" multiple="true">
            <#list options as key>
                    <option value="${key}" <#if value?index_of(key)!=-1>selected</#if>>${key}</option>
            </#list>
            </select>
            <div style="clear: both;"></div>
        </div>
    </div>			
</#macro>

<#macro commons id>
<div id="${id}" style="overflow:hidden;">
    <#assign title = action.getCardParam('title')!""/>
    <@text label="${action.getText('generic.properties.dialog.title', 'CARD Title')}" name="title" value="${title}"/> 
    <@text label="${action.getText('generic.properties.dialog.wait', 'CARD to wait')}" name="waitFor" value="${action.getCardParam('waitFor')!''}"/>  
    <#assign show_help = (action.getCardParam('show_help')!'')!'false'/>
    <@double_select label="${action.getText('generic.properties.dialog.help', 'Help button')}" name1="show_help" value1="${show_help}" options1=yes_noHash name2="help_page" value2="${action.getCardParam('help_page')!''}" options2=helpPages empty2=true/>
    <#assign show_properties = (action.getCardParam('show_properties')!'')!'false'/>
    <@select label="${action.getText('generic.properties.dialog.prop', 'Properties button')}" name="show_properties" value="${show_properties}" options=yes_noHash/>
    <#assign g_note = action.getCardParam('general_note')!""/>
    <@prop.textarea label="${action.getText('generic.properties.dialog.note', 'Note')}" name="general_note" value="${g_note}"/>
    <@prop.textarea label="${action.getText('events.properties.dialog.onactive', 'On Active')}" name="card_actived_event" value="${action.getCardParam('card_actived_event')!''}" style="height:100px"/>
</div>
</#macro>

<#macro codeMirror var areaId mode>
var codeElement=document.getElementById("${areaId}");
if($(codeElement).is(":visible")){
    if (${var}==null){
        ${var} = CodeMirror.fromTextArea(codeElement, {
            mode: "${mode}",
            tabMode: "indent",
            matchBrackets: true,
            lineNumbers: true
        });
    }
}
</#macro>