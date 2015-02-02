<div id="propstabs-5_${cardId}" style="overflow:hidden;">

     <fieldset>
	    <p>
	    <label for="title" class="prop_label">${tag.getText('generic.properties.dialog.title', 'CARD Title')}</label>
	    <#assign title = tag.getCardParam('title')!""/>
	    <input class="right_input" type="text" name="props['title']" size="30" value="${title}"/>	
	    </p>
	    
	    <p>
	    <label for="width" class="prop_label">${tag.getText('generic.properties.dialog.width', 'CARD width')}</label>
	    <#assign width = tag.getCardParam('card_width')!""/>
	    <input class="right_input" type="text" name="props['card_width']" size="30" value="${width}"/>	
	    </p>
	   
	    <p>
	    <label for="height" class="prop_label">${tag.getText('generic.properties.dialog.height', 'CARD height')}</label>
	    <#assign height = tag.getCardParam('card_height')!""/>
	    <input class="right_input" type="text" name="props['card_height']" size="30" value="${height}"/>	
	    </p>
	    
	    <p>
	    <label for="help" class="prop_label">${tag.getText('generic.properties.dialog.help', 'Help button')}</label>
	    <#assign show_help = (tag.getCardParam('show_help')!'')='true'/>
	    <select name="props['show_help']">
	    <option value="false" <#if !show_help>selected</#if> >${tag.getText('yes.or.no.no', 'No')}</option>
	    <option value="true" <#if show_help>selected</#if> >${tag.getText('yes.or.no.yes', 'Yes')}</option>
	    </select>
	    </p>
	    
	    <p>
	    <label for="properties" class="prop_label">${tag.getText('generic.properties.dialog.prop', 'Properties button')}</label>
	    <#assign show_properties = (tag.getCardParam('show_properties')!'')='true'/>
	    <select name="props['show_properties']">
	    <option value="false" <#if !show_properties>selected</#if> >${tag.getText('yes.or.no.no', 'No')}</option>
	    <option value="true" <#if show_properties>selected</#if> >${tag.getText('yes.or.no.yes', 'Yes')}</option>
	    </select>
	    </p>

	    <p>
	    <label for="width" class="prop_label_area">${tag.getText('generic.properties.dialog.note', 'Note')}</label>
	    <#assign g_note = tag.getCardParam('general_note')!""/>
	    <textarea class="prop_text_area" type="text" name="props['general_note']">${g_note}</textarea>	
	    </p>
	    
	   </fieldset>
</div>