def call(Map config = [:]) {
    String imageName = config.imageName
    String tag = config.get('tag', 'latest')

    echo "🚀 Pushing Docker image: ${imageName}:${tag}"
    
    sh "docker push ${imageName}:${tag}"
}
