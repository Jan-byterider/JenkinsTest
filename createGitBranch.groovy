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
        /* stage('Git Checkout'){
            steps {
                    git(gitCheckout(
                        branch: "main",
                        url: "https://github.com/Jan-byterider/PSBackup.git"
                    )
            }
        } */

        stage('Setup parameters') {
            steps {
                script { 
                    properties([
                        parameters([
                            string(name: 'pathToClean', defaultValue: 'Downloads', description: 'all childitems > 10 days will be deleted'),
                            string(name: 'hostname', defaultValue: 'target hostname', description: 'target hostname'),
                            string(name: 'username' ,defaultValue: 'email address', description: 'username' ),
                            string(name: 'password', defaultValue: '', description: 'password'),
                            booleanParam(name: 'dryRun', defaultValue: true , description: 'actions are not executed when false')
                        ])
                    ])
                }
            }
        }
        stage("Clone Git Repository") {
            steps {
                git(
                    url: "https://github.com/Jan-byterider/JenkinsTest.git",
                    branch: "newBranch",
                    changelog: true,
                    poll: true,
                    upstream: true
                    //push: true
                    
                )
                
                script {
                    //'checkout -b newBranch'
                     'push --set-upstream origin newBranch'
                     gitCheckout                }
            }
        }
    }
}