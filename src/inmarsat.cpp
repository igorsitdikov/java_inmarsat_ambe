#include "inmarsat.h"
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <iostream>
#include <iomanip>
#include <random>
#include <cmath>
#include <cassert>
#include <chrono>
#include <cstring>
#include <algorithm>
#include <functional>
#include <fstream>

using namespace std;

Inmarsat::Inmarsat(/* args */)
{
    mbe_initMbeParms(&current, &prev, &prev_mp_enhanced);
}

void Inmarsat::to_decode_slot(const char * datas, int size){
    errors = 0;
    errors2 = 0;
    unsigned char buf[size];
    for (int i = 0; i < size; i++)
        buf[i] = *(((char *)datas) + i);

    for (int j = 0; j < size; j+=12)
    {

        for (int k = 0; k < 12; ++k) {
            data[k] = buf[k + j];
        }
    for (int x = 0; x < 12; x++) {
        for (int i = 0; i < 8; i++) {
            // reversed
            ambe_bits[(x * 8) + i] = (((data[x]) >> i) & 0x01);
        }
    }

    for (int i = 0; i < 96; i++) {
        ambe_fr[rW[i]][rX[i]] = ambe_bits[i];
    }

    mbe_processAmbe4800x3600Frame(((short *) decoded_data), &errors, &errors2, errorbuffer,
                                    ambe_fr, ambe_d, &current, &prev, &prev_mp_enhanced, 1);
    }
}

void Inmarsat::to_decode_all(const char * datas, int size, char* result){
    errors = 0;
    errors2 = 0;
    char buf[size];
    for (int i = 0; i < size; i++)
    buf[i] = *(((char *)datas) + i);
    int counter = 0;
    for (int j = 0; j < size; j+=12)
    {

        for (int k = 0; k < 12; ++k) {
            data[k] = buf[k + j];
        }
        for (int x = 0; x < 12; x++) {
            for (int i = 0; i < 8; i++) {
                // reversed
                ambe_bits[(x * 8) + i] = (((data[x]) >> i) & 0x01);
            }
        }

        for (int i = 0; i < 96; i++) {
            ambe_fr[rW[i]][rX[i]] = ambe_bits[i];
        }

        mbe_processAmbe4800x3600Frame(((short *) decoded_data), &errors, &errors2, errorbuffer,
                                    ambe_fr, ambe_d, &current, &prev, &prev_mp_enhanced, 1);
   
        for (int l = 0; l < 320; l++)
        {
            result[counter++] = (char)decoded_data[l];
        }
    }
}
Inmarsat::~Inmarsat()
{
}