<#macro notify_observer observers event_name card="${cardId}" param="$(this)">
    <#list observers?split(",") as observer>
        try{${observer}_controller_event(${card}_card,${param},'${event_name}');
        }catch(err){};
    </#list>
</#macro>