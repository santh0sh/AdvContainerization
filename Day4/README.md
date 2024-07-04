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
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/52ea7420-49ee-456a-92fa-ce4008bb2659)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0b875948-ee98-4667-b212-d3f9de4c425f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/97df8183-60b3-4600-9a0c-96cda6858124)

