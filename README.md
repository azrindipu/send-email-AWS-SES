# Send Email using spring boot and AWS SES

This is a rest API to send email using AWS SES service. You have to add the configuration in the property file to according to your SES settings.

I have used mongoDB to store the email and a scheduler is responsible to send the email which are stored in db and also all history is stored for further reference.

## You have to modify

```
#---------------------------- mongoDB ------------------------------
spring.data.mongodb.database=<Your database name>
spring.data.mongodb.username=<Your database user name>
spring.data.mongodb.password=<Your database password>
spring.data.mongodb.port=<Your database port>
spring.data.mongodb.host=<Your database host name>
spring.data.mongodb.authentication-database=<Mongo authentication database name>

#---------------------------- mail ---------------------------------
spring.mail.host=<AWS-SES email host name>
spring.mail.port=<AWS-SES email port>
spring.mail.username=<AWS-SES user name>
spring.mail.password=<AWS-SES password>
spring.mail.from=<AWS_SES email name. Example: no-reply@quixx.xyz>
```

You have to modify the above configuration accordingly.
