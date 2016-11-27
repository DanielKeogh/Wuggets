(ns sound-tracker.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [sound-tracker.data :as data]
            [sound-tracker.pages :as pages]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (pages/home))
  (GET "/add-space" [] (pages/add-space))
  (GET "/spaces" [] (pages/spaces))
  (GET "/wuggets" [] (pages/space "Wuggets" "map"))
  (GET "/spaces/:spacename" [spacename]
       (pages/space spacename "map"))
  (POST "/out" req 
        (let [params (:params req)
              start (:start req)
              end  (:end req)]
          (data/get-log-str start end)))

  (POST "/in" req
        (let [params (:params req)
              source (get params :source)
              time (java.sql.Timestamp. (System/currentTimeMillis))
              levels (mapv (fn [e] (java.lang.Float/parseFloat e)) (get params :levels))]
          (println (str "New in " source " " time " " levels))
          (data/log source time levels)))
  (POST "/add-code" req
        (let [params (:params req)
              code (:code params)
              x (java.lang.Integer/parseInt (:x params))
              y (java.lang.Integer/parseInt (:y params))]

          (println (str "Code added" code " " x " " y))
          (data/set-source-location code x y)))
  (route/resources "/")
  (route/not-found "404 Not Found"))

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))
