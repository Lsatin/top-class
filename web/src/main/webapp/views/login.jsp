<%@ page contentType="text/html;charset=UTF-8" %>
<div class="alert alert-danger alert-dismissible fade show" role="alert">
    <strong>
        系统检测到您还未登入系统，请登入后重新尝试...
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </strong>
</div>
<form class="needs-validation" novalidate action="/signIn" method="post">
    <div class="form-row">
        <div class="col-md-6 mb-3">
            <label for="account">账&nbsp;&nbsp;&nbsp;户</label>
            <input type="text" class="form-control" id="username" name="username" length="20" required>
            <div class="invalid-feedback">
                请输入账户（老师姓名 / 学生姓名）
            </div>
        </div>
        <div class="col-md-6 mb-3">
            <label for="password">密&nbsp;&nbsp;&nbsp;码</label>
            <input type="password" class="form-control" id="password" name="password" length="50" required>
            <div class="invalid-feedback">
                请输入密码
            </div>
        </div>
    </div>
  <button class="btn btn-primary" type="submit">登&nbsp;&nbsp;&nbsp;入</button>
  <button class="btn btn-link" type="button">忘记密码</button>
</form>