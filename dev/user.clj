(ns user
  (:require [clojure.tools.deps.alpha.repl :as tools-repl]
            [clojure.tools.deps.alpha.libmap :as libmap]
            [clojure.tools.namespace.repl :refer [refresh set-refresh-dirs]]))


;; (defn pull-gist-dep
;;   "For a pocket gist yadda yadda improve before talk"
;;   [name config]
;;   (let [individuated-name (symbol (->> (:sha config)
;;                                        (take 4)
;;                                        (apply str))
;;                                   (str name))]
;;    (tools-repl/add-lib individuated-name
;;                        config)
;;    (require 'secret)))

(comment

  (tools-repl/add-lib 'secret {:git/url "https://gist.githubusercontent.com/jobez/b58c7b5cc7325e687daaf07a8108a850"  :sha "435e8966256699b555c2b27b092926b0ea049ec1"})

  (pull-gist-dep 'secrety {:git/url "https://gist.githubusercontent.com/jobez/b58c7b5cc7325e687daaf07a8108a850"  :sha "435e8966256699b555c2b27b092926b0ea049ec1"})

  (symbol "tree")
  (libmap/lib-map)
  (use 'secret))
