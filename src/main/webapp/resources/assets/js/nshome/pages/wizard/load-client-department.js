/* global KTUtil */

"use strict";

// Class Definition
var KTAppointmentGeneral = function () {


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


    var loadDepartments = function () {
        $('#department-panel').hide();
        $('#department').empty();
        $.ajax({
            url: 'http://127.0.0.1:800/client_dash/load/departments/',
            success: function (res) {
                var response = res;
                var title = response["title"];
                var message = response["message"];
                if (title === "fail") {
                    console.log(message);
                    return;
                }
                var search_results = response["results"];
                $('#department').empty();
                $('#department').append('<option value="">--</option>');
                $('#department').append('<option value="ALL">ALL</option>');
                search_results.forEach((item) => {
                   $('#department').append('<option value="' + item.departmentName + '">' + item.departmentName + '</option>');
                });
                $('#department-panel').show();
                $('#type-panel').show();
            },
            error: function (res) {
                console.log(res);
            }
        });

    };

    var loadDepartmentInfo = function () {
        $('#kt_innovative_load_parent').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });
            swalWithBootstrapButtons.fire({
                title: 'Are you sure?',
                text: "You are about to change to to parent mode",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, proceed!',
                cancelButtonText: 'No, Go back!',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                    $.ajax({
                        type: "POST",
                        url: 'http://127.0.0.1:800/client_dash/change_mode/parent/',
                        success: function (res) {
                            var response = res;
                            var title = response["title"];
                            if (title === "fail") {
                                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                return;
                            }
                            window.location.reload();
                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                        },
                        error: function (res) {
                            var response = JSON.stringify(res);
                            console.log(response);
                        }
                    });
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    swalWithBootstrapButtons.fire(
                            'Innovative Toll',
                            'Mode sustained',
                            'info'
                            );
                    loadDepartments();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                }
            });

        });



        $('#department').change(function (e) {
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
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var clientnam = $("#department option:selected").val();
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });
            swalWithBootstrapButtons.fire({
                title: 'Are you sure?',
                text: "You are about to change to view for : " + clientnam,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, proceed!',
                cancelButtonText: 'No, Go back!',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                    $.ajax({
                        type: "POST",
                        data: {
                            searchItem: clientnam
                        },
                        url: 'http://127.0.0.1:800/client_dash/change_mode/department/',
                        success: function (res) {
                            var response = res;
                            var title = response["title"];
                            if (title === "fail") {
                                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                return;
                            }
                            window.location.reload();
                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                        },
                        error: function (res) {
                            var response = JSON.stringify(res);
                            console.log(response);
                        }
                    });
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    swalWithBootstrapButtons.fire(
                            'Innovative Toll',
                            'Mode sustained',
                            'info'
                            );
                    loadDepartments();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                }
            });

        });


    };

    return {
        init: function () {
            loadDepartmentInfo();
            loadDepartments();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTAppointmentGeneral.init();
});