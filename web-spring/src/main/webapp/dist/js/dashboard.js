(function () {
    'use strict'
    feather.replace()

    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
            form.classList.add('was-validated');
            }, false);
        });
    }, false);

    // 左侧菜单点击事件（激活菜单样式）
    $('#sidebarMenu a').bind('click', function() {
        var url = window.location.href + $(this).attr('href')
        // 清除右侧内容
        $('main div').empty()
        // 请求内容
        $.ajax({
            type: "GET",
            url: url,
            timeout: 6000,
            beforeSend: function() {
            },
            complete: function(xhr, ts) {
                toast(ts, xhr.status)
            },
            success: function(data) {
                $('main div').html(data)
            },
            error: function(e, xhr, opt) {
                $('main div').html(e.responseText)
            }
        })
        // 删除激活菜单样式
        $('#sidebarMenu a').removeClass('active')
        // 当前点击菜单激活样式
        $(this).addClass('active')
        return false
    })

}())

/**
 *
 */
function toast(msg, code, timeout) {
    let level = 'info'
    switch (code) {
        case 500:
            level = 'danger'
        break;
        case 200:
            level = 'success'
        break;
        case 404:
            level = 'warning'
        break
        default:
            level = 'primary'
        break
    }
    $('main div:first').prepend('<div class="alert alert-' + level + ' alert-dismissible fade show" role="alert">' +
                    msg +
                   '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>')
    setTimeout("$('.alert').alert('close')", timeout == null || timeout == undifind ? 1200 : timeout)
}
