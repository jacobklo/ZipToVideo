package net.jacoblo.BMP;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PictureConverter {
  public static void main(String[] args) {
    PictureConverter pc = new PictureConverter();
    try {
      pc.createImage();
    }
    catch(Exception e) {
      
    }
  }
  
  public void createImage() throws IOException {
    int width = 10, height = 10;
    BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\lok1\\Desktop\\test.bmp"));
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "bmp", baos);
    baos.flush();
    byte[] imageInByte = baos.toByteArray();
    baos.close();
    
    for (byte b : imageInByte) {
      String s = String.format("%02X", b);
      System.out.print(s);
    }
  }
}
