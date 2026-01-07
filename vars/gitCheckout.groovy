def call(Map config = [:]) {
    String branch = config.get('branch', 'main')
    String url = config.url
    String credentialsId = config.get('credentialsId', '') // Optional for public repos
    boolean useSubmodules = config.get('useSubmodules', false)

    echo "⬇️ Checking out code from ${url} (Branch: ${branch})..."
    
    checkout([
        $class: 'GitSCM', 
        branches: [[name: branch]], 
        userRemoteConfigs: [[
            url: url,
            credentialsId: credentialsId
        ]],
        extensions: useSubmodules ? [[$class: 'SubmoduleOption', recursiveSubmodules: true]] : []
    ])
}
