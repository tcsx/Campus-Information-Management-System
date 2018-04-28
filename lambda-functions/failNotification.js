exports.handler = (event, context, callback) => {
    var AWS = require("aws-sdk");
       AWS.config.update({
      region: "us-west-2"
    });
    var ses = new AWS.SES();
    const {studentEmail, courseId} = event;
    const message = "Registration failed for course: " + courseId;
    
   
    var params = {
      Destination: { /* required */
        ToAddresses: [
          studentEmail
        ]
      },
      Message: { /* required */
        Body: { /* required */
          
          Text: {
           Charset: "UTF-8",
           Data: message
          }
         },
         Subject: {
          Charset: 'UTF-8',
          Data: 'Registration failed'
         }
        },
      Source: 'shan.xu@husky.neu.edu', /* required */

    };       
    ses.sendEmail(params, function(err, data) {
      if (err) console.log(err, err.stack); // an error occurred
      else     console.log(data);           // successful response
    });
    callback(null, message);

};