.phony: simple-dev

clj-repl:
	clojure

cljs-browser-repl:
	clojure --main cljs.main --compile browser.main -r

cljs-browser-dev:
	clojure -J-Dclojure.server.repl="{:port 5555 :accept cljs.server.browser/repl}]}"

fancier-cljs-repl:
	clojure -Sdeps '{:deps {com.bhauman/rebel-readline-cljs {:mvn/version "0.1.3"}}}' -m rebel-readline.cljs.main

fancier-clj-repl:
	clojure -A:rebel

clj-simple-dev:
	clojure -A:simple-dev -J-Dclojure.server.example1="{:port 5555 :accept clojure.core.server/repl}"

cljs-node-dev:
	clojure -J-Dclojure.server.repl="{:port 5555 :accept cljs.server.node/repl :args [{:opts $$(cat cljsc_opts.edn)}]}"

prepl-dev:
	clojure -A:simple-dev -J-Dclojure.server.prepl="{:port 5556 :accept clojure.core.server/io-prepl}"

compile-node:
	clojure --main cljs.main --target nodejs --compile canvas.main
