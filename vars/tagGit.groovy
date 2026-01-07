def call(Map config = [:]) {
    String tagName = config.tagName
    String message = config.get('message', "Release ${tagName}")
    String credentialsId = config.credentialsId

    echo "🏷️ Git Tagging: ${tagName}"

    withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'GIT_PASS', usernameVariable: 'GIT_USER')]) {
        sh """
            git config user.email "jenkins@yourcompany.com"
            git config user.name "Jenkins CI"
            git tag -a ${tagName} -m "${message}"
            git push https://${GIT_USER}:${GIT_PASS}@github.com/your-org/your-repo.git ${tagName}
        """
    }
}
