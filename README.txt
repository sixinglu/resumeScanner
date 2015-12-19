Functionality: scanning resume, output verb and noun freq from job description in your resume
Author: Sixing Lu
Language: Java
=================
Please download 

1.  from http://opennlp.sourceforge.net/models-1.5/
en-parser-chunking.bin 

2.  from http://opennlp.apache.org/download.html
apache-opennlp-1.6.0-bin.tar.gz

3. from http://nlp.stanford.edu/software/lex-parser.shtml#Download
Version 3.5.2	

Please put these files into the folder
opennlp-tools-1.6.0.jar
stanford-corenlp-3.5.2-models.jar
stanford-corenlp-3.5.2-sources.jar
stanford-corenlp-3.5.2.jar
stanford-postagger-3.5.2.jar

=================

Attached a Makefile.

Please execute:
make
java -cp .:stanford-corenlp-3.5.2.jar:stanford-postagger-3.5.2.jar:stanford-corenlp-3.5.2-models.jar:stanford-corenlp-3.5.2-sources.jar:opennlp-tools-1.6.0.jar resumeScanner 'jobDesciption.txt' 'resume.txt' 




