%module inmarsatAmbe

%include <typemaps.i>
%include <std_string.i>
%include <arrays_java.i>

%apply signed char[] { const char *  };
%apply signed char[] { char *  };

%pragma(java) jniclasscode=%{
    static {
         boolean embeddedLibrary = EmbeddedLibraryTools.LOADED_EMBEDDED_LIBRARY;
    }
%}

%{
#include "native/inmarsat.h"
%}
%include "native/inmarsat.h"
