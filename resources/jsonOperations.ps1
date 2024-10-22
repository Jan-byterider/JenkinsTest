[CmdletBinding()]
param (
    [Parameter(Mandatory=$true)]
    [string]
    $sharePath,
    [Parameter(Mandatory=$true)]
    [String]
    $retentionDays,
    [Parameter(Mandatory=$true)]
    [string]
    $jsonFilePath,
    [Parameter(Mandatory=$false)]
    [string]
    $username,
    [Parameter(Mandatory=$false)]
    $secret
)

write-host "Powershell script started - SharePath: $sharePath retentionDays: $retentionDays jsonFilePath: $jsonFilePath username: $username secret: $secret"

if($username -AND $secret ){
     $secret = ConvertTo-SecureString -String $secret -AsPlainText -Force
     $cred = New-Object System.Management.Automation.PSCredential ($userName, $secret)
}
#$jsonFilePath2 = $jsonFilePath.Replace('.json','_new.json')
#$jsonFilePath2

#[boolean]$dryRun = $true#read json
try {
    if(Test-Path $jsonFilePath){
        $jsonFileContent = Get-Content -Raw $jsonFilePath
        $jsonObj = [System.Collections.ArrayList]::new()
        
        [System.Collections.ArrayList]$jsonObj = ConvertFrom-Json $jsonFileContent
        
        Write-Host $jsonObj
        $shareJson =  ConvertTo-Json -InputObject $jsonObj
    }
} catch {
    Write-Host "Error loading json $jsonFilePath"
    Write-host -f red "Encountered Error:"$_.Exception.Message
}

try {
    $newJsonObject = New-Object -TypeName PSObject -Property @{
        Share = $sharePath
        Retention = [int32]$retentionDays
    }
}
catch {
    Write-Host "Error creating PS object conaining share data"
    Write-host -f red "Encountered Error:"$_.Exception.Message
}

$newJsonObject

try {
    $jsonObj.Add($newJsonObject)
}
catch {
    Write-Host "Error adding share to JSON object array list"
    Write-host -f red "Encountered Error:"$_.Exception.Message
    throw $Error
}

try {
    $shareJson =  ConvertTo-Json -InputObject $jsonObj
    write-host "shareJson: $shareJson"
    Write-host -f red "Encountered Error:"$_.Exception.Message
    $shareJson | out-file  -FilePath $jsonFilePath
}
catch {
    Write-Host "Error writing json $jsonFilePath"
    Write-host -f red "Encountered Error:"$_.Exception.Message
    throw $_
    $_
}
