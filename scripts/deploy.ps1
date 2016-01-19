<#

.SYNOPSIS 

Deploys the files under the application_files directory
to an Appian Server

.DESCRIPTION

This script assumes the following variables have been set.
If you are using Octopus Deploy, you can set them in the 
variables section.

  $Application_Path - path to the unzipped package
  $AppianUserName   - Appian login
  $AppianPassword   - Appian password
  $AppianUrl        - AppianServer
  $JarPath          - path to AutomatedDeployment.jar

In addition, if you are running behind a proxy,
you should set the proxy parameters
  $HttpsProxyHost     - e.g my.proxy.local
  $HttpsProxyPort     - e.g.80
  $HttpsProxyUser     - logon
  $HttpsProxyPassword - password
#>

#
# Allows bindings such as
#   .\Deploy.ps1 -Verbose
#
[CmdletBinding(SupportsShouldProcess=$true)]
Param(

  [switch]$RetainZip

)

function Get-ApplicationFilesPath()
{
  Return Join-Path $Application_Path "application_files\"
}

function Get-ZipExe()
{
    if ($jarPath -And (Test-Path $jarPath))
    {
       Return Join-Path (Get-Item $jarPath).DirectoryName 7z.exe
    }

}

function Validate-Arguments
{
  [CmdletBinding()]
  Param( )

  Process
  {
    $applicationFilesPath = Get-ApplicationFilesPath
    if (Test-Path $applicationFilesPath)
    {
       Write-Verbose "OK `$Application_Path. Subdirectory $applicationFilesPath found."
    }
    else
    {
       $message = "`$Application_Path should have a subdirectory called application_files `r`n" `
                + "`$Application_Path is currently set to '$Application_Path'"
       Write-Error $message
    }

    $isCorrectFileName = ($jarPath -And $jarPath.ToLower().EndsWith("automateddeployment.jar"))
    if ($jarPath -And (Test-Path $jarPath) -And $isCorrectFileName)
    {
       Write-Verbose "OK `$JarPath. '$jarpath' found."
    }
    elseif (-Not $isCorrectFileName)
    {
       Write-Error "`$jarPath must end with automatedDeployment.jar. Currently '$jarPath'"
    }
    elseif (($jarPath) -And (-Not (Test-Path $jarPath)))
    {
       Write-Error "`$jarPath '$jarPath' cannot be found."
    } 

    $zipExe = Get-ZipExe
    if (Test-Path $ZipExe)
    {
       Write-Verbose "OK `$ZipExe found $ZipExe"
    }
    else
    {
       Write-Error "`Cannot find '$ZipExe'"
    }

    if ($AppianUsername)
    {
       Write-Verbose "OK `$AppianUserName $AppianUserName present."
    }
    else 
    {
       Write-Error "`$AppianUserName must be set"
    }

    if ($AppianPassword)
    {
       Write-Verbose "OK `$AppianPassword ****** present."
    }
    else 
    {
       Write-Error "`$AppianPassword must be set"
    }

    if ($AppianUrl)
    {
       Write-Verbose "OK `$AppianUrl $AppianUrl present."
    }
    else
    {
       Write-Error "$AppianUrl must be set."
    }
  }
}

function New-ApplicationZip($applicationFilesPath)
{
  
    $tempFileName = [System.IO.Path]::GetTempFileName() + ".zip"

    $zipExe = Get-ZipExe

    Write-Verbose "Creating $tempFileName using contents from $applicationFilesPath with $ZipExe"
    
    & $ZipExe a $tempFileName -r $applicationFilesPath\* | Out-Null

    return $tempFileName

}

Validate-Arguments -ErrorAction Continue -ErrorVariable ValidateError
If ($ValidateError)
{
    Exit 9001
}

$zipFile = New-ApplicationZip (Get-ApplicationFilesPath)
Write-Verbose "`$zipFile is $zipFile"


If ($HttpsProxyHost)     { $HttpsProxyHost_Opts     = "-Dhttps.proxyHost=$HttpsProxyHost" }
If ($HttpsProxyPort)     { $HttpsProxyPort_Opts     = "-Dhttps.proxyPort=$HttpsProxyPort" }
If ($HttpsProxyUser)     { $HttpsProxyUser_Opts     = "-Dhttps.proxyUser=$HttpsProxyUser"}
If ($HttpsProxyPassword) { $HttpsProxyPassword_Opts = "-Dhttps.proxyPassword=$HttpsProxyPassword" }

[Environment]::SetEnvironmentVariable("JAVA_OPTS", $java_opts, "Process")


$javaOut = & { java `
    $HttpsProxyHost_opts `
    $HttpsProxyPort_opts `
    $HttpsProxyUser_opts `
    $HttpsProxyPassword_opts `
    -jar $JarPath `
    -username $AppianUsername `
    -password $AppianPassword `
    -application_path $zipFile `
    -url $AppianUrl } 2>&1 | Out-String

if ($javaOut -match "not inspect properly")
{
   if ($javaOut -Match "log file id is (\d+).")
   {
      $logFileId = $Matches[1]
      Write-Error "Automated deployment failed. See log at $AppianUrl/docs/$logfileid"
   }
   else
   {
      Write-Error "Automated deployment failed. Log file not found."
   }
}

If ($RetainZip)
{
  Write-Verbose "Retaining zip file $zipFile because -RetainZip specified."
}
Else
{  
  Remove-Item $ZipFile.Replace(".zip", "")
  Remove-Item $ZipFile
  Write-Verbose "Deleted zip file $zipFile because -RetainZip not specified."
}
