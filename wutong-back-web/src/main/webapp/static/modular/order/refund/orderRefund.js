var OrderRefund = {
    id: 'OrderRefundTable',
    setItem: null,
    table: null,
    layerIndex: -1
};


OrderRefund.initColumn = function() {
    return [{field: 'selectItem', radio: true},
        {title: '订单编号', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '项目名称', field: 'tenderProjectName', align: 'left', valign: 'middle', sortable: true},
        {title: '招标公司', field: 'tenderComName', align: 'left', valign: 'middle', sortable: true},
        {title: '投标公司', field: 'userName', align: 'left', valign: 'middle', sortable: true},
        {title: '项目金额', field: 'projectAmound', align: 'left', valign: 'middle', sortable: true},
        {title: '保证金金额', field: 'bidBond', align: 'left', valign: 'middle', sortable: true},
        {title: '退款状态', field: 'refundStatus', align: 'left', valign: 'middle', sortable: true, formatter: function (data, row, rowindex) {
            return data == 0 ? '未退款' : (data == 1 ? '已退款' : '退款中');
        }},
        {title: '退款人', field: 'refundUser', align: 'left', valign: 'middle', sortable: true},
        {title: '退款渠道', field: 'payChannel', align: 'left', valign: 'middle', sortable: true, formatter: function (data, row, rowindex) {
            return data == 'ALIPAY' ? '支付宝' : '微信';
        }},
        {title: '退款时间', field: 'refundTime', align: 'left', valign: 'middle', sortable: true}];
};


OrderRefund.search = function () {
    var queryData = {};
    queryData['tenderProjectName'] = $("#tenderProjectName").val();
    queryData['tenderComName'] = $("#tenderComName").val();
    queryData['userName'] = $("#userName").val();
    queryData['refundStatus'] = $("#refundStatus").val();
    queryData['bidOrderId'] = $("#bidOrderId").val();
    OrderRefund.table.refresh({query: queryData});
}

/**
 * 检查是否选中
 */
OrderRefund.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        OrderRefund.seItem = selected[0];
        return true;
    }
};




$(function () {
    var defaultColunms = OrderRefund.initColumn();
    var table = new BSTable(OrderRefund.id, "/order/tender/refund/list", defaultColunms);
    table.setPaginationType("client");//server 为取服务端  client 为客户端分页
    OrderRefund.table = table.init();
});