name := "Crude Battle"

version := "0.1"

scalaVersion := "2.13.16"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases"  at "https://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "ch.hevs.gdx2d" % "gdx2d-desktop" % "1.2.1",

  "com.badlogicgames.gdx" % "gdx-backend-lwjgl"  % "1.5.6",
  "com.badlogicgames.gdx" % "gdx-platform"       % "1.5.6" classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-box2d"          % "1.5.6",
  "com.badlogicgames.gdx" % "gdx-box2d-platform" % "1.5.6" classifier "natives-desktop",
  "com.badlogicgames.gdx" % "gdx-freetype"       % "1.5.6",
  "com.badlogicgames.gdx" % "gdx-freetype-platform" % "1.5.6" classifier "natives-desktop"
)

// Options de compilation pour forcer la cible sur Java 11
javacOptions ++= Seq("-source", "17", "-target", "17")
scalacOptions ++= Seq("-release", "17")

// Lancement dans un processus séparé
fork := true