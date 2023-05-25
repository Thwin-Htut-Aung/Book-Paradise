package com.book.paradise.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PDFToImageService {

	public int convertPDFToImage(String filePath, String outputName) throws IOException {
	    PDDocument document = PDDocument.load(new File(filePath));
	    PDFRenderer renderer = new PDFRenderer(document);
	    String writePath = "D:/Spring Project Workspace/Sartoon-Web-Novel/src/main/resources/static/novel pages/";
	    for (int page = 0; page < document.getNumberOfPages(); page++) {
	        BufferedImage image = renderer.renderImageWithDPI(
	          page, 300, ImageType.RGB);
	      
	        ImageIOUtil.writeImage(
	          image, writePath+outputName+"-page"+(page+1)+".jpg", 300);
	        
	    }
	    document.close();
	    
	    return document.getNumberOfPages();
	    
	}
	
}
