#Scala SBT Example Skeleton Of Multi Project with Play Framework


#####package1 => based on play framework
  
`sbt package1/run` => run play-framework  
`sbt package1/docker:publishLocal` => will create docker image of the play application.  
`sbt package1/docker:clean` => will remove the docker image.  

`sbt package1/playUpdateSecret` will generate a new secret play web-application and replace it with the old secret in the `application.conf`.  

If you have a dockerhub account you can update this environment variable (`DOCKERHUB_USER`) in your endpoint. then you will be able to publish the docker image to your repo by this task:  
`sbt package1/docker:publish`.  

You'll need to create a docker repository that equal to the sub-project name => `name.value`.  


#####package2 => without play-framework  
 `sbt package2/run` => run application  
 `sbt package2/docker:publishLocal` => will create docker image application.  
 `sbt package2/docker:clean` => will remove the docker image.  
 `sbt package2/docker:publish` => publish docker image to repo. 