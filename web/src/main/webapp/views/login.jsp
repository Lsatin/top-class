<%@ page contentType="text/html;charset=UTF-8" %>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <strong>
        系统检测到您还未登入系统，请登入后重新尝试...
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </strong>
</div>
<div class="container bd-example bd-example-position-examples">
    <form class="needs-validation" novalidate action="/signIn" method="post">
        <div class="mb-3 row">
            <label for="account" class="col-sm-1 col-form-label">账&nbsp;&nbsp;&nbsp;户</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" id="username" name="username" maxlength="20" required>
                <div class="invalid-feedback">
                    请输入账户（老师姓名 / 学生姓名）
                </div>
            </div>
        </div>
        <div class="mb-3 row">
            <label for="password" class="col-sm-1 col-form-label">密&nbsp;&nbsp;&nbsp;码</label>
            <div class="col-sm-6">
                <input type="password" class="form-control" id="password" name="password" maxlength="50" required>
                <div class="invalid-feedback">
                    请输入密码
                </div>
            </div>
        </div>
        <div class="mb-3 row">
            <div class="col-sm-1"></div>
            <div class="col-sm-6">
                <button class="btn btn-primary" type="submit">登&nbsp;&nbsp;&nbsp;入</button>
                <button class="btn btn-link" type="button">忘记密码</button>
            </div>
        </div>
    </form>
</div>
