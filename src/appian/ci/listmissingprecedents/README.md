ListMissingPrecedents
-------------------------

When you export a .zip application from
Appian, you may have intentionally or unintentionally
left out some precedents. ListMissingPrecedents is
a tool to help the continuous integration pipeline
work out what precedents are missing in the package.

It works in a similar way to the missing precedents
screen in Appian. As it is not easy to automate this
process, we have created ListMissingPrecedents to fill
the gap.

However, it is very new, and may likely miss some
precedents. If this is the case, have a look at the 
ListMissingPrecedents test case and see if you can
add it to the list.

How ListMissingPrecedents in used in the build pipeline
--------------------------------------------------

`QueryNameByUuid` later uses the results from 
ListMissingPrecedents in the continuous integration
pipeline.

Sample Usage
------------------

Step 1: Unzip the .zip file into C:\MyApplication\application_files

    7z x C:\downloads\MyApplication.zip -d C:\dev\MyApplication\application_files

Step 2: Run this command

    java -jar Appian.CI.jar ^
         -directory C:\dev\MyApplication\application_files > MissingPrecedents.txt



