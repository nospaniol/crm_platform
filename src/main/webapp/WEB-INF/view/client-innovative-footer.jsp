</div>
</div>
<div class="kt-footer  kt-grid__item kt-grid kt-grid--desktop kt-grid--ver-desktop" id="kt_footer">
    <div class="kt-container  kt-container--fluid ">
        <div class="kt-footer__copyright">
            2020&nbsp;&copy;&nbsp;<a href="<c:url value="/" />" class="kt-link">${institution}</a>
        </div>
        <div class="kt-footer__menu">
            <!--<a href="#" class="kt-footer__menu-link kt-link"><i class="flaticon-facebook-logo-button"></i>facebook</a>-->
            <!--<a href="#" class="kt-footer__menu-link kt-link"><i class="flaticon-twitter-logo"></i>twitter</a>-->
            <!--<a href="#" class="kt-footer__menu-link kt-link"><i class="flaticon-instagram-logo"></i>instagram</a>-->
        </div>
    </div>
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

    function goBack() {
        // window.history.back();
    }

    var KTAppOptions = {
        "colors": {
            "state": {
                "brand": "#22b9ff",
                "light": "#ffffff",
                "dark": "#282a3c",
                "primary": "#5867dd",
                "success": "#34bfa3",
                "info": "#36a3f7",
                "warning": "#ffb822",
                "danger": "#fd3995"
            },
            "base": {
                "label": ["#c5cbe3", "#a1a8c3", "#3d4465", "#3e4466"],
                "shape": ["#f0f3ff", "#d9dffa", "#afb4d4", "#646c9a"]
            }
        }
    };

    function noBack()
    {

    }

    document.firstElementChild.style.zoom = "reset";

</script>
<script src="<c:url value="/resources/assets/vendors/general/jquery/dist/jquery.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/popper.js/dist/umd/popper.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/bootstrap/dist/js/bootstrap.min.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/sticky-js/dist/sticky.min.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/owl.carousel/dist/owl.carousel.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/raphael/raphael.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/morris.js/morris.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/general/sweetalert2/dist/sweetalert2.min.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/js/nshome/scripts.bundle.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/vendors/custom/datatables/datatables.bundle.js?v=1001" />"  type="text/javascript"></script>
<script src="<c:url value="/resources/assets/js/nsdash/pages/dashboard.js?v=1001" />" type="text/javascript"></script>
 
<div id="passwordModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">CHANGE PASSWORD </h4>
                <p>Fill all fields: </p>
            </div>
            <div class="modal-body">  
                <div class="form">
                    <div class="row">
                        <div class="form-group">
                            <input  class="form-control" type="password" value="" name="currentPassword" id="currentPassword" required="" placeholder="Current Password"/>
                        </div>     
                        <div class="form-group">
                            <input  class="form-control" type="password" value="" name="newPassword" id="newPassword" required="" placeholder="New Password"/>
                        </div>  
                        <div class="form-group">
                            <input  class="form-control" type="password" value="" name="confirmPassword" id="confirmPassword" required=""  placeholder="Confirm Password"/>
                        </div>  
                    </div>                                                    
                    <div class="row">
                        <div class="kt-login__actions">
                            <button   onclick="modifyPassword()" class="btn btn-brand btn-pill btn-elevate">MODIFY<i class="kt-menu__link-icon flaticon2-add"></i></button>
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
    function modifyPassword() {
        var currentPassword = $('#currentPassword').val();
        var newPassword = $('#newPassword').val();
        var confirmPassword = $('#confirmPassword').val();
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-brand',
                cancelButton: 'btn btn-dark'
            },
            buttonsStyling: false
        });
        if (currentPassword === null || newPassword === null || confirmPassword === '') {
            swalWithBootstrapButtons.fire(
                    'Innovative Toll',
                    'Fill all required fields!',
                    'error'
                    );
            return;
        }

        swalWithBootstrapButtons.fire({
            title: 'Are you sure?',
            text: "You are about to change your password!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, proceed',
            cancelButtonText: 'No, Go back!',
            reverseButtons: true
        }).then((result) => {
            if (result.value) {
                $.ajax({
                    type: "POST",
                    url: 'http://127.0.0.1:800/user/change/password/' + currentPassword + '/' + newPassword + '/' + confirmPassword,
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
