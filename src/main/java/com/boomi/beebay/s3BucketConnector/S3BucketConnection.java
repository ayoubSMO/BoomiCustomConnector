package com.boomi.beebay.s3BucketConnector;

import com.boomi.beebay.s3BucketConnector.utils.Util;
import com.boomi.connector.api.ConnectorContext;
import com.boomi.connector.util.BaseConnection;

public class S3BucketConnection<C extends ConnectorContext> extends BaseConnection<C> {

    public S3BucketConnection(C context) {
      super(context);
    }
  // get the access key id to connect with s3 bucket
    public String getS3BucketAccessKeyId(){

      return getContext().getConnectionProperties().getProperty(Util.AWS_ACCESS_KEY_ID);
    }
  // get th secret access key to connect with s3 bucket
    public String getS3BucketSecretAccessKey(){

      return getContext().getConnectionProperties().getProperty(Util.AWS_SECRET_ACCESS_KEY);
    }

    public String getS3BucketObjectKey(){
      return getContext().getConnectionProperties().getProperty(Util.FOLDER_PATH);
    }

    public String getS3BucketName(){
      return getContext().getConnectionProperties().getProperty(Util.BUCKET_NAME);
    }


}
