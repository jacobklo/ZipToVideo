package net.jacoblo.BMP;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class BMPFile {
  private final static int BITMAP_FILE_HEADER_SIZE = 14;
  private final static int BITMAP_INFO_HEADER_SIZE = 40;
  
  private TagBitmapFileHeader m_FileHeader;
  private TagBitmapInfoHeader m_InfoHeader;
  private Bitmap m_Bitmap;
  
  public boolean isEmpty() {
    return m_Bitmap == null || m_Bitmap.m_Height <= 0 || m_Bitmap.m_Width <= 0 || m_Bitmap.m_Pixels == null || m_FileHeader == null || m_FileHeader.bfSize <= 0 || m_InfoHeader == null ;
  }
  
  public byte[] toByteArray() {
    if (isEmpty()) {
      return new byte[0];
    }
    
    // if false, header calculation has went wrong, abort
    if (! (m_FileHeader.calculateImageSize(m_Bitmap.m_Width, m_Bitmap.m_Height) && 
    m_InfoHeader.calculateImageSize(m_Bitmap.m_Width, m_Bitmap.m_Height) ) )  {
      return new byte[0];
    }
    
    byte[] result = new byte[m_FileHeader.bfSize];
    int position = 0;
    System.arraycopy(m_FileHeader.toByteArray(), 0, result, position, BITMAP_FILE_HEADER_SIZE);
    position += BITMAP_FILE_HEADER_SIZE;
    System.arraycopy(m_InfoHeader.toByteArray(), 0, result, position, BITMAP_INFO_HEADER_SIZE);
    position += BITMAP_INFO_HEADER_SIZE;
    System.arraycopy(m_Bitmap.toByteArray(), 0, result, position, m_Bitmap.getTotalByteSize(m_Bitmap.m_Width, m_Bitmap.m_Height));
    
    return result;
  }
  
  public boolean toObject() {
    // TODO
    return false;
  }
  
  public boolean saveBitmap(String pathToSave) {
    if (pathToSave == null || isEmpty())  return false;
    
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(pathToSave);
      fos.write(toByteArray());
      fos.flush();
    } catch ( IOException e) {
      return false;
    }
    if (fos != null) {
      try {
        fos.close();
      } catch (IOException e) {}
    }
    return true;
  }
  
  
  public static class TagBitmapFileHeader {
    public byte[] bfType = {'B', 'M'};
    public int bfSize = 0;
    public short bfReserved1 = 0;
    public short bfReserved2 = 0;
    public int bfOffBits = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;
    
    public boolean calculateImageSize( int pxWidth, int pxHeight ) {
      if (pxWidth < 0 || pxHeight < 0) return false;
      
      int pad = (4 - ((pxWidth * 3) % 4)) * pxHeight;
      int biSizeImage = ((pxWidth * pxHeight) * 3) + pad;
      bfSize = biSizeImage + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;
      return true;
    }
    
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
    
    public boolean calculateImageSize( int pxWidth, int pxHeight ) {
      if (pxWidth < 0 || pxHeight < 0) return false;
      
      int pad = (4 - ((pxWidth * 3) % 4)) * pxHeight;
      biSizeImage = ((pxWidth * pxHeight) * 3) + pad;
      biWidth = pxWidth;
      biHeight = pxHeight;
      return true;
    }
    
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
  
  public static class Bitmap {
    // m_Pixels stored image from Pixel Grabber
    public int[] m_Pixels;
    public int m_Width;
    public int m_Height;
    
    public Bitmap(Image image, int pxWidth, int pxHeight) {
      convertFromImage(image , pxWidth , pxHeight );
    }
    
    public byte[] toByteArray() {
      return convertPixelsToBitmapFormat();
    }
    
    // 1. bitmap format for lines is inverted, from end to fonrt line
    // 2. each scan line must be padded to an even 4-bytes boundary
    public byte[] convertPixelsToBitmapFormat() {
      if (m_Pixels == null || m_Width <= 0 || m_Height <= 0)  return new byte[0];
      
      byte[] result = new byte[getTotalByteSize(m_Width, m_Height)];
      
      for (int i = m_Height - 1, k = 0; i >= 0 ; i--, k++ ) {
        for (int j = 0 ; j < m_Width ; j++ ) {
          
          int currentPixelpointer = i * m_Width + j;
          int currentPixelValue = m_Pixels[currentPixelpointer];
          
          int byteLocationPointer = k * m_Width * 3 + (j * 3 );
          result[byteLocationPointer]   = (byte) (currentPixelValue & 0xFF);
          result[byteLocationPointer+1] = (byte) ( ( currentPixelValue >> 8 ) & 0xFF);
          result[byteLocationPointer+2] = (byte) ( ( currentPixelValue >> 16 ) & 0xFF);
          
        }
      }
      
      return result;
    }
    
    // each scan line must be padded to an even 4-bytes boundary
    public int getTotalByteSize(int width, int height ) {
      if (width <= 0 || height <= 0 ) return 0;
      
      int eachLineByteSize = (m_Width * 3) / 4 * 4  + ( ( 4 - ( (m_Width * 3) ) % 4 ) == 4 ? 0 : 4 );
      int totalByteSize = eachLineByteSize * m_Height;
      return totalByteSize;
    }
    
    public boolean toObject(byte[] bytes) {
      throw new IllegalArgumentException("You do not need this Bitmap helper class, if you already have bitmap formatted pixel bytes");
    }
    
    // The more suitable one is input a Image object, and convert it to bitmap formatted pixel bytes
    public boolean toObject(Image image, int pxWidth, int pxHeight) {
      return convertFromImage(image, pxWidth, pxHeight);
    }
    public boolean convertFromImage(Image image, int pxWidth, int pxHeight ) {
      if (image == null || pxWidth <= 0 || pxHeight <= 0 ) return false;
      
      int[] bitmap = new int[pxWidth * pxHeight];
      PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, pxWidth,pxHeight, bitmap, 0, pxWidth);
      
      try {
        pixelgrabber.grabPixels();
      } catch (InterruptedException e) {
        return false;
      }
      
      m_Pixels = bitmap;
      m_Width = pxWidth;
      m_Height = pxHeight;
      return true;
    }
    
  }
}
