//@Library('sharedLibs@main')
def powerShell(psCmd) {
    psCmd=psCmd.replaceAll("%", "%%")
    bat "powershell.exe -NonInteractive -ExecutionPolicy Bypass -Command \"\$ErrorActionPreference='Stop';[Console]::OutputEncoding=[System.Text.Encoding]::UTF8;$psCmd;EXIT \$global:LastExitCode\""
}

/*parameters { 
    string(name: 'hostname', defaultValue: '', description: 'target hostname')
    boolean(name: 'dryRun', defaultValue: true , description: 'actions are not executed when false')
    }*/

pipeline {
    agent any
    options {
        timestamps()
    }
    stages {
        stage('Git Checkout'){
           stage('Git Checkout'){
                step{
                    gitCheckout(
                        branch: "main",
                        url: "https://github.com/Jan-byterider/PSBackup.git"
                    )
                }
           }
        }
    }
}