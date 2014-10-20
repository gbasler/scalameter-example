import sbt._
import Keys._
import sbt.Reference._

object build extends Build {

  import DependencyManagement._

  // Settings shared by all sub-projects.
  val standardSettings: Seq[Project.Setting[_]] =
    Seq[Project.Setting[_]](
      ivyXML := DependencyManagement.ivyExclusionsAndOverrides,
      scalaVersion := "2.10.4",
      resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
        "releases" at "http://scala-tools.org/repo-releases",
        "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
        "Nexus Public" at "http://nexus/nexus/content/groups/public",
        "Nexus Releases" at "http://nexus/nexus/content/repositories/releases"
      )
    )

  //
  // PROJECTS
  //

  // Parent Project, it aggregates all others.
  lazy val root = Project(
    id = "scalameter-example",
    base = file("."),
    settings = Defaults.defaultSettings ++ standardSettings,
    aggregate = Seq[ProjectReference](core)
  )

  lazy val core = Project(
    id = "scalameter-example-core",
    base = file("scalameter-example-core"),
    settings = Defaults.defaultSettings ++ standardSettings ++ Seq(
      testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
      logBuffered := false,
      parallelExecution in Test := false,
      libraryDependencies ++= Seq(Specs, ScalaMeter, CommonsMath3) ++ SpecsDeps
    )
  )
}
