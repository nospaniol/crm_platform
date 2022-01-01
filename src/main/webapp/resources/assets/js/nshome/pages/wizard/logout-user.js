/* global KTUtil, self */

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


    var logoutAdmin = function () {
        $('#kt_innovative_logout').click(function (e) {
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
                text: "You are about to logout!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes,I want to logout',
                cancelButtonText: 'No, Go back!',
                reverseButtons: true
            }).then((result) => {
                if (result.value) {
                 javascript:go();
                } else if (result.dismiss === Swal.DismissReason.cancel) {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
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
            logoutAdmin();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});

function go() {
window.location.replace("http://127.0.0.1:800/user/logout",'window','toolbar=1,location=1,directories=1,status=1,menubar=1,scrollbars=1,resizable=1');
self.close;
}