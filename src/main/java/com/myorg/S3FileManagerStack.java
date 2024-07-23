package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnOutputProps;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.*;

public class S3FileManagerStack extends Stack {
    public S3FileManagerStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public S3FileManagerStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here

        Bucket bucket = Bucket.Builder.create(this, "S3FileManagerBucket")
                .bucketName("s3-file-manager-bucket")
                .versioned(false)
                .encryption(BucketEncryption.S3_MANAGED)
                .enforceSsl(true)
                .build();

        new CfnOutput(this, "BucketName", CfnOutputProps.builder()
                .description("The name of the S3 bucket")
                .value(bucket.getBucketName())
                .build());


    }
}
