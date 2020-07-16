(defproject net.valerauko/masakari "0.8.0"
  :description "Tool to detect unused entries in your dictionary files."
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.2-alpha1"]
                 [metosin/jsonista "0.2.6"]]
  :plugins [[io.taylorwood/lein-native-image "0.3.0"]]
  :main ^:skip-aot masakari.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
  :native-image
  {:name "masakari"
   :opts ["--no-server"
          "--no-fallback"
          "--report-unsupported-elements-at-runtime"
          "--initialize-at-build-time"
          "--allow-incomplete-classpath"]})
