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
        setTimeout(function () {
            alert.hide();
        }, 2000);
    }


    var cancelInnovativeForm = function () {
        $('.kt_innovative_cancel').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    cancelItem: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }


            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });
            var cancelItem = $(".cancelItem").val();
            swalWithBootstrapButtons.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, Delete vehicle!',
                cancelButtonText: 'No, Go back!',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                    $.ajax({
                        type: "POST",
                        data: {
                            cancelItem: cancelItem
                        },
                        url: 'http://127.0.0.1:800/vehicle/account/delete-account',
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
                                setTimeout(function () {
                                    window.location.reload();

                                }, 2000);
                            }
                            if (title === "success") {
                                swalWithBootstrapButtons.fire(
                                        'Innovative Toll',
                                        message,
                                        'success'
                                        );
                                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                                setTimeout(function () {
                                    window.location.reload();
                                }, 2000);
                            }

                        },
                        error: function (res) {
                            console.log(res);
                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                            showErrorMsg(form, 'success', 'System error. Please try again later.');
                        }
                    })

                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    swalWithBootstrapButtons.fire(
                            'Innovative Toll',
                            'Reverted',
                            'error'
                            );
                }
            });

        });
    }

    // Public Functions
    return {
        // public functions
        init: function () {
            cancelInnovativeForm();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});