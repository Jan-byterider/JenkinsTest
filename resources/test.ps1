write-host "Powershell script started"
try{
    $items = Get-ChildItem C:\TEMP -File -Recurse
    write-host "cleaning c:temp containing $items"
    if($items){
        $items | Remove-Item -Force
    }
}catch{
    write-host "Error cleaning c:\temp"
    write-host $error[0]
}