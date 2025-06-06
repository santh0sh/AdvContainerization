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

## Lab - Deploying a pod directly without replicaset/depoyment in declarative style
#### Points to note
<pre>
- Creating a Pod directly without replicaset & deployment is a bad practice
- As there is no ReplicaSet, ReplicaSet controller will not support scale up/down and healing of pod is also not possible
- As there is no Deployment, Deployment Controller will not support rolling update
- One genuine usecase for creating a Pod is for testing purpose
- With a test pod we can check if the service discovery is working
</pre>

```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
cat pod.yml
oc create -f pod.yml --save-config
oc get po -w
oc get po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/a7282baf-ffb1-4bf1-9c2a-84657bbe4c8d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/70912604-7273-4f05-89c2-c6927843dc13)

## Lab - Creating a clusterip internal service in declarative style
```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc get deploy,rs,po
oc create -f nginx-deploy.yml --save-config
oc get deploy,rs,po
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c954c2de-b86c-450a-831f-865044026e7f)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0d6420eb-44fd-4ee6-896a-cccae0fc8d3c)

Let's create a clusterip internal service
```
oc expose deploy/nginx --type=ClusterIP --port=8080 -o yaml --dry-run=client
oc expose deploy/nginx --type=ClusterIP --port=8080 -o yaml --dry-run=client > nginx-clusterip-svc.yml
oc create -f nginx-clusterip-svc.yml --save-config
oc get svc
oc describe svc/nginx
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/64d049c9-7c61-4a53-aa4d-eea8bec55b4c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/77bae339-46c0-42c0-a6f3-1e0ae8431256)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9faba10b-1734-496f-b7bf-18a6df768407)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c5309295-5622-4362-9421-1862217b0d1d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/90e2b0a8-fa60-41c8-8fbb-ee14210a0599)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/35ce7d0f-466d-411f-9f61-c87279d883ac)

Accessing the clusterip internal service using a test pod
```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc get svc
oc describe svc/nginx
ls -l
cat pod.yml
oc apply -f pod.yml
oc exec -it mypod sh
curl http://nginx:8080
exit
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/64144742-3515-4e8b-bd56-d52ce1688c6b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/3b498565-faaa-4ed3-b0cd-2d7d872de8bf)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9e87d94e-615a-4fc9-a50a-81dc9cb52fc1)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/8aa1e6b5-3e1d-4b28-9fc4-329bacfd97bd)

## Lab - Creating a nodeport external service in declarative style

```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc get deploy,rs,po
oc create -f nginx-deploy.yml --save-config
oc get deploy,rs,po
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/83e3ca9b-02b6-4538-b650-c8c12b476333)

Let's create a nodeport external service
```
oc expose deploy/nginx --type=NodePort --port=8080 -o yaml --dry-run=client
oc expose deploy/nginx --type=NodePort --port=8080 -o yaml --dry-run=client > nginx-nodeport-svc.yml
oc delete -f nginx-clusterip-svc.yml
oc create -f nginx-nodeport-svc.yml --save-config
oc get svc
oc describe svc/nginx
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bd1ca432-c738-49df-976b-733fbe3af4e9)

Accessing the nodeport external service using a test pod
```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc get svc
oc describe svc/nginx
ls -l
cat pod.yml
oc apply -f pod.yml
oc exec -it mypod sh
curl http://nginx:8080
exit
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9075f180-88d6-483c-b948-a8880016276b)


## Lab - Creating a loadbalancer service for nginx deployment in declarative style
Let's delete the existing nodeport service
```
cd ~/openshift-july-2024
git pull
cd Day3/declarative-manifest-scripts
oc delete -f nginx-nodeport-svc.yml
```

Let's create the lb service in declarative style
```
oc get deploy,po
oc expose deploy/nginx --type=LoadBalancer --port=8080 -o yaml --dry-run=client
oc expose deploy/nginx --type=LoadBalancer --port=8080 -o yaml --dry-run=client > nginx-lb-svc.yml
oc create -f nginx-lb-svc.yml
oc get svc
oc describe svc/nginx
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c4b3ff74-3722-4031-bff1-270387137689)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ad477b5f-b6a9-41bc-ac7a-0c01b7abb680)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/60a0ea6b-b89a-4888-9c33-39c5ccac5e8e)

## Info - Rolling update
<pre>
- is a way one can upgrade the application which is already deployed in Openshift to latest version without any downtime
- rolling udpate happens when we update the container image in a deployment
- for each container image version, the deployment controller creates one instance of ReplicaSet
- it is also possible to rollback to previous or any specific version in case you found any issue with the latest verion of your application
</pre>

## Lab - Rolling update - upgrading nginx image from 1.18 to 1.19
```
oc get deploy
oc get deploy/nginx -o yaml | grep image
oc get po
oc get po/nginx-566b5879cb-9zgck -o yaml | grep image
oc get rs
oc get rs/nginx-566b5879cb -o yaml | grep image
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0c080837-0cf6-4229-8a9c-d09d3609b6b4)

<pre>
- To split the screen horizontal, you need to press Ctrl+B "
- To navigate from one splitted window to other, Esc Ctrl+B cursor movement keys (up,down,left,right)
- To split the screen vertically, you need to press Ctrl+B %
</pre>


Let's use tmux to split the terminal
```
tmux
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/cbe2c284-fd98-4ab2-a48b-50c94a05aab9)

Let's split the terminal horizonal using Ctrl+B "
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0585aa78-1805-4f52-8279-53766c32febc)

Move to the bottom splitted window by pressing Ctrl+B down arrow
```
cd ~
clear
```
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/24e2923b-45ad-4d8d-93e6-bbcf505e62c4)


Let's split the bottom window vertically
```
Ctrl+B %
cd ~
clear
```
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/72277644-3946-47c5-99fc-99943212f9ae)

In the bottom left window, run the below command 
```
oc get rs -w
```
In the bottom right window, run the below command
```
oc get po -w
```
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5812ef3b-e2ce-4572-b434-b75234924a8f)


Let's upgrade then nginx image version from 1.18 to 1.19 from the top window
```
oc set image deploy/nginx nginx=bitnami/nginx:1.19
oc get deploy/nginx -o yaml | grep image
oc get rs
oc get rs/nginx-566b5879cb -o yaml | grep image
oc get rs/nginx-6b49c75d9 -o yaml | grep image
```

Checking the rollout status and history
```
oc rollout status deploy/nginx
oc rollout history deploy/nginx
```

![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/49a266d7-1417-4860-97ef-765912a1f293)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/773f66b1-ff3d-46c5-b176-1a12e9ee5916)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c8d0e774-d859-4793-803e-25f26bfcc0d2)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4181f09f-1af7-42c0-975f-78ba7eefef27)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/919d7800-3191-4ad7-b032-4bf06d837a1d)

If you wish to go back to older version, you may rollback ( practical usecase if newly deployed version unstable)
```
oc rollout undo deploy/nginx
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6f606c74-8778-47fd-b31e-31f05b8ce998)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b53a3cf0-37f9-4bae-95bc-8f2a47ebceee)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/a7ab4ed6-a68c-4c24-b670-a0e9c9fb882b)

## Lab - Deploying application in declarative style from Openshift webconsole
Go to Developer context in Openshift webconsole on your web browser
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/91fc7858-4e50-40b0-a3ac-914844a28ef2)
Click Search
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/35d167a6-d5e8-413b-9a51-d4e9e41188f8)
Search for "Deployment"
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9bb11bcb-7a5e-4445-9e2a-3508f9553472)
Click Create Deployment button
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/518168eb-c324-4084-b25a-8e0eec87e70f)

You need to edit name to nginx, app under label to nginx, container name to nginx, image to bitnami/nginx:latest
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e69bbb7a-4152-4846-8287-4374b07d5580)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/aa759a3c-7e8c-4b61-a1f0-8ec9504eac7a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/49a84bfe-6f81-4d33-8952-bcbab61f8157)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0b0a213a-e466-4a69-9760-44b8b433ddde)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5f33de83-3597-4f8e-aa23-494222760136)

Once you updated the mandatory fields, you may click on "Create" button
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/a1a4278d-cc0b-4bd2-8783-132e20cff319)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/01b6a3e8-fad4-4502-aef8-46e00c82aafb)

## Info - Persistent Volume Overview
<pre>
- is an external storage which is used to persistent the data permanently
- though Pod container's can use its internal storage, we will the lose once the Pod is deleted
- if we need data to persisted beyond life-time of the Pod, we need to use Persistent volumes
- Persistent volume are created by OpenShift Administrator
- Persistent can be provisioned either manually or dynamically using Storage Class
- Persistent Storage can be provisioned using NFS, AWS,Azure, GCP, etc.,
- it is always created in Cluster scope, hence any application deployed under any project namespace can claim PV and use it
- Persistent Volume mandatory attributes
  - Size in MiB/GiB/TiB
  - AccessModes 
    - ReadWriteOnce ( All Pods from same node can access the PV )
    - ReadWriteMany ( All Pod from all nodes can access the PV )
  - Server, Path, etc.,
  - StorageClass - Optional
  - Labels - Optional
</pre>

## Info - Persistent Volume Claim Overview
<pre>
- any application that needs Persistent Volume external storage has to request for it by defining the requirement in the form of Persistent Volume Claim(PVC)
- Persistent volume claim attributes
  - Size in MiB/GiB/TiB
  - AccessMode
  - StorageClass ( optional )
  - Labels (optional)
- the storage controller will search for a matching Persistent Volume as per the PVC attributes
- if storage controller is not able to find a matching PV, then the PVC will be in Pending state, the Pod that depends on this PVC will also be in Pending state
</pre>

## Info - NFS Shared Folder
<pre>
- For Persistent volume, we will be using NFS Server
- NFS Server is already installed in 
  - 10.10.15.9 (OS1)
    - user01 thru user08
  - 10.10.15.34 (OS2) and
    - user09 to user16
  - 10.10.15.5 (OS3) Linux Servers
    - user17 to user22
  
- For each participants, there will be about five folders
  For user01
  - /var/nfs/user01/mariadb
  - /var/nfs/user01/mysql
  - /var/nfs/user01/wordpress
  - /var/nfs/user01/redis
  - /var/nfs/user01/mongodb
</pre>

## Lab - Deploying mysql with Persistent volume and claim

Before proceeding with the deployment, you need to customize mysql-pv.yml, mysql-pvc.yml and mysql-deploy.yml.

```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/mysql
ls -l
cat mysql-pv.yml
oc create -f mysql-pv.yml --save-config
oc get persistentvolumes
oc get persistentvolume
oc get pv

cat mysql-pvc.yml
oc create -f mysql-pvc.yml --save-config
oc get persistentvolumeclaims
oc get persistentvolumeclaim
oc get pvc

cat mysql-deploy.yml
oc create -f mysql-deploy.yml --save-config
oc get deploy,rs,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/3b4afe0b-3f4f-442f-9b17-d2195a8c1675)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/05040f27-5583-421b-a9f1-bd334d7aaea6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c50fbd25-f1e1-4a89-aaf7-fbd0dab5b80c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/5c277092-a8ee-45b7-be3a-91826c945c99)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/a77eb458-428b-4023-a905-b9857a905eb4)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f19cec5e-e2bd-4728-b544-a6b877d81e32)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/2834875b-4893-45e7-a829-d4a8f5b6c4ce)

Getting inside the mysql pod shell
```
oc get deploy
oc rsh deploy/mysql
mysql -u root -p
```
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b855099e-6424-4448-b3c0-3cf6f13b7c7d)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/410c6d98-d7d0-42b1-a807-55d488b95460)

Connecting to mysql server using mysql client from mysql pod terminal, when prompted for password type 'root@123' without quotes
```
mysql -u root -p
SHOW DATABASES;
CREATE DATABASE tektutor;
USE tektutor;
CREATE TABLE TRAINING ( id INT NOT NULL, name VARCHAR(250) NOT NULL, duration VARCHAR(250) NOT NULL, PRIMARY KEY(id) );
DESCRIBE TABLE training;

INSERT INTO TRAINING VALUES (1, "DevOps", "5 Days");
INSERT INTO TRAINING VALUES (3, "Advanced Ansible tower", "5 Days");

SELECT * FROM TRAINING;
exit
exit
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f68d6a2d-09a9-4384-bb37-86f7cd3b4559)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/ce2da587-f35b-49da-aed7-79b1578bd760)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/816c24de-db8e-442a-83a2-e5231554ec52)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bdee9f5e-1de9-4570-8130-30385dc41378)

Delete the mysql pod from openshift webconsole and observe a new mysql pod will be created automatically.

Now, get inside the mysql pod terminal and try the below to see if the database and records are safe
```
mysql -u root -p
SHOW DATABASES;
USE tektutor;
SHOW TABLES;
SELECT * FROM training;
exit
exit
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4c53067c-1060-4a38-a5cc-34059b15e439)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/7df6ff62-a20f-4feb-93df-fa75f3849326)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/238164a6-bd16-425b-aaf9-54662024c12e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e88112a1-ae4a-48c4-b645-3bda7ecf8d60)

## Lab - Deploying redis database and persist data in a NFS path using Persistent Volume and Claim
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/redis
cat redis-pv.yml
oc create -f redis-pv.yml
oc get pv

cat redis-pvc.yml
oc create -f redis-pvc.yml
oc get pvc

cat redis-deploy.yml
oc create -f redis-deploy.yml
oc get deploy,po
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/fa4d921d-5310-48e0-a641-33691100701a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4196c07e-cc2d-4e04-9379-6395af7edc2a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9308b92a-b243-454f-86eb-ff2859afff50)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/bb5ff045-eff9-422e-8a2b-abec913077de)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/66dc628b-b28f-479b-b051-7d11b2198ad9)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/73755d8e-2510-41fc-bae3-54396e37f7df)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/aae26661-61ad-4538-9435-9e6b4978949e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/21c28eee-a98e-4fc7-bf6a-cb5d13d23197)

Once you are done with this exercise, you may clean up all the resources used by this project
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/redis

oc delete -f redis-deploy.yml
oc delete -f redis-pvc.yml
oc delete -f redis-pv.yml
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/200d2291-3705-4dd7-9e4a-c74e31cdd833)

## Lab - Deploying mongodb using Persistent volume and claim
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/mongodb
./deploy.sh
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/6c0d2f68-6bf1-49f6-b9fe-cace82da6a93)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d7f65a03-b12f-4382-b92e-4743d7d89a50)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/359274ba-5ee3-4a59-be2d-f1995939890b)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/c4a74a0e-ffb6-46aa-a47c-e84395d6aa2c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/30d2e524-363d-4a74-8ad4-1f45c47ff9d2)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/a6fed2b6-a956-4117-9c6b-e727098ae1fc)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/3feacb8e-422d-4ec0-ac3f-872a6ab6f43c)

Once you are done with this exercise, you may clean up all the resources that were created in this exercises
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/mongodb
./delete-all.sh
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/f16707fa-12c3-411d-98b5-8979ba9c75e0)

## Lab - Deploying a multipod application with Persistent volume and claim ( Wordpress and Mariadb )
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/wordpress
./deploy.sh
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/9964251b-e43c-4619-a985-e3e7543c5d93)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/655f4712-5aee-4207-ae82-a08d6762a14e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/069f6927-d714-45c8-a60b-44e4d6afc74c)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e4ef0d7e-4b0a-48fe-94cd-e4f9fbf90e4d)

![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b546d726-44e0-46d4-a6b9-4f1fd292388a)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/04aeea46-6bd9-4bc2-af0a-078e8d150c51)

Once you are done with this exercise, you may cleanup the resources
```
oc get all
oc get pv,pvc
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume/wordpress
ls -l
./delete-all.yml
```
Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/1f510b99-1f69-47ae-b780-c238a02ede3e)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/680fbb67-8f92-4cca-9ae4-b9cce67b2264)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/4689db07-6e8c-4f08-94a2-6e8e808161fa)

## Info - ConfigMap
<pre>
- CongigMap is used to store mulitple key-value configuration data
- any number of key/value pairs can be stored in configmap
- this is alternate to properties.xml or properties.yml file used in java or other programming languages
- this is used only for storing non-sensitive information
- for storing sensitive information like login credentials, consider using secret
- practical usecase examples
  - software tools path
  - application log path
</pre>

## Info - Secret
<pre>
- secret is also a Map internally just like ConfigMap
- only difference is the data stored in secret is not revealed, hence your application can securely retrieve and use it
- passwords, certificates can be stored in secret as key/value
- the key will be visible but not the password
- Openshift administrators can view both key/value stored in the secret
</pre>

## Lab - Let's refactor the wordpress/mariadb multi-pod application using ConfigMap and Secret
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume
./deploy.sh
```

Expected output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/87a28f0c-bf04-4e9c-a8b0-d34eff7e60b6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/b8004fb9-c78e-46cc-b657-31757e1f80a6)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/d457b40d-f5f9-412a-ad3f-6cdac8f58867)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/28fa39be-017e-4795-8d83-d3d7e0dacc42)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e4405be6-1ed1-456f-99ae-c7f0d151d0c3)


Once you are done with this exercise, consider deleting all the resources created part of this exercise to free up resources
```
cd ~/openshift-july-2024
git pull
cd Day3/persistent-volume
./delete-all.sh
```

Expeced output
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/0e248a42-cdb6-41bd-ba53-1a24a140bdc7)
![image](https://github.com/tektutor/openshift-july-2024/assets/12674043/e08eaf0c-4cb9-4278-a0ac-511e74093c48)

