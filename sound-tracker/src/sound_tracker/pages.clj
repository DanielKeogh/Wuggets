(ns sound-tracker.pages
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hc]
            [hiccup.page :as hpage]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))



(defn core-page [head body]
  (hpage/html5
   (into (into [:head] head)
         [[:link {:rel "stylesheet" 
                  :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
                  }]
          [:link {:rel "stylesheet" :href "style.css"}]])
   [:body 
    [:nav {:class "navbar navbar-inverse navbar-fixed-top"}
                 [:div {:class "container"}
                  [:button {:class "navbar-toggle collapsed" :data-toggle "collapse" :data-target "#navbar"
                            :aria-expanded "false" :aria-controls "navbar"}
                   [:span {:class "sr-only"} "Toggle navigation"]
                   [:span {:class "icon-bar"}]
                   [:span {:class "icon-bar"}]
                   [:span {:class "icon-bar"}]]
                  [:div {:class "navbar-header"}
                   [:a {:class "navbar-brand" :href "#"} "Wuggets"]]
                  [:div {:id "navbar" :class "collapse navbar-collapse"}
                   [:ul {:class "nav navbar-nav"}
                    [:li {:class "active"} [:a {:href "#"} "Home"]]]]]]
    [:div.container body]
    [:div#errors]
    (hpage/include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js")
    (hpage/include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js")
    (hpage/include-js "viewheat.js")]))

(defn get-heat-canvas [img-source]
  [:div#heatviewcontainer
   [:div#heatview
    [:img {:class "img" :src img-source :alt "A map."}]
    [:canvas#heatcanvas {:width 640 :height 480}]]
   [:form#view-range {:onsubmit "return formposted()"}
    [:input {:type "datetime" :value "2011-01-13"}]
    [:input {:type "datetime" :value "2011-01-13"}]
    [:input {:type "submit" :value "View Period"} ]
    ]
   ])

(defn home []
  (core-page [[:title "Wuggets Core"]]
             [:div [:h1 "Page Viewer"]
              (get-heat-canvas "maps/sample.png")
              ]))
