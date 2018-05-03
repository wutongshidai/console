var Product = {
		id: 'ProductTable',
		setItem: null,
		table: null,
		layerIndex: -1
};


Product.initColumn = function() {
	return [{field: 'selectItem', radio: true},
		{title: '商品编号', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '商品名称', field: 'prodName', align: 'left', valign: 'middle', sortable: true},
        {title: '品牌名称', field: 'brandName', align: 'left', valign: 'middle', sortable: true},
        {title: '所属目录', field: 'catalogPath', align: 'left', valign: 'middle', sortable: true},
        {title: '卖家名称', field: 'sellerName', align: 'left', valign: 'middle', sortable: true},
        {title: '卖家账号', field: 'sellerAccount', align: 'left', valign: 'middle', sortable: true},
        {title: '商品售价', field: 'sellerPrice', align: 'left', valign: 'middle', sortable: true},
        {title: '商品产地', field: 'prodPlace', align: 'left', valign: 'middle', sortable: true},
        {title: '售卖区域', field: 'saleArea', align: 'left', valign: 'middle', sortable: true},
        {title: '商家上架状态', field: 'onSale', align: 'center', valign: 'middle', sortable: true, formatter: function(data, row, rowIndex){
            return (data == 0) ? "商家上架" : "商家上架";
        }},
        {title: '系统上架状态', field: 'sysVisible', align: 'left', valign: 'middle', sortable: true, formatter: function(data, row, rowIndex){
            return (data == 0) ? "系统上架" : "系统下架";
        }},
        {title: '商品上架时间', field: 'addTime', align: 'left', valign: 'middle', sortable: true}];
};


Product.search = function () {
    var queryData = {};
    queryData['prodName'] = $("#prodName").val();
    Product.table.refresh({query: queryData});
}

/**
 * 检查是否选中
 */
Product.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Product.seItem = selected[0];
        return true;
    }
};

/**
 * 系统上架下架功能
 * @param visible
 */
Product.sysVisible = function (visible) {
    if (this.check()) {
        var operation = function() {
            var productId = Product.seItem.id;
            var ajax = new $ax("/business/product/sysVisible", function() {
                Feng.success("更新成功!");
                Product.table.refresh();
            }, function() {
                Feng.error("更新失败!");
            });
            var data = {prodcutId: productId, visible: visible};
            ajax.setData(data);
            ajax.start();
        }
        Feng.confirm("是否确定变更状态?",operation);
    }
}


$(function () {
    var defaultColunms = Product.initColumn();
    var table = new BSTable(Product.id, "/business/product/list", defaultColunms);
    table.setPaginationType("client");//server 为取服务端  client 为客户端分页
    Product.table = table.init();
});