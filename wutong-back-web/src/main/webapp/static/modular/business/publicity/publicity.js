var BidPublicity = {
		id: 'BidPublicityTable',
		setItem: null,
		table: null,
		layerIndex: -1
};

Date.prototype.format = function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
        (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
            RegExp.$1.length==1 ? o[k] :
                ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}


BidPublicity.initColumn = function() {
	return [{field: 'selectItem', radio: true},
		{title: '项目编号', field: 'projectCode', align: 'center', valign: 'middle',width:'50px'},
        {title: '项目名称', field: 'projectName', align: 'left', valign: 'middle', sortable: true},
        {title: '招标单位', field: 'owner', align: 'center', valign: 'middle', sortable: true},
        {title: '招标代理单位', field: 'companyName', align: 'center', valign: 'middle', sortable: true},
        {title: '中标单位候选人1', field: 'firstCandidate', align: 'center', valign: 'middle', sortable: true},
        {title: '中标单位候选人2', field: 'secondCandidate', align: 'center', valign: 'middle', sortable: true},
        {title: '中标单位候选人3', field: 'thirdCandidate', align: 'center', valign: 'middle', sortable: true},
        {title: '中标单位', field: 'winCompany', align: 'center', valign: 'middle', sortable: true},
        {title: '开标时间', field: 'publicityDate', align: 'center', valign: 'middle', sortable: true},
        {title: '中标时间', field: 'winDate', align: 'center', valign: 'middle', sortable: true, formatter: function (value, row, index) {
            return value.substr(0, 10);
        }},
        {title: '发布状态', field: 'publicityStatus', align: 'center', valign: 'middle', sortable: true, formatter: function(value, row, index) {
            return value == 1 ? "已发布" : "未发布";
        }}];
};


BidPublicity.search = function () {
    var queryData = {};
    queryData['publicityStatus'] = $("#publicityStatus").val();
    queryData['industry'] = $("#industry").val();
    BidPublicity.table.refresh({query: queryData});
}

/**
 * 检查是否选中
 */
BidPublicity.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BidPublicity.seItem = selected[0];
        return true;
    }
};

/**
 * 修改公告发布状态
 * @param status
 */
BidPublicity.upstatus = function (status) {
    if (this.check()) {

        var operation = function(){
            var id = BidPublicity.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/business/publicity/upstatus", function () {
                Feng.success((status == 1 ? "公告发布" : "公告撤销") + "成功!");
                BidPublicity.table.refresh();
            }, function (data) {
                Feng.error((status == 1 ? "公告发布" : "公告撤销") + "失败!");
            });
            ajax.set("status", status);
            ajax.set("id", id);
            ajax.start();
        };

        Feng.confirm("是否" + (status == 1 ? "发布" : "撤销") + "选定的公告?",operation);
    }
}

BidPublicity.openChangePolicity = function() {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑中标公告',
            area: ['1024px', '768px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/business/publicity/openChange?id=' + this.seItem.id
        });
        this.layerIndex = index;
    }
}


/**
 * 删除公告
 */
BidPublicity.delete = function () {
    if (this.check()) {

        var operation = function(){
            var id = BidPublicity.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/business/publicity/delete", function () {
                Feng.success("删除成功!");
                BidPublicity.table.refresh();
            }, function (data) {
                Feng.error("删除失败!");
            });
            ajax.set("id", id);
            ajax.start();
        };

        Feng.confirm("是否删除选定的公告?",operation);
    }
};


/**
 * 点击添加新公告
 */
BidPublicity.openAddNotice = function () {
    var index = layer.open({
        type: 2,
        title: '添加中标公告',
        area: ['1024px', '768px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/business/publicity/openAdd'
    });
    this.layerIndex = index;
};


$(function () {
    var defaultColunms = BidPublicity.initColumn();
    var table = new BSTable(BidPublicity.id, "/business/publicity/list", defaultColunms);
    BidPublicity.table = table.init();
});