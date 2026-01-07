# 🌟 Best Practices

To ensure your pipelines are stable, secure, and maintainable, please follow these guidelines when using or contributing to this library.

## 1. Pipeline Stability

### 🔒 Pin Your Versions
In a production environment, **never** load the library from the `main` branch. If the library code changes, your production pipeline might break unexpectedly.

* **Risky:** `@Library('jenkins-shared-library') _` (Defaults to main)
* **Safe:** `@Library('jenkins-shared-library@v1.2') _` (Locks to version 1.2)

*Tip: Create git tags (v1.0, v1.1) in this repo for stable releases.*

## 2. Resource Management

### 🧹 Clean Up Docker Images
Docker images take up massive amounts of disk space. If you don't clean them, the Jenkins agent will crash.
* **Rule:** Always call `cleanupDocker()` in the `post { always { ... } }` block.
* This ensures cleanup happens even if the build fails.

## 3. Security

### 🔑 Credentials Management
* **Never** hardcode passwords or API keys in the code.
* **Never** print secrets to the console log using `echo`.
* Always use `withCredentials` (as seen in `dockerLogin.groovy`) to handle secrets securely.

### 🛡️ Vulnerability Scanning
Integrate `scanVulnerabilities` into your workflow. It is better to fail a build than to deploy a container with known security holes.

## 4. Development Guidelines

### Use Map Parameters
When creating new scripts in `vars/`, avoid positional arguments. Use a Map (`config`) instead.
* **Bad:** `def call(String name, String version) { ... }`
  *(Hard to read if you have 5 arguments)*
* **Good:** `def call(Map config) { ... }`
  *(Usage: `myStep(name: 'foo', version: '1.0')` - readable and order doesn't matter)*.

### Keep `vars` Simple
The files in `vars/` should be simple wrappers. If you are writing complex `if/else` logic or data processing, move that code to a Class in the `src/` folder and call it from `vars`.
