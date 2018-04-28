exports.handler = (event, context, callback) = {
    var AWS = require(aws-sdk);
       AWS.config.update({
      region us-west-2
    });
    var docClient = new AWS.DynamoDB.DocumentClient();
    
    let registered = false;
    const {studentId, studentName, courseId} = event;
   if (studentId && courseId) {
       registered = true;
   }
    callback(null, {registered, ...event});
  

};