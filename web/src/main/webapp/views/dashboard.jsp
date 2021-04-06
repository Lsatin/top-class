<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!doctype html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="<%= basePath %>/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%= basePath %>/dist/css/bootstrap-table.min.css">
        <link rel="stylesheet" href="<%= basePath %>/dist/css/all.min.css">
        <link rel="stylesheet" href="<%= basePath %>/dist/css/dashboard.css">
        <title>尖子班</title>
    </head>
    <body>
        <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
            <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="/">
                <span data-feather="smile"></span>
                尖子班
            </a>
            <input class="form-control form-control-dark w-100" type="text" placeholder="搜索" aria-label="Search" />
            <ul class="navbar-nav px-3">
                <li class="nav-item text-nowrap">
                    <span class="border border-dark" data-feather="user" color="white" width="24"></span>
                </li>
            </ul>
            <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-toggle="collapse" data-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </nav>
        <div class="progress sticky-top" style="height: .15rem;">
          <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
        </div>

        <div class="container-fluid">
            <div class="row">
                <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
                    <div class="sidebar-sticky pr-5">
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link active" href="index">
                                    <span data-feather="home"></span>
                                    主页
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="school">
                                    <span data-feather="layout"></span>
                                    学校
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="class">
                                    <span data-feather="cast"></span>
                                    班级
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="course">
                                    <span data-feather="layers"></span>
                                    课程
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="teacher">
                                    <span data-feather="user"></span>
                                    老师
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="student">
                                    <span data-feather="users"></span>
                                    学生
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main role="main" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <div class="container-fluid mt-5">
                        <jsp:include page="index.jsp" flush="true"/>
                    </div>
                </main>
            </div>
        </div>

        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" id="modalSave">保存</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="<%= basePath %>/dist/js/feather.min.js"></script>
        <script src="<%= basePath %>/dist/js/jquery-3.5.1.min.js"></script>
        <script src="<%= basePath %>/dist/js/popper.min.js"></script>
        <script src="<%= basePath %>/dist/js/bootstrap.min.js"></script>
        <script src="<%= basePath %>/dist/js/bootstrap-table.min.js"></script>
        <script src="<%= basePath %>/dist/js/bootstrap-table-zh-CN.min.js"></script>
        <script src="<%= basePath %>/dist/js/dashboard.js"></script>

    </body>
</html>