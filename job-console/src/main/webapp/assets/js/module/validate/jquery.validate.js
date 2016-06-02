/*!
 * jquery.validate.js
 * @author ydr.me
 * @version 1.0
 */



/**
 * 表单验证插件
 * v1.0
 * 2014年3月11日23:35:27
 * 初始构造
 * TIPS:
 * HTML5中，表单的type限制之后，如果填写数据不符合type规范，则获取的值为空
 * 因此建议修改type为text，添加data-type代替
 * 如果是跨域请求，强烈建议在ajax中添加timeout参数
 * 脚本不会判断当前表单是否正在进行验证，请自行判断控制
 * TODO:
 * 没有严格判断 week 格式字符串
 */




/**
 * 表单各种属性参数设置
 * 在脚本处理 type=email 或 url 等项目时，无法读取非法的数据，因此
 * 需要将type修改为 text ，然后添加 data-type=email 或 ur
 *
 * input:text
 * textarea
 * 属性: required 必填项
 * 属性: pattern 正则
 * 属性: maxlength 最大长度
 * 属性: data-msg{required,pattern,maxlength} 消息json字符串
 *
 *
 * input:password
 * 属性: required 必填项
 * 属性: pattern 正则
 * 属性: maxlength 最大长度
 * 属性: data-equal 与之相等的表单项目id
 * 属性: data-msg{required,pattern,maxlength,equal} 消息json字符串
 *
 *
 * input:number
 * 属性: required 必填项
 * 属性: min 最小值
 * 属性: max 最大值
 * 属性: step 公倍数
 * 属性: data-msg{required,min,max,step,type} 消息json字符串
 *
 *
 * input:email
 * input:url
 * input:date
 * input:datetime
 * input:datetime-local
 * input:time
 * input:week
 * 属性: required 必填项
 * 属性: data-msg{required,type} 消息json字符串
 *
 *
 * single select
 * 属性: required 必选项
 * 属性: data-placeholder 占位值，默认""
 * 属性: data-msg{required} 消息json字符串
 *
 *
 * multiple select
 * 属性: data-placeholder 占位值，默认""
 * 属性: data-least 必须最小数量，默认无限制
 * 属性: data-most 必须最大数量，默认无限制
 * 属性: data-msg{least,most} 消息json字符串
 *
 *
 * input:checkbox
 * 属性: data-least 必须最小数量，默认无限制
 * 属性: data-most 必须最大数量，默认无限制
 * 属性: data-msg{least,most} 消息json字符串
 *
 *
 * input:radio
 * 属性: required 必选项
 * 属性: data-msg{required} 消息json字符串
 *
 *
 * 所有表单的ajax需要额外处理
 */


(function ($, undefined) {
	var emptyFn = function () {},
		isHTML5 = !! ('oninvalid' in document.createElement('input')),
		defaults = {
			// 是否跳过验证失败
			// false(默认): 出现*本地验证*错误就停止继续验证
			// true: 一直*本地验证*到结尾，无论错误与否
			isSkipInvalid: !1,


			// 是否自动提交，只在全部验证正确之后
			// true（默认）：自动调用 $.serizlize
			// false：不会自动提交
			isAutoSubmit: !0,


			// 自定义本地验证规则
			// checkbox 和 radio 的 id 填写第一个的id值
			// 示例：
			// {
			// 	  // 每个表单项目可以自定义多个按顺序的本地验证规则
			//    '表单项目id':[
			//    	 function(){
			//    	    // 这里必须按照格式来返回json对象
			//    	    // 一些的数据处理与判断后，返回
			//    	    return {
			//    	        // 状态，true表示验证通过，false表示验证失败
			//    	        status: false,
			//    	        // 消息，表示表单验证失败的消息，验证通过是不会有消息返回的
			//    	        msg: '该用户名已经被注册了，请尝试使用其他用户名'
			//    	    };
			//    	 }
			//    ]
			// }
			localRules: {},


			// 自定义远程验证方法
			// checkbox 和 radio 的 id 填写第一个的id值
			// *注意*: ajax 验证失败则不会继续该表单项目的剩下的ajax验证
			// 并且ajax验证都放在了本地验证正确之后才进行
			// 参数设置按照 $.ajax 方法来
			// 官方API: http://api.jquery.com/jQuery.ajax/
			// 示例：
			// {
			//    '表单项目id':[
			//     // 每个表单项目可能有1个多个ajax验证，按照数组顺序来
			//     {
			//    	   // 可以是 post 或 get
			//    	   type: 'post',
			//    	   // 可以说 json script text jsonp
			//    	   dataType: 'json',
			//    	   // 在跨域请求的时候，强烈建议添加 timeout 属性，
			//    	   // 否则在遇到403或其他错误时不能触发success 或 error 回调
			//    	   timeout: 5000,
			//    	   // 自行组织data数据
			//    	   data:{},
			//    	   // 只接受处理 success 和 error 两个回调
			//    	   success: function(){
			//    	        // 这里必须按照格式来返回json对象
			//    	        // 一些的数据处理与判断后，返回
			//    	        return {
			//    	            // 状态，true表示验证通过，false表示验证失败
			//    	            status: false,
			//    	            // 消息，表示表单验证失败的消息，验证通过是不会有消息返回的
			//    	            msg: '该用户名已经被注册了，请尝试使用其他用户名'
			//    	        };
			//    	   },
			//    	   // error 的设置参考 success
			//    	   error: function(){}
			//    }]
			// };
			remoteRules: {},


			// 验证正确时回调
			// this: 当前表单项目的js对象
			onvalid: emptyFn,

			// 验证错误时回调
			// this: 当前表单项目的js对象
			// 参数1: 错误消息
			oninvalid: emptyFn,

			// 聚焦时回调
			// this: 当前表单项目的js对象
			onfocus: emptyFn,

			// 正在远程验证
			// this: 当前表单项目的js对象
			onremote: emptyFn,

			// 全部验证都正确
			// this: null
			onsuccess: emptyFn,

			// 全部验证完成，包括正确和错误
			// this: null
			oncomplete: emptyFn,

			// 表单提交后回调
			onsubmit: emptyFn
		};


	$.fn.validate = function (settings) {
		var $form = this.eq(0),
			items = _parseFormItems($form),
			itemsLength = items.length,
			ajaxCurrent = 0,
			ajaxLength = 0,
			itemCurrent = 0,
			itemNopass = 0,
			options = $.extend({}, defaults, settings);


		// 验证队列
		! function _queue() {
			var item = items[itemCurrent],
				// 自定义本地验证
				localRules = (options.localRules[item.id] || []),
				localIndex = 0,
				localLength = localRules.length,
				localTemp,
				// 自定义 ajax 长度
				ajaxLength = (options.remoteRules[item.id] || []).length,
				// 错误消息
				msg = item.msg;


			options.onfocus.call(item.elem);

			// 验证 required
			if (item.required && (item.value === '' || item.value === undefined)) {
				return _error(msg.required);
			}


			// 验证 maxlength
			if (item.maxlength && item.value.length < item.maxlength) {
				return _error(msg.maxlength);
			}


			// 验证 pattern
			if (item.reg && !item.reg.test(item.value)) {
				return _error(msg.pattern);
			}


			// 验证 type
			if (!_validateType(item.type, item.value)) {
				return _error(msg.type);
			}


			// 验证 number
			if (item.type === 'number' && item.value !== undefined) {
				if (item.value < item.min) return _error(msg.min);
				if (item.value > item.max) return _error(msg.max);
				if (!item.value % item.step) return _error(msg.step);
			}


			// 验证 equal
			if (item.$equal.length && item.value !== item.$equal.val()) {
				return _error(msg.equal);
			}


			// 验证checkbox
			// 验证 multiple select
			if (item.type === 'checkbox' || item.type === 'select' && item.multiple) {
				if (item.least && item.value.length < item.least) return _error(msg.least);
				if (item.most && item.value.length > item.most) return _error(msg.most);
			}



			// 自定义本地验证
			for (localIndex in localRules) {
				localTemp = localRules[localIndex].call(item.elem);
				if (!localTemp.status) return _error(localTemp.msg);
			}


			// 自定义 ajax 验证
			if (ajaxLength) {
				! function _recursion() {
					_ajax(function (json) {
						// 正确才继续
						if (json.status) {
							ajaxCurrent++;
							if (ajaxCurrent === ajaxLength) {
								_success();
							} else _recursion();
						}
						// 出错就停止验证，减少网络开销
						else return _error(json.msg);
					});
				}();
			} else _success();



			// ajax请求

			function _ajax(callback) {
				var ajaxOtions = options.remoteRules[item.id][ajaxCurrent],
					_ajaxOptions = $.extend({}, ajaxOtions);
				_ajaxOptions.success = _ajaxOptions.error = _ajaxOptions.complete = null;
				options.onremote.call(item.elem);
				$.ajax(_ajaxOptions).done(function () {
					callback(ajaxOtions.success.apply(item.elem, arguments));
				}).fail(function () {
					callback(ajaxOtions.error.apply(item.elem, arguments));
				});
			}


			// 错误处理

			function _error(error) {
				itemNopass++;
				itemCurrent++;
				if (options.isSkipInvalid && itemsLength !== itemCurrent) {
					_queue();
				} else if (!options.isSkipInvalid) {
					options.oncomplete.call($form[0]);
				}
				options.oninvalid.call(item.elem, error || '不符合要求');
			}


			// 所有验证完毕 => 正确处理

			function _success() {
				ajaxCurrent = 0;
				ajaxLength = 0;
				itemCurrent++;
				options.onvalid.call(item.elem);
				if (itemCurrent === itemsLength) {
					options.oncomplete();
					if (!itemNopass) {
						options.onsuccess.call($form[0]);
						if (options.isAutoSubmit) _submit();
					}
				} else {
					_queue();
				}
			}
		}();



		// 表单提交

		function _submit() {
			$.ajax({
				url: $form.attr('action') || location.href,
				type: $form.attr('method') || 'get',
				data: $form.serialize()
			}).always(options.onsubmit);
		}
	};

	$.fn.validate.defaults = defaults;
	return this;





	/**
	 * 解析表单为一个表单项目集合
	 * @param  {Object} 表单对象
	 * @return {Array}  规则数组
	 * @version 1.0
	 * 2014年3月14日16:24:47
	 */

	function _parseFormItems($form) {
		var $items,
			// 已经加入规则的项目
			has = {},
			rules = [];

		$form.find('input,select,textarea').each(function () {
			var $item = $(this),
				tagName = $item[0].tagName.toLowerCase(),
				value = $item.val(),
				name = $item.attr('name'),
				pattern = $item.attr('pattern'),
				multiple = $item.prop('multiple'),
				equal = $item.data('equal'),
				placeholder = $item.data('placeholder') || '',
				type = $item.data('type') || $item.attr('type'),
				rule = {};


			if (!has[name]) {
				has[name] = 1;

				if (tagName === 'select' || tagName === 'textarea') type = tagName;

				// checkbox
				if (type === 'checkbox') {
					value = [];
					$('[name=' + name + ']:checked', $form).each(function () {
						value.push(this.value);
					});
				}
				// radio
				else if (type === 'radio') {
					value = $('[name=' + name + ']:checked', $form).val();
				}
				// number
				else if (type === 'number') {
					value = _formatNumber(value);
				}
				// select
				// single ""
				// multiple null
				// multiple ["1","2"]
				// 单选时去除默认项
				else if (type === 'select' && !multiple && value === placeholder) {
					value = undefined;
				}
				// multiple null
				else if (type === 'select' && value === null) {
					value = [];
				}


				rule = {
					elem: $item[0],
					id: $item.attr('id'),
					name: name,
					type: type,
					value: value,
					maxlength: _formatNumber($item.attr('maxlength')),
					pattern: pattern,
					reg: pattern ? new RegExp(pattern) : undefined,
					min: _formatNumber($item.attr('min')),
					max: _formatNumber($item.attr('max')),
					step: _formatNumber($item.attr('step')),
					least: _formatNumber($item.data('least')),
					most: _formatNumber($item.data('most')),
					required: $item.prop('required'),
					multiple: multiple,
					$equal: equal ? $('#' + $item.data('equal')) : [],
					msg: $item.data('msg') || {}
				};
				rules.push(rule);
			}
		});
		return rules;
	}



	/**
	 * 格式化数字，非数字转换为 undefined
	 * @param  {*} 任何
	 * @param  {*} 默认值
	 * @return {Number/undefined}
	 * @version 1.0
	 * 2014年3月13日16:05:23
	 */

	function _formatNumber(number, defaultNumber) {
		number = number * 1;
		return isNaN(number) ? defaultNumber : number;
	}



	/**
	 * 验证表单type类型的字符串
	 * @param  {String} 格式
	 * @param  {String} 字符串
	 * @return {Boolean} 真假
	 * @version 1.0
	 * 2014年3月11日22:16:44
	 */

	function _validateType(type, value) {
		switch (type) {
		case 'url':
			return /^(http|ftp|https|file):\/\/([\w\-]+\.)+[\w\-]+(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?$/i.test(value)
			// return /^(http|ftp|https|file):\/\/(\w(\:\w)?@)?([\w-]+\.)*?([a-z\d-]+\.[a-z]{2,6}(\.[a-z]{2})?(\:\d{2,6})?)((\/[^?#<>\/\\*":]*)+(\?[^#]*)?(#.*)?)?$/.test(value);
		case 'email':
			return /^[\w!\#\$%&'*+\/=?^`{|}~.\-]+@([\w-]+\.)+([a-z]{2,6}|xn--[a-z\d]+)$/i.test(value);
			// return /^[\w!\#\$%&'*+\/=?^`{|}~.\-]+@([\w-]+\.)+(com|net|org|hk|cn|gov|biz|info|cc|tv|mobi|name|asia|tw|sh|ac|io|tm|travel|ws|us|sc|mn|ag|vc|la|bz|in|cm|co|tel|me|pro|pw|xn--[a-zA-Z\d]+)$/i.test(value);
		case 'number':
			return /^\d+$/.test(value);
		case 'date':
		case 'month':
		case 'week':
		case 'time':
		case 'datetime':
		case 'datetime-local':
			return _checkDate(type, value);
		case 'color':
			// #0000ff
			return /^#[\da-f]{6}$/.test(value);
		}

		return !0;
	}


	/**
	 * 验证date格式字符串是否合法
	 * @param  {String} 格式
	 * @param  {String} 字符串
	 * @return {Boolean} 真假
	 * @version 1.0
	 * 2014年3月11日22:16:44
	 */

	function _checkDate(type, value) {
		var
		reg = {
			// 2014-03-10
			'date': /^(\d{4})-(\d{2})-(\d{2})()()$/,
			// 2014-03
			'month': /^(\d{4})-(\d{2})()()()$/,
			// 2014-W14
			'week': /^(\d{4})()()()()-W(\d{2})$/,
			// 03:03
			'time': /^()()()(\d{2}):(\d{2})$/,
			// 2014-03-10T03:03
			'datetime': /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2})$/,
			// 2014-03-11T21:59
			'datetime-local': /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2})$/
		}, D, year, gYear, month, gMonth, date, gDate, hours, gHours, minutes, gMinutes;
		if (reg[type].test(value)) {
			year = 1 * RegExp.$1 || 1;
			month = 1 * RegExp.$2 || 1;
			date = 1 * RegExp.$3 || 1;
			hours = 1 * RegExp.$4 || 1;
			minutes = 1 * RegExp.$5 || 1;
			D = new Date(year, month - 1, date, hours, minutes);
			gYear = D.getFullYear();
			gMonth = D.getMonth() + 1;
			gDate = D.getDate();
			gHours = D.getHours();
			gMinutes = D.getMinutes();
			if (type === 'date') {
				return gYear === year && gMonth === month && date === gDate;
			} else if (type === 'month') {
				return gYear === year && gMonth === month;
			} else if (type === 'week') {
				return gYear === year;
			} else if (type === 'time') {
				return gHours === hours && gMinutes === minutes;
			} else if (type === 'datetime' || type === 'datetime-local') {
				return gYear === year && gMonth === month && date === gDate && gHours === hours && gMinutes === minutes;
			}
		} else return !1;
	}

})(jQuery);
