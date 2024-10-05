write-host "Powershell script started"
try{
    $files = Get-ChildItem C:\TEMP -File -Recurse
    $dirs = Get-ChildItem C:\TEMP -Directory 
    write-host "cleaning c:temp containing $items"
    if($items){
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