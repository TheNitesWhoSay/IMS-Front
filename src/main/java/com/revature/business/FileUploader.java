package com.revature.business;

import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.logging.Log;

public class FileUploader {
	private static String bucketName     = "jjf28-bucket";
	
	public static String uploadFile(String fileName, File file) {
		AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider("jjf28"));
        try {
        	String keyName = "IMS-Storage/" + fileName;
        	//System.out.println("File: " + file);
        	//System.out.println("FileSize: " + file.getTotalSpace());
//        	byte[] contentBytes = null;
//        	try {
//				contentBytes = IOUtils.toByteArray(file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//        	Long contentLength = Long.valueOf(contentBytes.length);
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(contentLength);
            
            //s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            return "https://s3-us-west-2.amazonaws.com/jjf28-bucket/IMS-Storage/" + fileName;
         } catch (AmazonServiceException ase) {
            Log.info("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            Log.info("Error Message:    " + ase.getMessage());
            Log.info("HTTP Status Code: " + ase.getStatusCode());
            Log.info("AWS Error Code:   " + ase.getErrorCode());
            Log.info("Error Type:       " + ase.getErrorType());
            Log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            Log.info("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            Log.info("Error Message: " + ace.getMessage());
        }
        return null;
	}
	
	public static void main(String[] args) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider("jjf28"));
        try {
        	String gkeyName        = "IMS-Storage/myfile3.txt";
        	String guploadFileName = "C:\\Users\\Justin\\Desktop\\Commit Template2.txt";
        	String keyName = "IMS-Storage/" + gkeyName;
            Log.info("Uploading a new object to S3 from a file\n");
            File file = new File(guploadFileName);
            Log.info("Created FileObj");
            //request.setCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(new PutObjectRequest(
            		                 bucketName, keyName, file));
         } catch (AmazonServiceException ase) {
            Log.info("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            Log.info("Error Message:    " + ase.getMessage());
            Log.info("HTTP Status Code: " + ase.getStatusCode());
            Log.info("AWS Error Code:   " + ase.getErrorCode());
            Log.info("Error Type:       " + ase.getErrorType());
            Log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            Log.info("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            Log.info("Error Message: " + ace.getMessage());
        }
    }
}