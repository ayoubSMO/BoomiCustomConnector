package com.boomi.beebay.s3BucketConnector.operations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.boomi.beebay.s3BucketConnector.S3BucketConnection;
import com.boomi.beebay.s3BucketConnector.utils.Util;
import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseGetOperation;

import java.io.ByteArrayOutputStream;
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
    GetObjectRequest getObjectRequest = new GetObjectRequest(props.getProperty(Util.BUCKET_NAME), props.getProperty(Util.FOLDER_PATH));
    logger.log(Level.FINE, "Connection is maded successfully 22");
    try {
      S3Object s3Object = s3client.getObject(getObjectRequest);
      logger.log(Level.FINE, "Connection is maded successfully 33");
      S3ObjectInputStream objectContent = s3Object.getObjectContent();
      logger.log(Level.FINE, "Connection is maded successfully 44");

      // Download the object's content to a ByteArrayOutputStream

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[4096];
      int bytesRead;
      logger.log(Level.FINE, "Connection is maded successfully 55");
      while ((bytesRead = objectContent.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      logger.log(Level.FINE, "Connection is maded successfully 66");
      ResponseUtil.addSuccess(operationResponse, requestData, String.valueOf(200),
        ResponseUtil.toPayload(outputStream));
      logger.log(Level.FINE, "Connection is maded successfully 77");
      logger.log(Level.FINE, "Object is retrieved succefully");
    } catch (Exception e) {
      logger.log(Level.SEVERE,e.getMessage());
      ResponseUtil.addExceptionFailure(operationResponse,request.getObjectId(),e);
    }
  }
}

