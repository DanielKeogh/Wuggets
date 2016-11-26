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
                                          :time2 (java.sql.Timestamp. (+ (* 100 n) (.getTime time))) 
                                          :level level}) 
                           levels (range))))

(defn set-source-location [code x y]
  (sql/insert-multi! pg-db :location
                     [{:x x :y y :code code}]))

(defn format-logs [logs]
  (clojure.string/join 
   "|" 
   (map (fn [e] (clojure.string/join "," [(:x e) (:y e) (:rtime e) (:avglevel e)]) )
        (take 1000 logs))))

(defn get-logs [start end]
  (sql/query 
   pg-db
   ["select l.x, l.y, date_trunc('second', s.time2) rtime, avg(s.level) avglevel from sounds s
join location l on code = source
where s.time2 > ? and s.time2 < ? 
group by date_trunc('second', s.time2), x, y
order by date_trunc('second', s.time2)
" start end]
   {}))

(defn get-log-str [start end]
  (format-logs (get-logs (java.sql.Timestamp. 0) (java.sql.Timestamp. 99999999999999))))

