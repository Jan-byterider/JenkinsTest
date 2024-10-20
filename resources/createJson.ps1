$sharePaths30 = @(
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\in",
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\out",
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\processing",

    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\in",
    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\out",
    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\processing",

    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\in",
    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\out",
    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\processing",

    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\in",
    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\out",
    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\processing",

    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\in",
    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\out",
    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\processing",

    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\in",
    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\out",
    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\processing"
)
$sharePaths90 = @(
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\archive",
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\archive\completed",
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\archive\deprecated",
    "\\sv-arg-nasc-v1.corporate.argenta.be\val.ipaas\gtaia\poi\archive\error",

    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\archive",
    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\archive\completed",
    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\archive\deprecated",
    "\\sv-arg-nasc-a1.corporate.argenta.be\acc.ipaas\gtaia\poi\archive\error",

    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\archive",
    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\archive\completed",
    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\archive\deprecated",
    "\\sv-arg-nasc-d1.corporate.argenta.be\dvl.ipaas\gtaia\poi\archive\error",

    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\archive",
    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\archive\completed",
    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\archive\deprecated",
    "\\sv-arg-nasc-c1.corporate.argenta.be\sic.ipaas\gtaia\poi\archive\error",

    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\archive",
    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\archive\completed",
    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\archive\deprecated",
    "\\sv-arg-nasc-s1.corporate.argenta.be\sim.ipaas\gtaia\poi\archive\error",

    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\archive",
    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\archive\completed",
    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\archive\deprecated",
    "\\sv-arg-nasc-p3.corporate.argenta.be\prd.ipaas\gtaia\poi\archive\error"
)



$shares = [System.Collections.ArrayList]::new()
foreach ($line in $sharePaths30)
{
    $tempShareObject = New-Object psobject -Property @{
        Share = $line
        retention = 30
    }
    $shares.add($tempShareObject) | Out-Null
}
foreach ($line in $sharePaths90)
{
    $tempShareObject = New-Object psobject -Property @{
        Share = $line
        retention = 90
    }
    $shares.add($tempShareObject) | Out-Null
}

$shareJson = $shares | ConvertTo-Json 
$shareJson | out-file -path .\nasCleanupByRetentionDate.json

$jsonObj = ConvertFrom-Json $shareJson
$jsonObj
