//@Library('sharedLibs@main')
def powerShell(psCmd) {
    psCmd=psCmd.replaceAll("%", "%%")
    bat "powershell.exe -NonInteractive -ExecutionPolicy Bypass -Command \"\$ErrorActionPreference='Stop';[Console]::OutputEncoding=[System.Text.Encoding]::UTF8;$psCmd;EXIT \$global:LastExitCode\""
}

def jsonFilePath = 'resources\\nasCleanupByRetentionDate.json'

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
                            string(name: 'sharePath', defaultValue: '', description: 'path of the share \\networklocation\folder'),
                            string(name: 'retentionDays', defaultValue: '30', description: 'days to keep the files'),
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
                    branch: "Develop",
                    changelog: true,
                    poll: true
                    //upstream: true
                    //push: true
                    
                )
                
                script {
                    bat "git checkout -D newJsonFileBranch"
                    bat "git switch newJsonFileBranch"
                    bat "git pull origin Develop"
                    /*
                    bat "echo New file > newFile.txt" 
                    bat "git add newFile.txt"
                    bat "git commit -a -m 'test'"
                    bat "git switch origin/Develop"
                    bat "git merge newJsonFileBranch"
                    bat "git branch -D origin/newBranch"
                    bat "git branch -D newJsonFileBranch"
                    */
                }
            }
        }
        stage('add shares to Json file') {
            steps {
                git(
                    url: "https://github.com/Jan-byterider/JenkinsTest.git",
                    branch: "Develop",
                    changelog: true,
                    poll: true
                    //upstream: true
                    //push: true
                    
                )
                
                script {
                    bat "git switch newJsonFileBranch"
                    bat "git add jsonFilePath"
                    bat "git commit -a -m 'test'"
                    bat "git switch origin/Develop"
                    bat "git merge newJsonFileBranch"
                    bat "git branch -D origin/newJsonFileBranch"
                }
            }
        }
    }
}
    
    