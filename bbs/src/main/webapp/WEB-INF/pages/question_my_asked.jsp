<%--
  Created by IntelliJ IDEA.
  User: zengfeng
  Date: 2019/12/9
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>我的提问</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <style type="text/css">
            td {
                font-size: 10px;
                border-width: 0px;
            }

            a {
                text-decoration: none;
            }

            div {
                border: 1px solid #CDCDCD;
                margin: 5px;
            }
        </style>
        <script type="application/javascript">
            // 删除问题
            function delQ(qid) {
                if (confirm("你确定要删除这个问题吗？")) {
                    location.href = "${pageContext.request.contextPath}/user/delQ?qid=" + qid;
                }
            }
        </script>
    </head>
    <body>
        <a href="${pageContext.request.contextPath}/page/question_ask">
            <img src="${pageContext.request.contextPath}/images/ask.jpg" style="width: 76px;height: 32px;margin: 5px;">
        </a>
        <div>
            <table class="table table-hover">
                <c:forEach items="${questionsAsked}" var="question" varStatus="vs">
                    <tr>
                            <%--<td>${vs.count}</td>--%>
                        <td style="width: 500px; text-align: left;">
                            <a href="${pageContext.request.contextPath}/question/look?qid=${question.qid}">
                                <img src="${pageContext.request.contextPath}/images/question_logo.png"
                                     style="width: 20px;height: 20px;"/> ${question.title}
                            </a>
                            <font color="red"> - [悬赏 ${question.credit} 积分] </font>
                            <c:if test="${question.isResolved == 1}">
                                (已解决)
                            </c:if>
                        </td>
                        <td style="text-align: right">
                            <a href="javascript:delQ(${question.qid});">删除</a>
                            &nbsp;&nbsp;&nbsp;
                            <fmt:formatDate value="${question.askTime}" pattern="yyyy-MM-dd HH:mm"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
