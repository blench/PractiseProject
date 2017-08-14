$.mLayout;

$.mTab;
var addTab = function(tab, params) {
	var iframe = '<iframe src="'
			+ params.url
			+ '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>';
	
	var opts = {
		title : params.title,
		closable : params.closable==null ? true : params.closable,
		iconCls : params.iconCls,
		content : iframe,
		border : false,
		fit : true
	};
	if (tab.tabs('exists', opts.title)) {
		tab.tabs('select', opts.title);
		parent.$.messager.progress('close');
	} else {
		tab.tabs('add', opts);
	}
}

/** 
* @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].src = '';
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
				try {
					CollectGarbage();
				} catch (e) {
				}
			}
		}
	} catch (e) {
	}
};

/**
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 * 创建一个模式化的dialog
 * 
 * @returns $.modalDialog.handler 这个handler代表弹出的dialog句柄
 * 
 * @returns $.modalDialog.xxx 这个xxx是可以自己定义名称，主要用在弹窗关闭时，刷新某些对象的操作，可以将xxx这个对象预定义好
 */
$.modalDialog = function(options) {
	if ($.modalDialog.handler == undefined) {// 避免重复弹出
		var opts = $.extend({
			title : '',
			width : 840,
			height : 680,
			modal : true,
			onClose : function() {
				$.modalDialog.handler = undefined;
				$(this).dialog('destroy');
			}
		}, options);
		
		opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
		
		$.modalDialog.handler = $("<div/>").dialog(opts);
		
		return $.modalDialog.handler
	}
};

/**
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * grid tooltip参数
 * 
 * @author 孙宇
 */
var gridTooltipOptions = {
	tooltip : function(jq, fields) {
		return jq.each(function() {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function() {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function() {
				var content = $(this).text();
				if (content.replace(/(^\s*)|(\s*$)/g, '').length > 5) {
					$(this).tooltip({
						content : content,
						trackMouse : true,
						position : 'bottom',
						onHide : function() {
							$(this).tooltip('destroy');
						},
						onUpdate : function(p) {
							var tip = $(this).tooltip('tip');
							if (parseInt(tip.css('width')) > 500) {
								tip.css('width', 500);
							}
						}
					}).tooltip('show');
				}
			});
		}
	}
};
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * 
 * $("#dg").datagrid('tooltip'); 所有列
 * 
 * $("#dg").datagrid('tooltip',['productid','listprice']); 指定列
 * 
 * @author 夏悸
 */
$.extend($.fn.datagrid.methods, gridTooltipOptions);

/**
 * Treegrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * 
 * $("#dg").treegrid('tooltip'); 所有列
 * 
 * $("#dg").treegrid('tooltip',['productid','listprice']); 指定列
 * 
 * @author 夏悸
 */
$.extend($.fn.treegrid.methods, gridTooltipOptions);

/**
 * 两次密码必须相等
 * @author 杨锦
 */
$.extend($.fn.validatebox.defaults.rules, {
    /*必须和某个字段相等*/
    equalTo: {
        validator: function (value, param) {
            return $(param[0]).val() == value;
        },
        message: '字段不匹配'
    },
	checkUserName: {
	    validator: function (value, param) {
	    	return $(param[0]).val() == "1" ? true : false;
	    },
	    message: '用户名已存在'
	},
	IDCard: {
	    validator: function (value, param) {
	    	var reg=/\d{15}(\d\d[0-9xX])?/;
	        if(reg.test(value)){ return true; } else { return false; }
	    },
	    message: '身份证号不正确'
	},
	integer: {
		validator: function (value, param) {
	    	var reg=/^[0-9]*$/;
	        if(reg.test(value)){ return true; } else { return false; }
	    },
	    message: '请填写整数'
	},
	clarity:{
		validator: function (value, param) {
			var reg=/^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\b/;
	        if(reg.test(value)){ return true; } else { return false; }
	    },
	    message: '请填写0-255的整数'
	},
	mobile: {
	    validator: function (value, param) {
	    	var reg=/1\d{10}/;
	        if(reg.test(value)){ return true; } else { return false; }
	    },
	    message: '手机号不正确'
	},
	//国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    phone : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:020-88888888'
	},
	email:{
		validator : function(value){
			return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
		},
		message : '请输入有效的电子邮件账号(例：abc@126.com)'
	},
	floatOrInt : {// 验证是否为小数或整数		
        validator : function(value) {
            return /^(\d{1,10}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);		
        },		
        message : '请输入数字，并保证格式正确'		
    }
});