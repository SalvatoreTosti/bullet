(ns bullet.core
  (:gen-class)
  (:require [lanterna.screen :as s]
            [lanterna.terminal :as t]
            [clj-time.core :as clj-time]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

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
  {:year 2017 :months {:august nil :september nil}})

(defn monthly-log []
  {:month "August" :year 2017 :entries []})

(defn daily-log []
  {:date (clj-time/today) :entries [(task) (note) (event)]})

(defn test-index []
  {:future-log (future-log)
   :monthly-log (monthly-log)
   :daily-log (daily-log)
   :collections {}})

(defn view-task [task]
  (if (:done task) (println "x " (:title task))
    (println "* " (:title task))))

(defn view-note [note]
  (println "- "(:title note)))

(defn view-event [event]
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

(defn view-daily-log [daily-log]
  (doall (map view-entry (daily-log :entries))))
