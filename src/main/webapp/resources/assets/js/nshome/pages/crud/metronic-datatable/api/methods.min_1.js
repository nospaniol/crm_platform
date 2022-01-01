"use strict"; var KTDefaultDatatableDemo = {init:function(){var t, a; t = {data:{type:"remote", source:{read:{url:"https://keenthemes.com/metronic/themes/themes/metronic/dist/preview/inc/api/datatables/demos/default.php"}}, pageSize:20, serverPaging:!0, serverFiltering:!0, serverSorting:!0}, layout:{scroll:!0, height:550, footer:!1}, sortable:!0, pagination:!0, search:{input:$("#generalSearch")}, columns:[{field:"RecordID", title:"#", sortable:!1, width:30, type:"number", selector:{class:"kt-checkbox--solid"}, textAlign:"center"}, {field:"ID", title:"ID", width:30, type:"number", template:function(t){return t.RecordID}}, {field:"OrderID", title:"Order ID"}, {field:"Country", title:"Country", template:function(t){return t.Country + " " + t.ShipCountry}}, {field:"ShipDate", title:"Ship Date", type:"date", format:"MM/DD/YYYY"}, {field:"CompanyName", title:"Company Name"}, {field:"Status", title:"Status", template:function(t){var a = {1:{title:"Pending", class:"kt-badge--brand"}, 2:{title:"Delivered", class:" kt-badge--danger"}, 3:{title:"Canceled", class:" kt-badge--primary"}, 4:{title:"Success", class:" kt-badge--success"}, 5:{title:"Info", class:" kt-badge--info"}, 6:{title:"Danger", class:" kt-badge--danger"}, 7:{title:"Warning", class:" kt-badge--warning"}}; return'<span class="kt-badge ' + a[t.Status].class + ' kt-badge--inline kt-badge--pill">' + a[t.Status].title + "</span>"}}, {field:"Type", title:"Type", autoHide:!1, template:function(t){var a = {1:{title:"Online", state:"danger"}, 2:{title:"Retail", state:"primary"}, 3:{title:"Direct", state:"success"}}; return'<span class="kt-badge kt-badge--' + a[t.Type].state + ' kt-badge--dot"></span>&nbsp;<span class="kt-font-bold kt-font-' + a[t.Type].state + '">' + a[t.Type].title + "</span>"}}, {field:"Actions", title:"Actions", sortable:!1, width:110, overflow:"visible", autoHide:!1, template:function(){return'\t\t\t\t\t\t<div class="dropdown">\t\t\t\t\t\t\t<a href="javascript:;" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown">                                <i class="la la-ellipsis-h"></i>                            </a>\t\t\t\t\t\t  \t<div class="dropdown-menu dropdown-menu-right">\t\t\t\t\t\t    \t<a class="dropdown-item" href="#"><i class="la la-edit"></i> Edit Details</a>\t\t\t\t\t\t    \t<a class="dropdown-item" href="#"><i class="la la-leaf"></i> Update Status</a>\t\t\t\t\t\t    \t<a class="dropdown-item" href="#"><i class="la la-print"></i> Generate Report</a>\t\t\t\t\t\t  \t</div>\t\t\t\t\t\t</div>\t\t\t\t\t\t<a href="javascript:;" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit details">\t\t\t\t\t\t\t<i class="la la-edit"></i>\t\t\t\t\t\t</a>\t\t\t\t\t\t<a href="javascript:;" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Delete">\t\t\t\t\t\t\t<i class="la la-trash"></i>\t\t\t\t\t\t</a>\t\t\t\t\t'}}]}, a = $(".kt-datatable").KTDatatable(t), $("#kt_datatable_destroy").on("click", function(){$(".kt-datatable").KTDatatable("destroy")}), $("#kt_datatable_init").on("click", function(){a = $(".kt-datatable").KTDatatable(t)}), $("#kt_datatable_reload").on("click", function(){$(".kt-datatable").KTDatatable("reload")}), $("#kt_datatable_sort_asc").on("click", function(){a.sort("Status", "asc")}), $("#kt_datatable_sort_desc").on("click", function(){a.sort("Status", "desc")}), $("#kt_datatable_get").on("click", function(){if (a.rows(".kt-datatable__row--active"), a.nodes().length > 0){var t = a.columns("CompanyName").nodes().text(); console.log(t)}}), $("#kt_datatable_check").on("click", function(){var t = $("#kt_datatable_check_input").val(); a.setActive(t)}), $("#kt_datatable_check_all").on("click", function(){$(".kt-datatable").KTDatatable("setActiveAll", !0)}), $("#kt_datatable_uncheck_all").on("click", function(){$(".kt-datatable").KTDatatable("setActiveAll", !1)}), $("#kt_datatable_hide_column").on("click", function(){a.columns("ShipDate").visible(!1)}), $("#kt_datatable_show_column").on("click", function(){a.columns("ShipDate").visible(!0)}), $("#kt_datatable_remove_row").on("click", function(){a.rows(".kt-datatable__row--active").remove()}), $("#kt_form_status,#kt_form_type").selectpicker()}}; jQuery(document).ready(function(){KTDefaultDatatableDemo.init()});