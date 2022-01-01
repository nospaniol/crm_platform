<%@include file="administratortablehead.jsp" %>

<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid">
        <div class="row">  
            <div class="col-xl-4 col-lg-4 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <div class="kt-portlet__head kt-portlet__head--lg">
                        <div class="row">
                            <div clsss="col-6">                             
                                <button style="font-size:8px;" onclick="printExcel()" type="button" style="font-size:12px;" class="btn btn-dark">
                                    <i class="la la-file-excel-o"></i>EXPORT EXCEL
                                </button>
                            </div>
                            <div clsss="col-6">
                                <button style="font-size:8px;" onclick="printPdf()" type="button" style="font-size:12px;" class="btn btn-brand">
                                    <i class="la la-file-pdf-o"></i>PRINT PDF
                                </button>
                            </div> 
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-8 col-lg-8 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <form class="kt-form row" method="post">
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <div class="kt-portlet__head-label">
                                <div class="form-group" style="padding-left:2vh; padding-top:4vh;">
                                    <input  style="font-size:10px;"   class="form-control" type="date" placeholder="start date" id="startDate" name="startDate"></select>
                                    <span class="form-text text-muted">Please select  the start date.</span> 
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <div class="kt-portlet__head-label">
                                <div class="form-group" style="padding-left:2vh; padding-top:4vh;">
                                    <input  style="font-size:10px;"  class="form-control" type="date" placeholder="end date" id="endDate" name="endDate"></select>
                                    <span class="form-text text-muted">Please select  end date.</span> 
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                            <div class="kt-portlet__head-label">
                                <div class="form-group" style="padding-top:4vh;">
                                    <button  id="kt_search_info" class=" btn btn-brand btn-pill btn-elevate">Search<i class="kt-menu__link-icon flaticon2-search kt-margin-r-15"></i></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>       
        <div class="row"> 
            <div class="col-xl-12 col-lg-12 order-lg-2 order-xl-1">
                <div class="kt-portlet kt-portlet--height-fluid">
                    <table class="table table-striped- table-bordered table-hover table-checkable" id="transaction_info" >
                        <thead class="thead-dark">             
                            <tr>
                                <th style="color:white;font-size:10px;">Client</th> 
                                <th style="color:white;font-size:10px;">ID#</th> 
                                <th style="color:white;font-size:10px;">License Plate</th>                        
                                <th style="color:white;font-size:10px;">State</th>
                                <th style="color:white;font-size:10px;">Agency</th>
                                <th style="color:white;font-size:10px;">Exit / Date Time</th>
                                <th style="color:white;font-size:10px;">Posted / Date Time</th>
                                <th style="color:white;font-size:10px;">Exit Location</th>
                                <th style="color:white;font-size:10px;">Exit Lane</th>
                                <th style="color:white;font-size:10px;">Amount</th>
                                <th style="color:white;font-size:10px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody id="data_info">
                            <c:choose>
                                <c:when test="${allTransactions.size() > 0 }">
                                    <c:forEach var="transaction" items="${allTransactions}">                            
                                        <tr>
                                            <td style="font-size:10px;">${transaction.clientProfile.companyName}</td>
                                            <td style="font-size:10px;">${transaction.transactionId}</td>
                                            <td style="font-size:10px;">${transaction.vehicle.licensePlate}</td>
                                            <td style="font-size:10px;">${transaction.state}</td>
                                            <td style="font-size:10px;">${transaction.agency}</td>
                                            <td style="font-size:10px;">${transaction.exitDateTime}</td>
                                            <td style="font-size:10px;">${transaction.postedDateTime}</td>
                                            <td style="font-size:10px;">${transaction.exitLocation}</td>
                                            <td style="font-size:10px;">${transaction.exitLane}</td>
                                            <td style="font-size:10px;"><b>${transaction.amount}</b></td>
                                            <td nowrap>
                                                <span class="dropdown">'
                                                    <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" data-toggle="dropdown" aria-expanded="true">
                                                        <i class="la la-ellipsis-h"></i>
                                                    </a>
                                                    <div class="dropdown-menu dropdown-menu-right">
                                                        <button  class="dropdown-item"  type="button" data-toggle="modal" data-target="#myModal${transaction.transactionId}">
                                                            <i class="la la-edit"></i> Reverse Transaction</button>
                                                    </div>
                                                </span>
                                            </td>
                                        </tr>
                                    <div id="myModal${transaction.transactionId}" class="modal fade" role="dialog">
                                        <div class="modal-dialog">
                                            <!-- Modal content-->
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title">BATCH TRANSACTION  REVERSAL</h4>
                                                    <p>Related transaction to this batch will be deleted</p>
                                                </div>
                                                <div class="modal-body">  
                                                    <div class="form">
                                                        <div class="row">
                                                            <div class="form-group">
                                                                <input  class="form-control" type="hidden" id="transactionId${transaction.transactionId}" value="${transaction.transactionId}"/>
                                                            </div>                                                          
                                                        </div>                                                    
                                                        <div class="row">
                                                            <div class="col-6 kt-login__actions">
                                                                <button   onclick="reverseSolution${transaction.transactionId}()" class="btn btn-brand btn-pill btn-elevate" style="background-color:red">REVERSE TRANSACTION<i class="kt-menu__link-icon flaticon2-refresh"></i></button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button  type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <script>
                                        function reverseSolution${transaction.transactionId}() {
                                            var transactionId = $('#transactionId${transaction.transactionId}').val();
                                            const swalWithBootstrapButtons = Swal.mixin({
                                                customClass: {
                                                    confirmButton: 'btn btn-brand',
                                                    cancelButton: 'btn btn-dark'
                                                },
                                                buttonsStyling: false
                                            });
                                            swalWithBootstrapButtons.fire({
                                                title: 'Are you sure?',
                                                text: "You are about to reverse transactions !",
                                                icon: 'warning',
                                                showCancelButton: true,
                                                confirmButtonText: 'Yes, reverse transaction!',
                                                cancelButtonText: 'No, Go back!',
                                                reverseButtons: true
                                            }).then((result) => {
                                                if (result.value) {
                                                    $.ajax({
                                                        type: "POST",
                                                        url: 'http://127.0.0.1:800/transaction/reverse/transaction/' + transactionId,
                                                        success: function (res) {
                                                            var title = res["title"];
                                                            var message = res["message"];
                                                            if (title === "fail") {
                                                                swalWithBootstrapButtons.fire(
                                                                        'Innovative Toll',
                                                                        message,
                                                                        'error'
                                                                        );
                                                                return;
                                                            }
                                                            if (title === "success") {
                                                                swalWithBootstrapButtons.fire(
                                                                        'Innovative Toll',
                                                                        message,
                                                                        'success'
                                                                        );
                                                                setTimeout(function () {
                                                                    window.location.reload();
                                                                },
                                                                        1000);
                                                                return;
                                                            }
                                                        },
                                                        error: function (res) {
                                                            console.log(res);
                                                        }
                                                    });
                                                } else if (result.dismiss === Swal.DismissReason.cancel) {
                                                    swalWithBootstrapButtons.fire(
                                                            'Innovative Toll',
                                                            'Reverted',
                                                            'error'
                                                            );
                                                }
                                            });
                                        }
                                    </script>

                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;">No Data</td>
                                    <td style="font-size:10px;"></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>



                    <table class="table table-striped- table-bordered table-hover table-checkable" id="total_info" >
                        <thead>
                            <tr>                               
                                <th style="color:white;font-size:10px;"></th> 
                                <th style="color:white;font-size:10px;"></th> 
                                <th style="color:white;font-size:10px;"></th>                        
                                <th style="color:white;font-size:10px;"></th>
                                <th style="color:white;font-size:10px;"></th>
                                <th style="color:white;font-size:10px;"></th>
                                <th style="color:white;font-size:10px;"></th>
                                <th style="color:white;font-size:10px;"></th>
                                <th style="color:white;font-size:10px;"></th>
                                <th>TOTAL AMOUNT</th>
                                <th style="color:white;font-size:10px;"></th>
                            </tr>
                        </thead>
                        <tbody id="total_body">
                            <tr>
                                <td style="font-size:10px;"><b>TOTAL AMOUNT</b></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"></td>
                                <td style="font-size:10px;"><b>${totalAmount}</b></td>
                                <td nowrap></td>
                        </tbody>
                        </tr>
                    </table>
                    <c:if test="${allTransactions.size() > 0 }">
                        <nav aria-label="Pages">
                            <ul class="pagination pagination-sm flex-wrap">
                                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                    <c:forEach begin="0" end="${totalPages-1}" var="page">                             
                                    <li class="page-item"><a class="page-link" href="<c:url value='http://127.0.0.1:800/transaction/view_transaction?page=${page}&size=${size}' />">${page+1}</a></li>
                                    </c:forEach>
                                <li class="page-item"><a class="page-link" href="#">Next</a></li>
                            </ul>
                        </nav>
                    </c:if>
                </div> 
            </div> 
        </div> 
    </div>
</div>



<%@include file="dashtablefooter.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/view-transaction.js?v=1001" />"   type="text/javascript"></script>
<script>

                                        (function ($) {

                                            jQuery.fn.pagShrink = function () {

                                                var $this = $(this);

                                                for (var i = 0, max = $this.length; i < max; i++)
                                                {
                                                    new pagShrink($($this[i]));
                                                }

                                                /**
                                                 * Class
                                                 * @param oContainer
                                                 */
                                                function pagShrink(oContainer)
                                                {
                                                    this._getWidthOfItem = function () {

                                                        var oPageItem = $('.page-item');
                                                        var iWidthItem = Math.round($(oPageItem[(oPageItem.length - 1)]).outerWidth());

                                                        return Math.round(iWidthItem);
                                                    }

                                                    this._doCuttOff = function (iFrom, iUntil) {

                                                        iFrom = parseInt(iFrom);
                                                        iUntil = parseInt(iUntil);

                                                        for (var iCnt = iFrom; iCnt < iUntil; iCnt++)
                                                        {
                                                            $(this.oLiElement[iCnt]).hide();
                                                        }
                                                    }

                                                    this._getAdjustValues = function (iRange, iPosition, iScope) {

                                                        iRange = parseInt(iRange);
                                                        iPosition = parseInt(iPosition);
                                                        iScope = parseInt(iScope);

                                                        var iHalf = parseInt(iScope / 2);
                                                        var iLeft = parseInt(iPosition - iHalf);
                                                        var iTakeOverfromLeft = (iLeft < 0) ? (iLeft * -1) : 0;
                                                        var iRight = parseInt(iPosition + iHalf);
                                                        var iTakeOverfromRight = ((iRight > iRange) ? (iRange - iRight) : 0);

                                                        if (iTakeOverfromRight < 0)
                                                        {
                                                            iLeft = (iLeft + iTakeOverfromRight);
                                                            iRight = (iRight + ((iRange - iRight)));
                                                        }

                                                        if (iTakeOverfromLeft > 0)
                                                        {
                                                            iRight = (iRight + iTakeOverfromLeft);
                                                            iLeft = (iLeft + iTakeOverfromLeft);
                                                        }

                                                        return [iLeft, iRight];
                                                    }

                                                    this._init = function () {

                                                        $(this.oLiElement).show();
                                                        this.iPaginationWidth = oContainer.outerWidth();
                                                        this.iWidthItem = this._getWidthOfItem();
                                                        this.oLiElement = oContainer.find("li");
                                                        this.iMaxItems = Math.round(this.iPaginationWidth / this.iWidthItem);
                                                        this.iTooMuch = Math.round(this.oLiElement.length - this.iMaxItems);
                                                        this.iActive = $('li.active').index();
                                                        this.aAdjust = this._getAdjustValues(
                                                                this.oLiElement.length,
                                                                this.iActive,
                                                                (this.iMaxItems - 5)
                                                                );
                                                        this._doCuttOff(2, (this.aAdjust[0] - 2));
                                                        this._doCuttOff((this.aAdjust[1]), (this.oLiElement.length - 2));
                                                    }

                                                    //--------------------------------------

                                                    this._init();
                                                    var hTimer;

                                                    $(window).resize(
                                                            $.proxy(function () {

                                                                clearTimeout(hTimer);
                                                                hTimer = setTimeout($.proxy(function () {
                                                                    this._init()
                                                                }, this), 100);
                                                            }, this)
                                                            );
                                                }
                                            };
                                        }(jQuery));

                                        $(document).ready(function () {
                                            $("input[type='search']").wrap("<form>");
                                            $("input[type='search']").closest("form").attr("autocomplete", "off");
                                        });

                                        function printPdf() {
                                            var doc = new jsPDF();
                                            doc.addPage('a4', 'landscape');
                                            var tbl = $('#transaction_info').clone();
                                            tbl.find('tr th:nth-child(11), tr td:nth-child(11)').remove();
                                            var res = doc.autoTableHtmlToJson(tbl.get(0));

                                            var imgData = document.getElementById("client-logo");
                                            doc.addImage(imgData, 'PNG', 120, 20, 45, 35);
                                            doc.autoTable(res.columns, res.data, {
                                                startY: 60,
                                                styles: {
                                                    overflow: 'linebreak',
                                                    fontSize: 8,
                                                    columnWidth: 'wrap'
                                                },
                                                columnStyles: {
                                                    1: {columnWidth: 'auto'}
                                                },
                                                createdCell: function (cell, data) {
                                                    var a = this;
                                                }
                                            });
                                            doc = addWaterMark(doc);
                                            doc.deletePage(1);
                                            doc.save('innovativetoll.pdf');
                                        }

                                        function printExcel() {
                                            console.log("*****GENERATE EXCEL*******");
                                            var tbl = $('#transaction_info').clone();
                                            tbl.find('tr th:nth-child(11), tr td:nth-child(11)').remove();
                                            $(tbl).table2excel({
                                                exclude: ".noExl",
                                                name: "Innovative",
                                                filename: "innovative-spreadsheet", //do not include extension
                                                fileext: ".xls"
                                            });
                                        }

</script>
</body>
<!-- end::Body -->
</html>