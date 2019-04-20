[toc]
# AssembleUtil.java
```java
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class AssembleUtil {
	/**
	 * 有序的不重复的Set集合。
	 */
	private static Set<String> set = new TreeSet<String>();

	/**
	 *Author:Jack
	 *Time:2017年9月13日下午2:53:59
	 *@param sourceStr 初始化的字符串源信息
	 *@param max       每种组合是几个数字
	 *@return
	 *Return:String[]
	 *Description:根据字符串“1,2,3,4,5,6,7”的格式计算出来一共有多少组组合
	 */
	public static String[] getAssemble(String sourceStr, int max) {
		String sourceList [] = sourceStr.split(",");
		String[] array = getAssemble(sourceList, max);
		return array;
	}

	/**
	 *Author:Jack
	 *Time:2017年9月13日下午2:53:56
	 *@param sourceArray 初始化的字符串数组信息
	 *@param max         每种组合是几个数字
	 *@return
	 *Return:String[]
	 *Description: 根据字符串数组形式来计算一共有多少种组数{ "1", "2", "3", "4", "5","6","7" }
	 */
	public static String[] getAssemble(String[] sourceArray, int max) {
		for (int start = 0; start < sourceArray.length; start++) {
			doSet(sourceArray[start], sourceArray, max);
		}
		String[] arr = new String[set.size()];
		String[] array = set.toArray(arr);
		set.clear();
		return array;
	}

	/**
	 *Author:Jack
	 *Time:2017年9月13日下午3:00:18
	 *@param start
	 *@param sourceList
	 *@param max
	 *@return
	 *Return:Set<String>
	 *Description:计算组数
	 */
	private static Set<String> doSet(String start, String[] sourceList, int max) {
		String[] olds = start.split("_");
		if (olds.length == max) {
			set.add(start.replaceAll("_", "").trim());
		} else {
			for (int s = 0; s < sourceList.length; s++) {
				if (Arrays.asList(olds).contains(sourceList[s])) {
					continue;
				} else {
					doSet(start + "_" + sourceList[s], sourceList, max);
				}
			}
		}
		return set;
	}

	/**
	 *Author:Jack
	 *Time:2017年9月13日下午3:04:25
	 *@param args
	 *Return:void
	 *Description:测试方法
	 */
	public static void main(String[] args) {

		    String[] sourceArr = new String[] { "1", "2", "3", "4", "5","6","7" };
	        String[] resultArr = getAssemble(sourceArr, 3);
	        System.out.println("累计组合："+resultArr.length+","+Arrays.toString(resultArr));

	        String sourceStr = "1,2,3,4,5,6,7";
	        String[] resultArr2 = getAssemble(sourceStr, 3);
	        System.out.println("累计组合："+resultArr2.length+","+Arrays.toString(resultArr2));
	}
}
```
# Base64Util.java
```java
import java.io.*;

/**  
* @Title: Base64Util.java
* @Package com.jarvis.base.util
* @Description:Base64工具类
* @author Jack 
* @date 2017年9月2日 下午5:10:32
* @version V1.0  
*/
public final class Base64Util
{
    private static final int BASELENGTH = 255;

    private static final int LOOKUPLENGTH = 64;

    private static final int TWENTYFOURBITGROUP = 24;

    private static final int EIGHTBIT = 8;

    private static final int SIXTEENBIT = 16;

    // private static final int SIXBIT = 6;
    private static final int FOURBYTE = 4;

    // private static final int TWOBYTE = 2;
    private static final int SIGN = -128;

    private static final byte PAD = (byte) '=';

    private static byte[] base64Alphabet = new byte[BASELENGTH];

    private static byte[] lookUpBase64Alphabet = new byte[LOOKUPLENGTH];

    static
    {
        for (int i = 0; i < BASELENGTH; i++)
        {
            base64Alphabet[i] = -1;
        }
        for (int i = 'Z'; i >= 'A'; i--)
        {
            base64Alphabet[i] = (byte) (i - 'A');
        }
        for (int i = 'z'; i >= 'a'; i--)
        {
            base64Alphabet[i] = (byte) (i - 'a' + 26);
        }
        for (int i = '9'; i >= '0'; i--)
        {
            base64Alphabet[i] = (byte) (i - '0' + 52);
        }

        base64Alphabet['+'] = 62;
        base64Alphabet['/'] = 63;

        for (int i = 0; i <= 25; i++)
        {
            lookUpBase64Alphabet[i] = (byte) ('A' + i);
        }

        for (int i = 26, j = 0; i <= 51; i++, j++)
        {
            lookUpBase64Alphabet[i] = (byte) ('a' + j);
        }

        for (int i = 52, j = 0; i <= 61; i++, j++)
        {
            lookUpBase64Alphabet[i] = (byte) ('0' + j);
        }

        lookUpBase64Alphabet[62] = (byte) '+';
        lookUpBase64Alphabet[63] = (byte) '/';
    }

    public static boolean isBase64(String isValidString)
    {
        return isArrayByteBase64(isValidString.getBytes());
    }

    public static boolean isBase64(byte octect)
    {
        // shall we ignore white space? JEFF??
        return (octect == PAD || base64Alphabet[octect] != -1);
    }

    public static boolean isArrayByteBase64(byte[] arrayOctect)
    {
        int length = arrayOctect.length;
        if (length == 0)
        {
            // shouldn't a 0 length array be valid base64 data?
            // return false;
            return true;
        }
        for (int i = 0; i < length; i++)
        {
            if (!isBase64(arrayOctect[i]))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Encode String object;
     *
     * @param src String object to be encoded.
     * @return encoded String;
     */
    public static String encodeString(String src)
    {
        return encode(src);
    }

    public static String encodeBytes(byte[] src)
    {
        if (src == null || src.length == 0) {
            return null;
        }
        byte[] bytes = encode(src);
        return new String(bytes);
    }

    /**
     * Encode String object;
     *
     * @param src String object to be encoded.
     * @return encoded String;
     */
    public static String encode(String src)
    {
        String target = null;
        if (src != null)
        {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = encode(bts1);
            if (bts2 != null)
            {
                target = new String(bts2);
            }
        }
        return target;
    }

    /**
     * Encodes hex octects into Base64.
     *
     * @param binaryData Array containing binary data to encode.
     * @return Base64-encoded data.
     */
    public static byte[] encode(byte[] binaryData)
    {
        int lengthDataBits = binaryData.length * EIGHTBIT;
        int fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP;
        int numberTriplets = lengthDataBits / TWENTYFOURBITGROUP;
        byte encodedData[] = null;

        if (fewerThan24bits != 0)
        {
            // data not divisible by 24 bit
            encodedData = new byte[(numberTriplets + 1) * 4];
        }
        else
        {
            // 16 or 8 bit
            encodedData = new byte[numberTriplets * 4];
        }

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        for (i = 0; i < numberTriplets; i++)
        {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];

            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            encodedIndex = i * 4;
            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);
            byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6) : (byte) ((b3) >> 6 ^ 0xfc);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[(l << 2) | val3];
            encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3f];
        }

        // form integral number of 6-bit groups
        dataIndex = i * 3;
        encodedIndex = i * 4;
        if (fewerThan24bits == EIGHTBIT)
        {
            b1 = binaryData[dataIndex];
            k = (byte) (b1 & 0x03);
            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex + 2] = PAD;
            encodedData[encodedIndex + 3] = PAD;
        }
        else if (fewerThan24bits == SIXTEENBIT)
        {

            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2) : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4) : (byte) ((b2) >> 4 ^ 0xf0);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex + 3] = PAD;
        }

        return encodedData;
    }

    public static String decode(String src)
    {
        String target = null;
        if (src != null)
        {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = decode(bts1);
            if (bts2 != null)
            {
                target = new String(bts2);
            }
        }
        return target;
    }

    public static String decode(String src, String charSet) throws UnsupportedEncodingException
    {
        String target = null;
        if (src != null)
        {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = decode(bts1);
            if (bts2 != null)
            {
                target = new String(bts2, charSet);
            }
        }
        return target;
    }

    /**
     * Decodes Base64 data into octects
     *
     * @param base64Data Byte array containing Base64 data
     * @return Array containing decoded data.
     */
    public static byte[] decode(byte[] base64Data)
    {
        // handle the edge case, so we don't have to worry about it later
        if (base64Data.length == 0)
        {
            return null;
        }

        int numberQuadruple = base64Data.length / FOURBYTE;
        byte decodedData[] = null;
        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, marker0 = 0, marker1 = 0;

        // Throw away anything not in base64Data

        int encodedIndex = 0;
        int dataIndex = 0;
        {
            // this sizes the output array properly - rlw
            int lastData = base64Data.length;
            // ignore the '=' padding
            while (base64Data[lastData - 1] == PAD)
            {
                if (--lastData == 0)
                {
                    return new byte[0];
                }
            }
            decodedData = new byte[lastData - numberQuadruple];
        }

        for (int i = 0; i < numberQuadruple; i++)
        {
            dataIndex = i * 4;
            marker0 = base64Data[dataIndex + 2];
            marker1 = base64Data[dataIndex + 3];

            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex + 1]];

            if (marker0 != PAD && marker1 != PAD)
            {
                // No PAD e.g 3cQl
                b3 = base64Alphabet[marker0];
                b4 = base64Alphabet[marker1];

                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                decodedData[encodedIndex + 2] = (byte) (b3 << 6 | b4);
            }
            else if (marker0 == PAD)
            {
                // Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
            }
            else if (marker1 == PAD)
            {
                // One PAD e.g. 3cQ[Pad]
                b3 = base64Alphabet[marker0];

                decodedData[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            }
            encodedIndex += 3;
        }
        return decodedData;
    }

    /**
     * 隐藏工具类的构造方法
     */
    protected Base64Util()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /** *//**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decodeString(String base64) throws Exception {
        return Base64Util.decode(base64.getBytes());
    }

    /** *//**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encodeByte(byte[] bytes) throws Exception {
        return new String(Base64Util.encode(bytes));
    }

    /** *//**
     * <p>
     * 将文件编码为BASE64字符串
     * </p>
     * <p>
     * 大文件慎用，可能会导致内存溢出
     * </p>
     *
     * @param filePath 文件绝对路径
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encodeByte(bytes);
    }

    /** *//**
     * <p>
     * BASE64字符串转回文件
     * </p>
     *
     * @param filePath 文件绝对路径
     * @param base64 编码字符串
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decodeString(base64);
        byteArrayToFile(bytes, filePath);
    }

        /** *//**
     * <p>
     * 文件转换为二进制数组
     * </p>
     *
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }

    /** *//**
     * <p>
     * 二进制数据写文件
     * </p>
     *
     * @param bytes 二进制数据
     * @param filePath 文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }
}
```
# CharHelper.java
```java

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Title:字符编码工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author:
 * @version 1.0
 */
public class CharHelper {

	/**
	 * 转换编码 ISO-8859-1到GB2312
	 *
	 * @param text
	 * @return
	 */
	public static final String ISO2GB(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	/**
	 * 转换编码 GB2312到ISO-8859-1
	 *
	 * @param text
	 * @return
	 */
	public static final String GB2ISO(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GB2312"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static String GBK2UTF8(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("GBK"), "UTF-8");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * 字符串从GBK编码转换为Unicode编码
	 *
	 * @param text
	 * @return
	 */
	public static String GBK2Unicode(String text) {
		String result = "";
		int input;
		StringReader isr;
		try {
			isr = new StringReader(new String(text.getBytes(), "GBK"));
		} catch (UnsupportedEncodingException e) {
			return "-1";
		}
		try {
			while ((input = isr.read()) != -1) {
				result = result + "&#x" + Integer.toHexString(input) + ";";

			}
		} catch (IOException e) {
			return "-2";
		}
		isr.close();
		return result;

	}

	/**
	 * Author:Jack Time:2017年9月2日下午5:40:19
	 *
	 * @param strVal
	 * @return Return:String Description:
	 */
	public static String ISO2UTF8(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("ISO-8859-1"), "UTF-8");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * Author:Jack Time:2017年9月2日下午5:40:25
	 *
	 * @param strVal
	 * @return Return:String Description:
	 */
	public static String UTF82ISO(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = new String(strVal.getBytes("UTF-8"), "ISO-8859-1");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * 把字符串转换为UTF8859编码
	 *
	 * @param source
	 *            需要进行转换的字符串
	 */
	public static final String GBKto8859(String source) {
		String temp = null;
		if (source == null || source.equals("")) {
			return source;
		}
		try {
			temp = new String(source.getBytes("GBK"), "8859_1");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Convert code Error");
		}
		return temp;
	}

	/**
	 * 把字符串转换为GBK编码
	 *
	 * @param source
	 *            需要进行转换的字符串
	 */
	public static final String toGBK(String source) {
		String temp = null;
		if (source == null || source.equals("")) {
			return source;
		}
		try {
			temp = new String(source.getBytes("8859_1"), "GBK");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Convert code Error");
		}
		return temp;
	}

	/**
	 * 把字符串转换为gb2312编码
	 *
	 * @param source
	 *            需要进行转换的字符串
	 */
	public static final String toGb2312(String source) {
		String temp = null;
		if (source == null || source.equals("")) {
			return source;
		}
		try {
			temp = new String(source.getBytes("8859_1"), "GB2312");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("转换字符串为gb2312编码出错");
		}
		return temp;
	}

	/**
	 * 把中文字符串，转换为unicode字符串
	 *
	 * @param source
	 *            需要进行转换的字符串
	 * @return 转换后的unicode字符串
	 */
	public static String chineseToUnicode(String source) {
		if (source == null || source.trim().length() == 0) {
			return source;
		}
		String unicode = null;
		String temp = null;
		for (int i = 0; i < source.length(); i++) {
			temp = "\\u" + Integer.toHexString((int) source.charAt(i));
			unicode = unicode == null ? temp : unicode + temp;
		}
		return unicode;
	}

	/**
	 * 在将数据存入数据库前转换
	 *
	 * @param strVal
	 *            要转换的字符串
	 * @return 从“ISO8859_1”到“GBK”得到的字符串
	 * @since 1.0
	 */
	public static String toChinese(String strVal) {
		try {
			if (strVal == null) {
				return "";
			} else {
				strVal = strVal.trim();
				strVal = new String(strVal.getBytes("ISO8859_1"), "GBK");
				return strVal;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * Utf8URL编码
	 *
	 * @param s
	 * @return
	 */
	public static final String Utf8URLencode(String text) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {

			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {

				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}

				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}

			}
		}

		return result.toString();
	}

	/**
	 * Utf8URL解码
	 *
	 * @param text
	 * @return
	 */
	public static final String Utf8URLdecode(String text) {
		String result = "";
		int p = 0;

		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;

			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text == "" || text.length() < 9)
					return result;

				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}

		}

		return result + text;
	}

	/**
	 * utf8URL编码转字符
	 *
	 * @param text
	 * @return
	 */
	private static final String CodeToWord(String text) {
		String result;

		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}

		return result;
	}

	/**
	 * 编码是否有效
	 *
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unused")
	private static final boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 判断是否Utf8Url编码
	 *
	 * @param text
	 * @return
	 */
	public static final boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// CharTools charTools = new CharTools();

		String url;

		url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
		if (CharHelper.isUtf8Url(url)) {
			System.out.println(CharHelper.Utf8URLdecode(url));
		} else {
			// System.out.println(URLDecoder.decode(url));
		}

		url = "http://www.baidu.com/baidu?word=%D6%D0%B9%FA%B4%F3%B0%D9%BF%C6%D4%DA%CF%DF%C8%AB%CE%C4%BC%EC%CB%F7&tn=myie2dg";
		if (CharHelper.isUtf8Url(url)) {
			System.out.println(CharHelper.Utf8URLdecode(url));
		} else {
			// System.out.println(URLDecoder.decode(url));
		}
	}
}
```
# CharTools.java
```java
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * Title:字符编码工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author:
 * @version 1.0
 */
public class CharTools {

	/**
	 * 转换编码 ISO-8859-1到GB2312
	 *
	 * @param text
	 * @return
	 */
	public static final String ISO2GB(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("ISO-8859-1"), "GB2312");
		} catch (UnsupportedEncodingException ex) {
			result = ex.toString();
		}
		return result;
	}

	/**
	 * 转换编码 GB2312到ISO-8859-1
	 *
	 * @param text
	 * @return
	 */
	public static final String GB2ISO(String text) {
		String result = "";
		try {
			result = new String(text.getBytes("GB2312"), "ISO-8859-1");
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Utf8URL编码
	 *
	 * @param s
	 * @return
	 */
	public static final String Utf8URLencode(String text) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < text.length(); i++) {

			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {

				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
				}

				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}

			}
		}

		return result.toString();
	}

	/**
	 * Utf8URL解码
	 *
	 * @param text
	 * @return
	 */
	public static final String Utf8URLdecode(String text) {
		String result = "";
		int p = 0;

		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;

			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text == "" || text.length() < 9)
					return result;

				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}

		}

		return result + text;
	}

	/**
	 * utf8URL编码转字符
	 *
	 * @param text
	 * @return
	 */
	private static final String CodeToWord(String text) {
		String result;

		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}

		return result;
	}

	/**
	 * 编码是否有效
	 *
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unused")
	private static final boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 判断是否Utf8Url编码
	 *
	 * @param text
	 * @return
	 */
	public static final boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// CharTools charTools = new CharTools();

		String url;

		url = "http://www.google.com/search?hl=zh-CN&newwindow=1&q=%E4%B8%AD%E5%9B%BD%E5%A4%A7%E7%99%BE%E7%A7%91%E5%9C%A8%E7%BA%BF%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2&btnG=%E6%90%9C%E7%B4%A2&lr=";
		if (CharTools.isUtf8Url(url)) {
			System.out.println(CharTools.Utf8URLdecode(url));
		} else {
			// System.out.println(URLDecoder.decode(url));
		}

		url = "http://www.baidu.com/baidu?word=%D6%D0%B9%FA%B4%F3%B0%D9%BF%C6%D4%DA%CF%DF%C8%AB%CE%C4%BC%EC%CB%F7&tn=myie2dg";
		if (CharTools.isUtf8Url(url)) {
			System.out.println(CharTools.Utf8URLdecode(url));
		} else {
			// System.out.println(URLDecoder.decode(url));
		}

	}
}
```
# ConvertHelper.java
```java
import java.util.List;

/**  
* @Title: ConvertHelper.java
* @Package com.jarvis.base.util
* @Description:数据类型转换
* @author Jack 
* @date 2017年9月2日 下午3:22:05
* @version V1.0  
*/
public class ConvertHelper
{
	/**
	 * 把字串转化为整数,若转化失败，则返回0
	 *
	 * @param str
	 *            字串
	 * @return
	 */
	public static int strToInt(String str) {
		if (str == null) {
			return 0;
		}

		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成int类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 把字串转化为长整型数,若转化失败，则返回0
	 *
	 * @param str
	 *            要转化为长整型的字串
	 * @return
	 */
	public static long strToLong(String str) {
		if (str == null) {
			return 0;
		}

		try {
			return Long.parseLong(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成long类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 把字串转化为Float型数据,若转化失败，则返回0
	 *
	 * @param str
	 *            要转化为Float的字串
	 * @return
	 */
	public static float strToFloat(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Float.parseFloat(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成float类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 把字串转化为Double型数据，若转化失败，则返回0
	 *
	 * @param str
	 *            要转化为Double的字串
	 * @return
	 */
	public static double strToDouble(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return Double.parseDouble(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println(str + "转换成double类型失败，请检查数据来源");
		}
		return 0;
	}

	/**
	 * 描述：字符转为一个元素的Object数组
	 *
	 * @param str
	 * @return
	 */
	public static Object[] strToArry(String str) {
		if (str == null) {
			return null;
		} else {
			return new Object[] { str };
		}
	}

	/**
	 * 对于一个字符串数组，把字符串数组中的每一个字串转换为整数。 返回一个转换后的整型数组，对于每一个字串若转换失败，则对 应的整型值就为0
	 *
	 * @param strArray
	 *            要转化的数组
	 * @return
	 */
	public static int[] strArrayToIntArray(String[] strArray) {
		int[] intArray = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			intArray[i] = strToInt(strArray[i]);
		}
		return intArray;
	}

	/**
	 * 描述：数组转换为字符串
	 *
	 * @param arg0
	 *            数组
	 * @return
	 */
	public static String arrToString(Object[] arg0) {
		if (arg0 == null) {
			return "";
		}
		return arrToString(arg0, ",");
	}

	/**
	 * 描述：数据转换为字符串
	 *
	 * @param arg0
	 *            数组
	 * @param arg1
	 *            取数组个数
	 * @return
	 */
	public static String arrToString(Object[] arg0, int arg1) {
		if (arg0 == null) {
			return "";
		}
		return arrToString(arg0, ",", arg1);
	}

	/**
	 * 描述：数据转换为字符串
	 *
	 * @param arg0
	 *            数组
	 * @param arg1
	 *            间隔符号
	 * @return
	 */
	public static String arrToString(Object[] arg0, String arg1) {
		return arrToString(arg0, arg1, 0);
	}

	/**
	 * 描述：数据转换为字符串
	 *
	 * @param arg0
	 *            数组
	 * @param arg1
	 *            间隔符号
	 * @param arg2
	 *            取数组个数
	 * @return
	 */
	public static String arrToString(Object[] arg0, String arg1, int arg2) {
		if (arg0 == null || arg0.length == 0) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			int length = arg0.length;
			if (arg2 != 0) {
				length = arg2;
			}
			for (int i = 0; i < length; i++) {
				if (arg1 == null)
					arg1 = "";
				sb.append(arg0[i]).append(arg1);
			}
			sb.delete(sb.lastIndexOf(arg1), sb.length());
			return sb.toString();
		}
	}

	/**
	 * 描述：List转换为字符串
	 *
	 * @param list
	 *            List数据
	 * @param separation
	 *            间隔符
	 * @return
	 */
	public static String listToString(List<?> list) {
		return listToString(list, ",");
	}

	/**
	 * 描述：List转换为字符串
	 *
	 * @param list
	 *            List数据
	 * @param separation
	 *            间隔符
	 * @return
	 */
	public static String listToString(List<?> list, String separation) {
		return arrToString(listToStringArray(list), separation);
	}

	/**
	 * 描述：字串数据元素包装
	 *
	 * @param sArr
	 *            字串数据
	 * @param pre
	 *            前缀
	 * @param aft
	 *            后缀
	 * @return
	 */
	public static String[] strArrDoPack(String[] sArr, String pre, String aft) {
		return strArrDoPack(sArr, pre, aft, 1, 0);
	}

	/**
	 * 描述：字串数据元素包装
	 *
	 * @param sArr
	 *            字串数据
	 * @param pre
	 *            前缀
	 * @param aft
	 *            后缀
	 * @param num
	 *            生成个数
	 * @return
	 */
	public static String[] strArrDoPack(String[] sArr, String pre, String aft, int num) {
		return strArrDoPack(sArr, pre, aft, num, 0);
	}

	/**
	 * 描述：字串数据元素包装
	 *
	 * @param sArr
	 *            字串数据
	 * @param pre
	 *            前缀
	 * @param aft
	 *            后缀
	 * @param num
	 *            生成个数
	 * @param step
	 *            数字值1：加，-1：减，0：不变
	 * @return
	 */
	public static String[] strArrDoPack(String[] sArr, String pre, String aft, int num, int step) {
		String[] arr = null;
		if (sArr != null) {
			boolean isAdd = false;
			if (step > 0) {
				isAdd = true;
			}

			if (num < 0) {
				num = 1;
			}

			arr = new String[sArr.length * num];
			int icount = 0;
			for (int i = 0; i < num; i++) {
				for (int j = 0; j < sArr.length; j++) {
					if (StringHelper.isNotEmpty(pre)) {
						arr[icount] = pre + sArr[j];
					}
					if (StringHelper.isNotEmpty(aft)) {
						arr[icount] += aft;
					}
					icount++;
				}

				boolean b = false;
				if (step != 0) {
					pre = stepNumInStr(pre, isAdd);
					b = true;
				}
				if (!b) {
					if (step != 0) {
						aft = stepNumInStr(aft, isAdd);
					}
				}
			}

		}
		return arr;
	}

	/**
	 * 描述：生成字符串
	 *
	 * @param arg0
	 *            字符串元素
	 * @param arg1
	 *            生成个数
	 * @return
	 */
	public static String createStr(String arg0, int arg1) {
		if (arg0 == null) {
			return "";
		}
		return createStr(arg0, arg1, ",");
	}

	/**
	 * 描述：生成字符串
	 *
	 * @param arg0
	 *            字符串元素
	 * @param arg1
	 *            生成个数
	 * @param arg2
	 *            间隔符号
	 * @return
	 */
	public static String createStr(String arg0, int arg1, String arg2) {
		if (arg0 == null) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arg1; i++) {
				if (arg2 == null)
					arg2 = "";
				sb.append(arg0).append(arg2);
			}
			if (sb.length() > 0) {
				sb.delete(sb.lastIndexOf(arg2), sb.length());
			}

			return sb.toString();
		}
	}

	/**
	 * 描述：生成字符串数据
	 *
	 * @param arg0
	 *            字符串元素
	 * @param arg1
	 *            生成个数
	 * @param arg2
	 *            间隔符号
	 * @return
	 */
	public static String[] createStrArr(String arg0, int arg1) {
		if (arg0 == null) {
			return null;
		} else {
			String[] arr = new String[arg1];
			for (int i = 0; i < arg1; i++) {
				arr[i] = arg0;
			}

			return arr;
		}
	}

	/**
	 * 描述：只保留字符串的英文字母和“_”号
	 *
	 * @param str
	 * @return
	 */
	public static String replaceAllSign(String str) {
		if (str != null && str.length() > 0) {
			str = str.replaceAll("[^a-zA-Z_]", "");
		}
		return str;
	}

	/**
	 * 描述：字串中的数字值加1
	 *
	 * @param str
	 *            字串
	 * @param isAdd
	 *            数字值true：加，false：减
	 * @return
	 */
	public static String stepNumInStr(String str, boolean isAdd) {
		String sNum = str.replaceAll("[^0-9]", ",").trim();
		if (sNum == null || sNum.length() == 0) {
			return str;
		}
		String[] sNumArr = sNum.split(",");

		for (int i = 0; i < sNumArr.length; i++) {
			if (sNumArr[i] != null && sNumArr[i].length() > 0) {
				int itemp = Integer.parseInt(sNumArr[i]);
				if (isAdd) {
					itemp += 1;
				} else {
					itemp -= 1;
				}
				str = str.replaceFirst(sNumArr[i], String.valueOf(itemp));
				break;
			}
		}

		return str;
	}

	/**
	 * 描述：list 转换为 String[]
	 *
	 * @param list
	 * @return
	 */
	public static String[] listToStringArray(List<?> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
}
```
# DateHelper.java
```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *   
 *
 * @Title: DateHelper.java
 * @Package com.jarvis.base.util
 * @Description:日期工具类
 * @author Jack 
 * @date 2017年9月2日 下午2:33:46
 * @version V1.0  
 */
public class DateHelper {

	/**
	 * 日期格式yyyy-MM-dd
	 */
	public static final String pattern_date = "yyyy-MM-dd";

	/**
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 */
	public static final String pattern_time = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 描述：日期格式化
	 *
	 * @param date
	 *            日期
	 * @return 输出格式为 yyyy-MM-dd 的字串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, pattern_time);
	}

	/**
	 * 描述：日期格式化
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式化类型
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 描述：解析日期字串
	 *
	 * @param dateStr
	 *            日期字串
	 * @return 按 yyyy-MM-dd HH:mm:ss 格式解析
	 */
	public static Date parseString(String dateStr) {
		return parseString(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 描述：解析日期字串
	 *
	 * @param dateStr
	 *            日期字串
	 * @param pattern
	 *            字串日期格式
	 * @return 对应日期类型数据
	 */
	public static Date parseString(String dateStr, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			if (!StringHelper.isEmpty(dateStr)) {
				return dateFormat.parse(dateStr);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			System.err.println(dateStr + "转换成日期失败，可能是不符合格式：" + pattern);
		}
		return null;
	}

	/**
	 * 描述：获取指定日期的中文星期数
	 *
	 * @param date
	 *            指定日期
	 * @return 星期数，如：星期一
	 */
	public static String getWeekStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(7);
		--week;
		String weekStr = "";
		switch (week) {
		case 0:
			weekStr = "星期日";
			break;
		case 1:
			weekStr = "星期一";
			break;
		case 2:
			weekStr = "星期二";
			break;
		case 3:
			weekStr = "星期三";
			break;
		case 4:
			weekStr = "星期四";
			break;
		case 5:
			weekStr = "星期五";
			break;
		case 6:
			weekStr = "星期六";
		}
		return weekStr;
	}

	/**
	 * 描述：间隔时间
	 *
	 * @param date1
	 * @param date2
	 * @return 毫秒数
	 */
	public static long getDateMiliDispersion(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return 0L;
		}

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		return time1 - time2;
	}

	/**
	 * 描述：间隔天数
	 *
	 * @param date1
	 * @param date2
	 * @return 天数
	 */
	public static int getDateDiff(Date date1, Date date2) {
		if ((date1 == null) || (date2 == null)) {
			return 0;
		}
		long time1 = date1.getTime();
		long time2 = date2.getTime();

		long diff = time1 - time2;

		Long longValue = new Long(diff / 86400000L);
		return longValue.intValue();
	}

	/**
	 * 描述：获取指定日期之前多少天的日期
	 *
	 * @param date
	 *            指定日期
	 * @param day
	 *            天数
	 * @return 日期
	 */
	public static Date getDataDiff(Date date, int day) {
		if (date == null) {
			return null;
		}
		long time = date.getTime();
		time -= 86400000L * day;
		return new Date(time);
	}

	/**
	 * 描述：获取当前周
	 *
	 * @return
	 */
	public static int getCurrentWeek() {
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(7);
		--week;
		if (week == 0) {
			week = 7;
		}
		return week;
	}

	/**
	 * 描述：获取中文当前周
	 *
	 * @return
	 */
	public static String getCurrentWeekStr() {
		return getWeekStr(new Date());
	}

	/**
	 * 描述：获取本年
	 *
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(1);
	}

	/**
	 * 描述：获取本月
	 *
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(2) + 1;
	}

	/**
	 * 描述：获取本月的当前日期数
	 *
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(5);
	}

	/**
	 * 描述：当前时间与指定时间的差
	 *
	 * @param str
	 *            秒数
	 * @return 时间差，单位：秒
	 */
	public static int getUnixTime(String str) {
		if ((str == null) || ("".equals(str))) {
			return 0;
		}
		try {
			long utime = Long.parseLong(str) * 1000L;
			Date date1 = new Date(utime);

			Date date = new Date();

			long nowtime = (date.getTime() - date1.getTime()) / 1000L;
			return (int) nowtime;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取时差失败");
		}
		return 0;
	}

	/**
	 * 描述：去除日期字串中原“-”和“:”
	 *
	 * @param dateTime日期字串
	 * @return
	 */
	public static String formatString(String dateTime) {
		if ((dateTime != null) && (dateTime.length() >= 8)) {
			String formatDateTime = dateTime.replaceAll("-", "");
			formatDateTime = formatDateTime.replaceAll(":", "");
			String date = formatDateTime.substring(0, 8);
			return date;
		}

		return "";
	}

	/**
	 * 描述：当前时间与指定时间的差
	 *
	 * @param str
	 *            yyyy-MM-dd HH:mm:ss 格式的日期
	 * @return 时间差，单位：秒
	 */
	public static int getTimesper(String str) {
		if ((str == null) || ("".equals(str))) {
			return 0;
		}
		try {
			Date date1 = new Date(Long.parseLong(str));
			Date date = new Date();
			long nowtime = (date.getTime() - date1.getTime()) / 1000L;
			return (int) nowtime;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("日期转换出错");
		}
		return 0;
	}

	/**
	 * 描述：获取16位日期时间，yyyyMMddHHmmss
	 *
	 * @param dateTime
	 *            字串日期
	 * @return
	 */
	public static String formatDateTime(String dateTime) {
		if ((dateTime != null) && (dateTime.length() >= 8)) {
			String formatDateTime = dateTime.replaceAll("-", "");
			formatDateTime = formatDateTime.replaceAll(":", "");
			String date = formatDateTime.substring(0, 8);
			String time = formatDateTime.substring(8).trim();
			for (int i = time.length(); i < 6; ++i) {
				time = time + "0";
			}
			return date + time;
		}

		return "";
	}

	/**
	 * 描述：获取16位日期时间，yyyyMMddHHmmss
	 *
	 * @param date
	 *            日期
	 * @return
	 */
	public static String formatDateTime(Date date) {
		String dateTime = formatDate(date);
		return formatDateTime(dateTime);
	}
}
```
# Escape.java
```java
public class Escape {
	private final static String[] hex = { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
			"1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
			"27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
			"32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
			"3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
			"48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
			"5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
			"69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
			"74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
			"7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
			"8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
			"95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
			"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
			"AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
			"B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
			"CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
			"D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
			"E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
			"ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
			"F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

	private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
			0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	/** */
	/**   
	 * ����
	 *    
	 * @param s   
	 * @return   
	 */
	public static String escape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			if ('A' <= ch && ch <= 'Z') {
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') {
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') {
				sbuf.append((char) ch);
			} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
					|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
					|| ch == ')') {
				sbuf.append((char) ch);
			} else if (ch <= 0x007F) {
				sbuf.append('%');
				sbuf.append(hex[ch]);
			} else {
				sbuf.append('%');
				sbuf.append('u');
				sbuf.append(hex[(ch >>> 8)]);
				sbuf.append(hex[(0x00FF & ch)]);
			}
		}
		return sbuf.toString();
	}

	/** */
	/**   
	 * ���� ˵������������֤ ���۲���s�Ƿ񾭹�escape()���룬���ܵõ���ȷ�ġ����롱���
	 *    
	 * @param s   
	 * @return   
	 */
	public static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int i = 0;
		int len = s.length();
		while (i < len) {
			int ch = s.charAt(i);
			if ('A' <= ch && ch <= 'Z') {
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') {
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') {
				sbuf.append((char) ch);
			} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
					|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
					|| ch == ')') {
				sbuf.append((char) ch);
			} else if (ch == '%') {
				int cint = 0;
				if ('u' != s.charAt(i + 1)) {
					cint = (cint << 4) | val[s.charAt(i + 1)];
					cint = (cint << 4) | val[s.charAt(i + 2)];
					i += 2;
				} else {
					cint = (cint << 4) | val[s.charAt(i + 2)];
					cint = (cint << 4) | val[s.charAt(i + 3)];
					cint = (cint << 4) | val[s.charAt(i + 4)];
					cint = (cint << 4) | val[s.charAt(i + 5)];
					i += 5;
				}
				sbuf.append((char) cint);
			} else {
				sbuf.append((char) ch);
			}
			i++;
		}
		return sbuf.toString();
	}

	public static void main(String[] args) {
		String stest = "һ��Ц������֮��1234 abcd[]()<+>,.~\"";
		System.out.println(stest);
		System.out.println(escape(stest));
		System.out.println(unescape(escape(stest)));
	}
}
```
# FastJsonUtil.java
```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 *   
 *
 * @Title: FastJsonUtil.java
 * @Package com.jarvis.base.util
 * @Description:fastjson工具类
 * @author Jack 
 * @date 2017年9月2日 下午4:16:27
 * @version V1.0  
 */
public class FastJsonUtil {

	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	}

	private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.PrettyFormat  //是否需要格式化输出Json数据
	};

	/**
	 * Author:Jack Time:2017年9月2日下午4:24:14
	 *
	 * @param object
	 * @return Return:String Description:将对象转成成Json对象
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:27:25
	 *
	 * @param object
	 * @return Return:String Description:使用和json-lib兼容的日期输出格式
	 */
	public static String toJSONNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:24:54
	 *
	 * @param jsonStr
	 * @return Return:Object Description:将Json数据转换成JSONObject
	 */
	public static JSONObject toJsonObj(String jsonStr) {
		return (JSONObject) JSON.parse(jsonStr);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:25:20
	 *
	 * @param jsonStr
	 * @param clazz
	 * @return Return:T Description:将Json数据转换成Object
	 */
	public static <T> T toBean(String jsonStr, Class<T> clazz) {
		return JSON.parseObject(jsonStr, clazz);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:25:34
	 *
	 * @param jsonStr
	 * @return Return:Object[] Description:将Json数据转换为数组
	 */
	public static <T> Object[] toArray(String jsonStr) {
		return toArray(jsonStr, null);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:25:57
	 *
	 * @param jsonStr
	 * @param clazz
	 * @return Return:Object[] Description:将Json数据转换为数组
	 */
	public static <T> Object[] toArray(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz).toArray();
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:26:08
	 *
	 * @param jsonStr
	 * @param clazz
	 * @return Return:List<T> Description:将Json数据转换为List
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz);
	}

	/**
	 * 将javabean转化为序列化的JSONObject对象
	 *
	 * @param keyvalue
	 * @return
	 */
	public static JSONObject beanToJsonObj(Object bean) {
		String jsonStr = JSON.toJSONString(bean);
		JSONObject objectJson = (JSONObject) JSON.parse(jsonStr);
		return objectJson;
	}
	/**
	 * json字符串转化为map
	 *
	 * @param s
	 * @return
	 */
	public static Map<?, ?> stringToCollect(String jsonStr) {
		Map<?, ?> map = JSONObject.parseObject(jsonStr);
		return map;
	}

	/**
	 * 将map转化为string
	 *
	 * @param m
	 * @return
	 */
	public static String collectToString(Map<?, ?> map) {
		String jsonStr = JSONObject.toJSONString(map);
		return jsonStr;
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:19:00
	 *
	 * @param t
	 * @param file
	 * @throws IOException
	 *             Return:void Description:将对象的Json数据写入文件。
	 */
	public static <T> void writeJsonToFile(T t, File file) throws IOException {
		String jsonStr = JSONObject.toJSONString(t, SerializerFeature.PrettyFormat);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		bw.write(jsonStr);
		bw.close();
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:19:12
	 *
	 * @param t
	 * @param filename
	 * @throws IOException
	 *             Return:void Description:将对象的Json数据写入文件。
	 */
	public static <T> void writeJsonToFile(T t, String filename) throws IOException {
		writeJsonToFile(t, new File(filename));
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:22:07
	 *
	 * @param cls
	 * @param file
	 * @return
	 * @throws IOException
	 *             Return:T Description:将文件中的Json数据转换成Object对象
	 */
	public static <T> T readJsonFromFile(Class<T> cls, File file) throws IOException {
		StringBuilder strBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		while ((line = br.readLine()) != null) {
			strBuilder.append(line);
		}
		br.close();
		return JSONObject.parseObject(strBuilder.toString(), cls);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:22:30
	 *
	 * @param cls
	 * @param filename
	 * @return
	 * @throws IOException
	 *             Return:T Description:将文件中的Json数据转换成Object对象
	 */
	public static <T> T readJsonFromFile(Class<T> cls, String filename) throws IOException {
		return readJsonFromFile(cls, new File(filename));
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:23:06
	 *
	 * @param typeReference
	 * @param file
	 * @return
	 * @throws IOException
	 *             Return:T Description:从文件中读取出Json对象
	 */
	public static <T> T readJsonFromFile(TypeReference<T> typeReference, File file) throws IOException {
		StringBuilder strBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		while ((line = br.readLine()) != null) {
			strBuilder.append(line);
		}
		br.close();
		return JSONObject.parseObject(strBuilder.toString(), typeReference);
	}

	/**
	 * Author:Jack Time:2017年9月2日下午4:23:11
	 *
	 * @param typeReference
	 * @param filename
	 * @return
	 * @throws IOException
	 *             Return:T Description:从文件中读取出Json对象
	 */
	public static <T> T readJsonFromFile(TypeReference<T> typeReference, String filename) throws IOException {
		return readJsonFromFile(typeReference, new File(filename));
	}
}
```
# FileHelper.java
```java
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *   
 *
 * @Title: FileHelper.java
 * @Package com.jarvis.base.util
 * @Description:文件工具类
 * @author Jack 
 * @date 2017年9月2日 下午2:39:29
 * @version V1.0  
 */
public class FileHelper {

	/**
	 * 1kb
	 */
	private final static int KB_1 = 1024;

	/**
	 * 获得文件的CRC32校验和
	 *
	 * @param file
	 *            要进行校验的文件
	 * @return
	 * @throws Exception
	 */
	public static String getFileCRCCode(File file) throws Exception {
		FileInputStream is = new FileInputStream(file);
		CRC32 crc32 = new CRC32();
		CheckedInputStream cis = new CheckedInputStream(is, crc32);
		byte[] buffer = null;
		buffer = new byte[KB_1];
		while (cis.read(buffer) != -1) {
		}
		is.close();
		buffer = null;
		return Long.toHexString(crc32.getValue());
	}

	/**
	 * 获得字串的CRC32校验和
	 *
	 * @param string
	 *            要进行校验的字串
	 * @return
	 * @throws Exception
	 */
	public static String getStringCRCCode(String string) throws Exception {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(string.getBytes());
		CRC32 crc32 = new CRC32();
		CheckedInputStream checkedinputstream = new CheckedInputStream(inputStream, crc32);
		byte[] buffer = null;
		buffer = new byte[KB_1];
		while (checkedinputstream.read(buffer) != -1) {
		}
		inputStream.close();
		buffer = null;
		return Long.toHexString(crc32.getValue());
	}

	/**
	 * 连接路径和文件名称，组成最后的包含路径的文件名
	 *
	 * @param basePath
	 *            文件路径
	 * @param fullFilenameToAdd
	 *            文件名称
	 * @return
	 */
	public static String concat(String basePath, String fullFilenameToAdd) {
		return FilenameUtils.concat(basePath, fullFilenameToAdd);
	}

	/**
	 * 获得不带文件扩展名的文件名称
	 *
	 * @param filename
	 *            文件完整路径
	 * @return 不带扩展名的文件名称
	 */
	public static String getBaseName(String filename) {
		return FilenameUtils.getBaseName(filename);
	}

	/**
	 * 获得带扩展名的文件名称
	 *
	 * @param filename
	 *            文件完整路径
	 * @return 文件名称
	 */
	public static String getFileName(String filename) {
		return FilenameUtils.getName(filename);
	}

	/**
	 * 获得文件的完整路径，包含最后的路径分隔条
	 *
	 * @param filename
	 *            文件完整路径
	 * @return 目录结构
	 */
	public static String getFullPath(String filename) {
		return FilenameUtils.getFullPath(filename);
	}

	/**
	 * 获得文件的完整路径，不包含最后的路径分隔条
	 *
	 * @param filename
	 *            文件完整路径
	 * @return
	 */
	public static String getFullPathNoEndSeparator(String filename) {
		return FilenameUtils.getFullPathNoEndSeparator(filename);
	}

	/**
	 * 判断文件是否有某扩展名
	 *
	 * @param filename
	 *            文件完整路径
	 * @param extension
	 *            扩展名名称
	 * @return 若是，返回true，否则返回false
	 */
	public static boolean isExtension(String filename, String extension) {
		return FilenameUtils.isExtension(filename, extension);
	}

	/**
	 * 判断文件的扩展名是否是扩展名数组中的一个
	 *
	 * @param filename
	 *            文件完整路径
	 * @param extensions
	 *            扩展名名称
	 * @return 若是，返回true，否则返回false
	 */
	public static boolean isExtension(String filename, String[] extensions) {
		return FilenameUtils.isExtension(filename, extensions);
	}

	/**
	 * 规范化路径，合并其中的多个分隔符为一个,并转化为本地系统路径格式
	 *
	 * @param filename
	 *            文件完整路径
	 * @return
	 */
	public static String normalize(String filename) {
		return FilenameUtils.normalize(filename);
	}

	/**
	 * 规范化路径，合并其中的多个分隔符为一个,并转化为本地系统路径格式,若是路径，则不带最后的路径分隔符
	 *
	 * @param filename
	 *            文件完整路径
	 * @return
	 */
	public static String normalizeNoEndSeparator(String filename) {
		return FilenameUtils.normalizeNoEndSeparator(filename);
	}

	/**
	 * 把文件路径中的分隔符转换为unix的格式，也就是"/"
	 *
	 * @param path
	 *            文件完整路径
	 * @return 转换后的路径
	 */
	public static String separatorsToUnix(String path) {
		return FilenameUtils.separatorsToUnix(path);
	}

	/**
	 * 把文件路径中的分隔符转换为window的格式，也就是"\"
	 *
	 * @param path
	 *            文件完整路径
	 * @return 转换后的路径
	 */
	public static String separatorsToWindows(String path) {
		return FilenameUtils.separatorsToWindows(path);
	}

	/**
	 * 把文件路径中的分隔符转换当前系统的分隔符
	 *
	 * @param path
	 *            文件完整路径
	 * @return 转换后的路径
	 */
	public static String separatorsToSystem(String path) {
		return FilenameUtils.separatorsToSystem(path);
	}

	/**
	 * 提取文件的扩展名
	 *
	 * @param filename
	 *            文件名称
	 * @return 文件扩展名，若没有扩展名，则返回空字符串
	 */
	public static String getExtension(String filename) {
		return FilenameUtils.getExtension(filename);
	}

	/**
	 * 移出文件的扩展名
	 *
	 * @param filename
	 *            文件名称
	 * @return 若文件存在扩展名，则移出扩展名，然后返回移出后的值
	 */
	public static String removeExtension(String filename) {
		return FilenameUtils.removeExtension(filename);
	}

	/**
	 * 清除一个目录的内容，但不删除此目录
	 *
	 * @param directory
	 *            需要清除的目录
	 * @return true:清除成功 false:清除失败
	 */
	public static boolean cleanDirectory(File directory) {
		try {
			org.apache.commons.io.FileUtils.cleanDirectory(directory);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("清除目录出错");
		}
		return false;
	}

	/**
	 * 拷贝一个目录的内容到另外一个目录中
	 *
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyDirectory(File srcDir, File destDir) {
		return copyDirectory(srcDir, destDir, true);
	}

	/**
	 * 拷贝一个目录的内容到另外一个目录中
	 *
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyDirectory(String srcDir, String destDir) {
		return copyDirectory(new File(srcDir), new File(destDir));
	}

	/**
	 * 拷贝一个目录的内容到另外一个目录中
	 *
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @param preserveFileDate
	 *            是否保持文件日期
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyDirectory(File srcDir, File destDir, boolean preserveFileDate) {
		try {
			org.apache.commons.io.FileUtils.copyDirectory(srcDir, destDir, preserveFileDate);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("复制目录出错");
		}
		return false;
	}

	/**
	 * 拷贝源目录的内容到目的目录中(注：是拷贝到目的目录的里面)
	 *
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyDirectoryToDirectory(File srcDir, File destDir) {
		try {
			org.apache.commons.io.FileUtils.copyDirectoryToDirectory(srcDir, destDir);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("复制目录出错");
		}
		return false;
	}

	/**
	 * 拷贝源目录的内容到目的目录中(注：是拷贝到目的目录的里面)
	 *
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目的目录
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyDirectoryToDirectory(String srcDir, String destDir) {
		return copyDirectoryToDirectory(new File(srcDir), new File(destDir));
	}

	/**
	 * 拷贝文件
	 *
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目的文件
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile(File srcFile, File destFile) {
		return copyFile(srcFile, destFile, true);
	}

	/**
	 * 拷贝文件
	 *
	 * @param srcFile
	 *            源文件路径
	 * @param destFile
	 *            目的文件路径
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile(String srcFile, String destFile) {
		return copyFile(new File(srcFile), new File(destFile));
	}

	/**
	 * 拷贝文件
	 *
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目的文件
	 * @param preserveFileDate
	 *            是否保留文件日期
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFile(File srcFile, File destFile, boolean preserveFileDate) {
		try {
			org.apache.commons.io.FileUtils.copyFile(srcFile, destFile, preserveFileDate);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("复制文件出错");
		}
		return false;
	}

	/**
	 * 拷贝文件到某目录中
	 *
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目的目录
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFileToDirectory(File srcFile, File destDir) {
		try {
			org.apache.commons.io.FileUtils.copyFileToDirectory(srcFile, destDir);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("复制文件出错");
		}
		return false;
	}

	/**
	 * 拷贝文件到某目录中
	 *
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目的目录
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public static boolean copyFileToDirectory(String srcFile, String destDir) {
		return copyFileToDirectory(new File(srcFile), new File(destDir));
	}

	/**
	 * 删除一个目录和该目录下的所有内容
	 *
	 * @param directory
	 *            需要删除的目录
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean deleteDirectory(String directory) {
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(new File(directory));
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("删除目录出错");
		}
		return false;
	}

	/**
	 * 删除文件
	 *
	 * @param file
	 *            需要删除的文件路径
	 * @return true:删除成功 false:删除失败
	 */
	public static boolean deleteFile(String file) {
		try {
			org.apache.commons.io.FileUtils.forceDelete(new File(file));
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("删除文件出错");
		}
		return false;
	}

	/**
	 * 递归创建目录
	 *
	 * @param directory
	 *            目录
	 * @return
	 */
	public static boolean createDirectory(String directory) {
		try {
			org.apache.commons.io.FileUtils.forceMkdir(new File(directory));
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("创建目录出错");
		}
		return false;
	}

	/**
	 * 读入文件到字节数组中
	 *
	 * @param file
	 *            需要读取的文件路径
	 * @return 读取的字节数组，若读入失败，则返回null
	 */
	public static byte[] readFileToByteArray(String file) {
		try {
			byte[] bytes = org.apache.commons.io.FileUtils.readFileToByteArray(new File(file));
			return bytes;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("读取文件出错");
		}
		return null;
	}

	/**
	 * 读入文件到字串中
	 *
	 * @param file
	 *            需要读取的文件路径
	 * @return 读取的文件内容，若读入失败，则返回空字串
	 */
	public static String readFileToString(String file, String encoding) {
		try {
			if (StringHelper.isEmpty(encoding)) {
				encoding = "GBK";
			}
			String content = org.apache.commons.io.FileUtils.readFileToString(new File(file), encoding);
			return content;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("读取文件出错");
		}
		return "";
	}

	/**
	 * 读入文件到字串中
	 *
	 * @param file
	 *            需要读取的文件路径
	 * @return 读取的文件内容，若读入失败，则返回空字串
	 */
	public static String readFileToString(String file) {
		return readFileToString(file, "GBK");
	}

	/**
	 * 读入文本文件到一个按行分开的List中
	 *
	 * @param file
	 *            需要读取的文件路径
	 * @return 按行内容分开的List
	 */
	@SuppressWarnings("rawtypes")
	public static List readLines(String file) {
		return readLines(file, "GBK");
	}

	/**
	 * 读入文本文件到一个按行分开的List中
	 *
	 * @param file
	 *            需要读取的文件路径
	 * @return 按行内容分开的List
	 */
	@SuppressWarnings("rawtypes")
	public static List readLines(String file, String encoding) {

		try {
			if (StringHelper.isEmpty(encoding)) {
				encoding = "GBK";
			}
			List lineList = org.apache.commons.io.FileUtils.readLines(new File(file), encoding);
			return lineList;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("读取文件出错");
		}
		return null;

	}

	/**
	 * 递归求一个目录的容量大小
	 *
	 * @param directory
	 *            需要计算容量的目录路径
	 * @return 容量的大小(字节数)
	 */
	public static long sizeOfDirectory(String directory) {
		return org.apache.commons.io.FileUtils.sizeOfDirectory(new File(directory));
	}

	/**
	 * 写字节数组到文件中，若文件不存在，则建立新文件
	 *
	 * @param file
	 *            需要写的文件的路径
	 * @param data
	 *            需要写入的字节数据
	 * @return true:写入成功 false:写入失败
	 */
	public static boolean writeToFile(String file, byte[] data) {
		try {
			org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(file), data);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("写文件出错");
		}
		return false;
	}

	/**
	 * 写字串到文件中，若文件不存在，则建立新文件
	 *
	 * @param file
	 *            需要写的文件的路径
	 * @param data
	 *            需要写入的字串
	 * @return true:写入成功 false:写入失败
	 */
	public static boolean writeToFile(String file, String data) {
		return writeToFile(file, data, "GBK");
	}

	/**
	 * 写字串到文件中，若文件不存在，则建立新文件
	 *
	 * @param file
	 *            需要写的文件的路径
	 * @param data
	 *            需要写入的字串
	 * @param dncoding
	 *            文件编码，默认为GBK
	 * @return true:写入成功 false:写入失败
	 */
	public static boolean writeToFile(String file, String data, String encoding) {
		try {
			if (encoding == null || "".equals(encoding)) {
				encoding = "GBK";
			}
			org.apache.commons.io.FileUtils.writeStringToFile(new File(file), data, encoding);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("写文件出错");
		}
		return false;
	}

	/**
	 * 建立由filePathName指定的文件，若文件路径中的目录不存在，则先建立目录
	 *
	 * @param filePathName
	 *            文件路径全名
	 * @return
	 */
	public static boolean createNewFile(String filePathName) {
		String filePath = FileHelper.getFullPath(filePathName);
		// 若目录不存在，则建立目录
		if (!FileHelper.exists(filePath)) {
			if (!createDirectory(filePath)) {
				return false;
			}
		}

		try {
			File file = new File(filePathName);
			return file.createNewFile();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("创建文件出错");
			return false;
		}
	}

	/**
	 * 判断文件和目录是否已存在
	 *
	 * @param filePath
	 *            文件和目录完整路径
	 * @return tru:存在 false：不存在
	 */
	public static boolean exists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 判断特定的路径是否为文件
	 *
	 * @param filePath
	 *            文件完整的路径
	 * @return 若是文件，则返回true，否则返回false
	 */
	public static boolean isFile(String filePath) {
		File file = new File(filePath);
		return file.isFile();
	}

	/**
	 * 判断特定的路径是否为目录
	 *
	 * @param filePath
	 *            文件完整的路径
	 * @return 若是目录，则返回true，否则返回false
	 */
	public static boolean isDirectory(String filePath) {
		File file = new File(filePath);
		return file.isDirectory();
	}

	/**
	 * 更改文件的名称，若不在同一个目录下,则系统会移动文件
	 *
	 * @param srcFile
	 *            源文件路径名称
	 * @param destFile
	 *            目的文件路径名称
	 * @return
	 */
	public static boolean renameTo(String srcFile, String destFile) {
		File file = new File(srcFile);
		return file.renameTo(new File(destFile));
	}

	/**
	 *
	 * 描述：根据document生成Xml文件 作者：刘宝 时间：Jun 9, 2010 3:16:11 PM
	 *
	 * @param fileName
	 *            生成文件的路径
	 * @param document
	 * @param encoding
	 *            编码格式
	 * @return
	 */
	public static boolean WriteToXMLFile(String fileName, Document document, String encoding) {
		createNewFile(fileName);
		boolean success = false;
		/** 格式化输出,类型IE浏览一样 */
		OutputFormat format = OutputFormat.createPrettyPrint();
		/** 指定XML编码 */
		format.setEncoding(encoding);
		XMLWriter writer = null;
		try {
			/** 将document中的内容写入文件中 */
			writer = new XMLWriter(new FileOutputStream(new File(fileName)), format);
			writer.write(document);
			writer.flush();
			success = true;
			/** 执行成功,需返回true */
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("写文件出错");
		} finally {
			if (writer != null) {
				try {
					writer.close();
					writer = null;
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Convert code Error");
				}
			}
		}
		return success;
	}

	/**
     * 获取文件的后缀名并转化成大写
     *  
     * @param fileName
     *            文件名
     * @return
     */  
    public String getFileSuffix(String fileName) throws Exception {  
        return fileName.substring(fileName.lastIndexOf(".") + 1,  
                fileName.length()).toUpperCase();  
    }  

    /**
     * 创建多级目录
     *  
     * @param path
     *            目录的绝对路径
     */  
    public void createMultilevelDir(String path) {  
        try {  
            StringTokenizer st = new StringTokenizer(path, "/");  
            String path1 = st.nextToken() + "/";  
            String path2 = path1;  
            while (st.hasMoreTokens()) {  

                path1 = st.nextToken() + "/";  
                path2 += path1;  
                File inbox = new File(path2);  
                if (!inbox.exists())  
                    inbox.mkdir();  

            }  
        } catch (Exception e) {  
            System.out.println("目录创建失败" + e);  
            e.printStackTrace();  
        }  

    }  

    /**
     * 删除文件/目录(递归删除文件/目录)
     *  
     * @param path
     *            文件或文件夹的绝对路径
     */  
    public void deleteAll(String dirpath) {  
        if (dirpath == null) {  
            System.out.println("目录为空");  
        } else {  
            File path = new File(dirpath);  
            try {  
                if (!path.exists())  
                    return;// 目录不存在退出  
                if (path.isFile()) // 如果是文件删除  
                {  
                    path.delete();  
                    return;  
                }  
                File[] files = path.listFiles();// 如果目录中有文件递归删除文件  
                for (int i = 0; i < files.length; i++) {  
                    deleteAll(files[i].getAbsolutePath());  
                }  
                path.delete();  

            } catch (Exception e) {  
                System.out.println("文件/目录 删除失败" + e);  
                e.printStackTrace();  
            }  
        }  
    }  

    /**
     * 文件/目录 重命名
     *  
     * @param oldPath
     *            原有路径（绝对路径）
     * @param newPath
     *            更新路径
     * @author lyf 注：不能修改上层次的目录
     */  
    public void renameDir(String oldPath, String newPath) {  
        File oldFile = new File(oldPath);// 文件或目录  
        File newFile = new File(newPath);// 文件或目录  
        try {  
            boolean success = oldFile.renameTo(newFile);// 重命名  
            if (!success) {  
                System.out.println("重命名失败");  
            } else {  
                System.out.println("重命名成功");  
            }  
        } catch (RuntimeException e) {  
            e.printStackTrace();  
        }  

    }  

    /**
     * 新建目录
     */  
    public static boolean newDir(String path) throws Exception {  
        File file = new File(path);  
        return file.mkdirs();//创建目录  
    }  

    /**
     * 删除目录
     */  
    public static boolean deleteDir(String path) throws Exception {  
        File file = new File(path);  
        if (!file.exists())  
            return false;// 目录不存在退出  
        if (file.isFile()) // 如果是文件删除  
        {  
            file.delete();  
            return false;  
        }  
        File[] files = file.listFiles();// 如果目录中有文件递归删除文件  
        for (int i = 0; i < files.length; i++) {  
            deleteDir(files[i].getAbsolutePath());  
        }  
        file.delete();  

        return file.delete();//删除目录  
    }  

    /**
     * 更新目录
     */  
    public static boolean updateDir(String path, String newPath) throws Exception {  
        File file = new File(path);  
        File newFile = new File(newPath);  
        return file.renameTo(newFile);  
    }

 // 删除文件夹  
    // param folderPath 文件夹完整绝对路径  
    public static void delFolder(String folderPath) {  
        try {  
            delAllFile(folderPath); // 删除完里面所有内容  
            String filePath = folderPath;  
            filePath = filePath.toString();  
            java.io.File myFilePath = new java.io.File(filePath);  
            myFilePath.delete(); // 删除空文件夹  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    // 删除指定文件夹下所有文件  
    // param path 文件夹完整绝对路径  
    public static boolean delAllFile(String path) {  
        boolean flag = false;  
        File file = new File(path);  
        if (!file.exists()) {  
            return flag;  
        }  
        if (!file.isDirectory()) {  
            return flag;  
        }  
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            if (temp.isFile()) {  
                temp.delete();  
            }  
            if (temp.isDirectory()) {  
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件  
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹  
                flag = true;  
            }  
        }  
        return flag;  
    }  

    public static void main(String d[]) throws Exception{  
        //deleteDir("d:/ff/dddf");  
        updateDir("D:\\TOOLS\\Tomcat 6.0\\webapps\\BCCCSM\\nationalExperiment/22222", "D:\\TOOLS\\Tomcat 6.0\\webapps\\BCCCSM\\nationalExperiment/224222");  
    }  
}
```
# HttpClientHelper.java
```java
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author H__D
 * @date 2016年10月19日 上午11:27:25
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientHelper {

    // utf-8字符编码
    private static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。
    private static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型。相当于form表单的形式，提交数据
    private static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // HTTP内容类型。相当于form表单的形式，提交数据
    private static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";


    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;

    // 请求配置
    private static RequestConfig requestConfig;

    static {

        try {
            //System.out.println("初始化HttpClientTest~~~开始");
			SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(
                    connectionRequestTimeout).setSocketTimeout(socketTimeout).setConnectTimeout(
                    connectTimeout).build();

            //System.out.println("初始化HttpClientTest~~~结束");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000).build();
    }

    private static CloseableHttpClient getHttpClient() {

        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();

        return httpClient;
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private static String sendHttpGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }



    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     */
    public static String sendHttpPost(String httpUrl) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }

    /**
     * 发送 get请求
     *
     * @param maps
     *            参数
     */
    public static String sendHttpGet(String httpUrl, Map<String, String> maps) {
        String param = convertStringParamter(maps);
//        System.out.println(param);
        return sendHttpGet(httpUrl+"?"+param);
    }



    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param fileLists
     *            附件
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (maps != null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
            }
        }
        if (fileLists != null) {
            for (File file : fileLists) {
                FileBody fileBody = new FileBody(file);
                meBuilder.addPart("files", fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     *
     */
    public static String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (params != null && params.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(params, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        String parem = convertStringParamter(maps);
        return sendHttpPost(httpUrl, parem);
    }




    /**
     * 发送 post请求 发送json数据
     *
     * @param httpUrl
     *            地址
     * @param paramsJson
     *            参数(格式 json)
     *
     */
    public static String sendHttpPostJson(String httpUrl, String paramsJson) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_JSON_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求 发送xml数据
     *
     * @param httpUrl   地址
     * @param paramsXml  参数(格式 Xml)
     *
     */
    public static String sendHttpPostXml(String httpUrl, String paramsXml) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (paramsXml != null && paramsXml.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsXml, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_TEXT_HTML);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }


    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     *
     * @param parameterMap
     *            需要转化的键值对集合
     * @return 字符串
     */
    @SuppressWarnings("rawtypes")
	private static String convertStringParamter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

//    public static void main(String[] args) throws Exception {
//        
////        System.out.println(sendHttpGet("http://www.baidu.com"));
//    	Map<String,String> param = new HashMap<String, String>();
//    	param.put("name", "1213131");
//    	param.put("age", "18");
//        System.out.println(sendHttpGet("http://127.0.0.1:8081/member/getMemberInfoByUserId",param));
//    
//    }
}
```
# HttpHelper.java
```java
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *   
 *
 * @Title: HttpHelper.java
 * @Package com.jarvis.base.util
 * @Description:http请求工具类
 * @author Jack 
 * @date 2017年9月2日 下午3:34:49
 * @version V1.0  
 */
public class HttpHelper {

	private static final int DEFAULT_INITIAL_BUFFER_SIZE = 4 * 1024; // 4 kB

	private HttpHelper() {

	}

	/**
	 * 返回对应URL地址的内容，注意，只返回正常响应(状态响应代码为200)的内容
	 *
	 * @param urlPath
	 *            需要获取内容的URL地址
	 * @return 获取的内容字节数组
	 */
	public static byte[] getURLContent(String urlPath) {
		HttpURLConnection conn = null;
		InputStream inStream = null;
		byte[] buffer = null;

		try {
			URL url = new URL(urlPath);
			HttpURLConnection.setFollowRedirects(false);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setConnectTimeout(10000); // 10秒
			conn.setReadTimeout(60000); // 60秒

			conn.connect();

			int repCode = conn.getResponseCode();
			if (repCode == 200) {
				inStream = conn.getInputStream();
				int contentLength = conn.getContentLength();
				buffer = getResponseBody(inStream, contentLength);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("获取内容失败");
		} finally {
			try {
				if (inStream != null) {
					inStream.close();
				}

				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("关闭连接失败");
			}
		}
		return buffer;
	}

	/**
	 * 描述：获取BODY部分的字节数组 时间：2010-9-7 下午03:16:46
	 *
	 * @param instream
	 * @param contentLength
	 * @return
	 * @throws Exception
	 */
	private static byte[] getResponseBody(InputStream instream, int contentLength) throws Exception {
		if (contentLength == -1) {
			System.out.println("Going to buffer response body of large or unknown size. ");
		}
		ByteArrayOutputStream outstream = new ByteArrayOutputStream(
				contentLength > 0 ? (int) contentLength : DEFAULT_INITIAL_BUFFER_SIZE);
		byte[] buffer = new byte[4096];
		int len;
		while ((len = instream.read(buffer)) > 0) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		return outstream.toByteArray();
	}

	@SuppressWarnings("unused")
	private static void readFixedLenToBuffer(InputStream inStream, byte[] buffer) throws Exception {
		int count = 0;
		int remainLength = buffer.length;
		int bufLength = buffer.length;
		int readLength = 0;
		do {
			count = inStream.read(buffer, readLength, remainLength);
			if (count == -1) // 已经到达末尾
			{
				if (readLength != bufLength) // 若实际读取的数据和需要读取的数据不匹配，则报错
				{
					throw new Exception("读取数据出错，不正确的数据结束");
				}
			}

			readLength += count;

			if (readLength == bufLength) // 已经读取完，则返回
			{
				return;
			}

			remainLength = bufLength - readLength;
		} while (true);
	}

	/**
	 * 返回对应URL地址的内容，注意，只返回正常响应(状态响应代码为200)的内容
	 *
	 * @param urlPath
	 *            需要获取内空的URL地址
	 * @param charset
	 *            字符集编码方式
	 * @return 获取的内容字串
	 */
	public static String getURLContent(String urlPath, String charset) {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(urlPath);
			HttpURLConnection.setFollowRedirects(false);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setConnectTimeout(10000); // 10秒
			conn.setReadTimeout(60000); // 60秒

			conn.connect();

			int repCode = conn.getResponseCode();
			if (repCode == 200) {
				int count = 0;
				char[] chBuffer = new char[1024];
				BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				while ((count = input.read(chBuffer)) != -1) {
					buffer.append(chBuffer, 0, count);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("获取内容失败");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("关闭连接失败");
			}
		}

		return buffer.toString();
	}

	public static String getURLContent(String urlPath, String requestData, String charset) {
		BufferedReader reader = null;
		HttpURLConnection conn = null;
		StringBuffer buffer = new StringBuffer();
		OutputStreamWriter out = null;
		try {
			URL url = new URL(urlPath);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setConnectTimeout(10000); // 10秒
			conn.setReadTimeout(60000); // 60秒

			out = new OutputStreamWriter(conn.getOutputStream(), charset);
			out.write(requestData);
			out.flush();

			int repCode = conn.getResponseCode();
			if (repCode == 200) {
				int count = 0;
				char[] chBuffer = new char[1024];
				BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				while ((count = input.read(chBuffer)) != -1) {
					buffer.append(chBuffer, 0, count);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("获取内容失败");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("关闭连接失败");
			}
		}
		return buffer.toString();
	}
}
```
# HttpUtils.java
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 用于模拟HTTP请求中GET/POST方式
 * @author landa
 *
 */
public class HttpUtils {  
    /**
     * 发送GET请求
     *  
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */  
    public static String sendGet(String url, Map<String, String> parameters) {
        String result="";
        BufferedReader in = null;// 读取响应输入流  
        StringBuffer sb = new StringBuffer();// 存储参数  
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数  
            if(parameters.size()==1){
                for(String name:parameters.keySet()){
                    sb.append(name).append("=").append(
                            java.net.URLEncoder.encode(parameters.get(name),  
                            "UTF-8"));
                }
                params=sb.toString();
            }else{
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }
            String full_url = url + "?" + params;
//            System.out.println(full_url);
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(full_url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 建立实际的连接  
            httpConn.connect();  
            // 响应头部获取  
//            Map<String, List<String>> headers = httpConn.getHeaderFields();  
            // 遍历所有的响应头字段  
//            for (String key : headers.keySet()) {  
//                System.out.println(key + "\t：\t" + headers.get(key));  
//            }  
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }
        return result ;
    }  

    /**
     * 发送POST请求
     *  
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */  
    public static String sendPost(String url, Map<String, String> parameters) {  
        String result = "";// 返回的结果  
        BufferedReader in = null;// 读取响应输入流  
        PrintWriter out = null;  
        StringBuffer sb = new StringBuffer();// 处理请求参数  
        String params = "";// 编码之后的参数  
        try {  
            // 编码请求参数  
            if (parameters.size() == 1) {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8"));  
                }  
                params = sb.toString();  
            } else {  
                for (String name : parameters.keySet()) {  
                    sb.append(name).append("=").append(  
                            java.net.URLEncoder.encode(parameters.get(name),  
                                    "UTF-8")).append("&");  
                }  
                String temp_params = sb.toString();  
                params = temp_params.substring(0, temp_params.length() - 1);  
            }  
            // 创建URL对象  
            java.net.URL connURL = new java.net.URL(url);  
            // 打开URL连接  
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
                    .openConnection();  
            // 设置通用属性  
            httpConn.setRequestProperty("Accept", "*/*");  
            httpConn.setRequestProperty("Connection", "Keep-Alive");  
            httpConn.setRequestProperty("User-Agent",  
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
            // 设置POST方式  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            // 获取HttpURLConnection对象对应的输出流  
            out = new PrintWriter(httpConn.getOutputStream());  
            // 发送请求参数  
            out.write(params);
//            System.out.println(params);
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
            in = new BufferedReader(new InputStreamReader(httpConn  
                    .getInputStream(), "UTF-8"));  
            String line;  
            // 读取返回的内容  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }

    /**
     *Author:Jack
     *Time:2017年9月5日下午2:20:26
     *@param url
     *@param parameters
     *@return
     *Return:String
     *Description:发送Post请求，并且发送的是Json格式的数据。
     */
	public static String sendPostJson(String url, Map<String, String> parameters) {
		 String result = "";// 返回的结果  
	        BufferedReader in = null;// 读取响应输入流  
	        PrintWriter out = null;  
	        String params = "";// 编码之后的参数  
	        try {  
	            if(parameters!=null && parameters.size()>0){
	            	params = FastJsonUtil.toJSONString(parameters);
	            }
	            // 创建URL对象  
	            java.net.URL connURL = new java.net.URL(url);  
	            // 打开URL连接  
	            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL  
	                    .openConnection();  
	            // 设置通用属性  
	            httpConn.setRequestProperty("Accept", "*/*");  
	            httpConn.setRequestProperty("Connection", "Keep-Alive");  
	            httpConn.setRequestProperty("User-Agent",  
	                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  
	            // 设置POST方式  
	            httpConn.setDoInput(true);  
	            httpConn.setDoOutput(true);  
	            // 获取HttpURLConnection对象对应的输出流  
	            out = new PrintWriter(httpConn.getOutputStream());  
	            // 发送请求参数  
	            out.write(params);
//	            System.out.println(params);
	            // flush输出流的缓冲  
	            out.flush();  
	            // 定义BufferedReader输入流来读取URL的响应，设置编码方式  
	            in = new BufferedReader(new InputStreamReader(httpConn  
	                    .getInputStream(), "UTF-8"));  
	            String line;  
	            // 读取返回的内容  
	            while ((line = in.readLine()) != null) {  
	                result += line;  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                if (out != null) {  
	                    out.close();  
	                }  
	                if (in != null) {  
	                    in.close();  
	                }  
	            } catch (IOException ex) {  
	                ex.printStackTrace();  
	            }  
	        }  
	        return result;  
	}

    /**
     * 主函数，测试请求
     *  
     * @param args
     */  
//    public static void main(String[] args) {  
//        Map<String, String> parameters = new HashMap<String, String>();  
//        parameters.put("name", "sarin");  
//        String result =sendGet("http://www.baidu.com", parameters);
//        System.out.println(result);
//    }  
}
```
# ImageHelper.java
```java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.coobird.thumbnailator.Thumbnails;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *   
 *
 * @Title: ImageHelper.java
 * @Package com.jarvis.base.util
 * @Description:图片处理工具类。
 * @author Jack 
 * @date 2017年9月2日 下午3:04:40
 * @version V1.0  
 */
@SuppressWarnings("restriction")
public class ImageHelper {
	/**
	 * @描述：Base64解码并生成图片
	 * @入参：@param imgStr
	 * @入参：@param imgFile
	 * @入参：@throws IOException
	 * @出参：void
	 */
	public static void generateImage(String imgStr, String imgFile) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		// Base64解码
		byte[] bytes;
		OutputStream out = null;
		try {
			bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成图片
			out = new FileOutputStream(imgFile);
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @throws IOException
	 * @描述：根据路径得到base编码后图片
	 * @入参：@param imgFilePath
	 * @入参：@return
	 * @出参：String
	 */
	public static String getImageStr(String imgFilePath) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			throw new IOException();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * @throws IOException
	 * @描述：图片旋转
	 * @入参：@param base64In 传入的图片base64
	 * @入参：@param angle 图片旋转度数
	 * @入参：@throws Exception
	 * @出参：String 传出的图片base64
	 */
	public static String imgAngleRevolve(String base64In, int angle) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			Thumbnails.of(base64ToIo(base64In)).scale(1.0).rotate(angle).toOutputStream(os);
		} catch (IOException e) {
			throw new IOException();
		}
		byte[] bs = os.toByteArray();
		String s = new BASE64Encoder().encode(bs);
		return s;
	}

	/**
	 * @描述：base64转为io流
	 * @入参：@param strBase64
	 * @入参：@return
	 * @入参：@throws IOException
	 * @出参：InputStream
	 */
	public static InputStream base64ToIo(String strBase64) throws IOException {
		// 解码，然后将字节转换为文件
		byte[] bytes = new BASE64Decoder().decodeBuffer(strBase64); // 将字符串转换为byte数组
		return new ByteArrayInputStream(bytes);
	}
}
```
# IpMacUtil.java
```java
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *   
 *
 * @Title: IpMacUtil.java
 * @Package com.jarvis.base.util
 * @Description:Ip工具类。
 * @author Jack 
 * @date 2017年9月2日 下午3:05:00
 * @version V1.0  
 */
public class IpMacUtil {

	/**
	 * 隐藏IP的最后一段
	 *
	 * @param ip
	 *            需要进行处理的IP
	 * @return 隐藏后的IP
	 */
	public static String hideIp(String ip) {
		if (StringHelper.isEmpty(ip)) {
			return "";
		}

		int pos = ip.lastIndexOf(".");
		if (pos == -1) {
			return ip;
		}

		ip = ip.substring(0, pos + 1);
		ip = ip + "*";
		return ip;
	}

	/**
	 * 获取IP地址.
	 *
	 * @param request
	 *            HTTP请求.
	 * @param response
	 *            HTTP响应.
	 * @param url
	 *            需转发到的URL.
	 */
	// public static String getIpAddr(HttpServletRequest request)
	// {
	// String ip = request.getHeader("x-forwarded-for");
	// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	// {
	// ip = request.getHeader("Proxy-Client-IP");
	// }
	// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	// {
	// ip = request.getHeader("WL-Proxy-Client-IP");
	// }
	// if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	// {
	// ip = request.getRemoteAddr();
	// }
	// return ip;
	// }

	/**
	 * 判断该字串是否为IP
	 *
	 * @param ipStr
	 *            IP字串
	 * @return
	 */
	public static boolean isIP(String ipStr) {
		String ip = "(25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";
		String ipDot = ip + "\\.";
		return ipStr.matches(ipDot + ipDot + ipDot + ip);
	}

	/**
	 * 获取客户端Mac
	 *
	 * @param ip
	 * @return
	 */
	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			return "";
		}
		return macAddress;
	}
}
```
# MailHelper.java
```java

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**  
* @Title: MailHelper.java
* @Package com.jarvis.base.util
* @Description:mail工具类
* @author Jack 
* @date 2017年9月2日 下午3:39:46
* @version V1.0  
*/
public class MailHelper
{
    /**
     * 简单的发邮件方式    邮件内容只有标题和邮件内容  支持单个个用户发送
     *
     * @param host             邮件服务器地址
     * @param username         连接邮件服务器的用户名称
     * @param password         连接邮件服务器的用户密码
     * @param subject          邮件的主题
     * @param contents         邮件的内容
     * @param toEmailAddress   收件人的邮件地址
     * @param fromEmailAddress 发件人的邮件地址
     * @throws EmailException
     */
    public static void sendSimpleEmail(String host, String username, String password, String subject, String contents,
                                       String toEmailAddress, String fromEmailAddress) throws EmailException
    {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(host);
        email.setAuthentication(username, password);
        email.addTo(toEmailAddress);
        email.setFrom(fromEmailAddress, fromEmailAddress);
        email.setSubject(subject);
        email.setContent((Object)contents, "text/plain;charset=GBK");
        email.send();
    }

    /**
     * 简单的发邮件方式    邮件内容只有标题和邮件内容  支持多个用户批量发送
     *
     * @param host             邮件服务器地址
     * @param username         连接邮件服务器的用户名称
     * @param password         连接邮件服务器的用户密码
     * @param subject          邮件的主题
     * @param contents         邮件的内容
     * @param toEmailAddress   收件人的邮件地址
     * @param fromEmailAddress 发件人的邮件地址
     * @throws EmailException
     */
    public static void sendSimpleEmail(String host, String username, String password, String subject, String contents, String [] toEmailAddress, String fromEmailAddress) throws EmailException
    {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(host);
        email.setAuthentication(username, password);
        //发送给多个人
        for (int i = 0; i < toEmailAddress.length; i++)
        {
            email.addTo(toEmailAddress[i], toEmailAddress[i]);
        }
        email.setFrom(fromEmailAddress, fromEmailAddress);
        email.setSubject(subject);
        email.setContent((Object)contents, "text/plain;charset=GBK");
        email.send();
    }

    /**
     * 发送带附件的邮件方式  邮件内容有标题和邮件内容和附件，附件可以是本地机器上的文本，也可以是web上的一个URL 文件，
     * 当为web上的一个URL文件时，此方法可以将WEB中的URL文件先下载到本地，再发送给收入用户
     *
     * @param host             邮件服务器地址
     * @param username         连接邮件服务器的用户名称
     * @param password         连接邮件服务器的用户密码
     * @param subject          邮件的主题
     * @param contents         邮件的内容
     * @param toEmailAddress   收件人的邮件地址
     * @param fromEmailAddress 发件人的邮件地址
     * @param multiPaths       附件文件数组
     * @throws EmailException
     */

    public static void sendMultiPartEmail(String host, String username, String password, String subject,
                                          String contents, String toEmailAddress, String fromEmailAddress,
                                          String []multiPaths) throws MalformedURLException, EmailException
    {
        List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
        if (multiPaths != null)
        {
            for (int i = 0; i < multiPaths.length; i++)
            {
                EmailAttachment attachment = new EmailAttachment();
                if (multiPaths[i].indexOf("http") == -1)   //判断当前这个文件路径是否在本地  如果是：setPath  否则  setURL;
                {
                    attachment.setPath(multiPaths[i]);
                }
                else
                {
                    attachment.setURL(new URL(multiPaths[i]));
                }
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("");
                attachmentList.add(attachment);
            }
        }

        //发送邮件信息
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(host);
        email.setAuthentication(username, password);
        email.addTo(toEmailAddress);
        email.setFrom(fromEmailAddress, fromEmailAddress);
        email.setSubject(subject);
        email.setMsg(contents);   //注意这个不要使用setContent这个方法  setMsg不会出现乱码
        for (int i = 0; i < attachmentList.size(); i++)   //添加多个附件
        {
            email.attach((EmailAttachment) attachmentList.get(i));
        }
        email.send();
    }

    /**
     * 发送带附件的邮件方式  邮件内容有标题和邮件内容和附件，附件可以是本地机器上的文本，也可以是web上的一个URL 文件，
     * 当为web上的一个URL文件时，此方法可以将WEB中的URL文件先下载到本地，再发送给收入用户
     *
     * @param host             邮件服务器地址
     * @param username         连接邮件服务器的用户名称
     * @param password         连接邮件服务器的用户密码
     * @param subject          邮件的主题
     * @param contents         邮件的内容
     * @param toEmailAddress   收件人的邮件地址数组
     * @param fromEmailAddress 发件人的邮件地址
     * @param multiPaths       附件文件数组
     * @throws EmailException
     */

    public static void sendMultiPartEmail(String host, String username, String password, String subject,
                                          String contents, String[] toEmailAddress, String fromEmailAddress,
                                          String []multiPaths) throws MalformedURLException, EmailException
    {
        List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
        if (multiPaths != null)
        {
            for (int i = 0; i < multiPaths.length; i++)
            {
                EmailAttachment attachment = new EmailAttachment();
                if (multiPaths[i].indexOf("http") == -1)   //判断当前这个文件路径是否在本地  如果是：setPath  否则  setURL;
                {
                    attachment.setPath(multiPaths[i]);
                }
                else
                {
                    attachment.setURL(new URL(multiPaths[i]));
                }
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("");
                attachmentList.add(attachment);
            }
        }

        //发送邮件信息
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(host);
        email.setAuthentication(username, password);
        //发送给多个人
        for (int i = 0; i < toEmailAddress.length; i++)
        {
            email.addTo(toEmailAddress[i], toEmailAddress[i]);
        }
        email.setFrom(fromEmailAddress, fromEmailAddress);
        email.setSubject(subject);
        email.setMsg(contents);   //注意这个不要使用setContent这个方法  setMsg不会出现乱码
        for (int i = 0; i < attachmentList.size(); i++)   //添加多个附件
        {
            email.attach((EmailAttachment) attachmentList.get(i));
        }
        email.send();
    }


    /**
     * 发送Html格式的邮件
     *
     * @param host             邮件服务器地址
     * @param username         连接邮件服务器的用户名称
     * @param password         连接邮件服务器的用户密码
     * @param subject          邮件的主题
     * @param contents         邮件的内容
     * @param toEmailAddress   收件人的邮件地址
     * @param fromEmailAddress 发件人邮件地址
     * @throws EmailException
     */
    public static void sendHtmlEmail(String host, String username, String password, String subject, String contents, String toEmailAddress, String fromEmailAddress) throws EmailException
    {
        HtmlEmail email = new HtmlEmail();
        //email.setDebug(true);
        email.setHostName(host);
        email.setAuthentication(username, password);
        email.addTo(toEmailAddress);
        email.setFrom(fromEmailAddress, fromEmailAddress);
        email.setSubject(subject);
        email.setHtmlMsg(CharHelper.GBKto8859(contents));
        email.send();
    }

    /**
     * 发送Html格式的邮件
     *
     * @param host             邮件服务器地址
     * @param username         连接邮件服务器的用户名称
     * @param password         连接邮件服务器的用户密码
     * @param subject          邮件的主题
     * @param contents         邮件的内容
     * @param toEmailAddress   收件人的邮件地址数组
     * @param fromEmailAddress 发件人邮件地址
     * @throws EmailException
     */
    public static void sendHtmlEmail(String host, String username, String password, String subject, String contents, String[] toEmailAddress, String fromEmailAddress) throws EmailException
    {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(host);
        email.setAuthentication(username, password);
        //发送给多个人
        for (int i = 0; i < toEmailAddress.length; i++)
        {
            email.addTo(toEmailAddress[i], toEmailAddress[i]);
        }
        email.setFrom(fromEmailAddress, fromEmailAddress);
        email.setSubject(subject);
        email.setHtmlMsg(CharHelper.GBKto8859(contents));
        email.send();
    }
}
```
# MapHelper.java
```java
import java.util.Map;
/**
 *   
 *
 * @Title: MapHelper.java
 * @Package com.jarvis.base.util
 * @Description:Map工具类
 * @author Jack 
 * @date 2017年9月2日 下午3:42:01
 * @version V1.0  
 */
public class MapHelper {
	/**
	 * 获得字串值
	 *
	 * @param name
	 *            键值名称
	 * @return 若不存在，则返回空字串
	 */
	public static String getString(Map<?, ?> map, String name) {
		if (name == null || name.equals("")) {
			return "";
		}

		String value = "";
		if (map.containsKey(name) == false) {
			return "";
		}
		Object obj = map.get(name);
		if (obj != null) {
			value = obj.toString();
		}
		obj = null;

		return value;
	}

	/**
	 * 返回整型值
	 *
	 * @param name
	 *            键值名称
	 * @return 若不存在，或转换失败，则返回0
	 */
	public static int getInt(Map<?, ?> map, String name) {
		if (name == null || name.equals("")) {
			return 0;
		}

		int value = 0;
		if (map.containsKey(name) == false) {
			return 0;
		}

		Object obj = map.get(name);
		if (obj == null) {
			return 0;
		}

		if (!(obj instanceof Integer)) {
			try {
				value = Integer.parseInt(obj.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("name[" + name + "]对应的值不是数字，返回0");
				value = 0;
			}
		} else {
			value = ((Integer) obj).intValue();
			obj = null;
		}

		return value;
	}

	/**
	 * 获取长整型值
	 *
	 * @param name
	 *            键值名称
	 * @return 若不存在，或转换失败，则返回0
	 */
	public static long getLong(Map<?, ?> map, String name) {
		if (name == null || name.equals("")) {
			return 0;
		}

		long value = 0;
		if (map.containsKey(name) == false) {
			return 0;
		}

		Object obj = map.get(name);
		if (obj == null) {
			return 0;
		}

		if (!(obj instanceof Long)) {
			try {
				value = Long.parseLong(obj.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("name[" + name + "]对应的值不是数字，返回0");
				value = 0;
			}
		} else {
			value = ((Long) obj).longValue();
			obj = null;
		}

		return value;
	}

	/**
	 * 获取Float型值
	 *
	 * @param name
	 *            键值名称
	 * @return 若不存在，或转换失败，则返回0
	 */
	public static float getFloat(Map<?, ?> map, String name) {
		if (name == null || name.equals("")) {
			return 0;
		}

		float value = 0;
		if (map.containsKey(name) == false) {
			return 0;
		}

		Object obj = map.get(name);
		if (obj == null) {
			return 0;
		}

		if (!(obj instanceof Float)) {
			try {
				value = Float.parseFloat(obj.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("name[" + name + "]对应的值不是数字，返回0");
				value = 0;
			}
		} else {
			value = ((Float) obj).floatValue();
			obj = null;
		}

		return value;
	}

	/**
	 * 获取Double型值
	 *
	 * @param name
	 *            键值名称
	 * @return 若不存在，或转换失败，则返回0
	 */
	public static double getDouble(Map<?, ?> map, String name) {
		if (name == null || name.equals("")) {
			return 0;
		}

		double value = 0;
		if (map.containsKey(name) == false) {
			return 0;
		}

		Object obj = map.get(name);
		if (obj == null) {
			return 0;
		}

		if (!(obj instanceof Double)) {
			try {
				value = Double.parseDouble(obj.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
				System.err.println("name[" + name + "]对应的值不是数字，返回0");
				value = 0;
			}
		} else {
			value = ((Double) obj).doubleValue();
			obj = null;
		}

		return value;
	}

	/**
	 * 获取Bool值
	 *
	 * @param name
	 *            键值名称
	 * @return 若不存在，或转换失败，则返回false
	 */
	public static boolean getBoolean(Map<?, ?> map, String name) {
		if (name == null || name.equals("")) {
			return false;
		}

		boolean value = false;
		if (map.containsKey(name) == false) {
			return false;
		}
		Object obj = map.get(name);
		if (obj == null) {
			return false;
		}

		if (obj instanceof Boolean) {
			return ((Boolean) obj).booleanValue();
		}

		value = Boolean.valueOf(obj.toString()).booleanValue();
		obj = null;
		return value;
	}
}
```
# MD5Code.java
```java
/**
 * MD5编码工具类
 *
 */
public class MD5Code {
	static final int S11 = 7;

	static final int S12 = 12;

	static final int S13 = 17;

	static final int S14 = 22;

	static final int S21 = 5;

	static final int S22 = 9;

	static final int S23 = 14;

	static final int S24 = 20;

	static final int S31 = 4;

	static final int S32 = 11;

	static final int S33 = 16;

	static final int S34 = 23;

	static final int S41 = 6;

	static final int S42 = 10;

	static final int S43 = 15;

	static final int S44 = 21;

	static final byte[] PADDING = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0 };

	private long[] state = new long[4];// state (ABCD)

	private long[] count = new long[2];// number of bits, modulo 2^64 (lsb

	// first)

	private byte[] buffer = new byte[64]; // input buffer


	public String digestHexStr;

	private byte[] digest = new byte[16];

	public String getMD5ofStr(String inbuf) {
		md5Init();
		md5Update(inbuf.getBytes(), inbuf.length());
		md5Final();
		digestHexStr = "";
		for (int i = 0; i < 16; i++) {
			digestHexStr += byteHEX(digest[i]);
		}
		return digestHexStr;
	}

	public MD5Code() {
		md5Init();
		return;
	}

	private void md5Init() {
		count[0] = 0L;
		count[1] = 0L;
		// /* Load magic initialization constants.
		state[0] = 0x67452301L;
		state[1] = 0xefcdab89L;
		state[2] = 0x98badcfeL;
		state[3] = 0x10325476L;
		return;
	}

	private long F(long x, long y, long z) {
		return (x & y) | ((~x) & z);
	}

	private long G(long x, long y, long z) {
		return (x & z) | (y & (~z));
	}

	private long H(long x, long y, long z) {
		return x ^ y ^ z;
	}

	private long I(long x, long y, long z) {
		return y ^ (x | (~z));
	}

	private long FF(long a, long b, long c, long d, long x, long s, long ac) {
		a += F(b, c, d) + x + ac;
		a = ((int) a << s) | ((int) a >>> (32 - s));
		a += b;
		return a;
	}

	private long GG(long a, long b, long c, long d, long x, long s, long ac) {
		a += G(b, c, d) + x + ac;
		a = ((int) a << s) | ((int) a >>> (32 - s));
		a += b;
		return a;
	}

	private long HH(long a, long b, long c, long d, long x, long s, long ac) {
		a += H(b, c, d) + x + ac;
		a = ((int) a << s) | ((int) a >>> (32 - s));
		a += b;
		return a;
	}

	private long II(long a, long b, long c, long d, long x, long s, long ac) {
		a += I(b, c, d) + x + ac;
		a = ((int) a << s) | ((int) a >>> (32 - s));
		a += b;
		return a;
	}

	private void md5Update(byte[] inbuf, int inputLen) {
		int i, index, partLen;
		byte[] block = new byte[64];
		index = (int) (count[0] >>> 3) & 0x3F;
		// /* Update number of bits */
		if ((count[0] += (inputLen << 3)) < (inputLen << 3))
			count[1]++;
		count[1] += (inputLen >>> 29);
		partLen = 64 - index;
		// Transform as many times as possible.
		if (inputLen >= partLen) {
			md5Memcpy(buffer, inbuf, index, 0, partLen);
			md5Transform(buffer);
			for (i = partLen; i + 63 < inputLen; i += 64) {
				md5Memcpy(block, inbuf, 0, i, 64);
				md5Transform(block);
			}
			index = 0;
		} else
			i = 0;
		// /* Buffer remaining input */
		md5Memcpy(buffer, inbuf, index, i, inputLen - i);
	}

	private void md5Final() {
		byte[] bits = new byte[8];
		int index, padLen;
		// /* Save number of bits */
		Encode(bits, count, 8);
		// /* Pad out to 56 mod 64.
		index = (int) (count[0] >>> 3) & 0x3f;
		padLen = (index < 56) ? (56 - index) : (120 - index);
		md5Update(PADDING, padLen);
		// /* Append length (before padding) */
		md5Update(bits, 8);
		// /* Store state in digest */
		Encode(digest, state, 16);
	}

	private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos,
			int len) {
		int i;
		for (i = 0; i < len; i++)
			output[outpos + i] = input[inpos + i];
	}

	private void md5Transform(byte block[]) {
		long a = state[0], b = state[1], c = state[2], d = state[3];
		long[] x = new long[16];
		Decode(x, block, 64);
		/* Round 1 */
		a = FF(a, b, c, d, x[0], S11, 0xd76aa478L); /* 1 */
		d = FF(d, a, b, c, x[1], S12, 0xe8c7b756L); /* 2 */
		c = FF(c, d, a, b, x[2], S13, 0x242070dbL); /* 3 */
		b = FF(b, c, d, a, x[3], S14, 0xc1bdceeeL); /* 4 */
		a = FF(a, b, c, d, x[4], S11, 0xf57c0fafL); /* 5 */
		d = FF(d, a, b, c, x[5], S12, 0x4787c62aL); /* 6 */
		c = FF(c, d, a, b, x[6], S13, 0xa8304613L); /* 7 */
		b = FF(b, c, d, a, x[7], S14, 0xfd469501L); /* 8 */
		a = FF(a, b, c, d, x[8], S11, 0x698098d8L); /* 9 */
		d = FF(d, a, b, c, x[9], S12, 0x8b44f7afL); /* 10 */
		c = FF(c, d, a, b, x[10], S13, 0xffff5bb1L); /* 11 */
		b = FF(b, c, d, a, x[11], S14, 0x895cd7beL); /* 12 */
		a = FF(a, b, c, d, x[12], S11, 0x6b901122L); /* 13 */
		d = FF(d, a, b, c, x[13], S12, 0xfd987193L); /* 14 */
		c = FF(c, d, a, b, x[14], S13, 0xa679438eL); /* 15 */
		b = FF(b, c, d, a, x[15], S14, 0x49b40821L); /* 16 */
		/* Round 2 */
		a = GG(a, b, c, d, x[1], S21, 0xf61e2562L); /* 17 */
		d = GG(d, a, b, c, x[6], S22, 0xc040b340L); /* 18 */
		c = GG(c, d, a, b, x[11], S23, 0x265e5a51L); /* 19 */
		b = GG(b, c, d, a, x[0], S24, 0xe9b6c7aaL); /* 20 */
		a = GG(a, b, c, d, x[5], S21, 0xd62f105dL); /* 21 */
		d = GG(d, a, b, c, x[10], S22, 0x2441453L); /* 22 */
		c = GG(c, d, a, b, x[15], S23, 0xd8a1e681L); /* 23 */
		b = GG(b, c, d, a, x[4], S24, 0xe7d3fbc8L); /* 24 */
		a = GG(a, b, c, d, x[9], S21, 0x21e1cde6L); /* 25 */
		d = GG(d, a, b, c, x[14], S22, 0xc33707d6L); /* 26 */
		c = GG(c, d, a, b, x[3], S23, 0xf4d50d87L); /* 27 */
		b = GG(b, c, d, a, x[8], S24, 0x455a14edL); /* 28 */
		a = GG(a, b, c, d, x[13], S21, 0xa9e3e905L); /* 29 */
		d = GG(d, a, b, c, x[2], S22, 0xfcefa3f8L); /* 30 */
		c = GG(c, d, a, b, x[7], S23, 0x676f02d9L); /* 31 */
		b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8aL); /* 32 */
		/* Round 3 */
		a = HH(a, b, c, d, x[5], S31, 0xfffa3942L); /* 33 */
		d = HH(d, a, b, c, x[8], S32, 0x8771f681L); /* 34 */
		c = HH(c, d, a, b, x[11], S33, 0x6d9d6122L); /* 35 */
		b = HH(b, c, d, a, x[14], S34, 0xfde5380cL); /* 36 */
		a = HH(a, b, c, d, x[1], S31, 0xa4beea44L); /* 37 */
		d = HH(d, a, b, c, x[4], S32, 0x4bdecfa9L); /* 38 */
		c = HH(c, d, a, b, x[7], S33, 0xf6bb4b60L); /* 39 */
		b = HH(b, c, d, a, x[10], S34, 0xbebfbc70L); /* 40 */
		a = HH(a, b, c, d, x[13], S31, 0x289b7ec6L); /* 41 */
		d = HH(d, a, b, c, x[0], S32, 0xeaa127faL); /* 42 */
		c = HH(c, d, a, b, x[3], S33, 0xd4ef3085L); /* 43 */
		b = HH(b, c, d, a, x[6], S34, 0x4881d05L); /* 44 */
		a = HH(a, b, c, d, x[9], S31, 0xd9d4d039L); /* 45 */
		d = HH(d, a, b, c, x[12], S32, 0xe6db99e5L); /* 46 */
		c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8L); /* 47 */
		b = HH(b, c, d, a, x[2], S34, 0xc4ac5665L); /* 48 */
		/* Round 4 */
		a = II(a, b, c, d, x[0], S41, 0xf4292244L); /* 49 */
		d = II(d, a, b, c, x[7], S42, 0x432aff97L); /* 50 */
		c = II(c, d, a, b, x[14], S43, 0xab9423a7L); /* 51 */
		b = II(b, c, d, a, x[5], S44, 0xfc93a039L); /* 52 */
		a = II(a, b, c, d, x[12], S41, 0x655b59c3L); /* 53 */
		d = II(d, a, b, c, x[3], S42, 0x8f0ccc92L); /* 54 */
		c = II(c, d, a, b, x[10], S43, 0xffeff47dL); /* 55 */
		b = II(b, c, d, a, x[1], S44, 0x85845dd1L); /* 56 */
		a = II(a, b, c, d, x[8], S41, 0x6fa87e4fL); /* 57 */
		d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0L); /* 58 */
		c = II(c, d, a, b, x[6], S43, 0xa3014314L); /* 59 */
		b = II(b, c, d, a, x[13], S44, 0x4e0811a1L); /* 60 */
		a = II(a, b, c, d, x[4], S41, 0xf7537e82L); /* 61 */
		d = II(d, a, b, c, x[11], S42, 0xbd3af235L); /* 62 */
		c = II(c, d, a, b, x[2], S43, 0x2ad7d2bbL); /* 63 */
		b = II(b, c, d, a, x[9], S44, 0xeb86d391L); /* 64 */
		state[0] += a;
		state[1] += b;
		state[2] += c;
		state[3] += d;
	}

	private void Encode(byte[] output, long[] input, int len) {
		int i, j;
		for (i = 0, j = 0; j < len; i++, j += 4) {
			output[j] = (byte) (input[i] & 0xffL);
			output[j + 1] = (byte) ((input[i] >>> 8) & 0xffL);
			output[j + 2] = (byte) ((input[i] >>> 16) & 0xffL);
			output[j + 3] = (byte) ((input[i] >>> 24) & 0xffL);
		}
	}

	private void Decode(long[] output, byte[] input, int len) {
		int i, j;
		for (i = 0, j = 0; j < len; i++, j += 4)
			output[i] = b2iu(input[j]) | (b2iu(input[j + 1]) << 8)
					| (b2iu(input[j + 2]) << 16) | (b2iu(input[j + 3]) << 24);
		return;
	}

	public static long b2iu(byte b) {
		return b < 0 ? b & 0x7F + 128 : b;
	}

	public static String byteHEX(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}
}
```
# MD5Util.java
```java
import java.security.MessageDigest;

public class MD5Util {
    /**
     * Title: MD5加密 生成32位md5码
     * Description: TestDemo
     * @author lu
     * @date 2016年6月23日 下午2:36:07
     * @param inStr
     * @return 返回32位md5码
     * @throws Exception
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    /**
     * Title: MD5加密
     * Description: TestDemo
     * @author lu
     * @date 2016年6月23日 下午2:43:31
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * Title: 加密解密算法 执行一次加密，两次解密
     * Description: TestDemo
     * @author lu
     * @date 2016年6月23日 下午2:37:29
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
    public static String md5Decode(String str) {
        return convertMD5(convertMD5(str));
    }

    public static void main(String[] args) {
        String s = new String("13917114404");
        System.out.println(md5Decode("a6aeb3ffa55fc7d664406af9c3bd0f1b"));
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + md5(s));
        System.out.println("加密的：" + convertMD5(s));
        System.out.println("解密的：" + convertMD5(convertMD5(s)));
        System.out.println(md5("13917114404"));
    }
}
```
# NumericHelper.java
```java

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * 作者: 吴超 时间: 2017年3月2日下午12:29:07 说明: 数字工具类
 *
 */
public class NumericHelper {

	/**
	 * 描述：通过一个整数i获取你所要的哪几个(从0开始) i为 多个2的n次方之和，如i=7，那么根据原值是2的n次方之各，你的原值必定是1，2，4 。
	 *
	 * @param i
	 *            数值
	 * @return
	 */
	public static int[] getWhich(long i) {
		int exp = Math.getExponent(i);
		if (i == (1 << (exp + 1)) - 1) {
			exp = exp + 1;
		}
		int[] num = new int[exp];
		int x = exp - 1;
		for (int n = 0; (1 << n) < i + 1; n++) {
			if ((1 << (n + 1)) > i && (1 << n) < (i + 1)) {
				num[x] = n;
				i -= 1 << n;
				n = 0;
				x--;
			}
		}
		return num;
	}

	/**
	 * 描述：非四舍五入取整处理
	 *
	 * @param v
	 *            需要四舍五入的数字
	 * @return
	 */
	public static int roundDown(double v) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 0, BigDecimal.ROUND_DOWN).intValue();
	}

	/**
	 * 描述：四舍五入取整处理
	 *
	 * @param v
	 *            需要四舍五入的数字
	 * @return
	 */
	public static int roundUp(double v) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 0, BigDecimal.ROUND_UP).intValue();
	}

	/**
	 * 描述：提供精确的小数位四舍五入处理。
	 *
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 描述：四舍五入保留两位小数
	 *
	 * @param num
	 *            数字
	 * @return 保留两位小数的数字字串
	 */
	public static String format(double num) {
		return format(num, "0.00");
	}

	/**
	 * 描述：四舍五入数字保留小数位
	 *
	 * @param num
	 *            数字
	 * @param digits
	 *            小数位
	 * @return
	 */
	public static String format(double num, int digits) {
		String pattern = "0";
		if (digits > 0) {
			pattern += "." + createStr("0", digits, "");
		}
		return format(num, pattern);
	}

	/**
	 * 描述：生成字符串
	 *
	 * @param arg0
	 *            字符串元素
	 * @param arg1
	 *            生成个数
	 * @param arg2
	 *            间隔符号
	 * @return
	 */
	private static String createStr(String arg0, int arg1, String arg2) {
		if (arg0 == null) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arg1; i++) {
				if (arg2 == null)
					arg2 = "";
				sb.append(arg0).append(arg2);
			}
			if (sb.length() > 0) {
				sb.delete(sb.lastIndexOf(arg2), sb.length());
			}

			return sb.toString();
		}
	}

	/**
	 * 描述：数字格式化
	 *
	 * @param num
	 *            数字
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String format(double num, String pattern) {
		NumberFormat fmt = null;
		if (pattern != null && pattern.length() > 0) {
			fmt = new DecimalFormat(pattern);
		} else {
			fmt = new DecimalFormat();
		}
		return fmt.format(num);
	}

	/**
	 * 求浮点数的权重
	 *
	 * @param number
	 * @return
	 */
	public static double weight(double number) {
		if (number == 0) {
			return 1;
		}

		double e = Math.log10(Math.abs(number));
		int n = Double.valueOf(Math.floor(e)).intValue();
		double weight = 1;
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				weight *= 10;
			}
		} else {
			for (int i = 0; i > n; i--) {
				weight /= 10;
			}
		}
		return weight;
	}

	/**
	 * 获得权重的单位
	 *
	 * @param scale
	 * @return
	 */
	public static String unit(double scale) {
		if (scale == 1 || scale == 0) {
			return "";// 不设置单位倍率单位，使用基准单位
		}
		String[] units = new String[] { "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "兆" };
		String[] units2 = new String[] { "十分", "百分", "千分", "万分", "十万分", "百万分", "千万分" };
		double e = Math.log10(scale);
		int position = Double.valueOf(Math.ceil(e)).intValue();
		if (position >= 1 && position <= units.length) {
			return units[position - 1];
		} else if (position <= -1 && -position <= units2.length) {
			return units2[-position - 1];
		} else {
			return "无量";
		}
	}

	/**
	 * 获得浮点数的缩放比例
	 *
	 * @param num
	 * @return
	 */
	public static double scale(double num) {
		double absValue = Math.abs(num);
		// 无需缩放
		if (absValue < 10000 && absValue >= 1) {
			return 1;
		}
		// 无需缩放
		else if (absValue < 1 && absValue > 0.0001) {
			return 1;
		} else {
			return weight(num) / 10;
		}
	}

	/**
	 * 获得缩放后并且格式化的浮点数
	 *
	 * @param num
	 * @param scale
	 * @return
	 */
	public static double scaleNumber(double num, double scale) {
		DecimalFormat df = null;
		if (scale == 1) {
			df = new DecimalFormat("#.0000");
		} else {
			df = new DecimalFormat("#.00");
		}
		double scaledNum = num / scale;
		return Double.valueOf(df.format(scaledNum));
	}

	/**
	 * 产生n位随机数 TODO:性能不要，有待优化
	 */
	public static String ramdomNumber(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive !");
		}
		Random random = new Random();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < n; i++) {
			result.append(random.nextInt(10));
		}
		return result.toString();
	}

	/**
	 * 缩放1W倍
	 */
	public static double changeTo(double number) {
		boolean flag = false;
		if (number < 0) {
			flag = true;
		}
		double value = Math.abs(number);
		value = value / 10000.0;
		if (flag) {
			value = Double.parseDouble("-" + value);
		}
		return value;
	}

	/**
	 *
	 * 描述：缩放比例
	 *
	 * @param number
	 * @param scale
	 * @param points
	 * @return
	 */
	public static String scaleNumberToStr(double number, double scale, int points) {
		boolean flag = (number < 0);
		number = Math.abs(number);
		String result = "";
		DecimalFormat nbf3 = (DecimalFormat) NumberFormat.getInstance();// 默认格式
		nbf3.setGroupingUsed(false);
		nbf3.setMinimumFractionDigits(points);
		nbf3.setMaximumFractionDigits(points);
		double scaledNum = number / scale;
		result = nbf3.format(scaledNum);
		if (flag) {
			result = "-" + result;
		}
		return result;
	}
}
```
# RandomHelper.java
```java
import java.util.Random;

/**
 *   
 *
 * @Title: RandomHelper.java
 * @Package com.jarvis.base.util
 * @Description: 随机数工具类
 * @author Jack 
 * @date 2017年9月2日 下午4:01:38
 * @version V1.0  
 */
public class RandomHelper {
	/**
	 * RANDOM 基数
	 */
	private final static int RANDOM_BASE = 10;

	/**
	 * 产生指定长度的数字值随机数
	 *
	 * @param length
	 *            需要产生的长度
	 * @return
	 */
	public static String getRandomStr(int length) {
		Random random = new Random();
		String randStr = "";
		for (int i = 0; i < length; i++) {
			String randItem = String.valueOf(random.nextInt(RANDOM_BASE));
			randStr += randItem;
		}
		return randStr;
	}

	/**
	 * 描述：手机验证码生成带字符，包含数字和字符 作者： 时间：Oct 29, 2008 3:40:07 PM
	 *
	 * @param len
	 *            生成手机验证码长度
	 * @return
	 */
	public static String generateChatAndNumberIdentifyCode(int len) {
		char[] identifyStr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
				'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		// char[] identifyStr={'0','1','2','3','4','5','6','7','8','9'};
		// 生成随机类
		// Random random = new Random();
		int min = 0;
		int maxnum = identifyStr.length;
		String codeStr = "";
		for (int i = 0; i < len; i++) {
			int num = (int) ((maxnum - min) * Math.random() + min);
			codeStr += identifyStr[num];
		}
		return codeStr;
	}

	/**
	 * 描述：手机验证码生成带字符不包含数字
	 *
	 * @param len
	 *            生成手机验证码长度
	 * @return
	 */
	public static String generateIdentifyCode(int len) {
		char[] identifyStr = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		// char[] identifyStr={'0','1','2','3','4','5','6','7','8','9'};
		// 生成随机类
		// Random random = new Random();
		int min = 0;
		int maxnum = identifyStr.length;
		String codeStr = "";
		for (int i = 0; i < len; i++) {
			int num = (int) ((maxnum - min) * Math.random() + min);
			codeStr += identifyStr[num];
		}
		return codeStr;
	}
}
```
# ReflectHelper.java
```java
/**
 *   
 *
 * @Title: ReflectHelper.java
 * @Package com.jarvis.base.util
 * @Description: 反射工具类
 * @author Jack 
 * @date 2017年9月2日 下午4:02:35
 * @version V1.0  
 */
public class ReflectHelper {

	/**
	 * 提指定的类载入以系统中
	 *
	 * @param name
	 *            类名称
	 * @return 类对象
	 * @throws ClassNotFoundException
	 */
	public static Class<?> classForName(String name) throws ClassNotFoundException {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(name);
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("类[" + name + "]加载出错");
		} catch (SecurityException e) {
			e.printStackTrace();
			System.err.println("类[" + name + "]加载出错");
		}
		return Class.forName(name);
	}

	/**
	 * 根据名称生成指定的对象
	 *
	 * @param name
	 *            类名称
	 * @return 具体的对象,若发生异常，则返回null
	 */
	public static Object objectForName(String name) {
		try {
			return Class.forName(name).newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("类[" + name + "]获取对象实例出错");
		}
		return null;
	}
}
```
# StringHelper.java
```java
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *   
 *
 * @Title: StringHelper.java
 * @Package com.jarvis.base.utils
 * @Description:
 * @author Jack 
 * @date 2017年9月2日 下午2:23:51
 * @version V1.0   字符串处理工具类。
 */
public final class StringHelper {

	/**
	 * 描述： 构造方法
	 */
	private StringHelper() {
	}

	/**
	 * 空字符串
	 */
	public static final String EMPTY_STRING = "";

	/**
	 * 点
	 */
	public static final char DOT = '.';

	/**
	 * 下划线
	 */
	public static final char UNDERSCORE = '_';

	/**
	 * 逗点及空格
	 */
	public static final String COMMA_SPACE = ", ";

	/**
	 * 逗点
	 */
	public static final String COMMA = ",";

	/**
	 * 开始括号
	 */
	public static final String OPEN_PAREN = "(";

	/**
	 * 结束括号
	 */
	public static final String CLOSE_PAREN = ")";

	/**
	 * 单引号
	 */
	public static final char SINGLE_QUOTE = '\'';

	/**
	 * 回车
	 */
	public static final String CRLF = "\r\n";

	/**
	 * 常量 12
	 */
	public static final int FIANL_TWELVE = 12;

	/**
	 * 十六进制常量 0x80
	 */
	public static final int HEX_80 = 0x80;

	/**
	 * 十六进制常量 0xff
	 */
	public static final int HEX_FF = 0xff;

	/**
	 * 把字符数组，转化为一个字符
	 *
	 * @param seperator
	 *            字符分隔符
	 * @param strings
	 *            数组对象
	 * @return 字符串
	 */
	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0) {
			return EMPTY_STRING;
		}
		StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	/**
	 * 把迭代对象转化为一个字符串
	 *
	 * @param seperator
	 *            分隔符
	 * @param objects
	 *            迭代器对象
	 * @return 字符串
	 */
	public static String join(String seperator, Iterator<?> objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext()) {
			buf.append(objects.next());
		}
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	/**
	 * 把两个字符串数组的元素用分隔符连接，生成新的数组，生成的数组以第一个字符串数组为参照，与其长度相同。
	 *
	 * @param x
	 *            字符串数组
	 * @param seperator
	 *            分隔符
	 * @param y
	 *            字符串数组
	 * @return 组合后的字符串数组
	 */
	public static String[] add(String[] x, String seperator, String[] y) {
		String[] result = new String[x.length];
		for (int i = 0; i < x.length; i++) {
			result[i] = x[i] + seperator + y[i];
		}
		return result;
	}

	/**
	 * 生成一个重复的字符串，如需要重复*10次，则生成：**********。
	 *
	 * @param string
	 *            重复元素
	 * @param times
	 *            重复次数
	 * @return 生成后的字符串
	 */
	public static String repeat(String string, int times) {
		StringBuffer buf = new StringBuffer(string.length() * times);
		for (int i = 0; i < times; i++) {
			buf.append(string);
		}
		return buf.toString();
	}

	/**
	 * 字符串替换处理，把旧的字符串替换为新的字符串，主要是通过字符串查找进行处理
	 *
	 * @param source
	 *            需要进行替换的字符串
	 * @param old
	 *            需要进行替换的字符串
	 * @param replace
	 *            替换成的字符串
	 * @return 替换处理后的字符串
	 */
	public static String replace(String source, String old, String replace) {
		StringBuffer output = new StringBuffer();

		int sourceLen = source.length();
		int oldLen = old.length();

		int posStart = 0;
		int pos;

		// 通过截取字符串的方式，替换字符串
		while ((pos = source.indexOf(old, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(replace);
			posStart = pos + oldLen;
		}

		// 如果还有没有处理的字符串，则都添加到新字符串后面
		if (posStart < sourceLen) {
			output.append(source.substring(posStart));
		}

		return output.toString();
	}

	/**
	 * 替换字符，如果指定进行全替换，必须设wholeWords=true，否则只替换最后出现的字符。
	 *
	 * @param template
	 *            字符模板
	 * @param placeholder
	 *            需要替换的字符
	 * @param replacement
	 *            新的字符
	 * @param wholeWords
	 *            是否需要全替换，true为需要，false为不需要。如果不需要，则只替换最后出现的字符。
	 * @return 替换后的新字符
	 */
	public static String replace(String template, String placeholder, String replacement, boolean wholeWords) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			final boolean actuallyReplace = wholeWords || loc + placeholder.length() == template.length()
					|| !Character.isJavaIdentifierPart(template.charAt(loc + placeholder.length()));
			String actualReplacement = actuallyReplace ? replacement : placeholder;
			return new StringBuffer(template.substring(0, loc)).append(actualReplacement).append(
					replace(template.substring(loc + placeholder.length()), placeholder, replacement, wholeWords))
					.toString();
		}
	}

	/**
	 * 替换字符，只替换第一次出现的字符串。
	 *
	 * @param template
	 *            字符模板
	 * @param placeholder
	 *            需要替换的字符串
	 * @param replacement
	 *            新字符串
	 * @return 替换后的字符串
	 */
	public static String replaceOnce(String template, String placeholder, String replacement) {
		int loc = template.indexOf(placeholder);
		if (loc < 0) {
			return template;
		} else {
			return new StringBuffer(template.substring(0, loc)).append(replacement)
					.append(template.substring(loc + placeholder.length())).toString();
		}
	}

	/**
	 * 把字符串，按指字的分隔符分隔为字符串数组
	 *
	 * @param seperators
	 *            分隔符
	 * @param list
	 *            字符串
	 * @return 字符串数组
	 */
	public static String[] split(String list, String seperators) {
		return split(list, seperators, false);
	}

	/**
	 * 把字符串，按指字的分隔符分隔为字符串数组
	 *
	 * @param seperators
	 *            分隔符
	 * @param list
	 *            字符串
	 * @param include
	 *            是否需要把分隔符也返回
	 * @return 字符串数组
	 */
	public static String[] split(String list, String seperators, boolean include) {
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String[] result = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			result[i++] = tokens.nextToken();
		}
		return result;
	}

	/**
	 * 提取字符串中，以.为分隔符后的所有字符，如string.exe，将返回exe。
	 *
	 * @param qualifiedName
	 *            字符串
	 * @return 提取后的字符串
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, ".");
	}

	/**
	 * 提取字符串中，以指定分隔符后的所有字符，如string.exe，将返回exe。
	 *
	 * @param qualifiedName
	 *            字符串
	 * @param seperator
	 *            分隔符
	 * @return 提取后的字符串
	 */
	public static String unqualify(String qualifiedName, String seperator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(seperator) + 1);
	}

	/**
	 * 提取字符串中，以.为分隔符以前的字符，如string.exe，则返回string
	 *
	 * @param qualifiedName
	 *            字符串
	 * @return 提取后的字符串
	 */
	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		if (loc < 0) {
			return EMPTY_STRING;
		} else {
			return qualifiedName.substring(0, loc);
		}
	}

	/**
	 * 向字符串数组中的所有元素添加上后缀
	 *
	 * @param columns
	 *            字符串数组
	 * @param suffix
	 *            后缀
	 * @return 添加后缀后的数组
	 */
	public static String[] suffix(String[] columns, String suffix) {
		if (suffix == null) {
			return columns;
		}
		String[] qualified = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			qualified[i] = suffix(columns[i], suffix);
		}
		return qualified;
	}

	/**
	 * 向字符串加上后缀
	 *
	 * @param name
	 *            需要添加后缀的字符串
	 * @param suffix
	 *            后缀
	 * @return 添加后缀的字符串
	 */
	public static String suffix(String name, String suffix) {
		return (suffix == null) ? name : name + suffix;
	}

	/**
	 * 向字符串数组中的所有元素，添加上前缀
	 *
	 * @param columns
	 *            需要添加前缀的字符串数组
	 * @param prefix
	 *            prefix
	 * @return
	 */
	public static String[] prefix(String[] columns, String prefix) {
		if (prefix == null) {
			return columns;
		}
		String[] qualified = new String[columns.length];
		for (int i = 0; i < columns.length; i++) {
			qualified[i] = prefix + columns[i];
		}
		return qualified;
	}

	/**
	 * 向字符串添加上前缀
	 *
	 * @param name
	 *            需要添加前缀的字符串
	 * @param prefix
	 *            前缀
	 * @return 添加前缀后的字符串
	 */
	public static String prefix(String name, String prefix) {
		return (prefix == null) ? name : prefix + name;
	}

	/**
	 * 判断字符串是否为"true"、"t"，如果是，返回true，否则返回false
	 *
	 * @param tfString
	 *            需要进行判断真/假的字符串
	 * @return true/false
	 */
	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return trimmed.equals("true") || trimmed.equals("t");
	}

	/**
	 * 把对象数组转化为字符串
	 *
	 * @param array
	 *            对象数组
	 * @return 字符串
	 */
	public static String toString(Object[] array) {
		int len = array.length;
		if (len == 0) {
			return StringHelper.EMPTY_STRING;
		}
		StringBuffer buf = new StringBuffer(len * FIANL_TWELVE);
		for (int i = 0; i < len - 1; i++) {
			buf.append(array[i]).append(StringHelper.COMMA_SPACE);
		}
		return buf.append(array[len - 1]).toString();
	}

	/**
	 * 描述：把数组中的所有元素出现的字符串进行替换，把旧字符串替换为新字符数组的所有元素，只替换第一次出现的字符。
	 *
	 * @param string
	 *            需要替换的数组
	 * @param placeholders
	 *            需要替换的字符串
	 * @param replacements
	 *            新字符串数组
	 * @return 替换后的字符串数组
	 */
	public static String[] multiply(String string, Iterator<?> placeholders, Iterator<?> replacements) {
		String[] result = new String[] { string };
		while (placeholders.hasNext()) {
			result = multiply(result, (String) placeholders.next(), (String[]) replacements.next());
		}
		return result;
	}

	/**
	 * 把数组中的所有元素出现的字符串进行替换，把旧字符串替换为新字符数组的所有元素，只替换第一次出现的字符。
	 *
	 * @param strings
	 *            需要替换的数组
	 * @param placeholder
	 *            需要替换的字符串
	 * @param replacements
	 *            新字符串数组
	 * @return 替换后的字符串数组
	 */
	private static String[] multiply(String[] strings, String placeholder, String[] replacements) {
		String[] results = new String[replacements.length * strings.length];
		int n = 0;
		for (int i = 0; i < replacements.length; i++) {
			for (int j = 0; j < strings.length; j++) {
				results[n++] = replaceOnce(strings[j], placeholder, replacements[i]);
			}
		}
		return results;
	}

	/**
	 * 统计Char在字符串中出现在次数，如"s"在字符串"string"中出现的次数
	 *
	 * @param string
	 *            字符串
	 * @param character
	 *            需要进行统计的char
	 * @return 数量
	 */
	public static int count(String string, char character) {
		int n = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == character) {
				n++;
			}
		}
		return n;
	}

	/**
	 * 描述：计算字符串中未引用的字符
	 *
	 * @param string
	 *            字符串
	 * @param character
	 *            字符
	 * @return 未引用的字符数
	 */
	public static int countUnquoted(String string, char character) {
		if (SINGLE_QUOTE == character) {
			throw new IllegalArgumentException("Unquoted count of quotes is invalid");
		}

		int count = 0;
		int stringLength = string == null ? 0 : string.length();
		boolean inQuote = false;
		for (int indx = 0; indx < stringLength; indx++) {
			if (inQuote) {
				if (SINGLE_QUOTE == string.charAt(indx)) {
					inQuote = false;
				}
			} else if (SINGLE_QUOTE == string.charAt(indx)) {
				inQuote = true;
			} else if (string.charAt(indx) == character) {
				count++;
			}
		}
		return count;
	}

	/**
	 *
	 * 描述：描述：判断字符串是否为空，如果为true则为空。与isEmpty不同，如果字符为" "也视为空字符
	 *
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isBlank(String str) {
		boolean b = true;// 20140507 modify by liwei 修复对" "为false的bug
		if (str == null) {
			b = true;
		} else {
			int strLen = str.length();
			if (strLen == 0) {
				b = true;
			}

			for (int i = 0; i < strLen; i++) {
				if (!Character.isWhitespace(str.charAt(i))) {
					b = false;
					break;
				}
			}
		}

		return b;
	}

	/**
	 *
	 * 描述：描述：判断字符串是否为空，如果为true则不为空。与isNotEmpty不同，如果字符为" "也视为空字符
	 *
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		int strLen = 0;
		if (str != null)
			strLen = str.length();
		if (str == null || strLen == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断字符串是否非空，如果为true则不为空
	 *
	 * @param string
	 *            字符串
	 * @return true/false
	 */
	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	/**
	 * 判断字符串是否空，如果为true则为空
	 *
	 * @param str
	 *            字符串
	 * @return true/false
	 */

	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 向字符串添加上前缀，并以.作为分隔符
	 *
	 * @param name
	 *            需要添加前缀的字符串
	 * @param prefix
	 *            前缀
	 * @return 添加前缀后的字符串
	 */
	public static String qualify(String name, String prefix) {
		if (name.startsWith("'")) {
			return name;
		}

		return new StringBuffer(prefix.length() + name.length() + 1).append(prefix).append(DOT).append(name).toString();
	}

	/**
	 * 向字符串数组中的所有字符添加上前缀，前以点作为分隔符
	 *
	 * @param names
	 *            字符串数组
	 * @param prefix
	 *            前缀
	 * @return 添加前缀后的字符串数组
	 */
	public static String[] qualify(String[] names, String prefix) {
		if (prefix == null) {
			return names;
		}
		int len = names.length;
		String[] qualified = new String[len];
		for (int i = 0; i < len; i++) {
			qualified[i] = qualify(prefix, names[i]);
		}
		return qualified;
	}

	/**
	 * 在字符串中，查找字符第一次出现的位置
	 *
	 * @param sqlString
	 *            原字符串
	 * @param string
	 *            需要查找到字符串
	 * @param startindex
	 *            开始位置
	 * @return 第一个出现的位置
	 */
	public static int firstIndexOfChar(String sqlString, String string, int startindex) {
		int matchAt = -1;
		for (int i = 0; i < string.length(); i++) {
			int curMatch = sqlString.indexOf(string.charAt(i), startindex);
			if (curMatch >= 0) {
				if (matchAt == -1) {
					matchAt = curMatch;
				} else {
					matchAt = Math.min(matchAt, curMatch);
				}
			}
		}
		return matchAt;
	}

	/**
	 * 从字符串中提取指字长度的字符。区分中英文。<br>
	 * 如果需要加省略号，则将在指定长度上少取3个字符宽度，末尾加上"......"。
	 *
	 * @param string
	 *            字符串
	 * @param length
	 *            要取的字符长度，此为中文长度，英文仅当作半个字符。
	 * @param appendSuspensionPoints
	 *            是否需要加省略号
	 * @return 提取后的字符串
	 */
	public static String truncate(String string, int length, boolean appendSuspensionPoints) {
		if (isEmpty(string) || length < 0) {
			return string;
		}

		if (length == 0) {
			return "";
		}

		int strLength = string.length(); // 字符串字符个数
		int byteLength = byteLength(string); // 字符串字节长度
		length *= 2; // 换成字节长度

		// 判断是否需要加省略号
		boolean needSus = false;
		if (appendSuspensionPoints && byteLength >= length) {
			needSus = true;

			// 如果需要加省略号，则要少取2个字节用来加省略号
			length -= 2;
		}

		StringBuffer result = new StringBuffer();
		int count = 0;
		for (int i = 0; i < strLength; i++) {
			if (count >= length) { // 取完了
				break;
			}

			char c = string.charAt(i);

			if (isLetter(c)) { // Ascill字符
				result.append(c);
				count += 1;
			} else { // 非Ascill字符
				if (count == length - 1) { // 如果只要取1个字节了，而后面1个是汉字，就放空格
					result.append(" ");
					count += 1;
				} else {
					result.append(c);
					count += 2;
				}
			}
		}

		if (needSus) {
			result.append("...");
		}

		return result.toString();
	}

	/**
	 * 描述：判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 *
	 * @param c
	 *            需要判断的字符
	 * @return
	 */
	public static boolean isLetter(char c) {
		int k = HEX_80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 *
	 * @param s
	 *            ,需要得到长度的字符串
	 * @return int, 得到的字符串长度
	 */
	public static int byteLength(String s) {
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			if (isLetter(c[i])) {
				len++;
			} else {
				len += 2;
			}
		}
		return len;
	}

	/**
	 * 从字符串中提取指字长度的字符
	 *
	 * @param string
	 *            字符串
	 * @param length
	 *            字符长度
	 * @return 提取后的字符串
	 */
	public static String truncate(String string, int length) {
		if (isEmpty(string)) {
			return string;
		}

		if (string.length() <= length) {
			return string;
		} else {
			return string.substring(0, length);
		}
	}

	/**
	 * 去丢字符的左侧空格
	 *
	 * @param value
	 *            字符串
	 * @return 去丢左侧空格后的字符串
	 */
	public static String leftTrim(String value) {
		String result = value;
		if (result == null) {
			return result;
		}
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (!Character.isWhitespace(ch[i])) {
				break;
			}
			index = i;
		}

		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 去丢字符的右侧空格
	 *
	 * @param value
	 *            字符串
	 * @return 去右侧空格后的字符串
	 */
	public static String rightTrim(String value) {
		String result = value;
		if (result == null) {
			return result;
		}
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (!Character.isWhitespace(ch[i])) {
				break;
			}
			endIndex = i;
		}

		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 把null字符串转化为""
	 *
	 * @param source
	 *            空字符串
	 * @return 转化后的字符串
	 */
	public static String n2s(String source) {
		return source != null ? source : "";
	}

	/**
	 * 如果字符串为空，则返回默认字符串
	 *
	 * @param source
	 *            源字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return 转换后的字符串
	 */
	public static String n2s(String source, String defaultStr) {
		return source != null ? source : defaultStr;
	}

	/**
	 * 将字符串格式化成 HTML 以SCRIPT变量 主要是替换单,双引号，以将内容格式化输出，适合于 HTML 中的显示输出
	 *
	 * @param str
	 *            要格式化的字符串
	 * @return 格式化后的字符串
	 */
	public static String toScript(String str) {
		if (str == null) {
			return null;
		}

		String html = new String(str);

		html = replace(html, "\"", "\\\"");
		html = replace(html, "\r\n", "\n");
		html = replace(html, "\n", "\\n");
		html = replace(html, "\t", "    ");
		html = replace(html, "\'", "\\\'");

		html = replace(html, "  ", " &nbsp;");

		html = replace(html, "</script>", "<\\/script>");
		html = replace(html, "</SCRIPT>", "<\\/SCRIPT>");

		return html;
	}

	/**
	 * 同于String#trim()，但是检测null，如果原字符串为null，则仍然返回null
	 *
	 * @param s
	 *            s
	 * @return
	 */
	public static String trim(String s) {
		return s == null ? s : s.trim();
	}

	/**
	 * 对字符串进行空格处理，如果字符串为null呀是空字符串， 则返回默认的数字。
	 *
	 * @param source
	 *            需要进行处理的字符串
	 * @param defaultValue
	 *            缺省值
	 * @return 字符串的数字值
	 */
	public static int strTrim(String source, int defaultValue) {
		if (isEmpty(source)) {
			return defaultValue;
		}
		try {
			source = source.trim();
			int value = (new Integer(source)).intValue();
			return value;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("数字转换出错，请检查数据来源。返回默认值");
			return defaultValue;
		}
	}

	/**
	 * 对字符串进行过滤处理，如果字符串是null或为空字符串， 返回默认值。
	 *
	 * @param source
	 *            需要进行处理的字符串
	 * @param defaultValue
	 *            缺省值
	 * @return 过滤后的字符串
	 */
	public static String strTrim(String source, String defaultValue) {
		if (StringHelper.isEmpty(source)) {
			return defaultValue;
		}
		try {
			source = source.trim();
			return source;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("字符串去空格失败，返回默认值");
			return defaultValue;
		}
	}

	/**
	 * 描述：为了防止跨站脚本攻击，转换<>这种尖括号。
	 *
	 * @param source
	 * @return
	 */
	public static String encodeURL(String source) {
		if (source == null) {
			return null;
		}
		String html = new String(source);
		html = replace(html, "<", "&lt;");
		html = replace(html, ">", "&gt;");
		html = replace(html, "\"", "&quot;");
		html = replace(html, " ", "&nbsp;");
		html = replace(html, "\'", "&acute;");
		html = replace(html, "\\", "&#092;");
		html = replace(html, "&", "&amp;");
		html = replace(html, "\r", "");
		html = replace(html, "\n", "");
		html = replace(html, "(", "&#40;");
		html = replace(html, ")", "&#41;");
		html = replace(html, "[", "&#91;");
		html = replace(html, "]", "&#93;");
		html = replace(html, ";", "&#59;");
		html = replace(html, "/", "&#47;");

		return html;
	}

	/**
	 * 把字符串中一些特定的字符转换成html字符，如&、<、>、"号等
	 *
	 * @param source
	 *            需要进行处理的字符串
	 * @return 处理后的字符串
	 */
	public static String encodeHtml(String source) {
		if (source == null) {
			return null;
		}

		String html = new String(source);

		html = replace(html, "&", "&amp;");
		html = replace(html, "<", "&lt;");
		html = replace(html, ">", "&gt;");
		html = replace(html, "\"", "&quot;");
		html = replace(html, " ", "&nbsp;");
		html = replace(html, "\'", "&acute;");
		return html;
	}

	/**
	 * 把一些html的字符串还原
	 *
	 * @param source
	 *            需要进行处理的字符串
	 * @return 处理后的字符串
	 */
	public static String decodeHtml(String source) {
		if (source == null) {
			return null;
		}

		String html = new String(source);

		html = replace(html, "&amp;", "&");
		html = replace(html, "&lt;", "<");
		html = replace(html, "&gt;", ">");
		html = replace(html, "&quot;", "\"");
		html = replace(html, " ", "&nbsp;");

		html = replace(html, "\r\n", "\n");
		html = replace(html, "\n", "<br>\n");
		html = replace(html, "\t", "    ");
		html = replace(html, "  ", " &nbsp;");

		return html;
	}

	/**
	 * 判断字符串是否为布尔值，如true/false等
	 *
	 * @param source
	 *            需要进行判断的字符串
	 * @return 返回字符串的布尔值
	 */
	public static boolean isBoolean(String source) {
		if (source.equalsIgnoreCase("true") || source.equalsIgnoreCase("false")) {
			return true;
		}
		return false;
	}

	/**
	 * 去除字符串中的最后字符
	 *
	 * @param str
	 *            原字符串
	 * @param strMove
	 *            要去除字符 比如","
	 * @return 去除后的字符串
	 */
	public static String lastCharTrim(String str, String strMove) {
		if (isEmpty(str)) {
			return "";
		}

		String newStr = "";
		if (str.lastIndexOf(strMove) != -1 && str.lastIndexOf(strMove) == str.length() - 1) {
			newStr = str.substring(0, str.lastIndexOf(strMove));
		}
		return newStr;
	}

	/**
	 * 清除字符串里的html代码
	 *
	 * @param html
	 *            需要进行处理的字符串
	 * @return 清除html后的代码
	 */
	public static String clearHtml(String html) {
		if (isEmpty(html)) {
			return "";
		}

		String patternStr = "(<[^>]*>)";
		Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
		Matcher matcher = null;
		StringBuffer bf = new StringBuffer();
		try {
			matcher = pattern.matcher(html);
			boolean first = true;
			int start = 0;
			int end = 0;
			while (matcher.find()) {
				start = matcher.start(1);
				if (first) {
					bf.append(html.substring(0, start));
					first = false;
				} else {
					bf.append(html.substring(end, start));
				}

				end = matcher.end(1);
			}
			if (end < html.length()) {
				bf.append(html.substring(end));
			}
			html = bf.toString();
			return html;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("清除html标签失败");
		} finally {
			pattern = null;
			matcher = null;
		}
		return html;
	}

	/**
	 * 把文杯格式转换为html格式
	 *
	 * @param content
	 *            转换的内容
	 * @return
	 */
	public static String textFmtToHtmlFmt(String content) {
		content = StringHelper.replace(content, " ", "&nbsp;");
		content = StringHelper.replace(content, "\r\n", "<br>");
		content = StringHelper.replace(content, "\n", "<br>");

		return content;
	}

	/**
	 *
	 * 描述：大写英文字母转换成小写
	 *
	 * @param strIn
	 *            字符串参数
	 * @return
	 */
	public static String toLowerStr(String strIn) {
		String strOut = new String(); // 输出的字串
		int len = strIn.length(); // 参数的长度
		int i = 0; // 计数器
		char ch; // 存放参数的字符

		while (i < len) {
			ch = strIn.charAt(i);

			if (ch >= 'A' && ch <= 'Z') {
				ch = (char) (ch - 'A' + 'a');
			}

			strOut += ch;
			i++;
		}
		return strOut;
	}

	/**
	 *
	 * 描述：小写英文字母转换成大写
	 *
	 * @param strIn
	 *            字符串参数
	 * @return
	 */
	public static String toUpperStr(String strIn) {
		String strOut = new String(); // 输出的字串
		int len = strIn.length(); // 参数的长度
		int i = 0; // 计数器
		char ch; // 存放参数的字符

		while (i < len) {
			ch = strIn.charAt(i);

			if (ch >= 'a' && ch <= 'z') {
				ch = (char) (ch - 'a' + 'A');
			}

			strOut += ch;
			i++;
		}
		return strOut;
	}

	/**
	 * 货币缩写,提供亿和万两个单位，并精确到小数点2位 切换到新的算法:对数算法
	 *
	 * @param original
	 * @return
	 */
	public static String currencyShortFor(String original) {
		if (StringHelper.isBlank(original)) {
			return "";
		} else {
			String shortFor = "";
			double shortForValue = 0;
			DecimalFormat df = new DecimalFormat("#.00");

			try {
				double account = Double.parseDouble(original);
				if (account / 100000000 > 1) {
					shortForValue = account / 100000000;
					shortFor = df.format(shortForValue) + "亿";
				} else if (account / 10000 > 1) {
					shortForValue = account / 10000;
					shortFor = df.format(shortForValue) + "万";
				} else {
					shortFor = original;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.err.println("字符串[" + original + "]转换成数字出错");
			}

			return shortFor;
		}
	}

	/**
	 * 将日期格式由yyyyMMdd装换为yyyy-MM-dd
	 *
	 * @param date
	 *            Date string whose format is yyyyMMdd.
	 * @return
	 */
	public static String formatDate(String date) {
		if (isBlank(date) || date.length() < 8) {
			return "";
		}
		StringBuffer dateBuf = new StringBuffer();
		dateBuf.append(date.substring(0, 4));
		dateBuf.append("-");
		dateBuf.append(date.substring(4, 6));
		dateBuf.append("-");
		dateBuf.append(date.substring(6, 8));
		return dateBuf.toString();
	}

	/**
	 * 判断是否为整数
	 *
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^\\d+(\\.0)?$", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(str).matches();

	}

	/**
	 * 用于=中英文混排标题中限定字符串长度。保证显示长度最多只相差一个全角字符。
	 *
	 * @param string
	 *            需要截取的字符串
	 * @param byteCount
	 *            字节数（度量标准为中文为两个字节，ASCII字符为一个字节,这样子，刚好匹配ASCII为半角字符，而中文为全角字符，
	 *            保证在网页上中英文混合的句子长度一致）
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String substring(String string, int byteCount) throws UnsupportedEncodingException {
		if (isBlank(string)) {
			return string;
		}
		byte[] bytes = string.getBytes("Unicode");// 使用UCS-2编码.
		int viewBytes = 0; // 表示当前的字节数(英文为单字节，中文为双字节的表示方法)
		int ucs2Bytes = 2; // 要截取的字节数，从第3个字节开始,前两位为位序。（UCS-2的表示方法）
		// UCS-2每个字符使用两个字节来编码。
		// ASCII n+=1,i+=2
		// 中文 n+=2,i+=2
		for (; ucs2Bytes < bytes.length && viewBytes < byteCount; ucs2Bytes++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (ucs2Bytes % 2 == 1) {
				viewBytes++; // 低字节，无论中英文，都算一个字节。
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				// 高位时，仅中文的高位算一字节。
				if (bytes[ucs2Bytes] != 0) {
					viewBytes++;
				}
			}
		}
		// 截一半的汉字要保留
		if (ucs2Bytes % 2 == 1) {
			ucs2Bytes = ucs2Bytes + 1;
		}
		String result = new String(bytes, 0, ucs2Bytes, "Unicode");// 将字节流转换为java默认编码UTF-8的字符串
		if (bytes.length > ucs2Bytes) {
			result += "...";
		}
		return result;
	}

	/**
	 * 描述：根据长度截断字串
	 *
	 * @param str
	 *            字串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String[] splite(String str, int length) {
		if (StringHelper.isEmpty(str)) {
			return null;
		}
		String[] strArr = new String[(str.length() + length - 1) / length];
		for (int i = 0; i < strArr.length; i++) {
			if (str.length() > i * length + length - 1) {
				strArr[i] = str.substring(i * length, i * length + length - 1);
			} else {
				strArr[i] = str.substring(i * length);
			}
		}
		return strArr;
	}

	/**
	 * 描述：把某一个字符变成大写
	 *
	 * @param str
	 *            str 字串
	 * @param index
	 *            第几个字符
	 * @return
	 */
	public static String toUpOneChar(String str, int index) {
		return toUpOrLowOneChar(str, index, 1);
	}

	/**
	 * 描述：把某一个字符变成小写 作者：李建 时间：Dec 17, 2010 9:42:32 PM
	 *
	 * @param str
	 *            str 字串
	 * @param index
	 *            第几个字符
	 * @return
	 */
	public static String toLowOneChar(String str, int index) {
		return toUpOrLowOneChar(str, index, 0);
	}

	/**
	 * 描述：把某一个字符变成大写或小写 作者：李建 时间：Dec 17, 2010 9:39:32 PM
	 *
	 * @param str
	 *            字串
	 * @param index
	 *            第几个字符
	 * @param upOrLow
	 *            大小写 1：大写；0小写
	 * @return
	 */
	public static String toUpOrLowOneChar(String str, int index, int upOrLow) {
		if (StringHelper.isNotEmpty(str) && index > -1 && index < str.length()) {
			char[] chars = str.toCharArray();
			if (upOrLow == 1) {
				chars[index] = Character.toUpperCase(chars[index]);
			} else {
				chars[index] = Character.toLowerCase(chars[index]);
			}
			return new String(chars);
		}
		return str;
	}

	/**
	 * 将字符串用分隔符断裂成字符串列表
	 *
	 * @param value
	 *            原字符串
	 * @param separator
	 *            分隔字符
	 * @return 结果列表
	 */
	public static List<String> split2List(String value, String separator) {
		List<String> ls = new ArrayList<String>();
		int i = 0, j = 0;
		while ((i = value.indexOf(separator, i)) != -1) {
			ls.add(value.substring(j, i));
			++i;
			j = i;
		}
		ls.add(value.substring(j));
		return ls;
	}

	/**
	 * 将数组用分隔符连接成新字符串(split的逆方法)
	 *
	 * @param strs
	 *            字符串数组
	 * @param sep
	 *            分隔符
	 * @return 结果字符串
	 */
	public static String join(String[] strs, String sep) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			res.append(strs[i] + sep);
		}
		return res.substring(0, res.length() - sep.length());
	}

	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUUID() {
		String str = UUID.randomUUID().toString();// 标准的UUID格式为：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxx(8-4-4-4-12)
		// 去掉"-"符号，不用replaceAll的原因与split一样，replaceAll支持正则表达式，频繁使用时效率不够高(当然偶尔用一下影响也不会特别严重)
		return join(split(str, "-"), "");
	}

	/**
	 * <pre>
	 * 例： String strVal="This is a dog"; String
	 * strResult=CTools.replace(strVal,"dog","cat"); 结果： strResult equals
	 * "This is cat"
	 *
	 * @param strSrc
	 *            要进行替换操作的字符串
	 * @param strOld
	 *            要查找的字符串
	 * @param strNew
	 *            要替换的字符串
	 * @return 替换后的字符串
	 *
	 *         <pre>
	 */
	public static final String replaceAllStr(String strSrc, String strOld, String strNew) {
		if (strSrc == null || strOld == null || strNew == null)
			return "";

		int i = 0;

		if (strOld.equals(strNew)) // 避免新旧字符一样产生死循环
			return strSrc;

		if ((i = strSrc.indexOf(strOld, i)) >= 0) {
			char[] arr_cSrc = strSrc.toCharArray();
			char[] arr_cNew = strNew.toCharArray();

			int intOldLen = strOld.length();
			StringBuffer buf = new StringBuffer(arr_cSrc.length);
			buf.append(arr_cSrc, 0, i).append(arr_cNew);

			i += intOldLen;
			int j = i;

			while ((i = strSrc.indexOf(strOld, i)) > 0) {
				buf.append(arr_cSrc, j, i - j).append(arr_cNew);
				i += intOldLen;
				j = i;
			}

			buf.append(arr_cSrc, j, arr_cSrc.length - j);

			return buf.toString();
		}

		return strSrc;
	}

	/**
	 * 用于将字符串中的特殊字符转换成Web页中可以安全显示的字符串 可对表单数据据进行处理对一些页面特殊字符进行处理如'
	 * <','>','"',''','&'
	 *
	 * @param strSrc
	 *            要进行替换操作的字符串
	 * @return 替换特殊字符后的字符串
	 * @since 1.0
	 */

	public static String htmlEncode(String strSrc) {
		if (strSrc == null)
			return "";

		char[] arr_cSrc = strSrc.toCharArray();
		StringBuffer buf = new StringBuffer(arr_cSrc.length);
		char ch;

		for (int i = 0; i < arr_cSrc.length; i++) {
			ch = arr_cSrc[i];

			if (ch == '<')
				buf.append("&lt;");
			else if (ch == '>')
				buf.append("&gt;");
			else if (ch == '"')
				buf.append("&quot;");
			else if (ch == '\'')
				buf.append("&#039;");
			else if (ch == '&')
				buf.append("&amp;");
			else
				buf.append(ch);
		}

		return buf.toString();
	}

	/**
	 * 用于将字符串中的特殊字符转换成Web页中可以安全显示的字符串 可对表单数据据进行处理对一些页面特殊字符进行处理如'
	 * <','>','"',''','&'
	 *
	 * @param strSrc
	 *            要进行替换操作的字符串
	 * @param quotes
	 *            为0时单引号和双引号都替换，为1时不替换单引号，为2时不替换双引号，为3时单引号和双引号都不替换
	 * @return 替换特殊字符后的字符串
	 * @since 1.0
	 */
	public static String htmlEncode(String strSrc, int quotes) {

		if (strSrc == null)
			return "";
		if (quotes == 0) {
			return htmlEncode(strSrc);
		}

		char[] arr_cSrc = strSrc.toCharArray();
		StringBuffer buf = new StringBuffer(arr_cSrc.length);
		char ch;

		for (int i = 0; i < arr_cSrc.length; i++) {
			ch = arr_cSrc[i];
			if (ch == '<')
				buf.append("&lt;");
			else if (ch == '>')
				buf.append("&gt;");
			else if (ch == '"' && quotes == 1)
				buf.append("&quot;");
			else if (ch == '\'' && quotes == 2)
				buf.append("&#039;");
			else if (ch == '&')
				buf.append("&amp;");
			else
				buf.append(ch);
		}

		return buf.toString();
	}

	/**
	 * 和htmlEncode正好相反
	 *
	 * @param strSrc
	 *            要进行转换的字符串
	 * @return 转换后的字符串
	 * @since 1.0
	 */
	public static String htmlDecode(String strSrc) {
		if (strSrc == null)
			return "";
		strSrc = strSrc.replaceAll("&lt;", "<");
		strSrc = strSrc.replaceAll("&gt;", ">");
		strSrc = strSrc.replaceAll("&quot;", "\"");
		strSrc = strSrc.replaceAll("&#039;", "'");
		strSrc = strSrc.replaceAll("&amp;", "&");
		return strSrc;
	}

	/**
	 * 实际处理 return toChineseNoReplace(null2Blank(str));
	 *
	 * @param str
	 *            要进行处理的字符串
	 * @return 转换后的字符串
	 * @see fs_com.utils.CTools#toChinese
	 * @see fs_com.utils.CTools#null2Blank
	 */
	public static String toChineseAndHtmlEncode(String str, int quotes) {
		try {
			if (str == null) {
				return "";
			} else {
				str = str.trim();
				str = new String(str.getBytes("ISO8859_1"), "GBK");
				String htmlEncode = htmlEncode(str, quotes);
				return htmlEncode;
			}
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * 把null值和""值转换成&nbsp; 主要应用于页面表格格的显示
	 *
	 * @param str
	 *            要进行处理的字符串
	 * @return 转换后的字符串
	 */
	public static String str4Table(String str) {
		if (str == null)
			return "&nbsp;";
		else if (str.equals(""))
			return "&nbsp;";
		else
			return str;
	}
}
```
# URLHelper.java
```java

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *   
 *
 * @Title: URLHelper.java
 * @Package com.jarvis.base.util
 * @Description:URL工具类
 * @author Jack 
 * @date 2017年9月2日 下午4:06:57
 * @version V1.0  
 */
public class URLHelper {
	/**
	 * 对URL的中文进行编码
	 *
	 * @param url
	 *            来源字符串
	 * @return 编码后的字符串
	 */
	@SuppressWarnings("deprecation")
	public static String urlEncode(String url) {
		return java.net.URLEncoder.encode(url);
	}

	/**
	 * 对URL的中文进行解码
	 *
	 * @param url
	 *            来源字符串
	 * @return 解码后的字符串
	 */
	@SuppressWarnings("deprecation")
	public static String urlDecode(String url) {
		return java.net.URLDecoder.decode(url);
	}

	/**
	 * 把参数组成的MAP转化到对应的URL格式
	 *
	 * @param paramMap
	 *            Map参数
	 * @return
	 */
	public static String toURL(Map<?, ?> paramMap) {
		if (paramMap == null) {
			return "";
		}

		String url = "";
		for (Iterator<?> iter = paramMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = (paramMap.get(key)).toString();
			String param = key + "=" + value;
			url += param;
			url += "&";
		}

		if (url.endsWith("&")) {
			url = url.substring(0, url.length() - 1);
		}
		return url;
	}

	/**
	 * 描述：和toUrl反向
	 *
	 * @param url
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map toMap(String url) {
		if (url == null)
			return null;

		Map paramMap = new HashMap();
		String[] paramArray = url.split("&");
		for (int i = 0; i < paramArray.length; i++) {
			String[] param = paramArray[i].split("=");
			if (param.length == 2) {
				paramMap.put(param[0], param[1]);
			}
		}
		return paramMap;
	}

	/**
	 * 描述：移出查询字串中的某查询参数
	 *
	 * @param queryString
	 *            查询字串
	 * @param paramName
	 *            查询参数
	 * @return
	 */
	public static String removeQueryParam(String queryString, String paramName) {
		if (StringHelper.isEmpty(queryString)) {
			return "";
		}

		if (StringHelper.isEmpty(paramName)) {
			return queryString;
		}

		String key = paramName + "=";
		int pos = queryString.indexOf(paramName + "=");
		if (pos == -1) {
			return queryString;
		}

		String startStr = queryString.substring(0, pos);
		String endStr = queryString.substring(pos + key.length());
		pos = endStr.indexOf("&");
		if (pos == -1) {
			endStr = "";
		} else {
			endStr = endStr.substring(pos + 1);
		}

		String retStr = startStr + endStr;
		if (retStr.endsWith("&")) {
			retStr = retStr.substring(0, retStr.length() - 1);
		}
		return retStr;
	}
}
```
# UUID.java
```java
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A class that represents an immutable universally unique identifier (UUID).
 * A UUID represents a 128-bit value.
 * <p/>
 * <p>There exist different variants of these global identifiers. The methods
 * of this class are for manipulating the Leach-Salz variant, although the
 * constructors allow the creation of any variant of UUID (described below).
 * <p/>
 * <p>The layout of a variant 2 (Leach-Salz) UUID is as follows:
 * <p/>
 * The most significant long consists of the following unsigned fields:
 * <pre>
 * 0xFFFFFFFF00000000 time_low
 * 0x00000000FFFF0000 time_mid
 * 0x000000000000F000 version
 * 0x0000000000000FFF time_hi
 * </pre>
 * The least significant long consists of the following unsigned fields:
 * <pre>
 * 0xC000000000000000 variant
 * 0x3FFF000000000000 clock_seq
 * 0x0000FFFFFFFFFFFF node
 * </pre>
 * <p/>
 * <p>The variant field contains a value which identifies the layout of
 * the <tt>UUID</tt>. The bit layout described above is valid only for
 * a <tt>UUID</tt> with a variant value of 2, which indicates the
 * Leach-Salz variant.
 * <p/>
 * <p>The version field holds a value that describes the type of this
 * <tt>UUID</tt>. There are four different basic types of UUIDs: time-based,
 * DCE security, name-based, and randomly generated UUIDs. These types
 * have a version value of 1, 2, 3 and 4, respectively.
 * <p/>
 * <p>For more information including algorithms used to create <tt>UUID</tt>s,
 * see the Internet-Draft <a href="http://www.ietf.org/internet-drafts/draft-mealling-uuid-urn-03.txt">UUIDs and GUIDs</a>
 * or the standards body definition at
 * <a href="http://www.iso.ch/cate/d2229.html">ISO/IEC 11578:1996</a>.
 *
 * @version 1.14, 07/12/04
 * @since 1.5
 */
@Deprecated
public final class UUID implements java.io.Serializable
{

    /**
     * Explicit serialVersionUID for interoperability.
     */
    private static final long serialVersionUID = -4856846361193249489L;

    /*
     * The most significant 64 bits of this UUID.
     *
     * @serial
     */
    private final long mostSigBits;

    /**
     * The least significant 64 bits of this UUID.
     *
     * @serial
     */
    private final long leastSigBits;

    /*
     * The version number associated with this UUID. Computed on demand.
     */
    private transient int version = -1;

    /*
     * The variant number associated with this UUID. Computed on demand.
     */
    private transient int variant = -1;

    /*
     * The timestamp associated with this UUID. Computed on demand.
     */
    private transient volatile long timestamp = -1;

    /*
     * The clock sequence associated with this UUID. Computed on demand.
     */
    private transient int sequence = -1;

    /*
     * The node number associated with this UUID. Computed on demand.
     */
    private transient long node = -1;

    /*
     * The hashcode of this UUID. Computed on demand.
     */
    private transient int hashCode = -1;

    /*
     * The random number generator used by this class to create random
     * based UUIDs.
     */
    private static volatile SecureRandom numberGenerator = null;

    // Constructors and Factories

    /*
     * Private constructor which uses a byte array to construct the new UUID.
     */
    private UUID(byte[] data)
    {
        long msb = 0;
        long lsb = 0;
        for (int i = 0; i < 8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i = 8; i < 16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    /**
     * Constructs a new <tt>UUID</tt> using the specified data.
     * <tt>mostSigBits</tt> is used for the most significant 64 bits
     * of the <tt>UUID</tt> and <tt>leastSigBits</tt> becomes the
     * least significant 64 bits of the <tt>UUID</tt>.
     *
     * @param mostSigBits
     * @param leastSigBits
     */
    public UUID(long mostSigBits, long leastSigBits)
    {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    /**
     * Static factory to retrieve a type 4 (pseudo randomly generated) UUID.
     * <p/>
     * The <code>UUID</code> is generated using a cryptographically strong
     * pseudo random number generator.
     *
     * @return a randomly generated <tt>UUID</tt>.
     */
    @SuppressWarnings("unused")
	public static UUID randomUUID()
    {
        SecureRandom ng = numberGenerator;
        if (ng == null)
        {
            numberGenerator = ng = new SecureRandom();
        }

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        randomBytes[6] &= 0x0f;  /* clear version        */
        randomBytes[6] |= 0x40;  /* set to version 4     */
        randomBytes[8] &= 0x3f;  /* clear variant        */
        randomBytes[8] |= 0x80;  /* set to IETF variant  */
		UUID result = new UUID(randomBytes);
        return new UUID(randomBytes);
    }

    /**
     * Static factory to retrieve a type 3 (name based) <tt>UUID</tt> based on
     * the specified byte array.
     *
     * @param name a byte array to be used to construct a <tt>UUID</tt>.
     * @return a <tt>UUID</tt> generated from the specified array.
     */
    public static UUID nameUUIDFromBytes(byte[] name)
    {
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae)
        {
            throw new InternalError("MD5 not supported");
        }
        byte[] md5Bytes = md.digest(name);
        md5Bytes[6] &= 0x0f;  /* clear version        */
        md5Bytes[6] |= 0x30;  /* set to version 3     */
        md5Bytes[8] &= 0x3f;  /* clear variant        */
        md5Bytes[8] |= 0x80;  /* set to IETF variant  */
        return new UUID(md5Bytes);
    }

    /**
     * Creates a <tt>UUID</tt> from the string standard representation as
     * described in the {@link #toString} method.
     *
     * @param name a string that specifies a <tt>UUID</tt>.
     * @return a <tt>UUID</tt> with the specified value.
     * @throws IllegalArgumentException if name does not conform to the
     *                                  string representation as described in {@link #toString}.
     */
    public static UUID fromString(String name)
    {
        String[] components = name.split("-");
        if (components.length != 5)
            throw new IllegalArgumentException("Invalid UUID string: " + name);
        for (int i = 0; i < 5; i++)
            components[i] = "0x" + components[i];

        long mostSigBits = Long.decode(components[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(components[2]).longValue();

        long leastSigBits = Long.decode(components[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(components[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

    // Field Accessor Methods

    /**
     * Returns the least significant 64 bits of this UUID's 128 bit value.
     *
     * @return the least significant 64 bits of this UUID's 128 bit value.
     */
    public long getLeastSignificantBits()
    {
        return leastSigBits;
    }

    /**
     * Returns the most significant 64 bits of this UUID's 128 bit value.
     *
     * @return the most significant 64 bits of this UUID's 128 bit value.
     */
    public long getMostSignificantBits()
    {
        return mostSigBits;
    }

    /**
     * The version number associated with this <tt>UUID</tt>. The version
     * number describes how this <tt>UUID</tt> was generated.
     * <p/>
     * The version number has the following meaning:<p>
     * <ul>
     * <li>1    Time-based UUID
     * <li>2    DCE security UUID
     * <li>3    Name-based UUID
     * <li>4    Randomly generated UUID
     * </ul>
     *
     * @return the version number of this <tt>UUID</tt>.
     */
    public int version()
    {
        if (version < 0)
        {
            // Version is bits masked by 0x000000000000F000 in MS long
            version = (int) ((mostSigBits >> 12) & 0x0f);
        }
        return version;
    }

    /**
     * The variant number associated with this <tt>UUID</tt>. The variant
     * number describes the layout of the <tt>UUID</tt>.
     * <p/>
     * The variant number has the following meaning:<p>
     * <ul>
     * <li>0    Reserved for NCS backward compatibility
     * <li>2    The Leach-Salz variant (used by this class)
     * <li>6    Reserved, Microsoft Corporation backward compatibility
     * <li>7    Reserved for future definition
     * </ul>
     *
     * @return the variant number of this <tt>UUID</tt>.
     */
    public int variant()
    {
        if (variant < 0)
        {
            // This field is composed of a varying number of bits
            if ((leastSigBits >>> 63) == 0)
            {
                variant = 0;
            }
            else if ((leastSigBits >>> 62) == 2)
            {
                variant = 2;
            }
            else
            {
                variant = (int) (leastSigBits >>> 61);
            }
        }
        return variant;
    }

    /**
     * The timestamp value associated with this UUID.
     * <p/>
     * <p>The 60 bit timestamp value is constructed from the time_low,
     * time_mid, and time_hi fields of this <tt>UUID</tt>. The resulting
     * timestamp is measured in 100-nanosecond units since midnight,
     * October 15, 1582 UTC.<p>
     * <p/>
     * The timestamp value is only meaningful in a time-based UUID, which
     * has version type 1. If this <tt>UUID</tt> is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @throws UnsupportedOperationException if this UUID is not a
     *                                       version 1 UUID.
     */
    public long timestamp()
    {
        if (version() != 1)
        {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        long result = timestamp;
        if (result < 0)
        {
            result = (mostSigBits & 0x0000000000000FFFL) << 48;
            result |= ((mostSigBits >> 16) & 0xFFFFL) << 32;
            result |= mostSigBits >>> 32;
            timestamp = result;
        }
        return result;
    }

    /**
     * The clock sequence value associated with this UUID.
     * <p/>
     * <p>The 14 bit clock sequence value is constructed from the clock
     * sequence field of this UUID. The clock sequence field is used to
     * guarantee temporal uniqueness in a time-based UUID.<p>
     * <p/>
     * The  clockSequence value is only meaningful in a time-based UUID, which
     * has version type 1. If this UUID is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @return the clock sequence of this <tt>UUID</tt>.
     * @throws UnsupportedOperationException if this UUID is not a
     *                                       version 1 UUID.
     */
    public int clockSequence()
    {
        if (version() != 1)
        {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        if (sequence < 0)
        {
            sequence = (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
        }
        return sequence;
    }

    /**
     * The node value associated with this UUID.
     * <p/>
     * <p>The 48 bit node value is constructed from the node field of
     * this UUID. This field is intended to hold the IEEE 802 address
     * of the machine that generated this UUID to guarantee spatial
     * uniqueness.<p>
     * <p/>
     * The node value is only meaningful in a time-based UUID, which
     * has version type 1. If this UUID is not a time-based UUID then
     * this method throws UnsupportedOperationException.
     *
     * @return the node value of this <tt>UUID</tt>.
     * @throws UnsupportedOperationException if this UUID is not a
     *                                       version 1 UUID.
     */
    public long node()
    {
        if (version() != 1)
        {
            throw new UnsupportedOperationException("Not a time-based UUID");
        }
        if (node < 0)
        {
            node = leastSigBits & 0x0000FFFFFFFFFFFFL;
        }
        return node;
    }

    // Object Inherited Methods

    /**
     * Returns a <code>String</code> object representing this
     * <code>UUID</code>.
     * <p/>
     * <p>The UUID string representation is as described by this BNF :
     * <pre>
     *  UUID                   = <time_low> "-" <time_mid> "-"
     *                           <time_high_and_version> "-"
     *                           <variant_and_sequence> "-"
     *                           <node>
     *  time_low               = 4*<hexOctet>
     *  time_mid               = 2*<hexOctet>
     *  time_high_and_version  = 2*<hexOctet>
     *  variant_and_sequence   = 2*<hexOctet>
     *  node                   = 6*<hexOctet>
     *  hexOctet               = <hexDigit><hexDigit>
     *  hexDigit               =
     *        "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
     *        | "a" | "b" | "c" | "d" | "e" | "f"
     *        | "A" | "B" | "C" | "D" | "E" | "F"
     * </pre>
     *
     * @return a string representation of this <tt>UUID</tt>.
     */
    public String toString()
    {
        return (digits(mostSigBits >> 32, 8) + "-" +
                digits(mostSigBits >> 16, 4) + "-" +
                digits(mostSigBits, 4) + "-" +
                digits(leastSigBits >> 48, 4) + "-" +
                digits(leastSigBits, 12));
    }

    /**
     * Returns val represented by the specified number of hex digits.
     */
    private static String digits(long val, int digits)
    {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    /**
     * Returns a hash code for this <code>UUID</code>.
     *
     * @return a hash code value for this <tt>UUID</tt>.
     */
    public int hashCode()
    {
        if (hashCode == -1)
        {
            hashCode = (int) ((mostSigBits >> 32) ^
                    mostSigBits ^
                    (leastSigBits >> 32) ^
                    leastSigBits);
        }
        return hashCode;
    }

    /**
     * Compares this object to the specified object.  The result is
     * <tt>true</tt> if and only if the argument is not
     * <tt>null</tt>, is a <tt>UUID</tt> object, has the same variant,
     * and contains the same value, bit for bit, as this <tt>UUID</tt>.
     *
     * @param obj the object to compare with.
     * @return <code>true</code> if the objects are the same;
     *         <code>false</code> otherwise.
     */
    public boolean equals(Object obj)
    {
        if (!(obj instanceof UUID))
            return false;
        if (((UUID) obj).variant() != this.variant())
            return false;
        UUID id = (UUID) obj;
        return (mostSigBits == id.mostSigBits &&
                leastSigBits == id.leastSigBits);
    }

    // Comparison Operations

    /**
     * Compares this UUID with the specified UUID.
     * <p/>
     * <p>The first of two UUIDs follows the second if the most significant
     * field in which the UUIDs differ is greater for the first UUID.
     *
     * @param val <tt>UUID</tt> to which this <tt>UUID</tt> is to be compared.
     * @return -1, 0 or 1 as this <tt>UUID</tt> is less than, equal
     *         to, or greater than <tt>val</tt>.
     */
    public int compareTo(UUID val)
    {
        // The ordering is intentionally set up so that the UUIDs
        // can simply be numerically compared as two numbers
        return (this.mostSigBits < val.mostSigBits ? -1 :
                (this.mostSigBits > val.mostSigBits ? 1 :
                        (this.leastSigBits < val.leastSigBits ? -1 :
                                (this.leastSigBits > val.leastSigBits ? 1 :
                                        0))));
    }

    /**
     * Reconstitute the <tt>UUID</tt> instance from a stream (that is,
     * deserialize it). This is necessary to set the transient fields
     * to their correct uninitialized value so they will be recomputed
     * on demand.
     */
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException
    {

        in.defaultReadObject();

        // Set "cached computation" fields to their initial values
        version = -1;
        variant = -1;
        timestamp = -1;
        sequence = -1;
        node = -1;
        hashCode = -1;
    }
}
```
# ValidateUtil.java
```java
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: 吴超
 * 时间: 2017年9月2日下午8:38:17
 * 说明: 常用的数据验证工具类。
 *
 */
public class ValidateUtil {


	 public static final Pattern CODE_PATTERN = Pattern.compile("^0\\d{2,4}$");
	 public static final Pattern POSTCODE_PATTERN = Pattern.compile("^\\d{6}$");
	 public static final Pattern BANK_CARD_PATTERN = Pattern.compile("^\\d{16,30}$");
	 /**
	     * 匹配图象
	     *  
	     *  
	     * 格式: /相对路径/文件名.后缀 (后缀为gif,dmp,png)
	     *  
	     * 匹配 : /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp
	     *  
	     *  
	     * 不匹配: c:/admins4512.gif
	     *  
	     */  
	    public static final String ICON_REGEXP = "^(/{0,1}//w){1,}//.(gif|dmp|png|jpg)$|^//w{1,}//.(gif|dmp|png|jpg)$";  

	    /**
	     * 匹配email地址
	     *  
	     *  
	     * 格式: XXX@XXX.XXX.XX
	     *  
	     * 匹配 : foo@bar.com 或 foobar@foobar.com.au
	     *  
	     * 不匹配: foo@bar 或 $$$@bar.com
	     *  
	     */  
	    public static final String EMAIL_REGEXP = "(?://w[-._//w]*//w@//w[-._//w]*//w//.//w{2,3}$)";  

	    /**
	     * 匹配并提取url
	     *  
	     *  
	     * 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	     *  
	     * 匹配 : http://www.suncer.com 或news://www
	     *  
	     * 不匹配: c:/window
	     *  
	     */  
	    public static final String URL_REGEXP = "(//w+)://([^/:]+)(://d*)?([^#//s]*)";  

	    /**
	     * 匹配并提取http
	     *  
	     * 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或
	     * https://XXX
	     *  
	     * 匹配 : http://www.suncer.com:8080/index.html?login=true
	     *  
	     * 不匹配: news://www
	     *  
	     */  
	    public static final String HTTP_REGEXP = "(http|https|ftp)://([^/:]+)(://d*)?([^#//s]*)";  

	    /**
	     * 匹配日期
	     *  
	     *  
	     * 格式(首位不为0): XXXX-XX-XX或 XXXX-X-X
	     *  
	     *  
	     * 范围:1900--2099
	     *  
	     *  
	     * 匹配 : 2005-04-04
	     *  
	     *  
	     * 不匹配: 01-01-01
	     *  
	     */  
	    public static final String DATE_BARS_REGEXP = "^((((19){1}|(20){1})\\d{2})|\\d{2})-[0,1]?\\d{1}-[0-3]?\\d{1}$";    

	    /**
	     * 匹配日期
	     *  
	     *  
	     * 格式: XXXX/XX/XX
	     *  
	     *  
	     * 范围:
	     *  
	     *  
	     * 匹配 : 2005/04/04
	     *  
	     *  
	     * 不匹配: 01/01/01
	     *  
	     */  
	    public static final String DATE_SLASH_REGEXP = "^[0-9]{4}/(((0[13578]|(10|12))/(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)/(0[1-9]|[1-2][0-9]|30)))$";  

	    /**
	     * 匹配电话
	     *  
	     *  
	     * 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或
	     *  
	     * (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或
	     * XXXXXXXXXXX(11位首位不为0)
	     *  
	     *  
	     * 匹配 : 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或
	     * 010-12345678 或 12345678912
	     *  
	     *  
	     * 不匹配: 1111-134355 或 0123456789
	     *  
	     */  
	    public static final String PHONE_REGEXP = "^(?:0[0-9]{2,3}[-//s]{1}|//(0[0-9]{2,4}//))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";  

	    /**
	     * 匹配身份证
	     *  
	     * 格式为: XXXXXXXXXX(10位) 或 XXXXXXXXXXXXX(13位) 或 XXXXXXXXXXXXXXX(15位) 或
	     * XXXXXXXXXXXXXXXXXX(18位)
	     *  
	     * 匹配 : 0123456789123
	     *  
	     * 不匹配: 0123456
	     *  
	     */  
	    public static final String ID_CARD_REGEXP = "^//d{10}|//d{13}|//d{15}|//d{18}$";  

	    /**
	     * 匹配邮编代码
	     *  
	     * 格式为: XXXXXX(6位)
	     *  
	     * 匹配 : 012345
	     *  
	     * 不匹配: 0123456
	     *  
	     */  
	    public static final String ZIP_REGEXP = "^[0-9]{6}$";// 匹配邮编代码  

	    /**
	     * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠/
	     * 即空格,制表符,回车符等 )
	     *  
	     * 格式为: x 或 一个一上的字符
	     *  
	     * 匹配 : 012345
	     *  
	     * 不匹配: 0123456 // ;,:-<>//s].+$";//
	     */  
	    public static final String NON_SPECIAL_CHAR_REGEXP = "^[^'/";  
	    // 匹配邮编代码  

	    /**
	     * 匹配非负整数（正整数 + 0)
	     */  
	    public static final String NON_NEGATIVE_INTEGERS_REGEXP = "^//d+$";  

	    /**
	     * 匹配不包括零的非负整数（正整数 > 0)
	     */  
	    public static final String NON_ZERO_NEGATIVE_INTEGERS_REGEXP = "^[1-9]+//d*$";  

	    /**
	     *  
	     * 匹配正整数
	     *  
	     */  
	    public static final String POSITIVE_INTEGER_REGEXP = "^[0-9]*[1-9][0-9]*$";  

	    /**
	     *  
	     * 匹配非正整数（负整数 + 0）
	     *  
	     */  
	    public static final String NON_POSITIVE_INTEGERS_REGEXP = "^((-//d+)|(0+))$";  

	    /**
	     *  
	     * 匹配负整数
	     *  
	     */  
	    public static final String NEGATIVE_INTEGERS_REGEXP = "^-[0-9]*[1-9][0-9]*$";  

	    /**
	     *  
	     * 匹配整数
	     *  
	     */  
	    public static final String INTEGER_REGEXP = "^-?//d+$";  

	    /**
	     *  
	     * 匹配非负浮点数（正浮点数 + 0）
	     *  
	     */  
	    public static final String NON_NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^//d+(//.//d+)?$";  

	    /**
	     *  
	     * 匹配正浮点数
	     *  
	     */  
	    public static final String POSITIVE_RATIONAL_NUMBERS_REGEXP = "^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$";  

	    /**
	     *  
	     * 匹配非正浮点数（负浮点数 + 0）
	     *  
	     */  
	    public static final String NON_POSITIVE_RATIONAL_NUMBERS_REGEXP = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";  

	    /**
	     *  
	     * 匹配负浮点数
	     *  
	     */  
	    public static final String NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^(-(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*)))$";  

	    /**
	     *  
	     * 匹配浮点数
	     *  
	     */  
	    public static final String RATIONAL_NUMBERS_REGEXP = "^(-?//d+)(//.//d+)?$";  

	    /**
	     *  
	     * 匹配由26个英文字母组成的字符串
	     *  
	     */  
	    public static final String LETTER_REGEXP = "^[A-Za-z]+$";  

	    /**
	     *  
	     * 匹配由26个英文字母的大写组成的字符串
	     *  
	     */  
	    public static final String UPWARD_LETTER_REGEXP = "^[A-Z]+$";  

	    /**
	     *  
	     * 匹配由26个英文字母的小写组成的字符串
	     *  
	     */  
	    public static final String LOWER_LETTER_REGEXP = "^[a-z]+$";  

	    /**
	     *  
	     * 匹配由数字和26个英文字母组成的字符串
	     *  
	     */  
	    public static final String LETTER_NUMBER_REGEXP = "^[A-Za-z0-9]+$";  

	    /**
	     *  
	     * 匹配由数字、26个英文字母或者下划线组成的字符串
	     *  
	     */  
	    public static final String LETTER_NUMBER_UNDERLINE_REGEXP = "^//w+$";  


	public static boolean validateEmail(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		Pattern pattern = Pattern.compile(
				"^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

		// Pattern pattern =
		// Pattern.compile("^([a-zA-Z0-9_-])+@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}");
		Matcher matcher = pattern.matcher(str);

		return matcher.matches();

	}

	public static boolean validateMoblie(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("^(13|14|15|17|18)[0-9]{9}$");
		Matcher matcher = pattern.matcher(str);

		return matcher.matches();

	}

	 /**
     * 验证区号是否有效.
     *
     * @param code 要验证的区号
     * @return 是否正确身份证
     */
    public static boolean validateCode(String code) {
        if (StringHelper.isEmpty(code)) {
            return false;
        }
        Matcher m = CODE_PATTERN.matcher(code);
        return m.matches();
    }

    /**
     * 验证邮政编码是否有效.
     *
     * @param postcode 要验证的邮政编码
     * @return 是否正确邮政编码
     */
    public static boolean validatePostcode(String postcode) {
        if (StringHelper.isEmpty(postcode)) {
            return false;
        }
        Matcher m = POSTCODE_PATTERN.matcher(postcode);
        return m.matches();
    }

    /**
     * 验证银行卡是否有效.
     *
     * @param bankCardNumber 要验证的银行卡号
     * @return 是否正确银行卡号
     */
    public static boolean validateBankCardNumber(String bankCardNumber) {
        if (StringHelper.isEmpty(bankCardNumber)) {
            return false;
        }
        Matcher m = BANK_CARD_PATTERN.matcher(bankCardNumber);
        return m.matches();
    }

    /**
     * 通过身份证获取生日
     *
     * @param idNumber 身份证号
     * @return 返回生日, 格式为 yyyy-MM-dd 的字符串
     */
    public static String getBirthdayByIdNumber(String idNumber) {

        String birthday = "";

        if (idNumber.length() == 15) {
            birthday = "19" + idNumber.substring(6, 8) + "-" + idNumber.substring(8, 10) + "-" + idNumber.substring(10, 12);
        } else if (idNumber.length() == 18) {
            birthday = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-" + idNumber.substring(12, 14);
        }

        return birthday;

    }

    /**
     * 通过身份证获取性别
     *
     * @param idNumber 身份证号
     * @return 返回性别, 0 保密 , 1 男 2 女
     */
    public static Integer getGenderByIdNumber(String idNumber) {

        int gender = 0;

        if (idNumber.length() == 15) {
            gender = Integer.parseInt(String.valueOf(idNumber.charAt(14))) % 2 == 0 ? 2 : 1;
        } else if (idNumber.length() == 18) {
            gender = Integer.parseInt(String.valueOf(idNumber.charAt(16))) % 2 == 0 ? 2 : 1;
        }

        return gender;

    }

	/**
     * 通过身份证获取年龄
     *
     * @param idNumber     身份证号
     * @param isNominalAge 是否按元旦算年龄，过了1月1日加一岁 true : 是 false : 否
     * @return 返回年龄
     */
    public static Integer getAgeByIdNumber(String idNumber, boolean isNominalAge) {

        String birthString = getBirthdayByIdNumber(idNumber);
        if (StringHelper.isEmpty(birthString)) {
            return 0;
        }

        return getAgeByBirthString(birthString, isNominalAge);

    }


	 /**
     * 通过生日日期获取年龄
     *
     * @param birthDate 生日日期
     * @return 返回年龄
     */
    public static Integer getAgeByBirthDate(Date birthDate) {

        return getAgeByBirthString(new SimpleDateFormat("yyyy-MM-dd").format(birthDate));

    }

	 /**
     * 通过生日字符串获取年龄
     *
     * @param birthString 生日字符串
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString) {

        return getAgeByBirthString(birthString, "yyyy-MM-dd");

    }


	 /**
     * 通过生日字符串获取年龄
     *
     * @param birthString  生日字符串
     * @param isNominalAge 是否按元旦算年龄，过了1月1日加一岁 true : 是 false : 否
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString, boolean isNominalAge) {

        return getAgeByBirthString(birthString, "yyyy-MM-dd", isNominalAge);

    }

	/**
     * 通过生日字符串获取年龄
     *
     * @param birthString 生日字符串
     * @param format      日期字符串格式,为空则默认"yyyy-MM-dd"
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString, String format) {
        return getAgeByBirthString(birthString, "yyyy-MM-dd", false);
    }

	/**
     * 通过生日字符串获取年龄
     *
     * @param birthString  生日字符串
     * @param format       日期字符串格式,为空则默认"yyyy-MM-dd"
     * @param isNominalAge 是否按元旦算年龄，过了1月1日加一岁 true : 是 false : 否
     * @return 返回年龄
     */
    public static Integer getAgeByBirthString(String birthString, String format, boolean isNominalAge) {
        int age = 0;
        if (StringHelper.isEmpty(birthString)) {
            return age;
        }
        if (StringHelper.isEmpty(format)) {
            format = "yyyy-MM-dd";
        }
        try {

            Calendar birthday = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            birthday.setTime(sdf.parse(birthString));
            age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
            if (!isNominalAge) {
                if (today.get(Calendar.MONTH) < birthday.get(Calendar.MONTH) ||
                        (today.get(Calendar.MONTH) == birthday.get(Calendar.MONTH) &&
                                today.get(Calendar.DAY_OF_MONTH) < birthday.get(Calendar.DAY_OF_MONTH))) {
                    age = age - 1;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return age;

    }

    /**
     * 大小写敏感的正规表达式批配
     *  
     * @param source
     *            批配的源字符串
     * @param regexp
     *            批配的正规表达式
     * @return 如果源字符串符合要求返回真,否则返回假
     */  
    public static boolean isHardRegexpValidate(String str, String regexp) {
	    	if (str == null || str.trim().length() == 0) {
				return false;
			}
        	Pattern pattern = Pattern.compile(regexp);
    		Matcher matcher = pattern.matcher(str);
    		return matcher.matches();
    }  
}
```
# XMLHelper.java
```java
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *   
 *
 * @Title: XMLHelper.java
 * @Package com.jarvis.base.util
 * @Description:XML工具类
 * @author Jack 
 * @date 2017年9月2日 下午4:08:35
 * @version V1.0  
 */
public final class XMLHelper {
	/**
	 * 把XML按照给定的XSL进行转换，返回转换后的值
	 *
	 * @param xml
	 *            xml
	 * @param xsl
	 *            xsl
	 * @return
	 * @throws Exception
	 */
	public static String xml2xsl(String xml, URL xsl) throws Exception {
		if (StringHelper.isEmpty(xml)) {
			throw new Exception("xml string is empty");
		}
		if (xsl == null) {
			throw new Exception("xsl string is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;

		try {
			xmlSource = new StreamSource(new StringReader(xml));
			xslSource = new StreamSource(xsl.openStream());
			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			trans.transform(xmlSource, result);
			return writer.toString();
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer.close();
			writer = null;
			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 把XML按用户定义好的XSL样式进行输出
	 *
	 * @param xmlFilePath
	 *            XML文档
	 * @param xsl
	 *            XSL样式
	 * @return 样式化后的字段串
	 */
	public static String xml2xsl(String xmlFilePath, String xsl) throws Exception {
		if (StringHelper.isEmpty(xmlFilePath)) {
			throw new Exception("xml string is empty");
		}
		if (StringHelper.isEmpty(xsl)) {
			throw new Exception("xsl string is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = new StreamSource(new File(xmlFilePath));
		Source xslSource = new StreamSource(new File(xsl));
		Result result = new StreamResult(writer);

		try {
			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "UTF-8");
			properties.put(OutputKeys.METHOD, "html");
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);
			return writer.toString();
		} finally {
			writer.close();
			writer = null;

			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 读取XML文档，返回Document对象.<br>
	 *
	 * @param xmlFile
	 *            XML文件路径
	 * @return Document 对象
	 */
	public static Document getDocument(String xmlFile) throws Exception {
		if (StringHelper.isEmpty(xmlFile)) {
			return null;
		}

		File file = null;
		SAXReader saxReader = new SAXReader();

		file = new File(xmlFile);
		return saxReader.read(file);
	}

	/**
	 * 读取XML文档，返回Document对象.<br>
	 *
	 * @param xmlFile
	 *            file对象
	 * @return Document 对象
	 */
	public static Document getDocument(File xmlFile) {
		try {
			SAXReader saxReader = new SAXReader();
			return saxReader.read(xmlFile);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("读取xml文件出错，返回null");
			return null;
		}
	}

	/**
	 * 读取XML字串，返回Document对象
	 *
	 * @param xmlString
	 *            XML文件路径
	 * @return Document 对象
	 */
	public static Document getDocumentFromString(String xmlString) {
		if (StringHelper.isEmpty(xmlString)) {
			return null;
		}
		try {
			SAXReader saxReader = new SAXReader();
			return saxReader.read(new StringReader(xmlString));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("读取xml文件出错，返回null");
			return null;
		}
	}

	/**
	 * 描述：把xml输出成为html 作者： 时间：Oct 29, 2008 4:57:56 PM
	 *
	 * @param xmlDoc
	 *            xmlDoc
	 * @param xslFile
	 *            xslFile
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	public static String xml2html(String xmlDoc, String xslFile, String encoding) throws Exception {
		if (StringHelper.isEmpty(xmlDoc)) {
			throw new Exception("xml string is empty");
		}
		if (StringHelper.isEmpty(xslFile)) {
			throw new Exception("xslt file is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;
		String html = null;
		try {
			xmlSource = new StreamSource(new StringReader(xmlDoc));
			xslSource = new StreamSource(new File(xslFile));

			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.put(OutputKeys.METHOD, "html");
			properties.setProperty(OutputKeys.ENCODING, encoding);
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);

			html = writer.toString();
			writer.close();

			return html;
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer = null;

			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 描述：把xml输出成为html
	 *
	 * @param xmlFile
	 *            xmlFile
	 * @param xslFile
	 *            xslFile
	 * @param encoding
	 *            编码
	 * @return
	 * @throws Exception
	 */
	public static String xmlFile2html(String xmlFile, String xslFile, String encoding) throws Exception {
		if (StringHelper.isEmpty(xmlFile)) {
			throw new Exception("xml string is empty");
		}
		if (StringHelper.isEmpty(xslFile)) {
			throw new Exception("xslt file is empty");
		}

		StringWriter writer = new StringWriter();
		Source xmlSource = null;
		Source xslSource = null;
		Result result = null;
		String html = null;
		try {
			xmlSource = new StreamSource(new File(xmlFile));
			xslSource = new StreamSource(new File(xslFile));

			result = new StreamResult(writer);

			TransformerFactory transFact = TransformerFactory.newInstance();
			Transformer trans = transFact.newTransformer(xslSource);
			Properties properties = trans.getOutputProperties();
			properties.put(OutputKeys.METHOD, "html");
			properties.setProperty(OutputKeys.ENCODING, encoding);
			trans.setOutputProperties(properties);

			trans.transform(xmlSource, result);

			html = writer.toString();
			writer.close();

			return html;
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			writer = null;

			xmlSource = null;
			xslSource = null;
			result = null;
		}
	}

	/**
	 * 描述：
	 *
	 * @param name
	 *            名
	 * @param element
	 *            元素
	 * @return
	 */
	public static String getString(String name, Element element) {
		return (element.valueOf(name) == null) ? "" : element.valueOf(name);
	}

	/**
	 * 将一个XML文档保存至文件中.
	 *
	 * @param doc
	 *            要保存的XML文档对象.
	 * @param filePath
	 *            要保存到的文档路径.
	 * @param format
	 *            要保存的输出格式
	 * @return true代表保存成功，否则代表不成功.
	 */
	public static boolean savaToFile(Document doc, String filePathName, OutputFormat format) {
		XMLWriter writer;
		try {
			String filePath = FileHelper.getFullPath(filePathName);
			// 若目录不存在，则建立目录
			if (!FileHelper.exists(filePath)) {
				if (!FileHelper.createDirectory(filePath)) {
					return false;
				}
			}

			writer = new XMLWriter(new FileWriter(new File(filePathName)), format);
			writer.write(doc);
			writer.close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("写文件出错");
		}

		return false;
	}

	/**
	 * 将一个XML文档保存至文件中.
	 *
	 * @param filePath
	 *            要保存到的文档路径.
	 * @param doc
	 *            要保存的XML文档对象.
	 * @return true代表保存成功，否则代表不成功.
	 */
	public static boolean writeToXml(String filePathName, Document doc) {
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("UTF-8");
		return savaToFile(doc, filePathName, format);
	}
}
```
