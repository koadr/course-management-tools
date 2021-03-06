package com.lightbend.coursegentools

import java.io.File

/**
  * Copyright © 2016 Lightbend, Inc
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *
  * NO COMMERCIAL SUPPORT OR ANY OTHER FORM OF SUPPORT IS OFFERED ON
  * THIS SOFTWARE BY LIGHTBEND, Inc.
  *
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

object StudentifyCmdLineOptParse {

  def parse(args: Array[String]): Option[StudentifyCmdOptions] = {

    val parser = new scopt.OptionParser[StudentifyCmdOptions]("studentify") {
      head("studentify", "1.3")

      arg[File]("masterRepo")
        .text("base folder holding master course repository")
        .action { case (masterRepo, c) =>
          if (! folderExists(masterRepo)) {
            println(s"base master repo folder (${masterRepo.getPath}) doesn't exist")
            System.exit(-1)
          }
          c.copy(masterRepo = masterRepo)
        }

      arg[File]("out")
        .text("base folder for student repo")
        .action { case (out, config) =>
          if (! folderExists(out)) {
            println(s"base folder (${out.getPath}) doesn't exist")
            System.exit(-1)
          }
          config.copy(out = out)}

      opt[Unit]("multi-jvm")
        .text("generate multi-jvm build file")
        .abbr("mjvm")
        .action { case (_, c) =>
                c.copy(multiJVM = true)
            }

      opt[String]("first-exercise")
          .text("name of first exercise to output")
            .abbr("fe")
              .action { case (firstExercise, c) =>
                  c.copy(first = Some(firstExercise))
              }

      opt[String]("last-exercise")
        .text("name of last exercise to output")
        .abbr("le")
        .action { case (lastExercise, c) =>
          c.copy(last = Some(lastExercise))
        }

      opt[String]("selected-first-exercise")
        .text("name of initial exercise on start")
        .abbr("sfe")
        .action { case (selectedFirstEx, c) =>
          c.copy(selectedFirst = Some(selectedFirstEx))
        }
    }

    parser.parse(args, StudentifyCmdOptions())
  }
}
