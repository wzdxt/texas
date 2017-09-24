package cn.easyproject.easyocr;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ORC识别核心工具类<br>
 * 基于Tesseract ocr引擎。<br>
 * 提供：<br>
 * - 文件直接识别方法：discern、discernToFile、discernToFileAndGet<br>
 * - 图片清理并识别方法：discernAndAutoCleanImage、discernToFileAndAutoCleanImage、discernToFileAndGetAndAutoCleanImage<br> 
 * 
 * 通过tesseractOptions可以设置tesseract命令执行参数，例如：设置中文识别<br>
 * EasyOCR e=new EasyOCR();<br>
 * e.setTesseractOptions(EasyOCR.OPTION_LANG_CHI_SIM);<br>
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @version 3.0.3
 * 
 */
@Slf4j
public class EasyOCR {

	/**
	 * tesseract执行命令的位置，使用前需要提前设置 如果配置了path环境变量，则可以不用设置<br>
	 * 例如："D:\\Program Files\\Tesseract-OCR\\tesseract";
	 */
	private String tesseractPath = "tesseract";
	/**
	 * tesseract命令执行参数,可从TesseractOCR.OPTION_LANG_...常量选择<br>
	 * 例如：设置中文识别<br>
	 * EasyOCR e=new EasyOCR();<br>
	 * e.setTesseractOptions(EasyOCR.OPTION_LANG_CHI_SIM);
	 */
	private String tesseractOptions = "";
	/**
	 * 英文识别命令执行参数常量
	 */
	public static final String OPTION_LANG_ENG = "-l eng";

	/**
	 * 中文识别命令执行参数常量
	 */
	public static final String OPTION_LANG_CHI_SIM = "-l chi_sim";
	/**
	 * 自动清理图片时，图片宽度放大比例，某些图片形变后识别率可以提高，默认值为1，不形变
	 */
	private double autoCleanImageWidthRatio = 1;

	/**
	 * 自动清理图片时，图片高度放大比例，某些图片形变后识别率可以提高，默认值为1，不形变
	 */
	private double autoCleanImageHeightRatio = 1;
	/**
	 * 图片顺时针旋转角度，默认为0，不旋转
	 */
	private int autoCleanDegrees = 0;
	/**
	 * 清理图片时，默认的图片清理类型，默认为ImageType.NONE
	 */
	private Type autoCleanImageType = ImageType.NONE;

	/**
	 * 默认使用如下配置创建EasyOCR实例<br>
	 * - path环境变量配置的tesseract命令的位置<br>
	 * - 英文识别参数<br>
	 */
	public EasyOCR() {
	}

	/**
	 * 按参数创建EasyOCR实例<br>
	 * - 手动指定的tesseract命令的位置<br>
	 * - 默认英文识别参数<br>
	 *
	 * @param tesseractPath tesseract执行命令的位置（如果配置了path环境变量，则可以不用设置）
	 */
	public EasyOCR(String tesseractPath) {
		this.tesseractPath = tesseractPath;
	}

	/**
	 * 按参数创建EasyOCR实例<br>
	 * - 手动指定的tesseract命令的位置<br>
	 * - 手动指定的识别参数<br>
	 * @param tesseractPath tesseract执行命令的位置，如果配置了path环境变量，则可以不用设置
	 * @param tesseractOptions tesseract命令执行参数
	 */
	public EasyOCR(String tesseractPath, String tesseractOptions) {
		this.tesseractPath = tesseractPath;
		this.tesseractOptions = tesseractOptions;
	}

	/**
	 * 识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage 要识别的图片
	 *
	 * @return 读取到的代码
	 */
	public String discern(File fromImage) {
		String toFile = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		try {
			return discernToFileAndGet(fromImage.getCanonicalPath(), toFile,
					true);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            要识别的图片
	 *
	 * @return 读取到的代码
	 */
	public String discern(FileInputStream fromImage) {
		String toFile = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();

		String fromFile = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		String res = null;
		// 输入流转为临时操作文件
		if (inputStreamToFile(fromImage, fromFile)) {
			res = discernToFileAndGet(fromFile, toFile, true); // 获得内容，并删除toFile文件
		}
		new File(fromFile).delete(); // 删除输入流对应的临时操作文件
		return res;
	}

	/**
	 * 识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            要识别的图片
	 *
	 * @return 读取到的代码
	 */
	public String discern(String fromImage) {
		String toFile = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		// 获得内容，并删除toText文件
		return discernToFileAndGet(fromImage, toFile, true);
	}

	/**
	 * 按指定形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		return discernAndAutoCleanImage(fromImage, this.autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按图片类型，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage, Type imageType) {

		return discernAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {

		return discernAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 按图片类型、形变比例、顺时针旋转角度，，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		try {
			String fromPath = fromImage.getCanonicalPath();
			return discernAndAutoCleanImage(fromPath, imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage, Type imageType,
			int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(File fromImage, int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		return discernAndAutoCleanImage(fromImage, this.autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				autoCleanDegrees);

	}

	/**
	 * 按图片类型，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
			Type imageType) {

		return discernAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
										   Type imageType, double autoCleanImageWidthRatio,
										   double autoCleanImageHeightRatio) {

		return discernAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型、形变比例、顺时针旋转角度，，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
										   Type imageType, double autoCleanImageWidthRatio,
										   double autoCleanImageHeightRatio, int autoCleanDegrees) {

		String fromPath = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		String res = null;
		// 输入流转为临时操作文件
		if (inputStreamToFile(fromImage, fromPath)) {
			res = discernAndAutoCleanImage(fromPath, imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);
		}
		new File(fromPath).delete(); // 删除输入流对应的临时操作文件
		return res;
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
										   Type imageType, int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(InputStream fromImage,
			int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {

		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按图片类型，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage, Type imageType) {
		return discernAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage,
										   Type imageType, double autoCleanImageWidthRatio,
										   double autoCleanImageHeightRatio) {
		return discernAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按图片类型、形变比例、顺时针旋转角度，，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage,
										   Type imageType, double autoCleanImageWidthRatio,
										   double autoCleanImageHeightRatio, int autoCleanDegrees) {
		String res = "";
		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片
		// 临时图片文件地址，保存自动清理后生成的图片
		String ex = "";
		int lastDot = fromImage.lastIndexOf(".");
		if (lastDot != -1) {
			ex = fromImage.substring(lastDot);
		}

		try {
			// 如果是http://、ftp://开头的网络图片，将网络图片转为临时图片操作
			if (fromImage.startsWith("http://")
					|| fromImage.startsWith("ftp://")) {
				urlFlag = true;
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString() + ex;
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return res;
				}
			}

			String tempCleanImage = System.getProperty("java.io.tmpdir") + File.separator
					+ UUID.randomUUID().toString() + ex;

			// 生成自动清理后的新图片
			ImageClean imageClean = new ImageClean(imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);
			imageClean.cleanImage(fromImage, tempCleanImage);

			res = discern(tempCleanImage); // 识别自动清理后的图片内容

			new File(tempCleanImage).delete(); // 删除自动清理后的临时图片
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}

		return res;
	}

	/**
	 * 按图片类型和形变比例，清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param imageType
	 *            图片类型枚举
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage,
										   Type imageType, int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 清理识别图片内容，并返回读取到的内容字符串
	 *
	 * @param fromImage
	 *            原始图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 识别内容
	 */
	public String discernAndAutoCleanImage(String fromImage,
			int autoCleanDegrees) {
		return discernAndAutoCleanImage(fromImage, autoCleanImageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @return 生成是否完成
	 */
	public boolean discernToFile(File fromImage) {

		String toFile;
		try {
			toFile = fromImage.getCanonicalPath();
			int lastIndex = toFile.lastIndexOf(".");
			if (lastIndex != -1) {
				toFile = toFile.substring(0, lastIndex);
			}
			return discernToFile(fromImage.getCanonicalPath(), toFile);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存识别内容的文件名，默认会自动添加.txt后缀
	 * @return 生成是否完成
	 */
	public boolean discernToFile(File fromImage, String toFile) {
		try {
			return discernToFile(fromImage.getCanonicalPath(), toFile);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存识别内容的文件名，默认会自动添加.txt后缀
	 * @return 生成是否完成
	 */
	public boolean discernToFile(FileInputStream fromImage, String toFile) {
		String fromPath = System.getProperty("java.io.tmpdir") + File.separator
				+ UUID.randomUUID().toString();
		boolean res = false;
		// 输入流转为临时操作文件
		if (inputStreamToFile(fromImage, fromPath)) {
			res = discernToFile(fromPath, toFile); // 获得内容，并删除toFile文件
		}
		new File(fromPath).delete(); // 删除输入流对应的临时操作文件
		return res;
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @return 生成是否完成
	 */
	public boolean discernToFile(String fromImage) {

		String toFile = fromImage;
		int lastIndex = toFile.lastIndexOf(".");
		if (lastIndex != -1) {
			toFile = toFile.substring(0, lastIndex);
		}

		if (fromImage.startsWith("http://") || fromImage.startsWith("ftp://")) {
			int lastDot = fromImage.lastIndexOf(".");
			// 图片名称
			toFile = fromImage;
			int lastSeparator = fromImage.lastIndexOf("/");
			if (lastSeparator != -1) {
				if (lastDot != -1) {
					toFile = fromImage.substring(lastSeparator + 1, lastDot);
				} else {
					toFile = fromImage.substring(lastSeparator + 1);
				}
			}
		}
		return discernToFile(fromImage, toFile);
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存识别内容的文件名，默认会自动添加.txt后缀
	 * @return 生成是否完成
	 */
	public boolean discernToFile(String fromImage, String toFile) {
		boolean flag = false;

		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片

		try {
			if (fromImage.startsWith("http://")
					|| fromImage.startsWith("ftp://")) {
				urlFlag = true;
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString();
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return flag;
				}
			}

			List<String> cmd = new ArrayList<String>(); // 存放命令行参数的数组
			File img = new File(fromImage);
			cmd.add(tesseractPath);
			cmd.add("");
			cmd.add(toFile); // 输出文件位置
			setOptions(cmd);
			// cmd.add(" -l chi_sim -psm 7 nobatch");
			// cmd.add(LANG_OPTION); // 字符类别
			// cmd.add("eng"); // 英文，找到tessdata里对应的字典文件。
			ProcessBuilder pb = new ProcessBuilder();

			pb.directory(img.getParentFile());

			cmd.set(1, img.getName()); // 把图片文件位置放在第一个位置
			pb.command(cmd); // 执行命令行
			pb.redirectErrorStream(true); // 通知进程生成器是否合并标准错误和标准输出,把进程错误保存起来。
			Process process = pb.start(); // 开始执行进程
			int w = process.waitFor(); // 当前进程停止,直到process停止执行，返回执行结果.

			if (w == 0)// 0代表正常退出
			{
			} else {
				String msg;
				switch (w) {
				case 1:
					msg = "Errors accessing files. There may be spaces in your image's filename.";
					break;
				case 29:
					msg = "Cannot recognize the image or its selected region.";
					break;
				case 31:
					msg = "Unsupported image format.";
					break;
				default:
					msg = "Errors occurred.";
				}
				throw new RuntimeException(msg);
			}

			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}
		return flag;
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		return discernToFileAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			Type imageType) {
		return this.discernToFileAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
												  Type imageType, double autoCleanImageWidthRatio,
												  double autoCleanImageHeightRatio) {

		return discernToFileAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
												  Type imageType, double autoCleanImageWidthRatio,
												  double autoCleanImageHeightRatio, int autoCleanDegrees) {

		try {
			String fromPath = fromImage.getCanonicalPath();

			String toFile = fromPath;
			int lastIndex = toFile.lastIndexOf(".");
			if (lastIndex != -1) {
				toFile = toFile.substring(0, lastIndex);
			}

			return discernToFileAndAutoCleanImage(fromImage, toFile, imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);

		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
												  Type imageType, int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, autoCleanImageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio, int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			String toFile, Type imageType) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, toFile, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		try {
			String fromPath = fromImage.getCanonicalPath();
			return discernToFileAndAutoCleanImage(fromPath, toFile, imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);

		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
												  String toFile, Type imageType, int autoCleanDegrees) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(File fromImage,
			String toFile, int autoCleanDegrees) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio, int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
			String toFile, Type imageType) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, toFile, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		String fromPath = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		boolean res = false;
		// 输入流转为临时操作文件
		if (inputStreamToFile(fromImage, fromPath)) {
			res = discernToFileAndAutoCleanImage(fromPath, toFile, imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees); // 获得内容，并删除toFile文件
		}
		new File(fromPath).delete(); // 删除输入流对应的临时操作文件
		return res;

	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
												  String toFile, Type imageType, int autoCleanDegrees) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(InputStream fromImage,
			String toFile, int autoCleanDegrees) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		return discernToFileAndAutoCleanImage(fromImage, autoCleanImageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			Type imageType) {
		return this.discernToFileAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
												  Type imageType, double autoCleanImageWidthRatio,
												  double autoCleanImageHeightRatio) {

		return discernToFileAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
												  Type imageType, double autoCleanImageWidthRatio,
												  double autoCleanImageHeightRatio, int autoCleanDegrees) {
		boolean res = false;
		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片
		// 图片名称
		String toFile = fromImage;
		int lastDot = fromImage.lastIndexOf(".");
		// URL图片 toFile地址
		if (fromImage.startsWith("http://") || fromImage.startsWith("ftp://")) {
			urlFlag = true;
			int lastSeparator = fromImage.lastIndexOf("/");
			if (lastSeparator != -1) {
				if (lastDot != -1) {
					toFile = fromImage.substring(lastSeparator + 1, lastDot);
				} else {
					toFile = fromImage.substring(lastSeparator + 1);
				}
			}
		} else {
			if (lastDot != -1) {
				toFile = toFile.substring(0, lastDot);
			}
		}

		try {
			// 如果是http://、ftp://开头的网络图片，将网络图片转为临时图片操作
			if (urlFlag) {
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString();
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return res;
				}
			}

			res = discernToFileAndAutoCleanImage(fromImage, toFile, imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}

		return res;
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
												  Type imageType, int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, autoCleanImageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio, int autoCleanDegrees) {
		return discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			String toFile, Type imageType) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndAutoCleanImage(fromImage, toFile, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		boolean res = false;
		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片

		try {
			// 如果是http://、ftp://开头的网络图片，将网络图片转为临时图片操作
			if (fromImage.startsWith("http://")
					|| fromImage.startsWith("ftp://")) {
				urlFlag = true;
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString();
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return res;
				}
			}

			String tempCleanImage = System.getProperty("java.io.tmpdir")+ File.separator
					+ UUID.randomUUID().toString();
			// 生成自动清理后的新图片
			ImageClean imageClean = new ImageClean(imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);
			imageClean.cleanImage(fromImage, tempCleanImage);

			res = discernToFile(tempCleanImage, toFile); // 识别自动清理后的图片内容
			new File(tempCleanImage).delete(); // 删除自动清理后的临时图片
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}
		return res;
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
												  String toFile, Type imageType, int autoCleanDegrees) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 按指定图片类型和形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，默认文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            保存内容的文件
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 生成是否完成
	 */
	public boolean discernToFileAndAutoCleanImage(String fromImage,
			String toFile, int autoCleanDegrees) {
		return this.discernToFileAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，默认输出文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @return 读取到的代码
	 */
	public String discernToFileAndGet(File fromImage) {
		try {
			String fromPath = fromImage.getCanonicalPath();
			String toFile = fromPath;
			int lastIndex = toFile.lastIndexOf(".");
			if (lastIndex != -1) {
				toFile = toFile.substring(0, lastIndex);
			}
			return discernToFileAndGet(fromPath, toFile, false);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @return 读取到的代码
	 */
	public String discernToFileAndGet(File fromImage, String toFile) {
		try {
			String fromPath = fromImage.getCanonicalPath();
			return discernToFileAndGet(fromPath, toFile, false);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @return 读取到的代码
	 */
	public String discernToFileAndGet(FileInputStream fromImage, String toFile) {

		String fromPath = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		String res = null;
		// 输入流转为临时操作文件
		if (inputStreamToFile(fromImage, fromPath)) {
			res = discernToFileAndGet(fromPath, toFile, false);
		}
		new File(fromPath).delete();
		return res;
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，默认输出文件名为：图片名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @return 读取到的代码
	 */
	public String discernToFileAndGet(String fromImage) {
		// 图片名称
		String toFile = fromImage;
		int lastDot = fromImage.lastIndexOf(".");

		// URL图片 toFile地址
		if (fromImage.startsWith("http://") || fromImage.startsWith("ftp://")) {
			int lastSeparator = fromImage.lastIndexOf("/");
			if (lastSeparator != -1) {
				if (lastDot != -1) {
					toFile = fromImage.substring(lastSeparator + 1, lastDot);
				} else {
					toFile = fromImage.substring(lastSeparator + 1);
				}
			}
		} else {
			if (lastDot != -1) {
				toFile = toFile.substring(0, lastDot);
			}
		}

		return discernToFileAndGet(fromImage, toFile, false);
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @return 读取到的代码
	 */
	public String discernToFileAndGet(String fromImage, String toFile) {
		return discernToFileAndGet(fromImage, toFile, false);
	}

	/**
	 * 将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param deleteFile
	 *            是否删除文件，true在返回内容后会删除文件
	 * @return 读取到的代码
	 */
	private String discernToFileAndGet(String fromImage, String toFile,
			boolean deleteFile) {
		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片
		StringBuilder res = new StringBuilder("");
		try {
			// 如果是http://、ftp://开头的网络图片，将网络图片转为临时图片操作
			if (fromImage.startsWith("http://")
					|| fromImage.startsWith("ftp://")) {
				urlFlag = true;
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString();
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return res.toString();
				}
			}

			List<String> cmd = new ArrayList<String>(); // 存放命令行参数的数组
			File img = new File(fromImage);
			if (tesseractPath == null || tesseractPath.trim().equals("")) {
				tesseractPath = "tesseract";
			}
//			cmd.add("chcp.com");
//			cmd.add("65001");
//			cmd.add(";");
			cmd.add(tesseractPath);
			cmd.add("");
			cmd.add(toFile); // 输出文件位置
			setOptions(cmd);

			// cmd.add(" -l chi_sim -psm 7 nobatch"); // 英文，找到tessdata里对应的字典文件。
			// cmd.add(LANG_OPTION); // 字符类别
			// cmd.add("eng"); // 英文，找到tessdata里对应的字典文件。
			ProcessBuilder pb = new ProcessBuilder();
			pb.directory(img.getParentFile());

			cmd.set(1, img.getName()); // 把图片文件位置放在第一个位置
			pb.command(cmd); // 执行命令行
			pb.redirectErrorStream(true); // 通知进程生成器是否合并标准错误和标准输出,把进程错误保存起来。
			Process process = pb.start(); // 开始执行进程
			int w = process.waitFor(); // 当前进程停止,直到process停止执行，返回执行结果.

			if (w == 0)// 0代表正常退出
			{
				File file = new File(toFile + ".txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				String s = null;
				while ((s = br.readLine()) != null) {
					res.append(s).append("\n");
				}
				if (res.lastIndexOf("\n") != -1) {
					res.delete(res.length() - 2, res.length());
				}
				br.close();
				// 删除生成的文件
				if (deleteFile) {
					file.delete();
				}
			} else {
				String msg;
				switch (w) {
				case 1:
					msg = "Errors accessing files. There may be spaces in your image's filename.";
					break;
				case 29:
					msg = "Cannot recognize the image or its selected region.";
					break;
				case 31:
					msg = "Unsupported image format.";
					break;
				default:
					msg = "Errors accessing files. There may be wrong filename.";
				}
				throw new RuntimeException(msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}
		return res.toString();
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			Type imageType) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
													   Type imageType, double autoCleanImageWidthRatio,
													   double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
													   Type imageType, double autoCleanImageWidthRatio,
													   double autoCleanImageHeightRatio, int autoCleanDegrees) {

		try {
			String fromPath = fromImage.getCanonicalPath();
			String toFile = fromPath;
			int lastIndex = toFile.lastIndexOf(".");
			if (lastIndex != -1) {
				toFile = toFile.substring(0, lastIndex);
			}
			// 识别自动清理后的图片内容
			return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
					imageType, autoCleanImageWidthRatio,
					autoCleanImageHeightRatio, autoCleanDegrees);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
													   Type imageType, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio, int autoCleanDegrees) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			String toFile, Type imageType) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		try {
			String fromPath = fromImage.getCanonicalPath();
			int lastIndex = toFile.lastIndexOf(".");
			if (lastIndex != -1) {
				toFile = toFile.substring(0, lastIndex);
			}

			// 识别自动清理后的图片内容
			return discernToFileAndGetAndAutoCleanImage(fromPath, toFile,
					imageType, autoCleanImageWidthRatio,
					autoCleanImageHeightRatio, autoCleanDegrees);
		} catch (IOException e) {
			throw new EasyOCRException("File path is incorrect, please check",
					e);
		}

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
													   String toFile, Type imageType, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(File fromImage,
			String toFile, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio, int autoCleanDegrees) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
			String toFile, Type imageType) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {

		String fromPath = System.getProperty("java.io.tmpdir")+ File.separator
				+ UUID.randomUUID().toString();
		String res = null;
		// 输入流转为临时操作文件
		if (inputStreamToFile(fromImage, fromPath)) {
			// 识别自动清理后的图片内容
			res = discernToFileAndGetAndAutoCleanImage(fromPath, toFile,
					imageType, autoCleanImageWidthRatio,
					autoCleanImageHeightRatio, autoCleanDegrees);
		}
		new File(fromPath).delete();
		return res;
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
													   String toFile, Type imageType, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(InputStream fromImage,
			String toFile, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			Type imageType) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				this.autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
													   Type imageType, double autoCleanImageWidthRatio,
													   double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, imageType,
				autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
													   Type imageType, double autoCleanImageWidthRatio,
													   double autoCleanImageHeightRatio, int autoCleanDegrees) {
		String res = "";
		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片
		// 图片名称
		String toFile = fromImage;
		int lastDot = fromImage.lastIndexOf(".");
		// URL图片 toFile地址
		if (fromImage.startsWith("http://") || fromImage.startsWith("ftp://")) {
			urlFlag = true;
			int lastSeparator = fromImage.lastIndexOf("/");
			if (lastSeparator != -1) {
				if (lastDot != -1) {
					toFile = fromImage.substring(lastSeparator + 1, lastDot);
				} else {
					toFile = fromImage.substring(lastSeparator + 1);
				}
			}
		} else {
			if (lastDot != -1) {
				toFile = toFile.substring(0, lastDot);
			}
		}

		try {
			// 如果是http://、ftp://开头的网络图片，将网络图片转为临时图片操作
			if (urlFlag) {
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString();
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return res;
				}
			}

			// 识别自动清理后的图片内容
			res = discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
					imageType, autoCleanImageWidthRatio,
					autoCleanImageHeightRatio, autoCleanDegrees);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}

		return res;
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
													   Type imageType, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, imageType,
				this.autoCleanImageWidthRatio, this.autoCleanImageHeightRatio,
				autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 按指定形变比例，自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			String toFile, double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio, int autoCleanDegrees) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, autoCleanImageWidthRatio,
				autoCleanImageHeightRatio, autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			String toFile, Type imageType) {

		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, this.autoCleanDegrees);
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, autoCleanImageWidthRatio, autoCleanImageHeightRatio,
				this.autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanImageWidthRatio
	 *            直接指定图片宽度度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanImageHeightRatio
	 *            直接指定图片高度放大比例，某些图片形变后识别率可以提高
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			String toFile, Type imageType,
			double autoCleanImageWidthRatio, double autoCleanImageHeightRatio,
			int autoCleanDegrees) {
		String res = "";
		boolean urlFlag = false; // 是否是http://、ftp://开头的网络图片

		int lastIndex = toFile.lastIndexOf(".");
		if (lastIndex != -1) {
			toFile = toFile.substring(0, lastIndex);
		}


		try {
			// 如果是http://、ftp://开头的网络图片，将网络图片转为临时图片操作
			if (fromImage.startsWith("http://")
					|| fromImage.startsWith("ftp://")) {
				urlFlag = true;
				URL url = new URL(fromImage);
				InputStream is = url.openStream();

				fromImage = System.getProperty("java.io.tmpdir")+ File.separator
						+ UUID.randomUUID().toString();
				// 如果临时图片存储失败，则停止
				if (!inputStreamToFile(is, fromImage)) {
					is.close();
					new File(fromImage).delete();
					return res;
				}
			}

			String tempCleanImage = System.getProperty("java.io.tmpdir")+ File.separator
					+ UUID.randomUUID().toString();
			// 生成自动清理后的新图片
			ImageClean imageClean = new ImageClean(imageType,
					autoCleanImageWidthRatio, autoCleanImageHeightRatio,
					autoCleanDegrees);
			imageClean.cleanImage(fromImage, tempCleanImage);

			// 识别自动清理后的图片内容
			res = discernToFileAndGet(tempCleanImage, toFile);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 如果是http://、ftp://开头的网络图片，删除临时图片
			if (urlFlag) {
				new File(fromImage).delete();
			}
		}

		return res;
	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param imageType
	 *            图片类型
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
													   String toFile, Type imageType, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				imageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 自动清理图片并将识别的图片上的内容输出到指定文件，并返回读取到的内容字符串，无需后缀名.txt
	 *
	 * @param fromImage
	 *            要识别的图片
	 * @param toFile
	 *            同时保存内容的的文件
	 * @param autoCleanDegrees
	 *            图片自动清理时的顺时针旋转角度
	 * @return 读取到的代码
	 */
	public String discernToFileAndGetAndAutoCleanImage(String fromImage,
			String toFile, int autoCleanDegrees) {
		return discernToFileAndGetAndAutoCleanImage(fromImage, toFile,
				autoCleanImageType, this.autoCleanImageWidthRatio,
				this.autoCleanImageHeightRatio, autoCleanDegrees);

	}

	/**
	 * 获得图片清理的顺时针旋转角度
	 *
	 * @return 顺时针旋转角度
	 */
	public int getAutoCleanDegrees() {
		return autoCleanDegrees;
	}

	/**
	 * 自动清理图片时，图片高度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 *
	 * @return 图片高度放大比例
	 */
	public double getAutoCleanImageHeightRatio() {
		return autoCleanImageHeightRatio;
	}

	/**
	 * 获得图片的清理类型
	 *
	 * @return 图片的清理类型
	 */
	public Type getAutoCleanType() {
		return autoCleanImageType;
	}

	/**
	 * 自动清理图片时，图片宽度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 * 
	 * @return 图片宽度放大比例
	 */
	public double getAutoCleanImageWidthRatio() {
		return autoCleanImageWidthRatio;
	}

	/**
	 * tesseract执行命令的参数
	 * 
	 * @return tesseract执行命令的参数
	 */
	public String getTesseractOptions() {
		return tesseractOptions;
	}

	/**
	 * tesseract执行命令的位置，可从TesseractOCR.OPTION_LANG_...常量选择<br>
	 * 例如：设置中文识别<br>
	 * EasyOCR e=new EasyOCR();<br>
	 * e.setTesseractOptions(EasyOCR.OPTION_LANG_CHI_SIM);
	 * 
	 * @return tesseract执行命令的位置
	 */
	public String getTesseractPath() {
		return tesseractPath;
	}

	/**
	 * 将输入流信息输出到文件(内部使用)
	 * 
	 * @param fis 输入流
	 * @param file 文件路径
	 */
	private boolean inputStreamToFile(InputStream fis, String file) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(new FileOutputStream(file));
			byte[] data = new byte[8192];
			int len = -1;
			while ((len = bis.read(data)) != -1) {
				bos.write(data, 0, len);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 设置自动清理图片时的图片旋转角度
	 * 
	 * @param autoCleanDegrees 旋转角度
	 */
	public void setAutoCleanDegrees(int autoCleanDegrees) {
		this.autoCleanDegrees = autoCleanDegrees;
	}

	/**
	 * 自动清理图片时，图片高度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 * 
	 * @param autoCleanImageHeightRatio 图片放大比例
	 */
	public void setAutoCleanImageHeightRatio(double autoCleanImageHeightRatio) {
		this.autoCleanImageHeightRatio = autoCleanImageHeightRatio;
	}

	/**
	 * 自动清理图片时，设置图片宽度和高度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 * 
	 * @param autoCleanImageWidthRatio
	 *            图片宽度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 * @param autoCleanImageHeightRatio
	 *            图片高度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 */
	public void setAutoCleanImageRatio(double autoCleanImageWidthRatio,
			double autoCleanImageHeightRatio) {
		this.autoCleanImageWidthRatio = autoCleanImageWidthRatio;
		this.autoCleanImageHeightRatio = autoCleanImageHeightRatio;
	}

	/**
	 * 设置图片的清理类型
	 * 
	 * @param autoCleanImageType
	 *            图片的清理类型
	 */
	public void setAutoCleanImageType(Type autoCleanImageType) {
		this.autoCleanImageType = autoCleanImageType;
	}

	/**
	 * 自动清理图片时，图片宽度放大比例，某些图片形变后识别率可以提高，1为原始比例，不形变
	 * 
	 * @param autoCleanImageWidthRatio 图片宽度放大
	 */
	public void setAutoCleanImageWidthRatio(double autoCleanImageWidthRatio) {
		this.autoCleanImageWidthRatio = autoCleanImageWidthRatio;
	}

	/**
	 * 获得自动清理图片时的图片旋转角度
	 * 
	 * @param autoCleanDegrees  图片旋转角度
	 */
	public void setDegrees(int autoCleanDegrees) {
		this.autoCleanDegrees = autoCleanDegrees;
	}

	/**
	 * 设置命令行参数
	 * 
	 * @param cmd 运行参数
	 */
	private void setOptions(List<String> cmd) {
		String[] options = tesseractOptions.split(" ");
		for (String option : options) {
//			cmd.add("");
			cmd.add(option);
		}
	}

	/**
	 * tesseract执行命令的参数 可从TesseractOCR.OPTION_LANG_...常量选择
	 * 
	 * @param tesseractOptions
	 *            tesseract执行命令的参数
	 */
	public void setTesseractOptions(String tesseractOptions) {
		this.tesseractOptions = tesseractOptions.trim();
	}

	/**
	 * tesseract执行命令的位置，使用前需要提前设置 如果配置了path环境变量，则可以不用设置<br>
	 * 例如："D:\\Program Files\\Tesseract-OCR\\tesseract";
	 * 
	 * @param tesseractPath tesseract执行路径
	 */
	public void setTesseractPath(String tesseractPath) {
		this.tesseractPath = tesseractPath;
	}

}
