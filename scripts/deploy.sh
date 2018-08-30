unzip seed-1.0-SNAPSHOT.zip
withEnv(['BUILD-ID=dontkill']){
sh "nohup ./seed-1.0-SNAPSHOT/bin/seed &"
}

