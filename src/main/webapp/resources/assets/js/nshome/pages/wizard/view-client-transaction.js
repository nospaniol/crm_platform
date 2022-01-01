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
        KTUtil.animateClass(alert[0], 'fadeIn animated');
        alert.find('span').html(msg);
        setTimeout(function () {
            alert.fadeOut();
        }, 2000);
    };
    var initTransactionTable = function () {
        var table = $('#transaction_info');
        table.DataTable({
            responsive: true,
            oLanguage: {
                sSearch: "FILTER",
            },
            "searching": false,
            "paging": false,
            "bInfo": false
        });


        var total_table = $('#total_info');
        total_table.DataTable({
            responsive: true,
            "bFilter": false,
            "info": false,
            "searchable": false,
            "paging": false
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
                url: 'http://127.0.0.1:800/client_transaction/load/by/range/' + startDate + '/' + endDate,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    var total = 0.00;
                    if (title === "fail") {
                        $('#data_info').empty();
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        showErrorMsg(form, 'success', message + ", redirecting page to search results");
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        window.open('http://127.0.0.1:800/client_transaction/search_transaction', '_blank', false);
                        return;
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
            initTransactionTable();
        }
    };
}();
// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
