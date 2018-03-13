<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/listofcustomers.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
        <div>
            <label id="header"><spring:message code="listOfCustomers" /></label>
            <br><br>
            <input type="button" id="goToStart" value="<spring:message code="goToStart" />" onclick="goToStart();" />
            <input type="button" id="showCustomerInCockpit" value="<spring:message code="showCustomerInCockpit" />" onclick="showCustomerInCockpit('<spring:message code="selectCustomer" />');"/>
            <input type="button" id="newCustomer" value="<spring:message code="newCustomer" />" onclick="newCustomer();" />
            <input type="button" id="editCustomer" value="<spring:message code="editCustomer" />" onclick="editCustomer('<spring:message code="selectCustomer" />');" />
            <br><br><input type="text" id="selectedCid" hidden />                   
        </div>
        <div class="scrollingtable tblHeight">
            <div>
                <div>
                    <table id="tblCustomers">
                        <thead></thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
        <script src="js/table.js"></script>
        <script src="js/listofcustomers.js"></script>
        <script>
            $( function(){
               var vCols = ["cid", "company", "address", "zip", "city", "country", "contractId", "contractDate"];
               $.ajax({url: 'customers', success: function(data){buildTable(data, "tblCustomers", "col_cid", "selectedCid", vCols);}});
            });
        </script>
    </body>
</html>