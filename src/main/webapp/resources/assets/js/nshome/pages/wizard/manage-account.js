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
        $('.kt_innovative_deposit').click(function (e) {
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

            var cancelItem = form.serializeArray()[0]["value"];
            var clientName = form.serializeArray()[1]["value"];
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });

            var amount;
            swal({
                title: 'Innovative Toll',
                text: "Deposit amount for " + clientName + ": ",
                icon: 'info',
                confirmButtonText: 'Yes, deposit!',
                cancelButtonText: 'No, Go back!',
                buttons: true,
                dangerMode: true,
                content: "input",
            }).then((value) => {
                if (value === null || value === '') {
                    swal({
                        icon: 'warning',
                        title: 'Innovative Toll',
                        text: "No amount was deposited"
                    });
                    return;
                }
                if (isNaN(value)) {
                    swal({
                        icon: 'error',
                        title: 'Innovative Toll',
                        text: "Value must be numeric"
                    });
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: 'http://127.0.0.1:800/account/deposit/' + value + '/' + cancelItem,
                    success: function (res) {
                        console.log("successfull");
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
                        console.log("failed");
                        console.log(res);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        showErrorMsg(form, 'success', 'System error. Please try again later.');
                    }
                })
            });
        });

        $('.kt_innovative_subtract').click(function (e) {
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

            var cancelItem = form.serializeArray()[0]["value"];
            var clientName = form.serializeArray()[1]["value"];
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-brand',
                    cancelButton: 'btn btn-dark'
                },
                buttonsStyling: false
            });

            var amount;
            swal({
                title: 'Innovative Toll',
                text: "Subtract amount from " + clientName + ": ",
                icon: 'info',
                buttons: true,
                dangerMode: true,
                content: "input",
            }).then((value) => {
                if (value === null || value === '') {
                    swal({
                        icon: 'warning',
                        title: 'Innovative Toll',
                        text: "No amount was deposited"
                    });
                    return;
                }
                if (isNaN(value)) {
                    swal({
                        icon: 'error',
                        title: 'Innovative Toll',
                        text: "Value must be numeric"
                    });
                    return;
                }
                $.ajax({
                    type: "POST",
                    url: 'http://127.0.0.1:800/account/credit/' + value + '/' + cancelItem,
                    success: function (res) {
                        console.log("successfull");
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
                        console.log("failed");
                        console.log(res);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        showErrorMsg(form, 'success', 'System error. Please try again later.');
                    }
                })
            });
        });


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