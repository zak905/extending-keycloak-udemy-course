<#macro mainLayout active bodyClass>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="robots" content="noindex, nofollow">

    <title>${msg("accountManagementTitle")}</title>
    <link rel="icon" href="${url.resourcesPath}/img/favicon.ico">
    <#if properties.stylesCommon?has_content>
        <#list properties.stylesCommon?split(' ') as style>
            <link href="${url.resourcesCommonPath}/${style}" rel="stylesheet" />
        </#list>
    </#if>
    <#if properties.styles?has_content>
        <#list properties.styles?split(' ') as style>
            <link href="${url.resourcesPath}/${style}" rel="stylesheet" />
        </#list>
    </#if>
    <#if properties.scripts?has_content>
        <#list properties.scripts?split(' ') as script>
            <script type="text/javascript" src="${url.resourcesPath}/${script}"></script>
        </#list>
    </#if>
</head>
<body>

<nav class="navbar bg-light fixed-top">
  <div class="container-fluid">
      <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
     <#if referrer?has_content && referrer.url?has_content><li><a href="${referrer.url}" id="referrer">${msg("backTo",referrer.name)}</a></li></#if>
    <a class="navbar-brand" href="#">${msg("accountManagementTitle")}</a>
    <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
      <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasNavbarLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div class="offcanvas-body">
        <ul class="navbar-nav justify-content-end flex-grow-1 pe-3">
          <li class="nav-item">
            <a class="nav-link <#if active=='account'>active</#if>" aria-current="page" href="${url.accountUrl}">${msg("account")}</a>
          </li>
          <#if features.passwordUpdateSupported>
        <li class="nav-item">
            <a class="nav-link <#if active=='password'>active</#if>" aria-current="page" href="${url.passwordUrl}">${msg("password")}</a>
          </li>
          </#if>
              <li class="nav-item">
            <a class="nav-link <#if active=='totp'>active</#if>" aria-current="page" href="${url.totpUrl}">${msg("authenticator")}</a>
          </li>
           <#if features.identityFederation>
            <li class="nav-item">
            <a class="nav-link <#if active=='social'>active</#if>" aria-current="page" href="${url.socialUrl}">${msg("federatedIdentity")}</a>
          </li>
          </#if>
           <li class="nav-item">
            <a class="nav-link <#if active=='sessions'>active</#if>" aria-current="page" href="${url.sessionsUrl}">${msg("sessions")}</a>
          </li>
           <li class="nav-item">
            <a class="nav-link <#if active=='applications'>active</#if>" aria-current="page" href="${url.applicationsUrl}">${msg("applications")}</a>
          </li>
          <#if features.log>
            <li class="nav-item">
            <a class="nav-link <#if active=='log'>active</#if>" aria-current="page" href="${url.logUrl}">${msg("log")}</a>
          </li>
          </#if>
          <#if realm.userManagedAccessAllowed && features.authorization>
            <li class="nav-item">
            <a class="nav-link <#if active=='authorization'>active</#if>" aria-current="page" href="${url.resourceUrl}">${msg("myResources")}</a>
          </li>
          </#if>
           <li class="nav-item"><a class="btn btn-danger" href="${url.getLogoutUrl()}">${msg("doSignOut")}</a></li>
        </ul>
      </div>
    </div>
  </div>
      <div class="container">
        <div>
            <#if realm.internationalizationEnabled>
                 <li>
                     <div class="kc-dropdown" id="kc-locale-dropdown">
                         <a href="#" id="kc-current-locale-link">${locale.current}</a>
                         <ul>
                             <#list locale.supported as l>
                                 <li class="kc-dropdown-item"><a href="${l.url}">${l.label}</a></li>
                             </#list>
                         </ul>
                     </div>
                 <li>
             </#if>
        </div>

        <div class="col-sm-9 content-area">
            <#if message?has_content>
                <div class="alert alert-${message.type}" role="alert">
                    <span class="kc-feedback-text">${kcSanitize(message.summary)?no_esc}</span>
                </div>
            </#if>

            <h1>${msg("sayHi")}</h1>

            <#nested "content">
        </div>
    </div>

</nav>
        



</body>
</html>
</#macro>