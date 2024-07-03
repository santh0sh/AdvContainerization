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
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b69c4922-4f43-4888-ba23-caf84aaadf5f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4549a565-e461-48be-92bf-f144f8e4ce1f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/254a02b4-359a-4c95-a176-be93501473d1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/85374671-b4c2-4256-9ff3-d2d86f45024c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8faafeda-2f16-479c-8463-3489f43a05fb)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fe13fa34-94bd-4efa-8ba3-89dfc58b9701)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7e9a56a8-bbde-427c-8190-1185f7de12c5)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e81f2fa1-cf91-4a5a-a414-129bbe78614b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5a64d6bb-e1f2-49f2-b9ed-4095c6161cd8)

## Lab - Scale up nginx deployment in declarative style

We need to update the replicas in the nginx-deploy.yml file from 3 to 5, save it and apply
```
cd ~/openshift-july-2024
git pull
cd Day3/declaratives-manifest-scripts
cat nginx-deploy.yml
oc apply -f nginx-deploy.yml
oc get po -w
oc get po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9ddede29-fb30-40db-a5fe-0110e81b9c18)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0f2bacfc-b244-4aad-914e-ffe11ce529d1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c5c6f02a-17d3-4c97-9541-b1fb3edc9b2e)
