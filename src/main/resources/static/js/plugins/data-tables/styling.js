/**
 * Created by Fares Alaboud on 07/03/2015.
 */
$(document).ready(function() {

    var filterSearchBox = $( ".dataTables_filter").children('label').eq(0);
    filterSearchBox.append( "<span class='input-group-addon'><i class='fa fa-search'></i></span>");
    filterSearchBox.wrapInner( "<div class='icon-field input-group data-tables-icon-field' id='search-data-tables'></div>");
    filterSearchBox.children('input').eq(0).removeClass("input-small input-inline");
    filterSearchBox.unwrap();

    $("#search-data-tables").parent().parent().css("text-align", "right");
});