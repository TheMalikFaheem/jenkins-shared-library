def call(Map config = [:]) {
    String imageName = config.get('imageName', '')
    String tag = config.get('tag', 'latest')
    
    echo "🧹 Cleaning up Docker resources..."
    
    if (imageName) {
        // Remove specific image
        sh "docker rmi ${imageName}:${tag} || true"
    }
    
    // Prune dangling images (universal cleanup)
    sh "docker image prune -f"
}
