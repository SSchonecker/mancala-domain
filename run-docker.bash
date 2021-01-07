echo "Enter docker credentials"
read -p 'Username: ' DOCKER_USER_NAME
read -sp 'Password: ' DOCKER_ACCESS_TOKEN

docker login -u $DOCKER_USER_NAME -p $DOCKER_ACCESS_TOKEN
docker pull $DOCKER_USER_NAME/sschonecker-repo:mancalaimage
echo "====================== Running container ======================"
echo
docker run --name silkes-container $DOCKER_USER_NAME/sschonecker-repo:mancalaimage
echo
echo "====================== ================= ======================"
docker rm -f silkes-container
docker rmi $DOCKER_USER_NAME/sschonecker-repo:mancalaimage
docker logout $DOCKER_USER_NAME/sschonecker-repo