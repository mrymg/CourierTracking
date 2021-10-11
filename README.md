#  A Simple Springboot Application of Migros Courier Tracking

This is a simple application that uses REST services which developed with SpringBoot. It records the courier location in every service call and calculate courier total distance between courier locations. When courier comes near a store with distance less than 100 meter, system log this and record it to database. 
If Courier comes near the store again less than a minute, system will not record this. This will be reentrance.


# Design Notes

## Example Inputs

 - Add new courier and check if there is a near stores. 
```
POST /api/couriers
```
    { 
    "courierId": 6,
    "lat": 40.993016,
    "lng": 29.124946
    }
 - Get Courier Total Travel Distance 
```
GET /courier/totalTravelDistance/6
```
## Example Outputs
![](https://raw.githubusercontent.com/mrymg/CourierTracking/master/Screenshot%202021-10-11%20161717.png)


## Used Design Patterns

 - Singleton
 - Builder

## Database

This application uses an in-memory database (H2) .

> datasource.url : jdbc:h2:mem:migDB
> datasource.username : admin
> datasource.password : migros

All Migros Stores store at **STORE** table. 
When application is starting, Migros Stores are automatically read from resources/stores.json file and wrote to database.


## Server

Since my 8080 port is used by another projects, this application server port is: **8888**
So the link is like: 

    http://localhost:8888/courier

## Prerequisites
The following items should be installed in your system:

 - Maven 3 
 - Lombok
 - Git

### Steps

 1. Clone with git
`git clone https://github.com/mrymg/CourierTracking.git`

 2. In Intellij
`File -> New -> Project From Existing Source`

You can control the db with using this link:

    http://localhost:8888/h2-console/l


