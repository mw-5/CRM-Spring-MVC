<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/frmcontactperson.css" />
    </head>
    <body>
        <div class="container">
            <form method="post" action="FrmContactPerson_submit" id="command">
                <input type="text" name="cid" id="cid" value="${cid}" hidden />
                <input type="text" name="id" id="id" value="${id}" hidden />
                <div>
                    <label class="lbl"><spring:message code="forename" /></label>
                    <input type="text" name="forename" value="${forename}" />
                </div>
                <div>
                    <label class="lbl"><spring:message code="surname" /></label>
                    <input type="text" name="surname" value="${surname}" />
                </div>
                <div>
                    <label class="lbl"><spring:message code="gender" /></label>
                    <select name="gender">
                        <option value="m" ${gender == 'm' ? "selected" : ""}><spring:message code="male" /></option>
                        <option value="f" ${gender == 'f' ? "selected" : ""}><spring:message code="female" /></option>
                    </select>
                </div>
                <div>
                    <label class="lbl"><spring:message code="email" /></label>
                    <input type="text" name="email" value="${email}" />
                </div>
                <div>
                    <label class="lbl"><spring:message code="phone" /></label>
                    <input type="text" name="phone" value="${phone}" />
                </div>
                <div>
                    <input type="checkbox" name="mainContact" ${mainContact == true ? "checked" : ""} />
                    <label><spring:message code="mainContact" /></label>
                </div>
                <input type="submit" value="<spring:message code='submit' />" />
            </form>
        </div>
    </body>
</html>