<#assign super = stack.findValue('superUser')/>
<#assign parking_zone="cards_parking"/>
<#assign icon_on="w_btn_active"/>
<#assign icon_off="w_btn_inactive"/>
<#assign icon_show="w_btn_opened"/>
<#assign icon_hide="w_btn_card"/>
<#assign icon_move="w_btn_move"/>
<#assign icon_prop="w_btn_prop"/>
<#assign icon_help="w_btn_help"/>
<#assign card_w=130/>
<#assign card_h=184/>
<#assign ctx= req.contextPath/>
<div id="${zone}" class="<#if super> layout_cell</#if> <#if className??> ${className?html}</#if>" <#if style??>style="${style?html}"</#if>>
<script type="text/javascript">
	<#--Variabile che contiene la lista delle card presenti nella zona-->
	var ${zone}_cards=new Array();
</script>
<#if cardModels??>
    <#list cardModels as model>
    <#assign title=model.getParam('title')!''/>
    <#assign help=model.getParam('show_help')!''/>
    <#assign prop=model.getParam('show_properties')!''/> 
    <#assign card_action_url=tag.calcAction(model.action,model.namespace,model.method)/> 
    <#if help=''><#assign help='false'/></#if>
    <#if prop=''><#assign prop='false'/></#if>
    <#assign js_loaded=model.getParam('card_actived_event')!""/>
		
    <#--Patch per correggere il problema di visualizzazione nella parking_zone-->
    <#assign hide=model.hide!true/>
    <#if zone=parking_zone>
        <#assign hide=true/>
    </#if>
		
    <#if hide>
        <#assign iconHideClass=icon_hide/>
    <#else>
        <#assign iconHideClass=icon_show/>
    </#if>

    <#if model.published>
        <#assign iconPublishClass=icon_on/>
    <#else>
        <#assign iconPublishClass=icon_off/>
    </#if>

    <#assign toProp=super/>
    <#if !toProp>
        <#assign toProp=prop='true'/>
    </#if>
		
    <div id="${model.name}_card"<#rt/> 
        style="<#if !hide>width:100%;height:100%<#else>width:${card_w}px;height:${card_h}px</#if>" <#rt/> 
        class="card card_border <#if hide>card_closed <#if zone=parking_zone>card_parked</#if></#if>" <#rt/>
        type="${model.type}" cardId="${model.name}">

        <#-- Sezione contenuto CARD -->
        <div class="card-content">
            <#if hide>
                <img class="card_image" src="${ctx}/resource_${model.type}.png.action" onerror="this.onerror=null; this.src='${ctx}/resource_plugin_default.png.action';" />
                <div class="card_type">${model.type}</div>
            <#else>
                <div id="${model.name}_wait_loading" class="card_wait_load"><span>Waiting..</span></div>
            </#if>
        </div>
                
        <div class="card-header <#if super>admin-header <#else>user-header </#if>">
            <#if super>
            <span class="cmd_move w_btn ${icon_move}" <#if !hide>style="display:none"</#if>></span>
            </#if>

            <#-- Sezione comandi -->
            <div class="card-header_commands">
                <button class="cmd4 w_btn ${icon_help}" <#if hide || help!='true'>style="display:none"</#if>></button>
                <#if super>
                <button class="cmd1 w_btn ${iconHideClass}" <#if zone=parking_zone>style="display:none"</#if>></button>
                <button class="cmd2 w_btn ${iconPublishClass}" <#if hide>style="display:none"</#if>></button>
                </#if>
                <#if !model.stateless>
                <button class="cmd3 w_btn ${icon_prop}" <#if hide || !toProp>style="display:none"</#if>></button>
                </#if>
            </div>

            <#-- Sezione titolo CARD-->
            <div class="card-header_title">
                <span class="card-title-text"><#if super && hide>${model.name}<#else>${title}</#if></span>
            </div>
        </div>
    </div>
		
    <#if super>
    <div id="${model.name}_properties_save" style="position:relative;top:108px"></div>
    <form id="${model.name}_properties_retrive_form"></form>
    <div id="${model.name}_properties_dialog" class="properties_dialog md-modal md-effect-1">
        <div class="md-content" style="width:550px;height:580px">
            <h3>${model.name} Properties</h3>
            <div id="${model.name}_properties_retrive"></div>
            <div class="md-button-bar">
            <button id="${model.name}_properties_button_apply" class="btn btn-small btn-primary ">${tag.getText('generic.properties.dialog.apply', 'Apply')}</button>
            <button id="${model.name}_properties_button_close" class="btn btn-small btn-primary ">${tag.getText('generic.properties.dialog.close', 'Close')}</button>
            </div>
        </div>
    </div>
    </#if>
		
    <form id="${model.name}_help_retrive_form"></form>
    <div id="${model.name}_help_dialog" class="help_dialog md-modal md-effect-1">
        <div class="md-content" style="width:550px;height:580px">
            <h3>Help</h3>
            <div id="${model.name}_help_retrive"></div>
            <div class="md-button-bar">
            <button id="${model.name}_help_button_close" class="btn btn-small btn-primary ">${tag.getText('generic.properties.dialog.close', 'Close')}</button>
            </div>
        </div>
    </div>
		
		
    <script type="text/javascript">
        
    var ${model.name}_card = new (function(){
        var id='${model.name}';
        var cardAction='${card_action_url}';

        var card_title='${title}';
        var card_help=${help};
        var card_prop=${prop};
        var card_h=184;
        var card_w=130;

        function init(){
            jQuery.cardsStack.off('${model.name}_card_refresh');
            jQuery.cardsStack.on('${model.name}_card_refresh', function() {load()});

            jQuery.cardsStack.on('layout_resize', function() {
                try{
                    ${model.name}_on_layout_resize();
                }catch(err){}
            });

            <#-- Gestione bottone di toggle card -->
            $('#'+id+'_card .card-header .cmd1').unbind('click');
            $('#'+id+'_card .card-header .cmd1').click(function(){toggleCard($(this))});

            <#-- Gestione bottone di pubblicazione -->
            $('#'+id+'_card .card-header .cmd2').unbind('click');
            $('#'+id+'_card .card-header .cmd2').click(function() {
                $(this).toggleClass('${icon_on}').toggleClass('${icon_off}');
                var isPublish = $(this).is('.${icon_on}');
                publish(isPublish);
            });	
        };

        <#--Toggle CARD-->
        function toggleCard($cmd){
            var $card=$('#'+id+'_card');
            var $content=$card.find('.card-content');
            var isVisible = !$card.hasClass('card_closed');
            var header_h = $card.find('.card-header').outerHeight(true);
            //Agisce solo se la card non è parcheggiata
            if($card.hasClass('card_parked')) return false;
            $cmd.toggleClass('w_btn_opened').toggleClass('w_btn_card');
            if(isVisible){
                $content.addClass('animated fadeOut').empty();
                $card.animate({height: card_h ,width: card_w},400,function(){
                    $content.html('<img class="card_image" src="${ctx}/resource_${model.type}.png.action" onerror="this.onerror=null;this.src=\'${ctx}/resource_plugin_default.png.action\'" /><div class="card_type">${model.type}</div>');
                });
                $card.addClass('card_closed');
                $card.find('.card-title-text').html(id);
                $card.find('.card-header .cmd2').hide();
                $card.find('.card-header .cmd3').hide();
                $card.find('.card-header .cmd4').hide();
                $card.find('.card-header .cmd_move').show();
                $content.removeClass('fadeOut');
                $content.addClass('animated fadeIn');
                hide(isVisible);
            }else{
                $card.find('.card-title-text').html(card_title);
                $card.find('.card-content').hide();
                $card.find('.card-header .cmd_move').hide();
                $card.find('.card-content').empty();
                $card.find('.card-content').show();
                $card.removeClass('card_closed');
                $card.animate({height:'100%',width:'100%'},400,function(){
                    $content.addClass('animated fadeIn');
                    $card.find('.card-header .cmd2').show();
                    $card.find('.card-header .cmd3').show();
                    if(card_help){
                        $card.find('.card-header .cmd4').show();
                    }
                    hide(isVisible);
                });		
            }
        } 

        <#--Events calling -->
        this.cardContextEvent=cardContextEvent;
        function cardContextEvent($comp,type){
            try{
                if(type=="content_loaded"){
                    ${js_loaded}
                } 
            }catch(err1){
                try{
                    console.log(err1);
                }catch(err2){
                    alert(err1);
                }
            }
        };

        <#--metodo asincrono per l'hide-->
        function hide(value){
            jQuery.ajax({
                type: "POST",
                url: "${tag.calcAction("updateLayout", null, null)}",
                data: "optType=3&zoneName=${zone}&layout=${layout}&cardName="+id+"&hidden="+value
            });
            if(!value) {
                load();
            }
        };

        <#--metodo asincrono per il publish-->
        function publish(value){
            jQuery.ajax({
                type: "POST",
                url: "${tag.calcAction("updateLayout", null, null)}",
                data: "optType=3&zoneName=${zone}&layout=${layout}&cardName="+id+"&publish="+value
            });
        };

        <#--Funzione Ajax per il caricamento del contenuto della CARD -->
        function load(){
            if(!$('#'+id+'_card').hasClass('card_closed')){
                $('#'+id+'_card .card-content').load(
                    cardAction,{cardId: id},
                    function(){
                        cardContextEvent($('#'+id+'_card'),'content_loaded');
                        try{
                            flippedCard(id);
                        }catch(err){}
                    }
                )
            }
        };

        <#--metodo di ridisegno della CARD -->
        this.redraw=redraw;
        function redraw(title,help,prop){
            var isVisible = !$('#'+id+'_card').hasClass('card_closed');
            if(isVisible){
                $('#'+id+'_card .card-title-text').html(title);
                $('#'+id+'_card .card-content').empty();
                load();
                $('#'+id+'_card .card-content').show();
                $('#'+id+'_card .card-header .cmd2').show();
                $('#'+id+'_card .card-header .cmd3').show();
                if(help){
                    $('#'+id+'_card .card-header .cmd4').show();
                }else{
                    $('#'+id+'_card .card-header .cmd4').hide();
                };
            };
        };

        <#--metodo per il refresh della card è specifico per ogni card e implica l'impelemtazione della funzione [nome_card]_contentRefresh-->
        this.contentRefresh=contentRefresh;
        function contentRefresh(){
            try{
                ${model.name}_contentRefresh();
            }catch(err1){
                try{
                    console.log(err1);
                }catch(err2){
                    alert(err1);
                }
            }
        }
        init();
    })();
		
    ${zone}_cards.push(${model.name}_card);

    <#if super>
    var ${model.name}_properties_dialog = new (function() {

        var instance=this;
        var id='${model.name}';

        function init() {
            $('#'+id+'_card .card-header .cmd3').unbind('click');
            $('#'+id+'_card .card-header .cmd3').click(function() {
                show();
                ajaxCall_properties_retrive();
            });

            $('#'+id+'_properties_button_close' ).click(function( ev ) {
                ev.stopPropagation();
                hide();
            });

            $('#'+id+'_properties_button_apply' ).click(function( ev ) {
                ev.stopPropagation();
                hide();
                ajaxCall_properties_save()
            });
        };
				
        function hide() {
            $('#'+id+'_properties_dialog').removeClass('md-show' );
            $('.md-overlay').removeClass('md-show');
        }

        function show() {
            $('#'+id+'_properties_retrive').empty();
            $('#'+id+'_properties_dialog').addClass('md-show');
            $('.md-overlay').addClass('md-show');
        }
				
        function ajaxCall_properties_save(){
            $('#'+id+'_properties_form [name=cardId]').remove();
            try{
                instance.prepare_submit()
            }catch(error){}
            var params=$('#'+id+'_properties_form').serializeArray();
            params.push({name:'cardId',value:id});
            $('#'+id+'_properties_save').load('${tag.calcAction("saveCardConfiguration", null, null)}',params)
        };
				
        function ajaxCall_properties_retrive(){
            $('#'+id+'_properties_retrive').html('<div class="rotor ajax_spinner rotor-spinner-6"></div>');
            jQuery('#'+id+'_properties_retrive_form [name=cardId]').remove();
            var params=$('#'+id+'_properties_retrive_form').serializeArray();
            params.push({name:'cardId',value:id});
            $('#'+id+'_properties_retrive').load('${tag.calcAction("propertiesRetrive", model.namespace, null)}',params)
        };
        init();	
    })();	
    </#if>
		
    var ${model.name}_help_dialog = (function() {
			
        var id='${model.name}';

        function init() {
            $('#'+id+'_card .card-header .cmd4').unbind('click');
            $('#'+id+'_card .card-header .cmd4').click(function() {
                ajaxCall_help_retrive();
                show();
            });

            $('#'+id+'_help_button_close' ).click(function( ev ) {
                ev.stopPropagation();
                hide();
            });
        };

        function hide() {
            $('#'+id+'_help_dialog').removeClass('md-show' );
            $('.md-overlay').removeClass('md-show');
        }

        function show() {
            $('#'+id+'_help_dialog').addClass('md-show');
            $('.md-overlay').addClass('md-show');
        }
				
        function ajaxCall_help_retrive(){
            jQuery('#'+id+'_help_retrive_form [name=cardId]').remove();
            var params=$('#'+id+'_help_retrive_form').serializeArray();
            params.push({name:'cardId',value:id});
            $('#'+id+'_help_retrive').load('${tag.calcAction("helpPage", model.namespace, null)}',params)
        };
        init();	
    })();
		
    </script>	
    </#list>
</#if>

</div>


