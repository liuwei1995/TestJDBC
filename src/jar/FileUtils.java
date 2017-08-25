package jar;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtils
{
  public static final long ONE_KB = 1024L;
  public static final long ONE_MB = 1048576L;
  public static final long ONE_GB = 1073741824L;
  public static final long ONE_TB = 1099511627776L;
  public static final long ONE_PB = 1125899906842624L;
  public static final long ONE_EB = 1152921504606846976L;
  public static final BigInteger ONE_ZB = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(1152921504606846976L));

  public static final BigInteger ONE_YB = ONE_ZB.multiply(BigInteger.valueOf(1152921504606846976L));

  public static final File[] EMPTY_FILE_ARRAY = new File[0];

  private static final Charset UTF8 = Charset.forName("UTF-8");

  public static File getFile(File directory, String[] names)
  {
    if (directory == null) {
      throw new NullPointerException("directorydirectory must not be null");
    }
    if (names == null) {
      throw new NullPointerException("names must not be null");
    }
    File file = directory;
    for (String name : names) {
      file = new File(file, name);
    }
    return file;
  }

  public static File getFile(String[] names)
  {
    if (names == null) {
      throw new NullPointerException("names must not be null");
    }
    File file = null;
    for (String name : names) {
      if (file == null)
        file = new File(name);
      else {
        file = new File(file, name);
      }
    }
    return file;
  }

  public static String getTempDirectoryPath()
  {
    return System.getProperty("java.io.tmpdir");
  }

  public static File getTempDirectory()
  {
    return new File(getTempDirectoryPath());
  }

  public static String getUserDirectoryPath()
  {
    return System.getProperty("user.home");
  }

  public static File getUserDirectory()
  {
    return new File(getUserDirectoryPath());
  }

  public static FileInputStream openInputStream(File file)
    throws IOException
  {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new IOException("File '" + file + "' exists but is a directory");
      }
      if (!file.canRead())
        throw new IOException("File '" + file + "' cannot be read");
    }
    else {
      throw new FileNotFoundException("File '" + file + "' does not exist");
    }
    return new FileInputStream(file);
  }

  public static FileOutputStream openOutputStream(File file)
    throws IOException
  {
    return openOutputStream(file, false);
  }

  public static FileOutputStream openOutputStream(File file, boolean append)
    throws IOException
  {
    if (file.exists()) {
      if (file.isDirectory()) {
        throw new IOException("File '" + file + "' exists but is a directory");
      }
      if (!file.canWrite())
        throw new IOException("File '" + file + "' cannot be written to");
    }
    else {
      File parent = file.getParentFile();
      if ((parent != null) && 
        (!parent.mkdirs()) && (!parent.isDirectory())) {
        throw new IOException("Directory '" + parent + "' could not be created");
      }
    }

    return new FileOutputStream(file, append);
  }


  public static File toFile(URL url)
  {
    if ((url == null) || (!"file".equalsIgnoreCase(url.getProtocol()))) {
      return null;
    }
    String filename = url.getFile().replace('/', File.separatorChar);
    filename = decodeUrl(filename);
    return new File(filename);
  }

  static String decodeUrl(String url)
  {
    String decoded = url;
    if ((url != null) && (url.indexOf('%') >= 0)) {
      int n = url.length();
      StringBuffer buffer = new StringBuffer();
      ByteBuffer bytes = ByteBuffer.allocate(n);
      for (int i = 0; i < n; ) {
        if (url.charAt(i) == '%') {
          try {
            do {
              byte octet = (byte)Integer.parseInt(url.substring(i + 1, i + 3), 16);
              bytes.put(octet);
              i += 3;
            }while ((i < n) && (url.charAt(i) == '%'));

            if (bytes.position() > 0) {
              bytes.flip();
              buffer.append(UTF8.decode(bytes).toString());
              bytes.clear(); continue;
            }
          }
          catch (RuntimeException e)
          {
          }
          finally
          {
            if (bytes.position() > 0) {
              bytes.flip();
              buffer.append(UTF8.decode(bytes).toString());
              bytes.clear();
            }
          }
        }
        buffer.append(url.charAt(i++));
      }
      decoded = buffer.toString();
    }
    return decoded;
  }

  public static File[] toFiles(URL[] urls)
  {
    if ((urls == null) || (urls.length == 0)) {
      return EMPTY_FILE_ARRAY;
    }
    File[] files = new File[urls.length];
    for (int i = 0; i < urls.length; i++) {
      URL url = urls[i];
      if (url != null) {
        if (!url.getProtocol().equals("file")) {
          throw new IllegalArgumentException("URL could not be converted to a File: " + url);
        }

        files[i] = toFile(url);
      }
    }
    return files;
  }

  public static URL[] toURLs(File[] files)
    throws IOException
  {
    URL[] urls = new URL[files.length];

    for (int i = 0; i < urls.length; i++) {
      urls[i] = files[i].toURI().toURL();
    }

    return urls;
  }

  public static void copyFileToDirectory(File srcFile, File destDir)
    throws IOException
  {
    copyFileToDirectory(srcFile, destDir, true);
  }

  public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate)
    throws IOException
  {
    if (destDir == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if ((destDir.exists()) && (!destDir.isDirectory())) {
      throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
    }
    File destFile = new File(destDir, srcFile.getName());
    copyFile(srcFile, destFile, preserveFileDate);
  }

  public static void copyFile(File srcFile, File destFile)
    throws IOException
  {
    copyFile(srcFile, destFile, true);
  }

  public static void copyFile(File srcFile, File destFile, boolean preserveFileDate)
    throws IOException
  {
    if (srcFile == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (destFile == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!srcFile.exists()) {
      throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
    }
    if (srcFile.isDirectory()) {
      throw new IOException("Source '" + srcFile + "' exists but is a directory");
    }
    if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
      throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
    }
    File parentFile = destFile.getParentFile();
    if ((parentFile != null) && 
      (!parentFile.mkdirs()) && (!parentFile.isDirectory())) {
      throw new IOException("Destination '" + parentFile + "' directory cannot be created");
    }

    if ((destFile.exists()) && (!destFile.canWrite())) {
      throw new IOException("Destination '" + destFile + "' exists but is read-only");
    }
    doCopyFile(srcFile, destFile, preserveFileDate);
  }


  private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate)
    throws IOException
  {
    if ((destFile.exists()) && (destFile.isDirectory())) {
      throw new IOException("Destination '" + destFile + "' exists but is a directory");
    }

    FileInputStream fis = null;
    FileOutputStream fos = null;
    FileChannel input = null;
    FileChannel output = null;
    try {
      fis = new FileInputStream(srcFile);
      fos = new FileOutputStream(destFile);
      input = fis.getChannel();
      output = fos.getChannel();
      long size = input.size();
      long pos = 0L;
      long count = 0L;
      while (pos < size) {
        count = size - pos > 31457280L ? 31457280L : size - pos;
        pos += output.transferFrom(input, pos, count);
      }
    } finally {
    	output.close();
    	fos.close();
    	input.close();
    	fis.close();
    }

    if (srcFile.length() != destFile.length()) {
      throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
    }

    if (preserveFileDate)
      destFile.setLastModified(srcFile.lastModified());
  }

  public static void copyDirectoryToDirectory(File srcDir, File destDir)
    throws IOException
  {
    if (srcDir == null) {
      throw new NullPointerException("Source must not be null");
    }
    if ((srcDir.exists()) && (!srcDir.isDirectory())) {
      throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
    }
    if (destDir == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if ((destDir.exists()) && (!destDir.isDirectory())) {
      throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
    }
    copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
  }

  public static void copyDirectory(File srcDir, File destDir)
    throws IOException
  {
    copyDirectory(srcDir, destDir, true);
  }

  public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate)
    throws IOException
  {
    copyDirectory(srcDir, destDir, null, preserveFileDate);
  }

  public static void copyDirectory(File srcDir, File destDir, FileFilter filter)
    throws IOException
  {
    copyDirectory(srcDir, destDir, filter, true);
  }

  public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate)
    throws IOException
  {
    if (srcDir == null) {
      throw new NullPointerException("Source must not be null");
    }
    if (destDir == null) {
      throw new NullPointerException("Destination must not be null");
    }
    if (!srcDir.exists()) {
      throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
    }
    if (!srcDir.isDirectory()) {
      throw new IOException("Source '" + srcDir + "' exists but is not a directory");
    }
    if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
      throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
    }

    List exclusionList = null;
    if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
      File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
      if ((srcFiles != null) && (srcFiles.length > 0)) {
        exclusionList = new ArrayList(srcFiles.length);
        for (File srcFile : srcFiles) {
          File copiedFile = new File(destDir, srcFile.getName());
          exclusionList.add(copiedFile.getCanonicalPath());
        }
      }
    }
    doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
  }

  private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate, List<String> exclusionList)
    throws IOException
  {
    File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
    if (srcFiles == null) {
      throw new IOException("Failed to list contents of " + srcDir);
    }
    if (destDir.exists()) {
      if (!destDir.isDirectory()) {
        throw new IOException("Destination '" + destDir + "' exists but is not a directory");
      }
    }
    else if ((!destDir.mkdirs()) && (!destDir.isDirectory())) {
      throw new IOException("Destination '" + destDir + "' directory cannot be created");
    }

    if (!destDir.canWrite()) {
      throw new IOException("Destination '" + destDir + "' cannot be written to");
    }
    for (File srcFile : srcFiles) {
      File dstFile = new File(destDir, srcFile.getName());
      if ((exclusionList == null) || (!exclusionList.contains(srcFile.getCanonicalPath()))) {
        if (srcFile.isDirectory())
          doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
        else {
          doCopyFile(srcFile, dstFile, preserveFileDate);
        }
      }

    }

    if (preserveFileDate)
      destDir.setLastModified(srcDir.lastModified());
  }

  public static void forceMkdir(File directory)
    throws IOException
  {
    if (directory.exists()) {
      if (!directory.isDirectory()) {
        String message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";

        throw new IOException(message);
      }
    }
    else if (!directory.mkdirs())
    {
      if (!directory.isDirectory())
      {
        String message = "Unable to create directory " + directory;

        throw new IOException(message);
      }
    }
  }

  public static long sizeOf(File file)
  {
    if (!file.exists()) {
      String message = file + " does not exist";
      throw new IllegalArgumentException(message);
    }

    if (file.isDirectory()) {
      return sizeOfDirectory(file);
    }
    return file.length();
  }

  public static long sizeOfDirectory(File directory)
  {
    if (!directory.exists()) {
      String message = directory + " does not exist";
      throw new IllegalArgumentException(message);
    }

    if (!directory.isDirectory()) {
      String message = directory + " is not a directory";
      throw new IllegalArgumentException(message);
    }

    long size = 0L;

    File[] files = directory.listFiles();
    if (files == null) {
      return 0L;
    }
    for (File file : files) {
      size += sizeOf(file);
    }

    return size;
  }

  public static boolean isFileNewer(File file, File reference)
  {
    if (reference == null) {
      throw new IllegalArgumentException("No specified reference file");
    }
    if (!reference.exists()) {
      throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
    }

    return isFileNewer(file, reference.lastModified());
  }

  public static boolean isFileNewer(File file, Date date)
  {
    if (date == null) {
      throw new IllegalArgumentException("No specified date");
    }
    return isFileNewer(file, date.getTime());
  }

  public static boolean isFileNewer(File file, long timeMillis)
  {
    if (file == null) {
      throw new IllegalArgumentException("No specified file");
    }
    if (!file.exists()) {
      return false;
    }
    return file.lastModified() > timeMillis;
  }

  public static boolean isFileOlder(File file, File reference)
  {
    if (reference == null) {
      throw new IllegalArgumentException("No specified reference file");
    }
    if (!reference.exists()) {
      throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
    }

    return isFileOlder(file, reference.lastModified());
  }

  public static boolean isFileOlder(File file, Date date)
  {
    if (date == null) {
      throw new IllegalArgumentException("No specified date");
    }
    return isFileOlder(file, date.getTime());
  }

  public static boolean isFileOlder(File file, long timeMillis)
  {
    if (file == null) {
      throw new IllegalArgumentException("No specified file");
    }
    if (!file.exists()) {
      return false;
    }
    return file.lastModified() < timeMillis;
  }


}