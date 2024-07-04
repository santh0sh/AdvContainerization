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
oc logs -f hello-job-lbh6d 
```

Expected output

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
