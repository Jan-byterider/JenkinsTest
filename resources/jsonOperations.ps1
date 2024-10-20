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
try {
    $jsonFileContent = Get-Content -Raw $jsonFilePath
    $jsonObj = [System.Collections.ArrayList]::new()
    [System.Collections.ArrayList]$jsonObj = ConvertFrom-Json $jsonFileContent
} catch {
    Write-Host "Error loading json $jsonFilePath"
}

try {
    $newJsonObject = New-Object -TypeName PSObject -Property @{
        Share= $sharePath
        Retention = [int32]$retentionDays
    }
}
catch {
    Write-Host "Error creating PS object conaining share data"
}

$newJsonObject

try {
    $jsonObj.Add($newJsonObject)
}
catch {
    Write-Host "Error adding share to JSON object array list"
}

try {
    $shareJson = $jsonObj | ConvertTo-Json 
    $shareJson | out-file -path $jsonFilePath -Force
    write-host 
    
}
catch {
    Write-Host "Error writing json $jsonFilePath"
    $Error[0]
    $_
}
