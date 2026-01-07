# 🚀 Jenkins Shared Library

Welcome to the **Jenkins Shared Library** repository. This project hosts reusable CI/CD logic, standardizing how we build, test, and deploy applications across our organization.

By using this library, you can replace hundreds of lines of complex `Jenkinsfile` code with simple, one-line steps.

---

## 📚 Documentation & Guides

Detailed documentation for setup and usage is located in the `docs/` directory.

| Topic | Description | Link |
| :--- | :--- | :--- |
| **⚡ Getting Started** | How to configure Jenkins to use this library. | [Read Guide](docs/getting-started.md) |
| **📂 Architecture** | Explanation of the folder structure (`vars`, `src`, etc). | [Read Guide](folder-structure.md) |
| **📝 Examples** | Copy-paste `Jenkinsfile` templates for different projects. | [Read Guide](docs/example-jenkinsfile.md) |
| **⭐ Best Practices** | Tips on versioning, security, and stability. | [Read Guide](docs/best-practices.md) |

---

## ⚡ Quick Usage

Once [configured in Jenkins](docs/getting-started.md), you can use the library in your pipeline by adding the `@Library` annotation at the top.

### Example: Build, Push, and Deploy

```groovy
@Library('jenkins-shared-library') _

pipeline {
    agent any
    
    environment {
        APP_NAME = "my-app"
        VERSION  = "v${env.BUILD_NUMBER}"
        REGISTRY = "myuser"
    }

    stages {
        stage('📥 Checkout') {
            steps {
                gitCheckout(url: '[https://github.com/TheMalikFaheem/django-notes-app.git](https://github.com/TheMalikFaheem/django-notes-app.git)')
            }
        }

        stage('🏗️ Build & Push') {
            steps {
                // Login to Docker Hub
                dockerLogin(credentialsId: 'docker-hub-creds')
                
                // Build and Push
                buildDockerImage(imageName: "${REGISTRY}/${APP_NAME}", tag: VERSION)
                pushDockerImage(imageName: "${REGISTRY}/${APP_NAME}", tag: VERSION)
            }
        }

        stage('🚀 Deploy') {
            steps {
                // Deploy using Docker Compose
                deployWithCompose(projectName: APP_NAME, envFile: '.env.prod')
                
                // Run Database Migrations
                runMigrations(
                    containerName: "${APP_NAME}-backend-1",
                    command: "python manage.py migrate"
                )
            }
        }
    }
    
    post {
        always {
            cleanupDocker(imageName: "${REGISTRY}/${APP_NAME}", tag: VERSION)
        }
    }
}


## 📂 Repository Structure

- **vars/**  
  Contains the global pipeline steps (e.g., `buildDockerImage`, `deployWithCompose`) that are used directly inside Jenkins pipelines.

- **src/**  
  Contains helper classes and backend logic used internally by the scripts in `vars/`.

- **resources/**  
  Contains static templates (such as `docker-compose.yml`) and reusable shell scripts.

- **docs/**  
  Contains detailed documentation for developers and maintainers.

---

## ⚙️ Installation

1. Go to **Manage Jenkins → System → Global Pipeline Libraries**
2. Add a new library with the following details:

   - **Name**: `jenkins-shared-library`  
   - **Default Version**: `main`  
   - **Project Repository**:  
     ```
     https://github.com/TheMalikFaheem/jenkins-shared-library.git
     ```

---

## 🤝 Contributing

1. Fork the repository  
2. Create a feature branch (example: `feature/add-k8s-support`)  
3. Add your pipeline step to `vars/` or shared logic to `src/`  
4. Submit a Pull Request targeting the `main` branch  

---

## 📦 Version

Current version information is available in the `VERSION` file.

---

## 👨‍💻 Author

**Malik Faheem Ahmad**

---

## 📄 License

This project is licensed under the **MIT License**.
