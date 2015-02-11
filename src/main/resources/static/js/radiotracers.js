/**
 * Created by GEORGE RADUTA on 10.02.2015.
 */

//Function which will start the modal of removing radiotracer from the table
function removeRadiotracerClick(id) {
    var removeTh = document.getElementById(id);
    var changeTextModal = document.getElementById("removeTextModalID");
    var nameOfRadioTracer = id.replace("remove", "");
    changeTextModal.innerHTML = "Are you sure you want to remove <i><u><h4>" + nameOfRadioTracer + " </h4></u> </i>from the radiotracers table ?";

    removeTh.setAttribute("data-backdrop", "static");
    removeTh.setAttribute("data-keyboard", "false");
    removeTh.setAttribute("data-toggle", "modal");
    removeTh.setAttribute("data-target", "#bs-example-modal-smRadioTracerRemoval");
    var newID = id.replace("remove", "ThName");

    document.getElementById("okRemoveRadiotracer").setAttribute("name", newID);
}

// Function which will remove the radiotracer from the table
function removeRadiotracerOkClick(who) {
    var rowToDelete = document.getElementById(who);
    console.log(rowToDelete);
    rowToDelete.parentNode.remove();
    document.getElementById("okRemoveRadiotracer").setAttribute("data-dismiss", "modal");
}

// Function which will empty the fields from radiotracer
// Preventing browser cache
function emptyFieldsAddRadioTracer() {
    var nameOfRadiotracerInput = document.getElementById("nameOfRTInput");
    nameOfRadiotracerInput.value = "";
    var etaOfRadiotracerInput = document.getElementById("etaOfRTInput");
    etaOfRadiotracerInput.value = "";
    document.getElementById("okAddRadiotracer").disabled = true;
}
function addRadiotracer() {
    var table = document.getElementById("tableBodyRadioTracer");

    //TR
    var radiotracerTR = document.createElement("tr");
    var nameOfRadiotracerInput = document.getElementById("nameOfRTInput");
    var etaOfRadiotracerInput = document.getElementById("etaOfRTInput");
    radiotracerTR.setAttribute("id", "TR" + nameOfRadiotracerInput.value);

    //TH Name
    var nameOfRadiotracer = document.createElement("th");
    nameOfRadiotracer.setAttribute("id", "ThName" + nameOfRadiotracerInput.value);
    nameOfRadiotracer.innerHTML = nameOfRadiotracerInput.value;

    //TH eta
    var etaOfRadiotracer = document.createElement("th");
    etaOfRadiotracer.setAttribute("id", "Theta" + nameOfRadiotracerInput.value);
    etaOfRadiotracer.innerHTML = etaOfRadiotracerInput.value + " day(s)";

    //TH Remove
    var removeOfRadiotracerTH = document.createElement("th");
    removeOfRadiotracerTH.setAttribute("class", "text-center");
    var removeU = document.createElement("u");
    removeU.innerHTML = "Remove";
    removeU.setAttribute("id", "remove" + nameOfRadiotracerInput.value);
    removeU.setAttribute("data-backdrop", "static");
    removeU.setAttribute("data-keyboard", "false");
    removeU.setAttribute("data-toggle", "modal");
    removeU.setAttribute("data-target", "#bs-example-modal-smRadioTracerRemoval");
    removeU.setAttribute("style", "cursor:pointer;");
    removeU.setAttribute("onmouseover", "this.style.color='#2f2f8b';");
    removeU.setAttribute("onmouseout", "this.style.color='';");
    removeU.setAttribute("onclick", "removeRadiotracerClick(id)");
    removeOfRadiotracerTH.appendChild(removeU);

    radiotracerTR.appendChild(nameOfRadiotracer);
    radiotracerTR.appendChild(etaOfRadiotracer);
    radiotracerTR.appendChild(removeOfRadiotracerTH);
    table.appendChild(radiotracerTR);

    document.getElementById("okAddRadiotracer").setAttribute("data-dismiss", "modal");
}


//JQUERY
$("input").keyup(function () {
    var valueNameInput = $("#nameOfRTInput").val();
    var valueEtaInput = $("#etaOfRTInput").val();
    if (valueNameInput != "" && valueEtaInput != "") {
        $("#okAddRadiotracer").removeAttr("disabled");
    }
});