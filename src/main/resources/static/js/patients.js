/**
 * Created by Navi on 19/02/2015.
 */


$(function() {
    $( "#DOB" ).datepicker();
});

$(document).ready(function(){
    $('#addNew').on('click',addNewRow);
});


$(document).ready(function() {
    $("#deletePatients").click(function () {
        $("table input[type ='checkbox']:checked").parent().parent().remove();

    });


    $('#searchPatient').keyup(function () {
        var data = this.value.split(" ");
        var rows = $("#patientInfo tbody tr").hide();
        if(this.value ==""){
            rows.show();
            return;
        }
        rows.hide();
        rows.filter(function(i,v){
            var t = $(this);
            for (var d = 0; d < data.length; d++) {
                if (t.is(":Contains('" + data[d] + "')")) {
                    return true;
                }
            }
            return false;
        }).show();
    });
});

function addNewRow() {
    var table = $('#patientInfo');
    var label = '<tr>' +
        '<td><input type = "checkbox" id="selectPatient"></td>' +
        ' <td>{Name}</td> ' +
        ' <td>{Hospital Number}</td> ' +
        ' <td>{Date of birth}</td>' +
        '<td><a href ="#"><i class = "fa fa-pencil"></i></a></td>' +
        '</tr>';

    var dataAppend = label;

    $('div [class = "fillData"] input:text').each(function () {
        switch (this.id) {
            case 'patientName':
                dataAppend = dataAppend.replace('{Name}', this.value);
                break;
            case 'hospitalNum':
                dataAppend = dataAppend.replace('{Hospital Number}', this.value);
                break;
            case 'DOB':
                dataAppend = dataAppend.replace('{Date of birth}', this.value);
                break;
        }
    })

    table.append(dataAppend);
}

