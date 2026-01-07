def call(Map config = [:]) {
    String containerName = config.containerName // Name of the running DB container
    String dbUser = config.get('dbUser', 'postgres')
    String dbName = config.get('dbName', 'myapp_db')
    String backupPath = config.get('backupPath', './backups')
    
    String timestamp = new Date().format("yyyyMMdd-HHmmss", TimeZone.getTimeZone('UTC'))
    String filename = "${backupPath}/${dbName}_backup_${timestamp}.sql"

    echo "💾 Backing up database ${dbName} from ${containerName}..."

    sh "mkdir -p ${backupPath}"
    
    // Example for PostgreSQL (Adjust command for MySQL/Mongo)
    sh """
        docker exec -t ${containerName} pg_dump -U ${dbUser} ${dbName} > ${filename}
    """
    
    echo "✅ Backup saved to: ${filename}"
    
    // Optional: Archive artifacts in Jenkins to save the file
    archiveArtifacts artifacts: filename
}
