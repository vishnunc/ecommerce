pipeline {
	agent { label 'qa' }
  tools { 
        maven 'MAVEN3.5.4' 
        
    }
  triggers{
  	pollSCM '* * * * *'
  }
  stages {
    stage('code pull') {
      steps {
        checkout scm
        echo 'Git checkout complete'
      }
    }
    stage('build') {
      steps {
         /*slackSend channel: '#jenkins',
				color: 'good',
				message: "Build Started - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)",
				baseUrl: "https://qentelli.slack.com/services/hooks/jenkins-ci/",
				token:"HFi8BU1ac67whUX4kc9Ka1Z7" */
         sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
		    export SONAR_RUNNER_HOME=/opt/sonarrunner
                    echo ${SONAR_RUNNER_HOME}
                ''' 
         sh 'mvn clean compile'
      }
    }
    stage('test') {
      steps {
        parallel(
          "code analyze": {
           /* tool name: 'SonarRunner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
            withSonarQubeEnv('Sonar') { // from SonarQube servers > name
    		//sh "${HOME}/.jenkins/tools/hudson.plugins.sonar.SonarRunnerInstallation/Sonar/bin/sonar-runner -Dsonar.projectName=ecommerce -Dsonar.projectVersion=1.0 -Dsonar.projectKey=ecommerce -Dsonar.sources=src/main/java -Dsonar.java.binaries=target/classes"
             sh "${SONAR_RUNNER_HOME}/bin/sonar-runner -Dsonar.projectName=ecommerce -Dsonar.projectVersion=1.0 -Dsonar.projectKey=ecommerce -Dsonar.sources=src/main/java -Dsonar.java.binaries=target/classes"		    
            }*/
		  withSonarQubeEnv('Sonar') {
      // requires SonarQube Scanner for Maven 3.2+
      sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
    }
          },
          "unit tests": {
           sh 'mvn test'
            
          }
        )
      }
    }
    stage('dev-deploy') {
      steps {
        script{
        	withEnv(['JENKINS_NODE_COOKIE=dontKill']){
        		
        		sh 'nohup mvn tomcat7:run-war &'
        		sh 'sleep 10'
        	}
        }
      }
    }
    stage('smoke test') {
      steps {
        sh 'rm -rf ecommerce-smoke-uitests'
        sh 'git clone https://github.com/vishnunc/ecommerce-uitests.git ecommerce-smoke-uitests'
        sh 'cd ecommerce-smoke-uitests && ./gradlew cucumber -Pfeatures=src/test/resources/gradle/cucumber/smoke report --continue'
        
      }
    }
    stage('qa-deploy') {
      steps {
        
        sh 'mvn package'
      }
    }
    stage('ui tests') {
      steps {
        sh 'rm -rf ecommerce-uitests'
        sh 'git clone https://github.com/vishnunc/ecommerce-uitests.git'
       
        sh 'cd ecommerce-uitests && ./gradlew cucumber -Pfeatures=src/test/resources/gradle/cucumber report --continue'
        
      }
    }
 
  }
  post{
  	always{
  		slackSend channel: '#jenkins',
				color: 'good',
				message: "The pipeline completed ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)",
				baseUrl: "https://qentelli.slack.com/services/hooks/jenkins-ci/",
				token:"HFi8BU1ac67whUX4kc9Ka1Z7"
  	 publishHTML (target: [
      allowMissing: false,
      alwaysLinkToLastBuild: false,
      keepAll: true,
      reportDir: 'ecommerce-smoke-uitests/target/cucumber-html-reports',
      reportFiles: 'overview-features.html',
      reportName: "Smoke Test Report"
    ])
    
    publishHTML (target: [
      allowMissing: false,
      alwaysLinkToLastBuild: false,
      keepAll: true,
      reportDir: 'ecommerce-uitests/target/cucumber-html-reports',
      reportFiles: 'overview-features.html',
      reportName: "Regression Test Report"
    ])
    
    logstashSend failBuild: true, maxLines: 100000
  	}
  }
}
