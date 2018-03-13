function removeAttachment()
{
    document.getElementById("valAttachment").innerHTML = "";
    document.getElementById("containerAttachmentFile").innerHTML = "";
    document.getElementById("containerAttachmentFile").innerHTML = "<input type='file' name='attachmentFile' />";
}