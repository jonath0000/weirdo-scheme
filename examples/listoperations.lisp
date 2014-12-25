;
; Some examples of list operations.
;

(print (map (lambda (x) (+ x x)) (quote 3 2 1)))

(print (first (quote 1 2)))

(print (rest (quote 1 2 3)))

(print (cons 1 (quote 2 3)))