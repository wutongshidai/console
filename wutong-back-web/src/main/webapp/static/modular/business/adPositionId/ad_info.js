/**
 * 初始化通知详情对话框
 */
var adPositionInfoDlg = {
    adPositionInfoData: {}
};



adPositionInfoDlg.uploadFile = function() {
    //检查项目编号是否输入，作为文件夹路径传入

    $.ajax({
        url: Feng.ctxPath+'/business/adPositionId/uploadFile',　　　　　　　　　　//上传地址
        type: 'POST',
        cache: false,
        data: new FormData($('#uploadForm1')[0]),　　　　　　　　　　　　　//表单数据
        processData: false,
        contentType: false,
        success:function(data){
            console.log(data)
            if(data.state == 'SUCCESS'){
                $('#img').val('http://wut-ggw.oss-cn-beijing.aliyuncs.com/' + data.url);
            } else {
                Feng.error("文件上传失败!")
            }
        }
    });
}
adPositionInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/business/adPositionId/upAd", function (data) {
        Feng.success("修改成功!");
        window.parent.BusinessAdPositon.table.refresh();
        adPositionInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!");
    });
    ajax.set(this.adPositionInfoData);
    ajax.start();
}

adPositionInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    console.log(this.validate())
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/business/adPositionId/addAd", function (data) {
        Feng.success("添加成功!");
        window.parent.BusinessAdPositon.table.refresh();
        adPositionInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!");
    });
    ajax.set(this.adPositionInfoData);
    ajax.start();
}

/**
 * 清除数据
 */
adPositionInfoDlg.clearData = function () {
    this.adPositionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
adPositionInfoDlg.set = function (key, val) {
    this.adPositionInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
adPositionInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
adPositionInfoDlg.close = function () {
    parent.layer.close(window.parent.BusinessAdPositon.layerIndex);
}

/**
 * 收集数据
 */
adPositionInfoDlg.collectData = function () {
    this.set('name').set('img').set('url').set('levels').set('id')
}

/**
 * 判空
 * @returns {boolean}
 */
adPositionInfoDlg.validate = function () {
    // $('#noticeInfoForm').data("bootstrapValidator").resetForm();
    // $('#noticeInfoForm').bootstrapValidator('validate');
    // return $("#noticeInfoForm").data('bootstrapValidator').isValid();
    var title = $.trim($('#name').val());
    if (!title || title == '' || title == undefined) {
        Feng.error("请填写店铺名称!")
        return false;
    }
    var content = $.trim($('#img').val());
    if (!content || content == '' || content == undefined) {
        Feng.error("请上传图片!")
        return false;
    }
    var content = $.trim($('#url').val());
    if (!content || content == '' || content == undefined) {
        Feng.error("请填写店铺链接!")
        return false;
    }
    // var content = $.trim($('#status').val());
    // if (!content || content == '' || content == undefined) {
    //     Feng.error("请填写状态!")
    //     return false;
    // }
    var content = $.trim($('#levels').val());
    if (!content || content == '' || content == undefined) {
        Feng.error("请填写位置!")
        return false;
    }
    return true;
};