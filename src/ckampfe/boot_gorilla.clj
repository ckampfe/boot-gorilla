(ns ckampfe.boot-gorilla
  "Boot the gorilla repl"
  {:boot/export-tasks true}
  (:require [boot.core :as boot :refer [deftask]]
            [boot.task.built-in :as task]
            [boot.pod :as pod]
            [boot.util :as util]))

(deftask gorilla
  "Start the gorilla-repl server"
  [p port       PORT int "The port to run the web REPL on. Defaults to 4000."
   i ip         IP   str "The IP address the server listens on. Defaults to 127.0.0.1"
   n nrepl-port PORT int "The port that the nREPL server will be started up on. Defaults to a free port."
   b block           bool "Blocking (for standalone use)"]
  (let [env          (pod/make-pod (boot/get-env))
        port         (or port 4000)
        ip           (or ip "127.0.0.1")
        nrepl-port   (or nrepl-port 0)
        project-name (or (:project env) "a standalone project")]
    (comp
     (boot/with-pre-wrap fileset
       (pod/with-eval-in env
         (require '[gorilla-repl.core :as gorilla])
         (gorilla/run-gorilla-server {:port ~port
                                      :ip ~ip
                                      :nrepl-port ~nrepl-port
                                      :project ~project-name})
         (boot.util/info "<< started Gorilla REPL on http://localhost:%d >>\n" ~port))
       fileset)
     (if block
       (task/wait)
       identity))))
