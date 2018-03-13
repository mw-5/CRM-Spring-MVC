<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/cockpit.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
        <div id="leftBorder"></div>
        <div id="containerCenter">
            <div>
                <spring:message code="goToStart" var="vGoToStart" />
                <input type="button" value="${vGoToStart}" onclick="goToStart();" />
                <input type="button" value="<spring:message code="goToListOfCustomers" />" onclick="goToListOfCustomers();" />
                <label id="header">${company}</label>
                <br>
                <div id="searchBox">
                    <input type="text" id="txtSearchCid" />
                    <input type="image" src="resources/search.png" id="searchButton" onclick="search('<spring:message code="invalidValueEntered" />');"/>
                </div>
            </div>
            <div class="regionSpace">
                <table id="tblMasterData" class="vatib tbl">
                    <tr><td class="lblBorder cellLbl"><spring:message code="cid" /></td><td class="lblBorder cellVal" id="txtCid">${cid}</td></tr>
                    <tr><td class="lblBorder cellLbl"><spring:message code="address" /></td><td class="lblBorder cellVal">${address}</td></tr>
                    <tr><td class="lblBorder cellLbl"><spring:message code="zip" /></td><td class="lblBorder cellVal">${zip}</td></tr>
                    <tr><td class="lblBorder cellLbl"><spring:message code="city" /></td><td class="lblBorder cellVal">${city}</td></tr>
                    <tr><td class="lblBorder cellLbl"><spring:message code="country" /></td><td class="lblBorder cellVal">${country}</td></tr>
                    <tr><td class="lblBorder cellLbl"><spring:message code="contractId" /></td><td class="lblBorder cellVal">${contractId}</td></tr>
                    <tr><td class="lblBorder cellLbl"><spring:message code="contractDate" /></td><td class="lblBorder cellVal">${contractDate}</td></tr>
                </table>
                <div class="vatib">
                    <div class="tblHeightCp scrollingtable">
                        <div>
                            <div>
                                <table id="tblContactPersons">
                                    <!--<caption>contact persons</caption>-->
                                    <thead></thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="vatib">
                        <input type="button" class="btnList" value="<spring:message code="newContactPerson" />" onclick="newContactPerson('<spring:message code="selectCustomer" />');"/><br>
                        <input type="button" class="btnList" value="<spring:message code="editContactPerson" />" onclick="editContactPerson('<spring:message code="selectCp" />');"/>
                        <input type="text" id="idSelectedContactPerson" hidden />
                    </div>
                </div>
            </div>
            <div class="regionSpace">
                <div class="scrollingtable tblHeightNotes">
                    <div>
                        <div>
                            <table id="tblNotes">
                                <!--<caption>notes</caption>-->
                                <thead></thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="vatib">
                    <input type="button" class="btnList" value="<spring:message code="newNote" />" onclick="newNote('<spring:message code="selectCustomer" />');"/><br>
                    <input type="button" class="btnList" value="<spring:message code="editNote" />" onclick="editNote('<spring:message code="selectNote" />');"/><br>
                    <input type="button" class="btnList" value="<spring:message code="openAttachment" />" onclick="openAttachment('<spring:message code="selectNote" />');"/>
                    <input type="text" id="idSelectedNote" hidden />
                </div>
            </div>                            
        </div>
        <script src="js/table.js"></script>
        <script src="js/cockpit.js"></script>
        <script>
            cid = document.getElementById("txtCid").innerHTML;
            if (cid != "")
            {
                var vColsCp = ["forename", "surname", "gender", "email", "phone"];
                $.ajax({url: 'contact_persons?cid=' + cid, success: function(data){buildTable(data, 'tblContactPersons', 'col_id', 'idSelectedContactPerson', vColsCp);}});
                
                var vColsNotes = ["createdBy", "entryDate", "memo", "category", "attachment"];
                $.ajax({url: "notes?cid=" + cid, success: function(data){buildTable(data, 'tblNotes', 'col_id', 'idSelectedNote', vColsNotes);}});
            }
        </script>                        
    </body>
</html>