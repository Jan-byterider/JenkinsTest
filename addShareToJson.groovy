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
                    credentialsId: 'ba38f6eb-05e7-4f5b-9fa5-7d5cc7b16184',
                    branch: "develop",
                    changelog: true,
                    poll: true
                    //upstream: true
                    //push: true
                    
                )
                
                script {
                    
                    try{
                        bat "git branch -D newJsonFileBranch"
                    } catch (err) {
                        println "Branch: newJsonFileBranch doesn't exists yet. "
                        
                         bat "git branch -D Develop"
                    } /*catch (err) {
                        println "Branch: Develop doesn't exists yet. "
                    }
                    */
                    bat "git branch"
                    
                    bat "git fetch origin"
                    bat "git checkout -b newJsonFileBranch origin/develop"
                    //bat "git switch newJsonFileBranch"

                    String scriptlocation = "resources\\jsonOperations.ps1"
                    powerShell('pwd')
                    try{
                        powerShell("${scriptlocation} ${params.sharePath} ${params.retentionDays} ${jsonFilePath} ${params.username} ${params.password}") 
                        //bat "git add resources\\nasCleanupByRetentionDate_new.json"
                        bat "git add ."
                        bat "git commit -a -m 'test'"
                        bat "git fetch --all"
                        //bat "git push -u origin develop"
                        //bat "git merge newJsonFileBranch"
                        bat "git checkout origin/develop"
                        //bat "git switch -c origin/newJsonFileBranch"
                        sshagent(['77ec298e-4de3-4d77-bd17-73477c13ca95']){
                            bat "git push -u origin newJsonFileBranch"
                        }
                        
                        bat "git merge origin newJsonFileBranch"

                        //bat "git remote add upstream https://github.com/Jan-byterider/JenkinsTest.git"
                        //bat "git fetch upstream" 
                        //bat "git switch upstream"
                        bat "git branch -D newJsonFileBranch"
                        bat "dir /s"  
                        } catch (err2) {
                        println "catching error ${err2} "
                        throw err2
                    }
                    /*
                    bat "echo New file > newFile.txt" 
                    bat "git add newFile.txt"
                    bat "git commit -a -m 'test'"
                    bat "git switch origin/develop"
                    bat "git merge newJsonFileBranch"
                    bat "git branch -D origin/newBranch"
                    bat "git branch -D newJsonFileBranch"
                    */
                }
            }
        }
        
    }
}
    
    
