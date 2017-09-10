package cn.easyproject.easyocr;

/**
 * 验证码图片过滤清理类型枚举，必须实现Type接口 <br>
 * 可选择7种验证码图片清理类型： <br>
 * - 普通验证码图片 <br>
 * - 带干扰线的图片 <br>
 * - 点状验证码图片 <br>
 * - 白色文字,纯色背景验证码图片 <br>
 * - 空心文字验证码图片 <br>
 * - 无特殊干扰的普通图片清晰化 <br>
 * - 不做任何处理 <br>
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @version 3.0.3
 * 
 */
public enum ImageType implements Type {
	/**
	 * 图片类型：普通验证码图片
	 */
	CAPTCHA_NORMAL("changeToGrey" + "#changeToBinarization"),
	/**
	 * 图片类型：带干扰线的图片
	 */
	CAPTCHA_INTERFERENCE_LINE("#changeToBinarization" + "#cleanCrossOrphan"
			+ "#cleanCrossOrphan" + "#changeToBinarization"
			+ "#changeToGrayByAvgColor" + "#changeToBlackWhiteImage"),
	/**
	 * 图片类型：点状验证码图片
	 */
	CAPTCHA_SPOT("changeToGrayByAvgColor" + "#changeBrighten"
			+ "#changeToBlackWhiteImage"),
	/**
	 * 图片类型：白色文字，纯色背景验证码图片
	 */
	CAPTCHA_WHITE_CHAR("reverseGray" + "#changeToGrey"
			+ "#changeToBinarization"),
	/**
	 * 图片类型：空心文字验证码图片
	 */
	// 空心处理1.转换为包围字
	// 空心处理2.去掉包围，得到文字图片
	// 空心处理3.文字
	// 空心处理4.转为白底黑子
	CAPTCHA_HOLLOW_CHAR("getBlur" + "#changeToBlackWhiteImage"
			+ "#changeBrighten" + "#reverseGray" + "," + "cleanPadding" + ","
			+ "getBlur" + "#changeToBlackWhiteImage" + "#reverseGray"
			+ "#changeBrighten" + "," + "reverseGray"),
	/**
	 * 无特殊干扰的普通图片清晰化
	 */
	CLEAR("convertImageToGrayscale"),
	/**
	 * 不做任何处理
	 */
	NONE(""),
	SCORE("#,,###changeBrighten,changeToBinarization#"),
	TEAM("#,,##reverseGray"
			+ "#,###changeBrighten#"
			+ "changeToBinarization#"
			+ "reverseGray#"
			+ "changeBrighten#"
			+ "reverseGray#"
			+ "changeToGrayByAvgColor#"
			+ "#changeToGrey#"),
	PLAYER("#,"
//			+ ",##changeToGrey#"
//			+ ",##reverseGray#"
//			+ "#,###changeBrighten#"
//			+ "changeToBinarization#"
//			+ "reverseGray#"
//			+ "changeBrighten#"
//			+ "reverseGray#"
//			+ "changeToGrayByAvgColor#"
			+ "#changeToGrey#"
			+ "changeToBinarization#"
//			+ "#changeToBlackWhiteImage#"
			);

	private final String cleanMethods;

	private ImageType(String cleanMethods) {
		this.cleanMethods = cleanMethods;
	}

	/**
	 * 返回方法调用结果
	 * @return 清理方法列表
	 */
	public String getCleanMethods() {
		return this.cleanMethods;
	}

}
