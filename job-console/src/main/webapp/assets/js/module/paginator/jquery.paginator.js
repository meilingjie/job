// 分页处理，方便以后扩展 
(function($) {

	$.mypage = function(selector, target, handler) {
		$(selector).off("click", target).on("click", target, function() {
			var pageSize = $(this).attr("pageSize");
			var curPage = $(this).attr("curPage");
			if (curPage == "goto") {
				curPage = $(this).prev().find("input").val();
			}
			
			var p =  /^\d+$/;
			if (!p.test(curPage)){
				curPage = 1;
			}
			
			handler(curPage, pageSize);
		});
	};

})(jQuery);