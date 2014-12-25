;
; Examples of some math routines.
;

(define fact 
  (lambda (n) 
    (if (= 1 n)    (quote 1) (* n (fact (- n 1)))    )   )  )

(define deriv
  (lambda (f x dx)
    (/ (- (f (+ x dx)) (f x))
       dx)
    )
  )

(define riemann 
  (lambda (f x1 x2) 
    (if (= x1 x2) 
	(f x1)  
      (+ (riemann f (+ x1 1) x2) (f x1) )
      )
    )
  )

; test
(define factResult (fact 3))
(define derivResult (deriv (lambda (x) (* x x)) 100 2))
(define riemannResult (riemann (lambda (x) (* x 2)) 0 10))

(printenv)