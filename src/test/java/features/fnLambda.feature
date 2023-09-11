Feature: content from a bucket s3

  Background:
    * string req = '{"ccd": "123456"}'
    * def AWSConn = Java.type('aws.awsConn')
    * def functionListObject = Java.type('aws.listObject')
    * def functionFindObject = Java.type('aws.findObject')
    * def bucketName = 'retokarate'
    * def fileName = 'ejemplo.txt'
    #* def funcionAWS = Java.type('awsConn')
    #* def bucketUrl = Java.type('https://s3.console.aws.amazon.com/s3/buckets/retokarate')
    #* def s3 = Java.type('aws.')

    #* def region = aws.region
    #* def lambName = aws.lambdaFunctionName
    #* def urlbase = 'https://tk7v98e2t8.execute-api.us-east-1.amazonaws.com/v0/'


  @ListObjectS3
  Scenario: leer contenido del bucket s3
    Given def res = functionListObject.listBucketObjects(bucketName)
    When print res
    Then match res != null && res.size() > 0


    #And def S3Contents = response
    #* print S3Contents

  @FindObjectS3
  Scenario: verificar si un archivo existe en el bucket s3
    Given def findObject = functionFindObject.findTXTFile(bucketName, fileName)
    When print findObject
    Then match findObject != null

  @ConvertTXTtoJson
  Scenario: transformar txt a json
    Given def txtFile = functionFindObject.findTXTFile(bucketName, fileName)
    And  match txtFile != null
    When json jsonFile = txtFile
    Then print txtFile
    * print jsonFile