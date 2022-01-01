<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-md-12 col-lg-12 col-xl-3">
                        <div class="kt-login__wrapper">
                            <div class="kt-login__container">
                                <div class="kt-login__body">                              
                                    <div class="kt-login__signup">                                  
                                        <div class="kt-login__form">
                                            <div class="row">
                                                <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                                                    <div class="kt-portlet kt-portlet--head--noborder kt-portlet--height-fluid">
                                                        <div class="kt-portlet__head kt-portlet__head--noborder">
                                                            <div class="kt-portlet__head-label">                                                               
                                                                <%@include file="alert.jsp" %>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <c:if test="${!empty allDepartments}">                                                         
                                                    <div class="col-lg-12 col-xl-12 order-lg-2 order-xl-2">
                                                        <div class="kt-portlet kt-portlet--height-fluid"> 
                                                            <div class="kt-portlet__body"> 
                                                                <table  class="table table-striped- table-bordered table-hover table-checkable" id="kt_table_1" >
                                                                    <thead class="thead-dark">
                                                                        <tr>
                                                                            <th style="color:white" >Department Name</th>                        
                                                                            <th style="color:white" >Department Logo</th>
                                                                    </thead>
                                                                    <tbody>
                                                                        <c:forEach items="${allDepartments}" var="department">
                                                                            <tr>
                                                                                <td scope="row"> <i class="flaticon2-correct"></i> ${department.departmentName} </td>                            
                                                                                <td>
                                                                                    <img alt="Department Logo" src="data:image/jpg;base64,${department.logo}" style="width:15vh;height:4vh;"/>
                                                                                </td>                                                                        
                                                                            </tr>
                                                                        </c:forEach>
                                                                    </tbody>
                                                                </table>
                                                            </div> 
                                                        </div> 
                                                    </div> 
                                                </c:if>
                                            </div>       
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>             
                </div>
            </div>
        </div>
    </div>    
</div>    
<%@include file="clientfoot.jsp" %>
