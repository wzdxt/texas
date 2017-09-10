package cn.easyproject.easyocr;
/**
 * ORC识别异常类
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @version 3.0.3
 * 
 */
public class EasyOCRException extends RuntimeException {
	private static final long serialVersionUID = -1719360158193519744L;
	
	public EasyOCRException(String msg) {
		super(msg);
	}
	
	public EasyOCRException(String msg,Throwable throwable) {
		super(msg, throwable);
	}
}
