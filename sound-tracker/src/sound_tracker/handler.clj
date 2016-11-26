(ns sound-tracker.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [sound-tracker.pages :as pages]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (pages/home))
  (route/resources "/")
  (route/not-found "404 Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
