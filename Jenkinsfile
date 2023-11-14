// Load shared library from same revision of the same repository
def lib = library(identifier: "local@latest", retriever: legacySCM(scm)).lib

pipeline {
    agent any

    stages {
        stage("Use custom step") {
            steps {
                customStep("Hello, world!!!")
            }
        }
        stage("Classic look example (custom step)") {
            steps {
                script {
                    def messages = classicLoop()
                    echo(messages.join("\n"))
                }
            }
        }
        stage("Classic look example (lib method noncps)") {
            steps {
                script {
                    def messages = lib.Example.classicLoopNonCPS()
                    echo(messages.join("\n"))
                }
            }
        }
        stage("Classic look example (lib method cps)") {
            steps {
                script {
                    def messages = lib.Example.classicLoopCPS()
                    echo(messages.join("\n"))
                }
            }
        }
        stage("Classic look example (inline)") {
            steps {
                script {
                    def messages = []
                    messages += "Begin"
                    for (; ;) {
                        messages += "Inside classic loop"
                        break
                    }
                    messages += "End"
                    echo(messages.join("\n"))
                }
            }
        }
        stage("Testing") {
            steps {
                sh "./gradlew test"
            }
            post {
                always {
                    junit "build/test-results/test/**.xml"
                }
            }
        }
    }
}
