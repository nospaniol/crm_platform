/* global KTUtil */

"use strict";
// Class Definition
var KTInnovativeGeneral = function () {

    var showErrorMsg = function (form, type, msg) {
        var alert = $('<div class="kt-alert kt-alert--outline alert alert-' + type + ' alert-dismissible" role="alert">\
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>\
			<span></span>\
		</div>');
        form.find('.alert').remove();
        alert.prependTo(form);
        //alert.animateClass('fadeIn animated');
        KTUtil.animateClass(alert[0], 'fadeIn animated');
        alert.find('span').html(msg);
        setTimeout(function () {
            alert.fadeOut();
        }, 2000);
    };

    var initVehicleTable = function () {
        var table = $('#vehicle_info');
        table.DataTable({
            responsive: true,
            "lengthMenu": [[100, 50, 25, 10, -1], [100, 50, 25, 10, "All"]],
            "paging": false,
            "bInfo": false,
            pagingType: 'full_numbers'
        });
        var table1 = $('#vehicle_info_page');
        table1.DataTable({
            responsive: true,
            "paging": false,
            "bInfo": false
        });
    };


    var viewInnovativeForm = function () {
        $('#kt_innovative_client').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    client: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var clientnam = clientnam = $("#client option:selected").val();
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/vehicle/load/by/client?clientName=' + clientnam,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    if (title === "fail") {
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        $('#data_info').empty();
                        var i = 0;
                        var table = $('#vehicle_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        search_results.forEach((item) => {
                            var sdtm = new Date(item.startDate);
                            var edtm = new Date(item.endDate);
                            var dep = "--";
                            if (item.department !== null) {
                                dep = item.department.departmentName;
                            }
                            var toll = '';
                            if (item.tollTagId !== null) {
                                toll = item.tollTagId;
                            }
                            var unt = '';
                            if (item.unit !== null) {
                                unt = item.unit;
                            }
                            var mk = '';
                            if (item.make !== null) {
                                mk = item.make;
                            }
                            var vn = '';
                            if (item.vin !== null) {
                                vn = item.vin;
                            }
                            var md = '';
                            if (item.model !== null) {
                                md = item.model;
                            }
                            var cl = '';
                            if (item.color !== null) {
                                cl = item.color;
                            }
                            i = i + 1;
                            var stat = "";
                            if (item.vehicleStatus === 'start') {
                                stat = '<td><i style="color:skyblue;" title="start" class="flaticon2-add-circular-button"></i></td>';
                            }
                            if (item.vehicleStatus === 'active') {
                                stat = '<td><i style="color:blue;" title="active" class="flaticon2-correct"></i></td> ';
                            }
                            if (item.vehicleStatus === 'maintenance') {
                                stat = '<td><i style="color:black;" title="maintenance" class="flaticon2-correct"></i></td>';
                            }
                            if (item.vehicleStatus === 'inactive') {
                                stat = '<td><i style="color:grey;" title="inactive" class="flaticon2-correct"></i></td>';
                            }
                            if (item.vehicleStatus === 'end') {
                                stat = '<td><i style="color:red;" title="end" class="flaticon2-correct"></i></td>';
                            }
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td>' + item.vehicleId + '</td>' +
                                    stat +
                                    '<td>' + item.licensePlate + '</td>' +
                                    '<td>' + toll + '</td>' +
                                    '<td>' + unt + '</td>' +
                                    '<td>' + vn + '</td>' +
                                    '<td>' + mk + '</td>' +
                                    '<td>' + md + '</td>' +
                                    '<td>' + cl + '</td>' +
                                    '<td>' + sdtm + '</td>' +
                                    '<td>' + edtm + '</td>' +
                                    '<td>' +
                                    '<a title="View Details" href="http://127.0.0.1:800/vehicle/view/vehicle/info/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md"> ' +
                                    '               <i class="la la-book"></i> ' +
                                    '          </a> ' +
                                    '         <a href="http://127.0.0.1:800/vehicle/edit/vehicle/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit Vehicle">' +
                                    '            <i class="la la-edit"></i> ' +
                                    '       </a> ' +
                                    '</td>' +
                                    '</tr>'
                                    );
                        });

                        initVehicleTable();
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                        $("input[type='search']").wrap("<form>");
                        $("input[type='search']").closest("form").attr("autocomplete", "off");

                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            })


        });
        $('#kt_innovative_department').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    client: {
                        required: true
                    },
                    department: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            if ($("#department option:selected").val() === null || $("#department option:selected").val() === "") {
                return;
            }
            var clientnam = clientnam = $("#client option:selected").val();
            var departmen = $("#department option:selected").val();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/vehicle/load/by/client/department?clientName=' + clientnam + '&departmentName=' + departmen,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];

                    if (title === "fail") {
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        $('#data_info').empty();
                        var i = 0;
                        var table = $('#vehicle_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        search_results.forEach((item) => {
                            var sdtm = new Date(item.startDate);
                            var edtm = new Date(item.endDate);
                            var dep = "--";
                            if (item.department !== null) {
                                dep = item.department.departmentName;
                            }
                            var toll = '';
                            if (item.tollTagId !== null) {
                                toll = item.tollTagId;
                            }
                            var unt = '';
                            if (item.unit !== null) {
                                unt = item.unit;
                            }
                            var mk = '';
                            if (item.make !== null) {
                                mk = item.make;
                            }
                            var vn = '';
                            if (item.vin !== null) {
                                vn = item.vin;
                            }
                            var md = '';
                            if (item.model !== null) {
                                md = item.model;
                            }
                            var cl = '';
                            if (item.color !== null) {
                                cl = item.color;
                            }
                            i = i + 1;
                            var stat = "";
                            if (item.vehicleStatus === 'start') {
                                stat = '<td><i style="color:skyblue;" title="start" class="flaticon2-add-circular-button"></i></td>';
                            }
                            if (item.vehicleStatus === 'active') {
                                stat = '<td><i style="color:blue;" title="active" class="flaticon2-correct"></i></td> ';
                            }
                            if (item.vehicleStatus === 'maintenance') {
                                stat = '<td><i style="color:black;" title="maintenance" class="flaticon2-correct"></i></td>';
                            }
                            if (item.vehicleStatus === 'inactive') {
                                stat = '<td><i style="color:grey;" title="inactive" class="flaticon2-correct"></i></td>';
                            }
                            if (item.vehicleStatus === 'end') {
                                stat = '<td><i style="color:red;" title="end" class="flaticon2-correct"></i></td>';
                            }
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td>' + item.vehicleId + '</td>' +
                                    stat +
                                    '<td>' + item.licensePlate + '</td>' +
                                    '<td>' + toll + '</td>' +
                                    '<td>' + unt + '</td>' +
                                    '<td>' + vn + '</td>' +
                                    '<td>' + mk + '</td>' +
                                    '<td>' + md + '</td>' +
                                    '<td>' + cl + '</td>' +
                                    '<td>' + sdtm + '</td>' +
                                    '<td>' + edtm + '</td>' +
                                    '<td>' +
                                    '<a title="View Details" href="http://127.0.0.1:800/vehicle/view/vehicle/info/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md"> ' +
                                    '               <i class="la la-book"></i> ' +
                                    '          </a> ' +
                                    '         <a href="http://127.0.0.1:800/vehicle/edit/vehicle/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit Vehicle">' +
                                    '            <i class="la la-edit"></i> ' +
                                    '       </a> ' +
                                    '</td>' +
                                    '</tr>'
                                    );
                        });
                        initVehicleTable();
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                        $("input[type='search']").wrap("<form>");
                        $("input[type='search']").closest("form").attr("autocomplete", "off");
                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            })


        });
    }

    // Public Functions
    return {
        // public functions
        init: function () {
            viewInnovativeForm();
            initVehicleTable();
        }
    };
}();
// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
