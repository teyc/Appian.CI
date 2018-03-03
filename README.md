Appian Deployment Tools
=========================

Appian doesn't really have a great story for deploying changes
from Dev to Test to UAT and to Production environments. This tool
aims to solve that by having your changes checked into source control
and building deployment packages off source control from a build server.


Overview
---------

You will need to adopt the following development workflow for this
deployment tools to work.

Firstly you need to use a version control system. 

Secondly, you should have some kind of build server, and a deployment service.

Workflow to ensure all precedents are present
---------------------------------------------

1. Developer exports Appian application 
   package and downloads zip file to his/her
   development machine

2. Unzips package into working directory
   where application was formerly checked out

3. Uses ListMissingPrecedents to find all
   the uuids that are missing in the application.

4. Uses QueryNamesByUuid on _Production_ instance
   to find out which of the missing UUIDs are
   already present on the server.

5. Uses QueryNamesByUuid on _Development_ instance
   to get a human readable list of names for the
   missing UUIDs so they can be added to the 
   application for export.

6. Developer manually adds the missing UUIDs 
   to the application.

7. Uses BuildPackage to create a new zip file
   that can be deployed on to a server.

8. Uses InspectPackage to inspect that the zip file
   will deploy.

9. Uses DeployPackage to deploy the zip file.

Workflow to ensure that precedents are up-to-date
--------------------------------------------------

Just because a precedent is present on the Production
instance does not mean that the precedent is up-to-date.
By not including the updated precedent, the deployed package
might not run correctly.

To avert this, the developer could

1. Use ListMissingPrecedents to find out which
   precedents are not going to be deployed.

2. Use ExportAllObjects to export all objects
   from _Development_ and _Production_ instances.

3. Use CompareObjectsByUuid to see if the objects
   with the listed Uuids have changed.

4. Decide whether to include the changed Uuids   
   
Tools
-----------

**appian.ListMissingPrecedents**

Usage:

    appian.ListMissingPrecedents C:\dev\myapplication

lists all the precedents that are missing in
this package.

**appian.QueryNamesByUuid**

Usage:

    appian.QueryNamesByUuid -uuids ... -url ... -username ... -password ...

Queries a server the names of each given UUID.

**appian.BuildPackage**

Usage:

    appian.BuildPackage -directory ... -out ...

**appian.CompareObjectsByUuid**


Notes:
https://yourserver.appiancloud.com/suite/webapi/getContent?uuid=0003dc12-4371-8000-f92f-7f0000014e7a

