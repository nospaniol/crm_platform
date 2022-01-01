<%@include file="clientheader.jsp" %>
<div class="kt-content  kt-grid__item kt-grid__item--fluid kt-grid kt-grid--hor" id="kt_content">
    <!-- begin:: Content -->
    <div class="kt-container  kt-container--fluid  kt-grid__item kt-grid__item--fluid  kt-portlet">
        <div class="kt-portlet">
            <div class="kt-portlet__body  kt-portlet__body--fit">
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="col-lg-12 col-xl-12 order-lg-1 order-xl-1">
                        <%
                            if (ns_user.equals("CLIENT")) {
                        %>
                        <div class="row">
                            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1 kt-margin-top-20">
                                <button  type="button" data-toggle="modal" data-target="#passwordModal" class="btn btn-default btn-pill">Change Password
                                    <svg xmlns="https://www.w3.org/2000/svg" xmlns:xlink="https://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <rect id="bound" x="0" y="0" width="24" height="24"/>
                                            <polygon id="Path-59" fill="#000000" opacity="0.3" transform="translate(8.885842, 16.114158) rotate(-315.000000) translate(-8.885842, -16.114158) " points="6.89784488 10.6187476 6.76452164 19.4882481 8.88584198 21.6095684 11.0071623 19.4882481 9.59294876 18.0740345 10.9659914 16.7009919 9.55177787 15.2867783 11.0071623 13.8313939 10.8837471 10.6187476"/>
                                            <path d="M15.9852814,14.9852814 C12.6715729,14.9852814 9.98528137,12.2989899 9.98528137,8.98528137 C9.98528137,5.67157288 12.6715729,2.98528137 15.9852814,2.98528137 C19.2989899,2.98528137 21.9852814,5.67157288 21.9852814,8.98528137 C21.9852814,12.2989899 19.2989899,14.9852814 15.9852814,14.9852814 Z M16.1776695,9.07106781 C17.0060967,9.07106781 17.6776695,8.39949494 17.6776695,7.57106781 C17.6776695,6.74264069 17.0060967,6.07106781 16.1776695,6.07106781 C15.3492424,6.07106781 14.6776695,6.74264069 14.6776695,7.57106781 C14.6776695,8.39949494 15.3492424,9.07106781 16.1776695,9.07106781 Z" id="Combined-Shape" fill="#000000" transform="translate(15.985281, 8.985281) rotate(-315.000000) translate(-15.985281, -8.985281) "/>
                                        </g>
                                    </svg></button>

                            </div>
                            <script>
                                function notifyProgress() {
                                    Swal.fire({
                                        type: 'success',
                                        title: '${institution}',
                                        text: 'This module is under progress!',
                                        footer: '<a class="kt-link"> A great toll management solution provider</a>'
                                    });
                                }

                            </script>
                        </div> 
                        <%
                            }

                            if (ns_user.equals("DEPARTMENT")) {
                        %>
                        <div class="row">
                            <div class="col-lg-4 col-xl-4 order-lg-1 order-xl-1 kt-margin-top-20">
                                <button  type="button" class="kt_innovative_password btn btn-default btn-pill">Change Password
                                    <svg xmlns="https://www.w3.org/2000/svg" xmlns:xlink="https://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                                            <rect id="bound" x="0" y="0" width="24" height="24"/>
                                            <polygon id="Path-59" fill="#000000" opacity="0.3" transform="translate(8.885842, 16.114158) rotate(-315.000000) translate(-8.885842, -16.114158) " points="6.89784488 10.6187476 6.76452164 19.4882481 8.88584198 21.6095684 11.0071623 19.4882481 9.59294876 18.0740345 10.9659914 16.7009919 9.55177787 15.2867783 11.0071623 13.8313939 10.8837471 10.6187476"/>
                                            <path d="M15.9852814,14.9852814 C12.6715729,14.9852814 9.98528137,12.2989899 9.98528137,8.98528137 C9.98528137,5.67157288 12.6715729,2.98528137 15.9852814,2.98528137 C19.2989899,2.98528137 21.9852814,5.67157288 21.9852814,8.98528137 C21.9852814,12.2989899 19.2989899,14.9852814 15.9852814,14.9852814 Z M16.1776695,9.07106781 C17.0060967,9.07106781 17.6776695,8.39949494 17.6776695,7.57106781 C17.6776695,6.74264069 17.0060967,6.07106781 16.1776695,6.07106781 C15.3492424,6.07106781 14.6776695,6.74264069 14.6776695,7.57106781 C14.6776695,8.39949494 15.3492424,9.07106781 16.1776695,9.07106781 Z" id="Combined-Shape" fill="#000000" transform="translate(15.985281, 8.985281) rotate(-315.000000) translate(-15.985281, -8.985281) "/>
                                        </g>
                                    </svg></button>

                            </div>
                        </div> 
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="row row-no-padding row-col-separator-xl">
                    <div class="kt-portlet__body">
                        <%
                            if (ns_user.equals("CLIENT")) {
                        %>
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

                                            <table>
                                                <tr>                                                                        
                                                    <!-- company info -->
                                                    <td>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Company Name</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <label class="col-form-label">                                                                                     <h6 class="kt-portlet__head-title">
                                                                        ${clientProfile.companyName}
                                                                    </h6> 
                                                                </label>

                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Postal Address</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title">
                                                                        ${clientProfile.postalAddress}
                                                                    </h6>     
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Company Phone</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                                    <label class="col-form-label"> 
                                                                        <h6 class="kt-portlet__head-title"> ${clientProfile.companyPhoneNumber} </h6> 
                                                                    </label> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Email Address</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <div class="input-group-prepend">
                                                                        <span class="input-group-text"><i class="la la-at"></i></span></div>
                                                                    <label class="col-form-label"> 
                                                                        <h6 class="kt-portlet__head-title"> ${clientProfile.companyEmailAddress} </h6>   
                                                                    </label> 
                                                                </div>
                                                            </div>
                                                        </div> 
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Category</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <label class="col-form-label">  
                                                                        <h6 class="kt-portlet__head-title"> ${clientProfile.category} </h6>  
                                                                    </label> 
                                                                </div>
                                                            </div>
                                                        </div> 
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Account Type</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <label class="col-form-label">     
                                                                        <h6 class="kt-portlet__head-title"> ${clientProfile.accountType} </h6>      
                                                                    </label> 
                                                                </div>
                                                            </div>
                                                        </div> 
                                                    </td>
                                                    <td>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">First Name</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <label class="col-form-label">  
                                                                    <h6 class="kt-portlet__head-title">
                                                                        ${user.firstName}
                                                                    </h6>    
                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Last Name</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <label class="col-form-label"> 
                                                                    <h6 class="kt-portlet__head-title">
                                                                        ${user.lastName}
                                                                    </h6> 

                                                                </label>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Contact Phone</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                                    <label class="col-form-label"> 
                                                                        <h6 class="kt-portlet__head-title"> ${user.phoneToken.phoneNumber} </h6>  
                                                                    </label> 
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Email Address</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <div class="input-group">
                                                                    <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                                                    <label class="col-form-label">   
                                                                        <h6 class="kt-portlet__head-title"> ${user.emailToken.emailAddress} </h6>   
                                                                    </label> 
                                                                </div>
                                                            </div>
                                                        </div> 
                                                        <div class="form-group row">
                                                            <label class="col-xl-3 col-lg-3 col-form-label">Designation</label>
                                                            <div class="col-lg-9 col-xl-6">
                                                                <label class="col-form-label"> 
                                                                    <h6 class="kt-portlet__head-title">
                                                                        ${user.designation}

                                                                    </h6>                                                                                    </label>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>                                                                        
                                            </table>
                                        </div>
                                    </div>
                                </div>
                        </form>
                        <%
                            }

                            if (ns_user.equals("DEPARTMENT")) {
                        %>
                        <form action="" method="">
                            <div class="kt-form kt-form--label-right">
                                <div class="kt-form__body">
                                    <div class="kt-section kt-section--first">
                                        <div class="kt-section__body">                                                                            
                                            <div class="form-group row">
                                                <label class="col-xl-3 col-lg-3 col-form-label">Department Logo</label>
                                                <div class="col-lg-9 col-xl-6">
                                                    <img alt="Department Logo" src="data:image/jpg;base64,${departmentLogo}"  style="width:20vh;height:12vh;"/>                                                                                  
                                                </div>
                                            </div>

                                            <div class="row">
                                                <!-- company info -->
                                                <div class="col-xl-6 col-lg-6 order-lg-1 order-xl-1">
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Department Name</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="kt-portlet__head-label">
                                                                <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title">
                                                                        ${department.departmentName}
                                                                    </h6>                                                                                    </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">First Name</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title">
                                                                    ${user.firstName}
                                                                </h6>                                                                                    </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Last Name</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title">
                                                                    ${user.lastName}
                                                                </h6>                                                                                    </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Contact Phone</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-phone"></i></span></div>
                                                                <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title"> ${user.phoneToken.phoneNumber} </h6>                                                                                    </label> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Email Address</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <div class="input-group">
                                                                <div class="input-group-prepend"><span class="input-group-text"><i class="la la-at"></i></span></div>
                                                                <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title"> ${user.emailToken.emailAddress} </h6>                                                                                    </label> 
                                                            </div>
                                                        </div>
                                                    </div> 
                                                    <div class="form-group row">
                                                        <label class="col-xl-3 col-lg-3 col-form-label">Designation</label>
                                                        <div class="col-lg-9 col-xl-6">
                                                            <label class="col-form-label">                                                                                        <h6 class="kt-portlet__head-title">
                                                                    ${user.designation}
                                                                </h6>                                                                                    </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>                                                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>             

<%@include file="clientfoot.jsp" %>

</body>
</html>