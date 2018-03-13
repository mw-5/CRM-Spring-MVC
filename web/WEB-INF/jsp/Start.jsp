<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/start.css" rel="stylesheet" />
    </head>
    <body>
        <div id="rctCockpit" class="rct ani-cockpit"></div>
        <div id="rctRefresh" class="rct ani-customers"></div>
        <div id="rctCustomers" class="rct ani-refresh"></div>
        
        <div class="container">
            <input type="button" value="<spring:message code="cockpit" />" onclick="goToCockpit();" />
            <br>
            <input type="button" value="<spring:message code="customers" />" onclick="goToListOfCustomers();" />
            <br>
            <input type="button" value="<spring:message code="refresh" />" />
        </div>
        <script src="js/start.js"></script>
    </body>
</html>