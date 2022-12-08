<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section = "header">
        Feeling Survey
    <#elseif section = "form">
    <div id="kc-terms-text">
        How do you feel
    </div>
    <form class="form-actions" action="${url.loginAction}" method="POST">
    <div style="display: flex; flex-direction: column">
            <div>
                <input type="radio" id="good" name="feeling" value="good" checked>
                <label for="good">Good</label>
            </div>
            <div>
               <input type="radio" id="stressed" name="feeling" value="stressed">
               <label for="stressed">Stressed</label>
            </div>
            <div>
               <input type="radio" id="bad" name="feeling" value="bad">
                <label for="bad">Bad</label>
            </div>
            <div>
              <input type="radio" id="tired" name="feeling" value="tired">
              <label for="tired">Tired</label>
            </div>
          </div>
        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="accept" id="kc-accept" type="submit" value="${msg("doAccept")}"/>
        <input class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" name="skip" id="kc-decline" type="submit" value="skip"/>
    </form>
    <div class="clearfix"></div>
    </#if>
</@layout.registrationLayout>