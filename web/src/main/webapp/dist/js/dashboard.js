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
        // 面包屑内容更新
        $('main nav ol li').text($(this).text());
        // 清除右侧内容
        $('main div').empty()
        // 请求内容
        $.ajax({
            type: "GET",
            url: url,
            timeout: 6000,
            beforeSend: function(){
                $('.progress-bar').css('width', '10%')
            },
            complete: function(xhr, ts){
                $('.progress-bar').css('width', '0%')
                toast(ts, xhr.status)
            },
            success: function(data) {
                $('main div').html(data)
                $('.progress-bar').css('width', '100%')
            },
            error: function(e, xhr, opt) {
                $('main div').html(e.responseText)
                $('.progress-bar').css('width', '100%')
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
function toast(msg, code) {
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
    $('main div:first').before("<div class=\"alert alert-" + level + " alert-dismissible fade show\" role=\"alert\">" +
                    msg +
                   "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">" +
                     "<span aria-hidden=\"true\">&times;</span>" +
                   "</button>" +
                 "</div>")
    setTimeout("$('.alert').alert('close')", 1500)
}