# zio-diffx

## In light of ZIO 2's ability to perform diffing in zio-test, this project has been archived.

| Project Stage | CI | Release | Snapshot | Discord |
| --- | --- | --- | --- | --- |
| [![Project stage][Badge-Stage]][Link-Stage-Page] | [![Build Status][Badge-Circle]][Link-Circle] | [![Release Artifacts][Badge-SonatypeReleases]][Link-SonatypeReleases] | [![Snapshot Artifacts][Badge-SonatypeSnapshots]][Link-SonatypeSnapshots] | [![Badge-Discord]][Link-Discord] |

# Summary

A simple integration library between ZIO and [diffx](https://github.com/softwaremill/diffx).

Currently only supports ZIO 1.x, but 2.x should be easy to support. As the code is
minimal, a valid use-case might be to copy and paste the code into your
repository, which should be fine given licensing under the MPL-2.0.

zio-diffx cannot support Scala 3 until diffx artifacts are published, which
appears to be a work-in-progress.

# Dependency Coordinates

Make sure jitpack is in your resolvers, e.g.:

```scala
  resolvers ++= Seq(
    "jitpack.io" at "https://jitpack.io/",
  ),
```

The general suggestion is to use the most recent release listed on the
GitHub releases page for the project (not necessarily what is shown below):

```scala
"io.github.bbarker" %% "zio-diffx" % "0.0.5" % Test
```

# Documentation
[blog page on Medium](https://medium.com/@brandon.barker/zio-test-and-diffx-integration-42c003eb709)

# Contributing
[Documentation for contributors](https://zio.github.io/zio-diffx/docs/about/about_contributing)

## Code of Conduct

See the [Code of Conduct](https://zio.github.io/zio-diffx/docs/about/about_coc)

## Support

Come chat with us on [![Badge-Discord]][Link-Discord].


# License
[License](LICENSE)

[Badge-SonatypeReleases]: https://img.shields.io/nexus/r/https/oss.sonatype.org/dev.zio/zio-diffx_2.12.svg "Sonatype Releases"
[Badge-SonatypeSnapshots]: https://img.shields.io/nexus/s/https/oss.sonatype.org/dev.zio/zio-diffx_2.12.svg "Sonatype Snapshots"
[Badge-Discord]: https://img.shields.io/discord/629491597070827530?logo=discord "chat on discord"
[Badge-Circle]: https://circleci.com/gh/zio/zio-diffx.svg?style=svg "circleci"
[Link-Circle]: https://circleci.com/gh/zio/zio-diffx "circleci"
[Link-SonatypeReleases]: https://oss.sonatype.org/content/repositories/releases/dev/zio/zio-diffx_2.12/ "Sonatype Releases"
[Link-SonatypeSnapshots]: https://oss.sonatype.org/content/repositories/snapshots/dev/zio/zio-diffx_2.12/ "Sonatype Snapshots"
[Link-Discord]: https://discord.gg/2ccFBr4 "Discord"
[Badge-Stage]: https://img.shields.io/badge/Project%20Stage-Concept-red.svg
[Link-Stage-Page]: https://github.com/zio/zio/wiki/Project-Stages

