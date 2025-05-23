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

## Info - Openshift Build Strategies
<pre>
- Openshift support many different type of build strategies lke
  1. docker
  2. source
  3. custom
  4. pipeline
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
oc create -f buildconfig-push-image-to-jfrog-artifactory.yml --save-config
oc get bc
oc get builds
oc logs -f bc/hello-jfrog-bc
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/3b79c385-91da-4865-9c35-185c35037dc6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/65f42e33-1913-4540-92c8-89e2d0e68ada)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8898a327-fde1-43bb-a621-0e099ff9cfeb)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b52ab93f-9c35-41a2-a0f1-36e972cab37b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/2eaea2fb-0988-4da5-8af1-8ad93164e6a1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b1a4f43c-ff37-4769-803b-ae541d0b5c60)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/79a23316-c043-461c-88bb-5c8ec6413644)

## Lab - Using source strategy to deploy an application from GitHub source code
```
oc new-app --name=hello registry.access.redhat.com/ubi8/openjdk-11~https://github.com/tektutor/openshift-july-2024.git --context-dir=Day5/spring-ms --strategy=source
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7b89814a-a3ef-487f-950f-03d9c53d62b9)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7193279a-e2c3-4c32-a256-f3f652b1c8bc)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0acd6efa-b700-4959-8724-01f7cd813562)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b5a1a8ba-7164-44fd-9991-5899909d99ec)

## Info - Knative and Red Hat Serverless Operator
<pre>
- Red Hat Serverless operator is based on Knative opensource project
- Knative provides a serverless application layer on top of OpenShift/Kubernetes
- the term Knative means Kubernative native
- Knative is Kubernetes Serverless Framework
- Knative Framework consists of 3 build blocks
  - Function
  - Eventing
  - Serving
</pre>

## Info - What does Serverless mean?
<pre>
- serverles doesn't mean the absence of servers
- is an architecture model for running applications in an environment that is abstracted away from developers
- developers can focus more on developing their application, leaving openshift to deal with where their code/application runs
- an ideal serverless workload executes a single task
- a function that retrieves data from a database can be an excellent serverless workload
- when using serverless, there is a period between the request and creating the Pod environment.
- This period is called cold start
- Examples
  - Openshift serverless workloads follows this workflow
    - a request comes in
    - a pod is spun up to service the request
    - the Pod serves the request
    - the Pod is destroyed when there is no user traffic to handle ( i.e scaled down to zero )
    - your service will be scaled down all the way upto 0 Pod where is zero requests
    - your service will be automatically scaled up when there is a request
  - Other examples of a serverless workload can be an image processing function
    - an event could be a phot upload, the uploaded photo triggers an event to run an application to process the image
    - For example, the application may overlay text, create a banner, or make thumnails
    - Once the image i stored permanently, the application has served its purpose and is no longer needed
</pre>

## Info - Serverless Features
<pre>
- Stateless Function
  - a function to query a database and return the data
  - a function to query weather report and return the data
- Event Driven
  - a serverless model relies on a trigger to execute the code
  - could be a request to an API or an event on a queue
- Auto scales to zero
  - Being able to scale to zero means your code only runs when it needs to respond to an event
  - once the request is served, resources are released
</pre>

## Lab - Deploying your first knative service
```
kn service create hello \
--image ghcr.io/knative/helloworld-go:latest \
--port 8080 \
--env TARGET=World
```

Accessing the knative application from command line
```
curl -k https://hello-jegan-serverless.apps.crc-testing
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/413b5878-328f-4abe-9f01-9cca99c6d48c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7a5226ea-f91a-4e94-90d3-cdc793f7bb00)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6d76a2a8-2343-4fd5-83a0-9b1727bbc5bc)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/34cf21d5-20ef-4ea8-b2ea-de45cde4643f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/33d33292-968f-4b23-9654-971e7700f883)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/44567fe4-2260-45a2-bcbf-563ff501fa8d)

Update the service ( Rolling update )
```
kn service update hello --env TARGET=Knative!
kn revisions list
```

Accesing the knative application from command line
```
curl -k https://hello-jegan.apps-crc.testing
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f659d217-73d3-4d7b-8512-fdcae5eac0b6)

Splitting the traffic between two revisions ( something similar to canary deployment )
```
kn service update hello --traffic hello-00001=50 --traffic @latest=50
kn revisions list
```
Accesing the knative application from command line
```
curl -k https://hello-jegan.apps-crc.testing
curl -k https://hello-jegan.apps-crc.testing
curl -k https://hello-jegan.apps-crc.testing
curl -k https://hello-jegan.apps-crc.testing
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/08aedcce-42e9-42bb-8b7a-5d500d90fb08)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7d899b0f-f853-4d5d-8cb7-f998d7d89604)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d8e3f67d-6348-4156-94e8-c202021db2e6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/53391c21-2cee-41d3-90dd-7bc7bb0d124f)

Delete the knative service
```
kn service list
kn service delete hello
kn service list
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1f2f1a36-785d-45ff-a342-88b67c5705a5)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4df75591-3816-4feb-94fe-92c940ed7b40)

## Lab - Knative eventing

Let's deploy a sink service
```
oc project jegan-eventing
kn service create eventinghello --image=quay.io/rhdevelopers/eventinghello:0.0.2
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ae2fddf9-35ff-4512-bca6-d855e747f7f3)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9c539bf8-2e8b-4602-8048-82e37978c7e4)

Let's create an event source application
```
kn source ping create eventinghello-ping-source --schedule="*/2 * * * *" --data '{"message": "Scale up"}' --sink ksvc:eventinghello
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d4db3aae-5235-4591-9be9-9cb428bb9269)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/330ad9e5-2c8d-4486-a823-ea88e5c7842a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/cf035796-8bf6-4e88-b36b-b7e3da0c182f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f2437c1e-ee83-4a16-9c85-636c0137cf18)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/21188a83-89a1-4085-a315-760fb205d1b2)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/24b5e139-49d4-4982-aacd-a860e11e7db0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bebd34f2-8062-40b4-9346-6f9a99574bf9)

## Lab - Developing a simple knative function in nodejs and deploying into Openshift

This will generate a basic nodejs application in your current directory
```
oc new-project jegan-faas
kn func create -l node
```
If you wish to build your appliction
```
kn func build
```

If you wish to run the application locally and test i
```
kn func run
```

Deploy the nodejs application into openshift after building it
```
kn func deploy -n jegan-faas
```
Test the knative function
```
curl -k https://
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ad57cba6-c221-4879-9aa6-d7c6c8b69f94)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/af45c421-357d-4bd0-92a3-3a55c408f316)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5dcaeec8-b362-4cdb-bac4-401d3a3b13c4)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bbe42e64-2d68-4c80-8542-96673eba11c0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/54e9dee8-32f6-4e2f-8250-f62198f7c236)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e63b1e31-4cc5-422d-89ee-ebce507b644b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/15c37b4b-cc06-471d-9400-58d2bb930b8f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5c30f3a0-555c-4d57-b682-7c79097245a3)

## Request for your feedback ( Using RPS Lab machine )
<pre>
https://survey.zohopublic.com/zs/KI0DHJ  
</pre>

## Post test link ( Using RPS Lab machine )
<pre>
https://rpsconsulting116.examly.io/contest/publicU2FsdGVkX1/JCYJ6pgzhyRDN6KxFZZVDm8PAO5DQ1bAAZsl8h8W19j5WVwJ9CvyMbq6DIOfxTZ41/oNdinEYhg==  
</pre>

