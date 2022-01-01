<%@include file="administratorhead.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet  kt-portlet">
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-5 col-xl-5 order-lg-1 order-xl-1">                 
                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                        <div class="kt-portlet__head kt-portlet__head--noborder">
                            <div class="kt-portlet__head-label">
                                <h6 class="kt-portlet__head-title" id="page_title">
                                    ${topic}
                                </h6>
                                <h3>Total <p id="innovative_total">${total}</p></h3>
                                    <%@include file="alert.jsp" %>
                            </div>
                        </div>

                    </div>
                </div>  
            </div>
            <div class="col-lg-7 col-xl-7 order-lg-1 order-xl-2">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-xl-12 col-lg-12 order-lg-1 order-xl-1">
                        <form id="kt_innovative_form" class="kt-form" method="post" >
                            <div class="row row-no-padding row-col-separator-xl">    
                                <div class="form-group">
                                    <input  type="hidden" placeholder="select  category" class="form-control" value="${clientId}" id="client" name="client"/>
                                </div>
                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                    <div class="form-group">
                                        <select type="text" placeholder="select  month" class="form-control kt_selectpicker" id="year" name="year">
                                            <option value="" class="text-primary form-control">SELECT YEAR</option>
                                            <option value="2020">2020</option>
                                            <option value="2021">2021</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 order-lg-1 order-xl-1">
                                    <div class="form-group">
                                        <select type="text" placeholder="select  filter" class="form-control kt_selectpicker" id="filter_info" name="filter_info">
                                            <option value="">SELECT FILTER</option>
                                            <option value="licensePlate">License Plate</option>
                                            <option value="exitDateTime">Date</option>
                                            <option value="exitLocation">Location</option>
                                            <option value="exitLane">Lane</option>
                                            <option value="agency">Agency</option>
                                            <option value="state">State</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-xl-2 col-lg-3 order-lg-1 order-xl-1">
                                    <div class="kt-login__actions">
                                        <button  id="kt_innovative_annual_search" class="btn btn-primary">View<i class="kt-menu__link-icon flaticon2-search-1"></i></button>
                                    </div>
                                </div>    
                            </div> 
                        </form>
                    </div> 

                </div>
            </div>
        </div>  
        <div class="row row-no-padding row-col-separator-xl">
            <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                <div class="kt-portlet kt-portlet--height-fluid"> 
                    <div class="kt-portlet__body"> 
                        <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                            <thead class="thead-dark">
                                <tr>                      
                                    <th style="color:white" id="innovative_title">${innovative_title}</th>
                                    <th style="color:white" >Amount</th>
                            </thead>
                            <tbody id="data_info">
                                <c:choose>
                                    <c:when test="${allSpendings.size() > 0 }">
                                        <c:forEach var="tollspending" items="${allSpendings}">
                                            <tr>
                                                <td scope="row"> <i class="flaticon2-correct"></i> ${tollspending.title} </td>                            
                                                <td scope="row">${tollspending.amount} </td>                            
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="16">No data available</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                            </tbody>
                        </table>
                    </div> 
                </div> 
            </div> 
        </div>       
    </div>
</div>    
<%@include file="administratorfoot.jsp" %>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/load-department.js?v=1001" />"   type="text/javascript"></script>
<script  src="<c:url value="/resources/assets/js/nshome/pages/wizard/filter-spending.js?v=1001" />"   type="text/javascript"></script>

