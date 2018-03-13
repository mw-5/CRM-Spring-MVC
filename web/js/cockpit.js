function goToStart()
{
    location.href = "Start";
}

function goToListOfCustomers()
{
    location.href = "ListOfCustomers";
}

function newContactPerson(msg)
{
    cid = document.getElementById("txtCid").innerHTML;
    navTo("FrmContactPerson_new?cid=", cid, msg);
}

function editContactPerson(msg)
{
    id = document.getElementById("idSelectedContactPerson").value;
    navTo("FrmContactPerson_edit?id=", id, msg);
}

function newNote(msg)
{
    cid = document.getElementById("txtCid").innerHTML;
    navTo("FrmNote_new?cid=", cid, msg);
}

function editNote(msg)
{
    id = document.getElementById("idSelectedNote").value;
    navTo("FrmNote_edit?id=", id, msg);
}

function search(msg)
{
    id = document.getElementById("txtSearchCid").value;
    navTo("Cockpit_search?cid=", id, msg);
}

function openAttachment(msg)
{
    id = document.getElementById("idSelectedNote").value;
    navTo("Attachment?id=", id, msg);
}

function navTo(url, id, msg)
{
    if (id != undefined && !isNaN(id) && id > 0)
    {
        location.href = url + id;
    }
    else
    {
        alert(msg);
    }
}