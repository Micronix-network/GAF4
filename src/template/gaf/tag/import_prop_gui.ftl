<#if stack.findValue('cardModel')??>
<#assign wm = stack.findValue('cardModel')/>
</#if>

<#if wm??>
	<#if wm.domain??>
	<#assign domain = wm.domain/>
	<#assign others = stack.findValue('cardsWithSameType')/>
	<form id="${cardId}_import_properties_form">
	<div style="background-color: white;border: 1px solid #ddd;padding:5px;height: 35px;margin-bottom: 5px">
	<label for="import" style="width: 120px;font-weight: bold">${tag.getText('import.properties.dialog.label', 'Imp. properties')}</label>
	<select name="toImport">
	<#list others as card_info>
	<option value="${card_info[0]},${card_info[1]}">(${card_info[0]}) ${card_info[1]}</option>	
	</#list>
	</select>
	<button id="${cardId}_import_properties_button" class="btn btn-mini btn-primary" style="float:right">Carica</button>
	</div>
	</form>
	<div id="${cardId}_import_properties" style="display:none">
	</div>
	
	<script type="text/javascript">
		function ajaxCall_${cardId}_import_properties(){
			jQuery('#${cardId}_import_properties_form [name=cardId]').remove();
		    var params=$('#${cardId}_import_properties_form').serializeArray();
			params.push({name:'cardId',value:'${cardId}'});
			$('#${cardId}_import_properties').load('${tag.calcAction("importProperites", null, null)}',params)
		}	
		
		$('#${cardId}_import_properties_button').click(function(){
			ajaxCall_${cardId}_import_properties();
			return false;
		});
	</script>
	</#if>
</#if>