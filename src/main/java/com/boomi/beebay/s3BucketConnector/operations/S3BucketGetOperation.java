package com.boomi.beebay.s3BucketConnector.operations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.boomi.beebay.s3BucketConnector.S3BucketConnection;
import com.boomi.beebay.s3BucketConnector.utils.Util;
import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseGetOperation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class S3BucketGetOperation extends BaseGetOperation {

  public S3BucketGetOperation(S3BucketConnection connection) {
    super(connection);
  }

  @Override
  public S3BucketConnection getConnection() {
    return (S3BucketConnection) super.getConnection();
  }

  // get operation
  protected void executeGet(GetRequest request, OperationResponse operationResponse) {
    // get the object ID from the parameteres
    ObjectIdData requestData = request.getObjectId();
    PropertyMap props = getContext().getOperationProperties();

    // define the logger class to set log informations
    Logger logger = operationResponse.getLogger();

    // Setting AWS S3 credentials for connection
    AWSCredentials credentials = new BasicAWSCredentials(
      getConnection().getS3BucketAccessKeyId(),
      getConnection().getS3BucketSecretAccessKey()
    );

    logger.log(Level.FINE, "SecretAccessKey: " + getConnection().getS3BucketSecretAccessKey());
    logger.log(Level.FINE, "Credentials are set successfully");
    AmazonS3 s3client = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.CA_CENTRAL_1)
      .build();
    logger.log(Level.FINE, "Connection is maded successfully");
    logger.log(Level.FINE, "The bucket name retrieved from get operation is ; " + props.getProperty(Util.BUCKET_NAME) + Util.BUCKET_NAME);
    logger.log(Level.FINE, "The object key retrieved from get operation is ; " + props.getProperty(Util.FOLDER_PATH) + Util.FOLDER_PATH);
    logger.log(Level.FINE, getConnection().getS3BucketObjectKey());
    //GetObjectRequest getObjectRequest = new GetObjectRequest(props.getProperty(Util.BUCKET_NAME), props.getProperty(Util.FOLDER_PATH));
    logger.log(Level.FINE, "Connection is maded successfully 22");
    try {

      // Retrieve data from s3 bucket

      ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(props.getProperty(Util.BUCKET_NAME));
      List<S3ObjectSummary> objectSummaries = s3client.listObjects(listObjectsRequest).getObjectSummaries();


      for (S3ObjectSummary objectSummary : objectSummaries) {
        String objectKey = objectSummary.getKey();
        S3Object s3Object = s3client.getObject(props.getProperty(Util.BUCKET_NAME), objectKey);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
          while ((len = objectInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
          }
        ResponseUtil.addSuccess(operationResponse, requestData, String.valueOf(200),
            ResponseUtil.toPayload(byteArrayOutputStream));


          // At this point, the object content is in the byteArrayOutputStream.
          // You can access it as a byte array using byteArrayOutputStream.toByteArray().
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE,e.getMessage());
      ResponseUtil.addExceptionFailure(operationResponse,request.getObjectId(),e);
    }
  }
  }
