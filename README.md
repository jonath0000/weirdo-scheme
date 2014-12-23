weirdo-scheme
=============

A Scheme/LISP interpreter, mostly based on lis.py

Current status: On par with lis.py. Has nice web interface.

Web interface: build with 
>> gradlew war
Or goto [this example](http://www.tapire-solutions.net/lisp/)

Java interface: build with 
>> gradlew installApp 
>> ./build/libs/bin/weirdo-scheme

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
