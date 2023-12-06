package hw2;

import java.util.Scanner; // Import the Scanner class
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import javax.swing.JFileChooser;
import java.io.File;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class CreateS3Bucket {
	private static AWSCredentials credentials = null;
	static String awsRegion;
	static String bucketName;
	static File file;

	public CreateS3Bucket() {
		try {
			credentials = new ProfileCredentialsProvider("default").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/wzhang/.aws/credentials), and is in valid format.", e);
		}

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter AWS region: ");
		awsRegion = scan.nextLine();

		System.out.println("Enter the name of an bucket: ");
		bucketName = scan.nextLine();

		JFileChooser jfc = new JFileChooser();
		jfc.showDialog(null, "Please Select the File");
		jfc.setVisible(true);
		file = jfc.getSelectedFile();

		System.out.println("AWS region: " + awsRegion);
		System.out.println("name of an bucket: " + bucketName);
		System.out.println("File name " + file.getName());
		scan.close();

	}

	public static void main(String[] args) {
		CreateS3Bucket S3bucket = new CreateS3Bucket();

		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(awsRegion).build();

		if (s3.doesBucketExistV2(bucketName)) {
			System.out.format("Bucket %s already exists.\n", bucketName);
		} else {
			try {
				CreateBucketRequest request = new CreateBucketRequest(bucketName);
				Bucket b = s3.createBucket(bucketName);
			} catch (AmazonS3Exception e) {
				System.err.println(e.getErrorMessage());
			}
		}
		s3.putObject(new PutObjectRequest(bucketName,file.getName(), file));
        System.out.println("file uploaded sucessfully to " + bucketName);

	}
}