pipeline {
    agent any
    stages {
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
