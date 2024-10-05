[CmdletBinding()]
param (
    [Parameter(Mandatory=$true)]
    [string]
    $hostname,
    [Parameter(Mandatory=$true)]
    [boolean]
    $dryRun
)
write-host "Powershell script started - Target $hostname - DryRun $dryRun"
[boolean]$createFiles = $false
[boolean]$dryRun = $true
$today = Get-Date
$tresholdDate = $today.AddDays(-10)
try{
    $path = "C:\Users\jan\downloads"
    
    if ($createFiles -eq $true) {
        for($i=0;$i -lt 10; $i++)
        {
            Set-Location $path
            mkdir "dir$i" -Force
            $newPath = join-path -path $path -childPAth "dir$i"
            Set-Location $newPath
            for($j=0;$j -lt 5; $j++){
                mkdir "dir$j" -Force
                $lastPath = Join-Path -Path $newPath -ChildPath "dir$j"
                Set-Location $lastPath
                for($g=0;$g -lt 7;$g++){
                    New-Item -name "file$g.txt" -ItemType File -Value "Dit is file$g" -Force
                }
                set-location $newPath
            }
    
        }    
    }
    $files = Get-ChildItem $path -File -Recurse
    $dirs = Get-ChildItem $path -Directory 
    write-host "--------------------------------------------------------"
    write-host "cleaning $path containing $($files.count) files"
    write-host "cleaning $path containing: "  (Get-ChildItem C:\TEMP -Directory -recurse).count  " folders to delete"
    write-host "--------------------------------------------------------"
    if($files){
        write-host "files to delete: $files"
        if(!$dryRun){
            foreach ($file in $files){
                if ($file.LastWriteTime -le $tresholdDate) {
                    Remove-Item $file -Force -Confirm:$false
                }
            }
        }
    }else{
        write-host 'no files found - no delete action on files'
    }
    if($dirs){
        write-host "dirs to delete: $dirs"
        if(!$dryRun){
            foreach($dir in $dirs){
                if((Get-ChildItem $dir).count -eq 0){
                    Remove-Item $dir -Force
                }
            }
        }
    }else{
        write-host 'no dirs found - no delete action on folders'
    }
}catch{
    write-host "Error cleaning c:\temp"
    write-host $error[0]
}