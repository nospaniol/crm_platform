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


    var cancelInnovativeForm = function () {
        $('.kt_innovative_password').click(function (e) {

            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });
            swal({
                title: 'Innovative Toll',
                text: "Enter the current password!",
                icon: 'info',
                confirmButtonText: 'OK!',
                cancelButtonText: 'Cancel!',
                buttons: true,
                dangerMode: true,
                content: {
                    element: 'input',
                    attributes: {
                        placeholder: 'Type your password',
                        type: 'password'
                    }
                }
            }).then((currentPassword) => {
                if (currentPassword === null || currentPassword === '') {
                    swal({
                        icon: 'warning',
                        title: 'Innovative Toll',
                        text: 'Enter current password first'
                    });
                    return;
                }
                swal({
                    title: 'Innovative Toll',
                    text: "Enter the new password!",
                    icon: 'info',
                    confirmButtonText: 'OK!',
                    cancelButtonText: 'Cancel!',
                    buttons: true,
                    dangerMode: true,
                    content: {
                        element: 'input',
                        attributes: {
                            placeholder: 'Type your password',
                            type: 'password'
                        }
                    }
                }).then((newPassword) => {

                    if (newPassword === null || newPassword === '') {
                        swal({
                            icon: 'warning',
                            title: 'Innovative Toll',
                            text: 'Enter new password first'
                        });
                        return;
                    }
                    swal({
                        title: 'Innovative Toll',
                        text: "Confirm the new password!",
                        icon: 'info',
                        confirmButtonText: 'OK!',
                        cancelButtonText: 'Cancel!',
                        buttons: true,
                        dangerMode: true,
                        content: {
                            element: 'input',
                            attributes: {
                                placeholder: 'Type your password',
                                type: 'password'
                            }
                        }
                    }).then((confirmPassword) => {
                        if (confirmPassword === null || confirmPassword === '') {
                            swal({
                                icon: 'warning',
                                title: 'Innovative Toll',
                                text: 'Enter new password first'
                            });
                            return;
                        }
                        swal({
                            icon: 'warning',
                            title: 'Innovative Toll',
                            text: currentPassword + "---" + newPassword + "---" + confirmPassword
                        });
                        return;
                    });
                });
            });
        }
        );
    };
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