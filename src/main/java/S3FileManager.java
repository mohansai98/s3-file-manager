
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Paths;

public class S3FileManager {

    private final String bucketName;
    private final S3Client s3Client;

    public S3FileManager(String bucketName) {
        this.bucketName = bucketName;
        this.s3Client = S3Client.builder().region(Region.US_EAST_2).build();
    }

    public void uploadFile(String fileName, String filePath) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.putObject(request, RequestBody.fromFile(Paths.get(filePath)));
        System.out.println("File uploaded successfully");
    }

    public void downloadFile(String fileName, String destinationPath) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.getObject(request, Paths.get(destinationPath));
        System.out.println("File downloaded successfully");
    }

    public void listFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        response.contents().forEach(content -> System.out.println(content.key() + " (Size: " + content.size() + " bytes)"));
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest delete = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(delete);
        System.out.println("File deleted successfully");
    }

    public static void main(String[] args) {
        String bucketName = System.getenv("BUCKET_NAME");
        S3FileManager s3 = new S3FileManager(bucketName);
        System.out.println(bucketName);
        s3.uploadFile("sample.txt", "/Users/mohansai/Documents/aws/java/sample.txt");
        s3.listFiles();
        s3.downloadFile("sample.txt", "/Users/mohansai/Documents/aws/java/output_sample.txt");
        s3.deleteFile("sample.txt");
    }
}
