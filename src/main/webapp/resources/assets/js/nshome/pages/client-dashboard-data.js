/* global KTUtil */

"use strict";

// Class Definition
var KTInnovativeGeneral = function () {
    var client = $("#client_session").text();

    var viewInnovative = function () {
        $.ajax({
            type: "GET",
            url: 'http://127.0.0.1:800/client_dash/load/dashboard/' +client ,
            success: function (res) {
                $("#innovative_vehicle").text(res["vehicleCount"]);
                $("#innovative_client").text(res["clientCount"]);
                $("#innovative_spending").text(res["spendingAmount"]);
                $("#innovative_transaction").text(res["transactionCount"]);

            },
            error: function (res) {
                console.log(res);
            }
        });
//        var interval = setInterval(function () {
//            myPeriodicMethod();
//        }, 3000);
//        var i = 0;
        function myPeriodicMethod() {
            $.ajax({
                type: "GET",
                url: 'http://127.0.0.1:800/administrator/load/dashboard',
                success: function (res) {
                    $("#innovative_vehicle").text(res["vehicleCount"]);
                    $("#innovative_client").text(res["clientCount"]);
                    $("#innovative_spending").text(res["spendingAmount"]);
                    $("#innovative_transaction").text(res["transactionCount"]);

                },
                error: function (res) {
                    console.log(res);
                }
            });
        }


    };

    // Public Functions
    return {
        // public functions
        init: function () {
            viewInnovative();
        }
    };
}();

// Class Initialization
jQuery(document).ready(function () {
    KTInnovativeGeneral.init();
});
