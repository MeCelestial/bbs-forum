<%--
  Created by IntelliJ IDEA.
  User: WangYuYang
  Date: 2019/12/1
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
    <head>
        <title>忘记密码</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
              crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/my-login.css">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/my-login.js"></script>
    </head>
    <body class="my-login-page">
        <section class="h-100">
            <div class="container h-100">
                <div class="row justify-content-md-center align-items-center h-100">
                    <div class="card-wrapper">
                        <div class="brand">
                            <img src="../images/logo.jpg" alt="bootstrap 4 login page">
                        </div>
                        <div class="card fat">
                            <div class="card-body">
                                <h4 class="card-title">Forgot Password</h4>
                                <form action="${pageContext.request.contextPath}/user/forget" method="POST"
                                      class="my-login-validation" novalidate="">
                                    <div class="form-group">
                                        <label for="email">E-Mail Address</label>
                                        <input id="email" type="email" class="form-control" name="email" value=""
                                               required autofocus>
                                        <div class="invalid-feedback">
                                            Email is invalid
                                        </div>
                                        <div class="form-text text-muted">
                                            By clicking "Reset Password" we will send a password reset link
                                        </div>
                                    </div>

                                    <div class="form-group m-0">
                                        <button type="submit" class="btn btn-primary btn-block">
                                            Reset Password
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="footer">
                            Copyright &copy; 2019 &mdash; Your Company
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
