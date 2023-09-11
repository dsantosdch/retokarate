package aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.List;

public class listObject {

    public static List<String> listBucketObjects(String bucketName) {
        AmazonS3 s3Client = awsConn.getS3Client();

        List<String> objectKeys = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        for(S3ObjectSummary objectSummary: objectListing.getObjectSummaries()) {
            objectKeys.add(objectSummary.getKey());
        }
        return objectKeys;
    }

}
