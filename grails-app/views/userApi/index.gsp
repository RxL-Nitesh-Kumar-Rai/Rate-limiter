<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'userApi.label', default: 'UserApi')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-userApi" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation" style="display: none">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-userApi" class="content scaffold-list" role="main">
    <h1 style="display: none"><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <f:table collection="${userApiList}"/>

    <div class="pagination" style="display: none">
        <g:paginate total="${userApiCount ?: 0}"/>
    </div>
</div>

<div style="float: left; margin-left: 100px">

    <g:form>
        <div style="float: left">
            <p style="margin-top: 6px">USERNAME</p>

            <p style="margin-top: 6px">API NAME</p>

            <p style="margin-top: 6px">RATE LIMIT</p>
        </div>

        <div style="float: right; margin-left: 10px">
            <input type="text" name="userName"/><br>
            <input type="text" name="apiName"/> <g:actionSubmit value="Submit" action="update"
                                                                style="alignment: center"/> <br>
            <input type="text" name="rateLimit"/><br>
        </div>

    </g:form>
</div>

<div style="float: right; margin-right: 100px; max-height: 500px; overflow-y: scroll">
    <table>
        <thead>
        <th style="width: 100px">User name</th>
        <th style="width: 100px">Api</th>
        <th style="width: 100px">Rate limit</th>
        <th style="width: 100px">Message</th>
        <th style="width: 100px">Delete</th>
        </thead>
        <tbody>
        <g:each in="${list}" var="val">
            <tr>
                <td>${val.userName}</td>
                <td>${val.api}</td>
                <td>${val.rateLimit}</td>
                <td>${val.message}</td>
                <td><a href="${createLink(action: "delete", params: [userName: val.userName, apiName: val.api])}">X</a>
                </td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
</body>
</html>