exports.handler = (event, context, callback) => {
    var AWS = require("aws-sdk");
       AWS.config.update({
      region: "us-west-2"
    });
    var docClient = new AWS.DynamoDB.DocumentClient();
    
    
    const {studentId, studentName, courseId} = event;
   
    var table = "Student";
    

    const params = {
        TableName:table,
        Key:{
            studentId
        },
        UpdateExpression: "set tuition = tuition + :t",
        ExpressionAttributeValues:{
            ":t": 1500
        },
        ReturnValues:"UPDATED_NEW"
    };
    docClient.update(params, (err, data) => {
        if (err) {
            console.error("Unable to update item. Error JSON:", JSON.stringify(err, null, 2));
            
        } else {
            callback(null, event);
        }
        
    });


};