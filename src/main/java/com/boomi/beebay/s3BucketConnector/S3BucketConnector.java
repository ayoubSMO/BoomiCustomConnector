// Copyright (c) 2020 Boomi, Inc.
package com.boomi.beebay.s3BucketConnector;

import com.boomi.beebay.s3BucketConnector.operations.S3BucketCreateOperation;
import com.boomi.beebay.s3BucketConnector.operations.S3BucketGetOperation;
import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.api.Operation;
import com.boomi.connector.api.OperationContext;
import com.boomi.connector.util.BaseConnector;

public class S3BucketConnector extends BaseConnector {

    public Browser createBrowser(BrowseContext context) {
        return new S3BucketBrowser(context);
    }

    @Override
    public Operation createGetOperation(OperationContext context) {
        return new S3BucketGetOperation(new S3BucketConnection(context));
    }
}

