name := "Crude Battle"

version := "0.1"

scalaVersion := "2.13.16"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases"  at "https://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "ch.hevs.gdx2d" % "gdx2d-desktop" % "1.2.1",
  "com.badlogicgames.gdx" % "gdx-backend-lwjgl"  % "1.12.1",
  "com.badlogicgames.gdx" % "gdx-platform"       % "1.12.1" classifier "natives-desktop"
)

fork := true