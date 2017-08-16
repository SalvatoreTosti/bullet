(ns bullet.core
  (:gen-class)
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [clj-time.core :as clj-time]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn day-of-week-string [x]
  (cond
   (= x 1) "Monday"
   (= x 2) "Tuesday"
   (= x 3) "Wednesday"
   (= x 4) "Thursday"
   (= x 5) "Friday"
   (= x 6) "Saturday"
   (= x 7) "Sunday"
   :else "Unknown"))

;entry
;  task
;  note
;  event

(defn task []
  {:type :task :title "I'm a task" :due-date nil :done false})
(defn note []
  {:type :note :title "I'm a note"})
(defn event []
  {:type :event :title "I'm an event" :date nil })

(defn future-log []
  {:type :future :year 2017 :months [{:name "August" :entries [(task) (note) (event)]} {:name "September" :entries [(task) (note) (event)]}]})

(defn monthly-log []
  {:type :monthly :month "August" :year 2017 :entries [(task) (note) (event)]})

(defn daily-log []
  {:type :daily :date (clj-time/today) :entries [(task) (note) (event)]})

(defn test-index []
  {:future-log (future-log)
   :monthly-log (monthly-log)
   :daily-log (daily-log)
   :collections {}})

(defn- view-task [task]
  (if (:done task) (println "x " (:title task))
    (println "* " (:title task))))

(defn- view-note [note]
  (println "- "(:title note)))

(defn- view-event [event]
  (println "o " (:title event)))

(defmulti view-entry
  (fn[entry] (entry :type)))
(defmethod view-entry :task [entry]
  (view-task entry))
(defmethod view-entry :note [entry]
  (view-note entry))
(defmethod view-entry :event [entry]
  (view-event entry))
(defmethod view-entry :default [entry]
  (println "? " (:title entry)))

(defn- view-daily-log [log]
  (println (day-of-week-string
            (clj-time/day-of-week
             (log :date)))
           (.toString
            (log :date)))
  (doall (map view-entry (log :entries))))

(defn- view-monthly-log [log]
  (println (log :month) (log :year))
  (doall (map view-entry (log :entries))))

(defn- future-log-print-month [month]
  (println (:name month))
   (doall (map view-entry (month :entries))))

(defn view-future-log [log]
  (println (log :year))
  (doall (map future-log-print-month (:months log))))

(defmulti view-log
  (fn[log] (log :type)))
(defmethod view-log :daily [log]
  (view-daily-log log))
(defmethod view-log :monthly [log]
  (view-monthly-log log))
(defmethod view-log :future [log]
  (view-future-log log))
