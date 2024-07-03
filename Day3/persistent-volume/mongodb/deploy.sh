echo $'Deploying mongodb ...\n'
cat mongodb-pv.yml

oc apply -f mongodb-pv.yml

cat mongodb-pvc.yml
oc apply -f mongodb-pvc.yml

cat mongodb-deploy.yml
oc apply -f mongodb-deploy.yml

oc get deploy,rs,po,pv,pvc
