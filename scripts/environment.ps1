$Application_Path = $null
$AppianUserName   = $null
$AppianPassword   = $null
$AppianUrl        = $null
$AppianDeploymentToolsDir = "..\dist\"

$HttpsProxyHost     = $null
$HttpsProxyPort     = $null
$HttpsProxyUser     = $null
$HttpsProxyPassword = $null

$environments = @(
  @{ Name="Dev"      ; Url = "https://dev.appiancloud.com/suite" }, 
  @{ Name="Test"     ; Url = "https://test.appiancloud.com/suite" }, 
  @{ Name="UAT"      ; Url = "https://uat.appiancloud.com/suite" }, 
  @{ Name="Staging"  ; Url = "https://staging.appiancloud.com/suite" }, 
  @{ Name="Prod"     ; Url = "https://prod.appiancloud.com/suite" }
);

