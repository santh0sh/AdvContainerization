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
</pre>

```
cd ~/openshift-july-2024
git pull
cd Day5/buildconfig

oc create -f imagestream.yml --save-config
oc get imagestreams
oc get imagestream
oc get is
oc describe is/tektutor-hello

oc create -f buildconfig.yml --save-config
oc get buildconfigs
oc get buildconfig
oc get bc

oc get builds
oc get build

oc logs -f bc/hello-bc
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/523f55cc-f2f2-45d6-b2c6-f0c0c3c642f4)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7b7e944c-bb8f-4fe6-8b17-944b66c4af1a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/43cfc91a-0900-4136-ac87-7e7743313d09)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/173af5ae-166e-455a-b3b4-9fc237ae16e7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0dbf23d9-e609-40c6-af85-fe0595e15b7d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ba5c8736-6362-423c-b503-b232b27dea6d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8ef975ad-fbfb-4fca-9097-e087f2670865)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/faf8045c-fa38-44c2-8c05-1b1217c571f2)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/302db502-c2b5-42d1-b416-90fb990941f7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1e0b20f6-a24f-4961-86f1-aa633a9004db)

## Lab - Deploying our application using the custom docker image we pushed into Openshift's Internal Container Registry
```
cd ~/openshift-july-2024
git pull
cd Day5/buildconfig

oc create deployment hello --image=default-route-openshift-image-registry.apps-crc.testing/jegan/tektutor-hello:latest --replicas=3 -o yaml --dry-run=client

oc create deployment hello --image=default-route-openshift-image-registry.apps-crc.testing/jegan/tektutor-hello:latest --replicas=3 -o yaml --dry-run=client > hello-deploy.yml

ls -l
oc create -f hello-deploy.yml --save-config
oc get po

oc expose deploy/hello -o yaml --port=8080 --dry-run=client
oc expose deploy/hello -o yaml --port=8080 --dry-run=client > hello-svc.yml
oc create -f hello-svc.yml --save-config
oc get svc
oc describe svc/hello

oc expose svc/hello -o yaml --dry-run=client
oc expose svc/hello -o yaml --dry-run=client > hello-route.yml
oc explain route

curl http://hello-jegan.apps-crc.testing
oc logs -f deploy/hello
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6e16c29a-a071-4f53-8049-091f28392d28)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/66eede62-2413-4bab-b021-fc28bfc9e1db)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1ee4aefe-90e4-4917-a1fb-97eeffb84405)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b79263f4-133e-4ea4-a9c9-05decef455af)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/90156e6b-269b-467d-8ce9-c9708e958cd0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6497558b-b9b7-4d88-ab54-82ffd6306865)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4707d5db-48c1-4b4f-ab41-59836ac51fea)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f47f4ce8-f020-4f80-9ae3-cdb72d5448d2)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/74359a27-cb71-4f90-a749-8fc9360b3155)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/38633cb0-79ad-4ea8-8fd7-b7209e13bc9e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bc9382ac-b55e-4211-96c9-90f32b020323)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fb29897b-2b02-4b45-8dc8-82fc0d86c2c1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d08d99c7-9b71-453e-a164-3e56ec11d26b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/cc2f724a-f4db-4b88-8872-4acaa578c79c)

We need to deploy mysql with tektutor database and greeting table with a single record, that is the root-cause of why the hello pods throwing exception when we access the hello microservice route.
```
oc create deployment mysql --image=bitnami/mysql:latest -o yaml --dry-run=client
oc create deployment mysql --image=bitnami/mysql:latest -o yaml --dry-run=client > mysql-deploy.yml
cat mysql-deploy.yml
```

Let's get inside the mysql container shell, when prompts for password type 'root@123' as the password without quotes
```
oc rsh deploy/mysql
mysql -u root -p
SHOW DATABASES;
USE tektutor;
CREATE TABLE greeting ( message VARCHAR(200) NOT NULL );
DESCRIBE TABLE greeting;
INSERT INTO greeting VALUES ( "Hello Microservice v1.0 !" );
SELECT * FROM greeting;
exit
exit
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/60b0e3e6-25d9-479d-85ad-d34c4334aea4)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/acff83b0-ae67-4a43-8dc3-448ad4039747)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1b311549-3f2d-453a-a7d2-db5e29fb7f15)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7c923e1b-2a1e-4b06-83cc-40ea43b47462)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/afc60d8d-2f32-48ec-9afb-1c48a58f7a97)

We need to create a mysql service as the hello microservice deployment pods will connect to the mysql pod via a service name ( ie. service discovery ).

```
oc expose deploy/mysql --port=3306 -o yaml --dry-run=client
oc expose deploy/mysql --port=3306 -o yaml --dry-run=client > mysql-svc.yml
oc create -f mysql-svc.yml --save-config
oc get svc
oc describe svc/mysql
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bcf22f6c-afaf-4c74-8e2f-f51d9339552d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/549df2ea-a13e-4024-9ab7-c41607cc4d5f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/42121ab4-1909-4837-ac5b-60c6a7c926a7)


Now, since the mysql database deployment/service with necessary table and records are in place, let's try accessing the hello route url
```
oc get route
curl http://http://hello-jegan.apps-crc.testing
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c693fd41-50a6-4ef9-8d40-04f80fe35f32)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fb2fb378-f72c-40fb-8776-5961de40cccb)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8167ba84-e5a5-45ae-ba41-aede649a722b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/97f54e3a-610d-4d69-b6df-0b67583a16ab)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6b3ad31a-8ab0-42a7-8d3a-047dc4a0e6b7)


## Lab - Build custom docker image using application source code from GitHub and pushing the image to JFrog Private Container Registry
We need to create a JFrog secret to push the image to our private JFrog Container Registry
```
oc create secret docker-registry private-jfrog-image-registry --docker-server=tektutorjuly2024.jfrog.io --docker-username=<your-jfrog-registered-email> --docker-password=<your-jfrog-token>
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6fb2e7c8-330f-4135-b908-08c288304571)

Now let's create a buildconfig that will 
<pre>
- build the application binary using cloning latest code from our training github repository
- build a custom image that will have have application binary
- push to image to our Private JFrog Artifactory using the login credentials stored in the secret
</pre>
```
cat buildconfig-push-image-to-jfrog-artifactory.yml

```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/3b79c385-91da-4865-9c35-185c35037dc6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/65f42e33-1913-4540-92c8-89e2d0e68ada)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8898a327-fde1-43bb-a621-0e099ff9cfeb)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b52ab93f-9c35-41a2-a0f1-36e972cab37b)
