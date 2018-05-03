/**
 * 初始化通知详情对话框
 */
var NoticeInfoDlg = {
    noticeInfoData: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
NoticeInfoDlg.clearData = function () {
    this.noticeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticeInfoDlg.set = function (key, val) {
    this.noticeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticeInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
NoticeInfoDlg.close = function () {
    parent.layer.close(window.parent.BusinessNotice.layerIndex);
}

/**
 * 收集数据
 */
NoticeInfoDlg.collectData = function () {
    this.noticeInfoData['content'] = editor.getContent();
    this.set('title').set('columnId');
}

/**
 * 验证数据是否为空
 */
NoticeInfoDlg.validate = function () {
    // $('#noticeInfoForm').data("bootstrapValidator").resetForm();
    // $('#noticeInfoForm').bootstrapValidator('validate');
    // return $("#noticeInfoForm").data('bootstrapValidator').isValid();
    var title = $.trim($('#title').val());
    if (!title || title == '' || title == undefined) {
        Feng.error("请填写标题!")
        return false;
    }
    var content = $.trim(editor.getContent());
    if (!content || content == '' || content == undefined) {
        Feng.error("请填写内容!")
        return false;
    }
    return true;
};

/**
 * 提交添加
 */
NoticeInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/business/notice/add", function (data) {
        Feng.success("添加成功!");
        window.parent.BusinessNotice.table.refresh();
        NoticeInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
NoticeInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/notice/update", function (data) {
        Feng.success("修改成功!");
        window.parent.BusinessNotice.table.refresh();
        NoticeInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticeInfoData);
    ajax.start();
}

$(function () {
    // Feng.initValidator("noticeInfoForm", NoticeInfoDlg.validateFields);
    //
    // //初始化编辑器
    // var E = window.wangEditor;
    // var editor = new E('#editor');
    // editor.create();
    // editor.txt.html($("#contentVal").val());
    var editor = $('#content');
    NoticeInfoDlg.editor = editor;
});
