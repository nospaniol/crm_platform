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


    var viewInnovativeForm = function () {
        /*
         * 
         * MONTHLY REQUESTS
         */
        $('#kt_innovative_search').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    filter_info: {
                        required: true
                    },
                    month: {
                        required: true
                    },
                    year: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            if ($("#filter_info option:selected").val() === null || $("#filter_info option:selected").val() === "") {
                return;
            }

            $('.panel-footer').hide();
            var dt = $('#innovative_data').DataTable();

            $('#data_info').empty();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var filter = $("#filter_info option:selected").val();
            var month = $("#month option:selected").val();
            var year = $("#year option:selected").val();
            if (filter === null || filter === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }
            if (filter === null || filter === '' || month === null || month === '' || year === null || year === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }

            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/client_spending/filter/monthly/by/' + month + '/' + year + '/' + filter,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    var pageTitle = res["pageTitle"];
                    var tableTitle = res["tableTitle"];
                    var total = res["total"];
                    var ttl = parseFloat(total);
                    total = ttl.toFixed(2);
                    $("#page_title").text(pageTitle);
                    $("#innovative_title").text(tableTitle);
                    $("#innovative_total").text(total);
                    if (title === "fail") {
                        $("#innovative_total").text(0.00);
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td scope="row"> <i class="flaticon2-correct"></i> ' + item.title + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '</tr>'
                                    );
                        });
                        $('#innovative_data').DataTable({
                            "iDisplayLength": 15,
                            "lengthChange": false
                        });
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        setTimeout(function () {
                        }, 2000);
                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            });


        });
        $('#kt_innovative_department_search').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    filter_info: {
                        required: true
                    },
                    month: {
                        required: true
                    },
                    year: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            if ($("#filter_info option:selected").val() === null || $("#filter_info option:selected").val() === "") {
                return;
            }
            $('.panel-footer').hide();
            var dt = $('#innovative_data').DataTable();

            $('#data_info').empty();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var filter = $("#filter_info option:selected").val();
            var month = $("#month option:selected").val();
            var year = $("#year option:selected").val();
            if (filter === null || filter === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }
            if (filter === null || filter === '' || month === null || month === '' || year === null || year === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }
            var client = $('#client').val();
            if (client === null || client === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "refresh the page please! ");
                return;
            }
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/client_spending/filter/department/monthly/by/' + client + '/' + month + '/' + year + '/' + filter,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    var pageTitle = res["pageTitle"];
                    var tableTitle = res["tableTitle"];
                    var total = res["total"];
                    var ttl = parseFloat(total);
                    total = ttl.toFixed(2);
                    $("#page_title").text(pageTitle);
                    $("#innovative_title").text(tableTitle);
                    $("#innovative_total").text(total);
                    if (title === "fail") {
                        $("#innovative_total").text(0.00);
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td scope="row"> <i class="flaticon2-correct"></i> ' + item.title + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '</tr>'
                                    );
                        });
                        $('#innovative_data').DataTable({
                            "iDisplayLength": 15,
                            "lengthChange": false
                        });
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        setTimeout(function () {
                        }, 2000);
                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            });


        });


        /*
         * 
         * ANNUAL REQUESTS
         */
        $('#kt_innovative_annual_search').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    filter_info: {
                        required: true
                    },
                    year: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            if ($("#filter_info option:selected").val() === null || $("#filter_info option:selected").val() === "") {
                return;
            }
            $('.panel-footer').hide();
            var dt = $('#innovative_data').DataTable();

            $('#data_info').empty();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var filter = $("#filter_info option:selected").val();
            var year = $("#year option:selected").val();
            if (filter === null || filter === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }
            if (filter === null || filter === '' || year === null || year === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }

            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/client_spending/filter/annual/view/by/' + year + '/' + filter,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    var pageTitle = res["pageTitle"];
                    var tableTitle = res["tableTitle"];
                    var total = res["total"];
                    var ttl = parseFloat(total);
                    total = ttl.toFixed(2);
                    $("#page_title").text(pageTitle);
                    $("#innovative_title").text(tableTitle);
                    $("#innovative_total").text(total);
                    if (title === "fail") {
                        $("#innovative_total").text(0.00);
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td scope="row"> <i class="flaticon2-correct"></i> ' + item.title + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '</tr>'
                                    );
                        });
                        $('#innovative_data').DataTable({
                            "iDisplayLength": 15,
                            "lengthChange": false
                        });
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        setTimeout(function () {
                        }, 2000);
                    }

                },
                error: function (res) {
                    console.log(res);
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                    showErrorMsg(form, 'error', 'System error. Please try again later.');
                }
            });


        });

 
        $('#kt_innovative_annual_department_search').click(function (e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            form.validate({
                rules: {
                    client: {
                        required: true
                    },
                    filter_info: {
                        required: true
                    },
                    year: {
                        required: true
                    }
                }
            });
            if (!form.valid()) {
                return;
            }
            if ($("#filter_info option:selected").val() === null || $("#filter_info option:selected").val() === "") {
                return;
            }

            $('.panel-footer').hide();
            var dt = $('#innovative_data').DataTable();

            $('#data_info').empty();
            btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
            var filter = $("#filter_info option:selected").val();
            var year = $("#year option:selected").val();
            if (filter === null || filter === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }
            if (filter === null || filter === '' || year === null || year === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "select all required fields! ");
                return;
            }
            var client = $('#client').val();
            if (client === null || client === '') {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                showErrorMsg(form, 'danger', "refresh the page please! ");
                return;
            }
            $.ajax({
                type: "POST",
                url: 'http://127.0.0.1:800/tollspending/filter/annual/view/department/by/' + client + '/' + year + '/' + filter,
                success: function (res) {
                    var title = res["title"];
                    var message = res["message"];
                    var pageTitle = res["pageTitle"];
                    var tableTitle = res["tableTitle"];
                    var total = res["total"];
                    var ttl = parseFloat(total);
                    total = ttl.toFixed(2);
                    $("#page_title").text(pageTitle);
                    $("#innovative_title").text(tableTitle);
                    $("#innovative_total").text(total);
                    if (title === "fail") {
                        $("#innovative_total").text(0.00);
                        showErrorMsg(form, 'danger', message);
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    if (title === "success") {
                        var search_results = res["results"];
                        search_results.forEach((item) => {
                            $('#data_info').append(
                                    '<tr>' +
                                    '<td scope="row"> <i class="flaticon2-correct"></i> ' + item.title + '</td>' +
                                    '<td><b>' + item.amount + '</b></td>' +
                                    '</tr>'
                                    );
                        });
                        $('#innovative_data').DataTable({
                            "iDisplayLength": 15,
                            "lengthChange": false
                        });
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        setTimeout(function () {
                        }, 2000);
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
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
