QueryNameByUuid 
---------------

When you export a .zip file from Appian, you may have intentionally
left out some precedents because you expect these to have been already
deployed on the server.

QueryNameByUuid can read a file of UUIDs and tell you whether the
uuids exist on a destination server where you will be importing your
application into.

Prerequisites
----------------

You need to import an application package Appian.CI.QueryNameByUuid.
It contains a webapi endpoint that will return information when you
provide it a uuid.

How QueryNameByUuid in used in the build pipeline
--------------------------------------------------

`CompareMissing` later uses results from QueryNameByUuid. 

Sample usage
--------------------

First, use QueryNameByUuid against your destination server and pipe the
results to a file.

    java -jar Appian.CI.jar QueryNameByUuid ^
       -uuidsFile=missingPrecedents.txt ^
       -url=https://test.appiancloud.com/suite ^
       -username=joe ^
       -password=joes_secret > test.txt

Next, do the same against the development server

    java -jar Appian.CI.jar QueryNameByUuid ^
       -uuidsFile=missingPrecedents.txt ^
       -url=https://dev.appiancloud.com/suite ^
       -username=joe ^
       -password=joes_secret > dev.txt
