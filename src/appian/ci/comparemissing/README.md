CompareMissing
---------------

If you have two servers, one source and one destination,
then you may have a situation where there is a list of UUIDs 
which is present in source but missing in destination.

`QueryNameByUuid` takes care of finding that out, but 
what you want is to take
the list of UUIDs that is missing in destination and combine it
with list of UUIDs that have names from the source, and
create a report showing you the _names_ of the objects
that need to be added to the application before the application
can be deployed successfully.

What CompareMissing produces
-----------------------------
CompareMissing produces a Junit test report that
treats each missing UUID as a test failure.

It is used by TeamCity so that we can trigger a build failure
when precedents are not found.

Prerequisites
----------------

You should have run QueryNameByUuid two times. Once against
the development server, another time against the destination 
server.

Usage
-----------

java -jar Appian.CI.jar CompareMissing ^
    -source=dev.txt ^
    -target=test.txt ^
    -targetname=TestEnv > TestEnv-junit.xml
