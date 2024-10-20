//@Library('sharedLibs@main')
def powerShell(psCmd) {
    psCmd=psCmd.replaceAll("%", "%%")
    bat "powershell.exe -NonInteractive -ExecutionPolicy Bypass -Command \"\$ErrorActionPreference='Stop';[Console]::OutputEncoding=[System.Text.Encoding]::UTF8;$psCmd;EXIT \$global:LastExitCode\""
}

def jsonFilePath = "resources\\nasCleanupByRetentionDate.json"

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
                    branch: "Develop",
                    changelog: true,
                    poll: true
                    //upstream: true
                    //push: true
                    
                )
                
                script {
                    
                    try{
                        bat "git branch -D newJsonFileBranch"
                    } catch (err) {
                        println "Branch: ${newJsonFileBranch} doesn't exists yet. "
                    }

                    bat ""
                    bat "git checkout -b newJsonFileBranch"
                    bat "git switch newJsonFileBranch"
                    bat "git fetch origin Develop"
                    String scriptlocation = "resources\\jsonOperations.ps1"
                    powerShell('pwd')
                    try{
                        powerShell("${scriptlocation} ${params.sharePath} ${params.retentionDays} ${jsonFilePath} ${params.username} ${params.password}") 
                        bat "git add ${jsonFilePath}_new.json"
                        bat "git commit -a -m 'test'"
                        bat "git switch origin/Develop"
                        bat "git merge newJsonFileBranch" 
                        bat "git branch -D origin/newJsonFileBranch"  
                        } catch (err) {
                        println "catching error ${err} "
                        throw err
                    }
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
        
    }
}
    
    
