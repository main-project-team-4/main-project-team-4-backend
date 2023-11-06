TARGET_IMAGE=was-reuse;
PUSH_IMAGE=iksadnorth/was-reuse;
DIR_DOCKERFILE=./;

# Get Version Of Image
VERSION_IMAGE=$(source ./script/count_image_version.sh)
echo "This Version Of Image is $VERSION_IMAGE"

# Generate Jar File With Gradle
source ./script/build_spring_project.sh

# Docker Build & Push
docker build --tag $TARGET_IMAGE:$VERSION_IMAGE $DIR_DOCKERFILE
docker tag $TARGET_IMAGE:$VERSION_IMAGE $PUSH_IMAGE:$VERSION_IMAGE
docker push $PUSH_IMAGE:$VERSION_IMAGE