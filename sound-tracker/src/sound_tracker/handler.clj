(ns sound-tracker.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [sound-tracker.data :as data]
            [sound-tracker.pages :as pages]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (pages/home))
  (POST "/out" req 
        (let [params (:params req)
              start (java.sql.Timestamp. 0)
              end  (java.sql.Timestamp. 99999999999)]
          (str "wh" (data/format-logs (data/get-logs (java.sql.Timestamp. 0) (java.sql.Timestamp. 999999999999))))))

  (POST "/in" req
        (let [params (:params req)
              source (get params :source)
              time (java.sql.Timestamp. (System/currentTimeMillis))
              levels (mapv (fn [e] (java.lang.Float/parseFloat e)) (get params :levels))]
          (println (str "New in " source " " time " " levels))
          (data/log source time levels)))
  (route/resources "/")
  (route/not-found "404 Not Found"))

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
