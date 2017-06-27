(defproject clj-book "0.1.0-SNAPSHOT"
  :description "Tools for writing the Clojure book."
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot clj-book.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
