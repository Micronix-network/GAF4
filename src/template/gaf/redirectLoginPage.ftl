<!DOCTYPE html>
<html>
    <head>
      <script type="text/javascript" src="/${themeName}/js/jquery.js"></script>
      <script type="text/javascript">
        $(document).ready(function(){
          $('#toLoginForm').submit();
        });
      </script>
    </head>
    <body>
      <@s.form id="toLoginForm" action="nav_login" namespace="/" theme="simple">
        <@s.hidden name="message"/>
      </@s.form>
    </body>
</html>
