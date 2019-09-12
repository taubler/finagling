name := "Finagling"

version := "0.1"

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % "0.29.0",
  "com.twitter" %% "finagle-thrift" % "19.8.0",
  "com.twitter" %% "finagle-mysql" % "19.8.0",
  "com.twitter" %% "util-core" % "19.8.0",
  "com.twitter" %% "scrooge-core" % "19.8.0",
  "org.scalactic" %% "scalactic" % "3.0.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

logBuffered in Test := false
