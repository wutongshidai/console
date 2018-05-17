var BusinessAdPositon = {
    id: 'adPositionTable',
    setItem: null,
    table: null,
    layerIndex: -1
};

/**
 * 打开添加页面
 */
BusinessAdPositon.openAddad = function () {
    var index = layer.open({
        type: 2,
        title: '添加新公告',
        area: ['1024px', '768px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/business/adPositionId/openAdd'
    });
    this.layerIndex = index;
};

BusinessAdPositon.initColumn = function() {
    return [{field: 'selectItem', radio: true},
        {title: '编号', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '名称', field: 'name', align: 'left', valign: 'middle', sortable: true},
        {title: '图片', field: 'img', align: 'center', valign: 'middle', sortable: true},
        {title: '店铺链接', field: 'url', align: 'center', valign: 'middle', sortable: true,},
        // {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true, formatter: function(value, row, index) {
        //         return value == 1 ? "启用" : "禁用";
        //     }},
        {title: '位置', field: 'levels', align: 'center', valign: 'middle', sortable: true},
        {title: '发布时间', field: 'time', align: 'center', valign: 'middle', sortable: true}];
};



/**
 * 检查是否选中
 */
BusinessAdPositon.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        BusinessAdPositon.seItem = selected[0];
        return true;
    }
};



/**
 * 修改广告位内容
 * @param status
 */
BusinessAdPositon.upad = function () {
    console.log( this.seItem)
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改菜单',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/business/adPositionId/ad_edit/'+ this.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 删除广告位
 */
BusinessAdPositon.deletead = function () {
    if (this.check()) {
        var operation = function(){
            var announcementId = BusinessAdPositon.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/business/notice/delete", function () {
                Feng.success("删除成功!");
                BusinessAdPositon.table.refresh();
            }, function (data) {
                Feng.error("删除失败!");
            });
            ajax.set("announcementId", announcementId);
            ajax.start();
        };

        Feng.confirm("是否删除选定的公告?",operation);
    }
};

$(function () {
    var defaultColunms = BusinessAdPositon.initColumn();
    var table = new BSTable(BusinessAdPositon.id, "/business/adPositionId/list", defaultColunms);
    BusinessAdPositon.table = table.init();
});