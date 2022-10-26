package com.utils.common.enums;

import org.springframework.util.ObjectUtils;

/**
 * @Author Wong Junwei
 * @Date 2022/8/1
 * @Description 枚举
 */
public enum SerializerEnums {

	/**
	 * 脱敏
	 */
	PHONE("手机号", "(\\d{3})\\d{4}(\\d{4})", "$1****$2"),
	ID_CARD_15("身份证15位", "(\\d{6})\\d{7}(\\d{2})", "$1****$2"),
	ID_CARD_18("身份证18位", "(\\d{6})\\d{10}(\\d{2})", "$1****$2"),


	/**
	 * 格式化
	 */
	FORMAT_AREA_NAME("管辖区域格式化", "[^\u4e00-\u9fa5]", ""),
	;
	private final String code;
	private final String regexp;
	private final String expression;

	SerializerEnums(String code, String regexp, String expression) {
		this.code =  code;
		this.regexp = regexp;
		this.expression = expression;
	}


	public String getCode() {
		return code;
	}

	public String getRegexp() {
		return regexp;
	}

	public String getExpression() {
		return expression;
	}

	/**
	 * 脱敏替换
	 * @param str
	 * @return
	 */
	public String replaceAll(String str) {
		if (ObjectUtils.isEmpty(str)) {
			return str;
		}
		return str.replaceAll(regexp, expression);
	}

}
