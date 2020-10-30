<%@ page contentType="text/html;charset=UTF-8" %>
<script>
    $(function(){
        $('#schoolTable').bootstrapTable({
            url: window.location.href + 'school/list',          // 网址
            //search: true,                                       // 搜索
            theadClasses: 'thead-dark',                         // 黑色表头
            checkboxHeader: true,                               // 选择框
            clickToSelect: true,                                // 选择框全选
            loadingFontSize: '12px',                            // 加载字体大小
            pagination: true,                                   // 分页
            pageSize: 10,                                       // 页码
            paginationLoop: false,                              // 分页循环
            buttons: 'buttons',                                 // 自定义按钮
            showColumns: true,                                  // 自定义显示列
            showRefresh: true,                                  // 显示刷新
            showToggle: true,                                   // 卡视图开关
            //showFullscreen: true,                               // 显示全屏
            //detailView: true,                                   // 开启卡视图
            //detailViewByClick: true,                            // 显示细节按钮
            //detailViewIcon: false,                              // 显示细节图标
            detailFormatter: 'detailFormatter',                 // 卡视图格式化
            //detailViewAlign: 'right',                           // 细节按钮局右
            customSort: 'customSort',                           // 自定义排序,
            toolbar: '#toolbar',                                // 自定义查询器
            queryParams: 'queryParamsHandler',                  // 自定义查询参数处理器
            responseHandler: 'queryResponseHandler',            // 自定义查询器响应处理器
            columns: [{
                field: 'state',
                checkbox: 'checkbox'
            },{
                field: 'id',
                title: '#',
                sortable: true
            }, {
                field: 'name',
                title: '学校名称',
                sortable: true
            }, {
                field: 'address',
                title: '学校地址',
                sortable: true
            },{
                field: 'zipCode',
                title: '学校邮编',
                sortable: true
            }]
        })

        //
        $('#ok').click(function () {
          $('#schoolTable').bootstrapTable('refresh')
        })
    });

    // 自定义按钮
    function buttons () {
        return {
            btnWrench: {
                text: '修改',
                icon: 'fa-wrench',
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
                    $("#exampleModal").modal({
                        backdrop: "static",
                        keyboard: true,
                        show: true
                    }).on('shown.bs.modal', function (event) {
                        var _modal = $(this)
                        $(this).removeData("bs.modal");
                        _modal.find('.modal-title').text(selectRow[0].name)
                        var e = '<form><div class="form-group">'
                        e += '<input type="hidden" class="form-control" id="fId" value="' + selectRow[0].id + '" />'
                        e += '<label for="fName" class="col-form-label">学校名称: </label><input type="text" class="form-control" id="fName" value="' + selectRow[0].name + '" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="fAddress" class="col-form-label">学校地址:</label> <input type="text" class="form-control" id="fAddress" value="' + selectRow[0].address + '" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="fZipCode" class="col-form-label">邮政编码:</label> <input type="text" class="form-control" id="fZipCode" value="' + selectRow[0].zipCode + '" />'
                        e += '</div></form>'
                        _modal.find('.modal-body').html(e)
                    })

                    $('#modalSave').unbind()
                    $('#modalSave').bind('click', function(){
                        $.ajax({
                            beforeSend: function(){
                            },
                            complete: function(xhr, ts){
                            },
                            type: "GET",
                            url: window.location.href + "school/save",
                            data: {id: $('#fId').val(),
                                    name: $('#fName').val(),
                                    address: $('#fAddress').val(),
                                    zipCode: $('#fZipCode').val()
                                },
                            success: function(data) {
                                if (data > 1) {
                                    $('#schoolTable').bootstrapTable('refresh')
                                }
                                // 由于model显示时才改变，所以改为手动触发关闭
                                $('#exampleModal').find('button')[0].click()
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
                    $("#exampleModal").modal({
                        backdrop: "static",
                        keyboard: true,
                        show: true
                    }).on('shown.bs.modal', function (event) {
                        var modal = $(this)
                        $(this).removeData("bs.modal");
                        modal.find('.modal-title').text('学校信息')
                        var e = '<form><div class="form-group">'
                        e += '<label for="name" class="col-form-label">学校名称: </label><input type="text" class="form-control" id="fName" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="address" class="col-form-label">学校地址:</label> <input type="text" class="form-control" id="fAddress" />'
                        e += '</div>'
                        e += '<div class="form-group">'
                        e += '<label for="zipCode" class="col-form-label">邮政编码:</label> <input type="text" class="form-control" id="fZipCode" />'
                        e += '</div></form>'
                        modal.find('.modal-body').html(e)
                        modal.find('#modalSave').unbind()
                        modal.find('#modalSave').bind('click', function(){
                            $.ajax({
                                beforeSend: function(){
                                },
                                complete: function(xhr, ts){
                                },
                                type: "GET",
                                url: window.location.href + "school/save",
                                data: {id: $('#fId').val(),
                                        name: $('#fName').val(),
                                        address: $('#fAddress').val(),
                                        zipCode: $('#fZipCode').val()
                                    },
                                success: function(data) {
                                    if (data > 1) {
                                        $('#schoolTable').bootstrapTable('refresh')
                                    }
                                    // 由于model显示时才改变，所以改为手动触发关闭
                                    $('#exampleModal').find('button')[0].click()
                                },
                                error: function(e, xhr, opt) {
                                }
                            })
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
                        beforeSend: function(){
                        },
                        complete: function(xhr, ts){
                        },
                        type: "GET",
                        url: window.location.href + "school/del",
                        data: {id: selectRow[0].id
                            },
                        success: function(data) {
                            if (data > 1) {
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

    // 自定义排序
    function customSort(sortName, sortOrder, data) {
        var order = sortOrder === 'desc' ? -1 : 1
        data.sort(function (a, b) {
            var aa = +((a[sortName] + '').replace(/[^\d]/g, ''))
            var bb = +((b[sortName] + '').replace(/[^\d]/g, ''))
            if (aa < bb) {
                return order * -1
            }
            if (aa > bb) {
                return order
            }
            return 0
        })
    }

    // 自定义查询参数处理器
    function queryParamsHandler() {
        var queryParams = {}
        $("#toolbar").find('input[name]').each(function() {
            queryParams[$(this).attr('name')] = $(this).val()
        })
        return queryParams
    }

    // 自定义查询响应处理器
    function queryResponseHandler(resp) {
        return resp
    }

    // 自定义细节格式化
    function detailFormatter(index, row) {
        var html = []
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>')
        })
        return html.join('')
    }

</script>
<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3">
    <h1 class="h2">学校信息</h1>
</div>

<div id="toolbar">
    <div class="form-inline" role="form">
        <div class="form-group mr-4">
            <span>学校名称: </span>
            <input name="name" class="form-control w100" type="text" placeholder="请输入学校名称..." />
        </div>
        <div class="form-group mr-4">
            <span>学校地址: </span>
            <input name="address" class="form-control w100" type="text" placeholder="请输入学校地址..." />
        </div>
        <div class="form-group mr-2">
            <span>学校邮编: </span>
            <input name="zipCode" class="form-control" type="text" placeholder="请输入学校邮编..." />
        </div>
        <button id="ok" type="submit" class="btn btn-primary">检索</button>
    </div>
</div>

<div class="table-responsive">
    <table class="table table-striped table-lg" id="schoolTable"></table>
</div>