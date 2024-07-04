# Day 4

## ReplicationController
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
