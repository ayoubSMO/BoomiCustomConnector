package com.boomi.beebay.s3BucketConnector;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.boomi.beebay.s3BucketConnector.utils.Util;
import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.ConnectorException;
import com.boomi.connector.api.ContentType;
import com.boomi.connector.api.ObjectDefinition;
import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectDefinitions;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.util.BaseBrowser;
import java.util.Collection;
import java.util.List;

public class S3BucketBrowser extends BaseBrowser {


    protected S3BucketBrowser(BrowseContext context) {
        super(context);
    }


  public ObjectTypes getObjectTypes() {


    AWSCredentials credentials = new BasicAWSCredentials(
      getContext().getConnectionProperties().getProperty(Util.AWS_ACCESS_KEY_ID),
      getContext().getConnectionProperties().getProperty(Util.AWS_SECRET_ACCESS_KEY)
    );

    AmazonS3 s3client = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withRegion(Regions.CA_CENTRAL_1)
      .build();

      List<Bucket> buckets = s3client.listBuckets();
      ObjectTypes types = new ObjectTypes();

      for (Bucket b : buckets) {
        String key = b.getName();
        ObjectType type = new ObjectType();
        type.setId(key);
        type.setLabel(key);
        types.getTypes().add(type);
      }
      return types;
}

  public ObjectDefinitions getObjectDefinitions(String objectTypeId,
                                                Collection<ObjectDefinitionRole> roles) {
    ObjectDefinitions definitions = new ObjectDefinitions();

    definitions.getDefinitions().add(
      new ObjectDefinition()
        .withInputType(ContentType.NONE)
        .withOutputType(ContentType.BINARY));
    return definitions;
  }
}
