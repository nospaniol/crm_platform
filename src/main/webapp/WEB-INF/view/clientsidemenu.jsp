<button  class="kt-aside-close " id="kt_aside_close_btn"><i class="la la-close"></i></button>
<div class="kt-aside  kt-aside--fixed  kt-grid__item kt-grid kt-grid--desktop kt-grid--hor-desktop" id="kt_aside">
    <div class="kt-aside-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_aside_menu_wrapper">
        <div id="kt_aside_menu" class="kt-aside-menu " data-ktmenu-vertical="1" data-ktmenu-scroll="1" data-ktmenu-dropdown-timeout="500">
            <ul class="kt-menu__nav ">
                <li class="kt-menu__item  kt-menu__item--active" aria-haspopup="true"><a href="<c:url value="/client_dash/dashboard" />" class="kt-menu__link "><i class="kt-menu__link-icon flaticon2-protection"></i><span class="kt-menu__link-text">Dashboard</span></a></li>

                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-car"></i><span class="kt-menu__link-text">Manage Vehicles</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                    <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                        <ul class="kt-menu__subnav">
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/client_vehicle/file/upload" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Batch Upload</span></a></li>
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/client_vehicle/view_vehicle" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Vehicles</span></a></li>
                        </ul>
                    </div>
                </li>
                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-upload"></i><span class="kt-menu__link-text">Transactions</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                    <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                        <ul class="kt-menu__subnav">
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/client_transaction/view_today_transaction" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Daily Transactions</span></a></li>
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/client_transaction/view_transaction" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Transactions</span></a></li>
                        </ul>
                    </div>
                </li>

                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover">
                    <a href="<c:url value="/client_citation/view_citations" />" class="kt-menu__link kt-menu__toggle">
                        <i class="kt-menu__link-icon flaticon2-sheet"></i><span class="kt-menu__link-text">Citations</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                </li>
                <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon2-list-3"></i><span class="kt-menu__link-text">Client Invoices</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                    <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                        <ul class="kt-menu__subnav">

                            <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/client_invoice/view_invoice" />"  class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Invoice</span></a></li>
                            <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/client_invoice/monthly_invoice" />"  class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Monthly Invoice</span></a></li>
                        </ul>
                    </div>
                </li>             
            </ul>
        </div>
    </div>
</div>
