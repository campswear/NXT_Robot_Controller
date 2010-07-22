CC            = gcc
CCFLAGS       = -c -g
LINK          = gcc
LDFLAGS_SUNOS = -lsocket
LDFLAGS_LINUX = 
LDFLAGS       = $(LDFLAGS_LINUX)
JAVAC         = javac
RM            = rm


.SUFFIXES:
.SUFFIXES: .o .c .class .java

.c.o:
	$(CC) $(CCFLAGS) $*.c -o $*.o

.java.class:
	$(JAVAC) $*.java



all: one two

one: client Server.class

two: server Client.class

client: client.o
	$(LINK) $(LDFLAGS) -o $@ $^

server: server.o
	$(LINK) $(LDFLAGS) -o $@ $^


clean:
	$(RM) *.o *.class


client.o:	client.c
server.o:	server.c
