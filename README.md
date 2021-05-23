# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)



### Dev Notes
* In order to increase performance the repository works mostly on shallow copies. It results in increased risk to data integrity. 
Since application is for demo purposes only and performs non-modifying queries I considered it acceptable. 
To demonstrate performance implications of applying safer solution of deep copy it was used in case of query for CTR grouped by Campaign.
Tests show aprox. 30x increase in processing time.