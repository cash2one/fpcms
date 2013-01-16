<%@page import="com.fpcms.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<duowan:override name="head">
	<title>${cmsContent.title}</title>
	<meta name="keywords" content="${cmsContent.title}" />
	<meta name="description" content="${fn:substring(item.content,0,80)}" /> 
</duowan:override>

<duowan:override name="content">
		<div>
			<div id="imgContainer"></div>
			<h1>${cmsContent.title}</h1>
			${cmsContent.content}
			<br />
			<span>创建时间:<fmt:formatDate value="${cmsContent.dateCreated}" pattern="yyyy-MM-dd"/></span>
			<div><h3>上一篇:<a href="${ctx}/content/show/${preCmsContent.id}.do">${preCmsContent.title}</a><br />下一篇:<a href="${ctx}/content/show/${nextCmsContent.id}.do">${nextCmsContent.title}</a></h3></div>
		</div>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){
			var globalImgUrls;
			var request = $.ajax({url:"${ctx}/rpc/ImageWebService/getFemailImageList",dataType:"json"});
			request.done(function(response) { 
		    	//alert("done() "+response.result);
		    	globalImgUrls = response.result;		    	
		    });
			
			var globalLastImgIndex = 0;
			$(window).scroll(function () {
				var top = $(document).scrollTop() + $(window).height();
				if(isScrollDown(top)){
					if(top + 900 > $(document).height() && globalImgUrls) {
						for(i = 0; i < 2; i++) {
							if(globalLastImgIndex > globalImgUrls.length) {
								globalLastImgIndex = 0;
							}
				    		var url = globalImgUrls[globalLastImgIndex++];
							$('#imgContainer').append('<img src="${ctx}/proxy/redirect.do?url='+url+'"/>');							
				    	}
					}
				}
			});
		});
		
		var lastScrollTop = 0;
		function isScrollDown(top) {
			if(lastScrollTop == 0) {
				lastScrollTop = top;
			}
			try {
				if(top > lastScrollTop) {
					return true;
				}else {
					return false;
				}
			}finally {
				lastScrollTop = top;
			}
		}
		</script>
</duowan:override>

<%-- jsp模板继承,具体使用请查看: http://code.google.com/p/rapid-framework/wiki/rapid_jsp_extends --%>
<%@ include file="base.jsp" %>

