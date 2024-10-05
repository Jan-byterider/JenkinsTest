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
                    sh "powershell scriptlocation"

                }
            }
        }
    }
}
