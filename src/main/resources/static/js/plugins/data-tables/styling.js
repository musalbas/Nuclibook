/**
 * Created by Fares Alaboud on 07/03/2015.
 */
$(document).ready(function() {

    $( ".dataTables_filter").children('label').eq(0).append( "<span class='input-group-addon'><i class='fa fa-search'></i></span>");
    $( ".dataTables_filter" ).children('label').eq(0).wrapInner( "<div class='icon-field input-group' id='search-data-tables'></div>");
    $( ".dataTables_filter" ).children('label').eq(0).children('input').eq(0).removeClass("input-small input-inline");
    $( ".dataTables_filter" ).children('label').eq(0).unwrap();
});