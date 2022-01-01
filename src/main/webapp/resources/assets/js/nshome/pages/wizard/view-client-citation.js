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
    var initCitationTable = function () {
        var table = $('#citation_info');
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
                            var editor = '<span class="dropdown">'
                                    + '<a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">'
                                    + '<i class="la la-ellipsis-h"></i>'
                                    + '</a>'
                                    + '<div class="dropdown-menu dropdown-menu-right">'
                                    + '<button class="dropdown-item"  type="button" data-toggle="modal" data-target="#myModal' + full[0] + '">'
                                    + '<i class="la la-edit"></i> Dispute Citation</button>'
                                    // + '<a class="dropdown-item" href="#"><i class="la la-leaf"></i> Message Admin</a>'
                                    + '</div>'
                                    + '</span>';
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
                url: 'http://127.0.0.1:800/client_citation/load/by/range/' + startDate + '/' + endDate,
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
                        var table = $('#citation_info');
                        table.dataTable().fnClearTable();
                        table.dataTable().fnDestroy();
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            var dtm = new Date(item.citationDate);
                            var row = "<tr>";
                            var styling = '';
                            var dep = '';
                            if (item.department !== null) {
                                dep = item.department.departmentName;
                            }
                            if (item.citationDispute === true) {
                                styling = 'style="color:lightskyblue;" title="DISPUTED"';
                            }
                            $('#data_info').append(
                                    '<tr' + styling +'>' +
                                    '<td' + styling +' >' + item.citationId  + '</td>' +
                                    '<td ' + styling + ' >' + item.clientProfile.companyName + '</td>' +
                                    '<td  ' + styling + ' >' + dep + '</td>' +
                                    '<td ' + styling + ' >' + item.vehicle.licensePlate + '</td>' +
                                    '<td ' + styling + ' >' + item.citationAmount + '</td>' +
                                    '<td ' + styling + ' >' + item.citationType.citationTypeName + '</td>' +
                                    '<td ' + styling + ' >' + item.citationStatus + '</td>' +
                                    '<td ' + styling + ' >' + dtm + '</td>' +
                                    '<td ' + styling + ' >' + item.violationState.stateName + '</td>' +
                                    '<td ' + styling + ' >' + item.violationNumber + '</td>' +
                                    '<td ' + styling + ' >' + item.payableTo + '</td>' +
                                    '<td ' + styling + ' >' + item.paidAmount + '</td>' +
                                    '<td nowrap></td>' +
                                    '</tr>'
                                    );

                        });
                        initCitationTable();
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


    };

    // Public Functions
    return {
        // public functions
        init: function () {
            viewInnovativeForm();
            initCitationTable();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
