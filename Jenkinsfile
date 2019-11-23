def dockerFileContents = """
FROM centos:7

RUN yum update; yum clean all; yum -y install nano; yum -y install less;
RUN yum -y install java-1.8.0-openjdk-devel-debug.x86_64; yum -y install java-1.8.0-openjdk-src-debug.x86_64;
RUN yum -y install dos2unix;
RUN yum -y install net-tools;
RUN yum -y install openssh;
RUN yum -y install maven
RUN adduser jenkins

ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk
""";

def remoteServerRoot = "xxxx";
def credentialId = "XXX";

def restartAdminServerScript =
"""pkill -f "project_cartographer_admin"
nohup ${remoteServerRoot}/jdk8u212-b04/bin/java -jar -Dspring.config.location=${remoteServerRoot}/latest.properties "${remoteServerRoot}/project_cartographer_admin.jar" >${remoteServerRoot}/nohup.out 2>${remoteServerRoot}/nohup.err &""";

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

                stage('Deploy') {
                    sshPublisher(publishers: [sshPublisherDesc(configName: "${credentialId}", transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: "", execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: 'target/', sourceFiles: 'target/project_cartographer_admin.jar')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
                }

                stage('Restart Server') {
                    fileOperations([fileCreateOperation(fileContent: "${restartAdminServerScript}", fileName: './restart.sh')]);

                    withCredentials([usernamePassword(credentialsId: "${credentialId}", passwordVariable: 'password', usernameVariable: 'username')]) {
                        def remote = [:]
                        remote.name = "${credentialId}"
                        remote.host = "${credentialId}"
                        remote.user = "${username}"
                        remote.password = "${password}"
                        remote.allowAnyHosts = true
                        sshScript remote: remote, script: "restart.sh"
                    }
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