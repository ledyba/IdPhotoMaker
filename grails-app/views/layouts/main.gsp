<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
       "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% String layoutTitle = layoutTitle() %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja" lang="ja">
<head>
    <% if(layoutTitle){ %><title><g:message code="site.title" /> - <g:layoutTitle default="Grails" /></title><% }else{ %><title><g:message code="site.title" /></title><% } %>
    <meta http-equiv="Content-Script-Type" content="text/javascript" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <meta name="author" content="psi - http://ledyba.ddo.jp/" />
<g:layoutHead />
</head>
<body>
    <div id="spinner" class="spinner" style="display:none;">
      <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
    </div>
	<div id="main">
		<div id="header">
            <% if(layoutTitle){ %>
			<a class="title" href="${createLink(uri: '/')}"><g:message code="site.title" /></a> - ${layoutTitle}
            <% }else{ %>
			<a class="title" href="${createLink(uri: '/')}"><g:message code="site.title" /></a>
            <% } %>
		</div>
		<div id="main-box">
			<div id="content">
<g:layoutBody />
			</div>
			<div id="sidebar">
				<div class="module">
					<div class="module-header">
                      <g:message code="site.sidebar.ads.title" />
					</div>
					<div class="module-content">
                                          <script type="text/javascript">

                                            var _gaq = _gaq || [];
                                            _gaq.push(['_setAccount', 'UA-4766333-1']);
                                            _gaq.push(['_trackPageview']);

                                            (function() {
                                              var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
                                              ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                                              var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
                                            })();

                                          </script>
                                          <script type="text/javascript">
						google_ad_client = "pub-3121031347596821";
						google_ad_slot = "9192088374";
						google_ad_width = 160;
						google_ad_height = 600;
						</script>
						<script type="text/javascript"
						src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
						</script>
					</div>
				</div>
			</div>
		</div>
		<div id="footer"><g:message code="site.copyright" /></div>
	</div>
</body>
</html>
