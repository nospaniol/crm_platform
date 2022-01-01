
<div class="android-header mdl-layout__header mdl-layout__header--waterfall">
    <div class="mdl-layout__header-row">
        <span class="android-title mdl-layout-title">
            <img class="android-logo-image"  style="width:30vh;height:20vh;" src="<c:url value="/resources/images/android-logo.png?v=1001" />">
        </span>
        <!-- Add spacer, to align navigation to the right in desktop -->
        <div class="android-header-spacer mdl-layout-spacer"></div>
        <div class="android-search-box mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right mdl-textfield--full-width">
            <label class="mdl-button mdl-js-button mdl-button--icon" for="search-field">
                <i class="material-icons">search</i>
            </label>
            <div class="mdl-textfield__expandable-holder">
                <input  class="mdl-textfield__input" type="text" id="search-field">
            </div>
        </div>
        <!-- Navigation -->
        <div class="android-navigation-container">
            <nav class="android-navigation mdl-navigation">
                <a class="mdl-navigation__link mdl-typography--text-uppercase" href="<c:url value="/" />">Home</a>
            </nav>
        </div>

    </div>
</div>

<div class="android-drawer mdl-layout__drawer">
    <span class="mdl-layout-title">
        <img class="android-logo-image" src="<c:url value="/resources/images/android-logo.png?v=1001" />">
    </span>
    <nav class="mdl-navigation">
        <a class="mdl-navigation__link" href="<c:url value="/" />">Home</a>
        <div class="android-drawer-separator"></div>
    </nav>
</div>
