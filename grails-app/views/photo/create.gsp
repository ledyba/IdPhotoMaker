
<%@ page import="app.Photo" %>
<html>
  <head>
    <meta name="layout" content="main" />
    <title>写真のアップロードエラー</title>
  </head>
  <body>
    <h1>写真のアップロードエラー</h1>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <g:hasErrors bean="${photoInstance}">
    <div class="errors">
      <g:renderErrors bean="${photoInstance}" as="list" />
    </div>
  </g:hasErrors>
  <g:form controller="photo" action="save" method="post"  enctype="multipart/form-data">
    <div class="dialog">
      <table class="form">
        <tbody>
          <tr class="prop">
            <td valign="top" class="name">
              <label for="data"><g:message code="photo.data.label" default="Data" /></label>
            </td>
            <td valign="top" class="value ${hasErrors(bean: photoInstance, field: 'data', 'errors')} ${hasErrors(bean: photoInstance, field: 'mimeType', 'errors')} ${hasErrors(bean: photoInstance, field: 'thumb', 'errors')}">
              <input type="file" id="data" name="data" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="buttons">
      <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.paginate.next')}" /></span>
    </div>
  </g:form>
</body>
</html>
