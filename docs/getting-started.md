# 🚀 Getting Started with the Shared Library

This guide will help you configure your Jenkins instance to use this Shared Library and run your first pipeline.

## 1. Prerequisites

Before you begin, ensure you have:
* **Jenkins 2.x** (or newer) installed.
* **Plugins Installed**:
    * *Pipeline* (Workflow Aggregator)
    * *Git Plugin*
    * *Docker Pipeline* (for Docker operations)
    * *Pipeline Utility Steps* (for reading files/JSON)
* **Access** to the GitHub repository hosting this library.

## 2. Configure Global Library in Jenkins

You only need to do this once per Jenkins server.

1. Navigate to **Dashboard** → **Manage Jenkins** → **System**.
2. Scroll down to the **Global Pipeline Libraries** section.
3. Click the **Add** button.
4. Fill in the configuration:
    * **Name**: `jenkins-shared-library`
      *(Memorize this name; you will use it in your Jenkinsfiles)*.
    * **Default Version**: `main`
      *(This tells Jenkins which branch to use if none is specified)*.
    * **Load implicitly**: `Unchecked`
      *(We recommend keeping this unchecked so pipelines are explicit about what they load)*.
    * **Allow default version to be overridden**: `Checked`
      *(Allows you to test specific branches/tags)*.
5. Under **Retrieval Method**, select **Modern SCM**.
6. Select **Git**.
    * **Project Repository**: Enter the Git URL of *this* repository (e.g., `https://github.com/yourname/jenkins-shared-library.git`).
    * **Credentials**: Select your Git credentials if the repo is private.
7. Click **Save**.

## 3. Importing the Library

In any project `Jenkinsfile`, add this single line at the very top of the file to load the library:

```groovy
@Library('jenkins-shared-library') _
