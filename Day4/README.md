# Day 4

## Info - ReplicationController
<pre>
- Using ReplicationController one can deploy stateless applications
- This was a older feature from Kubernetes
- ReplicationController supports 2 functionalities
  1. Scale up/down
  2. Rolling update
- ReplicationController also doesn't support declaratively performing scaled up/down, rolling update etc.,
- As per SOLID Design Principle
  S - Single Responsibility Principle (SRP)
  O - Open Closed Principle (OCP)
  L - Liskov Substitution Principle (LSP)
  I - Interface Seggregation
  D - Dependency Injection or Dependency Inversion or Inversion of Control (IOC)
- To support declarative approach, Red Hat Openshift team added DeploymentConfig which internally makes use of ReplicationController
- Later, Kubernetes refactored ReplicationController into Deployment and ReplicaSet
- Deployment
  - supports rolling update
- ReplicaSet
  - supports scale up/down
- Any new application deployment should consider using Deployment & ReplicaSet over ReplicationController
- When Kubernetes introduced Deployment & ReplicaSet, Red Hat openshift team deprected use of DeploymentConfig as pretty much Deployment and DeploymentConfig does the same
- Hence, we should always consider using Deployment over ReplicationController and DeploymentConfig
</pre>

## Info - Deployment vs DaemonSet
<pre>
- Deployment resource is managed by Deployment Controller
- DaemonSet resource is managed by DaemonSet Controller
- It is possible to scale up/down manually and automatically a application deployed using Deployment
- DaemonSet, we can't scale up/down manually, because the DaemonSet Controller automically detects the number of nodes in the openshift cluster and creates so many Pod matching the number of Nodes, and DaemonSetController ensure one Pod runs in each node
- Deployment Controller ensures the desired number of Pods always runs in the cluster, however it doesn't put any contraint on which nodes those Pods are to be scheduled, it is totally left to scheduler
- DaemonSet is usesful in very special cases like
  - Prometheus - which is known for collecting performance metrics on the node level as well as on the application(Pod) level
  - Hence it is necessary that one Prometheus Pod runs in every node, hence it can be deployed as DaemonSet
  - kube-proxy - which support load-balancing of Pods that are represented by service
  - One kube-proxy Pod runs in every node, this could be deployed as a DaemonSet
  - DNS - which helps in Service Discovery has one Pod running in every node.  This is necessary to resolve the service name to its respective service IP on every node level.  This could be a DaemonSet.
</pre>

## Info - StatefulSet
<pre>
- Initially when Kubernetes was released it was supporting only Stateless applications, which is supported by Deployment
- Later, they saw a need for some Controller that also supports deploying Stateful applications like Databases
- Just like StatefulSet, Deployment also support Persistent Volume, so what exactly is the difference between these two?
  - When we deploy database applications as a Deployment with Persistent Volume with multiple Pod replicas, they don't run a cluster of databases
  - But StatefulSet has an option to create a cluster of databases
  - Just by deploying an application as a statefulset won't make then run as master and slave, we need to explicitly configure then either via ConfigMap or initContainers, PV/PVC or all of them in combination dependending on the appliction type deployed as Statefulset
  - Each Pod created by StatefulSet controller has an unique Pod identifier, which is also stable
  - the order in which the Pod are started also is guaranteed, assume we are deploying mysql as a statefulset with 3 Pods
    - mysql-0 Pod will be created first
    - mysql-1 Pod will only be created after the mysql-0 Pod starts running
    - mysql-2 Pod will only be created after the mysql-1 Pod starts running
</pre>

## Lab - Creating a one-time job
```
cd ~/openshift-july-2024
git pull
cd Day4/job
oc create -f job.yml --save-config
oc get jobs
oc get po 
oc logs -f hello-job-55j85 
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/dbdb2716-b724-4483-975c-bd23625f0cf0)


## Lab - Scheduling  a recurring job that runs at a specific time
```
cd ~/openshift-july-2024
git pull
cd Day4/cronjob
oc create -f cronjob.yml --save-config
oc get cronjobs
oc get po
oc logs -f 
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d3ba1903-b5e3-470a-b87e-a72ec846c089)


## Info - Helm Overview
<pre>
- Just like we have package managers in Linux distributions
  - apt-get in Ubuntu
  - yum, dnf,rpm in RHEL based Linux distributions
- Helm is a Package manager to install/uninstall/upgrade applications in Kubernetes/Openshift
- Helm also depends on the kube config just like the oc and kubectl client tool
- helm has to be installed just like oc and kubectl
- instead of we deploying the yaml files in a specific order while deploying and reverse while deleting, we can package all the yaml files in a Helm recommended directory structure and compress as a file.  The compressed file is called Helm chart.
- Helm charts can then be distributed to your customers, who can install/uninstall/upgrade the complex application using helm
- Helm charts are also available in the 
</pre>

## Lab - Creating custom helm chart (package) for wordpress/mariadb multi-pod application
```
cd ~/openshift-july-2024
git pull
cd Day4/helm
helm version
helm create wordpress
tree wordpress
cd wordpress/templates
rm -rf *
cd ../..
cp values.yaml wordpress
cp scripts/*.yml wordpress/templates
tree wordpress
helm package wordpress
ls -l
oc delete project jegan
oc new-project jegan
helm install wordpress wordpress-0.1.0.tgz
helm list
oc get deploy,rs,po,svc,route,pv,pvc
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9710cfaa-ad40-49c9-b8e9-5ba6183533ae)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fbde22e0-a8a7-4af2-8b78-6255a791c9fc)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/17dfc30e-bc4b-47af-9b3b-bf5cc4b4bf9e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7518b244-fa54-4485-8279-fa12c17ba234)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/97e6b74a-3fba-4e0a-8951-3eff0d667f91)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e65c79a4-c82c-4853-9fa0-023bb2f3094b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b87bf053-ea19-4d6b-af5a-2c87fdcae7ba)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fb388d21-1a4d-4c39-9548-fef22e426930)

![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/be5868c2-7b01-4627-a2a7-e7af72407128)


Once you are done with this exercise, you can uninstall wordpress using helm package manager tool
```
helm list
helm uninstall wordpress
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9d76d9f5-b940-4080-8274-1e04736da11f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fd4a1672-b649-4a05-86e3-6c9b28a51c38)

## Lab - Deploying Java - Springboot application from Openshift web console
Open the Openshift webconsole --> Developer context
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fdf153d9-d6ba-40a2-883e-7b469d7c0e26)
Click on +Add
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f6d537c5-4feb-43e9-bc5a-fac079676ac0)
Under Getting started resources, click "View all sample"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4c66de6f-5f87-4052-9ed8-35920291f0cd)
Select "Basic Spring Boot"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fb0c1428-2112-4f5e-ba05-cc49416e15e6)
Click on "Create" button
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1fa8c5a9-ef1d-4571-bf6d-81b4b625c1a1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/efbcc46f-deca-4d0a-8d1c-6b1d0fba74e6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ac27b913-0be4-471d-b247-4b0d48c7a01d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7f5c55da-9246-46af-98ca-d78e267293cd)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/687d06b7-4fff-4136-b79d-050c8c2ceed7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ed05338d-0366-4b7c-8f45-30b00a0a0a56)
Click on Advanced button
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/52ea7420-49ee-456a-92fa-ce4008bb2659)
Click on Proceed
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0b875948-ee98-4667-b212-d3f9de4c425f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/97df8183-60b3-4600-9a0c-96cda6858124)

To delete the application from Openshift web console, In the Developer context --> Topology --> Right click on the application
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/29bd9700-f3cf-42bd-a7a1-b63903bbc4cc)
Click on "Delete application"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/781cf574-cee9-455e-ae34-7aeaa9eccd2d)
Type "sample-app" and click on Delete
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5966014a-b551-489c-9c5e-5a9bdd43192d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ac5c1f7d-280e-4150-871c-38839da29c00)

## Lab - Deploying .Net application from Openshift web console using Developer context
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ba0614d5-ed28-4395-a37b-7bdefe83e86d)
Click on +Add
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b00a060f-e34d-4ec0-b36d-8eb19b2bd455)
Under Getting Started resources, click on "view all samples"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f8f0177b-5fd5-4e17-8cb3-8471caf1ea1c)
Click on "Basic .Net"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4528354e-d2c8-480b-a2dc-a5ac247ca551)
Click on "create" button
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f6356f32-3e57-44c7-9b28-fb6c6809d327)
Build Running
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8a79a91e-5106-475a-b458-8b376b78b94d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9176d556-286e-4a94-bd0e-46d25ea56886)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c31cdcd8-230c-487f-bc9d-8e71b24874d0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/93fff1e8-b8da-4d92-ab07-0e8ade91b127)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5fc9769a-eda7-4b98-8d44-720e493b3849)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9776d659-823d-4d53-9069-33b2b27a6968)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/58097207-c153-4283-b7de-ed9cdedaa6f0)

To delete the application, right on the application in the Topology Graph view
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b99d41b6-a395-4f01-8066-a953af608320)
Click on "Delete application"
Type "sample-app" and click on Delete
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5978bcd7-781b-4a8f-815e-f1cd6d9d1e05)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/486c1b06-23ed-42ef-a4c2-1f866f2b5c6d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4b64d4d4-fb35-442e-9baf-3da8da5b8930)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/07e5ce97-e357-40c3-a5e2-0d01081ca693)

## Lab - Deploying PHP multipod application
Navigate to Developer context in Openshift webconsole
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/74f9836b-03be-498b-b12e-923238512a23)
Click +Add
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5ea99665-e8d5-4141-bdd8-47bece82a279)
Under Developer Catalog, click on "All Services"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/72ee28a6-fc6e-481b-99ac-973120e69efa)
Search for "PHP"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/624f6e0c-f022-4521-9890-4973530f3dd8)
Select "CakePHP+Mysql Ephemeral"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/63e1bfbd-2d22-49e2-9439-e375b7e3a3fc)
Click "Instantiate Template"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/2b27254d-403d-4858-8465-d68f64236d0a)
Accept the defaults, scroll down to click "Create" button
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0960a5e1-db8f-410c-a80d-d99394ab6e9c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9366e8d3-6b1c-4a45-bb5b-9d6c9dce2994)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/cc14efbb-9452-414b-8dd1-d30749b16030)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f874c81c-990f-4aa4-b371-5adee8298399)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/36f55c41-13c4-469c-bfe5-82892d703804)

To Delete the application, right click on the application in the Topology View over the Graph
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f4196c02-a35a-4d49-894d-ef0d183986c7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5e7a77c2-bdbc-48a2-9271-311a39722d58)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/59a105a3-ec16-47a8-a8ed-71d8869f0d32)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8ef704b7-69f8-4374-bdb7-dd2e5d0d337f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4e3ff796-aef3-4046-9ff0-5c82ae9b7aee)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/35876d1f-368d-4ab0-a1dc-98baf158d390)

## Lab - Deploying Quarkus application from Develop Context in Openshift webconsole
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/2d807b24-55e6-4d48-b146-29741a58b48a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/049ea956-7fd9-4fde-b913-f15f909a4d43)
Select "Basic Quarkus"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f1a14698-8e49-4e70-a1f1-72f37543ca67)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ce735df1-b477-40f8-8bdb-601f8786d8dc)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/24ab3de5-dcec-4da4-8c52-1463af0967ae)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7468b91e-b641-4798-b881-5af402888da9)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/079380bb-8304-4278-a779-37afaa5aff86)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/255ebf35-fb3d-4898-8148-f1b6a6674598)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/10ce4525-e918-4405-af9e-1ef636516bc0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c2904b40-9c75-4343-b00e-b2d2fa8fdc0f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/87944a15-b4df-49e5-9912-5a8debb999a0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/76f82ec0-326e-4788-9381-0d485bd50ed1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e2920054-0be0-45f2-bea1-4e7241a6f920)


To delete the application
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/94c4e7d6-f151-45a9-8df3-284f3965b7a7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/16ef942c-82e2-40c5-9320-2b94106a5523)
