<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <title><g:message code="auth.please.sign.in" /></title>
    <style>
    body {
        padding-top: 40px;
        padding-bottom: 40px;
        /*background-color: #eee;*/
    }

    .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: 0 auto;
        /*background-color: #eee;*/
    }

    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;
    }

    .form-signin .checkbox {
        font-weight: normal;
    }

    .form-signin .form-control {
        position: relative;
        font-size: 16px;
        height: auto;
        padding: 10px;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }

    .form-signin .form-control:focus {
        z-index: 2;
    }

    .form-signin input[type="text"] {
        margin-bottom: -1px;
        border-bottom-left-radius: 0;
        border-bottom-right-radius: 0;
    }

    .form-signin input[type="password"] {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
    }
    </style>
</head>

<body>
<g:if test="${flash.message}">
    <div class="alert alert-danger">${flash.message}</div>
</g:if>


<g:form class="form-signin well" action="signIn">
    <input type="hidden" name="targetUri" value="${targetUri}"/>

    <h2 class="form-signin-heading"><g:message code="auth.please.sign.in" /></h2>
    <input type="text" name="login" class="form-control" placeholder="${message(code: 'auth.login')}" autofocus value="${login}">
    <input type="password" name="password" class="form-control" placeholder="${message(code: 'auth.password')}">
    <label class="checkbox"><input type="checkbox" name="rememberMe" value="${rememberMe}"> <g:message code="auth.remember.me"/></label>
    <button class="btn btn-lg btn-primary btn-block" type="submit">${message(code: message(code: "auth.sign.in"))}</button>
</g:form>
</body>
</html>
