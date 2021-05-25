#Simple Data Warehouse (extract, transform, load, query)
You are going to write a simple backend application that exposes data - extracted from a csv file - via an API.
  
  The file can be found here:
  http://adverity-challenge.s3-website-eu-west-1.amazonaws.com/PIxSyyrIKFORrCXfMYqZBI.csv
  
Transform it to your needs so it can be loaded into your favorite data store.
The API should make it possible to query the data in a generic and efficient way.

  Possible queries might look like this:
  
  ● Total Clicks for a given Datasource for a given Date range
  
  ● Click-Through Rate (CTR) per Datasource and Campaign
  
  ● Impressions over time (daily)
  
  And the data looks like this:

  ● a time dimension ( Date )

  ● regular dimensions ( Campaign , Datasource )

  ● metrics ( Clicks , Impressions )


  As a hint, the API consumes these parameters

  ● a set of metrics (plus calculated ones) to be aggregated on

  ● an optional set of dimensions to be grouped by

  ● an optional set of dimension filters to be filtered on

  Tech / architecture

  Feel free to

  ● Choose any JVM based language and framework

  ● Design any architecture that fits the problem/usecase best

  ● Model the API contract on your behalf

  ● Use any additional tech that helps extracting, transforming, loading and querying data

  Deliverable

  ● Publish your solution on GitHub

  ● Deploy it somewhere so that the API can be publicly queried from