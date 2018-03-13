<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>CRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/frmnote.css" rel="stylesheet" />
    </head>
    <body>
        <div class="container">
            <form method="post" action="FrmNote_submit" id="command" enctype="multipart/form-data">
                <input type="text" name="id" value="${id}" hidden />
                <input type="text" name="cid" value="${cid}" hidden />
                <input type="text" name="createdBy" value="${createdBy}" hidden />
                <input type="text" name="entryDate" value="${entryDate}" hidden />
                <div>
                    <label id="lblNote"><spring:message code="note" /></label>
                    <br>
                    <textarea id="txtNote" name="memo">${memo}</textarea>
                </div>
                <div>
                    <label class="lbl"><spring:message code="category" /></label>
                    <input type="text" name="category" value="${category}" />
                </div>
                <div>
                    <div>
                        <label id="lblAttachment"><spring:message code="attachment" /></label>
                        <label id="valAttachment" name="attachment">${attachment}</label>
                        <br>
                        <label id="lblAttach"><spring:message code="attach" /></label> <span id="containerAttachmentFile"><input type="file" name="attachmentFile"></input></span>
                        <br>
                        <div class="btnRemove">
                            <input type="button" value="<spring:message code="remove" />" onclick="removeAttachment();" />
                        </div>
                    </div>
                </div>
                <br>
                <div class="btnSubmit">
                    <input type="submit" value="<spring:message code="submit" />" />
                </div>
            </form>
        </div>
        <script src="js/frmnote.js"></script>
    </body>        
</html>