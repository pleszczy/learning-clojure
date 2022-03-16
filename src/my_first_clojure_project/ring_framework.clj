(ns my-first-clojure-project.ring-framework)

(use 'ring.middleware.resource
     'ring.middleware.content-type
     'ring.middleware.not-modified)


(defn content-type-response [response content-type]
  (assoc-in response [:headers "Content-Type"] content-type))

;(defn wrap-content-type [handler content-type]
;  (fn
;    ([request]
;     (-> (handler request)
;         (content-type-response content-type)))
;    ([request respond raise]
;     (-> (handler request)
;         (#(respond (content-type-response % content-type)) raise)
;
;         )
;     )
;    )
;  )
(response "Hello World")

(def app
  (wrap-content-type handler "text/html"))

(def app
  (-> handler
      (wrap-content-type "text/html")
      (wrap-keyword-params)
      (wrap-params)))

(def app
  (-> your-handler
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)
      )
  )

