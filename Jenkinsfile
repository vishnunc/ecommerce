pipeline {
	agent any	
  tools { 
        maven 'MAVEN' 
	jdk 'JAVA'
	gradle 'GRADLE'
        
    }
  stages {
    stage('code pull') {
	    //agent {label 'master'}
      steps {
        checkout scm
        echo 'Git checkout complete'
      }
    }
    stage('build') {
	//agent {label 'master'}    
      steps {
         /*slackSend channel: '#jenkins',
				color: 'good',
				message: "Build Started - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)",
				baseUrl: "https://qentelli.slack.com/services/hooks/jenkins-ci/",
				token:"HFi8BU1ac67whUX4kc9Ka1Z7" */
         /*sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
		    export SONAR_RUNNER_HOME=/opt/sonarrunner
                    echo ${SONAR_RUNNER_HOME}
                ''' */
	      //bat 'set JAVA_HOME = "C:\Program Files\Java\jdk1.8.0_181"'
         bat 'mvn clean compile'
      }
    }
    stage('test') {
	// agent {label 'master'}   
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
      bat 'mvn sonar:sonar'
    }
          },
          "unit tests": {
           bat 'mvn test'
            
          }
        )
      }
    }
    stage('dev-deploy') {
	//agent {label 'qa'}   
      steps {
        script{
        	withEnv(['JENKINS_NODE_COOKIE=dontKill']){
        		
        		bat 'start runapp.bat'
        		
        	}
        }
      }
    }
    stage('dev test') {
	//agent {label 'qa'}   
      steps {
	parallel(
		"smoke test":{
        bat 'git clone https://github.com/vishnunc/ecommerce-uitests.git ecommerce-smoke-uitests'
        bat 'cd ecommerce-smoke-uitests && ./gradlew cucumber -Pfeatures=src/test/resources/gradle/cucumber/smoke report --continue'
	},
	"api test":{
	bat 'git clone https://github.com/vishnunc/ecommerce-apitests.git ecommerce-apitests'
        bat 'cd ecommerce-smoke-uitests && ./gradlew cucumber -Pfeatures=src/test/resources/gradle/cucumber/smoke report --continue'
	}) 
      }
    }
    stage('qa-deploy') {
	//agent {label 'qa'} 
      steps {
        
        bat 'mvn package'
      }
    }
    stage('ui tests') {
	//agent {label 'qa'}
      steps {
        bat 'rm -rf ecommerce-uitests'
        bat 'git clone https://github.com/vishnunc/ecommerce-uitests.git'
       
        bat 'cd ecommerce-uitests && ./gradlew cucumber -Pfeatures=src/test/resources/gradle/cucumber report --continue'
        
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
