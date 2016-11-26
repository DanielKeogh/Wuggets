(ns sound-tracker.pages
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hc]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn core-page [head body]
  (hc/html 
   (into [:head] head)
   (into [:body] body)))

(defn get-view []
  [:div.view
   [:canvas.map]])

(defn home []
  (core-page [[:title "Wuggets Core"]]
             [[:h1 "Page Viewer"]]))
