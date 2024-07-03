# Day 3

## Lab - Deleting a project
```
oc delete project jegan
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e6677cd6-a027-44a5-bb53-15457e48a999)

## Lab - Deploying nginx in declarative style
```
oc new-project jegan
oc create deployment nginx --image=bitnami/nginx:1.18 --replicas=3 -o yaml --dry-run=client
oc create deployment nginx --image=bitnami/nginx:1.18 --replicas=3 -o yaml --dry-run=client > nginx-deploy.yml

oc create -f nginx-deploy.yml
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d49ff98c-6ec1-498e-b7c7-e4099349efc1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0bf32ae6-e7b0-40fe-9134-856fc9b32005)
