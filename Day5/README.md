# Day 5

## Info - S2I (Source to Image)
<pre>
- Normally Container Orchestration Platform like Docker SWARM, Kubernetes requires  a readily available container image to deploy the application into Container Orchestration Platform
- In case of Openshift, the S2I feature takes the Version Control url to clone your application source code, 
  - For Docker strategy 
    - GitHub repository url and branch to clone your source code
    - it will look for Dockerfile/Containerfile
  - For Source strategy
    - GitHub repository url and branch to clone your source code
    - it will look for Container Image that can be used to build your application
- Then it start the application build to get the application binary
- With the application binary, it then creates a Custom Container Image
- Which an be pushed either to Openshift Internal Container Registry or you can push it to Private Container Registry like JFrog Artifactory, Docker Hub or Sonatype Nexus.
</pre>  

## Lab - BuildConfig

<pre>
- The BuildConfig has build instructions
- For each Build, openshift creates an instance of BuildConfig called Build
- For each Build, one Pod will be created
- Within the Pod container, the build activity will happen
- the Pod container image must have all the tools required to perform your application build
- In case your application happens to be java application, to build it you will need a container image that has maven build and appropriate version of JDK
- In case your application happens to be a python application, to build it you will need a container image that has python version as per your application code
- 
</pre>
