# JarabraDix #

-----------------------------------------------------------------------

## What is JarabraDix ? ##

* **JarabraDix** is a Java library
that supports **binary integer value to decimal sequence conversion**.

* Yes, it will substitute implementations such as
`Integer.toString(int)`, `System.out(=PrintStream).println(int)` and so on.


## DoubDabC implementation ##

* There is no String constructor during conversion.
That means, **GC-friendry !**


## Supported Input ##

* JarabraDix supports int & long primitive value input.


## Supported Output ##

* You can get decimal number sequence result by `char[]` array.

* You can assign
`Appendable`, `Writer`, `StringBuffer`, `StringBuilder`, or `CharBuffer`
as Arabic numeral characters\(0-9\) sequence output.

* `CharSequence` wrapper class is provided.

* Extended `Writer` class is provided
which supports `print(int)` & `print(long)` methods
like `PrintWriter`.


## How to build ##

* JarabraDix needs to use [Maven 3.0.1+](https://maven.apache.org/)
and JDK 1.7+ to be built.

* JarabraDix runtime does not depend on any other library at all.
Just compile Java sources under `src/main/java/` if you don't use Maven.


## License ##

* Code is under [The MIT License][MIT].


## Project founder ##

* By [olyutorskii](https://github.com/olyutorskii) at 2017


[MIT]: https://opensource.org/licenses/MIT


--- EOF ---
