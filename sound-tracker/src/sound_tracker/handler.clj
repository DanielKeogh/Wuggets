(ns sound-tracker.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [sound-tracker.data :as data]
            [sound-tracker.pages :as pages]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (pages/home))
  (POST "/in" req
        (let [params (:params req)
              source (get params :source)
              time (java.sql.Date. (System/currentTimeMillis))
              level (get params :level)]
          (println (str "New in " source " " time " " level))
          (data/log source time level)))
  (route/resources "/")
  (route/not-found "404 Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
