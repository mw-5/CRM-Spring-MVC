<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="css/frmcustomer.css" />
        <link rel="stylesheet" type="text/css" href="css/cockpit.css" />
    </head>
    <body>
        <div>
            <form method="post" action="FrmCustomer_submit" id="command">
                <div>
                    <label><spring:message code="cid" /></label>
                    <label>${cid}</label>
                    <input type="text" name="cid" value="${cid}" hidden />
                </div>
                <div>
                    <label><spring:message code="company" /></label>                    
                    <input type="text" name="company" value="${company}" />
                </div>
                <div>
                    <label><spring:message code="address" /></label>                    
                    <input type="text" name="address" value="${address}" />
                </div>
                <div>
                    <label><spring:message code="zip" /></label>                    
                    <input type="text" name="zip" value="${zip}" />
                </div>
                <div>
                    <label><spring:message code="city" /></label>                    
                    <input type="text" name="city" value="${city}" />
                </div>
                <div>
                    <label><spring:message code="country" /></label>                    
                    <input type="text" name="country" value="${country}" />
                </div>
                <div>
                    <label><spring:message code="contractId" /></label>                    
                    <input type="text" name="contractId" value="${contractId}" />
                </div>
                <div>
                    <label><spring:message code="contractDate" /></label>                    
                    <input type="text" name="contractDate" id="contractDate" value="${contractDate}" />
                </div>
                <input type="submit" value="<spring:message code='submit' />" />
            </form>
        </div>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $( function(){
               $("#contractDate").datepicker({dateFormat: "yy-mm-dd"});
           });
        </script>
    </body>
</html>