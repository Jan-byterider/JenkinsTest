[CmdletBinding()]
param (
    [Parameter(Mandatory=$true)]
    [string]
    $pathToClean,
    [Parameter(Mandatory=$true)]
    [string]
    $hostname,
    [Parameter(Mandatory=$true)]
    [string]
    $dryRun,
    [Parameter(Mandatory=$true)]
    [string]
    $username,
    [Parameter(Mandatory=$true)]
    $secret
)
write-host "Powershell script started - Target $hostname - DryRun $dryRun"
[boolean]$createFiles = $false
#[boolean]$dryRun = $true
$secret = ConvertTo-SecureString -String $secret -AsPlainText -Force
[pscredential]$cred = New-Object System.Management.Automation.PSCredential ($userName, $secret)
$session = New-PSSession -ComputerName $hostname -UseSSL -Authentication Credssp -Credential $cred
$session

if($dryRun -eq 'true'){[bool]$dryRun = $true}
else{[bool]$dryRun = $false}

write-host "username $username"
write-host "password: $secret"


$today = Get-Date
$tresholdDate = $today.AddDays(-10)
$scriptblock = {
    try{
        # set path to downloads folder
        #$path = (New-Object -ComObject Shell.Application).Namespace('shell:Downloads').Self.Path
        try{
            $path = $using:pathToClean
            $check = Test-Path -path $path
        }catch{
            throw $error[0]
        }
        if(!$check)
        {
            throw "ERROR: $path does not exists on $hostname"
        }
        #$path = (New-Object -ComObject Shell.Application).Namespace("shell:$path").Self.Path
        $user = whoami.exe
        write-host $user
        
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
        $extensions = New-Object System.Collections.ArrayList 
        write-host "--------------------------------------------------------"
        write-host "cleaning $path containing $($files.count) files"
        write-host "cleaning $path containing: "  (Get-ChildItem $using:pathToClean -Directory -recurse).count  " folders to delete"
        write-host "--------------------------------------------------------"
        if($files){
            write-host "files to delete: $files"
            if(!$dryRun){
                foreach ($file in $files){
                    if ($file.LastWriteTime -le $tresholdDate) {
                        Remove-Item $file -Force -Confirm:$false
                        $extensions.add("$($files.Extension)")
                    }
                }
            }else{
                foreach ($file in $files){
                    if ($file.LastWriteTime -le $tresholdDate) {
                        $extensions.add("$($files.Extension)")
                    }
                }
            }
           
            $extensions = $extensions | Sort-Object -Unique
            foreach($ext in $extensions){
                write-host "Ext: $ext"
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
        write-host "Error cleaning $path"
        write-host $error[0]
    }
}
try{
    Invoke-Command -Session $session -ScriptBlock $scriptblock -ErrorAction Stop
}catch{
    write-host "Error cleaning $path"
    write-host $error[0]
}
