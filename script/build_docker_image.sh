TARGET_IMAGE=was-reuse:1.0.0
PUSH_IMAGE=iksadnorth/was-reuse:1.0.0
DIR_DOCKERFILE=./

# Docker Build & Push
docker build --tag $TARGET_IMAGE $DIR_DOCKERFILE
docker tag $TARGET_IMAGE $PUSH_IMAGE
docker push $PUSH_IMAGE
