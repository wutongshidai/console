var Expert = {
		id: 'ExpertTable',
		setItem: null,
		table: null,
		layerIndex: -1
};


Expert.initColumn = function() {
	return [{field: 'selectItem', radio: true}, 
		{title: '专家编号', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '专家姓名', field: 'spare3', align: 'center', valign: 'middle', sortable: true},
        {title: '职称', field: 'title', align: 'center', valign: 'middle', sortable: true},
        {title: '工作年限', field: 'dateWorke', align: 'center', valign: 'middle', sortable: true},
        {title: '学历', field: 'education', align: 'center', valign: 'middle', sortable: true}];
};


Expert.search = function () {
    var queryData = {};
    queryData['expertName'] = $("#expertName").val();
    Expert.table.refresh({query: queryData});
}


$(function () {
    var defaultColunms = Expert.initColumn();
    var table = new BSTable(Expert.id, "/business/experts/list", defaultColunms);
    table.setPaginationType("client");
    Expert.table = table.init();
});