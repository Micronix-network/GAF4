<!DOCTYPE html>
<html>
    <head>
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
        <div class="message" style="margin-bottom:20px">404 Error</div>
    <center>
        <img src="images/splash.png" style="width:320px">
    </center>
    <div class="message" style="font-size:16px"><b>${domain?cap_first}</b> page not found</div>
</body>
</html>
