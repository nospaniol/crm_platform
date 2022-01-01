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
    }
    var initInvoiceTable = function () {
        var table = $('#invoice_info');
        table.DataTable({
            responsive: true,
            "paging": true,
            pagingType: 'full_numbers',
            columnDefs: [
                {
                    targets: -1,
                    title: 'Actions',
                    orderable: false,
                    render: function (data, type, full, meta) {
                        var editor = "--";
                        if (full[0] === "No Data") {

                        } else {
                            var editor =
                                    '<a title="View Details & Actions" href="http://127.0.0.1:800/invoice/view/' + full[0] + '" class="btn btn-sm btn-clean btn-icon btn-icon-md">' +
                                    '<i class="la la-book"></i>' +
                                    '</a>';
                        }
                        return editor;

                    },
                },
                {
                    targets: 8,
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
                    targets: 9,
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

        $('#kt_innovative_search').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    month: {
                        required: true
                    },
                    year: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var month = $("#month option:selected").val();
            var year = $("#year option:selected").val();

            if (month === null || month === '' || year === null || year === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }

            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/invoice/filter/monthly/by/' + month + '/' + year,
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
                        var table = $('#invoice_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            var dep = "";
                            if (item.department !== null) {
                                dep = item.department.departmenName;
                            }
                            var feetype = "";
                            if (item.feeType !== null) {
                                feetype = item.feeType.feeTypeName;
                            }
                            if (item.invoiceDate !== null) {
                                var invoiceDate = new Date(item.invoiceDate);
                            }
                            if (item.paidDate !== null) {
                                var paidDate = new Date(item.paidDate);
                            }
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td>' + item.invoiceId + '</td>' +
                                    '<td>' + item.clientProfile.companyName + '</td>' +
                                    '<td>' + dep + '</td>' +
                                    '<td>' + feetype + '</td>' +
                                    '<td>' + invoiceDate + '</td>' +
                                    '<td>' + item.invoiceAmount + '</td>' +
                                    '<td>' + item.invoiceStat + '</td>' +
                                    '<td>' + paidDate + '</td>' +
                                    '<td>' + item.feeAmount + '</td>' +
                                    '<td>' + item.tollAmount + '</td>' +
                                    '<td>' + item.totalPaid + '</td>' +
                                    '<td nowrap></td>' +
                                    '</tr>'
                                    );
                        });
                        initInvoiceTable();
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

        $('#kt_innovative_client').click(function (e) {
            $('#data_info').empty();
            $('.panel-footer').hide();

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
                url: 'http://127.0.0.1:800/invoice/load/by/client?clientName=' + clientnam,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];

                    if (title === "fail") {
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        // showErrorMsg(form, 'success', message);
                        var dt = $('#innovative_data').DataTable();


                        var search_results = res["results"];
                        $('#data_info').empty();
                        $('.panel-footer').hide();
                        search_results.forEach((item) => {
                            var dtm = new Date(item.invoiceDate);
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td scope="row"> ' + item.clientProfile.companyName + '</td>' +
                                    '<td>' + item.department.departmentName + '</td>' +
                                    '<td><i class="flaticon2-correct"></i> ' + item.vehicle.licensePlate + '</td>' +
                                    '<td><b>' + item.invoiceAmount + '</b></td>' +
                                    '<td>' + item.invoiceType.invoiceTypeName + '</td>' +
                                    '<td>' + item.invoiceStat + '</td>' +
                                    '<td>' + dtm + '</td>' +
                                    '<td>' +
                                    ' <a href="http://127.0.0.1:800/invoice/view/' + item.invoiceId + '">' +
                                    ' <button type="button" class="btn btn-info btn-pill btn-elevate">View More</button>&nbsp;' +
                                    '  </a> ' +
                                    ' </td>' +
                                    '</tr>'
                                    );
                        });
                        $('#innovative_data').DataTable({
                            "iDisplayLength": 10,
                            "lengthChange": false
                        });
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

        $('#kt_innovative_department').click(function (e) {
            $('#data_info').empty();
            $('.panel-footer').hide();
            $('#innovative_data').DataTable().clear().draw()
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
                url: 'http://127.0.0.1:800/invoice/load/by/client/department?clientName=' + clientnam + '&departmentName=' + departmen,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    if (title === "fail") {
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        //showErrorMsg(form, 'success', message);
                        var dt = $('#innovative_data').DataTable();


                        var search_results = res["results"];
                        $('#data_info').empty();
                        $('.panel-footer').hide();
                        search_results.forEach((item) => {
                            var dtm = new Date(item.invoiceDate);
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td scope="row"> ' + item.clientProfile.companyName + '</td>' +
                                    '<td>' + item.department.departmentName + '</td>' +
                                    '<td><i class="flaticon2-correct"></i> ' + item.vehicle.licensePlate + '</td>' +
                                    '<td><b>' + item.invoiceAmount + '</b></td>' +
                                    '<td>' + item.invoiceType.invoiceTypeName + '</td>' +
                                    '<td>' + item.invoiceStat + '</td>' +
                                    '<td>' + dtm + '</td>' +
                                    '<td>' +
                                    ' <a href="http://127.0.0.1:800/invoice/view/' + item.invoiceId + '">' +
                                    ' <button type="button" class="btn btn-info btn-pill btn-elevate">View More</button>&nbsp;' +
                                    '  </a> ' +
                                    ' </td>' +
                                    '</tr>'
                                    );
                        });
                        $('#innovative_data').DataTable({
                            "iDisplayLength": 10,
                            "lengthChange": false
                        });
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

    };

    // Public Functions
    return {
        // public functions
        init: function () {
            viewInnovativeForm();
            initInvoiceTable();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
