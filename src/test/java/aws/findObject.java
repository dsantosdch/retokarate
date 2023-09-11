package aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class findObject {
    public static String findTXTFile(String bucketName, String fileName) {
        AmazonS3 s3Client = awsConn.getS3Client();

        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, fileName));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(objectInputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }
}
