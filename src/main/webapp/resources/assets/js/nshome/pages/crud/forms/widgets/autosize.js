// Class definition

var KTAutosize = function () {

    // Private functions
    var demos = function () {
        // basic demo
        var nshome = $('#kt_autosize_1');
        var demo2 = $('#kt_autosize_2');

        autosize(nshome);

        autosize(demo2);
        autosize.update(demo2);
    }

    return {
        // public functions
        init: function () {
            demos();
        }
    };
}();

jQuery(document).ready(function () {
    KTAutosize.init();
});