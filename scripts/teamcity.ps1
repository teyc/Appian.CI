<#

.SYNOPSIS

 teamcity.ps1

.DESCRIPTION

 Starting with a package directory, this script
 finds what UUIDs are missing within the package, 
 and then queries TEST, UAT, STAGING and PROD
 whether those UUIDs are present. 
 
 If the UUIDs are missing in the target system, 
 then an error file
 is written out and the Appian developer should
 consult the error file to
 add the missing objects into the application.

#>

[CmdletBinding(SupportsShouldProcess=$true)]
param
(
   [Parameter(Mandatory)][string] $Directory,
   [string] $EnvironmentVariablesScript
)

$fullDirectory = (Get-Item $Directory)
Write-Verbose "`$FullDirectory = $fullDirectory"

Write-Verbose "Invoking $EnvironmentVariablesScript"
. $EnvironmentVariablesScript 
Write-Verbose "`$AppianDeploymentToolsDir $AppianDeploymentToolsDir"

$appianCiJarPath = "$AppianDeploymentToolsDir\Appian.CI.jar"

function Write-PrecedentsNotExported
{
    Write-Verbose "Examining the exported application for precedents which were left out, and listing them in precedentsNotExported.txt"

    java -jar $appianCiJarPath ListMissingPrecedents -directory $fullDirectory > precedentsNotExported.txt
}

function Write-NamesForUuids ($environments)
{
    
    $environments | % {

        $EnvironmentName = $_.Name

        $OutFile = "$EnvironmentName.txt"

        Write-Verbose "QueryNameByUuid $($_.Url) "

        java $httpProxyHost_Opts $httpProxyPort_Opts $httpProxyUsername_Opts $httpProxyPassword_Opts `
          -jar $appianCiJarPath QueryNameByUuid `
          -uuidsFile precedentsNotExported.txt `
          -url $_.Url `
          -username $AppianUserName `
          -password $AppianPassword > $OutFile
    }
}

function Write-MissingUuids ($environments)
{
    $environments | % {

        $EnvironmentName = $_.Name

        $OutFile = "$EnvironmentName.xml"
        $TargetFileName = "$EnvironmentName.txt"

        if ($EnvironmentName -ne "Dev")
        {
            Write-Verbose "CompareMissing $($_.URL) "

            java `
              -jar $appianCiJarPath CompareMissing `
              -source dev.txt `
              -target $TargetFileName `
              -targetname $EnvironmentName `
              > $OutFile
        }

    }
}

Write-PrecedentsNotExported

Write-NamesForUuids $environments

Write-MissingUuids $environments

