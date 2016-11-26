(ns sound-tracker.data
  (:require [clojure.java.jdbc :as sql]))

(def pg-db {:dbtype "postgresql"
            :dbname "sound"
            :host "localhost"})


(defn make-sql-date [year month day]
  (java.sql.Date.
   (.getTimeInMillis
    (java.util.GregorianCalendar. year month day))))
 
(defn log [source time levels]
  (sql/insert-multi! pg-db :sounds 
                     (mapv (fn [level n] {:source source 
                                          :time (java.sql.Timestamp. (+ (* 100 n) (.getTime time))) 
                                          :level level}) 
                           levels (range))))

(defn get-logs []
  (sql/query pg-db
  ["select * from sound"]
  {})
  )
