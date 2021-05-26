# Adverity coding challenge

[![CircleCI](https://circleci.com/gh/fbeba/adverity.svg?style=shield&circle-token=05c5e129103e3d8deeee83b93915a6818710c824)](https://app.circleci.com/pipelines/github/fbeba/adverity)

Hello there. I am very pleased to have you here, interested in my work. I imagine the next section is by no means an epiphany for you, but still - who doesn't appreciate some ready to copy-paste set of commands, huh? Just to speed things up a bit. So let's start with... 

## Application preparation

First, we need to build it. With JDK 11 of any flavour. In order to build the app simply run

`$ mvn clean package`

Once completed application can be started by issuing lame

`java -jar .\target\adverity-1.0.jar`

For those who prefer docker way of life an alternative approach has been prepared. 

Type in:

`$ docker build -t adverity:1.0 .`

to build the image and then to start the container run:

`$ docker run -d -p 8080:8080 adverity:1.0`

_It doesn't do much else, I just needed this in order to send it to Amazon's ECR, but since I already have it..._

Now application should be up and running. If not then maybe you're looking for someone other than me ;) Or not, your call.

Assuming it works though - you can move to the next section and see what amazing stuff you can do with this app!

## API 

What can we do with it? Not that much, I admit. It's a tech demonstrator after all, not a production-grade service. But it can do the following:

### Query metrics as daily trends

`GET /impressions`

`GET /clicks`

Both of these accept request parameters `from=yyyy-MM-dd` and `to=yyy-MM-dd`. Those parameters are not required, but be aware that if you don't specify them yourself an app will provide you with default ones (beginning of time and today)

### CTR for dimension

`GET /ctr`

It will give you the CTRs computed in context of given parameter `dimension={DATASOURCE|CAMPAIGN}`. You don't actually need to put in whole dimension name. You can also try `dimension=data` or `dimension=d`, as long as it is a beginning part of dimension name - you're good.

### Summarized metrics by dimension

`GET /metric/{metric}/byDatasource/{datasourceName}`

`GET /metric/{metric}/byCampaign/{campaignName}`

Should you be interested in summarized metric (`CLICKS` or `IMPRESSIONS`) for given dimension (DATASOURCE or CAMPAIGN) in a specified time window (`from=yyyy-MM-dd` and `to=yyyy-MM-dd`) then this is the endpoint for you. 

And yes, you can be ~~lazy~~ effort-efficient here as well (e.g. `i` instead of `IMPRESSIONS` and so on)  

### Adding entries

`POST /add`

This endpoint can be called when you want to add some more data to application storage. 
It needs to be provided as request parameter `file` and can not exceed 10MB of size. 
File must be of type csv with column headers `Datasource,Campaign,Daily,Clicks,Impressions`
Please note that during import all queries are blocked with service returning HTTP 503, even if initializing request receives a response. 

### AWS

For showoff purposes I created an AWS deployment of the service. It is available under:
http://ec2-54-154-185-220.eu-west-1.compute.amazonaws.com:8080

## Dev Decisions Background
* Dockerfile is prepared assuming that if you have Docker you also have Maven and JDK 11. Hopefully it is a fair assumption ;) 
* Spring because extreme performance has not been considered a priority but optimal development speed has.
* Lombok because it's OOtB in Intellij and noone loves writing boilerplate
* opencsv because it does the job and I used it couple of times before
* Solution is built as DB-agnostic. This approach allows for (relatively) easy portability to any DB, while InMemory repository has proven extremely useful for TDD.  
* In order to increase performance the repository works on shallow copies. It results in increased risk to data integrity. 
Since application is for demo purposes only and performs non-modifying queries I considered it acceptable. 
