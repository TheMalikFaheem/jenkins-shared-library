def call(Map config = [:]) {
    String imageName = config.imageName
    String sourceTag = config.get('sourceTag', 'dev') // e.g., build-123
    String targetTag = config.get('targetTag', 'prod') // e.g., release-v1.0
    
    echo "📦 Promoting image ${imageName} from '${sourceTag}' to '${targetTag}'..."

    sh """
        docker pull ${imageName}:${sourceTag}
        docker tag ${imageName}:${sourceTag} ${imageName}:${targetTag}
        docker push ${imageName}:${targetTag}
    """
}
