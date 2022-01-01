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
        //alert.animateClass('fadeIn animated');
        KTUtil.animateClass(alert[0], 'fadeIn animated');
        alert.find('span').html(msg);
        setTimeout(function () {
            alert.fadeOut();
        }, 2000);
    }

    // Private Functions
    var displaySignUpForm = function () {
        login.removeClass('kt-login--forgot');
        login.removeClass('kt-login--signin');

        login.addClass('kt-login--signup');
        KTUtil.animateClass(login.find('.kt-login__signup')[0], 'flipInX animated');
    }

    var displaySignInForm = function () {
        login.removeClass('kt-login--forgot');
        login.removeClass('kt-login--signup');

        login.addClass('kt-login--signin');
        KTUtil.animateClass(login.find('.kt-login__signin')[0], 'flipInX animated');
        //login.find('.kt-login__signin').animateClass('flipInX animated');
    }

    var displayForgotForm = function () {
        login.removeClass('kt-login--signin');
        login.removeClass('kt-login--signup');

        login.addClass('kt-login--forgot');
        //login.find('.kt-login--forgot').animateClass('flipInX animated');
        KTUtil.animateClass(login.find('.kt-login__forgot')[0], 'flipInX animated');

    }

    var handleFormSwitch = function () {
        $('#kt_login_forgot').click(function (e) {
            e.preventDefault();
            displayForgotForm();
        });

        $('#kt_login_forgot_cancel').click(function (e) {
            e.preventDefault();
            displaySignInForm();
        });

        $('#kt_login_signup').click(function (e) {
            e.preventDefault();
            displaySignUpForm();
        });

        $('#kt_login_signup_cancel').click(function (e) {
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
                    password: {
                        required: true
                    }
                }
            });

            if (!form.valid()) {
                return;
            }

            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

//             $.ajax({
//                url: 'http://127.0.0.1:800/user/validate',
//                success: function (response, status, xhr, $form) {
//                    var title = response["title"];
//                    var message = response["message"];
//                    if (title === "fail") {
//                        setTimeout(function () {
//                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
//                            showErrorMsg(form, 'success', message);
//                        }, 2000);
//                    } else {
//                        setTimeout(function () {
//                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
//                            showErrorMsg(form, 'success', message);
//                        }, 2000);
//
//                    }
//
//                }
//            });
            form.submit();

        });
    }

    var handleSignUpFormSubmit = function () {
        $('#kt_login_signup_submit').click(function (e) {
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
                    phoneNumber: {
                        required: true
                    },
                    emailAddress: {
                        required: true,
                        email: true
                    },
                    companyName: {
                        required: true
                    },
                    companyTitle: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    rpassword: {
                        required: true
                    },
                    agree: {
                        required: true
                    }
                }
            });

            if (!form.valid()) {
                return;
            }


            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

            var pass1 = $("#pass").val();
            var pass2 = $("#rpassword").val();

            if (pass1 !== pass2) {
                setTimeout(function () {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'danger', "Passwords entered do not match");
                }, 1000);
                return;
            }
             $.ajax({
                url: 'http://127.0.0.1:800/user/signUp/addUser',
                success: function (response, status, xhr, $form) {
                    var title = response["title"];
                    var message = response["message"];
                    if (title === "fail") {
                        setTimeout(function () {
                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                            showErrorMsg(form, 'success', message);
                        }, 2000);
                    } else {
                        setTimeout(function () {
                            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                            form.clearForm();
                            form.validate().resetForm();
                            displaySignInForm();
                            var signInForm = login.find('.kt-login__signin form');
                            signInForm.clearForm();
                            signInForm.validate().resetForm();
                            showErrorMsg(signInForm, 'success', message);
                        }, 2000);
                    }
                },
                error: function (res) {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    var response = JSON.parse(res);
                    console.log(response);
                    swal.fire({
                        "title": "",
                        "text": "Connection error. Please try again later..",
                        "type": "error",
                        "confirmButtonClass": "btn btn-secondary"
                    });
                }
            });
        });

        $('#email').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');

            form.validate({
                rules: {
                    emailAddress: {
                        email: true,
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            var user_id = $("#email").val();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

             $.ajax({
                url: 'http://127.0.0.1:800/user/available/check_availability',
                data: {
                    searchItem: user_id
                },
                success: function (res) {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    var message = res["message"];
                    var title = res["title"];
                    var messageType = "";
                    if (title === "available") {
                        messageType = "success";
                    } else {
                        messageType = "danger";
                    }
                    setTimeout(function () {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        showErrorMsg(form, messageType, message);
                    }, 2000);
                },
                error: function (res) {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    var response = JSON.parse(res);
                    console.log(response);
                    swal.fire({
                        "title": "",
                        "text": "Connection error. Please try again later..",
                        "type": "error",
                        "confirmButtonClass": "btn btn-secondary"
                    });
                }
            });

        });

    };

    var handleForgotFormSubmit = function () {
        $('#kt_login_forgot_submit').click(function (e) {
            e.preventDefault();

            var btn = $(this);
            var form = $(this).closest('form');

            form.validate({
                rules: {
                    email: {
                        required: true,
                        email: true
                    }
                }
            });

            if (!form.valid()) {
                return;
            }

            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);

             $.ajax({
                url: '',
                success: function (response, status, xhr, $form) {
                    // similate 2s delay
                    setTimeout(function () {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false); // remove
                        form.clearForm(); // clear form
                        form.validate().resetForm(); // reset validation states

                        // display signup form
                        displaySignInForm();
                        var signInForm = login.find('.kt-login__signin form');
                        signInForm.clearForm();
                        signInForm.validate().resetForm();

                        showErrorMsg(signInForm, 'success', 'Cool! Password recovery instruction has been sent to your email.');
                    }, 2000);
                },
                error: function (res) {
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    var response = JSON.parse(res);
                    console.log(response);
                    swal.fire({
                        "title": "",
                        "text": "Connection error. Please try again later..",
                        "type": "error",
                        "confirmButtonClass": "btn btn-secondary"
                    });
                }
            });
        });
    }

    // Public Functions
    return {
        // public functions
        init: function () {
            handleFormSwitch();
            handleSignInFormSubmit();
            handleSignUpFormSubmit();
            handleForgotFormSubmit();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTLoginGeneral.init();
});