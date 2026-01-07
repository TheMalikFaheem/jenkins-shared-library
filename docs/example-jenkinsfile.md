# 📝 Example Jenkinsfiles

This document provides copy-paste templates for common pipeline scenarios.

## Scenario A: Simple CI (Build & Push)
*Use this for backend APIs or microservices that need to be containerized and pushed to a registry.*

```groovy
@Library('jenkins-shared-library') _

pipeline {
    agent any
    
    environment {
        // Define your image name
        IMAGE_NAME = "myorg/my-service"
        // Use build number as the tag
        TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Uses vars/gitCheckout.groovy
                gitCheckout(
                    url: '[https://github.com/myorg/my-service.git](https://github.com/myorg/my-service.git)',
                    branch: 'main'
                )
            }
        }

        stage('Build & Push') {
            steps {
                // Login to Docker Hub
                dockerLogin(credentialsId: 'docker-hub-creds')
                
                // Build the image
                buildDockerImage(imageName: IMAGE_NAME, tag: TAG)
                
                // Security Scan (Fail build if Critical bugs found)
                scanVulnerabilities(target: "${IMAGE_NAME}:${TAG}")
                
                // Push to registry
                pushDockerImage(imageName: IMAGE_NAME, tag: TAG)
            }
        }
    }
    
    post {
        always {
            // Clean up disk space
            cleanupDocker(imageName: IMAGE_NAME, tag: TAG)
            notifyStatus(currentBuild.result)
        }
    }
}
