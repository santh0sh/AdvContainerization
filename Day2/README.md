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
