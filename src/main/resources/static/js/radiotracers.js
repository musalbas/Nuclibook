/**
 * Created by GEORGE RADUTA on 10.02.2015.
 */

// Function which will empty the fields from AddRadioTracer Modal
// (Not calling this function will leave the fields full with last radioTracer added)
function emptyFieldsOfAddModal() {
    var nameOfRadiotracerInput = document.getElementById("nameOfRadiotracerInput");
    nameOfRadiotracerInput.value = "";
    var etaOfRadiotracerInput = document.getElementById("etaOfRadiotracerInput");
    etaOfRadiotracerInput.value = "";
    document.getElementById("saveRadiotracerButton").disabled = true;
    console.log("matadfas");

}

//Function to add a new radiotracer
//Creating a new row in the table and all elements that we need filled with data from the user
//TODO Create the relation to the databese
function addRadiotracer() {
    var table = document.getElementById("tableBodyRadioTracer");

    //Getting data from the user
    var nameOfRadiotracerInput = document.getElementById("nameOfRadiotracerInput").value;
    var etaOfRadiotracerInput = document.getElementById("etaOfRadiotracerInput").value;

    //TR
    var radiotracerTR = document.createElement("tr");
    radiotracerTR.setAttribute("id", "TR" + nameOfRadiotracerInput);

    //TH Name
    var nameOfRadiotracer = document.createElement("th");
    nameOfRadiotracer.setAttribute("id", "TH" + nameOfRadiotracerInput + "Name");
    nameOfRadiotracer.setAttribute("class", "text-center");
    nameOfRadiotracer.setAttribute("style", "vertical-align: middle");
    nameOfRadiotracer.innerHTML = nameOfRadiotracerInput;

    //TH Eta
    var etaOfRadiotracer = document.createElement("th");
    etaOfRadiotracer.setAttribute("id", "TH" + nameOfRadiotracerInput + "Eta");
    etaOfRadiotracer.setAttribute("class", "text-center");
    etaOfRadiotracer.setAttribute("style", "vertical-align: middle");
    etaOfRadiotracer.innerHTML = etaOfRadiotracerInput + " day(s)";

    //TH Remove
    var editRadiotracerTH = document.createElement("th");
    editRadiotracerTH.setAttribute("id", "TH" + nameOfRadiotracerInput + "Edit");
    editRadiotracerTH.setAttribute("class", "text-center");
    editRadiotracerTH.setAttribute("style", "vertical-align: middle");
    var uEdit = document.createElement("u");
    uEdit.innerHTML = "&nbsp;Edit";
    uEdit.setAttribute("id", "u" + nameOfRadiotracerInput + "Edit");
    uEdit.setAttribute("class", "fa fa-pencil-square-o");
    uEdit.setAttribute("data-backdrop", "static");
    uEdit.setAttribute("data-keyboard", "false");
    uEdit.setAttribute("data-toggle", "modal");
    uEdit.setAttribute("data-target", "#bs-example-modal-smRadioTracerRemoval");
    uEdit.setAttribute("style", "cursor:pointer;");
    uEdit.setAttribute("onmouseover", "this.style.color='#2f2f8b';");
    uEdit.setAttribute("onmouseout", "this.style.color='';");
    uEdit.setAttribute("onclick", "editRadiotracerClick(id)");             /////////////EDIT
    editRadiotracerTH.appendChild(uEdit);

    radiotracerTR.appendChild(nameOfRadiotracer);
    radiotracerTR.appendChild(etaOfRadiotracer);
    radiotracerTR.appendChild(editRadiotracerTH);
    table.appendChild(radiotracerTR);

    document.getElementById("saveRadiotracerButton").setAttribute("data-dismiss", "modal");
}


//Function which will start the modal of editing radiotracer in the table
function editRadiotracerClick(id) {
    // Form the name of the Radiotracer
    var nameOfRadioTracer = id.replace("u", "");
    nameOfRadioTracer = nameOfRadioTracer.replace("Edit", "");

    //Tell user what RadioTracer is editing (Setting the header of the modal)
    var changeTextModal = document.getElementById("headEditRadiotracer");
    changeTextModal.innerHTML = "<h4>You are editing <i><u>" + nameOfRadioTracer + "</u></i></h4>";

    //Open the Modal of the Edit
    var openModal = document.getElementById(id);
    openModal.setAttribute("data-backdrop", "static");
    openModal.setAttribute("data-keyboard", "false");
    openModal.setAttribute("data-toggle", "modal");
    openModal.setAttribute("data-target", "#bs-example-modal-sm-edit-radio-tracer");

    //Filling the modal with the data
    document.getElementById("editNameOfRadiotracerInput").value = nameOfRadioTracer;
    document.getElementById("editEtaOfRadiotracerInput").value = document.getElementById("TH" + nameOfRadioTracer + "Eta").innerHTML.replace("day(s)", "");

    //Setting the footer of the modal (Buttons)
    document.getElementById("editDeleteRadiotracerButton").setAttribute("name",nameOfRadioTracer);
    document.getElementById("editDeleteRadiotracerButton").setAttribute("onclick","editDeleteRadiotracer(name)");
    document.getElementById("editSaveRadiotracerButton").setAttribute("name",nameOfRadioTracer);
    document.getElementById("editSaveRadiotracerButton").setAttribute("onclick","editSaveRadiotracer(name)");
}

// Function which will remove the radiotracer from the table
function editDeleteRadiotracer(name) {
    //Forming the name of the element we want to remove
    name = "TH" + name + "Name";
    var rowToDelete = document.getElementById(name);
    rowToDelete.parentNode.remove();

    document.getElementById("editDeleteRadiotracerButton").setAttribute("data-dismiss", "modal");
}

//Function for when the Save button is clicked;
//Will update the table with the changes
//TODO connect to database
function editSaveRadiotracer(name) {
    //Data entered by user
    var nameOfRadiotracerInput = document.getElementById("editNameOfRadiotracerInput").value;
    var etaOfRadiotracerInput = document.getElementById("editEtaOfRadiotracerInput").value;

    //TR
    var radiotracerTR = document.getElementById("TR" + name);
    radiotracerTR.setAttribute("id", "TR" + nameOfRadiotracerInput);

    //TH Name
    var nameOfRadiotracer = document.getElementById("TH" + name + "Name");
    nameOfRadiotracer.setAttribute("id", "TH" + nameOfRadiotracerInput + "Name");
    nameOfRadiotracer.innerHTML = nameOfRadiotracerInput;

    //TH Eta
    var etaOfRadiotracer = document.getElementById("TH" + name + "Eta");
    etaOfRadiotracer.setAttribute("id","TH" + nameOfRadiotracerInput + "Eta");
    etaOfRadiotracer.innerHTML = etaOfRadiotracerInput + " day(s)";

    //TH Edit
    var editCamera = document.getElementById("TH" + name + "Edit");
    editCamera.setAttribute("id","TH" + nameOfRadiotracerInput + "Edit");
        var ueditCamera = document.getElementById("u" + name + "Edit");
        ueditCamera.setAttribute("id","u" + nameOfRadiotracerInput + "Edit");

    document.getElementById("editSaveRadiotracerButton").setAttribute("data-dismiss", "modal");
}

//JQUERY
$("input").keyup(function () {
    var valueNameInput = $("#nameOfRadiotracerInput").val();
    var valueEtaInput = $("#etaOfRadiotracerInput").val();
    if (valueNameInput != "" && valueEtaInput != "") {
        $("#saveRadiotracerButton").removeAttr("disabled");
    }
});