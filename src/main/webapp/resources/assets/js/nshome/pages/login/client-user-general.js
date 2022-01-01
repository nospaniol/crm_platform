/* global KTUtil */

"use strict";

// Class Definition
var KTLoginGeneral = function () {


    var showErrorMsg = function (form, type, msg) {
        var alert = $('<div class="kt-alert kt-alert--outline alert alert-' + type + ' alert-dismissible" role="alert">\
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>\
			<span></span>\
		</div>');

        form.find('.alert').remove();
        alert.prependTo(form);
        KTUtil.animateClass(alert[0], 'fadeIn animated');
        alert.find('span').html(msg);
        setTimeout(function () {
            alert.fadeOut();
        }, 2000);
    };

    var initUserTable = function () {
        var table = $('#user_info');
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
                                    '<a href="http://127.0.0.1:800/client/edit/user/' + full[0] + '" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="Edit User">' +
                                    '<i class="la la-edit"></i>' +
                                    '</a>';
                        }
                        return editor;

                    },
                },
                {
                    targets: 5,
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
                    targets: 6,
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

    var handleUserRegistration = function () {

        $('#kt_register_user').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');

            form.validate({
                rules: {
                    firstName: {
                        required: true
                    },
                    lastName: {
                        required: true
                    },
                    emailAddress: {
                        required: true
                    },
                    designation: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    rpassword: {
                        required: true
                    },
                    phoneNumber: {
                        required: true
                    }
                }
            });

            if (!form.valid()) {
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var clientId = $("#clientProfileId").val();
            console.log(clientId);

            var pass1 = $("#pass").val();
            var pass2 = $("#rpassword").val();

            if (pass1 !== pass2) {
                setTimeout(function () {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'danger', "Passwords entered do not match");
                }, 1000);
                return;
            }

            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });

            swalWithBootstrapButtons.fire({
                title: 'Are you sure?',
                text: "You are about to register a user account!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, Create user!',
                cancelButtonText: 'No, Go back!',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                     $.post({
                        url: 'http://127.0.0.1:800/client/register/new/user',
                        data: form.serialize(),
                        success: function (res) {
                            var title = res["title"];
                            var message = res["message"];
                            if (title === "fail") {
                                swalWithBootstrapButtons.fire(
                                        'Innovative Toll',
                                        message,
                                        'error'
                                        );
                                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                            }
                            if (title === "success") {
                                $('#data_info').empty();
                                var table = $('#user_info');
                                table.dataTable().fnClearTable();
                                table.dataTable().fnDestroy();
                                var search_results = res["results"];
                                search_results.forEach((item) => {
                                    $('#data_info').append(
                                            '<tr>' +
                                            '<td>' + item.userId + '</td>' +
                                            '<td> ' + item.firstName + '</td>' +
                                            '<td>' + item.lastName + '</td>' +
                                            '<td>' + item.emailToken.emailAddress + '</td>' +
                                            '<td>' + item.designation + '</td>' +
                                            '<td>' + item.phoneToken.phoneNumber + '</td>' +
                                            '<td>' +
                                            ' <form class="kt-form" id="kt_form" method="post"> ' +
                                            '<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /> ' +
                                            '<div class="kt-form__section kt-form__section--first"> ' +
                                            '<div class="kt-wizard-v3__form">  ' +
                                            '<input type="hidden"  class="cancelItem" name="cancelItem" value="' + item.userId + '"/> ' +
                                            '<div class="kt-login__actions">  ' +
                                            '<a href="" class="kt_innovative_cancel btn btn-sm btn-clean btn-icon btn-icon-md" title="Delete Department"> ' +
                                            '<i class="flaticon-delete"></i> ' +
                                            '</a> ' +
                                            '</div> ' +
                                            '</div>  ' +
                                            '</div>  ' +
                                            '</form> ' +
                                            ' </td>' +
                                            '<td nowrap></td>' +
                                            '</tr>'
                                            );
                                });
                                initUserTable();
                                swalWithBootstrapButtons.fire(
                                        'Innovative Toll',
                                        message,
                                        'success'
                                        );
                                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                            }

                        },
                        error: function (res) {
                            console.log(res);
                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                            showErrorMsg(form, 'success', 'System error. Please try again later.');
                        }
                    });

                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    swalWithBootstrapButtons.fire(
                            'Innovative Toll',
                            'Reverted',
                            'error'
                            );
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                }
            });

        });
    };

    // Public Functions
    return {
        // public functions
        init: function () {
            handleUserRegistration();
            initUserTable();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTLoginGeneral.init();
});