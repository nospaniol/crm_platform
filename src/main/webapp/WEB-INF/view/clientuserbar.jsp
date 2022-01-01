
<div class="kt-header__topbar-item kt-header__topbar-item--user">
    <div class="kt-header__topbar-wrapper" data-toggle="dropdown" data-offset="15px,0px">
        <span class="kt-hidden kt-header__topbar-welcome">Hi,</span>
        <span class="kt-hidden kt-header__topbar-username">Innovative</span>
        <img class="kt-hidden" alt="Pic" src="<c:url value="/resources/assets/media/users/300_21.jpg" />?v=1001" />
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
            <a href="<c:url value="/client_dash/profile" />" class="kt-notification__item">
               <div class="kt-notification__item-icon">
                    <i class="flaticon2-calendar-3 kt-font-success"></i>
                </div>
                <div class="kt-notification__item-details">
                    <div class="kt-notification__item-title kt-font-bold">
                        My Profile
                    </div>
                    <div class="kt-notification__item-time">
                        Account settings and more
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
        window.location.replace("http://127.0.0.1:800/user/logout", 'window', 'toolbar=1,location=1,directories=1,status=1,menubar=1,scrollbars=1,resizable=1');
        window.onhashchange = function () {

        };
        self.close();
    }
</script>