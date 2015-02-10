/**
 * Created by GEORGE RADUTA on 09.02.2015.
 */


function tst() {
    console.log("Mata");
}

function showDetailsOfAppointment() {
    var appointmentOverLay = document.getElementById("overLayDiv");
    var contentOfSite = document.getElementById("page-wrapper");
    var nameOfClient = document.getElementById("nameOfClientAppointment");

    contentOfSite.style.opacity = "0.2";
    appointmentOverLay.style.visibility = "visible";
    appointmentOverLay.style.display = "block";
    }

function bookAnAppointment() {

    var aElementAppoinment = document.createElement("a");
    aElementAppoinment.className = "list-group-item list-group-item-danger";
    aElementAppoinment.setAttribute("onclick", "showDetailsOfAppointment()");

    var span = document.createElement("span");
    span.className = "badge alert-danger";
    span.innerHTML = "Date selected"; //TODO
    aElementAppoinment.appendChild(span);

    var detailsI = document.createElement("i")
    detailsI.className = "fa fa-fw fa-warning text-left";
    detailsI.innerHTML = " ";
    aElementAppoinment.appendChild(detailsI);

    var labelName = document.createElement("label");
    labelName.innerHTML = "&nbsp; Name Of Patient";
    aElementAppoinment.appendChild(labelName);

    document.getElementById("upcomingAppointmentsList").appendChild(aElementAppoinment);


//<a href="#" class="list-group-item" onclick=showDetailsOfAppointment()>
//    <span class="badge">Today</span>
//    <i class="fa fa-fw fa-calendar"></i> Abigail Rainbow
//    </a>
    console.log("Appointment Booked");
}


