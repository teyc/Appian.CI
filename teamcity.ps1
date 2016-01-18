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

$appianCiJarPath = ".\dist\Appian.CI.jar"

java -jar $appianCiJarPath ListMissingPrecedents -directory $fullDirectory > missing.txt

$environments = @(
  @{ Name="Dev"      ; Url = "https://dev.appiancloud.com/suite" }, 
  @{ Name="Test"     ; Url = "https://test.appiancloud.com/suite" }, 
  @{ Name="UAT"      ; Url = "https://uat.appiancloud.com/suite" }, 
  @{ Name="Staging"  ; Url = "https://staging.appiancloud.com/suite" }, 
  @{ Name="Prod"     ; Url = "https://prod.appiancloud.com/suite" }
);

$environments | % {

    $EnvironmentName = $_.Name

    $OutFile = "$EnvironmentName.txt"

    Write-Verbose "QueryNameByUuid $($_.Url) "

    java $httpProxyHost_Opts $httpProxyPort_Opts $httpProxyUsername_Opts $httpProxyPassword_Opts `
      -jar $appianCiJarPath QueryNameByUuid `
      -uuidsFile missing.txt `
      -url $_.Url `
      -username joe.bloggs `
      -key GamLGtaAVHgTbjwsx2A2WB1w= `
      -password vWG6ipOP2Iw= > $OutFile
}

$environments | % {

    $EnvironmentName = $_.Name

    $OutFile = "$EnvironmentName.xml"
    $TargetFileName = "$EnvironmentName.txt"

    if ($EnvironmentName -ne "Dev")
    {
        Write-Verbose "CompareMissing $($_.URL) "

        java `
          -jar .\dist\Appian.CI.jar CompareMissing `
          -source dev.txt `
          -target $TargetFileName `
          -targetname $EnvironmentName `
          > $OutFile
    }
}
