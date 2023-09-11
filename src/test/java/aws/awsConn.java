package aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.codec.binary.Base64;
import java.util.HashMap;
import java.util.Map;

public class awsConn {

    private static AWSCredentials getSessionCredentials() {
        return new BasicAWSCredentials("AKIAXA5GPF4BPUAM5CP3", "2r79FASz+uVBPO6FfvVckr80NWVy4HHcUFSWBUpe");
    }


    public static AmazonS3 getS3Client() {
        return AmazonS3Client.builder()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                .build();
    }

    public static Map<String, Object> invokeLambda(String functionName, String region, String payload) {
        InvokeRequest lmbRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload(payload)
                .withLogType("Tail");
        lmbRequest.setInvocationType(InvocationType.RequestResponse);
        AWSLambda lambda = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                .build();

        InvokeResult lmbResult = lambda.invoke(lmbRequest);
        String decodedLogString = new String(Base64.decodeBase64(lmbResult.getLogResult().getBytes()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("statusLambda", lmbResult.getStatusCode());
        result.put("log", decodedLogString);
        return result;
    }

}
