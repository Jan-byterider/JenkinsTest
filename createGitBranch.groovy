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
           /*
            gitCheckout(
                branch: "develop",
                url: "git@github.com:Jan-byterider/JenkinsTest.git"
                credentialsId:'ba38f6eb-05e7-4f5b-9fa5-7d5cc7b16184'
            )
            /* dir('newBranch'){
                checkout -b 'newBranch'
            
            } */
            checkout scm

        }
    }
}