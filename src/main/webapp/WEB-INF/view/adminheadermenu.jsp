<button  class="kt-header-menu-wrapper-close" id="kt_header_menu_mobile_close_btn"><i class="la la-close"></i></button>
<div class="kt-header-menu-wrapper kt-grid__item kt-grid__item--fluid" id="kt_header_menu_wrapper">
    <div id="kt_header_menu" class="kt-header-menu kt-header-menu-mobile  kt-header-menu--layout-default ">
        <ul class="kt-menu__nav ">
            <!-- <li class="kt-menu__item  kt-menu__item--active" aria-haspopup="true"><a href="http://127.0.0.1:800/administrator/dashboard" class="kt-menu__link "><i class="kt-menu__link-icon flaticon2-protection"></i><span class="kt-menu__link-text">Dashboard</span></a></li> -->
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-users-1"></i><span class="kt-menu__link-text">Clients</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                    <ul class="kt-menu__subnav">
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/administrator/add_client" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">New Client</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/administrator/view_client" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Client</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/administrator/view_applicants" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Applicants</span></a></li>
                    </ul>
                </div>
            </li>
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-car"></i><span class="kt-menu__link-text">Vehicles</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                    <ul class="kt-menu__subnav">
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/axle/add_axle" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Axles</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/store/add_location" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Store locations</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/tagtype/add_tagtype" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Tag Types</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/vehicle/add_vehicle" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">New Vehicle</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/vehicle/file/upload" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Batch Upload</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/vehicle/view_vehicle" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Vehicles</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/vehicle/view_file" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Uploaded Files</span></a></li>
                           <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/vehicle/delete_batch" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Batch Delete</span></a></li>
                    </ul>
                </div>
            </li>
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-upload"></i><span class="kt-menu__link-text">Transactions</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                    <ul class="kt-menu__subnav">
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transaction/add_transaction" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Upload Transaction</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transaction/view_today_transaction" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Daily Transactions</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transaction/view_transaction" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Transactions</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transaction/active_disputes" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Disputes</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transaction/view_file" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Uploaded Files</span></a></li>
                            <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transaction/delete_batch" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Batch Delete</span></a></li>
                  </ul>
                </div>
            </li>
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon-piggy-bank"></i><span class="kt-menu__link-text">Fullfilment</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                    <ul class="kt-menu__subnav">
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/transponder/manage" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Manage  Fullfillment</span></a></li>
                    </ul>
                </div>
            </li>
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle"><i class="kt-menu__link-icon flaticon2-sheet"></i><span class="kt-menu__link-text">Citations</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                    <ul class="kt-menu__subnav">
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/citation/new_citation" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Add Citation</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/citation/view_citations" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Citation</span></a></li>  
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/citation/active_disputes" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Citation Disputes</span></a></li>  
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/citationtype/add_citationtype" />" class="kt-menu__link "><i class="kt-menu__link-icon flaticon-folder"></i><span class="kt-menu__link-text">Citation Type</a></li>
                    </ul>
                </div>
            </li>
            <li class="kt-menu__item  kt-menu__item--submenu" aria-haspopup="true" data-ktmenu-submenu-toggle="hover"><a href="javascript:;" class="kt-menu__link kt-menu__toggle">
                    <i class="kt-menu__link-icon flaticon2-list-3"></i>
                    <span class="kt-menu__link-text">Client Invoices</span><i class="kt-menu__ver-arrow la la-angle-right"></i></a>
                <div class="kt-menu__submenu "><span class="kt-menu__arrow"></span>
                    <ul class="kt-menu__subnav">
                        <li class="kt-menu__item " aria-haspopup="true"><a  href="<c:url value="/feetype/add_feetype" />" class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Fee Types</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/invoice/new_invoice" />"  class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Add Invoice</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/invoice/view_invoice" />"  class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">View Invoice</span></a></li>
                        <li class="kt-menu__item " aria-haspopup="true"><a href="<c:url value="/invoice/monthly_invoice" />"  class="kt-menu__link "><i class="kt-menu__link-bullet kt-menu__link-bullet--line"><span></span></i><span class="kt-menu__link-text">Monthly Invoice</span></a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</div>
