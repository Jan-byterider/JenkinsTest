//@Library('sharedLibs@main')
def powerShell(psCmd) {
    psCmd=psCmd.replaceAll("%", "%%")
    bat "powershell.exe -NonInteractive -ExecutionPolicy Bypass -Command \"\$ErrorActionPreference='Stop';[Console]::OutputEncoding=[System.Text.Encoding]::UTF8;$psCmd;EXIT \$global:LastExitCode\""
}

//def jsonFilePath = "resources\\nasCleanupByRetentionDate.json"
def jsonFilePath = "C:\\git\\JenkinsTest\\resources\\nasCleanupByRetentionDate.json"

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
                    credentialsId: 'newGit2',
                    branch: "develop",
                    changelog: true,
                    poll: true
                    //upstream: true
                    //push: true
                    
                )
                
                script {
                    
                    try{
                        bat "path=c:\\Program Files\\git\\usr\\bin;%path%"
                        bat "git branch -D newJsonFileBranc"
                    } catch (err) {
                        println "Branch: newJsonFileBranch doesn't exists yet. "
                                bat "git branch -D origin/newJsonFileBranch"
                         bat "git branch -D develop"
                    } catch (err3) {
                        println "Branch: Develop doesn't exists yet. "
                        try{
                            bat "git branch -D temp"}
                            catch (err4) {
                                println "Branch: temp doesn't exists yet. "
                            } 

                    }
                    
                    bat "git fetch origin"
                    bat "git fetch upstream"
                    //bat "git checkout -b newJsonFileBranch --track origin/develop"
                    bat "git remote -v"
                    bat "git branch"
                    bat "git checkout upstream/develop"
                    try{
                            bat "git switch -c temp"
                        } catch (err5){
                             println "Branch: temp already exists"
                        }
                    
                    
                    //bat "git switch origin/develop"
                    //bat "git checkout origin develop"

                    String scriptlocation = "resources\\jsonOperations.ps1"
                    powerShell('pwd')
                    try{
                        println "path: ${params.sharePath} - retentiondays: ${params.retentionDays} - jsonFilePath ${jsonFilePath}"
                        
                        powerShell("${scriptlocation} ${params.sharePath} ${params.retentionDays} ${jsonFilePath}") // ${params.username} ${params.password})                         
                        
                        //bat "git add resources\\nasCleanupByRetentionDate_new.json"
                        bat "git add ."
                        bat "git commit -a -m 'test'"
                        //bat "git fetch upstream"
                        bat "git status"
                        //bat "git push -u origin HEAD"



                        //bat "git h"
                        //bat "git merge originnewJsonFileBranch"
                        
                        //bat "git merge newJsonFileBranch"
                        //bat "git branch -D newJsonFileBranch"
                        // bat "git checkout upstream/develop"
                        bat "git switch develop"
                        bat "git fetch upstream"
                        bat "git merge develop temp"

                        //bat "git checkout upstream/develop"
                        bat "git branch -D temp"
                        //bat "git merge upstream/develop develop"
                        //bat "git push upstream develop:develop"
                        //bat "git branch -D tmp"
                        //bat "git checkout upstream/develop"
                        //bat "git merge tmp"
                       

                        //bat "git switch temp"
                        //bat "git switch -c origin/newJsonFileBranch"
                       
                        /* sshagent(credentials : ['gitSSH']){
                            //bats "ssh git branch"
                            //bat ("git push -u temp:origin/newJsonFileBranch")
                            bat 'ssh -T github.com/Jan-byterider/JenkinsTest.git'
                        } */
                        
                        //bat "git merge newJsonFileBranch"

                        //bat "git remote add upstream https://github.com/Jan-byterider/JenkinsTest.git"
                        //bat "git fetch upstream" 
                        bat "git branch"
                        bat ""
                        bat "dir"  
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
    post { 
        always { 
             gitPush(gitScm: scm, targetBranch: 'develop', targetRepo: 'origin')
        }
    }      
   
}


    
    
