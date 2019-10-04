CC := gcc
CXX := g++

JAVA_HOME := -I/usr/lib/jvm/java-11-oracle/include -I/usr/lib/jvm/java-11-oracle/include/linux

CCFLAGS := -O3 -DNDEBUG -fPIC
CXXFLAGS := -std=c++11 -g -Wall -DPIC -fPIC $(JAVA_HOME)

LIBS =  -L. -lmbe -lm

BUILD_DIR = $(PWD)
DEST_DIR = /usr/lib

COPY_FILES = $(DEST_DIR)/libinmarsat.so

OBJS=inmarsat_wrap.cpp ambe3600x2400.o ambe3600x2450.o ambe4800x3600.o ecc.o imbe7100x4400.o imbe7200x4400.o mbelib.o libmbe.a libinmarsat.so

all: $(OBJS)

copy: $(COPY_FILES)

$(DEST_DIR)/%.so: $(BUILD_DIR)/%.so
	sudo cp -f $< $@

libinmarsat.so: inmarsat_wrap.cpp inmarsat.cpp mbelib.h libmbe.a
	$(CXX) $(CXXFLAGS) -shared -o $@ $^ $(LIBS)

inmarsat_wrap.cpp: inmarsat.i
	swig -c++ -java -o $@ $^

libmbe.a: ambe3600x2400.o ambe3600x2450.o ambe4800x3600.o ecc.o imbe7100x4400.o imbe7200x4400.o mbelib.o
	ar -cvq $@ $^

ambe3600x2400.o: ambe3600x2400.c
	$(CC) $(CCFLAGS) -c -o $@ $<

ambe3600x2450.o: ambe3600x2450.c 
	$(CC) $(CCFLAGS) -c -o $@ $<

ambe4800x3600.o: ambe4800x3600.c
	$(CC) $(CCFLAGS) -c -o $@ $<

ecc.o: ecc.c 
	$(CC) $(CCFLAGS) -c -o $@ $<

imbe7100x4400.o: imbe7100x4400.c
	$(CC) $(CCFLAGS) -c -o $@ $<

imbe7200x4400.o: imbe7200x4400.c 
	$(CC) $(CCFLAGS) -c -o $@ $<

mbelib.o: mbelib.c
	$(CC) $(CCFLAGS) -c -o $@ $<

clean: 
	rm -f $(OBJS)