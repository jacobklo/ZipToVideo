package net.jacoblo.BMP;

import java.util.Arrays;

public class BMPFile {
  private final static int BITMAP_FILE_HEADER_SIZE = 14;
  private final static int BITMAP_INFO_HEADER_SIZE = 40;
  
  public static class TagBitmapFileHeader {
    public byte[] bfType = {'B', 'M'};
    public int bfSize = 1;
    public short bfReserved1 = 1;
    public short bfReserved2 = 1;
    public int bfOffBits = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;
    
    public byte[] toByteArray() {
      byte[] result = new byte[BITMAP_FILE_HEADER_SIZE];
      int position = 0;
      System.arraycopy(ByteUtil.toByta(bfType), 0, result, position, bfType.length);
      position += bfType.length;
      System.arraycopy(ByteUtil.toByta(bfSize), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(bfReserved1), 0, result, position, Short.BYTES);
      position += Short.BYTES;
      System.arraycopy(ByteUtil.toByta(bfReserved2), 0, result, position, Short.BYTES);
      position += Short.BYTES;
      System.arraycopy(ByteUtil.toByta(bfOffBits), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      return result;
    }
    
    public boolean toObject(byte[] bytes) {
      if (bytes == null || bytes.length != BITMAP_FILE_HEADER_SIZE ) return false;
      
      int position = 0;
      bfType[0] = bytes[position];
      position+= Byte.BYTES;
      bfType[1] = bytes[position];
      position+= Byte.BYTES;
      bfSize = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      bfReserved1 = ByteUtil.toShort(Arrays.copyOfRange(bytes, position, position + Short.BYTES));
      position += Short.BYTES;
      bfReserved2 = ByteUtil.toShort(Arrays.copyOfRange(bytes, position, position + Short.BYTES));
      position += Short.BYTES;
      bfOffBits = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      
      return true;
    }
  }
  
  public static class TagBitmapInfoHeader {
    public int biSize = BITMAP_INFO_HEADER_SIZE;
    public int biWidth = 0;
    public int biHeight = 0;
    public short biPlanes = 1;
    public short biBitCount = 24;
    public int biCompression = 0;
    public int biSizeImage = 0x030000;
    public int biXPelsPerMeter = 0x0;
    public int biYPelsPerMeter = 0x0;
    public int biClrUsed = 0;
    public int biClrImportant = 0;
    
    public byte[] toByteArray() {
      byte[] result = new byte[BITMAP_INFO_HEADER_SIZE];
      int position = 0;
      
      System.arraycopy(ByteUtil.toByta(biSize), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biWidth), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biHeight), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biPlanes), 0, result, position, Short.BYTES);
      position += Short.BYTES;
      System.arraycopy(ByteUtil.toByta(biBitCount), 0, result, position, Short.BYTES);
      position += Short.BYTES;
      System.arraycopy(ByteUtil.toByta(biCompression), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biSizeImage), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biXPelsPerMeter), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biYPelsPerMeter), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biClrUsed), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      System.arraycopy(ByteUtil.toByta(biClrImportant), 0, result, position, Integer.BYTES);
      position += Integer.BYTES;
      
      return result;
    }
    
    public boolean toObject(byte[] bytes) {
      if (bytes == null || bytes.length != BITMAP_INFO_HEADER_SIZE ) return false;
      int position = 0;
      
      biSize = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biWidth = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biHeight = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biPlanes = ByteUtil.toShort(Arrays.copyOfRange(bytes, position, position + Short.BYTES));
      position += Short.BYTES;
      biBitCount = ByteUtil.toShort(Arrays.copyOfRange(bytes, position, position + Short.BYTES));
      position += Short.BYTES;
      biCompression = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biSizeImage = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biXPelsPerMeter = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biYPelsPerMeter = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biClrUsed = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      biClrImportant = ByteUtil.toInt(Arrays.copyOfRange(bytes, position, position + Integer.BYTES));
      position += Integer.BYTES;
      
      return true;
    }
  }
}
