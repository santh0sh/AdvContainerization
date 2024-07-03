echo $ 'Undeploying mongodb ...\n'
oc delete -f mongodb-deploy.yml
oc delete -f mongodb-pvc.yml
oc delete -f mongodb-pv.yml

oc get deploy,rs,po,pv,pvc
