exports.handler = (event, context, callback) => {
    var AWS = require("aws-sdk");
       AWS.config.update({
      region: "us-west-2"
    });
    var docClient = new AWS.DynamoDB.DocumentClient();
    
    
    const {studentId, studentName, courseId} = event;
   
    var table = "Course";
    
    
    var getParams = {
        TableName:table,
        Key:{
            "id": courseId
        }
    };
    
    
    docClient.get(getParams, (err, data) => {
        if (err) {
            console.error("Unable to get item. Error JSON:", JSON.stringify(err, null, 2));
        }else {
            const students = JSON.parse(data.Item.roster.students);
            students[studentId] = studentName;
            
            const params = {
                TableName:table,
                Key:{
                    "id": courseId
                },
                UpdateExpression: "set roster.students = :s",
                ExpressionAttributeValues:{
                    ":s": JSON.stringify(students)
                },
                ReturnValues:"UPDATED_NEW"
            };
            docClient.update(params, (err, data) => {
                if (err) {
                    console.error("Unable to update item. Error JSON:", JSON.stringify(err, null, 2));
                } 
                callback(null, event);
            });
        }
        
    });

};