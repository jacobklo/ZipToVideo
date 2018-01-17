package net.jacoblo.BMP;

import java.awt.image.BufferedImage;
import java.util.Arrays;

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
    
    BufferedImage bi = new BufferedImage(2,2,BufferedImage.TYPE_3BYTE_BGR);
    BMPFile.Bitmap bp = new BMPFile.Bitmap(bi,2,2);
    
    byte[] result = bp.m_Bitmap;
    System.out.println(result.length);
    for (byte b : result) {
      String s = String.format("%02X", b);
      System.out.print(s + " ");
    }
  }
  
}
