import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Random;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.autsh.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class Insertingdatabase {
 static AmazonDynamoDBClient client;
 private DynamoDBMapper mapper;
 private static int PRODUCT_ID;
String id="4",jobn="sleep",job="1000",status="success";
 //public static void main(String[] args) throws IOException {
 	InsertDb()
 	{
 		
 	}
 	public InsertDb(String id,String jobn,String job)throws IOException {
 	
  //InsertDb demo = new InsertDb();
  
 
  init();
  
  this.id=id;
  this.jobn=jobn;
  this.job=job;
  
  //waitForTableToBecomeAvailable("ProductCatalog");
  
  
  
  //demo.createTable("JobStatus");
  boolean result=findByID(id);
  if(!result)
  {  
  insert(id,jobn,job,status);
  }
  else
  {
  	System.out.println(" in table");
  }
  
  
  /*for (int i = 0; i < 100; i++) {
   System.out.println(i);
   PRODUCT_ID = PRODUCT_ID + i;
   demo.insert();
  }*/
  //demo.getAllRows();
  /*JobItem itemRetrieved = demo.load(1);
  demo.update(itemRetrieved);*/
  /*JobItem updatedItem = demo.load(PRODUCT_ID);
  demo.delete(updatedItem);
  demo.load(updatedItem.getId());*/
  //System.out.println("Example complete!");
 }
 private static boolean findByID(String id) {
  HashMap scanFilter = new HashMap();
  Condition hashKeyCondition = new Condition().withComparisonOperator(
  ComparisonOperator.EQ.toString()).withAttributeValueList(new AttributeValue().withN(id));
  scanFilter.put("Id",hashKeyCondition);
  ScanRequest scanRequest = new ScanRequest("JobStatus").withScanFilter(scanFilter);
  ScanResult scanResult = client.scan(scanRequest);
  //List<Map> items = scanResult.getItems();
 boolean res=false;
  for (Map item : scanResult.getItems()) {
     System.out.println(item.get("Id"));
     System.out.println(item.get("Command"));
     System.out.println(item.get("Request"));
     System.out.println(item.get("Status"));
     res=true;
     
  }
 
  	 
 return  res;
}

 private void init() {
  PRODUCT_ID = new Random().nextInt(1000);
  /*AWSCredentials credentials = new ClasspathPropertiesFileCredentialsProvider()
    .getCredentials();*/
  client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
  Region usWest2 = Region.getRegion(Regions.US_WEST_2);
  client.setRegion(usWest2);
  mapper = new DynamoDBMapper(client);
 }
 /*void populateStudent(int stdId, String stdName, String subject, int marks) {

Map item = new HashMap();
  item.put("stdIdw", AttributeValue().withN(Integer.toString(stdId)));
  item.put("stdNamew", AttributeValue(stdName));
  item.put("subjectw", AttributeValue(subject));
  item.put("marksw", AttributeValue().withN(Integer.toString(marks)));

 PutItemRequest putItemRequest = new PutItemRequest("StudentTable", item);
 PutItemResult putItemResult = client.putItem(putItemRequest);

}*/


 private void createTable(String tableName) {
  try {
  	
  	/* CreateTableRequest createTableRequest = new CreateTableRequest()
  .withTableName("StudentTable")
  .withKeySchema(new KeySchemaElement().withAttributeName("stdId").withKeyType(KeyType.HASH),
                 new KeySchemaElement().withAttributeName("stdName").withKeyType(KeyType.RANGE))
  .withAttributeDefinitions(new AttributeDefinition().withAttributeName("stdId").withAttributeType("N"),
                            new AttributeDefinition().withAttributeName("stdName").withAttributeType("S"))
  .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(10L).withWriteCapacityUnits(10L));

  CreateTableResult result =client.createTable(createTableRequest);

  waitForTableToBecomeAvailable("StudentTable");*/
    CreateTableRequest createTableRequest = new CreateTableRequest()
     .withTableName(tableName);
 // if ( createTableRequest.doesTableExist(client, tableName) == false) {
   // ... create your table ...
     createTableRequest.withKeySchema(new KeySchemaElement()
     .withAttributeName("Id").withKeyType(KeyType.HASH));
   createTableRequest
     .withAttributeDefinitions(new AttributeDefinition()
       .withAttributeName("Id").withAttributeType(
         ScalarAttributeType.N));
   createTableRequest
     .withProvisionedThroughput(new ProvisionedThroughput()
       .withReadCapacityUnits(10L).withWriteCapacityUnits(
         10L));
   TableDescription createdTableDescription = client.createTable(
     createTableRequest).getTableDescription();
   System.out.println("Created Table: " + createdTableDescription);
  

	waitForTableToBecomeAvailable(tableName);

  	
 
 
   // Wait for it to become active
   
  } catch (AmazonServiceException e) {
   e.printStackTrace();
  } catch (AmazonClientException e) {
   e.printStackTrace();
  }
 }

 private static  void waitForTableToBecomeAvailable(String tableName) {
  System.out.println("Waiting for " + tableName + " to become ACTIVE...");
  long startTime = System.currentTimeMillis();
  long endTime = startTime + (10 * 60 * 1000);
  while (System.currentTimeMillis() < endTime) {
   try {
    Thread.sleep(1000 * 20);
   } catch (Exception e) {
   }
   try {
    DescribeTableRequest request = new DescribeTableRequest()
      .withTableName(tableName);
    TableDescription tableDescription = client.describeTable(
      request).getTable();
    String tableStatus = tableDescription.getTableStatus();
    System.out.println("  - current state: " + tableStatus);
    if (tableStatus.equals(TableStatus.ACTIVE.toString()))
     return;
   } catch (AmazonServiceException ase) {
    if (ase.getErrorCode().equalsIgnoreCase(
      "ResourceNotFoundException") == false)
     throw ase;
   }
  }
  throw new RuntimeException("Table " + tableName + " never went active");
 }

 private void insert(String id, String jobn,String job,String status) {
  JobItem item = new JobItem();
  item.setId(Integer.parseInt(id));
  item.setCommand(jobn);
  item.setRequest(job);
  item.setStatus(status);
  
  // Save the item (book).
  mapper.save(item);
 }

 private void update(JobItem itemRetrieved) {
  itemRetrieved.setRequest("Success");
  itemRetrieved.setStatus("-");
  mapper.save(itemRetrieved);
  System.out.println("Item updated:");
  System.out.println(itemRetrieved);
 }

 private void delete(JobItem updatedItem) {
  // Delete the item.
  mapper.delete(updatedItem);
 }

 private JobItem load(int id) {
  // Retrieve the updated item.
  DynamoDBMapperConfig config = new DynamoDBMapperConfig(
    DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
  JobItem updatedItem = mapper.load(JobItem.class, id, config);
  if (updatedItem == null) {
   System.out.println("Done - Sample item is deleted.");
  } else {
   System.out.println("Retrieved item:");
   System.out.println(updatedItem);
  }
  return updatedItem;
 }

 private void getAllRows() {
  ScanRequest scanRequest = new ScanRequest()
    .withTableName("ProductCatalogs1");
  scanRequest.setLimit(10);
  HashMap scanFilter = new HashMap();
  Condition condition = new Condition().withComparisonOperator(
    ComparisonOperator.EQ.toString()).withAttributeValueList(
    new AttributeValue().withS("611-1111111111"));
  scanFilter.put("Request", condition);
  Condition condition2 = new Condition().withComparisonOperator(
    ComparisonOperator.LE.toString()).withAttributeValueList(
    new AttributeValue().withN("1000"));
  scanFilter.put("Id", condition2);
  scanRequest.withScanFilter(scanFilter);
  try {
   System.out.println("Scan Request: " + scanRequest);
   ScanResult scanResponse = client.scan(scanRequest);
   for (Map item : scanResponse.getItems()) {
    System.out.println("hai    "+item.get("Id") + " , " +
    item.get("Request") + " , " +
    item.get("Authors") + " , " +
    item.get("Title"));
   }
   System.out.println("Scan Response: " + scanResponse);
   System.out.println("Count: " + scanResponse.getCount());
   System.out.println("Scanned Count: "
     + scanResponse.getScannedCount());
   System.out.println("Items: " + scanResponse.getItems());
  } catch (AmazonServiceException e) {
   e.printStackTrace();
  } catch (AmazonClientException e) {
   e.printStackTrace();
  }
 }
}