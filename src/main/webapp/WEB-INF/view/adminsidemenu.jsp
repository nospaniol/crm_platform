
<button class="kt-aside-close " id="kt_aside_close_btn"><i class="la la-close"></i></button>
<div class="kt-aside  kt-aside--fixed  kt-grid__item kt-grid kt-grid--desktop kt-grid--hor-desktop" id="kt_aside">

    <!-- begin:: Aside -->
    <div class="kt-aside__brand kt-grid__item " id="kt_aside_brand">
       
        <div class="kt-aside__brand-tools">
            <button class="kt-aside__brand-aside-toggler" id="kt_aside_toggler">
                
            </button>
        </div>
    </div>

    <!-- end:: Aside -->

    <div class="kt-aside-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_aside_menu_wrapper">
        <div id="kt_aside_menu" class="kt-aside-menu " data-ktmenu-vertical="1" data-ktmenu-scroll="1" data-ktmenu-dropdown-timeout="500">
            <ul class="kt-menu__nav ">
                <li class="kt-menu__item  kt-menu__item--active" aria-haspopup="true"><a href="http://127.0.0.1:800/administrator/dashboard" class="kt-menu__link "><i class="kt-menu__link-icon flaticon2-protection"></i><span class="kt-menu__link-text">Dashboard</span></a></li>
                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="<c:url value="/account/view_accounts" />" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-price-tag"></i><span class="kt-menu__link-text">Manage Accounts</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a></li>
                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="<c:url value="/saving/view_savings" />" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-price-tag"></i><span class="kt-menu__link-text">Client Savings</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a></li>
                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon2-group"></i><span class="kt-menu__link-text">System Users</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                    <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                        <ul class="kt-menu__subnav">
                            <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/administrator/add_staff" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Add</span></a></li>
                            <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/administrator/view_staff" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View</span></a></li>
                        </ul>
                    </div>
                </li>
                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon2-analytics-2"></i><span class="kt-menu__link-text">Config</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                    <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                        <ul class="kt-menu__subnav">
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/agency/add_agency" />" class="kt-menu__link "><i class="kt-menu__link-icon flaticon-folder"></i><span class="kt-menu__link-text">Add Agency</a></li>
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/state/add_state" />" class="kt-menu__link "><i class="kt-menu__link-icon flaticon-folder"></i><span class="kt-menu__link-text">Add State</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>