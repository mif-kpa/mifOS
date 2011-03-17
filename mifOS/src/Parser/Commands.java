/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Parser;

import RealMachine.Registers;

/**
 *
 * @author Piotr Petunov
 */
public class Commands {
/*
    // Sudetis
    public void Axyz(byte x, byte y, byte z) {
        Arritmetic(true, x, y, z);
    }

    // Atimtis
    public void Sxyz(byte x, byte y, byte z) {
        Arritmetic(false, x, y, z);
    }

    // Jei type yra true - sudetis, jei false - atimtis
    private void Arritmetic(boolean type, byte x, byte y, byte z) {
        Registers register = getRegister();
        switch (x) {
            case 0:
                if (z == 0) {
                    if (type)
                        register.r += register.r;
                    else
                        register.r -= register.r;
                }
                else if (z ==1) {
                    if (type)
                        register.r += register.m;
                    else
                        register.r -= register.m;
                }
                break;

            case 1:
                if (type)
                    register.r += GetMemory(y * 0x10 + z);
                else
                    register.r -= GetMemory(y * 0x10 + z);
                break;

            case 16:
                if (z == 0) {
                    if (type)
                        register.m += register.r;
                    else
                        register.m -= register.r;
                }
                else if (z ==1) {
                    if (type)
                        register.m += register.m;
                    else
                        register.m -= register.m;
                }
                break;

            case 17:
                if (type)
                    register.m += GetMemory(y * 0x10 + z);
                else
                    register.m += GetMemory(y * 0x10 + z);
                break;
        }
    }

    // Palyginimas
    public void Cxyz(byte x, byte y, byte z) {
        Registers register = getRegister();
        switch (x) {
            case 0:
                if (register.r < register.m)
                    register.sf = 0;
                else if (register.r == register.m)
                    register.sf = 1;
                else
                    register.sf = 2;
                break;
                
            case 1:
                if (register.r < GetMemory(y * 0x10 + z))
                    register.sf = 0;
                else if (register.r == GetMemory(y * 0x10 + z))
                    register.sf = 1;
                else
                    register.sf = 2;
                break;

            case 16:
                if (register.m < register.r)
                    register.sf = 0;
                else if (register.m == register.r)
                    register.sf = 1;
                else
                    register.sf = 2;
                break;
                
            case 17:
                if (register.m < GetMemory(y * 0x10 + z))
                    register.sf = 0;
                else if (register.m == GetMemory(y * 0x10 + z))
                    register.sf = 1;
                else
                    register.sf = 2;
                break;
        }
    }

    // And
    public void Nxyz(byte x, byte y, byte z) {
        Binary(0, x, y, z);
    }

    // Or
    public void Oxyz(byte x, byte y, byte z) {
        Binary(1, x, y, z);
    }

    // Xor
    public void Xxyz(byte x, byte y, byte z) {
        Binary(2, x, y, z);
    }

    // Jei type = 0 tai and, jei 1 tai or, jei 2 tai xor
    private void Binary(int type, byte x, byte y, byte z) {
        Registers register = getRegister();
        switch (x) {
            case 0:
                if (z == 0) {
                    if (type == 0)
                        register.r &= register.r;
                    else if (type == 1)
                        register.r |= register.r;
                    else if (type == 2)
                        register.r ^= register.r;
                }
                else if (z == 1)
                {
                    if (type == 0)
                        register.r &= register.m;
                    else if (type == 1)
                        register.r |= register.m;
                    else if (type == 2)
                        register.r ^= register.m;
                }
                break;
                
            case 1:
                if (type == 0)
                    register.r &= GetMemory(y * 0x10 + z);
                else if (type == 1)
                    register.r |= GetMemory(y * 0x10 + z);
                else if (type == 2)
                    register.r ^= GetMemory(y * 0x10 + z);
                break;

            case 16:
                if (z == 0) {
                    if (type == 0)
                        register.m &= register.r;
                    else if (type == 1)
                        register.m |= register.r;
                    else if (type == 2)
                        register.m ^= register.r;
                }
                else if (z == 1)
                {
                    if (type == 0)
                        register.m &= register.m;
                    else if (type == 1)
                        register.m |= register.m;
                    else if (type == 2)
                        register.m ^= register.m;
                }
                break;

            case 17:
                if (type == 0)
                    register.m &= GetMemory(y * 0x10 + z);
                else if (type == 1)
                    register.m |= GetMemory(y * 0x10 + z);
                else if (type == 2)
                    register.m ^= GetMemory(y * 0x10 + z);
                break;
        }
    }
*/
}
