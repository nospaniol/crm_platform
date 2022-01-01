<div class="kt-header__topbar-item kt-header__topbar-item--user">
    <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="15px,0px">
        <span class="kt-hidden kt-header__topbar-welcome">Hi,</span>
        <span class="kt-hidden kt-header__topbar-username">Innovative</span>
        <img class="kt-hidden" alt="Pic" src="<c:url value="/resources/assets/media/users/300_21.jpg" />" />
        <span class="kt-header__topbar-icon kt-hidden-"><i class="flaticon2-user-outline-symbol"></i></span>
    </div>
    <div class="dropdown-menu dropdown-menu-fit dropdown-menu-right dropdown-menu-anim dropdown-menu-xl">

        <!--begin: Head -->
        <div class="kt-user-card kt-user-card--skin-dark kt-notification-item-padding-x">
            <div class="kt-user-card__avatar">
                <span class="kt-badge kt-badge--lg kt-badge--rounded kt-badge--bold kt-font-success">
                    <% out.print(ns_first_name.toUpperCase().charAt(0));%>
                </span>
            </div>
            <div class="kt-user-card__name dark" style="color:black;">
                <% out.print(ns_first_name);%>
            </div>
        </div>
        <!--end: Head -->

        <!--begin: Navigation -->

        <div class="kt-notification">
            <a href="javascript:;"  type="button" data-toggle="modal" data-target="#passwordModal" class="kt-notification__item" >
                <div class="kt-notification__item-icon">
                    <svg xmlns="https://www.w3.org/2000/svg" xmlns:xlink="https://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1" class="kt-svg-icon">
                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
                            <rect id="bound" x="0" y="0" width="24" height="24"/>
                            <polygon id="Path-59" fill="#000000" opacity="0.3" transform="translate(8.885842, 16.114158) rotate(-315.000000) translate(-8.885842, -16.114158) " points="6.89784488 10.6187476 6.76452164 19.4882481 8.88584198 21.6095684 11.0071623 19.4882481 9.59294876 18.0740345 10.9659914 16.7009919 9.55177787 15.2867783 11.0071623 13.8313939 10.8837471 10.6187476"/>
                            <path d="M15.9852814,14.9852814 C12.6715729,14.9852814 9.98528137,12.2989899 9.98528137,8.98528137 C9.98528137,5.67157288 12.6715729,2.98528137 15.9852814,2.98528137 C19.2989899,2.98528137 21.9852814,5.67157288 21.9852814,8.98528137 C21.9852814,12.2989899 19.2989899,14.9852814 15.9852814,14.9852814 Z M16.1776695,9.07106781 C17.0060967,9.07106781 17.6776695,8.39949494 17.6776695,7.57106781 C17.6776695,6.74264069 17.0060967,6.07106781 16.1776695,6.07106781 C15.3492424,6.07106781 14.6776695,6.74264069 14.6776695,7.57106781 C14.6776695,8.39949494 15.3492424,9.07106781 16.1776695,9.07106781 Z" id="Combined-Shape" fill="#000000" transform="translate(15.985281, 8.985281) rotate(-315.000000) translate(-15.985281, -8.985281) "/>
                        </g>
                    </svg>
                </div>
                <div class="kt-notification__item-details">
                    <div class="kt-notification__item-title kt-font-bold">
                        Change Password
                    </div>
                </div>
            </a>

            <a href="javascript:;" class="kt-notification__item"  onclick="logoutPage()">
                <div class="kt-notification__item-icon">
                    <i class="flaticon-logout kt-font-warning"></i>
                </div>
                <div class="kt-notification__item-details">
                    <div class="kt-notification__item-title kt-font-bold">
                        Sign Out
                    </div>
                    <div class="kt-notification__item-time">
                    </div>
                </div>
            </a>

        </div>

        <!--end: Navigation -->
    </div>
</div>
<script>

    function go() {
        window.location.replace("http://127.0.0.1:800/user/logout", 'window', 'toolbar=1,location=1,directories=1,status=1,menubar=1,scrollbars=1,resizable=1');
        self.close();
    }

    function logoutPage() {
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                confirmButton: 'btn btn-brand',
                cancelButton: 'btn btn-dark'
            },
            buttonsStyling: false
        });
        swalWithBootstrapButtons.fire({
            title: 'Are you sure?',
            text: "You are about to logout!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes,I want to logout',
            cancelButtonText: 'No, Go back!',
            reverseButtons: true
        }).then((result) => {
            if (result.value) {
                window.location.replace("http://127.0.0.1:800/user/logout", 'window', 'toolbar=1,location=1,directories=1,status=1,menubar=1,scrollbars=1,resizable=1');



                window.onhashchange = function () {

                };
                self.close();
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