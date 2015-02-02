<!DOCTYPE html>
<html>
    <head>      
        <title>${action.getText('error.page.title', 'Error page')}</title>
        <style>
            .message {
            width:70%;
            background: transparent;
            border: none;
            margin-top: 20px;
            margin-bottom: 5px;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
            font-size: 48px;
            font-weight:300;
            font-family: sans-serif , Arial, ;
            background-color: rgba(0, 0, 0, 0.05);
            color: #333;
            padding-left: 5px;
            padding-right: 5px;
            padding-bottom: 5px;
            padding-top: 5px;
            z-index: 1000;
            -moz-border-radius: 4px;
            -webkit-border-radius: 4px;
            border-radius: 4px;
            -moz-box-shadow: inset 0 0 3px #aaa;
            -webkit-box-shadow: inset 0 0 3px #aaa;
            box-shadow: inner 0 0 3px #aaa;
            }

        </style>
    </head>
    <body>
         <#if errorBean??>
        <div class="message" style="margin-bottom:20px">Error: ${errorBean.errorType}</div>
        <center>
            <img src="images/error.png" style="width:320px">
        </center>
        <div class="message" style="font-size:16px">
            <#if errorBean??>
            ${errorBean.errorMsg}
                <#if errorBean.errorDett??>
                    <#if devMode>
                    <div class="dett">
                        ${errorBean.errorDett}
                    </div>
                    </#if>
                </#if>
            </#if> 	
        </div>
        <#else>
        <div class="message" style="margin-bottom:20px">Error: Not defined</div>
        <center>
            <img src="images/error.png" style="width:320px">
        </center>
        <div class="message" style="font-size:16px">
            <div class="dett">
                        Unknown generic error
            </div>
        </div>
        </#if> 
        
    </body>
</html>
