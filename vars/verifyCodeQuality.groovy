def call(Map config = [:]) {
    String tool = config.get('tool', 'sonar') // 'sonar' or 'generic'
    String command = config.get('command', 'npm run lint') // Used if tool is 'generic'
    
    echo "🔍 Starting Code Quality Check using ${tool}..."

    if (tool == 'sonar') {
        // Requires SonarQube Plugin installed and configured in Jenkins
        withSonarQubeEnv('MySonarServer') {
            sh 'mvn sonar:sonar' // Or sonar-scanner
        }
        // Optional: Wait for Quality Gate
        timeout(time: 5, unit: 'MINUTES') {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                error "❌ Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    } else {
        // Generic linter (npm, flake8, pylint, etc.)
        sh command
    }
}
