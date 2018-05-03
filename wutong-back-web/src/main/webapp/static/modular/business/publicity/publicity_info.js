/**
 * 初始化通知详情对话框
 */
var PublicityInfoDlg = {
    publicInfoData: {},
    editor: null,
    validateFields: {
        attachment: {
            validators: {
                notEmpty: {
                    message: '请上传中标通告文件'
                }
            }
        },
        projectCode: {
            validators: {
                notEmpty: {
                    message: '项目编码不能为空'
                }
            }
        },
        projectName: {
            validators: {
                notEmpty: {
                    message: '项目名称不能为空'
                }
            }
        },
        winCompany: {
            validators: {
                notEmpty: {
                    message: '中标公司不能为空'
                }
            }
        },
        winDate: {
            validators: {
                notEmpty: {
                    message: '中标时间不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
PublicityInfoDlg.clearData = function () {
    this.publicInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PublicityInfoDlg.set = function (key, val) {
    this.publicInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PublicityInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PublicityInfoDlg.close = function () {
    parent.layer.close(window.parent.BidPublicity.layerIndex);
}

/**
 * 收集数据
 */
PublicityInfoDlg.collectData = function () {
    this.set('attachment').set('projectCode').set('projectName').set('industry').set('firstCandidate').set('secondCandidate').set('thirdCandidate').set('winCompany').set('winDate').set('amount').set('companyName').set('id').set('publicityDate').set('cdate').set('publicityStatus').set('owner');
}


PublicityInfoDlg.uploadFile = function() {
    //检查项目编号是否输入，作为文件夹路径传入

    $.ajax({
            url: Feng.ctxPath+'/business/publicity/uploadFile',　　　　　　　　　　//上传地址
            type: 'POST',
            cache: false,
            data: new FormData($('#uploadForm')[0]),　　　　　　　　　　　　　//表单数据
            processData: false,
            contentType: false,
            success:function(data){
                if(data.result == 'SUCCESS'){
                    $('#attachment').val(data.fileName);
                    $('#preAttachment').attr('src', 'http://wut-publicity.oss-cn-beijing.aliyuncs.com/' + data.fileName);
                    $('#preAttachment').css("display", "block");
                } else {
                    Feng.error("文件上传失败!")
                }
            }
    });
}

/**
 * 验证数据是否为空
 */
PublicityInfoDlg.validate = function () {
    // $('#publicityForm').data("bootstrapValidator").resetForm();
    // $('#publicityForm').bootstrapValidator('validate');
    // return $("#publicityForm").data('bootstrapValidator').isValid();
    var projectCode = $.trim($('#projectCode').val());
    if ((!projectCode) || projectCode == '' || projectCode == undefined || projectCode == null) {
        Feng.error("请填写项目编号!")
        return false;
    }
    var projectName = $.trim($('#projectName').val());
    if ((!projectName) || projectName == '' || projectName == undefined || projectName == null) {
        Feng.error("请填写项目名称!")
        return false;
    }
    var owner = $.trim($('#owner').val());
    if ((!owner) || owner == '' || owner == undefined || owner == null) {
        Feng.error("请填写招标单位名称!")
        return false;
    }
    var companyName = $.trim($('#companyName').val());
    if ((!companyName) || companyName == '' || companyName == undefined || companyName == null) {
        Feng.error("请填写招标代理单位名称!")
        return false;
    }

    var firstCandidate = $.trim($('#firstCandidate').val());
    var secondCandidate = $.trim($('#secondCandidate').val());
    var thirdCandidate = $.trim($('#thirdCandidate').val());
    if (((!firstCandidate) || firstCandidate == '' || firstCandidate == undefined || firstCandidate == null) &&
        ((!secondCandidate) || secondCandidate == '' || secondCandidate == undefined || secondCandidate == null) &&
        ((!thirdCandidate) || thirdCandidate == '' || thirdCandidate == undefined || thirdCandidate == null) ) {
        Feng.error("请至少填写一个中标候选人名称!")
        return false;
    }
    var winCompany = $.trim($('#winCompany').val());
    if ((!winCompany) || winCompany == '' || winCompany == undefined || winCompany == null) {
        Feng.error("请填写中标单位名称!")
        return false;
    }
    var amount = $.trim($('#amount').val());
    if ((!amount) || amount == '' || amount == undefined || amount == null) {
        Feng.error("请填写中标金额!")
        return false;
    }
    var winDate = $.trim($('#winDate').val());
    if ((!winDate) || winDate == '' || winDate == undefined || winDate == null) {
        Feng.error("请填写中标时间!")
        return false;
    }
    var attachment = $.trim($('#attachment').val());
    if ((!attachment) || attachment == '' || attachment == undefined || attachment == null) {
        Feng.error("请上传中标通告图片!")
        return false;
    }
    return true;
};

/**
 * 提交添加
 */
PublicityInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/business/publicity/add", function (data) {
        Feng.success("添加成功!");
        window.parent.BidPublicity.table.refresh();
        PublicityInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.publicInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PublicityInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/business/publicity/update", function (data) {
        Feng.success("修改成功!");
        window.parent.BidPublicity.table.refresh();
        PublicityInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.publicInfoData);
    ajax.start();
}


$(function () {
    // Feng.initValidator("publicityForm", PublicityInfoDlg.validateFields);
    //
    // //初始化编辑器
    // var E = window.wangEditor;
    // var editor = new E('#editor');
    // editor.create();
    // editor.txt.html($("#contentVal").val());
    // var editor = $('#content');
    // PublicityInfoDlg.editor = editor;
    var industry = 1;
    if ($('#industryTemp')) {
        industry = $('#industryTemp').val();
    }
    if (!industry) {
        industry = 1;
    }
    $('#industry').val(industry);
    $("#winDate").datepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
    });
});
