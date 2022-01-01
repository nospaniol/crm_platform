<%@include file="clientheader.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content" style="padding-top:.2vh">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">
            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <!-- Month summary -->
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head" style="background-color:black;">
                        <div class="kt-portlet__head-label row">
                            <div class="col-4">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    Summary 
                                </h3>
                            </div>
                            <div class="col-5">
                                <select class="form-control" type="text" placeholder="select  department" id="month1" name="month1">
                                    <option value="" selected="selected">  ${nsmonth}</option>
                                    <option value="JANUARY">JANUARY</option>
                                    <option value="FEBRUARY">FEBRUARY</option>
                                    <option value="MARCH">MARCH</option>
                                    <option value="APRIL">APRIL</option>
                                    <option value="MAY">MAY</option>
                                    <option value="JUNE">JUNE</option>
                                    <option value="JULY">JULY</option>
                                    <option value="AUGUST">AUGUST</option>
                                    <option value="SEPTEMBER">SEPTEMBER</option>
                                    <option value="OCTOBER">OCTOBER</option>
                                    <option value="NOVEMBER">NOVEMBER</option>
                                    <option value="DECEMBER">DECEMBER</option>
                                </select>
                            </div>
                            <div class="col-3">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    <select style="width:100px;" class="form-control" type="text" placeholder="select department" id="year1" name="year1">
                                        <option value="" selected="selected">${nsyear}</option>
                                        <option value="2020">2020</option>
                                        <option value="2021">2021</option>
                                    </select>
                                </h3>
                            </div>
                        </div>
                    </div>
                    <div class="kt-widget14">
                        <div class="row">
                            <h5 class="kt-portlet__head-title text-uppercase text-black-50">
                                Tolls
                            </h5>
                        </div>  
                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    MTD Toll Spend
                                </p>
                            </div>
                            <div class="col-4">
                                <p class="kt-widget3__text"><strong> $ </strong> ${spendingAmount}</p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Vehicle Count
                                </p>
                            </div>
                            <div class="col-4">
                                <p class="kt-widget3__text">   ${activeVehicleCount}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Total Transactions
                                </p>
                            </div>
                            <div class="col-4">
                                <p class="kt-widget3__text">  ${transactionCount}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Disputed Transactions
                                </p>
                            </div>
                            <div class="col-4">
                                <p class="kt-widget3__text"> ${disputedTransactionCount}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Total Savings (Fees Waived)
                                </p>
                            </div>
                            <div class="col-4">
                                <p class="kt-widget3__text"><strong> $ </strong>  ${totalSavings}</p>
                            </div>
                        </div>

                        <div class="row">
                            <h5 class="kt-portlet__head-title text-uppercase text-black-50">
                                Citations
                            </h5>
                        </div> 
                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Red light
                                </p>
                            </div>
                            <div class="col-2">
                                <p class="kt-widget3__text">
                                    ${redLightCount}
                                </p>
                            </div>
                            <div class="col-2">
                                <p class="kt-widget3__text">
                                    $  ${redLightAmount}
                                </p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Speed Ticket
                                </p>
                            </div>
                            <div class="col-2">
                                <p class="kt-widget3__text">
                                    ${speedTicketCount}
                                </p>
                            </div>
                            <div class="col-2">
                                <p class="kt-widget3__text">
                                    $  ${speedTicketAmount}
                                </p>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-8">
                                <p class="kt-widget3__text">
                                    Parking
                                </p>
                            </div>
                            <div class="col-2">
                                <p class="kt-widget3__text">
                                    ${parkingCount}
                                </p>
                            </div>
                            <div class="col-2">
                                <p class="kt-widget3__text">
                                    $  ${parkingAmount}
                                </p>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <!-- Graphical view -->
            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <!-- Month summary -->
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head" style="background-color:black;">
                        <div class="kt-portlet__head-label row">
                            <div class="col-5">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    Daily Toll Spending 
                                </h3>
                            </div>
                            <div class="col-4">
                                <select class="form-control" type="text" placeholder="select  department" id="month2" name="month2">
                                    <option value="" selected="selected">  ${nsmonth}</option>
                                    <option value="JANUARY">JANUARY</option>
                                    <option value="FEBRUARY">FEBRUARY</option>
                                    <option value="MARCH">MARCH</option>
                                    <option value="APRIL">APRIL</option>
                                    <option value="MAY">MAY</option>
                                    <option value="JUNE">JUNE</option>
                                    <option value="JULY">JULY</option>
                                    <option value="AUGUST">AUGUST</option>
                                    <option value="SEPTEMBER">SEPTEMBER</option>
                                    <option value="OCTOBER">OCTOBER</option>
                                    <option value="NOVEMBER">NOVEMBER</option>
                                    <option value="DECEMBER">DECEMBER</option>
                                </select>
                            </div>

                            <div class="col-3">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    <select style="width:100px;" class="form-control" type="text" placeholder="select department" id="year2" name="year2">
                                        <option value="" selected="selected">${nsyear}</option>
                                        <option value="2020">2020</option>
                                        <option value="2021">2021</option>
                                    </select>
                                </h3>
                            </div>
                        </div>
                    </div>
                    <div class="kt-widget14">
                        <div id="line_info" style="height:300px;"></div>
                    </div>
                </div>  
            </div>

            <!-- final toll amount -->
            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <!-- Month summary -->
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head" style="background-color:black;">
                        <div class="kt-portlet__head-label row">
                            <div class="col-5">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    MTD Toll Spending 
                                </h3>
                            </div>
                            <div class="col-4">
                                <select class="form-control" type="text" placeholder="select  department" id="month3" name="month3">
                                    <option value="" selected="selected">  ${nsmonth}</option>
                                    <option value="JANUARY">JANUARY</option>
                                    <option value="FEBRUARY">FEBRUARY</option>
                                    <option value="MARCH">MARCH</option>
                                    <option value="APRIL">APRIL</option>
                                    <option value="MAY">MAY</option>
                                    <option value="JUNE">JUNE</option>
                                    <option value="JULY">JULY</option>
                                    <option value="AUGUST">AUGUST</option>
                                    <option value="SEPTEMBER">SEPTEMBER</option>
                                    <option value="OCTOBER">OCTOBER</option>
                                    <option value="NOVEMBER">NOVEMBER</option>
                                    <option value="DECEMBER">DECEMBER</option>
                                </select>
                            </div>

                            <div class="col-3">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    <select style="width:100px;" class="form-control" type="text" placeholder="select department" id="year3" name="year3">
                                        <option value="" selected="selected">${nsyear}</option>
                                        <option value="2020">2020</option>
                                        <option value="2021">2021</option>
                                    </select>
                                </h3>
                            </div>
                        </div>
                    </div>

                    <div class="kt-portlet__head-label row">
                        <div class="col-xl-8 col-lg-8 order-lg-2 order-xl-1">
                            <div class="kt-widget3__item">
                                <div id="pie_info" style="height:400px;"></div>
                            </div>
                            <div class="kt-widget3__item">
                                <div> <select class="form-control" style="font-size:10px;" id="agencies_labels"></select></div>
                            </div>
                        </div>

                        <div class="col-xl-4 col-lg-4 order-lg-4 order-xl-1">
                            <div id="" class="kt-widget3__item">
                                <table class="table table-striped table-bordered table-hover table-checkable">
                                    <thead class="thead text-uppercase">
                                        <tr>                      
                                            <th style="color:black">Agency</th>
                                        </tr>
                                    </thead>
                                    <tbody  id="agencies_label" style="font-size:10px;overflow:scroll; height:100px;" >
                                    </tbody>                                    
                                </table> 


                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- final year toll amount -->
        <div class="row">
            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <!-- Month summary -->
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head" style="background-color:black;">
                        <div class="kt-portlet__head-label">
                            <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                YTD Tollway Spending (${nsyear})
                            </h3>
                        </div>
                    </div>
                    <div class="kt-widget3__item">
                        <div class="kt-portlet kt-portlet--tab">
                            <div class="kt-portlet__body">
                                <div id="bar_info" style="height:300px;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <!-- Month summary -->
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head" style="background-color:black;">
                        <div class="kt-portlet__head-label row">
                            <div class="col-5">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    MTD Top Vehicles
                                </h3>
                            </div>
                            <div class="col-4">
                                <select class="form-control" type="text" placeholder="select  department" id="month4" name="month4">
                                    <option value="" selected="selected">  ${nsmonth}</option>
                                    <option value="JANUARY">JANUARY</option>
                                    <option value="FEBRUARY">FEBRUARY</option>
                                    <option value="MARCH">MARCH</option>
                                    <option value="APRIL">APRIL</option>
                                    <option value="MAY">MAY</option>
                                    <option value="JUNE">JUNE</option>
                                    <option value="JULY">JULY</option>
                                    <option value="AUGUST">AUGUST</option>
                                    <option value="SEPTEMBER">SEPTEMBER</option>
                                    <option value="OCTOBER">OCTOBER</option>
                                    <option value="NOVEMBER">NOVEMBER</option>
                                    <option value="DECEMBER">DECEMBER</option>
                                </select>
                            </div>


                            <div class="col-3">
                                <h3 class="kt-portlet__head-title text-uppercase  text-white">
                                    <select style="width:100px;" class="form-control" type="text" placeholder="select department" id="year4" name="year4">
                                        <option value="" selected="selected">${nsyear}</option>
                                        <option value="2020">2020</option>
                                        <option value="2021">2021</option>
                                    </select>
                                </h3>
                            </div>

                        </div>
                    </div>
                    <div class="kt-portlet kt-portlet--height-fluid">
                        <div class="kt-widget3__item">
                            <table class="table table-striped table-bordered table-hover table-checkable" id="spending_info" >
                                <thead class="thead text-uppercase">
                                    <tr>                      
                                        <th style="color:black; font-size:12px;">Vehicle</th>
                                        <th style="color:black; font-size:12px;">State</th>
                                        <th style="color:black; font-size:12px;">Tag Type</th>
                                        <th style="color:black; font-size:12px;">Axles</th>
                                        <th style="color:black; font-size:12px;" >Amount</th>
                                    </tr>
                                </thead>
                                <tbody id="data_info">

                                </tbody>                                    
                            </table>                 
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<%@include file="clientdashtablefooter.jsp" %>
<script>



    /****
     * 
     * Monthly readings
     * 
     */
    displayMonthlyChart();
    function displayMonthlyChart() {
        var monthlies = [];

        monthlies =${monthlies};

        var allMonths = ['JANUARY', 'FEBRUARY', 'MARCH', 'APRIL', 'MAY', 'JUNE', 'JULY', 'AUGUST', 'SEPTEMBER', 'OCTOBER', 'NOVEMBER', 'DECEMBER'];
        function getMonth(monthStr) {
            return new Date(monthStr + '-1-01').getMonth() + 1;
        }
        monthlies.sort(function (a, b) {
            getMonth(a);
            return allMonths.indexOf(a.y) > allMonths.indexOf(b.y);
        });
        new Morris.Bar({
            element: 'bar_info',
            data: monthlies,
            xLabelAngle: 70,
            xkey: 'y',
            ykeys: ['a'],
            labels: ['Amount'],
            barColors: ['#24a5ff']
        });
    }
    /****
     * 
     * Dailies
     * 
     * 
     */
    displayDailiesChart();
    function displayDailiesChart() {
        var dailies =${dailies};
        /* var allDays = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'];
         dailies.sort(function (a, b) {
         return allDays.indexOf(a.date) > allDays.indexOf(b.date);
         });
         */
        new Morris.Line({
            element: 'line_info',
            data: dailies,
            xkey: 'date',
            ykeys: ['amount'],
            parseTime: false,
            hideHover: true,
            stacked: true,
            xLabelAngle: '60',
            labels: ['Amount: '],
            lineColors: ['#24a5ff']
        });
    }
    /* 
     * END OF CHART
     * 
     */
    displayTable();
    function displayTable() {
        var vechicle_spendings =${spendings};

        vechicle_spendings.sort(function (a, b) {
            return b.total - a.total;
        });
        vechicle_spendings.length = 5;
        var counter = 0;
        vechicle_spendings.forEach((item) => {
            counter = counter + 1;
            if (counter < 6) {
                var state = "";
                if (item.state !== null) {
                    state = item.state;
                }
                var tagType = "";
                if (item.tagType !== null) {
                    tagType = item.tagType;
                }
                var axles = "";
                if (item.axle !== null || item.axle !== "null") {
                    axles = item.axle;
                }
                $('#data_info').append(
                        '<tr>' +
                        '<td style="font-size:12px;">sesse' + item.vehicle.licensePlate + '</td>' +
                        '<td style="font-size:12px;">' + state + '</td>' +
                        '<td style="font-size:12px;">' + tagType + '</td>' +
                        '<td style="font-size:12px;">' + axles + '</td>' +
                        '<td style="font-size:12px;">' + item.total + '</td>' +
                        '</tr>'
                        );
            } else {
                return;
            }
        });
    }
    var table = $('#spending_info');
    table.DataTable({
        responsive: true,
        bFilter: false,
        bInfo: false,
        paging: false
    });

    /*
     * 
     * 
     * 
     * 
     * 
     */
    /**
     * 
     * Pie Chart
     * 
     * 
     * 
     */
    displayAgenciesChart();
    function displayAgenciesChart() {
        var agencies = [];
        var counter = 0;

        agencies =${agencies};

        new Morris.Donut({
            element: 'pie_info',
            data: agencies,
            colors: ['#24a5ff', '#A9A9A9', '#0BA462', '#39B580', '#67C69D', '#95D7BB']
        });
    }
    displayAgencies();
    function displayAgencies() {
        $("#agencies_labels").hide();
        var agency_names =${agency_names};
        counter = 0;
        agency_names.forEach((item) => {
            counter = counter + 1;
            if (counter <= 5) {
                $("#agencies_label").append('<tr ><td>' + item.agency + '</td></tr>');
            } else {
                $("#agencies_labels").show();
                $("#agencies_labels").append('<option value="">' + item.agency + '</option>');
            }
        });
    }

    /**
     * 
     * Pie chart end
     * 
     * 
     * 
     */
    window.onload = function () {
        $('#month1').get(0).selectedIndex = 0;
        $('#month2').get(0).selectedIndex = 0;
        $('#month3').get(0).selectedIndex = 0;
        $('#month4').get(0).selectedIndex = 0;
        $('#month1').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#month1 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/month/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
        });


        $('#month2').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#month2 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/month/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
        });

        $('#month3').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#month3 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/month/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
            btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
        });


        $('#month4').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#month4 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/month/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
        });

        /*******
         * 
         * 
         * 
         */
        $('#year1').get(0).selectedIndex = 0;
        $('#year2').get(0).selectedIndex = 0;
        $('#year3').get(0).selectedIndex = 0;
        $('#year4').get(0).selectedIndex = 0;
        $('#year1').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#year1 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/year/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
        });
        $('#year2').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#year2 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/year/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
        });
        $('#year3').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#year3 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/year/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
        });

        $('#year4').change(function (e) {
            e.preventDefault();
            var btn = $(this);
            var clientnam = $("#year4 option:selected").val();
            $.ajax({
                type: "POST",
                data: {
                    searchItem: clientnam
                },
                url: 'http://127.0.0.1:800/client_dash/change_mode/year/',
                success: function (res) {
                    var response = res;
                    var title = response["title"];
                    if (title === "fail") {
                        btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                        return;
                    }
                    window.location.reload();
                    btn.removeClass('kt-spinner kt-spinner--right kt-spinner--sm kt-spinner--light').attr('disabled', false);
                },
                error: function (res) {
                    var response = JSON.stringify(res);
                    console.log(response);
                }
            });
        });

    }
</script>

</body>
</html>