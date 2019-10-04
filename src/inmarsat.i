%module aero_ambe

%include <typemaps.i>
%include <std_string.i>
%include <arrays_java.i>

%apply signed char[] { const char *  };
%apply signed char[] { char *  };

%pragma(java) jniclasscode=%{
    static {
         System.loadLibrary("inmarsat");
    }
%}

%{
#include "inmarsat.h"
%}
%include "inmarsat.h"
