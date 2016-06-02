(function($) {
	$.fn.drag = function() {
		var thisDiv = $(this);
		$(".ui-box-head", $(thisDiv)).bind("mousedown", function(event) {
			/* 获取需要拖动节点的坐标 */
			var offset_x = $(this).offset().left;// x坐标
			var offset_y = $(this).offset().top;// y坐标
			/* 获取当前鼠标的坐标 */
			var mouse_x = event.pageX;
			var mouse_y = event.pageY;
			/* 绑定拖动事件 */
			/* 由于拖动时，可能鼠标会移出元素，所以应该使用全局（document）元素 */

			$(document).bind("mousemove", function(ev) {
				// 计算鼠标移动了的位置
				var _x = ev.pageX - mouse_x;
				var _y = ev.pageY - mouse_y;
				// 设置移动后的元素坐标
				var now_x = (offset_x + _x) + "px";
				var now_y = (offset_y + _y) + "px";
				// 改变目标元素的位置
				$(thisDiv).css({
					top : now_y,
					left : now_x
				});
			});

		});
		$(document).bind("mouseup", function() {
			$(this).unbind("mousemove");
		});
	};
	$.fn.dialog = function(opts) {
		var defaultOpt = {
			title : "提示框"
		};
		var extendOpt = $.extend(defaultOpt, opts);
		var $dom = dialogFunc(extendOpt);
		$($dom).drag();
		$(this).after($dom);
	};
	$.fn.alert = function(opts) {
		var defaultOpt = {
			title : "提示框"
		};
		var extendOpt = $.extend(defaultOpt, opts);
		var $dom = alertFunc(extendOpt);
		$(this).after($dom);
		$($dom).drag();

	};
	$.fn.confirm = function(opts, funcYes, funcNo) {
		var defaultOpt = {
			title : "确认框"
		};
		var extendOpt = $.extend(defaultOpt, opts);
		var $dom = confirmFunc(extendOpt);
		$(this).after($dom);
		$($dom).drag();
		$("#clickYes", $dom).click(function() {
			funcYes();
			$($dom).remove();
		});
		$("#clickNo", $dom).click(function() {
			funcNo();
			$($dom).remove();
		});
	};
	$.fn.tip = function(opts) {
		var defaultOpt = {
			title : "确认框",
			content : "示例",
			time : 1000
		};
		var extendOpt = $.extend(defaultOpt, opts);
		var $dom = tipFunc(extendOpt);
		$($dom).drag();
		$(this).after($dom);
		setTimeout(function() {
			$($dom).remove();
		}, extendOpt.time);
	};
	function dialogFunc(extendOpt) {
		var idStr = Math.random();
		var domStr = "";
		domStr += contentFunc(extendOpt);
		domStr = showBoxer(idStr, extendOpt, domStr);
		return $(domStr);
	}
	function tipFunc(extendOpt) {
		var idStr = Math.random();
		var domStr = "";
		domStr += contentFunc(extendOpt);
		domStr = showBoxer(idStr, extendOpt, domStr);
		return $(domStr);
	}
	function confirmFunc(extendOpt) {
		var idStr = Math.random();
		var domStr = "";
		domStr += contentFunc(extendOpt);
		domStr += '<div class="ui-box-content" style="text-align: center;">'
				+ '<div id="clickYes" class="ui-button ui-button-lnormal">是</div>'
				+ '<div id="clickNo" class="ui-button ui-button-lnormal" style="margin-left:5px;">否</div></div>';
		domStr = showBoxer(idStr, extendOpt, domStr);
		return $(domStr);
	}
	function alertFunc(extendOpt) {
		var idStr = Math.random();
		var domStr = "";
		domStr += contentFunc(extendOpt);
		domStr += '<div class="ui-box-content" style="text-align: center;">'
				+ '<div onclick="javscript:document.getElementById('
				+ idStr
				+ ').remove();" class="ui-button ui-button-lnormal">确定</div></div>';
		domStr = showBoxer(idStr, extendOpt, domStr);
		return $(domStr);
	}
	function contentFunc(extendOpt) {
		var cstr = "";
		if (extendOpt.text != null) {
			cstr = '<div class="ui-box-content">' + extendOpt.text + '</div>';
			return cstr;
		} else if (extendOpt.content != null) {
			cstr = extendOpt.content;
			return cstr;
		} else if (extendOpt.url != null && extendOpt.url != "") {
			$.ajax({
				url : extendOpt.url,
				async : false,
				success : function(data) {
					cstr = data;
				}
			});
			return cstr;
		}
		return cstr;
	}
	function showBoxer(idStr, extendOpt, thisContent) {
		var positionX = document.documentElement.clientWidth / 2
				- (extendOpt.width == null ? 0 : extendOpt.width) / 2;
		var heightVal = extendOpt.height == null ? 'auto' : extendOpt.height
				+ 'px';
		var widthVal = extendOpt.width == null ? 'auto' : extendOpt.width
				+ 'px';
		var domStr = '<div id="'
				+ idStr
				+ '" class="ui-box" style="height:'
				+ heightVal
				+ ';width:'
				+ widthVal
				+ ';position:absolute;left:'
				+ positionX
				+ 'px;top:100px;z-index:9999;overflow:hidden;">'
				+ '<div class="ui-box-head" style="cursor:pointer;">'
				+ '<h3 class="ui-box-head-title">'
				+ extendOpt.title
				+ '</h3>'
				+ '<span class="ui-box-head-text"></span>'
				+ '<i class="ui-box-head-more ui-box-head-more-img iconfont">&#xf01a4;</i>'
				+ '</div><div class="ui-box-container" style="height:'
				+ extendOpt.height + 'px;width:' + extendOpt.width + 'px;">'
				+ thisContent + '</div></div>';
		return domStr;
	}
})(jQuery);