<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    var pager = {current: {page: 1, size: 20}, total: {page: 1, size: 20}, data: {}}
    $(function() {
        $('#schoolTable').bootstrapTable({
            columns: [
                {field: 'state', checkbox: 'checkbox'},
                {field: 'id', title: '#'},
                {field: 'name', title: '学校名称'},
                {field: 'address', title: '学校地址'},
                {field: 'zipCode', title: '学校邮编'}
            ],                                                              // 表列
            method: 'POST',                                                 // 请求方式
            url: window.location.href + 'school/list',                      // 请求地址
            search: false,                                                  // 搜索栏
            checkboxHeader: true,                                           // 选择框
            clickToSelect: true,                                            // 单击选择行
            loadingFontSize: '13px',                                        // 【加载中】字体大小

            pagination: true,                                               // 分页器
            paginationVAlign: 'top',                                        // 分页器垂直对齐
            pageNumber: pager.current.page,                                 // 页码
            pageSize: pager.current.size,                                   // 页量
            sidePagination: 'server',                                       // 分页方式【服务器】
            pageList: "[10, 25, 50, 100, 200, All]",                        // 页量选择

            iconSize: 'lg',                                                 // 图标大小
            buttons: 'buttons',                                             // 自定义按钮
            showRefresh: true,                                              // 刷新按钮
            showFullscreen: true,                                           // 全屏按钮

            toolbar: '#toolbar',                                            // 自定义查询器
            queryParams: 'queryParamsHandler',                              // 自定义请求处理器
            responseHandler: 'queryResponseHandler',                        // 自定义响应处理器
            idField: 'id'                                                   // 主键字段
        })

    });

    // 自定义按钮
    function buttons () {
        return {
            btnEdit: {
                text: '修改',
                icon: 'fa-pencil-alt',
                event: function() {
                    var selectRow = $('#schoolTable').bootstrapTable('getSelections');
                    if (selectRow.length > 1) {
                        toast('请选择单条记录...', 404)
                        return
                    }
                    if (selectRow.length < 1) {
                        toast('请选择记录...', 404)
                        return
                    }

                    $('#globalModal').on('shown.bs.modal', function (event) {
                        var modalTitle = this.querySelector('.modal-title')
                        var modalBody = this.querySelector('.modal-body')
                        modalTitle.textContent = selectRow[0].name
                        var e = '<form><div class="form-group">'
                        e += '<input type="hidden" class="form-control" id="modal_id" value="' + selectRow[0].id + '" />'
                        e += '<label for="fName" class="col-form-label">学校名称: </label><input type="text" class="form-control" id="modal_name" value="' + selectRow[0].name + '" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="fAddress" class="col-form-label">学校地址:</label> <input type="text" class="form-control" id="modal_address" value="' + selectRow[0].address + '" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="fZipCode" class="col-form-label">邮政编码:</label> <input type="text" class="form-control" id="modal_zipCode" value="' + selectRow[0].zipCode + '" />'
                        e += '</div></form>'
                        $(modalBody).html(e)
                    }).modal('show')

                    $('[data-bs-submit="modal"]').unbind()
                    $('[data-bs-submit="modal"]').bind('click', function() {
                        var params = {id: $('#modal_id').val(), name: $('#modal_name').val(), address: $('#modal_address').val(), zipCode: $('#modal_zipCode').val()}
                        $.ajax({
                            beforeSend: function() {},
                            complete: function(xhr, ts){
                                $('#globalModal').modal('hide')
                            },
                            type: "POST",
                            url: window.location.href + "school/save",
                            dataType: 'json',
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(params),
                            success: function(data) {
                                if (data) {
                                    $('#schoolTable').bootstrapTable('refresh')
                                }
                            },
                            error: function(e, xhr, opt) {
                            }
                        })
                    })
                },
                attributes: {
                    title: '修改'
                }
            },
            btnAdd: {
                text: '新增',
                icon: 'fa-plus',
                event: function () {
                    $("#globalModal").on('shown.bs.modal', function (event) {
                        var modalTitle = this.querySelector('.modal-title')
                        var modalBody = this.querySelector('.modal-body')
                        modalTitle.textContent = '新增'
                        var e = '<form><div class="form-group">'
                        e += '<label for="name" class="col-form-label">学校名称: </label><input type="text" class="form-control" id="modal_name" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="address" class="col-form-label">学校地址:</label> <input type="text" class="form-control" id="modal_address" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="zipCode" class="col-form-label">邮政编码:</label> <input type="text" class="form-control" id="modal_zipCode" />'
                        e += '</div></form>'
                        $(modalBody).html(e)
                    }).modal('show')

                    $('[data-bs-submit="modal"]').unbind()
                    $('[data-bs-submit="modal"]').bind('click', function() {
                        var params = {name: $('#modal_name').val(), address: $('#modal_address').val(), zipCode: $('#modal_zipCode').val()}
                        $.ajax({
                            beforeSend: function() {},
                            complete: function(xhr, ts) {
                                $('#globalModal').modal('hide')
                            },
                            type: "POST",
                            url: window.location.href + "school/save",
                            dataType: 'json',
                            contentType: "application/json;charset=utf-8",
                            data: JSON.stringify(params),
                            success: function(data) {
                                if (data) {
                                    $('#schoolTable').bootstrapTable('refresh')
                                }
                            },
                            error: function(e, xhr, opt) {
                            }
                        })
                    })
                },
                attributes: {
                    title: '新增'
                }
            },
            btnMinus: {
                text: '删除',
                icon: 'fa-minus',
                event: function() {
                    var selectRow = $('#schoolTable').bootstrapTable('getSelections');
                    if (selectRow.length > 1) {
                        toast('请选择单条记录...', 404)
                        return
                    }
                    if (selectRow.length < 1) {
                        toast('请选择记录...', 404)
                        return
                    }
                    $.ajax({
                        beforeSend: function(){},
                        complete: function(xhr, ts){},
                        type: "GET",
                        url: window.location.href + "school/delete",
                        data: {id: selectRow[0].id},
                        success: function(data) {
                            if (data) {
                                $('#schoolTable').bootstrapTable('refresh')
                            }
                        },
                        error: function(e, xhr, opt) {
                        }
                    })
                },
                attributes: {
                    title: '删除'
                }
            }
        }
    }

    // 自定义查询参数处理器
    function queryParamsHandler(params) {
        pager.current.page = params.offset / params.limit + 1
        pager.current.size = params.limit
        $('#toolbar').find('button').attr('disabled', 'disabled')
        $("#toolbar").find('input[name]').each(function() {
            pager.data[$(this).attr('name')] = $(this).val()
        })
        return JSON.stringify(pager)
    }

    // 自定义查询响应处理器
    function queryResponseHandler(resp) {
        console.log(resp)
        var data = {
            total: 0,
            rows: null
        };
        $('#toolbar').find('button').removeAttr('disabled')
        if (resp.code == '200') {
            data.total = resp.data.total.size
            data.rows = resp.data.data
            return data
        }
        return null
    }

</script>
<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
    <h1 class="h2">学校信息</h1>
</div>

<div id="toolbar">
    <form class="row g-3">
        <div class="col-md-4">
            <label for="inputCity" class="form-label">学校名称</label>
            <input name="name" type="text" class="form-control" placeholder="请输入学校名称..." />
        </div>
        <div class="col-md-4">
            <label for="inputCity" class="form-label">学校地址</label>
            <input name="address" type="text" class="form-control" placeholder="请输入学校地址..." />
        </div>
      <div class="col-md-4">
        <label for="inputZip" class="form-label">学校邮编</label>
        <input name="zipCode" type="text" class="form-control" placeholder="请输入学校邮编..." />
      </div>
      <div class="col-12">
        <button type="button" class="btn btn-primary float-end" onclick="javascript:$('#schoolTable').bootstrapTable('refresh');">搜索</button>
      </div>
    </form>
</div>

<div class="table-responsive">
    <table class="table table-striped table-lg" id="schoolTable"></table>
</div>