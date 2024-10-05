write-host "Powershell script started"
try{
    $path = "C:\TEMP"
    for($i=0;$i-lt 10; $i++)
    {
        Set-Location $path
        mkdir "dir$i"
        $newPath = join-path -path $path -childPAth "dir$i"
        Set-Location $newPath
        for($i=0;$i-lt 5; $i++){
            mkdir "dir$i"
            $lastPath = Join-Path -Path $newPath -ChildPath $lastPath
            Set-Location $lastPath
            New-Item -name "file$i.txt" -ItemType File -Value "Dit is file$i"
        }
    }
    $files = Get-ChildItem C:\TEMP -File -Recurse
    $dirs = Get-ChildItem C:\TEMP -Directory 
    write-host "cleaning c:temp containing $items"
    if($files){
        write-host "files to delete: $files"
        $files | Remove-Item -Force -Confirm:$false
    }else{
        write-host 'no files found - no delete action on files'
    }
    if($dirs){
        write-host "dirs to delete: $dirs"
        $dirs | Remove-Item -Force -Confirm:$false -Recurse
    }else{
        write-host 'no dirs found - no delete action on folders'
    }
}catch{
    write-host "Error cleaning c:\temp"
    write-host $error[0]
}