def call(Map config = [:]) {
    String target = config.target // Image name or filesystem path
    String type = config.get('type', 'image') // 'image' or 'fs'
    String severity = config.get('severity', 'CRITICAL,HIGH')
    
    echo "🛡️ Scanning ${target} for vulnerabilities (Severity: ${severity})..."
    
    // Ensure Trivy is installed on the agent or run via Docker
    if (type == 'image') {
        sh "trivy image --severity ${severity} --exit-code 1 --no-progress ${target}"
    } else {
        sh "trivy fs --severity ${severity} --exit-code 1 --no-progress ${target}"
    }
}
