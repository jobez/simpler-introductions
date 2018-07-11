(ns socket
  (:require [clojure.main :as main]
            [clojure.core.server :as server])
  (:import [java.net ServerSocket]))

;; thank you for this example https://github.com/puredanger/replicant/blob/master/src/replicant/util.clj

(defonce ^:dynamic *bindings* (atom {}))

;; helpers for data-centric repls

(defn data-eval
  [form]
  (let [out-writer (java.io.StringWriter.)
        err-writer (java.io.StringWriter.)
        capture-streams (fn []
                          (.flush *out*)
                          (.flush *err*)
                          {:out (.toString out-writer)
                           :err (.toString err-writer)})]
    (binding [*out* (java.io.BufferedWriter. out-writer)
              *err* (java.io.BufferedWriter. err-writer)]
      (try
        (let [result (eval form)]
          (merge (capture-streams) {:result result}))
        (catch Throwable t
          (merge (capture-streams) {:exception (Throwable->map t)}))))))

(defn data-repl
  [& kw-opts]
  (apply main/repl
    (conj kw-opts
      :need-prompt (constantly false)
      :prompt (constantly nil)
      :eval data-eval)))

;; add kw-opts to what's currently in clojure.core.server/repl
(defn repl
  [& kw-opts]
  (apply main/repl
    (conj kw-opts
      :init server/repl-init
      :read server/repl-read)))



;; helpers to stash and use bindings from another thread

(defn user-eval
  [binding-atom form]
  (let [result (eval form)]
    (reset! binding-atom (get-thread-bindings))
    result))

(defn user-repl
  [& kw-opts]
  (main/repl
    :init server/repl-init
    :eval (partial user-eval *bindings*)
    :read server/repl-read))

(defmacro with-user-bindings
  [binding-atom & body]
  `(with-bindings (deref ~binding-atom) ~@body))


(defn start-user-repl []
  (let [repl-name (gensym "repl")            ;; generate repl server name
        server  (server/start-server
                                {:name   (str repl-name)
                                 :port   0   ;; pick a free port
                                 :accept 'socket/user-repl})
        repl-port (.getLocalPort server)]
    repl-port))

(comment
  ;; First start a tooling repl server - tool repl will connect to this
  ;; -Dclojure.server.datarepl="{:port 5555 :accept 'replicant.util/data-repl}"

  ;; From tool, connect as a client to 127.0.0.1:5555

  (let [bindings (atom {})                   ;; shared atom to stash user's bindings
        ]

    ;; From tool, connect on repl-port for user repl
    ;; The user's client is now using user-eval above and will be
    ;; saving off their bindings in the shared atom.

    ;; The tooling repl can then observe those bindings and can eval in their context
    )
  )

(defn get-current-user-ns []
  (let [user-ns (with-user-bindings *bindings* *ns*)]
    ;; do whatever tool needs to do

    user-ns))
