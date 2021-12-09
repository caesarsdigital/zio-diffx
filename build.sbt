import BuildHelper._

inThisBuild(
  List(
    organization  := "dev.zio",
    homepage      := Some(url("https://zio.github.io/zio-diffx/")),
    licenses      := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers    := List(
      Developer(
        "jdegoes",
        "John De Goes",
        "john@degoes.net",
        url("http://degoes.net")
      )
    ),
    pgpPassphrase := sys.env.get("PGP_PASSWORD").map(_.toArray),
    pgpPublicRing := file("/tmp/public.asc"),
    pgpSecretRing := file("/tmp/secret.asc")
  )
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "fix",
  "; all compile:scalafix test:scalafix; all scalafmtSbt scalafmtAll"
)
addCommandAlias(
  "check",
  "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check"
)

addCommandAlias(
  "testJVM",
  ";zioDiffxJVM/test"
)
addCommandAlias(
  "testJS",
  ";zioDiffxJS/test"
)
addCommandAlias(
  "testNative",
  ";zioDiffxNative/test:compile"
)

val zioVersion = "1.0.9"

lazy val root = project
  .in(file("."))
  .settings(
    publish / skip := true,
    unusedCompileDependenciesFilter -= moduleFilter(
      "org.scala-js",
      "scalajs-library"
    )
  )
  .aggregate(
    zioDiffxJVM
    // zioDiffxJS,
    // zioDiffxNative,
    // docs
  )

lazy val zioDiffx = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .in(file("zio-diffx"))
  .settings(stdSettings("zio-diffx"))
  .settings(crossProjectSettings)
  .settings(buildInfoSettings("zio.diffx"))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio"                %% "zio"          % zioVersion,
      "dev.zio"                %% "zio-test"     % zioVersion % Test,
      "dev.zio"                %% "zio-test-sbt" % zioVersion,
      "com.softwaremill.diffx" %% "diffx-core"   % "0.6.0"
    )
  )
  .settings(testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"))
  .enablePlugins(BuildInfoPlugin)

/* lazy val zioDiffxJS = zioDiffx.js
  .settings(jsSettings)
  .settings(libraryDependencies += "dev.zio" %%% "zio-test-sbt" % zioVersion % Test)
  .settings(scalaJSUseMainModuleInitializer := true)
 */
lazy val zioDiffxJVM = zioDiffx.jvm
  // .settings(dottySettings)
  .settings(libraryDependencies += "dev.zio" %%% "zio-test-sbt" % zioVersion % Test)
  .settings(scalaReflectTestSettings)
/*
lazy val zioDiffxNative = zioDiffx.native
  .settings(nativeSettings) */

lazy val docs = project
  .in(file("zio-diffx-docs"))
  .settings(stdSettings("zio-diffx"))
  .settings(
    publish / skip                             := true,
    moduleName                                 := "zio-diffx-docs",
    scalacOptions -= "-Yno-imports",
    scalacOptions -= "-Xfatal-warnings",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(zioDiffxJVM),
    ScalaUnidoc / unidoc / target              := (LocalRootProject / baseDirectory).value / "website" / "static" / "api",
    cleanFiles += (ScalaUnidoc / unidoc / target).value,
    docusaurusCreateSite                       := docusaurusCreateSite.dependsOn(Compile / unidoc).value,
    docusaurusPublishGhpages                   := docusaurusPublishGhpages
      .dependsOn(Compile / unidoc)
      .value
  )
  .dependsOn(zioDiffxJVM)
  .enablePlugins(MdocPlugin, DocusaurusPlugin, ScalaUnidocPlugin)
