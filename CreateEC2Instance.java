package hw2;

import java.util.Scanner; // Import the Scanner class
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;

public class CreateEC2Instance {
	private static AWSCredentials credentials = null;
	static String awsRegion;
	static String securityGroup;
	static String instanceType;
	static String image_ID;
	static String key_Pair;

	public CreateEC2Instance() {
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

		System.out.println("Enter the name of a security group: ");
		securityGroup = scan.nextLine();

		System.out.println("Enter the name of an instance type: ");
		instanceType = scan.nextLine();

		System.out.println("Enter image_ID: ");
		image_ID = scan.nextLine();

		System.out.println("Enter key_Pair: ");
		key_Pair = scan.nextLine();

		System.out.println("AWS region: " + awsRegion);
		System.out.println("Security group: " + securityGroup);
		System.out.println("Instance type: " + instanceType);
		System.out.println("Image ID: " + image_ID);
		System.out.println("Key pair: " + key_Pair);
		scan.close();

	}

	public static void main(String[] args) {
		CreateEC2Instance i = new CreateEC2Instance();
		AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(awsRegion).build();
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest().withImageId(image_ID)
				.withInstanceType(instanceType).withMinCount(1).withMaxCount(1).withKeyName(key_Pair)
				.withSecurityGroups(securityGroup);

		RunInstancesResult result = ec2.runInstances(runInstancesRequest);
	}
}
