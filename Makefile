JFLAGS = -cp
JC = javac
JAR = \
     ".:stanford-corenlp-3.5.2.jar:stanford-postagger-3.5.2.jar:stanford-corenlp-3.5.2-models.jar:stanford-corenlp-3.5.2-sources.jar:opennlp-tools-1.6.0.jar" \
#JAR = .:\* 
      
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $(JAR) $*.java

CLASSES = \
	MapUtil.java \
	StandCoreNLP.java \
    resumeScanner.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
