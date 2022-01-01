"use strict";
// Class definition
var KTMorrisChartsDemo = function () {

    // Private functions
    var initSpendingTable = function () {
        var table = $('#spending_info');
        table.DataTable({
            responsive: true,
            bFilter: false, 
            bInfo: false,
            "paging": false,
            pagingType: 'full_numbers',
            columnDefs: [
                {
                    targets: -1,
                    title: 'Amount',
                    orderable: true
                },
                {
                    targets: 4,
                    render: function (data, type, full, meta) {
                        var status = {
                            1: {'title': 'Pending', 'class': 'kt-badge--brand'},
                            2: {'title': 'Delivered', 'class': ' kt-badge--danger'},
                            3: {'title': 'Canceled', 'class': ' kt-badge--primary'},
                            4: {'title': 'Success', 'class': ' kt-badge--success'},
                            5: {'title': 'Info', 'class': ' kt-badge--info'},
                            6: {'title': 'Danger', 'class': ' kt-badge--danger'},
                            7: {'title': 'Warning', 'class': ' kt-badge--warning'},
                        };
                        if (typeof status[data] === 'undefined') {
                            return data;
                        }
                        return '<span class="kt-badge ' + status[data].class + ' kt-badge--inline kt-badge--pill">' + status[data].title + '</span>';
                    },
                },
                {
                    targets: 3,
                    render: function (data, type, full, meta) {
                        var status = {
                            1: {'title': 'Online', 'state': 'danger'},
                            2: {'title': 'Retail', 'state': 'primary'},
                            3: {'title': 'Direct', 'state': 'success'},
                        };
                        if (typeof status[data] === 'undefined') {
                            return data;
                        }
                        return '<span class="kt-badge kt-badge--' + status[data].state + ' kt-badge--dot"></span>&nbsp;' +
                                '<span class="kt-font-bold kt-font-' + status[data].state + '">' + status[data].title + '</span>';
                    },
                },
            ],
        });
    };


    var lineInfo = function () {

        $.ajax({
            url: 'http://127.0.0.1:800/client_spending/daily/spendings/',
            type: 'GET',
            success: function (res) {
                var response = res;
                var title = response["title"];
                var message = response["message"];
                if (title === "fail") {

                    var events = [];
                    return;
                }
                if (title === "success") {
                    var search_results = response["results"];
                    var events = [];
                    search_results.forEach((item) => {

                        var ns_events = {
                            y: item.tollDate,
                            a: item.tollAmount
                        }
                        events.push(ns_events);
                    });
                    //Pass data into to the line chart
                    new Morris.Line({
                        element: 'line_info',
                        data: events,
                        // The name of the data record attribute that contains x-values.
                        xkey: 'y',
                        // A list of names of data record attributes that contain y-values.
                        ykeys: ['a'],
                        // Labels for the ykeys -- will be displayed when you hover over the
                        // chart.
                        labels: ['Amount'],
                        lineColors: ['#24a5ff']
                    });

                    //Data has been added

                }
            },
            error: function (xhr) {
                var errorMsg = 'Collecting data request failed: ' + JSON.stringify(xhr);
                console.log(errorMsg);
            }
        });


        // LINE CHART

    }

    var barInfo = function () {
        $.ajax({
            url: 'http://127.0.0.1:800/client_spending/monthly/spendings/',
            type: 'GET',
            success: function (res) {
                var response = res;
                var title = response["title"];
                var message = response["message"];

                if (title === "fail") {

                    var events = [];
                    return;
                }
                if (title === "success") {

                    var search_results = response["results"];
                    var events = [];
                    search_results.forEach((item) => {

                        var ns_events = {
                            y: item.tollDate,
                            a: item.tollAmount
                        }
                        events.push(ns_events);
                    });

                    //Pass data into to the pie chart

                    new Morris.Bar({
                        element: 'bar_info',
                        data: events,
                        xkey: 'y',
                        ykeys: ['a'],
                        labels: ['Amount'],
                        barColors: ['#24a5ff']
                    });
                }
            },
            error: function (xhr) {
                var errorMsg = 'Collecting data request failed: ' + JSON.stringify(xhr);
                console.log(errorMsg);
            }
        });
    };

    var pieInfo = function () {
        $.ajax({
            url: 'http://127.0.0.1:800/client_spending/agency/spendings/',
            type: 'GET',
            success: function (res) {
                var response = res;
                var title = response["title"];
                var message = response["message"];
                if (title === "fail") {

                    var events = [];
                    return;
                }
                if (title === "success") {
                    var search_results = response["results"];
                    var events = [];
                    search_results.forEach((item) => {
                        var ns_events = {
                            label: item.tollDate,
                            value: item.tollAmount
                        }
                        events.push(ns_events);
                    });

                    new Morris.Donut({
                        element: 'pie_info',
                        data: events,
                        colors: ['#24a5ff', '#D5705A']
                    });
                }
            },
            error: function (xhr) {
                var errorMsg = 'Collecting data request failed: ' + JSON.stringify(xhr);
                console.log(errorMsg);
            }
        });
    };

    return {
        // public functions
        init: function () {
            lineInfo();
            barInfo();
            pieInfo();
initSpendingTable();
        }
    };
}();

jQuery(document).ready(function () {
    KTMorrisChartsDemo.init();
});