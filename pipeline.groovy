Jenkinsfile (Declarative Pipeline)
pipeline{
	agent any
	stages{
		stage('build'){
			steps{
				echo 'blah'
				slackSend channel: '#jenkins',
				color: 'good',
				message: "The pipeline completed successfully.",
				baseUrl: "https://qentelli.slack.com/services/hooks/jenkins-ci/",
				token:"HFi8BU1ac67whUX4kc9Ka1Z7"
				
			}
			
		}
		stage('test'){
			steps{
				echo 'blah'
			}
			
		}
		stage('qa deploy'){
			steps{
				echo 'blah'
			}
			
		}
		stage('staging deploy'){
			steps{
				echo 'blah'
			}
			
			
			aaa
		}
	}
}