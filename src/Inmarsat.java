/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */


public class Inmarsat {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Inmarsat(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Inmarsat obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        aero_ambeJNI.delete_Inmarsat(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Inmarsat() {
    this(aero_ambeJNI.new_Inmarsat(), true);
  }

  public void to_decode_slot(byte[] datas, int size) {
    aero_ambeJNI.Inmarsat_to_decode_slot(swigCPtr, this, datas, size);
  }

  public void to_decode_all(byte[] datas, int size, byte[] result) {
    aero_ambeJNI.Inmarsat_to_decode_all(swigCPtr, this, datas, size, result);
  }

}
