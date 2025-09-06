package com.rocklin.offer.common.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import static com.rocklin.offer.common.constants.Constants.*;

public class PdfToImageUtil {

    /**
     * 将 PDF 转换为压缩后的 JPG 图片（使用 TwelveMonkeys 压缩器）
     *
     * @param pdfFile   输入 PDF 文件
     * @param outputDir 输出目录
     * @throws IOException
     */
    public static void convert(File pdfFile, File outputDir) throws IOException {
        final int DPI = 120;        // 分辨率，可调
        final float QUALITY = 0.7f; // 压缩质量（0.0 最小体积 ~ 1.0 原画质）

        // 确保输出目录存在
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                // 渲染 PDF 页为 BufferedImage
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI);

                File outputFile = new File(outputDir, PDF_PAGE_PREFIX + (page + 1) + PDF_PAGE_SUFFIX);

                // 使用 TwelveMonkeys 的 ImageWriter 进行 JPEG 压缩
                try (FileOutputStream fos = new FileOutputStream(outputFile);
                     ImageOutputStream ios = ImageIO.createImageOutputStream(fos)) {

                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
                    if (!writers.hasNext()) {
                        throw new IllegalStateException("未找到 JPEG 编码器");
                    }
                    ImageWriter writer = writers.next();
                    writer.setOutput(ios);

                    ImageWriteParam param = writer.getDefaultWriteParam();
                    if (param.canWriteCompressed()) {
                        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        param.setCompressionQuality(QUALITY); // 设置压缩比
                    }

                    writer.write(null, new IIOImage(bim, null, null), param);
                    writer.dispose();
                }

                System.out.println("已生成并压缩: " + outputFile.getAbsolutePath());
            }
        }
    }
}
