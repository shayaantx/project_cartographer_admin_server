def dockerFileContents = """
FROM centos:7

RUN yum update; yum clean all;
RUN yum -y install java-1.8.0-openjdk-devel.x86_64;
RUN yum -y install maven
RUN adduser jenkins

ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk
""";

node {
    def exception;
    try {
        stage("Checkout") {
            checkout scm
        }

        stage('Prepare docker') {
            fileOperations([fileCreateOperation(fileContent: "${dockerFileContents}", fileName: './Dockerfile')]);
        }

        def image = docker.build("project-cartographer-admin-server-image", "-f ./Dockerfile .");
        image.inside('-u root') {
            try {
                stage('Build/Test') {
                    sh 'mvn -version'
                    sh 'mvn compile'
                }

                stage('Package') {
                    sh 'mvn package'
                }
            } finally {
                def workspace = "${WORKSPACE}";
                if (workspace) {
                    echo "workspace=${WORKSPACE}"
                    //we do this because of https://issues.jenkins-ci.org/browse/JENKINS-24440
                    //where if we run the container as root, but run docker as jenkins, then create files in the container
                    //as root, jenkins can't clean them up
                    sh "chmod -R 777 ${WORKSPACE}"
                }
            }
        }
    } catch (e) {
        exception = e;
    }

    stage("Cleanup") {
        deleteDir();
    }

    if (exception != null) {
        throw exception;
    }
}