"use strict"; var KTAppUserProfile = {init:function(){new KTOffcanvas("kt_user_profile_aside", {overlay:!0, baseClass:"kt-app__aside", closeBy:"kt_user_profile_aside_close", toggleBy:"kt_subheader_mobile_toggle"})}}; KTUtil.ready(function(){KTAppUserProfile.init()});