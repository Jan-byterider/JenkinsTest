def PowerShell(psCmd) {
    psCmd=psCmd.replaceAll("%", "%%")
    bat "powershell.exe -NonInteractive -ExecutionPolicy Bypass -Command \"\$ErrorActionPreference='Stop';[Console]::OutputEncoding=[System.Text.Encoding]::UTF8;$psCmd;EXIT \$global:LastExitCode\""
}

pipeline {
    agent any
    stages {
        
            stage("Clone Git Repository") {
                steps {
                    git(
                        url: "https://github.com/Jan-byterider/JenkinsTest.git",
                        branch: "main",
                        changelog: true,
                        poll: true
                    )
                }
            }        
            stage('Hello World') {
                steps {
                    script {
                        println('Hello, World')
                        String scriptlocation = 'resources\\test.ps1'
                        PowerShell('pwd')
                        PowerShell('scriptlocation')

                    }
                }
            }
        }
    }

