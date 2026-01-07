def call(Map config = [:]) {
    // Default to Docker Hub if registry is not provided
    String registry = config.get('registry', '') 
    String credentialsId = config.get('credentialsId', 'docker-hub-credentials')

    echo "🔐 Logging into Docker Registry..."
    
    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        if (registry) {
            sh "echo $PASS | docker login $registry -u $USER --password-stdin"
        } else {
            sh "echo $PASS | docker login -u $USER --password-stdin"
        }
    }
}
