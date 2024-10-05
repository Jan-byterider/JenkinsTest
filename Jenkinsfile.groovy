pipeline {
    agent any
    stages {
        stage('Hello World') {
            steps {
                script {
                    println('Hello, World')
                }
            }
        }
    }
}
