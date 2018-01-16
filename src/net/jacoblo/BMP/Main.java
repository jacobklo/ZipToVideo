package net.jacoblo.BMP;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    BMPFile.TagBitmapInfoHeader tb = new BMPFile.TagBitmapInfoHeader();
    byte[] result = tb.toByteArray();
    
    System.out.println(result.length);
    for (byte b : result) {
      String s = String.format("%02X", b);
      System.out.print(s + " ");
    }
    
//    result[5] = 9;
//    result[7] = 8;
//    result[9] = 7;
//    result[13] = 5;
//    
//    tb.toObject(result);
//    System.out.println();
//    System.out.println("" + tb.bfSize + " " + tb.bfReserved1 + " " + tb.bfReserved2 + " " + tb.bfOffBits);
  }
  
}
