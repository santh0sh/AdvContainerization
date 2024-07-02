# Day 2

## Lab - Listing multiple resource in your project using a single command
```
oc get deploy,rs,po
oc get all
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/91588296-2765-4564-960f-d4005649238b)

## Lab - Delete a nginx deployment
```
oc get deploy,rs,po
oc delete deploy/nginx
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c07a443d-32db-4bbb-9779-5216baeb7a36)

## Lab - Creating a nginx deployment with bitnami/nginx image
#### Points to remember
<pre>
- the bitnami container images follow the Openshift best practices and conventions
- the bitnami container images are root-less, i.e the applications runs with non-root privilege as per openshift conventions
- hence, mostly all bitnami images are safer to use in Openshift
</pre>

```
oc create deployment nginx --image=bitnami/nginx:latest --replicas=3
oc get deployments
oc get deployment
oc get deploy

oc get replicasets
oc get replicaset
oc get rs

oc get pods
oc get pod
oc get po

oc logs nginx-66c775969-5vnbr
oc logs -f nginx-66c775969-5vnbr
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7952292f-1cd0-4f43-86cc-6152e5d8f768)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9eadd409-988e-4a3b-97c9-f8fc1e0bf94c)


## Lab - Finding more details about a running pod
```
oc get po
oc describe pod/nginx-66c775969-5vnbr
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4a3c9c8d-4512-43d1-bcca-56506fc5a3b0)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/28bd80d1-67c1-472c-91c7-4294437e3b07)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6de9b1ea-6f64-45bc-acc1-57b9cac42073)

## Info - What happens when we deploy an application in Openshift with the below command
```
oc create deployment nginx --image=bitnami/nginx:latest --replicas=3
```

<pre>
- the oc client tool makes a REST call to API Server requesting it to create nginx deployment with image bitnami/nginx:latest with 3 Pods in it
- API Server receives the request from oc client tool, it then create a new Deployment record in etcd database
- API Server then sends a broadcasting event saying a New Deployment is created
- the event is received by Deployment Controller, it then sends a REST API call to API Server, requesting it to create a ReplicaSet for ngin deployment
- API Server receives the request from Deployment Controller and it creates a ReplicaSet record in etcd database
- API Server sends a broadcasting event saying a New ReplicaSet is created
- the event is received by ReplicaSet Controller, it then understands 3 Pods are mentioned in the Desired count, hence it makes REST call to API server to create 3 Pods
- the API Server creates 3 Pod records in etcd database and it sends broadcasting events say new Pod created.  One such event will be broadcasted for every New Pod created.
- the scheduler receives the event and it sends scheduling recommendation for each Pod to the API Servers
- API Server receives the REST call from Scheduler, it then retrieves the existing Pod records from etcd and it updates the Pod records with the node details as recommended by Scheduler
- API Server then sends broadcasting events that Pod scheduler to so an do nodes
- the kubelet container agent that runs in node where the Pod is scheduled receives the event, it then downloads the container images, creates the container and starts the container
- the kubelet then sends the status of the container running on that nodes to API Server via REST calls
- the API Servers updates the status of the Pod based on the status it received from kubelet
- the kubelet keeps sending this kind of container status updates to API Server like a heart beat fashion
- the API keeps the Pod status updated based on the status reported by kubelet
</pre>

## Lab - Pod Port forwarding to access the web page served by a single pod
#### Points to remember
<pre>
- port forwarding must be used only for testing purpose
- generally used by developers
- should not be used in production
- for production use we must service
</pre>

```
oc get pods
oc port-forward nginx-66c775969-zxz5f 9090:8080
```

From another terminal, you may access the web page served the pod
```
curl http://localhost:9090
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/adba529e-0089-45bc-919e-911586c0a22a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1899a1bb-7e45-4c52-99ea-3db84002e337)

## Info - Kubernetes/Openshift Service
<pre>
- Service represents a group of pods from a single Deployment
- Service can be accessed by its Name - Service discovery
- Types of Services
  1. ClusterIP - Internal Service ( Supports inbuilt load-balancing )
  2. NodePort - External Service ( supports inbuilt load-balancing )
  3. LoadBalancer - External Service ( spins-off an external Load Balancer in AWS/Azure/GCP )
</pre>


## Lab - Creating an internal service for nginx deployment
<pre>
- In the below command, type=ClusterIP indicates we want to create an internal clusterIP service
- this type of service is practically used for databases which are accessed only within the cluster by frontend pods
- ClusterIP service is accessible only within the openshift cluster
- Pod running with the same cluster can access this type of service
- For clusterIP service, kube-proxy which runs in every nodes supports the load-balancing
- as this load-balancing is an internal implementation of Kubernetes/Openshift there will not be any extra charge even if our Openshift runs in public cloud like AWS/Azure/GCP for the service we created
- the port 8080 is the ports where nginx web server is listening internally with the Pod container
- the endpoints for the service is created by a controller called Endpoint controller
- the endpoint controller will be watching for new service, deployment scale up/down, dedeployment deletion, pod replaced with another pod
  
</pre>

Let's create an internal service for nginx deployment
```
oc get deploy
oc expose deploy/nginx --type=ClusterIP --port=8080
oc get endpoints
oc get services
oc get service
oc get svc
oc describe svc/nginx
oc scale deploy/nginx --replicas=3
oc get endpoints
oc get svc
oc describe svc/nginx
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b507480c-8f44-4e0a-b17e-9306a2b1ec8a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d1c7e0a3-2f81-4051-8004-6ce0760a97ce)

Accessing the clusterip internal service from a Pod that runs inside the same cluster
```
oc create deployment test --image=tektutor/spring-ms:1.0
oc rsh deploy/test
curl http://nginx:8080
exit
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/413bcdce-80b0-435f-958f-43876cd91b50)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/78b00c5e-c86a-4b79-9616-df56b2b04e6e)

## Info - How the Service Discovery works in Kubernetes/Openshift?
<pre>
- When the Pod containers are created by kubelet container agent, it also configures the /etc/resolv.conf file in every Pod container
- the /etc/resolv.conf file has a nameserver with IP 172.30.0.10
- the dns-default service has Pod running in each node
- they will resolve the nginx service name to its respective service IP
</pre>

Let's get inside the test pod that we created as part of test deployment
```
oc rsh deploy/test
cat /etc/resolv.conf
```

On the local machine, you can list and see dns service
```
oc get svc -n openshift-dns
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d1b37f90-9841-4baa-8e0e-3a4e37b79f2e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4c465ae6-d3fa-45c6-8b73-9f514afc8dac)

## Lab - Creating an external NodePort service for nginx deployment

We need to first delete the clusterip service we created earlier
```
oc get svc
oc delete svc/nginx
oc get svc
```

Let's create the nodeport service
```
oc expose deploy/nginx --type=NodePort --port=8080
oc get svc
oc describe svc/nginx
```

As this is an external service, we can directly access using the node name and node port
```
oc get nodes
curl http://master-1.ocp4.tektutor.org.labs:32326
curl http://master-2.ocp4.tektutor.org.labs:32326
curl http://master-3.ocp4.tektutor.org.labs:32326
curl http://worker-1.ocp4.tektutor.org.labs:32326
curl http://worker-2.ocp4.tektutor.org.labs:32326
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/13fcf686-26ad-4062-8afc-a2cb0f88b58e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/370c111f-e404-4f82-874d-5a1d42c2d3ed)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b89580ad-4448-433b-bd90-1907fa51e239)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1d0bf7e0-ab35-4b5e-a02a-efebaa20f17c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5360c970-9029-4aba-b5e2-0289a01b7932)

## Info - NodePort Service gotchas
<pre>
- NodePort is neither user friendly nor developer friendly
- the end-user must know the openshift node details like its hostname, node IP, how many nodes are there in the cluster
- for nodeport service we create one port in the range 30000-32767 will be opened on every node in the cluster
- the more nodeport services we create, more number of ports has to be opened up in the firewall
- opening too many ports leads to security issue
- Just ClusterIP and LoadBalancer service, NodePort service is a Kubernetes feature
- Openshift has a better alternate called Route
</pre>
