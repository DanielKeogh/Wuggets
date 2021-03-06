(ns sound-tracker.pages
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hc]
            [hiccup.page :as hpage]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn add-nav-page [name active url]
  [:li {:class (if (= name active) "active" "boop")} [:a {:href url} name]])

(defn core-page [name head body]
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
                    (add-nav-page "Home" name "/")
                    (add-nav-page "Create" name "/add-space")
                    (add-nav-page "Find" name "/spaces")
                    (add-nav-page "Start listening now!" name "/index.html")
                    ]]]]
    [:div.container body]
    [:div#errors]
    (hpage/include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js")
    (hpage/include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js")
    (hpage/include-js "render.js")
    (hpage/include-js "viewheat.js")
    ]))

(defn get-heat-canvas [img-source]
  [:div#heatviewcontainer
   [:div#heatview
    [:img {:class "img" :src img-source :alt "A map."}]
    [:canvas#heatcanvas {:width 640 :height 480}]]
   [:form#view-range {:onsubmit "return formposted()"}
   ; [:input {:type "datetime" :value "2011-01-13"}]
   ; [:input {:type "datetime" :value "2011-01-13"}]
    [:p "Press the button below to reveal the secrets of this epic Kingdom Hack software..."]
    [:input {:type "submit" :value "Experience"} ]]])

(defn home []
  (core-page "Home"
             [[:title "Wuggets - *The* Ambient Noise Monitoring System"]]
             [:div.core
              [:h1 "This is your Sound Space Experience"]
              [:p "Welcome to the worlds first ambient noise monitoring system."]
              [:ul.list-unstyled
               [:li [:i.swag "See"] " how sound affects your space."]
               [:li [:i.swag "Create"] " amazing visualisations."]
               [:li [:i.swag "Discover"] " new solutions to noise related problems."]
               [:li [:i.swag "Believe"] " in the future of space management."]]

              [:div [:h2 "Live Hackagong Map"]
               (get-heat-canvas "maps/map.png")]
              
              [:div [:h1 ]]]))


(defn add-space []
  (core-page "Create"
   [[:title "Wuggets - Create your space"]]
   [:div.space [:h1 "Create a Space"]
     [:form {:action "/spaces" :method :get}
      [:ul.list                       
       [:li.list-group-item [:label {:for "nme"} "Name your Space"]
        [:p [:input {:type "text" :name "nme" :placeholder "e.g. London Airport"}]]]
       [:li.list-group-item [:label {:for "pic"} "Upload an Image"]
        [:input {:type "file" :name "pic" :accept "image"}]]
       [:li.list-group-item                        
        [:input {:type "submit" :value "Push to Innovate"}]]]]]))

(defn spaces [] 
  (core-page "Find"
             [[:title "Wuggets - Find your space"]]
             [:div [:h1 "Find your space"]
              [:ul [:li [:a {:href "/wuggets"} "Wuggets"]]]
              [:h2 [:a {:href "/add-space"} "Add New Space"]]]))

(defn space [space-name filename]
  (core-page space-name
             [[:title (str "Space - " space-name)]]
             [:div [:h1 space-name]
              (get-heat-canvas (str "maps/" filename ".png"))
              [:h3 "Map coordinates"]

              [:form {:action "add-code" :method :post}
               [:ul.list-group
                [:li.list-group-item [:label {:for "code"} "Add a location code."]
                 [:p [:input {:type "text" :name "code" :placeholder "e.g. AAA"}]]]
                [:li.list-group-item [:label {:for "x"} "Set your X"]
                 [:p [:input {:type "text" :name "x" :placeholder "e.g. 50"}]]]
                [:li.list-group-item [:label {:for "y"} "Set your Y"]
                 [:p [:input {:type "text" :name "y" :placeholder "e.g. 50"}]]]
                [:li.list-group-item
                 [:input {:type "submit" :value "Assosciate Code"}]]]]
              ]))
