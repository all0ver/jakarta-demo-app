// Pipeline etapu I: pobranie kodu, kompilacja i testy w jednorazowym kontenerze
// Mavena, archiwizacja WAR-a. Wdrozenie (etap II) dojdzie pozniej.
pipeline {
    // Build na wbudowanym wezle Jenkinsa; praca odbywa sie w kontenerze (nizej)
    agent any

    options {
        // Przerwij build, gdy wisi dluzej niz 15 minut
        timeout(time: 15, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                // Kod z repozytorium, z ktorego Jenkins wzial ten Jenkinsfile
                checkout scm
            }
        }

        stage('Build i testy') {
            steps {
                // Jednorazowy kontener Mavena tworzony przez Podmana hosta (gniazdo).
                // -v $WORKSPACE:$WORKSPACE - sciezka ta sama w Jenkinsie i na hoscie
                //   (patrz compose.yaml Jenkinsa); kontener widzi TYLKO workspace.
                // -v maven-m2:/root/.m2 - wolumen z cache zaleznosci Mavena
                // -w $WORKSPACE - katalog roboczy kontenera
                // mvn -B verify - kompilacja + testy + pakowanie WAR; niepowodzenie
                //   testu przerywa build (fail-fast), kolejne etapy sie nie wykonaja.
                sh '''
                    podman run --rm \
                        -v "$WORKSPACE":"$WORKSPACE":Z \
                        -v maven-m2:/root/.m2 \
                        -w "$WORKSPACE" \
                        docker.io/library/maven:3.9-eclipse-temurin-21 \
                        mvn -B verify
                '''
            }
        }

        stage('Archiwizacja') {
            steps {
                // WAR zapisany jako artefakt builda (do pobrania z Jenkinsa)
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
    }

    post {
        always {
            // Raporty testow widoczne w Jenkinsie, rowniez gdy build sie nie powiodl
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
    }
}
