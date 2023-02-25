pipeline {
    agent any
    triggers { pollSCM('* * * * *') }


    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yassineKhouaja/jgsu-spring-petclinic.git'
            }   
        }
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                // git branch: 'main', url: 'https://github.com/yassineKhouaja/jgsu-spring-petclinic.git'
                // Run Maven on a Unix agent.
                 sh "./mvnw clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
        }
        post {
            always {
                emailext attachLog: true, 
                body: 'pealse go to ${BUILD_URL} and verify the build', 
                compressLog: true, 
                subject: 'job ${JOB_NAME} ${BUILD_NUMBER} is waiting for input', 
                to: 'yassinekhouaja@gmail.com'
            }
        }
    }
}
