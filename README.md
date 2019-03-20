# structured-data-strreaming
# Data Stream Development with Apache Spark, Kafka, and Spring Boot
This is the code repository for article at linkedin. It contains all the supporting project files necessary to work through the video course from start to finish.
### Technical Requirements
This course has the following software requirements:<br/>
Java 8
Kafka
MongoDB
Java compatible IDE

It consists of three projects.
__CollectSendToKafka:__
This program acts as a collector. It reads the input stream from the MeetupRSVP and send it to Kafka.

__SparkKafkaToMongo:__
Reads the message from Kafka queue, processes it and inserts the final output in to MongoDB.

__MongoReactiveWebSSE:__
It consists of a web page that uses a server sent event to display contents of MongoDB on MAP UI as they become available.

