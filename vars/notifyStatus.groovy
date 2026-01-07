def call(String buildStatus = 'SUCCESS') {
    // Current build status (default to SUCCESS if null)
    buildStatus = buildStatus ?: 'SUCCESS'
    
    String colorCode = '#00FF00' // Green
    String icon = '✅'
    
    if (buildStatus != 'SUCCESS') {
        colorCode = '#FF0000' // Red
        icon = '❌'
    }

    echo "${icon} Build Status: ${buildStatus}"

    // Example: Send Slack Notification (Requires Slack Plugin)
    /*
    slackSend (
        color: colorCode,
        message: "${icon} Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${buildStatus}).\nCheck console: ${env.BUILD_URL}"
    )
    */
    
    // Example: Send Email (Requires Email Extension Plugin)
    /*
    emailext (
        subject: "${icon} Build ${buildStatus}: ${env.JOB_NAME}",
        body: "See details at ${env.BUILD_URL}",
        to: 'devops@example.com'
    )
    */
}
