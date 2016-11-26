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

(defn format-logs [logs]
  (clojure.string/join 
   "a" 
   (map (fn [e] (clojure.string/join "a" [(:source e) (:time e) (:level e)]) )
        (take 20 logs))))

(defn get-logs [start end]
  (sql/query 
   pg-db
   ["select * from sounds where time > ? and time < ? order by time" start end]
   {}))
