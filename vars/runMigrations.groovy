def call(Map config = [:]) {
    String containerName = config.containerName
    String command = config.get('command', 'python manage.py migrate')
    
    echo "🛠️ Running migrations on container: ${containerName}"
    
    // Check if container is running first
    def isRunning = sh(script: "docker ps -q -f name=${containerName}", returnStdout: true).trim()
    
    if (isRunning) {
        sh "docker exec ${containerName} ${command}"
    } else {
        error "❌ Container ${containerName} is not running. Cannot run migrations."
    }
}
