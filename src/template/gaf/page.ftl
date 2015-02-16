<!DOCTYPE html>
<#assign ctx= request.contextPath/>
<#assign message= message!''/>
<#assign contentHeight=pageHeight!500/>
<#assign cards_domain= domain!''/>
<#assign grid=cardsGrid!'single'/>
<#assign gui_theme=themeName!'default'/>
<#assign pageTitle=action.getText('menu.'+activeUseCase, activeUseCase)/>

<html>
<head>
<title>${pageTitle}</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="cleartype" content="on">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" type="text/css" href="/${gui_theme}/css/style.css" />
<link rel="stylesheet" type="text/css" href="/${gui_theme}/js/CodeMirror-2.21/lib/codemirror.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css">

<link rel="icon" href="images/gwa_favicon.png" type="image/png" />

<!--[if IE]><script  type="text/javascript" src="/js/excanvas.js"></script><![endif]-->
<script type="text/javascript" src="/${gui_theme}/js/application.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/jquery.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/jquery-ui.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/modernizr.custom.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/date_region/jquery.ui.datepicker-${locale.language}.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/CodeMirror-2.21/lib/codemirror.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/CodeMirror-2.21/mode/mysql/mysql.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/CodeMirror-2.21/mode/javascript/javascript.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/CodeMirror-2.21/mode/xml/xml.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/CodeMirror-2.21/mode/css/css.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/CodeMirror-2.21/mode/htmlmixed/htmlmixed.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/d3_v3.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/nv_d3.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/jquery.twosidedmultiselect.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/DataTables-1.9.4/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/DataTables-1.9.4/extras/FixedColumns/media/js/FixedColumns.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/menu/mlpushmenu.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="/${gui_theme}/js/jquery-tabs.js"></script>


<script type="text/javascript">  

	//Definizione dell'oggetto di pagina a cui bindare gli ascoltatori
	jQuery.page = jQuery({});
		
	//Definizione dell'oggetto pr la gestione del caricamento delle CARD
	jQuery.cardsStack = jQuery({});
	
	var cardsFullLoadedStack = new Array();
	
	var CARD_WIDTH=130;
	var CARD_HEIGHT=182;

	var card_drawer=false;

   /**
	* Evento scatenato dall'attivazione di una card
	*/
	function flippedCard(name){
		cardsFullLoadedStack.push(name);
	}
		
	
    <#if superUser>
   /**
    * Salva sul DB l'ordine delle CARD presenti in una zona
    **/
 	function save_cards_Order(obj){
 		jQuery.ajax({
 			type: "POST",
 			url: "${action.calcAction('updateLayout', null, null)}",
 			data: "optType=1&zoneName="+obj.attr('id')+"&layout=${cards_domain}&cardsOrder="+getCardsIdArray(obj),
 			success: function(msg){
 				//alert( "Data Saved: " + msg );
 			}
 		});
 	};
 	
 	function helperDrag(evt,ui){
		var clone=$(ui).clone();
		clone.height(CARD_HEIGHT);
		clone.width(CARD_WIDTH);
		clone.find('.card-header_commands').empty();
		clone.find('.card-title-text').html($(ui).attr('cardId'));
		var content=clone.find('.card-content').empty();
		content.html(getCardImage($(ui)));
		clone.addClass('card_shadow');
		return clone.appendTo('body').show();
	}
	
	function stopDrag(source,ui,receiver){
		//salvataggio stato zone start e end
		$(receiver).append(ui);
		save_cards_Order(source);
		save_cards_Order(receiver);
		var isVisible = !ui.hasClass('card_closed');
		var $command=ui.find('.card-header .card-header_commands .cmd1');
		if(ui.parent().attr('id')=='cards_parking'){
			if(isVisible){
					ui.height(CARD_HEIGHT);
					ui.width(CARD_WIDTH);
        		   	$command.trigger('click');
        		   	isVisible=false;
        		   	}else{
        		   	}
				  $command.hide();
        	   	  ui.addClass('card_parked');
		}else{
			ui.removeClass('card_parked');
			$command.show();
       		if(!isVisible){
      	  		$command.trigger('click');
         	};  
		}
		if(isVisible){
			 jQuery.cardsStack.trigger(ui.attr('id')+'_refresh');
		   	}
	}
	
	/**
 	 * Recupera il codice html per la visualizzazione dell'icona di una CARD
 	 *
 	 */
 	function getCardImage($card){
 		var type=$card.attr('type');
 		var html='<img class="card_image" src="${ctx}/resource_'+type+'.png.action" onerror="this.onerror=null;this.src=\'${ctx}/resource_plugin_default.png.action\'" /><div class="card_type">'+type+'</div>'
 		return html;
 	}
 	
 	</#if>
 	
 	/**
 	 * Attiva una CARD caricato nella pagina
 	 * @param ename nome evento
 	 */	
 	function flipCard(card_name){
 		if(!$('#'+card_name+'_card').hasClass('card_closed')){
 			jQuery.cardsStack.trigger(card_name+"_card_refresh");
 		}
 	}

 	/**
 	 * Gira una CARD aspettando un certo tempo passato come parametro
 	 * @param time il tempo da attendere
 	 * @param card_name il nome della CARD da attivare
 	 * @param waitFor il nome della CARD che sblocca il caricamento
 	 */
 	function flipCardWithDelay(time,card_name,waitFor){
 	 	  setTimeout(function(){
 	 		  if(isInArray(waitFor,cardsFullLoadedStack)){
 	 			flipCard(card_name);
 	 		  }else{
 	 			flipCardWithDelay(time,card_name,waitFor);  
 	 		  }
 			  }, time);
 	   }
 	
 	
 	
 	/**
 	 * Fuzione per il cambio di dominio funzionale della gui (cambio di pagina o back/forward nel browser)
 	 * @param domain il nuovo dominio
 	 * @returns
 	 */
 	function changeDomainContext(domain,action,pageCards){
 	 	  jQuery.ajax({
 	 			type: "GET",
 	 			url: action,
 	 			data: "domain="+domain,
 	 			cache : false,
 	 			success: function(m){
 	 				if(m=='success'){
 	 					flipAllCards(pageCards)
 	 				}else{
 	 					//alert("Domain setting problem: " +m);
 	 				}
 	 				},
 	 			error : function(jqXHR, textStatus) {
 	 				  alert( "Request failed: " + textStatus );
 	 			}
 	 			});
 	   }
     
     
    function flipAllCards(pageCards){
    	for (index = 0; index < pageCards.length; ++index) {
    	    if(!pageCards[index].hidden){
    	    	if(pageCards[index].wait==""){
    	    		flipCard(pageCards[index].id);
    	    	}else{
    	    		if(isInArray(pageCards[index].wait,cardsFullLoadedStack)){
    	    			flipCard(pageCards[index].id);
    				  }else{
    					flipCardWithDelay(0, pageCards[index].id, pageCards[index].wait);
    				  }
    	    	}
    	    }
    	}	
     }
    
    

$(document).ready(function(){ 
	
		new mlPushMenu( document.getElementById( 'mp-menu' ), document.getElementById( 'trigger' ) );

<#if superUser>		
		
		if(card_drawer){
			$("#parking_zone").show();
		}
		
                
                $("#trigger_card_drawer").click(function(){
                    
                        $("#cards_drawer").slideToggle('fast',function(){
				
			});	
                });
	
		
		$(".card").draggable({ 
			handle: '.cmd_move',
			helper: function (evt) {
				try{	
       	     	 	return helperDrag(evt, this);
       	     	 }catch(err){}
			 },
			 revert: 'invalid', 
			 start: function(evt,ui){
				 $(".layout_cell .card").animate({opacity:0.3},300);
			 },
			 stop :  function(evt,ui){
				 $(".layout_cell .card").animate({opacity:1},100);
			 },	 
		});
		
		$(".layout_cell").droppable({
			drop: function( event, ui ) {
				try{
					var parent=ui.draggable.parent();
					var card=ui.draggable;
        	     	stopDrag(parent,card,$(this));
        	     }catch(err){
        	    	
        	     }
		      },
		    tolerance : 'pointer',
		    hoverClass: "select_border",
		    accept: '.card',
		});
</#if>			
		
	});
</script>

</head>
<body>
<div id="container">
	<div class="mp-pusher" id="mp-pusher">
		
		<@s.i18n name="menu">
			<@gaf.mainMenu/>
		</@s.i18n>
	
		<div class="scroller">
			<div class="scroller-inner">
				<!-- Top Navigation -->
				<header class="page-header">
					<div style="position:absolute;top:0px;left:0px">
						<a href="#" id="trigger" class="menu-trigger icon icon-menu"></a>
					</div>
					<div id="header_notify_area"><div class="header_notify_pin red_pin" style="display:none"></div><div class="header_notify_pin green_pin" style="display:none"></div></div>
					<h1>${pageTitle}</h1>
                                        <#if superUser>	
                                        <div style="position:absolute;top:2px;right:0px;width:28px">
						<a href="#" id="trigger_card_drawer" class="card_drawer_trigger icon icon-document"></a>
					</div>
                                        </#if>
				</header>
                                <div id="cards_drawer" class="drawer">
                                <div id="parking_zone">
                                    <@gaf.placeCards zone="cards_parking" layout="${cards_domain}" style="min-height:184px;border:none" />
                                </div>
                               </div>
				<div class="page-grid-content clearfix" style="min-height:${contentHeight}px">
                                        <#attempt>
                                            <#include grid+".grid"/>
                                        <#recover>
                                             <#include "grids/single.grid"/>
                                        </#attempt>
				</div>
                                 
			</div>
		</div>
	</div>
</div>
		
	
	
	<div class="md-overlay"></div>
	
	<script type="text/javascript">	
			var pageCards = new Array();
			<#list cardModels.values() as model>
			pageCards.push({ id:"${model.name}",hidden : ${model.hide?string},wait :"${model.getParam('waitFor')!''}"});
			</#list>
			changeDomainContext('${cards_domain}','<@s.url action="changeDomainContext"/>',pageCards);  
	</script>
</body>
</html>

