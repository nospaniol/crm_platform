  <c:if test="${!empty message}">  
    <div class="kt-alert kt-alert--outline alert alert-info alert-dismissible" role="alert">
        <button  type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
        <span>${message}</span>
    </div>
  </c:if>