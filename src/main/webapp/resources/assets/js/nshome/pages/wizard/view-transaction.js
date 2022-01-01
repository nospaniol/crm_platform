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

    var initTransactionTable = function () {
        var table = $('#transaction_info');
        table.DataTable({
            responsive: true,
            oLanguage: {
                sSearch: "FILTER",
            },
            "searching": true,
            "paging": false,
            "bInfo": false
        });


        var total_table = $('#total_info');
        total_table.DataTable({
            responsive: true,
            "lengthChange": false,
            "info": false,
            "bFilter": false,
            "searchable": false,
            "paging": false
        });

    };

    var viewInnovativeForm = function () {

        $('#kt_search_info').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    startDate: {
                        required: true
                    },
                    endDate: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            if (endDate === null || endDate === '' || startDate === null || startDate === '') {
                showErrorMsg(form, 'danger', "select a date first! ");
                return;
            }
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/transaction/load/by/range/' + startDate + '/' + endDate,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    var total = 0.00;
                    if (title === "fail") {
                        $('#data_info').empty();
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        $('#data_info').empty();
                        var table = $('#transaction_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        var totaltable = $('#total_info');
                        totaltable.dataTable().fnClearTable();
                        totaltable.dataTable().fnDestroy();
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            var dtm = new Date(item.exitDateTime);
                            var pdtm = new Date(item.postedDate);
                            var dep = "--";
                            if (item.department !== null) {
                                dep = item.department.departmentName;
                            }
                            var location = "--";
                            if (item.exitLocation !== null) {
                                location = item.exitLocation;
                            }
                            total = total + item.amount;
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td>' + item.clientProfile.companyName + '</td>' +
                                    '<td>' + dep + '</td>' +
                                    '<td>' + item.vehicle.licensePlate + '</td>' +
                                    '<td>' + item.state + '</td>' +
                                    '<td>' + item.agency + '</td>' +
                                    '<td>' + dtm + '</td>' +
                                    '<td>' + pdtm + '</td>' +
                                    '<td>' + location + '</td>' +
                                    '<td>' + item.exitLane + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '<td nowrap></td>' +
                                    '</tr>'
                                    );

                        });
                        $('#total_body').empty();
                        $('#total_body').append(
                                '<tr>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td><b>' + total.toFixed(2) + '</b></td>' +
                                '<td nowrap></td>' +
                                '</tr>'
                                );
                        initTransactionTable();
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
            });


        });

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
            var clientnam = $("#client option:selected").val();
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/transaction/load/by/client?clientName=' + clientnam,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    if (title === "fail") {
                        $('#data_info').empty();
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var total = res["total"];
                        var search_results = res["results"];
                        $('#data_info').empty();
                        var table = $('#transaction_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        var table = $('#total_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        search_results.forEach((item) => {
                            var dtm = new Date(item.exitDateTime);
                            var pdtm = new Date(item.postedDate);
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td>' + item.transactionId + '</td>' +
                                    '<td scope="row"> <i class="flaticon2-correct"></i> ' + item.vehicle.licensePlate + '</td>' +
                                    '<td>' + item.state + '</td>' +
                                    '<td>' + item.agency + '</td>' +
                                    '<td>' + dtm + '</td>' +
                                    '<td>' + pdtm + '</td>' +
                                    '<td>' + item.exitLane + '</td>' +
                                    '<td>' + item.exitLocation + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '<td nowrap></td>' +
                                    '</tr>'
                                    );
                        });

                        $('#total_body').empty();
                        $('#total_body').append(
                                '<tr>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td>--</td>' +
                                '<td><b>' + total.toFixed(2) + '</b></td>' +
                                '<td nowrap></td>' +
                                '</tr>'
                                );
                        initTransactionTable();
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
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var clientnam = clientnam = $("#client option:selected").val();
            var departmen = $("#department option:selected").val();
            if ($("#department option:selected").val() === null || $("#department option:selected").val() === "") {
                return;
            }

            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/transaction/load/by/client/department?clientName=' + clientnam + '&departmentName=' + departmen,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    if (title === "fail") {
                        $('#data_info').empty();
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        showErrorMsg(form, 'success', message+", redirecting page to search results");
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            })


        });

    };

    // Public Functions
    return {
        // public functions
        init: function () {
            viewInnovativeForm();
            initTransactionTable();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
