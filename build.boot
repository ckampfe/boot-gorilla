(def project 'ckampfe/boot-gorilla)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"src"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [boot/core "RELEASE" :scope "test"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [gorilla-repl "0.3.6"]])

(task-options!
 pom {:project     project
      :version     version
      :description "start the gorilla repl"
      :url         "https://github.com/ckampfe/boot-gorilla"
      :scm         {:url "https://github.com/ckampfe/boot-gorilla"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]])
