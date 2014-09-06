weirdo-scheme
=============

A Scheme/LISP interpreter, mostly based on lis.py

Current status: Some commands work, recursion not so much.

Example output:
---------------

```
>> (define myVar 1)
===> ()
>> (define myFunc (lambda (arg1 arg2) (+ arg1 arg2 10)))
===> ()
>> (myFunc 1 2)
===> 13
>> (printenv)
<printenv>Environment: myVar, myFunc, 
===> ()
>> exit
```
