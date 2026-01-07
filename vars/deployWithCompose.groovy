def call(Map config = [:]) {
    String projectName = config.get('projectName', 'jenkins-deployment')
    String envFile = config.get('envFile', '.env') // Path to .env file
    
    // Determine which Compose file to use
    String composeFile = 'docker-compose.yml'
    
    if (fileExists(composeFile)) {
        echo "📂 Using local docker-compose.yml found in workspace."
    } else {
        echo "📂 Local compose file not found. Loading template from Shared Library resources..."
        def composeContent = libraryResource 'docker-compose.yml'
        writeFile file: 'docker-compose.yml', text: composeContent
    }

    echo "🚀 Deploying with Docker Compose (Project: ${projectName})..."
    
    // Ensure env file exists or creating empty one to prevent errors
    if (!fileExists(envFile)) {
        sh "touch ${envFile}"
    }

    sh """
        docker compose -p ${projectName} --env-file ${envFile} down --remove-orphans
        docker compose -p ${projectName} --env-file ${envFile} up -d
    """
}
