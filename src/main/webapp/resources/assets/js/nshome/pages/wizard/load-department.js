//$('select').selectric();

$('#department-panel').hide();
$('#client').prop('selectedIndex', 0);
$('#department').prop('selectedIndex', 0);
$('#kt_innovative_department').hide();
function loadDepartments() {
    $('#department-panel').hide();
    $('#kt_innovative_department').hide();
    $('#department').prop('selectedIndex', 0);
    $('#department').empty();
    var btn = $('#kt_innovative_submit');
    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
    var clientnam = clientnam = $("#client option:selected").val();
    $.ajax({
        type: "POST",
        data: {
            searchItem: clientnam
        },
        url: 'http://127.0.0.1:800/client/load/departments/',
        success: function (res) {
            var response = res;
            var title = response["title"];
            if (title === "fail") {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                return;
            }
            var search_results = response["results"];
            $('#department').empty();
            $('#department').append('<option value="">Select a department first</option>');
            search_results.forEach((item) => {
                $('#department').append('<option value="' + item.departmentName + '">' + item.departmentName + '</option>');
            });
            $('#department').prop('selectedIndex', 0);
            $('#department-panel').show();
            $('#kt_innovative_department').show();
            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);

        },
        error: function (res) {
            var response = JSON.stringify(res);
            console.log(response);
        }
    });
}
