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
    };

    var initVehicleTable = function () {
        var table = $('#vehicle_info');
        table.DataTable({
            responsive: true,
            "lengthMenu": [[100, 50, 25, 10, -1], [100, 50, 25, 10, "All"]],
            "paging": false,
            "bInfo": false,
            pagingType: 'full_numbers'
        });
    };



    // Public Functions
    return {
        // public functions
        init: function () {
            initVehicleTable();
        }
    };
}();
// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
