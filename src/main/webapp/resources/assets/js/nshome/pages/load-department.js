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
        $('#type-panel').hide();
        $('#client').change(function (e) {
            $('#department').empty();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    client: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
           var clientnam = clientnam = $("#client option:selected").val();
           
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            form.ajaxSubmit({
                async: false,
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client/load/departments/',
                success: function (res) {
                    console.log("loading departments...");
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    var response = res;
                    var title = response["title"];
                    var message = response["message"];
                    var result = response["results"];
                    if (title === "fail") {
                        showErrorMsg(form, 'success', message);
                        return;
                    }
                    var search_results = response["results"];
                    $('#department').empty();
                    search_results.forEach((item) => {
                        $('#department').append('<option value="' + item.departmentName + '">' + item.departmentName + '</option>');
                    });
                    $('#department-panel').show();
                    $('#type-panel').show();
                    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'success', 'Connection error. Please try again later.');
                }
            });
        }
        );

    };


    return {
        init: function () {
            loadDepartments();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTAppointmentGeneral.init();
});