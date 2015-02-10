/**
 * Created by GEORGE RADUTA on 10.02.2015.
 */

$(document).on("click", "#mataclass", function () {
    var myBookId = $(this).attr('id');
    console.log(myBookId);
});

//Function which will remove Radiotracer from the table
function removeRadiotracerOkClick() {
    console.log("Ok clicked");
    document.getElementById("okRemoveRadiotracer").setAttribute("data-dismiss","modal");
    var rowToDelete = document.getElementById("trRadioTracer");
    var table = document.getElementById("tableBodyRadioTracer");
    table.removeChild(rowToDelete);
    console.log("Deleted");
}

function addRadiotracer() {
    var table = document.getElementById("tableBodyRadioTracer");

    //TR
    var radiotracerTR = document.createElement("tr");
    var nameOfRadiotracerInput = document.getElementById("nameOfRTInput");
    var etaOfRadiotracerInput = document.getElementById("etaOfRTInput");

    radiotracerTR.setAttribute("id","TR" + nameOfRadiotracerInput.value);

    //TH Name
    var nameOfRadiotracer = document.createElement("th");
    nameOfRadiotracer.setAttribute("id","ThName" + nameOfRadiotracerInput.value);
    nameOfRadiotracer.innerHTML = nameOfRadiotracerInput.value;

    //TH eta
    var etaOfRadiotracer = document.createElement("th");
    etaOfRadiotracer.setAttribute("id","Theta" + etaOfRadiotracerInput.value);
    etaOfRadiotracer.innerHTML = etaOfRadiotracerInput.value + " day(s)";

    //TH Remove
    var removeOfRadiotracerTH = document.createElement("th");
    removeOfRadiotracerTH.setAttribute("class","text-center");
        var removeU = document.createElement("u");
        removeU.innerHTML = "Remove";
        removeU.setAttribute("id","remove" + nameOfRadiotracerInput.value);
        removeU.setAttribute("data-backdrop", "static");
        removeU.setAttribute("data-keyboard", "false");
        removeU.setAttribute("data-toggle", "modal");
        removeU.setAttribute("data-target", "#bs-example-modal-smRadioTracerRemoval");
        removeU.setAttribute("style", "cursor:pointer;");
        removeU.setAttribute("onmouseover", "this.style.color='#2f2f8b';");
        removeU.setAttribute("onmouseout", "this.style.color='';");
    removeOfRadiotracerTH.appendChild(removeU);

    radiotracerTR.appendChild(nameOfRadiotracer);
    radiotracerTR.appendChild(etaOfRadiotracer);
    radiotracerTR.appendChild(removeOfRadiotracerTH);
    table.appendChild(radiotracerTR);

    document.getElementById("okAddRadiotracer").setAttribute("data-dismiss","modal");
}

