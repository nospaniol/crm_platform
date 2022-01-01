/* global KTUtil */

"use strict";

// Class Definition
var KTLoginGeneral = function () {

    var login = $('#kt_login');

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
    }


    var displaySignInForm = function () {
        login.addClass('kt-login--signin');
        KTUtil.animateClass(login.find('.kt-login__signin')[0], 'flipInX animated');
        //login.find('.kt-login__signin').animateClass('flipInX animated');
    }


    var handleFormSwitch = function () {
        $('#kt_login_forgot').click(function (e) {
            e.preventDefault();
            displaySignInForm();
        });
    }

    var handleSignInFormSubmit = function () {
        $('#kt_login_signin_submit').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');

            form.validate({
                rules: {
                    emailAddress: {
                        required: true,
                        email: true
                    },
                    firstName: {
                        required: true
                    },
                    lastName: {
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
            form.submit();

        });
    }

    // Public Functions
    return {
        // public functions
        init: function () {
            handleFormSwitch();
            handleSignInFormSubmit();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTLoginGeneral.init();
});