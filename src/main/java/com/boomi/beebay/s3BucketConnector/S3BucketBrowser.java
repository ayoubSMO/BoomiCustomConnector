// Copyright (c) 2020 Boomi, Inc.
package com.boomi.beebay.s3BucketConnector;

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

public class S3BucketBrowser extends BaseBrowser {


    protected S3BucketBrowser(BrowseContext context) {
        super(context);
    }

    public ObjectTypes getObjectTypes() {
    ObjectTypes types = new ObjectTypes();
    ObjectType type = new ObjectType();
    type.setId(Util.BLOB);
    type.setLabel(Util.BLOB);
    types.getTypes().add(type);
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
