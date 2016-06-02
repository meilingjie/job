(function($) {
	$.fn.form_err = function(errors) {
		var len = errors.length;
		$(this).form_success();
		for (var i = 0; i < len; i++) {
			var e = errors[i];
			var estr = e.field;
			estr = estr.replace(/\./g, "\\.");
			if ($("#" + estr, $(this)).get(0).tagName.toLowerCase() != 'select') {
				$("#" + estr, $(this)).addClass("admin-field-error");
				if ($("#" + estr, $(this)).next(".admin-field-errortext") != null) {
					$("#" + estr, $(this)).next(".admin-field-errortext")
							.remove();
				}
				$("#" + estr, $(this)).after(
						"<span class=\"admin-field-errortext\">" + e.message
								+ "</span>");
			} else {
				$("#" + estr, $(this)).next(".ui-select").addClass(
						"admin-field-error");
				if ($("#" + estr, $(this)).next(".ui-select").next(
						".admin-field-errortext") != null) {
					$("#" + estr, $(this)).next(".ui-select").next(
							".admin-field-errortext").remove();
				}
				$("#" + estr, $(this)).next(".ui-select").after(
						"<span class=\"admin-field-errortext\">" + e.message
								+ "</span>");

			}

		}
	};

	$.fn.form_success = function() {
		return $(".admin-field-error", $(this)).each(function() {
			$(this).removeClass("admin-field-error");
			if ($(this).next(".admin-field-errortext") != null) {
				$(this).next(".admin-field-errortext").remove();
			}
		});
		// return this.each(function() {
		//			
		// if ($(this).hasClass("admin-field-error")) {
		// $(this).removeClass("admin-field-error");
		// }
		// });
	};
})(jQuery);
