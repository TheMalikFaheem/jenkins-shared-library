def call(Map config = [:]) {
    String imageName = config.imageName
    String tag = config.get('tag', 'latest')
    String dockerfile = config.get('dockerfile', 'Dockerfile')
    String context = config.get('context', '.')
    
    // Optional: Add build args if provided in config
    String buildArgs = config.get('buildArgs', '') // Pass as "--build-arg KEY=VAL"

    echo "🏗️ Building Docker image: ${imageName}:${tag}"
    
    sh """
        docker build ${buildArgs} -f ${dockerfile} -t ${imageName}:${tag} ${context}
    """
    
    // Return the full image string for use in later steps
    return "${imageName}:${tag}"
}
