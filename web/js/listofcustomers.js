function goToStart()
{
    location.href = "Start";
}

function newCustomer()
{
    location.href = "FrmCustomer_new";
}

function editCustomer(msg)
{
    var cid = document.getElementById("selectedCid").value;
    navTo("FrmCustomer_edit?cid=", cid, msg);
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

function showCustomerInCockpit(msg)
{
    var cid = document.getElementById("selectedCid").value;
    navTo("Cockpit_search?cid=", cid, msg);
}