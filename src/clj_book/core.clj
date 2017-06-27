(ns clj-book.core
  (:require [clojure.string :as s])
  (:gen-class))

(defn render-code
  [code lang]
  (let [pbr (java.io.PushbackReader. (java.io.StringReader. code))
        result (loop [result nil]
                 (if-let [expr (read pbr nil nil)] ;; nil nil so don't throw on EOF
                   (recur (eval expr))
                   (pr-str result)))]
    (format "```%s\n%s\n;; => %s```" lang code result)))

(defn my-book->md
  [my-book]
  (->> (loop [lines (s/split-lines my-book)
              result []]
         (if (empty? lines)
           result
           (let [line (first lines)]
             (if-let [[_ lang] (re-find #"<!---code:([^-]+)-->" line)]
               (let [end-index (.indexOf lines (format "<!---/code:%s-->" lang))
                     code (s/join "\n" (subvec lines 1 end-index))]
                 (recur (vec (drop (inc end-index) lines)) (conj result (render-code code lang))))
               (recur (vec (rest lines)) (conj result line))))))
       (s/join "\n")))

(defn -main
  [& args]
  (println
   (my-book->md (slurp "test.my-book"))))
