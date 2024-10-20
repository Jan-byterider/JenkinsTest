[CmdletBinding()]
param (
    [Parameter(Mandatory=$true)]
    [string]
    $sharePath,
    [Parameter(Mandatory=$true)]
    [string]
    $retentionDays,
    [Parameter(Mandatory=$true)]
    [string]
    $jsonFilePath
)

write-host "Powershell script started - SharePath: $sharePath retentionDays: $retentionDays jsonFilePath: $jsonFilePath"

#[boolean]$dryRun = $true#read json
$jsonFileContent = Get-Content -Raw $jsonFilePath
$jsonObj = [System.Collections.ArrayList]::new()
[System.Collections.ArrayList]$jsonObj = ConvertFrom-Json $jsonFileContent

$newJsonObject = New-Object -TypeName PSObject -Property @{
    Share= $sharePath
    Retention = [int32]$retentionDays
}

$newJsonObject
$jsonObj.Add($newJsonObject)

$shareJson = $jsonObj | ConvertTo-Json 
$shareJson | out-file -path $jsonFilePath -Force
write-host 

