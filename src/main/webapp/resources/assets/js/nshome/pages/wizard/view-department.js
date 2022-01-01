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

    var initClientTable = function () {
        var table = $('#department_info');
        table.DataTable({
            responsive: true,
            "paging": true,
            pagingType: 'full_numbers'
        });
    };

    // Public Functions
    return {
        // public functions
        init: function () {
            initClientTable();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTLoginGeneral.init();
});