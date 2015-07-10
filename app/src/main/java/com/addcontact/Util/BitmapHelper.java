package com.addcontact.Util;

/**
 * Created by Manish on 23/04/2015.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.http.AndroidHttpClient;
import android.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Methods for manupulating Bitmaps.
 */
public class BitmapHelper {

    public static final int BUFFER_SIZE = 8192;       // 8KB
    private static final int DEFAULT_COMPRESS_QUALITY = 90;
    private static final int INDEX_ORIENTATION = 0;

    /**
     * Converts an image into its Base64 representation for wire transfer.
     *
     * @param filePath
     * @return
     */
    public static byte[] convertImageToByteArray(String filePath) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] result = null;

        try {
            inputStream = new FileInputStream(filePath);
            outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            result = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * Compresses a Bitmap.
     *
     * @param bitmap
     * @param compressionLevel
     * @return A byte array.
     */
    public static byte[] convertImageToByteArray(Bitmap bitmap, int compressionLevel) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionLevel, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Converts a bitmap into a byte array
     *
     * @param bitmap
     * @return
     */
    public static byte[] convertImageToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, outputStream);
        return outputStream.toByteArray();

    }

    /**
     * Converts a byte array into a bitmap.
     *
     * @param data
     * @return
     */
    public static Bitmap convertByteArrayToImage(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * Converts a Base64-encoded string to a Bitmap.
     *
     * @param data
     * @return
     */
    public static Bitmap convertBase64StringToImage(String data) {
        byte[] result = Base64.decode(data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(result, 0, result.length);
    }

    /**
     * Converts a bitmap into its Base64-string representation.
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String convertImageToBase64String(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] result = outputStream.toByteArray();
        return Base64.encodeToString(result, Base64.DEFAULT);
    }

    /**
     * Downloads an image from the specified URL.
     *
     * @param url
     * @return
     */
    public static Bitmap downloadImage(String url) {

        AndroidHttpClient client = AndroidHttpClient.newInstance("GTicket");
        HttpGet request = new HttpGet(url);

        try {

            HttpResponse response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                //logger.error("Error " + statusCode + " while retrieving image from " + url);
                return null;
            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream inputStream = null;

                try {
                    inputStream = entity.getContent();
                    return BitmapFactory.decodeStream(inputStream);
                } finally {
                    if (inputStream != null)
                        inputStream.close();

                    entity.consumeContent();
                }
            }

        } catch (IOException e) {
            //logger.error("Error while retrieving image from " + url + " Details: " + e.toString());
            request.abort();
        } finally {
            if (client != null)
                client.close();
        }

        return null;
    }

    /**
     * Gets a bitmap from the specified path
     *
     * @param filePath
     * @param sampleSize sampleSize 1 = 100%, 2 = 50%(1/2), 4 = 25%(1/4), etc.
     * @return
     */
    public static Bitmap getBitmapFromLocalPath(String filePath, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        return BitmapFactory.decodeFile(filePath);
    }

    /**
     * Decode and sample down a bitmap from a file to the requested width and height.
     *
     * @param filename     The full path of the file to decode
     * @param targetWidth  The requested width of the resulting bitmap
     * @param targetHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio
     * and dimensions that are equal to or greater than the requested width and height
     */
    public static Bitmap scaleImage(String filename, int targetWidth, int targetHeight) {

        // Avoid an OutOfMemory exception
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        // tell the decoder to subsample the image
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    /**
     * Scales an image to fit the specified dimensions.
     * It also rotates the image by looking up the orientation in EXIF.
     * <p/>
     * Managing multiple full-sized images can be tricky with limited memory as it throws
     * OutOfMemory Exception for larger images. If you find your application running out
     * of memory after displaying just a few images, you can dramatically reduce the amount
     * of dynamic heap used by expanding the JPEG into a memory array that's already scaled
     * to match the size of the destination view.
     */
    public static Bitmap scaleAndRotateImage(String path, int targetWidth, int targetHeight) {

        int imageWidth, imageHeight;
        int imageOrientation = 0;

        // Get EXIF information
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            imageOrientation = exifToDegrees(orientation);
        } catch (Exception ex) {
            //logger.error("Error getting EXIF information from the photo", ex);
        }

        // Check the dimensions of the image to avoid OutOfMemory exception
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Raw height and width of image
        if (imageOrientation == 90 || imageOrientation == 270) {
            imageWidth = options.outHeight;
            imageHeight = options.outWidth;
        } else {
            imageWidth = options.outWidth;
            imageHeight = options.outHeight;
        }

        // Find out which way needs to be reduced
        int scaleFactor = 2;

        if (imageWidth > targetWidth || imageHeight > targetHeight) {
            final int widthRatio = Math.round((float) imageWidth / (float) targetWidth);
            final int heightRatio = Math.round((float) imageHeight / (float) targetHeight);

            scaleFactor = Math.max(heightRatio, widthRatio);
        }

        /*if (imageHeight > targetHeight || imageWidth > targetWidth) {
            scaleFactor = Math.min(imageWidth / targetWidth, imageHeight / targetHeight);
        }*/

        // Set Bitmap options to scale the image decode target
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        // Decode the JPEG file into a Bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        // Rotate the bitmap if required
        if (imageOrientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(imageOrientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        // Re-scale the bitmap if it was rotated
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();

        if (imageWidth != targetWidth || imageHeight != targetHeight) {
            float widthRatio = (float) imageWidth / (float) targetWidth;
            float heightRatio = (float) imageHeight / (float) targetHeight;
            float maxRatio = Math.max(widthRatio, heightRatio);
            imageWidth = (int) ((float) imageWidth / maxRatio);
            imageHeight = (int) ((float) imageHeight / maxRatio);

            bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, true);
        }

        return bitmap;
    }

    /**
     * Decode and sample down a bitmap from a file input stream to the requested width and height.
     *
     * @param fileDescriptor The file descriptor to read from
     * @param targetWidth    The requested width of the resulting bitmap
     * @param targetHeight   The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     * that are equal to or greater than the requested width and height
     */
    public static Bitmap scaleImage(FileDescriptor fileDescriptor, int targetWidth, int targetHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * Decode and sample down a bitmap from resources to the requested width and height.
     *
     * @param resources    The resources object containing the image data
     * @param resourceId   The resource id of the image data
     * @param targetWidth  The requested width of the resulting bitmap
     * @param targetHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     * that are equal to or greater than the requested width and height
     */
    public static Bitmap scaleImage(Resources resources, int resourceId, int targetWidth, int targetHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourceId, options);

        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resourceId, options);
    }

    /**
     * Decodes, resizes and compress an image to a smaller-size image and saves it to the destination file.
     *
     * @param sourceFilePath
     * @param destinationFilePath
     * @param targetWidth
     * @param targetHeight
     * @param compressionLevel
     * @return
     * @throws IOException
     */
    public static File scaleAndSaveImage(String sourceFilePath, String destinationFilePath, int targetWidth, int targetHeight, int compressionLevel) throws IOException {
        int imageWidth, imageHeight;
        int imageOrientation = 0;

        // Get EXIF information
        try {
            ExifInterface exif = new ExifInterface(sourceFilePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            imageOrientation = exifToDegrees(orientation);
        } catch (Exception ex) {
            //logger.error("Error getting EXIF information from the photo", ex);
        }

        // Check the dimensions of the image to avoid OutOfMemory exception
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(sourceFilePath, options);

        // Raw height and width of image
        if (imageOrientation == 90 || imageOrientation == 270) {
            imageWidth = options.outHeight;
            imageHeight = options.outWidth;
        } else {
            imageWidth = options.outWidth;
            imageHeight = options.outHeight;
        }

        // Find out which way needs to be reduced
        int scaleFactor = 2;

        if (imageWidth > targetWidth || imageHeight > targetHeight) {
            final int widthRatio = Math.round((float) imageWidth / (float) targetWidth);
            final int heightRatio = Math.round((float) imageHeight / (float) targetHeight);

            scaleFactor = Math.max(heightRatio, widthRatio);
        }

        // Set Bitmap options to scale the image decode target
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        // Decode the JPEG file into a Bitmap using the scale factor
        Bitmap bitmap = BitmapFactory.decodeFile(sourceFilePath, options);

        // Rotate the bitmap if required
        if (imageOrientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(imageOrientation);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        // Re-scale the bitmap if it was rotated
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();

        if (imageWidth != targetWidth || imageHeight != targetHeight) {
            float widthRatio = (float) imageWidth / (float) targetWidth;
            float heightRatio = (float) imageHeight / (float) targetHeight;
            float maxRatio = Math.max(widthRatio, heightRatio);
            imageWidth = (int) ((float) imageWidth / maxRatio);
            imageHeight = (int) ((float) imageHeight / maxRatio);

            bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, true);
        }

        // Save the new bitmap to the destination file
        File destinationFile = new File(destinationFilePath);
        FileOutputStream outputStream = new FileOutputStream(destinationFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionLevel, outputStream);

        outputStream.flush();
        outputStream.close();
        outputStream = null;

        return destinationFile;
    }

    /**
     * Saves the bitmap to the specified file without compression.
     *
     * @param bitmap
     * @param file
     * @return
     */
    public static String saveImage(Bitmap bitmap, File file) throws IOException {
        return saveImage(bitmap, file, 100);
    }

    public static String saveImage(Bitmap bitmap, File file, int compressionLevel) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionLevel, outputStream);

        outputStream.flush();
        outputStream.close();
        outputStream = null;

        return file.getAbsolutePath();
    }

    /**
     * Saves the bitmap to the specified file with compression.
     *
     * @param bitmap
     * @param filePath
     * @param compressionLevel
     * @return
     */
    public static File saveImage(Bitmap bitmap, String filePath, int compressionLevel) throws IOException {
        File imageFile = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionLevel, outputStream);

        outputStream.flush();
        outputStream.close();
        outputStream = null;

        return imageFile;
    }

    /**
     * Saves the bitmap by the specified location.
     *
     * @param bitmap
     * @param dirPath
     * @param fileName
     * @return
     */
    public static File saveImage(Bitmap bitmap, String dirPath, String fileName) throws IOException {
        return saveImage(bitmap, dirPath, fileName, DEFAULT_COMPRESS_QUALITY);
    }

    /**
     * Saves the Bitmap to the specified location with compression.
     *
     * @param bitmap
     * @param dirPath
     * @param fileName
     * @param compressionLevel
     * @return
     */
    public static File saveImage(Bitmap bitmap, String dirPath, String fileName, int compressionLevel) throws IOException {
        File imageFile = new File(dirPath, fileName);

        // Encode the file as a compressed JPEG image
        FileOutputStream outputStream = new FileOutputStream(fileName);
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionLevel, outputStream);

        outputStream.flush();
        outputStream.close();
        outputStream = null;

        return imageFile;
    }

    //<editor-fold desc="Helper methods"

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options      An options object with out* params already populated (run through a decode*
     *                     method with inJustDecodeBounds==true
     * @param targetWidth  The requested width of the resulting bitmap
     * @param targetHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int targetWidth, int targetHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > targetHeight || width > targetWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) targetHeight);
            final int widthRatio = Math.round((float) width / (float) targetWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).
            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = targetWidth * targetHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    public static int exifToDegrees(int exifOrientation) {

        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
        }

        return 0;
    }

    //</editor-fold>
}
