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
