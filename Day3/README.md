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
cd Day3/declarative-manifest-scripts
cat nginx-deploy.yml
oc apply -f nginx-deploy.yml
oc get po -w
oc get po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9ddede29-fb30-40db-a5fe-0110e81b9c18)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0f2bacfc-b244-4aad-914e-ffe11ce529d1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c5c6f02a-17d3-4c97-9541-b1fb3edc9b2e)

## Lab - Deleting a deployment in declarative style
```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc get deploy,rs,po
oc delete -f nginx-deploy.yml
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c1328eb4-8566-4a60-a6d6-0f0874dae532)

## Lab - Deploy nginx in declarative style with --save-config flag
#### Points to remember
<pre>
- oc create should be used only the first time we are creating the resource
- oc create will assume the resource definitions captured in the yaml file is not deployed already in openshift
- oc apply must be used after updating any changes done in yaml file following the oc create command
- oc apply command will work first time and after updating the yaml file as well
- when we use the save-config flag while using oc create command, it save the meta-data i.e using which yaml file the resources were created in the etcd database
- so next time apply changes after any modification done in the yaml file, it will validate that we used the same file while creating the resource, this way we avoid the warning we get while using apply in declarative style
</pre>

```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc get deploy,rs,po

cat nginx-deploy.yml
oc create -f nginx-deploy.yml --save-config
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c39dd4e9-0305-4031-b162-9c19514bc51e)


Update the nginx-deploy.yml, replace replicas from 5 to 3, save it and apply
```
cat nginx-deploy.yml
oc apply -f nginx-deploy.yml
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/45cee3ab-0b50-45eb-b756-08a13856863e)

## Lab - Deploying replicaset without deployment in declarative style
#### Points to remember
<pre>
- Deploying an application directly using replicaset isn't considered a best practice
- When the top level resource is replicaset, deployment controller will not monitor the replicaset, hence repairing replicaset is not possible
- Deployment controller is the one which supports rolling update, because now we deployed replicaset without deployment, rolling update isn't possible
- Hence, for these reasons we should always deploy stateless applications as a Deployment not using replicaset directly
</pre>

```
oc get rs
oc get rs -o yaml
oc get rs -o json
oc get rs/nginx-566b5879cb -o yaml
oc get rs -o yaml > nginx-rs.yml
cat nginx-rs.yml
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d593245c-121a-4d4b-8f9e-5fc5fb4acfe4)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/85ef963e-e91b-4255-990a-5c24c2dbe366)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/13513404-e612-48cc-994d-69f58e19d68a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/a2fb08c3-a475-444a-a0fd-88bf4d9547ee)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/453c14d3-8c89-492c-bdb6-ba9eae17adb7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6080a673-5a71-42c7-ae22-c2588865a0d7)

Let's delete the existing deployment
```
oc delete -f nginx-deploy.yml
oc get deploy,rs,po
cat nginx-rs.yml
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b5c238b1-b066-4fde-ad2f-ba48fa443dab)


Let's now create the replicaset without deployment in declarative style
```
oc create -f nginx-rs.yml --save-config
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/badd7300-3c1e-432a-882b-b410d942bc0c)


