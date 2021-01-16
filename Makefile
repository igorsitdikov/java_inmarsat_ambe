CC := gcc
CXX := g++

LIB_NAME := libinmarsat

ifeq ($(OS),Windows_NT)
	TARGET := $(LIB_NAME).dll
	JAVA_OS := win32
	ifeq ($(PROCESSOR_ARCHITECTURE),AMD64)
		OS_PATH := amd64/Windows
	endif
else
    TARGET := $(LIB_NAME).so
	JAVA_OS := linux
	UNAME_S := $(shell uname -s)
	UNAME_P := $(shell uname -p)
	ifeq ($(UNAME_S),Linux)
		ifeq ($(UNAME_P),x86_64)
		    OS_PATH := amd64/Linux
    	endif
	endif
endif

JAVA_HOME = /usr/lib/jvm/java-1.8.0-openjdk-amd64
CFLAGS := -O3 -DNDEBUG -fPIC
CXXFLAGS := -std=c++11 -g -Wall -DPIC -fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/$(JAVA_OS)

OUTPUT_PATH := src/main/resources/NATIVE/$(OS_PATH)/

SOURCES = \
	native/mbelib.c \
	native/ambe3600x2400.c \
	native/imbe7200x4400.c \
	native/imbe7100x4400.c \
	native/ecc.c \
	native/ambe4800x3600.c \
	native/ambe3600x2450.c

LIBS =  -L. -lmbe -lm

all: $(TARGET) copy

copy:
	mkdir -p $(OUTPUT_PATH)
	strip -g $(TARGET)
	cp $(TARGET) $(OUTPUT_PATH)
	chmod +x copy_dependencies.sh
	./copy_dependencies.sh $(TARGET) $(OUTPUT_PATH)

$(TARGET): inmarsat_wrap.cpp native/inmarsat.cpp native/mbelib.h libmbe.a
	$(CXX) $(CXXFLAGS) -shared -o $@ $^ $(LIBS)

inmarsat_wrap.cpp: native/inmarsat.i
	mkdir -p src/main/java/l2m/decoder/inmarsat
	swig -c++ \
		-java -package l2m.decoder.inmarsat \
		-outdir src/main/java/l2m/decoder/inmarsat -o $@ $<

libmbe.a: $(SOURCES:.c=.o)
	ar -cvq $@ $^

clean:
	$(RM) -f native/*.o *.so *.dll *.a *_wrap.cpp *.wav $(OBJS)