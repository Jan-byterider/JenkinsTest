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
    stages {
        stage('Setup parameters') {
            steps {
                script { 
                    properties([
                        parameters([
                            string(name: 'hostname', defaultValue: '', description: 'target hostname'),
                            boolean(name: 'dryRun', defaultValue: true , description: 'actions are not executed when false')
                        ])
                ])
            }
        }
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
                        String scriptlocation = 'resources\\cleanupFiles.ps1'
                        powerShell('pwd')
                        powerShell("${scriptlocation} ${params.hostname} \$true")

                    }
                }
            }
        }
    }
}

