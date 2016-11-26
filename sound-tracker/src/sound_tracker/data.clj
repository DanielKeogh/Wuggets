(ns sound-tracker.data
  (:require [clojure.java.jdbc :as sql]))

(def pg-db {:dbtype "postgresql"
            :dbname "sound"
            :host "localhost"})


(defn make-sql-date [year month day]
  (java.sql.Date.
   (.getTimeInMillis
    (java.util.GregorianCalendar. year month day))))
 
(defn log [source time level]
  (sql/insert-multi! pg-db :sounds [{:source source :time time :level level}]))
