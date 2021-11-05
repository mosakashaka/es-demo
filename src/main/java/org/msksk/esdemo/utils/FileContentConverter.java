package org.msksk.esdemo.utils;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileContentConverter {
    /**
     * JSON字符串特殊字符处理     * @param s     * @return String
     */
    public static String stringJson(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
//                case '    ':
//                    sb.append("\    ");
//                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * word读取工具     * @param path     * @return
     */
    public static String readWord(InputStream is, boolean isExtendedVersion) {
        String buffer = "";
        POITextExtractor ex = null;
        try {
            if (!isExtendedVersion) {
                ex = new WordExtractor(is);
                buffer = ex.getText();
            } else {
                XWPFDocument doc = new XWPFDocument(is);
                ex = new XWPFWordExtractor(doc);
                buffer = ex.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ex) {
                try {
                    ex.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }

    /**
     * excel读取工具     * @param path     * @return
     */
    public static String readExcel(InputStream is, boolean isExtendedVersion) {
        String buffer = "";
        POITextExtractor ex = null;
        try {
            if (!isExtendedVersion) {
                HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
                ex = new ExcelExtractor(wb);
                ((ExcelExtractor) ex).setIncludeSheetNames(true);
                ((ExcelExtractor) ex).setFormulasNotResults(true);
                ((ExcelExtractor) ex).setIncludeCellComments(true);
                buffer = ex.getText();
            } else {
                ex = new XSSFExcelExtractor(OPCPackage.open(is));
                buffer = ex.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ex) {
                try {
                    ex.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }

    /**
     * pdf读取工具     * @param path     * @return
     */
    public static String readPdf(InputStream is) {
        String buffer = "";
        try {
            PdfDocument doc = new PdfDocument(is);
            PdfPageBase page = null;
            for (int i = 0; i < doc.getPages().getCount(); i++) {
                page = doc.getPages().get(i);
                buffer += page.extractText(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    private static boolean isPlainText(String suffix) {
        switch (suffix) {
            case "txt":
                return true;
            case "inf":
                return true;
            case "conf":
                return true;
            case "cnf":
                return true;
            case "md":
                return true;
            default:
                return false;
        }
    }

    /**
     * 根据不同类型，获取内容     * @param path     * @return
     */
    public static String readContent(InputStream is, String suffix) {
        String str = "";
        suffix = (null == suffix) ? "" : suffix.toLowerCase();
        switch (suffix) {
            case "doc":
                str = readWord(is, false);
                break;
            case "docx":
                str = readWord(is, true);
                break;
            case "xls":
                str = readExcel(is, false);
                break;
            case "xlsx":
                str = readExcel(is, true);
                break;
            case "pdf":
                str = readPdf(is);
                break;
            default:
                if (isPlainText(suffix)) {
                    try {
                        ByteArrayOutputStream result = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while (true) {
                            if (!((length = is.read(buffer)) != -1)) break;
                            result.write(buffer, 0, length);
                        }
                        str = result.toString("UTF-8");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        return str;
    }
}