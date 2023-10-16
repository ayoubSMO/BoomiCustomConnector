// Copyright (c) 2020 Boomi, Inc.
package com.boomi.beebay.s3BucketConnector.operations;

import com.boomi.connector.api.ObjectData;
import com.boomi.connector.api.OperationResponse;
import com.boomi.connector.api.ResponseUtil;
import com.boomi.connector.api.UpdateRequest;
import com.boomi.connector.util.BaseUpdateOperation;
import com.boomi.beebay.s3BucketConnector.S3BucketConnection;

public class S3BucketCreateOperation extends BaseUpdateOperation {

    public S3BucketCreateOperation(S3BucketConnection connection) {
        super(connection);
    }

    @Override
    public S3BucketConnection getConnection() {
        return (S3BucketConnection) super.getConnection();
    }

    protected void executeUpdate(UpdateRequest updateRequest, OperationResponse operationResponse) {

}}
