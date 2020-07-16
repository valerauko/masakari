(ns masakari.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]]
            [jsonista.core :as json])
  (:gen-class))

(defn flatten-map
  ([item] (flatten-map item {} []))
  ([item aggr parents]
   (reduce-kv
    (fn [aggr k v]
      (let [key-added (conj parents k)]
        (if (map? v)
          (flatten-map v aggr key-added)
          (assoc aggr (str/join "." key-added) false))))
    aggr
    item)))

(defn dict-map
  [file]
  (json/read-value file))

(defn file-lists
  [all-files]
  (->> all-files
       (filter #(.isFile %))
       (reduce
         (fn group-files
           [files file]
           (let [filename (.getName file)
                 type (condp re-find filename
                        #"\.json$" :lang
                        #"\.(js|vue)$" :code
                        nil)]
             (if type
               (update files type conj file)
               files)))
         {:lang [] :code []})))

(defn usages-in-file
  [file]
  (with-open [reader (io/reader file)]
    (reduce
      (fn [aggr line]
        (if-let [match (some->> line
                                (re-seq #"(?:[$.]t\(|v-t=\")'([^']+)'")
                                (map second))]
          (concat aggr match)
          aggr))
      []
      (line-seq reader))))

(defn -main
  "I don't do a whole lot ... yet."
  [& [path]]
  (let [path (or path ".")
        all-files (file-seq (io/file path))
        {:keys [lang code]} (file-lists all-files)
        dicts (apply merge (map (comp flatten-map dict-map) lang))
        usages (->> code
                    (map usages-in-file)
                    (reduce concat))]
    (->> usages
         (reduce (fn [present addr] (dissoc present addr)) dicts)
         keys
         sort
         pprint)))
