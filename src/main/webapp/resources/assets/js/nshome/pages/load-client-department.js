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