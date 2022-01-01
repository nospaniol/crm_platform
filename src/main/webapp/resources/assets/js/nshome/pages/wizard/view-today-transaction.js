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
            "paging": true,
            "lengthMenu": [ [100, 50, 25,10, -1], [100, 50, 25,10,"All"] ],
            pagingType: 'full_numbers',
            columnDefs: [
                {
                    targets: -1,
                    title: 'Actions',
                    orderable: false,
                    render: function (data, type, full, meta) {
                        var editor = '<span class="dropdown">'
                                + '<a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">'
                                + '<i class="la la-ellipsis-h"></i>'
                                + '</a>'
                                + '<div class="dropdown-menu dropdown-menu-right">'
                                + '<button class="dropdown-item"  type="button" data-toggle="modal" data-target="#myModal' + full[0] + '">'
                                + '<i class="la la-edit"></i> Reverse Transaction</button>'
                                // + '<a class="dropdown-item" href="#"><i class="la la-leaf"></i> Message Admin</a>'
                                + '</div>'
                                + '</span>';
                        return editor;

                    },
                },
                {
                    targets: 7,
                    render: function (data, type, full, meta) {
                        var status = {
                            1: {'title': 'Pending', 'class': 'kt-badge--brand'},
                            2: {'title': 'Delivered', 'class': ' kt-badge--danger'},
                            3: {'title': 'Canceled', 'class': ' kt-badge--primary'},
                            4: {'title': 'Success', 'class': ' kt-badge--success'},
                            5: {'title': 'Info', 'class': ' kt-badge--info'},
                            6: {'title': 'Danger', 'class': ' kt-badge--danger'},
                            7: {'title': 'Warning', 'class': ' kt-badge--warning'},
                        };
                        if (typeof status[data] === 'undefined') {
                            return data;
                        }
                        return '<span class="kt-badge ' + status[data].class + ' kt-badge--inline kt-badge--pill">' + status[data].title + '</span>';
                    },
                },
                {
                    targets: 8,
                    render: function (data, type, full, meta) {
                        var status = {
                            1: {'title': 'Online', 'state': 'danger'},
                            2: {'title': 'Retail', 'state': 'primary'},
                            3: {'title': 'Direct', 'state': 'success'},
                        };
                        if (typeof status[data] === 'undefined') {
                            return data;
                        }
                        return '<span class="kt-badge kt-badge--' + status[data].state + ' kt-badge--dot"></span>&nbsp;' +
                                '<span class="kt-font-bold kt-font-' + status[data].state + '">' + status[data].title + '</span>';
                    },
                },
            ],
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
                url: 'http://127.0.0.1:800/client_transaction/load/by/range/' + startDate + '/' + endDate,
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
                        $('#data_info').empty();
                        var table = $('#transaction_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        var search_results = res["results"];
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
                                    '<td>' + item.exitLane + '</td>' +
                                    '<td>' + item.exitLocation + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '<td nowrap></td>' +
                                    ' <div id="myModal' + item.transactionId + '" class="modal fade" role="dialog">' +
                                    ' <div class="modal-dialog">' +
                                    ' <div class="modal-content">' +
                                    '<div class="modal-header">' +
                                    ' <h4 class="modal-title">Reason for dispute</h4>' +
                                    ' <p>TRANSACTION ID : ' + item.transactionId + '</p>' +
                                    ' </div>' +
                                    ' <div class="modal-body">  ' +
                                    ' <div class="form">' +
                                    '  <div class="row">' +
                                    ' <div class="form-group">' +
                                    ' <input class="form-control" type="hidden" value="' + item.transactionId + '" name="transactionId' + item.transactionId + '" id="transactionId' + item.transactionId + '"/>' +
                                    ' </div>  ' +
                                    ' <div class="form-group">' +
                                    '  <textarea class="form-control" type="text" placeholder="Dispute" name="dispute' + item.transactionId + '" id="dispute' + item.transactionId + '" required=""></textarea>' +
                                    '</div> ' +
                                    '</div> ' +
                                    '<div class="row">' +
                                    '<div class="kt-login__actions" onclick="submitDispute' + item.transactionId + '">' +
                                    ' <button  onclick="submitDispute' + item.transactionId + '" class="btn btn-brand btn-pill btn-elevate">Submit<i class="kt-menu__link-icon flaticon2-add"></i></button>' +
                                    ' </div>' +
                                    '</div>' +
                                    ' </div>' +
                                    '<div class="modal-footer">' +
                                    '   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>' +
                                    ' </div>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>' +
                                    '<script>' +
                                    'function submitDispute' + item.transactionId + '() {' +
                                    'console.log("processing..")' +
                                    'var transactionId = $(#transactionId' + item.transactionId + ').val();' +
                                    'var dispute = $(#dispute' + item.transactionId + ').val();' +
                                    'alert(transactionId + dispute);' +
                                    'return;' +
                                    ' }' +
                                    ' </script>' +
                                    '</div>' +
                                    '</tr>'
                                    );
                        });
                        initTransactionTable();
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            });


        });

        $('#kt_innovative_search').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    transactionDate: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

            var searchDate = $('#transactionDate').val();
            if (searchDate === null || searchDate === '') {
                showErrorMsg(form, 'danger', "select a date first! ");
                return;
            }
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/transaction/load/by/today/' + searchDate,
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
                        $('#data_info').empty();
                        var table = $('#transaction_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        var search_results = res["results"];
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
                        initTransactionTable();
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            })


        });

        $('#kt_innovative_client').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    transactionDate: {
                        required: true
                    },
                    client: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            var searchDate = $('#transactionDate').val();
            if (searchDate === null || searchDate === '') {
                showErrorMsg(form, 'danger', "select a date first! ");
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var clientnam = $("#client option:selected").val();


            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/transaction/load/by/today/client?transactionDate=' + searchDate,
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
                        var search_results = res["results"];
                        $('#data_info').empty();
                        var table = $('#transaction_info');
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
                        initTransactionTable();
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

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
                    transactionDate: {
                        required: true
                    },
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
            var searchDate = $('#transactionDate').val();
            if (searchDate === null || searchDate === '') {
                showErrorMsg(form, 'danger', "select a date first! ");
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

            var clientnam = clientnam = $("#client option:selected").val();
            var departmen = $("#department option:selected").val();

            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/transaction/load/by/today/client/department?clientName=' + clientnam + '&departmentName=' + departmen + '&transactionDate=' + searchDate,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];

                    if (title === "fail") {
                        $('#data_info').empty();
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        $('#data_info').empty();
                        var table = $('#transaction_info');
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
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        initTransactionTable();
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
            initTransactionTable();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
function parseJsonDate(jsonDateString) {
    return new Date(parseInt(jsonDateString.replace('/Date(', '')));
}