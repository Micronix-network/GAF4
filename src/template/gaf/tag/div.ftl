<#assign super = stack.findValue('superUser')/>
<#if stack.findValue('cardId')??>
<#assign cardId = stack.findValue('cardId')/>
</#if>
<#assign ctx= req.contextPath/>

<script type="text/javascript">

	function loadContent_${id}(){
            var params={};
            <#if formId??>
                $('#${formId} [name=cardId]').remove();
                params=$('#${formId}').serializeArray();
                <#if cardId??>
                    params.push({name:'cardId',value:'${cardId}'});
                </#if>
            </#if>
            $('#${id}_content').load('<#if directUrl??>${directUrl}<#else>${tag.calcAction()}</#if>',params, 
            function() {
                $('#${id}').find('.rotor.ajax_spinner').remove();
            }); 
	}
	
	function clearContent_${id}(){
            $('#${id}_content').fadeOut('fast',function(){
                $('#${id}_content').empty();
                $('#${id}_content').show();
            });
		
	}

	function ajaxCall_${id}(){
	<#if loadImage??>
            <#if loadImage=='true'>
                <#assign loadImage = 'spinner-6'/>
            </#if>

            var $rotor=$('<div class="rotor ajax_spinner rotor-${loadImage}"></div>');
            if(Modernizr.csstransitions){
                $('#${id}').append($rotor);
            }else{
                $('#${id}_content').html('<div class="ajax_spinner_old"></div>');
            }
            var left=($('#${id}').width()/2)-($rotor.width()/2);
            var height=$('#${id}').height();
            if(height>0){
                height=height/2;
            }else{
                height=50;
            }
            var top=($('#${id}').position().top)+height-($rotor.height()/2);

            $rotor.css("left",left+"px");
            $rotor.css("top",top+"px");
            loadContent_${id}();
	<#else>
            loadContent_${id}();
	</#if>
	}

	$(document).ready(function(){
	<#if listen??>
        <#list listen as lst>
            if(checkEvent('${lst}','ajaxCall_${id}_')){
            jQuery.page.off('${lst}');
            jQuery.page.on('${lst}', function ajaxCall_${id}_(e){ajaxCall_${id}()});};
        </#list>
	</#if>	

	<#if reloadTime??>
            window.setInterval(function() {
            ajaxCall_${id}();
            }, ${(reloadTime * 1000)?c});
	</#if>
	
	<#if startOnLoad>
            ajaxCall_${id}()
	</#if>
	});

</script>


<div id="${id}" class="<#if className??> ${className?html}</#if>" <#if style??>style="${style?html}"</#if>>
<div id="${id}_content" class="div_asinc_content">
${tag_body}
</div>
</div>




