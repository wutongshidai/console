var BusinessNotice = {
		id: 'NoticeTable',
		setItem: null,
		table: null,
		layerIndex: -1
};


BusinessNotice.initColumn = function() {
	return [{field: 'selectItem', radio: true},
		{title: '公告编号', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '标题', field: 'title', align: 'left', valign: 'middle', sortable: true},
        {title: '作者', field: 'autherId', align: 'center', valign: 'middle', sortable: true},
        {title: '栏目', field: 'columnId', align: 'center', valign: 'middle', sortable: true, formatter: function (value, row, index) {
            return value == 1 ? "行业新闻" : "梧桐热点";
        }},
        {title: '发布状态', field: 'status', align: 'center', valign: 'middle', sortable: true, formatter: function(value, row, index) {
            return value == 1 ? "已发布" : "未发布";
        }},
        {title: '发布角色', field: 'roleId', align: 'center', valign: 'middle', sortable: true, formatter: function (value, row, index) {
            return value == 1 ? "系统发布" : "用户发布";
        }},
        {title: '发布时间', field: 'cdate', align: 'center', valign: 'middle', sortable: true}];
};


BusinessNotice.search = function () {
    var queryData = {};
    queryData['columnId'] = $("#columnId").val();
    queryData['status'] = $("#status").val();
    queryData['roleId'] = $("#roleId").val();
    BusinessNotice.table.refresh({query: queryData});
}

/**
 * 检查是否选中
 */
BusinessNotice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BusinessNotice.seItem = selected[0];
        return true;
    }
};

/**
 * 修改公告发布状态
 * @param status
 */
BusinessNotice.upstatus = function (status) {
    if (this.check()) {

        var operation = function(){
            var announcementId = BusinessNotice.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/business/notice/upstatus", function () {
                Feng.success((status == 1 ? "公告发布" : "公告撤销") + "成功!");
                BusinessNotice.table.refresh();
            }, function (data) {
                Feng.error((status == 1 ? "公告发布" : "公告撤销") + "失败!");
            });
            ajax.set("announcementId", announcementId);
            ajax.set("status", status);
            ajax.start();
        };

        Feng.confirm("是否" + (status == 1 ? "发布" : "撤销") + "选定的公告?",operation);
    }
}


/**
 * 删除公告
 */
BusinessNotice.delete = function () {
    if (this.check()) {

        var operation = function(){
            var announcementId = BusinessNotice.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/business/notice/delete", function () {
                Feng.success("删除成功!");
                BusinessNotice.table.refresh();
            }, function (data) {
                Feng.error("删除失败!");
            });
            ajax.set("announcementId", announcementId);
            ajax.start();
        };

        Feng.confirm("是否删除选定的公告?",operation);
    }
};


/**
 * 点击添加新公告
 */
BusinessNotice.openAddNotice = function () {
    var index = layer.open({
        type: 2,
        title: '添加新公告',
        area: ['1024px', '768px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/business/notice/openAdd'
    });
    this.layerIndex = index;
};


$(function () {
    var defaultColunms = BusinessNotice.initColumn();
    var table = new BSTable(BusinessNotice.id, "/business/notice/list", defaultColunms);
    BusinessNotice.table = table.init();
});