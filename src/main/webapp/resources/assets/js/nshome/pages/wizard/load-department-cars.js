//$('select').selectric();

$('#innovative_data').DataTable({"iDisplayLength": 10, "bPaginate": false, "lengthChange": false});
$('#department-panel').hide();
$('#client').prop('selectedIndex', 0);
$('#department').prop('selectedIndex', 0);
$('#kt_innovative_department').hide();
$('#kt_innovative_plate').hide();
$('#licensePlate').empty();

function loadDepartments() {
    loadClientPlates();
    $('#department-panel').hide();
    $('#kt_innovative_department').hide();
    $('#department').prop('selectedIndex', 0);
    $('#department').empty();
    $('#departmentName').empty();
    $('#licensePlate').prop('selectedIndex', 0);
    $('#licensePlate').empty();
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
            var message = response["message"];
            var result = response["results"];
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

function loadClientPlates() {
    $('#kt_innovative_plate').hide();
    $('#licensePlate').prop('selectedIndex', 0);
    $('#licensePlate').empty();
    var btn = $(this);
    var form = $(this).closest('form');
    form.validate({
        rules: {
            client: {
                required: true
            }
        }
    });
    var clientnam = clientnam = $("#client option:selected").val();
    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:800/vehicle/load/combo/by/client?clientName=' + clientnam,
        success: function (res) {
            var title = res["title"];
            var message = res["message"];
            if (title === "fail") {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                return;
            }
            if (title === "success") {
                $('#kt_innovative_plate').show();
                var search_results = res["results"];
                $('#licensePlate').empty();
                search_results.forEach((item) => {
                    $('#licensePlate').append(
                            '<option value="' + item.licensePlate + '">' + item.licensePlate + '</option>'
                            );
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

}


function loadDepartmentPlates() {
    $('#licensePlate').prop('selectedIndex', 0);
    $('#licensePlate').empty();
    var btn = $(this);
    var form = $(this).closest('form');
    form.validate({
        rules: {
            client: {
                required: true
            },
            department: {
                required: true
            }
        }
    });
    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
    if ($("#department option:selected").val() === null || $("#department option:selected").val() === "") {
        return;
    }
    var clientnam = clientnam = $("#client option:selected").val();
    var departmen = $("#department option:selected").val();
    btn.addClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', true);
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:800/vehicle/load/by/client/department?clientName=' + clientnam + '&departmentName=' + departmen,
        success: function (res) {
            var title = res["title"];
            var message = res["message"];
            if (title === "fail") {
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                return;
            }
            if (title === "success") {
                $('#kt_innovative_plate').show();
                var search_results = res["results"];
                $('#licensePlate').empty();
                search_results.forEach((item) => {
                    $('#licensePlate').append(
                            '<option value="' + item.licensePlate + '">' + item.licensePlate + '</option>'
                            );
                });
                btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                setTimeout(function () {
                }, 2000);
            }

        },
        error: function (res) {
            console.log("not plATE");
            console.log(res);
            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
            showErrorMsg(form, 'error', 'System error. Please try again later.');
        }
    });

}
