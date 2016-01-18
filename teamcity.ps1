#
# teamcity.ps1
#
# Starting with a package directory, this script
# finds what UUIDs are missing within the package, 
# and then queries TEST, UAT, STAGING and PROD
# whether those UUIDs are present. 
# 
# If the UUIDs are missing in the target system, 
# then an error file
# is written out and the Appian developer should
# consult the error file to
# add the missing objects into the application.
#
param
(
   [Parameter(Mandatory)] $directory
)

$fullDirectory = (Get-Item $directory)
write-debug "FullDirectory = $fullDirectory"

$httpProxyHost_Opts = "-Dhttps.proxyHost=webproxy.local"
$httpProxyPort_Opts = "-Dhttps.proxyPort=80"
$httpProxyUsername_Opts = "-Dhttps.proxyUser=joe.bloggs"
$httpProxyPassword_Opts = "-Dhttps.proxyPassword=supersecret"

java -jar .\dist\Appian.CI.jar ListMissingPrecedents -directory $fullDirectory > missing.txt

java `
  $httpProxyHost_Opts $httpProxyPort_Opts $httpProxyUsername_Opts $httpProxyPassword_Opts ` 
  -jar .\dist\Appian.CI.jar QueryNameByUuid `
  -uuidsFile missing.txt `
  -url https://dev.appiancloud.com/suite `
  -username joe.bloggs `
  -key GamlMtaAVHgTbjwsx2A2WB1w= `
  -password vWG6ipOP2Iw= > dev.txt

java `
  $httpProxyHost_Opts $httpProxyPort_Opts $httpProxyUsername_Opts $httpProxyPassword_Opts ` 
  -jar .\dist\Appian.CI.jar QueryNameByUuid `
  -uuidsFile missing.txt `
  -url https://test.appiancloud.com/suite `
  -username joe.bloggs `
  -key GamlMtaAVHgTbjwsx2A2WB1w= `
  -password vWG6ipOP2Iw= > test.txt

java `
  $httpProxyHost_Opts $httpProxyPort_Opts $httpProxyUsername_Opts $httpProxyPassword_Opts ` 
  -jar .\dist\Appian.CI.jar QueryNameByUuid `
  -uuidsFile missing.txt `
  -url https://uat.appiancloud.com/suite `
  -username joe.bloggs `
  -key GamlMtaAVHgTbjwsx2A2WB1w= `
  -password vWG6ipOP2Iw= > uat.txt

java `
  $httpProxyHost_Opts $httpProxyPort_Opts $httpProxyUsername_Opts $httpProxyPassword_Opts ` 
  -jar .\dist\Appian.CI.jar QueryNameByUuid `
  -uuidsFile missing.txt `
  -url https://prod.appiancloud.com/suite `
  -username joe.bloggs `
  -key GamlMtaAVHgTbjwsx2A2WB1w= `
  -password vWG6ipOP2Iw= > prod.txt

java `
  -jar .\dist\Appian.CI.jar CompareMissing `
  -source dev.txt `
  -target test.txt `
  -targetname "TEST" `
  > test.xml

java `
  -jar .\dist\Appian.CI.jar CompareMissing `
  -source dev.txt `
  -target uat.txt `
  -targetname "UAT" `
  > uat.xml

java `
  -jar .\dist\Appian.CI.jar CompareMissing `
  -source dev.txt `
  -target uat.txt `
  -targetname "STAGING" `
  > staging.xml

java `
  -jar .\dist\Appian.CI.jar CompareMissing `
  -source dev.txt `
  -target uat.txt `
  -targetname "PROD" `
  > prod.xml

