## kDancer
kDancer is a concordance analyser for lemmatised corpus data. While the algorithm is targeted for use with Finnish language data, it is not in itself restricted to any one language. Only the mode which identifies compound words makes some assumptions that are not guaranteed in every language.

#### Technical details
The program is written in Java 8 utilising BufferedReader for efficiently parsing corpus data consisting of possibly millions of lexemes. The algorithm makes two passes through the lemmatised data, indexing during the first pass and collecting the needed data on the second pass. While certain optimisations might allow the data to be parsed with only one pass, there can be no guarantees on the effectiveness without making assumptions about the corpus data size and composition.

#### Usage
The program can be run from a command line with arguments. Lexeme type can be either `simple` or `compound` which determines whether to identify simple or compound lemmas:

`java -jar <path to data-file> <target lemma> <context size> <lemma type> <optional path to output file>`

e.g.

`java -jar corpus.txt menettely 3 simple concordance.txt`

This will parse the file `corpus.txt`, finding lexemes with lemma `menettely`, creating contexts of size 3 (7 lexemes in total), only considering lexemes where the lemma is exactly `menettely` and outputting the resulting contexts into `concordance.txt`.

If the output file is not provided, the program will automatically output the result into a file with the name

`<target lemma>_<context size>_<lemma type>_<current datetime>`

#### Input file
The program accepts text-files where each line is of the following form:

`<lexeme> lemma`

The lines can be separated by either a single line feed (LF) or a combined line feed + carriage return (CR+LF). Empty lines will be ignored.

#### Output file
The program will output the results of the concordance analysis into a file with the following form

```
Lemma: menettely
Context size: 3
Lemma type: simple
Occurrences: 889
 
suoritetaan sitä vastaavan menettelyn mukaisesti yksi velvoitetyö | Line 11414
hänen edustajansa vilpillisestä menettelystä tai jos takaisin | Line 24386
.
.
.
```

where the line number indicates the line in the input file where the target lemma was identified.

#### Special cases
- If the lexeme contains a space the lexeme will be replaced by `<LEXEME INCLUDES SPACE>` in the context. Common examples of this are names. This behaviour is due to the way spaces are used to parse the input file. Custom separators might be implemented in the future.
- If the lexeme is an empty word, then the lexeme will be replaced by `<EMPTY WORD>` in the context.

#### Benchmarks
Coming soon...