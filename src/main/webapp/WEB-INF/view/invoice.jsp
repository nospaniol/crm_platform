<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="row row-no-padding row-col-separator-xl">
                <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                        <div class="kt-portlet__head kt-portlet__head--noborder">
                            <div class="kt-portlet__head-label">
                                <h3 class="kt-portlet__head-title">
                                    Invoice
                                </h3>
                                <%@include file="alert.jsp" %>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="col-lg-6 col-xl-6 order-lg-1 order-xl-1">
                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                        <div class="kt-portlet__head kt-portlet__head--noborder">
                            <div class="kt-portlet__head-label">
                                <div class="row">
                                    <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1"> 
                                        <div class="kt-form__section kt-form__section--first">
                                            <div class="kt-wizard-v3__form"> 
                                                <div class="kt-login__actions">
                                                    <a href="http://127.0.0.1:800/invoice/edit/${invoice.invoiceId}" > <button class="btn btn-info">Edit Invoice<i class="kt-menu__link-icon flaticon-edit-1"></i></button></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1">
                                        <form class="kt-form" id="kt_form" method="post">
                                            <input  type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />  
                                            <div class="kt-form__section kt-form__section--first">
                                                <div class="kt-wizard-v3__form"> 
                                                    <input  type="hidden"  class="cancelItem" name="cancelItem" value="${invoice.invoiceId}"/>
                                                    <div class="kt-login__actions">
                                                        <button  id="kt_innovative_cancel" class="btn btn-dark">Delete Invoice<i class="kt-menu__link-icon flaticon-delete"></i></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form> 
                                    </div>
                                </div> 
                            </div>
                        </div>

                    </div>
                </div>

                <!--invoice content-->


                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">    
                    <div class="kt-portlet__body">
                        <form action="" method="">
                            <div class="kt-form kt-form--label-right">
                                <div class="kt-form__body">
                                    <div class="kt-section kt-section--first">
                                        <div class="kt-section__body">                                                                            
                                            <div class="form-group row">
                                                <label class="col-xl-3 col-lg-3 col-form-label">Company Logo</label>
                                                <div class="col-lg-9 col-xl-6">
                                                    <img alt="Company Logo" src="data:image/jpg;base64,${companyLogo}"  style="width:20vh;height:12vh;"/>                                                                                  
                                                </div>
                                            </div>

                                            <div class="row">
                                                <!-- company info -->
                                                <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Company Name</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="kt-portlet__head-label">
                                                                <h6 class="kt-portlet__head-title">
                                                                    ${invoice.clientProfile.companyName}
                                                                </h6>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Department</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title">
                                                                ${invoice.department.departmentName}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Fee Type</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title"> ${invoice.feeType.feeTypeName}</h6> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Invoice Date</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title"> ${invoice.invoiceDate}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Invoice Amount</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title"> ${invoice.invoiceAmount}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">

                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Invoice Status</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title"> ${invoice.invoiceStat}</h6> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Paid Date</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title">
                                                                ${invoice.paidDate}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Total Paid</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title">
                                                                ${invoice.totalPaid}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Toll Amount</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <h6 class="kt-portlet__head-title"> ${invoice.tollAmount}</h6> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Fee Amount</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <h6 class="kt-portlet__head-title">
                                                                ${invoice.feeAmount}
                                                            </h6>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>                                                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                </div>                                        


                <div class="col-lg-10 col-xl-10 order-lg-1 order-xl-1">    
                    <script src="//mozilla.github.io/pdf.js/build/pdf.js?v=1001"></script>
                    <div>
                        <button  id="prev"  class="btn btn-info btn-pill">Previous</button>
                        <button  id="next" class="btn btn-info btn-pill">Next</button>
                        &nbsp; &nbsp;
                        <span>Page: <span id="page_num"></span> / <span id="page_count"></span></span>
                    </div>
                    <canvas id="the-canvas"></canvas>
                </div>
            </div>
        </div>
    </div>    
</div>  
<%@include file="administratorfoot.jsp" %>
<script>

    var url = 'data:application/pdf;base64,${invoicePdf}';

    var pdfjsLib = window['pdfjs-dist/build/pdf'];

    pdfjsLib.GlobalWorkerOptions.workerSrc = '//mozilla.github.io/pdf.js/build/pdf.worker.js';

    var pdfDoc = null,
            pageNum = 1,
            pageRendering = false,
            pageNumPending = null,
            scale = 0.8,
            canvas = document.getElementById('the-canvas'),
            ctx = canvas.getContext('2d');

    /**
     * Get page info from document, resize canvas accordingly, and render page.
     * @param num Page number.
     */
    function renderPage(num) {
        pageRendering = true;
        // Using promise to fetch the page
        pdfDoc.getPage(num).then(function (page) {
            var viewport = page.getViewport({scale: scale});
            canvas.height = viewport.height;
            canvas.width = viewport.width;

            // Render PDF page into canvas context
            var renderContext = {
                canvasContext: ctx,
                viewport: viewport
            };
            var renderTask = page.render(renderContext);

            // Wait for rendering to finish
            renderTask.promise.then(function () {
                pageRendering = false;
                if (pageNumPending !== null) {
                    // New page rendering is pending
                    renderPage(pageNumPending);
                    pageNumPending = null;
                }
            });
        });

        // Update page counters
        document.getElementById('page_num').textContent = num;
    }

    /**
     * If another page rendering in progress, waits until the rendering is
     * finised. Otherwise, executes rendering immediately.
     */
    function queueRenderPage(num) {
        if (pageRendering) {
            pageNumPending = num;
        } else {
            renderPage(num);
        }
    }

    /**
     * Displays previous page.
     */
    function onPrevPage() {
        if (pageNum <= 1) {
            return;
        }
        pageNum--;
        queueRenderPage(pageNum);
    }
    document.getElementById('prev').addEventListener('click', onPrevPage);

    /**
     * Displays next page.
     */
    function onNextPage() {
        if (pageNum >= pdfDoc.numPages) {
            return;
        }
        pageNum++;
        queueRenderPage(pageNum);
    }
    document.getElementById('next').addEventListener('click', onNextPage);

    /**
     * Asynchronously downloads PDF.
     */
    pdfjsLib.getDocument(url).promise.then(function (pdfDoc_) {
        pdfDoc = pdfDoc_;
        document.getElementById('page_count').textContent = pdfDoc.numPages;

        // Initial/first page rendering
        renderPage(pageNum);
    });
</script>

<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/delete-invoice.js?v=1001" />"   type="text/javascript"></script>
