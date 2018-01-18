package net.jacoblo.BMP;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Main {
  public static void main(String[] args) {
//    TagBitmapInfoHeader tb = new TagBitmapInfoHeader();
//    byte[] result = tb.toByteArray();
//    
//    System.out.println(result.length);
//    for (byte b : result) {
//      String s = String.format("%02X", b);
//      System.out.print(s + " ");
//    }
//    
//    result[5] = 9;
//    result[7] = 8;
//    result[9] = 7;
//    result[13] = 5;
//    
//    tb.toObject(result);
//    System.out.println();
//    System.out.println("" + tb.bfSize + " " + tb.bfReserved1 + " " + tb.bfReserved2 + " " + tb.bfOffBits);
    
//    BufferedImage bi;
//    try {
//      bi = ImageIO.read(new File("C:\\Users\\lok1\\Desktop\\1px.bmp"));
//      BMPFile.Bitmap bp = new BMPFile.Bitmap(bi,2,2);
//      
//      byte[] result = bp.m_Bitmap;
//      System.out.println(result.length);
//      for (byte b : result) {
//        String s = String.format("%02X", b);
//        System.out.print(s + " ");
//      }
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    
    
    int[] tp = { 150000, 200000 };
    byte[] orig = ByteUtil.toByta(tp);
    for (byte b : orig) {
      String s = String.format("%02X", b);
      System.out.print(s + " ");
    }
    System.out.println("");
    
    BMPFile.Bitmap bp = new BMPFile.Bitmap(null, 0, 0);
    bp.m_Height = 1;
    bp.m_Width = 2;
    bp.m_Pixels = tp;
    byte[] out = bp.convertPixelsToBitmapFormat();
    for (byte b : out) {
      String s = String.format("%02X", b);
      System.out.print(s + " ");
    }
  }
  
}
