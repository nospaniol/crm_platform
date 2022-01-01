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
            "searchable": false,
            "searching": false,
            "paging": false,
            "bInfo": false
        });
        var table_search = $('#vehicle_search_info');
        table_search.DataTable({
            responsive: true,
            "searchable": false,
            "searching": false,
            "paging": false,
            "bInfo": false
        });
    };


    var viewInnovativeForm = function () {
        var client = $("#client_session").text();
        $('#kt_innovative_department').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
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
            var departmen = $("#department option:selected").val();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            $('#data_info').empty();
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/client_vehicle/load/by/client/department?&departmentName=' + departmen,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];

                    if (title === "fail") {
                        var table = $('#vehicle_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        var i = 0;
                        var table = $('#vehicle_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        search_results.forEach((item) => {
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
                            $('#data_search_info').append(
                                    '<tr>' +
                                    '<td>' + item.vehicleId + '</td>' +
                                    stat +
                                    '<td>' + item.licensePlate + '</td>' +
                                    '<td>' + item.model + '</td>' +
                                    '<td>' + item.color + '</td>' +
                                    '<td>' + item.make + '</td>' +
                                    '<td>' + item.state + '</td>' +
                                    '<td>' + item.year + '</td>' +
                                    '<td>' + item.vin + '</td>' +
                                    '<td>' + item.type + '</td>' +
                                    '<td>' + item.startDate + '</td>' +
                                    '<td style="font-size:12px;">' +
                                    '<a title="View Details" href="http://127.0.0.1:800/client_vehicle/view/vehicle/info/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md">' +
                                    '<i class="fas fa-eye"></i>' +
                                    '</a> ' +
                                    '<a href="http://127.0.0.1:800/client_vehicle/edit/vehicle/' + item.vehicleId + '" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit Vehicle"> ' +
                                    '<i class="fas fa-edit"></i>' +
                                    '</a> ' +
                                    '</td>' +
                                    '</tr>'
                                    );
                        });
                        initVehicleTable();
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        setTimeout(function () {
                        }, 2000);
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
            initVehicleTable();
            viewInnovativeForm();
        }
    };
}();
// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
