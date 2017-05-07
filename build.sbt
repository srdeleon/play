name := "warehouse"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  filters,
  "ws.securesocial" %% "securesocial" % "2.1.4"
)     

resolvers += Resolver.url("sbt-plugin-snapshots",
url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/")
)(Resolver.ivyStylePatterns)
	
play.Project.playJavaSettings
