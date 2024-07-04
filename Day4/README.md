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
