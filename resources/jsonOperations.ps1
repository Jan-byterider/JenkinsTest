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
    $jsonFilePath,
    [Parameter(Mandatory=$true)]
    [string]
    $username,
    [Parameter(Mandatory=$true)]
    $secret
)

write-host "Powershell script started - SharePath: $sharePath retentionDays: $retentionDays jsonFilePath: $jsonFilePath username: $username secret: $secret"
$secret = ConvertTo-SecureString -String $secret -AsPlainText -Force
[pscredential]$cred = New-Object System.Management.Automation.PSCredential ($userName, $secret)
$jsonFilePath2 = $jsonFilePath.Replace('.json','_new.json')
$jsonFilePath2

#[boolean]$dryRun = $true#read json
try {
    $jsonFileContent = Get-Content -Raw $jsonFilePath
    $jsonObj = [System.Collections.ArrayList]::new()
    [System.Collections.ArrayList]$jsonObj = ConvertFrom-Json $jsonFileContent
    Write-Host $jsonObj
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
    throw $Error
}

try {
    $shareJson = $jsonObj | ConvertTo-Json 
    write-host "shareJson: $shareJson"
    Get-Location | Write-Host
    $items = Get-ChildItem -Recurse 
    write-host $items
    $session = New-PSSession -Credential $cred -Authentication Credssp -UseSSL -ComputerName bachus
    Invoke-Command -Session $session  -ScriptBlock {
        param(
            [Parameter()]
            $shareJsdon
        ) 
        write-host "parameter info : $shareJson"
         out-file $shareJson  -FilePath .\nasCleanupByRetentionDate.json}-ArgumentList @($shareJson)
    #write-host 
    
}
catch {
    Write-Host "Error writing json ..\nasCleanupByRetentionDate.json"
    Write-host -f red "Encountered Error:"$_.Exception.Message
    throw $_
    $_
}
