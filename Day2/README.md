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
